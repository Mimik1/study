find :: (a -> Bool) -> [a] -> Maybe a
find fun (h:t) 
    | (fun h == True) = 	Just x
    | (length t == 0) = 	Nothing
    | otherwise = 		find fun t



