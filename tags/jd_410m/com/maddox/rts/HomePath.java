package com.maddox.rts;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class HomePath
{
  private static char notSeparator;

  public static final List list()
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    while (true) {
      String str = get(i);
      if (str == null)
        return localArrayList;
      localArrayList.add(str);
      i++;
    }
  }

  private static final String subNames(String paramString1, String paramString2, boolean paramBoolean) {
    int i = paramString1.length() - 1;
    while (i >= 0) {
      j = paramString1.charAt(i);
      if ((j == 47) || (j == 92)) {
        i++;
        break;
      }
      i--;
    }
    if (i <= 0) {
      i = 0;
    }
    int j = paramString2.length() - 1;
    while (j >= 0) {
      k = paramString2.charAt(j);
      if ((k == 47) || (k == 92)) {
        j++;
        break;
      }
      j--;
    }
    if (j == paramString2.length())
    {
      return null;
    }
    if (j <= 0)
      j = 0;
    int k = 0;
    while ((k < i) && (k < j)) {
      m = Character.toLowerCase(paramString1.charAt(k));
      if (m == 92) m = 47;
      int n = Character.toLowerCase(paramString2.charAt(k));
      if (n == 92) n = 47;
      if (m != n)
        break;
      k++;
    }
    if ((k != i) && (paramBoolean))
      return null;
    if (k < i) {
      while (k > 0) {
        m = Character.toLowerCase(paramString1.charAt(k));
        if ((m == 92) || (m == 47)) {
          k++;
          break;
        }
        k--;
      }
    }
    int m = k;
    StringBuffer localStringBuffer = new StringBuffer();
    char c;
    while (k < i) {
      c = Character.toLowerCase(paramString1.charAt(k));
      if ((c == '\\') || (c == '/'))
        localStringBuffer.append("../");
      k++;
    }
    k = m;
    j = paramString2.length();
    while (k < j) {
      c = Character.toLowerCase(paramString2.charAt(k));
      if (c == '\\') c = '/';
      localStringBuffer.append(c);
      k++;
    }
    return localStringBuffer.toString();
  }

  public static final String toLocalName(String paramString, int paramInt) {
    if (!isFileSystemName(paramString))
      return paramString;
    String str = get(paramInt);
    if (str == null)
      return null;
    int i = str.charAt(str.length() - 1);
    if ((i != 47) && (i != 92)) {
      return subNames(str + '/', paramString, true);
    }
    return subNames(str, paramString, true);
  }

  public static final String toLocalName(String paramString1, String paramString2, int paramInt) {
    paramString1 = toLocalName(paramString1, paramInt);
    paramString2 = toLocalName(paramString2, paramInt);
    if ((paramString1 == null) || (paramString2 == null))
      return null;
    return subNames(paramString1, paramString2, false);
  }

  public static final boolean isFileSystemName(String paramString) {
    int i = paramString.charAt(0);
    if ((i == 47) || (i == 92))
      return true;
    if (paramString.length() > 1) {
      i = paramString.charAt(1);
      if (i == 58)
        return true;
    }
    return false;
  }

  public static final String concatNames(String paramString1, String paramString2) {
    if (isFileSystemName(paramString2))
      return paramString2;
    StringBuffer localStringBuffer = new StringBuffer(paramString1);
    int i = localStringBuffer.length();
    int j = -1;
    char c;
    for (int k = 0; k < i; k++) {
      c = Character.toLowerCase(localStringBuffer.charAt(k));
      if ((c == '/') || (c == '\\')) {
        c = '/';
        j = k;
      }
      localStringBuffer.setCharAt(k, c);
    }
    if ((j >= 0) && (j + 1 < i)) {
      localStringBuffer.delete(j + 1, i);
    }
    k = 0;
    i = paramString2.length();
    while (k < i) {
      c = paramString2.charAt(k);
      if (c == '.') {
        k++;
        if (k == i) break;
        c = paramString2.charAt(k);
        if (c == '.') {
          k++;
          if (k == i) break;
          c = paramString2.charAt(k);
          if ((c == '\\') || (c == '/')) {
            j--;
            while ((j >= 0) && (localStringBuffer.charAt(j) != '/')) j--;
            if (j < 0)
            {
              return null;
            }
            localStringBuffer.delete(j + 1, localStringBuffer.length());
          }
        } else if ((c != '\\') && (c != '/'))
        {
          k--;
          break;
        }
      } else {
        if (c >= ' ')
          break;
      }
      k++;
    }
    if (k == i)
    {
      return null;
    }
    if (k > 0) localStringBuffer.append(paramString2.substring(k)); else
      localStringBuffer.append(paramString2);
    i = localStringBuffer.length();
    if (j < 0) j = 0;
    while (j < i) {
      c = localStringBuffer.charAt(j);
      if (c == '\\') {
        localStringBuffer.setCharAt(j, '/');
      }
      j++;
    }
    return localStringBuffer.toString();
  }

  public static final String toFileSystemName(String paramString, int paramInt) {
    if (isFileSystemName(paramString))
      return paramString.replace(notSeparator, File.separatorChar);
    String str1 = get(paramInt);
    if (str1 == null)
      return null;
    int i = str1.charAt(str1.length() - 1);
    String str2 = null;
    if ((i != 47) && (i != 92))
      str2 = concatNames(str1 + '/', paramString);
    else
      str2 = concatNames(str1, paramString);
    if (str2 != null)
      str2 = str2.replace(notSeparator, File.separatorChar);
    return str2;
  }

  public static final String toFileSystemName(String paramString1, String paramString2, int paramInt) {
    if (isFileSystemName(paramString2))
      return paramString2.replace(notSeparator, File.separatorChar);
    if (!isFileSystemName(paramString1))
      paramString1 = toFileSystemName(paramString1, paramInt);
    if (paramString1 == null)
      return null;
    String str = concatNames(paramString1, paramString2);
    if (str != null)
      str = str.replace(notSeparator, File.separatorChar);
    return str;
  }
  public static final native String get(int paramInt);

  public static final native void add(String paramString);

  public static final native void remove(String paramString);

  static {
    RTS.loadNative();
    if (File.separatorChar == '/') notSeparator = '\\'; else
      notSeparator = '/';
  }
}