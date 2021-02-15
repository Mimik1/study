%  sort1.erl
-module(sort1).
-compile([export_all]).

get_mstimestamp() ->
  {Mega, Sec, Micro} = os:timestamp(),
  (Mega*1000000 + Sec)*1000 + round(Micro/1000).



merge(L1, L2)    -> merge(L1, L2, []).
merge([], L2, A) -> A++L2;
merge(L1, [], A) -> A++L1;
merge([H1|T1], [H2|T2], A) when H2>=H1 -> merge(T1, [H2|T2], A++[H1]);
merge([H1|T1], [H2|T2], A) when H1>H2  -> merge([H1|T1], T2, A++[H2]).


sorts([L]) -> [L];
sorts(L) -> 
  {L1,L2} = lists:split(length(L) div 2, L),
    merge(sorts(L1), sorts(L2)).


receive_results(Ref, L1, L2) ->
    receive
        {l1, Ref, L1R} when L2 == undefined -> receive_results(Ref, L1R, L2);
        {l2, Ref, L2R} when L1 == undefined -> receive_results(Ref, L1, L2R);
        {l1, Ref, L1R} -> {L1R, L2};
        {l2, Ref, L2R} -> {L1, L2R}
    after 5000 -> receive_results(Ref, L1, L2)
    end.

sortw([L],_) -> [L]; 
sortw(L, N) when N > 1  -> 
    {L1,L2} = lists:split(length(L) div 2, L),
    {Parent, Ref} = {self(), make_ref()},
    spawn(fun()-> Parent ! {l1, Ref, sortw(L1, N-2)} end), 
    spawn(fun()-> Parent ! {l2, Ref, sortw(L2, N-2)} end), 
    {L1R, L2R} = receive_results(Ref, undefined, undefined),
    lists:merge(L1R, L2R);
sortw(L, _) -> {L1,L2} = lists:split(length(L) div 2, L), lists:merge(sortw(L1, 0), sortw(L2, 0)).

gensort() ->
 L=[rand:uniform(5000)+100 || _ <- lists:seq(1, 25339)],	
 Lw=L,
 io:format("Liczba elementów = ~p ~n",[length(L)]),
 
 io:format("Sortuję sekwencyjnie~n"),	
 TS1=get_mstimestamp(),
 sorts(L),
 DS=get_mstimestamp()-TS1,	
 io:format("Czas sortowania ~p [ms]~n",[DS]),
 io:format("Sortuję współbieżnie~n"),	
 TS2=get_mstimestamp(),
 sortw(Lw, erlang:system_info(schedulers)),
 DS2=get_mstimestamp()-TS2,	
 io:format("Czas sortowania ~p [ms]~n",[DS2]).
 
 
