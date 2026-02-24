package entity;

import main.GamePanel;

public class Projectile extends Entity {
    Entity user;
    public Projectile(GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user){
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.health = this.maxHealth;
    }

    public void update(){
        if(user == gp.player){
            // player -> monster
            // check for collision with monster
            int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monster);
            if(monsterIndex != 999){
                gp.player.damageMonster(monsterIndex, this, attack * (gp.player.level / 2), knockBackPower); // damage increases with player level
                generateParticle(user.projectile, gp.monster[gp.currentMap][monsterIndex]);
                alive = false;
            }
        }
        else{ // projectile user = monster
            // monster -> player
            // check for collision with player
            boolean contactPlayer = gp.collisionChecker.checkPlayer(this);
            if(!gp.player.invincible && contactPlayer){
                damagePlayer(attack);
                generateParticle(user.projectile, gp.player);
                alive = false;
            }
        }

        switch(direction){
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
        }

        health--;
        if(health <= 0){
            alive = false;
        }

        spriteCounter++;
        if(spriteCounter > 12){
            if(spriteNumber == 1){
                spriteNumber = 2;
            }
            else if(spriteNumber == 2){
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }
    }

    // overridden in subclasses
    public boolean hasResource(Entity user){
        boolean hasResource = false;
        return hasResource;
    }

    // overridden in subclasses
    public void useResource(Entity user){
    }
}
