% Assignment 5 - Q4
% Drew Pulliam - DTP180003
% CS4337.0U2

male(drew).
male(joseph).
male(ronn).
male(jared).
male(clent).
male(edward).
male(john).
male(frank).
male(george).
male(clark).
female(cindy).
female(nikki).
female(ashley).
female(karen).
female(nancy).
female(lucy).

parent_of(george,frank).
parent_of(frank,ronn).
parent_of(nancy,karen).
parent_of(ronn,karen).
parent_of(nancy, nikki).
parent_of(ronn, nikki).
parent_of(ronn,clark).
parent_of(nancy,clark).
parent_of(karen,ashley).
parent_of(karen,jared).
parent_of(karen,drew).
parent_of(edward,ashley).
parent_of(edward,jared).
parent_of(edward,drew).
parent_of(clent, lucy).
parent_of(nikki, joseph).
parent_of(nikki, lucy).
parent_of(clent, joseph).
parent_of(james, harry).
parent_of(ashley,john).
parent_of(ashley,cindy).
 
father_of(X,Y):- male(X),
    parent_of(X,Y).
 
mother_of(X,Y):- female(X),
    parent_of(X,Y).
 
grandfather_of(X,Y):- male(X),
    parent_of(X,Z),
    parent_of(Z,Y).

great_grandfather_of(X,Y):- male(X),
    parent_of(X,A),
    parent_of(A,B),
    parent_of(B,Y).

great_great_grandfather_of(X,Y):- male(X),
    parent_of(X,A),
    parent_of(A,B),
    parent_of(B,C),
    parent_of(C,Y).
 
grandmother_of(X,Y):- female(X),
    parent_of(X,Z),
    parent_of(Z,Y).
 
sister_of(X,Y):-
    female(X),
    father_of(F, Y), father_of(F,X),X \= Y.
 
sister_of(X,Y):- female(X),
    mother_of(M, Y), mother_of(M,X),X \= Y.
 
aunt_of(X,Y):- female(X),
    parent_of(Z,Y), sister_of(X,Z),!.
 
brother_of(X,Y):-
    male(X),
    father_of(F, Y), father_of(F,X),X \= Y.
 
brother_of(X,Y):- male(X),
    mother_of(M, Y), mother_of(M,X),X \= Y.
 
uncle_of(X,Y):-
    parent_of(Z,Y), brother_of(X,Z).
 
cousin_of(X,Y) :-
    parent_of(A,X),
    not(parent_of(A,Y)),
    parent_of(B,A),
    parent_of(B,C),
    parent_of(C,Y).
    
niece_of(X,Y):- female(X),
    parent_of(A,X),
    parent_of(B,A),
    parent_of(B,Y).
    
nephew_of(X,Y):- male(X),
    parent_of(A,X),
    parent_of(B,A),
    parent_of(B,Y).