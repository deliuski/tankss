import java.awt.*;

class Bullet {
    private int x, y;
    private int speed = 10;
    private int directionX, directionY;
    private int width = 10, height = 10;
    private Color color;
    
    public Bullet(int x, int y, int directionX, int directionY, Color color) {
        this.x = x;
        this.y = y;
        this.directionX = directionX; // No negation
        this.directionY = directionY;
        this.color = color;
    }
    

    public void move() {
        x += directionX * speed;
        y += directionY * speed;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, width, height);
    }

    public boolean isOffScreen(int screenWidth, int screenHeight) {
        return x < 0 || x > screenWidth || y < 0 || y > screenHeight;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
