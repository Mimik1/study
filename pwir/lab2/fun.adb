-- fun.adb

with Ada.Text_IO, Ada.Float_Text_IO, Ada.Numerics.Float_Random;
use Ada.Text_IO, Ada.Float_Text_IO, Ada.Numerics.Float_Random;

package body Fun is
	Gen : Generator;

	procedure Print(V: Vector) is
	begin
		for I in V'Range loop
		    Put_Line("Agregat["&I'Img &" ] =" & V(I)'Img);
		end loop;
	end Print;

	procedure Random_insert(V: out Vector) is
	begin
		for I in V'Range loop
		    V(I) := Random(Gen);
		end loop;
	end Random_insert;
	
	function Is_sorted(V: Vector) return Boolean is
	begin
		return (for all I in (V'First) .. (V'Last - 1) => V(I + 1) >= V(I));
	end Is_sorted;
	
	procedure Sort(V: in out Vector) is

		procedure Swap(L, R : Integer) is
			Tmp : Float := V(L);
		begin
			V(L) := V(R);
			V(R) := Tmp;
		end Swap;
	begin
		if V'Length >1 then
			for I in V'Range loop
				for J in I .. V'Last loop
					if V(I) > V(J) then
						Swap(I, J);
					end if;
				end loop;
			end loop;
		end if;
	end Sort;
		
end Fun;
