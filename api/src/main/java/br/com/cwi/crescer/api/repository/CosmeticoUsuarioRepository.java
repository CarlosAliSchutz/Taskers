package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.Cosmetico;
import br.com.cwi.crescer.api.domain.CosmeticoUsuario;
import br.com.cwi.crescer.api.domain.TipoCosmetico;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CosmeticoUsuarioRepository extends JpaRepository<CosmeticoUsuario, Long> {

    boolean existsByUsuarioAndCosmetico(Usuario usuario, Cosmetico cosmetico);

    CosmeticoUsuario findByUsuarioAndCosmeticoTipoAndEquipado(
            Usuario usuario, TipoCosmetico cosmeticoTipo, boolean equipado);

    Optional<CosmeticoUsuario> findByUsuarioAndCosmetico(Usuario usuario, Cosmetico cosmetico);

    List<CosmeticoUsuario> findAllByUsuarioIdAndEquipadoIsTrue(Long usuarioId);
}
