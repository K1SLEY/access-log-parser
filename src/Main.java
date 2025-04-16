import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

class LongLineException extends RuntimeException {
    public LongLineException(String message) {
        super(message);
    }
}

public class Main {


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Statistics stats = new Statistics();

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
                int totalRequests = 0;


                while ((line = reader.readLine()) != null) {

                    int length = line.length();
                    LogEntry entry = new LogEntry(line);
                    stats.addEntry(entry);

                    if (length > 1024) {
                        throw new LongLineException("Строка превышает 1024 символа: " + length);
                    }
                    totalRequests++;


                }

                System.out.println("Существующие страницы: ");
                stats.getExistingPages().forEach(page -> System.out.println("- " + page));

                HashMap<String, Double> osStats = stats.getOsStatistics();
                osStats.forEach((os, ratio) ->
                        System.out.printf("%s: %.2f%%\n", os, ratio * 100));


                HashMap<String, Double> browserStats = stats.getBrowserStats();
                System.out.println("Cтраницы с ошибкой 404: ");
                stats.getNotFoundPages().forEach(page -> System.out.println("-! " + page));
                browserStats.forEach((browser, ratio) ->
                        System.out.printf("%s: %.2f%%\n", browser, ratio * 100));

                System.out.println("Общее количество запросов: " + totalRequests);

                System.out.printf("Средний трафик в час: %.2f мб/час%n", stats.getTrafficRate() / 1048576);
            } catch (IOException e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
        }
    }
}


