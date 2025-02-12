package br.iftm.edu.tspi.pmvc.projeto_crud.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
                    select cod_musica,  
                         titulo, 
                         ano_lancamento, 
                         duracao, 
                         artista
                    from musica
                    where codigo = ?
                    """;
          return conexaoBanco.queryForObject(sql, 
               new BeanPropertyRowMapper<>(Musica.class),
               codigo);
     }

     public void novo(Musica musica) {
          String sql = "insert into musica(titulo, ano_lancamento, duracao, cod_artista) values (?, ?, ?, ?)";
          conexaoBanco.update(sql, 
              musica.getTitulo(),
              musica.getAnoLancamento(),
              musica.getDuracao(),
              musica.getArtista().getCodigo());
     }

     public boolean delete(Integer codigo) {
          String sql = "delete from musica where cod_musica = ?";
          return conexaoBanco.update(sql,codigo) > 0;
     }

     public boolean update(Musica musica) {
          String sql = "update musica set titulo = ?, ano_lancamento = ?, duracao = ?, artista = ? where codigo = ?";
          return conexaoBanco.update(sql,
               musica.getTitulo(),
               musica.getAnoLancamento(),
               musica.getDuracao(),
               musica.getArtista(), 
               musica.getCodigo()) > 0;
     }     
}
