class EmptyStackException extends Exception{}

public class MyStack<T>{
    T[] stack;
    /*public static void main(String[] args) {
        MyStack<Integer> S = new MyStack<Integer>();
        for(int i = 0; i<11;i++){
            S.push(i);
        }
        for(int i = 0; i<11;i++){
            System.out.println(S.pop());
        }
    }*/
    MyStack(){
        this.stack = (T[])new Object[10];
        for(int i = 0; i < 10;i++)
            this.stack[i] = null;
    }
    void push(T num){
        int i=0;
        for(i=0;i<this.stack.length;i++)
            if(this.stack[i] == null){
                this.stack[i] = num;
                break;
            }
        if(i == this.stack.length){ //if stack is full
            T[] A = (T[])new Object[2*this.stack.length];
            for(int j = 0;j <A.length;j++){
                if(j<this.stack.length)
                    A[j] = this.stack[j];
                else
                    A[j] = null;
            }
            A[this.stack.length] = num;
            this.stack = A;
        }
    }
    T pop() throws EmptyStackException{
        T num = this.stack[this.stack.length-1];
        for(int i=0;i<this.stack.length;i++)
            if(this.stack[i] == null){
                if(i == 0){
                   throw new EmptyStackException();
                }
                num = stack[i-1];
                this.stack[i-1] = null;
                return num;
            }
        this.stack[this.stack.length-1] = null;
        return num;
    }
    T top() throws EmptyStackException{
        for(int i=0;i<this.stack.length;i++)
            if(this.stack[i] == null){
                if(i == 0){
                    throw new EmptyStackException();
                }
                return this.stack[i-1];
            }
        return this.stack[this.stack.length-1];
    }
    boolean isEmpty(){
        if(this.stack[0] == null)
            return true;
        return false;
    }
}