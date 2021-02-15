import Control.Applicative

data Tree a = Empty | Leaf a | Node (Tree a) (Tree a)
	deriving (Show)

instance Functor Tree where
      fmap f Empty        = Empty
      fmap f (Leaf x)     = Leaf (f x)
      fmap f (Node l r) = Node (fmap f l) (fmap f r)

instance Applicative Tree where
   pure x = Leaf x
   (Leaf f) <*> (Leaf x) = Leaf (f x)
   (Leaf f) <*> (Node l r) = Node (fmap f l) (fmap f r)
   _ <*> _ = Empty

instance Monad Tree where
	return=Leaf
	x >>= f= mergeTrees(fmap f x)

mergeTrees:: Tree (Tree a) -> Tree a
mergeTrees Empty = Empty
mergeTrees (Leaf x) = x
mergeTrees (Node l r) = Node (mergeTrees l) (mergeTrees r)

insert :: Ord a => a -> Tree a ->  Tree a
insert x Empty = Node x Empty
insert x (Node v Empty) = Node v (Node x Empty) 
insert x Node u (Node d Empty) = 
	| d >= u && x <= u = Node u (Node (Node x Empty) (Node d Empty) ) 
	| d >= u && x > u = Node u (Node (Node x Empty) (Node d Empty) )
myTree = Node 
			(Leaf 3)
			(Node
				(Node 
					(Leaf 1)
					(Node
						(Empty)
						(Node 
							(Leaf 2 )
							(Empty)
						)
					)
				)
				(Node
					(Leaf 4) 
					(Empty)
				)
			)

