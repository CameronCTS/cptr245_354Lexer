import java.nio.charset.StandardCharsets;

public class IncludeFile {

    public Token include(String filePath) {

        Lexer lexerInclude = new Lexer();
        Token tokenInclude = new Token(Sym.error, "error");

        lexerInclude.readFile(filePath, StandardCharsets.UTF_8);

        while (Character.toString(lexerInclude.charArray[lexerInclude.index]).matches ("[^\\x00]")) {

            tokenInclude = lexerInclude.getToken();

            return tokenInclude;
        }

        System.err.println("Empty INCLUDE file.");
        return tokenInclude;
    }
}
