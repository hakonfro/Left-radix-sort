//haakonif
public class VRadix{
  private static int NUM_BIT;
  private static int INSERT_MAX;
  public VRadix(int sortering, int cutoff){
    NUM_BIT = sortering;
    INSERT_MAX = cutoff;
  }
  public VRadix(){
    this.NUM_BIT = 8;
    this.INSERT_MAX = 15;
  }
  public double vRadixMulti(int[] arr){
    long tt = System.nanoTime();
    //sjekker om arrayet er stort nok til Radixsortering
    if(arr.length< INSERT_MAX){
      insertSort(arr, 0, arr.length-1);
    }else{
      int[] a = arr;
      int[] b = new int[a.length];
      //finner max verdi og finner antall bits den består av
      int max = a[0];
      for(int i : a){
        if(i > max) max = i;
      }
      int numBit = 0;
      while(max >=(1<<numBit)){
        numBit++;
      }
      //sjekker om NUM_BIT er større enn antall bits i max
      int mask = Math.min(numBit, NUM_BIT);
      venstreRadix(a, b, 0, a.length-1, numBit, mask);
    }
    //tar tiden og sjekker om arrayet er sortert så returnerer tiden
    double time = (System.nanoTime() - tt)/1000000.0;
    testSort(arr);

    return time;
  }
  public void venstreRadix(int[] a, int[] b, int left, int right, int leftSortBit, int maskLen){

    //bit definerer antall bitskift vi må gjøre for å se på det mest venstre bits
    int bit = leftSortBit - maskLen;
    int acumVal = 0;
    int j;
    int mask = ((1<<maskLen)-1);
    int[] count = new int[mask+1];
    //count[] =hvor mange det er med de ulike verdiene
    // av dette sifret I a [left..right]
    for(int i = left; i <= right; i++){
      count[((a[i]>>bit) & mask)]++;
    }

    //Teller opp verdiene I count[] slik at count[i] sier hvor i b[] vi skal
    //flytte første element med verdien ‘i’ vi sorterer.
    for(int i = 0; i<count.length;i++){
        j = count[i];
        count[i] = acumVal;
        acumVal += j;
        count[i] += left;
    }
    // Flytter tallene fra a[] til b[] sorter på dette sifferet I a[left..right] for
    // alle de ulike verdiene for dette sifferet
    for(int i = left; i<=right; i++){
      b[count[(a[i]>>bit)&mask]++] = a[i];
    }
    //kopierer de sorterte verdiene fra a[] til b[] sortert på leftsortbit - mask
    System.arraycopy(b, left, a, left, right-left+1);
    if(bit==0) return;
    if(maskLen > bit) maskLen = bit;

      //sjekker om tallene fra left..right er sortert på maskLen(NUM_BIT)
      //finner neste del (left...right) som skal sorteres
      //kaller på instikk hvis den usorterte tall delen er mindre enn INSERT_MAX eller
      //rekursivt dersom delen er større enn INSERT_MAX
      int prev = left;
      for(int i = 0; i < count.length; i++){
        if(count[i]-prev>1){
          if(count[i]- prev < INSERT_MAX){
            insertSort(a,prev,count[i]-1);
          }else{
             venstreRadix(a, b, prev, count[i]-1, bit,maskLen);
          }
        }
        prev = count[i];
    }
  }//end venstreRadix sortering

  //vanlig innstikk sortering
  public void insertSort(int[] a, int left, int right){
    int i, t, max=a.length-1;
    for(int k = left; k< right; k++){
      if(a[k]>a[k+1]){
        t = a[k+1];
        i = k;
        do{
          a[i+1] = a[i];
          i--;
        } while(i>=0 && a[i]>t);
          a[i+1] = t;
      }
    }
  }
  //enkel test metode som sjekker om arrayet har blitt sortert
  public void testSort(int a[]){
    for(int i= 0; i<a.length-1; i++){
      if(a[i]>a[i+1]){
        System.out.printf("Feil på:\n a[%d] %d > a[%d] %d\n", i, a[i], i+1, a[i+1]);
      }
    }
  }
}
