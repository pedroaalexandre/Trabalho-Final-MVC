package br.iftm.edu.tspi.pmvc.projeto_crud.domain;

public class Musica {
     
     private Integer codigo;
     private String titulo; 
     private int anoLancamento; 
     private String duracao;
     private Artista artista;

     public Musica() {
     }

     public Musica(Integer codigo) {
          this.codigo = codigo;
     }

     public Musica(Integer codigo, String titulo) {
          this.codigo = codigo;
          this.titulo = titulo;
     }

     public Musica(Integer codigo, String titulo, int anoLancamento, String duracao, Artista artista) {
          this.codigo = codigo;
          this.titulo = titulo;
          this.anoLancamento = anoLancamento;
          this.duracao = duracao;
          this.artista = artista;
     }

     public Integer getCodigo() {
          return codigo;
     }

     public void setCodigo(Integer codigo) {
          this.codigo = codigo;
     }

     public String getTitulo() {
          return titulo;
     }

     public void setTitulo(String titulo) {
          this.titulo = titulo;
     }

     public int getAnoLancamento() {
          return anoLancamento;
     }

     public void setAnoLancamento(int anoLancamento) {
          this.anoLancamento = anoLancamento;
     }

     public String getDuracao() {
          return duracao;
     }

     public void setDuracao(String duracao) {
          this.duracao = duracao;
     }

     public Artista getArtista() {
          return artista;
     }

     public void setArtista(Artista artista) {
          this.artista = artista;
     }

     @Override
     public int hashCode() {
          final int prime = 31;
          int result = 1;
          result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
          if (codigo == null) {
               if (other.codigo != null)
                    return false;
          } else if (!codigo.equals(other.codigo))
               return false;
          return true;
     }   
   
}
