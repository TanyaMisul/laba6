import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

//  Летящие самолеты с разной скоростью. При нажатии на кнопку
//  «Старт» самолеты начинают лететь. При нажатии на кнопку «Стоп»
//  самолеты исчезают, и отображается дальность полета каждого
//  самолета.

public class Window extends JFrame {
    Plane plane1, plane2;
    private JButton start, stop;
    private JTextArea result;
    private boolean isTimerRunning = true;
    private long millis;

    public Window() throws HeadlessException, IOException {
        super("Plane app current");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(getMaximumSize());
        initElements();
        createEventHandlers();
        setVisible(true);
    }

    private void initElements() {
        // init panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        getContentPane().add(panel);

        // init buttons
        panel.add(start = new JButton("start"));
        panel.add(stop = new JButton("stop"));

        // init result
        panel.add(result = new JTextArea());
        result.setEditable(false);
        result.setVisible(false);

        // init first plane
        plane1 = new Plane("D:/LabWorks/3 курс 5 семестр/plane1.png", 100);
        panel.add(plane1);

        // init second plane
        plane2 = new Plane("D:/LabWorks/3 курс 5 семестр/plane2.png", 150);
        panel.add(plane2);
    }


    private void createEventHandlers() {
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("start");
                new Thread(plane1).start();
                new Thread(plane2).start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (isTimerRunning) {
                            millis += 10;
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException exception) {
                                exception.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plane1.terminate();
                plane2.terminate();
                isTimerRunning = false;
                System.out.println("stop");
                plane1.setVisible(false);
                plane2.setVisible(false);
                result.setText("Time: " + (float) millis / 1000 + " s\n" +
                        "First plane distance: " + plane1.getSteeps() * (float) millis / 1000 + "\n" +
                        "Second plane distance: " + plane2.getSteeps() * (float) millis / 1000);
                result.setVisible(true);
            }
        });
    }

    public static void main(String[] args) throws IOException {
        new Window();
    }
}
