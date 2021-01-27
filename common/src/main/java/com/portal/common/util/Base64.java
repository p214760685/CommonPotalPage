package com.portal.common.util;

public final class Base64 {
    private static final int BASELENGTH = 128;
    private static final int LOOKUPLENGTH = 64;
    private static final int TWENTYFOURBITGROUP = 24;
    private static final int EIGHTBIT = 8;
    private static final int SIXTEENBIT = 16;
    private static final int FOURBYTE = 4;
    private static final int SIGN = -128;
    private static final char PAD = '=';
    private static final boolean fDebug = false;
    private static final byte[] base64Alphabet = new byte[128];
    private static final char[] lookUpBase64Alphabet = new char[64];

    public Base64() {
    }

    private static boolean isWhiteSpace(char octect) {
        return octect == 32 || octect == 13 || octect == 10 || octect == 9;
    }

    private static boolean isPad(char octect) {
        return octect == 61;
    }

    private static boolean isData(char octect) {
        return octect < 128 && base64Alphabet[octect] != -1;
    }

    public static String encode(byte[] binaryData) {
        if(binaryData == null) {
            return null;
        } else {
            int lengthDataBits = binaryData.length * 8;
            if(lengthDataBits == 0) {
                return "";
            } else {
                int fewerThan24bits = lengthDataBits % 24;
                int numberTriplets = lengthDataBits / 24;
                int numberQuartet = fewerThan24bits == 0?numberTriplets:numberTriplets + 1;
                char[] encodedData = (char[])null;
                encodedData = new char[numberQuartet * 4];
                boolean k = false;
                boolean l = false;
                boolean b1 = false;
                boolean b2 = false;
                boolean b3 = false;
                int encodedIndex = 0;
                int dataIndex = 0;

                byte val2;
                byte var17;
                byte var18;
                byte var19;
                byte var20;
                for(int val1 = 0; val1 < numberTriplets; ++val1) {
                    var19 = binaryData[dataIndex++];
                    var20 = binaryData[dataIndex++];
                    byte var21 = binaryData[dataIndex++];
                    var18 = (byte)(var20 & 15);
                    var17 = (byte)(var19 & 3);
                    val2 = (var19 & -128) != 0?(byte)(var19 >> 2 ^ 192):(byte)(var19 >> 2);
                    byte val21 = (var20 & -128) != 0?(byte)(var20 >> 4 ^ 240):(byte)(var20 >> 4);
                    byte val3 = (var21 & -128) != 0?(byte)(var21 >> 6 ^ 252):(byte)(var21 >> 6);
                    encodedData[encodedIndex++] = lookUpBase64Alphabet[val2];
                    encodedData[encodedIndex++] = lookUpBase64Alphabet[val21 | var17 << 4];
                    encodedData[encodedIndex++] = lookUpBase64Alphabet[var18 << 2 | val3];
                    encodedData[encodedIndex++] = lookUpBase64Alphabet[var21 & 63];
                }

                byte var22;
                if(fewerThan24bits == 8) {
                    var19 = binaryData[dataIndex];
                    var17 = (byte)(var19 & 3);
                    var22 = (var19 & -128) != 0?(byte)(var19 >> 2 ^ 192):(byte)(var19 >> 2);
                    encodedData[encodedIndex++] = lookUpBase64Alphabet[var22];
                    encodedData[encodedIndex++] = lookUpBase64Alphabet[var17 << 4];
                    encodedData[encodedIndex++] = 61;
                    encodedData[encodedIndex++] = 61;
                } else if(fewerThan24bits == 16) {
                    var19 = binaryData[dataIndex];
                    var20 = binaryData[dataIndex + 1];
                    var18 = (byte)(var20 & 15);
                    var17 = (byte)(var19 & 3);
                    var22 = (var19 & -128) != 0?(byte)(var19 >> 2 ^ 192):(byte)(var19 >> 2);
                    val2 = (var20 & -128) != 0?(byte)(var20 >> 4 ^ 240):(byte)(var20 >> 4);
                    encodedData[encodedIndex++] = lookUpBase64Alphabet[var22];
                    encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | var17 << 4];
                    encodedData[encodedIndex++] = lookUpBase64Alphabet[var18 << 2];
                    encodedData[encodedIndex++] = 61;
                }

                return new String(encodedData);
            }
        }
    }

    public static byte[] decode(String encoded) {
        if(encoded == null) {
            return null;
        } else {
            char[] base64Data = encoded.toCharArray();
            int len = removeWhiteSpace(base64Data);
            if(len % 4 != 0) {
                return null;
            } else {
                int numberQuadruple = len / 4;
                if(numberQuadruple == 0) {
                    return new byte[0];
                } else {
                    byte[] decodedData = (byte[])null;
                    boolean b1 = false;
                    boolean b2 = false;
                    boolean b3 = false;
                    boolean b4 = false;
                    boolean d1 = false;
                    boolean d2 = false;
                    boolean d3 = false;
                    boolean d4 = false;
                    int i = 0;
                    int encodedIndex = 0;
                    int dataIndex = 0;

                    byte var17;
                    byte var18;
                    byte var19;
                    byte var20;
                    char var21;
                    char var22;
                    char var23;
                    char var24;
                    for(decodedData = new byte[numberQuadruple * 3]; i < numberQuadruple - 1; ++i) {
                        if(!isData(var21 = base64Data[dataIndex++]) || !isData(var22 = base64Data[dataIndex++]) || !isData(var23 = base64Data[dataIndex++]) || !isData(var24 = base64Data[dataIndex++])) {
                            return null;
                        }

                        var17 = base64Alphabet[var21];
                        var18 = base64Alphabet[var22];
                        var19 = base64Alphabet[var23];
                        var20 = base64Alphabet[var24];
                        decodedData[encodedIndex++] = (byte)(var17 << 2 | var18 >> 4);
                        decodedData[encodedIndex++] = (byte)((var18 & 15) << 4 | var19 >> 2 & 15);
                        decodedData[encodedIndex++] = (byte)(var19 << 6 | var20);
                    }

                    if(isData(var21 = base64Data[dataIndex++]) && isData(var22 = base64Data[dataIndex++])) {
                        var17 = base64Alphabet[var21];
                        var18 = base64Alphabet[var22];
                        var23 = base64Data[dataIndex++];
                        var24 = base64Data[dataIndex++];
                        if(isData(var23) && isData(var24)) {
                            var19 = base64Alphabet[var23];
                            var20 = base64Alphabet[var24];
                            decodedData[encodedIndex++] = (byte)(var17 << 2 | var18 >> 4);
                            decodedData[encodedIndex++] = (byte)((var18 & 15) << 4 | var19 >> 2 & 15);
                            decodedData[encodedIndex++] = (byte)(var19 << 6 | var20);
                            return decodedData;
                        } else {
                            byte[] tmp;
                            if(isPad(var23) && isPad(var24)) {
                                if((var18 & 15) != 0) {
                                    return null;
                                } else {
                                    tmp = new byte[i * 3 + 1];
                                    System.arraycopy(decodedData, 0, tmp, 0, i * 3);
                                    tmp[encodedIndex] = (byte)(var17 << 2 | var18 >> 4);
                                    return tmp;
                                }
                            } else if(!isPad(var23) && isPad(var24)) {
                                var19 = base64Alphabet[var23];
                                if((var19 & 3) != 0) {
                                    return null;
                                } else {
                                    tmp = new byte[i * 3 + 2];
                                    System.arraycopy(decodedData, 0, tmp, 0, i * 3);
                                    tmp[encodedIndex++] = (byte)(var17 << 2 | var18 >> 4);
                                    tmp[encodedIndex] = (byte)((var18 & 15) << 4 | var19 >> 2 & 15);
                                    return tmp;
                                }
                            } else {
                                return null;
                            }
                        }
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    private static int removeWhiteSpace(char[] data) {
        if(data == null) {
            return 0;
        } else {
            int newSize = 0;
            int len = data.length;

            for(int i = 0; i < len; ++i) {
                if(!isWhiteSpace(data[i])) {
                    data[newSize++] = data[i];
                }
            }

            return newSize;
        }
    }

    static {
        int i;
        for(i = 0; i < 128; ++i) {
            base64Alphabet[i] = -1;
        }

        for(i = 90; i >= 65; --i) {
            base64Alphabet[i] = (byte)(i - 65);
        }

        for(i = 122; i >= 97; --i) {
            base64Alphabet[i] = (byte)(i - 97 + 26);
        }

        for(i = 57; i >= 48; --i) {
            base64Alphabet[i] = (byte)(i - 48 + 52);
        }

        base64Alphabet[43] = 62;
        base64Alphabet[47] = 63;

        for(i = 0; i <= 25; ++i) {
            lookUpBase64Alphabet[i] = (char)(65 + i);
        }

        i = 26;

        int j;
        for(j = 0; i <= 51; ++j) {
            lookUpBase64Alphabet[i] = (char)(97 + j);
            ++i;
        }

        i = 52;

        for(j = 0; i <= 61; ++j) {
            lookUpBase64Alphabet[i] = (char)(48 + j);
            ++i;
        }

        lookUpBase64Alphabet[62] = 43;
        lookUpBase64Alphabet[63] = 47;
    }
}
