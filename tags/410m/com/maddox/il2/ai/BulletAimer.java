// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BulletAimer.java

package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;

public interface BulletAimer
{

    public abstract float TravelTime(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1);

    public abstract boolean FireDirection(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, com.maddox.JGP.Vector3d vector3d);
}
