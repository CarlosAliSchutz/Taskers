package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.security.controller.request.EditarUsuarioRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.validator.UsuarioProviderGoogleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditarUsuarioService {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private UsuarioProviderGoogleValidator usuarioProviderGoogleValidator;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void editar(EditarUsuarioRequest request) {

        Usuario usuario = usuarioAutenticadoService.get();

        usuarioProviderGoogleValidator.validar(usuario);

        usuario.setNomeCompleto(request.getNomeCompleto());
        usuario.setImagemPerfil(request.getImagemPerfil());
        usuario.setEmail(request.getEmail());

        usuarioRepository.save(usuario);
    }
}
