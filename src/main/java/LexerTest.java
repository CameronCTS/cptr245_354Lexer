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
        lexer.tokenize("MODULE Goodbye");
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
    public void testTypeOrLexeme() {

        assertEquals(lexer.typeOrLexeme[0], "MODULE");
    }

    @Test
    public void testTokenizeMultipleTokens() {

        lexer.tokenize("MODULE Goodbye MODULE Hello");
        assertEquals(lexer.tokenArray[1].GetType(), 20);
        assertEquals(lexer.tokenArray[1].GetLexeme(), "Hello");
    }

    @Test
    public void testTokenizePunctuation() {

        lexer.tokenize("MODULE Goodbye;MODULE Hello&= YAY");
        token = lexer.getToken();
        assertEquals(lexer.tokenArray[1].GetType(),65);
        assertEquals(lexer.tokenArray[3].GetType(), 43);

    }

    @Test
    public void testIllegalCharacterSeparateToken() {

        lexer.tokenize("MODULE Goodbye @");
        assertEquals(lexer.tokenArray[1].GetType(), 1);

    }

    /*
    @Test
    public void testIllegalCharacterWithinToken() {

    }
    */


    @Test
    //null input throws IOException
    public void testReadFileNotFound() {
        assertEquals(lexer.readFile("src/main/doesNotExist.txt", StandardCharsets.UTF_8), "Error. Caught IOException");
    }


}