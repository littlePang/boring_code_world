package com.little.pang.boring.code.serivce;

import java.util.Arrays;

/**
 * Created by jaky on 10/11/16.
 */
public class KmpTraining {

    private int[] next;

    private void fillMatch(char[] originArr) {
        next[0] = -1;

        for (int i = 1; i < originArr.length; i++) {

            int preCharMatch = next[i - 1];

            while (preCharMatch != -1 && originArr[preCharMatch+1] != originArr[i]) {
                preCharMatch = next[preCharMatch];
            }

            if (preCharMatch == -1) {
                next[i] = 0;
            } else {
                next[i] = preCharMatch + 1;

            }

        }
    }

    private int firstMatchPos(char[] originArr, char[] matchArr) {
        int i = 0;
        int j = 0;
        while (i < originArr.length && j < matchArr.length) {
            if (j == -1 || originArr[i] == matchArr[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }

        }

        if (j == matchArr.length) {
            return i-j;
        }

        return -1;
    }

    private void run() {
        String matchArr =  "abaabad";
        String originArr = "aaaab";
        next = new int[matchArr.length()];
        fillMatch(matchArr.toCharArray());
        System.out.println(Arrays.toString(next));
        int pos = firstMatchPos(originArr.toCharArray(), matchArr.toCharArray());
        System.out.println(pos);
        if (pos >= 0)
            System.out.println(originArr.substring(pos, pos + matchArr.length()));
    }

    public static void main(String[] args) {
        new KmpTraining().run();
    }

}
