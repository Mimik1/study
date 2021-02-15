% lab8_1
-module(lab8_1).
-compile([export_all]).

konsument() ->
	receive
		{Od, koniec} ->
			Od!{konsument, koniec},
			{koniec, konsument};

                {Od, L}->
			io:format("k:dostalem ~w~n", [L]),
			konsument()
        end.

posrednik() ->
	receive
		{Od, {K, koniec}}->
			K!{Od, koniec},
			{koniec, posrednik};

		{Od, {K, L}}->
			io:format("p: wysylam ~w~n", [L]),
			K!{self(), L},
			posrednik()
	end.

producent_start(0, P, K, S) ->
	P!{S, {K, koniec}};
producent_start(N, P, K, S) when N > 0 ->
	P!{S, {K, rand:uniform(10)}},
	producent_start(N-1, P, K, S).
producent(N) ->
	Ppid = spawn(lab8_1, posrednik, []),
	Kpid = spawn(lab8_1, konsument, []),
	producent_start(N, Ppid, Kpid, self()),
	receive
		{_, koniec} ->ok
	end,
	{producent, koniec}.
