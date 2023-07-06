package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.UsuarioRankingResponse;
import br.com.cwi.crescer.api.mapper.UsuarioRankingResponseMapper;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarRankingService {

    private static final int NUMERO_PARA_ELEVAR_VALORES_RANKING = 1;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Page<UsuarioRankingResponse> listar(String nome, Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.
                findByAtivoTrueAndNomeCompletoContainingIgnoreCase(nome, pageable);

        List<UsuarioRankingResponse> listaDeUsuariosClassificadosPorRanking = usuarios.getContent().stream().map(usuario -> {
            Long posicao = usuarioRepository
                    .findPosicaoUsuario(usuario.getExperiencia(), usuario.getId()) + NUMERO_PARA_ELEVAR_VALORES_RANKING;

            return UsuarioRankingResponseMapper.toResponse(usuario, posicao);
        })
        .sorted(Comparator.comparingLong(UsuarioRankingResponse::getPosicaoRanking))
        .collect(Collectors.toList());

        return new PageImpl<>(listaDeUsuariosClassificadosPorRanking, pageable, usuarios.getTotalElements());
    }
}