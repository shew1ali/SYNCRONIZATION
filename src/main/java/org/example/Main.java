package org.example;

import java.sql.SQLOutput;
import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        String[] lines = new String[1000];

        for (int i = 0; i < lines.length; i++) {
            lines[i] = generateRoute("RLRFR", 100);
        }
        List<Thread> threads = new ArrayList<>();

        for (String line : lines) {
            Runnable counter = () -> {
                Integer numberOfRs = 0;
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == 'R') {
                        numberOfRs++;
                    }
                }
                System.out.println(line.substring(0, 100) + " " + numberOfRs);

                synchronized (numberOfRs){
                    if(sizeToFreq.containsKey(numberOfRs)){
                        sizeToFreq.put(numberOfRs,sizeToFreq.get(numberOfRs) + 1);
                    } else {
                        sizeToFreq.put(numberOfRs, 1);
                    }
                }
            };
            Thread thread = new Thread(counter);
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

        int maxAmount = 0;
        Integer ttlNmbrSymbols = 0;
        for (Integer key : sizeToFreq.keySet()) {
            if (sizeToFreq.get(key) > maxAmount) {
                maxAmount = sizeToFreq.get(key);
                ttlNmbrSymbols = key;
            }
        }
        System.out.println();
        System.out.println("Самое частое количество повторений " + ttlNmbrSymbols + " (а всего встретился " + maxAmount + ")");
        sizeToFreq.remove(ttlNmbrSymbols);

        System.out.println("Другие размеры:");
        for (Integer key : sizeToFreq.keySet()) {
            int value = sizeToFreq.get(key);
            System.out.println("- " + key + " (" + value + " раз)");
        }
    }

        public static String generateRoute (String letters,int length){
            Random random = new Random();
            StringBuilder route = new StringBuilder();
            for (int i = 0; i < length; i++) {
                route.append(letters.charAt(random.nextInt(letters.length())));
            }
            return route.toString();
        }
    }

