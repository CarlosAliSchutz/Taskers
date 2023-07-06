package br.com.cwi.crescer.api.security.repository;

import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);

    Page<Usuario> findByAtivoTrueAndNomeCompletoContainingIgnoreCase(String nomeCompleto, Pageable pageable);

    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.ativo = true AND u.experiencia > :experiencia OR (u.experiencia = :experiencia AND u.id < :id)")
    Long findPosicaoUsuario(@Param("experiencia") Integer experiencia, @Param("id") Long id);

    Page<Usuario> findAllByAtivoTrue(Pageable filtroParaUsuarios);
}
