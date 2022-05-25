package com.example.logics;

public class Decrypt extends AlphabetCaesar {

    private final Encrypt enc;
    public Decrypt(String text, int key){
        enc = new Encrypt(text, -key);
    }

    public String decrypt() {
        return enc.encrypt();
    }
}
