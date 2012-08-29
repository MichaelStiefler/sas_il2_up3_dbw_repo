// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   TypeGuidedMissileCarrier.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3f;
import com.maddox.il2.engine.Actor;

public interface TypeGuidedMissileCarrier
{

    public abstract com.maddox.il2.engine.Actor getMissileTarget();

    public abstract boolean hasMissiles();

    public abstract void shotMissile();

    public abstract int getMissileLockState();

    public abstract com.maddox.JGP.Point3f getMissileTargetOffset();
}
