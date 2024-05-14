/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class LinearProbingHash<Key> {

    Key[] table;
    int M;
    int N;
    boolean[] full;

    public LinearProbingHash(int M) {
        table = (Key[]) new Object[M];
        this.M = M;
    }
    public int hash(Key t) {
        if(t==null){
            return 0;
        }
        return ((t.hashCode() & 0x7fffffff) % M);
    }
    private void resize(int capacity) {
        System.out.println("resize");
        LinearProbingHash<Key> temp = new LinearProbingHash<Key>(capacity);
        for (int i = 0; i < M; i++) {
            if (table[i] != null) {
                temp.insert(table[i]);
            }
        }
        table = temp.table;
        full = temp.full;
        M = temp.M;
    }
    public void insert(Key key) {
        int i;
        int h = hash(key);
        System.out.println(" hash: " + h);
        for (i = h; table[i] != null; i = (i + 1) % M) {
            if (table[i].equals(key)) {
                break;
            }
        }
        table[i] = key;
        N++;
        if(N>M-10){
            resize(M+10);
        }
    }
    public int location(Key key) {
        int ix = hash(key);
        int startIx = ix;
        while (table[ix] != null && (ix + 1 != startIx)) {
            if (table[ix].equals(key)) {
                return ix;
            }
            if (ix + 1 == startIx) {
                return ix; 
            }
            ix = (ix + 1) % M;
        }
        return ix;
    }
    public boolean contains(Key key) {
        int ix = hash(key);
        int startIx = ix;
        while (table[ix] != null && (ix + 1 != startIx)) {
            if (table[ix].equals(key)) {
                return true; 
            }if (ix + 1 == startIx) return false; 
            ix = (ix + 1) % M;  
        }
        return false;
    }
    public void delete(Key key) {
        int t = location(key);
        table[t] = null;
    }
    public int index(Key key) {
        int ix = hash(key);
        int startIx = ix;

        while (table[ix] != null && (ix + 1 != startIx)) {
            if (table[ix].equals(key)) {
                return ix;
            }
            if (ix + 1 == startIx) {
                return -1;
            }
            ix = (ix + 1) % M;
        }
        return ix;
    }
    public String intToString(int index){
        if (index>=0 && index< M*2 && table[index] !=null){
                    return (String)table[index];

        }
        return null;
    }
    public String toString() {
        String s = "[";
        for (int i = 0; i < M; i++) {
            s += table[i] + ",";
        }
        return s + "]";
    }
}
