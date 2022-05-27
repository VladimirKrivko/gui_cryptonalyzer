package com.example.logics;

/**
 * @author Vladimir Krivko
 * Класс BruteForce реализует дешифрование текста перебирая все возможные варианты ключей и подсчитывая максимальное количество вхождений в текст " " (пробелов) и ", " (запятая пробел).
 */
public class BruteForce {

    /**
     * Массив char с алфавитом шифрования в данный момент. Инициализация происходит в момент создания объекта.
     */
    private final char[] currentAlphabet = AlphabetCaesar.getAlphabetCipher();

    /**
     * Поле хранящее закрытый текст для дешифрования.
     */
    private final String text;

    /**
     * Поле хранящее int ключ по которому будет совершено дешифрование. Не final поскольку будет итерировать все возможные значения в заданном алфавите.
     */
    private int key;

    /**
     * Конструктор класса для пользователя.
     *
     * @param text Принимает закрытый текст для дешифрования.
     */
    public BruteForce(String text) {
        this.text = text;
    }

    /**
     * Метод доступный пользователю выполняющий дешифровку.
     *
     * @return Возвращает дешифрованный (открытый) текст.
     */
    public String decrypt() {
        return new Decrypt(text, bruteForce()).decrypt();
    }

    /**
     * Метод находит ключ (int) при котором вероятнее всего текст станет считаться расшифрованным.
     * Метод делает смещение текста на 1 и подсчитывает сумму вхождений " " (пробелов) и ", " (запятая пробел).
     * Ключ при котором было максимальное сумма вхождений является правильным.
     * Даже одного пробела будет достаточно. А в случае если ваш текст состоит из одного предложения не содержащего запятых, это будет верным решением.
     *
     * @return Возвращает int ключ, который в последующем необходимо передать в вызов конструктора объекта Decrypt метода decrypt(). Написал, прям написал...
     */
    private int bruteForce() {

        int decryptKey = 0;
        int maxNumberOfRepetitions = 0;
        String text;
        Decrypt temp;

        while (key < currentAlphabet.length) {
            // Переменная key по умолчанию инициализируется нулем.
            temp = new Decrypt(this.text, ++key);
            text = temp.decrypt();

            //Количество повторений паттерна ", " в потенциально расшифрованном тексте.
            int numberOfRepetitions_1 = countFragmentInText(text, ", ");
            //Количество повторений пробела в потенциально расшифрованном тексте. На случай описанный в шапке метода.
            int numberOfRepetitions_2 = countFragmentInText(text, " ");

            int numberOfRepetitions = numberOfRepetitions_1 + numberOfRepetitions_2;
            //Если нашли больше повторений, то и ключик (текущий key) надо записать.
            if (numberOfRepetitions > maxNumberOfRepetitions) {
                maxNumberOfRepetitions = numberOfRepetitions;
                decryptKey = key;
            }
        }
        return decryptKey;
    }

    /**
     * Честно нашел этот метод в интернетах, использовал его собсна в классах BruteForce и StaticAnalysis для подсчета вхождений подстроки fragment в строку text.
     *
     * @param text     Любой текст.
     * @param fragment Фрагмент текста который мы будем искать и подсчитывать.
     * @return Количество (int) вхождений подстроки fragment в строку text.
     */
    static int countFragmentInText(String text, String fragment) {
        return (text.length() - text.replace(fragment, "").length()) / fragment.length();
    }
}
