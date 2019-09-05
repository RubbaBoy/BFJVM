import java.util.Scanner;

public class ex {
    private static byte[] tape = new byte[65536];
    private static int index;
    private static Scanner scanner;

    public static void main(String[] var0) {
        ++tape[index];
        ++tape[index];
        ++tape[index];
        ++tape[index];
        ++tape[index];
        ++tape[index];
        ++tape[index];
        ++tape[index];
        ++tape[index];
        ++tape[index];

        while(tape[index] > 0) {
            --tape[index];
            ++index;
            ++tape[index];
            ++tape[index];
            ++tape[index];
            ++tape[index];
            ++tape[index];
            ++tape[index];
            ++tape[index];
            --index;
        }

        ++index;
        ++tape[index];
        ++tape[index];
        System.out.println((char)tape[index]);
        ++tape[index];
        System.out.println((char)tape[index]);
    }

    static {
        scanner = new Scanner(System.in);
    }
}
