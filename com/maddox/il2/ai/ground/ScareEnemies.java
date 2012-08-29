// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ScareEnemies.java

package com.maddox.il2.ai.ground;

import com.maddox.il2.engine.Accumulator;
import com.maddox.il2.engine.Actor;

// Referenced classes of package com.maddox.il2.ai.ground:
//            Coward, Prey

public class ScareEnemies
    implements com.maddox.il2.engine.Accumulator
{

    public ScareEnemies()
    {
    }

    public static com.maddox.il2.ai.ground.ScareEnemies enemies()
    {
        return enemies;
    }

    public static void set(int i)
    {
        enemies.usedWeaponsMask = i;
    }

    public void clear()
    {
    }

    public boolean add(com.maddox.il2.engine.Actor actor, double d)
    {
        if(!(actor instanceof com.maddox.il2.ai.ground.Coward) || !(actor instanceof com.maddox.il2.ai.ground.Prey) || (((com.maddox.il2.ai.ground.Prey)actor).HitbyMask() & usedWeaponsMask) == 0)
        {
            return true;
        } else
        {
            ((com.maddox.il2.ai.ground.Coward)actor).scare();
            return true;
        }
    }

    private int usedWeaponsMask;
    private static com.maddox.il2.ai.ground.ScareEnemies enemies = new ScareEnemies();

}
