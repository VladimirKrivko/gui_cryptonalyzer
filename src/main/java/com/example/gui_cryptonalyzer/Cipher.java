package com.example.gui_cryptonalyzer;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Cipher {
    public static String fileInputName;
    public static String fileOutputName;
    public static int encryptKey;

    /*static final public char[] alphabetRus = {'A', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У'
            , 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', 'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л'
            , 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', '.', ' ', '"', ':', '-', '!', ',', '?'};*/
    static final public char[] alphabetRus = "AБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ-!,?абвгдеёжзийклмнопрстуфхцчшщъыьэюя. \":".toCharArray();

    //Первые 11 самых популярных букв русского алфавита в порядке убывания согласно статьи "Частотность" Википедия.
    //Пробел включен по условию и является самым популярным символом практически любого языка.
    public static char[] popLetterRus = " оеаинтсрвл".toCharArray();

    //Метод задает путь исходящего файла рядом с исходным с припиской к имени "_CRYPT".
    public static void setFileOutputName (String fileInputName) {
        fileOutputName = fileInputName.substring(0, fileInputName.lastIndexOf(".")) +
                "_CRYPT" + fileInputName.substring(fileInputName.lastIndexOf("."));
    }

    //Метод возвращает текст входящего файла.
    public static String getTextFromFile(String fileName) throws IOException {
        try (FileInputStream fis = new FileInputStream(fileName);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int b;
            while ((b = fis.read()) != -1) {
                baos.write(b);
            }
            return baos.toString();
        }
    }

    //Метод записывает текст в исходящий файл.
    public static void pushTextToFile(String text) throws IOException {
        try (FileWriter fw = new FileWriter(fileOutputName)) {
            fw.write(text);
        }
    }

    //Метод вохвращает шифрованный текст, смещение происходит по ключу записанному в статическую переменную encryptKey.
    public static String encrypt(String inputText) throws IOException {

        char[] text = inputText.toCharArray();
        char[] result = new char[text.length];

        boolean isAdded;

        for (int i = 0; i < text.length; i++) {
            isAdded = false;
            for (int j = 0; j < alphabetRus.length; j++) {
                if (text[i] == alphabetRus[j]) {
                    int index = (j + encryptKey) % alphabetRus.length;
                    if (index < 0) {
                        index += alphabetRus.length;
                    }
                    result[i] = alphabetRus[index];
                    isAdded = true;
                }
            }
            if (!isAdded) {
                result[i] = text[i];
            }
        }
        return new String(result);
    }

    //Метод BruteForce делает смещение текста на 1 и подсчитывает колличество вхождений ", "
    //Ключ при котором было максимальное колличество вхождений ", " является правильным.
    //Можно добавить еще критерии отбора, но и этого достаточно.
    public static String bruteForce(String inputText, String popularLetter) throws IOException {

        int decryptKey = 0;
        int marker = 0;
        String text;

        while (encryptKey < alphabetRus.length) {

            encryptKey++;

            text = encrypt(inputText);

            int countMarker = countFragmentInText(text, popularLetter);

            if (countMarker > marker) {
                marker = countMarker;
                decryptKey = encryptKey;
            }
        }
        encryptKey = decryptKey;
        return encrypt(inputText);
    }

    //Метод возвращает ключ при котором самые популярные шифрованные буквы текста становятся самыми популярными буквами открытого текста.
    //Если не загружать другой текст того же автора, то в popLettersOpenTextAuthor передается стандартная частотность букв " оеаинтсрвл".
    public static int statAnal(char[] popLettersOpenTextAuthor, char[] popLettersCipherText) throws IOException {

        int[] keys = new int[popLettersOpenTextAuthor.length];
        int indexAlphabet;
        for (int i = 0; i < popLettersCipherText.length; i++) {

            for (indexAlphabet = 0; indexAlphabet < alphabetRus.length; indexAlphabet++) {      // получить индекс шифросимвола в массиве алфавита.
                if (alphabetRus[indexAlphabet] == popLettersCipherText[i]) {
                    break;
                }
            }

            for (int j = indexAlphabet, count = 0; j < alphabetRus.length; j++, count++) {
                if (popLettersOpenTextAuthor[i] != popLettersCipherText[i]) {
                    popLettersCipherText[i] = alphabetRus[j];
                    if (j == alphabetRus.length - 1) {
                        j = -1;
                    }
                } else {
                    keys[i] = count - 1;
                    break;
                }
            }
        }
        //Получив массив ключей, нахожу ключ который повторяется больше других.
        int key = 0;
        int maxRepeatKey = 0;

        for (int i = 0; i < keys.length; i++) {
            int countRepeatKey = 0;
            for (int j = 0; j < keys.length; j++) {
                if (keys[i] == keys[j]) {
                    countRepeatKey++;
                }
            }
            if (countRepeatKey > maxRepeatKey) {
                maxRepeatKey = countRepeatKey;
                key = keys[i];
            }
        }
        return key;
    }

    //Честно нашел этот метод в интернетах, использовал его собсна в bruteForce и frequencyOfLetters для подсчета вхождений подстроки fragment в строку text.
    public static int countFragmentInText(String text, String fragment) {
        return (text.length() - text.replace(fragment, "").length()) / fragment.length();
    }

    //Метод находит 11 самых популярных букв в тексте в порядке убывания.
    //Компаратор для HashMap сам не написал, нашел на просторах интернетов.
    //В Мапу добавляется ключ-буква и значение-количество ее повторения в тексте. Мапа сортируется в порядке убывания по значению.
    public static char[] frequencyOfLetters (String inputText) {
        char[] popLetterInText = new char[11];

        Map<Character, Integer> unsortedMap = new HashMap<>();
        for (int i = 0; i < alphabetRus.length; i++) {
            unsortedMap.put(alphabetRus[i], countFragmentInText(inputText, String.valueOf(alphabetRus[i])));
        }
        Map<Character, Integer> sortedMap = unsortedMap.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> -e.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> {
                            throw new AssertionError();
                        },
                        LinkedHashMap::new
                ));
        int index = 0;
        for (Character popLetter: sortedMap.keySet()) {
            popLetterInText[index] = popLetter;
            index++;
            if (index >= 11) {
                break;
            }
        }
        return popLetterInText;
    }

    //Метод сделан для удобства пользователя при вводе ключа. Пользователь может написать что угодно, хоть лбом по клавиатуре ударить.
    //Нехитрой манипуляцией получаем из текста какое-то целое число с которым и будет работать программа.
    public static int generateEncryptKey(String stringKey) throws IOException {
        char[] chars = stringKey.toCharArray();
        int key = 0;
        for (int i = 0; i < chars.length; i++) {
            if (i % 2 == 0) {
                key += chars[i];
            } else {
                key -= chars[i];
            }
        }
        if (key > Cipher.alphabetRus.length) {
            key %= Cipher.alphabetRus.length;
        }
        return key;
    }
}
