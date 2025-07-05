package util;

import java.io.File;
import java.math.BigInteger;

public class ContainerUtil {

    enum SortOrder {
        Asc, Desc
    }

    public static Integer[] distinct(Integer[] arr) {
        // Integer[] sdp = { 3, 3, 4, 4, 5, 3, 7, 9 };
        Integer[] checked = new Integer[arr.length];
        checked[0] = arr[0];
        int distinctSize = 0;

        for (int i = 1, pi = 1; i < arr.length; i++) {

            if (!contains(checked, arr[i])) {
                checked[pi] = arr[i];
                pi++;
            }
        }

        for (var x : checked) {
            if (x != 0) {
                distinctSize += 1;
            }
        }

        Integer[] cpArr = new Integer[distinctSize];

        for (int i = 0, pi = 0; i < arr.length; i++) {
            if (contains(cpArr, arr[i])) {
                continue;
            } else {

                cpArr[pi] = arr[i];
                if (pi == cpArr.length - 1) {
                    break;
                }
                pi++;

            }
        }

        return cpArr;
    }

    static boolean contains(Integer[] arr, int element) {

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] == element) {
                return true;
            }
        }
        return false;
    }

    public static Integer[] swap(Integer[] e) {
        int temp = e[0];
        if (e[0] > e[1]) {
            e[1] = e[0];
            e[0] = temp;
        }
        return e;
    }

    public static Integer[] slice(Integer[] e, int from, int to) {

        int size = to > 0 && to > from ? to - from : from;
        Integer[] e1 = new Integer[size];
        for (int i = 0, j = from; i <= size - 1; j++, i++) {
            e1[i] = e[j];
            /*
             * if (to != 0 && i == (to - 1)) {
             * break; || j <= e.length - 1
             * }
             */
        }

        return e1;

    }

    public static Integer[] copyTo(Integer[] des, int desFrom, Integer[] src, int srcFrom) {

        Integer[] consDes = new Integer[des.length];
        if (src.length > des.length) {

            // check vacant spaces

            int vcount = vacantCount(des);

            if (vcount > 0) {
                des = distinct(des);
                des = defrag(des, vcount);
            }

            // resize des
            int diff = src.length - des.length;
            consDes = new Integer[des.length + diff];
        } else {
            consDes = des;
        }

        for (int d = 0, pi = desFrom; d < src.length; d++, pi++) {
            consDes[pi] = src[d];
        }
        return consDes;

    }

    public static int vacantCount(Integer[] arr) {
        int vcount = 0;

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] == null) {
                vcount += 1;
            }
        }

        return vcount;
    }

    public static Integer[] defrag(Integer[] arr, int actsize) {
        Integer[] anewArr = new Integer[actsize];

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] != 0) {
                // vcount += 1;
                anewArr[i] = arr[i];
            }
        }

        return anewArr;
    }

    public static Integer[] fullCopy(Integer[] arr) {
        Integer[] cpArr = new Integer[arr.length];

        for (int i = 0; i <= arr.length - 1; i++) {
            cpArr[i] = arr[i];
        }

        return cpArr;

    }

    public static Integer[] purgeSort(Integer[] e, SortOrder sortOrder) {
        Integer[] e2 = new Integer[e.length];
        for (int i = 0, pi = 0; i < e.length; i++) {

            if (e2[e2.length - 1] != null) {
                break;
            }

            Integer pe = null;
            if (sortOrder.equals(SortOrder.Desc)) {
                pe = maxOfArray(e);
            } else {
                pe = minOfArray(e);
            }

            e = removeItem(e, pe);
            e2[pi] = pe;
            pi++;
            --i;
        }

        return e2;

    }

    public static Integer[] removeItem(Integer[] arr, int item) {

        Integer[] narr = new Integer[arr.length - 1];
        try {
            for (int i = 0, pi = 0; i < arr.length; i++) {
                if (arr[i] == item) {
                    continue;
                } else {
                    narr[pi] = arr[i];
                    pi++;
                }
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return narr;
    }

    public static int minOfArray(Integer[] e) {

        int min = 0;
        try {
            min = e[0];
            for (int j = 0; j < e.length; j++) {

                if (e[j] < min) {
                    min = e[j];
                }
            }
        } catch (Exception e1) {
            System.out.print(e1.toString());
        }

        return min;
    }

    public static int maxOfArray(Integer[] e) {

        int max = 0;
        try {
            max = e[0];
            for (int j = 0; j < e.length; j++) {

                if (e[j] > max) {
                    max = e[j];
                }
            }
        } catch (Exception e1) {
            System.out.print(e1.toString());
        }

        return max;
    }

    public static char[][] addToCharArray2D(char[][] target, char[] item) {
        char[][] cArray = (char[][]) target;
        char[][] cArrayInc = new char[cArray.length + 1][cArray.length + 1];
        for (int i = 0; i < cArray.length; i++) {
            cArrayInc[i] = cArray[i];
        }
        // cArrayInc = cArray;
        cArrayInc[cArrayInc.length - 1] = item;

        return cArrayInc;
    }

    public static char[] addToCharArray(char[] target, char item) {
        char[] cArray = (char[]) target;
        char[] cArrayInc = new char[cArray.length + 1];
        for (int i = 0; i < cArray.length; i++) {
            cArrayInc[i] = cArray[i];
        }
        // cArrayInc = cArray;
        cArrayInc[cArrayInc.length - 1] = item;

        return cArrayInc;

    }

    public static char[] addAllToCharArray(char[] mainArr, char[] subArr) {

        for (int i = 0; i < subArr.length; i++) {
            mainArr = addToCharArray(mainArr, subArr[i]);
        }

        return mainArr;

    }

    public static Object[] addAll(Object[] mainArr, Object[] subArr) {
        for (int i = 0; i < subArr.length; i++) {
            mainArr = add(mainArr, subArr[i]); 
        }

        return mainArr;
    }

    public static Integer[] addToIntegerArray(Integer[] target, Integer item) {
        Integer[] cArray = (Integer[]) target;
        Integer[] cArrayInc = new Integer[cArray.length + 1];
        for (int i = 0; i < cArray.length; i++) {
            cArrayInc[i] = cArray[i];
        }
        // cArrayInc = cArray;
        cArrayInc[cArrayInc.length - 1] = item;

        return cArrayInc;

    }

    public static String[] addToStringArray(String[] target, String item) {
        String[] cArray = (String[]) target;
        String[] cArrayInc = new String[cArray.length + 1];
        for (int i = 0; i < cArray.length; i++) {
            cArrayInc[i] = cArray[i];
        }
        // cArrayInc = cArray;
        cArrayInc[cArrayInc.length - 1] = item;

        return cArrayInc;

    }

    public static Object[] add(Object[] target, Object item) {
       
        Object[] cArray = target;
        Object[] cArrayInc = new Object[cArray.length + 1]; 
        for (int i = 0; i < cArray.length; i++) {
            cArrayInc[i] = cArray[i];
        }
        // cArrayInc = cArray;
        cArrayInc[cArrayInc.length - 1] = item;

        return cArrayInc;

    }

    public static <E> E[] remove(E[] target, int from) {

        E[] cArray = (E[]) target;
        Object[] cArrayInc = new Object[cArray.length - 1];
        for (int i = 0; i < cArray.length; i++) {

            if (i == from) {
                continue;
            }

            cArrayInc[i] = cArray[i];
        }

        return (E[]) cArrayInc;

    }

    static <E> int has(E[] arr, E item) {

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] == item) {
                return 1;
            }
        }
        return 0;
    }

}
