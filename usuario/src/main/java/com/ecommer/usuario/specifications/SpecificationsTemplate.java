package com.ecommer.usuario.specifications;

import com.ecommer.usuario.models.UsuarioModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationsTemplate {
    // A interface UsuarioSpec define os filtros de busca que podem ser aplicados sobre os atributos da entidade UsuarioModel
    @And({
            @Spec(path = "usuarioId", spec = Equal.class), // Filtro de igualdade para o atributo usuarioId
            @Spec(path = "email", spec = Equal.class), // Filtro de igualdade para o atributo email
            @Spec(path = "senha", spec = Equal.class), // Filtro de igualdade para o atributo email
            @Spec(path = "nome", spec = Like.class), // Filtro de busca parcial para o atributo nome (LIKE)
    })
    public interface UsuarioSpec extends Specification<UsuarioModel> {}
}
