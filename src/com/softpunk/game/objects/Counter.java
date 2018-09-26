package com.softpunk.game.objects;

import java.io.*;

public class Counter implements Serializable {
    private double count;
    private final int OBJECT_DONE_TO_SAVE = 10000000;
    private final byte[] container;
    private final int size;
    private final int various;

    public Counter(int size, int various) {
        this.size = size;
        this.various = various;
        container = new byte[size];
        count = 0;
    }

    public String getString() {
        String c = "";
        byte[] s = getCombination();

        for (int i = s.length - 1; i >= 0; i--) {
            switch (s[i]) {
                case 1:
                    c += " U' ";
                    break;

                case 2:
                    c += " D ";
                    break;

                case 3:
                    c += " B ";
                    break;

                case 4:
                    c += " F' ";
                    break;

                case 5:
                    c += " L ";
                    break;

                case 6:
                    c += " R' ";
                    break;

                case -1:
                    c += " U ";
                    break;

                case -2:
                    c += " D' ";
                    break;

                case -3:
                    c += " B' ";
                    break;

                case -4:
                    c += " F ";
                    break;

                case -5:
                    c += " L' ";
                    break;

                case -6:
                    c += " R ";
                    break;
            }
        }

        return c;
    }

    public void show() {
        String c = "";
        byte[] co = getCombination();

        for (int i = 0; i < co.length; i++) {
            c += co[i];
        }

        System.out.println("combination: " + c + ", counter: " + count);
    }

    public double getCount() {
        return count;
    }

    public byte[] getCombination() {
        byte[] out = null;

        for (int i = 0; i < size; i++) {
            if (container[i] !=0) {
                out = new byte[size - i];

                for (int j = 0; j < size - i; j++)
                    out[j] = container[i+j];

                break;
            }
        }

        return out;
    }

    public void iterate() {
        count++;
        iterate(new int[]{size - 1});

        while (testEmptyRotate() || testRepeatRotate()) {
            iterate(new int[]{size - 1});
        }

        if (count % OBJECT_DONE_TO_SAVE == 0) {
            save();
        }
    }

    private void iterate(int[] i) {
        container[i[0]] = (byte) ((container[i[0]] + 1) % (various + 1));

        if (container[i[0]] == 0) {
            i[0]--;
            iterate(i);
        }
    }

    private boolean testEmptyRotate() {
        for (int i = 0; i < size; i++) {
            if (container[i] != 0) {
                for (int j = i+1; j < size; j++) {
                    if (container[j] == 0) {
                        return true;
                    }
                }

                break;
            }
        }

        return false;
    }

    private boolean testRepeatRotate() {
        for (int i = 0; i < size; i++) {
            if (container[i] != 0 && i < size - 3) {
                for (int j = i+3; j < size; j++) {
                    if (container[j] == container[j-1] && container[j] == container[j-2] && container[j] == container[j-3]) {
                        return true;
                    }
                }

                break;
            }
        }

        return false;
    }

    private void save() {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        System.out.println(count);

        try {
            fos = new FileOutputStream(size + "_" + various + ".txt");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
