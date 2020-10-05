//haakonif
import java.util.Arrays;
import java.util.*;
public class main{
  public static void main(String[] args) {
    int bitsortering;
    int instikkCut;
    if(args.length<2) {
      System.out.println("Bruker standard verdier: NUM_BIT = 8, CUTOFF = 15");
      bitsortering = 8;
      instikkCut = 15;
    }
    else{
      bitsortering = new Integer(args[0]).intValue();
      instikkCut = new Integer(args[1]).intValue();
    }
    VRadix sort = new VRadix(bitsortering, instikkCut);
    //tar tiden for forskjellige verdier av N
    for(int n= 100; n<=10000000; n*=10){
      double[] radixTime = new double[3];
      //kjører vRadix 3 ganger
      for(int i = 0; i<radixTime.length; i++){
        int[] arr = nyttArray(n);
        radixTime[i] = sort.vRadixMulti(arr);
      }
      double[] qtime = new double[3];
      //kjører quicksort 3 ganger
      for(int i = 0; i<qtime.length; i++){
        int[] arr = nyttArray(n);
        double tt = System.nanoTime();
        Arrays.sort(arr);
        qtime[i] = (System.nanoTime() - tt)/1000000.0;
      }
      //sorterer for å finne median
      Arrays.sort(radixTime);
      Arrays.sort(qtime);
      //printer ut resultatet
      System.out.printf("N = %d Radixtime: %1.4f ",n, radixTime[1]);
      System.out.printf("Quicksorttime: %1.4f ",qtime[1]);
      System.out.printf("Speedup: %f\n", qtime[1]/radixTime[1]);
    }
  }
  //metode som produserer like random array med like tall for hver n
  public static int[] nyttArray(int n){
    Random r = new Random(45);
    int[] a = new int[n];
    for(int i= 0; i<a.length; i++){
      a[i] = r.nextInt(n);
    }
    return a;
  }
}
