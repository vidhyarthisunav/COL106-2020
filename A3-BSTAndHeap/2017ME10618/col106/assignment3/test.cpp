#include <iostream>
#include <vector>

using namespace std;

int solution(vector<int> &A, int K, int L) {

    int sum = 0;
    int index_1 = 0, index_2 = 0;
    for(int i = 0; i < A.size()-K+1; i++){
        int temp_sum = 0;
        for(int j = i; j < K+i;j++){
            temp_sum += A[j];
        }
        if(temp_sum > sum){
            sum = temp_sum;
            index_1 = i;
            index_2 = K+i-1;
        }
    }

                        int K_first = sum;
    
    

    vector<int> H;
    for(int i = 0; i <A.size();i++){
        if( i < index_1 || i > index_2){
            H.push_back(A[i]);}
    }
    
    sum = 0;
    for(int i = 0; i < H.size()-L+1; i++){
        int temp_sum = 0;
        for(int j = i; j < L+i;j++){
            temp_sum += H[j];
        }
        if(temp_sum > sum){
            sum = temp_sum;
        }
    }
                    int L_second = sum;
    
    index_1 = 0, index_2 = 0;
    sum = 0;
    for(int i = 0; i < A.size()-L+1; i++){
        int temp_sum = 0;
        for(int j = i; j < L+i;j++){
            temp_sum += A[j];
        }
        if(temp_sum > sum){
            sum = temp_sum;
            index_1 = i;
            index_2 = L+i-1;
        }
    }

                int L_first = sum;

    vector<int> J;
    for(int i = 0; i <A.size();i++){
        if( i < index_1 || i > index_2){
            J.push_back(A[i]);}
    }
    sum = 0;
    for(int i = 0; i < J.size()-K+1; i++){
        int temp_sum = 0;
        for(int j = i; j < K+i;j++){
            temp_sum += J[j];
        }
        if(temp_sum > sum){
            sum = temp_sum;
        }
    }
                    int K_second = sum;

    return max(K_first+L_second,L_first+K_second);
}
int main(){
    int N;
    cin >> N;
    vector<int> A(N);
    for(int i =0;i<N;i++){
        cin >> A[i];
    }
    int K = 3, L = 2;
    cout << solution(A,K,L);
    return 0;    
}