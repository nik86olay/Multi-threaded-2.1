package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static AtomicInteger counterThree = new AtomicInteger(0);
    static AtomicInteger counterFour = new AtomicInteger(0);
    static AtomicInteger counterFive = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> analysisOneLetter(texts));
        Thread thread2 = new Thread(() -> analysisPalindrome(texts));
        Thread thread3 = new Thread(() -> analysisIncreasing(texts));

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println();
        System.out.println("Красивых слов с длиной 3: " + counterThree + " шт.");
        System.out.println("Красивых слов с длиной 4: " + counterFour + " шт.");
        System.out.println("Красивых слов с длиной 5: " + counterFive + " шт.");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void analysisOneLetter(String[] texts) {
        for (String text : texts) {
            if (text.replaceAll(String.valueOf(text.charAt(0)), "").length() == 0) {
                //System.out.println(Thread.currentThread() + " Бинго!!! " + text);
                addCount(text);
            }
        }
    }

    public static void analysisPalindrome(String[] texts) {
        for (String text : texts) {
            boolean check = true;
            int length = text.length();
            if (text.replaceAll(String.valueOf(text.charAt(0)), "").length() == 0) {
                check = false;
            } else {
                for (int i = 0; i < length / 2; i++) {
                    if (text.charAt(i) != text.charAt(length - i - 1)) {
                        check = false;
                        break;
                    }
                }
            }

            if (check) {
                //System.out.println(Thread.currentThread() + " Бинго!!! " + text);
                addCount(text);
            }
        }
    }

    public static void analysisIncreasing(String[] texts) {
        for (String text : texts) {
            boolean check = true;
            if (text.replaceAll(String.valueOf(text.charAt(0)), "").length() == 0) {
                check = false;
            } else {
                byte[] byteArr = text.getBytes();
                byte firstSymbol = 'a';
                for (byte secondSymbol : byteArr) {
                    if (firstSymbol > secondSymbol) {
                        check = false;
                        break;
                    }
                    firstSymbol = secondSymbol;
                }
            }
            if (check) {
                //System.out.println(Thread.currentThread() + " Бинго!!! " + text);
                addCount(text);
            }
        }
    }

    public static void addCount(String text) {
        switch (text.length()) {
            case 3 -> counterThree.getAndIncrement();
            case 4 -> counterFour.getAndIncrement();
            case 5 -> counterFive.getAndIncrement();
        }
    }
}