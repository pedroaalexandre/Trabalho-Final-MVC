package br.iftm.edu.tspi.pmvc.projeto_crud.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.iftm.edu.tspi.pmvc.projeto_crud.domain.Artista;
import br.iftm.edu.tspi.pmvc.projeto_crud.domain.Genero;
import br.iftm.edu.tspi.pmvc.projeto_crud.repository.ArtistaRepository;
import br.iftm.edu.tspi.pmvc.projeto_crud.repository.GeneroRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/artista")
public class ArtistaController {

    private final GeneroRepository generoRepository;
    private final ArtistaRepository artistaRepository;

    public static final String URL_LISTA = "artista/lista";
    public static final String URL_FORM = "artista/form";
    public static final String URL_REDIRECT_LISTA = "redirect:/artista";

    public static final String ATRIBUTO_MENSAGEM = "mensagem";
    public static final String ATRIBUTO_OBJETO = "artista";
    public static final String ATRIBUTO_LISTA = "artistas";

    public ArtistaController(GeneroRepository generoRepository, ArtistaRepository artistaRepository) {
        this.generoRepository = generoRepository;
        this.artistaRepository = artistaRepository;
    }

    @GetMapping
    public String listar(Model model) {
        List<Artista> artistas = artistaRepository.listar();
        model.addAttribute(ATRIBUTO_LISTA, artistas);
        return URL_LISTA;
    }

    @GetMapping("/buscar")
    public String buscarPorNome(@RequestParam("nome") String nome, Model model) {
        List<Artista> artistaBusca = artistaRepository.buscarPorNome(nome);
        model.addAttribute(ATRIBUTO_LISTA, artistaBusca);
        if(artistaBusca.isEmpty()) {
            model.addAttribute(ATRIBUTO_MENSAGEM, nome + " não encontrado.");
        }
        return URL_LISTA;
    }

    @GetMapping("/novo")
    public String abrirFormNovo(Model model) {
        Artista artista = new Artista();
        List<Genero> generos = generoRepository.listar();
        model.addAttribute("generos", generos);
        model.addAttribute(ATRIBUTO_OBJETO, artista);
        return URL_FORM;
    }
    
    @GetMapping("/editar/{codigo}")
    public String abrirFormEditar(@PathVariable("codigo") Integer codigo, Model model, RedirectAttributes redirectAttributes) {
        Artista artistaBusca = artistaRepository.buscarPorCodigo(codigo);
        if(artistaBusca == null) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, codigo + " não encontrado.");
            return URL_REDIRECT_LISTA;
        }else{
            model.addAttribute(ATRIBUTO_OBJETO, artistaBusca);
            List<Genero> generos = generoRepository.listar();
            model.addAttribute("generos", generos);
            return URL_FORM;
        }
    }

    @PostMapping("/novo")
    public String salvar(@ModelAttribute("artista") Artista artista, RedirectAttributes redirectAttributes) {
        artistaRepository.novo(artista);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, artista.getNome() + " salvo com sucesso!");
        return URL_REDIRECT_LISTA;
    }
    
    @PostMapping(value = "/excluir/{codigo}")
    public String excluir(@PathVariable("codigo") Integer codigo, RedirectAttributes redirectAttributes) {
        artistaRepository.delete(codigo);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Artista excluído com sucesso.");
        return URL_REDIRECT_LISTA;
    }

    @PostMapping("/editar/{codigo}")
    public String atualizar(@PathVariable("codigo") Integer codigo, @ModelAttribute ("artista") Artista artista, RedirectAttributes redirectAttributes) {
        if(artistaRepository.update(artista)) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, artista.getCodigo() + " atualizada com sucesso!");
        }else{
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Não foi possível atualizar " + artista.getCodigo());
        }
        return URL_REDIRECT_LISTA;
    }
}
