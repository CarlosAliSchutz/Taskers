package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Cosmetico;
import br.com.cwi.crescer.api.domain.CosmeticoUsuario;
import br.com.cwi.crescer.api.repository.CosmeticoUsuarioRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.core.BuscarCosmeticoService;
import br.com.cwi.crescer.api.service.core.BuscarCosmeticoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
public class EquiparCosmeticoService {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CosmeticoUsuarioRepository cosmeticoUsuarioRepository;

    @Autowired
    private BuscarCosmeticoUsuarioService buscarCosmeticoUsuarioService;

    @Autowired
    private BuscarCosmeticoService buscarCosmeticoService;

    @Transactional
    public void equipar(Long id) {
        Usuario usuario = usuarioAutenticadoService.get();
        Cosmetico cosmetico = buscarCosmeticoService.porId(id);

        CosmeticoUsuario cosmeticoParaEquipar = buscarCosmeticoUsuarioService.buscar(usuario, cosmetico);

        CosmeticoUsuario cosmeticoEquipado = cosmeticoUsuarioRepository
                .findByUsuarioAndCosmeticoTipoAndEquipado(usuario, cosmetico.getTipo(), true);

        if(cosmeticoEquipado != null){
            if(cosmeticoEquipado.equals(cosmeticoParaEquipar)){
                throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Você já está equipando esse cosmético");
            } else {
                cosmeticoEquipado.setEquipado(false);
                cosmeticoParaEquipar.setEquipado(true);
            }
        } else {
            cosmeticoParaEquipar.setEquipado(true);
        }

        usuarioRepository.save(usuario);
    }

}

