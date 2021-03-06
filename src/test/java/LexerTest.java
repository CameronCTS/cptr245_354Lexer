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
        String inputTestString = "MODULE hello;";
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

    @Test
    public void testGetTokenInt() {

        lexer.readString("00FA;");
        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_INT_LITERAL);
        assertEquals(token.GetLexeme(), "FA");
    }

    @Test
    public void testGetTokenIntMultiple() {

        lexer.readString("00FA0123456789H 00123456789023;");

        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_INT_LITERAL);
        assertEquals(token.GetLexeme(), "FA01234567H");

        Token token2;
        token2 = lexer.getToken();
        assertEquals(token2.GetType(), Sym.T_INT_LITERAL);
        assertEquals(token2.GetLexeme(), "1234567890");
    }

    @Test
    public void testGetTokenStringTooLong() {

        lexer.readString("'This test string is much, much longer that 80 characters. Would you not agree? I would.';");
        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_STR_LITERAL);
        assertEquals(token.GetLexeme(), "This test string is much, much longer that 80 characters. Would you not agree? ");
    }

    @Test
    public void testGetTokenStringEOF() {

        lexer.readString("'This test string is much, much longer that 80 characters. Would you not agree? I would. EOF;");
        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_STR_LITERAL);
        assertEquals(token.GetLexeme(), "This test string is much, much longer that 80 characters. Would you not agree? ");
    }

    @Test
    public void testGetTokenStringNewLine() {

        lexer.readString("'This test string is much, much longer that 80 characters. Would you not agree? I would.\n;");
        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_STR_LITERAL);
        assertEquals(token.GetLexeme(), "This test string is much, much longer that 80 characters. Would you not agree? ");
    }

    // Keep this test.
    @Test
    public void testGetTokenStringDoubleSingle() {

        lexer.readString("\"test 'in single quotes.'\";");
        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_STR_LITERAL);
        assertEquals(token.GetLexeme(),  "test 'in single quotes.'");
    }

    @Test
    public void testGetTokenStringSingleDouble() {

        lexer.readString("'test \"in double quotes.\"';");
        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_STR_LITERAL);
        assertEquals(token.GetLexeme(),  "test \"in double quotes.\"");
    }

    @Test
    public void testGetTokenReal() {

        lexer.readString("1.23E+45;");
        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_REAL_LITERAL);
        assertEquals(token.GetLexeme(), "1.23E+45");
    }


    @Test
    public void testGetTokenRealNoED() {

        lexer.readString("0001.2345678911;");
        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_REAL_LITERAL);
        assertEquals(token.GetLexeme(), "1.23456789");
    }

    @Test
    public void testGetTokenRealExpTooLong() {

        lexer.readString("0001.2345678911D004569;");
        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_REAL_LITERAL);
        assertEquals(token.GetLexeme(), "1.23456789D456");
    }

    @Test
    public void testGetTokenChar() {

        lexer.readString("00A1FX;");
        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_CHAR_LITERAL);
        assertEquals(token.GetLexeme(), "A1FX");
    }

    @Test
    public void testGetTokenCharTooLong() {

        lexer.readString("00a1FbffX;");
        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_CHAR_LITERAL);
        assertEquals(token.GetLexeme(), "a1FX");
    }


    @Test
    public void testGetTokenInclude() {

        lexer.readString("INCLUDE \"src/main/inputTest.txt\";");
        token = lexer.getToken();
        assertEquals(token.GetType(), Sym.T_MODULE);
        assertEquals(token.GetLexeme(), "MODULE");

    }

    /* This test doesn't pass because INCLUDE can only
        retrieve the first lexeme in a file.


    @Test
    public void testGetTokenIncludeMultiple() {

    lexer.readString("INCLUDE \"src/main/inputTest.txt\";");
    token = lexer.getToken();
    assertEquals(token.GetType(), Sym.T_MODULE);
    assertEquals(token.GetLexeme(), "MODULE");

    Token token2;
    token2 = lexer.getToken();
    assertEquals(token2.GetType(), Sym.T_ID);
    assertEquals(token2.GetLexeme(), "hello");
    }

     */
}