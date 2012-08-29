// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   StringClipboard.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            RTS

public final class StringClipboard
{

    public StringClipboard()
    {
    }

    public static void copy(java.lang.String s)
    {
        if(s == null || s.length() == 0)
        {
            return;
        } else
        {
            com.maddox.rts.StringClipboard.Copy(s);
            return;
        }
    }

    public static java.lang.String paste()
    {
        java.lang.String s = com.maddox.rts.StringClipboard.Paste();
        if(s == null)
            s = "";
        return s;
    }

    private static native void Copy(java.lang.String s);

    private static native java.lang.String Paste();

    static 
    {
        com.maddox.rts.RTS.loadNative();
    }
}
