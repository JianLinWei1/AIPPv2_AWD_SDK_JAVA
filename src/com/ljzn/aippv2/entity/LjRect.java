package com.ljzn.aippv2.entity;


import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


import com.sun.jna.Structure;
/**
 * 
 * @author JianLinWei
 * @date: 2019年7月8日下午2:45:02
 */
public class LjRect extends Structure  implements Serializable{
	/**
	 * 矩形左边的坐标
	 */
 public 	int left;	
 /**
  * 矩形顶边的坐标
  */
public 	int top;	

/**
 * 矩形右边的坐标
 */
public	int right;	
/**
 * 矩形底边的坐标
 */
public	int bottom;	


@SuppressWarnings("serial")
public static class ByRefence  extends LjRect implements Structure.ByReference{}
@SuppressWarnings("serial")
public  static class ByValue  extends  LjRect  implements  Structure.ByValue{}

private static final long serialVersionUID = 1L;

@Override
protected List<String> getFieldOrder() {
	
	return Arrays.asList("left","top","right","bottom");
}
}
