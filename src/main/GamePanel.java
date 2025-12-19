package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16 x 16px tile
    final int scale = 3; // scale original tile size by this value
    public final int tileSize = originalTileSize * scale; // 48 x 48px tile

    public final int maxScreenCol = 16; // max number of tile columns on the screen
    public final int maxScreenRow = 12; // max number of tile rows on the screen

    public final int screenWidth = tileSize * maxScreenCol; // 768px
    public final int screenHeight = tileSize * maxScreenRow; // 576px

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    final int FPS = 60;

    TileManager tileManager = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler();
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    // ENTITIES AND OBJECTS
    public Player player = new Player(this, keyHandler);
    public SuperObject obj[] = new SuperObject[10]; // display 10 objects at a time, can replace after objects are picked up

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame(){
        assetSetter.setObject();

        playMusic(0); // BlueBoyAdventure song at index 0
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    // delta game loop method
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
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
//                System.out.println("FPS" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(){
        player.update();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // DEBUG
        long drawStart = 0;
        if(keyHandler.checkDrawTime){
            drawStart = System.nanoTime();
        }

        // tiles
        tileManager.draw(g2); // before drawing player, so player is drawn on top

        //object
        for(int i = 0; i < obj.length; i++){
            if(obj[i] != null){
                obj[i].draw(g2, this);
            }
        }

        // player - drawn on top of objects and tiles
        player.draw(g2);

        // ui - usually top layer
        ui.draw(g2);

        // DEBUG
        if(keyHandler.checkDrawTime){
            long drawEnd = System.nanoTime();
            long timePassed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + timePassed, 10, 400);
            System.out.println("Draw Time: " + timePassed);
        }

        g2.dispose();
    }

    public void playMusic(int index){
        music.setFile(index);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSoundEffect(int index){
        soundEffect.setFile(index);
        soundEffect.play();
    }
}
