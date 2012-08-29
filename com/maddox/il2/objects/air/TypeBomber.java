// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TypeBomber.java

package com.maddox.il2.objects.air;

import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import java.io.IOException;

public interface TypeBomber
{

    public abstract boolean typeBomberToggleAutomation();

    public abstract void typeBomberAdjDistanceReset();

    public abstract void typeBomberAdjDistancePlus();

    public abstract void typeBomberAdjDistanceMinus();

    public abstract void typeBomberAdjSideslipReset();

    public abstract void typeBomberAdjSideslipPlus();

    public abstract void typeBomberAdjSideslipMinus();

    public abstract void typeBomberAdjAltitudeReset();

    public abstract void typeBomberAdjAltitudePlus();

    public abstract void typeBomberAdjAltitudeMinus();

    public abstract void typeBomberAdjSpeedReset();

    public abstract void typeBomberAdjSpeedPlus();

    public abstract void typeBomberAdjSpeedMinus();

    public abstract void typeBomberUpdate(float f);

    public abstract void typeBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException;

    public abstract void typeBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException;
}
