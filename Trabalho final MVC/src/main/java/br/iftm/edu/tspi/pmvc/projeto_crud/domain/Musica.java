package br.iftm.edu.tspi.pmvc.projeto_crud.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Musica {
     
     private Integer codigo;
     private String titulo; 
     private Integer anoLancamento;
     private String duracao;
     private Artista artista;
     private String imagem;


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
