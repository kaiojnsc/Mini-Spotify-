# Mini Spotify

Aplicação Java de console que simula funcionalidades básicas de um serviço de streaming musical, permitindo gerenciar usuários, playlists e um catálogo de mídias (músicas, podcasts e audiobooks).

## Funcionalidades

- **Gerenciamento de Usuários**
  - Criar, listar e abrir perfis de usuários.
  - Criar, listar, abrir e remover playlists de cada usuário.

- **Gerenciamento de Playlists**
  - Adicionar mídias do catálogo a uma playlist.
  - Remover itens por índice.
  - Limpar playlist ou visualizar seus itens.
  - Exibir resumo do usuário com suas playlists.

- **Gerenciamento do Catálogo**
  - Listar todas as mídias disponíveis.
  - Adicionar músicas, podcasts e audiobooks.
  - Remover mídias por título.

## Estrutura do Projeto

- `Main.java`: Ponto de entrada da aplicação, contendo menus e lógica principal.
- Outras classes de suporte (não incluídas no snippet):
  - `Usuario`, `Playlist`, `Catalogo`, `Midia`, `Musica`, `Podcast`, `Audiobook`, `Genero`.

## Como Executar

1. Certifique-se de ter o **Java 17+** instalado.
2. Compile os arquivos `.java`:
   ```bash
   javac appspotify/src/*.java
   ```
3. Execute o programa:
   ```bash
   java appspotify.src.Main
   ```

## Funcionalidades Opcionais

- A função `seedOpcional()` insere dados iniciais para testes.
- Para iniciar sem dados, basta comentar a chamada no método `main`.

## Contribuindo

1. Faça um fork do projeto.
2. Crie uma branch para a sua feature:
   ```bash
   git checkout -b minha-feature
   ```
3. Commit suas alterações:
   ```bash
   git commit -m "Adiciona minha feature"
   ```
4. Faça push para a branch:
   ```bash
   git push origin minha-feature
   ```
5. Abra um Pull Request.

**Desenvolvido como projeto educacional colaborativo para demonstrar conceitos de programação orientada a objetos e estruturação de projetos em Java.**

