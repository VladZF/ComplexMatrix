import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void simpleTest() {
        Random random = new Random();
        Matrix A = new Matrix(2, 2);
        Matrix B = new Matrix(2, 2);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                A.set(i, j, new Complex(random.nextInt(-10, 10), random.nextInt(-10, 10)));
                B.set(i, j, new Complex(random.nextInt(-10, 10), random.nextInt(-10, 10)));
            }
        }

        System.out.println("Matrix A:\n" + A);
        System.out.println("Matrix B:\n" + B);
        System.out.println("A + B:\n" + A.add(B));
        System.out.println("A - B:\n" + A.subtract(B));
        System.out.println("A * B:\n" + A.multiply(B));
        System.out.println("A * 2:\n" + A.multiplyWithNumber(2));
        System.out.println("A_transposed:\n" + A.transpose());
        System.out.println("Determinant of A:\n" + A.determinant());
    }

    public static void determinantTest() {
        Matrix A = new Matrix(3, 3);
        A.set(0,0, new Complex(2, 0));
        A.set(1, 1, new Complex(-3, 0));
        A.set(2, 2, new Complex(4, 0));
        System.out.println(A);
        System.out.println(A.determinant());
    }


    public static void main(String[] args) {
        simpleTest();
    }
}