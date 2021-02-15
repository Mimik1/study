with Ada.Text_IO, Ada.Integer_Text_IO, Ada.Numerics.Discrete_Random;
use Ada.Text_IO, Ada.Integer_Text_IO;

procedure Lab4Tree is

type Node is
  record
     Data: Integer := 0;
     Left : access Node := Null;
     Right: access Node := Null;
  end record;

type Node_Ptr is access all Node;

type Vektor is array (Integer range <>) of Integer;

procedure Pre_Order(Tree: access Node) is
begin
  Put(Tree.Data,4);
  New_Line;
  if (Tree.Left /= Null) then
     Pre_Order(Tree.Left);
  end if;
  if (Tree.Right /= Null) then
        Pre_Order(Tree.Right);
  end if;
end Pre_Order;


procedure Print(Tree: access Node ) is
  T : access Node := Tree;
begin
  if T = Null then
     Put_Line("Tree EMPTY!");
     return;
  else
     Put_Line("Tree:");
  end if;
     Pre_Order(T);
end Print;

procedure Insert(Tree: in out Node_Ptr; D: in Integer) is
   E : Node_Ptr := new Node;
begin
  if (Tree = Null) then
     E.all := (D, Null, Null);
     Tree := E;
     return;
  end if;

  if (D < Tree.Data) then
     Insert(Tree.Left, D);
  else
     Insert(Tree.Right, D);
  end if;
end Insert;


procedure Add_Random(Tree: in out Node_Ptr; M: in Integer; N: in Integer) is
  subtype Num_Type is Integer range 0 .. M;
  package Rand is new Ada.Numerics.Discrete_Random(Integer);
  use Rand;
  D : Integer := 0;
  Gen: Generator;
begin
 Reset(Gen);
 for I in 1..N loop
   D := Random(Gen) mod (M+1);
   Insert(Tree, D);
 end loop;

end Add_Random;

function Search(Tree: access Node; D: in Integer) return Boolean is
  T : access Node := Tree;
begin
  if (T = Null) then
     return false;
  end if;

  if (T.Data = D) then
     return true;
  elsif (D < T.Data) then
        return Search(T.Left, D);
  else
        return Search(T.Right, D);
  end if;
     return false;
end Search;

procedure Find_Predecessor(Tree: in out Node_Ptr; D: out Integer) is
begin
  if (Tree.Right = NULL) then
     D := Tree.Data;
     Tree := Tree.Left;
  else
     Find_Predecessor(Tree.Right, D);
  end if;
end Find_Predecessor;


procedure Delete(Tree: in out Node_Ptr; D: in Integer) is
  begin
  if (D < Tree.Data) then
     Delete(Tree.Left, D);
  elsif (D > Tree.Data) then
     Delete(Tree.Right, D);
  else
     if (Tree.Left = NULL and Tree.Right = NULL) then
        Tree := NULL;
     elsif Tree.Right = NULL then
        Tree := Tree.Left;
     elsif Tree.Left = NULL then
        Tree := Tree.Right;
     else
        Find_Predecessor(Tree.Left, Tree.Data);
     end if;
  end if;
  EXCEPTION
    WHEN Constraint_Error =>     -- Obj not found in the given tree
    RETURN;
end Delete;


procedure Push(Tab: in out Vektor; Tree: in out Node_Ptr; Counter : in out Integer) is
begin
  if (Tree = null) then return;
  end if;

  Push(Tab,Tree.Left, Counter);
  Tab(Counter) := Tree.Data;
  Counter := Counter + 1;
  Push(Tab,Tree.Right, Counter);

end Push;

procedure SortTab(Tab: in out Vektor) is
  Size: Integer := Tab'Length;
  Tmp: Integer;
begin
  loop
     for I in 1 .. Size - 1 loop
        if (Tab(I) > Tab(I+1)) then
           Tmp := Tab(I);
           Tab(I) := Tab(I+1);
           Tab(I+1) := Tmp;
        end if;
     end loop;
     Size := Size -1 ;
     exit when (Size <= 1);
  end loop;

end SortTab;

function TreeSize(Tree: in out Node_Ptr) return Integer is
  X: Integer := 0;
begin
  if (Tree = Null) then
     return 0;
  end if;

  return 1 + TreeSize(Tree.Left) + TreeSize(Tree.Right);
end TreeSize;

procedure BuildTree(Tree: in out Node_Ptr; Tab: in out Vektor; Start: in Integer; TheEnd: in Integer) is
Mid: Integer;
begin
  if (Start > TheEnd) then
     return;
  end if;
  Mid := ((Start+TheEnd)/2);
  insert(Tree, Tab(Mid));
  BuildTree(Tree.left, Tab, Start, Mid - 1);
  BuildTree(Tree.Right, Tab, Mid+1, TheEnd);

end BuildTree;

procedure BalancedTree(Tree: in out Node_Ptr) is
  Size: Integer := TreeSize(Tree)+1;
  W1 : Vektor (1..Size);
  Counter : Integer := 1;
begin
  Push(W1,Tree,Counter);
  SortTab(W1);
  Tree := Null;
  BuildTree(Tree, W1, 1, Size-1);


end BalancedTree;

Tree : Node_Ptr := Null;

begin
	Add_Random(Tree, 10, 5);
	Print(Tree);

	Put_Line("Is tree contains 11 : " & Search(Tree, 11)'Img);
	Put_Line("Addding 11.");
	Insert(Tree, 11);
	Print(Tree);
	Put_Line("Is tree contains 11 : " & Search(Tree, 11)'Img);
	Put_Line("Balancing tree");
	BalancedTree(Tree);
	Print(Tree);
	Put_Line("Delate 11");
	Delete(Tree,11);
	Print(Tree);
	Put_Line("Is tree contains 11 : " & Search(Tree, 11)'Img);

end Lab4Tree;

