% Assignment 5 - Q5
% Drew Pulliam - DTP180003
% CS4337.0U2

color_counties(Colors):-
        setof(County/_, X^ngb(County,X), Colors),
        colors(Colors).

colors([]).
colors([County/Color|Rest]):-
        colors(Rest),
        member(Color, [yellow,blue,red,green]),
        \+ (member(County1/Color, Rest), neighbour(County, County1)).

neighbour(County, County1):-
        ngb(County, Neighbours),
        member(County1, Neighbours).


ngb(walker, [montgomery]).
ngb(montgomery, [walker,liberty,harris,waller]).
ngb(liberty, [chambers,harris,montgomery]).
ngb(harris, [montgomery,waller,liberty,chambers,galveston,brazoria,fort_bend]).
ngb(chambers, [liberty,harris]).
ngb(galveston, [harris,brazoria]).
ngb(brazoria, [galveston,harris,fort_bend,matagorda,wharton]).
ngb(fort_bend, [wharton,brazoria,harris,waller,austin]).
ngb(matagorda, [brazoria,wharton]).
ngb(wharton, [matagorda,brazoria,fort_bend,austin,colorado]).
ngb(colorado, [wharton,austin]).
ngb(austin, [colorado,wharton,fort_bend,waller]).
ngb(waller, [austin,fort_bend,harris,montgomery]).

member(X, [X|_]).
member(X, [_|T]):-
        member(X, T).