package main;

import entity.Player;
import tile.TileManager;
import entity.Entity;

import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{
    public boolean debug = false;

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

    // SYSTEM
    TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    Thread gameThread;

    // ENTITIES AND OBJECTS
    public Player player = new Player(this, keyHandler);
    public Entity obj[] = new Entity[50]; // display 50 objects at a time, can replace after objects are picked up
    public Entity npc[] = new Entity[10]; // holds up to 10 NPCs
    public Entity monster[] = new Entity[20]; // holds up to 10 monsters at once
    public ArrayList<Entity> projectileList = new ArrayList<>();

    // all types of entities will be in this list, used for render order
    ArrayList<Entity> entityList = new ArrayList<Entity>();

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int inventoryState = 5;


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        Color backgroundColor = new Color(73, 162, 105);
        this.setBackground(backgroundColor);
        this.setDoubleBuffered(true);

        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame(){
        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setMonster();
//        playMusic(0); // BlueBoyAdventure song at index 0

        gameState = titleState;
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
        if(gameState == playState){
            // player
            player.update();
            // npcs
            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    npc[i].update();
                }
            }

            // monsters
            for(int i = 0; i < monster.length; i++){
                if(monster[i] != null){
                    if(monster[i].alive && !monster[i].dying){
                        monster[i].update();
                    }
                    else if(!monster[i].alive){
                        monster[i].checkDrop();
                        monster[i] = null;
                    }
                }
            }
            // projectiles
            for(int i = 0; i < projectileList.size(); i++){
                if(projectileList.get(i) != null){
                    if(projectileList.get(i).alive){
                        projectileList.get(i).update();
                    }
                    else if(!projectileList.get(i).alive){
                        projectileList.remove(i);
                    }
                }
            }
        }
        if(gameState == pauseState){
            // nothing
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // DEBUG
        long drawStart = 0;
        if(keyHandler.checkDrawTime){
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        }
        else{ // NON-TITLE SCREEN
            // tiles
            tileManager.draw(g2); // before drawing player, so player is drawn on top

            // ADD ALL ENTITIES TO ENTITY LIST
            // add player
            entityList.add(player);

            // add NPCs
            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    entityList.add(npc[i]);
                }
            }

            // add monsters
            for(int i = 0; i < monster.length; i++){
                if(monster[i] != null){
                    entityList.add(monster[i]);
                }
            }

            // add projectiles
            for(int i = 0; i < projectileList.size(); i++){
                if(projectileList.get(i) != null){
                    entityList.add(projectileList.get(i));
                }
            }

            // add objects
            for(int i = 0; i < obj.length; i++){
                if(obj[i] != null){
                    entityList.add(obj[i]);
                }
            }

            // sort by world y coordinates
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            // DRAW ENTITIES
            for(int i = 0; i < entityList.size(); i++){
                entityList.get(i).draw(g2);
            }

            // EMPTY ENTITY LIST
            entityList.clear();

            // ui - usually top layer -> rendered last
            ui.draw(g2);

            // DEBUG
            // show draw time
            if(keyHandler.checkDrawTime){
                long drawEnd = System.nanoTime();
                long timePassed = drawEnd - drawStart;
                g2.setColor(Color.white);
                g2.drawString("Draw Time: " + timePassed, 10, 400);
                System.out.println("Draw Time: " + timePassed);
            }
            // general debug
            if(debug){
                g2.setFont(new Font("Arial", Font.PLAIN, 20));
                g2.setColor(Color.white);
                int x = 10;
                int y = 100;
                int lineHeight = 20;
                g2.drawString(player.worldX + ", " + player.worldY, x, y);
                y += lineHeight;

                g2.drawString("Col: " + (player.worldX + player.solidArea.x) / tileSize, x, y);
                y += lineHeight;

                g2.drawString("Row: " + (player.worldY + player.solidArea.y) / tileSize, x, y);
                y += lineHeight;

            }
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
