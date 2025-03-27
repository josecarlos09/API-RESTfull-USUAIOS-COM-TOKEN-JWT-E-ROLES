package com.ecommer.usuario.repositorys;

import com.ecommer.usuario.enums.RoleType;
import com.ecommer.usuario.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleModel, UUID> {
    // Método para buscar uma RoleModel a partir do nome do tipo de role (RoleType)
    Optional<RoleModel> findByRoleNome(RoleType nome);
}
