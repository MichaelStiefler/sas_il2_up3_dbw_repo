package com.maddox.il2.game;

import com.maddox.rts.IniFile;
import com.maddox.util.UnicodeTo8bit;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class SConsole
{
  static BufferedReader in = null;
  static int prompt = -1;
  static boolean bReady = true;

  public static void main(String[] paramArrayOfString) {
    int i = 0;
    IniFile localIniFile = new IniFile("confc.ini", 0);
    i = localIniFile.get("Console", "IP", 0, 0, 65000);
    if (i == 0) {
      return; } 
Socket localSocket = null;
    PrintWriter localPrintWriter = null;
    BufferedReader localBufferedReader = null;
    Object localObject;
    try { InetAddress localInetAddress = InetAddress.getLocalHost();
      if ((paramArrayOfString != null) && (paramArrayOfString.length > 0)) {
        localInetAddress = InetAddress.getByName(paramArrayOfString[0]);
      }
      localObject = InetAddress.getLocalHost();
      String str = localIniFile.get("NET", "localHost", (String)null);
      if ((str != null) && (str.length() > 0))
        localObject = InetAddress.getByName(str);
      localSocket = new Socket(localInetAddress, i, (InetAddress)localObject, 0);
      localPrintWriter = new PrintWriter(localSocket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(localSocket.getInputStream()));

      localBufferedReader = new BufferedReader(new InputStreamReader(System.in));
    } catch (Exception localException1) {
      System.err.println("Couldn't get I/O for the connection to IL2");
      System.exit(1);
    }

    1 local1 = new Thread() {
      public void run() { String str1 = "<consoleN><";
        int i = str1.length();
        try {
          while (SConsole.bReady) {
            String str2 = SConsole.in.readLine();
            if (str2 == null) break;
            str2 = UnicodeTo8bit.load(str2);
            int j = str2.length();
            int k = 0;
            int m = 0;
            while (k + i <= j) {
              if (str2.regionMatches(k, str1, 0, i)) {
                int n = k + i;
                int i1 = str2.indexOf(">", n);
                if (i1 > 0) {
                  try {
                    m = 1;
                    SConsole.prompt = Integer.parseInt(str2.substring(n, i1)); } catch (Exception localException2) {
                  }
                  if (i1 + 1 < j) str2 = str2.substring(0, k) + str2.substring(i1 + 1, j); else
                    str2 = str2.substring(0, k);
                  j = str2.length();
                  if ((j == 1) && (str2.charAt(0) == '\n')) {
                    j = 0;
                    str2 = null;
                  }
                } else {
                  k++;
                }
              } else {
                k++;
              }
            }
            if ((str2 != null) && (str2.length() > 0)) {
              System.out.print(str2);
            }
            if (m != 0) {
              System.out.print(SConsole.prompt + ">");
              System.out.flush();
            }
          }
        } catch (Exception localException1) {
          SConsole.bReady = false;
        }
      }
    };
    local1.start();
    try
    {
      System.out.println("IL2 remote console");
      System.out.println("For end console enter 'quit'");
      localPrintWriter.println("server");
      do {
        if ("quit".equals(localObject)) {
          localPrintWriter.println("<QUIT QUIT>");
          localPrintWriter.flush();
          break;
        }
        localPrintWriter.println(UnicodeTo8bit.save((String)localObject, false));
        localPrintWriter.flush();
        if ("exit".equals(localObject))
          break;
        if (!bReady) break; 
      }while ((localObject = localBufferedReader.readLine()) != null);
    }
    catch (Exception localException2)
    {
    }

    bReady = false;
    try
    {
      localPrintWriter.close();
      in.close();
      localSocket.close(); } catch (Exception localException3) {
    }
    local1.interrupt();

    System.exit(0);
  }
}