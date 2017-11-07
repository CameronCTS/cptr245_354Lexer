import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Lexer {

    public Token tok;

    public static String readFile(String path, Charset encoding) {

        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, encoding);
        }catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
            return "Error. Caught IOException";
        }
    }

    public void SetToken(int type, String strLexeme) {

        tok = new Token(type, strLexeme);

    }

    public Token GetToken() {

        tok.GetType();
        tok.GetLexeme();

        return tok;
    }

}
