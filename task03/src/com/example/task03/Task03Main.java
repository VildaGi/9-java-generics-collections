package com.example.task03;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

public class Task03Main {

    public static void main(String[] args) throws IOException {

        List<Set<String>> anagrams = findAnagrams(new FileInputStream("task03/resources/singular.txt"), Charset.forName("windows-1251"));
        for (Set<String> anagram : anagrams) {
            System.out.println(anagram);
        }

    }

    public static List<Set<String>> findAnagrams(InputStream inputStream, Charset charset) {
        List<String> words = readAndFilterWords(inputStream, charset);

        Map<String, Set<String>> anagramGroups = groupAnagrams(words);

        return prepareResult(anagramGroups);
    }

    private static List<String> readAndFilterWords(InputStream inputStream, Charset charset) {
        Set<String> filteredWords = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.trim();

                // Приводим к нижнему регистру
                word = word.toLowerCase();

                // Проверяем минимальную длину
                if (word.length() < 3) {
                    continue;
                }

                // Проверяем, что слово содержит только русские буквы
                if (!isRussianWord(word)) {
                    continue;
                }

                // Добавляем в множество для исключения дубликатов
                filteredWords.add(word);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ArrayList<>(filteredWords);
    }

    private static Map<String, Set<String>> groupAnagrams(List<String> words) {
        Map<String, Set<String>> anagramMap = new HashMap<>();

        for (String word : words) {
            String key = getAnagramKey(word);
            anagramMap.computeIfAbsent(key, k -> new TreeSet<>()).add(word);
        }

        return anagramMap;
    }

    private static String getAnagramKey(String word) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    private static List<Set<String>> prepareResult(Map<String, Set<String>> anagramGroups) {
        List<Set<String>> result = new ArrayList<>();

        for (Set<String> group : anagramGroups.values()) {
            if (group.size() >= 2) {
                result.add(new LinkedHashSet<>(group));
            }
        }

        result.sort((group1, group2) -> {
            String first1 = group1.iterator().next();
            String first2 = group2.iterator().next();
            return first1.compareTo(first2);
        });

        return result;
    }

    private static boolean isRussianWord(String word) {
        return word.matches("[а-яё]+");
    }
}
