import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import sun.security.ec.point.Point;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener{
	
	//Represents 1 bloon
	public class bloon {
		bloon(int rank_in, int X_in, int Y_in) {
			rank = rank_in;
			section = 0;
			X = X_in;
			Y = Y_in;
			sprite = rank == 1 ? RED_BLOON_SPRITE : BLUE_BLOON_SPRITE; //This is bad, need a better system 
		}
		public int rank, section, X, Y;
		public Image sprite;
	} 
	
	//super for all money types
	public class monkey_models {
		public int X;
		public int Y;
		public Image sprite;
	}
	
	//represents one dm
	public class dart_monkey_model extends monkey_models {
		dart_monkey_model() {
			X = 675;
			Y = 50;
			sprite = DM_SPRITE.getImage();
		}
	}
	
	//MEMBERS
	//<-----FINALS---->
	final ArrayList<Integer> RANKS = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6)); //ranks: red --> blue --> green --> yellow --> black --> white
	final int NUM_ROUNDS = 50;
	final int COST_OF_DM = 200;
	final public int BLOON_SIZE = 30;
	final Image RED_BLOON_SPRITE = new ImageIcon("images/red_bloon.png").getImage();
	final Image BLUE_BLOON_SPRITE = new ImageIcon("images/blue_bloon.png").getImage();
	final Image MAP_IMAGE = new ImageIcon("D:\\DownloadsD\\map.png").getImage();
	final ImageIcon CASH_IMAGE = new ImageIcon("images/cash_icon.png");
	final ImageIcon DM_SPRITE = new ImageIcon("images/dart_monkey.png");
	//<---FINALS--->
	

	ArrayList<bloon> round_collection;	//Represents what bloons are in a round

	ArrayList<monkey_models> monkeys;	//represents what monkeys are in play

	JButton start_round;	//buttons to repaint new round_collection once empty

	JButton dart_monkey_button;	//button to place and buy dm

	JLabel money;	//shows how much money is available

	Timer timer;	//repaint refresher
	

	public int round_in_play; // round happening
	public int spawnX; //bloon spawn point X
	public int spawnY; // ... Y

	public Boolean running = false; // is a round happening, must be init before paint
	public Boolean model_placing; // is a monkey being placed (CHECK IF THIS IS BEING USED)
	

	
	GamePanel() {
		//init members
		this.setPreferredSize(new Dimension(900,750));
		this.setLayout(null);
		this.setBackground(Color.white);
		
		round_in_play = 0;
		model_placing = false;
		spawnX = 0;
		spawnY = 245;
		timer = new Timer(1,this);
		round_collection = new ArrayList<bloon>();
		monkeys = new ArrayList<monkey_models>();
		
		build_round_collection(round_in_play);
		
		//MENU stuff
		dart_monkey_button = new JButton(DM_SPRITE);
		dart_monkey_button.setBounds(675,50,50,45);
		dart_monkey_button.addMouseListener(new MouseListener() {

			@Override
			//must be overriden, does nothing
			public void mouseClicked(MouseEvent e) {
				//must be overriden, does nothing
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				dart_monkey_model bob = new dart_monkey_model();
				monkeys.add(bob);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				money.setText(String.valueOf(Integer.parseInt(money.getText()) - COST_OF_DM)); // change this to cost of monkey
				model_placing = false;
			}

			@Override
			//must be overriden, does nothing
			public void mouseEntered(MouseEvent e) {
				//must be overriden, does nothing
				
			}

			@Override
			//must be overriden, does nothing
			public void mouseExited(MouseEvent e) {
				//must be overriden, does nothing
				
			}
		
		});
		dart_monkey_button.addMouseMotionListener(new MouseMotionListener() {


			@Override
			public void mouseDragged(MouseEvent e) {
				model_placing = true;
				moveModel(e.getX(),e.getY());
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				//must be here, does nothing
			}
		});
		
		money = new JLabel("750", CASH_IMAGE, JLabel.CENTER);
		money.setFont(new Font("SansSerif", Font.PLAIN, 30));
		money.setBounds(675,0,125,50);
	
		start_round = new JButton();
		start_round.setText("Start Round");
		start_round.setBounds(675, 465, 125,50);//X Y width height
		start_round.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				running = true;
				spawn_next_round();
				timer.start();
			}});
		
		this.add(start_round);
		this.add(money);
		this.add(dart_monkey_button);
		this.setVisible(true);
		
	}
	
	public void moveModel(int newX, int newY) {
		monkeys.get(monkeys.size()-1).X = newX + 650;
		monkeys.get(monkeys.size()-1).Y = newY + 25;
		repaint();
	}
	
	//NEEDS TO BE CHANGED. MAKE THIS A SEMI RANDOM EVENT TO GEN 50 ROUNDS
	public void build_round_collection(int round_in) {
		round_collection.add(new bloon(RANKS.get(0),spawnX-BLOON_SIZE,spawnY));
		if (round_in == 0) {
			for (int i = 1; i < 5; ++i) {
				round_collection.add(new bloon(RANKS.get(0), round_collection.get(i-1).X-BLOON_SIZE, spawnY));
			}
		}
		if (round_in == 1) {
			for (int i = 1; i <= 3; ++i) {
				round_collection.add(new bloon(RANKS.get(0), round_collection.get(i-1).X-BLOON_SIZE, spawnY));
			}
			for (int i = 1; i <= 4; ++i) {
				round_collection.add(new bloon(RANKS.get(1), round_collection.get(i-1).X-BLOON_SIZE, spawnY));
			}
		}
		

	}
	
	public void spawn_next_round() {
		repaint();
	}
	
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g.drawImage(MAP_IMAGE, 0,0,null);
		AffineTransform oldForm = g2.getTransform();
		if (running) {
			for (int i = 0; i < round_collection.size(); ++i) {
				g.drawImage(round_collection.get(i).sprite, round_collection.get(i).X, round_collection.get(i).Y, BLOON_SIZE, BLOON_SIZE, null);
			}
			
		}
		for(int i = 0; i < monkeys.size(); ++i) {
			if (running) {
				Rectangle first_bloon = new Rectangle();
				Rectangle monkey_to_check = new Rectangle();
				first_bloon.setRect(round_collection.get(0).X, round_collection.get(0).Y, BLOON_SIZE, BLOON_SIZE);
				System.out.println("getting monkey");
				monkey_to_check.setRect(monkeys.get(i).X+45-(4*BLOON_SIZE), monkeys.get(i).Y+45-(4*BLOON_SIZE), 8*BLOON_SIZE, 8*BLOON_SIZE);
				System.out.println("got it");
				if (first_bloon.intersects(monkey_to_check)) {
				g2.rotate(Math.atan2((double)round_collection.get(0).Y - (double)monkeys.get(i).Y, (double)round_collection.get(0).X-(double)monkeys.get(i).X)+Math.toRadians(90),monkeys.get(i).X+45, monkeys.get(i).Y+45);
				}
			}
			g2.drawImage(monkeys.get(i).sprite, monkeys.get(i).X, monkeys.get(i).Y, 90,90, null);
			g2.setTransform(oldForm);
		}
	}
	
	//BLOONS SHOULD MOVE BASED ON SPEED NOT RANK SEARCH WIKI
	
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
