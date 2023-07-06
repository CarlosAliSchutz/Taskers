package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.security.domain.Provider;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.service.IncluirConquistasPadraoUsuarioService;
import br.com.cwi.crescer.api.service.IncluirCosmeticoPadraoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IncluirUsuarioLogadoGoogleService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private IncluirCosmeticoPadraoUsuarioService incluirCosmeticoPadraoUsuarioService;

    @Autowired
    private IncluirConquistasPadraoUsuarioService incluirConquistasPadraoUsuarioService;

    @Transactional
    public void incluir(String nome, String email, String foto) {

        if (!usuarioRepository.existsByEmail(email)) {
            Usuario usuarioLogadoGoogle = new Usuario();
            usuarioLogadoGoogle.setNomeCompleto(nome);
            usuarioLogadoGoogle.setEmail(email);
            usuarioLogadoGoogle.setProvider(Provider.GOOGLE);
            usuarioLogadoGoogle.setExperiencia(0);
            usuarioLogadoGoogle.setTaskcoin(0);
            usuarioLogadoGoogle.setAtivo(true);
            usuarioLogadoGoogle.setNotificacoesEmail(true);
            usuarioLogadoGoogle.setImagemPerfil(foto);

            usuarioRepository.save(usuarioLogadoGoogle);

            incluirCosmeticoPadraoUsuarioService.incluir(usuarioLogadoGoogle);
            incluirConquistasPadraoUsuarioService.incluir(usuarioLogadoGoogle);
        }

    }

}