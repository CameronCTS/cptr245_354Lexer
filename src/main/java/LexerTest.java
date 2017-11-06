import org.junit.*;

import static org.junit.Assert.*;

public class LexerTest {

    public Lexer lexer;
    public Token token;

    @Before
    public void setup() {

        lexer = new Lexer();

    }

    @Test
    public void testSetToken() {

        lexer.SetToken(1, "10");
        token = lexer.GetToken();
        assertEquals(token.GetLexeme(), "10");
        assertEquals(token.GetType(), 1);
    }




}