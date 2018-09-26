package com.softpunk.game.objects;

import static com.softpunk.game.Main.byteToString;

public class Cube {
    private Plane[] planes;

    public Cube() {
        planes = new Plane[6];

        for (int i = 0; i < 6; i++) {
            planes[i] = new Plane((byte) i);
        }
    }

    public boolean testColor() {
        for (int i = 0; i < 6; i++) {
            if (!planes[i].testColor()) {
                return false;
            }
        }

        return true;
    }

    public void rotate(byte side) {
        int row;
        byte[] tmp;

        if (side == 1 || side == 2) {
            row = side == 1 ? 0 : 2;
            tmp = planes[3].getRow(row, true);

            for (int i = 2; i >= 0; i--) {
                planes[i+1].setRow(row, planes[i].getRow(row, true));
            }

            planes[0].setRow(row, tmp);
        } else if (side == 3) {
            tmp = planes[5].getRow(2, false);
            planes[5].setRow(2, planes[0].getCol(0, true));
            planes[0].setCol(0, planes[4].getRow(0, false));
            planes[4].setRow(0, planes[2].getCol(2, true));
            planes[2].setCol(2, tmp);
        } else if (side == 4) {
            tmp = planes[5].getRow(0, false);
            planes[5].setRow(0, planes[0].getCol(2, true));
            planes[0].setCol(2, planes[4].getRow(2, false));
            planes[4].setRow(2, planes[2].getCol(0, true));
            planes[2].setCol(0, tmp);
        } else if (side == 5) {
            tmp = planes[5].getCol(0, false);
            planes[5].setCol(0, planes[1].getCol(0, true));
            planes[1].setCol(0, planes[4].getCol(0, true));
            planes[4].setCol(0, planes[3].getCol(2, false));
            planes[3].setCol(2, tmp);
        } else if (side == 6) {
            int iCol = side == 5 ? 2 : 0;
            row = side == 5 ? 0 : 2;
            tmp = planes[5].getCol(row, false);
            planes[5].setCol(row, planes[1].getCol(row, true));
            planes[1].setCol(row, planes[4].getCol(row, true));
            planes[4].setCol(row, planes[3].getCol(iCol, false));
            planes[3].setCol(iCol, tmp);
        }

        switch (side) {
            case 1:
                planes[4].rotate(true);
                break;

            case 2:
                planes[5].rotate(false);
                break;

            case 3:
                planes[3].rotate(false);
                break;

            case 4:
                planes[1].rotate(true);
                break;

            case 5:
                planes[0].rotate(false);
                break;

            case 6:
                planes[2].rotate(true);
                break;
        }
    }

    public void makeCombination(byte[] combination) {
        for (int i = combination.length - 1; i >= 0; i--) {
            if (combination[i] < 0) {
                for (int j = 0; j < 3; j++)
                    rotate((byte) -combination[i]);
            } else {
                rotate(combination[i]);
            }

        }
    }

    public String getView() {
        String c ="   |";
        c += byteToString(planes[4].getRow(0, true))+"|\n   |";
        c += byteToString(planes[4].getRow(1, true))+"|\n   |";
        c += byteToString(planes[4].getRow(2, true))+"|\n---------------\n";

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                c += byteToString(planes[j].getRow(i, true))+"|";
            }

            c += "\n";
        }

        c += "---------------\n";

        c += "   |"+byteToString(planes[5].getRow(0, true))+"|\n   |";
        c += byteToString(planes[5].getRow(1, true))+"|\n   |";
        c += byteToString(planes[5].getRow(2, true))+"|";

        return c;
    }


}
