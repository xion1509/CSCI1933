//Dennis Xiong 5331544
//Kenny Xiong 5317957

public class CollisionLogger {
    /* add data members here */
    private int swidth;
    private int sheight;
    private int bucketwidth;
    private int[][] array2D;

    public CollisionLogger(int screenWidth, int screenHeight, int bucketWidth) {    //Constructor: height,width, bucketwidth, and 2D array.
        swidth = screenWidth;
        sheight = screenHeight;
        bucketwidth = bucketWidth;
        array2D = new int[((int)(sheight*1.0 / bucketwidth))][(int)(swidth * 1.0 / bucketwidth)];
    }

    /**
     * This method records the collision event between two balls. Specifically, in increments the bucket corresponding to
     * their x and y positions to reflect that a collision occurred in that bucket.
     */
    public void collide(Ball one, Ball two) {                           //Calculate two balls point of collision and increment 2D array
        double xcollide = (one.getXPos() + two.getXPos()) / 2;
        double ycollide = (one.getYPos() + two.getYPos()) / 2;
        int xcom = Math.max(0, Math.min((int) xcollide, this.sheight - 1)) / this.bucketwidth;
        int ycom= Math.max(0, Math.min((int) ycollide, this.sheight - 1)) / this.bucketwidth;
        array2D[ycom][xcom] += 1;
        /* update data members to reflect the collision event between ball "one" and ball "two" */

    }

    /**
     * Returns the heat map scaled such that the bucket with the largest number of collisions has value 255,
     * and buckets without any collisions have value 0.
     */
    public int[][] getNormalizedHeatMap() {
        //normalizedHeatMap take in scale values of collisions
        int[][] normalizedHeatMap = new int[((int)(sheight*1.0 / bucketwidth))][(int)(swidth * 1.0 / bucketwidth)]; //NOTE-- these dimensions need to be updated properly!!
        /* implement your code to produce a normalized heat map of type int[][] here */
        int max = 0;
//        float slope = 0;
        int scaleValue = 0;
        for (int i = 0; i < array2D.length; i++) {
            for (int j = 0; j < array2D[0].length; j++) {       //Find the max collisions
                if (array2D[i][j] > max) {
                    max = array2D[i][j];
                }
            }
        }
//        slope = 255.0f / max;
        for (int k = 0; k < array2D.length; k++) {
            for (int c = 0; c < array2D[0].length; c++) {
                scaleValue = (int) (255 * ((double)array2D[k][c]/max)); //Update normalizedHeatMap with scale values
                normalizedHeatMap[k][c] = scaleValue;
            }
        }
        return normalizedHeatMap;
    }
}