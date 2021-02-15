-module(lab5).
-compile([export_all]).

%wstawianie elementu do drzewa
insert(X, nil) -> {X, nil, nil};
insert(X, {Y, Left, Right}) when X < Y -> {Y, insert(X, Left), Right};
insert(X, {Y, Left, Right}) when X >= Y -> {Y, Left, insert(X, Right)}.

%generacja losowego drzewa (liczby)
random(Tree, Max) -> insert(rand:uniform(Max), Tree).

insertRandom(Tree, 0, _) -> Tree;
insertRandom(Tree, Size, Max) -> insertRandom(random(Tree, Max), Size-1, Max).

%generacja drzewa z listy
listToTree([], Tree) -> Tree;
listToTree([H|T], Tree) -> listToTree(T, insert(H, Tree)).

%zwinięcie drzewa do listy (3 dowolne metody)

	%Inorder
treeToListInorder(nil) -> [];
treeToListInorder({X, Left, Right}) -> treeToListInorder(Left) ++ [X] ++ treeToListInorder(Right).

	%Preorder
treeToListPreorder(nil) -> [];
treeToListPreorder({X, Left, Right}) -> [X] ++ treeToListPreorder(Left) ++ treeToListPreorder(Right).

	%Postorder
treeToListPostorder(nil) -> [];
treeToListPostorder({X, Left, Right}) -> treeToListPostorder(Left) ++ treeToListPostorder(Right) ++ [X].

%szukanie elementu w drzewie (wersja „zwyczajna” i wersja „wyjątkowa”)

	%zwyczajna
search(_, nil) -> false;
search(X, {X, _, _}) -> true;
search(X, {Y, Left, Right}) -> if
				      X < Y ->
					      search(X, Left);
				      true ->
					      search(X, Right)
			      end.

	%wyjątkowa
searchThrow(_, nil) ->  throw(false);
searchThrow(X, {X, _, _}) -> throw(true);
searchThrow(X, {Y, Left, Right}) -> if
                                       X < Y ->
                                               searchThrow(X, Left);
                                       true ->
     						    searchThrow(X, Right)
 	                             end.

searchException(X, Tree) -> try
				    searchThrow(X, Tree)
			    catch
				    throw:true -> 'true';
				    throw:false -> 'false'
			    end.

