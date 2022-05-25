package com.example.logics;

public class GenerateKey {

    private GenerateKey() { }

    public static int generateEncryptKey(String stringKey) {
        char[] chars = stringKey.toCharArray();
        int key = 0;
        for (int i = 0; i < chars.length; i++) {
            if (i % 2 == 0) {
                key += chars[i];
            } else {
                key -= chars[i];
            }
        }
        if (key > AlphabetCaesar.alphabetCipher.length) {
            key %= AlphabetCaesar.alphabetCipher.length;
        }
        return key;
    }
}