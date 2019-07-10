package com.ljzn.aippv2.entity;

public enum LjImageType {
	LJ_IMAGE_BGR8UC3(0), /// < BGR 8:8:8 24bpp ( 3ͨ��24bit BGR ���� )
	LJ_IMAGE_BGRA8UC4(1), /// < BGRA 8:8:8:8 32bpp ( 4ͨ��32bit BGRA ���� )
	LJ_IMAGE_GRAY8UC1(2); /// < Y 1 8bpp ( ��ͨ��8bit�Ҷ����� )
	int value;

	LjImageType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
