//Kenny Xiong (5317957)
//Dennis Xiong (5331544)

public class MatrixEntry { //MatrixEntry Class
    private int row; //instants variable for row
    private int col; //instants variable for col
    private int data;//instants variable for data
    private MatrixEntry nextRow; //instants matrix entry for row
    private MatrixEntry nextColumn; ////instants matrix entry for column

    public MatrixEntry(int row, int col, int data){ //Constructor for matrix entry
        this.row = row;
        this.col = col;
        this.data = data;
    }
    public int getColumn(){ //Gets number of columns of a matrix entry
        return col;
    }
    public void setColumn(int col){ //Sets number of columns of a matrix entry
        this.col = col;
    }
    public int getRow(){ //Gets number of rows of a matrix entry
        return row;
    }
    public void setRow(int row){ //Sets number of rows of a matrix entry
        this.row = row;
    }
    public int getData(){ //Gets data or element of a matrix entry
        return data;
    }
    public void setData(int data){ //Gets number of rows of a matrix entry
        this.data = data;
    }
    public MatrixEntry getNextRow(){ //Get the next row of the matrix entry
        return nextRow;
    }
    public void setNextRow(MatrixEntry el){ //Add new matrix entry to row
        nextRow = el;
    }
    public MatrixEntry getNextCol(){ //Get the next col of the matrix entry
        return nextColumn;
    }
    public void setNextCol(MatrixEntry el){ //Add new matrix entry to col
        nextColumn = el;
    }
}

