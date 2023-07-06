package br.com.cwi.api.service.core;

import br.com.cwi.api.factories.AfazerFactory;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.repository.AfazerRepository;
import br.com.cwi.crescer.api.service.core.BuscarAfazerService;
import br.com.cwi.crescer.api.validator.FoiRemovidoValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuscarAfazerServiceTest {

    @InjectMocks
    private BuscarAfazerService tested;

    @Mock
    private AfazerRepository afazerRepository;

    @Mock
    private FoiRemovidoValidator foiRemovidoValidator;

    @Test
    @DisplayName("Deve buscar afazer por id")
    void deveRetornarAfazer() {
        Afazer afazer = AfazerFactory.getAfazerFacil();
        when(afazerRepository.findById(afazer.getId())).thenReturn(Optional.of(afazer));

        Afazer retorno = tested.porId(afazer.getId());

        verify(foiRemovidoValidator).validar(afazer.isAtivo());
        verify(afazerRepository).findById(afazer.getId());
        assertEquals(afazer, retorno);
    }

    @Test
    @DisplayName("Deve retornar erro quando não encontrar afazer")
    void deveRetornarErro() {

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.porId(1L));

        assertEquals("Afazer não encontrado", exception.getReason());
    }

}
