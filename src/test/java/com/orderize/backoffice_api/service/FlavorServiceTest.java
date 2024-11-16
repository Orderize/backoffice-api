package com.orderize.backoffice_api.service;

import java.math.BigDecimal;
import java.time.LocalDate;
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

import com.orderize.backoffice_api.dto.flavor.FlavorRequestDto;
import com.orderize.backoffice_api.dto.flavor.FlavorResponseDto;
import com.orderize.backoffice_api.dto.ingredient.IngredientResponseDto;
import com.orderize.backoffice_api.exception.AlreadyExistsException;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.flavor.FlavorRequestToFlavor;
import com.orderize.backoffice_api.mapper.flavor.FlavorToFlavorResponseDto;
import com.orderize.backoffice_api.model.Flavor;
import com.orderize.backoffice_api.model.Ingredient;
import com.orderize.backoffice_api.repository.FlavorRepository;

public class FlavorServiceTest {

    @Mock
    private FlavorRepository flavorRepository;

    @Mock
    private FlavorRequestToFlavor flavorRequestToFlavor;

    @Mock
    private FlavorToFlavorResponseDto flavorToFlavorResponseDto;

    @Mock
    private List<Ingredient> ingredients;

    @Mock
    private List<IngredientResponseDto> ingredientResponseDtos;

    @InjectMocks
    private FlavorService flavorService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Ao salvar um Flavor válido")
    void testSaveFlavor_Success() {
        FlavorRequestDto requestDto = new FlavorRequestDto("Calabresa", "Coberta com fatias de calabresa defumada, cebola e azeitonas pretas.", BigDecimal.valueOf(13.43));
        Flavor flavor = new Flavor("Calabresa", "Coberta com fatias de calabresa defumada, cebola e azeitonas pretas.", BigDecimal.valueOf(13.43), LocalDate.parse("2020-04-20"), ingredients);
        FlavorResponseDto responseDto = new FlavorResponseDto(1L, "Calabresa", "Coberta com fatias de calabresa defumada, cebola e azeitonas pretas.", BigDecimal.valueOf(13.43), LocalDate.parse("2020-04-20"), ingredientResponseDtos);
        
        when(flavorRequestToFlavor.map(requestDto)).thenReturn(flavor);
        when(flavorRepository.save(flavor)).thenReturn(flavor);
        when(flavorToFlavorResponseDto.map(flavor)).thenReturn(responseDto);
        
        FlavorResponseDto result = flavorService.saveFlavor(requestDto);
        
        assertEquals(1L, result.id(), "O ID do FlavorResponseDto deveria ser 1");
        assertEquals("Calabresa", result.name(), "O nome deveria ser 'Calabresa'");
        assertEquals("Coberta com fatias de calabresa defumada, cebola e azeitonas pretas.", result.description(), "A descrição não corresponde ao esperado");
        assertEquals(LocalDate.parse("2020-04-20"), result.registered(), "A data de registro deveria ser 2020-04-20");
        assertEquals(ingredientResponseDtos, result.ingredients(), "A lista de ingredientes não correponde ao esperado");
        
        verify(flavorRequestToFlavor).map(requestDto);
        verify(flavorRepository).save(flavor);
        verify(flavorToFlavorResponseDto).map(flavor);
    }
    
    @Test
    @DisplayName("Ao salvar um Flavor que já existe pelo nome")
    void testSaveFlavor_AlreadyExistsByName() {
        FlavorRequestDto requestDto = new FlavorRequestDto("Calabresa", "Coberta com fatias de calabresa defumada, cebola e azeitonas pretas.", BigDecimal.valueOf(13.43));
        Flavor flavor = new Flavor("Calabresa", "Coberta com fatias de calabresa defumada, cebola e azeitonas pretas.", BigDecimal.valueOf(13.43), LocalDate.parse("2020-04-20"), ingredients);

        when(flavorRequestToFlavor.map(requestDto)).thenReturn(flavor);
        when(flavorRepository.existsByName("Calabresa")).thenReturn(true);

        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> 
            flavorService.saveFlavor(requestDto), "Esperava-se que AlreadyExistsException fosse lançada"
        );

        assertEquals("Já existe um sabor com este nome", exception.getMessage());
        
        verify(flavorRepository).existsByName("Calabresa");
        verify(flavorRepository, never()).save(any());
    }

    @Test
    @DisplayName("Ao atualizar um Flavor válida")
    void testUpdateFlavor_Success() {
        FlavorRequestDto requestDto = new FlavorRequestDto("Calabresa", "Coberta com fatias de calabresa defumada, cebola e azeitonas pretas.", BigDecimal.valueOf(13.43));
        Flavor updatedFlavor = new Flavor("Calabresa", "Coberta com fatias de calabresa defumada.", BigDecimal.valueOf(13.43), LocalDate.parse("2020-03-20"), ingredients);
        FlavorResponseDto responseDto = new FlavorResponseDto(1L, "Calabresa", "Coberta com fatias de calabresa defumada.", BigDecimal.valueOf(13.43), LocalDate.parse("2020-03-20"), ingredientResponseDtos);

        when(flavorRepository.findById(1L)).thenReturn(Optional.of(updatedFlavor));
        when(flavorRequestToFlavor.map(updatedFlavor, requestDto)).thenReturn(updatedFlavor);
        when(flavorRepository.save(updatedFlavor)).thenReturn(updatedFlavor);
        when(flavorToFlavorResponseDto.map(updatedFlavor)).thenReturn(responseDto);

        FlavorResponseDto result = flavorService.updateFlavor(1L, requestDto);

        assertEquals(1L, result.id(), "O ID do FlavorResponseDto deveria ser 1");
        assertEquals("Calabresa", result.name(), "O nome deveria ser 'Calabresa'");
        assertEquals("Coberta com fatias de calabresa defumada.", result.description(), "A descrição não corresponde ao esperado");
        assertEquals(LocalDate.parse("2020-03-20"), result.registered(), "A data de registro deveria ser 2020-04-20");
        assertEquals(ingredientResponseDtos, result.ingredients(), "A lista de ingredientes não correponde ao esperado");
        
        verify(flavorRepository).findById(1l);
        verify(flavorRequestToFlavor).map(updatedFlavor, requestDto);
        verify(flavorRepository).save(updatedFlavor);
        verify(flavorToFlavorResponseDto).map(updatedFlavor);
    }

    @Test
    @DisplayName("Ao atualizar um Flavor que não existe")
    void testUpdateFlavor_NotFound() {
        FlavorRequestDto requestDto = new FlavorRequestDto("Calabresa", "Coberta com fatias de calabresa defumada, cebola e azeitonas pretas.", BigDecimal.valueOf(13.43));

        when(flavorRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            flavorService.updateFlavor(1L, requestDto);
        });

        assertEquals("Sabor não encontrado", exception.getMessage());
        
        verify(flavorRepository).findById(1L);
        verify(flavorRepository, never()).save(any());
    }

    // TODO: Refazer teste
//    @Test
//    @DisplayName("Ao buscar todos os Flavors")
//    void testGetAllFlavors_Success() {
//        Flavor flavor1 = new Flavor("Margherita", "Pizza tradicional com molho de tomate, mussarela de búfala e manjericão fresco.",  BigDecimal.valueOf(13.43), LocalDate.parse("2020-04-20"), ingredients);
//        Flavor flavor2 = new Flavor("Calabresa", "Coberta com fatias de calabresa defumada, cebola e azeitonas pretas.", BigDecimal.valueOf(13.43), LocalDate.parse("2020-05-20"), ingredients);
//        FlavorResponseDto responseDto1 = new FlavorResponseDto(1L, "Margherita", "Pizza tradicional com molho de tomate, mussarela de búfala e manjericão fresco.", BigDecimal.valueOf(13.43),  LocalDate.parse("2020-04-20"), ingredientResponseDtos);
//        FlavorResponseDto responseDto2 = new FlavorResponseDto(2L, "Calabresa", "Coberta com fatias de calabresa defumada, cebola e azeitonas pretas.", BigDecimal.valueOf(13.43), LocalDate.parse("2020-05-20"), ingredientResponseDtos);
//
//        when(flavorRepository.findAll()).thenReturn(List.of(flavor1, flavor2));
//        when(flavorToFlavorResponseDto.map(flavor1)).thenReturn(responseDto1);
//        when(flavorToFlavorResponseDto.map(flavor2)).thenReturn(responseDto2);
//
//        List<FlavorResponseDto> flavors = flavorService.getAllFlavors();
//
//        assertEquals(2, flavors.size(), "O tamanho da lista esperado é 2");
//        assertEquals(1L, flavors.get(0).id(), "O ID do FlavorResponseDto na posição (0) deveria ser 1");
//        assertEquals(2L, flavors.get(1).id(), "O ID do FlavorResponseDto na posição (1) deveria ser 2");
//        assertEquals("Margherita", flavors.get(0).name(), "O nome deveria ser 'Margherita'");
//        assertEquals("Calabresa", flavors.get(1).name(), "O nome deveria ser 'Calabresa'");
//
//        verify(flavorRepository).findAll();
//    }

    // TODO: Refazer teste
//    @Test
//    @DisplayName("Ao buscar um Flavor pelo ID com sucesso")
//    void testGetFlavorById_Success() {
//        Flavor flavor = new Flavor("Margherita", "Pizza tradicional com molho de tomate, mussarela de búfala e manjericão fresco.",  BigDecimal.valueOf(13.43), LocalDate.parse("2020-04-20"), ingredients);
//        FlavorResponseDto responseDto = new FlavorResponseDto(1L, "Margherita", "Pizza tradicional com molho de tomate, mussarela de búfala e manjericão fresco.",  BigDecimal.valueOf(13.43), LocalDate.parse("2020-04-20"), ingredientResponseDtos);
//
//        when(flavorRepository.findById(1L)).thenReturn(Optional.of(flavor));
//        when(flavorToFlavorResponseDto.map(flavor)).thenReturn(responseDto);
//
//        FlavorResponseDto result = flavorService.getFlavorById(1L);
//
//        assertEquals(1L, result.id());
//        assertEquals("Margherita", result.name());
//        assertEquals(ingredientResponseDtos, result.ingredients());
//
//        verify(flavorRepository).findById(1L);
//    }

    @Test
    @DisplayName("Ao buscar uma Flavor pelo ID inexistente")
    void testGetFlavorById_NotFound() {
        when(flavorRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> 
            flavorService.getFlavorById(1L), "Esperava-se que ResourceNotFoundException fosse lançada"
        );

        assertEquals("Sabor não encontrado", exception.getMessage());
        verify(flavorRepository).findById(1L);
    }


    @Test
    @DisplayName("Ao excluir um Flavor válida")
    void testDeleteFlavor_Success() {
        when(flavorRepository.existsById(1L)).thenReturn(true);

        flavorService.deleteFlavor(1L);

        verify(flavorRepository).existsById(1L);
        verify(flavorRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Ao excluir uma Flavor que não existe")
    void testDeleteFlavor_NotFound() {
        when(flavorRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> 
            flavorService.deleteFlavor(1L), "Esperava-se que ResourceNotFoundException fosse lançada"
        );

        assertEquals("Sabor não encontrado", exception.getMessage());
        verify(flavorRepository).existsById(1L);
        verify(flavorRepository, never()).deleteById(1L);
    }

}
