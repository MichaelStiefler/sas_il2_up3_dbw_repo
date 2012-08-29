package com.maddox.il2.net;

import com.maddox.rts.HomePath;
import com.maddox.rts.NetAddress;
import com.maddox.rts.SFSReader;
import com.maddox.util.NumberTokenizer;
import com.maddox.util.SharedTokenizer;
import com.maddox.util.StrMath;
import com.maddox.util.UnicodeTo8bit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class NetBanned
{
  public static String fileName = "banned.txt";

  public ArrayList name = new ArrayList();
  public ArrayList patt = new ArrayList();
  public ArrayList ip = new ArrayList();

  private static int[] tmp = new int[4];

  public boolean isExist(String paramString)
  {
    for (int i = 0; i < this.name.size(); i++) {
      if (paramString.equals(this.name.get(i)))
        return true;
    }
    for (i = 0; i < this.patt.size(); i++) {
      if (StrMath.simple((String)this.patt.get(i), paramString))
        return true;
    }
    return false;
  }
  public boolean isExist(NetAddress paramNetAddress) {
    int i = this.ip.size();
    if (i < 0) return false;
    NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramNetAddress.getHostAddress(), ".");
    if (localNumberTokenizer.countTokens() != 4)
      return false;
    for (int j = 0; j < 4; j++) {
      tmp[j] = localNumberTokenizer.next(0);
    }
    for (j = 0; j < i; j++) {
      int k = 1;
      int[][] arrayOfInt = (int[][])(int[][])this.ip.get(j);
      for (int m = 0; m < 4; m++) {
        if (arrayOfInt[m][0] == -1)
          continue;
        if ((arrayOfInt[m][0] <= tmp[m]) && (tmp[m] <= arrayOfInt[m][1]))
          continue;
        k = 0;
        break;
      }
      if (k != 0) {
        return true;
      }
    }
    return false;
  }

  public void save() {
    save(fileName);
  }
  public void save(String paramString) {
    try { PrintWriter localPrintWriter = new PrintWriter(new BufferedWriter(new FileWriter(HomePath.toFileSystemName(paramString, 0))));
      String str;
      for (int i = 0; i < this.name.size(); i++) {
        str = (String)this.name.get(i);
        localPrintWriter.println("name " + UnicodeTo8bit.save(str, true));
      }
      for (i = 0; i < this.patt.size(); i++) {
        str = (String)this.patt.get(i);
        localPrintWriter.println("pattern " + UnicodeTo8bit.save(str, true));
      }
      for (i = 0; i < this.ip.size(); i++) {
        localPrintWriter.println("ip " + ipItem(i));
      }

      localPrintWriter.close();
    } catch (IOException localIOException) {
      System.out.println("Save file " + paramString + " failed: " + localIOException.getMessage());
    }
  }

  public String ipItem(int paramInt) {
    int[][] arrayOfInt = (int[][])(int[][])this.ip.get(paramInt);
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < 4; i++) {
      if (i != 0) localStringBuffer.append('.');
      if (arrayOfInt[i][0] == -1) {
        localStringBuffer.append('*');
      } else if (arrayOfInt[i][0] == arrayOfInt[i][1]) {
        localStringBuffer.append(arrayOfInt[i][0]);
      } else {
        localStringBuffer.append(arrayOfInt[i][0]);
        localStringBuffer.append('-');
        localStringBuffer.append(arrayOfInt[i][1]);
      }
    }
    return localStringBuffer.toString();
  }
  public void load() {
    load(fileName);
  }
  public void load(String paramString) {
    try { BufferedReader localBufferedReader = new BufferedReader(new SFSReader(paramString));
      while (true) {
        String str1 = localBufferedReader.readLine();
        if (str1 == null)
          break;
        int i = str1.length();
        if (i == 0)
          continue;
        SharedTokenizer.set(str1);
        String str2 = SharedTokenizer.next(null);
        if (str2 == null)
          continue;
        String str3 = SharedTokenizer.next(null);
        if (str3 == null)
          continue;
        if (str2.equals("name")) {
          if (!this.name.contains(str3))
            this.name.add(str3);
        } else if (str2.equals("pattern")) {
          if (!this.patt.contains(str3))
            this.patt.add(str3);
        } else if (str2.equals("ip")) {
          int[][] arrayOfInt = ipItem(str3);
          if ((arrayOfInt != null) && 
            (findIpItem(arrayOfInt) == -1))
            this.ip.add(arrayOfInt);
        }
      }
      localBufferedReader.close();
    } catch (IOException localIOException) {
      System.out.println("File " + paramString + " load failed: " + localIOException.getMessage());
    }
  }

  public int[][] ipItem(String paramString) {
    NumberTokenizer localNumberTokenizer1 = new NumberTokenizer(paramString, ".");
    if (localNumberTokenizer1.countTokens() != 4)
      return (int[][])null;
    int[][] arrayOfInt = new int[4][2];
    try {
      for (int i = 0; i < 4; i++) {
        String str = localNumberTokenizer1.next();
        if ("*".equals(str))
        {
          byte tmp67_66 = -1; arrayOfInt[i][1] = tmp67_66; arrayOfInt[i][0] = tmp67_66;
        } else if (str.indexOf("-") >= 0) {
          NumberTokenizer localNumberTokenizer2 = new NumberTokenizer(str, "-");
          arrayOfInt[i][0] = localNumberTokenizer2.next(-1);
          arrayOfInt[i][1] = localNumberTokenizer2.next(-1);
          if (arrayOfInt[i][0] == -1) return (int[][])null;
          if (arrayOfInt[i][1] == -1) return (int[][])null; 
        }
        else {
          int j = Integer.parseInt(str);
          int tmp172_170 = j; arrayOfInt[i][1] = tmp172_170; arrayOfInt[i][0] = tmp172_170;
        }
      }
    } catch (Exception localException) {
      return (int[][])null;
    }
    return arrayOfInt;
  }

  public int findIpItem(int[][] paramArrayOfInt) {
    for (int i = 0; i < this.ip.size(); i++) {
      int[][] arrayOfInt = (int[][])(int[][])this.ip.get(i);
      int j = 1;
      for (int k = 0; k < 4; k++)
        for (int m = 0; m < 2; m++)
          if (arrayOfInt[k][m] != paramArrayOfInt[k][m])
            j = 0;
      if (j != 0) return i;
    }
    return -1;
  }
}