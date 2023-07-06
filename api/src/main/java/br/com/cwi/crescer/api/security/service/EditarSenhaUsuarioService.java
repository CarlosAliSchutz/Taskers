package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.security.controller.request.EditarSenhaRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.core.GerarSenhaCriptografadaService;
import br.com.cwi.crescer.api.security.validator.UsuarioProviderGoogleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

@Service
public class EditarSenhaUsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GerarSenhaCriptografadaService gerarSenhaCriptografadaService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private UsuarioProviderGoogleValidator usuarioProviderGoogleValidator;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void editar(EditarSenhaRequest request) {
        Usuario usuario = usuarioAutenticadoService.get();

        usuarioProviderGoogleValidator.validar(usuario);

        if(!passwordEncoder.matches(request.getSenhaAtual(), usuario.getSenha())){
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Sua senha atual está incorreta");
        }

        usuario.setSenha(gerarSenhaCriptografadaService.getSenhaCriptografada(request.getNovaSenha()));
        usuarioRepository.save(usuario);
    }
}
