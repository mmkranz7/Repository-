import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
public class GameController extends JFrame {
	int CELL_SIZE = 50;
	int y = 0;
	int levelnumber = 0;
	 
	//Controls the scale of the game
	Object object;
	Join join;
	Main main;
	public boolean win = false;
	int WhichNewGroup = 0;
	//	ArrayList<Object> jellys;
	ArrayList<Join> newGroups = new ArrayList<>();
	Object[][] groundblocks;
	int[][] level;
	int[][][] Levels;
	ArrayList<ArrayList<ArrayList<Integer>>> ListLevels = new ArrayList<ArrayList<ArrayList<Integer>>>();
	//making the levels ^
	public GameController(){
		super();
		initjellys();
		createJelly();
		level = Levels[levelnumber];
		initGUI();

		//the the initialization that only happens once
	}
	private void initGUI() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
		setSize(level[0].length*CELL_SIZE, level.length*CELL_SIZE);
		// making the height of the game  making the length of the game
		KeyListener listener = new MyKeyListener();
		addKeyListener(listener);
		setFocusable(true);
		object = new Object(this);
		main = new Main();
		//makes the screen and key listeners

	}
	private void initjellys(){
		boolean LevelInitComplete = false;
		BufferedReader reader;
		String line = null;
		try {
			int numlevel=0;
			reader = new BufferedReader(new FileReader("src/Levels.txt"));
			line = reader.readLine();
			//reads one line of the text document containing the levels
			while(line!=null){
				int y = 0;
				while(line!=(null)&&!line.equals("")){

					ListLevels.add(new ArrayList <>());
					ListLevels.get(numlevel).add(new ArrayList<>());
					for(int x=0; x<line.length(); x++){	
						System.out.print(numlevel);
						ListLevels.get(numlevel).get(y).add((int) (line.charAt(x)-'0'));
						System.out.print((line.charAt(x)-'0'));
						//loops through the line adding the necesary characters into the correct parts of the triple array
					}
					System.out.println();
					y++;
					line = reader.readLine();
				}
				numlevel++;
				System.out.println();
				System.out.println(numlevel);
				System.out.println();
				
				line = reader.readLine();
			}
//			for( Integer i = 0; i <numlevel; i++){
//			levelchooser.addItem(i.toString());
//			}
			Levels = new int[ListLevels.size()][][];
			for(int z=0; z<numlevel; z++){
				System.out.println(ListLevels.get(z).size());
				Levels[z] = new int[ListLevels.get(z).size()][ListLevels.get(z).get(0).size()];
				for(int c=0; c<ListLevels.get(z).size(); c++){
					for(int v=0; v< ListLevels.get(z).get(c).size(); v++){
						Levels[z][c][v]= 
								ListLevels.get(z).get(c).get(v);
						//converts the arraylist into an array
					}
				}
			}
			y=0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	
	public void createJelly(){
		level = Levels[levelnumber];
		for(int i = 0;i < level[0].length; i++){
			for(int j =0; j<level.length;j++){
				if(level[j][i] == 2){
					Join newGroup = new Join(this, Color.RED);
					newGroup.joinedjelly.add( new Object(this,i,j,Color.RED,1,1,newGroup));
					newGroups.add(newGroup);
				}
				if(level[j][i] == 3){
					Join newGroup = new Join(this, Color.BLUE);
					newGroup.joinedjelly.add( new Object(this,i,j,Color.BLUE,1,1,newGroup));
					newGroups.add(newGroup);
				}
				if(level[j][i] == 4){
					Join newGroup = new Join(this, Color.GREEN);
					newGroup.joinedjelly.add( new Object(this,i,j,Color.GREEN,1,1,newGroup));
					newGroups.add(newGroup);
				}
				//initializes the jellys, using the numbers to represent different types of blocks
			}

		}
	}

	public void step(){
		//loops over and over as the game loop
		//		CELL_SIZE = getWidth()/12;
		Join newGroup = new Join(this);
		for( Join x : newGroups){
			x.Gravity();
		}
		//		if(!join.GravityCheck(false)){
		main.SelectedLevel(this);

		newGroup.checkforconnect(this);
		//checks if jellys have connected
		//		}
		win = true;
		for(Join x : newGroups){
			for(Join y : newGroups){
				if(x.c==y.c&& x!=y&& x.c!=null){
					win = false;
					//decides wheter you have won
				}
			}
		}	
	}

	public void delay (int time){
		try{
			Thread.sleep(time);
		}
		catch (Exception e){	
		}
		//makes it appear more smooth
	}
	public void display(Graphics g, Object ob){
		g.setColor(ob.c);
		g.fillRect(ob.xPos*CELL_SIZE, ob.yPos*CELL_SIZE, ob.width*CELL_SIZE, ob.height*CELL_SIZE);	
	}
	//displays the jelly objects but only 1 at a time
	public void HighlightSwitch(){
		if(WhichNewGroup==newGroups.size()-1){
			WhichNewGroup=0;
		}else{
			WhichNewGroup++;
		}
		if(newGroups.get(WhichNewGroup).joinedjelly.size() ==0){
			WhichNewGroup++;
		}
		WhichNewGroup %= newGroups.size();
	}
	//makes the jellys get highlighted	
	public void Highlight(Graphics g){
		for(int x=0; x < newGroups.get(WhichNewGroup).joinedjelly.size();x++){
			Object active = newGroups.get(WhichNewGroup).joinedjelly.get(x);
			g.fillRect(active.xPos*CELL_SIZE -CELL_SIZE/10,
					active.yPos*CELL_SIZE -CELL_SIZE/10,
					CELL_SIZE+CELL_SIZE/5,
					CELL_SIZE+CELL_SIZE/5);
		}
	}
	public void reset(){
		newGroups.clear();
		createJelly();
		level = Levels[levelnumber];
		setSize(level[levelnumber].length*CELL_SIZE, level.length*CELL_SIZE);
		//resets the jellys
	}
	public void paint(Graphics g) {

		g.setColor(Color.gray);
		g.fillRect(0, 0, 100*CELL_SIZE, 100*CELL_SIZE);
		//^ makes the background grey
		g.setColor(Color.YELLOW);
		Highlight(g);
		//^makes the highlight

		g.setColor(Color.BLACK);
		for(Join x : newGroups){ 
			for(Object z : x.joinedjelly ){
				display(g,z);
			}
		}
		//makes the jellys
		g.setColor(Color.WHITE);


		for(int i = 0;i < level[levelnumber].length; i++){
			for(int j =0; j<level.length;j++){
				if(level[j][i]==1){
					g.fillRect(i*CELL_SIZE, j*CELL_SIZE, CELL_SIZE, CELL_SIZE);
				}
				//creates the game walls/ground

			}
		}
		//makes the tutorial thingy and then takes it away once u hit a button
		if(y==0){
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 1000, 1000);
			g.setColor(Color.black);
			g.drawString("HELLO AND WELCOME TO THE JELLY NO PUZZLE REMAKE", 100, 100);
			g.drawString("THE GOAL OF THIS GAME IS TO CONNECT ALL OF THE JELLIES OF THE SAME COLOR", 60, 120);
			g.drawString("ONCE JELLIES ARE CONNECTED YOU CAN STILL MOVE THEM. USE THE LEFT AND RIGHT",50,140);
			g.drawString("ARROWS TO MOVE. THE SPACEBAR IS USED TO SWITCH JELLIES. USE R TO RESET.", 60, 160);
			g.drawString("HIT ENTER TO BEGIN", 100, 180);
		}
		//Displays the jellys that have joined
		if(win == true){
			levelnumber++;
			reset();
			win = false;
			//			 newGroup.joinedjelly = new ArrayList();
			//not needed but probably need to re initialize


			//reinitializes the jellys allowing for the next level to begin
		}
		step();
		delay(25);
		repaint();
	}
	class MyKeyListener implements KeyListener {
		/*@Override indicates that these functions override
		 * functions in their parent class.
		 */ 
		@Override
		public void keyTyped(KeyEvent e) {


		}
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()){
			case KeyEvent.VK_RIGHT:System.out.println("right key pressed");newGroups.get(WhichNewGroup).MoveRight();
			break;
			case KeyEvent.VK_LEFT:System.out.println("left key pressed");newGroups.get(WhichNewGroup).MoveLeft();
			break;
			case KeyEvent.VK_DOWN:System.out.println("down key pressed");
			break;
			case KeyEvent.VK_SPACE:System.out.println("Space key pressed");HighlightSwitch();
			break;
			case KeyEvent.VK_R:System.out.println("r pressed");reset();
			break;
			case KeyEvent.VK_ENTER:System.out.println("enter pressed");y=1;
			break;
			case KeyEvent.VK_Y:System.out.println("enter pressed");win=true;
			break;
			case KeyEvent.VK_4:System.out.println("4 pressed");y=1;win=true;
			break;
			}
			System.out.println("keyPressed="+e.getKeyCode());
		}
		//All the key presses happen here
		@Override
		public void keyReleased(KeyEvent e) {
			System.out.println("keyReleased="+KeyEvent.getKeyText(e.getKeyCode()));
			//these are the key listeners that will be able to do things when a key is pressed.
		}
	}
}
