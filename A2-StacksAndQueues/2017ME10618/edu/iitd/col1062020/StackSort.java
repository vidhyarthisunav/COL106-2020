//package edu.iitd.col1062020;

//import java.util.Scanner;

public class StackSort{

    StackSort(){}
    /*public static void main(String[] args) {
        Scanner x = new Scanner(System.in);
        int n = x.nextInt();
        int[] A = new int[n];
        for(int i=0;i<n;i++){
            A[i] = x.nextInt();
        }
        x.close();
        String[] S = sort(A);
        for(int i = 0; i < S.length; i++){
            System.out.println(S[i]);
            //for(int j = 0; j <S[0].length; j++)
                
        }
    }*/
    static String[] sort(int[] A) {
        
        int n = A.length;
        int[] S = new int[n];
        String[] ans = new String[2*n];
        MyStack<Integer> T = new MyStack<Integer>();
        T.push(A[0]);
        ans[0] = "PUSH";
        int c = 1, d = 0;

        for(int i = 1; i < A.length ;i++){
            try {
                if(A[i] < T.top()){
                    T.push(A[i]);
                    ans[c] = "PUSH";
                    c++;
                }
                else{
                    while(!T.isEmpty() && A[i] > T.top()){
                        S[d] = T.pop();
                        d++;
                        ans[c] = "POP";
                        c++;
                    }
                    T.push(A[i]);
                    ans[c] = "PUSH";
                    c++;
                }
            } catch (EmptyStackException e) {
                e.printStackTrace();
            }
        }
        while(!T.isEmpty()){
            try {
                S[d] = T.pop();
            } catch (EmptyStackException e) {
                e.printStackTrace();
            }
            d++;
            ans[c] = "POP";
            c++;
        }
        int j = 1;
        for(j = 1;j < n;j++){
            if(S[j]<S[j-1]){//if the string is not sorted
                String[] P = new String[1];
                P[0] = "NOTPOSSIBLE";
                return P;
            }
        }
        return ans;
    }

    static String[][] kSort(int[] A){
        
        int n = A.length;
        int[] S = new int[n];
        //String[] ans = new String[2*n];
        MyStack<Integer> T = new MyStack<Integer>();
        MyStack<String[]> U = new MyStack<String[]>();
        // T.push(A[0]);
        // ans[0] = "PUSH";
        // int c = 1, d = 0,
        int counter = 0;

        while(true){
            String[] ans = new String[2*n];
            T.push(A[0]);
            ans[0] = "PUSH";
            int c = 1, d = 0;
            counter++;
            for(int i = 1; i < A.length ;i++){
                try {
                    if(A[i] < T.top()){
                        T.push(A[i]);
                        ans[c] = "PUSH";
                        c++;
                    }
                    else{
                        while(!T.isEmpty() && A[i] > T.top()){
                            S[d] = T.pop();
                            d++;
                            ans[c] = "POP";
                            c++;
                        }
                        T.push(A[i]);
                        ans[c] = "PUSH";
                        c++;
                    }
                } catch (EmptyStackException e) {
                    e.printStackTrace();
                }
            }

            while(!T.isEmpty()){
                try {
                    S[d] = T.pop();
                } catch (EmptyStackException e) {
                    e.printStackTrace();
                }
                d++;
                ans[c] = "POP";
                c++;
            }
            //for(int i =0; i<ans.length;i++)
            //    System.out.println(ans[i]);
            U.push(ans);
            
            //if the array is sorted, break
            int j = 1;
            for(j =1;j<n;j++){
                if(S[j]<S[j-1]){
                    break;}
            }
            if(j==n){
                break;}
          
            for(int i = 0;i<S.length;i++)
                A[i] = S[i];

            // T.push(A[0]);
            // ans[0] = "PUSH";
            // c = 1; d = 0;
        }
        String[][] W = new String[counter][2*n];
        for(int i = counter-1; i > -1; i--){
            try{
                String[] Y = U.pop();
                for(int j = 0; j < 2*n; j++){
                    W[i][j] = Y[j];
                }
            } 
            catch (EmptyStackException e) {
                e.printStackTrace();
            }
        }
        return W;
    }
}