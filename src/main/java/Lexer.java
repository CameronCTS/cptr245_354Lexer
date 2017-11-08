import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Lexer {

    public String[] typeOrLexeme;
    public Token[] tokenArray;
    private Token newToken;
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
        typeOrLexeme = inputStr.split("\\s+|((?<=[&^|:,.=>{\\[(<\\-#+}\\]);~/*])|(?=[&^|:,.=>{\\[(<\\-#+}\\]);~/*]))");

        tokenArray = new Token[typeOrLexeme.length];
        int j = 0;

        for (int i = 0; i < typeOrLexeme.length; i++) {

            if (typeOrLexeme[i].equals("MODULE")) {

                newToken = setToken(Sym.T_MODULE, typeOrLexeme[i+1]);
                i++; //Skip the next array element because it was just set as the Lexeme.
                tokenArray[j] = newToken;
                j++;
            }

            else if (typeOrLexeme[i].equals(";")) {

                newToken = setToken(Sym.T_SEMI,typeOrLexeme[i]);
                tokenArray[j] = newToken;
                j++;
            }

            else if (typeOrLexeme[i].equals("&")) {

                newToken = setToken(Sym.T_AMPERSAND, typeOrLexeme[i]);
                tokenArray[j] = newToken;
                j++;
            }
        }


    }


    public Token setToken(int type, String strLexeme) {

        tok = new Token(type, strLexeme);
        return tok;
    }

    public Token getToken() {

        tok.GetType();
        tok.GetLexeme();

        return tok;
    }

}
