package util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class SortUtils {
    public static <T> void mergeSort(List<T> list, Comparator<T> comparator) {
        int leftEnd, left, rightStart, rightEnd, right;
        var tempList = new ArrayList<>(list);

        for (int size = 1; size < list.size(); size *= 2) {
            for (int leftStart = 0; leftStart < list.size() - size; leftStart += 2 * size) {
                leftEnd = leftStart + size - 1;
                left = leftStart;
                rightStart = leftStart + size;
                rightEnd = Math.min(leftStart + 2 * size - 1, list.size() - 1);
                right = rightStart;

                for (int i = leftStart; i <= rightEnd; i++) {
                    tempList.set(i, list.get(i));
                }

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
    }
}
