// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Predator.java

package com.maddox.il2.ai.ground;


// Referenced classes of package com.maddox.il2.ai.ground:
//            Prey

public interface Predator
    extends com.maddox.il2.ai.ground.Prey
{

    public abstract int WeaponsMask();

    public abstract float AttackMaxDistance();
}
