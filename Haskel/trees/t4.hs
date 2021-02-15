pierwsze :: [Int] -> [Int]
pierwsze x = filter (\y -> y `mod` 2 == 1) x
