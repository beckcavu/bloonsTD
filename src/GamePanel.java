import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener{
	
	public class bloon {
		bloon(int rank_in, int X_in, int Y_in) {
			rank = rank_in;
			section = 0;
			X = X_in;
			Y = Y_in;
		}
		public int rank, section, X, Y;
	}
	final int NUM_ROUNDS = 1;
	//HashMap<Integer, bloon[]> round_map;
	bloon[] round_1 = new bloon[5];
	int[] locationsX = new int[5];
	int[] locationsY = new int[5];
	JButton start_round;
	public int round;
	public int spawnX;
	public int spawnY;
	final public int BLOON_SIZE = 25;
	public Boolean running = false;
	public int section;
	public Boolean build_map = true;
	Timer timer;
	
	GamePanel() {
		section = 0;
		this.setPreferredSize(new Dimension(900,750));
		this.setLayout(null);
		round = 0;
		spawnX = 0;
		spawnY = 0;
		timer = new Timer(1,this);
		round_1[0] = new bloon(3,0,245);
		for (int i = 1; i < 5;++i) {
			round_1[i] = new bloon(2,round_1[i-1].X-BLOON_SIZE,245);
			locationsX[i] = spawnX - (i*BLOON_SIZE);
			locationsY[i] = 245;
		}
		Timer t2 = new Timer(1,new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});//use this to add bloons to the ARRAYLIST of a round 
		
		//round_map = new HashMap<Integer, bloon[]>();
		start_round = new JButton();
		start_round.setText("Start Round");
		start_round.setBounds(675, 0, 125,50);//X Y width height
		start_round.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				running = true;
				spawn_next_round();
			}});
		this.add(start_round);
		//construct_rounds();
		timer.start();
	}
	
	public void spawn_next_round() {
		++round;
		repaint(); // THIS NEEDS TO CHANGE WE DONT WANT THE BUTTON TO REPAINT
	}
	
	/*public void construct_rounds() {
		for (int i = 1; i <= NUM_ROUNDS; ++i) {
			bloon[] b = new bloon[i+2];
			for (int j = 0; j < b.length; ++j) {
				b[j] = new bloon(1);
			}
			round_map.put(i, b);
		}
	}*/
	
	
	public void paint(Graphics g) {
		super.paint(g);
		Image image = new ImageIcon("D:\\DownloadsD\\map.png").getImage();
		g.drawImage(image, 0,0,null);
		/*if (running) {
			bloon[] r = round_map.get(round);
			for (int i = 0; i < r.length; ++i) {
				g.fillOval(spawnX - (i*BLOON_SIZE), 0, BLOON_SIZE, BLOON_SIZE);
			}
		}*/
		if (running) {
			for (int i = 0; i < round_1.length; ++i) {
				g.fillOval(round_1[i].X, round_1[i].Y, BLOON_SIZE, BLOON_SIZE);
			}
		}
		
	}
	
	public void move() {
		for (int i = 0; i < round_1.length; ++i) {
			switch (round_1[i].section) {
			case 0: {
				round_1[i].X += round_1[i].rank;
				if (round_1[i].X >= 135) {
					++round_1[i].section;
				}
				break;
			}
			case 1: {
				round_1[i].Y -= round_1[i].rank;
				if (round_1[i].Y <= 115) {
					++round_1[i].section;
				}
				break;
			}
			case 2: {
				round_1[i].X += round_1[i].rank;
				if (round_1[i].X >= 246) {
					++round_1[i].section;
				}
				break;
			}
			case 3: {
				round_1[i].Y += round_1[i].rank;
				if (round_1[i].Y >= 367) {
					++round_1[i].section;
				}
				break;
			}
			case 4: {
				round_1[i].X -= round_1[i].rank;
				if (round_1[i].X <= 110) {
					++round_1[i].section;
				}
				break;
			}
			case 5: {
				round_1[i].Y += round_1[i].rank;
				if (round_1[i].Y >= 455) {
					++round_1[i].section;
				}
				break;
			}
			case 6: {
				round_1[i].X += round_1[i].rank;
				if (round_1[i].X >=487) {
					++round_1[i].section;
				}
				break;
			}
			case 7: {
				round_1[i].Y -= round_1[i].rank;
				if (round_1[i].Y <= 325) {
					++round_1[i].section;
				}
				break;
			}
			case 8: {
				round_1[i].X -= round_1[i].rank;
				if (round_1[i].X <= 387) {
					++round_1[i].section;
				}
				break;
			}
			case 9: {
				round_1[i].Y -= round_1[i].rank;
				if (round_1[i].Y <= 230) {
					++round_1[i].section;
				}
				break;
			}
			case 10: {
				round_1[i].X += round_1[i].rank;
				if (round_1[i].X >= 475) {
					++round_1[i].section;
				}
				break;
			}
			case 11: {
				round_1[i].Y -= round_1[i].rank;
				if (round_1[i].Y <= 80) {
					++round_1[i].section;
				}
				break;
			}
			case 12: {
				round_1[i].X -= round_1[i].rank;
				if (round_1[i].X<= 325) {
					++round_1[i].section;
				}
				break;
			}
			case 13: {
				round_1[i].Y -= round_1[i].rank;
				if (round_1[i].Y <= 0) {
					++round_1[i].section;
				}
				break;
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
