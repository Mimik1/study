with Ada.Text_IO;
use Ada.Text_IO;

package body Buf_Gen is
	protected body Buf is
		entry Put(Sign : in Character)
		when Counter<Size is begin
			B(Next_In) := Sign;
			Next_In := Next_In+1;
			Counter := Counter+1;
		end Put;

		entry Get(Sign: out Character)
		when Counter>0 is begin
			Next_Out := Next_In-1;
			Counter := Counter - 1;
			Next_In := Next_In - 1;
			Sign := B(Next_Out);
		end Get;
	end Buf;
end Buf_Gen;
