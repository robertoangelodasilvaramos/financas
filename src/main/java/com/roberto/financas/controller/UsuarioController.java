package com.roberto.financas.controller;

import com.roberto.financas.exception.ErroAutenticacao;
import com.roberto.financas.exception.RegraNegocioException;
import com.roberto.financas.model.dto.UsuarioDto;
import com.roberto.financas.model.entity.Usuario;
import com.roberto.financas.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usuario")
public class UsuarioController {

    private UsuarioService service;

    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody UsuarioDto dto){

        try {
            Usuario usuario = service.autenticar(dto.getEmail(), dto.getSenha());
            return ResponseEntity.ok(usuario);
        }catch (ErroAutenticacao e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping
    public ResponseEntity salvarUsuario(@RequestBody UsuarioDto dto) {

        Usuario usuario = Usuario.builder().email(dto.getEmail())
                .nome(dto.getNome())
                .senha(dto.getSenha()).build();

        try {
            Usuario usuarioSalvo = service.salvarUsuario(usuario);
            return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}

