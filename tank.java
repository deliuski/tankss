import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;

class Tank {
    private int x, y;
    private int health = 3;
    private int maxHealth = 3;
    private int speed = 5;
    private BufferedImage image; // Use BufferedImage for better compatibility
    private boolean up, down, left, right;
    private int upKey, downKey, leftKey, rightKey, shootKey;
    private int width = 50, height = 75;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private double angle; // Angle for rotation

    public Tank(int x, int y, String imagePath, int upKey, int downKey, int leftKey, int rightKey, int shootKey) {
        this.x = x;
        this.y = y;
        loadTankImage(imagePath);
        this.upKey = upKey;
        this.downKey = downKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.shootKey = shootKey;
    }

    private void loadTankImage(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldTransform = g2d.getTransform();

        g2d.translate(x + width / 2, y + height / 2);
        g2d.rotate(angle);
        g2d.translate(-width / 2, -height / 2);
        g2d.drawImage(image, 0, 0, width, height, null);

        g2d.setTransform(oldTransform);

        drawHealthBar(g);
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
    }

    private void drawHealthBar(Graphics g) {
        int barWidth = width;
        int barHeight = 10;
        int healthBarWidth = (int) ((double) health / maxHealth * barWidth);
        g.setColor(Color.GRAY);
        g.fillRect(x, y - barHeight - 5, barWidth, barHeight);
        g.setColor(health > 1 ? Color.GREEN : Color.RED);
        g.fillRect(x, y - barHeight - 5, healthBarWidth, barHeight);
    }

    public void move() {
        if (up) {
            y -= speed;
            angle = 0; // Facing up
        }
        if (down) {
            y += speed;
            angle = Math.PI; // Facing down
        }
        if (left) {
            x -= speed;
            angle = -Math.PI / 2; // Facing left
        }
        if (right) {
            x += speed;
            angle = Math.PI / 2; // Facing right
        }
    
        if (up && left) {
            x -= speed; //up and left
            angle = -Math.PI / 4;
        }
        if (up && right) {
            x += speed; //up and right
            angle = Math.PI / 4;
        }
        if (down && left) {
            x -= speed; //down and left
            angle = -3 * Math.PI / 4;
        }
        if (down && right) {
            x += speed; // down and right
            angle = 3 * Math.PI / 4;
        }
        for (Bullet bullet : bullets) {
            bullet.move();
        }
        bullets.removeIf(bullet -> bullet.isOffScreen(1366, 768));
    }   

    public boolean isDestroyed() {
        return health <= 0;
    }

    public void takeDamage() {
        health--;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == upKey) { up = true; }
        if (key == downKey) { down = true; }
        if (key == leftKey) { left = true; }
        if (key == rightKey) { right = true; }
        if (key == shootKey) shoot();
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == upKey) up = false;
        if (key == downKey) down = false;
        if (key == leftKey) left = false;
        if (key == rightKey) right = false;
    }

    public void shoot() {
        int bulletX = x + width / 2;
        int bulletY = y + height / 2;
        double radianAngle = angle;
        int normX = (int) (Math.cos(radianAngle) * 3);
        int normY = (int) (Math.sin(radianAngle) * 3);
        int rotatedX = (int) (normX * Math.cos(-Math.PI / 2) - normY * Math.sin(-Math.PI / 2));
        int rotatedY = (int) (normX * Math.sin(-Math.PI / 2) + normY * Math.cos(-Math.PI / 2));
        bullets.add(new Bullet(bulletX, bulletY, rotatedX, rotatedY, Color.YELLOW));
    }
    

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}
