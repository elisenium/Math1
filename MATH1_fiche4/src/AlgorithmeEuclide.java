public class AlgorithmeEuclide {

    public static void main(String[] args) {
        int pgcd1 = pgcd(10,100);
        System.out.println(pgcd1);
    }
    public static int pgcd(int a, int b){
        int r;

        for(;;) {
            r = a % b;
            if (r==0)
                break;
            a = b;
            b = r;
        }
        return b;
    }
}
