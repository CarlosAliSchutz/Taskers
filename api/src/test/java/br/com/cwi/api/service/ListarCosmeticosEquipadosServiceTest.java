package br.com.cwi.api.service;

import br.com.cwi.api.factories.CosmeticoFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.controller.response.CosmeticoResponse;
import br.com.cwi.crescer.api.domain.Cosmetico;
import br.com.cwi.crescer.api.repository.CosmeticoRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.ListarCosmeticosEquipadosService;
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
public class ListarCosmeticosEquipadosServiceTest {

    @InjectMocks
    private ListarCosmeticosEquipadosService tested;

    @Mock
    private CosmeticoRepository cosmeticoRepository;

    @Mock
    private UsuarioAutenticadoService autenticadoService;

    @Test
    @DisplayName("Deve listar todos os cosmeticos equipados")
    void deveListarCosmeticosEquipados() {
        Usuario usuario = UsuarioFactory.getUsuario();
        List<Cosmetico> cosmeticos = CosmeticoFactory.getLista();

        when(autenticadoService.getId()).thenReturn(usuario.getId());
        when(cosmeticoRepository.buscarCosmeticosEquipados(usuario.getId())).thenReturn(cosmeticos);

        List<CosmeticoResponse> response = tested.listar();

        verify(cosmeticoRepository).buscarCosmeticosEquipados(usuario.getId());
        verify(autenticadoService).getId();

        assertEquals(cosmeticos.size(), response.size());
        assertEquals(cosmeticos.get(0).getId(), response.get(0).getId());
        assertEquals(cosmeticos.get(1).getId(), response.get(1).getId());
        assertEquals(cosmeticos.get(2).getId(), response.get(2).getId());
    }

    @Test
    @DisplayName("Deve retornar lista vazia caso não houver cosmeticos equipados")
    void deveRetornarListaVazia() {
        Usuario usuario = UsuarioFactory.getUsuario();
        List<Cosmetico> cosmeticos = CosmeticoFactory.getListaVazia();

        when(autenticadoService.getId()).thenReturn(usuario.getId());
        when(cosmeticoRepository.buscarCosmeticosEquipados
                (usuario.getId())).thenReturn(cosmeticos);

        List<CosmeticoResponse> response = tested.listar();

        verify(cosmeticoRepository).buscarCosmeticosEquipados
                (usuario.getId());
        verify(autenticadoService).getId();

        assertEquals(cosmeticos.size(), response.size());
    }
}
