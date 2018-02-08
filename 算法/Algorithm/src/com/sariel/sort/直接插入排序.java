package com.sariel.sort;

public class 直接插入排序 {

	public static void main(String[] args) {
		int a[] = { 20, 1, 15, 60, 43, 98, 64 };
		System.out.println("排序前：");
		for (int i = 0; i < a.length; i++) {
			System.out.println("   " + a[i]);
		}

		// 数组长度
		int length = a.length;
		// 要插入的数
		int insertNum;
		// 插入次数
		for (int i = 1; i < length; i++) {
			insertNum = a[i];

			// 已经排列好的序列元素个数
			int j = i - 1;

			// 序列从后到前循环，将大于insertNum的数向前移一格
			while (j >= 0 && a[j] > insertNum) {
				// 元素移动一格
				a[j + 1] = a[j];
				j--;
			}

			// 将需要插入的数放在要插入的位置
			a[j + 1] = insertNum;
		}
		System.out.println("排序后：");
		for (int i = 0; i < a.length; i++) {
			System.out.println("   " + a[i]);
		}
	}
}
