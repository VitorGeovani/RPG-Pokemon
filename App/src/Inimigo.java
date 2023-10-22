import java.util.Random;

public class Inimigo {
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
