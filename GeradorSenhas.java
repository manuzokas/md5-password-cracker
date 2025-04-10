/**
 * Classe responsável por gerar todas as combinações possíveis de senhas
 * a partir de um conjunto de caracteres definido.
 */
public class GeradorSenhas {
    private String caracteres;

    /**
     * Construtor que inicializa o gerador com o conjunto de caracteres.
     * 
     * @param caracteres String contendo todos os caracteres que podem ser usados na
     *                   geração de senhas
     */
    public GeradorSenhas(String caracteres) {
        this.caracteres = caracteres;
    }

    /**
     * Gera todas as combinações possíveis de senhas do tamanho especificado
     * e as submete ao quebrador de senhas MD5 para teste.
     * 
     * Implementa um sistema de incremento de índices similar a um odômetro,
     * onde cada posição representa um caractere do conjunto fornecido.
     * 
     * @param tamanhoSenha Tamanho das senhas a serem geradas
     * @param quebrador    Instância do quebrador de senhas MD5 para testar cada
     *                     combinação
     */
    public void gerarCombinacoes(int tamanhoSenha, QuebradorMD5 quebrador) {
        char[] senhaAtual = new char[tamanhoSenha];
        int[] indices = new int[tamanhoSenha];
        long contador = 0;

        while (true) {
            // Construir a senha atual baseada nos índices
            for (int i = 0; i < tamanhoSenha; i++) {
                senhaAtual[i] = caracteres.charAt(indices[i]);
            }

            quebrador.testarSenha(new String(senhaAtual));
            contador++;

            // Exibir progresso a cada 1 milhão de combinações
            if (contador % 1_000_000 == 0) {
                System.out.println("Combinações testadas: " + contador);
            }

            // Lógica de incremento dos índices
            int posicao = tamanhoSenha - 1;
            while (posicao >= 0 && indices[posicao] == caracteres.length() - 1) {
                indices[posicao] = 0;
                posicao--;
            }

            if (posicao < 0) {
                break;
            }

            indices[posicao]++;
        }

        System.out.println("Total de combinações testadas: " + contador);
    }
}