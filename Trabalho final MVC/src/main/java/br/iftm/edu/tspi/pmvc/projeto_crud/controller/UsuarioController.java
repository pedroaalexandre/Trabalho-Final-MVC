package br.iftm.edu.tspi.pmvc.projeto_crud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.iftm.edu.tspi.pmvc.projeto_crud.domain.Usuario;
import br.iftm.edu.tspi.pmvc.projeto_crud.service.UsuarioService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

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

    @GetMapping("/usuario/cadastro")
    public String novo(Model model) {
        return URL_FORM;
    }
    
    @PostMapping("/usuario/login")
    public String validarLogin(Usuario login, Model model) {
        if(service.verificaLogin(login)) {
            return "redirect:/home";
        } else {
            model.addAttribute("mensagem", "Usuário ou senha inválidos");
            return "usuario/login";
        }
    }

    @PostMapping("/usuario/cadastro")
    public String cadastro(Usuario loginDigitado, Model model) {
        service.salvar(loginDigitado);
        model.addAttribute(ATRIBUTO_MENSAGEM, "Usuário " + loginDigitado.getLogin() + " cadastrado com sucesso");
        return "usuario/login";
    }
}