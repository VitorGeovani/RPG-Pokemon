import java.util.Scanner;
import java.util.Random;

public class RPGFightGame {

    public static int batalha(Personagem personagem) {
        int i = 1;
        int recorde = 0;
        Random rand = new Random();
        Inimigo[] inimigos = {new Inimigo("Inimigo 1", 200), new Inimigo("Inimigo 2", 150), new Inimigo("Inimigo 3", 100)};

        Scanner leitor = new Scanner(System.in);

        while (personagem.hp > 0) {
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
                        } else {
                            System.out.println("Você não possui mais ataques especiais.");
                        }
                        break;
                    case 3:
                        System.out.println("Você escolheu recuperar vida.");
                        int valorCura = rand.nextInt(11); // Valor de cura entre 0 e 10
                        int palpite;
                        System.out.print("Tente adivinhar o valor de cura (0-10): ");
                        try {
                            palpite = leitor.nextInt();
                        } catch (java.util.InputMismatchException e) {
                            palpite = -1;
                        }
                        if (palpite == valorCura) {
                            personagem.hp += 15; // Cura completa se o palpite for correto
                        } else if (palpite >= 0 && palpite <= 10) {
                            double porcentagemCura = 0.1 * palpite; // Porcentagem de cura com base no palpite
                            int cura = (int) (personagem.hp * porcentagemCura);
                            personagem.hp += cura;
                        }
                        System.out.println("Você se curou em " + (personagem.hp - personagem.hpAntes) + " pontos.");
                        break;
                    case 4:
                        personagem.desistir();
                        System.exit(0);
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
            // System.out.println(personagem.getNome() + " chegou a " + personagem.hp + " pontos de vida.");

            if (personagem.hp <= 0) {
                System.out.println("Você foi derrotado. Fim de jogo.");
            }
            i++;

            if (personagem.hp > recorde) {
                recorde = personagem.hp;
            }

            // System.out.println("RECORDE ATUAL = " + recorde);
        }
        return 0;
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
            // System.out.println("RECORDE ATUAL = " + recorde);
            System.out.println("Fim de jogo. Deseja continuar? (1) Sim (2) Não");
            continua = leitor.nextInt();
        }

        leitor.close();
    }
}
