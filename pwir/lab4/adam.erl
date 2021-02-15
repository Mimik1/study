%% -*- coding: utf-8 -*-
-module(adam).
-compile([export_all]).

% Dodaj do poniższego modułu:
pole({prostokat,A,B}) ->  A*B;
pole({kolo,R}) -> 3.14*R*R;
% obsługę trójkątów i trapezów (obliczanie pola),
pole({trojkat,A,H}) ->  0.5*A*H;
pole({trapez,A,B,H}) -> 0.5*(A+B)*H;
% obsługę figur 3-wymiarowych (pole kuli, sześcianu, stożka oraz funkcje do obliczania ich objętości).
pole({szescian,A}) -> 6*pole({prostokat,A,A});
pole({kula,R}) ->  4*pole({kolo,R});
pole({stozek,R,H}) ->  0.333*pole({kolo,R})*H.

% Napisz program liczący długość listy (len/1).
len([]) -> 0;
len([_|T]) -> 1 + len(T).

% Napisz program podający najmniejszy element listy (amin/1).
amin([]) -> "empty list";
amin([H|[]]) -> H;
amin([H|T]) -> amin(H, T).

amin(Min, []) -> Min;
amin(Min, [H|T]) -> if Min < H -> amin(Min, T);
			true -> amin(H, T) end.

% Napisz program podający największy element listy (amax/1).
amax([]) -> "empty list";
amax([H|[]]) -> H;
amax([H|T]) -> amax(H, T).

amax(Max, []) -> Max;
amax(Max, [H|T]) -> if Max > H -> amax(Max, T);
			true -> amax(H, T) end.

% Napisz program zwracający krotkę 2-elementową z najmniejszym i największym elementem listy (tmin_max/1).
tmin_max([]) -> "empty list";
tmin_max(L) -> {amin(L), amax(L)}.

% Napisz program zwracający listę 2-elementową z najmniejszym i największym elementem listy (lmin_max/1).
lmin_max([]) -> "empty list";
lmin_max(L) -> [amin(L), amax(L)].

% Napisz program wyliczający pola figur/brył podanych jako lista krotek. Zwracana ma być lista pól. 
pola([]) -> [];
pola([H|T]) -> [pole(H)] ++ pola(T).

% Napisz program, który dla danego N zwróci listę formatu [N,N-1,…,2,1]. 
dec_list(1) -> [1];
dec_list(N) -> [N|dec_list(N-1)].

% Napisz konwerter temperatury pomiędzy różnymi skalami (minimum 4). Temperatura podawana jest jako krotka {typ, wartość} np. {c, 22.4}, {f,0.0}. Funkcja konwertująca ma przyjmować 2 parametry: krotkę reprezentującą temperaturę oraz skalę docelową np. temp_conv({c,22.4},k). Wartością zwracaną ma być odpowiednia krotka np. {k,233.47}. 

% Napisz program generujący listę jedynek o zadanej długości. Napisz program generujący listę o podanej długości składającą się z podanego elementu.
generuj(0, _) -> [];
generuj(N, E) -> E ++ generuj(N-1, E).

% Napisz program sortujący listę rekurencyjnie przez scalanie.
msort([]) -> [];
msort([H]) ->
    [H];
msort(List) ->
    {Front, Back} = split(List),
    merge(msort(Front), msort(Back)).

split(List) ->
    split(List, List, []).
split([], Back, Front) ->
    {lists:reverse(Front), Back};
split([_], Back, Front) ->
    {lists:reverse(Front), Back};
split([_,_ | Counter], [H | T], Result) ->
    split(Counter, T, [H | Result]).
 
merge([], Back) ->
    Back;
merge(Front, []) ->
    Front;
merge([L | Front], [R | Back]) when L < R ->
    [L | merge(Front, [R | Back])];
merge([L | Front], [R | Back]) ->
    [R | merge([L | Front], Back)].
    
% Napisz program sortujący listę metodą bąbelkową.
bubble_sort(L) when length(L) =< 1 ->
    L;
bubble_sort(L) ->
    SL = bubble_sort_p(L),
    bubble_sort(lists:sublist(SL,1,length(SL)-1)) ++ [lists:last(SL)].

bubble_sort_p([])  ->
    [];
bubble_sort_p([F]) ->
    [F];
bubble_sort_p([F,G|T]) when F > G ->
    [G|bubble_sort_p([F|T])];
bubble_sort_p([F,G|T]) ->
    [F|bubble_sort_p([G|T])].

