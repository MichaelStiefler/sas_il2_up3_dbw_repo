// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   InterpolateTickRef.java

package com.maddox.il2.engine;


// Referenced classes of package com.maddox.il2.engine:
//            InterpolateRef, MsgInterpolateTick

public class InterpolateTickRef extends com.maddox.il2.engine.InterpolateRef
{

    public boolean tick()
    {
        com.maddox.il2.engine.MsgInterpolateTick.send(actor);
        return true;
    }

    public InterpolateTickRef()
    {
    }
}
