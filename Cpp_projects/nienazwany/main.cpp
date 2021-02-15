
#include <iostream>
#include <vector>
#include <unistd.h>
#include <cstdlib>
using namespace std;

// Global variables

const int ROWS=9;
const int COLS=10;
const char ALIVE= 1;
const char DEAD= 0;

// Function prototypes
void generation(vector< vector<int> > &world, vector< vector<int> > &worldcopy);

void display(vector< vector<int> > &world);

int main()
{
    vector< vector<int> > world(ROWS, vector<int>(COLS, DEAD));
    vector< vector<int> > worldcopy(ROWS, vector<int>(COLS, DEAD));

    // Set the initial alive cell configuration

   world= {{0,0,0,0,1,1,0,0,0,0}, {0,0,1,0,0,0,0,1,0,0},{0,1,0,0,0,0,0,0,1,0}, {1,0,0,0,0,0,0,0,0,1},
          {1,0,0,0,0,0,0,0,0,1},{1,0,0,0,0,0,0,0,0,1},{0,1,0,0,0,0,0,0,1,0}, {0,0,1,0,0,0,0,1,0,0},
          {0,0,0,0,1,1,0,0,0,0}};
    while(true)
    {
        system("clear");
        display(world);
        usleep(80000);
        generation(world, worldcopy);

        return 0;
    }


}


void generation(vector< vector<int> > &world, vector< vector<int> > &worldcopy)
{
    int count=0;

    for (int i=0; i<ROWS; i++)
    {
        for (int j=0; j<COLS; j++)
        {
            // Check neighbor cells for life

            if (worldcopy[i-1][j+1]==ALIVE)
            {
                count++;
            }
            if (worldcopy[i][j+1]==ALIVE)
            {
                count++;
            }
            if (worldcopy[i+1][j+1]==ALIVE)
            {
                count++;
            }
            if (worldcopy[i-1][j]==ALIVE)
            {
                count++;
            }
            if (worldcopy[i+1][j]==ALIVE)
            {
                count++;
            }
            if (worldcopy[i-1][j-1]==ALIVE)
            {
                count++;
            }
            if (worldcopy[i][j-1]==ALIVE)
            {
                count++;
            }
            if (worldcopy[i+1][j-1]==ALIVE)
            {
                count++;
            }

            // Rules
            // Lonliness
            if ((worldcopy[i][j]==ALIVE) && (count==0 || count ==1))
            {
                world[i][j] = worldcopy[i][j];
                world[i][j] = DEAD;
            }
            // Lives to next gen.
            else if ((worldcopy[i][j]==ALIVE) && (count==2 || count==3))
            {
                world[i][j] = worldcopy[i][j];
                world[i][j] = ALIVE;
            }
            // Death by overcrowding
            else if (worldcopy[i][j]==ALIVE && count >3)
            {
                world[i][j] = worldcopy[i][j];
                world[i][j] = DEAD;
            }
            // Birth
            else if(worldcopy[i][j]==ALIVE && count==3)
            {
                world[i][j] = worldcopy[i][j];
                world[i][j] = ALIVE;
            }
        }
    }
}

void display(vector< vector<int> > &world)
{

    for (int i=0; i <=ROWS; i++)
    {
        for (int j=0; j<=COLS; j++)
        {
            cout << world[i][j];
        }
        cout << endl;
    }
}
