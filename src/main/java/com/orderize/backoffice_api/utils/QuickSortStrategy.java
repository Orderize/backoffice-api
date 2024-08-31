package com.orderize.backoffice_api.utils;

import java.util.Random;

public class QuickSortStrategy<T extends Comparable<T>> implements SortStrategy<T> {

    private T[] itens;

    public QuickSortStrategy(){}

    public QuickSortStrategy(T[] itens) {
        this.itens = itens;
    }

    @Override
    public T[] sort() {
        if (itens == null || itens.length == 0) return null;

        int low = 0;
        int high = this.itens.length-1;
        
        quickSort(low, high);
        return itens;
    }

    private void quickSort(int low, int high) {
        int mid = new Random().nextInt(high - low + 1) + low;
        T pivot = this.itens[mid];
        int l = low;
        int h = high;

        while (l <= h) {
            // Ponteiro l é incrementado 1 enquanto for menor que o pivo 
            while (this.itens[l].compareTo(pivot) < 0) l++;
            // Ponteiro h é decrementado 1 enquanto for maior ou igual ao pivo 
            while (this.itens[h].compareTo(pivot) > 0) h--;

            if (l <= h) {
                T tempItem = this.itens[l];
                this.itens[l] = this.itens[h];
                this.itens[h] = tempItem;

                l++;
                h--; 
            }
        }

        // Se a posição low for menor que o ponteiro h, chama o quickSort com a primeira posição sendo o low e a última sendo o ponteiro h 
        if (low < h) quickSort(low, h);
        // Se o ponteiro l for menor que a posição high, chama o quickSort com a primeira posição sendo o l e a última sendo a posição high 
        if (l < high) quickSort(l, high);
    }

}
