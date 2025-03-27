package com.ecommer.usuario.services.impl;

import com.ecommer.usuario.dtos.UsuarioRecordDto;
import com.ecommer.usuario.enums.RoleType;
import com.ecommer.usuario.enums.StatusUsuario;
import com.ecommer.usuario.enums.TipoPerfio;
import com.ecommer.usuario.exceptios.NotFoundException;
import com.ecommer.usuario.models.UsuarioModel;
import com.ecommer.usuario.repositorys.UsuarioRepository;
import com.ecommer.usuario.services.RoleService;
import com.ecommer.usuario.services.UsuarioService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    // Logger para registrar mensagens de log
    Logger logger = LogManager.getLogger(UsuarioServiceImpl.class);

    // Repositórios e serviços necessários para a manipulação de dados de usuário
    final UsuarioRepository usuarioRepository;
    final RoleService roleService;
    final PasswordEncoder passwordEncoder;

    // Construtor para injeção de dependência de todos os componentes necessários
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Método para buscar todos os usuários com paginação e filtro por especificação.
     *
     * @param spec Especificação para filtro dinâmico.
     * @param pageable Paginação para divisão de resultados.
     * @return Page<UsuarioModel> Retorna uma página de usuários.
     */
    @Override
    public Page<UsuarioModel> findAll(Specification<UsuarioModel> spec, Pageable pageable) {
        return usuarioRepository.findAll(spec, pageable);
    }

    /**
     * Método para buscar um usuário pelo ID.
     *
     * @param usuarioId ID do usuário a ser buscado.
     * @return Optional<UsuarioModel> Retorna o usuário encontrado ou um Optional vazio.
     * @throws NotFoundException Caso o usuário não seja encontrado.
     */
    @Override
    public Optional<UsuarioModel> findById(UUID usuarioId) {
        Optional<UsuarioModel> usuarioModelOptional = usuarioRepository.findById(usuarioId);

        if (usuarioModelOptional.isEmpty()) {
            logger.error("ERRO, USUARIO NÃO ENCONTRADO!");  // Registra o erro no log
            throw new NotFoundException("ERRO, USUARIO NÃO ENCONTRADO!");  // Lança uma exceção
        }
        return usuarioModelOptional;
    }

    /**
     * Método para excluir um usuário pelo modelo.
     *
     * @param usuarioModel Modelo do usuário a ser excluído.
     * @return UsuarioModel Retorna o usuário excluído.
     */
    @Override
    public UsuarioModel deleteUsuarioId(UsuarioModel usuarioModel) {
        usuarioRepository.delete(usuarioModel);
        return usuarioModel;
    }

    /**
     * Método para atualizar os dados de um usuário.
     *
     * @param usuarioModel Modelo do usuário a ser atualizado.
     * @param usuarioRecordDto Dados atualizados do usuário.
     * @return UsuarioModel Retorna o usuário com os dados atualizados.
     */
    @Override
    public UsuarioModel updateUsuario(UsuarioModel usuarioModel, UsuarioRecordDto usuarioRecordDto) {
        // Atualiza os dados do usuário
        usuarioModel.setNome(usuarioRecordDto.nome());
        usuarioModel.setStatusUsuario(StatusUsuario.ATIVO);  // Define o status como ativo
        usuarioModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));  // Atualiza a data de modificação

        return usuarioRepository.save(usuarioModel);  // Salva o usuário atualizado
    }

    /**
     * Método para atualizar a senha de um usuário.
     *
     * @param usuarioModel Modelo do usuário a ter a senha atualizada.
     * @param usuarioRecordDto Dados com a nova senha.
     * @return UsuarioModel Retorna o usuário com a senha atualizada.
     */
    @Override
    public UsuarioModel updateSenha(UsuarioModel usuarioModel, UsuarioRecordDto usuarioRecordDto) {
        usuarioModel.setSenha(passwordEncoder.encode(usuarioRecordDto.senha())); // Criptografa a senha na base de dados, após a atualização
        usuarioModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));  // Atualiza a data de modificação

        return usuarioRepository.save(usuarioModel);  // Salva o usuário com a nova senha
    }

    /**
     * Método para atualizar o status de um usuário.
     *
     * @param usuarioModel Modelo do usuário a ter o status atualizado.
     * @param usuarioRecordDto Dados com o novo status.
     * @return UsuarioModel Retorna o usuário com o status atualizado.
     */
    @Override
    public UsuarioModel updateStatusUsuario(UsuarioModel usuarioModel, UsuarioRecordDto usuarioRecordDto) {
        usuarioModel.setStatusUsuario(usuarioRecordDto.statusUsuario());  // Atualiza o status do usuário
        return usuarioRepository.save(usuarioModel);  // Salva o usuário com o novo status
    }

    /**
     * Método para verificar se já existe um usuário com o mesmo nome.
     *
     * @param nome Nome do usuário a ser verificado.
     * @return boolean Retorna true se o nome já existir, caso contrário, false.
     */
    @Override
    public boolean existByNome(String nome) {
        return usuarioRepository.existsByNome(nome);
    }


    /**
     * Método para verificar se já existe um usuário com a mesma senha.
     *
     * @param senha Senha do usuário a ser verificada.
     * @return boolean Retorna true se a senha já existir, caso contrário, false.
     */
    @Override
    public boolean existBySenha(String senha) {
        return usuarioRepository.existsBySenha(senha);
    }

    /**
     * Método para verificar se já existe um usuário com o mesmo email.
     *
     * @param email E-mail do usuário a ser verificado.
     * @return boolean Retorna true se o nome já existir, caso contrário, false.
     */
    @Override
    public boolean existByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }


    /**
     * Método para salvar um novo usuário no sistema.
     *
     * @param usuarioRecordDto Dados do novo usuário.
     * @return UsuarioModel Retorna o usuário recém-criado.
     */
    @Transactional
    @Override
    public UsuarioModel saveUsuario(UsuarioRecordDto usuarioRecordDto) {
        // Instanciando o modelo de usuário
        var usuarioModel = new UsuarioModel();
        // Convertendo os dados do DTO para o modelo
        BeanUtils.copyProperties(usuarioRecordDto, usuarioModel);

        // Setando campos necessários
        usuarioModel.setPerfilUsuario(TipoPerfio.USUARIO);  // Define o tipo de usuário
        usuarioModel.setStatusUsuario(StatusUsuario.ATIVO);  // Define o status do usuário como ativo
        usuarioModel.setDataCriacao(LocalDateTime.now(ZoneId.of("America/Recife")));  // Define a data de criação
        usuarioModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));  // Define a data de atualização


        // Codificando a senha antes de salvar no banco de dados
        usuarioModel.setSenha(passwordEncoder.encode(usuarioModel.getSenha()));

        // Adiciona a role de usuário ao conjunto de roles do usuário
        usuarioModel.getRoles().add(roleService.findByRoleNome(RoleType.ROLE_USUARIO));

        // Salvando o usuário no banco de dados
        return usuarioRepository.save(usuarioModel);
    }
}
