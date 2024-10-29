package com.orderize.backoffice_api.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class List__c <T> {
    T[] vetor;
    int index; 

    @SuppressWarnings("unchecked")
    public List__c(List<T> listType) {
        this.vetor = (T[]) listType.toArray();
        this.index = listType.size();
    }

    @SuppressWarnings("unchecked")
    public List__c(Integer size) {
        this.vetor = (T[]) new Object[size];
        this.index = 0;
    }

    @SuppressWarnings("unchecked")
    public List__c() {
        this.vetor = (T[]) new Object[10];
        this.index = 0;
    }

    public void add(T type) {
        if (index == vetor.length) {
            System.out.println("Limite máximo atingido");
        } else {
            this.vetor[index++] = type;
        }
    }

    public void remove(int idx) {
        if (idx < 0 || idx >= this.vetor.length) { 
            System.out.println("Index inválido");
            return;
        }
        for (int i = idx; i < index-1; i++) {
            this.vetor[i] = this.vetor[i + 1];
        }
    }

    public T get(int idx) {
        if (idx < 0 || idx >= this.vetor.length) {
            System.out.println("Index inválido");
            return null;
        }
        return this.vetor[idx];
    }

    public int get(T type) {
        for (int i = 0; i < index; i++) {
            T obj = this.vetor[i];
            if (obj.equals(type)) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return index;
    }

    public List<T> toList() {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < index; i++) {
            list.add(this.vetor[i]);
        }
        return list;
    }

    public boolean isEmpty() {
        return index == 0;
    }

    public boolean isFull() {
        return index == this.vetor.length;
    }

    public void sort(Comparator<T> comparator) {
        QuickSortStrategy<T> quick = new QuickSortStrategy<T>(comparator);
        quick.sort(vetor);
    }

}