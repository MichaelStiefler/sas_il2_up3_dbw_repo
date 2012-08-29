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
    for (int j = 0; j < this.patt.size(); j++) {
      if (StrMath.simple((String)this.patt.get(j), paramString))
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
    for (int k = 0; k < i; k++) {
      int m = 1;
      int[][] arrayOfInt = (int[][])this.ip.get(k);
      for (int n = 0; n < 4; n++) {
        if (arrayOfInt[n][0] == -1)
          continue;
        if ((arrayOfInt[n][0] <= tmp[n]) && (tmp[n] <= arrayOfInt[n][1]))
          continue;
        m = 0;
        break;
      }
      if (m != 0) {
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

      for (int i = 0; i < this.name.size(); i++) {
        String str1 = (String)this.name.get(i);
        localPrintWriter.println("name " + UnicodeTo8bit.save(str1, true));
      }
      for (int j = 0; j < this.patt.size(); j++) {
        String str2 = (String)this.patt.get(j);
        localPrintWriter.println("pattern " + UnicodeTo8bit.save(str2, true));
      }
      for (int k = 0; k < this.ip.size(); k++) {
        localPrintWriter.println("ip " + ipItem(k));
      }

      localPrintWriter.close();
    } catch (IOException localIOException) {
      System.out.println("Save file " + paramString + " failed: " + localIOException.getMessage());
    }
  }

  public String ipItem(int paramInt) {
    int[][] arrayOfInt = (int[][])this.ip.get(paramInt);
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
        if (str2.equals("name"))
          if (!this.name.contains(str3)) {
            this.name.add(str3); continue;
          }
        if (str2.equals("pattern"))
          if (!this.patt.contains(str3)) {
            this.patt.add(str3); continue;
          }
        if (str2.equals("ip")) {
          int[][] arrayOfInt = ipItem(str3);
          if ((arrayOfInt == null) || 
            (findIpItem(arrayOfInt) != -1)) continue;
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
      return null;
    int[][] arrayOfInt = new int[4][2];
    try {
      for (int i = 0; i < 4; i++) {
        String str = localNumberTokenizer1.next();
        if ("*".equals(str))
        {
          byte tmp61_60 = -1; arrayOfInt[i][1] = tmp61_60; arrayOfInt[i][0] = tmp61_60;
        } else if (str.indexOf("-") >= 0) {
          NumberTokenizer localNumberTokenizer2 = new NumberTokenizer(str, "-");
          arrayOfInt[i][0] = localNumberTokenizer2.next(-1);
          arrayOfInt[i][1] = localNumberTokenizer2.next(-1);
          if (arrayOfInt[i][0] == -1) return null;
          if (arrayOfInt[i][1] == -1) return null; 
        }
        else {
          int j = Integer.parseInt(str);
          int tmp157_155 = j; arrayOfInt[i][1] = tmp157_155; arrayOfInt[i][0] = tmp157_155;
        }
      }
    } catch (Exception localException) {
      return null;
    }
    return arrayOfInt;
  }

  public int findIpItem(int[][] paramArrayOfInt) {
    for (int i = 0; i < this.ip.size(); i++) {
      int[][] arrayOfInt = (int[][])this.ip.get(i);
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