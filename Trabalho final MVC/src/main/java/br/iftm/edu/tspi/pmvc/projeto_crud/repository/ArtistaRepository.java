package br.iftm.edu.tspi.pmvc.projeto_crud.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.iftm.edu.tspi.pmvc.projeto_crud.domain.Artista;
import br.iftm.edu.tspi.pmvc.projeto_crud.domain.Genero;

@Repository
public class ArtistaRepository {
    
    private JdbcTemplate conexaoBanco;

    public ArtistaRepository(JdbcTemplate conexaoBanco) {
        this.conexaoBanco = conexaoBanco;
    }

    public List<Artista> listar() {
        String sql = """
                SELECT 
                    a.cod_artista,
                    a.nome_artista,
                    a.data_nascimento, 
                    TIMESTAMPDIFF(YEAR, a.data_nascimento, CURDATE()) AS idade,
                    g.cod_genero,
                    g.desc_genero
                FROM artista a
                JOIN genero g ON g.cod_genero = a.cod_genero
                ORDER BY a.cod_artista
                """;
        return conexaoBanco.query(sql, (rs, rowNum) -> {
                Artista artista = new Artista();
                artista.setCodigo(rs.getInt("cod_artista"));
                artista.setNome(rs.getString("nome_artista"));
                artista.setDataNascimento(rs.getDate("data_nascimento").toLocalDate()); // Corrigido
                
                Genero genero = new Genero();
                genero.setCodigo(rs.getInt("cod_genero"));
                genero.setDescricao(rs.getString("desc_genero"));
                
                artista.setGenero(genero);
                return artista;
                });
    }

    // public Artista geArtista(ResultSet rs) throws SQLException {
    //     Artista artista = new Artista();
    //     artista.setCodigo(rs.getInt("cod_artista"));
    //     artista.setNome(rs.getString("nome_artista"));
    //     artista.setIdade(rs.getInt("idade_artista"));

    //     Genero genero = new Genero();
    //     genero.setCodigo(rs.getInt("cod_genero"));
    //     genero.setDescricao(rs.getString("desc_genero"));
    //     genero.setData(rs.getString("ano_surgimento"));
    //     genero.setPais(rs.getString("local_surgimento"));

    //     artista.setGenero(genero);
    //     return artista;
    // }

    public List<Artista> buscarPorNome(String nome) {
        String sql = """
                    select cod_artista,
                            nome_artista,
                            a.data_nascimento, 
                            TIMESTAMPDIFF(YEAR, a.data_nascimento, CURDATE()) AS idade,
                            a.cod_genero,
                            g.desc_genero,
                            g.ano_surgimento,
                            g.local_surgimento
                    from artista a, genero g
                    where g.cod_genero = a.cod_genero and lower(nome_artista) like ?;
                    """;
        return conexaoBanco.query(sql, (rs, rowNum) -> {
                Artista artista = new Artista();
                artista.setCodigo(rs.getInt("cod_artista"));
                artista.setNome(rs.getString("nome_artista"));
                artista.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());

                Genero genero = new Genero();
                genero.setCodigo(rs.getInt("cod_genero"));
                genero.setDescricao(rs.getString("desc_genero"));
                genero.setData(rs.getString("ano_surgimento"));
                genero.setPais(rs.getString("local_surgimento"));

                artista.setGenero(genero);
                return artista;
        });
    }

    public void novo(Artista artista) {
        String sql = """
                INSERT INTO artista
                (nome_artista, data_nascimento, cod_genero)
                VALUES (?,?,?)
                """;
    
        conexaoBanco.update(sql,    
                            artista.getNome(),
                            Date.valueOf(artista.getDataNascimento()), 
                            artista.getGenero().getCodigo());
    }    

    public boolean update(Artista artista) {
        String sql = """
                update artista
                set nome_artista = ?,
                    data_nascimento = ?,
                    cod_genero = ?
                where cod_artista = ?
                """;
        return conexaoBanco.update(sql,    
                artista.getNome(),
                artista.getDataNascimento() != null ? Date.valueOf(artista.getDataNascimento()) : null,
                artista.getGenero().getCodigo(), artista.getCodigo()) > 0;
    }

    public void delete(Integer codigo) {
        String sql = "delete from artista where cod_artista = ?";
        conexaoBanco.update(sql, codigo);
    }

    public Artista buscarPorCodigo(Integer Codigo) {
        String sql = """
                    select cod_artista,
                            nome_artista,
                            a.data_nascimento, 
                            TIMESTAMPDIFF(YEAR, a.data_nascimento, CURDATE()) AS idade,
                            a.cod_genero,
                            g.desc_genero,
                            g.ano_surgimento,
                            g.local_surgimento
                    from artista a, genero g
                    where g.cod_genero = a.cod_genero and cod_artista = ?;
                    """;
        return conexaoBanco.queryForObject(sql, (rs, rowNum) -> {
                Artista artista = new Artista();
                artista.setCodigo(rs.getInt("cod_artista"));
                artista.setNome(rs.getString("nome_artista"));
                artista.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());

                Genero genero = new Genero();
                genero.setCodigo(rs.getInt("cod_genero"));
                genero.setDescricao(rs.getString("desc_genero"));
                genero.setData(rs.getString("ano_surgimento"));
                genero.setPais(rs.getString("local_surgimento"));

                artista.setGenero(genero);
                return artista;
        },Codigo);
    }
}