package com.example.logics;

/**
 * @author Vladimir Krivko
 * Класс содержит алфавит по которому будет производиться шифрование методом Цезаря.
 */
public class AlphabetCaesar {
    /**
     * Переменная хранящая ссылку на массив char с алфавитом шифрования.
     */
    protected static char[] alphabetCipher = "AБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ-!,?абвгдеёжзийклмнопрстуфхцчшщъыьэюя. \":".toCharArray();

}
