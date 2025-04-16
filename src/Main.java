import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
                int googlebotCount = 0;
                int yandexBotCount = 0;
                while ((line = reader.readLine()) != null) {

                    int length = line.length();
                    LogEntry entry = new LogEntry(line);
                    stats.addEntry(entry);

                    if (length > 1024) {
                        throw new LongLineException("Строка превышает 1024 символа: " + length);
                    }
                    totalRequests++;

                    String[] parts = line.split("\"");

                    if (parts.length >= 6) {
                        String userAgent = parts[5];


                        if (userAgent.contains("Googlebot")) {
                            googlebotCount++;
                        } else if (userAgent.contains("YandexBot")) {
                            yandexBotCount++;
                        }
                    }
                }

                System.out.println("Общее количество запросов: " + totalRequests);
                System.out.println("Googlebot: " + googlebotCount + " (" +
                        (totalRequests > 0 ? (100 * googlebotCount / totalRequests) : 0) + "%)");
                System.out.println("YandexBot: " + yandexBotCount + " (" +
                        (totalRequests > 0 ? (100 * yandexBotCount / totalRequests) : 0) + "%)");
                System.out.printf("Средний трафик в час: %.2f мб/час%n", stats.getTrafficRate()/1048576);
            } catch (IOException e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
        }
    }
}


