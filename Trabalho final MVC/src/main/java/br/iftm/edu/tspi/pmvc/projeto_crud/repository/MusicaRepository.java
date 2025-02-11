package br.iftm.edu.tspi.pmvc.projeto_crud.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.iftm.edu.tspi.pmvc.projeto_crud.domain.Musica;

@Repository
public class MusicaRepository {

     private final JdbcTemplate conexaoBanco;
     
     public MusicaRepository(JdbcTemplate conexaoBanco) {
          this.conexaoBanco = conexaoBanco;
     }

     public List<Musica> listar() {
          String sql = """
                    select cod_musica, 
                         titulo,
                         ano_lancamento,
                         duracao, 
                         artista
                    from musica;
                    """;
          return conexaoBanco.query(sql, (res, rowNum) -> 
               new Musica(
                    res.getInt("cod_musica"),
                    res.getString("titulo"),
                    res.getInt("ano_lancamento"),
                    res.getString("duracao"),
                    res.getString("artista")
               )
          );
     }

     public Musica buscaPorCodigo(Integer codigo) {
          String sql = """
                    select cod_musica,  
                         titulo, 
                         ano_lancamento, 
                         duracao, 
                         artista
                    from musica
                    where cod_musica = ? 
                    """;
          return conexaoBanco.queryForObject(sql, 
               new BeanPropertyRowMapper<>(Musica.class),
               codigo);
     }

     public void novo (Musica musica) {
          String sql = "insert into musica(titulo, ano_lancamento, duracao, artista) values (?, ?, ?, ?)";
          conexaoBanco.update(sql, 
               musica.getTitulo(),
               musica.getAno_lancamento(),
               musica.getDuracao(),
               musica.getArtista());
     }

     public boolean delete(Integer codigo) {
          String sql = "delete from musica where cod_musica = ?";
          return conexaoBanco.update(sql,codigo) > 0;
     }

     public boolean update(Musica musica) {
          String sql = "update musica set titulo = ?, ano_lancamento = ?, duracao = ?, artista = ? where cod_musica = ?";
          return conexaoBanco.update(sql,
               musica.getTitulo(),
               musica.getAno_lancamento(),
               musica.getDuracao(),
               musica.getArtista(), 
               musica.getCod_musica()) > 0;
     }     
}
