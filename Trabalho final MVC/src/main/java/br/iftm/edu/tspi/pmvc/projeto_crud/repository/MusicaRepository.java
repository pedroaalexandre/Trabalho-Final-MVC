package br.iftm.edu.tspi.pmvc.projeto_crud.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.iftm.edu.tspi.pmvc.projeto_crud.domain.Artista;
import br.iftm.edu.tspi.pmvc.projeto_crud.domain.Genero;
import br.iftm.edu.tspi.pmvc.projeto_crud.domain.Musica;

@Repository
public class MusicaRepository {

     private final JdbcTemplate conexaoBanco;
     
     public MusicaRepository(JdbcTemplate conexaoBanco) {
          this.conexaoBanco = conexaoBanco;
     }

     public List<Musica> listar() {
          String sql = """
                    select m.cod_musica, 
                         m.titulo,
                         m.ano_lancamento,
                         m.duracao,
                         m.imagem,
                         a.cod_artista,
                         a.nome_artista,
                         a.cod_genero,
                         g.desc_genero
                    from musica m, artista a, genero g
                    where a.cod_artista = m.cod_artista
                    and a.cod_genero = g.cod_genero
                    order by m.cod_musica
                    """;
          return conexaoBanco.query(sql, (rs, rowNum) -> {
          Musica musica = new Musica();
          musica.setCodigo(rs.getInt("cod_musica"));
          musica.setTitulo(rs.getString("titulo"));
          musica.setAnoLancamento(rs.getInt("ano_lancamento"));
          musica.setDuracao(rs.getString("duracao"));
          musica.setImagem(rs.getString("imagem"));

          Genero genero = new Genero();
          genero.setCodigo(rs.getInt("cod_genero"));
          genero.setDescricao(rs.getString("desc_genero"));

          Artista artista = new Artista();
          artista.setCodigo(rs.getInt("cod_artista"));
          artista.setNome(rs.getString("nome_artista"));
          artista.setGenero(genero);

          //musica.setGenero(genero);
          musica.setArtista(artista);
          return musica;
          });
     }

     public Musica buscaPorCodigo(Integer codigo) {
          String sql = """
              select m.cod_musica,  
                     m.titulo, 
                     m.ano_lancamento, 
                     m.duracao,
                     m.imagem,
                     a.cod_artista,
                     a.nome_artista,
                     g.cod_genero,
                     g.desc_genero
              from musica m
              join artista a on a.cod_artista = m.cod_artista
              join genero g on g.cod_genero = a.cod_genero
              where m.cod_musica = ?
          """;
          return conexaoBanco.queryForObject(sql, (rs, rowNum) -> {
              Musica musica = new Musica();
              musica.setCodigo(rs.getInt("cod_musica"));
              musica.setTitulo(rs.getString("titulo"));
              musica.setAnoLancamento(rs.getInt("ano_lancamento"));
              musica.setDuracao(rs.getString("duracao"));
              musica.setImagem(rs.getString("imagem"));
      
              Genero genero = new Genero();
              genero.setCodigo(rs.getInt("cod_genero"));
              genero.setDescricao(rs.getString("desc_genero"));
      
              Artista artista = new Artista();
              artista.setCodigo(rs.getInt("cod_artista"));
              artista.setNome(rs.getString("nome_artista"));
              artista.setGenero(genero);
      
              musica.setArtista(artista);
              return musica;
          }, codigo);
      }

      public void novo(Musica musica) {
          String sql = "INSERT INTO musica (titulo, ano_lancamento, duracao, cod_artista, imagem) VALUES (?, ?, ?, ?, ?)";
          conexaoBanco.update(sql, 
              musica.getTitulo(),
              musica.getAnoLancamento(),
              musica.getDuracao(),
              musica.getArtista().getCodigo(),
              musica.getImagem()
          );
      }

     public boolean delete(Integer codigo) {
          String sql = "delete from musica where cod_musica = ?";
          return conexaoBanco.update(sql,codigo) > 0;
     }

     public boolean update(Musica musica) {
          String sql = "UPDATE musica SET titulo = ?, ano_lancamento = ?, duracao = ?, cod_artista = ?, imagem = ? WHERE cod_musica = ?";
          return conexaoBanco.update(sql,
              musica.getTitulo(),
              musica.getAnoLancamento(),
              musica.getDuracao(),
              musica.getArtista().getCodigo(),
              musica.getImagem(),
              musica.getCodigo()) > 0;
      }

      public List<Musica> listarPorGenero(Integer codGenero) {
          String sql = """
              SELECT m.cod_musica, 
                     m.titulo,
                     m.ano_lancamento,
                     m.duracao,
                     m.imagem,
                     a.cod_artista,
                     a.nome_artista,
                     a.cod_genero,
                     g.desc_genero
              FROM musica m
              JOIN artista a ON a.cod_artista = m.cod_artista
              JOIN genero g ON g.cod_genero = a.cod_genero
              WHERE g.cod_genero = ?
          """;
      
          return conexaoBanco.query(sql, (rs, rowNum) -> {
              Musica musica = new Musica();
              musica.setCodigo(rs.getInt("cod_musica"));
              musica.setTitulo(rs.getString("titulo"));
              musica.setAnoLancamento(rs.getInt("ano_lancamento"));
              musica.setDuracao(rs.getString("duracao"));
              musica.setImagem(rs.getString("imagem"));
      
              Genero genero = new Genero();
              genero.setCodigo(rs.getInt("cod_genero"));
              genero.setDescricao(rs.getString("desc_genero"));
      
              Artista artista = new Artista();
              artista.setCodigo(rs.getInt("cod_artista"));
              artista.setNome(rs.getString("nome_artista"));
              artista.setGenero(genero);
      
              musica.setArtista(artista);
              return musica;
          }, codGenero);
      }
      
}
