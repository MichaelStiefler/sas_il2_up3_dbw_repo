// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   EnvControl.java

package com.maddox.sound;

import com.maddox.rts.SectFile;

// Referenced classes of package com.maddox.sound:
//            SoundControl, BaseObject

public class EnvControl extends com.maddox.sound.SoundControl
{

    public EnvControl()
    {
    }

    public void load(com.maddox.rts.SectFile sectfile, java.lang.String s)
    {
        boolean flag = handle != 0;
        if(flag)
        {
            float f = sectfile.get(s, "gain0", 1.0F);
            float f1 = sectfile.get(s, "gain1", 1.0F);
            boolean flag1 = com.maddox.sound.EnvControl.jniSet(handle, f, f1);
            if(!flag1)
                com.maddox.sound.BaseObject.printf("ERROR loading sound control " + s);
        }
    }

    protected int getClassId()
    {
        return 3;
    }

    protected static native boolean jniSet(int i, float f, float f1);
}
