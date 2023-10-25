package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 16, 32, 32);

        setDefaultValues();
        getPLayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPLayerImage() {
        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/matedown1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/matedown2.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/mateup1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/mateup2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/mateleft1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/mateleft2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/materight1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/materight2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }
            //CHECK TILE COLLISION
            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            //IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (collisionOn == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case  "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spritePlayerCounter++;
            if (spritePlayerCounter > 12) {
                if (spritePlayerNum == 1) {
                    spritePlayerNum = 2;
                } else if (spritePlayerNum == 2) {
                    spritePlayerNum = 1;
                }
                spritePlayerCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spritePlayerNum == 1) {
                    image = up1;
                }
                if (spritePlayerNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spritePlayerNum == 1) {
                    image = down1;
                }
                if (spritePlayerNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spritePlayerNum == 1) {
                    image = left1;
                }
                if (spritePlayerNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spritePlayerNum == 1) {
                    image = right1;
                }
                if (spritePlayerNum == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
