package com.orderize.backoffice_api.utils;

import java.util.Comparator;
import java.util.Random;

public class QuickSortStrategy<T> {

    private final Comparator<T> comparator;

    public QuickSortStrategy(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void sort(T[] vetor) {
        if (vetor == null || vetor.length == 0) {
            System.out.println("Vetor inválido");
            return;
        }

        int low = 0;
        int high = vetor.length-1;
        
        quickSort(vetor, low, high);
    }

    private void quickSort(T[] vetor, int low, int high) {
        int mid = new Random().nextInt(high - low + 1) + low;
        T pivot = vetor[mid];
        int l = low;
        int h = high;

        while (l <= h) {
            // Ponteiro l é incrementado 1 enquanto for menor que o pivo 
            while (comparator.compare(vetor[l],pivot) < 0) l++;
            // Ponteiro h é decrementado 1 enquanto for maior ou igual ao pivo 
            while (comparator.compare(vetor[h], pivot) > 0) h--;

            if (l <= h) {
                swap(vetor, l, h);
                l++;
                h--; 
            }
        }

        // Se a posição low for menor que o ponteiro h, chama o quickSort com a primeira posição sendo o low e a última sendo o ponteiro h 
        if (low < h) quickSort(vetor, low, h);
        // Se o ponteiro l for menor que a posição high, chama o quickSort com a primeira posição sendo o l e a última sendo a posição high 
        if (l < high) quickSort(vetor, l, high);
    }

    private void swap(T[] vetor, int low, int high) {
        T auxItem = vetor[low];
        vetor[low] = vetor[high];
        vetor[high] = auxItem;
    }
}
