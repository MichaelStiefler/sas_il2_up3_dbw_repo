// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BulletEmitter.java

package com.maddox.il2.ai;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;

public interface BulletEmitter
{

    public abstract int countBullets();

    public abstract boolean haveBullets();

    public abstract void loadBullets();

    public abstract void loadBullets(int i);

    public abstract void _loadBullets(int i);

    public abstract boolean isEnablePause();

    public abstract boolean isPause();

    public abstract void setPause(boolean flag);

    public abstract float bulletMassa();

    public abstract boolean isShots();

    public abstract void shots(int i);

    public abstract void shots(int i, float f);

    public abstract com.maddox.il2.ai.BulletEmitter detach(com.maddox.il2.engine.HierMesh hiermesh, int i);

    public abstract java.lang.String getHookName();

    public abstract void set(com.maddox.il2.engine.Actor actor, java.lang.String s, com.maddox.il2.engine.Loc loc);

    public abstract void set(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1);

    public abstract void set(com.maddox.il2.engine.Actor actor, java.lang.String s);
}
