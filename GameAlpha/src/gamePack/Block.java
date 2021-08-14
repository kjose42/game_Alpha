package gamePack;

import java.awt.Image;

public class Block {
	public Image visual, miniBlock, breakingVisual;
	public int xcoord, ycoord, layers, breakingTime;
	public boolean handBreak, placeBool;
	public Block(Image vPic, int tInt, Image bPic, boolean hBool, boolean pBool) {
		visual = vPic;
		breakingTime = tInt;
		breakingVisual = bPic;
		handBreak = hBool;
		placeBool = pBool;
	}
	public void setPic(Image pic) {
		visual = pic;
	}
	public Image getPic() {
		return visual;
	}
}
