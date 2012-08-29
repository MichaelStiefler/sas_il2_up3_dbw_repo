// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CollisionInterface.java

package com.maddox.il2.engine;


// Referenced classes of package com.maddox.il2.engine:
//            Actor

public interface CollisionInterface
{

    public abstract boolean collision_isEnabled();

    public abstract double collision_getCylinderR();

    public abstract void collision_processing(com.maddox.il2.engine.Actor actor);
}
