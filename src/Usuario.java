package appspotify.src;

import java.util.*;

/**
 * Representa um usuário do mini-Spotify que possui várias playlists.
 */
public class Usuario {
    private final String nome;
    private final String email; // opcional, pode ser vazio
    // chave = nome da playlist em minúsculas (para evitar duplicatas por caixa)
    private final Map<String, Playlist> playlists = new HashMap<>();

    public Usuario(String nome, String email) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome do usuário não pode ser vazio.");
        this.nome = nome.trim();

        // email é opcional; se vier, valida minimamente
        if (email == null || email.isBlank()) {
            this.email = "";
        } else {
            String e = email.trim();
            if (!e.contains("@") || !e.contains(".")) {
                throw new IllegalArgumentException("E-mail inválido.");
            }
            this.email = e;
        }
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }

    /** Cria playlist com este usuário como dono. Lança exceção se já existir. */
    public Playlist criarPlaylist(String nomePlaylist) {
        if (nomePlaylist == null || nomePlaylist.isBlank())
            throw new IllegalArgumentException("Nome da playlist não pode ser vazio.");
        String chave = chave(nomePlaylist);
        if (playlists.containsKey(chave))
            throw new IllegalArgumentException("Já existe uma playlist com esse nome: " + nomePlaylist);

        Playlist p = new Playlist(nomePlaylist.trim(), this.nome);
        playlists.put(chave, p);
        return p;
    }

    /** Remove playlist pelo nome (case-insensitive). Retorna true se removeu. */
    public boolean removerPlaylist(String nomePlaylist) {
        if (nomePlaylist == null) return false;
        return playlists.remove(chave(nomePlaylist)) != null;
    }

    /** Obtém a playlist pelo nome (case-insensitive) ou null se não existir. */
    public Playlist getPlaylist(String nomePlaylist) {
        if (nomePlaylist == null) return null;
        return playlists.get(chave(nomePlaylist));
    }

    /** Lista playlists em ordem alfabética. */
    public List<Playlist> listarPlaylists() {
        List<Playlist> lista = new ArrayList<>(playlists.values());
        lista.sort(Comparator.comparing(Playlist::getNome, String.CASE_INSENSITIVE_ORDER));
        return lista;
    }

    /** Atalho: adiciona mídia a uma playlist do usuário. */
    public void adicionarMidia(String nomePlaylist, Midia m) {
        Playlist p = exigirPlaylist(nomePlaylist);
        p.adicionar(m);
    }

    /** Atalho: remove mídia por índice de uma playlist. */
    public Midia removerMidiaPorIndice(String nomePlaylist, int indice) {
        Playlist p = exigirPlaylist(nomePlaylist);
        return p.removerPorIndice(indice);
    }

    /** Imprime um resumo do usuário e de suas playlists. */
    public void imprimirResumo() {
        System.out.println("Usuário: " + nome + (email.isEmpty() ? "" : " <" + email + ">"));
        List<Playlist> lista = listarPlaylists();
        if (lista.isEmpty()) {
            System.out.println("  (sem playlists)");
            return;
        }
        for (Playlist p : lista) {
            System.out.println("- " + p);
        }
    }

    @Override
    public String toString() {
        return "Usuario \"" + nome + "\" (playlists: " + playlists.size() + ")";
    }

    // --- utilitários privados ---
    private static String chave(String s) {
        return s.trim().toLowerCase(Locale.ROOT);
    }

    private Playlist exigirPlaylist(String nomePlaylist) {
        Playlist p = getPlaylist(nomePlaylist);
        if (p == null) throw new NoSuchElementException("Playlist não encontrada: " + nomePlaylist);
        return p;
    }
}
