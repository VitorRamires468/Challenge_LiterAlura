package challenge.alura.literalura.repository;

import challenge.alura.literalura.model.Autor;
import challenge.alura.literalura.model.Categoria;
import challenge.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {

    @Query("SELECT l FROM Livro l ORDER BY l.id")
    List<Livro> buscarLivros();

    @Query("SELECT DISTINCT a FROM Autor a WHERE a.anoDeNascimento < :anoPesquisa AND a.anoDeFalecimento > :anoPesquisa")
    List<Autor> buscarAutoresVivos(int anoPesquisa);

    Optional<Autor> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT l FROM Livro l WHERE l.idiomas = :categoria")
    List<Livro> buscarPorIdioma(Categoria categoria);
}
