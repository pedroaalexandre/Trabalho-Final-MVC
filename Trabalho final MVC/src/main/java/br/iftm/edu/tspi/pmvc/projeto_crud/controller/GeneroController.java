package br.iftm.edu.tspi.pmvc.projeto_crud.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.iftm.edu.tspi.pmvc.projeto_crud.domain.Genero;
import br.iftm.edu.tspi.pmvc.projeto_crud.repository.GeneroRepository;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/genero")
public class GeneroController {
    
    private final GeneroRepository repository;

    public static final String URL_INICIAL = "genero/listaGeneros";
    public static final String URL_FORM = "genero/criar";
    public static final String URL_REDIRECT_INICIAL = "redirect:/genero/listaGeneros";

    public static final String ATRIBUTO_MENSAGEM = "mensagem";
    public static final String ATRIBUTO_OBJETO = "genero";
    public static final String ATRIBUTO_LISTA = "generos";

    public GeneroController(GeneroRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/listaGeneros")
    public String listarGeneros(Model model) {
        List<Genero> generos = repository.listar();
        model.addAttribute(ATRIBUTO_LISTA, generos);
        return URL_INICIAL;
    }
    
    @GetMapping("/criar")
    public String redirecionarCriarGenero(Model model) {
        Genero genero = new Genero();
        model.addAttribute(ATRIBUTO_OBJETO, genero);
        return URL_FORM;
    }
    

    @GetMapping("/editar/{codigo}")
    public String formEditarGenero(@PathVariable("codigo") Integer codigo, Model model, RedirectAttributes redirectAttributes) {
        Genero generoBusca = repository.buscaPorCodGenero(codigo);
        if (generoBusca == null) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, codigo + " não encontrado.");
            return URL_REDIRECT_INICIAL;
        }else{
            model.addAttribute(ATRIBUTO_OBJETO, generoBusca);
            return URL_FORM;
        }
    }
    

    @PostMapping("/criar")
    public String salvar(@ModelAttribute("genero") Genero genero, RedirectAttributes redirectAttributes) {
        repository.novo(genero);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, genero.getDescricao() + " salvo com sucesso!");
        return URL_REDIRECT_INICIAL;
    }
    

    @PostMapping("/editar/{codigo}")
    public String atualizar(@PathVariable("codigo") Integer codigo, @ModelAttribute ("genero") Genero genero, RedirectAttributes redirectAttributes) {
        if(repository.update(genero)) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM,"Atualizado com sucesso!");
        }else{
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Não foi possível atualizar " + genero.getCodigo());
        }
        return URL_REDIRECT_INICIAL;
    }

    @PostMapping("/excluir/{codigo}")
    public String excluir(@PathVariable("codigo") Integer codigo, RedirectAttributes redirectAttributes) {
        repository.delete(codigo);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Gênero excluído com sucesso!");
        return URL_REDIRECT_INICIAL;
    }
    
    
    
}
