import javax.swing.JFrame;

import javax.swing.JFrame;

public class GameFrame extends JFrame{

    //constructors
    GameFrame(){

        GamePanel panel = new GamePanel();
        this.add(panel);

        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null); //middle of computer screen

    }
    
}
