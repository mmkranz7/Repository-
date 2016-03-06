import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.Spring;


public class Join {
	int Checkr = 0;
	int Checkl = 0;
	ArrayList<Object> joinedjelly = new ArrayList<Object>();
	GameController game;
	Object object;
	Color c;
	public Join(GameController game){
		this(game,Color.BLACK);
	}

	public Join(GameController game,
			Color c){
		this.c = c;
		this.game= game;
	}

	public boolean Gravity(){

		if(GravityCheck(false)){
			GravityCheck(true);
			for(Object z : joinedjelly){
				z.yPos++;
			}
			return true;
		}
		return false;
	}

	public boolean GravityCheck(boolean debug){
		for(Join y : game.newGroups){
			for(Object x : y.joinedjelly){
				for(Object z : joinedjelly){
					//makes sure it is not checking with itself
					if(this == y){
						break;
					}
					//makes sure it is not going to be in another jelly.
					if(x.xPos==z.xPos && x.yPos==z.yPos+1&& x!=z){
						return false;
					}else{
						//makes sure it is not going to fall into the ground.
						if(game.level[z.yPos+1][z.xPos]==1){
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	public ArrayList<Join> NeighborMakerRight(){
		ArrayList<Join> Neighbor = new ArrayList<Join>();
		for(Object y: joinedjelly){
			for(Join i : game.newGroups){
				for(Object z : i.joinedjelly){
					if(y.yPos==z.yPos&&y.xPos+1==z.xPos&&!Neighbor.contains(i)&&i!=this){	
						Neighbor.add(i);
					}
				}
			}
		}
		return Neighbor;
	}
	public ArrayList<Join> NeighborMakerLeft(){
		ArrayList<Join> Neighbor = new ArrayList<Join>();
		for(Object y: joinedjelly){
			for(Join i : game.newGroups){
				for(Object z : i.joinedjelly){
					if(y.yPos==z.yPos&&y.xPos-1==z.xPos&&!Neighbor.contains(i)&&i!=this){	
						Neighbor.add(i);
					}
				}
			}
		}
		return Neighbor;
	}
	public void MoveRight(){
		if(!CheckRight()){
			return;
		}
		for( Join p : NeighborMakerRight()){
			p.MoveRight();
		}
		for(Object y : joinedjelly){
			y.moveright();
		}
	}
	public boolean CheckRight(){
		for(Object y: joinedjelly){
			if(y.xPos+1 < game.level[y.yPos].length &&
					game.level[y.yPos][y.xPos+1] ==1){
				return false;
			}
		}
			for(Join x : NeighborMakerRight()){
				if(!x.CheckRight()){
					return false;
				}
			}
			return true;
	}
	public boolean CheckLeft(){
		for(Object y: joinedjelly){
			if(y.xPos-1 >= 0 &&
					game.level[y.yPos][y.xPos-1] ==1){
				return false;
			}
		}
			for(Join x : NeighborMakerLeft()){
				if(!x.CheckLeft()){
					return false;
				}
			}
			return true;
	}


	//makes sure that the block to the right is clear


	public void MoveLeft(){
		if(!CheckLeft()){
			return;
		}
		for( Join p : NeighborMakerLeft()){
			p.MoveLeft();
		}
		for(Object y : joinedjelly){
			y.moveleft();
		}
	}
	//makes sure that the block to the right is clear


	public void Dothething(GameController game, Join group1, Join group2){
		group1.joinedjelly.addAll(group2.joinedjelly);
		group2.c = null;
		group2.joinedjelly.clear();
		game.HighlightSwitch();
	}

	public void CombineJelly(GameController game, Join group1, Join group2, String side){
		for(Object one : group1.joinedjelly){
			for(Object two : group2.joinedjelly){
				if(one.xPos == two.xPos
						&(one.yPos-1 == two.yPos && group1 != group2 || one.yPos+1 == two.yPos && group1 != group2)){
					Dothething(game,group1,group2);
					return;
				}

				//loops through the joinedjellys in the new groups and checks for the color
				if(one.yPos == two.yPos
						&&(one.xPos-1 == two.xPos && group1 != group2 || one.xPos+1 == two.xPos && group1 != group2)){
					Dothething(game,group1,group2);
					return;
					//checks if the jellys of the same color are touching and then puts them into the same group.
				}
				//checks for the jellys of the same color and if they are touching on the sides
			}
		}
	}


	public void checkforconnect(GameController game){
		ArrayList<Join> newGroups = game.newGroups;
		for( Join group1 : newGroups){
			for(Join group2 : newGroups){
				if(group1.c == group2.c){
					if(!group1.Gravity() && !group2.Gravity()){
						CombineJelly(game,group1,group2, "top");	
						CombineJelly(game,group1,group2, "side");
					}
				}
			}
		}
	}
}
