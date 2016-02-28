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


	/*
	 * Checks if the spot to the left is clear.
	 */
	//	public boolean CheckPush(){
	//		for(Join x : game.newGroups){
	//			for(Object y : x.joinedjelly){
	//				if()
	//			}
	//		}
	//	}
	
	public boolean CheckingPushleft(Join x){
		if(x.MoveLeft()){
			return true;
		}
		return false;
	}
	//never reached
//	public boolean CheckingLeft(Join i){
//		for(Join x : game.newGroups){
//			for(Object y : x.joinedjelly){
//				if(i == x){
//					break;
//				}
//				if(xPos-1==y.xPos &&
//						yPos==y.yPos){
//					if(CheckingPushleft(x)){
//						return true;
//					}else{
//						return false;
//					}
//				}
//			}
//
//		}
//		if(xPos-1 >= 0 &&
//				game.level[yPos][xPos-1] !=1){
//			return true;
//		}
//
//
//
//		//makes this jelly move to the right
//		return false;
//	}
	public String CheckingMove(Join p, String direction){
		int checkNum = 0;
		if(direction.equals("right")){
			checkNum=1;
		}
		if(direction.equals("left")){
			checkNum=-1;
		}
		for(Join x : game.newGroups){
			for(Object y : x.joinedjelly){
				if(p == x){
					break;
				}
				if(yPos==y.yPos){
					if(xPos+checkNum==y.xPos){
						if(direction.equals("right")){
							if(CheckingPushright(x)){
								return "right";
							}else{
								return "";
							}
						}
						if(direction.equals("left")){
							if(CheckingPushleft(x)){
								return "left";
							}else{
								return "";
							}
						}
					}
				}
			}
		}
		if(direction.equals("right") && xPos+1 < game.Levels[game.levelnumber][yPos].length &&
				game.level[yPos][xPos+1] !=1){
			return "right";
		}
		if(direction.equals("left")&&xPos-1 >= 0 &&
				game.level[yPos][xPos-1] !=1){
			return "left";
		}

		//makes this jelly move to the right
		return "";
	}		
	public boolean CheckingPushright(Join x){
		if(x.MoveRight()){
			return true;
		}
		return false;
	}
	public void moveleft(){

		xPos--;
		System.out.print(xPos);
	}
	/*
	 * The reason this does nothing is because it would cause a loop otherwise.
	 */
	//	public void Right(){	
	//		Confirmright();
	//	}
	/*
	 * makes this jelly move to the right
	 */


	public void moveright(){

		xPos++;
		System.out.print(xPos);
	}

}
