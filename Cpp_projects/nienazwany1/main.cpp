#include <iostream>
#include <string>
#include <list>
#include <stack>
#include <cctype>
#include <cstdlib>
#include <stdio.h>
#include <stdlib.h>
#include <vector>

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

/*class ope{
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
};
*/

int main () {

  string xy="3x*y-z=";

//dawalem rezerwe miejsca na 10 mysle ze na dlugosc lancucha byloby ok wtedy nie trzeba na bierzaca alokowac
  vector<Op> tab;
  tab.reserve(10);
  list<string> tab2;
  vector<string> spr;
  spr.reserve(10);
  vector<Expr> tab3;
  tab3.reserve(10);

  string types[]= {"op","oper","expr"};
  int nnum = 1, nalp=1;

  stack<Op> stos;
  stack<Expr> stos2;

  int i=0;

// odpowiednik: Op three(1,3), x(1,"x"), y(2,"y"), z(3,"z");
  for ( string::iterator it=xy.begin(); it!=xy.end(); ++it)
  {
    if(isdigit(*it))
    {
      spr.push_back(types[0]);
      tab.push_back(Op(nnum, (int)(*it)-48));
      nnum++;
    }
    else if(isalpha(*it))
    {
      spr.push_back(types[0]);
      string a="";
      a+=(*it);
      tab.push_back(Op(nalp,a));
      nalp++;
    }
    else
    {
      spr.push_back(types[1]);
      string a="";
      a+=(*it);
      tab2.push_front(a);
    }
    i++;
  }

 // odpowiednik: three.eval(); x.eval(); y.eval(); z.eval();
  for(int i=0;i<tab.size();i++)
  {
    tab[i].eval();
  }

// odpowiednik: Expr ex1(1,"*",three,y), ex2(2,"-",x,ex1), ex3(3,"=",z,ex2);
//i tu jest problem bo wskazniki z Expr sie nakladaja i nie mam pojecia jak to naprawic
  int nexpr=1;
  int a=0;
  for(int i=0;i<spr.size();i++)
  {
    if(spr[i]=="oper")
    {
      if(spr[i-1]=="op" && spr[i-2]=="op")
      {
        Op x2 = stos.top();
        stos.pop();
        Op x1 = stos.top();
        stos.pop();
        stos2.push(Expr(nexpr,tab2.back(),x1,x2));
        tab3.push_back(Expr(nexpr,tab2.back(),x1,x2));
      }
      if(spr[i-1]=="op" && spr[i-2]=="expr")
      {
        Op x1 = stos.top();
        stos.pop();
        Expr x2 = stos2.top();
        stos2.pop();
        stos2.push(Expr(nexpr,tab2.back(),x1,x2));
        tab3.push_back(Expr(nexpr,tab2.back(),x1,x2));
      }
      if(spr[i-1]=="expr" && spr[i-2]=="op")
      {
        Expr x1 = stos2.top();
        stos2.pop();
        Op x2 = stos.top();
        stos.pop();
        stos2.push(Expr(nexpr,tab2.back(),x1,x2));
        tab3.push_back(Expr(nexpr,tab2.back(),x1,x2));
      }
      if(spr[i-1]=="expr" && spr[i-2]=="expr")
      {
        Expr x1 = stos2.top();
        stos2.pop();
        Expr x2 = stos2.top();
        stos2.pop();
        stos2.push(Expr(nexpr,tab2.back(),x1,x2));
        tab3.push_back(Expr(nexpr,tab2.back(),x1,x2));
      }
      tab3.back().eval();
      tab2.pop_back();
      nexpr++;
      spr[i] = types[2];
      i--;
    }
    else if(spr[i]=="op")
    {
    stos.push(tab[a]);
    a++;
    }
  }

// odpowiednik: ex1.eval(); ex2.eval(); ex3.eval();
  /*for(int i=0;i<tab3.size();i++)
  {
    tab3[i].eval();
  }
*/
  return 0;
}
