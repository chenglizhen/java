package util.qrcode;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * @ClassName ImageAndQRcode
 * @Description 1.生成带logo的二维码 2.获取源图片中的二维码坐标 3.将新生成的二维码覆盖源图片中的二维码
 * @Author Cheng Lizhen
 * @Date 2019年3月15日 上午11:37:26
 */
public class ImageAndQRcode {

	private static final int QRCOLOR = 0xFF000000; // 默认是黑色
	private static final int BGWHITE = 0xFFFFFFFF; // 背景颜色

	private static final int WIDTH = 385; // 二维码宽
	private static final int HEIGHT = 385; // 二维码高

	private static final int MAGINX = 350; // 二维码距离底图左上角原点，x轴距离
	private static final int MAGINY = 815; // 二维码距离底图左上角原点，y轴距离

	// 用于设置QR二维码参数
	private static HashMap<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>() {
		private static final long serialVersionUID = 1L;
		{
			// 设置QR二维码的纠错级别为H（最高）
			// 有4种纠错级别：L = ~7% correction、 M = ~15% 、Q = ~25%、H = ~30%
			put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			put(EncodeHintType.CHARACTER_SET, "UTF-8");// 设置编码方式
			put(EncodeHintType.MARGIN, 1);// margin值不是图片的边框,而是将矩阵放大整数倍之后的留白区域。
		}
	};

	/**
	 * 生成带logo的二维码图片
	 * 
	 * @param logoFile
	 *            logo图片地址
	 * @param codeFile
	 *            二维码图片地址
	 * @param qrUrl
	 *            编码内容
	 */
	public static void drawLogoQRCode(File logoFile, File codeFile, String qrUrl) {
		try {
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			// 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
			BitMatrix bm = multiFormatWriter.encode(qrUrl, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
			BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

			// 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
			for (int x = 0; x < WIDTH; x++) {
				for (int y = 0; y < HEIGHT; y++) {
					image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
				}
			}
			int width = image.getWidth();
	        int height = image.getHeight();
			if (Objects.nonNull(logoFile) && logoFile.exists()) {
				// 构建绘图对象
				Graphics2D g = image.createGraphics();
				// 读取Logo图片
				BufferedImage logo = ImageIO.read(logoFile);
				g.drawImage(logo, width * 2 / 5, height * 2 / 5, width * 7 / 25, height * 7 / 25, null);
				g.dispose(); // 处理此图形上下文并释放它正在使用的系统资源。
				logo.flush(); // 刷新图片
			}
			image.flush();
			ImageIO.write(image, "png", codeFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 将新生成的二维码覆盖源图中的二维码
	 * @param filePath 原图片路径
	 * @param newPath  二维码图片路径
	 */
	public void deEncodeByPath(String filePath, String newPath, String saveFilePath) {

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
			generateWaterFile(readImage, saveFilePath);
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
	 * 向背景图片中插入二维码
	 * @param bgFile
	 *            背景图片
	 * @param QrCodeFile
	 *            二维码图片
	 * @param x
	 *            距离右下角的x偏移量   
	 * @param y
	 *            距离右下角的y偏移量   
	 * @param alpha
	 *            透明度, 选择值从0.0~1.0: 完全透明~完全不透明   
	 * @return
	 * @throws IOException
	 */
	private static BufferedImage watermark(File bgFile, File QrCodeFile, int x, int y, float alpha) throws IOException {
		// 获取底图
		BufferedImage buffImg = ImageIO.read(bgFile);
		// 获取层图
		BufferedImage QrCodeImg = ImageIO.read(QrCodeFile);
		// 创建Graphics2D对象，用在底图对象上绘图
		Graphics2D g2d = buffImg.createGraphics();

		int QrCodeImgWidth = QrCodeImg.getWidth();// 获取层图的宽度
		int QrCodeImgHeight = QrCodeImg.getHeight();// 获取层图的高度

		// 在图形和图像中实现混合和透明效果
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
		// 绘制
		g2d.drawImage(QrCodeImg, x, y, QrCodeImgWidth, QrCodeImgHeight, null);
		g2d.dispose();// 释放图形上下文使用的系统资源

		return buffImg;
	}

	/**
	 * @param buffImg
	 *            图像加水印之后的BufferedImage对象
	 * @param saveFilePath
	 *            图像加水印之后的保存路径     
	 */
	private void generateWaterFile(BufferedImage buffImg, String saveFilePath) {
		int temp = saveFilePath.lastIndexOf(".") + 1;
		try {
			ImageIO.write(buffImg, saveFilePath.substring(temp), new File(saveFilePath));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * @param qrUrl
	 *            二维码内容
	 * @param bgFilePath
	 *            背景图片地址
	 * @param QrCodeFilePath
	 *            二维码图片地址
	 * @param logoFilePath
	 *            logo图片地址
	 * @param saveFilePath
	 *            存储路径
	 * @throws Exception
	 */
	public void addImageQRcode(String qrUrl, String bgFilePath, String QrCodeFilePath, String logoFilePath,
			String saveFilePath) throws Exception {
		// 生成包含logo的二维码
		File logoFile = new File(logoFilePath);
		File QrCodeFile = new File(QrCodeFilePath);
		drawLogoQRCode(logoFile, QrCodeFile, qrUrl);

//		deEncodeByPath(bgFilePath, QrCodeFilePath, saveFilePath);		
		try {
			ImageAndQRcode newImageUtils = new ImageAndQRcode();
			// 构建叠加层
			BufferedImage buffImg = ImageAndQRcode.watermark(new File(bgFilePath), QrCodeFile, MAGINX, MAGINY, 1.0f);
			// 输出水印图片
			newImageUtils.generateWaterFile(buffImg, saveFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		ImageAndQRcode add = new ImageAndQRcode();
		try {
			add.addImageQRcode("http://www.baidu.com", "E:/bg.png", "E:/code.png", "E:/logo.jpg", "E:/invite.png");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
