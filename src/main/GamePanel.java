package main;

import ai.PathFinder;
import entity.Player;
import tile.TileManager;
import entity.Entity;
import tile_interactive.InteractiveTile;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable{
    Config config = new Config(this);
    public boolean debug = false;

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16 x 16px tile
    final int scale = 3; // scale original tile size by this value
    public final int tileSize = originalTileSize * scale; // 48 x 48px tile

    public final int maxScreenCol = 20; // max number of tile columns on the screen
    public final int maxScreenRow = 12; // max number of tile rows on the screen

    public final int screenWidth = tileSize * maxScreenCol; // 960px
    public final int screenHeight = tileSize * maxScreenRow; // 576px

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 0;

    // FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;

    final int FPS = 60;

    // SYSTEM
    public TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    public PathFinder pathFinder = new PathFinder(this);
    Thread gameThread;

    // ENTITIES AND OBJECTS
    public Player player = new Player(this, keyHandler);
    public Entity obj[][] = new Entity[maxMap][50]; // display 50 objects at a time, can replace after objects are picked up
    public Entity npc[][] = new Entity[maxMap][10]; // holds up to 10 NPCs
    public Entity monster[][] = new Entity[maxMap][20]; // holds up to 10 monsters at once
    public InteractiveTile interactiveTile[][] = new InteractiveTile[maxMap][50];

    // projectiles from players and monsters
    public Entity projectile[][] = new Entity[maxMap][20];
//    public ArrayList<Entity> projectileList = new ArrayList<>();

    // particles
    public ArrayList<Entity> particleList = new ArrayList<>();

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
    public final int optionsState = 6;
    public final int gameOverState = 7;
    public final int transitionState = 8;
    public final int tradeState = 9;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);

        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame(){
        assetSetter.setInteractiveTile();
        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setMonster();
        playMusic(0); // BlueBoyAdventure song at index 0

        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();

        if(fullScreenOn){
            setFullScreen();
        }
    }

    public void setFullScreen(){
        // GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        gd.setFullScreenWindow(Main.window);

        // GET FULLSCREEN WIDTH AND HEIGHT
        screenWidth2 = Main.window.getWidth(); // monitor fullscreen width
        screenHeight2 = Main.window.getHeight(); // monitor fullscreen height
    }

    public void retry(){
        // reset player position and life
        player.setDefaultPositions();
        player.restoreLifeAndMana();

        // reset NPC and monsters
        assetSetter.setNPC();
        assetSetter.setMonster();
    }

    public void restart(){
        // reset player inventory and stats
        player.setDefaultValues();
        player.setItems();

        // reset all objects and entities
        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setMonster();
        assetSetter.setInteractiveTile();
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
                drawToTempScreen(); // draw to buffered image
                drawToScreen(); // draw buffered image to screen
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
            for(int i = 0; i < npc[currentMap].length; i++){
                if(npc[currentMap][i] != null){
                    npc[currentMap][i].update();
                }
            }

            // monsters
            for(int i = 0; i < monster[currentMap].length; i++){
                if(monster[currentMap][i] != null){
                    if(monster[currentMap][i].alive && !monster[currentMap][i].dying){
                        monster[currentMap][i].update();
                    }
                    else if(!monster[currentMap][i].alive){
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }
            // projectiles
            for(int i = 0; i < projectile[currentMap].length; i++){
                if(projectile[currentMap][i] != null){
                    if(projectile[currentMap][i].alive){
                        projectile[currentMap][i].update();
                    }
                    else if(!projectile[currentMap][i].alive){
                        projectile[currentMap][i] = null;
                    }
                }
            }

            // particles
            for(int i = 0; i < particleList.size(); i++){
                if(particleList.get(i) != null){
                    if(particleList.get(i).alive){
                        particleList.get(i).update();
                    }
                    else if(!particleList.get(i).alive){
                        particleList.remove(i);
                    }
                }
            }

            // interactive tiles
            for(int i = 0; i < interactiveTile[currentMap].length; i++){
                if(interactiveTile[currentMap][i] != null){
                    interactiveTile[currentMap][i].update();
                }
            }
        }
        if(gameState == pauseState){
            // nothing happens
        }
    }

    public void drawToTempScreen(){
        // DEBUG
        long drawStart = 0;
        if(debug){
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        }
        else{ // NON-TITLE SCREEN

            // draw background -- DOESNT WORK
            Color backgroundColor = new Color(73, 162, 105);
            g2.setBackground(backgroundColor);
            g2.drawRect(0, 0, screenWidth, screenHeight);

            // tiles
            tileManager.draw(g2); // before drawing player, so player is drawn on top

            // interactive tiles
            for(int i = 0; i < interactiveTile[currentMap].length; i++){
                if(interactiveTile[currentMap][i] != null){
                    interactiveTile[currentMap][i].draw(g2);
                }
            }

            // ADD ALL ENTITIES TO ENTITY LIST
            // add player
            entityList.add(player);

            // add NPCs
            for(int i = 0; i < npc[currentMap].length; i++){
                if(npc[currentMap][i] != null){
                    entityList.add(npc[currentMap][i]);
                }
            }

            // add monsters
            for(int i = 0; i < monster[currentMap].length; i++){
                if(monster[currentMap][i] != null){
                    entityList.add(monster[currentMap][i]);
                }
            }

            // add projectiles
            for(int i = 0; i < projectile[currentMap].length; i++){
                if(projectile[currentMap][i] != null){
                    entityList.add(projectile[currentMap][i]);
                }
            }

            // add particles
            for(int i = 0; i < particleList.size(); i++){
                if(particleList.get(i) != null){
                    entityList.add(particleList.get(i));
                }
            }

            // add objects
            for(int i = 0; i < obj[currentMap].length; i++){
                if(obj[currentMap][i] != null){
                    entityList.add(obj[currentMap][i]);
                }
            }

            // sort by world y coordinates -- entity below another entity is rendered in front
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
            if(debug){
                g2.setFont(new Font("Arial", Font.PLAIN, 20));

                long drawEnd = System.nanoTime();
                long timePassed = drawEnd - drawStart;

                g2.setColor(Color.black);
                g2.drawString("Draw Time: " + timePassed,(tileSize / 3) + 2 , (tileSize * 10) + 2);
                g2.setColor(Color.white);
                g2.drawString("Draw Time: " + timePassed, tileSize / 3, tileSize * 10);
            }
        }
    }

    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
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
