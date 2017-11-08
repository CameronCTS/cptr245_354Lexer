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

        typeOrLexeme = inputStr.split("\\s+|((?<=[&^|:,.=>{\\[(<\\-#+}\\]);~/*])|(?=[&^|:,.=>{\\[(<\\-#+}\\]);~/*]))");

        tokenArray = new Token[typeOrLexeme.length];
        int j = 0;
        String lexeme;

        for (int i = 0; i < typeOrLexeme.length; i++) {

            switch (getTypeOrLexeme(i)) {

                // Keywords
                case "ARRAY":
                    j = addNewToken(j, Sym.T_ARRAY, typeOrLexeme[i+1]);
                    i++; //Skip the next array element because it was just set as the Lexeme.
                    break;
                case "BEGIN":
                    j = addNewToken(j, Sym.T_BEGIN, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "BY":
                    j = addNewToken(j, Sym.T_BY, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "CASE":
                    j = addNewToken(j, Sym.T_CASE, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "CONST":
                    j = addNewToken(j, Sym.T_CONST, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "DIV":
                    j = addNewToken(j, Sym.T_DIV, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "DO":
                    j = addNewToken(j, Sym.T_DO, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "ELSE":
                    j = addNewToken(j, Sym.T_ELSE, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "ELSEIF":
                    j = addNewToken(j, Sym.T_ELSIF, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "END":
                    j = addNewToken(j, Sym.T_END, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "EXIT":
                    j = addNewToken(j, Sym.T_EXIT, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "FOR":
                    j = addNewToken(j, Sym.T_FOR, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "IF":
                    j = addNewToken(j, Sym.T_IF, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "IMPORT":
                    j = addNewToken(j, Sym.T_IMPORT, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "IN":
                    j = addNewToken(j, Sym.T_IN, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "IS":
                    j = addNewToken(j, Sym.T_IS, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "LOOP":
                    j = addNewToken(j, Sym.T_LOOP, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "MOD":
                    j = addNewToken(j, Sym.T_MOD, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "MODULE":
                    j = addNewToken(j, Sym.T_MODULE, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "NIL":
                    j = addNewToken(j, Sym.T_NIL, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "OF":
                    j = addNewToken(j, Sym.T_OF, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "OR":
                    j = addNewToken(j, Sym.T_OR, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "POINTER":
                    j = addNewToken(j, Sym.T_POINTER, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "PROCEDURE":
                    j = addNewToken(j, Sym.T_PROCEDURE, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "RECORD":
                    j = addNewToken(j, Sym.T_RECORD, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "REPEAT":
                    j = addNewToken(j, Sym.T_REPEAT, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "RETURN":
                    j = addNewToken(j, Sym.T_RETURN, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "THEN":
                    j = addNewToken(j, Sym.T_THEN, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "TO":
                    j = addNewToken(j, Sym.T_TO, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "TYPE":
                    j = addNewToken(j, Sym.T_TYPE, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "UNTIL":
                    j = addNewToken(j, Sym.T_UNTIL, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "VAR":
                    j = addNewToken(j, Sym.T_VAR, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "WHILE":
                    j = addNewToken(j, Sym.T_WHILE, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "WITH":
                    j = addNewToken(j, Sym.T_WITH, typeOrLexeme[i+1]);
                    i++;
                    break;

                // Predeclared identifiers (equivalent to keywords)
                case "BOOLEAN":
                    j = addNewToken(j, Sym.T_BOOLEAN, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "CHAR":
                    j = addNewToken(j, Sym.T_CHAR, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "FALSE":
                    j = addNewToken(j, Sym.T_FALSE, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "INTEGER":
                    j = addNewToken(j, Sym.T_INTEGER, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "NEW":
                    j = addNewToken(j, Sym.T_NEW, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "REAL":
                    j = addNewToken(j, Sym.T_REAL, typeOrLexeme[i+1]);
                    i++;
                    break;
                case "TRUE":
                    j = addNewToken(j, Sym.T_TRUE, typeOrLexeme[i+1]);
                    i++;
                    break;

                // Punctuation
                case "&":
                    j = addNewToken(j, Sym.T_AMPERSAND, typeOrLexeme[i]);
                    break;
                case "^":
                    j = addNewToken(j, Sym.T_ARROW, typeOrLexeme[i]);
                    break;
                case "|":
                    j = addNewToken(j, Sym.T_BAR, typeOrLexeme[i]);
                    break;
                case ":":
                    if (getTypeOrLexeme(i+1).equals("=")) {
                        lexeme = typeOrLexeme[i] + typeOrLexeme[i+1];
                        j = addNewToken(j, Sym.T_ASSIGN,lexeme);
                    }
                    else j = addNewToken(j, Sym.T_COLON, typeOrLexeme[i]);
                    break;
                case ",":
                    j = addNewToken(j, Sym.T_SEMI, typeOrLexeme[i]);
                    break;
                case ".":
                    if (getTypeOrLexeme(i+1).equals(".")) {
                        lexeme = typeOrLexeme[i] + typeOrLexeme[i+1];
                        j = addNewToken(j, Sym.T_DOTDOT,lexeme);
                    }
                    else j = addNewToken(j, Sym.T_DOT, typeOrLexeme[i]);
                    break;
                case "=":
                    j = addNewToken(j, Sym.T_EQU, typeOrLexeme[i]);
                    break;
                case ">":
                    if (getTypeOrLexeme(i+1).equals("=")) {
                        lexeme = typeOrLexeme[i] + typeOrLexeme[i+1];
                        j = addNewToken(j, Sym.T_GTE,lexeme);
                    }
                    else j = addNewToken(j, Sym.T_GT, typeOrLexeme[i]);
                    break;
                case "{":
                    j = addNewToken(j, Sym.T_LBRACE, typeOrLexeme[i]);
                    break;
                case "[":
                    j = addNewToken(j, Sym.T_LBRACKET, typeOrLexeme[i]);
                    break;
                case "(":
                    j = addNewToken(j, Sym.T_LPAREN, typeOrLexeme[i]);
                    break;
                case "<":
                    if (getTypeOrLexeme(i+1).equals("=")) {
                        lexeme = typeOrLexeme[i] + typeOrLexeme[i+1];
                        j = addNewToken(j, Sym.T_LTE,lexeme);
                    }
                    else j = addNewToken(j, Sym.T_LT, typeOrLexeme[i]);
                    break;
                case "-":
                    j = addNewToken(j, Sym.T_MINUS, typeOrLexeme[i]);
                    break;
                case "#":
                    j = addNewToken(j, Sym.T_NEQ, typeOrLexeme[i]);
                    break;
                case "+":
                    j = addNewToken(j, Sym.T_PLUS, typeOrLexeme[i]);
                    break;
                case "}":
                    j = addNewToken(j, Sym.T_RBRACE, typeOrLexeme[i]);
                    break;
                case "]":
                    j = addNewToken(j, Sym.T_RBRACKET, typeOrLexeme[i]);
                    break;
                case ")":
                    j = addNewToken(j, Sym.T_RPAREN, typeOrLexeme[i]);
                    break;
                case ";":
                    j = addNewToken(j, Sym.T_SEMI, typeOrLexeme[i]);
                    break;
                case "~":
                    j = addNewToken(j, Sym.T_TILDE, typeOrLexeme[i]);
                    break;
                case "/":
                    j = addNewToken(j, Sym.T_SLASH, typeOrLexeme[i]);
                    break;
                case "*":
                    j = addNewToken(j, Sym.T_STAR, typeOrLexeme[i]);
                    break;
                default:
                    j = addNewToken(j, Sym.error, typeOrLexeme[i]);
                    //throw new IllegalCharacterException("'" + typeOrLexeme[i] + "' is an illegal character.");




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
