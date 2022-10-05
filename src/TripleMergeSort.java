import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class TripleMergeSort {
    public static void main(String[] args)
    {
        final int[] arr = new int[100];
        IntStream.range(0, 100).forEach(index -> arr[index] = (int) (Math.random() * 500));
        final int[] arrClone = arr.clone();
        final long startTime = System.currentTimeMillis();
        tripleMergeSort(arr, 0, arr.length-1);
        for (int a : arr){
            System.out.print(a + " ");
        }
        System.out.println();
        final long stopTime = System.currentTimeMillis();
        System.out.println(String.format("Executing time: %d ms", stopTime - startTime));

        Arrays.sort(arrClone);
        System.out.println(Arrays.equals(arr, arrClone));
    }

    public static void tripleMergeSort(final int[] arr, final int leftIndex, final int rightIndex) throws NullPointerException, ArrayIndexOutOfBoundsException{
        if (arr == null) throw new NullPointerException("arr cannot be null");
        if (leftIndex < 0 || rightIndex >= arr.length) throw new ArrayIndexOutOfBoundsException("Incorrect indexes passed");
        if (leftIndex < rightIndex){
            final int midIndex_1 = leftIndex + (rightIndex - leftIndex) / 3;
            final int midIndex_2 = leftIndex + ((rightIndex - leftIndex) / 3) * 2;

            tripleMergeSort(arr, leftIndex, midIndex_1);
            tripleMergeSort(arr, midIndex_1+1, midIndex_2);
            tripleMergeSort(arr, midIndex_2+1, rightIndex);
            tripleMerge(arr, leftIndex, midIndex_1, midIndex_2, rightIndex);
        }
    }

    private static void tripleMerge(final int[] arr, final int leftIndex, final int midIndex_1, final int midIndex_2, final int rightIndex){
        final int[] firstSubarray = Arrays.copyOfRange(arr, leftIndex, midIndex_1 + 1);
        final int[] secondSubarray = Arrays.copyOfRange(arr, midIndex_1 + 1, midIndex_2 + 1);
        final int[] thirdSubarray = Arrays.copyOfRange(arr, midIndex_2 + 1, rightIndex + 1);

        final int[][] subarrays = {firstSubarray, secondSubarray, thirdSubarray};

        final int[] indexes = new int[subarrays.length + 1];
        indexes[indexes.length-1] = leftIndex;
        List<Integer> remainingIndexes = IntStream.range(0, 3).filter(index -> indexes[index] < subarrays[index].length).boxed().toList();
        while(!remainingIndexes.isEmpty()){
            final int min = Collections.min(remainingIndexes.stream().map(index -> subarrays[index][indexes[index]]).toList());
            for (final int index : remainingIndexes){
                if (subarrays[index][indexes[index]] == min){
                    arr[indexes[indexes.length-1]] = min;
                    ++indexes[indexes.length-1];
                    ++indexes[index];
                    break;
                }
            }
            remainingIndexes = IntStream.range(0, 3).filter(index -> indexes[index] < subarrays[index].length).boxed().toList();
        }
    }

}
