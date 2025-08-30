package appspotify.src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa uma playlist de mídias (Música, Podcast, Audiobook).
 * Mantém ordem de inserção (permite itens repetidos, como no Spotify).
 */
public class Playlist {
    private final String nome;
    private final String dono; // por enquanto, o nome do usuário (String). Depois ligaremos a um Usuario.
    private final List<Midia> itens = new ArrayList<>();

    public Playlist(String nome, String dono) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome da playlist não pode ser vazio.");
        if (dono == null || dono.isBlank())
            throw new IllegalArgumentException("Dono da playlist não pode ser vazio.");
        this.nome = nome.trim();
        this.dono = dono.trim();
    }

    public String getNome() { return nome; }
    public String getDono() { return dono; }

    /** Retorna uma visão imutável dos itens (evita alterações externas). */
    public List<Midia> getItens() { return Collections.unmodifiableList(itens); }

    /** Adiciona uma mídia ao final da playlist. */
    public void adicionar(Midia m) {
        if (m == null) throw new IllegalArgumentException("Mídia não pode ser nula.");
        itens.add(m);
    }

    /** Remove a PRIMEIRA ocorrência desta mídia. Retorna true se removeu. */
    public boolean remover(Midia m) {
        if (m == null) return false;
        return itens.remove(m);
    }

    /** Remove pelo índice (0-based). Lança IndexOutOfBoundsException se inválido. */
    public Midia removerPorIndice(int indice) {
        return itens.remove(indice);
    }

    /** Limpa todos os itens da playlist. */
    public void limpar() { itens.clear(); }

    /** Verifica se a playlist contém a mídia (usa equals de Midia). */
    public boolean contem(Midia m) { return itens.contains(m); }

    /** Conta quantos itens há na playlist. */
    public int tamanho() { return itens.size(); }

    /** Soma a duração total, em segundos. */
    public int duracaoTotalSegundos() {
        int total = 0;
        for (Midia m : itens) total += m.getDuracaoSegundos();
        return total;
    }

    /** Duração total formatada (HH:MM:SS). */
    public String duracaoTotalFormatada() {
        int total = duracaoTotalSegundos();
        int h = total / 3600;
        int rem = total % 3600;
        int m = rem / 60;
        int s = rem % 60;
        return String.format("%02d:%02d:%02d", h, m, s);
    }

    /** Imprime a playlist no console. */
    public void imprimir() {
        System.out.println("Playlist: " + nome + " (dono: " + dono + ")");
        if (itens.isEmpty()) {
            System.out.println("  [vazia]");
        } else {
            for (int i = 0; i < itens.size(); i++) {
                System.out.println("  " + (i + 1) + ") " + itens.get(i));
            }
            System.out.println("Total: " + tamanho() + " itens | Duração: " + duracaoTotalFormatada());
        }
    }

    @Override
    public String toString() {
        return "Playlist \"" + nome + "\" (dono: " + dono + ", itens: " + tamanho() + ", duracao: " + duracaoTotalFormatada() + ")";
    }
}
