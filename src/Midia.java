package appspotify.src;

import java.util.Objects;

public abstract class Midia {
    private final String titulo;
    private final String artista;
    private final int duracaoSegundos;  // deve ser > 0
    private final Genero genero;

    protected Midia(String titulo, String artista, int duracaoSegundos, Genero genero) {
        if (titulo == null || titulo.isBlank())
            throw new IllegalArgumentException("Título não pode ser vazio.");
        if (artista == null || artista.isBlank())
            throw new IllegalArgumentException("Artista não pode ser vazio.");
        if (duracaoSegundos <= 0)
            throw new IllegalArgumentException("Duração deve ser positiva (em segundos).");
        if (genero == null)
            throw new IllegalArgumentException("Gênero não pode ser nulo.");

        this.titulo = titulo.trim();
        this.artista = artista.trim();
        this.duracaoSegundos = duracaoSegundos;
        this.genero = genero;
    }

    // --- Getters (encapsulamento) ---
    public String getTitulo() { return titulo; }
    public String getArtista() { return artista; }
    public int getDuracaoSegundos() { return duracaoSegundos; }
    public Genero getGenero() { return genero; }

    // --- Polimorfismo: cada subtipo define seu "tipo" e "detalhes" ---
    public abstract String tipo();      // ex.: "Música", "Podcast", "Audiobook"
    public abstract String detalhes();  // texto adicional específico do subtipo

    @Override
    public String toString() {
        return "%s: \"%s\" - %s (%s) [%ds]".formatted(
            tipo(), getTitulo(), getArtista(), getGenero().rotulo(), getDuracaoSegundos()
    );
}


    // Igualdade por conteúdo essencial (tipo + título + artista)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Midia m)) return false;
        return Objects.equals(titulo, m.titulo)
                && Objects.equals(artista, m.artista)
                && Objects.equals(this.tipo(), m.tipo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, artista, tipo());
    }
}
