#include <iostream>
#include <string>
#include <list>
#include <stack>
#include <cctype>
#include <cstdlib>
#include <stdio.h>
#include <stdlib.h>

using namespace std;

enum TYP {NUM, SYM, VAR}; static const char *typestr[]={"num","sym","var"};
class Op {
  private: TYP type; int ident; string name; double value;
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
  private: int ident; string oper; Op *op_1, *op_2; Expr *ex_1, *ex_2; bool is_bin=true, is_ex1=false, is_ex2=false;
  public:
    Expr(int id, string op_str, Op op1)
    {
      ident=id;
      oper=op_str;
      op_1=&op1;
      is_bin=false;
    }
    Expr(int id, string op_str, Op op1, Op op2){ ident=id; oper=op_str; op_1=&op1; op_2=&op2; is_ex1=false; is_ex2=false; }
    Expr(int id, string op_str, Expr ex1){ ident=id; oper=op_str; ex_1=&ex1; is_bin=false; is_ex1=true; }
    Expr(int id, string op_str, Expr ex1, Expr ex2){ ident=id; oper=op_str; ex_1=&ex1; ex_2=&ex2; is_ex1=true; is_ex2=true; }
    Expr(int id, string op_str, Op op1, Expr ex2){ ident=id; oper=op_str; op_1=&op1; ex_2=&ex2; is_ex2=true; }
    Expr(int id, string op_str, Expr ex1, Op op2){ ident=id; oper=op_str; ex_1=&ex1; op_2=&op2; is_ex1=true; }
    int id(){ return ident; } bool isbin(){ return is_bin; } bool isex1(){ return is_ex1; } bool isex2(){ return is_ex2; }
    void eval(){
      cout << "\nex" << this->id() << endl; cout << " operator: " << oper << endl;
      if (isex1()) cout << " arg1: ex" << ex_1->id()<< endl; else cout << " arg1: " << typestr[op_1->tp()] << op_1->id()<< endl;
      if (isbin()){
          if (isex2()) cout << " arg2: ex" << ex_2->id()<< endl; else cout << " arg2: "<< typestr[op_2->tp()] << op_2->id()<< endl; } }
};

class ope{
  public:
    string oper;
};

class kl{
  public:
      bool isop=false;
      bool isexpr=false;
      bool isoper=false;
      Op *x_op;
      Expr *x_expr;
      ope w;
    kl(Op x){isop=true;x_op=&x;}

    kl(Expr x){isexpr=true;x_expr=&x;}

    kl(ope oper){isoper=true;w=oper;}
  //*****************
    void re()
    {
      if(isop) reo();
      else ree();
    }
    Op* reo()
    {
      return x_op;
    }
    Expr* ree()
    {
      return x_expr;
    }
  //*****************
};

int main () {

  string xy="3x*y-z=";
  list<kl> tab;
  int nnum = 1, nalp=1;
  stack<kl> stos;

  for ( string::iterator it=xy.begin(); it!=xy.end(); ++it)
  {
    if(isdigit(*it))
    {
      Op var(nnum, (int)(*it)-48);
      kl var2(var);
      tab.push_back(var2);
      nnum++;
      var.eval();
    }
    else if(isalpha(*it))
    {
      string a="";
      a+=(*it);
      Op var(nalp,a);
      kl var2(var);
      tab.push_back(var2);
      nalp++;
      var.eval();
    }
    else
    {
      ope var;
      var.oper= *it;
      kl var2(var);
      tab.push_back(var2);
    }
  }
  int nexpr=1;
  Op x(1,"x"), y(2,"y");
  for( list<kl>::iterator iter=tab.begin(); iter != tab.end(); iter++ )
  {

    if(iter->isoper)
      {
        kl temp1 = stos.top();
        stos.pop();
        kl temp2 = stos.top();
        stos.pop();
        //Expr vare(nexpr,iter->w.oper, *(temp1.re()), *(temp2.re()));
        Expr vare(nexpr,iter->w.oper, x,y);
        kl vare2(vare);
        stos.push(vare2);
        vare.eval();
        nexpr++;
      }
      else
      {
        stos.push(*iter);
      }
  }

  //Op three(1,3), x(1,"x"), y(2,"y"), z(3,"z"); three.eval(); x.eval(); y.eval(); z.eval();
  //Expr ex1(1,"*",three,y), ex2(2,"-",x,ex1), ex3(3,"=",z,ex2); ex1.eval(); ex2.eval(); ex3.eval();
  return 0;
}
