package com.ljzn.aippv2.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

/**
 * 
 * @author JianLinWei
 * @date: 2019年7月8日下午2:47:50
 */
public class LjPonitf extends Structure  implements Serializable{
	/**
	 * 点的x坐标
	 */
	public float x; 
	/**
	 * 点的y坐标
	 */
	public float y; 
	
	@SuppressWarnings("serial")
	public static class ByRefence  extends LjPonitf implements Structure.ByReference{}
	@SuppressWarnings("serial")
	public  static class ByValue  extends  LjPonitf  implements  Structure.ByValue{}
	
	private static final long serialVersionUID = 1L;
	@Override
	protected List<String> getFieldOrder() {
		return Arrays.asList("x","y");
	}

}
