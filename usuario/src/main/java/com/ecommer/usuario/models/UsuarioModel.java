package com.ecommer.usuario.models;


import com.ecommer.usuario.enums.StatusUsuario;
import com.ecommer.usuario.enums.TipoPerfio;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Classe que representa o modelo da entidade "Usuario".
 * Esta classe é mapeada para a tabela "TB_USUARIO" no banco de dados.
 * A classe implementa Serializable para garantir que os objetos possam ser serializados.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USUARIO")
public class UsuarioModel extends RepresentationModel<UsuarioModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //
    private UUID usuarioId;

    @Column(nullable = false, unique = true, length = 150)
    private String nome;

    @JsonIgnore
    @Column(nullable = false, unique = true, length = 255)
    private String senha;

    @JsonIgnore
    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusUsuario statusUsuario;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoPerfio perfilUsuario;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Acesso apenas escrita
    @ManyToMany(fetch = FetchType.LAZY) // Associação de muitos para muitos
    // Será gerado uma tabela auxiliar TB_USUARIO_ROLE que conterar o id de usuario e o id de role
    @JoinTable(name = "TB_USUARIO_ROLE",
            joinColumns = @JoinColumn(name = "usuarioId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<RoleModel> roles = new HashSet<>(); // Estrutura de Set

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StatusUsuario getStatusUsuario() {
        return statusUsuario;
    }

    public void setStatusUsuario(StatusUsuario statusUsuario) {
        this.statusUsuario = statusUsuario;
    }

    public TipoPerfio getPerfilUsuario() {
        return perfilUsuario;
    }

    public void setPerfilUsuario(TipoPerfio perfilUsuario) {
        this.perfilUsuario = perfilUsuario;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Set<RoleModel> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleModel> roles) {
        this.roles = roles;
    }
}