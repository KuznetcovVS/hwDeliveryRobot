package ru.netology;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RobotDelivery {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        int numberOfRoutes = 1000;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfRoutes);

        for (int i = 0; i < numberOfRoutes; i++) {
            executor.submit(() -> {
                String route = generateRoute("RLRFR", 100);
                int countR = countOccurrences(route, 'R');
                updateFrequency(countR);
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Ожидание завершения всех потоков
        }

        printResults();
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static int countOccurrences(String str, char ch) {
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == ch) {
                count++;
            }
        }
        return count;
    }

    public synchronized static void updateFrequency(int count) {
        sizeToFreq.put(count, sizeToFreq.getOrDefault(count, 0) + 1);
    }

    public static void printResults() {
        int mostFrequentCount = -1;
        int maxFrequency = -1;

        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentCount = entry.getKey();
            }
        }

        System.out.println("Самое частое количество повторений " + mostFrequentCount + " (встретилось " + maxFrequency + " раз)");

        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getKey() != mostFrequentCount) { // Исключаем самое частое
                System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз)");
            }
        }
    }
}