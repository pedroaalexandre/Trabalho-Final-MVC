package br.iftm.edu.tspi.pmvc.projeto_crud.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.iftm.edu.tspi.pmvc.projeto_crud.domain.Artista;
import br.iftm.edu.tspi.pmvc.projeto_crud.domain.Musica;
import br.iftm.edu.tspi.pmvc.projeto_crud.repository.ArtistaRepository;
import br.iftm.edu.tspi.pmvc.projeto_crud.repository.GeneroRepository;
import br.iftm.edu.tspi.pmvc.projeto_crud.repository.MusicaRepository;

@Controller
@RequestMapping("/musica")
public class MusicaController {

    private final ArtistaRepository artistaRepository;
    private final MusicaRepository musicaRepository;
    private final GeneroRepository generoRepository;

    public static final String URL_LISTA = "musica/listaMusica";
    public static final String URL_FORM = "musica/formulario";
    public static final String URL_REDIRECT_LISTA = "redirect:/musica/listar";

    public static final String ATRIBUTO_MENSAGEM = "mensagem";
    public static final String ATRIBUTO_OBJETO = "musica";
    public static final String ATRIBUTO_LISTA = "musicas";

    public static final String IMAGEM_PADRAO = "musica.webp";

    // Injeção de dependência para o diretório de upload
    @Value("${upload.dir}") // Diretório configurado no application.properties
    private String uploadDir;

    public MusicaController(MusicaRepository musicaRepository, ArtistaRepository artistaRepository, GeneroRepository generoRepository) {
        this.musicaRepository = musicaRepository;
        this.artistaRepository = artistaRepository;
        this.generoRepository = generoRepository;
    }

    // Será acessado para listar as músicas cadastradas;
    @GetMapping("/listar")
    public String indexListar(Model model) {
        List<Musica> musicas = musicaRepository.listar();
        model.addAttribute(ATRIBUTO_LISTA, musicas);
        return URL_LISTA;
    }

    @GetMapping("/buscar")
    public String buscarPorCodigo(@PathVariable("codigo") Integer codigo, Model model, RedirectAttributes redirectAttributes) {  
        Musica musicaBusca = musicaRepository.buscaPorCodigo(codigo);
        if (musicaBusca == null) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, codigo + "não encontrado.");
            return URL_REDIRECT_LISTA;
        } else {
            model.addAttribute(ATRIBUTO_OBJETO, musicaBusca);
            return URL_FORM;
        }
    }

    // Utilizado para abrir um formulário para o cadastro de uma nova música;
    @GetMapping("/novo")
    public String abrirFormNovo(Model Model) {
        Musica musica = new Musica();
        List<Artista> artistas = artistaRepository.listar();
        Model.addAttribute("artistas", artistas);
        Model.addAttribute(ATRIBUTO_OBJETO, musica);
        return URL_FORM;
    }

    @GetMapping("/editar/{codigo}")
    public String abrirFormEditar(@PathVariable("codigo") Integer codigo, Model model, RedirectAttributes redirectAttributes) {
        Musica musicaBusca = musicaRepository.buscaPorCodigo(codigo);
        if(musicaBusca == null) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, codigo + " não encontrado.");
            return URL_REDIRECT_LISTA;
        }else{
            model.addAttribute(ATRIBUTO_OBJETO, musicaBusca);
            List<Artista> artistas = artistaRepository.listar();
            model.addAttribute("artistas", artistas);
            return URL_FORM;
        }
    }

    // Utilizado para realizar uma requição de criação de uma nova música e inclui-la as informações na listagem;
    @PostMapping("/novo")
    public String salvar(@ModelAttribute("musica") Musica musica,
                        @RequestParam(value = "imagemFile", required = false) MultipartFile imagemFile, 
                        RedirectAttributes redirectAttributes) {

        // Verifica se um arquivo foi enviado
        if (imagemFile != null && !imagemFile.isEmpty()) {
            try {
                // Gera um nome único para evitar conflitos
                String nomeArquivo = UUID.randomUUID() + "_" + imagemFile.getOriginalFilename();
                Path caminhoArquivo = Paths.get(uploadDir, nomeArquivo);
                Files.write(caminhoArquivo, imagemFile.getBytes());

                musica.setImagem(nomeArquivo); // Salva o nome da imagem
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao salvar a imagem.");
                return "redirect:/musica/novo";
            }
        }else{
            musica.setImagem(IMAGEM_PADRAO);
        }

        musicaRepository.novo(musica); // 🔵 Agora a música já tem a imagem antes de ser salva no banco

        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Música " + musica.getTitulo() + " adicionada com sucesso!");        
        return URL_REDIRECT_LISTA;
    }
    
    // // Utilizado para realizar uma requição de atualização de uma música já criada e enviar as informações para a listagem;
    // @PostMapping("/editar/{codigo}")
    // public String editar(@PathVariable("codigo") Integer codigo, @ModelAttribute("codigo") Musica musica, RedirectAttributes redirectAttributes) {
    //     if (musicaRepository.update(musica)){
    //         redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, musica.getTitulo() + " atualizado com sucesso");
    //     } else {
    //         redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Não foi possível atualizar "+ musica.getTitulo());
    //     }
    //     return URL_REDIRECT_LISTA;
    // }

    @PostMapping("/editar/{codigo}")
        public String editar(@PathVariable("codigo") Integer codigo, 
                            @ModelAttribute("codigo") Musica musica, 
                            @RequestParam(value = "imagemFile", required = false) MultipartFile imagemFile, 
                            @RequestParam(value = "imagemExistente", required = false) String imagemExistente,
                            RedirectAttributes redirectAttributes) {

            // Verifica se um novo arquivo de imagem foi enviado
            if (imagemFile != null && !imagemFile.isEmpty()) {
                try {
                    // Gera um nome único para evitar conflitos
                    String nomeArquivo = UUID.randomUUID() + "_" + imagemFile.getOriginalFilename();
                    Path caminhoArquivo = Paths.get(uploadDir, nomeArquivo);
                    Files.write(caminhoArquivo, imagemFile.getBytes());

                    musica.setImagem(nomeArquivo); // Salva o nome da nova imagem
                } catch (IOException e) {
                    redirectAttributes.addFlashAttribute("mensagem", "Erro ao salvar a nova imagem.");
                    return "redirect:/musica/editar/" + codigo;
                }
            } else {
                // Mantém a imagem existente
                musica.setImagem(imagemExistente);
            }

            if (musicaRepository.update(musica)) {
                redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, musica.getTitulo() + " atualizado com sucesso");
            } else {
                redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Não foi possível atualizar " + musica.getTitulo());
            }
            return URL_REDIRECT_LISTA;
    }

    // Utilizado para realizar a exclusão de uma música e removê-la da listagem;
    @PostMapping(value = "/excluir/{codigo}")
    public String excluir(@PathVariable ("codigo") Integer codigo, Musica musica, RedirectAttributes redirectAttributes) {
        musicaRepository.delete(codigo);       
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, " Música excluída com sucesso!");    
        return URL_REDIRECT_LISTA;
    }
}
