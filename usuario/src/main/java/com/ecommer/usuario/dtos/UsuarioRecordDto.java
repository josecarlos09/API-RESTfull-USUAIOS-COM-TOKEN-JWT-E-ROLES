package com.ecommer.usuario.dtos;

import com.ecommer.usuario.enums.StatusUsuario;
import com.ecommer.usuario.validates.SenhaConstraint;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRecordDto(@Email(message = "O campo deve ser preenhido no formato de E-mail")
                               @NotBlank(groups = UsuarioView.RegistroUsuarioPost.class, message = "O campo E-mail é obrigatorio!")
                               @JsonView(UsuarioView.RegistroUsuarioPost.class)
                               String email,

                               @NotNull(groups = {UsuarioView.RegistroUsuarioPost.class, UsuarioView.UsuarioPut.class}, message = "O campo nome é obrigatorio!")
                               @Size(groups = UsuarioView.RegistroUsuarioPost.class, min = 5, max = 50, message = "O número máximo de caracteres é 50, e o mínimo é 5.")
                               @JsonView({UsuarioView.RegistroUsuarioPost.class, UsuarioView.UsuarioPut.class})
                               String nome,

                               @NotBlank(groups = {UsuarioView.RegistroUsuarioPost.class, UsuarioView.SenhaPut.class}, message = "O campo senha é obrigatorio")
                               @Size(groups = {UsuarioView.RegistroUsuarioPost.class, UsuarioView.SenhaPut.class}, min = 5, max = 20, message = "Informe a senha com no minimo 5 caracteres e no maximo 20")
                               @SenhaConstraint(groups = {UsuarioView.RegistroUsuarioPost.class, UsuarioView.SenhaPut.class})
                               @JsonView({UsuarioView.RegistroUsuarioPost.class, UsuarioView.SenhaPut.class})
                               String senha,

                               @NotBlank(groups = UsuarioView.SenhaPut.class, message = "Informe a senha antiga")
                               @Size(groups = UsuarioView.SenhaPut.class, min = 5, max = 20, message = "Informe a senha com no minimo 5 caracteres e no maximo 20")
                               @SenhaConstraint(groups = UsuarioView.SenhaPut.class)
                               @JsonView(UsuarioView.SenhaPut.class)
                               String senhaAntiga,

                               @NotNull(groups = UsuarioView.StatusUsuarioPut.class, message = "Informe o status do usuário")
                               @JsonView(UsuarioView.StatusUsuarioPut.class)
                               StatusUsuario statusUsuario,

                               @NotNull(groups = UsuarioView.RoleUsuario.class, message = "Informe a permição do usuário")
                               @JsonView(UsuarioView.RoleUsuario.class)
                               String roleUsuario){
    public interface UsuarioView{
        interface RegistroUsuarioPost{}
        interface UsuarioPut {}
        interface StatusUsuarioPut{}
        interface SenhaPut{}
        interface RoleUsuario{}
    }
}