#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <regex>
#include <algorithm>

using namespace std;

typedef vector<string> list;

vector<list> prepare(ifstream& plik){
    string word;        //word
    list sente;         //sentence
    vector<list> text;  //all text
    if(plik){
        while(plik>>word) {
            if(word[ word.length()-1 ] == '.' || word[ word.length()-1 ] == '!' || word[ word.length()-1 ] == '?' || word[ word.length()-1 ] == ':'){
                word.erase(word.length()-1);
                sente.push_back(word);
                text.push_back(sente);
                sente.clear();
            }
            else
            sente.push_back(word);

        }
    }
    else{
        cout << "Error" << endl;
        exit(0);
        }
  return text;
}

string cut(string &word){
    unsigned long num = word.length();
    string tmp;
    if(num<3)
        tmp="               ";                   //tmp becomes something that will not be found in the text
    else if(num<5)                               //words shorter than 5 are not cuted
        tmp=word;
    else
        for(unsigned i=0; i< num*0.75; i++)
            tmp+= word[i];
    return tmp;
}

void write_word(vector<vector<unsigned int>> &counter, string &word, list &used){
    if(find(used.begin(), used.end(), word) == used.end())      //checking the repeat
    if(!counter.empty()){
        cout<<"Wyraz:"<<word<<endl;
        for (vector<vector<unsigned int>>::iterator w = counter.begin() ; w != counter.end(); ++w){
            cout<<" powtorzenie w "<<(*w)[0]<<" zdaniu, wyraz "<<(*w)[1]<<endl;
        }
        cout<<endl;
    }
}

void comparsion_of_words(vector<list> &t1, vector<list> &t2){
    unsigned int sn2=0, wn2=0;
    vector<vector<unsigned int>> counter;
    list used;
    for (vector<list>::iterator sente1 = t1.begin() ; sente1 != t1.end(); ++sente1){

        for (list::iterator word1 = sente1->begin() ; word1 != sente1->end(); ++word1){

            for (vector<list>::iterator sente2 = t2.begin() ; sente2 != t2.end(); ++sente2){
                sn2++;

                for (list::iterator word2 = sente2->begin() ; word2 != sente2->end(); ++word2){
                    wn2++;

                    if( regex_match(*word2, regex(cut(*word1)+".*"))){          //checking the similarity
                        counter.push_back({sn2,wn2});
                    }
                }
                wn2=0;
            }
            write_word(counter, *word1, used);
            counter.clear();
            used.push_back(*word1);

            sn2=0;
        }
    }

}

void write_sente(list &sente1, list sente2, unsigned int sn){
    cout<<"Dla zdania: \"";
    for (list::iterator word = sente1.begin() ; word != sente1.end(); ++word)
        cout<<*word<<" ";
    cout<<".\""<<endl<<"podejżenie plagiatu w zdaniu "<<sn<<":"<<endl;
    for (list::iterator word = sente2.begin() ; word != sente2.end(); ++word)
        cout<<*word<<" ";
    cout<<"."<<endl<<endl;
}

void comparsion_of_sentences(vector<list> &t1, vector<list> &t2){
    unsigned int sn2=0, wn2=0, simn=0;
    vector<vector<unsigned int>> counter;
    list used;
    for (vector<list>::iterator sente1 = t1.begin() ; sente1 != t1.end(); ++sente1){

        for (vector<list>::iterator sente2 = t2.begin() ; sente2 != t2.end(); ++sente2){
            sn2++;

            for (list::iterator word1 = sente1->begin() ; word1 != sente1->end(); ++word1){

                for (list::iterator word2 = sente2->begin() ; word2 != sente2->end(); ++word2){
                    wn2++;
                    if( regex_match(*word2, regex(cut(*word1)+".*"))){          //checking the similarity
                        simn++;
                        if(word1+1 != sente1->end()){
                            ++word1;
                        }
                     }
                }
                wn2=0;
            }
            if(simn > (sente1->size())*0.5){
                write_sente(*sente1, *sente2, sn2);
            }
            simn=0;
        }
        sn2=0;
    }
}

int main() {
    ifstream plik1("/home/dominik/Qt-projects/plagiat_detector/tekst1.txt");
    ifstream plik2("/home/dominik/Qt-projects/plagiat_detector/tekst2.txt");
    vector<list> text1, text2;
    text1 = prepare(plik1);
    text2 = prepare(plik2);
    comparsion_of_words(text1, text2);
    comparsion_of_sentences(text1, text2);
 }
