data Tree a = Empty | Node a (Tree a) (Tree a)
	deriving (Show)

insert :: Ord a => a -> Tree a ->  Tree a
insert el Empty = Node el Empty Empty
insert el (Node a left right)
--	| el == a	= Node el left right
	| el <= a	= Node a (insert el left) right
	| el > a	= Node a left (insert el right)

empty :: Tree a -> Bool
empty  Empty = True
empty  (Node a left right) = False

search :: Ord a => a -> Tree a -> Bool
search el Empty = False
search el (Node a left right)
	| el == a	= True
	| el < a	= search el left
	| el > a	= search el right

toString :: Show a =>Tree a -> [Char]
toString Empty = ""
toString (Node a left right) =(show a) ++ "(" ++ toString left ++ "," ++ toString right ++ ")"

leavesNum :: Num a => Tree a -> Int
leavesNum Empty = 0
leavesNum (Node a Empty Empty) = 1
leavesNum (Node a left right) = leavesNum left + leavesNum right

leaves :: Num a => Tree a -> [a]
leaves Empty = []
leaves (Node a Empty Empty) = [a]
leaves (Node a left right) = (leaves left) ++ (leaves right)

nnodes :: Num a => Tree a -> Int
nnodes Empty = 0
nnodes (Node a left right) = 1 + nnodes left + nnodes right

nsum :: Num a => Tree a -> a
nsum Empty = 0
nsum (Node a left right) = a + nsum left + nsum right

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

myTree :: Tree Int
myTree = Node 3 (Node 1 Empty (Node 2 Empty Empty)) (Node 4 Empty Empty)
myTree2 :: Tree Int
myTree2 = Node 3  (Node 1 Empty Empty) (Node 4 Empty (Node 5 Empty Empty))
emptyTree = Empty
