import java.awt.Color; 
import java.awt.Graphics2D; 
import java.awt.image.BufferedImage; 
import java.io.File; 
 
import javax.imageio.ImageIO; 
 
import com.swetake.util.Qrcode; 
 
/**
 * 二维码生成器
 * 
 * @author Michael
 */ 
public class QRCodeEncoderHandler{ 
 
    /**
     * 生成二维码(QRCode)图片
     * @paramcontent
     * @paramimgPath
     */ 
    public static BufferedImage encoderQRCode(String content) {
       try { 
 
           Qrcode qrcodeHandler = new Qrcode();
           // 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
           qrcodeHandler.setQrcodeErrorCorrect('L'); 
           qrcodeHandler.setQrcodeEncodeMode('B');
           // 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大
           qrcodeHandler.setQrcodeVersion(15); 
 
           System.out.println(content); 
           byte[] contentBytes = content.getBytes("gb2312"); 
 
           BufferedImage bufImg = new BufferedImage(240, 240, 
                   BufferedImage.TYPE_INT_RGB); 
 
           Graphics2D gs = bufImg.createGraphics(); 
 
           gs.setBackground(Color.WHITE); 
           gs.clearRect(0, 0, 250, 250); 
 
           // 设定图像颜色> BLACK 
           gs.setColor(Color.BLACK); 
 
           // 设置偏移量 不设置可能导致解析出错 
           int pixoff = 2; 
           // 输出内容> 二维码 
           if (contentBytes.length > 0 && contentBytes.length < 420) { 
               boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes); 
               for (int i = 0; i < codeOut.length; i++) { 
                   for (int j = 0; j < codeOut.length; j++) { 
                       if (codeOut[j][i]) { 
                           gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3); 
                       } 
                   } 
               } 
           } else { 
               System.err.println("QRCode content bytes length = " 
                       +contentBytes.length + " not in [ 0,120 ]. "); 
           } 
 
           gs.dispose(); 
           bufImg.flush(); 
 
           //File imgFile = new File(imgPath);
 
           // 生成二维码QRCode图片 
           //ImageIO.write(bufImg, "png", imgFile);
           return bufImg;
 
       } catch (Exception e) { 
           e.printStackTrace(); 
       }
        return null;
    }
}  