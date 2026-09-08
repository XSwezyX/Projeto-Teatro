public class Reserva {
    private String cpf;
    private String nome;
    private Espetaculo espetaculo;
    private int qtdIngressos;
    private String[] assentos;

    public Reserva(String cpf, String nome, Espetaculo espetaculo, int qtdIngressos, String[] assentos) {
        this.cpf = cpf;
        this.nome = nome;
        this.espetaculo = espetaculo;
        this.qtdIngressos = qtdIngressos;
        this.assentos = assentos;
    }
}
