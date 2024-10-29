import javax.swing.*;

public class TankGame extends JFrame {
    public TankGame() {
        setTitle("TANK BTSB");
        setSize(1366, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Battlefield battlefield = new Battlefield();
        add(battlefield);
        setVisible(true);
    }
    

    public static void main(String[] args) {
        new TankGame();
    }
}
