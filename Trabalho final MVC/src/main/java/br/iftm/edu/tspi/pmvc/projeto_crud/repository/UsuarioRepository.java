package br.iftm.edu.tspi.pmvc.projeto_crud.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import br.iftm.edu.tspi.pmvc.projeto_crud.domain.Usuario;

@Repository
public class UsuarioRepository {

    private JdbcTemplate conexaoBanco;

    public UsuarioRepository(JdbcTemplate conexaoBanco) {
        this.conexaoBanco = conexaoBanco;
    }

    public Usuario validarLogin (Usuario loginDigitado) {
        String sql = "select desc_login, desc_senha from usuario where desc_login = ?";
        return conexaoBanco.queryForObject(
                                sql, 
                                new BeanPropertyRowMapper<>(Usuario.class),
                                loginDigitado.getLogin());
    }
}