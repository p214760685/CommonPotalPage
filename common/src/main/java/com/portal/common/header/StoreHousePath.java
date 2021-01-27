//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.portal.common.header;

import android.util.SparseArray;
import java.util.ArrayList;

public class StoreHousePath {
    private static final SparseArray<float[]> sPointList = new SparseArray();

    public StoreHousePath() {
    }

    public static void addChar(char c, float[] points) {
        sPointList.append(c, points);
    }

    public static ArrayList<float[]> getPath(String str) {
        return getPath(str, 1.0F, 14);
    }

    public static ArrayList<float[]> getPath(String str, float scale, int gapBetweenLetter) {
        ArrayList list = new ArrayList();
        float offsetForWidth = 0.0F;

        for(int i = 0; i < str.length(); ++i) {
            char pos = str.charAt(i);
            int key = sPointList.indexOfKey(pos);
            if(key != -1) {
                float[] points = (float[])sPointList.get(pos);
                int pointCount = points.length / 4;

                for(int j = 0; j < pointCount; ++j) {
                    float[] line = new float[4];

                    for(int k = 0; k < 4; ++k) {
                        float l = points[j * 4 + k];
                        if(k % 2 == 0) {
                            line[k] = (l + offsetForWidth) * scale;
                        } else {
                            line[k] = l * scale;
                        }
                    }

                    list.add(line);
                }

                offsetForWidth += (float)(57 + gapBetweenLetter);
            }
        }

        return list;
    }

    static {
        float[][] LETTERS = new float[][]{{24.0F, 0.0F, 1.0F, 22.0F, 1.0F, 22.0F, 1.0F, 72.0F, 24.0F, 0.0F, 47.0F, 22.0F, 47.0F, 22.0F, 47.0F, 72.0F, 1.0F, 48.0F, 47.0F, 48.0F}, {0.0F, 0.0F, 0.0F, 72.0F, 0.0F, 0.0F, 37.0F, 0.0F, 37.0F, 0.0F, 47.0F, 11.0F, 47.0F, 11.0F, 47.0F, 26.0F, 47.0F, 26.0F, 38.0F, 36.0F, 38.0F, 36.0F, 0.0F, 36.0F, 38.0F, 36.0F, 47.0F, 46.0F, 47.0F, 46.0F, 47.0F, 61.0F, 47.0F, 61.0F, 38.0F, 71.0F, 37.0F, 72.0F, 0.0F, 72.0F}, {47.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 72.0F, 0.0F, 72.0F, 47.0F, 72.0F}, {0.0F, 0.0F, 0.0F, 72.0F, 0.0F, 0.0F, 24.0F, 0.0F, 24.0F, 0.0F, 47.0F, 22.0F, 47.0F, 22.0F, 47.0F, 48.0F, 47.0F, 48.0F, 23.0F, 72.0F, 23.0F, 72.0F, 0.0F, 72.0F}, {0.0F, 0.0F, 0.0F, 72.0F, 0.0F, 0.0F, 47.0F, 0.0F, 0.0F, 36.0F, 37.0F, 36.0F, 0.0F, 72.0F, 47.0F, 72.0F}, {0.0F, 0.0F, 0.0F, 72.0F, 0.0F, 0.0F, 47.0F, 0.0F, 0.0F, 36.0F, 37.0F, 36.0F}, {47.0F, 23.0F, 47.0F, 0.0F, 47.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 72.0F, 0.0F, 72.0F, 47.0F, 72.0F, 47.0F, 72.0F, 47.0F, 48.0F, 47.0F, 48.0F, 24.0F, 48.0F}, {0.0F, 0.0F, 0.0F, 72.0F, 0.0F, 36.0F, 47.0F, 36.0F, 47.0F, 0.0F, 47.0F, 72.0F}, {0.0F, 0.0F, 47.0F, 0.0F, 24.0F, 0.0F, 24.0F, 72.0F, 0.0F, 72.0F, 47.0F, 72.0F}, {47.0F, 0.0F, 47.0F, 72.0F, 47.0F, 72.0F, 24.0F, 72.0F, 24.0F, 72.0F, 0.0F, 48.0F}, {0.0F, 0.0F, 0.0F, 72.0F, 47.0F, 0.0F, 3.0F, 33.0F, 3.0F, 38.0F, 47.0F, 72.0F}, {0.0F, 0.0F, 0.0F, 72.0F, 0.0F, 72.0F, 47.0F, 72.0F}, {0.0F, 0.0F, 0.0F, 72.0F, 0.0F, 0.0F, 24.0F, 23.0F, 24.0F, 23.0F, 47.0F, 0.0F, 47.0F, 0.0F, 47.0F, 72.0F}, {0.0F, 0.0F, 0.0F, 72.0F, 0.0F, 0.0F, 47.0F, 72.0F, 47.0F, 72.0F, 47.0F, 0.0F}, {0.0F, 0.0F, 0.0F, 72.0F, 0.0F, 72.0F, 47.0F, 72.0F, 47.0F, 72.0F, 47.0F, 0.0F, 47.0F, 0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F, 72.0F, 0.0F, 0.0F, 47.0F, 0.0F, 47.0F, 0.0F, 47.0F, 36.0F, 47.0F, 36.0F, 0.0F, 36.0F}, {0.0F, 0.0F, 0.0F, 72.0F, 0.0F, 72.0F, 23.0F, 72.0F, 23.0F, 72.0F, 47.0F, 48.0F, 47.0F, 48.0F, 47.0F, 0.0F, 47.0F, 0.0F, 0.0F, 0.0F, 24.0F, 28.0F, 47.0F, 71.0F}, {0.0F, 0.0F, 0.0F, 72.0F, 0.0F, 0.0F, 47.0F, 0.0F, 47.0F, 0.0F, 47.0F, 36.0F, 47.0F, 36.0F, 0.0F, 36.0F, 0.0F, 37.0F, 47.0F, 72.0F}, {47.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 36.0F, 0.0F, 36.0F, 47.0F, 36.0F, 47.0F, 36.0F, 47.0F, 72.0F, 47.0F, 72.0F, 0.0F, 72.0F}, {0.0F, 0.0F, 47.0F, 0.0F, 24.0F, 0.0F, 24.0F, 72.0F}, {0.0F, 0.0F, 0.0F, 72.0F, 0.0F, 72.0F, 47.0F, 72.0F, 47.0F, 72.0F, 47.0F, 0.0F}, {0.0F, 0.0F, 24.0F, 72.0F, 24.0F, 72.0F, 47.0F, 0.0F}, {0.0F, 0.0F, 0.0F, 72.0F, 0.0F, 72.0F, 24.0F, 49.0F, 24.0F, 49.0F, 47.0F, 72.0F, 47.0F, 72.0F, 47.0F, 0.0F}, {0.0F, 0.0F, 47.0F, 72.0F, 47.0F, 0.0F, 0.0F, 72.0F}, {0.0F, 0.0F, 24.0F, 23.0F, 47.0F, 0.0F, 24.0F, 23.0F, 24.0F, 23.0F, 24.0F, 72.0F}, {0.0F, 0.0F, 47.0F, 0.0F, 47.0F, 0.0F, 0.0F, 72.0F, 0.0F, 72.0F, 47.0F, 72.0F}};
        float[][] NUMBERS = new float[][]{{0.0F, 0.0F, 0.0F, 72.0F, 0.0F, 72.0F, 47.0F, 72.0F, 47.0F, 72.0F, 47.0F, 0.0F, 47.0F, 0.0F, 0.0F, 0.0F}, {24.0F, 0.0F, 24.0F, 72.0F}, {0.0F, 0.0F, 47.0F, 0.0F, 47.0F, 0.0F, 47.0F, 36.0F, 47.0F, 36.0F, 0.0F, 36.0F, 0.0F, 36.0F, 0.0F, 72.0F, 0.0F, 72.0F, 47.0F, 72.0F}, {0.0F, 0.0F, 47.0F, 0.0F, 47.0F, 0.0F, 47.0F, 36.0F, 47.0F, 36.0F, 0.0F, 36.0F, 47.0F, 36.0F, 47.0F, 72.0F, 47.0F, 72.0F, 0.0F, 72.0F}, {0.0F, 0.0F, 0.0F, 36.0F, 0.0F, 36.0F, 47.0F, 36.0F, 47.0F, 0.0F, 47.0F, 72.0F}, {0.0F, 0.0F, 0.0F, 36.0F, 0.0F, 36.0F, 47.0F, 36.0F, 47.0F, 36.0F, 47.0F, 72.0F, 47.0F, 72.0F, 0.0F, 72.0F, 0.0F, 0.0F, 47.0F, 0.0F}, {0.0F, 0.0F, 0.0F, 72.0F, 0.0F, 72.0F, 47.0F, 72.0F, 47.0F, 72.0F, 47.0F, 36.0F, 47.0F, 36.0F, 0.0F, 36.0F}, {0.0F, 0.0F, 47.0F, 0.0F, 47.0F, 0.0F, 47.0F, 72.0F}, {0.0F, 0.0F, 0.0F, 72.0F, 0.0F, 72.0F, 47.0F, 72.0F, 47.0F, 72.0F, 47.0F, 0.0F, 47.0F, 0.0F, 0.0F, 0.0F, 0.0F, 36.0F, 47.0F, 36.0F}, {47.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 36.0F, 0.0F, 36.0F, 47.0F, 36.0F, 47.0F, 0.0F, 47.0F, 72.0F}};

        int i;
        for(i = 0; i < LETTERS.length; ++i) {
            sPointList.append(i + 65, LETTERS[i]);
        }

        for(i = 0; i < LETTERS.length; ++i) {
            sPointList.append(i + 65 + 32, LETTERS[i]);
        }

        for(i = 0; i < NUMBERS.length; ++i) {
            sPointList.append(i + 48, NUMBERS[i]);
        }

        addChar(' ', new float[0]);
        addChar('-', new float[]{0.0F, 36.0F, 47.0F, 36.0F});
        addChar('.', new float[]{24.0F, 60.0F, 24.0F, 72.0F});
    }
}
