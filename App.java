import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Aplicativo principal para demonstração do quebrador de senhas MD5.
 * Oferece dois modos de operação: demonstração rápida e modo completo.
 */
public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        exibirMenuOpcoes();
        int opcao = scanner.nextInt();
        scanner.close();

        processarOpcao(opcao);
    }

    /**
     * Exibe o menu de opções para o usuário.
     */
    private static void exibirMenuOpcoes() {
        System.out.println("Escolha o modo de execução:");
        System.out.println("1 - Modo rápido (3 caracteres, demonstração)");
        System.out.println("2 - Modo completo (5 caracteres, todas as senhas)");
        System.out.print("Digite sua opção (1 ou 2): ");
    }

    /**
     * Processa a opção selecionada pelo usuário.
     * 
     * @param opcao Opção selecionada (1 ou 2)
     */
    private static void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                executarModoDemonstracao();
                break;
            case 2:
                executarModoCompleto();
                break;
            default:
                System.out.println("Opção inválida. Execute novamente e escolha 1 ou 2.");
        }
    }

    /**
     * Executa o modo de demonstração com configurações simplificadas.
     */
    private static void executarModoDemonstracao() {
        String caracteresDemo = "0123456789abcdef";
        List<String> hashesDemo = Arrays.asList(
                "202cb962ac59075b964b07152d234b70" // hash MD5 de "123"
        );

        configurarEExecutarQuebrador(caracteresDemo, hashesDemo, 3, "demonstração (3 caracteres)");
    }

    /**
     * Executa o modo completo com todas as combinações possíveis.
     */
    private static void executarModoCompleto() {
        String caracteres = "0123456789#$%&*+-.*=abcdefghijlmnoqrstuvwxyzABCDEFGHIJLMNOPQRSTUVWXYZ";
        List<String> hashesAlvo = Arrays.asList(
                "a12a53e9c429a1561e96f2bf0d46517b",
                "f59537d4f10ef116e5eb15dba1451ed3",
                "818a12c6f3c6b8591e050b00495e7951",
                "82df659723a88d89ca7fdb652d813fa5",
                "4a8fd58e7832f13002b86bb2169ba16b");

        configurarEExecutarQuebrador(caracteres, hashesAlvo, 5, "completo (5 caracteres)");
    }

    /**
     * Configura e executa o quebrador de senhas com os parâmetros especificados.
     * 
     * @param caracteres    Conjunto de caracteres para geração de senhas
     * @param hashes        Lista de hashes MD5 a serem quebrados
     * @param tamanhoSenha  Tamanho das senhas a serem geradas
     * @param modoDescricao Descrição do modo de operação
     */
    private static void configurarEExecutarQuebrador(String caracteres, List<String> hashes,
            int tamanhoSenha, String modoDescricao) {
        System.out.println("\nIniciando modo " + modoDescricao + "...");

        QuebradorMD5 quebrador = new QuebradorMD5(hashes);
        GeradorSenhas gerador = new GeradorSenhas(caracteres);

        quebrador.iniciarBusca();
        gerador.gerarCombinacoes(tamanhoSenha, quebrador);
        quebrador.exibirResultados();
    }
}