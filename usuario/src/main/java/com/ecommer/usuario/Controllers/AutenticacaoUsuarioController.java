package com.ecommer.usuario.Controllers;

import com.ecommer.usuario.configs.security.TokenJwt;
import com.ecommer.usuario.dtos.JwtRecordDto;
import com.ecommer.usuario.dtos.LoginRecordDto;
import com.ecommer.usuario.dtos.UsuarioRecordDto;
import com.ecommer.usuario.services.UsuarioService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoUsuarioController {

    // Logger para registrar eventos e auxiliar na depuração
    private static final Logger logger = LogManager.getLogger(AutenticacaoUsuarioController.class);

    // Injeção de dependências
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final TokenJwt jwtProvedor;

    // Construtor para injeção via Spring
    public AutenticacaoUsuarioController(UsuarioService usuarioService, AuthenticationManager authenticationManager, TokenJwt jwtProvedor) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtProvedor = jwtProvedor;
    }

    /**
     * Endpoint para autenticação de usuários.
     * Recebe um DTO com nome e senha e retorna um token JWT caso as credenciais sejam válidas.
     *
     * @param loginRecordDto DTO contendo as credenciais do usuário.
     * @return ResponseEntity com o token JWT em caso de sucesso.
     */
    @PostMapping("/ ")
    public ResponseEntity<JwtRecordDto> autenticacao(@RequestBody @Valid LoginRecordDto loginRecordDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRecordDto.nome(), loginRecordDto.senha()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(new JwtRecordDto(jwtProvedor.gerarJwt(authentication)));
    }

    /**
     * Endpoint para registro de usuários vinculados a um funcionário.
     *
     * @param usuarioRecordDto DTO com os dados do usuário a ser registrado.
     * @return ResponseEntity informando sucesso ou erro no registro do usuário.
     */
    @PostMapping("/registro")
    public ResponseEntity<Object> registroUsuario(
            @RequestBody @Validated(UsuarioRecordDto.UsuarioView.RegistroUsuarioPost.class)
            @JsonView(UsuarioRecordDto.UsuarioView.RegistroUsuarioPost.class) UsuarioRecordDto usuarioRecordDto){

        // Verifica se o nome de usuário já existe no sistema
        if (usuarioService.existByNome(usuarioRecordDto.nome())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO, USUARIO JÁ EXISTENTE!");
        }

        // Verifica se a senha informada já está em uso
        if (usuarioService.existBySenha(usuarioRecordDto.senha())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO, ESSA SENHA JÁ ESTÁ EM USO!");
        }
        // Verifica se o nome de usuário já existe no sistema
        if (usuarioService.existByEmail(usuarioRecordDto.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO, ESTE E-MAIL JÁ ESTÁ EM USO!");
        }

        // Log de depuração
        logger.debug("POST: Registro de usuário {}", usuarioRecordDto);

        // Criação do usuário e retorno da resposta
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.saveUsuario(usuarioRecordDto));
    }
}
