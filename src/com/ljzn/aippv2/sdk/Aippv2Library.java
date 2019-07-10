package com.ljzn.aippv2.sdk;

import com.ljzn.aippv2.entity.LjFace;
import com.ljzn.aippv2.entity.LjFeature;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

/**
 * 
 * @author JianLinWei
 * @date: 2019年7月8日下午2:01:30
 */
public interface Aippv2Library  extends Library{
	public static final String LIB_NAME = "ml_face";
	public static  final  Aippv2Library  aippv2lib = Native.loadLibrary(Aippv2Library.LIB_NAME, Aippv2Library.class);
	
	
	/**
	 * 创建人脸检测句柄
	 * @Description:
	 * @auther:JianLinwei
	 * @date:下午1:59:52
	 * @param model_path 模板库路径
	 * @param lj_handel_t  检测句柄
	 * @return
	 */
	int  lj_fd_create_detector(String  model_path , IntByReference  lj_handel_t);
	
	
	/**
	 * 销毁人脸检测句柄
	 * @Description:
	 * @auther:JianLinwei
	 * @date:2019年7月8日下午2:03:54
	 * @param lj_handel_t
	 * @return
	 */
	void lj_fd_destroy_detector(int  lj_handel_t);
	
	
	/**
	 * 检测人脸
	 * @Description:
	 * @auther:JianLinwei
	 * @date:2019年7月8日下午2:08:45
	 * @param lj_handel_t  检测句柄
	 * @param image_type  图像类型
	 * @param image_data  图像数据
	 * @param image_width  宽
	 * @param image_height  高
	 * @param p_faces_arry  人脸数据数组
	 * @param p_faces_count  人脸数量
	 * @return
	 */
	int  lj_fd_detect(int lj_handel_t , int image_type ,  byte[]  image_data ,  int  image_width , int image_height , 
			PointerByReference p_faces_arry , IntByReference p_faces_count);
	
	
	/**
	 * 设置人脸检测参数
	 * @Description:
	 * @auther:JianLinwei
	 * @date:2019年7月8日下午2:12:34
	 * @param lj_handel_t 人脸检测句柄
	 * @param minSize  人脸最低大小(值越低速度越慢)
	 * @param threshold_p 网络神经p值
	 * @param threshold_r 网络神经r值
	 * @param threshold_o  网络神经o值
	 * 说明：不调用该函数时,内部默认使用lj_fd_detect_set_parameters(detector_handle,100,0.9f,0.9f,0.95f);
	 * 	建议: 验证身份证时参数为lj_fd_detect_set_parameters(detector_handle,60,0.6f,0.7f,0.8f);(可避免身份证照片因为像素太低检测不到人脸的情况)
	 * 其他时候使用默认参数即可
	 */
	void  lj_fd_detect_set_parameters(int  lj_handel_t , int minSize  ,  float threshold_p  , float threshold_r , float threshold_o);
	
	/**
	 * 释放人脸检测返回结果时分配的内存
	 * @Description:
	 * @auther:JianLinwei
	 * @date:2019年7月8日下午2:21:28
	 * @param faces_array 检测到的人脸信息数组
	 */
	void  lj_fd_release_detect_result(LjFace.ByRefence  faces_array);
	
	/**
	 * 创建人脸比对句柄
	 * @Description:
	 * @auther:JianLinwei
	 * @date:2019年7月8日下午2:23:52
	 * @param model_path  模板路径
	 * @param pfr_handle 返回句柄
	 * @return
	 */
	int  lj_fv_create_verifier(String   model_path , IntByReference pfr_handle);
	
	
	/**
	 * 销毁人脸比对句柄
	 * @Description:
	 * @auther:JianLinwei
	 * @date:2019年7月8日下午2:25:17
	 * @param pfr_handle
	 */
	void  lj_fv_destroy_verifier(int pfr_handle);
	
	/**
	 * 提取人脸特征
	 * @Description:
	 * @auther:JianLinwei
	 * @date:2019年7月8日下午2:30:41
	 * @param pfr_handle 人脸比对句柄
	 * @param image_type 用于提取特征的图像数据的格式
	 * @param image_data 用于提取特征的图像数据
	 * @param images_width  用于提取特征的图像的宽度 以像素为单位
	 * @param image_height 用于提取特征的图像的高度 以像素为单位
	 * @param face  人脸的landmarks及矩形区域。相关信息可以通过人脸检测得到。
	 * @param feature  人脸特征
	 * @return
	 */
	int  lj_fv_extract_feature(int  pfr_handle , int  image_type , byte[]   image_data , int images_width ,int image_height ,
			LjFace  face , LjFeature feature);
	
	
	/**
	 * 比较特征值
	 * @Description:
	 * @auther:JianLinwei
	 * @date:2019年7月8日下午2:33:09
	 * @param feature1
	 * @param feature2
	 * @return
	 */
	float  lj_fv_compare_features(LjFeature feature1  , LjFeature feature2);
	
	
	/**
	 * 活体检测
	 * @Description:
	 * @auther:JianLinwei
	 * @date:2019年7月8日下午2:36:47
	 * @param lj_handle_t  
	 * @param image_type
	 * @param visible_image_data
	 * @param visible_image_width
	 * @param visible_image_height
	 * @param red_image_data
	 * @param red_image_width
	 * @param red_image_height
	 * @param p_face
	 * @return
	 */
	int   lj_fd_biology_detect(int  lj_handle_t ,  int  image_type , byte[]  visible_image_data , int  visible_image_width ,int visible_image_height
			                  ,byte[]  red_image_data,  int red_image_width , int red_image_height , LjFace p_face
			                   );
	
}
