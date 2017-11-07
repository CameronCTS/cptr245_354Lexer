import org.junit.*;
import static org.junit.Assert.*;
import java.nio.charset.StandardCharsets;

public class LexerTest {

    public Lexer lexer;
    public Token token;

    @Before
    public void setUp() {

        lexer = new Lexer();

    }

    @Test
    public void testSetToken() {

        lexer.SetToken(1, "10");
        token = lexer.GetToken();
        assertEquals(token.GetLexeme(), "10");
        assertEquals(token.GetType(), 1);
    }

    @Test
    public void testReadFile() {
        assertEquals(lexer.readFile("src/main/inputTest.txt", StandardCharsets.UTF_8), "Test file.");
    }

    /*
    @Test
    null input throws IOException
     */

}