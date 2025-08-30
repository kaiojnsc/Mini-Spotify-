package appspotify.src;

import java.util.*;

public class Main {

    private static final Scanner in = new Scanner(System.in);
    private static final Catalogo catalogo = new Catalogo();
    private static final Map<String, Usuario> usuarios = new HashMap<>(); // chave = nome (lowercase)

    public static void main(String[] args) {
        seedOpcional(); // comente se nao quiser dados iniciais
        loopPrincipal();
        System.out.println("Encerrado.");
    }

    // ===================== LOOP PRINCIPAL =====================
    private static void loopPrincipal() {
        while (true) {
            System.out.println("\n=== Mini Spotify (backend) ===");
            System.out.println("[1] Usuarios");
            System.out.println("[2] Catalogo");
            System.out.println("[0] Sair");
            System.out.print("Opcao: ");
            String op = in.nextLine().trim();

            try {
                switch (op) {
                    case "1" -> menuUsuarios();
                    case "2" -> menuCatalogo();
                    case "0" -> { return; }
                    default -> System.out.println("Opcao invalida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    // ===================== MENU USUARIOS =====================
    private static void menuUsuarios() {
        while (true) {
            System.out.println("\n--- Usuarios ---");
            System.out.println("[1] Criar usuario");
            System.out.println("[2] Listar usuarios");
            System.out.println("[3] Abrir usuario");
            System.out.println("[0] Voltar");
            System.out.print("Opcao: ");
            String op = in.nextLine().trim();

            switch (op) {
                case "1" -> criarUsuario();
                case "2" -> listarUsuarios();
                case "3" -> abrirUsuario();
                case "0" -> { return; }
                default -> System.out.println("Opcao invalida.");
            }
        }
    }

    private static void criarUsuario() {
        System.out.print("Nome do usuario: ");
        String nome = in.nextLine();
        System.out.print("Email (opcional): ");
        String email = in.nextLine();

        Usuario u = new Usuario(nome, email);
        usuarios.put(chave(nome), u);
        System.out.println("Usuario criado: " + u.getNome());
    }

    private static void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuario.");
            return;
        }
        System.out.println("Usuarios:");
        usuarios.values().stream()
                .sorted(Comparator.comparing(Usuario::getNome, String.CASE_INSENSITIVE_ORDER))
                .forEach(u -> System.out.println("- " + u.getNome() + (u.getEmail().isEmpty() ? "" : " <" + u.getEmail() + ">")));
    }

    private static void abrirUsuario() {
        System.out.print("Nome do usuario: ");
        String nome = in.nextLine();
        Usuario u = usuarios.get(chave(nome));
        if (u == null) {
            System.out.println("Usuario nao encontrado.");
            return;
        }
        menuUsuarioAberto(u);
    }

    // ===================== MENU USUARIO ABERTO =====================
    private static void menuUsuarioAberto(Usuario u) {
        while (true) {
            System.out.println("\n--- Usuario: " + u.getNome() + " ---");
            System.out.println("[1] Criar playlist");
            System.out.println("[2] Listar playlists");
            System.out.println("[3] Abrir playlist");
            System.out.println("[4] Remover playlist");
            System.out.println("[5] Resumo do usuario");
            System.out.println("[0] Voltar");
            System.out.print("Opcao: ");
            String op = in.nextLine().trim();

            switch (op) {
                case "1" -> {
                    System.out.print("Nome da playlist: ");
                    String nomePl = in.nextLine();
                    Playlist p = u.criarPlaylist(nomePl);
                    System.out.println("Playlist criada: " + p.getNome());
                }
                case "2" -> {
                    var lista = u.listarPlaylists();
                    if (lista.isEmpty()) System.out.println("(sem playlists)");
                    else lista.forEach(pl -> System.out.println("- " + pl));
                }
                case "3" -> {
                    System.out.print("Nome da playlist: ");
                    String nomePl = in.nextLine();
                    Playlist p = u.getPlaylist(nomePl);
                    if (p == null) { System.out.println("Playlist nao encontrada."); }
                    else menuPlaylistAberta(u, p);
                }
                case "4" -> {
                    System.out.print("Nome da playlist: ");
                    String nomePl = in.nextLine();
                    boolean ok = u.removerPlaylist(nomePl);
                    System.out.println(ok ? "Removida." : "Nao existia.");
                }
                case "5" -> u.imprimirResumo();
                case "0" -> { return; }
                default -> System.out.println("Opcao invalida.");
            }
        }
    }

    // ===================== MENU PLAYLIST ABERTA =====================
    private static void menuPlaylistAberta(Usuario u, Playlist p) {
        while (true) {
            System.out.println("\n--- Playlist: " + p.getNome() + " (dono: " + p.getDono() + ") ---");
            System.out.println("[1] Adicionar do catalogo (por titulo)");
            System.out.println("[2] Remover por indice");
            System.out.println("[3] Listar itens");
            System.out.println("[4] Limpar playlist");
            System.out.println("[0] Voltar");
            System.out.print("Opcao: ");
            String op = in.nextLine().trim();

            try {
                switch (op) {
                    case "1" -> {
                        System.out.print("Titulo exato da midia no catalogo: ");
                        String titulo = in.nextLine();
                        Midia m = catalogo.buscar(titulo);
                        if (m == null) System.out.println("Nao encontrada no catalogo.");
                        else { p.adicionar(m); System.out.println("Adicionada: " + m.getTitulo()); }
                    }
                    case "2" -> {
                        if (p.tamanho() == 0) { System.out.println("Playlist vazia."); break; }
                        System.out.print("Indice (1..n): ");
                        int idx = lerInt();
                        Midia removida = p.removerPorIndice(idx - 1);
                        System.out.println("Removida: " + removida.getTitulo());
                    }
                    case "3" -> p.imprimir();
                    case "4" -> { p.limpar(); System.out.println("Playlist limpa."); }
                    case "0" -> { return; }
                    default -> System.out.println("Opcao invalida.");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Indice invalido.");
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    // ===================== MENU CATALOGO =====================
    private static void menuCatalogo() {
        while (true) {
            System.out.println("\n--- Catalogo ---");
            System.out.println("[1] Listar midias");
            System.out.println("[2] Adicionar Musica");
            System.out.println("[3] Adicionar Podcast");
            System.out.println("[4] Adicionar Audiobook");
            System.out.println("[5] Remover por titulo");
            System.out.println("[0] Voltar");
            System.out.print("Opcao: ");
            String op = in.nextLine().trim();

            try {
                switch (op) {
                    case "1" -> catalogo.imprimirTudo();
                    case "2" -> adicionarMusica();
                    case "3" -> adicionarPodcast();
                    case "4" -> adicionarAudiobook();
                    case "5" -> {
                        System.out.print("Titulo exato: ");
                        String t = in.nextLine();
                        boolean ok = catalogo.remover(t);
                        System.out.println(ok ? "Removido." : "Nao existia.");
                    }
                    case "0" -> { return; }
                    default -> System.out.println("Opcao invalida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    // ===================== ADICOES AO CATALOGO =====================
    private static void adicionarMusica() {
        System.out.print("Titulo: ");
        String titulo = in.nextLine();
        System.out.print("Artista: ");
        String artista = in.nextLine();
        int dur = lerPositivo("Duracao (segundos): ");
        Genero g = lerGenero();
        System.out.print("Album (pode deixar vazio): ");
        String album = in.nextLine();

        catalogo.adicionar(new Musica(titulo, artista, dur, g, album));
        System.out.println("Musica adicionada.");
    }

    private static void adicionarPodcast() {
        System.out.print("Titulo do episodio: ");
        String titulo = in.nextLine();
        System.out.print("Host: ");
        String host = in.nextLine();
        int dur = lerPositivo("Duracao (segundos): ");
        Genero g = lerGenero();
        System.out.print("Programa (nome da serie): ");
        String programa = in.nextLine();
        int ep = lerPositivo("Numero do episodio (>=1): ");

        catalogo.adicionar(new Podcast(titulo, host, dur, g, programa, ep));
        System.out.println("Podcast adicionado.");
    }

    private static void adicionarAudiobook() {
        System.out.print("Titulo do livro: ");
        String titulo = in.nextLine();
        System.out.print("Autor: ");
        String autor = in.nextLine();
        System.out.print("Narrador: ");
        String narrador = in.nextLine();
        int dur = lerPositivo("Duracao (segundos): ");
        Genero g = lerGenero();

        catalogo.adicionar(new Audiobook(titulo, autor, narrador, dur, g));
        System.out.println("Audiobook adicionado.");
    }

    // ===================== HELPERS =====================
    private static String chave(String s) { return s.trim().toLowerCase(Locale.ROOT); }

    private static int lerInt() {
        String s = in.nextLine().trim();
        try { return Integer.parseInt(s); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Digite um numero inteiro."); }
    }

    private static int lerPositivo(String prompt) {
        System.out.print(prompt);
        int v = lerInt();
        if (v <= 0) throw new IllegalArgumentException("O valor deve ser > 0.");
        return v;
    }

    private static Genero lerGenero() {
        System.out.println("Genero (ex.: Rock, Pop, MPB, Jazz, Classica, Sertanejo, Forro, Pagode, Funk, Eletronica, Hip Hop, Reggae, Blues, Country, Indie, Outro)");
        System.out.print("Digite o genero: ");
        String gtxt = in.nextLine();
        return Genero.parse(gtxt); // lanca excecao se invalido
    }

    // ===================== DADOS OPCIONAIS =====================
    private static void seedOpcional() {
        try {
            catalogo.adicionar(new Musica("Imagine", "John Lennon", 183, Genero.ROCK, "Imagine"));
            catalogo.adicionar(new Podcast("IA em 2025", "Lando", 2400, Genero.OUTRO, "TechTalks", 42));
            catalogo.adicionar(new Audiobook("1984", "George Orwell", "Narrador X", 36000, Genero.OUTRO));

            Usuario u = new Usuario("Lando", "lando@example.com");
            usuarios.put(chave(u.getNome()), u);
            Playlist p = u.criarPlaylist("Foco nos estudos");
            p.adicionar(catalogo.buscar("Imagine"));
        } catch (Exception ignored) {
            // se rodar mais de uma vez, pode dar duplicado; ignoramos aqui
        }
    }
}
