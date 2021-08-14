package gamePack;

import java.util.ArrayList;

public class Chunk {
	public ArrayList <Column [][]> array = new ArrayList<Column [][]>();
	public Chunk(Column [][] top, Column [][] bot) {
		array.add(top);
		array.add(bot);
	}
	//public getLvl() {
		
	//}
}
