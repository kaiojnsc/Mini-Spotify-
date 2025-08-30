package appspotify.src;

public class Musica extends Midia {
    private final String album; // opcional, pode ser vazio

    public Musica(String titulo, String artista, int duracaoSegundos, Genero genero, String album) {
        super(titulo, artista, duracaoSegundos, genero);
        this.album = (album == null || album.isBlank()) ? "Desconhecido" : album.trim();
    }

    public String getAlbum() {
        return album;
    }

    @Override
    public String tipo() {
        return "Música";
    }

    @Override
    public String detalhes() {
        return "Álbum: " + album;
    }

    @Override
    public String toString() {
        return super.toString() + " | " + detalhes();
    }
}
