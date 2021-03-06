package com.example.logics;

/**
 * @author Vladimir Krivko
 * Класс Decrypt выполняет дешифрование текста по целочисленному значению.
 */
public class Decrypt {

    /**
     * Поле хранящее int ключ по которому будет совершено дешифрование.
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
     * @param text Принимает закрытый текст для дешифрования.
     * @param key  Принимает ключ по которому будет совершено дешифрование. Или можно сказать по другому: принимает ключ по которому было совершено шифрование данного закрытого текста.
     */
    protected Decrypt(String text, int key) {
        this.key = key;
        enc = new Encrypt(text, -key);
    }

    /**
     * Основной Конструктор класса для пользователя.
     *
     * @param text     Принимает закрытый текст для дешифрования.
     * @param password Принимает любое строковое значение, которое при инициализации переменной key конвертируется в целочисленное значение со знаком "-".
     */
    public Decrypt(String text, String password) {
        key = -new GenerateKey().generateEncryptKey(password);
        enc = new Encrypt(text, key);
    }

    /**
     * @return Возвращает дешифрованный текст, смещение происходит по ключу записанному в переменную key объекта Encrypt.
     */
    public String decrypt() {
        return enc.encrypt();
    }
}
