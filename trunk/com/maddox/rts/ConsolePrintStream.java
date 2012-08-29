package com.maddox.rts;

import java.io.PrintStream;

class ConsolePrintStream extends PrintStream
{
  private boolean bErr;
  private String pref;
  private Console console;

  public ConsolePrintStream(boolean paramBoolean, Console paramConsole)
  {
    super(new ConsoleOutputStream());
    this.bErr = paramBoolean;
    this.console = paramConsole;
  }

  private void write(char[] paramArrayOfChar)
  {
    int i;
    if (this.bErr)
      for (i = 0; i < paramArrayOfChar.length; i++)
        this.console.errWrite(paramArrayOfChar[i]);
    else
      for (i = 0; i < paramArrayOfChar.length; i++)
        this.console.outWrite(paramArrayOfChar[i]);
  }

  private void write(String paramString)
  {
    int i;
    if (this.bErr)
      for (i = 0; i < paramString.length(); i++)
        this.console.errWrite(paramString.charAt(i));
    else
      for (i = 0; i < paramString.length(); i++)
        this.console.outWrite(paramString.charAt(i));
  }

  private void newLine() {
    write("\n");
  }

  public void flush() {
    if (this.bErr) this.console.errFlush(); else
      this.console.outFlush(); 
  }
  public void close() {
  }
  public void write(int paramInt) {
  }
  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
  }

  public void print(boolean paramBoolean) {
    write(paramBoolean ? "true" : "false");
  }
  public void print(char paramChar) {
    write(String.valueOf(paramChar));
  }
  public void print(int paramInt) {
    write(String.valueOf(paramInt));
  }
  public void print(long paramLong) {
    write(String.valueOf(paramLong));
  }
  public void print(float paramFloat) {
    write(String.valueOf(paramFloat));
  }
  public void print(double paramDouble) {
    write(String.valueOf(paramDouble));
  }
  public void print(char[] paramArrayOfChar) {
    write(paramArrayOfChar);
  }
  public void print(String paramString) {
    if (paramString == null) {
      paramString = "null";
    }
    write(paramString);
  }
  public void print(Object paramObject) {
    write(String.valueOf(paramObject));
  }
  public void println() {
    newLine();
  }
  public void println(boolean paramBoolean) {
    synchronized (this) {
      print(paramBoolean);
      newLine();
    }
  }

  public void println(char paramChar) {
    synchronized (this) {
      print(paramChar);
      newLine();
    }
  }

  public void println(int paramInt) {
    synchronized (this) {
      print(paramInt);
      newLine();
    }
  }

  public void println(long paramLong) {
    synchronized (this) {
      print(paramLong);
      newLine();
    }
  }

  public void println(float paramFloat) {
    synchronized (this) {
      print(paramFloat);
      newLine();
    }
  }

  public void println(double paramDouble) {
    synchronized (this) {
      print(paramDouble);
      newLine();
    }
  }

  public void println(char[] paramArrayOfChar) {
    synchronized (this) {
      print(paramArrayOfChar);
      newLine();
    }
  }

  public void println(String paramString) {
    synchronized (this) {
      print(paramString);
      newLine();
    }
  }

  public void println(Object paramObject) {
    synchronized (this) {
      print(paramObject);
      newLine();
    }
  }
}