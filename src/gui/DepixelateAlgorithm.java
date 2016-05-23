package gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DepixelateAlgorithm {

	private BufferedImage image;
	private PartialSumMatrix redValues;
	private PartialSumMatrix greenValues;
	private PartialSumMatrix blueValues;
	private PixelIdGroup pixelIdGroup;
	
	private DepixelateAlgorithm() {}
	
	public Image getImage() {
		return image;
	}
	
	public void clickOnPixel(int x, int y) {
		int pixelId = pixelIdGroup.getPixelId(x, y);
		Rectangle r = pixelIdGroup.getRectangle(pixelId);
		
		int midleX = r.up + (r.down - r.up) / 2;
		int midleY = r.left + (r.right - r.left) / 2;
		redraw(r.up, r.left, midleX, midleY);
		redraw(r.up, midleY+1, midleX, r.right);
		redraw(midleX+1, r.left, r.down, midleY);
		redraw(midleX+1, midleY+1, r.down, r.right);
		
		pixelIdGroup.divide(pixelId);
	}
	
	private void redraw(int up, int left, int down, int right) {
		System.out.println(up + " " + left + " " + down + " " + right);
		int totalPixels = (down-up+1) * (right-left+1);
		int red = (int)(redValues.getSum(up, left, down, right) / (1.0*totalPixels));
		int green = (int)(greenValues.getSum(up, left, down, right) / (1.0*totalPixels));
		int blue = (int)(blueValues.getSum(up, left, down, right) / (1.0*totalPixels));
		System.out.println(red + " " + green + " " + blue);
		Color c = new Color(red, green, blue);
		for(int i = up; i <= down; i++)
			for(int j = left; j <= right; j++)
				image.setRGB(i, j, c.getRGB());
	}
	
	public static DepixelateAlgorithm loadForImageFile(File imageFile) {
		DepixelateAlgorithm algorithm = new DepixelateAlgorithm();
		try {
			algorithm.image = ImageIO.read(imageFile);
			initializePartialSumMatrices(algorithm);
			initializeImagePixel(algorithm);
		} catch(IOException ioException) {
			ioException.printStackTrace();
		}
		
		return algorithm;
	}
	
	public static void initializePartialSumMatrices(DepixelateAlgorithm algorithm) {
		if(algorithm.image == null)
			return;
		
		int[][] redValues = new int[algorithm.image.getWidth()][algorithm.image.getHeight()];
		int[][] greenValues = new int[algorithm.image.getWidth()][algorithm.image.getHeight()];
		int[][] blueValues = new int[algorithm.image.getWidth()][algorithm.image.getHeight()];
		for(int i = 0; i < algorithm.image.getWidth(); i++) {
			for(int j = 0; j < algorithm.image.getHeight(); j++) {
				Color c = new Color(algorithm.image.getRGB(i, j));
				redValues[i][j] = c.getRed();
				greenValues[i][j] = c.getGreen();
				blueValues[i][j] = c.getBlue();
			}
		}
		algorithm.redValues = PartialSumMatrix.generateForArray(redValues);
		algorithm.greenValues = PartialSumMatrix.generateForArray(greenValues);
		algorithm.blueValues = PartialSumMatrix.generateForArray(blueValues);
	}
	
	public static void initializeImagePixel(DepixelateAlgorithm algorithm) {
		if(algorithm.image == null)
			return;
		
		algorithm.pixelIdGroup = new PixelIdGroup(algorithm.image.getHeight(), algorithm.image.getWidth());
		int width = algorithm.image.getWidth();
		int height = algorithm.image.getHeight();
		int totalPixels = width * height;
		int red = (int)(algorithm.redValues.getSum(0, 0, width-1, height-1) / (1.0*totalPixels));
		int green = (int)(algorithm.greenValues.getSum(0, 0, width-1, height-1) / (1.0*totalPixels));
		int blue = (int)(algorithm.blueValues.getSum(0, 0, width-1, height-1) / (1.0*totalPixels));
		Color c = new Color(red, green, blue);
		for(int i = 0; i < algorithm.image.getWidth(); i++) {
			for(int j = 0; j < algorithm.image.getHeight(); j++) {
				algorithm.image.setRGB(i, j, c.getRGB());
			}
		}
	}
	
}
