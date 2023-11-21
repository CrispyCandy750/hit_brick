package UI;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 这个类有静态方法，用于获取图片
 */
public class ImageManager {

    /**
     * input the path of the image, then return the image
     * @param path the path of the image
     * @return
     */
    public static BufferedImage getImage(String path){
        try{
            BufferedImage image = ImageIO.read(ImageManager.class.getResource(path));
            return image;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
