

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class Teatro {
    private Vetor<Espetaculo> espetaculos;
    private Vetor<Reserva> reservas;
    private Scanner entrada;
    private boolean dadosCarregados;

    public Teatro() {
        espetaculos = new Vetor<Espetaculo>(20);
        reservas = new Vetor<Reserva>(400);
        entrada = new Scanner(System.in);
        dadosCarregados = false;
    }

    public static void main(String[] args) {
        Teatro teatro = new Teatro();
        teatro.executar();
    }

    public void executar() {
        int opcao;

        do {
            exibirMenu();
            opcao = lerInteiro("Opcao: ");

            if (opcao == 1) {
                carregarEspetaculos();
            } else if (opcao == 2) {
                exibirEspetaculos();
            } else if (opcao == 3) {
                fazerReserva();
            } else if (opcao != 7) {
                System.out.println("Opcao invalida.");
            }
        } while (opcao != 7);

        System.out.println("Encerrando o programa.");
    }

    public void exibirMenu() {
        System.out.println("\n1. Carregar espetaculos");
        System.out.println("2. Exibir espetaculos");
        System.out.println("3. Fazer reserva");
        System.out.println("7. Sair");
    }

    public void carregarEspetaculos() {
        if (dadosCarregados) {
            System.out.println("Os espetaculos ja foram carregados.");
            return;
        }

        FileReader data;

        try {
            data = new FileReader("espetaculos.txt");
            BufferedReader linha = new BufferedReader(data);
            String aux = linha.readLine();

            while (aux != null) {
                String[] partes = aux.split(";");

                if (partes.length == 5) {
                    int codigo = Integer.parseInt(partes[0]);
                    String nome = partes[1];
                    String dataEspetaculo = partes[2];
                    String horario = partes[3];
                    double preco = Double.parseDouble(partes[4]);
                    Espetaculo espetaculo = new Espetaculo(codigo, nome, dataEspetaculo, horario, preco);
                    espetaculos.add(espetaculos.size(), espetaculo);
                }

                aux = linha.readLine();
            }

            data.close();
            dadosCarregados = true;
            System.out.println("Espetaculos carregados com sucesso.");
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de dados nao encontrado.");
        } catch (IOException e) {
            System.out.println("Erro na leitura do arquivo.");
        } catch (Exception e) {
            System.out.println("Erro ao carregar os espetaculos.");
        }
    }

    public void exibirEspetaculos() {
        if (!verificarCarga()) {
            return;
        }

        System.out.println("\nEspetaculos disponiveis:");

        for (int indice = 0; indice < espetaculos.size(); indice++) {
            try {
                Espetaculo espetaculo = espetaculos.get(indice);
                System.out.printf(Locale.US, "%d - %s - %s - %s - R$ %.2f%n", espetaculo.getCodigo(), espetaculo.getNome(), espetaculo.getData(), espetaculo.getHorario(), espetaculo.getPreco());
            } catch (Exception e) {
                System.out.println("Erro ao exibir os espetaculos.");
                return;
            }
        }
    }

    public void fazerReserva() {
        if (!verificarCarga()) {
            return;
        }

        int codigo = lerInteiro("Codigo do espetaculo: ");
        Espetaculo espetaculo = buscarEspetaculo(codigo);

        if (espetaculo == null) {
            System.out.println("Espetaculo nao encontrado.");
            return;
        }

        if (reservas.isFull()) {
         System.out.println("Limite de reservas atingido.");
             return;
        }

        System.out.println("\n" + espetaculo.getCodigo() + " - " + espetaculo.getNome() + " - " + espetaculo.getData() + " - " + espetaculo.getHorario());
        String cpf = lerTexto("CPF: ");
        String nome = lerTexto("Nome: ");
        int quantidade = lerQuantidade();

        if (espetaculo.quantidadeAssentosLivres() < quantidade) {
            System.out.println("Nao ha assentos livres suficientes para esta reserva.");
            return;
        }

        System.out.println("\nMapa de assentos:");
        espetaculo.exibirMapa();
        String[] assentosReservados = new String[quantidade];

        for (int indice = 0; indice < quantidade; indice++) {
            assentosReservados[indice] = selecionarAssento(espetaculo, indice + 1);
        }

        try {
            Reserva reserva = new Reserva(cpf, nome, espetaculo, quantidade, assentosReservados);
            reservas.add(reservas.size(), reserva);
            exibirResumoReserva(reserva);
        } catch (Exception e) {
            System.out.println("Erro ao registrar a reserva.");
        }
    }

    private boolean verificarCarga() {
        if (!dadosCarregados) {
            System.out.println("Carregue os espetaculos antes de usar esta opcao.");
            return false;
        }

        return true;
    }

    private Espetaculo buscarEspetaculo(int codigo) {
        for (int indice = 0; indice < espetaculos.size(); indice++) {
            try {
                Espetaculo espetaculo = espetaculos.get(indice);

                if (espetaculo.getCodigo() == codigo) {
                    return espetaculo;
                }
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }

    private String selecionarAssento(Espetaculo espetaculo, int numeroAssento) {
        while (true) {
            String assento = lerTexto("Assento " + numeroAssento + ": ").toUpperCase();

            if (assento.length() != 2) {
                System.out.println("Assento invalido. Use o formato A1 ate E8.");
            } else {
                int linha = assento.charAt(0) - 'A';
                int coluna = assento.charAt(1) - '1';

                if (!espetaculo.posicaoValida(linha, coluna)) {
                    System.out.println("Assento inexistente.");
                } else if (!espetaculo.estaLivre(linha, coluna)) {
                    System.out.println("Assento ocupado.");
                } else {
                    espetaculo.ocuparAssento(linha, coluna);
                    return assento;
                }
            }
        }
    }

    private void exibirResumoReserva(Reserva reserva) {
        System.out.println("\nReserva realizada!");
        System.out.println("Espetaculo: " + reserva.getEspetaculo().getNome());
        System.out.print("Assentos: ");

        for (int indice = 0; indice < reserva.getQtdeAssentos(); indice++) {
            System.out.print(reserva.getAssentos()[indice] + " ");
        }

        System.out.println();
        System.out.println("Quantidade: " + reserva.getQtdeAssentos());
        System.out.printf(Locale.US, "Valor total: R$ %.2f%n", reserva.getEspetaculo().getPreco() * reserva.getQtdeAssentos());
    }

    private int lerInteiro(String mensagem) {
        while (true) {
            String texto = lerTexto(mensagem);

            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero inteiro valido.");
            }
        }
    }

    private int lerQuantidade() {
        while (true) {
            int quantidade = lerInteiro("Quantidade de ingressos (1 a 4): ");

            if (quantidade >= 1 && quantidade <= 4) {
                return quantidade;
            }

            System.out.println("A quantidade deve estar entre 1 e 4.");
        }
    }

    private String lerTexto(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = entrada.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println("Digite uma informacao valida.");
        }
    }
}
