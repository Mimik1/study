-- lab2.adb
 
with Ada.Text_IO, Ada.Float_Text_IO, Ada.Calendar, Fun;
use Ada.Text_IO, Ada.Float_Text_IO, Ada.Calendar, Fun;

procedure Lab2 is
	Agr : Vector := (1..3 => 1.0, 4..6 =>1.5);
	
	T1, T2: Time;
	D: Duration;
begin
	T1:= Clock;

	Put_Line("Agregat początkowy:");
	fun.Print(Agr);

	fun.Random_insert(Agr);
	Put_Line("Agregat z losowymi wartosciami:");
	fun.Print(Agr);

	Put_Line("Czy Agregat jest posortowany:");
	Put_Line(fun.Is_sorted(Agr)'Img);

	Put_Line("Agregat z posortowanymi wartosciami:");
	fun.Sort(Agr);
	fun.Print(Agr);
	Put_Line("Czy Agregat jest posortowany:");
	Put_Line(fun.Is_sorted(Agr)'Img);

	T2:= Clock;
	D:= T2 - T1;
	Put_Line("Czas wykonywania to " & D'Img & " s");
	
end Lab2;
