// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Compress.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            RTS

public final class Compress
{

    public static final int code(int i, byte abyte0[], int j)
    {
        if(i == 0)
            return j;
        else
            return com.maddox.rts.Compress.Code(i, abyte0, j);
    }

    public static final boolean decode(int i, byte abyte0[], int j)
    {
        if(i == 0)
            return true;
        else
            return com.maddox.rts.Compress.Decode(i, abyte0, j);
    }

    private static native int Code(int i, byte abyte0[], int j);

    private static native boolean Decode(int i, byte abyte0[], int j);

    private Compress()
    {
    }

    public static final int NONE = 0;
    public static final int LZSS = 1;
    public static final int ZIP = 2;

    static 
    {
        com.maddox.rts.RTS.loadNative();
    }
}
