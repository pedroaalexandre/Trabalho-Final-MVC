package br.iftm.edu.tspi.pmvc.projeto_crud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.iftm.edu.tspi.pmvc.projeto_crud.domain.Usuario;
import br.iftm.edu.tspi.pmvc.projeto_crud.service.UsuarioService;






@Controller
public class UsuarioController {
    
    private final UsuarioService service;

    public static final String URL_LOGIN = "usuario/login";
    public static final String URL_LISTA = "usuario/lista";
    public static final String URL_FORM = "usuario/cadastro";
    public static final String URL_REDIRECT_LOGIN = "redirect:/";

    public static final String ATRIBUTO_MENSAGEM = "mensagem";

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String telaInicial(Model model) {
        return URL_LOGIN;
    }

    @PostMapping("/usuario/login")
    public String validarLogin(Usuario login, Model model) {
        if(service.verificaLogin(login)) {
            model.addAttribute("mensagem", "Usuário logado com sucesso");
        }else{
            model.addAttribute("mensagem", "Usuário ou senha inválidos");
        }
        return URL_LOGIN;
    }

    // @PostMapping("/usuario/login")
    //     public String logar(Usuario loginDigitado,Model model) {
    //     model.addAttribute("mensagem","Você digitou o usuário"+ loginDigitado.getLogin() + " e senha "+loginDigitado.getSenha());
    //     return URL_LOGIN;

    // }
    
}