import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Lexer {

    public String[] typeOrLexeme;
    public Token[] tokenArray;
    private Token newToken;
    private Token tok;


    // Method to read in a .txt file and output its contents as a char array.
    public char[] readFile(String path, Charset encoding) {

        char[] charArray;
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            String inputString = new String(encoded, encoding);
            charArray = inputString.toCharArray();
            return charArray;
        }catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
            return null;
        }

    }


    // point to the next character in a string
    // read in each character and identify punctuation
    // read in each character and use special characters to identify types

    public void tokenizeChars(char[] charString) {

        int j = 0;
        int type;
        tokenArray = new Token[charString.length];
        String lexeme = "";
        for(int i = 0; i < charString.length; i++) {

            if (Character.isWhitespace(charString[i])) {
                //ignore the character.
            }

            else if (Character.toString(charString[i]).matches("[A-Z]")) {

                lexeme += charString[i];

                if (Character.isWhitespace(charString[i + 1])) {
                    //type = Sym.T_MODULE;
                    tokenArray[j] = setToken(Sym.T_MODULE, lexeme);
                    j++;
                    //addNewToken(j, type, lexeme);
                }

            }


            else if (charString[i] == ';') {
                tokenArray[j] = setToken(Sym.T_SEMI, ";");
                j++;
            }


            else if (charString[i] == '"') {

            }
        }
    }



    private String getTypeOrLexeme(int index) {
        return typeOrLexeme[index];
    }
    private int addNewToken(int arrayIndex, int tType, String lexeme) {

        newToken = setToken(tType, lexeme);
        tokenArray[arrayIndex] = newToken;
        return arrayIndex + 1;
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
