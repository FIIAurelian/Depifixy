package gui;

import java.util.HashMap;
import java.util.Map;

public class PixelIdGroup {
	
	private int[][] pixels;
	private Map<Integer, Rectangle> groupDimensions;
	private int lastGroupId = 0;
	
	public PixelIdGroup(int height, int width) {
		pixels = new int[width][height];
		groupDimensions = new HashMap<>();
		addRectangle(new Rectangle(0, 0, width-1, height-1));
	}
	
	public int getPixelId(int height, int width) {
		return pixels[height][width];
	}
	
	public Rectangle getRectangle(int pixelId) {
		return groupDimensions.get(pixelId);
	}
	
	public void divide(int pixelId) {
		Rectangle r = groupDimensions.get(pixelId);
		if(r == null)
			return;
		
		int midleX = r.up + (r.down - r.up) / 2;
		int midleY = r.left + (r.right - r.left) / 2;
		addRectangle(new Rectangle(r.up, r.left, midleX, midleY));
		addRectangle(new Rectangle(r.up, midleY+1, midleX, r.right));
		addRectangle(new Rectangle(midleX+1, r.left, r.down, midleY));
		addRectangle(new Rectangle(midleX+1, midleY+1, r.down, r.right));
	}
	
	private void addRectangle(Rectangle r) {
		lastGroupId = lastGroupId + 1;
		groupDimensions.put(lastGroupId, r);
		for(int i = r.up; i <= r.down; i++)
			for(int j = r.left; j <= r.right; j++)
				pixels[i][j] = lastGroupId;
	}

}
