% Assignment 5 - Q2
% Drew Pulliam - DTP180003
% CS4337.0U2

elements(List, Total) :-
    flatten(List, FlatList),
    length(FlatList, Total).