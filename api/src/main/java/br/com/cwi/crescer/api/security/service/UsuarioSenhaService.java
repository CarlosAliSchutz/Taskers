package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.security.controller.request.EditarSenhaEsquecidaRequest;
import br.com.cwi.crescer.api.security.controller.request.EsqueciSenhaRequest;
import br.com.cwi.crescer.api.security.domain.SenhaToken;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.core.BuscarUsuarioService;
import br.com.cwi.crescer.api.security.validator.UsuarioProviderGoogleValidator;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.SecureRandomFactoryBean;
import org.springframework.security.core.token.Token;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class UsuarioSenhaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    @Autowired
    private UsuarioProviderGoogleValidator usuarioProviderGoogleValidator;

    @Autowired
    private EnviarEmailService enviarEmailService;

    @SneakyThrows
    public void gerarToken(EsqueciSenhaRequest request) {
        Usuario usuario = buscarUsuarioService.buscarPorEmail(request.getEmail());

        KeyBasedPersistenceTokenService tokenService = getInstanceFor(usuario);

        Token token = tokenService.allocateToken(usuario.getEmail());

        enviarEmailService.enviar(request.getEmail(),
                "Solicitação de recuperação de senha",
                "Pode redefinir sua senha acessando este link: http://localhost:3000/redefinir-senha/" + token.getKey());
    }

    private KeyBasedPersistenceTokenService getInstanceFor(Usuario usuario) throws Exception {
        KeyBasedPersistenceTokenService tokenService = new KeyBasedPersistenceTokenService();

        tokenService.setServerSecret(usuario.getSenha());
        tokenService.setServerInteger(16);
        tokenService.setSecureRandom(new SecureRandomFactoryBean().getObject());
        return tokenService;
    }

    @SneakyThrows
    @Transactional
    public void trocarSenha(EditarSenhaEsquecidaRequest request, String token) {
        String novaSenha = request.getSenha();
        SenhaToken senhaToken = readPublicData(token);

        if(isExpired(senhaToken)) {
            throw new ResponseStatusException(BAD_REQUEST, "Token expirado");
        }

        Usuario usuario = buscarUsuarioService.buscarPorEmail(senhaToken.getEmail());

        usuarioProviderGoogleValidator.validar(usuario);

        KeyBasedPersistenceTokenService tokenService = this.getInstanceFor(usuario);
        try {
            tokenService.verifyToken(token);
        } catch (Exception ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Token inválido");
        }

        usuario.setSenha(this.passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);
    }

    private boolean isExpired(SenhaToken senhaToken) {
        Instant createdAt = new Date(senhaToken.getCriadoEm()).toInstant();
        Instant now = new Date().toInstant();
        return createdAt.plus(Duration.ofMinutes(5)).isBefore(now);
    }

    private SenhaToken readPublicData(String token) {
        String tokenDecoded = new String(Base64.getDecoder().decode(token));
        String[] tokenParts = tokenDecoded.split(":");
        Long timestamp = Long.parseLong(tokenParts[0]);
        String email = tokenParts[2];
        return new SenhaToken(email, timestamp);
    }
}
