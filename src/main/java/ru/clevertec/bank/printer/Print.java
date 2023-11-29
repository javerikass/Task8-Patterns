package ru.clevertec.bank.printer;

public interface Print<T> {

    void printToPDF(T object);

}
