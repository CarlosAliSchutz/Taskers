package br.com.cwi.api.validator;

import br.com.cwi.crescer.api.validator.FoiRemovidoValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class FoiRemovidoValidatorTest {

    @InjectMocks
    private FoiRemovidoValidator tested;

    @Test
    @DisplayName("Deve dar erro quando for removido")
    void deveDarErroQuandoForRemovido() {

        tested.validar(true);
    }

    @Test
    @DisplayName("Deve dar erro quando nao for removido")
    void deveDarErroQuandoNaoForRemovido() {

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            tested.validar(false);
        });
    }

}
