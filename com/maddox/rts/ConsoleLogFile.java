// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Console.java

package com.maddox.rts;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Calendar;

// Referenced classes of package com.maddox.rts:
//            ConsoleOut, Console, HomePath

class ConsoleLogFile
    implements com.maddox.rts.ConsoleOut
{

    public void flush()
    {
        f.flush();
    }

    public void println(java.lang.String s)
    {
        f.println(s);
    }

    public void type(java.lang.String s)
    {
        if(com.maddox.rts.Console.bTypeTimeInLogFile)
        {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            f.print("[" + shortDate.format(calendar.getTime()) + "]\t");
        }
        f.print(s);
    }

    public void close()
    {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        f.println("[" + longDate.format(calendar.getTime()) + "] -------------- END log session -------------");
        f.close();
    }

    public ConsoleLogFile(java.lang.String s)
        throws java.io.FileNotFoundException
    {
        f = new PrintWriter(new FileOutputStream(com.maddox.rts.HomePath.toFileSystemName(s, 0), true));
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        longDate = java.text.DateFormat.getDateTimeInstance(2, 2);
        shortDate = java.text.DateFormat.getTimeInstance(2);
        f.println("[" + longDate.format(calendar.getTime()) + "] ------------ BEGIN log session -------------");
    }

    private static final boolean useCalendar = true;
    private java.io.PrintWriter f;
    private java.text.DateFormat longDate;
    private java.text.DateFormat shortDate;
}
