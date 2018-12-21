//Dennis Xiong 5331544
//Kenny Xiong 5317957

import java.awt.*;
public class Ball extends Circle {
    private double xspeed = 0;
    private double yspeed = 0;


    public Ball(double x, double y, double r, Color c){   //Ball Constructor
        super(x,y,r);   //Takes values from circle.java
        setColor(c);
    }
    public void setSpeedX(double xs){       //Set speed for x direction
        xspeed = xs;
    }
    public void setSpeedY(double ys){       //Set speed for y direction
        yspeed = ys;
    }
    public double getSpeedX(){              //Get speed for x direction
        return xspeed;
    }
    public double getSpeedY(){              //Get speed for y direction
        return yspeed;
    }
    public void updatePosition(double units){
        x += (xspeed*units);                //Updates X position for ball
        y += (yspeed*units);                //Updates Y position for ball
    }
    public boolean intersect(Ball newball){  //
        double distance = Math.sqrt(Math.pow(x-newball.getXPos(),2)+Math.pow(y-newball.getYPos(),2)); //Calculates distance between the centers of two balls
        double distanceaway = distance - (r + newball.getRadius()); //Calculates distance between two balls closest edge.
        if(distanceaway <= 0){
            return true;    //Returns true if both balls touches or intersect with each other.
        }else{
            return false;
        }
    }
    public void collide(Ball newball){
        if(intersect(newball)){     //If statement for when two ball intersect
            double distance = Math.sqrt(Math.pow(x-newball.getXPos(),2)+Math.pow(y-newball.getYPos(),2)); //calculates distance

            double coldistancex = (x - newball.getXPos())/distance; //calculates unit vectors
            double coldistancey = (y - newball.getYPos())/distance;

            double newColVelocity1=(xspeed*coldistancex)+(yspeed*coldistancey); //calculates original ball 1 velocity for x direction
            double newnormVelocity1=-1*(xspeed*coldistancey)+(yspeed*coldistancex); //calculates original ball 1 velocity for y direction

            double newColVelocity2=(newball.getSpeedX()*coldistancex)+(newball.getSpeedY()*coldistancey); //calculates original ball 2 velocity for x direction
            double newnormVelocity2=-1*(newball.getSpeedX()*coldistancey)+(newball.getSpeedY()*coldistancex); //calculates original ball 2 velocity for y direction


            double finalXVelocity1 = (newColVelocity2 * coldistancex)-(newnormVelocity1 * coldistancey); //calculates original ball 1 final velocities
            double finalYVelocity1 = (newColVelocity2 * coldistancey)+(newnormVelocity1 *coldistancex);


            double finalXVelocity2 = (newColVelocity1 * coldistancex)-(newnormVelocity2 * coldistancey); //calculates original ball 2 final velocities
            double finalYVelocity2 = (newColVelocity1 * coldistancey)+(newnormVelocity2 *coldistancex);

            xspeed = finalXVelocity1;       //Setting final velocities for X and Y direction for both balls.
            yspeed = finalYVelocity1;
            newball.setSpeedX(finalXVelocity2);
            newball.setSpeedY(finalYVelocity2);
        }
    }
}