public class ListaLinear {
    private Espetaculo[] vetor;
    private int quantidade;

    public ListaLinear() {
        vetor = new Espetaculo[20];
        quantidade = 0;
    }

    public void inserir(Espetaculo espetaculo) {
        if (quantidade < 20) {
            vetor[quantidade] = espetaculo;
            quantidade++;
        }
    }

    public Espetaculo obter(int posicao) {
        return vetor[posicao];
    }

    public int tamanho() {
        return quantidade;
    }
}
