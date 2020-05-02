//import java.util.Scanner;

//package edu.iitd.col1062020;

public class MyCalculator{

    MyCalculator(){}
    /*public static void main(String[] args) {
        Scanner x = new Scanner(System.in);
        String S = x.nextLine();
        System.out.println(calculate(S));
    }*/

    static int calculate (String S){
        /*String S = new String();
        Scanner x = new Scanner(System.in);
        S = x.nextLine();*/

        //remove whitespaces
        String S0 = new String();
        for(int i = 0; i < S.length(); i++){
            if(S.charAt(i) == ' ')
                continue;
            S0 = S0 + S.charAt(i);
        }

        MyStack<Integer> T = new MyStack<Integer>();
        int i = 0;
        while(i < S0.length()){
            String temp = new String();
            if(S0.charAt(i) == '('){
                T.push(i);
                i++;
            }
            else if(S0.charAt(i) == ')'){

                int start = 0;
                try {
                    start = T.pop() + 1;
                } 
                catch (EmptyStackException e) {
                    e.printStackTrace();
                }
                int end = i;
                int ans = SolveLine(S0.substring(start, end));
                //System.out.println(ans);
                temp = S0.substring(0, start-1) + ans;
                i = temp.length();
                temp = temp + S0.substring(end+1, S0.length());
                S0 = temp;
                //System.out.println(temp);
            }
            else
                i++;
        }
        return SolveLine(S0);
    }

    public static int SolveLine(String S0){

        //solve all the * operations and put the new expression in a new string
        String S1 = new String();
        int i = 0, counter = 0, j = 0;
        while(i < S0.length()){

            if(S0.charAt(i) == '+' || S0.charAt(i) == '-' || i == S0.length() - 1){
                S1 = S1 + S0.substring(counter, i+1);
                counter = i+1;
            }
            if(S0.charAt(i) == '*'){
                int  product = 1;
                for(j = i + 1; j < S0.length(); j++)
                    if(S0.charAt(j) == '+' || S0.charAt(j) == '-')
                        break;

                String N = S0.substring(counter, j);
                String[] M = N.split("\\*");
                //int[] A = new int[M.length];
                for(int l = 0; l < M.length;l++)
                    product *= Integer.parseInt(M[l]);
                S1 = S1 + product;
                counter = j;
                i = j;
                continue;
            }
            i++;
        }//System.out.println(S1);

        //check for all the +- and -- operators
        for(int k = 0; k < S1.length()-1; k++){
            if(S1.charAt(k+1) == '-'){
                if(S1.charAt(k) == '+'){
                    String temp = new String();
                    temp = S1.substring(0, k) + "-" + S1.substring(k+2,S1.length());
                    S1 = temp;
                }
                else if(S1.charAt(k) == '-'){
                    String temp = new String();
                    temp = S1.substring(0, k) + "+" + S1.substring(k+2,S1.length());
                    S1 = temp;
                }              
            }
        }

        //check if there is a - sign at start of the string, if yes, fix a zero at the starting
        //to avoid creation of an empty substring
        if(S1.charAt(0) == '-'){
            String temp = new String();
            temp = "0" + S1;
            S1 = temp;
        }

        //solve all the + and - operators
        String[] num = S1.split("\\+|\\-");
        int[] Ar = new int[num.length];
        for(int k = 0; k < num.length;k++){
            Ar[k] = Integer.parseInt(num[k]);
        }
        int c = 1, ans = Ar[0];
        for(int k = 0; k < S1.length();k++){
            if(!Character.isDigit(S1.charAt(k))){
                if(S1.charAt(k) == '+')
                    ans = ans + Ar[c];
                else if(S1.charAt(k) == '-')
                    ans = ans - Ar[c];
                c++;}
        }
        return ans;
    }
}