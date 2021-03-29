% Assignment 5 - Q6
% Drew Pulliam - DTP180003
% CS4337.0U2

% dct = deaths,cases,tests

dct(clinton,4,95,2773).
dct(essex,0,36,2214).
dct(franklin,0,20,2651).
dct(fulton,19,196,2527).
dct(hamilton,1,5,411).
dct(herkimer,3,103,2691).
dct(lewis,5,20,1771).
dct(oneida,45,917,14310).
dct(saratoga,14,463,11178).
dct(st_lawrence,2,197,7966).
dct(warren,27,251,4079).
dct(washington,14,228,2766).


smallest_cases(County) :-
    smallest_cases([clinton,essex,franklin,fulton,hamilton,herkimer,
           lewis,oneida,saratoga,st_lawrence,warren,washington],County).
smallest_cases([Min],Min).
smallest_cases([H,K|T], County) :-
    dct(H,_,Case1,_),
    dct(K,_,Case2,_),
    Case1 > Case2,
    smallest_cases([K|T],County).
smallest_cases([H,K|T], County) :-
    dct(H,_,Case1,_),
    dct(K,_,Case2,_),
    Case1 =< Case2,
    smallest_cases([H|T],County).

largest_deaths(County) :-
    largest_deaths([clinton,essex,franklin,fulton,hamilton,herkimer,
           lewis,oneida,saratoga,st_lawrence,warren,washington],County).
largest_deaths([Min],Min).
largest_deaths([H,K|T], County) :-
    dct(H,Case1,_,_),
    dct(K,Case2,_,_),
    Case1 < Case2,
    largest_deaths([K|T],County).
largest_deaths([H,K|T], County) :-
    dct(H,Case1,_,_),
    dct(K,Case2,_,_),
    Case1 >= Case2,
    largest_deaths([H|T],County).

total_tests(TotalTests) :- 
    total_tests([clinton,essex,franklin,fulton,hamilton,herkimer,
           lewis,oneida,saratoga,st_lawrence,warren,washington],TotalTests).

total_tests([],0).
total_tests([H|T],TotalTests) :-
    total_tests(T,Rest),
    dct(H,_,_,Tests),
    TotalTests is (Rest + Tests).
    
larger_deaths(County, Var) :-
    dct(County,Deaths1,_,_),
    dct(Var,Deaths2,_,_),
	Deaths2 > Deaths1.

larger_cases_and_deaths(County, Var) :-
    dct(County,Deaths1,Cases1,_),
    dct(Var,Deaths2,Cases2,_),
	Deaths2 > Deaths1,
    Cases2 > Cases1.

larger_cases_or_deaths(County, Var) :-
    dct(County,Deaths1,_,_),
    dct(Var,Deaths2,_,_),
	Deaths2 > Deaths1;
    dct(County,_,Cases1,_),
    dct(Var,_,Cases2,_),
    Cases2 > Cases1.

average_deaths(AverageDeaths) :-
    total_deaths([clinton,essex,franklin,fulton,hamilton,herkimer,
           lewis,oneida,saratoga,st_lawrence,warren,washington],TotalDeaths),
    AverageDeaths is TotalDeaths / 12.

total_deaths([],0).
total_deaths([H|T],TotalDeaths) :-
    total_deaths(T,Rest),
    dct(H,Deaths,_,_),
    TotalDeaths is (Rest + Deaths).






