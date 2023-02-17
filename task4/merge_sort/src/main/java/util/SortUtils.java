package util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortUtils {
    public static <T> void mergeSort(List<T> list, Comparator<T> comparator) {
        for (int size = 1; size < list.size(); size *= 2) {
            for (int j = 0; j < list.size() - size; j += 2 * size) {
                merge(list,
                        new ArrayList<>(list),
                        comparator,
                        j,
                        j + size - 1,
                        j + size,
                        Math.min(j + 2 * size - 1, list.size() - 1));
            }
        }
    }

    private static <T> void merge(List<T> list,
                                  List<T> tempList,
                                  Comparator<T> comparator,
                                  int leftStart,
                                  int leftEnd,
                                  int rightStart,
                                  int rightEnd) {

        for (int i = leftStart; i <= rightEnd; i++) {
            tempList.set(i, list.get(i));
        }

        int left = leftStart;
        int right = rightStart;

        for (int i = leftStart; i <= rightEnd; i++) {
            if (left > leftEnd) {
                list.set(i, tempList.get(right++));
            } else if (right > rightEnd) {
                list.set(i, tempList.get(left++));
            } else if (comparator.compare(tempList.get(left), tempList.get(right)) < 0) {
                list.set(i, tempList.get(left++));
            } else {
                list.set(i, tempList.get(right++));
            }
        }
    }
}
