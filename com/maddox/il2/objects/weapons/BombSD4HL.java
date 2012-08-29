// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombSD4HL.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.MsgShot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombSD4HL extends com.maddox.il2.objects.weapons.Bomb
{

    public BombSD4HL()
    {
    }

    protected boolean haveSound()
    {
        return index % 10 == 0;
    }

    protected void doExplosion(com.maddox.il2.engine.Actor actor, java.lang.String s)
    {
        java.lang.Class class1 = getClass();
        com.maddox.JGP.Point3d point3d = new Point3d();
        pos.getTime(com.maddox.rts.Time.current(), point3d);
        float f = com.maddox.rts.Property.floatValue(class1, "power", 0.0F);
        int i = com.maddox.rts.Property.intValue(class1, "powerType", 0);
        float f1 = com.maddox.rts.Property.floatValue(class1, "radius", 0.0F);
        com.maddox.il2.ai.MsgShot.send(actor, s, point3d, new Vector3f(0.0F, 0.0F, -600F), M, getOwner(), f, 1, 0.0D);
        com.maddox.il2.objects.effects.Explosions.generate(actor, point3d, f, i, f1, !com.maddox.il2.game.Mission.isNet());
        destroy();
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombSD4HL.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/SD-4HL/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 0.6F);
        com.maddox.rts.Property.set(class1, "power", 0.345384F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.152F);
        com.maddox.rts.Property.set(class1, "massa", 4.264F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
