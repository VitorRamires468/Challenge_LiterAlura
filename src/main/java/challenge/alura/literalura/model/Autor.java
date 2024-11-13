package challenge.alura.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;
    private Integer anoDeNascimento;
    private Integer anoDeFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Livro> livros = new ArrayList<>();

    public Autor(){
    }

    public Autor(DadosAutor dadosAutor) {
        this.nome = dadosAutor.name().split(",")[0];
        this.anoDeNascimento = dadosAutor.birthYear();
        this.anoDeFalecimento = dadosAutor.deathyear();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoDeNascimento() {
        return anoDeNascimento;
    }

    public void setAnoDeNascimento(Integer anoDeNascimento) {
        this.anoDeNascimento = anoDeNascimento;
    }

    public Integer getAnoDeFalecimento() {
        return anoDeFalecimento;
    }

    public void setAnoDeFalecimento(Integer anoDeFalecimento) {
        this.anoDeFalecimento = anoDeFalecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        livros.forEach(livro -> livro.setAutor(this));
        this.livros = livros;
    }

    @Override
    public String toString() {
        return "nome='" + nome + '\'' +
                ", anoDeNascimento=" + anoDeNascimento +
                ", anoDeFalecimento=" + anoDeFalecimento +
                ", livros=" + livros;
    }
}
