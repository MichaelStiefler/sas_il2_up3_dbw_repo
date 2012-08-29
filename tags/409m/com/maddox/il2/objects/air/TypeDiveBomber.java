// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TypeDiveBomber.java

package com.maddox.il2.objects.air;

import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import java.io.IOException;

public interface TypeDiveBomber
{

    public abstract boolean typeDiveBomberToggleAutomation();

    public abstract void typeDiveBomberAdjAltitudeReset();

    public abstract void typeDiveBomberAdjAltitudePlus();

    public abstract void typeDiveBomberAdjAltitudeMinus();

    public abstract void typeDiveBomberAdjVelocityReset();

    public abstract void typeDiveBomberAdjVelocityPlus();

    public abstract void typeDiveBomberAdjVelocityMinus();

    public abstract void typeDiveBomberAdjDiveAngleReset();

    public abstract void typeDiveBomberAdjDiveAnglePlus();

    public abstract void typeDiveBomberAdjDiveAngleMinus();

    public abstract void typeDiveBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException;

    public abstract void typeDiveBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException;
}
