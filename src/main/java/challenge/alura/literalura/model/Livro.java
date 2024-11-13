package challenge.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @Enumerated(value = EnumType.STRING)
    private Categoria idiomas;

    private Integer downloads;

    @ManyToOne
    private Autor autor;

    public Livro(){

    }

    public Livro(DadosLivro dadosLivro){
        this.titulo = dadosLivro.title();
        this.idiomas = Categoria.fromString(dadosLivro.languages().get(0));
        this.downloads = dadosLivro.downloads();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Categoria getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Categoria idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public String getAutor() {
        return autor.getNome();
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "titulo='" + titulo + '\'' +
                ", idiomas=" + idiomas +
                ", downloads=" + downloads +
                ", autor=" + autor.getNome();
    }
}
