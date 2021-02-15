import Control.Applicative
 
data Tree a = Leaf a | Node (Tree a) (Tree a)
              deriving (Show)
 
instance Functor Tree where
   fmap f (Leaf x) = Leaf (f x)
   fmap f (Node x y) = Node (fmap f x) (fmap f y)
 
instance Applicative Tree where
   pure x = Leaf x
   (Leaf f) <*> (Leaf x) = Leaf (f x)
   (Leaf f) <*> (Node x y) = Node (fmap f x) (fmap f y)
 
instance Monad Tree where
   return x = Leaf x
   (Leaf x) >>= f = f x
   (Node x y) >>= f = Node (x >>= f) (y >>= f)


myTree = Node (Leaf 3) (Node (Leaf 4) (Leaf 5))
