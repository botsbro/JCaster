/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thomasbotsford3draycastingengine;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author tom
 */
public class Texture extends Window{
    
    public static BufferedImage slice1;
    public static BufferedImage slice2;
    public static BufferedImage slice3;
    public static BufferedImage slice4;
    public static BufferedImage slice5;
    public static BufferedImage slice6;
    public static BufferedImage slice7;
    public static BufferedImage slice8;
    public static BufferedImage slice9;
    public static BufferedImage slice10;
    public static BufferedImage slice11;
    public static BufferedImage slice12;
    public static BufferedImage slice13;
    public static BufferedImage slice14;
    public static BufferedImage slice15;
    public static BufferedImage slice16;
    
    public static void createSliceTextures(BufferedImage texture){
//        slice1 = texture.getSubimage(0, 0, 2, 32);
//        slice2 = texture.getSubimage(2, 0, 2, 32);
//        slice3 = texture.getSubimage(4, 0, 2, 32);
//        slice4 = texture.getSubimage(6, 0, 2, 32);
//        slice5 = texture.getSubimage(8, 0, 2, 32);
//        slice6 = texture.getSubimage(10, 0, 2, 32);
//        slice7 = texture.getSubimage(12, 0, 2, 32);
//        slice8 = texture.getSubimage(14, 0, 2, 32);
//        slice9 = texture.getSubimage(16, 0, 2, 32);
//        slice10 = texture.getSubimage(18, 0, 2, 32);
//        slice11 = texture.getSubimage(20, 0, 2, 32);
//        slice12 = texture.getSubimage(22, 0, 2, 32);
//        slice13 = texture.getSubimage(24, 0, 2, 32);
//        slice14 = texture.getSubimage(26, 0, 2, 32);
//        slice15 = texture.getSubimage(28, 0, 2, 32);
//        slice16 = texture.getSubimage(30, 0, 2, 32);
        slice1 = texture.getSubimage(0, 0, texture.getWidth()/16, texture.getHeight());
        slice2 = texture.getSubimage(texture.getWidth()/16, 0, texture.getWidth()/16, texture.getHeight());
        slice3 = texture.getSubimage((texture.getWidth()/16*2), 0, texture.getWidth()/16, texture.getHeight());
        slice4 = texture.getSubimage((texture.getWidth()/16*3), 0, texture.getWidth()/16, texture.getHeight());
        slice5 = texture.getSubimage((texture.getWidth()/16*4), 0, texture.getWidth()/16, texture.getHeight());
        slice6 = texture.getSubimage((texture.getWidth()/16*5), 0, texture.getWidth()/16, texture.getHeight());
        slice7 = texture.getSubimage((texture.getWidth()/16*6), 0, texture.getWidth()/16, texture.getHeight());
        slice8 = texture.getSubimage((texture.getWidth()/16*7), 0, texture.getWidth()/16, texture.getHeight());
        slice9 = texture.getSubimage((texture.getWidth()/16*8), 0, texture.getWidth()/16, texture.getHeight());
        slice10 = texture.getSubimage((texture.getWidth()/16*9), 0, texture.getWidth()/16, texture.getHeight());
        slice11 = texture.getSubimage((texture.getWidth()/16*10), 0, texture.getWidth()/16, texture.getHeight());
        slice12 = texture.getSubimage((texture.getWidth()/16*11), 0, texture.getWidth()/16, texture.getHeight());
        slice13 = texture.getSubimage((texture.getWidth()/16*12), 0, texture.getWidth()/16, texture.getHeight());
        slice14 = texture.getSubimage((texture.getWidth()/16*13), 0, texture.getWidth()/16, texture.getHeight());
        slice15 = texture.getSubimage((texture.getWidth()/16*14), 0, texture.getWidth()/16, texture.getHeight());
        slice16 = texture.getSubimage((texture.getWidth()/16*15), 0, texture.getWidth()/16, texture.getHeight());
        
    }
    
    
}
