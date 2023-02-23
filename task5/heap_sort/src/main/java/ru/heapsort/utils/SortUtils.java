package ru.heapsort.utils;

import java.util.Comparator;
import java.util.List;

public class SortUtils {
    public static <T> void heapSort(List<T> list, Comparator<T> comparator) {
        for (int i = list.size() / 2; i >= 0; i--){
            siftDown(list, comparator, i, list.size() - 1);
        }

        for(int i = list.size() - 1; i >= 1; i--){
            T temp = list.get(0);
            list.set(0, list.get(i));
            list.set(i, temp);
            siftDown(list, comparator, 0, i - 1);
        }
    }

    private static <T> void siftDown(List<T> list, Comparator<T> comparator, int root, int bottom){
        int maxChild;
        boolean isHeapDone = false;
        while(root * 2 <= bottom && !isHeapDone){
            if(root * 2 == bottom){
                maxChild = root * 2;
            }else if (comparator.compare(list.get(root * 2), list.get(root * 2 + 1)) > 0){
                maxChild = root * 2;
            }else {
                maxChild = root * 2 + 1;
            }

            if(comparator.compare(list.get(root), list.get(maxChild)) < 0){
                T temp = list.get(root);
                list.set(root, list.get(maxChild));
                list.set(maxChild, temp);
                root = maxChild;
            }else {
                isHeapDone = true;
            }
        }
    }
}
