/**
 * 
 */
package util.picture;

/**
 * @ClassName Exif
 * @Description 
 * @Author Cheng Lizhen
 * @Date 2019年3月19日 下午12:00:50
 */

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
 
/**
 * 测试用于读取图片的EXIF信息
 * @author Winter Lau
 */
public class ExifTester {
	/*public static void main(String[] args) throws ImageProcessingException,IOException{
        File jpegFile = new File("E:/hand.jpg");
        Metadata metadata = ImageMetadataReader.readMetadata(jpegFile);
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                //格式化输出[directory.getName()] - tag.getTagName() = tag.getDescription()
                System.out.format("[%s] - %s = %s\n",
                        directory.getName(), tag.getTagName(), tag.getDescription());
            }
            if (directory.hasErrors()) {
                for (String error : directory.getErrors()) {
                    System.err.format("ERROR: %s", error);
                }
            }
        }
    }*/
	
	
	public static Map<String,Object> getExif(String fileName){
        Map<String,Object> map = new HashMap<String,Object>();
        File file = new File(fileName);
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            map = printExif(file,metadata);
        } catch (ImageProcessingException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return map;
    }
    //获取exif信息，将旋转角度信息拿到
    private static Map<String,Object> printExif(File file,Metadata metadata){
        Map<String,Object> map = new HashMap<String,Object>();
        String tagName = null;
        String desc = null;
        for(Directory directory : metadata.getDirectories()){
            for(Tag tag : directory.getTags()){
                tagName = tag.getTagName();
                desc = tag.getDescription();
                System.out.println(tagName+":"+desc);
                if(tagName.equals("Orientation")){
                    map.put("Orientation", desc);
                }
            }
        }   
        return map;
    }

    public static int getAngle(Map<String,Object> map){
        String ori = map.get("Orientation").toString();
        int ro = 0;
        if(ori.indexOf("90")>=0){
            ro=1;
        }else if(ori.indexOf("180")>=0){
            ro=2;
        }else if(ori.indexOf("270")>=0){
            ro=3;
        }
        return ro;
    }
	
    public static void main(String[] args) {
    	System.out.println(getExif("E:\\1.jpg"));
	}
}

