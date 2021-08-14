package gamePack;

import java.util.ArrayList;

public class Column {//each grid square consists of a column
					//columns can have up to two blocks: a floor block and a non-floor block
	public ArrayList <Block> blocks = new ArrayList<Block>();
	public int coordLeft, coordUp;
	public boolean treeBool, smallPlant;
	public Column(int x, int y, boolean tBool, boolean pBool){
		coordLeft = x * 40;
		coordUp = y * 40;
		//boolean for if a tree is in the column
		treeBool = tBool;
		//boolean for if a non-floor is in the column 
		//(but the non-floor is unable to block the player)
		smallPlant = pBool;
	}
	public void addBlock (Block block) {
		if(blocks.size() < 2) {
			blocks.add(0, block);
			return;}
	}
	public void removeBlock () {
		if(blocks.size() == 2) {
			blocks.remove(0);}
		smallPlant = false;
	}
	public void replaceFloor(Block block) {//replaces floor block
		if(blocks.size() == 1) {
			replaceBlock(block);
			return;}
	}
	public void replaceBlock(Block block) {
		blocks.remove(0);
		blocks.add(0, block);
	}
	public void treeSet(boolean aBool) {
		treeBool = aBool;
	}
	public boolean treeGet() {
		return treeBool;
	}
	public void smallSet(boolean pBool) {
		smallPlant = pBool;
	}
	public boolean smallGet() {
		return smallPlant;
	}
	public Block blockGet(int lvl) {
		Block block = blocks.get(lvl);
		return block;
	}
	public int xGet() {
		return coordLeft;
	}
	public int yGet() {
		return coordUp;
	}
	public int sizeGet() {
		return blocks.size();
	}
}