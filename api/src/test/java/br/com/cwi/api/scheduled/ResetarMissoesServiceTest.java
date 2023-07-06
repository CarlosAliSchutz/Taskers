package br.com.cwi.api.scheduled;

import br.com.cwi.api.factories.AfazerFactory;
import br.com.cwi.api.factories.DiariaFactory;
import br.com.cwi.api.factories.HabitoFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.repository.*;
import br.com.cwi.crescer.api.scheduled.ResetarMissoesService;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
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

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResetarMissoesServiceTest {

    @InjectMocks
    private ResetarMissoesService tested;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private AfazerRepository afazerRepository;
    @Mock
    private HabitoRepository habitoRepository;
    @Mock
    private DiariaRepository diariaRepository;
    @Mock
    private MissaoAfazerRepository missaoAfazerRepository;
    @Mock
    private MissaoHabitoRepository missaoHabitoRepository;
    @Mock
    private MissaoDiariaRepository missaoDiariaRepository;

    @Test
    @DisplayName("Deve Resetar as missoes dos usuarios")
    void deveResetarMissaoDosUsuarios() {
        PageRequest filtroParaUmResultado = PageRequest.of(0, 1);

        Afazer afazer = AfazerFactory.getAfazerFacil();
        Diaria diaria = DiariaFactory.getDiariaFacil();
        Habito habito = HabitoFactory.getHabitoFacil();
        Page<Afazer> afazerPaginado = new PageImpl<>(Collections.singletonList(afazer));
        Page<Diaria> diariaPaginado = new PageImpl<>(Collections.singletonList(diaria));
        Page<Habito> habitoPaginado = new PageImpl<>(Collections.singletonList(habito));

        List<Usuario> usuarios = UsuarioFactory.getLista();
        Pageable pageable = PageRequest.of(0,100);
        Page<Usuario> usuarioPaginado = new PageImpl<>(usuarios);

        when(usuarioRepository.findAllByAtivoTrue(pageable)).thenReturn(usuarioPaginado);

        usuarios.forEach(usuario -> {
            when(afazerRepository.buscarAfazerDisponivelAleatorio(usuario.getId(), filtroParaUmResultado))
                    .thenReturn(afazerPaginado);
            when(habitoRepository.buscarHabitoDisponivelAleatorio(usuario.getId(), filtroParaUmResultado))
                    .thenReturn(habitoPaginado);
            when(diariaRepository.buscarDiariaDisponivelAleatoria(usuario.getId(), filtroParaUmResultado))
                    .thenReturn(diariaPaginado);
        });

        tested.resetar();

        verify(missaoAfazerRepository).deleteAll();
        verify(missaoHabitoRepository).deleteAll();
        verify(missaoDiariaRepository).deleteAll();

        usuarios.forEach(usuario -> {
            verify(afazerRepository).buscarAfazerDisponivelAleatorio(usuario.getId(), filtroParaUmResultado);
            verify(habitoRepository).buscarHabitoDisponivelAleatorio(usuario.getId(), filtroParaUmResultado);
            verify(diariaRepository).buscarDiariaDisponivelAleatoria(usuario.getId(), filtroParaUmResultado);
        });
        verify(missaoAfazerRepository, times(3)).save(any());
        verify(missaoHabitoRepository, times(3)).save(any());
        verify(missaoDiariaRepository, times(3)).save(any());
    }

    @Test
    @DisplayName("Nao Deve Criar Missoes Quando Nao Haver Tarefas")
    void naoDeveCriarMissoesQuandoNaoHaverTarefas() {
        PageRequest filtroParaUmResultado = PageRequest.of(0, 1);

        Page<Afazer> afazerPaginado = new PageImpl<>(AfazerFactory.getListaVazia());
        Page<Diaria> diariaPaginado = new PageImpl<>(DiariaFactory.getListaVazia());
        Page<Habito> habitoPaginado = new PageImpl<>(HabitoFactory.getListaVazia());

        List<Usuario> usuarios = UsuarioFactory.getLista();
        Pageable pageable = PageRequest.of(0,100);
        Page<Usuario> usuarioPaginado = new PageImpl<>(usuarios);

        when(usuarioRepository.findAllByAtivoTrue(pageable)).thenReturn(usuarioPaginado);

        usuarios.forEach(usuario -> {
            when(afazerRepository.buscarAfazerDisponivelAleatorio(usuario.getId(), filtroParaUmResultado))
                    .thenReturn(afazerPaginado);
            when(habitoRepository.buscarHabitoDisponivelAleatorio(usuario.getId(), filtroParaUmResultado))
                    .thenReturn(habitoPaginado);
            when(diariaRepository.buscarDiariaDisponivelAleatoria(usuario.getId(), filtroParaUmResultado))
                    .thenReturn(diariaPaginado);
        });

        tested.resetar();

        verify(missaoAfazerRepository).deleteAll();
        verify(missaoHabitoRepository).deleteAll();
        verify(missaoDiariaRepository).deleteAll();

        usuarios.forEach(usuario -> {
            verify(afazerRepository).buscarAfazerDisponivelAleatorio(usuario.getId(), filtroParaUmResultado);
            verify(habitoRepository).buscarHabitoDisponivelAleatorio(usuario.getId(), filtroParaUmResultado);
            verify(diariaRepository).buscarDiariaDisponivelAleatoria(usuario.getId(), filtroParaUmResultado);
        });
        verify(missaoAfazerRepository, never()).save(any());
        verify(missaoHabitoRepository, never()).save(any());
        verify(missaoDiariaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Nao Deve Lancar Erro Quando Lista De Usuarios Estiver Vazio")
    void naoDeveLancarErroQuandoListaDeUsuariosEstiverVazio() {
        List<Usuario> usuarios = UsuarioFactory.getListaVazia();
        Pageable pageable = PageRequest.of(0,100);
        Page<Usuario> usuarioVazioPaginado = new PageImpl<>(usuarios);

        when(usuarioRepository.findAllByAtivoTrue(pageable)).thenReturn(usuarioVazioPaginado);

        tested.resetar();

        verify(missaoAfazerRepository).deleteAll();
        verify(missaoHabitoRepository).deleteAll();
        verify(missaoDiariaRepository).deleteAll();

        verify(afazerRepository, never()).buscarAfazerDisponivelAleatorio(anyLong(), any());
        verify(habitoRepository, never()).buscarHabitoDisponivelAleatorio(anyLong(), any());
        verify(diariaRepository, never()).buscarDiariaDisponivelAleatoria(anyLong(), any());

        verify(missaoAfazerRepository, never()).save(any());
        verify(missaoHabitoRepository, never()).save(any());
        verify(missaoDiariaRepository, never()).save(any());
    }
}
