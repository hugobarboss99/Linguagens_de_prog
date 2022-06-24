package lexical;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    
    private Map<String, TokenType> st;

    public SymbolTable() {
        st = new HashMap<String, TokenType>();

        // SYMBOLS
        st.put(";", TokenType.SEMI_COLON);
        st.put(",", TokenType.COMMA);
        st.put("*", TokenType.MUL);
        st.put("(", TokenType.OPEN_BRA);
        st.put(")", TokenType.CLOSE_BRA);

        // OPERATORS
        st.put("=", TokenType.ASSIGN);
        st.put("!=", TokenType.NOT_EQUALS);
        st.put("<", TokenType.LOWER);
        st.put(">", TokenType.GREATER);
        st.put("<=", TokenType.LOWER_EQUAL);
        st.put(">=", TokenType.GREATER_EQUAL);
        
        // KEYWORDS
        st.put("SELECT", TokenType.SELECT);
        st.put("FROM", TokenType.FROM);
        st.put("WHERE", TokenType.WHERE);
        st.put("AND", TokenType.AND);
        st.put("OR", TokenType.OR);
        st.put("BETWEEN", TokenType.BETWEEN);
        st.put("ORDER", TokenType.ORDER);
        st.put("BY", TokenType.BY);
        st.put("ASC", TokenType.ASC);
        st.put("DESC", TokenType.DESC);
        st.put("IN", TokenType.IN);
    }

    public boolean contains(String token) {
        return st.containsKey(token);
    }

    public TokenType find(String token) {
        return this.contains(token) ? st.get(token) : TokenType.NAME;
    }
}
