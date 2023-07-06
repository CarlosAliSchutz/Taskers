package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Cosmetico;
import br.com.cwi.crescer.api.domain.CosmeticoUsuario;
import br.com.cwi.crescer.api.repository.CosmeticoUsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.core.BuscarCosmeticoService;
import br.com.cwi.crescer.api.service.core.ValidarUsuarioPossuiCosmeticoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static br.com.cwi.crescer.api.domain.TipoConquista.COMPRAR_COSMETICO;

@Service
public class ComprarCosmeticoService {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CosmeticoUsuarioRepository cosmeticoUsuarioRepository;

    @Autowired
    private ValidarUsuarioPossuiCosmeticoService validarUsuarioPossuiCosmeticoService;

    @Autowired
    private BuscarCosmeticoService buscarCosmeticoService;

    @Autowired
    private RealizarConquistaService realizarConquistaService;

    @Transactional
    public void comprar(Long id) {
        Usuario usuario = usuarioAutenticadoService.get();
        Cosmetico cosmetico = buscarCosmeticoService.porId(id);

        validarUsuarioPossuiCosmeticoService.validarSeNaoPossui(usuario, cosmetico);

        if(usuario.getTaskcoin() < cosmetico.getValor()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Taskcoins insuficientes");
        }

        usuario.setTaskcoin(usuario.getTaskcoin() - cosmetico.getValor());

        CosmeticoUsuario cosmeticoUsuario = new CosmeticoUsuario();
        cosmeticoUsuario.setCosmetico(cosmetico);
        cosmeticoUsuario.setUsuario(usuario);

        usuarioRepository.save(usuario);
        cosmeticoUsuarioRepository.save(cosmeticoUsuario);

        realizarConquistaService.realizar(usuario.getId(), COMPRAR_COSMETICO);
    }
}
