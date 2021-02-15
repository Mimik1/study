import Debug.Trace                                                    -- importuje wszystkie funkcje z modułu Debug.Trace
quicksort [] = []                                                     -- definiuje zachowanie na pustej liście 
quicksort xx@(x:xs)                                                   -- @ inforumuje żeby zmienna xx była widoczna jako całość i jako poszczególne części x i xs
                | trace ("qs: "++ show x ++ " " ++show xx) False = [] -- zastosowano tu składnie | case = execute | case2 = execute2, ponieważ trace zwróci False ta gałąź się nigdy nie wykona, ale zostanie wypisana wiadomość na stderr
                | otherwise = quicksort low ++ x : quicksort high     -- otherwise to nic innego jak True, można nawet sprawdzić True == otherwise w GHCi ta gałąź się wykona w pozostałych przypadkach (w tym wypadku zawsze), następuje konkatenacja listy posortowanej quicksortem z listą składającą się z elementu rodzielającego i listy posortowanej quicksortem
                              where low = [e | e <- xs, e < x]        -- w bloku where następują definicje zmiennych lokalnych low to podlista xs z wartościami mniejszymi od strażnika x
                                    high = [e | e <- xs, e >= x]      -- natomiast high to lista w z wartościami większymi lub równymi strażnikowi 
