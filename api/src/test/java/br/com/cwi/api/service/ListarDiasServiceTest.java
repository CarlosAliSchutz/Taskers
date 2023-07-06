package br.com.cwi.api.service;

import br.com.cwi.api.factories.AfazerFactory;
import br.com.cwi.api.factories.DiaFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.controller.response.AfazerResumidoResponse;
import br.com.cwi.crescer.api.controller.response.DiaResponse;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.domain.Dia;
import br.com.cwi.crescer.api.repository.AfazerRepository;
import br.com.cwi.crescer.api.repository.DiaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.ListarAfazeresService;
import br.com.cwi.crescer.api.service.ListarDiasService;
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
public class ListarDiasServiceTest {

    @InjectMocks
    private ListarDiasService tested;

    @Mock
    private DiaRepository diaRepository;

    @Test
    @DisplayName("Deve listar os dias")
    void deveListarOsDias() {
        Pageable pageable = PageRequest.of(0,5);
        List<Dia> dias = DiaFactory.getLista();
        Page<Dia> diasPaginados = new PageImpl<>(dias);

        when(diaRepository.findAll(pageable)).thenReturn(diasPaginados);

        Page<DiaResponse> response = tested.listar(pageable);

        assertEquals(dias.size(), response.getSize());
        assertEquals(dias.get(0).getId(), response.getContent().get(0).getId());
        assertEquals(dias.get(1).getId(), response.getContent().get(1).getId());
    }

    @Test
    @DisplayName("Deve listar lista de dias vazia")
    void deveListarListaDeDiasVazia() {
        Pageable pageable = PageRequest.of(0,5);
        List<Dia> dias = DiaFactory.getListaVazia();
        Page<Dia> diasPaginados = new PageImpl<>(dias);

        when(diaRepository.findAll(pageable)).thenReturn(diasPaginados);

        Page<DiaResponse> response = tested.listar(pageable);

        assertEquals(dias.size(), response.getSize());
    }

}
