package com.ljzn.aippv2.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import com.ljzn.aippv2.entity.ImageData;
import com.ljzn.aippv2.entity.LjFace;
import com.ljzn.aippv2.entity.LjFeature;
import com.ljzn.aippv2.entity.LjImageType;
import com.ljzn.aippv2.entity.LjPonitf;
import com.ljzn.aippv2.sdk.Aippv2Library;
import com.ljzn.aippv2.util.OpenCvUtils;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Platform;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

/**
 * 
 * @author JianLinWei
 * @date: 2019年7月8日下午3:06:44
 */
public final class LjAippv2Core {

	static {
		try {
			if (Platform.isWindows()) {
				String dlls[] = Platform.is64Bit()
						? new String[] { "libgcc_s_seh-1", "libgfortran-3", "libopenblas", "libquadmath-0", "ml_face",
								"ml_face", "opencv_world341", "opencv_world341", "opencv_java341" }
						: new String[] { "libgcc_s_sjlj-1", "libgfortran-3", "libopenblas", "libquadmath-0", "ml_face",
								"ml_face", "opencv_world341_x86", "opencv_world341", "opencv_java341_x86" };
						
				String sys_tmp_dir = System.getProperty("java.io.tmpdir");
//				File tmp_file = Files.createTempDirectory(null).toFile();
				File tmp_file = new File(sys_tmp_dir+"/lj");
				if(!tmp_file.exists())
					Files.createDirectory(tmp_file.toPath());
				String platfromprefix = Platform.RESOURCE_PREFIX;
				for (int i = 0; i < dlls.length; i++) {
					String dllName = dlls[i] + ".dll";
					File dll_file = new File(tmp_file, dllName);
					try (FileOutputStream fileOutputStream = new FileOutputStream(dll_file)) {
						try (InputStream inputStream = LjAippv2Core.class.getClassLoader()
								.getResourceAsStream(platfromprefix + "/" + dllName)) {
							byte[] buf = new byte[inputStream.available()];
							int count = 0;
							while ((count = inputStream.read(buf, 0, buf.length)) > 0) {
								fileOutputStream.write(buf, 0, count);
							}

						}

					}

					NativeLibrary.addSearchPath(dlls[i], tmp_file.getAbsolutePath());

				}
				System.load(tmp_file.getAbsolutePath() + "\\opencv_java341.dll");
				tmp_file.deleteOnExit();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建人脸检测句柄
	 * 
	 * @Description:
	 * @auther:JianLinwei
	 * @date:2019年7月8日下午5:19:21
	 * @param modelPath
	 *            模板文件路径
	 * @return 句柄
	 */
	public static int LjFdCreateDetector(String modelPath) {

		IntByReference lj_handel_t = new IntByReference();
		Aippv2Library.aippv2lib.lj_fd_create_detector(modelPath, lj_handel_t);
		return lj_handel_t.getValue();
	}

	/**
	 * 人脸检测
	 * 
	 * @Description:
	 * @auther:JianLinwei
	 * @date:2019年7月8日下午6:23:48
	 * @param lJhandle
	 *            检测句柄
	 * @param imageData
	 *            图像数据
	 * @return
	 */
	public static List<LjFace> LjFdDetect(int lJhandle, byte[] imageData) {

		Mat mat = new MatOfByte(imageData);
		Mat mat2 = Imgcodecs.imdecode(mat, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		imageData = new byte[(int) (mat2.total() * mat2.channels())];
		mat2.convertTo(mat2, CvType.CV_8UC3);
		mat2.get(0, 0, imageData);
		PointerByReference p_faces_arry = new PointerByReference();
		IntByReference p_faces_count = new IntByReference();
		Aippv2Library.aippv2lib.lj_fd_detect(lJhandle, LjImageType.LJ_IMAGE_BGR8UC3.getValue(), imageData, mat2.width(),
				mat2.height(), p_faces_arry, p_faces_count);
		List<LjFace> faces = new ArrayList<>();
		LjFace ljface = new LjFace();
		LjPonitf[] ljponitf = new LjPonitf[5];
		ljponitf[0] = new LjPonitf();
		ljponitf[1] = new LjPonitf();
		ljponitf[2] = new LjPonitf();
		ljponitf[3] = new LjPonitf();
		ljponitf[4] = new LjPonitf();
		for (int i = 0; i < p_faces_count.getValue(); i++) {
			int[] rect = p_faces_arry.getValue().getIntArray(0 + i * 80, 4);
			ljface.rect.left = rect[0];
			ljface.rect.top = rect[1];
			ljface.rect.right = rect[2];
			ljface.rect.bottom = rect[3];
			if((ljface.rect.right -ljface.rect.left)> mat2.width()){
				ljface.rect.right = mat2.width() - ljface.rect.left;
			}
			if((ljface.rect.right - ljface.rect.left) < 0){
				ljface.rect.right = 0;
				ljface.rect.left = 0;
			}
			if((ljface.rect.top - ljface.rect.bottom) > mat2.height()){
				ljface.rect.top = mat2.height() - ljface.rect.bottom;
			}
			if((ljface.rect.top - ljface.rect.bottom) < 0){
				ljface.rect.top = 0 ;
				ljface.rect.bottom = 0;
			}

			float[] confidence = p_faces_arry.getValue().getFloatArray(16 + i * 80, 1);
			ljface.confidence = confidence[0];
			float[] landmarks = p_faces_arry.getValue().getFloatArray(20 + i * 80, 10);
			ljponitf[0].x = landmarks[0];
			ljponitf[0].y = landmarks[1];
			ljponitf[1].x = landmarks[2];
			ljponitf[1].y = landmarks[3];
			ljponitf[2].x = landmarks[4];
			ljponitf[2].y = landmarks[5];
			ljponitf[3].x = landmarks[6];
			ljponitf[3].y = landmarks[7];
			ljponitf[4].x = landmarks[8];
			ljponitf[4].y = landmarks[9];
			ljface.landmarks = ljponitf;
			int[] intarry = p_faces_arry.getValue().getIntArray(60 + i * 80, 4);
			ljface.yaw = intarry[0];
			ljface.pitch = intarry[1];
			ljface.roll = intarry[2];
			ljface.id = intarry[3];

			float[] quatity = p_faces_arry.getValue().getFloatArray(76 + i * 80, 1);
			ljface.quality = quatity[0];

			faces.add(ljface);
		}

		return faces;

	}

	/**
	 * 人脸比对句柄
	 * 
	 * @Description:
	 * @auther:JianLinwei
	 * @date:2019年7月9日上午10:45:47
	 * @param modelPth
	 *            模板
	 * @return
	 */
	public static int LjFvCreateVerifier(String modelPth) {
		IntByReference reference = new IntByReference();
		Aippv2Library.aippv2lib.lj_fv_create_verifier(modelPth, reference);
		return reference.getValue();
	}

	/**
	 * 提取人脸特征
	 * 
	 * @Description:
	 * @auther:JianLinwei
	 * @date:2019年7月9日上午10:49:41
	 * @param LjHandlePtr
	 *            人脸比对句柄
	 * @param imageDate
	 *            图像数据
	 * @param face
	 *            人脸检测的数据LjFace
	 * @return 人脸特征
	 */
	public static LjFeature LjFvExtractFeature(int LjHandlePtr, byte[] imageData, LjFace face) {
		Mat mat = new MatOfByte(imageData);
		Mat mat2 = Imgcodecs.imdecode(mat, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		imageData = new byte[(int) (mat2.total() * mat2.channels())];
		mat2.convertTo(mat2, CvType.CV_8UC3);
		mat2.get(0, 0, imageData);
		LjFeature feature = new LjFeature();
		Aippv2Library.aippv2lib.lj_fv_extract_feature(LjHandlePtr, LjImageType.LJ_IMAGE_BGR8UC3.getValue(), imageData,
				mat2.width(), mat2.height(), face, feature);
		return feature;

	}

	/**
	 * 人脸比对(比较特征值)
	 * 
	 * @Description:
	 * @auther:JianLinwei
	 * @date:2019年7月9日上午11:23:46
	 * @param feature1
	 *            特征1
	 * @param feature2
	 *            特征2
	 * @return 分值
	 */
	public static float LjFvCompareFeatures(LjFeature feature1, LjFeature feature2) {
		return Aippv2Library.aippv2lib.lj_fv_compare_features(feature1, feature2);
	}

	/**
	 * 活体检测
	 * 
	 * @Description:
	 * @auther:JianLinwei
	 * @date:2019年7月9日上午11:31:48
	 * @param lJHandle
	 *            检测句柄
	 * @param visibleImageData
	 *            可见光数据
	 * @param redImageData
	 *            红外光数据
	 * @return
	 */
	public static LjFace LjFdBiologyDetect(int lJHandle, byte[] visibleImageData, byte[] redImageData) {
		ImageData data1 = OpenCvUtils.Byte2Mat(visibleImageData);
		ImageData data2 = OpenCvUtils.Byte2Mat(redImageData);
		LjFace p_face = new LjFace();
		Aippv2Library.aippv2lib.lj_fd_biology_detect(lJHandle, LjImageType.LJ_IMAGE_BGR8UC3.getValue(),
				data1.getImageData(), data1.getImageWidth(), data1.getImageHeight(), data2.getImageData(),
				data2.getImageWidth(), data2.getImageHeight(), p_face);

		return p_face;
	}

}
