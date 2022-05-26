package com.example.logics;

public class Decrypt implements GenerateKey {

    private final int key;
    private final Encrypt enc;
    protected Decrypt(String text, int key){
        this.key = key;
        enc = new Encrypt(text, -key);
    }

    public Decrypt(String text, String password) {
        key = -generateEncryptKey(password);
        enc = new Encrypt(text, key);
    }

    public String decrypt() {
        return enc.encrypt();
    }
}
