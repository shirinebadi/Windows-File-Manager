package midterm;

import javax.swing.*;
import java.awt.*;

public class Main {
protected JFrame jFrame;

public void fileManager(){
    jFrame = new JFrame("File Manager Windows");
    jFrame.setSize(600, 400);
    jFrame.setLocationRelativeTo(null);
    jFrame.setMinimumSize(new Dimension(400, 300));
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    FileManage f = new FileManage();
    jFrame.setContentPane(f.gui);
    jFrame.setVisible(true);
}

public static void main(String[] args){
    Main main = new Main();
    main.fileManager();
}
        }