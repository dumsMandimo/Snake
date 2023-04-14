
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;



public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;

    final int x [] = new int[GAME_UNITS];
    final int y [] = new int[GAME_UNITS];
    int bodyParts = 6;
    int appleEat;
    int appleX; //random position of apple
    int appleY;
    char dir = 'R'; //right
    boolean running = false;
    Timer timer;
    Random random;





    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }
    public void startGame(){
        newApple(); //new apple on screen
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g){
        if(running){
            for(int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE;i++){
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH,i*UNIT_SIZE);
            }
            //apple drawing
            g.setColor(Color.green);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            //head and body of snake
    
            for(int i = 0; i <bodyParts; i++){
                if(i == 0){
                    g.setColor(Color.red);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else{
                    g.setColor(new Color(255,204,203));
                    g.fillRect(x[i],y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

        }
        else{
            gameOver(g);
        }
        
    }
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;

    }
    public void move(){

        for(int i = bodyParts; i > 0; i--){ //shifting coordinates of array by 1

            x[i] = x[i-1]; 
            y[i] = y[i-1];
        }
        switch(dir){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                 x[0] = x[0] - UNIT_SIZE;
                 break;
            case'R':
                x[0] = x[0] + UNIT_SIZE;
                break;

        }

    }

    public void checkApple(){
        //examine coordinates of snake and apple
        if((x[0] == appleX) && (y[0] == appleY)){
            bodyParts++;
            appleEat++;
            newApple();
        }

    }

    public void checkCollis(){
        //check if head collides with body
        for(int i = bodyParts; i > 0; i--){
            if((x[0] == x[i]) && (y[0]==y[i])){ //head collided with body
                running = false;
            }
        }
        //check if head touches left border
        if (x[0] < 0){
            running = false;

        }
        //if head touches right border
        if(x[0]> SCREEN_WIDTH){
            running = false;
        }
        //check if head touches top border
        if(y[0] < 0 ){
            running = false;
        }
        //checl if head touches botton border
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }
        if(!running){
            timer.stop();

        }



    }
    public void gameOver(Graphics g){

        //GameOver text
        g.setColor(Color.red);
        g.setFont(new Font("Times new Roman", Font.BOLD, 75));
        FontMetrics met = getFontMetrics(g.getFont()); //set font to middle
        g.drawString("Game Over",(SCREEN_WIDTH - met.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2 );

    }


    public void actionPerformed(ActionEvent e){

        if(running){
            move();
            checkApple();
            checkCollis();

        }
        repaint();

    }
    //controls
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(dir != 'R'){
                        dir = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir != 'L'){
                        dir = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(dir != 'D'){
                        dir = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(dir != 'U'){
                        dir = 'D';
                    }
                    break;

            }

        }
    }
    
}
