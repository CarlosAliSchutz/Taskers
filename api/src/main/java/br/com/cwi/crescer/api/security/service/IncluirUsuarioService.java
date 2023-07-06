package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.security.controller.request.IncluirUsuarioRequest;
import br.com.cwi.crescer.api.security.domain.Permissao;
import br.com.cwi.crescer.api.security.domain.Provider;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.core.GerarSenhaCriptografadaService;
import br.com.cwi.crescer.api.service.IncluirConquistasPadraoUsuarioService;
import br.com.cwi.crescer.api.service.IncluirCosmeticoPadraoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static br.com.cwi.crescer.api.security.domain.Funcao.USUARIO;
import static br.com.cwi.crescer.api.security.mapper.UsuarioMapper.toEntity;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
public class IncluirUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GerarSenhaCriptografadaService gerarSenhaCriptografadaService;

    @Autowired
    private IncluirCosmeticoPadraoUsuarioService incluirCosmeticoPadraoUsuarioService;

    @Autowired
    private IncluirConquistasPadraoUsuarioService incluirConquistasPadraoUsuarioService;


    @Transactional
    public void incluir(IncluirUsuarioRequest request) {

        if(usuarioRepository.existsByEmail(request.getEmail())){
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Esse email já existe");
        }

        Usuario usuario = toEntity(request);
        usuario.setSenha(gerarSenhaCriptografadaService.getSenhaCriptografada(request.getSenha()));
        usuario.adicionarPermissao(getPermissaoPadrao());
        usuario.setExperiencia(0);
        usuario.setTaskcoin(0);
        usuario.setAtivo(true);
        usuario.setProvider(Provider.LOCAL);
        usuario.setNotificacoesEmail(true);

        usuarioRepository.save(usuario);

        incluirCosmeticoPadraoUsuarioService.incluir(usuario);
        incluirConquistasPadraoUsuarioService.incluir(usuario);
    }


    private Permissao getPermissaoPadrao() {
        Permissao permissao = new Permissao();
        permissao.setFuncao(USUARIO);
        return permissao;
    }
}
