//Kenny Xiong (5317957)
//Dennis Xiong (5331544)
import org.junit.*;
import static org.junit.Assert.*;

public class MatrixEntryTest{

    private MatrixEntry MatrixTest = new MatrixEntry(4,5,20);   //make MatrixEntry object
    @Test
    public void testConstructor() { //Testing Constructor
        assertEquals("testrow:",4,MatrixTest.getRow()); //test getRow method ; expected value = 4
        assertEquals("testcolumn:",5,MatrixTest.getColumn());   //test getColumn method ; expected value = 5
        assertEquals("testdata:",20,MatrixTest.getData());  //test getData method ; expected value = 20
    }

    @Test
    public void setColTest() {  //test set and get Column methods
        MatrixTest.setColumn(6);
        int realCol = MatrixTest.getColumn();
        assertEquals(6,realCol);    //expect 6
    }

    @Test
    public void setRowTest() {  //test set and get Row methods
        MatrixTest.setRow(10);
        assertEquals(10,MatrixTest.getRow());   //expect 10
    }

    @Test
    public void setDataTest() { //test set and get Data methods
        MatrixTest.setData(30);
        assertEquals(30,MatrixTest.getData());  //expect 30
    }

    @Test
    public void setNextRowTest() {  //test setNextRow & getNextRow methods
        MatrixEntry MatrixrowTest = new MatrixEntry(3,6,20);    //create a matrixEntry object
        MatrixTest.setNextRow(MatrixrowTest); //MatrixTest object should be the same as MatrixrowTest
        assertEquals(MatrixrowTest,MatrixTest.getNextRow());   //should return the same MatrixEntry object for Row
    }

    @Test
    public void setNextCol() {  //test setNextCol & getNextCol methods
        MatrixEntry MatrixrowTest = new MatrixEntry(4,7,10);    //create a matrixEntry object
        MatrixTest.setNextCol(MatrixrowTest); //MatrixTest object should be the same as MatrixrowTest
        assertEquals(MatrixrowTest,MatrixTest.getNextCol());  //should return same MatrixEntry object for Col
    }
}