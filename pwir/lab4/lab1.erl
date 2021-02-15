%% -*- coding: utf-8 -*-
-module(lab4).
-compile([export_all]).

%Pola i objętości
pole({kwadrat,X,Y}) ->  X*Y;
pole({kolo,X}) -> 3.14*X*X;

pole({trojkat, X, Y}) -> 0.5 * X * Y;
pole({trapez, X, Y, Z}) -> 0.5 * Z * (X + Y).
