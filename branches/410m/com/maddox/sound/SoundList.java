// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SoundList.java

package com.maddox.sound;


// Referenced classes of package com.maddox.sound:
//            SoundFX

public class SoundList
{

    public SoundList()
    {
        first = null;
    }

    public com.maddox.sound.SoundFX get()
    {
        return first;
    }

    public void clear()
    {
        while(first != null) 
            first.remove();
    }

    public void destroy()
    {
        while(first != null) 
            first.destroy();
    }

    protected com.maddox.sound.SoundFX first;
}
