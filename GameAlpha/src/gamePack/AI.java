package gamePack;

import java.awt.Image;

public class AI {
	public int xCoord, yCoord, direction;
	private Image pW, mW, pA, mA, pS, mS, pD, mD, hW, hitMW, hA, hitMA, hS, hitMS, hD, hitMD, pic;
	public AI(int x, int y, int d, Image inPW, Image inMW, Image inPA, Image inMA, Image inPS, 
			Image inMS, Image inPD, Image inMD, Image inHW, Image inHitMW, Image inHA, Image inHitMA,
			Image inHS, Image inHitMS, Image inHD, Image inHitMD) {
			xCoord = x;
			yCoord = y;
			direction = d;
			pW = inPW;
			mW = inMW;
			pA = inPA;
			mA = inMA;
			pS = inPS;
			mS = inMS;
			pD = inPD;
			mD = inMD;
			hW = inHW;
			hitMW = inHitMW;
			hA = inHA;
			hitMA = inHitMA;
			hS = inHS;
			hitMS = inHitMS;
			hD = inHD;
			hitMD = inHitMD;
			pic = pW;
	}
	public void xSet(int x) {
		xCoord = x;
	}
	public void ySet(int y) {
		yCoord = y;
	}
	public int xGet() {
		return xCoord;
	}
	public int yGet() {
		return yCoord;
	}
	public Image picGet() {
		return pic;
	}
}
