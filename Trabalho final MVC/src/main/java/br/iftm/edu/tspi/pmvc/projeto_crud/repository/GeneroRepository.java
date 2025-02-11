package br.iftm.edu.tspi.pmvc.projeto_crud.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import br.iftm.edu.tspi.pmvc.projeto_crud.domain.Genero;

@Repository
public class GeneroRepository {

    private JdbcTemplate conexaoBanco;

    public GeneroRepository(JdbcTemplate conexaoBanco) {
        this.conexaoBanco = conexaoBanco;
    }

    public List<Genero> listar() {
        String sql="select cod_genero as codigo, desc_genero as descricao, ano_surgimento as data, local_surgimento as pais from genero";
        return conexaoBanco.query(sql, new BeanPropertyRowMapper<>(Genero.class));
    }

    public Genero buscaPorCodGenero(Integer codigo) {
        String sql = """
                    select cod_genero as codigo, desc_genero as descricao, ano_surgimento as data, local_surgimento as pais 
                    from genero
                    where cod_genero = ?
                    """;
        return conexaoBanco.queryForObject(sql,
                    new BeanPropertyRowMapper<>(Genero.class), 
                    codigo);
    }
    
    public void novo(Genero genero) {
        String sql = "insert into genero(cod_genero, desc_genero, ano_surgimento, local_surgimento) values(?,?,?,?)";
        conexaoBanco.update(sql,
                            genero.getCodigo(),
                            genero.getDescricao(),
                            genero.getData(),
                            genero.getPais());
    }
    
    public boolean delete(Integer codigo) {
        String sql = """
                delete from genero
                where cod_genero = ?
                """;
        return conexaoBanco.update(sql, codigo) > 0;
    }

    public boolean update(Genero genero) {
        String sql = """
                update genero 
                set desc_genero = ?, 
                    ano_surgimento = ?,
                    local_surgimento = ? 
                where cod_genero = ?
                """;
        return conexaoBanco.update(sql,
                            genero.getDescricao(),
                            genero.getData(),
                            genero.getPais(),
                            genero.getCodigo()) > 0;
    }
}
