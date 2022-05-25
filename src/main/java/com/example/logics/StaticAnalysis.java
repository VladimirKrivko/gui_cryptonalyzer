package com.example.logics;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class StaticAnalysis extends AlphabetCaesar {

    private final String text;

    private char[] popLetterRus = " оеаинтсрвл".toCharArray();

    public StaticAnalysis(String text) {
        this.text = text;
    }

    public StaticAnalysis(String text, String anotherTextByTheSameAuthor) {
        this.text = text;
        popLetterRus = frequencyOfLetters(anotherTextByTheSameAuthor);

    }

    public String decrypt() {
        int key = frequencyAnalysis(popLetterRus, frequencyOfLetters(text));
        return new Encrypt(text, key).encrypt();
    }

    private int frequencyAnalysis(char[] popLettersOpenTextAuthor, char[] popLettersCipherText) {
        //Массив под каждый потенциальный ключ.
        int[] keys = new int[popLettersOpenTextAuthor.length];
        int indexAlphabet;
        for (int i = 0; i < popLettersCipherText.length; i++) {
            // получаю индекс шифросимвола в массиве алфавита.
            for (indexAlphabet = 0; indexAlphabet < alphabetCipher.length; indexAlphabet++) {
                if (alphabetCipher[indexAlphabet] == popLettersCipherText[i]) {
                    break;
                }
            }
            // ищу ключ при котором самая популярная шифрованная буква станет самой популярной буквой открытого текста.
            for (int j = indexAlphabet, count = 0; j < alphabetCipher.length; j++, count++) {
                if (popLettersOpenTextAuthor[i] != popLettersCipherText[i]) {
                    popLettersCipherText[i] = alphabetCipher[j];
                    if (j == alphabetCipher.length - 1) {
                        j = -1;     // -1 потому что нулевой индекс тоже проверить нужно, инкремент сделает 0.
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

    private char[] frequencyOfLetters (String inputText) {
        char[] popLetterInText = new char[11];

        Map<Character, Integer> unsortedMap = new HashMap<>();
        for (int i = 0; i < alphabetCipher.length; i++) {
            unsortedMap.put(alphabetCipher[i], countFragmentInText(inputText, String.valueOf(alphabetCipher[i])));
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

    static int countFragmentInText(String text, String fragment) {
        return (text.length() - text.replace(fragment, "").length()) / fragment.length();
    }
}
