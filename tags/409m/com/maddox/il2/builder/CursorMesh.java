// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Builder.java

package com.maddox.il2.builder;

import com.maddox.il2.engine.ActorMesh;
import com.maddox.rts.Message;

class CursorMesh extends com.maddox.il2.engine.ActorMesh
{

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public CursorMesh(java.lang.String s)
    {
        super(s);
        flags |= 0x2000;
        drawing(false);
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }
}
