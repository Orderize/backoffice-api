package com.orderize.backoffice_api.utils;

import java.util.Comparator;

public interface SortStrategy<T> {
    public T[] sort();
}
