// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Preset.java

package com.maddox.sound;

import com.maddox.rts.SectFile;

// Referenced classes of package com.maddox.sound:
//            BaseObject

public class Preset extends com.maddox.sound.BaseObject
{

    protected Preset(java.lang.String s)
    {
        handle = 0;
        flags = 0;
        name = s;
    }

    public void load(com.maddox.rts.SectFile sectfile, java.lang.String s)
        throws java.lang.Exception
    {
    }

    public void save(com.maddox.rts.SectFile sectfile, java.lang.String s)
        throws java.lang.Exception
    {
    }

    protected void finalize()
        throws java.lang.Throwable
    {
        if(handle != 0)
            com.maddox.sound.Preset.jniDestroy(handle);
        super.finalize();
    }

    protected int createObject()
    {
        if(!com.maddox.sound.BaseObject.enabled)
            return 0;
        else
            return com.maddox.sound.Preset.jniCreateObject(handle, flags);
    }

    protected int getObject()
    {
        if(!com.maddox.sound.BaseObject.enabled)
            return 0;
        int i = com.maddox.sound.Preset.jniGetFreeObject(handle, flags);
        if(i == 0)
            i = createObject();
        return i;
    }

    protected static native int jniGet(java.lang.String s);

    protected static native void jniDestroy(int i);

    protected static native int jniCreateObject(int i, int j);

    protected static native int jniGetFreeObject(int i, int j);

    protected java.lang.String name;
    protected int handle;
    protected int flags;
}
