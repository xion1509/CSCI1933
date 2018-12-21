//Dennis Xiong 5331544
//Kenny Xiong 5317957


import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Random;
public class BallScreenSaver extends AnimationFrame {
    public Ball b = new Ball(0, 0, 0, Color.green);
    private Color c;
    private int ballnum;                //variables private/public
    public int BORDER = 30;
    public Ball[] ballarray;
    public CollisionLogger heat;
    public static final int bucketwidth = 20;
    public int saveCounter = 0;

    public BallScreenSaver(int w, int h, String n, int balln) {
        super(w, h, n);     //inheritance
        heat = new CollisionLogger(w,h,bucketwidth);
        ballnum = balln;
        setSize(w, h);
        ballarray = new Ball[ballnum];
        ballarray[0] = b;
        Random xpos = new Random();                 //get random positions for green ball
        Random ypos = new Random();
        int xRand = xpos.nextInt(770) + 30;
        int yRand = ypos.nextInt(770) + 89;
        b.setPos(xRand, yRand);
        Random xSpeedR = new Random();          //get random speeds for green ball
        Random ySpeedR = new Random();
        int xRandSpeed = xSpeedR.nextInt(50 + 1 + 50) - 50;
        int yRandSpeed = ySpeedR.nextInt(50 + 1 + 50) - 50;
        b.setSpeedX(xRandSpeed);
        b.setSpeedY(yRandSpeed);
        b.setRadius(18);
        for (int i = 1; i < ballnum; i++) {
            Random xpo = new Random();
            Random ypo = new Random();
            int xRan = xpo.nextInt(770) + 30;
            int yRan = ypo.nextInt(770) + 89;
            ballarray[i] = new Ball(xRan, yRan, 18, Color.red); //RANDOM x and y
            Random xSpeedR1 = new Random();
            Random ySpeedR1 = new Random();
            int xRandSpeed1 = xSpeedR.nextInt(50 + 1 + 50) - 50;
            int yRandSpeed1 = ySpeedR.nextInt(50 + 1 + 50) - 50;    //Setting random speeds for red balls
            ballarray[i].setSpeedX(xRandSpeed1);
            ballarray[i].setSpeedY(yRandSpeed1);
        }
    }

    public void action() {
        for (int i = 0; i < ballnum; i++) {
            if ((ballarray[i].getXPos() + ballarray[i].getRadius()) <= 50 || (ballarray[i].getXPos() + ballarray[i].getRadius()) >= 770 || (ballarray[i].getYPos() + ballarray[i].getRadius()) <= 100 || (ballarray[i].getYPos() + ballarray[i].getRadius()) >= 770) {
                ballarray[i].setSpeedX(ballarray[i].getSpeedX() * -1);
                ballarray[i].setSpeedY(ballarray[i].getSpeedY() * -1);  //set speed
            }

            for (int j = 0; j < ballnum; j++) {
                if (ballarray[i].intersect(ballarray[j]) && ballarray[i] != ballarray[j]) { //if intersect, ball collide then heat collect data into array
                    ballarray[i].collide(ballarray[j]);
                    heat.collide(ballarray[i], ballarray[j]);
                    if (ballarray[i].getColor() == Color.green || ballarray[j].getColor() == Color.green) { //Change color
                        ballarray[j].setColor(Color.green);
                        ballarray[i].setColor(Color.green);
                    }
                }
            }
            ballarray[i].setPos(ballarray[i].getXPos() + ballarray[i].getSpeedX() / getFPS(), ballarray[i].getYPos() + ballarray[i].getSpeedY() / getFPS());
        }
    }

    public void draw(Graphics g) {      //draw grid and border
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.red);
        g.drawRect(BORDER, BORDER, getWidth() - BORDER * 2, getHeight() - 30 * 2);
        for (int i = 0; i < ballnum; i++) {
            g.setColor(ballarray[i].getColor());
            g.fillOval((int) ballarray[i].getXPos(), (int) ballarray[i].getYPos(), (int) ballarray[i].getRadius(), (int) ballarray[i].getRadius());
        }
    }

    public void processKeyEvent(KeyEvent eventobject) {
        int keyCode = eventobject.getKeyCode();
        if (keyCode == KeyEvent.VK_RIGHT) {
            for (int j = 0; j < ballnum; j++) {
                ballarray[j].setSpeedX(ballarray[j].getSpeedX() + (ballarray[j].getSpeedX() * .10));        //Press right arrow to increase balls speed
                ballarray[j].setSpeedY(ballarray[j].getSpeedY() + (ballarray[j].getSpeedY() * .10));
            }
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            for (int j = 0; j < ballnum; j++) {
                ballarray[j].setSpeedX(ballarray[j].getSpeedX() + (ballarray[j].getSpeedX() * -0.10));      //Press left arrow to decrease balls speed
                ballarray[j].setSpeedY(ballarray[j].getSpeedY() + (ballarray[j].getSpeedY() * -0.10));
            }
        }
        if (keyCode == KeyEvent.VK_P) {
            EasyBufferedImage image = EasyBufferedImage.createImage(heat.getNormalizedHeatMap());       //Draw heat map
            try {
                image.save("heatmap" + saveCounter + ".png", EasyBufferedImage.PNG);
                saveCounter++;
            } catch (IOException exc) {
                exc.printStackTrace();
                System.exit(1);
            }
        }
    }
        public static void main(String[] args){
            BallScreenSaver screen = new BallScreenSaver(800, 800, "ball", 20); //BallScreenSaver class object
            screen.start();
        }
}
