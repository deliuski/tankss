import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

class Tank {
    private int x, y;
    private int health = 3;
    private int maxHealth = 3;
    private int speed = 5;
    private Color color;
    private boolean up, down, left, right;
    private int upKey, downKey, leftKey, rightKey, shootKey;
    private int width = 50, height = 75;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    
    // Direction the tank is facing (1 for forward or 0 for neutral)
    private int directionX = 1, directionY = 0;

    public Tank(int x, int y, Color color, int upKey, int downKey, int leftKey, int rightKey, int shootKey) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.upKey = upKey;
        this.downKey = downKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.shootKey = shootKey;
    }

    public void draw(Graphics g) {
        // Draw the tank
        g.setColor(color);
        g.fillRect(x, y, width, height);
        
        // Draw health bar
        drawHealthBar(g);
        
        // Draw each bullet
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
    }
    

    private void drawHealthBar(Graphics g) {
        int barWidth = width; // Full width of the tank
        int barHeight = 10;
        int healthBarWidth = (int) ((double) health / maxHealth * barWidth);

        // Background for health bar (gray)
        g.setColor(Color.GRAY);
        g.fillRect(x, y - barHeight - 5, barWidth, barHeight);

        // Health bar (red when health is low)
        g.setColor(health > 1 ? Color.GREEN : Color.RED);
        g.fillRect(x, y - barHeight - 5, healthBarWidth, barHeight);
    }


    public void move() {
        if (up) y -= speed;
        if (down) y += speed;
        if (left) x -= speed;
        if (right) x += speed;

        // Update each bullet's position
        bullets.forEach(Bullet::move);
        
        // Remove off-screen bullets
        bullets.removeIf(bullet -> bullet.isOffScreen(800, 600)); // screen width and height
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
        
        if (key == upKey) {
            up = true; directionY = -1; directionX = 0;
        }
        if (key == downKey) {
            down = true; directionY = 1; directionX = 0;
        }
        if (key == leftKey) {
            left = true; directionX = -1; directionY = 0;
        }
        if (key == rightKey) {
            right = true; directionX = 1; directionY = 0;
        }
        if (key == shootKey) {
            shoot();
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == upKey) up = false;
        if (key == downKey) down = false;
        if (key == leftKey) left = false;
        if (key == rightKey) right = false;
    }

    public void shoot() {
        // Add a new bullet in the tank's direction
        int bulletX = x + width / 2;
        int bulletY = y + height / 2;
        bullets.add(new Bullet(bulletX, bulletY, directionX, directionY, color));
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}
