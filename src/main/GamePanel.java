package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;

public class GamePanel extends JPanel implements Runnable{
  
  //SCREEN SETTINGS
  final int originaTitleSize = 16; //16x16 title
  final int scale = 3; //scale of the screen 

  public final int titleSize = originaTitleSize * scale; //48x48 pixels

  final int maxScreenCol = 16; //16 tiles wide
  final int maxScreenRow = 12; //12 tiles tall

  final int screenWidth = titleSize * maxScreenCol; //768 pixels wide
  final int screenHeight = titleSize * maxScreenRow; //576 pixels tall

	//FPS
	int FPS = 60;

  KeyHandler keyH = new KeyHandler();
  Thread gameThread;
  Player player = new Player(this, keyH);

  //Set player default position
  int playerX = 100;
  int playerY = 100;
  int playerSpeed = 4;


  public GamePanel() {
    
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.BLACK);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyH);
    this.setFocusable(true);
  }

  public void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
  } 

  @Override
  public void run() {

		double drawInterval = 1000000000 / FPS;  //0.0166666666666667 seconds
		double nextDrawTime = System.nanoTime() + drawInterval; //current time + 0.0166666666666667 seconds

		while (gameThread != null) {

			//1. Update information such as character positions
			update();


			//2. Draw the screen with the updated information
			repaint();


			try {
				double remainingTime = nextDrawTime - System.nanoTime();

				remainingTime = remainingTime / 1000000; //convert to milliseconds
				if (remainingTime < 0) {
					remainingTime = 0;
				}
				Thread.sleep((long) remainingTime);

				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

  public void update() {
    player.update();
  }

  public void paintComponent(Graphics g) {
    //2. Draw the screen with the updated information
    super.paintComponent(g);
    
    Graphics2D g2 = (Graphics2D) g;

    player.draw(g2);

    g2.dispose();
  }

}
