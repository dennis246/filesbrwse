package util;

import java.util.ArrayList;
import java.util.List;

import util.ContainerUtil;

public class StringUtil {
    // public static void main(String[] args) {

    /*
     * String content =
     * "A program consist of set of instructions which are either variables or functions.\nBy default, external variables and functions have the property that all references to the same concept."
     * ;
     * String searchTerm = "consist of set of instructions";
     * 
     * System.out.println("seq" + (findSequence(content, searchTerm, null) ? "" :
     * "\snot") + "\sfound"
     * + "for statement \n\n" + content);
     * 
     * String replaceTerm = "can be seen as a sequence of steps";
     * String replacedContent = replaceSequence(content, searchTerm, replaceTerm);
     * System.out.println("replaced with: \n\n" + replacedContent);
     * 
     * replaceTerm = "";
     * replacedContent = replaceSequence(content,
     * "By default, external variables and functions have the property that all references to the same concept."
     * , replaceTerm);
     * System.out.println("replaced with: \n\n" + replacedContent);
     */

    /*
     * String replaceTerm = "20, 200, 23440, 21, 45, 506";
     * String replacedContent = replace("functional programming", "al program",
     * "_of_");
     * System.out.println("replaced with: \n\n" + replacedContent);
     * 
     * }
     */

    public static void clearConsoleLine(int len) {
        for (int i = 0; i != len; ++i) {
            System.out.print("\b");
        }
    }

    public static String clearLine(String line, int len) {
        char[] lineArr = line.toCharArray();

        for (int i = 0, clf = lineArr.length - 1; i < len; i++, clf--) {
            lineArr[clf] = '\0';
        }
        return new String(lineArr);
    }

    public static void pad(char with, int times, int pos) {
        // pos -1(prefix) 0(?) 1(postfix)
    }

    private static String replace(String content, String searchTerm, String replaceTerm) {
        String finalContent = null;

        char[] contentArr = content.toCharArray();
        char[] searchTermArr = searchTerm.toCharArray();
        char[] identifiedSeqArr = new char[searchTermArr.length];

        // p1 + m + p2
        int p1EndsAt = contentArr.length;
        int p2StartsFrom = contentArr.length;

        int termIdIndex = 0;
        for (int i = 0; i < contentArr.length; i++) {

            if (identifiedSeqArr.length == 0) {
                termIdIndex = 0;
            }

            for (int j = termIdIndex; j < searchTermArr.length; j++) {
                if (contentArr[i] == searchTermArr[j]) {

                    // index pos
                    if (identifiedSeqArr[0] == 0) {
                        p1EndsAt = i + 1;
                    }

                    identifiedSeqArr[j] = searchTermArr[j];
                    termIdIndex += j;
                    i++;

                    if (identifiedSeqArr[identifiedSeqArr.length - 1] != 0
                            && identifiedSeqArr.length == searchTermArr.length) {
                        // return true;

                        p2StartsFrom = p1EndsAt + searchTermArr.length - 1;

                        // p1 + m + p2
                        char[] p1 = new char[p1EndsAt];
                        System.arraycopy(contentArr, 0, p1, 0, p1.length - 1);
                        char[] p2 = new char[contentArr.length - p2StartsFrom];
                        System.arraycopy(contentArr, p2StartsFrom, p2, 0, p2.length);

                        char[] m = replaceTerm.toCharArray();

                        char[] fcArr = new char[p1.length + m.length + p2.length];

                        System.arraycopy(p1, 0, fcArr, 0, p1.length);
                        System.arraycopy(m, 0, fcArr, p1.length - 1, m.length);
                        System.arraycopy(p2, 0, fcArr, p1.length + m.length, p2.length);

                        finalContent = new String(fcArr);
                        return finalContent;

                    }

                } else {
                    // reset
                    termIdIndex = 0;
                    identifiedSeqArr = new char[searchTermArr.length];
                    break;
                }

            }
        }

        return finalContent;

    }

    private static boolean find(String content, String searchTerm, String criteria) {

        char[] contentArr = content.toLowerCase().toCharArray();
        char[] searchTermArr = searchTerm.toLowerCase().toCharArray();
        char[] identifiedSeqArr = new char[searchTermArr.length];
        int termIdIndex = 0;
        for (int i = 0; i < contentArr.length; i++) {

            if (identifiedSeqArr.length == 0) {
                termIdIndex = 0;
            }

            for (int j = termIdIndex; j < searchTermArr.length; j++) {
                if (contentArr[i] == searchTermArr[j]) {
                    identifiedSeqArr[j] = searchTermArr[j];
                    termIdIndex += j;
                    i++;

                    if (identifiedSeqArr[identifiedSeqArr.length - 1] != 0
                            && identifiedSeqArr.length == searchTermArr.length) {
                        return true;
                    }

                } else {
                    // reset
                    termIdIndex = 0;
                    identifiedSeqArr = new char[searchTermArr.length];
                    break;
                }

            }
        }

        return false;

    }

    private static void matchCase() {
        int start = 0, end = 127;
        for (int i = start; i < end; i++) {
            var current = (char) i;
            System.err.print(current);

            int factor = 0;
            // for nums
            if (i > 32 && i < 65) {
                factor = 16;
            }
            // for alphabets
            if (i > 65 && i < 65 + 32) {
                factor = 32;
            }

            char convCase = (char) (current / 1 + factor);
            System.err.print(convCase);

            if (i != end) {
                System.err.print("\s");
            }

        }

        List<String> aList = new ArrayList<>();
        //DList<String> aList2 = new DList<>();
        //aList2.add("");
    }

    public static String randomIDGenerator(int idLength, boolean allowChars, boolean allowNumbers,
            boolean allowSymbols)
            throws Exception {

        String generated = null;
        int totalSize = 0;
        int startFrom = allowSymbols ? 33 : 48;

        int seekLimit = startFrom + 1;
        if (allowSymbols) {
            int cap = 15;
            totalSize += cap;
            seekLimit += cap;
        }

        if (allowNumbers) {
            int cap = 10;
            totalSize += cap;
            seekLimit += cap + 6;
        }

        if (allowChars) {
            int cap = 52;
            totalSize += cap;
            seekLimit += cap + 6;
        }

        char[] chars = new char[totalSize];

        for (int id = startFrom, pi = 0; id < seekLimit; id++) {
            if ((allowSymbols && id >= 33 && id <= 47) ||
                    (allowNumbers && id >= 48 && id <= 57) ||
                    (allowChars && ((id >= 65 && id <= 90) || (id >= 97 && id <= 122)))) {

                chars[pi] = (char) id;
                pi++;
            }
        }

        // System.out.print(chars);

        char[] genArr = new char[idLength];
        for (int i = 0; i < idLength; i++) {
            int cindex = Double.valueOf(Math.random() * chars.length).intValue();
            //System.out.print(cindex + "\s");

            if (cindex < seekLimit) {
                genArr[i] = chars[cindex];
            } else {
                i--;
                throw new Exception("invalid fn result");
            }

        }

        generated = new String(genArr);

        return generated;
    }

    public static String randomIDGeneratorOR(int idLength, boolean allowChars, boolean allowNumbers, boolean allowSymbols)
            throws Exception {
        String generated = null;
        int totalSize = 0;
        int startFrom = allowSymbols ? 33 : 48;

        int seekLimit = startFrom + 1;
        if (allowSymbols) {
            int cap = 15;
            totalSize += cap;
            seekLimit += cap;
        }

        if (allowNumbers) {
            int cap = 10;
            totalSize += cap;
            seekLimit += cap + 6;
        }

        if (allowChars) {
            int cap = 52;
            totalSize += cap;
            seekLimit += cap + 6;
        }

        char[] genArr = new char[idLength];
        int outOfRangeResCount = 0;

        for (int i = 0; i < idLength; i++) {

            int id = Double.valueOf(Math.random() * seekLimit).intValue();

            // validate
           
            if ((allowSymbols && id >= 33 && id <= 47) ||
                    (allowNumbers && id >= 48 && id <= 57) ||
                    (allowChars && ((id >= 65 && id <= 90) || (id >= 97 && id <= 122)))) {

                genArr[i] = (char) id;
            } else {
                i--;
                outOfRangeResCount +=1;
                //throw new Exception("invalid fn result");
            }
        }

        if(outOfRangeResCount > 0){
            System.out.println("out of range count "+outOfRangeResCount);
        }

        generated = new String(genArr);
        return generated;
    }


    public static String errorAt(char[] blockArr, int stopAt) {

        char[] ecArr = new char[0];
        for (int i = 0; i < blockArr.length; i++) {

            if (i == stopAt) {
                break;
            } else {
                ecArr = ContainerUtil.addToCharArray(ecArr, blockArr[i]);
            }
            // System.out.println(blockArr[i]);
        }

        return new String(ecArr);
    }


    static int hasChar(char[] arr, char item) {

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] == item) {
                return 1;
            }
        }
        return 0;
    }

    static int hasChars(char[] mainArr, char[] items) {

        int hasCount = 0;
        for (int i = 0; i < mainArr.length - 1; i++) {

            for (int j = 0; j < items.length - 1; j++) {

                if (mainArr[i] == items[j]) {
                    hasCount += 1;
                }
            }
        }

        if (hasCount > 0) {
            return 1;
        }

        return 0;
    }





    public static int checkExactSequence(char[] fragment, char[][] sequences) {

       /*  char[][] sequences = { { '"', ':', '"' }, { '"', ',', '"' }, { '"', '}', '}' }, { '"', '}', ']' },
                { '"', ']', '}' }, { '"', ']', ']' }, {',','"'} }; */

        int exactMatch = 0;
        int matchCount = 0;

        for (int g = 0; g < sequences.length; g++) {

            matchCount = 0;
            exactMatch = 0;
            char[] cseq = sequences[g];

            int sp = 0;
            for (int h = 0; h < cseq.length; h++) {
                if (fragment[sp] == cseq[h]) {
                    ++matchCount;
                    ++sp;
                } else {
                    break;
                }
            }

            if (matchCount == fragment.length) {
                exactMatch = 1;

               /*  utilInfo.put("clseq_exactMatch", 1);
                utilInfo.put("clseq_matchScore", matchCount); */

                return 1;
            }

        }

        return 0;

    }






}
