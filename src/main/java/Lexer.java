import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Lexer {

    public String[] typeOrLexeme;
    private Token tok;

    public static String readFile(String path, Charset encoding) {

        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, encoding);
        }catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
            return "Error. Caught IOException";
        }
    }

    public void tokenize(String inputStr) {

        int type = 1; //Report error if not set later;

        typeOrLexeme = inputStr.split("\\s+|((?<=;|&)|(?=;|&))");

        if (typeOrLexeme[0].equals("MODULE")) {
            type = 20;
        }
        setToken(type, typeOrLexeme[1]);
    }


    public void setToken(int type, String strLexeme) {

        tok = new Token(type, strLexeme);

    }

    public Token getToken() {

        tok.GetType();
        tok.GetLexeme();

        return tok;
    }

}
