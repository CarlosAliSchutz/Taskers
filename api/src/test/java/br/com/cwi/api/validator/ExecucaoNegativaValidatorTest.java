package br.com.cwi.api.validator;

import br.com.cwi.crescer.api.validator.ExecucaoNegativaValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class ExecucaoNegativaValidatorTest {

    @InjectMocks
    private ExecucaoNegativaValidator tested;

    @Test
    @DisplayName("Deve dar erro quando for zero")
    void deveDarErroQuandoForZero() {
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            tested.estaZerado(0);
        });
    }

    @Test
    @DisplayName("Nao Deve Dar Erro Quando Nao For Zero")
    void naoDeveDarErroQuandoNaoForZero() {

        tested.estaZerado(5);
    }

}
