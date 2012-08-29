// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RocketTinyTim.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Rocket, Ballistics

public class RocketTinyTim extends com.maddox.il2.objects.weapons.Rocket
{

    public RocketTinyTim()
    {
        tOrient = new Orient();
        flags &= 0xffffffdf;
    }

    public boolean interpolateStep()
    {
        if(tEStart > 0L)
            if(com.maddox.rts.Time.current() > tEStart)
            {
                tEStart = -1L;
                setThrust(222000F);
                if(com.maddox.il2.engine.Config.isUSE_RENDER())
                {
                    newSound(soundName, true);
                    com.maddox.il2.engine.Eff3DActor.setIntesity(smoke, 1.0F);
                    com.maddox.il2.engine.Eff3DActor.setIntesity(sprite, 1.0F);
                    flame.drawing(true);
                    light.light.setEmit(2.0F, 100F);
                }
            } else
            {
                com.maddox.il2.objects.weapons.Ballistics.update(this, M, 0.07068583F, 0.0F, true);
                pos.setAbs(tOrient);
                return false;
            }
        return super.interpolateStep();
    }

    public void start(float f)
    {
        super.start(-1F);
        com.maddox.il2.fm.FlightModel flightmodel = ((com.maddox.il2.objects.air.Aircraft)getOwner()).FM;
        tOrient.set(flightmodel.Or);
        speed.set(flightmodel.Vwld);
        noGDelay = -1L;
        tEStart = com.maddox.rts.Time.current() + com.maddox.il2.ai.World.Rnd().nextLong(900L, 1100L);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            breakSounds();
            com.maddox.il2.engine.Eff3DActor.setIntesity(smoke, 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(sprite, 0.0F);
            flame.drawing(false);
            light.light.setEmit(0.0F, 0.0F);
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private long tEStart;
    private com.maddox.il2.engine.Orient tOrient;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketTinyTim.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/TinyTim/mono.sim");
        com.maddox.rts.Property.set(class1, "sprite", "3DO/Effects/Rocket/firesprite.eff");
        com.maddox.rts.Property.set(class1, "flame", "3DO/Effects/Rocket/mono.sim");
        com.maddox.rts.Property.set(class1, "smoke", "3DO/Effects/Rocket/rocketsmokewhite.eff");
        com.maddox.rts.Property.set(class1, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
        com.maddox.rts.Property.set(class1, "emitLen", 50F);
        com.maddox.rts.Property.set(class1, "emitMax", 2.0F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.rocket_132");
        com.maddox.rts.Property.set(class1, "radius", 75F);
        com.maddox.rts.Property.set(class1, "timeLife", 1000000F);
        com.maddox.rts.Property.set(class1, "timeFire", 33F);
        com.maddox.rts.Property.set(class1, "force", 0.0F);
        com.maddox.rts.Property.set(class1, "power", 125F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.3F);
        com.maddox.rts.Property.set(class1, "massa", 582F);
        com.maddox.rts.Property.set(class1, "massaEnd", 270F);
    }
}
