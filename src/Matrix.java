public class Matrix {
    private Complex[][] matrix;

    private int n = 0, m = 0;

    public Matrix(int n, int m) {
        if (n <= 0 || m <= 0)
            throw new IllegalArgumentException("Number of Rows/Columns must be positive number");
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
        matrix[row][column] = new Complex(value);
    }

    public Complex get(int row, int column) {
        return new Complex(matrix[row][column]);
    }

    public int getRowsCount() {
        return n;
    }

    public int getColumnsCount() {
        return m;
    }

    public Matrix add(Matrix other) {
        if (this.n != other.n || this.m != other.m) {
            throw new IllegalArgumentException("Sizes are different");
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
        if (this.n != other.n || this.m != other.m)
            throw new IllegalArgumentException("Sizes are different");
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
        if (m != other.n)
            throw new IllegalArgumentException("columns count in first matrix and rows count in second are different");
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


    private static Matrix getMinorMatrix(Matrix matrix, int column) {
        Matrix minor = new Matrix(matrix.n - 1, matrix.m - 1);
        int counter = 0;
        for (int i = 1; i < matrix.n; i++) {
            for (int j = 0; j < matrix.m; j++) {
                if (j == column) {
                    continue;
                }
                int newI = counter / minor.n;
                int newJ = counter % minor.n;
                minor.matrix[newI][newJ] = new Complex(matrix.matrix[i][j]);
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
            Matrix minor = getMinorMatrix(matrix, i);
            result = result.add(matrix.matrix[0][i]
                    .setScale(Math.pow(-1, i + 2))
                    .multiply(determinant_helper(minor)));
        }
        return result;
    }

    public Matrix transpose() {
        Matrix T = new Matrix(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                T.matrix[i][j] = new Complex(matrix[j][i]);
            }
        }
        return T;
    }

    public Complex determinant() {
        if (m != n)
            throw new ClassFormatError("Matrix is not square. Impossible to get determinant");
        return determinant_helper(this);
    }
}
