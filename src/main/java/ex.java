import com.uddernetworks.bfjvm.HelloWorld;

import java.util.Scanner;

public class ex {
    private static Scanner scanner;
    private static byte[] tape;
    private static int index;

    public static void main(String[] var0) {
        while (ex.tape[ex.index] > 0) {
            ex.tape[ex.index]++;
        }
    }

    static {
        scanner = new Scanner(System.in);
    }
}
