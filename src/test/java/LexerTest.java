import org.junit.*;
import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;

public class LexerTest {

    private Lexer lexer;
    private Token token;

    @Before
    public void setUp() {

        lexer = new Lexer();
    }

    @Test
    public void testReadFile() {

        char[] inputCharString;
        char[] inputTestCharArray;

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
    public void testGetTokenAmp() {

        lexer.readString("&");
        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_AMPERSAND);
        assertEquals(token.GetLexeme(), "&");
    }


    @Test
    public void testGetTokenArrow() {

        lexer.readString("^");
        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_ARROW);
        assertEquals(token.GetLexeme(), "^");
    }


    @Test
    public void testGetTokenColon() {

        lexer.readString(":");
        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_COLON);
        assertEquals(token.GetLexeme(), ":");
    }

    @Test
    public void testGetTokenAssign() {

        lexer.readString(":=");
        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_ASSIGN);
        assertEquals(token.GetLexeme(), ":=");
    }

    @Test
    public void testGetTokenKeyword() {

        lexer.readString("ARRAY;");
        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_ARRAY);
        assertEquals(token.GetLexeme(), "ARRAY");
    }

    @Test
    public void testGetTokenIDTooLong() {

        lexer.readString("thisIdentifierIsLongerThanFortyCharactersSS;");
        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_ID);
        assertEquals(token.GetLexeme(), "thisIdentifierIsLongerThanFortyCharacter");
    }

    @Test
    public void testGetTokenKeywordID() {

        String str = "INTEGER index88;";
        lexer.readString(str);

        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_INTEGER);
        assertEquals(token.GetLexeme(), "INTEGER");

        Token token2;
        token2 = lexer.getToken();
        assertEquals(token2.GetType(), Sym.T_ID);
        assertEquals(token2.GetLexeme(), "index88");

        Token token3;
        token3 = lexer.getToken();
        assertEquals(token3.GetType(), Sym.T_SEMI);
        assertEquals(token3.GetLexeme(), ";");
    }


}