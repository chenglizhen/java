package util.qrcode;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * @ClassName QRCodeTools
 * @Description 定位获取图片中的二维码坐标，并替换原来的二维码
 * @Author Cheng Lizhen
 * @Date 2019年3月18日 下午6:00:42
 */
public class QRCodeTools {

	/**
	 * @param filePath 原图片路径
	 * @param newPath  二维码图片路径
	 */
	public static void deEncodeByPath(String filePath, String newPath) {

		// 原图里面二维码的url
		String originalURL = null;
		try {
			// 将远程文件转换为流
			BufferedImage readImage = ImageIO.read(new File(filePath));
			LuminanceSource source = new BufferedImageLuminanceSource(readImage);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

			Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
			Result result = null;
			result = new MultiFormatReader().decode(binaryBitmap, hints);
			originalURL = result.getText();

			// 解码
			ResultPoint[] resultPoint = result.getResultPoints();
			System.out.println("原二维码里面的url:" + originalURL + ",\npoints1： " + resultPoint[0] + ",\npoints2： "
					+ resultPoint[1] + ",\npoints3： " + resultPoint[2] + ",\npoints4： " + resultPoint[3]);

			// 获得二维码坐标
			float point1X = resultPoint[0].getX();
			float point1Y = resultPoint[0].getY();
			float point2X = resultPoint[1].getX();
			float point2Y = resultPoint[1].getY();

			// 替换二维码的图片文件路径，二维码需要带白边，以便在  以模糊计算的偏移量绘制图片时，能遮住原图的二维码。
			BufferedImage writeFile = ImageIO.read(new File(newPath));

			// 二维码左边两个小方块中心点的距离
			final int twoPoints = (int) Math.sqrt(Math.abs(point1X - point2X) * Math.abs(point1X - point2X)
					+ Math.abs(point1Y - point2Y) * Math.abs(point1Y - point2Y));
			// 模糊估计，将二维码的长宽分成4份，小方块占1份*2，剩余长度占2份，二维码长宽则为方块中心点距离的4/3 
			final int w = twoPoints * 4 / 3;
			final int h = w;

			Hashtable<EncodeHintType, Object> hints2 = new Hashtable<EncodeHintType, Object>();
			hints2.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			hints2.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			hints2.put(EncodeHintType.MARGIN, 0);

			Graphics2D graphics = readImage.createGraphics();
			// 二维码定位的小方块从左到右  黑白黑白黑的比例为：1：1：3：1：1，绘制二维码的坐标为：左上角中心点 的 x、y坐标减少1/2个小方块的长度
			// twoPoints/3 得到小方块的长度，再除以2得到偏移量
			int x = Math.round(point2X) - twoPoints / (3 * 2);
			int y = Math.round(point2Y) - twoPoints / (3 * 2);

			// 开始合并绘制图片
			graphics.drawImage(writeFile, x, y, w, h, null);
			// logo边框大小
			graphics.setStroke(new BasicStroke(2));
			// //logo边框颜色
			graphics.setColor(Color.WHITE);
			graphics.drawRect(x, y, w, h);
			readImage.flush();
			graphics.dispose();

			// 打印替换后的图片
			generateWaterFile(readImage, "E:\\save.png");
		}

		catch (IOException e) {
			System.out.println("资源读取失败" + e.getMessage());
			e.printStackTrace();
		} catch (NotFoundException e) {
			System.out.println("读取图片二维码坐标前发生异常:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @param buffImg
	 *            图像加水印之后的BufferedImage对象
	 * @param savePath
	 *            图像加水印之后的保存路径     
	 */
	private static void generateWaterFile(BufferedImage buffImg, String savePath) {
		int temp = savePath.lastIndexOf(".") + 1;
		try {
			ImageIO.write(buffImg, savePath.substring(temp), new File(savePath));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {
		deEncodeByPath("E:\\zfb.png", "E:\\code.png");
	}
}
