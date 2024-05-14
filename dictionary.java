/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project22b;

/**
 *
 * @author deniz
 */
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
    public boolean insert(Key key) {
        int i;
        int h = hash(key);
        System.out.println(" hash: " + h);
        for (i = h; table[i] != null; i = (i + 1) % M) {
            if (table[i].equals(key)) {
                return true;
            }
            if (i + 1 == h) {
                resize(M+1);
                insert(key); 
            }
        }
        table[i] = key;
        N++; 
        return true;
    }
    public int location(Key key){
        int ix = hash(key);
        int startIx = ix;

        while (table[ix] != null && (ix + 1 != startIx)) {
            if (table[ix].equals(key)) {
                return ix; 
            }if (ix + 1 == startIx) return ix; // starting point
            ix = (ix + 1) % M;
        }
        return ix;
    }
    
    public void delete(Key key){  
        int t =location(key);  
        table[t]=null;  
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
    public String toString() {
        String s = "[";
        for (int i = 0; i < M; i++) {
            s += table[i] + ",";
        }
        return s + "]";
    }
    public static void main(String[] args) throws FileNotFoundException {
        int i = 1;
        System.out.println("* loading the dictionary *");
        LinearProbingHash<String> diction = new LinearProbingHash<String>(235924);
        File mydictionary = new File("/Users/deniz/Documents/dict.txt");
        Scanner dictionary = new Scanner(mydictionary);
        while (dictionary.hasNext()) {
            String word = dictionary.next();
            diction.insert(word.toLowerCase());
        }
        dictionary.close();
        System.out.println("* loading complete ! *");
        while (i == 1) {
            System.out.println(" *  Options:  *");
            System.out.println(" 1. Search for an entry in this dictionary ");
            System.out.println(" 2. Insert a word to the dictionary ");
            System.out.println(" 3. Delete a word from the dictionary ");
            System.out.println(" 4. Do a spell check ");
            System.out.println(" 5. Quit ");
            Scanner input = new Scanner(System.in);
            int option = input.nextInt();
            switch (option) {
                case 1://Search for an entry in this dictionary in constant time.
                    System.out.println("* search for an entry *");
                    System.out.println(" type your entry: ");
                    Scanner input1 = new Scanner(System.in);
                    String entry = input1.next();
                    if (diction.contains(entry.toLowerCase())){
                        System.out.println(entry+" exist in the dictionary!!");
                        System.out.print(" at "+diction.location(entry));
                    }else{
                        System.out.println(entry+" does not exist in the dictionary!!");
                    }
                    break;
                case 2://Insert a word to the dictionary in constant time.
                    System.out.println("* insert a new word to dictionary *");
                    System.out.println("type your word to be added : ");
                    Scanner input2 = new Scanner(System.in);
                    String entry2 = input2.next();
                    if (diction.contains(entry2.toLowerCase())){
                        System.out.println(entry2+" already exist in dictionary!!");    
                    }else{
                        diction.insert(entry2.toLowerCase()); 
                        System.out.println(entry2+" is added to the dictionary succesfully!!");
                    }
                    break;
                case 3://Delete a word from the dictionary in constant time.
                    System.out.println("* delete a word from dictionary *");
                    System.out.println("type your word to be deleted : ");
                    Scanner input3 = new Scanner(System.in);
                    String entryword3 = input3.next();
                    if(diction.contains(entryword3)){
                        diction.delete(entryword3);
                        System.out.println(entryword3+" is deleted from the dictionary succesfully!! ");
                    }else{
                        System.out.println(entryword3+" is already in the dictionary!! ");
                    }
                    break;
                case 4://Given a random text file, do a spell check in linear time
                    File myfile = new File("/Users/deniz/Documents/den.txt");
                    Scanner myfilesc = new Scanner(myfile);
                    ArrayList<String> errors = new ArrayList<>();
                    while (myfilesc.hasNext()) {
                        String wordcheck = myfilesc.next();
                        if(diction.contains(wordcheck.toLowerCase().toString())){
                            System.out.println(wordcheck+"'s spelling is correct!!");
                        }else{
                            System.out.println(wordcheck+"'s spelling is incorrect or not included in the dictionary!!");
                            errors.add(wordcheck.toLowerCase().toString());
                        }
                    }myfilesc.close();
                    if(!errors.isEmpty()){
                        System.out.println("list of incorrect spellings: ");
                        printArray(errors); 
                    }else{
                        System.out.println("there is no incorrect spelling!! ");
                    }
                    break;
                case 5://quit
                    i = 0;
                    break;
            }
        }
    }
    public static void printArray(ArrayList<String> arr){
        for(int i=0;i<arr.size();i++){
            System.out.println(arr.get(i).toString());
        }
    }
}


    
    

