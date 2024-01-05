import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
public class GamePanel extends JPanel implements ActionListener {
    static final int Screen_width = 600;
    static final int Screen_height = 600;
    static final int unit_size = 25;
    static final int Game_units = (Screen_width * Screen_height)/unit_size;
    static final int Delay = 75;
    final int x[] = new int[Game_units];
    final int y[] = new int[Game_units];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    GamePanel(){
         random = new Random();
        this.setPreferredSize(new Dimension(Screen_width,Screen_height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MykeyAdapter());
        StartGame();
        //StartGame();
    }
    public void StartGame(){
        newApple();
        running = true;
        timer = new Timer(Delay,this);
        timer.start();



    }
    public void paintComponent(Graphics g){
      super.paintComponent(g);
      draw(g);

    }
    public void draw(Graphics g){
        if (running) {
            g.setColor(Color.gray);
            for (int i= 0;i<Screen_height/unit_size;i++ ){
                g.drawLine(i*unit_size,0,i*unit_size,Screen_height);

                g.drawLine(0,i*unit_size,Screen_width,i*unit_size);
            }
            g.setColor(Color.red);
            g.fillOval(appleX,appleY,unit_size,unit_size);

            for (int i = 0; i < bodyParts; i++){
                if(i == 0){
                    g.setColor(Color.green);
                    g.fillRect(x[i],y[i],unit_size,unit_size);
                }
                else{
                    g.setColor(new Color(45,180,0));
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i],y[i],unit_size,unit_size);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("chalkboard",Font.ITALIC,35));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " +applesEaten,(Screen_width - metrics.stringWidth("Score: " +applesEaten))/2, g.getFont().getSize());

            }
        else{
            gameOver(g);
        }
    }

    public void newApple(){
        appleX = random.nextInt((int)(Screen_width/unit_size))*unit_size;
        appleY = random.nextInt((int)(Screen_height/unit_size))*unit_size;
    }
    public void move(){
        for(int i = bodyParts;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch(direction){
            case'U':
                y[0] = y[0] - unit_size;
                break;
            case 'D':
                y[0] = y[0] + unit_size;
                break;
            case 'L':
                x[0] = x[0] - unit_size;
                break;
            case'R':
                x[0] = x[0]+ unit_size;
                break;
        }
    }
    public void checkApple(){
        if((x[0] == appleX)&&(y[0] == appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollisions(){
        //check if head collides with body
        for(int i = bodyParts;i > 0;i--){
            if((x[0] == x[i] )&& (y[0]==y[i])){
                running = false;
            }
        }
        // check if head touches left border
        if(x[0] < 0){
            running = false;
        }
        // check if touch right border
        if(x[0] > Screen_width){
            running = false;
        }
        //check if head touches top border
        if (y[0]< 0){
            running = false;
        }
        if (y[0]> Screen_height){
            running = false;
        }
        if (running == false){
            timer.stop();
        }
    }


    public void gameOver(Graphics g){
        //gameover
        g.setColor(Color.red);
        g.setFont(new Font("chalkboard",Font.ITALIC,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",(Screen_width - metrics.stringWidth("Game Over"))/2, Screen_height/2);
        //score
        g.setColor(Color.red);
        g.setFont(new Font("chalkboard",Font.ITALIC,35));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " +applesEaten,(Screen_width - metrics1.stringWidth("Score: " +applesEaten))/2, g.getFont().getSize());
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    public class MykeyAdapter extends KeyAdapter{
        //@Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if (direction != 'R'){
                        direction = 'L';

                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L'){
                        direction = 'R';

                    }
                    break;

                case KeyEvent.VK_UP:
                    if (direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U'){
                        direction = 'D';

            }
            break;

        }
    }
}}
