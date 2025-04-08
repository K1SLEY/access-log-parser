import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

class LongLineException extends RuntimeException {
    public LongLineException(String message) {
        super(message);
    }
}

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
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                String line;
                int totalLines = 0;
                int maxLength = 0;
                int minLength = Integer.MAX_VALUE;

                while ((line = reader.readLine()) != null) {
                    totalLines++;
                    int length = line.length();

                    if (length > 1024) {
                        throw new LongLineException("Строка превышает 1024 символа: " + length);
                    }

                    maxLength = Math.max(maxLength, length);
                    minLength = Math.min(minLength, length);
                }

                if (totalLines == 0) {
                    System.out.println("Файл пуст.");
                } else {
                    System.out.println("Общее количество строк: " + totalLines);
                    System.out.println("Длина самой длинной строки: " + maxLength);
                    System.out.println("Длина самой короткой строки: " + (minLength == Integer.MAX_VALUE ? 0 : minLength));
                }

            } catch (LongLineException ex) {
                System.err.println("Ошибка: " + ex.getMessage());
            } catch (Exception ex) {
                System.err.println("Ошибка при чтении файла: " + ex.getMessage());
            }
        }
    }
}


