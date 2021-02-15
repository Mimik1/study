#include <iostream>
#include <string>
#include <list>
#include <cctype>
#include <stdlib.h>

using namespace std;
enum TYP {NUM, SYM, VAR};
static const char *typestr[]={"num","sym","var"};
class Op {
  private:
      TYP type;
      int ident;
      string name;
      double value;
  public:
    Op(int id, double val)
    {
        type=NUM;
        ident=id;
        value=val;
    }
    Op(int id, string nm)
    {
        type=SYM;
        ident=id;
        name=nm;
    }
    Op(int id, string nm, double val)
    {
        type=VAR;
        ident=id;
        name=nm;
        value=val;
    }
    enum TYP tp()
    {
        return type;
    }
    int id()
    {
        return ident;
    }
    bool isnum()
    {
        return type==NUM;
    }
    bool issym()
    {
        return type==SYM;
    }
    void eval()
    {
      cout << typestr[type] << id() << endl;
      if (!isnum()) cout << " name: " << name << endl;
      if (!issym()) cout << " value: " << value << endl;
    }
};
class Expr {
  private:
      int ident;
      string oper;
      Op *op_1, *op_2;
      Expr *ex_1, *ex_2;
      bool is_bin=true, is_ex1=false, is_ex2=false;
  public:
    Expr(int id, string op_str, Op op1)
    {
        ident=id;
        oper=op_str;
        op_1=&op1;
        is_bin=false;
    }
    Expr(int id, string op_str, Op op1, Op op2)
    {
        ident=id;
        oper=op_str;
        op_1=&op1;
        op_2=&op2;
        is_ex1=false;
        is_ex2=false; }
    Expr(int id, string op_str, Expr ex1)
    {
        ident=id;
        oper=op_str;
        ex_1=&ex1;
        is_bin=false;
        is_ex1=true;
    }
    Expr(int id, string op_str, Expr ex1, Expr ex2)
    {
        ident=id;
        oper=op_str;
        ex_1=&ex1;
        ex_2=&ex2;
        is_ex1=true;
        is_ex2=true;
    }
    Expr(int id, string op_str, Op op1, Expr ex2)
    {
        ident=id;
        oper=op_str;
        op_1=&op1;
        ex_2=&ex2;
        is_ex2=true;
    }
    Expr(int id, string op_str, Expr ex1, Op op2)
    {
        ident=id;
        oper=op_str;
        ex_1=&ex1;
        op_2=&op2;
        is_ex1=true;
    }
    int id()
    {
        return ident;
    }
    bool isbin()
    {
        return is_bin;
    }
    bool isex1()
    {
        return is_ex1;
    }
    bool isex2()
    {
        return is_ex2;
    }
    void eval(){
      cout << "\nex" << this->id() << endl; cout << " operator: " << oper << endl;
      if (isex1()) cout << " arg1: ex" << ex_1->id()<< endl;
      else cout << " arg1: " << typestr[op_1->tp()] << op_1->id()<< endl;
      if (isbin())
      {
          if (isex2()) cout << " arg2: ex" << ex_2->id()<< endl;
          else cout << " arg2: "<< typestr[op_2->tp()] << op_2->id()<< endl; }
    }
};

class Type_of
{
    public:
    string type_;
    int nr_type;
};

int main () {
  string equa = "3y*x-z=";

  string typs[] = {"ope", "num", "sym", "expr", "non"};
  Type_of list_type[equa.length()];
  Op array[equa.length()];
  int n_num=0, n_sym=0;

  for( int i=0; i<equa.length(); i++)
  {
     if(isdigit(equa[i]))
     {
         n_num++;
         Type_of tmp_type;
         tmp_type.type_=typs[1];
         tmp_type.nr_type = n_num;
         list_type[i]= tmp_type;
         //Op var_op(n_num, atoi.equa[i]);
         Op var_op(n_num, (int)equa[i]);

     }
  }
 /* Op three(1,3), x(1,"x"), y(2,"y"), z(3,"z");
  three.eval();
  x.eval();
  y.eval();
  z.eval();
  Expr ex1(1,"*",three,y), ex2(2,"-",x,ex1), ex3(3,"=",z,ex2);
  ex1.eval();
  ex2.eval();
  ex3.eval();*/
  return 0;
}
