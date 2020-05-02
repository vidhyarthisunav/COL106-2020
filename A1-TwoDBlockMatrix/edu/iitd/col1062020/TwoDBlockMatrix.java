import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

class IncompatibleDimensionException extends Exception{}
class SubBlockNotFoundException extends Exception{}
class InverseDoesNotExistException extends Exception{}

public class TwoDBlockMatrix{
    /*public static void main(String[] args) throws Exception{
        File A = new File("textfile1.txt");
        InputStream in = null;
        try {
            in = new FileInputStream(A);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //final TwoDBlockMatrix B = buildTwoDBlockMatrix(new Scanner(new File("textfile2.txt")));
        TwoDBlockMatrix G = buildTwoDBlockMatrix(in);
        TwoDBlockMatrix T = G.getSubBlock(1, 1, 4, 3);
        //String S = P.toString();
        //System.out.print(S);
    }*/
    
    float[][] matrix; // row_size by column_size matrix

    static TwoDBlockMatrix buildTwoDBlockMatrix(InputStream in){

        String content_str = "";
			int content;
			try {
			while ((content = in.read()) != -1) {
				content_str = content_str + (char)content;
			}
		}	catch (Exception e) {
			e.printStackTrace();
        }
        Scanner x = new Scanner(content_str);
        
        float[][] matrix_full = new float[10][10];

        int row_size = 0, column_size = 0;

        while(x.hasNext()){
            
            String current_line = x.nextLine();

            int row_index, column_index;
            String[] splited_strings = current_line.split("\\s");
            /*for(int i=0;i<splited_strings.length;i++)
                    System.out.print(splited_strings[i]+"\n");*/
            //System.out.print(current_line+"\n");
            row_index = Integer.valueOf(splited_strings[0].charAt(0))-48;
            column_index = Integer.valueOf(splited_strings[1].charAt(0))-48;
            //System.out.print(row_index+"hn"+column_index);
            int temp_row = row_index;
            current_line = x.nextLine(); 
            
            int counter =0;

            while(current_line.charAt(0)!='#'){

                counter = counter + 1;
                int cut = 0;
                for(int i=current_line.length()-1;i>-1;i--)
                    if(Character.isDigit(current_line.charAt(i))){
                        cut = current_line.length()-1-i;
                        break;
                    }
                String trimmed_string = current_line.substring(0,current_line.length()-cut);
                //System.out.print(trimmed_string+"this");
                splited_strings = trimmed_string.split("\\s");
                /*for(int i=0;i<splited_strings.length;i++)
                    System.out.print(splited_strings[i]+"hnn\n");*/

                if(column_size < splited_strings.length + column_index - 1)
                    column_size = splited_strings.length + column_index - 1;

                for(int i=0 ; i < splited_strings.length ; i++){
                    matrix_full[row_index-1][column_index-1+i] = Float.parseFloat(splited_strings[i]);//<-this line here
                }

                row_index = row_index + 1;
                current_line = x.nextLine();
            }
            if(row_size < temp_row + counter - 1)
                row_size = temp_row + counter - 1;
        }
        x.close();

        float[][] final_matrix = new float[row_size][column_size];
        for(int i=0;i<row_size;i++)
            for(int j=0;j<column_size;j++)
                final_matrix[i][j] = matrix_full[i][j];

        /*for(int i=0;i<row_size;i++){
            for(int j=0;j<column_size;j++)
                System.out.print(final_matrix[i][j] + " ");
            System.out.print("\n");}*/
        
        TwoDBlockMatrix matrix = new TwoDBlockMatrix(final_matrix);
        return matrix;
    }

    TwoDBlockMatrix(float[][]final_matrix){
        this.matrix = final_matrix;}

    public String toString(){

        DecimalFormat Z = new DecimalFormat("0.00");
        String S = "";
        float[][] F = this.matrix;
        int row_start=-1,column_start=-1,row_end,column_end,row_size = F.length ,column_size = F[0].length;
        //int count = 3; 
        while(true){
            int i,j;
            int V=0;
            for( i=0;i<row_size;i++){
                for( j=0;j<column_size;j++){
                    if(F[i][j] != 0){
                        column_start = j;
                        row_start = i;
                        V=1;
                        break;
                    }
                }
                if(V==1)
                    break;
            }
            if(V==0)
                break;
            //System.out.print(column_start+" "+row_start+"\n");
            column_end = column_size - 1;
            for( i=column_start+1; i<column_size; i++){
                if(F[row_start][i]==0){
                    column_end = i-1;
                    break;}
            }
            
            row_end = row_size-1;
            int H=0;
            for( i = row_start+1; i< row_size; i++){
                for( j = column_start; j<=column_end; j++){
                    if(F[i][j] == 0){
                        row_end = i-1;
                        H=1;
                        break;
                    }
                }
                if(H==1)
                    break;
            }
            int A = row_start + 1;
            int B = column_start + 1;
            S = S + A + " " + B + "\n";
            for( i = row_start; i<=row_end; i++){
                for( j = column_start; j<=column_end; j++){
                    S = S + Z.format(F[i][j]) + " ";
                    F[i][j] = (float)0;
                }
                S = S +";\n";
            }
            S = S + "#\n";
            //count = count -1;
        }
        return S;
    }
    
    TwoDBlockMatrix transpose(){
        float[][] Mat = this.matrix;
        float[][] trans = new float[Mat[0].length][Mat.length];
        for(int i=0;i<Mat.length;i++){
            for(int j=0;j<Mat[0].length;j++){
                trans[j][i] = Mat[i][j];
            }
        }
        /*for(int i=0;i<Mat[0].length;i++){
                for(int j=0;j<Mat.length;j++){
                    System.out.print(trans[i][j]+" ");
                }
                System.out.print("\n");
            }*/
        TwoDBlockMatrix Obj = new TwoDBlockMatrix(trans);
        return Obj;
    }
    
    TwoDBlockMatrix multiply (final TwoDBlockMatrix other) throws IncompatibleDimensionException{
        
        float[][] one = this.matrix;
        int row1 = one.length, col1 = one[0].length, row2 = other.matrix.length, col2 = other.matrix[0].length;
        float[][] multiply = new float[row1][col2];
        if(col1 != row2){
           throw new IncompatibleDimensionException();
        }
        
        for(int i=0;i<row1;i++){
            for(int j=0;j<col2;j++){
                multiply[i][j] = 0;
                for(int k=0;k<col1;k++)
                    multiply[i][j] += one[i][k]*other.matrix[k][j];
            }
        }
        
            /*for(int i=0;i<multiply.length;i++){
                for(int j=0;j<multiply[0].length;j++){
                    System.out.print(multiply[i][j]+" ");
                }
                System.out.print("\n");
            }*/
        TwoDBlockMatrix answer = new TwoDBlockMatrix(multiply);
        return answer;               
    }
    TwoDBlockMatrix getSubBlock ( final int row_start, final int col_start, final int row_end, final int col_end)  throws SubBlockNotFoundException{

        float[][] one = this.matrix;
        if(row_start >= row_end || col_start >= col_end || row_end > one.length + 1 || col_end > one[0].length + 1 ){
           throw new SubBlockNotFoundException();
        }

        float[][] SubBlock = new float[row_end-row_start][col_end-col_start];
        for(int i = row_start-1; i<=row_end-2; i++)
            for(int j = col_start-1; j<=col_end-2; j++){
                SubBlock[i-row_start+1][j-col_start+1] = one[i][j];
                //int A =i-row_start+1, B =j-col_start+1;
                //System.out.print(A+" "+B);
            }

        /*for(int i=0;i<SubBlock.length;i++){
                for(int j=0;j<SubBlock[0].length;j++){
                    System.out.print(SubBlock[i][j]+" ");
                }
                System.out.print("\n");
            }*/

        TwoDBlockMatrix answer = new TwoDBlockMatrix(SubBlock);
        return answer;
    }
    TwoDBlockMatrix inverse() throws InverseDoesNotExistException{
        float[][] one = this.matrix;
        int size = one.length;
        if(one.length != one[0].length){
            throw new InverseDoesNotExistException();
        }
        /*float[][] one1 = new float[10][10];
        for(int i=0;i<one.length;i++)
            for(int j=0;j<one[0].length;i++)
                System.out.print(i+" "+j);
                //one1[i][j] = one[i][j];

        float det = determinant(one1,10);
        System.out.print(det);
        if(det == 0){
            throw new InverseDoesNotExistException();
        }*/
        float inverse[][] = new float[size][size];
        
        //initializing the inverse matrix
        for (int i=0; i<size; i++) 
            inverse[i][i] = (float)1;
        
        //constructing the upper triangular matrix
        for(int i=0; i<size; i++){
            //make ii element of the matrix unity
            float scale = one[i][i];
            for(int j = 0; j<size; j++){
                one[i][j] = one[i][j]/scale;
                inverse[i][j] = inverse[i][j]/scale;
            }
           /* for(int z=0;z<size;z++){
                for(int j=0;j<size;j++){
                    System.out.print(one[z][j]+" ");
                }
                System.out.print("\n");
            }*/
            //make the all the next rows next rows element corresponsing to ii equal to zero by subtracting
            for(int k = i+1; k<size;k++){
                float scale2 = one[k][i];
                for(int j = 0; j<size;j++){
                    //System.out.print(one[k][j]+" "+one[k][j]+" "+one[k][i]+" "+one[i][j]+" "+"this one"+"\n");
                    one[k][j] = one[k][j] - scale2*one[i][j];
                    inverse[k][j] = inverse[k][j] - scale2*inverse[i][j];
                }
            }
            //reverse fundae for identity matrix
           /* for(int z=0;z<size;z++){
                for(int j=0;j<size;j++){
                    System.out.print(one[z][j]+" ");
                }
                System.out.print("\n");
            }*/
        }
        /*for(int i=0;i<size;i++){
            for(int j=0;j<size;j++)
                System.out.print(inverse[i][j] + " ");
            System.out.print("\n");}*/
        for(int i = size-1; i>-1; i--){
            for(int k = i-1; k>-1; k--){
                float scale3 = one[k][i];
                one[k][i] = one[k][i]-scale3*one[i][i];
                for(int j=0;j<size;j++)
                    inverse[k][j] = inverse[k][j]-scale3*inverse[i][j];
            }
        }
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                Double num = (double)inverse[i][j];
                if(num.isNaN()){
                    throw new InverseDoesNotExistException();
                }
            }
        }
        /*for(int i=0;i<size;i++){
            for(int j=0;j<size;j++)
                System.out.print(inverse[i][j] + " ");
            System.out.print("\n");}*/
        
        TwoDBlockMatrix answer = new TwoDBlockMatrix(inverse);
        return answer;
    }             
}