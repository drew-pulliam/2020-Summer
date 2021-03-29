
Implementation important:
To test, hardcode the location of the file into the program.

This program iterates through a for loop using recursive decent programing in order to break it down using the associated EBNF rules.
<forLoop> -> for(<Assign>,<cond><assign>)'{'{<assign>;}'}'
<assign> -> id (=<expr>|++|--)
<cond> -> <expr> (>|>=|<|<=|!=|==) <expr>
<expr> -> <term> {(+ | -) <term>}
<term> -> <factor> {(* | /) <factor>)
<factor> -> id | int_constant | ( <expr )
In order to do so Lexical analysis is preformed to break the code down into understandable tokens to be operated on.