package com.softpunk.game;

import com.softpunk.game.objects.Counter;
import com.softpunk.game.objects.Cube;
import com.softpunk.game.objects.Plane;

import java.io.*;

public class Main {
    private static final int size = 26;
    private static final int various = 6;
    private static Counter counter;
    private static Cube cube;

    public static void main(String[] args) {
        counter = getCounter(size, various);
        cube = new Cube();

        while (true) {
            counter.iterate();
            cube = new Cube();
            cube.makeCombination(counter.getCombination());

            if (cube.testColor()) {
                saveCombination();
            }
        }
    }

    public static String byteToString(byte[] a) {
        String c = "";

        for (int i = 0; i < a.length; i++) {
            c += a[i];
        }

        return c;
    }

    private static Counter getCounter(int size, int various) {
        File f = new File(size+"_"+various+".txt");

        if (f.exists()) {
            FileInputStream fis = null;
            ObjectInputStream oin = null;

            try {
                fis = new FileInputStream(f);
                oin = new ObjectInputStream(fis);
                return  (Counter) oin.readObject();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            return new Counter(size, various);
        }

        return null;
    }

    private static void saveCombination() {
        String c = "---------------------------------------------\nCombination: "+counter.getString()+"\n";
        c += "VIEW: \n"+cube.getView()+"\n---------------------------------------------\n";
        saveString(c);
    }

    private static void saveString(String data) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(new File("combination.txt"), true);
            os.write(data.getBytes(), 0, data.length());
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
