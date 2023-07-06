package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.Cosmetico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CosmeticoRepository extends JpaRepository<Cosmetico, Long> {

    @Query("SELECT c FROM Cosmetico c LEFT JOIN CosmeticoUsuario cu ON c.id = cu.cosmetico.id AND cu.usuario.id = :usuarioId WHERE cu.id IS NULL")
    Page<Cosmetico> buscarCosmeticosDisponiveisParaCompra(Long usuarioId, Pageable pageable);

    @Query("SELECT c FROM Cosmetico c INNER JOIN CosmeticoUsuario cu ON c.id = cu.cosmetico.id AND cu.usuario.id = :usuarioId")
    Page<Cosmetico> buscarCosmeticosAdquiridos(Long usuarioId, Pageable pageable);

    @Query("SELECT c FROM Cosmetico c INNER JOIN CosmeticoUsuario cu ON c.id = cu.cosmetico.id " +
            "AND cu.usuario.id = :usuarioId AND cu.equipado = true")
    List<Cosmetico> buscarCosmeticosEquipados(Long usuarioId);

    Optional<Cosmetico> findByNome(String nome);
}
