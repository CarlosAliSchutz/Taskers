package br.com.cwi.api.util;

import br.com.cwi.api.factories.AfazerFactory;
import br.com.cwi.api.factories.DiaFactory;
import br.com.cwi.api.factories.DiariaFactory;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.domain.Dia;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.util.GeraAfazerParaCalendarioGoogle;
import br.com.cwi.crescer.api.util.GeraDiariaParaCalendarioGoogle;
import com.google.api.services.calendar.model.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GeraAfazerParaCalendarioGoogleTest {

    @Test
    @DisplayName("Deve Gerar Um Afazer Para O Calendario Do Google")
    void deveGerarUmAfazerFacilParaOCalendarioDoGoogle() {
        Afazer afazer = AfazerFactory.getAfazerFacil();

        Event afazerParaCalendario = GeraAfazerParaCalendarioGoogle.gerar(afazer);

        assertEquals(afazer.getNome(), afazerParaCalendario.getSummary());
        assertEquals(afazer.getDescricao(), afazerParaCalendario.getDescription());
    }
}
