// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   UnitSpawn.java

package com.maddox.il2.ai.ground;

import com.maddox.il2.engine.Actor;

public interface UnitSpawn
{

    public abstract com.maddox.il2.engine.Actor unitSpawn(int i, int j, com.maddox.il2.engine.Actor actor);

    public abstract java.lang.Class unitClass();
}
