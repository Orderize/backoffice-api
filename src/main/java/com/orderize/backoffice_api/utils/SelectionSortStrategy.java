package com.orderize.backoffice_api.utils;

public class SelectionSortStrategy<T extends Comparable<T>> implements SortStrategy<T>  {
    
    T[] itens;

    public SelectionSortStrategy(){}

    public SelectionSortStrategy(T[] itens) {
        this.itens = itens;
    }

    @Override
    public T[] sort() {
        for (int i = 0; i < itens.length-1; i++) {
            int min = i;
            for (int j = i + 1; j < itens.length; j++) {
                if (this.itens[j].compareTo(this.itens[i]) < 0) {
                    min = j;
                }
            }
            T tempItem = this.itens[i];
            this.itens[i] = this.itens[min];
            this.itens[min] = tempItem;
        }
        return itens;
    }

}
