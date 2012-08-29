// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   InterpolateTick.java

package com.maddox.il2.engine;


// Referenced classes of package com.maddox.il2.engine:
//            Interpolate, MsgInterpolateTick

public class InterpolateTick extends com.maddox.il2.engine.Interpolate
{

    public boolean tick()
    {
        com.maddox.il2.engine.MsgInterpolateTick.send(actor);
        return true;
    }

    public InterpolateTick()
    {
    }
}
