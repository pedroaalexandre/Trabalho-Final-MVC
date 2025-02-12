package br.iftm.edu.tspi.pmvc.projeto_crud.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.iftm.edu.tspi.pmvc.projeto_crud.domain.Artista;
import br.iftm.edu.tspi.pmvc.projeto_crud.domain.Genero;

// @Repository
// public class ArtistaRepository {

//     private final List<Artista> artistas;

//     public ArtistaRepository() {

//         this.artistas = new ArrayList<>();

//         Genero gospel = new Genero(1, "Gospel", "Século XX", "Estados Unidos");

//         this.artistas.add(new Artista(1, "Alessandro Vilas Boas", 29, gospel));
//     }

//     public List<Artista> listar() {
//         return this.artistas;
//     }

//     public List<Artista> buscarPorNome(String nome) {
//         List<Artista> artistaBusca = new ArrayList<>();
//         for (Artista artista: this.artistas) {
//             if(artista.getNome().toLowerCase().contains(nome.toLowerCase())) {
//                 artistaBusca.add(artista);
//             }
//         }
//         return artistaBusca;
//     }

//     public Artista buscarPorCodigo(Integer codigo) {
//         Artista artistaBusca = new Artista(codigo);
//         int index = artistas.indexOf(artistaBusca);
        
//         if(index != -1) {
//             return artistas.get(index);
//         }else{
//             return null;
//         }
//     }

//     public void novo(Artista artista) {
//         artistas.add(artista);
//     }

//     public boolean delete(Integer codigo) {
//         Artista artista = new Artista(codigo);
//         return artistas.remove(artista);
//     }

//     public boolean update(Artista artista) {
//         int index = artistas.indexOf(artista);
//         if(index != -1) {
//             artistas.set(index, artista);
//             return true;
//         }
//         return false;
//     }
// }

@Repository
public class ArtistaRepository {
    
    private JdbcTemplate conexaoBanco;

    public ArtistaRepository(JdbcTemplate conexaoBanco) {
        this.conexaoBanco = conexaoBanco;
    }

    public List<Artista> listar() {
        String sql = """
                    select cod_artista,
                            nome_artista,
                            idade_artista,
                            g.desc_genero,
                            g.cod_genero
                    from artista a, genero g
                    where g.cod_genero = a.cod_genero
                    order by a.cod_artista
                    """;
        return conexaoBanco.query(sql, (rs, rowNum) -> {
                Artista artista = new Artista();
                artista.setCodigo(rs.getInt("cod_artista"));
                artista.setNome(rs.getString("nome_artista"));
                artista.setIdade(rs.getInt("idade_artista"));

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
                            idade_artista,
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
                artista.setIdade(rs.getInt("idade_artista"));

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
                insert into artista
                (cod_artista, nome_artista, idade_artista, cod_genero)
                values(?,?,?,?)
                """;

        conexaoBanco.update(sql,    
                            artista.getCodigo(),
                            artista.getNome(),
                            artista.getIdade(),
                            artista.getGenero().getCodigo());
    }

    public boolean update(Artista artista) {
        String sql = """
                update artista
                set nome_artista = ?,
                    idade_artista = ?,
                    cod_genero = ?
                where cod_artista = ?
                """;
        return conexaoBanco.update(sql,    
                artista.getNome(),
                artista.getIdade(),
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
                            idade_artista,
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
                artista.setIdade(rs.getInt("idade_artista"));

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