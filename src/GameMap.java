import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GameMap extends JPanel{
	GameMap() {
		this.setPreferredSize(new Dimension(550,500));
		this.setBounds(0,0,650,550);
	}
	private static final long serialVersionUID = 1L;
	
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		
		Image image = new ImageIcon("D:\\DownloadsD\\map.png").getImage();
		g.drawImage(image, 0,0,null);
	}
}
