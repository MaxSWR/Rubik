package com.softpunk.game.objects;

import java.util.HashSet;

public class Plane {
    public byte[][] plane;
    private byte color;

    public Plane(byte color) {
        this.color = color;
        plane = new byte[][]{
                {color, color, color},
                {color, color, color},
                {color, color, color}
        };
        /* i - row, j - col*/
    }

    public byte[] getRow(int i, boolean forward) {
        return getRow(i, plane, forward);
    }

    public byte[] getCol(int j,  boolean forward) {
        return getCol(j, plane, forward);
    }

    public void setRow(int i, byte[] r) {
        setRow(i, r, plane);
    }

    public void setCol(int j, byte[] r) {
        setCol(j, r, plane);
    }

    public byte[] getRow(int i, byte[][] out, boolean forward) {
        if (forward) {
            return new byte[] {out[i][0], out[i][1], out[i][2]};
        } else {
            return new byte[] {out[i][2], out[i][1], out[i][0]};
        }
    }

    public byte[] getCol(int j, byte[][] out,  boolean forward) {
        if (forward) {
            return new byte[] {out[0][j], out[1][j], out[2][j]};
        } else {
            return new byte[] {out[2][j], out[1][j], out[0][j]};
        }
    }

    public void setRow(int i, byte[] r, byte[][] in) {
        in[i][0] = r[0];
        in[i][1] = r[1];
        in[i][2] = r[2];
    }

    public void setCol(int j, byte[] r, byte[][] in) {
        in[0][j] = r[0];
        in[1][j] = r[1];
        in[2][j] = r[2];
    }

    public void rotate(boolean forward) { // forward = inverse of clock arrow
        byte[][] tmp = new byte[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tmp[i][j] += plane[i][j];
            }
        }

        if (forward) {
            setCol(0, getRow(0, tmp, !forward));
            setCol(1, getRow(1, tmp, !forward));
            setCol(2, getRow(2, tmp, !forward));
        } else {
            setCol(2, getRow(0, tmp, !forward));
            setCol(1, getRow(1, tmp, !forward));
            setCol(0, getRow(2, tmp, !forward));
        }
    }

    public boolean testColor() {
        HashSet<Byte> colors = new HashSet<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                colors.add(plane[i][j]);
            }
        }

        if (colors.size() != 6) {
            return false;
        }

        byte c;
        int counterColor = 0;

        for (int colorsC = 0; colorsC < 6; colorsC++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (plane[i][j] == colorsC) {
                        counterColor++;

                        if (counterColor > 2) {
                            return  false;
                        }
                    }
                }
            }

            counterColor = 0;
        }

        return true;
    }

    public void show() {
        String c = "";

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                c += plane[i][j];
            }

            c += "\n   ";
        }

        System.out.println(color+": \n   "+c);
    }
}
