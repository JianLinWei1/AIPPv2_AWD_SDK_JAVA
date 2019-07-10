package com.ljzn.aippv2.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;
/**
 * 
 * @author JianLinWei
 * @date: 2019年7月8日下午2:49:15
 */
public class LjFeature  extends Structure implements Serializable{
	/**
	 * 特征长度
	 */
 public	int len;
 /**
  * 特征数据
  */
public	float[] data = new float[512];
@SuppressWarnings("serial")
public static class ByRefence  extends LjFeature implements Structure.ByReference{}
@SuppressWarnings("serial")
public  static class ByValue  extends  LjFeature  implements  Structure.ByValue{}


private static final long serialVersionUID = 1L;

@Override
protected List<String> getFieldOrder() {
	
	return Arrays.asList("len","data");
}
	

}
