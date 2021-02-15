
head' (x:[_]) = x

length' (x:[]) = 1
length' (x:y) = 1+length' (y)

take' 0 list = []
take' n (x:y) 
	| n <= (length' (x:y)) = (x) : (take' (n-1) y)
	| n > (length' (x:y)) =	take' (length' (x:y)) (x:y)

map' f [] = []
map' f (x:y) = (f x) : (map' f y)

(+++) x y = x+y
