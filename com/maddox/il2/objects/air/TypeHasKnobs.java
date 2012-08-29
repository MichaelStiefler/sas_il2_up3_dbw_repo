// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TypeHasKnobs.java

package com.maddox.il2.objects.air;

import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import java.io.IOException;

public interface TypeHasKnobs
{

    public abstract void typeHasKnobsSet(int i, float f);

    public abstract void typeHasKnobsDoSet(int i, float f);

    public abstract void typeHasKnobsPlus(int i);

    public abstract void typeHasKnobsMinus(int i);

    public abstract void typeHasKnobsReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException;

    public abstract void typeHasKnobsReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException;
}
