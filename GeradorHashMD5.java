import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GeradorHashMD5 {
    public static void main(String[] args) {
        String senha = "123";
        System.out.println("Hash MD5 de '" + senha + "': " + calcularMD5(senha));
    }

    private static String calcularMD5(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(texto.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }
}

