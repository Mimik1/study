-- Tutaj ma się znaleźć zadanie numer 3. 
-- Trzy operacje oparte o monadę State
-- 1. insert
-- 2. search
-- 3. remove
-- Proszę dopisać w Main jakiś przykład użycia operacji w notacji `do`

import Control.Monad.State 
data Tree a = Empty | Node a (Tree a) (Tree a)
	deriving (Show, Eq)
----------------------------------------
insert :: Ord a => a -> Tree a ->  Tree a
insert el Empty = Node el Empty Empty
insert el (Node a left right)
	| el == a	= Node el left right
	| el < a	= Node a (insert el left) right
	| el > a	= Node a left (insert el right)

insert_state :: (Ord a) => a -> State (Tree a) ()
insert_state a = state $ \f -> ((), insert a f)
----------------------------------------
search :: Ord a => a -> Tree a -> Bool
search el Empty = False
search el (Node a left right)
	| el == a	= True
	| el < a	= search el left
	| el > a	= search el right

search_state :: (Ord a) => a -> State (Tree a) Bool
search_state a = state $ \f -> (search a f, f)
----------------------------------------
rebuilt :: (Ord a) => Tree a -> Tree a -> Tree a
rebuilt a Empty = a
rebuilt Empty a = a
rebuilt (Node a leftA rightA) (Node b leftB rightB) 
        | a == b = 	Node a leftA 					rightA
        | a < b = 	Node a leftA 					(rebuilt rightA (Node b leftB rightB))
        | a > b = 	Node a (rebuilt leftA (Node b leftB rightB))	rightA
remove :: (Ord a) => Tree a -> a -> Tree a
remove (Node a left right) x
        | a < x = 	Node a left 					(remove right x)
        | a > x = 	Node a (remove left x) 				right
remove (Node a Empty right) x = right
remove (Node a left Empty) x = left
remove (Node a left right) x = rebuilt left right
remove Empty a = Empty

remove_state :: (Ord a) => a -> State (Tree a) a
remove_state a = state $ \f -> (a, remove f a)
----------------------------------------
myTree :: Tree Integer
myTree = Node 3 (Node 1 Empty (Node 2 Empty Empty)) (Node 4 Empty Empty)
emptyTree :: Tree Integer
emptyTree = Empty

test = do
	insert_state 1
	insert_state 2
	insert_state 3
	remove_state 2
	search_state 2

