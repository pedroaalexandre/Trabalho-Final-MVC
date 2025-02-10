package br.iftm.edu.tspi.pmvc.projeto_crud.repository;

import java.util.List;

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

    public List<Usuario> listar() {
        String sql = "select desc_login as login, desc_senha as senha, nome_usuario as nome, cod_usuario as codigo from usuario";
        return conexaoBanco.query(sql, new BeanPropertyRowMapper<>(Usuario.class));
    }

    public Usuario buscaPorLogin(String login) {
        String sql = """
                select desc_login as login, desc_senha as senha, nome_usuario as nome, cod_usuario as codigo 
                from usuario
                where desc_login = ?
                """;
        
        return conexaoBanco.queryForObject(sql,
                    new BeanPropertyRowMapper<>(Usuario.class),
                    login);
    }

    public void novo(Usuario usuario) {
        String sql = "insert into usuario(desc_login, desc_senha, nome_usuario, cod_usuario) values (?,?,?,?)";
        conexaoBanco.update(sql,
                            usuario.getLogin(),
                            usuario.getSenha(),
                            usuario.getNome(),
                            usuario.getCodigo());
    }

    public boolean delete(String login) {
        String sql = "delete from usuario where desc_login = ?";
        return conexaoBanco.update(sql, login) > 0;
    }

    public boolean update(Usuario usuario) {
        String sql = "update usuario set desc_login = ?, desc_senha = ?, nome_usuario = ? where cod_usuario = ?";
        return conexaoBanco.update(sql,
                            usuario.getLogin(),
                            usuario.getSenha(),
                            usuario.getNome(),
                            usuario.getCodigo()) > 0;
    }
}