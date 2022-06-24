package lexical;

public enum TokenType {
    //SPECIALS
    UNEXPECTED_EOF,
    INVALID_TOKEN,
    END_OF_FILE,

    // SYMBOLS
    SEMI_COLON,     // ;
    COMMA,          // ,
    MUL,            // *
    OPEN_BRA,		// (
    CLOSE_BRA, 		// )

    // OPERATORS
    ASSIGN,         // =
    NOT_EQUALS,     // !=
    LOWER,          // <
    GREATER,        // >
    LOWER_EQUAL,    // <=
    GREATER_EQUAL,  // >=

    // KEYWORDS
    SELECT,
    FROM,
    WHERE,
    AND,
    OR,
    BETWEEN,
    ORDER,
    BY,
    ASC,
    DESC,
    IN,

    // OTHERS
    NAME,           // identifier
    NUMBER,         // integer
    TEXT            // string
};
