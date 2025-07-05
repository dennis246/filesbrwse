package util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class MathUtil {

    public static List<Integer> numStore = new ArrayList<>();
    public static int regPassCount = 0;

    public static void primeSeq(int from, int to) {

        int[] unitDivs = { 2, 3, 5 };
        for (int i = from; i < to; i++) {

            /*
             * if ( (i / 1 == i && i % 2 != 0 && i % 3 != 0 && i % 5 != 0)) {
             * System.out.print(i + "\s");
             * }
             */
            int passCounts = 0;
            if (i / 1 == i && i / 1 != 1) {
                passCounts += 1;
            }

            for (int d = 0; d < unitDivs.length; d++) {

                if (i == unitDivs[d] || i % unitDivs[d] != 0) {
                    passCounts += 1;
                }
            }

            if (passCounts == 1 + unitDivs.length) {
                System.out.print(i + "\s");
            }

        }
    }

    public static boolean checkIfPrime(int num) {
        int[] unitDivs = { 2, 3, 5 };

        int passCounts = 0;
        if (num / 1 == num && num / 1 != 1) {
            passCounts += 1;
        }

        for (int d = 0; d < unitDivs.length; d++) {

            if (num == unitDivs[d] || num % unitDivs[d] != 0) {
                passCounts += 1;
            }
        }

        if (passCounts == 1 + unitDivs.length) {
            // System.out.print(num + "is prime!");
            return true;
        } else {
            return false;
        }

    }

    public static void fibo(int from, int to) {

        int[] pool = { 0, 1 };
        System.out.print(pool[0] + "\s" + pool[1] + "\s");

        for (int i = 0; i < 12; i++) {

            int res = pool[0] + pool[1];
            System.out.print(res + "\s");

            pool[0] = pool[1];
            pool[1] = res;

        }
    }

    public static void primeFactorization(int target) {
        // factorStatment += target + " = ";

        if (checkIfPrime(target)) {
            System.out.print(target + " is prime");
            return;
        }
        unitDivision(target, 2);
        int index = 0;
        for (var item : numStore) {
            index += 1;
            System.out.print(item + (index != numStore.size() ? "x" : ""));
        }
    }

    public static void unitDivision(int target, int div) {
        if (target == 0 || target < div) {
            return;
        }

        int quot = 0;
        if (target % div == 0) {
            quot = target / div;
            numStore.add(div);

            if (quot > 0) {
                unitDivision(quot, div);
            }

        } else {
            // target = quot;
            unitDivision(target, div + 1);
        }

    }

    public static int GCF(int num1, int num2) {
        int gtrNum = 0;
        int lsrNum = 0;

        if (num1 > num2) {
            gtrNum = num1;
            lsrNum = num2;
        } else if (num1 < num2) {
            lsrNum = num1;
            gtrNum = num2;
        }

        if(lsrNum == 0){
            return gtrNum;
        }

        regReduce(gtrNum, lsrNum);
        // System.out.print("GCF of " + num1 + " and " + num2 + " is " +
        // numStore.get(numStore.size() - 1));
        int res = numStore.get(numStore.size() - 1);
        numStore = new ArrayList<>();
        return res;

    }

    public static void regReduce(int gtrNum, int lsrNum) {

        int res = gtrNum - lsrNum;

        if (res == 0) {
            numStore.add(lsrNum);
            return;
        }

        if (res > lsrNum) {
            numStore.add(lsrNum);
            regReduce(res, lsrNum);
        } else if (res <= lsrNum) {
            regReduce(lsrNum, res);
        }

        // return res;

    }

    private static void findGCF(List<Integer> numList) {

        // if(numList.size() > 1)
        int num1 = numList != null ? numList.get(0) : 0;
        int num2 = numList != null && numList.size() > 1 ? numList.get(1) : 0;
        int res = 0;
        for (int i = 1; i < numList.size(); i++) {

            res = GCF(num1, num2);

            if (i == numList.size() - 1) {
                break;
            }

            num1 = res;
            num2 = numList.get(i + 1);

        }

        System.out.print("GCF of set is " + res);

    }

    private static void findLCM(List<Integer> numList) {

        // if(numList.size() > 1)
        int num1 = numList != null ? numList.get(0) : 0;
        int num2 = numList != null && numList.size() > 1 ? numList.get(1) : 0;
        int resGCF = 0;
        int resLCM = 0;
        for (int i = 1; i < numList.size(); i++) {

            resGCF = GCF(num1, num2);

            resLCM = num1 * num2 / resGCF;

            if (i == numList.size() - 1) {
                break;
            }

            num1 = resLCM;
            num2 = numList.get(i + 1);

        }

        System.out.print("LCM of set is " + resLCM);

    }

    private static int arrayToInteger(Integer[] numArr) {
        int num = 0;

        for (int i = 0, pf = numArr.length - 1; i < numArr.length; i++, pf--) {
            num += numArr[i] * pow(pf, 10);
        }

        return num;
    }

    private static BigInteger arrayToBigInteger(Integer[] numArr) {
        BigInteger num = BigInteger.ZERO;

        for (int i = 0, pf = numArr.length - 1; i < numArr.length; i++, pf--) {
            // num += numArr[i] * pow(pf,10);
            BigInteger p1 = new BigInteger(String.valueOf(numArr[i]));
            BigInteger p2 = powBig(pf, 10);
            BigInteger current = p1.multiply(p2);
            num = num.add(current);
        }

        return num;
    }

    static BigInteger powBig(int factor, int base) {

        if (factor == 0) {
            return BigInteger.ONE;
        }

        BigInteger baseBig = new BigInteger(String.valueOf(base));
        BigInteger res = baseBig;
        for (int i = 1; i < factor; i++) {
            // res *= base;
            res = res.multiply(baseBig);
        }

        return res;
    }

    static int pow(int factor, int base) {

        if (factor == 0) {
            return 1;
        }

        int res = base;
        for (int i = 1; i < factor; i++) {
            res *= base;
        }

        return res;
    }

    public static int sumOf(Integer[] numArr) {
        int sum = 0;
        for (int i = 0; i < numArr.length; i++) {
            sum += numArr[i];
        }

        return sum;
    }


    public static void main(String a[]) {
        // prime(1, 100);
        // System.out.print("\n\n");
        // fibo(1, 100);
        // primeFactorization(18);

       // findGCF(List.of(1,4, 0));
        findLCM(List.of(10,18,25,21));

    }

}