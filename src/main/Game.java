package main;

import javax.swing.JFrame;

class Game{
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		GamePanel gamePanel = new GamePanel();
		
		window.add(gamePanel);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.setResizable(false);
		window.setTitle("2D Game");
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		
		gamePanel.startGameThread();
	}
}