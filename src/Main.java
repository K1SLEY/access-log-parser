import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите первое число:");
        int number_1 = new Scanner(System.in).nextInt();
        System.out.println("Введите второе число:");
        int number_2 = new Scanner(System.in).nextInt();
        int sum = number_1 + number_2;
        int subsum = number_1 - number_2;
        int mult = number_1 * number_2;
        double div =(double)number_1 / number_2;
        System.out.println("Сумма чисел " + number_1 + " + "+ number_2 + " = " + sum  );
        System.out.println("Разность чисел " + number_1 + " - "+ number_2 + " = " + subsum);
        System.out.println("Умножение чисел " + number_1 + " * "+ number_2 + " = " + mult);
        System.out.println("Частное чисел " + number_1 + " / "+ number_2 + " = " + div);


    }
}