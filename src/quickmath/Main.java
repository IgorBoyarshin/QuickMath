package quickmath;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Igor on 08-Aug-15.
 */
public class Main extends JFrame {
    public Main() {
        init();
    }

    private void init() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane pane = new JScrollPane();
        JTextArea area = new JTextArea();

        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        pane.getViewport().add(area);
        panel.add(pane);

        add(panel);


        setTitle("Quick Math");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                Main app = new Main();
                app.setVisible(true);
            }
        });
    }
}
