package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlterarNotificacoesEmailService {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void alterar() {
        Usuario usuario = usuarioAutenticadoService.get();

        usuario.setNotificacoesEmail(!usuario.isNotificacoesEmail());

        usuarioRepository.save(usuario);
    }
}
