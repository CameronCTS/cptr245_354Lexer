import org.junit.*;
import static org.junit.Assert.*;
import java.nio.charset.StandardCharsets;

public class LexerTest {

    private Lexer lexer;
    private Token token;
    private String inputString;

    @Before
    public void setUp() {

        lexer = new Lexer();

    }

    @Test
    public void testSetToken() {

        lexer.setToken(45, "10");
        token = lexer.getToken();
        assertEquals(token.GetLexeme(), "10");
        assertEquals(token.GetType(), Sym.T_ASSIGN);
    }

    @Test
    public void testReadFile() {

        inputString = lexer.readFile("src/main/inputTest.txt", StandardCharsets.UTF_8);
        assertEquals(inputString, "MODULE Hello;");
    }

    @Test
    public void testTokenizeSingleToken() {

        lexer.tokenize("MODULE Goodbye");
        token = lexer.getToken();
        assertEquals(token.GetType(), 20);
        assertEquals(token.GetLexeme(), "Goodbye");
    }

    @Test
    public void testTokenizePunctuation() {

        lexer.tokenize("MODULE Goodbye;MODULE Hello&");
        token = lexer.getToken();
        assertEquals(token.GetType(), 20);
        assertEquals(token.GetLexeme(), "Goodbye");
        assertEquals(lexer.typeOrLexeme[2],";");
        assertEquals(lexer.typeOrLexeme[5], "&");
    }

    /*
    @Test
    public void testTokenizeMultipleTokens() {

        lexer.tokenize("MODULE Goodbye;MODULE Hello");
        token = lexer.getToken();
        assertEquals(token.GetType(), 20);
        assertEquals(token.GetLexeme(), "Goodbye");
    }
    */


    @Test
    //null input throws IOException
    public void testReadFileNotFound() {
        assertEquals(lexer.readFile("src/main/doesNotExist.txt", StandardCharsets.UTF_8), "Error. Caught IOException");
    }


}