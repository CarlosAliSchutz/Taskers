package br.com.cwi.api.service;

import br.com.cwi.api.factories.CosmeticoFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.controller.response.CosmeticoResponse;
import br.com.cwi.crescer.api.domain.Cosmetico;
import br.com.cwi.crescer.api.repository.CosmeticoRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.ListarCosmeticosDisponiveisService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarCosmeticosDisponiveisServiceTest {

    @InjectMocks
    private ListarCosmeticosDisponiveisService tested;

    @Mock
    private CosmeticoRepository cosmeticoRepository;

    @Mock
    private UsuarioAutenticadoService autenticadoService;

    @Test
    @DisplayName("Deve listar todos os cosmeticos disponiveis")
    void deveListarCosmeticos() {
        Usuario usuario = UsuarioFactory.getUsuario();
        Pageable pageable = PageRequest.of(0,5);
        List<Cosmetico> cosmeticos = CosmeticoFactory.getLista();
        Page<Cosmetico> cosmeticosPaginadas = new PageImpl<>(cosmeticos);

        when(autenticadoService.getId()).thenReturn(usuario.getId());
        when(cosmeticoRepository.buscarCosmeticosDisponiveisParaCompra
                ( usuario.getId(), pageable)).thenReturn(cosmeticosPaginadas);

        Page<CosmeticoResponse> response = tested.listar(pageable);

        verify(cosmeticoRepository).buscarCosmeticosDisponiveisParaCompra
                ( usuario.getId(), pageable);
        verify(autenticadoService).getId();

        assertEquals(cosmeticos.size(), response.getSize());
        assertEquals(cosmeticos.get(0).getId(), response.getContent().get(0).getId());
        assertEquals(cosmeticos.get(1).getId(), response.getContent().get(1).getId());
        assertEquals(cosmeticos.get(2).getId(), response.getContent().get(2).getId());
    }

    @Test
    @DisplayName("Deve retornar lista vazia caso não houver cosmeticos disponiveis")
    void deveRetornarListaVazia() {
        Usuario usuario = UsuarioFactory.getUsuario();
        Pageable pageable = PageRequest.of(0,5);
        List<Cosmetico> cosmeticos = CosmeticoFactory.getListaVazia();
        Page<Cosmetico> cosmeticosPaginadas = new PageImpl<>(cosmeticos);

        when(autenticadoService.getId()).thenReturn(usuario.getId());
        when(cosmeticoRepository.buscarCosmeticosDisponiveisParaCompra
                (usuario.getId(), pageable)).thenReturn(cosmeticosPaginadas);

        Page<CosmeticoResponse> response = tested.listar(pageable);

        verify(cosmeticoRepository).buscarCosmeticosDisponiveisParaCompra
                ( usuario.getId(),pageable);
        verify(autenticadoService).getId();

        assertEquals(cosmeticos.size(), response.getSize());
    }
}
