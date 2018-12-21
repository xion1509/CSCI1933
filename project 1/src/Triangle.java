//Dennis Xiong 5331544
//Kenny Xiong 5317957

import java.lang.Math;
import java.awt.*;

public class Triangle {

    private double xpos;
    private double ypos;
    private double width;
    private double height;
    private Color color;

    public Triangle(double x, double y, double w, double h){    //Constructor
        xpos=x;
        ypos=y;
        width=w;
        height=h;
    }

    public double calculatePerimeter(){            //Triangle perimeter
        double side = Math.sqrt(Math.pow(width/2,2)+Math.pow(height,2));
        double result = (2 * side) + width;
        return result;
    }
    public double calculateArea(){          //Triangle Area
        double result=(width*height)*0.5;
        return result;
    }
    public void setColor(Color newColor){   //Set Triangle Color
        color=newColor;
    }
    public void setPos(double x, double y){ //Set Triangle position
        xpos=x;
        ypos=y;
    }
    public void setWidth(double w){ //Set Triangle position
        width=w;
    }
    public void setHeight(double h){    //Set Triangle Height
        height=h;
    }
    public Color getColor(){    //Set Triangle Color
        return color;
    }
    public double getXPos(){    //Set Triangle x position
        return xpos;
    }
    public double getYPos(){    //Set Triangle y position
        return ypos;
    }
    public double getHeight(){  //Set Triangle height
        return height;
    }
    public double getWidth(){   //Set Triangle Width
        return width;
    }
}
