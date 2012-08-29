package com.maddox.rts;

import java.io.PrintStream;

public class ConsoleOutPrint
  implements ConsoleOut
{
  private PrintStream out;

  public ConsoleOutPrint(PrintStream paramPrintStream)
  {
    this.out = paramPrintStream;
  }

  public void type(String paramString) {
    this.out.print(paramString);
  }
  public void flush() {
    this.out.flush();
  }
}