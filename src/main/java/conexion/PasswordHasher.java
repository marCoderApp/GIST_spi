package conexion;

//JAVA SECURITY IMPORTS
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.util.Base64;

public class PasswordHasher {

    private static final SecureRandom RNG = new SecureRandom();
    private static final int ITERATIONS = 210_000;
    private static final int SALT_BYTES = 16;
    private static final int KEY_BITS = 256;

    private PasswordHasher() {}

    public static String hash(char[] password) {
        byte[] salt = new byte[SALT_BYTES];
        RNG.nextBytes(salt);

        byte[] dk = pbkdf2(password, salt, ITERATIONS, KEY_BITS);

        // Limpieza básica del password en memoria
        for (int i = 0; i < password.length; i++) password[i] = '\0';

        return "pbkdf2$" + ITERATIONS + "$" +
                Base64.getEncoder().encodeToString(salt) + "$" +
                Base64.getEncoder().encodeToString(dk);
    }

    public static boolean verify(char[] password, String stored) {
        String[] parts = stored.split("\\$");
        if (parts.length != 4 || !"pbkdf2".equals(parts[0])) return false;

        int iter = Integer.parseInt(parts[1]);
        byte[] salt = Base64.getDecoder().decode(parts[2]);
        byte[] expected = Base64.getDecoder().decode(parts[3]);

        byte[] actual = pbkdf2(password, salt, iter, expected.length * 8);

        for (int i = 0; i < password.length; i++) password[i] = '\0';

        return MessageDigest.isEqual(expected, actual);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int keyBits) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyBits);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return skf.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new IllegalStateException("Error creando/verificando hash PBKDF2", e);
        }
    }
}

