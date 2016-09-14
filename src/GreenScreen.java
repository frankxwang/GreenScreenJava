import java.awt.Color;

import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
public class GreenScreen {
	public static String path = "/Users/frank/Documents/workspace/GreenScreen/";
	private void greenScreen(String fileName, String fileName2, String out){
		try {
			BufferedImage image = ImageIO.read(new File(
			        path + fileName));
			BufferedImage image2 = ImageIO.read(new File(
			        path + fileName2));
			int row = 0;
			Color compare = new Color(image.getRGB(0, 0));
			for(int i=1; i<(image.getHeight()-1)*image.getWidth()-8; i+=8){
				for(int j=0; j<8; j++){
					int x = (i+j) % image.getWidth();
					if(x == 0 && row < image.getHeight()-1){
						x = 0;
						row++;
					}else if(row >= image.getHeight()-1){
						System.out.println("Too long");
						throw new Exception("Too long");
					}
					Color rgb1 = new Color(image.getRGB(x, row));
					int rgb2 = image2.getRGB(x, row);
					if(colorWithin(rgb1, compare, 30)){
						System.out.println("hi");
						image.setRGB(x, row, rgb2);
					}
				}
			}
			ImageIO.write(image, "png", new File(
			        path + out));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean colorWithin(Color c1, Color c2, int i){
		if(Math.abs(c1.getBlue() - c2.getBlue()) <= i 
				&& Math.abs(c1.getGreen() - c2.getGreen()) <= i
				&& Math.abs(c1.getRed() - c2.getRed()) <= i){
			return true;
		}
		return false;
	}
	public static void main(String args[]){
		GreenScreen gs = new GreenScreen();
		gs.greenScreen("fore.png", "back.png", "file.png");
	}
}
