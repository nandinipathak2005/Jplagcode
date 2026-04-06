public class Test {

    public static void main(String[] args) {

        int[] arr = {4, 7, 1, 9, 3};

        int total = calculateSum(arr);
        int maximum = findMax(arr);

        double average = total * 1.0 / arr.length;

        System.out.println("Total = " + total);
        System.out.println("Average = " + average);
        System.out.println("Largest = " + maximum);
    }

    public static int calculateSum(int[] a) {
        int s = 0;

        for (int value : a) {
            s += value;
        }

        return s;
    }

    public static int findMax(int[] a) {

        int max = a[0];

        for (int i = 1; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
            }
        }

        return max;
    }
}
