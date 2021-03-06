import java.util.Scanner;
import java.io.*;

public class Main {
    /*
    Assignment 1 question 5
    Drew Pulliam
    dtp180003
    */
    static int charClass;
    static char[] lexeme = new char[100];
    static char nextChar;
    static int lexLen;
    static int token;
    static int nextToken;
    /* Character classes */
    static final char LETTER = 0;
    static final char DIGIT = 1;
    static final char UNKNOWN = 99;
    /* Token codes */
    static int INT_LIT = 10;
    static int IDENT = 11;
    static int ASSIGN_OP = 20;
    static int ADD_OP = 21;
    static int SUB_OP = 22;
    static int MULT_OP = 23;
    static int DIV_OP = 24;
    static int LEFT_PAREN = 25;
    static int RIGHT_PAREN = 26;
    static int GR_THAN = 27;
    static int GR_EQ_THAN = 28;
    static int LS_THAN = 29;
    static int LS_EQ_THAN = 30;
    static int EQ_TO = 31;
    static int NT_EQ_TO = 32;
    static int INCR_OP = 31;
    static int DECR_OP = 32;
    static int NEGATION = 33;
    static int DO = 34;
    static int WHILE = 38;
    static int SEMICOLON = 35;
    static int LEFT_CURLY = 36;
    static int RIGHT_CURLY = 37;
    static final char EOF = (char)-1;

    static Scanner input;
    static String filename = "C:\\Users\\ilive\\Documents\\UTD\\2020-Summer\\CS4337-0U2\\assignments\\q5\\doWhile.txt";
    public static void main(String[] args) throws IOException{
        input = new Scanner(new File(filename));
        input.useDelimiter("");

        getChar();
		lex();
        doBlock();
        whileLoop();
    }

    /*forLoop
 Parses strings in the language generated by the rule:
 <forLoop> -> for(<Assign>,<cond><assign>)'{'{<assign>;}'}'
 */
static void whileLoop() {
	System.out.printf("enter <whileLoop>\n");
	if (nextToken == WHILE) {
		lex();
		if (nextToken == LEFT_PAREN) {
			lex();
            cond();
			if (nextToken == RIGHT_PAREN) {
				lex();
			}
			else {
				error();	// Must have ; after assignment
			}
		}
		else {
			error();	// Must have (
		}
	}
	else {
		error();	// No 'for' - No for loop
	}
	System.out.printf("exit <whileLoop>\n");
}
/*cond
Parses strings in the language generated by the rule:
<cond> -> <expr> (>|>=|<|<=|!=|==) <expr>
*/
static void cond() {
	System.out.printf("enter <cond>\n");
	expr();	//Cond can just be an exression or a comparason between expressions
	if (nextToken == GR_THAN || nextToken == GR_EQ_THAN || nextToken == LS_THAN || nextToken == LS_EQ_THAN || nextToken == NT_EQ_TO || nextToken == NT_EQ_TO) {
		lex();
		expr();
	}
	System.out.printf("Exit <cond>\n");
}

    static void doBlock() {
        System.out.printf("enter <doBlock>\n");
        if (nextToken == DO) {
            lex();
            if (nextToken == LEFT_CURLY) {
                lex();
                assign();
                while (nextToken != RIGHT_CURLY) {	// {<assign>;}'}' keep with the assignments until there is an error w/o a ; or in the assignment.
                    assign();						// Ends when '}' is found
                    if (nextToken != SEMICOLON) {
                        error();
                    }
                    else {
                        lex();
                    }
                }
            }
            else {
                error();	// Must have (
            }
        }
        else {
            error();	// No 'for' - No for loop
        }
        System.out.printf("exit <doBlock>\n");
    }

    /*assign
Parses strings in the language generated by the rule:
<assign> -> id = <expr>
*/
static void assign() {
        System.out.printf("enter <assign>\n");
        if (nextToken == IDENT) {
            lex();
            if (nextToken == ASSIGN_OP) {	// If its an assignment then it will use an experesion
                lex();
                expr();
            }
            else {
                error();	// Must be one of the 3 operations
            }
        }
        else {
            error();	// Always has to start with an IDENT (id)
        }
        System.out.printf("exit <assign>\n");
    }

    /* expr
Parses strings in the language generated by the rule:
<expr> -> <term> {(+ | -) <term>}
*/
static void expr() {
        System.out.printf("Enter <expr>\n");
        /* Parse the first term */
        term();
        /* As long as the next token is + or -, get
        the next token and parse the next term */
        while (nextToken == ADD_OP || nextToken == SUB_OP) {
            lex();
            term();
        }
        System.out.printf("Exit <expr>\n");
    } /* End of function expr */

    /* term
 Parses strings in the language generated by the rule:
 <term> -> id | int_constant
 */
static void term() {
        System.out.printf("Enter <term>\n");
        if (nextToken == IDENT || nextToken == INT_LIT) {
            /* Get the next token */
            lex();
            /* If the RHS is ( <expr>), call lex to pass over the
             left parenthesis, call expr, and check for the right
             parenthesis */
        }else{
            error();
        }
        System.out.printf("Exit <term>\n");
    } /* End of function term */

    /* lookup - a function to lookup operators and parentheses
 and return the token */
    static int lookup(char ch) {
        switch (ch) {
        case '(':
            addChar();
            nextToken = LEFT_PAREN;
            break;
        case ')':
            addChar();
            nextToken = RIGHT_PAREN;
            break;
        case '{':
            addChar();
            nextToken = LEFT_CURLY;
            break;
        case '}':
            addChar();
            nextToken = RIGHT_CURLY;
            break;
        case ';':
            addChar();
            nextToken = SEMICOLON;
            break;
        case '+':
            addChar();
            nextToken = ADD_OP;
            break;
        case '-':
            addChar();
            nextToken = SUB_OP;
            break;
        case '*':
            addChar();
            nextToken = MULT_OP;
            break;
        case '/':
            addChar();
            nextToken = DIV_OP;
            break;
        case '>':
            addChar();
            nextChar = input.next().charAt(0);
            if (nextChar == '=') {
                addChar();
                nextToken = GR_EQ_THAN;
            }
            else {
                nextToken = GR_THAN;
            }
            break;
        case '<':
            addChar();
            nextChar = input.next().charAt(0);
            if (nextChar == '=') {
                addChar();
                nextToken = LS_EQ_THAN;
            }
            else {
                nextToken = LS_THAN;
            }
            break;
        case '!':
            addChar();
            nextToken = NEGATION;
            break;
        case '=':
            addChar();
            nextToken = ASSIGN_OP;
            break;
        default:
            addChar();
            nextToken = EOF;
            break;
        }
        return nextToken;
    }

    /* addChar - a function to add nextChar to lexeme */
    static void addChar() {
        if (lexLen <= 98) {
            lexeme[lexLen++] = nextChar;
            lexeme[lexLen] = 0;
        }
        else
            System.out.printf("Error - lexeme is too long \n");
    }

    /* getChar - a function to get the next character of
 input and determine its character class */
    static void getChar() {
        if (input.hasNextLine() == false) {
            nextChar = EOF;
            charClass = EOF;
        }
        else {
            nextChar = input.next().charAt(0);
            if (Character.isLetter(nextChar))
                charClass = LETTER;
            else if (Character.isDigit(nextChar))
                charClass = DIGIT;
            else charClass = UNKNOWN;
        }
    }

    static void getNonBlank() {
        while (nextChar == ' ')
            getChar();
    }

    /* lex - a simple lexical analyzer for arithmetic
 expressions */
    static int lex() {
        lexLen = 0;
        getNonBlank();
        switch (charClass) {
            /* Parse identifiers */
        case LETTER:
            addChar();
            getChar();
            while (charClass == LETTER || charClass == DIGIT) {
                addChar();
                getChar();
            }
            if (lexeme[0] == 'd' && lexeme[1] == 'o' && lexLen == 2) {	// Checks if it is a for as in a for loop
                nextToken = DO;
            }
            if (lexeme[0] == 'w' && lexeme[1] == 'h' && lexeme[1] == 'i' && lexeme[1] == 'l' && lexeme[1] == 'e' && lexLen == 5) {	// Checks if it is a for as in a for loop
                nextToken = WHILE;
            }
            else {
                nextToken = IDENT;
            }
            break;
            /* Parse integer literals */
        case DIGIT:
            addChar();
            getChar();
            while (charClass == DIGIT) {
                addChar();
                getChar();
            }
            nextToken = INT_LIT;
            break;
            /* Parentheses and operators */
        case UNKNOWN:
            lookup(nextChar);
            getChar();
            break;
            /* EOF */
        case EOF:
            nextToken = EOF;
            lexeme[0] = 'E';
            lexeme[1] = 'O';
            lexeme[2] = 'F';
            lexeme[3] = 0;
            break;
        } /* End of switch */
        System.out.printf("Next token is: %d, Next lexeme is %s\n", nextToken, lexeme);
        return nextToken;
    } /* End of function lex */

    static void error() {
        System.out.printf("Error");
        // exit(0);
    }
    
}