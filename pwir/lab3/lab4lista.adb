with Ada.Text_IO, Ada.Integer_Text_IO, Ada.Numerics.Discrete_Random;
use Ada.Text_IO, Ada.Integer_Text_IO;

procedure Lab4Lista is

type Element is
  record 
    Data : Integer := 0;
    Next : access Element := Null;
  end record; 

type Elem_Ptr is access all Element;

procedure Print(List : access Element) is
  L : access Element := List;
begin
  if List = Null then
    Put_Line("List EMPTY!");
  else
    Put_Line("List:"); 
  end if; 
  while L /= Null loop
    Put(L.Data, 4); -- z pakietu Ada.Integer_Text_IO
    New_Line;
    L := L.Next;
  end loop; 
end Print;

procedure Insert(List : in out Elem_Ptr; D : in Integer) is
  E : Elem_Ptr := new Element; 
begin
  E.Data := D;
  E.Next := List;
  -- lub E.all := (D, List);
  List := E;
end Insert;

-- wstawianie jako funkcja - wersja krótka
function Insert(List : access Element; D : in Integer) return access Element is 
  ( new Element'(D,List) ); 


-- do napisania !!
-- 1
procedure Insert_Sort(List : in out Elem_Ptr; D : in Integer) is
	E : Elem_Ptr := new Element;
	L : Elem_Ptr := List;

begin
	E.all := (D,List);

	--empty list
	if List = null then
		List := E;
		return;
	end if;

	--D is the smallest
	if L.Data > D then
		E.Next := List;
		List := E;
		return;
	end if;

	--D is somewhere in the middle of the list
	while L.Next /= null loop
		if L.Next.Data > D then
			E.Next := L.Next;
			L.Next := E;
			return;
		end if;
		L := L.Next;
	end loop;

	--D is the biggest
	E.Next := L.Next;
	L.Next := E;

end Insert_Sort;

--2
procedure Add_Randoms(List : in out Elem_Ptr; N : in Integer; M : in Integer) is
	package Rand is new Ada.Numerics.Discrete_Random(Integer);
	use Rand;
	Gen: Generator;
	D: Integer := 0;
begin
	Reset(Gen);

	for I in 0..N loop
    D := (Random(Gen) mod (M+1));
		Insert_Sort(List, D); 
	end loop;
end Add_Randoms;

--3
function Search(List : in out Elem_Ptr; D : in Integer) return Boolean is
	L : Elem_Ptr := List;
begin
	--empty list
	if List = null then
		return false;

	--normal search
	else
		while L /= null loop
			if L.Data = D then
				return true;
			end if;
			L := L.Next;
		end loop;
	end if;

	--not find
	return false;
end Search;

--4
procedure Delete(List : in out Elem_Ptr; D : in Integer) is
	L : Elem_Ptr := List.Next;
	P : Elem_Ptr := List;
begin
	--empty list
	if List = null then
		return;
	end if;
	
	--D is first
	if P.Data = D then
		List := List.Next;
		return;
	end if;

	--delate if find
	while L /= null loop
		if L.Data = D then
			P.Next := L.Next;
			return;
		end if;
		P := L;
		L := L.Next;
	end loop;
end Delete;

Lista : Elem_Ptr := Null;

begin
  Print(Lista);
  Lista := Insert(Lista, 21);
  Print(Lista);
  Insert(Lista, 20); 
  Print(Lista);
  for I in reverse 1..12 loop
  Insert(Lista, I);
  end loop;
  Print(Lista);
	
	--Insert_Sort(Lista, 9);
	--Print(Lista);

	--Add_Randoms(Lista, 2, 20);
	--Print(Lista);

	--Put_Line("" & Search(Lista, 21)'Img);

	--Delete(Lista, 10);
	--Print(Lista);
end Lab4Lista;
