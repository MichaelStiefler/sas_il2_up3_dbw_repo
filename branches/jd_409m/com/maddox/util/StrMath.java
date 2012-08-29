package com.maddox.util;

public class StrMath
{
  public static boolean simple(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString2 == null)) return false;
    char[] arrayOfChar1 = new char[paramString1.length() + 1];
    char[] arrayOfChar2 = new char[paramString2.length() + 1];
    paramString1.getChars(0, paramString1.length(), arrayOfChar1, 0);
    paramString2.getChars(0, paramString2.length(), arrayOfChar2, 0);
    arrayOfChar1[paramString1.length()] = '\000';
    arrayOfChar2[paramString2.length()] = '\000';
    int i = 0;
    int j = 0;
    int k = -1;
    int m = -1;
    while (true)
    {
      if (arrayOfChar2[j] == 0)
        return true;
      j++;
      i++;

      while (arrayOfChar1[i] != arrayOfChar2[j])
      {
        if ((arrayOfChar2[j] != 0) && (arrayOfChar1[i] == '?')) {
          i++;
          j++; continue;
        }if (arrayOfChar1[i] == '*') {
          i++; k = i;
          m = j; continue;
        }if ((m == -1) || (arrayOfChar2[m] == 0)) break label183; m++; j = m;
        i = k;
      }
    }
    label183: return false;
  }
}