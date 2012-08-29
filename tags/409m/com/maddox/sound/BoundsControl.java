// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BoundsControl.java

package com.maddox.sound;

import com.maddox.rts.SectFile;

// Referenced classes of package com.maddox.sound:
//            SoundControl, BaseObject

public class BoundsControl extends com.maddox.sound.SoundControl
{

    protected BoundsControl()
    {
    }

    public void load(com.maddox.rts.SectFile sectfile, java.lang.String s)
    {
        boolean flag = handle != 0;
        if(flag)
        {
            float f = sectfile.get(s, "minlo", 0.0F);
            float f1 = sectfile.get(s, "minhi", 0.0F);
            float f2 = sectfile.get(s, "maxlo", 0.0F);
            float f3 = sectfile.get(s, "maxhi", 0.0F);
            float f4 = sectfile.get(s, "value", 0.0F);
            float f5 = sectfile.get(s, "pmin", 0.0F);
            float f6 = sectfile.get(s, "pmax", 0.0F);
            boolean flag1 = com.maddox.sound.BoundsControl.jniSet(handle, f, f1, f2, f3, f4, f5, f6);
            if(!flag1)
                com.maddox.sound.BaseObject.printf("ERROR loading sound control " + s);
        }
    }

    protected int getClassId()
    {
        return 1;
    }

    protected static native boolean jniSet(int i, float f, float f1, float f2, float f3, float f4, float f5, float f6);
}
