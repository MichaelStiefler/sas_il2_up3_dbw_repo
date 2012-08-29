// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SoundControl.java

package com.maddox.sound;

import com.maddox.rts.SectFile;

// Referenced classes of package com.maddox.sound:
//            BaseObject

public class SoundControl extends com.maddox.sound.BaseObject
{

    protected SoundControl()
    {
        handle = 0;
    }

    protected int getClassId()
    {
        return 0;
    }

    protected void init(int i, int j)
    {
        handle = com.maddox.sound.SoundControl.jniCreate(i, j, getClassId());
    }

    public void load(com.maddox.rts.SectFile sectfile, java.lang.String s)
    {
    }

    protected static native int jniCreate(int i, int j, int k);

    public static final int BOUNDS_CONTROL = 1;
    public static final int MOD_CONTROL = 2;
    public static final int ENV_CONTROL = 3;
    protected int handle;
}
