import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.Random;
import java.util.Scanner;

public class BlackWindow {

    static Scanner leitor = new Scanner(System.in);

    // Função para exibir o banner
    public static void exibirBanner() {
        try (BufferedReader br = new BufferedReader(new FileReader("./App/src/banner.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao ler o arquivo de banner: " + e.getMessage());
        }
    }
    
    // Função para exibir o banner de agradecimento
    public static void exibirBannerAgradecimento() {
        try (BufferedReader br = new BufferedReader(new FileReader("./App/src/banner-agradecimento.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao ler o arquivo de banner: " + e.getMessage());
        }
    }

    // Função para remover um inimigo do array de inimigos
    public static Inimigo[] remove(Inimigo inimigo, Inimigo[] inimigos) {
        if (inimigos.length == 0) {
            return inimigos;
        }

        int length = inimigos.length - 1;
        Inimigo[] novosInimigos = new Inimigo[length];

        for (int i = 0, j = 0; i < inimigos.length; i++) {
            if (inimigos[i] != inimigo) {
                novosInimigos[j++] = inimigos[i];
            }
        }

        return novosInimigos;
    }

    // Função para executar a batalha
    public static int batalha(Personagem personagem) {
        int rodadas = 0;
        // int recorde = 0;
        Random rand = new Random();
        Inimigo[] inimigos = {new Inimigo("Sinnoh", 100), new Inimigo("Kalos", 150), new Inimigo("Alola", 200)};

        while (personagem.hp > 0 && existemInimigosVivos(inimigos)) {
            rodadas++;
            Inimigo inimigo = inimigos[rand.nextInt(inimigos.length)];

            while (personagem.hp > 0 && inimigo.hp > 0) {
                personagem.imprimeHP(personagem.hp, inimigo.hp);
                int escolha = personagem.atacar();

                switch (escolha) {
                    case 1:
                        int danoBasico = 10;
                        System.out.println(personagem.getNome() + " aplicou um ataque básico causando " + danoBasico + " de dano.");
                        inimigo.hp -= danoBasico;
                        break;
                    case 2:
                        if (personagem.contagemEspecial > 0) {
                            int danoEspecial = 20;
                            System.out.println(personagem.getNome() + " aplicou um ataque especial causando " + danoEspecial + " de dano.");
                            inimigo.hp -= danoEspecial;
                            personagem.contagemEspecial--;
                        } else {
                            System.out.println("Você não possui mais ataques especiais.");
                        }
                        break;
                    case 3:
                        personagem.recuperarVida();
                        break;
                    case 4:
                        personagem.desistir();
                        break;
                    default:
                        System.out.println("Opção inválida");
                        break;
                }

                if (inimigo.hp <= 0) {
                    inimigos = remove(inimigo, inimigos);
                    System.out.println(inimigo.nome + " derrotado");
                } else {
                    int escolhaInimigo = inimigo.atacar();
                    int danoComputador = (int) (escolhaInimigo * 2.2);
                    System.out.println(inimigo.nome + " aplicou um ataque causando " + danoComputador + " de dano.");
                    personagem.hp -= danoComputador;
                }
            }

            if (personagem.hp > 0) {
                personagem.hp += 5;
                if (personagem.hp > 150) {
                    personagem.hp = 150;
                }
                if (rodadas % 10 == 0) {
                    personagem.contagemEspecial++;
                    if (personagem.contagemEspecial > 5) {
                        personagem.contagemEspecial = 5;
                    }
                }
            }

            System.out.println("====================");
            System.out.println("Fim da rodada " + rodadas);
        }

        return 0;
    }

    // Função para verificar se existem inimigos vivos
    public static boolean existemInimigosVivos(Inimigo[] inimigos) {
        for (Inimigo inimigo : inimigos) {
            if (inimigo.hp > 0) {
                return true;
            }
        }
        return false;
    }

    // Função principal
    public static void main(String[] args) {
        int continua = 1;
        int recorde = 0;

        exibirBanner();

        while (continua == 1) {
            System.out.println("Escolha um personagem:");
            System.out.println("(1) Pikachu");
            System.out.println("(2) Charmander");
            System.out.println("(3) Squirtle");
            System.out.println("(4) Bulbasaur");

            int escolhaPersonagem;
            while (true) {
                System.out.print("Escolha um número entre 1 e 4: ");
                escolhaPersonagem = leitor.nextInt();
                if (escolhaPersonagem >= 1 && escolhaPersonagem <= 4) {
                    break;
                } else {
                    System.out.println("Escolha inválida. Tente novamente.");
                }
            }

            Personagem personagemEscolhido = null;

            boolean confirmacao = false;
            while (!confirmacao) {
                switch (escolhaPersonagem) {
                    case 1:
                        personagemEscolhido = new Pikachu();
                        break;
                    case 2:
                        personagemEscolhido = new Charmander();
                        break;
                    case 3:
                        personagemEscolhido = new Squirtle();
                        break;
                    case 4:
                        personagemEscolhido = new Bulbasaur();
                        break;
                }

                System.out.println("Você escolheu " + personagemEscolhido.getNome() + ". Tem certeza? (1) Sim (2) Não");
                int escolhaConfirmacao = leitor.nextInt();
                if (escolhaConfirmacao == 1) {
                    confirmacao = true;
                } else {
                    System.out.println("Escolha um personagem novamente:");
                    System.out.println("(1) Pikachu");
                    System.out.println("(2) Charmander");
                    System.out.println("(3) Squirtle");
                    System.out.println("(4) Bulbasaur");
                    escolhaPersonagem = leitor.nextInt();
                }
            }

            int pontos = batalha(personagemEscolhido);
            System.out.println(personagemEscolhido.getNome() + " chegou a " + personagemEscolhido.hp + " pontos de vida.");
            if (pontos > recorde) {
                recorde = pontos;
            }
            System.out.println("Fim de jogo. Deseja continuar? (1) Sim (2) Não");
            continua = leitor.nextInt();

            if (continua != 1) {
                exibirBannerAgradecimento();
            }
        }
    }

    // Classe para inimigos
    static class Inimigo {
        String nome;
        int hp;

        // Construtor da classe Inimigo
        public Inimigo(String nome, int hp) {
            this.nome = nome;
            this.hp = hp;
        }

        // Método para atacar o personagem do usuário
        public int atacar() {
            Random gerador = new Random();
            return gerador.nextInt(3) + 1;
        }
    }

    // Classe abstrata para personagens
    static abstract class Personagem {
        protected int hp;
        protected int contagemEspecial;
        boolean desistiu;
        private String nome;
        protected int hpAntes;
        private boolean usouRecuperacao = false;

        // Construtor da classe abstrata Personagem
        public Personagem(String nome) {
            hp = 150;
            contagemEspecial = 5;
            desistiu = false;
            this.nome = nome;
            hpAntes = hp;
        }

        // Método abstrato para atacar
        public int atacar() {
            System.out.println("Escolha sua ação:");
            System.out.println("(1) - Ataque Básico");
            System.out.println("(2) - Ataque Especial");
            System.out.println("(3) - Recuperar Vida");
            System.out.println("(4) - Desistir");
            int escolha = leitor.nextInt();

            switch (escolha) {
                case 1:
                    break;
                case 2:
                    if (contagemEspecial > 0) {
                        System.out.println("Você escolheu usar um ataque especial.");
                    } else {
                        System.out.println("Você não possui mais ataques especiais.");
                    }
                    break;
                case 3:
                    if (!usouRecuperacao) {
                        recuperarVida();
                        usouRecuperacao = true;
                    } else {
                        System.out.println("Você já usou a recuperação de vida nesta partida.");
                    }
                    break;
                case 4:
                    desistir();
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }

            return escolha;
        }

        // Método para recuperar vida do personagem
        public void recuperarVida() {
            if (!usouRecuperacao) {
                System.out.println("Você escolheu recuperar vida.");
                System.out.print("Digite um número entre 1 e 10 para tentar se recuperar: ");
                int valorEscolhido = leitor.nextInt();
                int recuperacao = new Random().nextInt(valorEscolhido * 5) + 1;
                if (recuperacao > 50) {
                    recuperacao = 50;
                }
                hpAntes = hp;
                hp += recuperacao;
                System.out.println("Você se curou em " + recuperacao + " pontos.");
                usouRecuperacao = true;
            } else {
                System.out.println("Você já usou a recuperação!");
            }
        }

        // Método para imprimir o HP do personagem
        public void imprimeHP(int hpUsuario, int hpComputador) {
            System.out.println("====================");
            System.out.println("- HP Usuário: " + hpUsuario);
            System.out.println("- HP Inimigo: " + hpComputador);
            System.out.println("* Contagem de Especiais: " + contagemEspecial);
            System.out.println("====================");
        }

        // Método para desistir da luta
        public void desistir() {
            desistiu = true;
            System.out.println("Você desistiu da luta e está fora do Campeonato!");
            System.exit(0);
        }

        // Método para retornar o nome do personagem
        public String getNome() {
            return nome;
        }
    }

    // Classes para personagens específicos que herdam da classe abstrata Personagem
    static class Pikachu extends Personagem {
        public Pikachu() {
            super("Pikachu");
            hp = 150;
            contagemEspecial = 5;
            desistiu = false;
        }
    }

    static class Charmander extends Personagem {
        public Charmander() {
            super("Charmander");
            hp = 150;
            contagemEspecial = 5;
            desistiu = false;
        }
    }

    static class Bulbasaur extends Personagem {
        public Bulbasaur() {
            super("Bulbasaur");
            hp = 150;
            contagemEspecial = 5;
            desistiu = false;
        }
    }

    static class Squirtle extends Personagem {
        public Squirtle() {
            super("Squirtle");
            hp = 150;
            contagemEspecial = 5;
            desistiu = false;
        }
    }
}
