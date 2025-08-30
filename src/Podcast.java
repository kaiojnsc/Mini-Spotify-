package appspotify.src;

public class Podcast extends Midia {
    private final String programa; // nome do podcast (série)
    private final int episodio;    // número do episódio (>=1)

    /**
     * @param titulo            Título do episódio
     * @param host              Apresentador (vai no campo "artista" da classe base)
     * @param duracaoSegundos   Duração em segundos (>0)
     * @param genero            Use Genero.OUTRO ou algo que faça sentido
     * @param programa          Nome do podcast (série)
     * @param episodio          Número do episódio (>=1)
     */
    public Podcast(String titulo,
                   String host,
                   int duracaoSegundos,
                   Genero genero,
                   String programa,
                   int episodio) {

        super(titulo, host, duracaoSegundos, genero);

        if (programa == null || programa.isBlank())
            throw new IllegalArgumentException("Programa do podcast não pode ser vazio.");
        if (episodio < 1)
            throw new IllegalArgumentException("Número do episódio deve ser >= 1.");

        this.programa = programa.trim();
        this.episodio = episodio;
    }

    public String getPrograma() { return programa; }
    public int getEpisodio() { return episodio; }

    @Override
    public String tipo() { return "Podcast"; }

    @Override
    public String detalhes() {
        return "Programa: " + programa + " | Episodio: " + episodio;
    }

    @Override
    public String toString() {
        return super.toString() + " | " + detalhes();
    }
}
