package com.example.logics;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Vladimir Krivko
 * Класс StaticAnalysis дешифрует текст статистическим анализом, а именно с применением метода частотного анализа.
 */
public class StaticAnalysis {

    /**
     * Массив char с алфавитом шифрования в данный момент. Инициализация происходит в момент создания объекта.
     */
    private final char[] currentAlphabet = AlphabetCaesar.getAlphabetCipher();

    /**
     * Поле хранящее закрытый текст для дешифрования.
     */
    private final String text;

    /**
     * Поле хранящее первые 11 самых популярных букв русского алфавита в порядке убывания согласно статье "Частотность" Википедия.
     * Пробел включен по условию и является самым популярным символом практически любого языка.
     * 11 символов потому что дальше разница в частоте использования букв минимальная, а значт в теории может увеличится количество ошибок.
     * 11 это хорошо, вроде как работает))
     */
    private char[] popLetterRus = " оеаинтсрвл".toCharArray();

    /**
     * Конструктор класса для пользователя.
     *
     * @param text Принимает закрытый текст для дешифрования.
     */
    public StaticAnalysis(String text) {
        this.text = text;
    }

    /**
     * Конструктор класса для пользователя, на тот случай когда нужно дешифровать закрытый текст используя частотную статистику алфавита этого же автора.
     *
     * @param text                       Параметр принимает закрытый текст.
     * @param anotherTextByTheSameAuthor Параметр принимает открытый (другой) текст того же автора.
     */
    public StaticAnalysis(String text, String anotherTextByTheSameAuthor) {
        this.text = text;
        popLetterRus = frequencyOfLetters(anotherTextByTheSameAuthor);

    }

    /**
     * Метод доступный пользователю выполняющий дешифровку.
     *
     * @return Возвращает дешифрованный (открытый) текст.
     */
    public String decrypt() {
        int key = frequencyAnalysis(popLetterRus, frequencyOfLetters(text));
        return new Encrypt(text, key).encrypt();
    }

    /**
     * Метод возвращает ключ (int), при котором самые популярные шифрованные буквы текста становятся самыми популярными буквами открытого текста.
     * Если не загружать другой текст того же автора, то в popLettersOpenTextAuthor передается стандартная частотность букв " оеаинтсрвл".
     * Метод чувствителен к объему текста, так как частотную статистику надо собирать с некоторого объема. В коротких же текстах будет своя частная статистика,
     * которую сложно предсказать. Но если дополнительно добавить этот же короткий открытый текст, то метод соберет эту частную недостатистику и расшифрует верно.
     * В общем текст должен содержать около 250 символов (входящих в currentAlphabet).
     *
     * @param popLettersOpenTextAuthor Принимает массив на 11 элементов самых популярных букв открытого текста того же автора, либо массив стандартной частотности " оеаинтсрвл".
     * @param popLettersCipherText     Принимает массив на 11 элементов самых популярных букв закрытого текста.
     * @return Возвращает ключ (int), при котором самые популярные шифрованные буквы текста становятся самыми популярными буквами открытого текста.
     */
    private int frequencyAnalysis(char[] popLettersOpenTextAuthor, char[] popLettersCipherText) {
        //Массив под каждый потенциальный ключ.
        int[] keys = new int[popLettersOpenTextAuthor.length];
        int indexAlphabet;
        for (int i = 0; i < popLettersCipherText.length; i++) {
            // получаю индекс шифросимвола в массиве алфавита.
            for (indexAlphabet = 0; indexAlphabet < currentAlphabet.length; indexAlphabet++) {
                if (currentAlphabet[indexAlphabet] == popLettersCipherText[i]) {
                    break;
                }
            }
            // ищу ключ при котором самая популярная шифрованная буква станет самой популярной буквой открытого текста.
            for (int j = indexAlphabet, count = 0; j < currentAlphabet.length; j++, count++) {
                if (popLettersOpenTextAuthor[i] != popLettersCipherText[i]) {
                    popLettersCipherText[i] = currentAlphabet[j];
                    if (j == currentAlphabet.length - 1) {
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

    /**
     * Метод находит 11 самых популярных букв в тексте в порядке убывания.
     * В Мапу добавляется ключ-буква и значение-количество ее повторения в тексте. Мапа сортируется в порядке убывания по значению.
     *
     * @param inputText Принимает текст с которого будет собираться частотная статистика.
     * @return Возвращает массив char[] 11 самых популярных букв в тексте в порядке убывания.
     */
    private char[] frequencyOfLetters(String inputText) {
        char[] popLetterInText = new char[11];

        Map<Character, Integer> unsortedMap = new HashMap<>();
        for (int i = 0; i < currentAlphabet.length; i++) {
            unsortedMap.put(currentAlphabet[i], countFragmentInText(inputText, String.valueOf(currentAlphabet[i])));
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
        for (Character popLetter : sortedMap.keySet()) {
            popLetterInText[index] = popLetter;
            index++;
            if (index >= 11) {
                break;
            }
        }
        return popLetterInText;
    }

    /**
     * Этот метод для подсчета вхождений подстроки fragment в строку text. Использовал его в классах BruteForce и StaticAnalysis.
     * Надо переписать его в другое место, чтобы не повторялся?
     *
     * @param text     Любой текст.
     * @param fragment Фрагмент текста который мы будем искать и подсчитывать.
     * @return Количество (int) вхождений подстроки fragment в строку text.
     */
    static int countFragmentInText(String text, String fragment) {
        return (text.length() - text.replace(fragment, "").length()) / fragment.length();
    }
}
