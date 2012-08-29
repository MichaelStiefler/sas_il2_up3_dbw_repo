package com.maddox.rts;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Calendar;

class ConsoleLogFile
  implements ConsoleOut
{
  private static final boolean useCalendar = true;
  private PrintWriter f;
  private DateFormat longDate;
  private DateFormat shortDate;

  public void flush()
  {
    this.f.flush();
  }
  public void println(String paramString) {
    this.f.println(paramString);
  }

  public void type(String paramString) {
    if (Console.bTypeTimeInLogFile) {
      Calendar localCalendar = Calendar.getInstance();

      this.f.print("[" + this.shortDate.format(localCalendar.getTime()) + "]\t");
    }
    this.f.print(paramString);
  }

  public void close()
  {
    Calendar localCalendar = Calendar.getInstance();

    this.f.println("[" + this.longDate.format(localCalendar.getTime()) + "] -------------- END log session -------------");

    this.f.close();
  }

  public ConsoleLogFile(String paramString)
    throws FileNotFoundException
  {
    this.f = new PrintWriter(new FileOutputStream(HomePath.toFileSystemName(paramString, 0), true));

    Calendar localCalendar = Calendar.getInstance();

    this.longDate = DateFormat.getDateTimeInstance(2, 2);
    this.shortDate = DateFormat.getTimeInstance(2);
    this.f.println("[" + this.longDate.format(localCalendar.getTime()) + "] ------------ BEGIN log session -------------");
  }
}