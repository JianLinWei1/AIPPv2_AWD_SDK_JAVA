package com.ljzn.aippv2.entity;

import java.io.Serializable;

public class ImageData implements Serializable{
    private  byte[]   imageData;
    
    private  int  imageWidth;
    
    private  int imageHeight;
    
	
	private static final long serialVersionUID = 1L;


	public byte[] getImageData() {
		return imageData;
	}


	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}


	public int getImageWidth() {
		return imageWidth;
	}


	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}


	public int getImageHeight() {
		return imageHeight;
	}


	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}
	
	
	
}
