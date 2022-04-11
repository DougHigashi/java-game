package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

	
	final int originalTileSize = 16;
	final int scale = 3;
	
	public final int tileSize = originalTileSize  * scale;		//48x48
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;		//768 px
	public final int screenHeight = tileSize * maxScreenRow;	//576 px
	
	
	//World Settings
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	
	private int FPS = 60;
	
	KeyHandler keyHandler = new KeyHandler();
	Thread gameThread;
	public Player player = new Player(this, keyHandler);
	TileManager tm = new TileManager(this);
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyHandler);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		
		double drawTime = 1000000000/FPS;
		double delta = 0;					//Accumulator
		long lastTime = System.nanoTime();
		long now;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			now = System.nanoTime();
			delta += (now - lastTime) / drawTime;
			timer += (now - lastTime);
			lastTime = now;
			
			if(delta >= 1) {				//When accumulator == 1 (second) update and render
				update();
				repaint();
				delta--;
				drawCount +=1;
			}
			
			if(timer >= 1000000000) {		//When 1 second passes, print the fps
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}
	
	public void update() {
		player.update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		tm.draw(g2);
		player.draw(g2);
		
		g2.dispose();
	}
	
}
