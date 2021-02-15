with Ada.Text_IO;
use Ada.Text_IO;

procedure Lab7_2 is
  
protected type Semafor_Bin(Init_Sem: Boolean := True) is
  entry Wait;
  procedure Signal;
  private
   S : Boolean := Init_Sem;
end Semafor_Bin ;

protected body Semafor_Bin  is
  entry Wait when S is
  begin
    S := False;
  end Wait;
  procedure Signal  is
  begin
    S := True;
  end Signal;
end Semafor_Bin ;
  
Semafor1: Semafor_Bin(False);  
  
task Producer; 

task body Producer is
begin  
  Put_Line("Producer: Making ... " );
  delay 0.5;
  Put_Line("Producer: Sending...");
  Semafor1.Signal;
end Producer;

task Consumer; 

task body Consumer is
begin  
  Put_Line("Consumer: Waiting...");
  Semafor1.Wait;
  Put_Line("Consumer: I gained signal ...");
end Consumer;

begin
  Put_Line("Main");
end Lab7_2;
  
