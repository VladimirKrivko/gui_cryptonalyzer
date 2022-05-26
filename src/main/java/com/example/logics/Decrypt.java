package com.example.logics;

/**
 * @author Vladimir Krivko
 * Класс Decrypt выполняет дешифрование текста по целочисленнному значению.
 * Реализует интерфейс GenerateKey, чтобы скрыть преобразование строкового значения, которое вводит пользователь, к целочисленному значению.
 */
public class Decrypt implements GenerateKey {

    /**
     * Поле хранящее int ключ по которому будет совершено шифрование.
     */
    private final int key;

    /**
     * Поле хранящее экземпляр класса Encrypt, посредством которого будет производиться расшифровка "закрытого" текста.
     */
    private final Encrypt enc;

    /**
     * protected Конструктор класса, потому что доступ к нему понадобится другим классам расшифровки этого пакета.
     * Конструктор создает объект класса Encrypt, меняя знак ключа. Ибо Цезарь! :)
     *
     * @param text принимает закрытый текст для дешифрования.
     * @param key  принимает ключ по которому будет совершено дешифрование.
     */
    protected Decrypt(String text, int key) {
        this.key = key;
        enc = new Encrypt(text, -key);
    }

    /**
     * Основной Конструктор класса для пользователя.
     *
     * @param text     принимает закрытый текст для дешифрования.
     * @param password принимает любое строковое значение, которое при инициализации переменной key конвертируется в целочисленное значение со знаком "-".
     */
    public Decrypt(String text, String password) {
        key = -generateEncryptKey(password);
        enc = new Encrypt(text, key);
    }

    /**
     * @return Возвращает дешифрованный текст, смещение происходит по ключу записанному в переменную key объекта Encrypt.
     */
    public String decrypt() {
        return enc.encrypt();
    }
}
