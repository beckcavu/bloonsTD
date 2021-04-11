import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameFrame extends JFrame{
	GameFrame() {
		setSize(new Dimension(815,550));
		setLocationRelativeTo(null);
		setTitle("bloons TD");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		add(new GamePanel());
	}
}
