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


//Options
typedef vector<vector<bool>> Area;
static unsigned int speed=100000;
static vector<int> rules1={2,3};
static vector<int> rules2={3};
static pair<char,char> ikons('O',' ');
static int sizex=40,sizey = 40;


void draw(Area gen){
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
void clear(){
    #ifdef WINDOWS
        system("cls");
    #else
        system ("clear");
    #endif
}

[[noreturn]]
void game(Area gen){
  Area last_gen;
  int size1 = int(gen.size());
  int size2 = int(gen[0].size());
  last_gen.resize(static_cast<unsigned long>(size1), vector < bool >(static_cast<unsigned long>(size2)));
  last_gen=gen;

  while(true){
    draw(gen);
    last_gen=gen;

    for(long i=0; i<size1; i++)
      for(long j=0; j<size2; j++){

        unsigned int alive=0;
        for(long line=-1; line<2; line++)
          for(long column=-1; column<2; column++)
            if(!(line==0 && column==0))
              if(i+line>=0 && i+line<size1 && j+column>=0 && j+column<size2)
                if(last_gen[i+line][j+column])
                  alive++;


        if(gen[i][j] == true){
            if(find(rules1.begin(), rules1.end(), alive) != rules1.end() )
                gen[i][j] = true;
            else
                gen[i][j] = false;
        }
        else if(gen[i][j] != true){
            if(find(rules2.begin(), rules2.end(), alive) != rules2.end() )
              gen[i][j] = true;
            else
                gen[i][j] = false;
        }
      }
    cout<<endl;
    blank();
    clear();
  }
}
[[noreturn]]
void  random_game(){
    Area gen;
    gen.resize((40),vector<bool>(40));
    srand(time(nullptr));
    for(unsigned long i=0; i<gen.size(); i++)
        for(unsigned long j=0; j<gen[0].size(); j++){
            gen[i][j]=rand() % 2;
        }
    game(gen);
}
void read(){
    fstream plik;
    string line;
    unsigned long lines=0;
    Area gen;
    //gen.resize((40),vector<bool>(40));
    plik.open("/home/dominik/Qt-projects/gol/Odczyt.txt",ios::in);
    if(plik.good())
        {
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
        plik.close();
        game(gen);
        }
    else cout <<"Brak pliku";
}

void menu();
void menu_text_color(){
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
    menu();
}
void menu_size(){
    cout<<"Current x size: "<<sizex<<endl;
    cout<<"Current y size: "<<sizey<<endl;
    cout<<endl<<"1.Change x"<<endl<<"2.Change y"<<endl<<"3.Back"<<endl;
    int tmp;
    cin>>tmp;
    switch(tmp){
        case 1:
            cout<<"Write number not greater than 100:"<<endl;
            cin>>sizex;
            break;
        case 2:
            cout<<"Write number not greater than 100:"<<endl;
            cin>>sizey;
            break;
        default:
            menu();
            break;
    }
    clear();
    menu_size();
    }
void menu_rules(){
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
    int tmp;
    cin>>tmp;
    clear();
    string newrules1,newrules2;
    if(tmp==1){
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
    else
    menu();
    clear();
    menu_rules();
}
void menu_ikons(){
    cout<<"Current Ikons:"<<endl<<"Alive: "<<ikons.first<<endl<<"Death: "<<ikons.second<<endl<<endl<<"1.Change"<<endl<<"2.Back"<<endl;
    int tmp=1;
    cin>>tmp;
    system("clear");
    if(tmp==1){
        cout<<"New ikons:"
           <<endl<<"New alive ikon: ";
        cin>>ikons.first;
            cout<<"New death ikon: ";
        cin>>ikons.second;
    }
    else
    menu();
    clear();
    menu_ikons();
}
void menu_velocity(){
    cout<<"Current velocity:"<<speed<<endl<<"1.Change"<<endl<<"2.Back"<<endl;
    int tmp=1;
    cin>>tmp;
    clear();
    if(tmp==1){
        cout<<"New velocity:";
              cin>>speed;
    }
    else
    menu();
    clear();
    menu_velocity();
}
void menu1(){
    clear();
    cout<<"~~~~~~~~~~ M E N U ~~~~~~~~~~~~"<<endl<<"Opcje gry:"
       <<endl<<"1.Random"<<endl<<"2.Declaration"<<endl<<"3.Read form file"<<endl<<"4.Back"<<endl;
    int tmp;
    cin>>tmp;
    clear();
    switch(tmp){
        case 1:
            random_game();
            break;
        case 2:     //Declaration
            cout<<"tu bedzie deklaracja`````````````````````````````````````````````````````````````````````````````````````````````````````````";
            break;
        case 3:     //Read
            read();
            break;
        case 4:     //Back
            menu();
            break;
    }
}
void menu2(){
    clear();
    cout<<"~~~~~~~~~~ M E N U ~~~~~~~~~~~~"<<endl<<"Opcje gry:"
    <<endl<<"1.Rules"<<endl<<"2.Ikons"<<endl<<"3.Veclocity"
    <<endl<<"4.Size"<<endl<<"5.Text Color"<<endl<<"6.Back"<<endl;
    int tmp;
    cin>>tmp;
    clear();
    switch(tmp){
        case 1:     //Rules
            menu_rules();
            break;
        case 2:     //Ikons
            menu_ikons();
            break;
        case 3:     //Velocity
            menu_velocity();
            break;
        case 4:     //Size
            menu_size();
            break;
        case 5:     //Color
            menu_text_color();
            break;
        default:    //Back
            menu();
            break;
    }
}
void menu(){
  clear();
  cout<<"~~~~~~~~~~ M E N U ~~~~~~~~~~~~"
     <<endl<<"1.Start"<<endl<<"2.Option"<<endl;
  int tmp;
  cin>>tmp;
  switch(tmp){
    case 1:
        menu1();
        break;
    case 2:
        menu2();
        break;
  }
}

int main() {
  menu();
}
