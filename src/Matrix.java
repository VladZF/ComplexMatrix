public class Matrix {
    private Complex[][] matrix;

    private int n = 0, m = 0;

    public Matrix(int n, int m) {
        if (n <= 0 || m <= 0) {
            System.out.println("Number of Rows/Columns must be positive number");
            return;
        }
        this.n = n;
        this.m = m;
        matrix = new Complex[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = new Complex();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < n; i++) {
            info.append("[");
            for (int j = 0; j < m; j++) {
                info.append(matrix[i][j].toString()).append(", ");
            }
            info.append("\b\b]\n");
        }
        return info.toString();
    }

    public void set(int row, int column, Complex value) {
        matrix[row][column] = value;
    }

    public Complex get(int row, int column) {
        return matrix[row][column];
    }

    public int getRowsCount() {
        return n;
    }

    public int getColumnsCount() {
        return m;
    }

    public Matrix add(Matrix other) {
        if (this.n != other.n || this.m != other.m) {
            System.out.println("Sizes are different");
            return null;
        }
        Matrix newMatrix = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                newMatrix.matrix[i][j] = matrix[i][j].add(other.matrix[i][j]);
            }
        }
        return newMatrix;
    }

    public Matrix subtract(Matrix other) {
        if (this.n != other.n || this.m != other.m) {
            System.out.println("Sizes are different");
            return null;
        }
        Matrix newMatrix = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                newMatrix.matrix[i][j] = matrix[i][j].subtract(other.matrix[i][j]);
            }
        }
        return newMatrix;
    }

    public Matrix multiplyWithNumber(double lambda) {
        Matrix newMatrix = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                newMatrix.matrix[i][j] = matrix[i][j].setScale(lambda);
            }
        }
        return newMatrix;
    }

    public Matrix multiply(Matrix other) {
        if (m != other.n) {
            System.out.println("columns count in first matrix and rows count in second are different");
            return null;
        }
        Matrix newMatrix = new Matrix(n, other.m);
        for (int i = 0; i < newMatrix.n; i++) {
            for (int j = 0; j < newMatrix.m; j++) {
                for (int k = 0; k < m; k++) {
                    Complex z = matrix[i][k].multiply(other.matrix[k][j]);
                    newMatrix.matrix[i][j] = newMatrix.matrix[i][j].add(z);
                }
            }
        }
        return newMatrix;
    }


    private static Matrix getMinorMatrix(Matrix matrix, int row, int column) {
        Matrix minor = new Matrix(matrix.n - 1, matrix.m - 1);
        int counter = 0;
        for (int i = 0; i < matrix.n; i++) {
            for (int j = 0; j < matrix.m; j++) {
                if (i == row || j == column) {
                    continue;
                }
                int newI = counter / minor.n;
                int newJ = counter % minor.n;
                minor.matrix[newI][newJ] = matrix.matrix[i][j];
                counter++;
            }
        }
        return minor;
    }
    private static Complex determinant_helper(Matrix matrix) {
        if (matrix.n == 1)
            return matrix.matrix[0][0];
        Complex result = new Complex();
        for (int i = 0; i < matrix.n; i++) {
            Matrix minor = getMinorMatrix(matrix, 0, i);
            result = result.add(matrix.matrix[0][i]
                    .setScale(Math.pow(-1, i + 2))
                    .multiply(determinant_helper(minor)));
        }
        return result;
    }

    public Complex determinant() {
        if (m != n) {
            System.out.println("Matrix is not square. Impossible to get determinant");
            return null;
        }
        return determinant_helper(this);
    }
}
