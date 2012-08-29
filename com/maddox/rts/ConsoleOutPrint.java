// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ConsoleOutPrint.java

package com.maddox.rts;

import java.io.PrintStream;

// Referenced classes of package com.maddox.rts:
//            ConsoleOut

public class ConsoleOutPrint
    implements com.maddox.rts.ConsoleOut
{

    public ConsoleOutPrint(java.io.PrintStream printstream)
    {
        out = printstream;
    }

    public void type(java.lang.String s)
    {
        out.print(s);
    }

    public void flush()
    {
        out.flush();
    }

    private java.io.PrintStream out;
}
