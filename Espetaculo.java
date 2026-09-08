public class Espetaculo {
    private int codigo;
    private String nome;
    private String data;
    private double preco;
    private char[][] assentos;

    public Espetaculo(int codigo, String nome, String data, double preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.data = data;
        this.preco = preco;

        assentos = new char[5][8];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                assentos[i][j] = 'L';
            }
        }
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getData() {
        return data;
    }
    public double getPreco() {
        return preco;
    }
}
