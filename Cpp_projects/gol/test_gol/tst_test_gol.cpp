#include <QtTest>

#include <iostream>
#include <vector>
#include <cstdlib>
#include <unistd.h>
#include <fstream>
#include <stdlib.h>
#include <sstream>
#include <time.h>
#include <stdio.h>
#include <ctime>
#include <algorithm>
#include <utility>
#include <stdio.h>
#include <unistd.h>
#ifdef WINDOWS
    # include <windows.h>
    #include <thread>
    #include <chrono>
#endif


using namespace std;
typedef vector<vector<bool>> Area;

class options{
    private:
    unsigned int speed=100000;
    vector<int> rules1={2,3}, rules2={3};
    pair<char,char> ikons;
    unsigned long sizex=40,sizey = 40;
    public:
    Area gen;
    options(){
        ikons.first ='1';
        ikons.second ='0';
        gen.resize((sizex),vector<bool>(sizey));
    }
    unsigned int rt_speed(){
        return speed;
    }
    unsigned  rt_size(char tmp){
        if(tmp == 'x')
            return sizex;
        else {
            return sizey;
        }
    }
    vector<int>& rt_rule(int tmp){
        if(tmp == 1)
            return rules1;
        else {
            return rules2;
        }
    }

    void sh_size(){
        cout<<"Current x size: "<<sizex<<endl;
        cout<<"Current y size: "<<sizey<<endl;
        cout<<endl<<"1.Change x"<<endl<<"2.Back"<<endl;
    }
    void sh_velocity(){

    }
    void sh_ikons(){
        cout<<"Current Ikons:"<<endl<<"Alive: "<<ikons.first<<endl<<"Death: "<<ikons.second<<endl<<endl<<"1.Change"<<endl<<"2.Back"<<endl;
    }
    void sh_rules(){
        cout<<"Current rules:";
        for(auto x:rules1)
            cout<<x<<" ";
        cout<<"/ ";
        for(auto x:rules2)
            cout<<x<<" ";
        cout<<endl<<endl<<"Legend:"<<endl<<"the number of cells in the neighborhood for which living cells survive"
           <<endl<<"  /  "
           <<endl<<"the number of cellsthe number of cells in the neighborhood for which dead cells come alive "
           <<endl<<endl<<"1.Change"<<endl<<"2.Back"<<endl;
    }

    void ch_size(unsigned long x,unsigned long y){

        sizex=x;

        sizey=y;
        gen.resize((sizex),vector<bool>(sizey));
        }
    void ch_velocity(unsigned int tmp){
        speed=tmp;
    }
    void ch_rules(string newrules1,string newrules2 ){
        rules1.resize(newrules1.size());
        rules2.resize(newrules2.size());
        for(unsigned int i=0; i<newrules1.size();i++)
            rules1[i]=newrules1[i]-48;
        for(unsigned int i=0; i<newrules2.size();i++)
            rules2[i]=newrules2[i]-48;
    }
    void ch_ikons(char x, char y){
        cout<<"Alive ikon:"<<endl;
            ikons.first=x;
        cout<<"Death ikon:"<<endl;
            ikons.second=y;
    }

    void draw(){
        for(auto line : gen){
            for(auto cell : line ){
              if(cell==true)
                cout<<ikons.first;
              else
                cout<<ikons.second;
            }
            cout<<endl;
          }
    }
    void blank(){
        #ifdef WINDOWS
            sleep_for(milliseconds(speed));
        #else
            usleep(speed);
        #endif
    }

    void coping(fstream &plik){
        string line;
        unsigned long lines=0;
        gen.resize((40),vector<bool>(40));
        while(getline(plik, line))
            {
                for(unsigned long i=0;i<40;i++){
                    if(line[i]=='1') {gen[lines][i]=true;}
                    else {gen[lines][i]=false;}
                }
                lines++;
            }
    }
};

void clear(){
    #ifdef WINDOWS
        system("cls");
    #else
        system ("clear");
    #endif
}

Area game(options opt){
  Area last_gen;
  int size1 = int(opt.rt_size('x'));
  int size2 = int(opt.rt_size('y'));
  last_gen.resize(static_cast<unsigned long>(size1), vector < bool >(static_cast<unsigned long>(size2)));
  last_gen=opt.gen;
  unsigned int generations=0;

  while(generations < 10){
    //cout<<"Number of generations:"<<generations<<endl;
    //opt.draw();
    last_gen=opt.gen;

    for(long i=0; i<size1; i++)
      for(long j=0; j<size2; j++){

        unsigned int alive=0;
        for(long line=-1; line<2; line++)
          for(long column=-1; column<2; column++)
            if(!(line==0 && column==0))
              if(i+line>=0 && i+line<size1 && j+column>=0 && j+column<size2)
                if(last_gen[i+line][j+column])
                  alive++;

        if(opt.gen[i][j] == true){
            if(find(opt.rt_rule(1).begin(), opt.rt_rule(1).end(), alive) != opt.rt_rule(1).end() )
                opt.gen[i][j] = true;
            else
                opt.gen[i][j] = false;
        }
        else if(opt.gen[i][j] != true){
            if(find(opt.rt_rule(2).begin(), opt.rt_rule(2).end(), alive) != opt.rt_rule(2).end() )
              opt.gen[i][j] = true;
            else
                opt.gen[i][j] = false;
        }
      }
    opt.blank();
    //clear();
    generations++;
  }
  //cout<<"Number of generations:"<<generations<<endl;
  return opt.gen;
}
[[noreturn]]
void  random_game(options opt){
    srand(time(nullptr));
    for(unsigned long i=0; i<opt.rt_size('x'); i++)
        for(unsigned long j=0; j<opt.rt_size('y'); j++){
            opt.gen[i][j]=rand() % 2;
        }
    game(opt);
}
Area read(options opt, string p){
    fstream plik (p);
    if(plik.is_open())
        {
        opt.coping(plik);
        plik.close();
        return game(opt);
        }
}


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
Area coping_sample(string p){
    string line;
    ifstream myfile (p);
    Area sample;
    sample.resize((40),vector<bool>(40));
    int lines=0;
    if (myfile.is_open())
    {
      while ( getline (myfile,line) )
      {
          for(unsigned long i=0;i<40;i++){
              if(line[i]=='1') {sample[lines][i]=true;}
              else {sample[lines][i]=false;}
          }
          lines++;
      }
      myfile.close();
    }
    else cout << "Unable to open file";
    return sample;
}

class test_gol : public QObject
{
    Q_OBJECT

private slots:
    void  area_tests_1();
    void  area_tests_2();
    void  area_tests_3();
    void change_velocity_test();
    void change_size_test();
    void change_rules_test();

};


void test_gol::area_tests_1()
{
    Area a=coping_sample("/home/dominik/Qt-projects/test_gol/wynik1.txt");
    options opt;
    string path="/home/dominik/Qt-projects/test_gol/test1.txt";
    QVERIFY(read(opt, path)==a);
}
void test_gol::area_tests_2()
{
    Area b=coping_sample("/home/dominik/Qt-projects/test_gol/wynik2.txt");
    options opt;
    string path="/home/dominik/Qt-projects/test_gol/test2.txt";
    QVERIFY(read(opt, path)==b);
}
void test_gol::area_tests_3()
{
    Area c=coping_sample("/home/dominik/Qt-projects/test_gol/wynik3.txt");
    options opt;
    string path="/home/dominik/Qt-projects/test_gol/test3.txt";
    QVERIFY(read(opt, path)==c);
}
void test_gol::change_velocity_test()
{
    options opt;
    opt.ch_velocity(100);
    QVERIFY(opt.rt_speed()==100);
}
void test_gol::change_size_test()
{
   options opt;
   opt.ch_size(5,5);
   QVERIFY(opt.rt_size('x')==5);
}
void test_gol::change_rules_test(){
    options opt;
    opt.ch_rules("1234","9876");
    vector<int> x={9,8,7,6};
    QVERIFY(opt.rt_rule(2)==x);
}

QTEST_APPLESS_MAIN(test_gol)

#include "tst_test_gol.moc"
