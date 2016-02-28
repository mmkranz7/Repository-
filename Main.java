import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;


public class Main {
	static JComboBox levelchooser = new JComboBox();
	public static void main(String[] args) {
		GameController game = new GameController();
		JFrame frame = new JFrame();
		String[] choices = { "Level 1","Level 2", "Level 3","Level 4","Level 5","Level 6"};

		levelchooser = new JComboBox<String>(choices);
		frame.add(levelchooser);
		levelchooser.setVisible(true);
		frame.setVisible(true);
		frame.setSize(200,50);

	}
	public void SelectedLevel(GameController game){
		levelchooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.levelnumber = levelchooser.getSelectedIndex();
				game.reset();
				System.out.print("HERE");
			}
		});
	}
}

