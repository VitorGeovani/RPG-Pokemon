import java.util.Random;
import java.util.Scanner;

public class Personagem {
    int hp;
    int contagemEspecial;
    boolean desistiu;
    private String nome;

    public Personagem(String nome) {
        hp = 150;
        contagemEspecial = 5;
        desistiu = false;
        this.nome = nome;
    }

    public int atacar() {
        Scanner leitor = new Scanner(System.in);
        System.out.println("Escolha seu ataque");
        System.out.println("(1) - Ataque Básico");
        System.out.println("(2) - Ataque Especial");
        System.out.println("(3) - Desistir");
        return leitor.nextInt();
    }

    public int ataqueComputador() {
        Random gerador = new Random();
        return gerador.nextInt(3) + 1;
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
//                         