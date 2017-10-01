package com.pedash.diff;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuliya Pedash on 20.06.2017.
 */
public class Diff {
    private char[] firstSeq;
    private char[] secondSeq;
    private int len1;
    private int len2;
    private List<Character> deleted;
    private List<Character> added;
    private List<Character> common;
    private int lcs[][];

    public List<Character> getDeleted() {
        return deleted;
    }

    public List<Character> getAdded() {
        return added;
    }

    public Diff(char[] firstSeq, char[] secondSeq) {
        this.firstSeq = firstSeq;
        this.secondSeq = secondSeq;
        len1 = firstSeq.length;
        len2 = secondSeq.length;
        lcs = getLCS();
    }
    public int[][] getLCS() {
        int lcs[][] = new int[len1][len2];
        for (int i = len1 - 2; i >= 0; i--) {
            for (int j = len2 - 2; j >= 0; j--) {
                if (firstSeq[i] == secondSeq[j]) {
                    lcs[i][j] = 1 + lcs[i + 1][j + 1];
                } else {
                    lcs[i][j] = Math.max(lcs[i + 1][j], lcs[i][j + 1]);
                }
            }
        }
        return lcs;
    }

    public char[] getFirstSeq() {
        return firstSeq;
    }

    public char[] getSecondSeq() {
        return secondSeq;
    }

    public void doDiff() {
        deleted = new ArrayList<>();
        added = new ArrayList<>();
        common = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (lcs[i][j] != 0 && i < len1 && j < len2) {
            if (firstSeq[i] == secondSeq[j]) {
                common.add(firstSeq[i]);
                i++;
                j++;
            } else {
                if (lcs[i][j] == lcs[i + 1][j]) {
                    deleted.add(firstSeq[i]);
                    i++;
                } else {
                    added.add(secondSeq[j]);
                    j++;
                }
            }
        }
        while (i < len1) {
            deleted.add(firstSeq[i]);
            i++;
        }
        while (j < len2) {
            added.add(secondSeq[j]);
            j++;
        }
    }



}
