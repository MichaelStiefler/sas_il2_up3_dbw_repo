// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ModControl.java

package com.maddox.sound;

import com.maddox.rts.SectFile;

// Referenced classes of package com.maddox.sound:
//            SoundControl, BaseObject

public class ModControl extends com.maddox.sound.SoundControl
{

    public ModControl()
    {
    }

    public void load(com.maddox.rts.SectFile sectfile, java.lang.String s)
    {
        boolean flag = handle != 0;
        if(flag)
        {
            java.lang.String s1 = sectfile.get(s, "file", (java.lang.String)null);
            float f = sectfile.get(s, "pitch", 1.0F);
            float f1 = sectfile.get(s, "min", 0.3F);
            float f2 = sectfile.get(s, "max", 1.0F);
            boolean flag1 = com.maddox.sound.ModControl.jniSet(handle, s1, f, f1, f2);
            if(!flag1)
                com.maddox.sound.BaseObject.printf("ERROR loading sound control " + s);
        }
    }

    protected int getClassId()
    {
        return 2;
    }

    protected static native boolean jniSet(int i, java.lang.String s, float f, float f1, float f2);
}
