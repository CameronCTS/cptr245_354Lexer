import org.junit.Test;

import static org.junit.Assert.*;

public class LexerTest {

    @Test
    public void testGetLexeme() {

        Lexer lex = new Lexer();
        Token token = lex.GetToken ();
        assertEquals(token.GetLexeme(), "5");
    }

}