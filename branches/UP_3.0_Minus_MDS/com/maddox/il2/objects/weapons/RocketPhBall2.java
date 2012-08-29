// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 

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
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Rocket

public class RocketPhBall2 extends com.maddox.il2.objects.weapons.Rocket
{

    public RocketPhBall2()
    {
    }

    public void start(float f)
    {
        float f1 = 6F + com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 6F);
        super.start(f1);
        ((com.maddox.il2.engine.Actor)this).drawing(false);
        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
            com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (this)), ((com.maddox.il2.engine.Hook) (null)), new Loc(), 1.0F, "3DO/Effects/Fireworks/20_Sparks.eff", f1);
        switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 3))
        {
        case 0: // '\0'
            com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (this)), ((com.maddox.il2.engine.Hook) (null)), new Loc(), 1.0F, "effects/Smokes/SmokeBlack_BuletteTrail.eff", f1);
            // fall through

        case 1: // '\001'
        case 2: // '\002'
        default:
            return;
        }
    }

    protected void doExplosion(com.maddox.il2.engine.Actor actor, java.lang.String s)
    {
        pos.getTime(com.maddox.rts.Time.current(), p);
        java.lang.Class class1 = ((java.lang.Object)this).getClass();
        float f = com.maddox.rts.Property.floatValue(class1, "power", 1000F);
        int i = com.maddox.rts.Property.intValue(class1, "powerType", 0);
        float f1 = com.maddox.rts.Property.floatValue(class1, "radius", 0.0F);
        ((com.maddox.il2.objects.weapons.Rocket)this).getSpeed(speed);
        com.maddox.JGP.Vector3f vector3f = new Vector3f(((com.maddox.JGP.Tuple3d) (speed)));
        vector3f.normalize();
        vector3f.scale(850F);
        com.maddox.il2.ai.MsgShot.send(actor, s, p, vector3f, M, ((com.maddox.il2.engine.Actor)this).getOwner(), (float)((double)(0.5F * M) * speed.lengthSquared()), 3, 0.0D);
        com.maddox.il2.ai.MsgExplosion.send(actor, s, p, ((com.maddox.il2.engine.Actor)this).getOwner(), M, f, i, f1);
        com.maddox.il2.objects.effects.Explosions.generateExplosion(actor, p, f, i, f1, 0.0D);
        ((com.maddox.il2.objects.weapons.Rocket)this).destroy();
    }

    protected void doExplosionAir()
    {
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

    private static com.maddox.JGP.Point3d p = new Point3d();

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketPhBall2.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "mesh", "3DO/Arms/Null/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sprite", ((java.lang.Object) (null)));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "flame", ((java.lang.Object) (null)));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "smoke", ((java.lang.Object) (null)));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "emitColor", ((java.lang.Object) (new Color3f(0.0F, 0.0F, 0.0F))));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "emitLen", 0.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "emitMax", 0.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", ((java.lang.Object) (null)));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "radius", 0.1F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "timeLife", 999.999F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "timeFire", 0.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "force", 0.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "power", 0.0186F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "powerType", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 0.001F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 0.01485F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massaEnd", 0.01485F);
    }
}
