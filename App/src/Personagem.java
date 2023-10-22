import java.util.Scanner;

public class Personagem {
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
                // Implementando o ataque básico
                break;
            case 2:
                if (contagemEspecial > 0) {
                    System.out.println("Você escolheu usar um ataque especial.");
                    contagemEspecial--; // Reduzindo a contagem de ataques especiais em 1.
                    // Implementando o ataque especial
                } else {
                    System.out.println("Você não possui mais ataques especiais.");
                }
                break;
            case 3:
                if (!usouRecuperacao) {
                    System.out.println("Você escolheu recuperar vida.");
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

    public void recuperarVida() {
        Scanner leitor = new Scanner(System.in);
        System.out.println("Digite um valor entre 0 e 10 para tentar se recuperar:");
        int valorEscolhido = leitor.nextInt();
        
        int recuperacao = 0;
        if (valorEscolhido >= 0 && valorEscolhido <= 10) {
            recuperacao = valorEscolhido;
        }
        
        if (hp + recuperacao > 150) {
            recuperacao = 150 - hp;
        }

        hpAntes = hp;
        hp += recuperacao;
        
        System.out.println("Você se curou em " + recuperacao + " pontos.");
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
    }

    public String getNome() {
        return nome;
    }
}