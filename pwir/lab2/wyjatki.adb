--wyjatki.adb

with Ada.Text_IO;
use Ada.Text_IO;

procedure Print is
	F: File_Type;
	F_n: String(1..100) := (others => ' ');
	F_l: Integer := 0;
	Tmp: Character;
begin
	loop
		Put_Line("File name:");
		Get_Line(F_n, F_l);
	begin
		Open(F, In_File, F_n(1..F_l));
		exit;
		exception
			when Name_Error => Put_Line("Error");
		end;
	end loop;
	
	while not End_OF_File (F) loop
		Get(F, Tmp);
		Put(Tmp);
	end loop;
	Close(F);
end Print;
