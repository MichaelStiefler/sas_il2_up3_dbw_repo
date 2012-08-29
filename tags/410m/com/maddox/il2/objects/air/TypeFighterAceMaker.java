// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TypeFighterAceMaker.java

package com.maddox.il2.objects.air;

import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import java.io.IOException;

public interface TypeFighterAceMaker
{

    public abstract boolean typeFighterAceMakerToggleAutomation();

    public abstract void typeFighterAceMakerAdjDistanceReset();

    public abstract void typeFighterAceMakerAdjDistancePlus();

    public abstract void typeFighterAceMakerAdjDistanceMinus();

    public abstract void typeFighterAceMakerAdjSideslipReset();

    public abstract void typeFighterAceMakerAdjSideslipPlus();

    public abstract void typeFighterAceMakerAdjSideslipMinus();

    public abstract void typeFighterAceMakerReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException;

    public abstract void typeFighterAceMakerReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException;
}
