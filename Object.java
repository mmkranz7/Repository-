import java.awt.Color;


public class Object {
	GameController game;
	int xPos;
	int yPos;
	Color c;
	int height;
	int width;
	Join Myjoin;
	//if this jelly is on top of another
	public Object(GameController game){
		this(game, 1, 1, Color.BLACK, 1,1,null);
	}
	public Object(GameController game,
			int xPos,
			int yPos,
			Color c,
			int height,
			int width,
			Join Myjoin){

		this.xPos = xPos;
		this.yPos = yPos;
		this.c = c;
		this.game= game;
		this.height=height;
		this.width=width;
		this.Myjoin=Myjoin;
	}//the constructor for the jellys

	public void move(String dir){
		xPos=(dir=="r" ? (xPos+1) : (xPos-1));
		System.out.print(xPos);
	}

}
