//Dennis Xiong 5331544
//Kenny Xiong 5317957
import java.lang.Math;
import java.awt.*;
public class Circle {
    private double xpos = 0;
    private double ypos = 0;
    private double radi = 0;
    private Color color;
    public Circle(double xposition ,double yposition, double radius){ //Constructor
        xpos = xposition;
        ypos = yposition;
        radi = radius;
    }
    public double calculatePerimeter() {    //Perimeter
        double perimeter;
        perimeter = 2 * radi * Math.PI;
        return perimeter;
    }
    public double calculateArea(){      //Area
        double area = (radi * radi)* Math.PI;
        return area;
    }
    public void setColor(Color newColor) { //Set Color
         color=newColor;
    }
    public void setPos(double newXpos, double newYpos){ //Set circle position
         xpos=newXpos;
         ypos=newYpos;
    }
    public void setRadius(double newradius){ //Set Radius
        radi = newradius;
    }
    public Color getColor(){ //Excess circle color
        return color;
    }
    public double getXPos(){  //Excess x coordinate position
        return xpos;
    }
    public double getYPos(){ //Excess y coordinate position
        return ypos;
    }
    public double getRadius(){ //Excess radius
        return radi;
    }
}

