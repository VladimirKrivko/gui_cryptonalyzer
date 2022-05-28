package com.example.logics;

/**
 * @author Vladimir Krivko
 * Класс GenerateKey описывает преобразование строки к целочисленному значению. Содержит единственный метод int generateEncryptKey(String password).
 */
class GenerateKey {

    /**
     * Массив char с алфавитом шифрования в данный момент.
     */
    private final char[] currentAlphabet = AlphabetCaesar.getAlphabetCipher();

    /**
     * метод реализующий преобразование строки к целочисленному значению.
     * @param password принимает на вход любое строковое значение, которое пользователь введет в поле "Введи код и нажми ENTER".
     * @return возвращает какое-то целочисленное значение согласно алгоритму, которое в последствии станет реальным ключом шифрования (смещения).
     */
    public int generateEncryptKey(String password) {
        char[] chars = password.toCharArray();
        int key = 0;
        for (int i = 0; i < chars.length; i++) {
            if (i % 2 == 0) {
                key += chars[i];
            } else {
                key -= chars[i];
            }
        }
        if (key > currentAlphabet.length) {
            key %= currentAlphabet.length;
        }
        return key;
    }
}
