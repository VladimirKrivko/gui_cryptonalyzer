package com.example.logics;

/**
 * @author Vladimir Krivko
 * Класс содержит алфавит по которому будет производиться шифрование методом Цезаря.
 */
public class AlphabetCaesar {
    /**
     * Переменная хранящая ссылку на массив char с алфавитом шифрования. Не final потому что в будущем хочу реализовать изменение алфавита шифрования, либо выбор из заготовленных алфавитов.
     */
    private static char[] alphabetCipher = "AБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ-!,?абвгдеёжзийклмнопрстуфхцчшщъыьэюя. \":".toCharArray();

    /**
     * Геттер поля alphabetCipher.
     * @return массив char с алфавитом шифрования в данный момент.
     */
    public static char[] getAlphabetCipher() {
        return alphabetCipher;
    }

    /*public static void setAlphabetCipher(char[] alphabetCipher) {
        alphabetCipher = alphabetCipher;
    }*/
}
