import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

class Battlefield extends JPanel implements KeyListener {
    private ArrayList<Tank> tanks = new ArrayList<>();
    private Timer timer;

    public Battlefield() {
        setFocusable(true);
        addKeyListener(this);

        // Initialize tanks with their starting positions
        tanks.add(new Tank(50, 100, Color.RED, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_Q));      // Red tank shoots with Q
        tanks.add(new Tank(700, 100, Color.BLUE, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.   VK_ENTER)); // Blue  tank shoots with Enter
        tanks.add(new Tank(375, 500, Color.GREEN, KeyEvent.VK_T, KeyEvent.VK_G, KeyEvent.VK_F, KeyEvent.VK_H, KeyEvent.VK_SPACE)); //       Green tank shoots with Space

        
        // Set up the game loop with a timer (repaints every 20 ms)
        timer = new Timer(20, e -> {
            updateGame();
            repaint();
        });
        timer.start();
    }

    private void updateGame() {
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
    
        for (Tank tank : tanks) {
            tank.move();
    
            // Check each tank's bullets against other tanks
            for (Bullet bullet : tank.getBullets()) {
                for (Tank target : tanks) {
                    if (target != tank && bullet.getBounds().intersects(target.getBounds())) {
                        target.takeDamage();
                        bulletsToRemove.add(bullet);
                    }
                }
            }
        }
    
        // Remove bullets that hit tanks
        for (Tank tank : tanks) {
            tank.getBullets().removeAll(bulletsToRemove);
        }
    
        // Remove tanks that are destroyed
        tanks.removeIf(Tank::isDestroyed);
    }
    
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.GRAY);
        
        for (Tank tank : tanks) {
            tank.draw(g);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for (Tank tank : tanks) {
            tank.keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for (Tank tank : tanks) {
            tank.keyReleased(e);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
