import javax.swing.*;
import java.awt.*;

public class Plane extends JLabel implements Runnable {
    private int steeps;
    private boolean terminate = true;

    public Plane(String url, int steeps) {
        super(new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH)));
        this.steeps = steeps;
    }

    public int getSteeps() {
        return steeps;
    }

    @Override
    public void run() {
        while (terminate) {
            setLocation(getX() + 1, getY());
            try {
                Thread.sleep(1000 / steeps);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void terminate() {
        terminate = false;
    }
}
