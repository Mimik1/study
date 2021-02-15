-- lab6_2.adb

with Ada.Text_IO, Ada.Numerics.Discrete_Random;
use Ada.Text_IO;

procedure Lab6_2 is
	
task Zadanie_A is
	entry Start;
end Zadanie_A;  

task Zadanie_B is
	entry Start;
end Zadanie_B;

function GenericF(I:Integer) return Float is
     subtype Num_Type is Integer range 0 .. 5;
     package Random_Num is new Ada.Numerics.Discrete_Random(Num_Type);
     use Random_Num;
     X : Float := 0.0;
     Gen: Generator;
begin
   Reset(Gen);
   X := Float(Random(Gen));
   return X;
end GenericF;

type Color is (Czerwony, Zielony, Niebieski);

function GenericColor(I:Integer) return Color is
	--type Kolory is (Czerwony, Zielony, Niebieski);
  package Los_Color is new Ada.Numerics.Discrete_Random(Color);
  use Los_Color;

  X : Color;
  Gen: Generator; -- z pakietu Los_Kolor
begin
	Reset(Gen);
	X := Random(Gen);
	return X;
end GenericColor;

function GenericI(I:Integer) return Integer is
      subtype Num_Type is Integer range 0 .. 49;
      package Random_Num is new Ada.Numerics.Discrete_Random(Num_Type);
			use Random_Num;
			X : Integer := 0;
  		Gen: Generator;
begin
 Reset(Gen);
 X := Random(Gen);
 return X;
end GenericI;

type Day is (poniedzialek, wtorek, sroda, czwartek, piatek, sobota, niedziela);

function GenericDay(I:Integer) return Day is
  package Los_Day is new Ada.Numerics.Discrete_Random(Day);
  use Los_Day;
 
  X : Day;
  Gen: Generator;
begin
  Reset(Gen);
  X := Random(Gen);
   return X;
end GenericDay;


task body Zadanie_A is	
begin
  accept Start;
	  Put_Line("A:  " & GenericF(1)'Img);
end Zadanie_A;

task body Zadanie_B is	
begin
  accept Start; 
		Put("B:  " & GenericF(1)'Img &" "& GenericColor(1)'Img &" "& GenericDay(1)'Img);
		for I in 1..6 loop
			Put(" " & GenericI(1)'Img);
		end loop;
end Zadanie_B;

begin
  Zadanie_A.Start;
  Zadanie_B.Start; 
end Lab6_2;
