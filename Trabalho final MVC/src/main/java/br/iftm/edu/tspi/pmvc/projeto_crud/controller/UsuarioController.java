package br.iftm.edu.tspi.pmvc.projeto_crud.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.iftm.edu.tspi.pmvc.projeto_crud.domain.Usuario;
import br.iftm.edu.tspi.pmvc.projeto_crud.repository.UsuarioRepository;




@Controller
@RequestMapping("/")
public class UsuarioController {
    
    private final UsuarioRepository repository;

    public static final String URL_LOGIN = "usuario/login";
    public static final String URL_LISTA = "usuario/lista";
    public static final String URL_FORM = "usuario/cadastro";
    public static final String URL_REDIRECT_LOGIN = "redirect:/";

    public static final String ATRIBUTO_MENSAGEM = "mensagem";
    public static final String ATRIBUTO_OBJETO = "usuario";
    public static final String ATRIBUTO_LISTA = "usuarios";

    public UsuarioController(UsuarioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String login(Model model) {
        return URL_LOGIN;
    }

    @GetMapping("/usuario/cadastro")
    public String redirecionarCadastro(Model model) {
        Usuario usuario = new Usuario();
        model.addAttribute(ATRIBUTO_OBJETO, usuario);
        return URL_FORM;
    }
    
    
    @GetMapping("/usuario/lista")
    public String listar(Model model) {
        List<Usuario> usuarios = repository.listar();
        model.addAttribute(ATRIBUTO_LISTA, usuarios);
        return URL_LISTA;
    }


    @GetMapping("/usuario/editar/{login}")
    public String abrirFormEditar(@PathVariable("login") String login, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuarioBusca = repository.buscaPorLogin(login);
        if(usuarioBusca == null) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, login + " não encontrado.");
            return URL_REDIRECT_LOGIN;
        }else{
            model.addAttribute(ATRIBUTO_OBJETO, usuarioBusca);
            return URL_FORM;
        }
    }    

    @PostMapping("/usuario/cadastro")
    public String salvar(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes redirectAttributes) {
        repository.novo(usuario);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, usuario.getNome() + " salvo com sucesso!");
        return URL_REDIRECT_LOGIN;
    }

    @PostMapping("/usuario/excluir/{login}")
    public String excluir(@PathVariable("login") String login, RedirectAttributes redirectAttributes) {
        repository.delete(login);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Usuário excluído com sucesso.");
        return URL_REDIRECT_LOGIN;
    }
    
    @PostMapping("/usuario/editar/{login}")
    public String atualizar(@PathVariable("login") String login, @ModelAttribute("usuario") Usuario usuario, RedirectAttributes redirectAttributes) {
        if (repository.update(usuario)) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, usuario.getNome()+ " atualizado com sucesso");
        } else {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, " Não foi possível atualizar "+usuario.getNome());
        }        
        return URL_REDIRECT_LOGIN;
    }
    
}