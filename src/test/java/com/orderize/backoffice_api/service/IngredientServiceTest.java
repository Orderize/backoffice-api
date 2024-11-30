package com.orderize.backoffice_api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.orderize.backoffice_api.dto.ingredient.IngredientRequestDto;
import com.orderize.backoffice_api.dto.ingredient.IngredientResponseDto;
import com.orderize.backoffice_api.exception.AlreadyExistsException;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.ingredient.IngredientRequestDtoToIngredient;
import com.orderize.backoffice_api.mapper.ingredient.IngredientToIngredientResponseDto;
import com.orderize.backoffice_api.model.Ingredient;
import com.orderize.backoffice_api.repository.IngredientRepository;

public class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientRequestDtoToIngredient ingredientRequestDtoToIngredient;

    @Mock
    private IngredientToIngredientResponseDto ingredientToIngredientResponseDto;

    @InjectMocks
    private IngredientService ingredientService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Ao salvar um Ingredient válido")
    void testSaveIngredient_Success() {
        IngredientRequestDto requestDto = new IngredientRequestDto("Mozzarella Cheese", BigDecimal.valueOf(1.50));
        Ingredient ingredient = new Ingredient("Mozzarella Cheese", BigDecimal.valueOf(1.50));
        IngredientResponseDto responseDto = new IngredientResponseDto(1L, "Mozzarella Cheese", BigDecimal.valueOf(1.50));
        
        when(ingredientRequestDtoToIngredient.map(requestDto)).thenReturn(ingredient);
        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
        when(ingredientToIngredientResponseDto.map(ingredient)).thenReturn(responseDto);
        
        IngredientResponseDto result = ingredientService.saveIngredient(requestDto);
        
        assertEquals(1L, result.id(), "O ID do IngredientResponseDto deveria ser 1");
        assertEquals("Mozzarella Cheese", result.name(), "O nome deveria ser 'Mozzarella Cheese'");
        assertEquals(BigDecimal.valueOf(1.5), result.paid(), "A valor pago deveria ser 1.50");
        
        verify(ingredientRequestDtoToIngredient).map(requestDto);
        verify(ingredientRepository).save(ingredient);
        verify(ingredientToIngredientResponseDto).map(ingredient);
    }
    
    @Test
    @DisplayName("Ao salvar um Ingredient que já existe pelo nome")
    void testSaveIngredient_AlreadyExistsByName() {
        IngredientRequestDto requestDto = new IngredientRequestDto("Mozzarella Cheese", BigDecimal.valueOf(1.50));
        Ingredient ingredient = new Ingredient("Mozzarella Cheese", BigDecimal.valueOf(1.50));

        when(ingredientRequestDtoToIngredient.map(requestDto)).thenReturn(ingredient);
        when(ingredientRepository.existsByName("Mozzarella Cheese")).thenReturn(true);

        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> 
            ingredientService.saveIngredient(requestDto), "Esperava-se que AlreadyExistsException fosse lançada"
        );

        assertEquals("Já existe um ingrediente com este nome", exception.getMessage());
        
        verify(ingredientRepository).existsByName("Mozzarella Cheese");
        verify(ingredientRepository, never()).save(any());
    }

    @Test
    @DisplayName("Ao atualizar um Ingredient válida")
    void testUpdateIngredient_Success() {
        IngredientRequestDto requestDto = new IngredientRequestDto("Mozzarella Cheese", BigDecimal.valueOf(1.50));
        Ingredient updatedIngredient = new Ingredient("Mozzarella", BigDecimal.valueOf(1.30));
        IngredientResponseDto responseDto = new IngredientResponseDto(1L, "Mozzarella", BigDecimal.valueOf(1.30));


        when(ingredientRepository.existsById(1L)).thenReturn(true);
        when(ingredientRequestDtoToIngredient.map(requestDto)).thenReturn(updatedIngredient);
        when(ingredientRepository.save(updatedIngredient)).thenReturn(updatedIngredient);
        when(ingredientToIngredientResponseDto.map(updatedIngredient)).thenReturn(responseDto);

        IngredientResponseDto result = ingredientService.updateIngredient(1L, requestDto);

        assertEquals(1L, result.id(), "O ID do IngredientResponseDto deveria ser 1");
        assertEquals("Mozzarella", result.name(), "O nome deveria ser 'Mozzarella'");
        assertEquals(BigDecimal.valueOf(1.3), result.paid(), "O valor pago deveria ser 1.30");
        
        verify(ingredientRepository).existsById(1l);
        verify(ingredientRequestDtoToIngredient).map(requestDto);
        verify(ingredientRepository).save(updatedIngredient);
        verify(ingredientToIngredientResponseDto).map(updatedIngredient);
    }

    @Test
    @DisplayName("Ao atualizar um Ingredient que não existe")
    void testUpdateIngredient_NotFound() {
        IngredientRequestDto requestDto = new IngredientRequestDto("Mozzarella Cheese", BigDecimal.valueOf(1.50));


        when(ingredientRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            ingredientService.updateIngredient(1L, requestDto);
        });

        assertEquals("Ingrediente não encontrado", exception.getMessage());
        
        verify(ingredientRepository).existsById(1L);
        verify(ingredientRepository, never()).save(any());
    }

    @Test
    @DisplayName("Ao buscar todos os Ingredients")
    void testGetAllIngredients_Success() {
        Ingredient ingredient1 = new Ingredient("Mozzarella", BigDecimal.valueOf(1.30));
        Ingredient ingredient2 = new Ingredient("Pepperoni", BigDecimal.valueOf(2.00));
        IngredientResponseDto responseDto1 = new IngredientResponseDto(1L, "Mozzarella", BigDecimal.valueOf(1.30));
        IngredientResponseDto responseDto2 = new IngredientResponseDto(2L, "Pepperoni", BigDecimal.valueOf(2.00));

        when(ingredientRepository.findAll()).thenReturn(List.of(ingredient1, ingredient2));
        when(ingredientToIngredientResponseDto.map(ingredient1)).thenReturn(responseDto1);
        when(ingredientToIngredientResponseDto.map(ingredient2)).thenReturn(responseDto2);

        List<IngredientResponseDto> ingredients = ingredientService.getAllIngredients();

        assertEquals(2, ingredients.size(), "O tamanho da lista esperado é 2");
        assertEquals(1L, ingredients.get(0).id(), "O ID do IngredientResponseDto na posição (0) deveria ser 1");
        assertEquals(2L, ingredients.get(1).id(), "O ID do IngredientResponseDto na posição (1) deveria ser 2");
        assertEquals("Mozzarella", ingredients.get(0).name(), "O nome deveria ser 'Mozzarella'");
        assertEquals("Pepperoni", ingredients.get(1).name(), "O nome deveria ser 'Pepperoni'");

        verify(ingredientRepository).findAll();
    }

    @Test
    @DisplayName("Ao buscar um Ingredient pelo ID com sucesso")
    void testGetIngredientById_Success() {
        Ingredient ingredient = new Ingredient("Mozzarella", BigDecimal.valueOf(1.30));
        IngredientResponseDto responseDto = new IngredientResponseDto(1L, "Mozzarella", BigDecimal.valueOf(1.30));

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(ingredientToIngredientResponseDto.map(ingredient)).thenReturn(responseDto);

        IngredientResponseDto result = ingredientService.getIngredientById(1L);

        assertEquals(1L, result.id(), "O ID do IngredientResponseDto deveria ser 1");
        assertEquals("Mozzarella", result.name(), "O nome deveria ser 'Mozzarella'");
        assertEquals(BigDecimal.valueOf(1.30), result.paid(), "O valor pago deveria ser 1.30");
        
        verify(ingredientRepository).findById(1L);
    }

    @Test
    @DisplayName("Ao buscar uma Ingredient pelo ID inexistente")
    void testGetIngredientById_NotFound() {
        when(ingredientRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> 
            ingredientService.getIngredientById(1L), "Esperava-se que ResourceNotFoundException fosse lançada"
        );

        assertEquals("Ingrediente não encontrado", exception.getMessage());
        verify(ingredientRepository).findById(1L);
    }


    @Test
    @DisplayName("Ao excluir um Ingredient válida")
    void testDeleteIngredient_Success() {
        when(ingredientRepository.existsById(1L)).thenReturn(true);

        ingredientService.deleteIngredient(1L);

        verify(ingredientRepository).existsById(1L);
        verify(ingredientRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Ao excluir uma Ingredient que não existe")
    void testDeleteIngredient_NotFound() {
        when(ingredientRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> 
            ingredientService.deleteIngredient(1L), "Esperava-se que ResourceNotFoundException fosse lançada"
        );

        assertEquals("Ingrediente não encontrado", exception.getMessage());
        verify(ingredientRepository).existsById(1L);
        verify(ingredientRepository, never()).deleteById(1L);
    }

}
