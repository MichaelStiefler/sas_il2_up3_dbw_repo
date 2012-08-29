// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RTS.java

package com.maddox.rts;

import java.io.InputStream;
import java.io.PrintStream;

// Referenced classes of package com.maddox.rts:
//            LDRCallBack, SFSInputStream, Finger

public final class RTS extends com.maddox.rts.LDRCallBack
{

    private RTS()
    {
    }

    private int[] getSwTbl(int i)
    {
        if(i < 0)
            i = -i;
        int j = i % 16 + 14;
        int k = i % com.maddox.rts.Finger.kTable.length;
        if(j < 0)
            j = -j % 16;
        if(j < 10)
            j = 10;
        if(k < 0)
            k = -k % com.maddox.rts.Finger.kTable.length;
        int ai[] = new int[j];
        for(int l = 0; l < j; l++)
            ai[l] = com.maddox.rts.Finger.kTable[(k + l) % com.maddox.rts.Finger.kTable.length];

        return ai;
    }

    protected final byte[] load(java.lang.String s)
    {
        byte abyte0[];
        int i = com.maddox.rts.Finger.Int("sdw" + s + "cwc2w9e");
        com.maddox.rts.Finger.Int(getSwTbl(i));
        com.maddox.rts.SFSInputStream sfsinputstream = new SFSInputStream(com.maddox.rts.Finger.LongFN(0L, "cod/" + i));
        abyte0 = new byte[sfsinputstream.available()];
        sfsinputstream.read(abyte0, 0, abyte0.length);
        return abyte0;
        java.lang.Exception exception;
        exception;
        return null;
    }

    protected final java.io.InputStream open(java.lang.String s)
    {
        return new SFSInputStream(s);
        java.lang.Exception exception;
        exception;
        return null;
    }

    public static void cppErrPrint(java.lang.String s)
    {
        java.lang.System.err.print(s);
    }

    public static native int version();

    public static native void setPostProcessCmd(java.lang.String s);

    public static native int interf();

    public static final void loadNative()
    {
        com.maddox.rts.SFSInputStream.loadNative();
    }

    static 
    {
        com.maddox.rts.RTS.loadNative();
    }
}
