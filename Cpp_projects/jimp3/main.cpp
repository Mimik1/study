#include <iostream>
#include <sstream>
#include <list>
#include <stack>
#include <map>
#include <vector>
#include <iterator>

using namespace std;

#define LFT_ASSOC 0
#define RGT_ASSOC 1

// Map the different operators: +, -, *, /,= etc
typedef map< string, pair< int,int >> OpMap;
typedef vector<string>::const_iterator cv_iter;
typedef string::iterator s_iter;

const OpMap::value_type assocs[] =
  {  OpMap::value_type( "=", make_pair<int,int>(-1, LFT_ASSOC ) ) ,
     OpMap::value_type( "+", make_pair<int,int>( 0, LFT_ASSOC ) ),
     OpMap::value_type( "-", make_pair<int,int> ( 0, LFT_ASSOC ) ),
     OpMap::value_type( "*", make_pair<int,int> ( 5, LFT_ASSOC ) ),
     OpMap::value_type( "/", make_pair<int,int> ( 5, LFT_ASSOC ) ) };

const OpMap opmap( assocs, assocs + sizeof( assocs ) / sizeof( assocs[ 0 ] ) );

// Test if token is an parenthesesis
bool isParenthesis( const string& token)    {  return token == "(" || token == ")"; }
// Test if token is an operator
bool isOperator( const string& token)
    {  return token == "+" || token == "-" ||  token == "*" || token == "/" || token == "="; }
// Test associativity of operator token
bool isAssociative( const string& token, const int& type)
    { const pair<int,int> p = opmap.find( token )->second;  return p.second == type; }
// Compare precedence of operators.
int cmpPrecedence( const string& token1, const string& token2 )
    {  const pair<int,int> p1 = opmap.find( token1 )->second;
       const pair<int,int> p2 = opmap.find( token2 )->second;
       return p1.first - p2.first; }

// Convert infix expression format into reverse Polish notation
bool infixToRPN( const vector<string>& inputTokens,
                 const unsigned int& size,  vector<string>& strArray )
{
    bool success = true;
    list<string> out;
    stack<string> stack;
    // While there are tokens to be read
    for ( unsigned int i = 0; i < size; i++ )
    {
        // Read the token
        const string token = inputTokens[ i ];
        // If token is an operator
        if ( isOperator( token ) ) {
             // While there is an operator token, o2, at the top of the stack AND
            // either o1 is left-associative AND its precedence is equal to that of o2,
            // OR o1 has precedence less than that of o2,
            const string o1 = token;
            if ( !stack.empty() )  {
                string o2 = stack.top();
                while ( isOperator( o2 ) &&
                        ( ( isAssociative( o1, LFT_ASSOC) &&  cmpPrecedence( o1, o2 ) == 0 ) ||
                        ( cmpPrecedence( o1, o2 ) < 0 ) ) )  {
                    // pop o2 off the stack, onto the output queue;
                    stack.pop();  out.push_back( o2 );
                    if ( !stack.empty() ) o2 = stack.top();
                    else break;
                }
            }
            // push o1 onto the stack.
            stack.push( o1 );
        }
        // If the token is a left parenthesis, then push it onto the stack.
        else if ( token == "(" )  {
            // Push token to top of the stack
            stack.push( token );
        }
        // If token is a right parenthesis ')'
        else if ( token == ")" )  {
            // Until the token at the top of the stack is a left parenthesis,
            // pop operators off the stack onto the output queue.
            string topToken  = stack.top();
            while ( topToken != "(" )  {
                out.push_back(topToken );
                stack.pop();  if ( stack.empty() ) break;
                topToken = stack.top();
            }
            // Pop the left parenthesis from the stack, but not onto the output queue.
            if ( !stack.empty() ) stack.pop();
            // If the stack runs out without finding a left parenthesis,
            // then there are mismatched parentheses.
            if ( topToken != "(" )  {   return false;   }
        }
        // If the token is a number, then add it to the output queue.
        else  {   out.push_back( token );  }
    }
    // While there are still operator tokens in the stack:
    while ( !stack.empty() )  {
        const string stackToken = stack.top();
        // If the operator token on the top of the stack is a parenthesis,
        // then there are mismatched parentheses.
        if ( isParenthesis( stackToken )   )
        { return false; }
        // Pop the operator onto the output queue./
        out.push_back( stackToken );
        stack.pop();
    }
    strArray.assign( out.begin(), out.end() );  return success;
}

void RPNtoAST( vector<string> symbols )
{
    stack<string> st; unsigned int cnt = 0; string tmp;
    // For each symbol
    for ( unsigned int i = 0; i <  symbols.size(); ++i )  {
        const string symbol = symbols[ i ];
        // If the symbol is not an operator push it onto the stack
        if ( !isOperator(symbol) )    {  st.push(symbol);   }
        else  {
            // Symbol is an operator: pop top two entries
            const string val2 = st.top();   st.pop();
            if ( !st.empty() )  {
               // Generate an operator node of the AST
                const string val1 = st.top();  st.pop();
                if (symbol == "=")  {  cout << val1.c_str() << " = " << val2.c_str() << "\n";  }
                else {
                    tmp="tmp"+ to_string(cnt++);
                    if (symbol == "+")
                        {  cout << tmp << " = "<<  val1.c_str() << " + " << val2.c_str() << "\n";  }
                    else if (symbol == "-")
                        {  cout << tmp << " = " << val1.c_str() << " - " << val2.c_str() << "\n";  }
                    else if (symbol == "*")
                        {  cout << tmp  << " = " << val1.c_str() << " * " << val2.c_str() << "\n";  }
                    else if (symbol == "/")
                        {  cout << tmp  << " = " << val1.c_str() << " / " << val2.c_str() << "\n";  }
                    else { cout << "error: unknown operator \n"; return ;}
                 }
            }
            else  {
                if ( symbol == "-" )  { cout <<tmp  << " = " << val2.c_str() << "  * (-1)\n";  }
                else { cout << val2.c_str() << "\n";  }
            }
            // Push result onto stack
           ostringstream s;  s << tmp; st.push( s.str() );
        }
    }
  //  return strtod( st.top().c_str(), nullptr );
}

vector<string>Tokenize( const string& expression )
{
    vector<string> tokens;  string str = "";
    for ( unsigned int i = 0; i < expression.length(); ++i )
    {
        const string token( 1, expression[ i ] );
        if ( isOperator( token ) || isParenthesis( token ) )  {
            if ( !str.empty() )  {  tokens.push_back( str ) ;   }
            str = "";  tokens.push_back( token );
        }
        else  {
            // Append the characters
            if ( !token.empty() && token != " " )
            {  str.append( token );  }
            else {
                if ( str != "" )  {  tokens.push_back( str );  str = "";  }
            }
        }
    }
    if ( str != "" )  {  tokens.push_back( str );  str = "";  }
    return tokens;
}

// Print iterators in a generic way
template<typename T, typename InputIterator>
void Print( const string& message,  const InputIterator& itbegin,
             const InputIterator& itend,  const string& delimiter) {
    cout << message << endl;
    copy( itbegin,  itend,  ostream_iterator<T>(cout, delimiter.c_str()) );
    cout << endl;
}

int main()
{
    string s = "z = x - ( 3 * y )";  //  string s= "((1.5+a)*b-123/c)/2";
    Print<char, s_iter>( "Input expression:", s.begin(), s.end(), "" );
    // Tokenize input expression
    vector<string> tokens = Tokenize( s );
    cout << "Tokens:\n";
    for(unsigned int i = 0; i < tokens.size();  i++){  cout <<  i << ": " << tokens[i] << "\n";  }
    // Generate abstract syntax tree
    vector<string> rpn;
    if ( infixToRPN( tokens, tokens.size(), rpn) )  {
        Print<string, cv_iter>( "RPN:", rpn.begin(), rpn.end(), " " );
        cout << "AST:\n";    RPNtoAST( rpn );
    }
    else {   cout << "Mismatch in parentheses" << endl;  }
    return 0;
}
