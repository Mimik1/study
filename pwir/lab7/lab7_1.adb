with Ada.Text_IO, Buf_Gen;
use Ada.Text_IO, Buf_Gen;

procedure Lab7_1 is
Bufor_Gen: Buf(5);
task Producer;

task body Producer is
  X : Character := 'a';
begin
  Put_Line("Producer: Puting into buffor: '" & X & "'...");
  Bufor_Gen.Put(X);
  Put_Line("Producer: Inserted...");
end Producer;

task Consumer;

task body Consumer is
  X : Character := ' ';
begin
  Put_Line("Consumer: Geting...");
  Bufor_Gen.Get(X);
  Put_Line("Consumer: Gained: '" & X & "'");
end Consumer;

begin
	Put_Line("Main");
end Lab7_1;
