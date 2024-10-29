import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Battlefield extends JPanel implements KeyListener {
    private ArrayList<Tank> tanks = new ArrayList<>();
    private Timer timer;
    private BufferedImage mapImage;

    public Battlefield() {
        setFocusable(true);
        addKeyListener(this);

        // Load the map image
        try {
            mapImage = ImageIO.read(new File("photos\\map.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize tanks
        tanks.add(new Tank(50, 100, "photos\\tank_1.png", KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_Q)); // Red tank
        tanks.add(new Tank(700, 100, "photos\\tank_2.png", KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER)); // Blue tank
        tanks.add(new Tank(375, 500, "photos\\tank_3.png", KeyEvent.VK_T, KeyEvent.VK_G, KeyEvent.VK_F, KeyEvent.VK_H, KeyEvent.VK_SPACE)); // Green tank

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
        for (Tank tank : tanks) {
            tank.getBullets().removeAll(bulletsToRemove);
        }
        tanks.removeIf(Tank::isDestroyed);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (mapImage != null) {
            g.drawImage(mapImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            setBackground(Color.GRAY);
        }

        // Draw each tank and their bullets
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
