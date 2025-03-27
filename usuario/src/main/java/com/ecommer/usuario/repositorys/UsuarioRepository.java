package com.ecommer.usuario.repositorys;

import com.ecommer.usuario.models.UsuarioModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, UUID> {
    boolean existsByNome(String nome);

    boolean existsBySenha(String senha);

    // Método para buscar um usuário pelo nome, carregando as roles (funções/roles do usuário) de forma imediata (fetch)
    @EntityGraph(attributePaths = "roles", type = EntityGraph.EntityGraphType.FETCH)
    Optional<UsuarioModel> findByNome(String nome);

    Page<UsuarioModel> findAll(Specification<UsuarioModel> spec, Pageable pageable);

    boolean existsByEmail(String email);
}
