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

        inputCharString = lexer.readFile("src/main/inputTest.txt", StandardCharsets.UTF_8);
        String inputTestString = "MODULE Hello;";
        inputTestCharArray = inputTestString.toCharArray();
        assertArrayEquals(inputCharString, inputTestCharArray);

    }

    @Test
    public void testTokenizeCharsSemi() {
        String inputTestString = "MODULE Hello;";
        inputTestCharArray = inputTestString.toCharArray();
        lexer.tokenizeChars(inputTestCharArray);
        assertEquals(lexer.tokenArray[0].GetType(), Sym.T_SEMI);
        assertEquals(lexer.tokenArray[0].GetLexeme(), ";");
    }

    @Test
    public void testTokenizeCharsString() {
        char[] charArray = {'"', 'h','e','l','l','o','"','e','x','t','r','a'};
        lexer.tokenizeChars(charArray);
        assertEquals(lexer.tokenArray[0].GetType(), Sym.T_STR_LITERAL);
        assertEquals(lexer.tokenArray[0].GetLexeme(), "hello");
    }










    /*
    @Test
    public void testTypeOrLexeme() {

        assertEquals(lexer.typeOrLexeme[0], "MODULE");
    }

    @Test
    public void testTokenizeMultipleTokens() {

        lexer.tokenize("MODULE Goodbye MODULE Hello");
        assertEquals(lexer.tokenArray[1].GetType(), Sym.T_MODULE);
        assertEquals(lexer.tokenArray[1].GetLexeme(), "MODULE");
    }

    @Test
    public void testTokenizePunctuation() {

        lexer.tokenize("MODULE Goodbye;MODULE Hello&= YAY");
        //token = lexer.getToken();
        assertEquals(lexer.tokenArray[1].GetType(),65);
        assertEquals(lexer.tokenArray[3].GetType(), 43);
    }

    @Test
    public void testIllegalCharacterSeparateToken() {

        lexer.tokenize("MODULE Goodbye @");
        assertEquals(lexer.tokenArray[1].GetType(), 1);

    }

    */

    @Test
    //null input throws IOException
    public void testReadFileNotFound() {

        assertNull(lexer.readFile("src/main/doesNotExist.txt", StandardCharsets.UTF_8));
    }


}