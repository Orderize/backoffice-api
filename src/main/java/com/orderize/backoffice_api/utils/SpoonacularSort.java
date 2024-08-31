package com.orderize.backoffice_api.utils;

import java.util.List;
import java.util.Random;

import com.orderize.backoffice_api.external.spoonacular.dto.SpoonacularResponseDto;

public class SpoonacularSort implements SortStrategy<SpoonacularResponseDto> {
    
    private SpoonacularResponseDto[] itens;
    
    public SpoonacularSort() {
    }

    public SpoonacularSort(SpoonacularResponseDto[] itens) {
        this.itens = itens;
    }

    public SpoonacularSort(List<SpoonacularResponseDto> itens) {
        this.itens = itens.toArray(new SpoonacularResponseDto[0]);
    }

    @Override
    public SpoonacularResponseDto[] sort() {
        if (itens == null || itens.length == 0) return null;
    
        int low = 0;
        int high = this.itens.length-1;
        
        quickSort(low, high);
    
        return itens;
    }

    private void quickSort(int low, int high) {
        int mid = new Random().nextInt(high - low + 1) + low;
        String pivot = this.itens[mid].title();
        int l = low;
        int h = high;

        while (l <= h) {
            // Ponteiro l é incrementado 1 enquanto for menor que o pivo 
            while (this.itens[l].title().compareTo(pivot) < 0) l++;
            // Ponteiro h é decrementado 1 enquanto for maior ou igual ao pivo 
            while (this.itens[h].title().compareTo(pivot) > 0) h--;

            if (l <= h) {
                SpoonacularResponseDto tempItem = this.itens[l];
                this.itens[l] = this.itens[h];
                this.itens[h] = tempItem;

                l++;
                h--; 
            }
        }

        if (low < h) quickSort(low, h);
        if (l < high) quickSort(l, high);
    }
}
