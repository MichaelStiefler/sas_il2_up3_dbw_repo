package com.maddox.util;

public class Stdio
{
  private static final int MP = 12;
  private static int[] I = new int[12];
  private static double[] D = new double[12];
  private static String[] S = new String[12];

  private static int[] begs = new int[12];
  private static int[] ends = new int[12];

  public static void sscanf(String paramString1, String paramString2)
  {
    int k;
    char c;
    for (int i = k = 0; i < paramString1.length(); i++) {
      c = paramString1.charAt(i);
      if ((c >= '+') && (c != ',')) {
        begs[k] = i;
        for (; i < paramString1.length(); i++) {
          c = paramString1.charAt(i);
          if ((c <= '+') || (c == ',')) break;
        }
        ends[k] = i;
        k++;
      }
    }
    int j;
    for (i = j = 0; (i < paramString2.length() - 1) && (j < k); i++) {
      c = paramString2.charAt(i);
      if (c == '%') {
        String str = paramString1.substring(begs[j], ends[j]);
        while (true) { i++; if (i >= paramString2.length()) break;
          c = paramString2.charAt(i);
          switch (Character.toUpperCase(c)) { case 'C':
          case 'E':
          case 'H':
          case 'J':
          case 'K':
          case 'M':
          case 'N':
          case 'P':
          case 'Q':
          case 'R':
          case 'T':
          case 'U':
          case 'V':
          case 'W':
          default:
            break;
          case 'D':
          case 'I':
            I[j] = Integer.parseInt(str);
            j++;
            break;
          case 'X':
            I[j] = Integer.parseInt(str, 16);
            j++;
            break;
          case 'B':
            I[j] = Integer.parseInt(str, 2);
            j++;
            break;
          case 'O':
            I[j] = Integer.parseInt(str, 8);
            j++;
            break;
          case 'F':
          case 'G':
          case 'L':
            D[j] = Double.parseDouble(str);
            j++;
            break;
          case 'S':
            S[j] = str;
            j++;
          }
        }
      }
    }
  }

  public static int getInt(int paramInt)
  {
    return I[paramInt];
  }

  public static float getFloat(int paramInt) {
    return (float)D[paramInt];
  }

  public static double getDouble(int paramInt) {
    return D[paramInt];
  }

  public static String getString(int paramInt) {
    return S[paramInt];
  }
}