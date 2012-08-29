package com.maddox.util;

public class QuoteTokenizer
{
  private StringBuffer buf = null;

  public QuoteTokenizer(String paramString)
  {
    this.buf = new StringBuffer(paramString);

    while ((this.buf.length() > 0) && 
      (this.buf.charAt(0) == ' ')) this.buf.deleteCharAt(0);

    while (this.buf.length() > 0) {
      int i = this.buf.length() - 1;
      if (this.buf.charAt(i) != ' ') break; this.buf.deleteCharAt(i);
    }
  }

  public String getGap()
  {
    String str = this.buf.substring(0);
    this.buf.delete(0, this.buf.length());
    return str;
  }

  public boolean hasMoreTokens() {
    return this.buf.length() > 0;
  }

  public String nextToken() {
    return nextWord();
  }

  public String next() {
    return nextWord();
  }

  private String nextWord() {
    while ((this.buf.length() > 0) && 
      (this.buf.charAt(0) == ' ')) this.buf.deleteCharAt(0);

    if (this.buf.length() == 0)
      return "";
    int i = 0;
    String str = null;
    if (this.buf.charAt(i) == '"') {
      this.buf.deleteCharAt(0);
      while (i < this.buf.length()) {
        if (this.buf.charAt(i) == '"') {
          if ((i > 0) && (this.buf.charAt(i - 1) == '\\')) {
            this.buf.deleteCharAt(i - 1);
            i--;
          } else {
            this.buf.deleteCharAt(i);
            break;
          }
        }
        i++;
      }
    }
    int j = this.buf.length();
    while ((i < j) && 
      (this.buf.charAt(i) != ' ')) {
      i++;
    }

    str = this.buf.substring(0, i);
    this.buf.delete(0, i);

    while ((this.buf.length() > 0) && 
      (this.buf.charAt(0) == ' ')) this.buf.deleteCharAt(0);

    return str;
  }

  public static String toToken(String paramString) {
    int i = paramString.length();
    int j = 0;
    for (; j < i; j++) {
      int k = paramString.charAt(j);
      if ((k == 32) || (k == 34))
        break;
    }
    if (j == i) {
      return paramString;
    }
    StringBuffer localStringBuffer = new StringBuffer(paramString);
    for (j = 0; j < i; j++) {
      int m = localStringBuffer.charAt(j);
      if (m == 34) {
        localStringBuffer.insert(j, '\\');
        j++;
        i++;
      }
    }
    localStringBuffer.insert(0, '"');
    localStringBuffer.append('"');
    return localStringBuffer.toString();
  }
}