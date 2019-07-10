package com.ljzn.aippv2.util;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import com.ljzn.aippv2.entity.ImageData;

/**
 * 
 * @author JianLinWei
 * @date: 2019年7月9日上午11:38:48
 */
public class OpenCvUtils {
	
	/**
	 * 处理图像数据
	 * @Description:
	 * @auther:JianLinwei
	 * @date:2019年7月9日上午11:38:54
	 * @param bs
	 * @return
	 */
	public static  ImageData  Byte2Mat(byte[] bs){
		ImageData data = new ImageData();
		Mat  mat  = new MatOfByte(bs);
		Mat  mat2 = Imgcodecs.imdecode(mat, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		bs  = new byte[(int)(mat.total() * mat.channels())];
		mat2.convertTo(mat2, CvType.CV_8UC3);
		mat2.get(0, 0, bs);
		
		data.setImageData(bs);
		data.setImageWidth(mat2.width());
		data.setImageHeight(mat2.height());
		return data;
	}

}
