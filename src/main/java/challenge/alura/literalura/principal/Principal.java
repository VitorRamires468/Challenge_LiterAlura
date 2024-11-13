package challenge.alura.literalura.principal;

import challenge.alura.literalura.model.*;
import challenge.alura.literalura.repository.AutorRepository;
import challenge.alura.literalura.service.ConsumoApi;
import challenge.alura.literalura.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private final String URL = "https://gutendex.com/books/?search=";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados converteDados = new ConverteDados();
    private AutorRepository repository;

    public Principal(AutorRepository repository){
        this.repository = repository;
    }

    public void menu(){
        int opcao = -1;

        while(opcao!=0){
            System.out.println("\t1 - buscar livros pelo título\n\t2 - listar livros registrados\n\t3 - listar autores registrados\n\t4 - listar autores vivos em um determinado ano\n\t5 - listar livros em um determinado idioma\n\t0 - sair\n");
            opcao = leitura.nextInt();
            leitura.nextLine();
            switch (opcao){
                case 1:
                    buscarLivrosPeloTitulo();
                    break;
                case 2:
                    listarLivroRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEmDeterminadoAno();
                    break;
                case 5:
                    listarLivrosEmDeterminadoIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Escolha uma opção válida!");
                    break;
            }
        }
    }


    private DadosLivro buscarDadosLivroAPI(String titulo){
        String resposta = consumoApi.consomeApi(URL +titulo.toLowerCase().replace(" ","+"));
        Resultado resultado = converteDados.converteDados(resposta,Resultado.class);
        DadosLivro dadosLivro = resultado.dadosLivro().get(0);
        return dadosLivro;
    }

    public void exbibirLivro(Livro livro){
        System.out.println("-----LIVRO------");
        System.out.println("Livro: "+livro.getTitulo());
        System.out.println("Autor: "+livro.getAutor());
        System.out.println("Idioma: "+livro.getIdiomas());
        System.out.println("Downloads: "+livro.getDownloads());
        System.out.println("----------------");
    }

    private void buscarLivrosPeloTitulo(){
        System.out.println("Digite o nome do livro: ");
        var titulo = leitura.nextLine();
        DadosLivro dadosLivro = buscarDadosLivroAPI(titulo);
        Livro livro = new Livro(dadosLivro);
        Autor autor = new Autor(dadosLivro.author().get(0));
        List<Livro> livros = new ArrayList<>();
        livros.add(livro);

        Optional<Autor> autorNoBanco = repository.findByNomeContainingIgnoreCase(autor.getNome());
        if(autorNoBanco.isPresent()){
            Autor autorPresente = autorNoBanco.get();
            autorPresente.setLivros(livros);
            autorPresente.setLivros(livros);
            repository.save(autorPresente);
        }else{
            autor.setLivros(livros);
            repository.save(autor);
        }
        exbibirLivro(livro);

    }

    private void listarLivroRegistrados() {
        List<Livro> livrosCadastrados = repository.buscarLivros();
        livrosCadastrados.stream()
                .forEach(l -> exbibirLivro(l));

    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = repository.findAll();
        autores.forEach(System.out::println);
    }

    private void listarAutoresVivosEmDeterminadoAno() {
        System.out.println("Informe o ano para verificar autores vivos: ");
        int anoPesquisa = leitura.nextInt();
        leitura.nextLine();
        List<Autor> autores = repository.buscarAutoresVivos(anoPesquisa);

        autores.forEach(System.out::println);

    }

    private void listarLivrosEmDeterminadoIdioma() {
        System.out.println("Informe a categoria: ");
        for (Categoria categoria : Categoria.values()){
            System.out.println(categoria.getCategoria());
        }
        String idioma = leitura.nextLine();
        Categoria categoria = Categoria.fromString(idioma);

        List<Livro> livrosIdioma = repository.buscarPorIdioma(categoria);
        livrosIdioma.forEach(System.out::println);

    }

}
