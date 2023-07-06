package br.com.cwi.api.service;

import br.com.cwi.api.factories.MissaoFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.controller.response.MissaoResponse;
import br.com.cwi.crescer.api.domain.MissaoAfazer;
import br.com.cwi.crescer.api.domain.MissaoDiaria;
import br.com.cwi.crescer.api.domain.MissaoHabito;
import br.com.cwi.crescer.api.repository.MissaoAfazerRepository;
import br.com.cwi.crescer.api.repository.MissaoDiariaRepository;
import br.com.cwi.crescer.api.repository.MissaoHabitoRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.ListarMissoesService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarMissoesServiceTest {

    @InjectMocks
    private ListarMissoesService tested;

    @Mock
    private UsuarioAutenticadoService autenticadoService;

    @Mock
    private MissaoAfazerRepository missaoAfazerRepository;

    @Mock
    private MissaoDiariaRepository missaoDiariaRepository;

    @Mock
    private MissaoHabitoRepository missaoHabitoRepository;

    @Test
    @DisplayName("Deve retornar lista de responses")
    void deveRetornarListaDeResponses() {
        Usuario usuario = UsuarioFactory.getUsuario();

        MissaoAfazer missaoAfazer = MissaoFactory.getMissaoAfazer();
        MissaoDiaria missaoDiaria = MissaoFactory.getMissaoDiaria();
        MissaoHabito missaoHabito = MissaoFactory.getMissaoHabito();

        when(autenticadoService.getId()).thenReturn(usuario.getId());

        when(missaoAfazerRepository.findByProprietarioId(usuario.getId())).thenReturn(missaoAfazer);
        when(missaoHabitoRepository.findByProprietarioId(usuario.getId())).thenReturn(missaoHabito);
        when(missaoDiariaRepository.findByProprietarioId(usuario.getId())).thenReturn(missaoDiaria);

        List<MissaoResponse> response = tested.listar();

        verify(missaoAfazerRepository).findByProprietarioId(usuario.getId());
        verify(missaoHabitoRepository).findByProprietarioId(usuario.getId());
        verify(missaoDiariaRepository).findByProprietarioId(usuario.getId());
        verify(autenticadoService).getId();

        assertEquals(missaoAfazer.getId(), response.get(0).getId());
        assertEquals(missaoDiaria.getId(), response.get(2).getId());
        assertEquals(missaoHabito.getId(), response.get(1).getId());
    }

    @Test
    @DisplayName("Deve retornar lista de responses Incompleta Quando Um Valor For Nulo")
    void deveRetornarListaDeResponsesIncompletaQuandoUmValorForNulo() {
        Usuario usuario = UsuarioFactory.getUsuario();

        MissaoAfazer missaoAfazer = MissaoFactory.getMissaoAfazer();
        MissaoHabito missaoHabito = MissaoFactory.getMissaoHabito();

        when(autenticadoService.getId()).thenReturn(usuario.getId());

        when(missaoAfazerRepository.findByProprietarioId(usuario.getId())).thenReturn(missaoAfazer);
        when(missaoDiariaRepository.findByProprietarioId(usuario.getId())).thenReturn(null);
        when(missaoHabitoRepository.findByProprietarioId(usuario.getId())).thenReturn(missaoHabito);

        List<MissaoResponse> response = tested.listar();

        verify(missaoAfazerRepository).findByProprietarioId(usuario.getId());
        verify(missaoHabitoRepository).findByProprietarioId(usuario.getId());
        verify(missaoDiariaRepository).findByProprietarioId(usuario.getId());
        verify(autenticadoService).getId();

        assertEquals(missaoAfazer.getId(), response.get(0).getId());
        assertEquals(missaoHabito.getId(), response.get(1).getId());
        assertEquals(2, response.size());
    }

    @Test
    @DisplayName("Deve retornar lista de responses")
    void deveRetornarListaVazia() {
        Usuario usuario = UsuarioFactory.getUsuario();

        when(autenticadoService.getId()).thenReturn(usuario.getId());

        when(missaoAfazerRepository.findByProprietarioId(usuario.getId())).thenReturn(null);
        when(missaoHabitoRepository.findByProprietarioId(usuario.getId())).thenReturn(null);
        when(missaoDiariaRepository.findByProprietarioId(usuario.getId())).thenReturn(null);

        List<MissaoResponse> response = tested.listar();

        verify(missaoAfazerRepository).findByProprietarioId(usuario.getId());
        verify(missaoHabitoRepository).findByProprietarioId(usuario.getId());
        verify(missaoDiariaRepository).findByProprietarioId(usuario.getId());
        verify(autenticadoService).getId();

        assertEquals(0, response.size());
    }

}
