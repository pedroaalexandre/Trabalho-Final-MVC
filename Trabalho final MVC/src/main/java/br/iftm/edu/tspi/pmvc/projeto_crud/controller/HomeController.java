package br.iftm.edu.tspi.pmvc.projeto_crud.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.iftm.edu.tspi.pmvc.projeto_crud.domain.Genero;
import br.iftm.edu.tspi.pmvc.projeto_crud.domain.Musica;
import br.iftm.edu.tspi.pmvc.projeto_crud.repository.GeneroRepository;
import br.iftm.edu.tspi.pmvc.projeto_crud.repository.MusicaRepository;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final MusicaRepository musicaRepository;
    private final GeneroRepository generoRepository;

    public HomeController(MusicaRepository musicaRepository, GeneroRepository generoRepository) {
        this.musicaRepository = musicaRepository;
        this.generoRepository = generoRepository;
    }

    @GetMapping
    public String home(Model model) {
        List<Musica> musicas = musicaRepository.listar();
        List<Genero> generos = generoRepository.listar();
        
        model.addAttribute("musicas", musicas);
        model.addAttribute("generos", generos);

        return "home/home";
    }
}