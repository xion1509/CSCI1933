//Dennis Xiong 5331544
//Kenny Xiong 5317957
import java.awt.*;
import java.util.Random;
import java.util.Scanner;
public class fractalshape {

    //Triangle fractal Recursion and calculate Area

    public static double trifractal(Canvas canvas, double totalarea, double x, double y, double w, double h, double n) {
        if (n == 0) {
            return 0;
        } else {
            Triangle tri_left = new Triangle(x - w / 2, y, w / 2, h / 2);   //create triangle on left position of base triangle
            Triangle tri_right = new Triangle(x + w, y, w / 2, h / 2);      //create triangle on right
            Triangle tri_top = new Triangle(x + (w / 4), y - h, w / 2, h / 2);  //create triangle on top

            Random randcolor = new Random();    //Get random colors between (red,green,blue)
            float red = randcolor.nextFloat();
            float green = randcolor.nextFloat();
            float blue = randcolor.nextFloat();
            Color c = new Color(red, green, blue);
            tri_left.setColor(c);       //Set shape color
            tri_right.setColor(c);
            tri_top.setColor(c);

            canvas.drawShape(tri_left); //draw triangles 3 times
            canvas.drawShape(tri_right);
            canvas.drawShape(tri_top);

            totalarea = tri_left.calculateArea() + tri_right.calculateArea() + tri_top.calculateArea(); //calculating total area

            double a = trifractal(canvas, totalarea, x - w / 2, y, w / 2, h / 2, n - 1);    //decreasing size of triangles and positions
            double b = trifractal(canvas, totalarea, x + w, y, w / 2, h / 2, n - 1);
            double d = trifractal(canvas, totalarea, x + (w / 4), y - h, w / 2, h / 2, n - 1);
            return totalarea + (a + b + d); //Recursive call
        }
    }

    //Circle fractal Recursion. returns area.

    public static double Circfractal(Canvas canvas, double totalarea, double x, double y, double r, double n) {
        if (n == 0) {
            return 0;
        } else {
            Circle circ_left = new Circle(x - (r * 1.5), y, r / 2);       //Create 4 circles objects
            Circle circ_right = new Circle(x + (r * 1.5), y, r / 2);
            Circle circ_top = new Circle(x, y - (r * 1.5), r / 2);
            Circle circ_bottown = new Circle(x, y + (1.5 * r), r / 2);

            Random randcolor = new Random();    //Generate random colors
            float red = randcolor.nextFloat();
            float green = randcolor.nextFloat();
            float blue = randcolor.nextFloat();
            Color c = new Color(red, green, blue);

            circ_bottown.setColor(c);   //Set Colors
            circ_left.setColor(c);
            circ_right.setColor(c);
            circ_top.setColor(c);

            canvas.drawShape(circ_bottown); //Draw circles
            canvas.drawShape(circ_left);
            canvas.drawShape(circ_right);
            canvas.drawShape(circ_top);

            totalarea = circ_bottown.calculateArea() + circ_left.calculateArea() + circ_right.calculateArea() + circ_top.calculateArea();   //Recursive call
            return totalarea + Circfractal(canvas, totalarea, x - (r * 1.5), y, r / 2, n - 1) + Circfractal(canvas, totalarea, x + (r * 1.5), y, r / 2, n - 1) + Circfractal(canvas, totalarea, x, y + (r * 1.5), r / 2, n - 1) + Circfractal(canvas, totalarea, x, y - (r * 1.5), r / 2, n - 1);

        }
    }


    public static double Rectfractal(Canvas canvas, double totalarea, double x, double y, double w, double h, double n) {
        if (n == (0)) {
            return 0;
        } else {
            Rectangle rect_left = new Rectangle(x - (w / 2), y + (h / 4), w / 2, h / 2);    //create 4 rectangle objects
            Rectangle rect_right = new Rectangle(x + w, y + (h / 4), w / 2, h / 2);
            Rectangle rect_top = new Rectangle(x + (w / 4), y - (h / 2), w / 2, h / 2);
            Rectangle rect_bottown = new Rectangle(x + (w / 4), y + h, w / 2, h / 2);

            Random randcolor = new Random();    //generate random colors
            float red = randcolor.nextFloat();
            float green = randcolor.nextFloat();
            float blue = randcolor.nextFloat();
            Color c = new Color(red, green, blue);

            rect_bottown.setColor(c);   //set Colors
            rect_left.setColor(c);
            rect_right.setColor(c);
            rect_top.setColor(c);

            canvas.drawShape(rect_bottown); //Draw rectangles
            canvas.drawShape(rect_left);
            canvas.drawShape(rect_right);
            canvas.drawShape(rect_top);

            totalarea = rect_bottown.calculateArea() + rect_left.calculateArea() + rect_right.calculateArea() + rect_top.calculateArea();   //Recursive call
            return totalarea + Rectfractal(canvas, totalarea, x - (w / 2), y + (h / 4), w / 2, h / 2, n - 1) + Rectfractal(canvas, totalarea, x + w, y + (h / 4), w / 2, h / 2, n - 1) + Rectfractal(canvas, totalarea, x + (w / 4), y + h, w / 2, h / 2, n - 1) + Rectfractal(canvas, totalarea, x + (w / 4), y - (h / 2), w / 2, h / 2, n - 1);

        }
    }

    public static void main(String[] args) {
        boolean keepgoing = true;
//        Canvas canvas = new Canvas(2000, 2000);
        while (keepgoing) {
            Scanner s = new Scanner(System.in);
            System.out.println("Choices: circle,triangle,rectangle: ");
            String shape = s.next();
            Random randcolor = new Random();                              //Create canvas and generate random colors
            float red = randcolor.nextFloat();
            float green = randcolor.nextFloat();
            float blue = randcolor.nextFloat();
            Color col = new Color(red, green, blue);
            Canvas canvas = new Canvas(2000, 2000);

            if (shape.equals("triangle")) {
                Triangle tri1 = new Triangle(400, 1000, 300, 300);         //call recursive function to draw fractal
                tri1.setColor(col);
                canvas.drawShape(tri1); //draw base triangle
                double bigArea = tri1.calculateArea();
                double totalarea = trifractal(canvas, 0, 400, 1000, 300, 300, 7);
                String area = String.valueOf(totalarea + bigArea);  //calculate area and remember to add the base area drawn in main.
                System.out.println("Total Area: " + area);
            } else if (shape.equals("circle")) {
                Circle cir = new Circle(600, 300, 100);
                cir.setColor(col);
                canvas.drawShape(cir);  //draw base circle
                double totalarea = Circfractal(canvas, 0, 600, 300, 100, 7) + 31415.92;  //call recursive function
                System.out.println("Area:" + totalarea);
            } else if (shape.equals("rectangle")) {
                Rectangle rect = new Rectangle(400, 300, 150, 200);
                rect.setColor(col);
                canvas.drawShape(rect); //draw base rectangle
                double bigArea = rect.calculateArea();
                double totalarea = Rectfractal(canvas, 0, 400, 300, 150, 200, 7);   //call recursive function
                String area = String.valueOf(totalarea + bigArea);
                System.out.println("Total Area: " + area);
            }
            Scanner s1 = new Scanner(System.in);
            System.out.println("Draw more? Enter 'y' or 'n'");
            String answer = s1.next();
            if (answer.equals("n")) {
                keepgoing = false;
                System.out.println("Thanks for drawing! Have a good one!");
            }
        }
    }
}