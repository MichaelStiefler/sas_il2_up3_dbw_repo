// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SFS.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            SFSException, RTS

public final class SFS
{

    public static final void mount(java.lang.String s)
        throws com.maddox.rts.SFSException
    {
        com.maddox.rts.SFS.mount(s, 0);
    }

    public static final native void mount(java.lang.String s, int i)
        throws com.maddox.rts.SFSException;

    public static final void mountAs(java.lang.String s, java.lang.String s1)
        throws com.maddox.rts.SFSException
    {
        com.maddox.rts.SFS.mountAs(s, s1, 0);
    }

    public static final native void mountAs(java.lang.String s, java.lang.String s1, int i)
        throws com.maddox.rts.SFSException;

    public static final native void unMount(java.lang.String s)
        throws com.maddox.rts.SFSException;

    public static final native void unMountPath(java.lang.String s)
        throws com.maddox.rts.SFSException;

    public static final native void setCacheBlockSize(boolean flag, int i);

    public static final native int getCacheBlockSize(boolean flag);

    public static final native void setCacheSize(boolean flag, int i);

    public static final native int getCacheSize(boolean flag);

    private SFS()
    {
    }

    public static final int FLAG_SYSTEM_BUFFERING = 0;
    public static final int FLAG_NO_BUFFERING = 1;
    public static final int FLAG_INTERNAL_BUFFERING = 2;
    public static final int FLAG_MAPPING = 3;

    static 
    {
        com.maddox.rts.RTS.loadNative();
    }
}
