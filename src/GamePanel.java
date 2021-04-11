import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener{
	
	//Represents 1 bloon
	public class bloon {
		bloon(int rank_in, int X_in, int Y_in) {
			rank = rank_in;
			section = 0;
			X = X_in;
			Y = Y_in;
		}
		public int rank, section, X, Y;
		public Image sprite;
	}
	
	//members
	final int RED = 1;
	final int BLUE = 2;
	final int NUM_ROUNDS = 1;
	ArrayList<bloon> round_collection;
	JButton start_round;
	public int round_in_play;
	public int spawnX;
	public int spawnY;
	final public int BLOON_SIZE = 25;
	public Boolean running = false;
	Timer timer;
	
	GamePanel() {
		//init members
		this.setPreferredSize(new Dimension(900,750));
		this.setLayout(null);
		round_in_play = 0;
		spawnX = 0;
		spawnY = 245;
		timer = new Timer(1,this);
		round_collection = new ArrayList<bloon>();
		build_round_collection(round_in_play);
		
		start_round = new JButton();
		start_round.setText("Start Round");
		start_round.setBounds(675, 0, 125,50);//X Y width height
		start_round.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				running = true;
				spawn_next_round();
				timer.start();
			}});
		this.add(start_round);

		
	}
	
	public void build_round_collection(int round_in) {
		round_collection.add(new bloon(RED,spawnX-BLOON_SIZE,spawnY));
		if (round_in == 0) {
			for (int i = 1; i < 5; ++i) {
				round_collection.add(new bloon(BLUE, round_collection.get(i-1).X-BLOON_SIZE, spawnY));
			}
		}
		if (round_in == 1) {
			for (int i = 1; i <= 3; ++i) {
				round_collection.add(new bloon(RED, round_collection.get(i-1).X-BLOON_SIZE, spawnY));
			}
			for (int i = 1; i <= 4; ++i) {
				round_collection.add(new bloon(BLUE, round_collection.get(i-1).X-BLOON_SIZE, spawnY));
			}
		}
		

	}
	
	public void spawn_next_round() {
		repaint();
	}
	
	
	public void paint(Graphics g) { // CHANGE THIS PROCESS, BLOONS SHOULD HOLD OWN IMAGE (MAKE STATIC IMAGES)
		super.paint(g);
		Image image = new ImageIcon("D:\\DownloadsD\\map.png").getImage();
		g.drawImage(image, 0,0,null);
		Image red = new ImageIcon("images/red_bloon.png").getImage();
		Image blue = new ImageIcon("images/blue_bloon.png").getImage();
		if (running) {
			for (int i = 0; i < round_collection.size(); ++i) {
				g.drawImage(round_collection.get(i).rank == 1 ? red : blue, round_collection.get(i).X, round_collection.get(i).Y, BLOON_SIZE, BLOON_SIZE, null);
			}
		}
		
	}
	
	public void goRight(int i, int bound) {
		if ((round_collection.get(i).X += round_collection.get(i).rank) >= bound) {
			++round_collection.get(i).section;
		}
	}
	
	public void goUp(int i, int bound) {
		if ((round_collection.get(i).Y -= round_collection.get(i).rank) <= bound) {
			++round_collection.get(i).section;
		}
	}
	
	public void goDown(int i, int bound) {
		if ((round_collection.get(i).Y += round_collection.get(i).rank) >= bound) {
			++round_collection.get(i).section;
		}
	}
	
	public void goLeft(int i, int bound) {
		if ((round_collection.get(i).X -= round_collection.get(i).rank) <= bound) {
			++round_collection.get(i).section;
		}
	}
	
	public void move() {
		for (int i = 0; i < round_collection.size(); ++i) {
			switch (round_collection.get(i).section) {
			case 0: {
				goRight(i , 135);
				break;
			}
			case 1: {
				goUp(i, 115);
				break;
			}
			case 2: {
				goRight(i,246);
				break;
			}
			case 3: {
				goDown(i, 367);
				break;
			}
			case 4: {
				goLeft(i, 110);
				break;
			}
			case 5: {
				goDown(i, 455);
				break;
			}
			case 6: {
				goRight(i, 487);
				break;
			}
			case 7: {
				goUp(i, 325);
				break;
			}
			case 8: {
				goLeft(i,387);
				break;
			}
			case 9: {
				goUp(i,230);
				break;
			}
			case 10: {
				goRight(i, 475);
				break;
			}
			case 11: {
				goUp(i, 80);
				break;
			}
			case 12: {
				goLeft(i, 325);
				break;
			}
			case 13: {
				goUp(i, 0);
				break;
			}
			case 14: {
				round_collection.remove(i);
				if (round_collection.isEmpty()) {
					timer.stop();
					build_round_collection(++round_in_play);
				}
			}
			}
		}
		
		repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		move();
	}

}
