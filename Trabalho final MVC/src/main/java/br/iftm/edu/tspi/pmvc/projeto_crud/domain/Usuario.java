package br.iftm.edu.tspi.pmvc.projeto_crud.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Usuario {

    private String login;
    private String senha;
    private String nome;
}


