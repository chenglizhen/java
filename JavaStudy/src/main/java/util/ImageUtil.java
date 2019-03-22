/**
 * 
 */
package util;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 图片处理工具类
 * 
 * @author feiq
 */
public class ImageUtil {

	private static Logger LOGGER = Logger.getLogger(ImageUtil.class);

	private static BASE64Encoder encoder = new BASE64Encoder();
	private static BASE64Decoder decoder = new BASE64Decoder();

	/**
	 * 压缩图片长宽比
	 * 
	 * @param img
	 *            <图片对象>
	 * @return 压缩后的图片
	 */
	public static BufferedImage compressImage(BufferedImage img) {

		int width = img.getWidth();
		if (width <= 640) {
			return img;
		}
		int height = img.getHeight();

		double rate = 640.0 / (width);
		width = (int) (width * rate);
		height = (int) (height * rate);
		Image image = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = img.getGraphics();
		graphics.drawImage(image, 0, 0, null);
		graphics.dispose();

		return img;

	}

	/**
	 * 将本地图片文件进行base64加密
	 * @param localPath 本地图片文件地址
	 * @param format 照片的格式，如jpg，png等格式
	 * @return base64编码后图片对应的字符集
	 */
	public static String ImageToBase64(String localPath, String format) {
		String result = null;
		File file = new File(localPath);
		if (!file.exists()) {
			return result;
		}
		BufferedImage bi = null;
		ByteArrayOutputStream baos = null;
		try {
			bi = ImageIO.read(file);
			baos = new ByteArrayOutputStream();
			// 默认将图片转换为jpg格式 
			ImageIO.write(bi, format == null ? "jpg" : format, baos);
			byte[] bytes = baos.toByteArray();
			result = encoder.encodeBuffer(bytes).trim();
		} catch (IOException e) {
			return null;
		} finally {
			try {
				if (baos != null) {
					baos.close();
					baos = null;
				}
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * 返回图片地址
	 * 
	 * @param imgStr
	 * @return
	 */
	public static String Base64toImage(String imgStr, String savePath) {
		if (imgStr == null) {
			return "";
		}
		try {
			// 解码
			byte[] b = decoder.decodeBuffer(imgStr);
			// 处理数据
			for (int i = 0; i < b.length; i++) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-MM-SS");
			String filename = sdf.format(new Date());
			File saveFile = new File(savePath);
			if (!saveFile.exists()) {
				saveFile.mkdirs();
			}
			String imgpath = filename + ".png";
			File flie = new File(imgpath);
			if (flie.exists()) {
				flie.createNewFile();
			}
			OutputStream out = new FileOutputStream(savePath + imgpath);
			out.write(b);
			out.flush();
			out.close();
			return savePath + imgpath;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 旋转图片为指定角度
	 *
	 * @param file
	 *            目标图像
	 * @param angel
	 *            旋转角度
	 * @return
	 */
	public static File rotateImage(File file, final int angel) {
		BufferedImage src = null;
		try {
			src = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int src_width = src.getWidth(null);
		int src_height = src.getHeight(null);

		LOGGER.info("width:" + src_width + "," + "height:" + src_height);
		Rectangle rect_des = calcRotatedSize(new Rectangle(new Dimension(src_width, src_height)), angel);

		BufferedImage bufferedImage = new BufferedImage(rect_des.width, rect_des.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bufferedImage.createGraphics();

		g2.translate((rect_des.width - src_width) / 2, (rect_des.height - src_height) / 2);
		g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);

		g2.drawImage(src, null, null);
		g2.dispose();

		try {
			ImageIO.write(bufferedImage, "jpg", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 调用方法输出图片文件
		// outImage(file.getPath(), bi, (float) 0.5);
		return file;
	}

	/**
	 * 计算旋转参数
	 */
	public static Rectangle calcRotatedSize(Rectangle src, int angel) {
		// if angel is greater than 90 degree,we need to do some conversion.
		if (angel > 90) {
			if (angel / 9 % 2 == 1) {
				int temp = src.height;
				src.height = src.width;
				src.width = temp;
			}
			angel = angel % 90;
		}

		double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
		double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
		double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
		double angel_dalta_width = Math.atan((double) src.height / src.width);
		double angel_dalta_height = Math.atan((double) src.width / src.height);

		int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_width));
		int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_height));
		int des_width = src.width + len_dalta_width * 2;
		int des_height = src.height + len_dalta_height * 2;
		return new java.awt.Rectangle(new Dimension(des_width, des_height));
	}

	/**
	 * 
	 * @param file
	 *            先压缩再旋转图片
	 * @param savePath
	 *            保存路径
	 * @param angel
	 *            旋转角度
	 * @return
	 */
	public static File GeometricCompression(File file, String savePath, final int angel) {

		long before0 = System.currentTimeMillis();
		BufferedImage srcbufImgData = null;
		try {
			srcbufImgData = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		long after0 = System.currentTimeMillis();
		LOGGER.info("上传图片所需时间：" + (after0 - before0) / 1000.0 + "");

		int src_width = srcbufImgData.getWidth(null);
		int src_height = srcbufImgData.getHeight(null);
		LOGGER.info("w1:" + src_width + ";h1:" + src_height);

		long before = System.currentTimeMillis();
		srcbufImgData = ImageUtil.compressImage(srcbufImgData); // 压缩图片
		long after = System.currentTimeMillis();
		LOGGER.info("压缩所需时间：" + (after - before) / 1000.0 + "");

		src_width = srcbufImgData.getWidth(null);
		src_height = srcbufImgData.getHeight(null);
		LOGGER.info("w2:" + src_width + ";h2:" + src_height);

		long before1 = System.currentTimeMillis();
		Rectangle rect_des = calcRotatedSize(new Rectangle(new Dimension(src_width, src_height)), angel);

		BufferedImage bufferedImage = new BufferedImage(rect_des.width, rect_des.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bufferedImage.createGraphics();

		g2.translate((rect_des.width - src_width) / 2, (rect_des.height - src_height) / 2);
		g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);

		g2.drawImage(srcbufImgData, null, null);
		g2.dispose();
		long after1 = System.currentTimeMillis();
		LOGGER.info("旋转所需时间：" + (after1 - before1) / 1000.0 + "");

		long before2 = System.currentTimeMillis();

		try {
			ImageIO.write(bufferedImage, "jpg", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		long after2 = System.currentTimeMillis();
		LOGGER.info("写出图片所需时间：" + (after2 - before2) / 1000.0 + "");

		// 重构上传图片的名称
		// SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		// String newImgName = df.format(new Date()) + "_" + Math.abs(new
		// Random().nextLong()) + "." + fileExt;
		// File temp = new File(savePath + newImgName);
		// try {
		// ImageIO.write(srcbufImgData, "jpg", file);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// file.delete();
		return file;
	}
}
