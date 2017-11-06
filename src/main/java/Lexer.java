public class Lexer {

    public Token tok;

    public void SetToken(int type, String strLexeme) {

        tok = new Token(type, strLexeme);

    }

    public Token GetToken() {

        tok.GetType();
        tok.GetLexeme();

        return tok;
    }

}
