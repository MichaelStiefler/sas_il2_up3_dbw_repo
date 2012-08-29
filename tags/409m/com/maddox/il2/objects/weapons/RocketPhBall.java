// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RocketPhBall.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.MsgShot;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Loc;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Rocket

public class RocketPhBall extends com.maddox.il2.objects.weapons.Rocket
{

    public RocketPhBall()
    {
    }

    public void start(float f)
    {
        float f1 = 6F + com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 6F);
        super.start(f1);
        drawing(false);
        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
            com.maddox.il2.engine.Eff3DActor.New(this, null, new Loc(), 1.0F, "3DO/Effects/Fireworks/PhosfourousBall.eff", f1);
        switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 3))
        {
        case 0: // '\0'
            com.maddox.il2.engine.Eff3DActor.New(this, null, new Loc(), 1.0F, "Effects/Smokes/SmokeBlack_BuletteTrail.eff", f1);
            break;

        case 1: // '\001'
        case 2: // '\002'
            com.maddox.il2.engine.Eff3DActor.New(this, null, new Loc(), 1.0F, "3DO/Effects/Tracers/TrailCurved.eff", f1);
            break;
        }
    }

    protected void doExplosion(com.maddox.il2.engine.Actor actor, java.lang.String s)
    {
        pos.getTime(com.maddox.rts.Time.current(), p);
        java.lang.Class class1 = getClass();
        float f = com.maddox.rts.Property.floatValue(class1, "power", 1000F);
        int i = com.maddox.rts.Property.intValue(class1, "powerType", 0);
        float f1 = com.maddox.rts.Property.floatValue(class1, "radius", 0.0F);
        getSpeed(speed);
        com.maddox.JGP.Vector3f vector3f = new Vector3f(speed);
        vector3f.normalize();
        vector3f.scale(850F);
        com.maddox.il2.ai.MsgShot.send(actor, s, p, vector3f, M, getOwner(), (float)((double)(0.5F * M) * speed.lengthSquared()), 3, 0.0D);
        com.maddox.il2.ai.MsgExplosion.send(actor, s, p, getOwner(), M, f, i, f1);
        destroy();
    }

    protected void doExplosionAir()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private static com.maddox.JGP.Point3d p = new Point3d();

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketPhBall.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Null/mono.sim");
        com.maddox.rts.Property.set(class1, "sprite", (java.lang.Object)null);
        com.maddox.rts.Property.set(class1, "flame", (java.lang.Object)null);
        com.maddox.rts.Property.set(class1, "smoke", (java.lang.Object)null);
        com.maddox.rts.Property.set(class1, "emitColor", new Color3f(0.0F, 0.0F, 0.0F));
        com.maddox.rts.Property.set(class1, "emitLen", 0.0F);
        com.maddox.rts.Property.set(class1, "emitMax", 0.0F);
        com.maddox.rts.Property.set(class1, "sound", (java.lang.Object)null);
        com.maddox.rts.Property.set(class1, "radius", 0.1F);
        com.maddox.rts.Property.set(class1, "timeLife", 999.999F);
        com.maddox.rts.Property.set(class1, "timeFire", 0.0F);
        com.maddox.rts.Property.set(class1, "force", 0.0F);
        com.maddox.rts.Property.set(class1, "power", 0.01485F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.001F);
        com.maddox.rts.Property.set(class1, "massa", 0.01485F);
        com.maddox.rts.Property.set(class1, "massaEnd", 0.01485F);
    }
}
