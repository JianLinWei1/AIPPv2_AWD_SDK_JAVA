package com.ljzn.aippv2.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class LjFace extends Structure implements Serializable{
    
	/**
	 * 人脸区域
	 */
	public LjRect rect; 
	/**
	 * 置信度
	 */
	public float confidence; 
	
	/**
	 * 关键点
	 */
	public LjPonitf[] landmarks  = new LjPonitf[5] ; 
	/**
	 *  水平转角的度数。正值代表脸向右看。
	 */
	public int yaw; 
	/**
	  * 俯仰角的度数。正值代表脸向上看。											
	 */
	public int pitch; 
	/**
	 * 旋转角的度数。正值代表脸向右肩倾斜。
	 */
	public int roll; 
	/**
	 * 每帧图像中的人脸ID(实际开发中无作用)
	 */
	public int id; 
	/**
	 * 是否闭眼  1-闭眼，0-睁眼
	 */
	public  int  close_eyes;
	/**
	 * 是否张嘴：1-张嘴，0-正常
	 */
	public int open_mouth;
	 /**
	  * 是否戴墨镜：1-戴墨镜，0-未戴
	  */
	public int sunglasses;
	/**
	 * 是否戴口罩：1-戴口罩，0-未戴
	 */
	public int mask;
	
	/**
	 * 人脸质量。本版本不提供。
	 */
	public float quality; 
	/**
	 * 保留参数
	 */
	public int  bio ;
	
	
	@SuppressWarnings("serial")
	public static class ByRefence  extends LjFace implements Structure.ByReference{}
	@SuppressWarnings("serial")
	public  static class ByValue  extends  LjFace  implements  Structure.ByValue{}
	

	private static final long serialVersionUID = 1L;

	@Override
	protected List<String> getFieldOrder() {

		return Arrays.asList("rect", "confidence", "landmarks", "yaw", "pitch", "roll", "id", "quality","bio","mask","sunglasses" ,"open_mouth" ,"close_eyes");
	}

}
