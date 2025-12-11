package main;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16 x 16px tile
    final int scale = 3; // scale original tile size by this value
    final int maxScreenCol = 16; // max number of tile columns on the screen
    final int maxScreenRow = 12; // max number of tile rows on the screen

    final int tileSize = originalTileSize * scale; // 48 x 48px tile
    final int screenWidth = tileSize * maxScreenCol; // 768px
    final int screenHeight = tileSize * maxScreenRow; // 576px

    final int FPS = 60;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    // set players default position
    int playerX = screenWidth / 2;
    int playerY = screenHeight / 2;
    int playerSpeed = 4;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    // sleep game loop method
//    @Override
//    public void run() {
//        double drawInterval = (double) 1000000000 / FPS; // one second divided by 60 FPS = num of seconds for one frame
//        double nextDrawTime = System.nanoTime() + drawInterval;
//        //game loop
//        while(gameThread != null){
//            // 1 UPDATE: updated information such as character position
//            update();
//            // 2 DRAW: draw screen with updated info
//            repaint();
//
//            try {
//                double remainingDrawTime = (nextDrawTime - System.nanoTime()) / 1000000; // convert to ms
//
//                if(remainingDrawTime < 0){
//                    remainingDrawTime = 0;
//                }
//                Thread.sleep((long) remainingDrawTime);
//
//                nextDrawTime += drawInterval;
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    // delta game loop method
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0; // hello i am a comment
        int drawCount = 0;

        while(gameThread != null){
            currentTime = System.nanoTime();
            timer += (currentTime - lastTime);
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                drawCount++;
                delta--;
            }

            if(timer >= 1000000000){
                System.out.println("FPS" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(){
        if(keyHandler.upPressed){
            playerY -= playerSpeed;
        }
        else if(keyHandler.downPressed){
            playerY += playerSpeed;
        }
        else if(keyHandler.leftPressed){
            playerX -= playerSpeed;
        }
        else if(keyHandler.rightPressed){
            playerX += playerSpeed;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose();
    }
}
