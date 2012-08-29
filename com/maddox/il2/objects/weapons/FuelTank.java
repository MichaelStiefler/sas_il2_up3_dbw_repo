// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FuelTank.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.MsgDestroy;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class FuelTank extends com.maddox.il2.objects.weapons.Bomb
{

    public FuelTank()
    {
        java.lang.Class class1 = getClass();
        fill(com.maddox.rts.Property.floatValue(class1, "massa", 0.0F));
    }

    protected void doExplosion(com.maddox.il2.engine.Actor actor, java.lang.String s)
    {
        com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current(), this);
        pos.getTime(com.maddox.rts.Time.current(), p);
        if(com.maddox.il2.ai.World.land().isWater(p.x, p.y))
            com.maddox.il2.objects.effects.Explosions.WreckageDrop_Water(p);
    }

    protected void fill(float f)
    {
        setName("_fueltank_");
        M = f;
        Fuel = f * 0.9F;
    }

    public float getFuel(float f)
    {
        if(f > Fuel)
            f = Fuel;
        Fuel -= f;
        M -= f;
        return f;
    }

    private float Fuel;
    private static com.maddox.JGP.Point3d p = new Point3d();

}
