import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;


// !! Please note that this Lexer assumes that all identifiers begin with a lowercase alphabetical character.

public class Lexer {

    public char[] charArray;
    public int index = 0;
    private HashMap<String, Integer> keywordHashMap = new HashMap<>();
    private boolean noPeriod = true;
    private boolean notChar = true;

    // Method to read in a .txt file and output its contents as a char array.
    public char[] readFile(String path, Charset encoding) {

        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            String inputString = new String(encoded, encoding);
            charArray = inputString.toCharArray();
            return charArray;
        }catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
            return null;
        }

    }

    public char[] readString(String inputString) {

        charArray = inputString.toCharArray();
        return charArray;
    }


    // point to the next character in a string
    // read in each character and identify punctuation
    // read in each character and use special characters to identify types


    public Token getToken() {

        String lexeme;
        int type = 0;
        //int indexOrg;
        hashMapInit();


        // Ignore whitespace characters.
        while (Character.isWhitespace(charArray[index])) {

            index++;
        }

        // Tokenize keywords:

        if (Character.toString(charArray[index]).matches("[A-Z]")) {

            lexeme = keywordSearch();

            // Add special case for INCLUDE token
            if (lexeme.equals("INCLUDE")) {

                String path = includeFilePath();
                //indexOrg = index;   // Save the value of index.

                IncludeFile includeFile = new IncludeFile();
                Token tok = includeFile.include(path);
                //index = indexOrg;   // Restore index.
                return tok;
            }

            else {

                type = keywordHashMap.get(lexeme);
                return new Token(type, lexeme);
            }


        }

        // Tokenize ID:
        if (Character.toString(charArray[index]).matches("[a-z]")) {

            lexeme = iDSearch();
            return new Token(Sym.T_ID, lexeme);

        }

        // Tokenize Int literal.
        // If int literal contains a period, break and call realSearch
        // to find Real literal instead.
        if (Character.toString(charArray[index]).matches("[0-9]")) {

            lexeme = intSearch();

            if (noPeriod && notChar) {
                return new Token(Sym.T_INT_LITERAL, lexeme);
            }

            else if (notChar) {
                lexeme = realSearch();
                return new Token(Sym.T_REAL_LITERAL, lexeme);
            }

            else if (noPeriod) {
                lexeme = charSearch();
                return new Token(Sym.T_CHAR_LITERAL, lexeme);
            }

        }

        // Tokenize String literal
        // If string is started using double quotes.
        if (Character.toString(charArray[index]).matches("[\"]")) {

            index++; // Increment past the " or ' to make the stringSearch function easier.
                     // (Also so " or ' is not included at the beginning of the string literal.)
            lexeme = stringSearch("\"");
            return new Token(Sym.T_STR_LITERAL, lexeme);
        }

        // If string is started using single quotes.
        if (Character.toString(charArray[index]).matches("[']")) {

            index++; // Increment past the " or ' to make the stringSearch function easier.
            // (Also so " or ' is not included at the beginning of the string literal.)
            lexeme = stringSearch("'");
            return new Token(Sym.T_STR_LITERAL, lexeme);
        }

        // Tokenize punctuation tokens:

        else if (charArray[index] == '&') {

            index++;
            return new Token(Sym.T_AMPERSAND, "&");
        }


        else if (charArray[index] == '^') {

            index++;
            return new Token(Sym.T_ARROW, "^");
        }

        else if (charArray[index] == ':') {

            lexeme = twoCharToken(':','=');
            if (lexeme.equals(":")) {
                return new Token(Sym.T_COLON, ":");
            }

            else if (lexeme.equals(":=")) {
                return new Token(Sym.T_ASSIGN, ":=");
            }

        }

        else if (charArray[index] == '|') {

            index++;
            return new Token(Sym.T_BAR,"|");
        }

        else if (charArray[index] == ',') {

            index++;
            return new Token(Sym.T_COMMA,",");
        }

        else if (charArray[index] == '.') {

            lexeme = twoCharToken('.','.');
            if (lexeme.equals(".")) {
                return new Token(Sym.T_DOT, ".");
            }

            else if (lexeme.equals("..")) {
                return new Token(Sym.T_DOTDOT, "..");
            }

        }

        else if (charArray[index] == '=') {

            index++;
            return new Token(Sym.T_EQU,"=");
        }

        else if (charArray[index] == '>') {

            lexeme = twoCharToken('>','=');
            if (lexeme.equals(">")) {
                return new Token(Sym.T_GT, ">");
            }

            else if (lexeme.equals(">=")) {
                return new Token(Sym.T_GTE, ">=");
            }

        }

        else if (charArray[index] == '{') {

            index++;
            return new Token(Sym.T_LBRACE,"{");
        }

        else if (charArray[index] == '[') {

            index++;
            return new Token(Sym.T_LBRACKET,"[");
        }

        else if (charArray[index] == '(') {

            index++;
            return new Token(Sym.T_LPAREN,"(");
        }

        else if (charArray[index] == '<') {

            lexeme = twoCharToken('<','=');
            if (lexeme.equals("<")) {
                return new Token(Sym.T_LT, "<");
            }

            else if (lexeme.equals("<=")) {
                return new Token(Sym.T_LTE, "<=");
            }

        }

        else if (charArray[index] == '-') {

            index++;
            return new Token(Sym.T_MINUS,"-");
        }

        else if (charArray[index] == '#') {

            index++;
            return new Token(Sym.T_NEQ,"#");
        }

        else if (charArray[index] == '+') {

            index++;
            return new Token(Sym.T_PLUS,"+");
        }

        else if (charArray[index] == '}') {

            index++;
            return new Token(Sym.T_RBRACE,"}");
        }

        else if (charArray[index] == ']') {

            index++;
            return new Token(Sym.T_RBRACKET,"]");
        }

        else if (charArray[index] == ')') {

            index++;
            return new Token(Sym.T_RPAREN,")");
        }

        else if (charArray[index] == ';') {

            index++;
            return new Token(Sym.T_SEMI,";");
        }

        else if (charArray[index] == '~') {

            index++;
            return new Token(Sym.T_TILDE,"~");
        }

        else if (charArray[index] == '/') {

            index++;
            return new Token(Sym.T_SLASH,"/");
        }

        else if (charArray[index] == '*') {

            index++;
            return new Token(Sym.T_STAR,"*");
        }

        // error state
        return new Token(Sym.error, "error");
    }


    private String keywordSearch() {
        int totalLength = index;
        int stringLength = 0;
        String lexemeStr = "";


        // Check if we have reached the end of charArray.
        // If present, add the next capital letter to lexemeStr.
        while (totalLength + 1 < charArray.length) {

            if (Character.isWhitespace(charArray[totalLength])) {
                index += stringLength;
                return lexemeStr;
            }

            if (Character.toString(charArray[totalLength]).matches("[A-Z]")) {
                lexemeStr += charArray[totalLength];
                totalLength++;
                stringLength++;
            }

        }


        index += stringLength;
        return lexemeStr;
    }

    private String iDSearch() {
        int totalLength = index;
        int stringLength = 0;
        String lexemeStr = "";


        // Check if we have reached the end of charArray.
        // If present, add the next capital letter to lexemeStr.
        while (totalLength + 1 < charArray.length && stringLength <= 39) {

            if (Character.isWhitespace(charArray[totalLength])) {
                index = totalLength;
                return lexemeStr;
            }

            if (Character.toString(charArray[totalLength]).matches("[a-zA-Z0-9\\-]")) {
                lexemeStr += charArray[totalLength];
                totalLength++;
                stringLength++;
            }

            // If quotes are found in an ID:

        }
        // If the identifier is longer than 40 characters, read until a non-identifier character is found
        // Set index = to this character.
        // Return the identifier truncated to 40 characters.
        if (stringLength > 40) {

            while (Character.toString(charArray[totalLength]).matches("[^\\s&^|:,.=>{(<\\-#+});~/*\\[\\]]")) {

                stringLength++;
            }

            System.err.println("Identifier too long.");
            index += (stringLength - 1); // Put the non-identifier character back in the input stream.
            return lexemeStr;

        }

        index += stringLength;
        return lexemeStr;
    }

    private String intSearch() {
        int totalLength = index;    // Marks the number of the character in the entire char array.
        int stringLength = 0;       // Marks the number of the character in the sub array.
        String lexemeStr = "";

        while (Character.toString(charArray[totalLength]).matches("0")) {

            totalLength++;
        }
        // Check if we have reached the end of charArray.
        // If present, add the next character to lexemeStr.
        while (totalLength + 1 < charArray.length && stringLength < 10) {

            // If whitespace is found, the end of the lexeme has been reached.
            if (Character.isWhitespace(charArray[totalLength])) {
                index = totalLength;
                return lexemeStr;
            }

            // Return lexemeStr if H is found
            if (Character.toString(charArray[totalLength]).matches("H")) {
                lexemeStr += charArray[totalLength];
                totalLength++;
                index = totalLength;
                return lexemeStr;
            }

            if (Character.toString(charArray[totalLength]).matches("[a-fA-F0-9]")) {
                lexemeStr += charArray[totalLength];
                totalLength++;
                stringLength++;
            }

            // If a period is found, break out of the loop and call realSearch().
            if (Character.toString(charArray[totalLength]).matches("[.]")) {
                // Don't adjust index to make sure realSearch() starts with the correct value.
                noPeriod = false;
                return lexemeStr;
            }

            // If an X is found, the number is a Char literal. Break out of intSearch and call charSearch().
            if (Character.toString(charArray[totalLength]).matches("[X]")) {
                // Don't adjust index to make sure charSearch() starts with the correct value.
                notChar = false;
                return lexemeStr;
            }
            // Add error case if a-z or G-Z are found.


        }
        // If the int literal is longer than 10 characters, read until a non-alphanumeric character is found
        // Set index = to this character.
        // Return the identifier truncated to 10 characters.
        if (stringLength >= 10) {

            while (Character.toString(charArray[totalLength]).matches("[a-fA-FH0-9]")) {

                // If H is found after more than 10 characters, append it as character 10 and
                // return lexemeStr and print error message.
                if (Character.toString(charArray[totalLength]).matches("[H]")) {

                    lexemeStr += charArray[totalLength];
                    System.err.println("Int_literal too long.");
                    totalLength++;
                    index = totalLength;
                    return lexemeStr;
                }

                if (Character.toString(charArray[totalLength]).matches(".")) {
                    // Don't adjust index to make sure realSearch() starts with the correct value.
                    noPeriod = true;
                }

                // Add error case if a-z or G-Z are found.

                totalLength++;
            }

            System.err.println("Int_literal too long.");
            index = (totalLength - 1); // Put the non-integer character back in the input stream.
            return lexemeStr;

        }

        index = totalLength;
        return lexemeStr;
    }

    private String stringSearch(String exitQuote) {
        int totalLength = index;    // Marks the number of the character in the entire char array.
        int stringLength = 0;       // Marks the number of the character in the sub array.
        String lexemeStr = "";

        // Check if we have reached the end of charArray.
        // If present, add the next character to lexemeStr.
        while (totalLength + 1 < charArray.length && stringLength < 79) {

            // Return lexemeStr if " or ' is found
            if (Character.toString(charArray[totalLength]).matches(exitQuote)) {
                // Don't add " or ' to the string but still increment past it.
                totalLength++;
                index = totalLength;
                return lexemeStr;
            }

            // Add all characters that are not " or ' to lexeme.
            lexemeStr += charArray[totalLength];
            totalLength++;
            stringLength++;

        }
        // If the string literal is longer than 80 characters, read until a " or ' is found.
        // Set index = to this character + 1.
        // Return the identifier truncated to 80 characters.
        if (stringLength >= 79) {

            // Iterate past all characters after 80.
            while (!Character.toString(charArray[totalLength]).matches(exitQuote)) {

                // Return string if new line encountered.
                if (Character.toString(charArray[totalLength]).matches("[\n]")) {

                    // Increment index to the character after the new line.
                    totalLength++;
                    System.err.println("Unterminated literal, newline in string.");
                    index = totalLength;
                    return lexemeStr;
                }

                if (totalLength + 3 < charArray.length
                        && Character.toString(charArray[totalLength + 1]).matches("E")
                        && Character.toString(charArray[totalLength + 2]).matches("O")
                        && Character.toString(charArray[totalLength + 3]).matches("F")) {

                    totalLength += 3;
                    System.err.println("Unterminated literal, EOF in string.");
                    index = totalLength;
                    return lexemeStr;
                }
                totalLength++;
            }

            System.err.println("Unterminated string literal.");
            index = totalLength;
            return lexemeStr;

        }

        index = totalLength;
        return lexemeStr;
    }

    private String realSearch() {

        int totalLength = index;    // Marks the number of the character in the entire char array.
        int mantissa = 0;       // Marks the number of the character in the sub array.
        int exponent = 0;
        String lexemeStr = "";

        // Remove leading zeros.
        while (Character.toString(charArray[totalLength]).matches("0")) {

            totalLength++;
        }

        // Get mantissa.
        while (totalLength + 1 < charArray.length && mantissa < 10) {

            if (Character.toString(charArray[totalLength]).matches("[0-9.]")) {
                lexemeStr += charArray[totalLength];
                totalLength++;
            }
            mantissa++;
        }

        // If the next character is a number, the mantissa is too long.
        if (Character.toString(charArray[totalLength]).matches("[0-9]")) {
            System.err.println("Real literal mantissa too long.");
        }

        // Get E or D.
        while (totalLength + 1 < charArray.length) {
            if (Character.toString(charArray[totalLength]).matches("[ED]")) {
                lexemeStr += charArray[totalLength];
                totalLength++;
                break; //Break out of the while loop if E or D is found.
            }

            // If another non-numeric character is found before E or D, return the mantissa as lexemeStr.
            if (Character.toString(charArray[totalLength]).matches("[^ED0-9]")) {
                // Do not increment totalLength so character read back into getToken is the same as above.
                index = totalLength;
                return lexemeStr;
            }
            totalLength++;
        }

        // Get + or - if present.
        if (Character.toString(charArray[totalLength]).matches("[+\\-]")) {
            lexemeStr += charArray[totalLength];
            totalLength++;
        }

        // Get exponent digits.
        while (totalLength + 1 < charArray.length && exponent < 3) {

            // Ignore leading zeros on the exponent.
            if (Character.toString(charArray[totalLength]).matches("[0]")) {
                totalLength++;
            }

            if (Character.toString(charArray[totalLength]).matches("[1-9]")) {
                lexemeStr += charArray[totalLength];
                totalLength++;
                exponent++;
            }

            if (Character.toString(charArray[totalLength]).matches("[^0-9]")) {

                //The end of the exponent has been found;
                break;
            }

        }

        if (exponent >= 3) {

            while(Character.toString(charArray[totalLength]).matches("[0-9]")) {

                totalLength++;
            }

            System.err.println("Real literal exponent too long.");
            index = totalLength; //getToken will restart at the same character.
            return lexemeStr;
        }

        index = totalLength;
        return lexemeStr;
    }

    private String charSearch() {
        int totalLength = index;    // Marks the number of the character in the entire char array.
        int stringLength = 0;       // Marks the number of the character in the sub array.
        String lexemeStr = "";

        while (Character.toString(charArray[totalLength]).matches("0")) {

            totalLength++;
        }

        while (totalLength + 1 < charArray.length && stringLength < 3) {

            // Return lexemeStr if X is found
            if (Character.toString(charArray[totalLength]).matches("[xX]")) {
                lexemeStr += charArray[totalLength];
                totalLength++;
                index = totalLength;
                return lexemeStr;
            }

            if (Character.toString(charArray[totalLength]).matches("[a-fA-F0-9]")) {

                lexemeStr += charArray[totalLength];
                totalLength++;
                stringLength++;
            }

        }

        if (Character.toString(charArray[totalLength]).matches("[xX]")) {

            lexemeStr += charArray[totalLength];
            totalLength++;
            index = totalLength;
            return lexemeStr;
        }

        if (stringLength >= 3) {

            while (Character.toString(charArray[totalLength]).matches("[a-fA-FX0-9]")) {

                // If X is found after more than 3 characters, append it as character 3 and
                // return lexemeStr and print error message.
                if (Character.toString(charArray[totalLength]).matches("[xX]")) {

                    lexemeStr += charArray[totalLength];
                    System.err.println("Illegal character literal.");
                    totalLength++;
                    index = totalLength;
                    return lexemeStr;
                }

                // Add error case if a-z or G-Z are found.

                totalLength++;
            }

            System.err.println("Illegal character literal.");
            index = (totalLength - 1); // Put the non-char literal character back in the input stream.
            return lexemeStr;

        }

        index = totalLength;
        return lexemeStr;
    }

    private String includeFilePath() {

        String filePath = "";

        while (Character.toString(charArray[index]).matches("\\s")) {
            index++;
        }

        if (Character.toString(charArray[index]).matches("[\"]")) {

            index++; // Increment past the ".
            filePath = stringSearch("\"");
        }

        else if (Character.toString(charArray[index]).matches("[\']")) {

            index++; // Increment past the '.
            filePath = stringSearch("'");
        }

        else {

            System.err.println("Illegal INCLUDE directive " + filePath + ".");
        }

        return filePath;

    }

    // Hash map to match strings to strings and token types.
    private void hashMapInit() {

        keywordHashMap.put("ARRAY", Sym.T_ARRAY);
        keywordHashMap.put("BEGIN", Sym.T_BEGIN);
        keywordHashMap.put("BY", Sym.T_BY);
        keywordHashMap.put("CASE", Sym.T_CASE);
        keywordHashMap.put("CONST", Sym.T_CONST);
        keywordHashMap.put("DIV", Sym.T_DIV);
        keywordHashMap.put("DO", Sym.T_DO);
        keywordHashMap.put("ELSE", Sym.T_ELSE);
        keywordHashMap.put("ELSIF", Sym.T_ELSIF);
        keywordHashMap.put("END", Sym.T_END);
        keywordHashMap.put("EXIT", Sym.T_EXIT);
        keywordHashMap.put("FOR", Sym.T_FOR);
        keywordHashMap.put("IF", Sym.T_IF);
        keywordHashMap.put("IMPORT", Sym.T_IMPORT);
        keywordHashMap.put("IN", Sym.T_IN);
        keywordHashMap.put("IS", Sym.T_IS);
        keywordHashMap.put("LOOP", Sym.T_LOOP);
        keywordHashMap.put("MOD", Sym.T_MOD);
        keywordHashMap.put("MODULE", Sym.T_MODULE);
        keywordHashMap.put("NIL", Sym.T_NIL);
        keywordHashMap.put("OF", Sym.T_OF);
        keywordHashMap.put("OR", Sym.T_OR);
        keywordHashMap.put("POINTER", Sym.T_POINTER);
        keywordHashMap.put("PROCEDURE", Sym.T_PROCEDURE);
        keywordHashMap.put("RECORD", Sym.T_RECORD);
        keywordHashMap.put("REPEAT", Sym.T_REPEAT);
        keywordHashMap.put("RETURN", Sym.T_RETURN);
        keywordHashMap.put("THEN", Sym.T_THEN);
        keywordHashMap.put("TO", Sym.T_TO);
        keywordHashMap.put("TYPE", Sym.T_TYPE);
        keywordHashMap.put("UNTIL", Sym.T_UNTIL);
        keywordHashMap.put("VAR", Sym.T_VAR);
        keywordHashMap.put("WHILE", Sym.T_WHILE);
        keywordHashMap.put("WITH", Sym.T_WITH);

        // Predeclared identifiers are in this case equivalent to keywords
        keywordHashMap.put("BOOLEAN", Sym.T_BOOLEAN);
        keywordHashMap.put("CHAR", Sym.T_CHAR);
        keywordHashMap.put("FALSE", Sym.T_FALSE);
        keywordHashMap.put("INTEGER", Sym.T_INTEGER);
        keywordHashMap.put("NEW", Sym.T_NEW);
        keywordHashMap.put("REAL", Sym.T_REAL);
        keywordHashMap.put("TRUE", Sym.T_TRUE);
    }


    private String twoCharToken(char firstChar, char secondChar) {

        String str = "";
        while (index + 1 < charArray.length) {
            if (charArray[index + 1] == secondChar) {

                str += firstChar;
                str += secondChar;
                index += 2;     // Must increment past current char and next char.
                return str;
            }
        }

        index++;
        str += firstChar;
        return str;
    }


}
