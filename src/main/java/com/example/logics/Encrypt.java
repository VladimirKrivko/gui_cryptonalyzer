package com.example.logics;

/**
 * @author Vladimir Krivko
 * Класс Encrypt выполняет шифрование текста по целочисленному значению.
 */
public class Encrypt {

    /**
     * Массив char с алфавитом шифрования в данный момент. Инициализация происходит в момент создания объекта.
     */
    private final char[] currentAlphabet = AlphabetCaesar.getAlphabetCipher();

    /**
     * Поле хранящее String текст для шифрования.
     */
    private final String text;

    /**
     * Поле хранящее int ключ по которому будет совершено шифрование.
     */
    private final int key;

    /**
     * protected Конструктор класса, потому что доступ к нему понадобится только в этом пакете и возможно в будущем наследникам.
     *
     * @param text принимает открытый текст для шифрования.
     * @param key  принимает ключ по которому будет совершено шифрование.
     */
    protected Encrypt(String text, int key) {
        this.text = text;
        this.key = key;
    }

    /**
     * Основной Конструктор класса для пользователя.
     *
     * @param text     принимает открытый текст для шифрования.
     * @param password принимает любое строковое значение, которое при инициализации переменной key конвертируется в целочисленное значение.
     */
    public Encrypt(String text, String password) {
        this.text = text;
        this.key = new GenerateKey().generateEncryptKey(password);
    }

    /**
     * @return Возвращает шифрованный текст, смещение происходит по ключу записанному в переменную key.
     */
    public String encrypt() {

        char[] text = this.text.toCharArray();
        char[] result = new char[text.length];

        boolean isAdded;

        for (int i = 0; i < text.length; i++) {
            isAdded = false;
            for (int j = 0; j < currentAlphabet.length; j++) {
                if (text[i] == currentAlphabet[j]) {
                    //Чтобы индекс не вылетал за пределы массива заданного алфавита берем остаток от деления на длину алфавита.
                    //Тогда все индексы что меньше длины массива алфавита останутся неизменными, а те что больше "пойдут на второй круг".
                    int index = (j + key) % currentAlphabet.length;
                    //На тот случай если encryptKey отрицательный.
                    if (index < 0) {
                        index += currentAlphabet.length;
                    }
                    result[i] = currentAlphabet[index];
                    isAdded = true;
                }
            }
            if (!isAdded) {
                result[i] = text[i];
            }
        }
        return new String(result);
    }
}
