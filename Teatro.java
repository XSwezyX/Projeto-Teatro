import java.io.*;
import java.util.*;

public class Teatro {
    private ListaLinear espetaculos;
    private ListaLinear reservas;

    public Teatro() {
        espetaculos = new ListaLinear();
        reservas = new ListaLinear();
    }

    public static void main(String[] args) throws IOException {
        Teatro teatro = new Teatro();
        Scanner ent = new Scanner(System.in);

        int opcao;

        do {
            System.out.println();
            System.out.println("===== TEATRO =====");
            System.out.println("1. Carregar espetáculos");
            System.out.println("2. Exibir espetáculos");
            System.out.println("3. Fazer reserva");
            System.out.println("4. Consultar mapa de assentos");
            System.out.println("5. Consultar reserva");
            System.out.println("6. Estatísticas");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = ent.nextInt();

            if (opcao == 1) {
                teatro.carregarEspetaculos();
            }

            if (opcao == 2) {
                if (teatro.espetaculos.tamanho() > 0) {
                    teatro.exibirEspetaculos();
                } else {
                    System.out.println("É necessário carregar os espetáculos primeiro.");
                }
            }

         

        } while (opcao != 7);

        System.out.println("Programa encerrado.");
        ent.close();
    }

   public void carregarEspetaculos() throws IOException {
    FileReader data;

    try {
      
        }

        data.close();
        System.out.println("Espetáculos carregados com sucesso!");

    } catch (FileNotFoundException e) {
        System.out.println("Arquivo de dados não encontrado!");
        System.exit(0);
    }
}

  
        }

