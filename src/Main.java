import java.util.Random;
import java.util.Scanner;

public class Main {

    public static boolean isMutuallySimple(int a, int b) { // проверка на взаимно простые числа
        if(a < b) {
            for(int i = 5; i <= a; i++) {
                if(b % i == 0 && a % i == 0) {
                    return false;
                }
            }
            return true;
        }
        else return false;
    }

    public static int closeExp(int a, int b) {  // находим закрытую экспоненту
        for(int i = 1;;i++) {
            if((i * b) % a == 1)
                return i;
        }
//        int d0 = a; int d1 = b;
//        int x0 = 1; int x1 = 0;
//        int y0 = 0; int y1 = 1;
//        while(d1 > 1) {
//            int q = d0 / d1;
//            int d2 = d0 % d1;
//            int x2 = x0 - q * x1;
//            int y2 = y0 - q * y1;
//            d0 = d1; d1 = d2;
//            x0 = x1; x1 = x2;
//            y0 = y1; y1 = y2;
//        }
//        return y1;
    }

    public static int pow(int base, int power, long mod) {  // быстрое возведение в степень
        if (power == 0) return 1;
        int result = pow(base, power / 2, mod);
        if (power % 2 == 0)
            return (int)((result * result) % mod);
        else
            return (int)((base * result * result) % mod);
    }

    private static int length = 0;

    public static int RSA_coder(char[] string) {
        // генерация публичного ключа
        int[] simpleNumbers = new int[] {5, 7, 11, 13, 17, 19, 23, 29, 31, 37};
        Random random = new Random();
        // выбираем 2 случайных разных числа
        int first = random.nextInt(10);
        int second;
        do {
            second = random.nextInt(10);
        } while(first == second);
        // получаем произведение чисел
        int multiply = simpleNumbers[first] * simpleNumbers[second];
        // получаем функцию Эйлера
        int function = (simpleNumbers[first] - 1) * (simpleNumbers[second] - 1);
        // получаем открытую экспоненту
        int exp = 0;
        for(int i = 0; i < simpleNumbers.length; i++) {
            if(isMutuallySimple(simpleNumbers[i], function)){
                exp = simpleNumbers[i];
                break;
            }
        }
        // вычислим закрытую экспоненту
        int closeExp = closeExp(function, exp);
//        if(closeExp < 0)
//            closeExp += function;
        // шифруем строку
        int[] mas = new int[string.length];
        for(int i = 0; i < string.length; i++) {
            mas[i] = pow(string[i] - 63, exp, multiply);
        }
        System.out.println("Результат:");
        for(int i = 0; i < mas.length; i++)
            System.out.print(mas[i] + " ");
        System.out.println();
        System.out.println("Публичный ключ:");
        System.out.println(exp + " " + multiply);
        System.out.println("Приватный ключ:");
        System.out.println(closeExp + " " + multiply);
        return mas.length;
    }

    public static void RSA_decoder(int length) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите приватный ключ:");
        int closeExp = scan.nextInt();
        int multiply = scan.nextInt();
        int[] mas = new int[length];
        System.out.println("Введите строку для расшифровки:");
        // расшифровываем строку
        for(int i = 0; i < mas.length; i++) {
            mas[i] = pow(scan.nextInt(), closeExp, multiply);
            System.out.print((char)(mas[i] + 63));
        }
        System.out.println();
    }

    public static void mainMenu() {
        System.out.println("Зашифровать строку(1)");
        System.out.println("Расшифровать строку(2)");
        Scanner scan = new Scanner(System.in);
        int value = scan.nextInt();
        String str;
        switch (value) {
            case 1: {
                System.out.println("Введите строку для шифрования:");
                scan.nextLine();
                str = scan.nextLine();
                length = RSA_coder(str.toCharArray());
            } break;
            case 2: {
                RSA_decoder(length);
            } break;
            default:
                System.out.println("Проверьте введенные данные.");
        }
    }

    public static void main(String[] args) {
        while (true) {
            mainMenu();
        }
    }

}
