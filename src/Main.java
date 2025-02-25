import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int count = 0;

        while (true) {
            System.out.println("Введите путь к файлу");
            String path = scanner.nextLine();
            File file = new File(path);
            boolean isFile = file.isFile();

            if (!isFile) {
                if (!file.exists()) {
                    System.out.println("Файла не существует.");
                } else {
                    System.out.println("Указанный путь является директорией.");
                }
                continue;
            }

            System.out.println("Путь указан верно");
            count++;
            System.out.println("Это файл номер " + count);
        }
    }
}

