package zxn.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class SureImage {
	int width = 120;
	int height = 25;
	// ����һ���ڴ�ͼ��BufferedImage
	BufferedImage image = new BufferedImage(width, height,
			BufferedImage.TYPE_INT_RGB);
	public BufferedImage getImage() {
		return image;
	}

	// �õ����ڸ�ͼƬ�Ļ��ʣ�Graphics
	Graphics g = image.getGraphics();

	// ���߿�
	public String  creatImage() {
		g.setColor(Color.BLUE);
		g.drawRect(0, 0, width, height);
		// ��䱳��ɫ
		g.setColor(Color.YELLOW);
		g.fillRect(1, 1, width - 2, height - 2);
		// ��������
		g.setColor(Color.GRAY);

		Random r = new Random();
		for (int i = 0; i < 10; i++)
			g.drawLine(r.nextInt(width), r.nextInt(height), r.nextInt(width),
					r.nextInt(height));

		// �������
		g.setColor(Color.RED);
		g.setFont(new Font("����", Font.BOLD | Font.ITALIC, 20));
		int x = 23;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			String code = r.nextInt(10) + "";
			sb.append(code);
			g.drawString(code, x, 20);
			x += 20;
		}
		return sb.toString();
		
	}

}
