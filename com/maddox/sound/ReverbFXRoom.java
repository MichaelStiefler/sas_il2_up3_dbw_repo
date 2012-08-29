// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ReverbFXRoom.java

package com.maddox.sound;


// Referenced classes of package com.maddox.sound:
//            BaseObject, Reverb

public class ReverbFXRoom extends com.maddox.sound.BaseObject
{

    public ReverbFXRoom(float f)
    {
        minAttn = f;
        attn = 1.0F;
        prevAttn = 1.0F;
    }

    public void set(float f)
    {
        attn = minAttn + (1.0F - minAttn) * f;
    }

    protected void tick(com.maddox.sound.Reverb reverb)
    {
        if(prevAttn != attn)
        {
            reverb.set(100, attn);
            prevAttn = attn;
        }
    }

    protected float minAttn;
    protected float attn;
    protected float prevAttn;
}
