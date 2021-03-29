import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class RadixSort
{

    /*
    --------------------------------------------------------------------------------------------
    Drew Pulliam - DTP180003
    CS3345 Project 4
    --------------------------------------------------------------------------------------------
    */

    public RadixSort() {
        
    }

    /**
     * sort an integer list based on the specified digit 
     * exp = 1 for 1's digit, 10 for 10's, etc.
     * @param lis[] the list to sort
     * @param exp the digit to sort
     * @return int[] sorted version of input list
     */
    public static int[] digitSort(int lis[], int exp) {
        int sorted[] = new int[lis.length];
        int digitList[] = new int[10];
        Arrays.fill(digitList, 0); // make sure all initialized to 0

        // add one to each digit in the digit list
        for (int i: lis){
            digitList[(i/exp)%10] += 1;
        }

        // use digitList to give positions in sorted[] output list
        for (int i = 1; i < digitList.length; i++){
            digitList[i] += digitList[i - 1];
        }

        for (int i = lis.length - 1; i >= 0; i--){
            sorted[digitList[ (lis[i]/exp)%10 ] - 1] = lis[i]; 
            digitList[ (lis[i]/exp)%10 ]--; // subtract one from digitList as one position has been filled
        }

        return sorted;
    }

    /**
     * sort an integer list using RadixSort
     * @param lis[] the list to sort
     * @return int[] sorted version of input list
     */
    public static int[] radixSort(int lis[]) {

        // find number with most digits in lis[]
        int maxDigits = Math.abs(lis[0]);
        for(int i: lis){
            if (Math.abs(i) > maxDigits){
                maxDigits = Math.abs(i);
            }
        }

        // maxDigits now contains the number with max digits in lis[]
        // stop digit sorting after doing that many digits
        for(int i = 1; maxDigits/i > 0; i *= 10){
            lis = digitSort(lis, i);
        }

        return lis;
    }

    
    public static void main( String [ ] args ) throws IOException {
        PrintWriter output = new PrintWriter(new File("output.txt"));
        Scanner input = new Scanner(new File("input.txt"));

        // read everything from the file into an ArrayList because we don't know how many integers there are
        ArrayList<Integer> list = new ArrayList<Integer>();
        while(input.hasNextLine()){
            String line = input.nextLine();
            list.add(Integer.parseInt(line));   // this line will throw an exception if the file is not only integers
        }

        // convert the ArrayList into an int[] for easier use
        int[] lis = new int[list.size()];
        for(int i = 0; i < list.size(); i++){
            lis[i] = list.get(i);
        }
        
        System.out.println("Initial list from input.txt");
        for (int i: lis){
            System.out.print(i+", ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Final list after RadixSort");
        int[] sorted = radixSort(lis);
        for (int i: sorted){
            System.out.print(i+", ");
            output.println(i);  // print output to output.txt
        }
        System.out.println();

        output.close();
        input.close();
    }
}