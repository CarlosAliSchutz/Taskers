package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.*;
import br.com.cwi.crescer.api.repository.ConquistaRepository;
import br.com.cwi.crescer.api.repository.UsuarioConquistaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncluirConquistasPadraoUsuarioService {

    @Autowired
    private ConquistaRepository conquistaRepository;

    @Autowired
    private UsuarioConquistaRepository usuarioConquistaRepository;

    @Transactional
    public void incluir(Usuario usuario){
        List<TipoConquista> tiposConquista = Arrays.asList(
                TipoConquista.REALIZAR_HABITOS,
                TipoConquista.INCLUIR_HABITOS,
                TipoConquista.REALIZAR_DIARIAS,
                TipoConquista.INCLUIR_DIARIAS,
                TipoConquista.FINALIZAR_AFAZERES,
                TipoConquista.INCLUIR_AFAZERES,
                TipoConquista.COMPRAR_COSMETICO,
                TipoConquista.REALIZAR_MISSAO
        );

        List<Conquista> conquistasBuscadas = tiposConquista.stream()
                .flatMap(tipoConquista -> conquistaRepository.findByTipo(tipoConquista).stream())
                .collect(Collectors.toList());

        List<UsuarioConquista> conquistasDoUsuario = conquistasBuscadas.stream()
                .map(conquista -> UsuarioConquista.builder()
                        .conquista(conquista)
                        .progresso(0)
                        .concluida(false)
                        .usuario(usuario)
                        .build())
                .collect(Collectors.toList());

        usuarioConquistaRepository.saveAll(conquistasDoUsuario);
    }

}
