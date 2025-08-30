package appspotify.src;

import java.util.*;

/**
 * Catálogo central de mídias (Música, Podcast, Audiobook).
 * Armazena em memória e permite adicionar, buscar, remover e listar.
 */
public class Catalogo {
    // chave = título em letras minúsculas (para evitar duplicados com diferença de maiúscula)
    private final Map<String, Midia> midias = new HashMap<>();

    /**
     * Adiciona uma mídia ao catálogo.
     * Lança exceção se já existir uma com o mesmo título.
     */
    public void adicionar(Midia m) {
        String chave = m.getTitulo().toLowerCase(Locale.ROOT);
        if (midias.containsKey(chave)) {
            throw new IllegalArgumentException("Já existe uma mídia com esse título: " + m.getTitulo());
        }
        midias.put(chave, m);
    }

    /**
     * Busca uma mídia pelo título (ignora maiúsculas/minúsculas).
     * @return Midia encontrada ou null se não existir.
     */
    public Midia buscar(String titulo) {
        if (titulo == null) return null;
        return midias.get(titulo.toLowerCase(Locale.ROOT));
    }

    /**
     * Remove mídia pelo título.
     * @return true se removeu, false se não existia.
     */
    public boolean remover(String titulo) {
        if (titulo == null) return false;
        return midias.remove(titulo.toLowerCase(Locale.ROOT)) != null;
    }

    /**
     * Lista todas as mídias (ordem alfabética por título).
     */
    public List<Midia> listar() {
        List<Midia> lista = new ArrayList<>(midias.values());
        lista.sort(Comparator.comparing(Midia::getTitulo));
        return lista;
    }

    /**
     * Mostra todas as mídias impressas no console.
     */
    public void imprimirTudo() {
        if (midias.isEmpty()) {
            System.out.println("Catálogo vazio.");
        } else {
            listar().forEach(System.out::println);
        }
    }
}
