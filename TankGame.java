import javax.swing.*;

public class TankGame extends JFrame {
    public TankGame() {
        setTitle("3-Player Tank Battle");
        setSize(800, 600); // Size of the game window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Battlefield battlefield = new Battlefield();
        add(battlefield);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TankGame();
    }
}
