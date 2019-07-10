package com.ljzn.aippv2.entity;

public enum LjImageType {
	LJ_IMAGE_BGR8UC3(0), /// < BGR 8:8:8 24bpp ( 3通道24bit BGR 像素 )
	LJ_IMAGE_BGRA8UC4(1), /// < BGRA 8:8:8:8 32bpp ( 4通道32bit BGRA 像素 )
	LJ_IMAGE_GRAY8UC1(2); /// < Y 1 8bpp ( 单通道8bit灰度像素 )
	int value;

	LjImageType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
