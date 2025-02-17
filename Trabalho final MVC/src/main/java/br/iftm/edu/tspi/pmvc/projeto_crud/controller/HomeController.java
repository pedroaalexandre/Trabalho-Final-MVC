package br.iftm.edu.tspi.pmvc.projeto_crud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        // Listar todas as músicas e gêneros
        List<Musica> musicas = musicaRepository.listar();
        List<Genero> generos = generoRepository.listar();

        // Adicionar as listas de músicas por gênero dinamicamente
        Map<Integer, List<Musica>> musicasPorGenero = new HashMap<>();
        for (Genero genero : generos) {
            List<Musica> musicasDoGenero = musicaRepository.listarPorGenero(genero.getCodigo());
            musicasPorGenero.put(genero.getCodigo(), musicasDoGenero);
        }

        // Adicionar as listas de músicas e gêneros ao modelo
        model.addAttribute("musicas", musicas);
        model.addAttribute("generos", generos);
        model.addAttribute("musicasPorGenero", musicasPorGenero);

        // Retornar a visão
        return "home/home";
    }

}