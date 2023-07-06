package br.com.cwi.api.service;

import br.com.cwi.api.factories.*;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.domain.Dia;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.repository.DiariaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.service.AlterarDiariaGoogleCalendarService;
import br.com.cwi.crescer.api.service.GerarCalendarioGoogleApiService;
import br.com.cwi.crescer.api.service.IncluirDiariaGoogleCalendarService;
import com.google.api.services.calendar.Calendar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IncluirDiariaGoogleCalendarServiceTest {

    @InjectMocks
    private IncluirDiariaGoogleCalendarService tested;

    @Mock
    private DiariaRepository diariaRepository;
    @Mock
    private GerarCalendarioGoogleApiService gerarCalendarioGoogleApi;

    @Test
    @DisplayName("Nao Deve Incluir Diaria No Google Calendar Quando Nao Possuir Conexao Com Api")
    void naoDeveIncluirAfazerNoGoogleCalendarQuandoNaoPossuirConexaoComApi() {
        Diaria diaria = DiariaFactory.getDiariaFacil();
        List<Dia> dias = DiaFactory.getLista();
        diaria.setDias(dias);
        Usuario usuario = UsuarioFactory.getUsuario();
        Calendar calendarioGoogle = CalendarGoogleApiFactory.getCalendar();

        when(gerarCalendarioGoogleApi.gerar()).thenReturn(calendarioGoogle);

        tested.incluir(usuario, diaria);

        verify(gerarCalendarioGoogleApi).gerar();
        verify(diariaRepository, never()).save(diaria);
    }

    @Test
    @DisplayName("Nao Deve Incluir Diaria No Google Calendar Quando Afazer Nao Possuir Integracao")
    void naoDeveIncluirDiariaNoGoogleCalendarQuandoAfazerNaoPossuirIntegracao() {
        Diaria diaria = DiariaFactory.getDiariaFacil();
        Usuario usuario = UsuarioFactory.getUsuarioComXP();

        tested.incluir(usuario, diaria);

        verify(gerarCalendarioGoogleApi, never()).gerar();
        verify(diariaRepository, never()).save(diaria);
    }
}
