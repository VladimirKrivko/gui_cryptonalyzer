package com.example.logics;

public class Encrypt extends AlphabetCaesar {

    private final String text;

    private final int key;

    public Encrypt(String text, int key){
        this.text = text;
        this.key = key;
    }

    public String encrypt() {

        char[] text = this.text.toCharArray();
        char[] result = new char[text.length];

        boolean isAdded;

        for (int i = 0; i < text.length; i++) {
            isAdded = false;
            for (int j = 0; j < alphabetCipher.length; j++) {
                if (text[i] == alphabetCipher[j]) {
                    //Чтобы индекс не вылетал за пределы массива заданного алфавита берем остаток от деления на длину алфавита.
                    //Тогда все индексы что меньше длины массива алфавита останутся неизменными, а те что больше "пойдут на второй круг".
                    int index = (j + key) % alphabetCipher.length;
                    //На тот случай если encryptKey отрицательный.
                    if (index < 0) {
                        index += alphabetCipher.length;
                    }
                    result[i] = alphabetCipher[index];
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
