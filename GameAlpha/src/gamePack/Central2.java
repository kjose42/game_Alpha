package gamePack;

import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.Timer;
import java.lang.Math;
import java.util.ArrayList;

public class Central2 extends javax.swing.JApplet 
	implements ActionListener, MouseListener, MouseMotionListener, KeyListener{
	private int xChunk = 0, yChunk = 0, xOccupy = 5, yOccupy = 5, wCount = 0, aCount = 0, sCount = 39, dCount = 39, walkSpeed = 2, xHole = -1, yHole = -1, barInt = 0,
			moveTimer = 0;
	private Image titleV, playerV, hotBarV, descendV, ascendV, selectorV, backScreen, waterV, pigV;
	private boolean start = true, wPress = false, aPress = false, sPress = false, dPress = false, pause = false, loadReady = false, inWater = false, underground = false;
	private Block grass, water, log, planks, tree, dandelion, poppy, sand, stone, bedrock, 
		brownMushroom, redMushroom, coalOre, ironOre, goldOre, gravel, lava, light, hole, pig, empty,
		inHand;
	public Block [] hotBar = new Block [10];
	public Column [] occupyCheck = new Column [25];
	public Column [][] lvl = new Column[14][14];
	public Column [][] otherLvl = new Column [14][14];
	public ArrayList<AI> AIs = new ArrayList<AI>();
	public void init() { //initialize timers, listeners, blocks, and create lvl
		this.setSize(new java.awt.Dimension(600, 600));
		this.addMouseMotionListener(this);
		addMouseMotionListener(this);
		this.addMouseListener(this);
		this.addKeyListener(this);
		Timer time = new Timer(1, this);
		time.start();
		//block initialize starts here. can i make this more efficient??
		titleV = getImage(getDocumentBase(), "menu.png");
		playerV = getImage(getDocumentBase(), "playVisual.png");
		hotBarV = getImage(getDocumentBase(), "hotBarVisual.png");
		selectorV = getImage(getDocumentBase(), "selectorVisual.png");
		descendV = getImage(getDocumentBase(), "descendVisual.png");
		ascendV = getImage(getDocumentBase(), "ascendVisual.png");
		Image grassV = getImage(getDocumentBase(), "grassVisual.png");
		grass = new Block (grassV, 20, null, false, false);
		waterV = getImage(getDocumentBase(), "waterVisual.png");
		water = new Block (waterV, 20, null, false, false);
		Image lavaV = getImage(getDocumentBase(), "lavaVisual.png");
		lava = new Block (lavaV, 20, null, false, false);
		Image logV = getImage(getDocumentBase(), "logVisual.png");
		log = new Block (logV, 20, null, false, false);
		Image planksV = getImage(getDocumentBase(), "planksVisual.png");
		planks = new Block(planksV, 20, null, false, false);
		Image treeV = getImage(getDocumentBase(), "saplingVisual.png");
		tree = new Block (treeV, 20, null, false, false);
		Image dandelionV = getImage(getDocumentBase(), "dandelionVisual.png");
		dandelion = new Block (dandelionV, 20, null, false, false);
		Image poppyV = getImage(getDocumentBase(), "poppyVisual.png");
		poppy = new Block (poppyV, 20, null, false, false);
		Image sandV = getImage(getDocumentBase(), "sandVisual.png");
		sand = new Block (sandV, 20, null, false, false);
		Image stoneV = getImage(getDocumentBase(), "stoneVisual.png");
		stone = new Block(stoneV, 20, null, false, false);
		Image bedrockV = getImage(getDocumentBase(), "bedrockVisual.png");
		bedrock = new Block(bedrockV, 20, null, false, false);
		Image BMushroomV = getImage(getDocumentBase(), "brownMushroomVisual.png");
		brownMushroom = new Block(BMushroomV, 20, null, false, false);
		Image RMushroomV = getImage(getDocumentBase(), "redMushroomVisual.png");
		redMushroom = new Block(RMushroomV, 20, null, false, false);
		Image COreV = getImage(getDocumentBase(), "coalOreVisual.png");
		coalOre = new Block(COreV, 20, null, false, false);
		Image IOreV = getImage(getDocumentBase(), "ironOreVisual.png");
		ironOre = new Block(IOreV, 20, null, false, false);
		Image GOreV = getImage(getDocumentBase(), "goldOreVisual.png");
		goldOre = new Block(GOreV, 20, null, false, false);
		Image gravelV = getImage(getDocumentBase(), "gravelVisual.png");
		gravel = new Block(gravelV, 20, null, false, false);
		pigV = getImage(getDocumentBase(), "piggyVisual.png");
		//pig = new Block(pigV, 20, null, false, false);
		Image emptyV = getImage(getDocumentBase(), "emptyVisual.png");
		empty = new Block(emptyV, 20, null, false, false);
		Image holeV = emptyV;
		hole = new Block(emptyV, 20, null, false, false);
		Image lightV = getImage(getDocumentBase(), "lightVisual.png");
		light = new Block(lightV, 20, null, false, false);
		hotBar [0] = log;
		hotBar [1] = planks;
		hotBar [2] = tree;
		hotBar [3] = stone;
		hotBar [4] = gravel;
		hotBar [5] = coalOre;
		hotBar [6] = ironOre;
		hotBar [7] = goldOre;
		hotBar [8] = empty;
		hotBar [9] = empty;
		inHand = log;
		backScreen = this.createImage(600, 600);
		createLvl();
	}
	public void paint(Graphics g) {
		//two screens: backGraph and g
		//1. draw everything on backGraph
		//2. copy of backGraph pasted on g
		Graphics backGraph = backScreen.getGraphics();
		if(start == true){
			backGraph.drawImage(titleV, 0, 0, 600, 600, this);}
		else {	backGraph.drawImage(waterV, 0, 0, 600, 600, this);
				for(int inc1 = 0; inc1 < lvl.length; inc1++) {
					for(int inc2 = 0; inc2 < lvl[0].length; inc2++) {
						Column col = lvl[inc1][inc2];
						if(col.xGet() > -32 && col.yGet() > -32) {
							if(col.sizeGet() > 1) {
								Image blockPic = col.blockGet(1).getPic();
								backGraph.drawImage(blockPic, xChunk + col.xGet(), yChunk + col.yGet(), 40, 40, this);}
							Image blockPic = col.blockGet(0).getPic();
							backGraph.drawImage(blockPic, xChunk + col.xGet(), yChunk + col.yGet(), 40, 40, this);}}}
				/*if(underground == false) {
					for(int inc = 0; inc < AIs.size(); inc++) {
						AI tempAI = AIs.get(inc);
						backGraph.drawImage(tempAI.picGet(), xChunk + tempAI.xGet(), yChunk + tempAI.yGet(), 40, 40, this);}}*/
				backGraph.drawImage(playerV, 280, 280, 40, 40, this);
				if(inHand != empty) {
					backGraph.drawImage(inHand.getPic(), 282, 308, 10, 10, this);}
				backGraph.drawImage(hotBarV, 98, 556, 404, 44, this);
				backGraph.drawImage(selectorV, 100+(barInt*40), 552, 40, 8, this);
				for(int inc = 0; inc < 10; inc++) {
					Block element = hotBar[inc];
					backGraph.drawImage(element.getPic(), 102+(inc*40), 560, 36, 36, this);}}
		g.drawImage(backScreen, 0, 0, 600, 600, this);
		this.requestFocus();
	}
	public void occupyUpd8() {//player's top-left corner (of hitbox) has moved to a different grid square
		int inc = 0;
		for(int x = xOccupy; x < xOccupy+5; x++) {
			for(int y = yOccupy; y < yOccupy+5; y++) {
				occupyCheck[inc] = lvl[x][y];
				inc++;}}
	}
	public void liquidGen(int x, int y, int count, int max) {//generate bodies of water above ground
		if(count >= max) {
			int randomInt2 = (int)(Math.random()*8);
			if(randomInt2 == 4 && lvl[x][y].blockGet(0) == grass) {
				lvl[x][y].replaceFloor(sand);}
			return;}
		//randomly generating water above, below, left, right of the original x & y
		if(x - 1 >= 2) {
			if((x - 1) != 7 && y != 7) {
				int randomInt2 = (int)(Math.random()*8);
				if(randomInt2 >= 0 && randomInt2 <= 3) {
					if(lvl[x - 1][y].sizeGet() < 2 && lvl[x - 1][y].blockGet(0) == grass) {
						lvl[x - 1][y].replaceFloor(water);
						liquidGen(x - 1, y, count + 1, max);}}
				else{if(randomInt2 == 4 && lvl[x - 1][y].blockGet(0) == grass) {
						lvl[x - 1][y].replaceFloor(sand);}}}}
		if(y - 1 >= 2) {
			if(x != 7 && (y - 1) != 7) {
				int randomInt2 = (int)(Math.random()*8);
				if(randomInt2 >= 0 && randomInt2 <= 3) {
					if(lvl[x][y - 1].sizeGet() < 2 && lvl[x][y - 1].blockGet(0) == grass) {
						lvl[x][y - 1].replaceFloor(water);
						liquidGen(x, y - 1, count + 1, max);}}
				else{if(randomInt2 == 4 && lvl[x][y - 1].blockGet(0) == grass) {
						lvl[x][y - 1].replaceFloor(sand);}}}}
		if(x + 1 < 12) {
			if((x + 1) != 7 && y != 7) {
				int randomInt2 = (int) (Math.random()*8);
				if(randomInt2 >= 0 && randomInt2 <= 3) {
					if(lvl[x + 1][y].sizeGet() < 2 && lvl[x + 1][y].blockGet(0) == grass) {
						lvl[x + 1][y].replaceFloor(water);
						liquidGen(x + 1, y, count + 1, max);}}
				else{if(randomInt2 == 4 && lvl[x + 1][y].blockGet(0) == grass) {
						lvl[x + 1][y].replaceFloor(sand);}}}}
		if(y + 1 < 12) {
			if(x != 7 && (y + 1) != 7) {
				int randomInt2 = (int)(Math.random()*8);
				if(randomInt2 >= 0 && randomInt2 <= 3) {
					if(lvl[x][y + 1].sizeGet() < 2 && lvl[x][y + 1].blockGet(0) == grass) {
						lvl[x][y + 1].replaceFloor(water);
						liquidGen(x, y + 1, count + 1, max);}}
				else{if(randomInt2 == 4 && lvl[x][y + 1].blockGet(0) == grass) {
						lvl[x][y + 1].replaceFloor(sand);}}}}
	}
	public void uLiquidGen(int x, int y, int count, int max, Block liquid) {//generating bodies of water underground
		if(count >= max) {
			return;}
		//randomly generating water above, below, left, right of the original x & y
		if(x - 1 >= 2) {
			if((x - 1) != 7 && y != 7) {
				int randomInt2 = (int)(Math.random()*8);
				if(randomInt2 >= 0 && randomInt2 <= 3) {
					if(otherLvl[x - 1][y].sizeGet() < 2) {
						otherLvl[x - 1][y].replaceFloor(liquid);
						uLiquidGen(x - 1, y, count + 1, max, liquid);}}}}
		if(y - 1 >= 2) {
			if(x != 7 && (y - 1) != 7) {
				int randomInt2 = (int)(Math.random()*8);
				if(randomInt2 >= 0 && randomInt2 <= 3) {
					if(otherLvl[x][y - 1].sizeGet() < 2) {
						otherLvl[x][y - 1].replaceFloor(liquid);
						uLiquidGen(x, y - 1, count + 1, max, liquid);}}}}
		if(x + 1 < 12) {
			if((x + 1) != 7 && y != 7) {
				int randomInt2 = (int) (Math.random()*8);
				if(randomInt2 >= 0 && randomInt2 <= 3) {
					if(otherLvl[x + 1][y].sizeGet() < 2) {
						otherLvl[x + 1][y].replaceFloor(liquid);
						uLiquidGen(x + 1, y, count + 1, max, liquid);}}}}
		if(y + 1 < 12) {
			if(x != 7 && (y + 1) != 7) {
				int randomInt2 = (int)(Math.random()*8);
				if(randomInt2 >= 0 && randomInt2 <= 3) {
					if(otherLvl[x][y + 1].sizeGet() < 2) {
						otherLvl[x][y + 1].replaceFloor(liquid);
						uLiquidGen(x, y + 1, count + 1, max, liquid);}}}}
	}
	public void stoneGen(int x, int y, int count) {//generating cave systems
		if(count >= 5) {
			return;}
		//randomly removing stone blocks above, below, left, and right of the original x & y
		if(x - 1 >= 2) {
			if((x - 1) != 7 || y != 7) {
				int randomInt2 = (int)(Math.random()*2);
				if(randomInt2 == 0) {
						otherLvl[x - 1][y].removeBlock();
						stoneGen(x - 1, y, count + 1);}}}
		if(y - 1 >= 2) {
			if((y - 1) != 7 || y != 7) {
				int randomInt2 = (int)(Math.random()*2);
				if(randomInt2 == 0) {
						otherLvl[x][y - 1].removeBlock();
						stoneGen(x, y - 1, count + 1);}}}
		if(x + 1 < 12) {
			if((x + 1) != 7 || y != 7) {
				int randomInt2 = (int)(Math.random()*2);
				if(randomInt2 == 0) {
						otherLvl[x + 1][y].removeBlock();
						stoneGen(x + 1, y, count + 1);}}}
		if(y + 1 < 12) {
			if(x != 7 || (y + 1) != 7) {
				int randomInt2 = (int)(Math.random()*2);
				if(randomInt2 == 0) {
						otherLvl[x][y + 1].removeBlock();
						stoneGen(x, y + 1, count + 1);}}}
	}
	public void createLvl() {//loading lvl
		//lvl generation (without any random elements)
		//for above ground: grass for floor and stone for world border
		//for underground: bedrock for floor and log for world border
		for(int x = 0; x < lvl.length; x++) {
			for(int y = 0; y < lvl[0].length; y++) {
				Column col = new Column(x, y, true, false);
				col.addBlock(grass);
				if(x == 0 || x == 1 || x == 12 || x == 13) {
					col.addBlock(stone);}
				else{if(y == 0 || y == 1 || y == 12 || y == 13) {
						col.addBlock(stone);}}
				lvl[x][y] = col;}}
		for(int x = 0; x < otherLvl.length; x++) {
			for(int y = 0; y < otherLvl[0].length; y++) {
				Column col = new Column(x, y, true, false);
				col.addBlock(bedrock);
				col.addBlock(stone);
				if(x == 0 || x == 1 || x == 12 || x == 13) {
					col.removeBlock();
					col.addBlock(log);}
				else{if(y == 0 || y == 1 || y == 12 || y == 13) {
						col.removeBlock();
						col.addBlock(log);}}
				otherLvl[x][y] = col;}}
		//lvl generation (with random elements)
		for(int x = 2; x < 12; x++) {
			for(int y = 2; y < 12; y++) {
				int randomInt = (int)(Math.random()*3200);
				if(randomInt >= 0 && randomInt <= 155) {
					otherLvl[x][y].removeBlock();
					stoneGen(x, y, 0);}
				if(otherLvl[x][y].sizeGet() == 1) {
					if(randomInt >= 582 && randomInt <= 600) {
						otherLvl[x][y].replaceFloor(water);
						uLiquidGen(x, y, 0, 3, water);}
					if(randomInt >= 607 && randomInt <= 645) {
						otherLvl[x][y].replaceFloor(lava);
						uLiquidGen(x, y, 0, 4, lava);}}
				if(otherLvl[x][y].sizeGet() == 2) {
					if(randomInt >= 183 && randomInt <= 441) {
						otherLvl[x][y].replaceBlock(gravel);}
					if(randomInt >= 470 && randomInt <= 566) {
						otherLvl[x][y].replaceBlock(coalOre);}
					if(randomInt >= 567 && randomInt <= 579) {
						otherLvl[x][y].replaceBlock(ironOre);}
					if(randomInt == 580 || randomInt == 581) {
						otherLvl[x][y].replaceBlock(goldOre);}}
			}
		}
		for(int x = 2; x < 12; x++) {
			for(int y = 2; y < 12; y++) {
				if(x != 7 && y != 7) {
					if(lvl[x][y].blockGet(0) == grass) {
						int randomInt = (int)(Math.random()*200);
						if(randomInt >= 0 && randomInt <= 27) {
							if(lvl[x][y].treeGet() == true) {
								lvl[x][y].addBlock(tree);
								if(x + 1 < 10) {
									lvl[x + 1][y].treeSet(false);}
								if(y + 1 < 10) {
									lvl[x][y + 1].treeSet(false);}}}
						if(randomInt >= 28 && randomInt <= 34) {
							lvl[x][y].addBlock(dandelion);
							lvl[x][y].smallSet(true);}
						if(randomInt >= 35 && randomInt <= 41) {
							lvl[x][y].addBlock(poppy);
							lvl[x][y].smallSet(true);}
						if(randomInt >= 42 && randomInt <= 49) {
							lvl[x][y].addBlock(brownMushroom);
							lvl[x][y].smallSet(true);}
						if(randomInt >= 50 && randomInt <= 57) {
							lvl[x][y].addBlock(redMushroom);
							lvl[x][y].smallSet(true);}
						if(randomInt == 58) {
							lvl[x][y].replaceFloor(water);
							liquidGen(x, y, 0, 5);}
						if(randomInt == 59 || randomInt == 60) {
							lvl[x][y].addBlock(hole);
							otherLvl[x][y].removeBlock();
							otherLvl[x][y].addBlock(light);}
						if(randomInt >= 61 && randomInt <= 63) {
							createAI(x, y);}
					}
				}
			}
		}
		loadReady = true;
		occupyUpd8();
	}
	public void createAI(int x, int y) {//AI creation (will continue later)
		AI newAI = new AI(x * 40, y * 40, 0, pigV, null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null);
		AIs.add(newAI);
	}
	public void posUpd8(int xC, int yC, int wC, int aC, int sC, int dC) {//player position update
		xChunk = xChunk + xC;
		yChunk = yChunk + yC;
		wCount = wCount + wC;
		aCount = aCount + aC;
		sCount = sCount + sC;
		dCount = dCount + dC;
	}
	public boolean holeFound(Column col) {//asking player whether to go underground
		Block checkBlock = col.blockGet(0);
		if(checkBlock == hole || checkBlock == light) {
			pause = true;
			wPress = false;
			aPress = false;
			sPress = false;
			dPress = false;
			xHole = col.xGet()/40;
			yHole = col.yGet()/40;
			Block temp = new Block(descendV, 20, null, false, false);
			if(checkBlock == light) {
				temp = new Block (ascendV, 20, null, false, false);}
			lvl[xHole][yHole].replaceBlock(temp);
			repaint();
			return true;}
		return false;
	}
	//public void moveAI() {
	//}
	public void actionPerformed(ActionEvent arg0) {//method only controls player movement for now
		if(loadReady == true) {
			//moveAI();
			boolean waterCheck = false;
			for(int inc = 0; inc < occupyCheck.length; inc++) {//checks if player is in water
				int xL = occupyCheck[inc].xGet() + xChunk;
				int yU = occupyCheck[inc].yGet() + yChunk;
				if(yU <= 318 && yU >= 280) {
					if(xL >= 241 && xL <= 318) {
						if(occupyCheck[inc].blockGet(0) == water || occupyCheck[inc].blockGet(0) == lava) {
							waterCheck = true;
							break;}}}}
			if(waterCheck == true) {//slows down player if in water
				inWater = true;
				if(moveTimer == 0) {
					moveTimer = 1;}
				else{moveTimer = 0;}}
			else {inWater = false;}
			/*if((wCount != 0 && lvl[xOccupy+2][yOccupy+3].getBlock(0) == water) || (wCount != 0 && lvl[xOccupy+3][yOccupy+3].getBlock(0) == water) || (wCount == 0 && lvl[xOccupy+2][yOccupy+2].getBlock(0) == water)) {
				System.out.println("***yOccupy: " + yOccupy);
				inWater = true;}
			else{inWater = false;}*/
			
			//code handles player running into objects and running into lvl transition blocks (hole & light)
			if(inWater == false || moveTimer == 0) {
				if(wPress == true && sPress == false) {
					boolean blocked = false;
					for(int inc = 0; inc < occupyCheck.length; inc++) {
						int xL = occupyCheck[inc].xGet() + xChunk;
						int xR = xL + 39;
						int yU = occupyCheck[inc].yGet() + yChunk;
						int yD = yU + 39;
						if(xR <= 358 && xL >= 241) {
							if(yD == 279) {
								if(occupyCheck[inc].sizeGet() > 1 && occupyCheck[inc].smallGet() == false) {
									blocked = true;
									sCount = 39;
									if(holeFound(occupyCheck[inc]) == true) {
										break;}}}}}
					if(blocked == false) {
						if(wCount - walkSpeed < 0) {
							//System.out.println("wMOVE");
							yOccupy = yOccupy - 1;
							occupyUpd8();
							wCount = 40 + wCount;
							sCount = wCount;}
						posUpd8(0, walkSpeed, -1 * walkSpeed, 0, -1 * walkSpeed, 0);}
					repaint();
				}
			if(aPress == true && dPress == false) {
				boolean blocked = false;
				for(int inc = 0; inc < occupyCheck.length; inc++) {
					int xL = occupyCheck[inc].xGet() + xChunk;
					int xR = xL + 39;
					int yU = occupyCheck[inc].yGet() + yChunk;
					int yD = yU + 39;
					if(yD <= 358 && yU >= 241) {
						if(xR == 279) {
							if(occupyCheck[inc].sizeGet() > 1 && occupyCheck[inc].smallGet() == false) {
								blocked = true;
								dCount = 39;
								if(holeFound(occupyCheck[inc]) == true) {
									break;}}}}}
				if(blocked == false) {
					if(aCount - walkSpeed < 0) {
						//System.out.println("aMOVE");
						xOccupy = xOccupy - 1;
						occupyUpd8();
						aCount = aCount + 40;
						dCount = aCount;}
					posUpd8(walkSpeed, 0, 0, -1 * walkSpeed, 0, -1 * walkSpeed);}
				repaint();
			}
			if(sPress == true && wPress == false) {
				boolean blocked = false;
				for(int inc = 0; inc < occupyCheck.length; inc++) {
					int xL = occupyCheck[inc].xGet() + xChunk;
					int xR = xL + 39;
					int yU = occupyCheck[inc].yGet() + yChunk;
					if(xR <= 358 && xL >= 241) {
						if(yU == 320) {
							if(occupyCheck[inc].sizeGet() > 1 && occupyCheck[inc].smallGet() == false) {
								blocked = true;
								wCount = 0;
								if(holeFound(occupyCheck[inc]) == true) {
									break;}}}}}
				if(blocked == false) {
					if(sCount + walkSpeed > 39) {
						//System.out.println("sMOVE");
						yOccupy = yOccupy + 1;
						occupyUpd8();
						sCount = sCount - 40;
						wCount = sCount;}
					posUpd8(0, -1 * walkSpeed, walkSpeed, 0, walkSpeed, 0);}
				repaint();
			}
			if(dPress == true && aPress == false) {
				boolean blocked = false;
				for(int inc = 0; inc < occupyCheck.length; inc++) {
					int xL = occupyCheck[inc].xGet() + xChunk;
					int yU = occupyCheck[inc].yGet() + yChunk;
					int yD = yU + 39;
					if(yD <= 358 && yU >= 241) {
						if(xL == 320) {
							if(occupyCheck[inc].sizeGet() > 1 && occupyCheck[inc].smallGet() == false) {
								blocked = true;
								aCount = 0;
								if(holeFound(occupyCheck[inc]) == true) {
									break;}}}}}
				if(blocked == false) {
					if(dCount + walkSpeed > 39) {
						//System.out.println("dMOVE");
						xOccupy = xOccupy + 1;
						occupyUpd8();
						dCount = dCount - 40;
						aCount = dCount;}
					posUpd8(-1 * walkSpeed, 0, 0, walkSpeed, 0, walkSpeed);}
				repaint();
			}
		}
	}
	}
	public void mouseDragged(MouseEvent arg0) {
	}
	public void mouseMoved(MouseEvent e) {
	}
	public void mouseClicked(MouseEvent arg0) {
	}
	public void mouseEntered(MouseEvent arg0) {
	}
	public void mouseExited(MouseEvent arg0) {
	}
	public void mousePressed(MouseEvent click) {
		double x = click.getX();
		double y = click.getY();
		if(start == true) {//for title screen
			if(y >= 320 && y <= 365) {
				start = false;
				repaint();}
			return;}
		if(x >= 280 && x <= 319) {
			if(y >= 280 && y <= 319) {
				return;}}
		if(x >= 98 && x <= 502) {
			if(y >= 556) {
				double xBar = Math.ceil((x - 98)/40);
				barInt = (int)(xBar - 1);
				inHand = hotBar[barInt];
				repaint();
				return;}}
		double xGrid = Math.ceil((x - xChunk)/40);
		double yGrid = Math.ceil((y - yChunk)/40);
		int xInt = (int)(xGrid - 1);
		int yInt = (int)(yGrid - 1);
		if(click.getButton() == 1) {
			if(x >= xChunk + 80 && x <= xChunk + 479) {
				if(y >= yChunk + 80 && y <= yChunk + 479) {
					if(pause == false) {
						if(inHand != empty) {
							lvl[xInt][yInt].addBlock(inHand);}}
					else{//for answering lvl transition (aka ascend? descend?)
						int xOpt = (xHole * 40) + xChunk;
						int yOpt = (yHole * 40) + yChunk + 20;
						if(y >= yOpt && y <= yOpt + 19) {
							pause = false;
							if(x >= xOpt + 20 && x <= xOpt + 39) {
								if(lvl[xHole][yHole].blockGet(0).getPic() == descendV) {
									lvl[xHole][yHole].replaceBlock(hole);}
								else {lvl[xHole][yHole].replaceBlock(light);}}
							if(x >= xOpt && x <= xOpt + 19){
								if(lvl[xHole][yHole].blockGet(0).getPic() == descendV) {
									lvl[xHole][yHole].replaceBlock(hole);}
								else {lvl[xHole][yHole].replaceBlock(light);}
								Column [][] tempLvl = lvl;
								lvl = otherLvl;
								otherLvl = tempLvl;
								if(underground == false){
									underground = true;}
								else {underground = false;}
								int xDif = (xHole * 40) - (280 - xChunk);
								int yDif = (yHole * 40) - (280 - yChunk);
								xChunk = xChunk - xDif;
								yChunk = yChunk - yDif;
								xOccupy = xHole - 2;
								yOccupy = yHole - 2;
								occupyUpd8();
								wCount = 0;
								aCount = 0;
								sCount = 39;
								dCount = 39;
								repaint();}
							xHole = -1;
							yHole = -1;
						}
					}
				}
			}
		}
		if(click.getButton() == 3) {
			Block checkBlock = lvl[xInt][yInt].blockGet(0);
			if(x >= xChunk + 80 && x <= xChunk + 479) {
				if(y >= yChunk + 80 && y <= yChunk + 479) {
					if(checkBlock != hole && checkBlock != light) {
						lvl[xInt][yInt].removeBlock();}}}}
		repaint();
	}
	public void mouseReleased(MouseEvent arg0) {
	}
	public void keyPressed(KeyEvent key) {//for player movement
		if(start == false && pause == false) {
			if(key.getKeyChar() == 'w') {
				wPress = true;}
			if(key.getKeyChar() == 'a') {
				aPress = true;}
			if(key.getKeyChar() == 's') {
				sPress = true;}
			if(key.getKeyChar() == 'd') {
				dPress = true;}}
	}
	public void keyReleased(KeyEvent key) {//for player movement
		if(key.getKeyChar() == 'w') {
			wPress = false;}
		if(key.getKeyChar() == 'a') {
			aPress = false;}
		if(key.getKeyChar() == 's') {
			sPress = false;}
		if(key.getKeyChar() == 'd') {
			dPress = false;}
		repaint();
	}
	public void keyTyped(KeyEvent e) {
	}
}