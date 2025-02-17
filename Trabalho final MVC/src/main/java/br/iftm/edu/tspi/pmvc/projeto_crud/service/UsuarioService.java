package br.iftm.edu.tspi.pmvc.projeto_crud.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.iftm.edu.tspi.pmvc.projeto_crud.domain.Usuario;
import br.iftm.edu.tspi.pmvc.projeto_crud.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    private UsuarioRepository repository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public boolean verificaLogin (Usuario loginDigitado) {
        try {
            Usuario loginBanco = repository.validarLogin(loginDigitado);
            return encoder.matches(loginDigitado.getSenha(), loginBanco.getSenha());
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
