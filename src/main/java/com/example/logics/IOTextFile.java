package com.example.logics;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Vladimir Krivko
 * Класс IOTextFile утилитарный, служит для получения текста из файла и сохранения текста в новый файл.
 */
public class IOTextFile {
    /**
     * Поле хранящее путь к входящему файлу.
     */
    private final String inputPathFile;
    /**
     * Поле хранящее путь к исходящему файлу.
     */
    private final String outputPathFile;
    /**
     * Поле хранящее текст входящего файла.
     */
    private final String text;

    /**
     * Конструктор класса для пользователя. Инициализирует все поля объекта.
     * Задает путь к исходящему файлу с припиской к имени "_CRYPT".
     *
     * @param inputPathFile путь к входящему файлу.
     * @throws IOException Бросит исключение, если файл по указанному пути будет отсутствовать.
     */
    public IOTextFile(String inputPathFile) throws IOException {
        this.inputPathFile = inputPathFile;

        text = initializeText();

        outputPathFile = inputPathFile.substring(0, inputPathFile.lastIndexOf(".")) +
                "_CRYPT" + inputPathFile.substring(inputPathFile.lastIndexOf("."));
    }

    /**
     * Геттер поля хранящего путь к входящему файлу.
     *
     * @return Возвращает строку, содержащую абсолютный путь входящего файла.
     */
    public String getInputPathFile() {
        return inputPathFile;
    }

    /**
     * Геттер поля хранящего путь к исходящему файлу.
     *
     * @return Возвращает строку, содержащую абсолютный путь исходящего файла.
     */
    public String getOutputPathFile() {
        return outputPathFile;
    }

    /**
     * Метод служит для инициализации поля хранящего текст входящего файла.
     *
     * @return Возвращает текст входящего файла.
     * @throws IOException Бросит исключение, если файл по указанному пути будет отсутствовать.
     */
    private String initializeText() throws IOException {
        try (FileInputStream fis = new FileInputStream(inputPathFile);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int b;
            while ((b = fis.read()) != -1) {
                baos.write(b);
            }
            return baos.toString();
        }
    }

    /**
     * Геттер поля хранящего текст входящего файла.
     *
     * @return Возвращает (String) текст входящего файла.
     */
    public String getText() {
        return this.text;
    }

    /**
     * Метод записывает в исходящий файл переданный параметром текст.
     *
     * @param text Подразумевается что в этот параметр передается модифицированный текст (шифрованный/расшифрованный).
     * @throws IOException Бросит исключение, если файл по указанному пути объекта будет отсутствовать.
     */
    public void pushTextToFile(String text) throws IOException {
        try (FileWriter fw = new FileWriter(outputPathFile)) {
            fw.write(text);
        }
    }
}
