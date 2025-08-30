package appspotify.src;

/**
 * Representa um Audiobook (livro narrado em áudio).
 */
public class Audiobook extends Midia {
    private final String autor;    // autor do livro
    private final String narrador; // quem narra o audiobook

    /**
     * Constrói um Audiobook.
     *
     * @param titulo           Título do livro
     * @param autor            Autor da obra
     * @param narrador         Narrador do audiobook
     * @param duracaoSegundos  Duração em segundos (>0)
     * @param genero           Gênero associado (literário/musical)
     */
    public Audiobook(String titulo,
                     String autor,
                     String narrador,
                     int duracaoSegundos,
                     Genero genero) {

        // no "artista" da classe base vamos guardar o narrador principal
        super(titulo, narrador, duracaoSegundos, genero);

        if (autor == null || autor.isBlank())
            throw new IllegalArgumentException("Autor não pode ser vazio.");
        if (narrador == null || narrador.isBlank())
            throw new IllegalArgumentException("Narrador não pode ser vazio.");

        this.autor = autor.trim();
        this.narrador = narrador.trim();
    }

    public String getAutor() { return autor; }
    public String getNarrador() { return narrador; }

    @Override
    public String tipo() {
        return "Audiobook";
    }

    @Override
    public String detalhes() {
        return "Autor: " + autor + " | Narrador: " + narrador;
    }

    @Override
    public String toString() {
        return super.toString() + " | " + detalhes();
    }
}
