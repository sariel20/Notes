package com.sariel.sort;

public class ֱ�Ӳ������� {

	public static void main(String[] args) {
		int a[] = { 20, 1, 15, 60, 43, 98, 64 };
		System.out.println("����ǰ��");
		for (int i = 0; i < a.length; i++) {
			System.out.println("   " + a[i]);
		}

		// ���鳤��
		int length = a.length;
		// Ҫ�������
		int insertNum;
		// �������
		for (int i = 1; i < length; i++) {
			insertNum = a[i];

			// �Ѿ����кõ�����Ԫ�ظ���
			int j = i - 1;

			// ���дӺ�ǰѭ����������insertNum������ǰ��һ��
			while (j >= 0 && a[j] > insertNum) {
				// Ԫ���ƶ�һ��
				a[j + 1] = a[j];
				j--;
			}

			// ����Ҫ�����������Ҫ�����λ��
			a[j + 1] = insertNum;
		}
		System.out.println("�����");
		for (int i = 0; i < a.length; i++) {
			System.out.println("   " + a[i]);
		}
	}
}
