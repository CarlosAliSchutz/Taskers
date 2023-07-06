package br.com.cwi.api.service;

import br.com.cwi.api.factories.CosmeticoFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.domain.Cosmetico;
import br.com.cwi.crescer.api.domain.CosmeticosPadroes;
import br.com.cwi.crescer.api.repository.CosmeticoUsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.service.IncluirCosmeticoPadraoUsuarioService;
import br.com.cwi.crescer.api.service.core.BuscarCosmeticoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IncluirCosmeticoPadraoUsuarioServiceTest {

    @InjectMocks
    private IncluirCosmeticoPadraoUsuarioService tested;

    @Mock
    private BuscarCosmeticoService buscarCosmeticoService;

    @Mock
    private CosmeticoUsuarioRepository cosmeticoUsuarioRepository;
    
    @Test
    @DisplayName("Deve incluir cosmeticos padroes")
    void deveIncluirCosmeticosPadroes() {
        Usuario usuario = UsuarioFactory.getUsuario();
        Cosmetico cosmetico = CosmeticoFactory.getCosmeticoRoupa();

        List<String> nomesCosmeticosPadroesEquipados = Arrays.asList(
                CosmeticosPadroes.HOMEMPADRAO.getNome(),
                CosmeticosPadroes.PLANTAPADRAO.getNome(),
                CosmeticosPadroes.PETPADRAO.getNome(),
                CosmeticosPadroes.FUNDOPADRAO.getNome(),
                CosmeticosPadroes.MULHERPADRAO.getNome()
        );

        nomesCosmeticosPadroesEquipados.forEach(nomeCosmetico -> {
            when(buscarCosmeticoService.porNome(nomeCosmetico)).thenReturn(cosmetico);
        });

        tested.incluir(usuario);

        verify(cosmeticoUsuarioRepository).saveAll(any());
    }

}
