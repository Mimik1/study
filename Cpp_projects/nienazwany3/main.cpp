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
    unsigned long sizex=40,sizey = 100;
    public:
    Area gen;
    options(){
        ikons.first ='#';
        ikons.second =' ';
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

    void ch_size(){
        cout<<"Size x-write number not greater than 100:"<<endl;
        cin>>sizex;
        cout<<"Size y-write number not greater than 100:"<<endl;
        cin>>sizey;
        gen.resize((sizex),vector<bool>(sizey));
        }
    void ch_velocity(){
        cout<<"New velocity:";
        cin>>speed;
    }
    void ch_rules(){
        string newrules1,newrules2;
        cout<<"Write strings with numbers 0-8 without spaces"<<endl<<"New rules:";
        cin>>newrules1;
        cout<<" / "<<endl;
        cin>>newrules2;
        rules1.resize(newrules1.size());
        rules2.resize(newrules2.size());
        for(unsigned int i=0; i<newrules1.size();i++)
            rules1[i]=newrules1[i]-48;
        for(unsigned int i=0; i<newrules2.size();i++)
            rules2[i]=newrules2[i]-48;
    }
    void ch_ikons(){
        cout<<"Alive ikon:"<<endl;
            cin>>ikons.first;
        cout<<"Death ikon:"<<endl;
            cin>>ikons.second;
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
        while(!plik.eof())
            {
                getline(plik, line);
                lines++;
            }
        gen.resize((lines),vector<bool>(line.size()));
        lines=0;
        plik.seekg (0, ios::beg);
        while(!plik.eof())
            {
                getline(plik, line);
                for(unsigned long i=0;i<line.size();i++){
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

[[noreturn]]
void game(options opt){
  Area last_gen;
  int size1 = int(opt.rt_size('x'));
  int size2 = int(opt.rt_size('y'));
  last_gen.resize(static_cast<unsigned long>(size1), vector < bool >(static_cast<unsigned long>(size2)));
  last_gen=opt.gen;
  unsigned int generations=0;

  while(generations < 10){
    cout<<"Number of generations:"<<generations<<endl;
    opt.draw();
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
    cout<<endl;
    opt.blank();
    clear();
    generations++;
  }
  cout<<"Number of generations:"<<generations<<endl;
  opt.draw();

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
void read(options opt){
    fstream plik;
    string path;
    cout<<"Write the path of prepared file:";
    path="/home/dominik/Pobrane/3.txt";
    plik.open(path,ios::in);
    if(plik.good())
        {
        opt.coping(plik);
        plik.close();
        game(opt);
        }
    else cout <<"Brak pliku";
}

void menu(options opt);
void menu_text_color(options opt){
   #ifdef WINDOWS
    HANDLE hOut;
    hOut = GetStdHandle( STD_OUTPUT_HANDLE );
    cout<<"1.Change text color"<<endl<<"2.Back"<<endl;
    int tmp;
    cin>>tmp;
    if(tmp==1)
    {
        cout<<"Choose the text color:"<<endl;
        SetConsoleTextAttribute( hOut, STD_OUTPUT_HANDLE );
        cout<<"1.THIS IS WHITE"<<endl;
        SetConsoleTextAttribute( hOut, FOREGROUND_RED );
        cout<<"2.THIS IS RED"<<endl;
        SetConsoleTextAttribute( hOut, FOREGROUND_GREEN );
        cout<<"3.THIS IS GREEN"<<endl;
        SetConsoleTextAttribute( hOut, FOREGROUND_BLUE );
        cout<<"4.THIS IS BLUE"<<endl;
        SetConsoleTextAttribute( hOut, FOREGROUND_BLUE | FOREGROUND_GREEN | FOREGROUND_RED );
        cout<<"5.Back"<<endl;
        cin>>tmp;
        switch(tmp){
            case 2:
                SetConsoleTextAttribute( hOut, FOREGROUND_RED );
                break;
            case 3:
                SetConsoleTextAttribute( hOut, FOREGROUND_GREEN );
                break;
            case 4:
                SetConsoleTextAttribute( hOut, FOREGROUND_BLUE );
                break;
            default:
                break;
        }
    }
#endif
    menu(opt);
}
void menu_size(options opt){
    opt.sh_size();
    int tmp;
    cin>>tmp;
    switch(tmp){
        case 1:
            opt.ch_size();
            break;
        default:
            menu(opt);
            break;
    }
    clear();
    menu_size(opt);
    }
void menu_rules(options opt){
    opt.sh_rules();
    int tmp;
    cin>>tmp;
    clear();

    if(tmp==1){
        opt.ch_rules();
        }
    else
        menu(opt);
    clear();
    menu_rules(opt);
}
void menu_ikons(options opt){
    opt.sh_ikons();
    int tmp=1;
    cin>>tmp;
    system("clear");
    if(tmp==1){
        opt.ch_ikons();
    }
    else
        menu(opt);
    clear();
    menu_ikons(opt);
}
void menu_velocity(options opt){
    opt.sh_velocity();
    int tmp=1;
    cin>>tmp;
    clear();
    if(tmp==1){
        opt.ch_velocity();
    }
    else
        menu(opt);
    clear();
    menu_velocity(opt);
}
void menu1(options opt){
    clear();
    cout<<"~~~~~~~~~~ M E N U ~~~~~~~~~~~~"<<endl<<"Opcje gry:"
       <<endl<<"1.Random"<<endl<<"2.Declaration"<<endl<<"3.Read form file"<<endl<<"4.Back"<<endl;
    int tmp;
    cin>>tmp;
    clear();
    switch(tmp){
        case 1:
            random_game(opt);
            break;
        case 2:     //Declaration
            cout<<"tu bedzie deklaracja`````````````````````````````````````````````````````````````````````````````````````````````````````````";
            break;
        case 3:     //Read
            read(opt);
            break;
        case 4:     //Back
            menu(opt);
            break;
    }
}
void menu2(options opt){
    clear();
    cout<<"~~~~~~~~~~ M E N U ~~~~~~~~~~~~"<<endl<<"Options:"
    <<endl<<"1.Rules"<<endl<<"2.Icons"<<endl<<"3.Velocity"
    <<endl<<"4.Size"<<endl<<"5.Text Color"<<endl<<"6.Back"<<endl;
    int tmp;
    cin>>tmp;
    clear();
    switch(tmp){
        case 1:     //Rules
            menu_rules(opt);
            break;
        case 2:     //Ikons
            menu_ikons(opt);
            break;
        case 3:     //Velocity
            menu_velocity(opt);
            break;
        case 4:     //Size
            menu_size(opt);
            break;
        case 5:     //Color
            menu_text_color(opt);
            break;
        default:    //Back
            menu(opt);
            break;
    }
}
void menu(options opt){
  clear();
  cout<<"~~~~~~~~~~ M E N U ~~~~~~~~~~~~"
     <<endl<<"1.Start"<<endl<<"2.Option"<<endl;
  int tmp;
  cin>>tmp;
  switch(tmp){
    case 1:
        menu1(opt);
        break;
    case 2:
        menu2(opt);
        break;
  }
}

int main() {
  options opt;
  menu(opt);
}
