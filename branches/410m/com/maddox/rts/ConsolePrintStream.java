// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Console.java

package com.maddox.rts;

import java.io.PrintStream;

// Referenced classes of package com.maddox.rts:
//            ConsoleOutputStream, Console

class ConsolePrintStream extends java.io.PrintStream
{

    public ConsolePrintStream(boolean flag, com.maddox.rts.Console console1)
    {
        super(new ConsoleOutputStream());
        bErr = flag;
        console = console1;
    }

    private void write(char ac[])
    {
        if(bErr)
        {
            for(int i = 0; i < ac.length; i++)
                console.errWrite(ac[i]);

        } else
        {
            for(int j = 0; j < ac.length; j++)
                console.outWrite(ac[j]);

        }
    }

    private void write(java.lang.String s)
    {
        if(bErr)
        {
            for(int i = 0; i < s.length(); i++)
                console.errWrite(s.charAt(i));

        } else
        {
            for(int j = 0; j < s.length(); j++)
                console.outWrite(s.charAt(j));

        }
    }

    private void newLine()
    {
        write("\n");
    }

    public void flush()
    {
        if(bErr)
            console.errFlush();
        else
            console.outFlush();
    }

    public void close()
    {
    }

    public void write(int i)
    {
    }

    public void write(byte abyte0[], int i, int j)
    {
    }

    public void print(boolean flag)
    {
        write(flag ? "true" : "false");
    }

    public void print(char c)
    {
        write(java.lang.String.valueOf(c));
    }

    public void print(int i)
    {
        write(java.lang.String.valueOf(i));
    }

    public void print(long l)
    {
        write(java.lang.String.valueOf(l));
    }

    public void print(float f)
    {
        write(java.lang.String.valueOf(f));
    }

    public void print(double d)
    {
        write(java.lang.String.valueOf(d));
    }

    public void print(char ac[])
    {
        write(ac);
    }

    public void print(java.lang.String s)
    {
        if(s == null)
            s = "null";
        write(s);
    }

    public void print(java.lang.Object obj)
    {
        write(java.lang.String.valueOf(obj));
    }

    public void println()
    {
        newLine();
    }

    public void println(boolean flag)
    {
        synchronized(this)
        {
            print(flag);
            newLine();
        }
    }

    public void println(char c)
    {
        synchronized(this)
        {
            print(c);
            newLine();
        }
    }

    public void println(int i)
    {
        synchronized(this)
        {
            print(i);
            newLine();
        }
    }

    public void println(long l)
    {
        synchronized(this)
        {
            print(l);
            newLine();
        }
    }

    public void println(float f)
    {
        synchronized(this)
        {
            print(f);
            newLine();
        }
    }

    public void println(double d)
    {
        synchronized(this)
        {
            print(d);
            newLine();
        }
    }

    public void println(char ac[])
    {
        synchronized(this)
        {
            print(ac);
            newLine();
        }
    }

    public void println(java.lang.String s)
    {
        synchronized(this)
        {
            print(s);
            newLine();
        }
    }

    public void println(java.lang.Object obj)
    {
        synchronized(this)
        {
            print(obj);
            newLine();
        }
    }

    private boolean bErr;
    private java.lang.String pref;
    private com.maddox.rts.Console console;
}
