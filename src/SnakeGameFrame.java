import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SnakeGameFrame extends JFrame {

     public SnakeGameFrame() {
        this.add(new SnakeGamePanel());
        this.setTitle("SNAKE");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
         this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }


}
