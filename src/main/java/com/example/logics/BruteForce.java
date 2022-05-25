package com.example.logics;

public class BruteForce extends AlphabetCaesar {

    private final String text;
    private int encryptKey;

    public BruteForce(String text) {
        this.text = text;
    }

    public String decrypt() {
        return new Decrypt(this.text, bruteForce(text)).decrypt();
    }

    private int bruteForce(String inputText) {

        int decryptKey = 0;
        int maxNumberOfRepetitions = 0;
        String text;
        Decrypt temp;

        while (encryptKey < alphabetCipher.length) {
            //Статическая переменная по умолчанию инициализируется нулем.
            temp = new Decrypt(this.text, ++encryptKey);
            text = temp.decrypt();

            //Количество повторений паттерна ", " в потенциально расшифрованном тексте.
            int numberOfRepetitions_1 = countFragmentInText(text, ", ");
            //Количество повторений пробела в потенциально расшифрованном тексте. На случай описанный в шапке метода.
            int numberOfRepetitions_2 = countFragmentInText(text, " ");

            int numberOfRepetitions = numberOfRepetitions_1 + numberOfRepetitions_2;
            //Если нашли больше повторений, то и ключик (текущий encryptKey) надо записать.
            if (numberOfRepetitions > maxNumberOfRepetitions) {
                maxNumberOfRepetitions = numberOfRepetitions;
                decryptKey = encryptKey;
            }
        }
        return decryptKey;
    }

    static int countFragmentInText(String text, String fragment) {
        return (text.length() - text.replace(fragment, "").length()) / fragment.length();
    }
}
