// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TypeAIM9Carrier.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3f;
import com.maddox.il2.engine.Actor;

public interface TypeAIM9Carrier
{

    public abstract com.maddox.il2.engine.Actor getAIM9target();

    public abstract boolean hasMissiles();

    public abstract int getAIM9lockState();

    public abstract com.maddox.JGP.Point3f getAIM9targetOffset();
}
