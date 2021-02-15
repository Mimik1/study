data Osoba = Osoba
        { imie          :: String
        , nazwisko      :: String
        , pesel         :: String
        } deriving (Show)

instance Eq Osoba where
        x == y  = 	nazwisko x == nazwisko y
instance Ord Osoba where
	compare x y = 	compare (read( pesel x)::Float) (read( pesel y)::Float)
--     	x < y =		(read( pesel x)::Float) < (read( pesel y)::Float)
--      x > y = 	(read( pesel x)::Float) > (read( pesel y)::Float)
--      x <= y = 	(read( pesel x)::Float) <= (read( pesel y)::Float)
--      x >= y = 	(read( pesel x)::Float) >= (read( pesel y)::Float)

szymon = Osoba "Szymon" "Bobek" "12345678901"
bobek = Osoba "S" "Bobek"  "12345678901"
zenon = Osoba "Zenon" "Adamczyk" "111111111"

