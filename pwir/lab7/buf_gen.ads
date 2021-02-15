with Ada.Text_IO;
use Ada.Text_IO;

package Buf_Gen is

type TBuf is array (Integer range <>) of Character;

protected type Buf(Size : Integer) is
	entry Put(Sign : in Character);
	entry Get(Sign : out Character);

 	private
 		B : TBuf(1..Size);
 		Counter  : Integer:=0;
 		Next_In   : Integer:=1;
 		Next_Out : Integer:=1;
end Buf;
end Buf_Gen;
