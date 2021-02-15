-- lab6_1.adb

with Ada.Text_IO, Ada.Numerics.Discrete_Random, Ada.Numerics.Elementary_Functions;
use Ada.Text_IO, Ada.Numerics.Elementary_Functions;

procedure Lab6_1 is
	
task Klient is
  entry Start;
end Klient;

task Serwer is 
  entry Start;
	entry Koniec;
  entry Coor(X, Y:Integer);
end Serwer;

task body Klient is
  package Rand is new Ada.Numerics.Discrete_Random(Integer);
  use Rand;
  Gen: Generator;
  X: Integer := 0;
  Y: Integer := 0;
	N: Integer := 3;
begin
  accept Start;	
  for K in 1..N loop
		reset(gen);
	  X := (Random(Gen) mod 10);
	  Y := (Random(Gen) mod 10);
	  Put_Line("A:  (" & X'Img & " ," & Y'Img &" )");
	  Serwer.Coor(X, Y);
  end loop;
	Serwer.Koniec;	
end Klient;

task body Serwer is
  Dist1: Float := 0.0;
  Dist2: Float := 0.0;
 	Xa: Integer := 0;
	Ya: Integer := 0;	
	Xb: Integer := 0;
 	Yb: Integer :=0;	
begin
  accept Start;
  loop
    select 
			accept Coor(X, Y: in Integer) do
				Xb := X;
				Yb := Y;
	    	Dist1 := Sqrt(Float( Xb**(2) + Yb**(2)));
				Dist2 := Sqrt(Float((abs(Xa - Xb))**(2) + (abs(Ya - Yb))**(2)));
			end Coor;
	  	Put_Line("B:  Distance between (0, 0) and (" & Xb'Img & " ," & Yb'Img &" ) =" & Dist1'Img);
			Put_Line("B:  Distance between (" & Xa'Img & " ," & Ya'Img &" ) and (" & Xb'Img & " ," & Yb'Img &" ) =" & Dist2'Img    );
			Xa := Xb;
			Ya := Yb;
    or 
			accept Koniec;
				exit;
		end select;
  end loop;
  
end Serwer;

begin
  Klient.Start;
  Serwer.Start;  
end Lab6_1;
	  	
