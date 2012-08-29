// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.objects.ActorCrater;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombType3AntiAirChild extends com.maddox.il2.objects.weapons.Bomb
{

    public BombType3AntiAirChild()
    {
    }

    protected boolean haveSound()
    {
        return false;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        java.lang.Class class1;
        try
        {
            class1 = java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
        return class1;
    }

    protected void doExplosion(com.maddox.il2.engine.Actor actor, java.lang.String s, com.maddox.JGP.Point3d point3d)
    {
        java.lang.Class class1 = getClass();
        float f = com.maddox.rts.Property.floatValue(class1, "power", 1000F);
        int i = com.maddox.rts.Property.intValue(class1, "powerType", 0);
        float f1 = com.maddox.rts.Property.floatValue(class1, "radius", 150F);
        com.maddox.il2.ai.MsgExplosion.send(actor, s, point3d, getOwner(), M, f, i, f1);
        com.maddox.il2.objects.ActorCrater.initOwner = getOwner();
        com.maddox.il2.objects.effects.Explosions.generateExplosion(actor, point3d, 2.0F, i, f1, 0.0D);
        com.maddox.il2.objects.ActorCrater.initOwner = null;
        destroy();
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombType3AntiAirChild.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/SD-4HL/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 1.0F);
        com.maddox.rts.Property.set(class1, "power", 0.0572F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.000404F);
        com.maddox.rts.Property.set(class1, "massa", 0.075F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_cassette");
    }
}
