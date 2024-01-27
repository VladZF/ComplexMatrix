public class Matrix {
    private Complex[][] matrix;

    private int rowsCount = 0, columnsCount = 0;

    public Matrix(int RowsCount, int columnsCount) {
        if (RowsCount <= 0 || columnsCount <= 0)
            throw new IllegalArgumentException("Number of Rows/Columns must be positive number");
        this.rowsCount = RowsCount;
        this.columnsCount = columnsCount;
        matrix = new Complex[RowsCount][columnsCount];
        for (int i = 0; i < RowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                matrix[i][j] = new Complex();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < rowsCount; i++) {
            info.append("[");
            for (int j = 0; j < columnsCount; j++) {
                info.append(matrix[i][j].toString()).append(", ");
            }
            info.append("\b\b]\n");
        }
        return info.toString();
    }

    public void set(int row, int column, Complex value) {
        matrix[row][column] = new Complex(value);
    }

    public Complex get(int row, int column) {
        return new Complex(matrix[row][column]);
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public int getColumnsCount() {
        return columnsCount;
    }

    public Matrix add(Matrix other) {
        if (this.rowsCount != other.rowsCount || this.columnsCount != other.columnsCount) {
            throw new IllegalArgumentException("Sizes are different");
        }
        Matrix newMatrix = new Matrix(rowsCount, columnsCount);
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                newMatrix.matrix[i][j] = matrix[i][j].add(other.matrix[i][j]);
            }
        }
        return newMatrix;
    }

    public Matrix subtract(Matrix other) {
        if (this.rowsCount != other.rowsCount || this.columnsCount != other.columnsCount)
            throw new IllegalArgumentException("Sizes are different");
        Matrix newMatrix = new Matrix(rowsCount, columnsCount);
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                newMatrix.matrix[i][j] = matrix[i][j].subtract(other.matrix[i][j]);
            }
        }
        return newMatrix;
    }

    public Matrix multiplyWithNumber(double lambda) {
        Matrix newMatrix = new Matrix(rowsCount, columnsCount);
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                newMatrix.matrix[i][j] = matrix[i][j].setScale(lambda);
            }
        }
        return newMatrix;
    }

    public Matrix multiply(Matrix other) {
        if (columnsCount != other.rowsCount)
            throw new IllegalArgumentException("columns count in first matrix and rows count in second are different");
        Matrix newMatrix = new Matrix(rowsCount, other.columnsCount);
        for (int i = 0; i < newMatrix.rowsCount; i++) {
            for (int j = 0; j < newMatrix.columnsCount; j++) {
                for (int k = 0; k < columnsCount; k++) {
                    Complex z = matrix[i][k].multiply(other.matrix[k][j]);
                    newMatrix.matrix[i][j] = newMatrix.matrix[i][j].add(z);
                }
            }
        }
        return newMatrix;
    }


    private static Matrix getMinorMatrix(Matrix matrix, int column) {
        Matrix minor = new Matrix(matrix.rowsCount - 1, matrix.columnsCount - 1);
        int counter = 0;
        for (int i = 1; i < matrix.rowsCount; i++) {
            for (int j = 0; j < matrix.columnsCount; j++) {
                if (j == column) {
                    continue;
                }
                int newI = counter / minor.rowsCount;
                int newJ = counter % minor.rowsCount;
                minor.matrix[newI][newJ] = new Complex(matrix.matrix[i][j]);
                counter++;
            }
        }
        return minor;
    }
    private static Complex determinant_helper(Matrix matrix) {
        if (matrix.rowsCount == 1)
            return matrix.matrix[0][0];
        Complex result = new Complex();
        for (int i = 0; i < matrix.rowsCount; i++) {
            Matrix minor = getMinorMatrix(matrix, i);
            result = result.add(matrix.matrix[0][i]
                    .setScale(Math.pow(-1, i + 2))
                    .multiply(determinant_helper(minor)));
        }
        return result;
    }

    public Matrix transpose() {
        Matrix T = new Matrix(columnsCount, rowsCount);
        for (int i = 0; i < columnsCount; i++) {
            for (int j = 0; j < rowsCount; j++) {
                T.matrix[i][j] = new Complex(matrix[j][i]);
            }
        }
        return T;
    }

    public Complex determinant() {
        if (columnsCount != rowsCount)
            throw new ClassFormatError("Matrix is not square. Impossible to get determinant");
        return determinant_helper(this);
    }
}
