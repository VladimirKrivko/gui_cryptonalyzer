package com.example.logics;

public interface GenerateKey {

    char[] currentAlphabet = AlphabetCaesar.getAlphabetCipher();

    default int generateEncryptKey(String password) {
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
