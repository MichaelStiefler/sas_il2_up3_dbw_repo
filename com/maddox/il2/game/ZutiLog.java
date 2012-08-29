package com.maddox.il2.game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;

public class ZutiLog
{
  public static String zutiLogLocation = System.getProperty("user.home") + System.getProperty("file.separator") + "ZutiLog.txt";

  private FileWriter fstream = null;
  private BufferedWriter out = null;

  public static void Write(String paramString)
  {
    try
    {
      FileWriter localFileWriter = new FileWriter(zutiLogLocation, true);
      BufferedWriter localBufferedWriter = new BufferedWriter(localFileWriter);
      localBufferedWriter.write(paramString + "\n");
      localBufferedWriter.close();
    }
    catch (Exception localException)
    {
    }
  }

  public ZutiLog()
  {
    try
    {
      this.fstream = new FileWriter(zutiLogLocation + ".txt", true);
      this.out = new BufferedWriter(this.fstream);
    }
    catch (Exception localException) {
    }
  }

  public void WriteStr(String paramString) {
    try {
      this.out.write(paramString + "\n");
    }
    catch (Exception localException) {
    }
  }

  public void CloseFile() {
    try {
      this.out.close();
    }
    catch (Exception localException)
    {
    }
  }
}