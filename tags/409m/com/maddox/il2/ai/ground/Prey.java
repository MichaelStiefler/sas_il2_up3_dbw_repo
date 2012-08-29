// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Prey.java

package com.maddox.il2.ai.ground;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.BulletProperties;

public interface Prey
{

    public abstract int HitbyMask();

    public abstract int chooseBulletType(com.maddox.il2.engine.BulletProperties abulletproperties[]);

    public abstract int chooseShotpoint(com.maddox.il2.engine.BulletProperties bulletproperties);

    public abstract boolean getShotpointOffset(int i, com.maddox.JGP.Point3d point3d);
}
