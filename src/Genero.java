package appspotify.src;

import java.text.Normalizer;
import java.util.Locale;

public enum Genero {
    ROCK("Rock"),
    POP("Pop"),
    MPB("MPB"),
    JAZZ("Jazz"),
    CLASSICA("Clássica"),
    SERTANEJO("Sertanejo"),
    FORRO("Forró"),
    PAGODE("Pagode"),
    FUNK("Funk"),
    ELETRONICA("Eletrônica"),
    HIP_HOP("Hip Hop"),
    REGGAE("Reggae"),
    BLUES("Blues"),
    COUNTRY("Country"),
    INDIE("Indie"),
    OUTRO("Outro");

    private final String rotulo;

    Genero(String rotulo) { this.rotulo = rotulo; }

    /** Rótulo legível (ex.: "Clássica"). */
    public String rotulo() { return rotulo; }

    @Override
    public String toString() { return rotulo; }

    /** Converte texto em Genero (ignora acentos/maiúsculas). Lança IllegalArgumentException se inválido. */
    public static Genero parse(String texto) {
        if (texto == null) throw new IllegalArgumentException("Gênero não pode ser nulo.");
        String norm = normalizar(texto);
        for (Genero g : values()) {
            String nomeEnum = g.name().toUpperCase(Locale.ROOT);         // ex.: CLASSICA, HIP_HOP
            String nomeRotulo = normalizar(g.rotulo);                     // ex.: CLASSICA, HIP HOP
            if (norm.equals(nomeEnum) || norm.equals(nomeRotulo)) return g;
            // também aceita com '_' ↔ ' '
            if (norm.replace(' ', '_').equals(nomeEnum)) return g;
            if (norm.replace('_', ' ').equals(nomeRotulo)) return g;
        }
        throw new IllegalArgumentException("Gênero inválido: " + texto);
    }

    private static String normalizar(String s) {
        String n = Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", ""); // remove acentos
        return n.trim().toUpperCase(Locale.ROOT);
    }
}

