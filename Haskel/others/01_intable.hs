{-# LANGUAGE FlexibleInstances #-}
class Intable a where
	toInt :: a -> Int
instance Intable [Char] where
	toInt x = read x ::Int
instance Intable Int where
	toInt x = x

mySuperAdd :: (Intable a, Intable b) => a -> b -> Int
mySuperAdd x y = toInt x + toInt y
