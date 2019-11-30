#include<iostream>
#include<set>
#include<vector>

using namespace std;

#define ll long long int 
#define vll vector<ll> 
#define pb push_back
#define loop(i,x,y) for(int i=x;i<y;i++)
#define MAX_LL 1e18
#define MIN_LL -1*1e18
#define pll pair<ll,ll> 
#define mp make_pair
#define F first
#define S second
#define B begin()
#define E end()
#define vpll vector<pll>

ll n,m,k,x,y,z;
vpll v[(int)1e6+100];
ll d[(int)1e6+100];
ll par[(int)1e6+100];
bool vis[(int)1e6+100];
set<pll> st;
pll p;

void dijkstras(){

    st.insert(mp(0,x));
    vis[x] = 1;
    par[x] = x;

    loop(i,0,n)d[i] = MAX_LL;   

    while(st.size()){

        p = *st.B;  

        vis[p.S] = 1;

        if(d[p.S] > p.F)d[p.S] = p.F;
            
        cout<<"@ "<<p.S+1<<" "<<p.F<<endl;

        st.erase(st.B);

        for(auto i:v[p.S])if(!vis[i.F]){

            st.insert(mp(i.S+p.F,i.F));

            par[i.F] = p.S;
        }

    }

    cout<<"dis "<<d[z]<<endl;

    if(d[z] < MAX_LL){

        y = z;

        cout<<" rev path"<<endl;

        while(true){

            cout<<y+1;

            if(par[y] == y)break;
        
            cout<<" ** ";

            y = par[y];
        }

        cout<<endl;
    }
}

int main(){

    cout<<"welcome"<<endl;
    cout<<"enter the number of nodes, number of edges"<<endl;

    cin>>n>>m;

    cout<<"now enter the edges in format x1 --- x2 --- weight "<<endl;

    loop(i,0,m){

        cin>>x>>y>>z;

        x--;
        y--;

        v[x].pb(mp(y,z));
        v[y].pb(mp(x,z));
    }

    cout<<"enter the source and destination "<<endl;
    cin>>x>>z;
    x--;
    z--;

    dijkstras();

}