//Kenny Xiong (5317957)
//Dennis Xiong (5331544)

// Part 5
// (a) In an N x N matrix where m is non-zero elements, there are 3*m memory for SparseIntMatrix implementation.
//     There are 3 memory unit in each MatrixEntry(each store an m) because row,column, and data represent one memory unit.
//     Hence, 3 * m is the total memory usage.
//     A 2D array will require N^2 of memory. Since, the size is N x N, the 2D array will take in all the elements including 0.
//     The "empty" spaces will be wasted memory.

// (b) N=100,000 m=1,000,000 . The SparseIntMatrix implementation is more space-efficient because 3 * 1,000,000 = 3e6 memory units.
//     If we compare that to N^2(2D array implementation), it is 1e10 memory units which is greater than 3e6. Difference of 9.997e9.
//     N^2 = 3m so if we plug in N and solve for m. m = 3.3E9, so estimating when m > 3.3E9, N^2 is more efficient. In a case where N=M,
//     N^2 is more efficient when N and M < 4.

import java.io.*;
import java.util.*;

public class SparseIntMatrix { // Public class for Sparse Matrix
    private int numRows;    //Instants the number of rows
    private int numCols;    //Instants the number of columns
    private MatrixEntry[] ColArray;     //Instants the 1-d array for column
    private MatrixEntry[] RowArray;     //Instants the 1-d array for rows
    public SparseIntMatrix(int numRows, int numCols) { //Constructor for the Sparse matrix
        this.numRows = numRows;     //Constructs number of rows
        this.numCols = numCols;     //Constructs number of columns
        ColArray = new MatrixEntry[numCols]; //Constructs array for column
        RowArray = new MatrixEntry[numRows]; //Constructs array for row
    }

    public SparseIntMatrix(int numRows, int numCols, String inputFile) { //Constructor for the Sparse matrix, but with input files
        this.numRows = numRows;     //Constructs number of rows
        this.numCols = numCols;     //Constructs number of columns
        ColArray = new MatrixEntry[numCols]; //Constructs array for column
        RowArray = new MatrixEntry[numRows]; //Constructs array for row
        //File filedata = new File(inputFile); //Instants file variable
        try{
        Scanner s = new Scanner(new File(inputFile)); //Instants scanner s
        while (s.hasNextLine()) { //Uses scanner s to read each line of the input file
            String Line = s.nextLine();
            String[] splitLine = Line.split(",");
            //System.out.println(Arrays.toString(splitLine));
            int row = Integer.parseInt((splitLine[0]));
            int col = Integer.parseInt((splitLine[1]));
            int data = Integer.parseInt((splitLine[2]));
            if (data != 0) {    //If data is not zero, create a new matrix entry and set the element for it.
                setElement(row, col, data);
            }
        }
        }
        catch(FileNotFoundException e){
            System.out.println("input file not found");
        }
    }

    public int getElement(int row, int col) { //Method for getting data/element
        MatrixEntry Matrixtemp = RowArray[row]; //Instants a matrix entry for a row.
        boolean keepgoing = true;
        int DataInfo = 0;
        if (RowArray[row] == null) //Returns zero if row is null
            return DataInfo;
        while (Matrixtemp != null) { //While loop when row is not null
            if (Matrixtemp.getColumn() == col) { //if the element's position in row matches with column position, then return the data.
                DataInfo = Matrixtemp.getData();
                break;
            } else {
                Matrixtemp = Matrixtemp.getNextCol(); //if element's position in row doesn't match column's position, then move to the next element.
            }
        }

        return DataInfo;
    }

    public boolean setElement(int row, int col, int data) { // Method for adding elements into the column and row arrays

        if (row >= numRows || row < 0 || col >= numCols || col < 0) { //If statement for boundaries
            return false;
        }
        MatrixEntry matrixCurrent = RowArray[row]; //instants pre-temporary row position
        MatrixEntry matrixobject = new MatrixEntry(row,col,data); // instants the wanted matrix entry
        boolean keepgoing = true;
        if(matrixCurrent == null || matrixCurrent.getColumn() == col) { //If pre-temp. is null, then just add wanted matrix entry
            RowArray[row] = matrixobject;
            keepgoing = false;
        }
        MatrixEntry nextMatrixtemp = RowArray[row].getNextCol(); //instants after temporary row position
        while (keepgoing) {
            if (matrixCurrent.getColumn() < col && nextMatrixtemp == null) { //if col is greater than pre-temp, but after-temp is null, then just insert the wanted matrix entry
                matrixCurrent.setNextCol(matrixobject);
                break;
            }
            if (matrixCurrent.getColumn() < col && col < nextMatrixtemp.getColumn()) { //if col is greater than pre-temp, but less than after-temp, then insert wanted entry between the pre and after temp.
                matrixCurrent.setNextCol(matrixobject);
                matrixobject.setNextCol(nextMatrixtemp);
                break;
            }
            if (matrixCurrent.getColumn() < col && col == nextMatrixtemp.getColumn()) { //if col is greater than pre-temp, but equal to after-temp, then replace after-temp with wanted entry.
                matrixCurrent.setNextCol(matrixobject);
                break;
            } else { //If none of the conditions above is match, then move to the next element in the row.
                matrixCurrent = nextMatrixtemp;
                nextMatrixtemp = nextMatrixtemp.getNextCol();
            }
        }
        MatrixEntry MatrixtempCol = ColArray[col]; //Instants pre-temp. for column
        boolean going = true;
        if(MatrixtempCol == null || MatrixtempCol.getRow() == row) { //If pre-temp. is null, then just add wanted matrix entry
            ColArray[col] = matrixobject;
            going = false;
        }
        MatrixEntry nextMatrixCol = ColArray[col].getNextRow();//Instants after-temp. for column
        while (going) {
            if (MatrixtempCol.getRow() < row || nextMatrixCol == null) { //if row is greater than pre-temp, but after-temp is null, then just insert the wanted matrix entry
                MatrixtempCol.setNextRow(matrixobject);
                break;
            }
            if (MatrixtempCol.getRow() < row && row < (nextMatrixCol.getRow())) {//if row is greater than pre-temp, but less than after-temp is null, then just insert the wanted matrix entry in between
                MatrixtempCol.setNextRow(matrixobject);
                matrixobject.setNextRow(nextMatrixCol);
                break;
            }
            if (MatrixtempCol.getRow() < row && row == nextMatrixCol.getRow()) {//if row is greater than pre-temp, but row=after-temp, then just replace after-temp with wanted matrix entry
                MatrixtempCol.setNextRow(matrixobject);
                break;
            } else { //if above conditions don't match then move to the next element in the column
                MatrixtempCol = nextMatrixtemp;
                nextMatrixCol = nextMatrixCol.getNextCol();
            }
        }
        return true;
    }


    public boolean removeElement(int row, int col, int data) { //Method to remove a matrix entry from both 1-d arrays
        if (row >= numRows || row < 0 || col > numCols || col < 0) { //if out of bounds for either row or column, then return false
            return false;
        }
        MatrixEntry Matrixtemp = RowArray[row]; //Returns false if there no elements in a row.
        if (Matrixtemp == null) {
            return false;
        }
        boolean keepgoing = true;
        if (Matrixtemp.getColumn() == col) {    //Checks if the first column of rowArray[row] is the same as col, if yes, then remove object.
            RowArray[row]=null;
            keepgoing = false; //Prevent going in the while loop
        }
        while (keepgoing) {
            if(Matrixtemp.getNextCol() == null){   //if there are no matrixobject after matrixtemp then remove fails. Hence, return false.
                return false;
            }
            if ((Matrixtemp.getNextCol()).getColumn() == col) { //if the matrixobject matrixtemp points to has the column that we want to remove, if yes, then remove it.
                Matrixtemp.setNextCol(null);    //Setting the next matrixobject to null will remove it
                break;
            } else {
                Matrixtemp = Matrixtemp.getNextCol();   //Set matrixtemp to the matrixobject it is pointing to.
            }
        }
        MatrixEntry Matrixtempcol = ColArray[col];  //assign a temp MatrixEntry to ColArray[col]
        boolean going = false;
        if (Matrixtempcol.getRow() == row) {    //checks to see if ColArray[col] has the row we want to remove
             ColArray[col] = null;
            going = false; //prevent from going into while loop
        }
            while (going) {
                if(Matrixtempcol.getNextRow() == null){ //If matrixtempCol does point to a matrixobject, then removal fails. Hence return false.
                    return false;
                }
                if ((Matrixtempcol.getNextRow()).getRow() == row) { //Checks if the next Matrixobject has the row we want to remove, if yes, then remove it.
                    Matrixtempcol.setNextRow(null);
                    break;
                } else {
                    Matrixtempcol = Matrixtempcol.getNextRow(); //set matrixtempcol to the matrixobject it is pointing at.
                }
            }
            return true; //if none of the if statements returns false then remove has to be successful.
    }

    public int getNumCols() {   //get number of columns
        return numCols;
    }

    public int getNumRows() { //get number of rows
        return numRows;
    }

    public boolean plus(SparseIntMatrix otherMat) {
        if(numRows != otherMat.getNumRows() || numCols != otherMat.getNumCols()){   //checks if numRows and numCols are the same as the other Matrix passed in
            return false;
        }
        for (int i = 0; i < numRows; i++) { //Loop through each rows in numRows
            MatrixEntry tempR = RowArray[i]; //Set a temp MatrixEntry for RowArray[i]; This will eventually go through all the rows.
            int j=0;
            while(tempR!=null) {        //We need this so that when MatrixEntry points to null, then we know we're done. We can then iterate the next row.
                tempR.setData(tempR.getData() + otherMat.getElement(i, j));  //add both data from both matrices, then set new Data
                if (tempR.getData()<=0){        //Remove the matrixobject tempR if the data is less than or equal to 0
                    removeElement(i,j,tempR.getData());
                }
                tempR = tempR.getNextCol(); //Set tempR to the next matrixobject its pointing to in order to add all the matrixobjects in that row
                j+=1;
            }
        }
        for (int i = 0; i < numCols; i++) {     //Loop through columns in numCols
            MatrixEntry tempC = ColArray[i];    //set temp for ColArray[i]
            int j=0;
            while(tempC!=null){
                tempC.setData(tempC.getData() + otherMat.getElement(i, j)); //Add the data from both matrices, then add data
                if (tempC.getData()<=0){        //if data <= 0, remove tempC matrixobject
                    removeElement(i,j,tempC.getData());
                }
                tempC = tempC.getNextRow(); //set temp matrixobject to the matrixobject it points to
                j+=1;
            }
        }
        return true;
    }

    public boolean minus(SparseIntMatrix otherMat) {
        if (numRows != otherMat.getNumRows() && numCols != otherMat.getNumCols()) {     //checks if numRows and numCols are the same as the other Matrix passed in
            return false;
        }

        for (int i = 0; i < numRows; i++) {     //Loop through each rows in numRows
            MatrixEntry tempR = RowArray[i];    //Set a temp MatrixEntry for RowArray[i]; This will eventually go through all the rows.
            int j = 0;
            while (tempR != null) {             //We need this so that when MatrixEntry points to null, then we know we're done. We can then iterate the next row.
                tempR.setData(tempR.getData() - otherMat.getElement(i, j)); //Subtract data from both matrices, then set new Data
                if (tempR.getData() <= 0) {         //Remove the matrixobject tempR if the data is less than or equal to 0
                    removeElement(i, j, tempR.getData());
                }
                tempR = tempR.getNextCol();     //Set tempR to the next matrixobject its pointing to in order to add all the matrixobjects in that row
                j += 1;
            }
        }
        for (int i = 0; i < numCols; i++) { //Loop through columns in numCols
            MatrixEntry tempC = ColArray[i];    //set temp for ColArray[i]
            int j = 0;
            while (tempC != null) {
                tempC.setData(tempC.getData() - otherMat.getElement(i, j)); //Subtract the data from both matrices, then subtract data
                if (tempC.getData() <= 0) {
                    removeElement(i, j, tempC.getData());       //if data <= 0, remove tempC matrixobject
                }
                tempC = tempC.getNextRow();     //set temp matrixobject to the matrixobject it points to
                j += 1;
            }
        }
        return true;  //if both matrices are the same dimensions, then it has to be true
    }
    public static void main(String[] args) {
        SparseIntMatrix matrix1 = new SparseIntMatrix(800,800,"matrix1_data.txt");
        MatrixViewer.show(matrix1);
        SparseIntMatrix matrix2 = new SparseIntMatrix(800,800,"matrix2_data.txt");
        SparseIntMatrix matrix3 = new SparseIntMatrix(800,800,"matrix3_noise.txt");
        matrix2.minus(matrix3);
        MatrixViewer.show(matrix2);
    }
}
