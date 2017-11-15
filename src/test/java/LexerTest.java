import org.junit.*;
import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;

public class LexerTest {

    private Lexer lexer;
    private Token token;
    private char[] inputCharString;
    private char[] inputTestCharArray;

    @Before
    public void setUp() {

        lexer = new Lexer();
    }

    @Test
    public void testReadFile() {

        inputCharString = lexer.readFile("src/main/inputTest.txt", StandardCharsets.UTF_8);
        String inputTestString = "MODULE Hello;";
        inputTestCharArray = inputTestString.toCharArray();
        assertArrayEquals(inputCharString, inputTestCharArray);

    }

    //null input throws IOException
    @Test
    public void testReadFileNotFound() {

        assertNull(lexer.readFile("src/main/doesNotExist.txt", StandardCharsets.UTF_8));
    }


    @Test
    public void getTokenAmp() {

        lexer.readString("&");
        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_AMPERSAND);
        assertEquals(token.GetLexeme(), "&");
    }


    @Test
    public void getTokenArrow() {

        lexer.readString("^");
        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_ARROW);
        assertEquals(token.GetLexeme(), "^");
    }

    @Test
    public void getTokenMultiple() {
        String str = "&^";
        lexer.readString(str);

        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_AMPERSAND);
        assertEquals(token.GetLexeme(), "&");

        Token token2;
        token2 = lexer.getToken();
        assertEquals(token2.GetType(), Sym.T_ARROW);
        assertEquals(token2.GetLexeme(), "^");

    }

    @Test
    public void getTokenColon() {

        lexer.readString(":");
        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_COLON);
        assertEquals(token.GetLexeme(), ":");
    }

    @Test
    public void getTokenAssign() {

        lexer.readString(":=");
        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_ASSIGN);
        assertEquals(token.GetLexeme(), ":=");
    }



}