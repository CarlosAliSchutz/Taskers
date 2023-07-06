package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.security.config.EncontrarUsuarioService;
import br.com.cwi.crescer.api.security.domain.Provider;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.google.login.CustomOAuth2User;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class LoginOAuth2GoogleHandler implements AuthenticationSuccessHandler {
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    private IncluirUsuarioLogadoGoogleService incluirUsuarioLogadoGoogleService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EncontrarUsuarioService encontrarUsuarioService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

        Optional<Usuario> usuario = usuarioRepository.findByEmail(oauthUser.getEmail());

        if(usuario.isEmpty() || (usuario.isPresent() && usuario.get().getProvider().equals(Provider.GOOGLE))){
            incluirUsuarioLogadoGoogleService.incluir(oauthUser.getName(), oauthUser.getEmail(), oauthUser.getPicture());

            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                    oauthToken.getAuthorizedClientRegistrationId(),
                    oauthToken.getName());
            String accessToken = authorizedClient.getAccessToken().getTokenValue();

            UserDetails userDetails = encontrarUsuarioService.loadUserByUsername(oauthUser.getEmail());
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, accessToken, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        response.sendRedirect("http://localhost:3000/verificacao");
    }
}
