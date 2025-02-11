package br.iftm.edu.tspi.pmvc.projeto_crud.domain;

public class Musica {
     
     private Integer cod_musica;
     private String titulo; 
     private int ano_lancamento; 
     private String duracao;
     private String artista;

     public Musica() {
     }

     public Musica(Integer cod_musica) {
          this.cod_musica = cod_musica;
     }

     public Musica(Integer cod_musica, String titulo) {
          this.cod_musica = cod_musica;
          this.titulo = titulo;
     }

     public Musica(Integer cod_musica, String titulo, int ano_lancamento, String duracao, String artista) {
          this.cod_musica = cod_musica;
          this.titulo = titulo;
          this.ano_lancamento = ano_lancamento;
          this.duracao = duracao;
          this.artista = artista;
     }

     public Integer getCod_musica() {
          return cod_musica;
     }

     public void setCod_musica(Integer cod_musica) {
          this.cod_musica = cod_musica;
     }

     public String getTitulo() {
          return titulo;
     }

     public void setTitulo(String titulo) {
          this.titulo = titulo;
     }

     public int getAno_lancamento() {
          return ano_lancamento;
     }

     public void setAno_lancamento(int ano_lancamento) {
          this.ano_lancamento = ano_lancamento;
     }

     public String getDuracao() {
          return duracao;
     }

     public void setDuracao(String duracao) {
          this.duracao = duracao;
     }

     public String getArtista() {
          return artista;
     }

     public void setArtista(String artista) {
          this.artista = artista;
     }

     @Override
     public int hashCode() {
          final int prime = 31;
          int result = 1;
          result = prime * result + ((cod_musica == null) ? 0 : cod_musica.hashCode());
          return result;
     }

     @Override
     public boolean equals(Object obj) {
          if (this == obj)
               return true;
          if (obj == null)
               return false;
          if (getClass() != obj.getClass())
               return false;
          Musica other = (Musica) obj;
          if (cod_musica == null) {
               if (other.cod_musica != null)
                    return false;
          } else if (!cod_musica.equals(other.cod_musica))
               return false;
          return true;
     }   
   
}
