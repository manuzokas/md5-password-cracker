import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe responsável por quebrar hashes MD5 através de força bruta,
 * comparando combinações de senhas com uma lista de hashes alvo.
 */
public class QuebradorMD5 {
    private final List<String> hashesAlvo;
    private final Map<String, String> senhasEncontradas;
    private final Map<String, Long> tempoPorSenha;
    private long tempoInicio;
    private boolean todasEncontradas;

    /**
     * Construtor que inicializa o quebrador com a lista de hashes alvo.
     * 
     * @param hashesAlvo Lista de hashes MD5 que devem ser quebrados
     */
    public QuebradorMD5(List<String> hashesAlvo) {
        this.hashesAlvo = hashesAlvo;
        this.senhasEncontradas = new HashMap<>();
        this.tempoPorSenha = new HashMap<>();
        this.todasEncontradas = false;

        // Inicializa estruturas para cada hash alvo
        for (String hash : hashesAlvo) {
            senhasEncontradas.put(hash, null);
            tempoPorSenha.put(hash, 0L);
        }
    }

    /**
     * Inicia o processo de busca, registrando o tempo inicial.
     */
    public void iniciarBusca() {
        tempoInicio = System.currentTimeMillis();
        todasEncontradas = false;
        System.out.println("Iniciando a busca pelas senhas...");
    }

    /**
     * Calcula o hash MD5 de uma string.
     * 
     * @param texto String a ser hasheada
     * @return Hash MD5 em formato hexadecimal
     * @throws RuntimeException Se o algoritmo MD5 não estiver disponível
     */
    private String calcularMD5(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(texto.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

    /**
     * Testa uma senha contra os hashes alvo.
     * 
     * @param senha Senha a ser testada
     */
    public void testarSenha(String senha) {
        if (todasEncontradas) {
            return;
        }

        String hashSenha = calcularMD5(senha);

        if (hashesAlvo.contains(hashSenha)) {
            if (senhasEncontradas.get(hashSenha) == null) {
                long tempoGasto = System.currentTimeMillis() - tempoInicio;
                senhasEncontradas.put(hashSenha, senha);
                tempoPorSenha.put(hashSenha, tempoGasto);

                System.out.println("✅ Senha encontrada: " + senha + " (Hash: " + hashSenha + ")");
                System.out.println("⏳ Tempo gasto: " + (tempoGasto / 1000.0) + " segundos");

                verificarCompleto();
            }
        }
    }

    /**
     * Verifica se todos os hashes alvo foram quebrados.
     */
    private void verificarCompleto() {
        for (String hash : hashesAlvo) {
            if (senhasEncontradas.get(hash) == null) {
                return;
            }
        }
        todasEncontradas = true;
        System.out.println("\n🔎 Todas as senhas foram encontradas! Finalizando busca...");
    }

    /**
     * Indica se a busca deve continuar.
     * 
     * @return true se ainda há hashes não quebrados, false caso contrário
     */
    public boolean deveContinuar() {
        return !todasEncontradas;
    }

    /**
     * Exibe os resultados finais da busca, incluindo senhas encontradas
     * e tempos gastos para cada hash.
     */
    public void exibirResultados() {
        System.out.println("\n--- RESULTADOS FINAIS ---");
        for (String hash : hashesAlvo) {
            String senha = senhasEncontradas.get(hash);
            long tempo = tempoPorSenha.get(hash);

            if (senha != null) {
                System.out.printf("🔓 Hash: %s | Senha: %s | Tempo: %.3f segundos%n",
                        hash, senha, tempo / 1000.0);
            } else {
                System.out.printf("🔴 Hash: %s | Senha: NÃO ENCONTRADA | Tempo: ---%n", hash);
            }
        }
    }
}