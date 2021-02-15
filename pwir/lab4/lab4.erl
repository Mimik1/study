%% -*- coding: utf-8 -*-
-module(lab4).
-compile([export_all]).

%Pola i objętości
pole({kwadrat,X,Y}) ->  X*Y;
pole({kolo,X}) -> 3.14*X*X;

pole({trojkat, X, Y}) -> 0.5 * X * Y;
pole({trapez, X, Y, Z}) -> 0.5 * Z * (X + Y);

pole({kula, X}) -> 4 * pole({kolo, X});
pole({szescian, X}) -> 6 * pole({kwadrat, X});
pole({stozek, X, Y}) -> (1 / 3) * Y * pole({kolo, X}).

%Długość listy
len([]) -> 0;
len([_|T]) -> 1 + len(T).

%Min max
amin([]) -> [];
amin([H|[]]) -> H;
amin([H|T]) -> find_min(H, T).
find_min(MIN, []) -> MIN;
find_min(MIN, [H|T]) -> if 
							MIN < H -> 
								find_min(MIN, T);
							true ->
								find_min(H, T) 
						end.

amax([]) -> [];
amax([H|[]]) -> H;
amax([H|T]) -> find_max(H, T).
find_max(MAX, []) -> MAX;
find_max(MAX, [H|T]) -> if 
							MAX > H -> 
								find_max(MAX, T);
							true ->
								find_max(H, T) 
						end.

tmin_max([]) -> [];
tmin_max(L) -> {amin(L), amax(L)}.

lmin_max([]) -> [];
lmin_max(L) -> [amin(L), amax(L)].

%Pola z listy elementów
pola([]) -> [];
pola([H|T]) -> [pole(H) | pola(T)].

%Lista malejąca
descending_list(1) -> [1];
descending_list(N) -> [N | descending_list(N-1)].

%Konwerter temperatur
	%c - Celsjusz
	%f - Fahrenheit
	%k - Kelvin
	%r - Rankine
temp_conv({S, T}, S) -> {S, T};
temp_conv({c, T}, S2) -> if
							S2 == f -> {f, (32+ 1.8 * T)};
							S2 == k -> {k, (273.15 + T)};
							S2 == r -> {r, (1.8 * (273.15 + T))}
						end;
temp_conv({S1, T}, S2) -> if
							S1 == f -> temp_conv({c, ((T - 32) / 1.8)}, S2);
							S1 == k -> temp_conv({c, (T - 273.15)}, S2);
							S1 == r -> temp_conv({r, ((T - 273.15) / 1.8)}, S2)
						end.

%Generator listy o zadanej długości
generator(0, _) -> [];
generator(N, E) -> [E | generator(N-1, E)].

%Sortowanie
merge_sort([]) -> [];
merge_sort([H]) -> [H];
merge_sort(List) ->	{L, R} = split(List, List, []),
    				merge(merge_sort(L), merge_sort(R)).

split([], L, R) -> {lists:reverse(R), L};
split([_], L, R) -> {lists:reverse(R), L};
split([_,_ | Counter], [H | T], Result) -> split(Counter, T, [H | Result]).
 
merge([], R) -> R;
merge(L, []) -> L;
merge([L | Front], [R | Back])->if 
									L < R ->
    									[L | merge(Front, [R | Back])];
									true ->
										[R | merge([L | Front], Back)]
								end.

bubble_sort([]) -> [];
bubble_sort([H]) -> [H];
bubble_sort(List) ->
    NewList = prepare(List),
    bubble_sort(lists:sublist(NewList, 1, length(NewList)-1)) ++ [lists:last(NewList)].

prepare([])  -> [];
prepare([F]) -> [F];
prepare([F, G | T]) -> if 
							F > G -> 
								[G | prepare([F | T])];
							true-> 
								[F | prepare([G | T])]
						end.
