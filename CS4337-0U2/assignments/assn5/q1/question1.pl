% Assignment 5 - Q1
% Drew Pulliam - DTP180003
% CS4337.0U2

append([], List, List).
append([Head | List_1], List_2, [Head | List_3]) :-
    append(List_1, List_2, List_3).

reverse([],[]).    % the empty list is its own reverse. Base for induction.
reverse([H|T], Rev) :-
    reverse(T, Trev), append(Trev, [H], Rev).

reverseandappendlist(List_1, List_2) :-
	reverse(List_1, Rev), append(List_1, Rev, List_2).