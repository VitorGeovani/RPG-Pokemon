import java.util.Scanner;
import java.util.Random;

public class RPGFightGame {

    public static int batalha(Personagem personagem) {
        int hpComputador;
        int escolhaAtaque;
        int i = 1;

        while (personagem.hp > 0) {
            hpComputador = 10 + i;

            System.out.println("====================");
            System.out.println("INÍCIO " + i);
            System.out.println("====================\n");

            while (personagem.hp > 0 && hpComputador > 0) {
                personagem.imprimeHP(personagem.hp, hpComputador);
                escolhaAtaque = personagem.atacar();
                switch (escolhaAtaque) {
                    case 1:
                        System.out.println(personagem.getNome() + " aplicou um ataque básico.");
                        hpComputador -= 7;
                        break;
                    case 2:
                        System.out.println(personagem.getNome() + " aplicou um ataque especial.");
                        hpComputador -= 20;
                        personagem.contagemEspecial--;
                        break;
                    case 3:
                        personagem.desistir();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opção inválida");
                        break;
                }

                if (hpComputador > 0) {
                    escolhaAtaque = personagem.ataqueComputador();
                    switch (escolhaAtaque) {
                        case 1:
                            System.out.println("Computador aplicou um soco.");
                            personagem.hp -= 2 + (int) (i / 10);
                            break;
                        case 2:
                            System.out.println("Computador aplicou um chute.");
                            personagem.hp -= 3 + (int) (i / 10);
                            personagem.contagemEspecial--;
                            break;
                        case 3:
                            System.out.println("Computador aplicou um ataque especial.");
                            personagem.hp -= 4 + (int) (i / 20);
                            break;
                    }
                } else {
                    System.out.println("Inimigo derrotado");
                }
            }

            if (personagem.hp > 0) {
                personagem.hp += 5;
                if (personagem.hp > 150) {
                    personagem.hp = 150;
                }
                if (i % 10 == 0) {
                    personagem.contagemEspecial++;
                    if (personagem.contagemEspecial > 5) {
                        personagem.contagemEspecial = 5;
                    }
                }
            }
            i++;
        }

        return i;
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
            System.out.println("(4) Bulbassaur");
            int escolhaPersonagem = leitor.nextInt();

            Personagem personagemEscolhido = null;

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
                    personagemEscolhido = new Bulbassaur();
                    break;
                default:
                    System.out.println("Personagem inválido. Escolha novamente.");
                    continue;
            }

            int pontos = batalha(personagemEscolhido);
            System.out.println(personagemEscolhido.getNome() + " chegou a " + pontos + " pontos.");
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
