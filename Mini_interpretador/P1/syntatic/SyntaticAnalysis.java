package syntatic;

import interpreter.command.Command;
import lexical.Lexeme;
import lexical.LexicalAnalysis;
import lexical.TokenType;

import java.util.Stack;

public class SyntaticAnalysis {
    private LexicalAnalysis lex;
    private Lexeme current;
    private Stack<Lexeme> history;
    private Stack<Lexeme> queued;

    public SyntaticAnalysis(LexicalAnalysis lex) {
        this.lex = lex;
        this.current = lex.nextToken();
        this.history = new Stack<Lexeme>();
        this.queued = new Stack<Lexeme>();
    }

    public Command start() {
        procCmd();
        eat(TokenType.END_OF_FILE);
        return null;
    }

    private void rollback() {
        assert !history.isEmpty();

        System.out.println("Rollback (\"" + current.token + "\", " + current.type + ")");
        queued.push(current);
        current = history.pop();
    }

    private void advance() {
        System.out.println("Advanced (\"" + current.token + "\", " +
            current.type + ")");
        history.add(current);
        current = queued.isEmpty() ? lex.nextToken() : queued.pop();
    }
    private void eat(TokenType type) {
        System.out.println("Expected (..., " + type + "), found (\"" + 
            current.token + "\", " + current.type + ")");
        if (type == current.type) {
            history.add(current);
            current = queued.isEmpty() ? lex.nextToken() : queued.pop();
        } else {
            showError();
        }
    }

    private void showError() {
        /*System.out.printf("%02d: ", lex.getLine());

        switch (current.type) {
            case INVALID_TOKEN:
                System.out.printf("Lexema inválido [%s]\n", current.token);
                break;
            case UNEXPECTED_EOF:
            case END_OF_FILE:
                System.out.printf("Fim de arquivo inesperado\n");
                break;
            default:
                System.out.printf("Lexema não esperado [%s]\n", current.token);
                break;
        }*/
    	
    	System.out.println("Nao.");
        System.exit(1);
    }
    
    //<cmd> ::= <select> <from> [<where>] [<order>]';'
    private void procCmd() {
    	if (current.type == TokenType.SELECT)
            procSel();
        else
            showError();
        
        if (current.type == TokenType.FROM) {
            procFrom();
        }
        else
            showError();
        
        if (current.type == TokenType.WHERE) {
            procWhere();
        }
        if (current.type == TokenType.ORDER) {
            procOrder();
        }
        eat(TokenType.SEMI_COLON);
    }
    //<select> ::= select (<exp> {','<exp>} | '*')
    private void procSel() {
    	eat(TokenType.SELECT);
    	if(current.type == TokenType.NAME) {
    		advance();
    		while(current.type == TokenType.COMMA) {
    			advance();
    			procExp();
    		} 
    	} else {
    		eat(TokenType.MUL);
    	}
    }
    
    //<from> ::= from <exp>
    private void procFrom() {
    	eat(TokenType.FROM);
    	procExp();
    }
    
    //<where> ::= where <gen> {(and | or) <gen>}
    private void procWhere() {
    	eat(TokenType.WHERE);
        procGen();
        while (current.type == TokenType.AND || current.type == TokenType.OR) {
            if (current.type == TokenType.AND) {
                advance();
            }
            else
                advance();
            procGen();
        }
    }
    
    //<gen> ::= <exp> (<a> | <b> | <c> )
    private void procGen() {
    	procExp();

       if (current.type == TokenType.ASSIGN || current.type == TokenType.NOT_EQUALS || current.type == TokenType.GREATER || current.type == TokenType.GREATER_EQUAL ||
           current.type == TokenType.LOWER || current.type == TokenType.LOWER_EQUAL) {   
            procA();
        }
        else if (current.type == TokenType.BETWEEN) {
            procB();
        }
        else {
            procC();
        }
    }
    
    //<a> ::= <op> <const>
    private void procA() {
    	procOp();
        procConst();
    }
    
    //<b> ::= between <const> and <const>
    private void procB() {
    	eat(TokenType.BETWEEN);
        procConst();

        eat(TokenType.AND);
        procConst();
    }
    
    //<c> ::= in '(' <const> {',' <const> } ')'
    private void procC() {
    	eat(TokenType.IN);
    	eat(TokenType.OPEN_BRA);
    	procConst();
    	while(current.type == TokenType.COMMA) {
    		advance();
    		procConst();
    	}
    	eat(TokenType.CLOSE_BRA);
    }
    
    //<order>::= order by <exp> [ASC | DESC]
    private void procOrder() {
    	eat(TokenType.ORDER);
    	if(current.type == TokenType.BY){
            advance();
            procExp();
            if(current.type == TokenType.ASC || current.type == TokenType.DESC) {
            	advance();
            }
        } else {
        	showError();
        }
    }
    
    //<op> ::= ('=' | '!=' | '<' | '>' | '<=' | '>=' )
    private void procOp() {
    	if(current.type == TokenType.ASSIGN){
            advance();
    	}
        else if(current.type == TokenType.NOT_EQUALS) {
        	advance();
        }
        else if(current.type == TokenType.LOWER){
            advance();
        } 
        else if(current.type == TokenType.GREATER){
            advance();
        } 
        else if(current.type == TokenType.LOWER_EQUAL){
            advance();
        } 
        else if(current.type == TokenType.GREATER_EQUAL){
            advance();
    	}
        else {
    		showError();
    	}
    }
    
    //<const> ::= <number> | <text>
    private void procConst(){
    	if (current.type == TokenType.NUMBER) {
            procNumber();
        }
    	else if (current.type == TokenType.TEXT) {
            procText();
        }
    	else {
            showError();
        }
    }
    
    private void procExp() {
        eat(TokenType.NAME);
    }

    private void procNumber() {
        eat(TokenType.NUMBER);
    }

    private void procText() {
        eat(TokenType.TEXT);
    }
}
