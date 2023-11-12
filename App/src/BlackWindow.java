import java.util.Random;
import java.util.Scanner;

public class BlackWindow {
   
    public static int batalha(Personagem personagem) {
        int i = 1;
        int recorde = 0;
        Random rand = new Random();
        Inimigo[] inimigos = {new Inimigo("Inimigo 1", 200), new Inimigo("Inimigo 2", 150), new Inimigo("Inimigo 3", 100)};

        Scanner leitor = new Scanner(System.in);

        while (personagem.hp > 0 && existemInimigosVivos(inimigos)) {
            Inimigo inimigo = inimigos[rand.nextInt(3)]; // Escolhe um inimigo aleatoriamente
            int ataquesEspeciaisRestantes = personagem.contagemEspecial;

            System.out.println("====================");
            System.out.println("RODADA " + i);
            System.out.println("====================\n");

            while (personagem.hp > 0 && inimigo.hp > 0) {
                personagem.imprimeHP(personagem.hp, inimigo.hp);
                int escolha = personagem.atacar();

                switch (escolha) {
                    case 1:
                        System.out.println(personagem.getNome() + " aplicou um ataque básico.");
                        inimigo.hp -= 7;
                        break;
                    case 2:
                        if (ataquesEspeciaisRestantes > 0) {
                            System.out.println(personagem.getNome() + " aplicou um ataque especial.");
                            inimigo.hp -= 20;
                            ataquesEspeciaisRestantes--;
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

                if (inimigo.hp > 0) {
                    int escolhaInimigo = inimigo.atacar();
                    switch (escolhaInimigo) {
                        case 1:
                            System.out.println(inimigo.nome + " aplicou um soco.");
                            personagem.hp -= 2 + (int) (i / 10);
                            break;
                        case 2:
                            System.out.println(inimigo.nome + " aplicou um chute.");
                            personagem.hp -= 3 + (int) (i / 10);
                            break;
                        case 3:
                            System.out.println(inimigo.nome + " aplicou um ataque especial.");
                            personagem.hp -= 4 + (int) (i / 20);
                            break;
                    }
                } else {
                    System.out.println(inimigo.nome + " derrotado");
                }
            }

            if (personagem.hp > 0) {
                personagem.hp += 5;
                if (personagem.hp > 150) {
                    personagem.hp = 150;
                }
                if (i % 10 == 0) {
                    ataquesEspeciaisRestantes++;
                    if (ataquesEspeciaisRestantes > 5) {
                        ataquesEspeciaisRestantes = 5;
                    }
                }
            }

            System.out.println("====================");
            System.out.println("Fim da rodada " + i);

            if (personagem.hp <= 0) {
                System.out.println("Você foi derrotado. Fim de jogo.");
                break; // Encerre a batalha
            }

            i++;

            if (personagem.hp > recorde) {
                recorde = personagem.hp;
            }

            System.out.println("RECORDE ATUAL = " + recorde);
        }
        return 0;
    }

    // Método auxiliar para verificar se há inimigos vivos
    private static boolean existemInimigosVivos(Inimigo[] inimigos) {
        for (Inimigo inimigo : inimigos) {
            if (inimigo.hp > 0) {
                return true; // Pelo menos um inimigo ainda está vivo
            }
        }
        return false; // Todos os inimigos foram derrotados
    }

    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        int continua = 1;
        int recorde = 0;

        while (continua == 1) {
            System.out.println("Escolha um personagem:");
            System.out.println("(1) Pikachu");
            System.out.println("(2) Charmander");
            System.out.println("(3) Squirtle");
            System.out.println("(4) Bulbasaur");
            int escolhaPersonagem = leitor.nextInt();

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
                    default:
                        System.out.println("Personagem inválido. Escolha um número entre 1 e 4.");
                        break;
                }

                // Pergunta ao usuário se ele tem certeza da escolha
                System.out.println("Você escolheu " + personagemEscolhido.getNome() + ". Tem certeza? (1) Sim (2) Não");
                int escolhaConfirmacao = leitor.nextInt();
                if (escolhaConfirmacao == 1) {
                    confirmacao = true;
                } else {
                    // Se o usuário não tem certeza, permita que ele escolha novamente
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
            System.out.println("RECORDE ATUAL = " + recorde);
            System.out.println("Fim de jogo. Deseja continuar? (1) Sim (2) Não");
            continua = leitor.nextInt();
        }

        leitor.close();
    }
}

class Bulbasaur extends Personagem {

    public Bulbasaur() {
        super("Bulbasaur");
        hp = 150;
        contagemEspecial = 5;
        desistiu = false;
    }
}

class Charmander extends Personagem {

    public Charmander() {
        super("Charmander");
        hp = 150;
        contagemEspecial = 5;
        desistiu = false;
    }

}

class Inimigo {
    String nome;
    int hp;

    public Inimigo(String nome, int hp) {
        this.nome = nome;
        this.hp = hp;
    }

    public int atacar() {
        Random gerador = new Random();
        return gerador.nextInt(3) + 1;
    }
}

class Personagem {
    protected int hp;
    protected int contagemEspecial;
    boolean desistiu;
    private String nome;
    protected int hpAntes;
    private boolean usouRecuperacao = false;

    public Personagem(String nome) {
        hp = 150;
        contagemEspecial = 5;
        desistiu = false;
        this.nome = nome;
        hpAntes = hp;
    }

    public int atacar() {
        Scanner leitor = new Scanner(System.in);
        System.out.println("Escolha sua ação:");
        System.out.println("(1) - Ataque Básico");
        System.out.println("(2) - Ataque Especial");
        System.out.println("(3) - Recuperar Vida");
        System.out.println("(4) - Desistir");
        int escolha = leitor.nextInt();

        switch (escolha) {
            case 1:
                // Implemente o ataque básico aqui
                break;
            case 2:
                if (contagemEspecial > 0) {
                    System.out.println("Você escolheu usar um ataque especial.");
                    contagemEspecial--; // Reduza a contagem de ataques especiais em 1.
                    // Implemente o ataque especial aqui
                } else {
                    System.out.println("Você não possui mais ataques especiais.");
                }
                break;
            case 3:
                if (!usouRecuperacao) {
                    recuperarVida();
                    usouRecuperacao = true;
                } 
                else {
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

    public void recuperarVida() {
        Scanner leitor = new Scanner(System.in);
    
        if (!usouRecuperacao) {
            System.out.println("Você escolheu recuperar vida.");
    
            // O jogador digita um número de 0 a 10.
            System.out.print("Digite um número entre 1 e 10 para tentar se recuperar: ");
            int valorEscolhido = leitor.nextInt();
    
            // Gere um valor aleatório entre 0 e o número escolhido.
            int recuperacao = new Random().nextInt(valorEscolhido * 5) + 1; // Recuperação entre 1 e 50.
    
            // Garanta que a recuperação não ultrapasse 50 pontos.
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

    public void imprimeHP(int hpUsuario, int hpComputador) {
        System.out.println("====================");
        System.out.println("- HP Usuario: " + hpUsuario);
        System.out.println("- HP Computador: " + hpComputador);
        System.out.println("* Contagem Especiais: " + contagemEspecial);
        System.out.println("====================");
    }

    public void desistir() {
        desistiu = true;
        System.out.println("Você desistiu da luta e está fora do Campeonato!");
        System.exit(0);
    }

    public String getNome() {
        return nome;
    }
}

class Pikachu extends Personagem {

    public Pikachu() {
        super("Pikachu");
        hp = 150;
        contagemEspecial = 5;
        desistiu = false;
    }
}

class Squirtle extends Personagem {

    public Squirtle() {
        super("Squirtle");
        hp = 150;
        contagemEspecial = 5;
        desistiu = false;
    }
}
