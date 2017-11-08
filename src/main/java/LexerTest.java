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
    public void testTypeOrLexeme() {
        lexer.tokenize("MODULE Goodbye");
        assertEquals(lexer.typeOrLexeme[0], "MODULE");
    }

    @Test
    public void testTokenArray() {
        lexer.tokenize("MODULE Goodbye");
        assertEquals(lexer.tokenArray[0].GetType(), 20);
    }

    @Test
    public void testTokenizeSingleToken() {

        lexer.tokenize("MODULE Goodbye");
        //token = lexer.getToken();
        assertEquals(lexer.tokenArray[0].GetType(), 20);
        assertEquals(lexer.tokenArray[0].GetLexeme(), "Goodbye");
    }

    @Test
    public void testTokenizeMultipleTokens() {

        lexer.tokenize("MODULE Goodbye MODULE Hello");
        //token = lexer.getToken();
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
    //null input throws IOException
    public void testReadFileNotFound() {
        assertEquals(lexer.readFile("src/main/doesNotExist.txt", StandardCharsets.UTF_8), "Error. Caught IOException");
    }


}