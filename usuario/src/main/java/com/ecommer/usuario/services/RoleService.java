package com.ecommer.usuario.services;

import com.ecommer.usuario.enums.RoleType;
import com.ecommer.usuario.models.RoleModel;

public interface RoleService {

    // Método para buscar um modelo de role (papel de usuário) com base no nome do papel (RoleType).
    // O parâmetro 'roleType' é utilizado para identificar o tipo de papel desejado (ex: ADMIN, USER, etc.).
    // Retorna o modelo de role correspondente ou uma exceção, caso não encontrado.
    RoleModel findByRoleNome(RoleType roleType);
}