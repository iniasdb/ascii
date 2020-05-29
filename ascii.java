package ascii;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

public class ascii {
	
	private static int height;
	private static int width;
	private static int red;
	private static int green;
	private static int blue;
	private static char[] chars;
	private static int count = 1;
	private static int x = 1;
	private static FileWriter fw;
	private static PrintWriter pw;

	
	public static void printPixelRGB(int pixel, int i, int j) {
		red = (pixel >> 16) & 0xff;
		green = (pixel >> 8) & 0xff;
		blue = (pixel) & 0xff;
		//System.out.println("rgb: " + red + ", " + green + ", " + blue);
		brightnessMap(red, green, blue, i, j);
	}
	
	public static void brightnessMap(int red, int green, int blue, int i, int j) {
		int[][] light = new int[width][height];
        int brightness = (red + green + blue) / 3;	
        //System.out.println(brightness);
        light[j][i] = brightness;
        brightnessToAscii(brightness);
	}
	
	public static void asciiMatrix() {
		String characters = "`^\\\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";
		chars = new char[characters.length()];
				
		for (int i = 0; i < characters.length()-1; i++) {
			//System.out.println(characters.charAt(i));
			chars[i] = characters.charAt(i);
		}
	}
	
	public static void brightnessToAscii(int brightness) {
		for (int i = 1; i < chars.length + 1; i++) {
			if (255 - (4 * i) <= brightness && brightness >= 255 - (4 * (i+1))) {
				if (count == (width) * x) {
					//System.out.println();
					//System.out.print(chars[i-1]);
					pw.println();
					pw.print(chars[i-1]);
					pw.print(chars[i-1]);
					pw.print(chars[i-1]);
					//System.out.println(count);
					x++;
					break;
				} else {
					//System.out.print(chars[i-1]);
					pw.print(chars[i-1]);
					pw.print(chars[i-1]);
					pw.print(chars[i-1]);
					//System.out.println(count);
					break;
				}
			}
		}
		count++;
	}
	

	public static void main(String[] args) throws IOException {
		File file = new File("afbeelding.jpg");
		BufferedImage bimage = ImageIO.read(file);

		ColorSpace colorSpace = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp colorConvertOp = new ColorConvertOp(colorSpace, null);
        BufferedImage image = colorConvertOp.filter(bimage, null);
        
		fw = new FileWriter(new File("output.txt"));
		pw = new PrintWriter(fw);
		asciiMatrix();
		
		height = image.getHeight(null);
		width = image.getWidth(null);
		System.out.println(width + " x " + height);
		pw.println(width);
		
		int[][] pixels = new int[width][height];


		for( int i = 0; i < width; i++ ) {
		    for( int j = 0; j < height; j++ ) {
		    	
		    	System.out.println("coord: " + i + " " + j);
		        int pixel = image.getRGB(j, i);
		        printPixelRGB(pixel, j, i);
		    	
		        pixels[j][i] = pixel;
		    }
		}
		
		fw.close();
		pw.close();
	}

}
