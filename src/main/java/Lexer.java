import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Lexer {

    private char[] charArray;
    //private Token newToken;
    private Token tok;
    private int index = 0;

    // Method to read in a .txt file and output its contents as a char array.
    public char[] readFile(String path, Charset encoding) {

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

    public char[] readString(String inputString) {

        charArray = inputString.toCharArray();
        return charArray;
    }


    // point to the next character in a string
    // read in each character and identify punctuation
    // read in each character and use special characters to identify types


    public Token getToken() {

        String lexeme;
        int type = 0;


        // Ignore whitespace characters.
        while (Character.isWhitespace(charArray[index])) {

            index++;
        }

        // Tokenize keywords:

        if (Character.toString(charArray[index]).matches("[A-Z]")) {

            lexeme = keywordSearch();
            //type =

        }

        // Tokenize numbers:

        // Tokenize punctuation tokens:

        else if (charArray[index] == '&') {

            index++;
            return new Token(Sym.T_AMPERSAND, "&");
        }


        else if (charArray[index] == '^') {

            index++;
            return new Token(Sym.T_ARROW, "^");
        }

        else if (charArray[index] == ':') {

            lexeme = twoCharToken(':','=');
            if (lexeme.equals(":")) {
                return new Token(Sym.T_COLON, ":");
            }

            else if (lexeme.equals(":=")) {
                return new Token(Sym.T_ASSIGN, ":=");
            }

        }

        else if (charArray[index] == '|') {

            index++;
            return new Token(Sym.T_BAR,"|");
        }

        else if (charArray[index] == ',') {

            index++;
            return new Token(Sym.T_COMMA,",");
        }

        else if (charArray[index] == '.') {

            lexeme = twoCharToken('.','.');
            if (lexeme.equals(".")) {
                return new Token(Sym.T_DOT, ".");
            }

            else if (lexeme.equals("..")) {
                return new Token(Sym.T_DOTDOT, "..");
            }

        }

        else if (charArray[index] == '=') {

            index++;
            return new Token(Sym.T_EQU,"=");
        }

        else if (charArray[index] == '>') {

            lexeme = twoCharToken('>','=');
            if (lexeme.equals(">")) {
                return new Token(Sym.T_GT, ">");
            }

            else if (lexeme.equals(">=")) {
                return new Token(Sym.T_GTE, ">=");
            }

        }

        else if (charArray[index] == '{') {

            index++;
            return new Token(Sym.T_LBRACE,"{");
        }

        else if (charArray[index] == '[') {

            index++;
            return new Token(Sym.T_LBRACKET,"[");
        }

        else if (charArray[index] == '(') {

            index++;
            return new Token(Sym.T_LPAREN,"(");
        }

        else if (charArray[index] == '<') {

            lexeme = twoCharToken('<','=');
            if (lexeme.equals("<")) {
                return new Token(Sym.T_LT, "<");
            }

            else if (lexeme.equals("<=")) {
                return new Token(Sym.T_LTE, "<=");
            }

        }

        else if (charArray[index] == '-') {

            index++;
            return new Token(Sym.T_MINUS,"-");
        }

        else if (charArray[index] == '#') {

            index++;
            return new Token(Sym.T_NEQ,"#");
        }

        else if (charArray[index] == '+') {

            index++;
            return new Token(Sym.T_PLUS,"+");
        }

        else if (charArray[index] == '}') {

            index++;
            return new Token(Sym.T_RBRACE,"}");
        }

        else if (charArray[index] == ']') {

            index++;
            return new Token(Sym.T_RBRACKET,"]");
        }

        else if (charArray[index] == ')') {

            index++;
            return new Token(Sym.T_RPAREN,")");
        }

        else if (charArray[index] == ';') {

            index++;
            return new Token(Sym.T_SEMI,";");
        }

        else if (charArray[index] == '~') {

            index++;
            return new Token(Sym.T_TILDE,"~");
        }

        else if (charArray[index] == '/') {

            index++;
            return new Token(Sym.T_SLASH,"/");
        }

        else if (charArray[index] == '*') {

            index++;
            return new Token(Sym.T_STAR,"*");
        }




        // else if number check for decimal after number
        // error state
        return new Token(Sym.error, "error");
    }


    private String keywordSearch() {
        int stringLength = index;
        String lexemeStr = "";


        // Check if we have reached the end of charArray.
        // If present, add the next capital letter to lexemeStr.
        while (stringLength + 1 < charArray.length) {

            if (Character.toString(charArray[stringLength]).matches("[A-Z]")) {
                lexemeStr += charArray[stringLength];
                stringLength++;
            }

            if (Character.isWhitespace(charArray[stringLength + 1])) {
                index += stringLength;
                return lexemeStr;
            }
        }

        index += stringLength;
        return lexemeStr;
    }

    // Hash map to match strings to strings and token types.


    private String twoCharToken(char firstChar, char secondChar) {

        String str = "";
        while (index + 1 < charArray.length) {
            if (charArray[index + 1] == secondChar) {

                str += firstChar;
                str += secondChar;
                index += 2;     // Must increment past current char and next char.
                return str;
            }
        }

        index++;
        str += firstChar;
        return str;
    }


}
