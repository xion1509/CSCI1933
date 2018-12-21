//Dennis Xiong 5331544
//Kenny Xiong 5317957

import java.awt.*;
public class Rectangle {
    private double botXpos;
    private double botYpos;
    private double width;
    private double height;
    private Color color;
    public Rectangle(double Xposition,double Yposition,double widths,double heights){   //Constructor
        botXpos = Xposition;
        botYpos = Yposition;
        width = widths;
        height = heights;
    }
    public double calculatePerimeter(){ //Cal rectangle perimeter
        double Perimeter = (height * 2)+(width * 2);
        return Perimeter;
    }
    public double calculateArea(){  //Cal rectangle Area
        double area = height * width;
        return area;
    }
    public void setColor(Color newcolor){   // set rectangle color
        color = newcolor;
    }
    public void setPos(double newXpos,double newYpos){  //set rectangle positions
        botXpos = newXpos;
        botYpos = newYpos;
    }
    public void setHeight(double newheight){    //set rectangle height
        height = newheight;
    }
    public void setWidth(double newidth){   //set rectangle width
        width = newidth;
    }
    public Color getColor(){    //get rectangle Color
        return color;
    }
    public double getXPos(){  //get rectangle X coordinate position
        return botXpos;
    }
    public double getYPos(){  //get rectangle Y coordinate position
        return botYpos;
    }
    public double getHeight(){ //get rectangle height
        return height;
    }
    public double getWidth(){  //get rectangle width
        return width;
    }
}