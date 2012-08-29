// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RocketSimple.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.ScoreCounter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.LightPointWorld;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorSimpleMesh;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Rocket, Ballistics

public class RocketSimple extends com.maddox.il2.objects.weapons.Rocket
{
    class Interpolater extends com.maddox.il2.engine.Interpolate
    {

        public boolean tick()
        {
            if(timeBegin + timeFire < com.maddox.rts.Time.current())
                endSmoke();
            com.maddox.il2.objects.weapons.Ballistics.update(actor, M, 0.0F, 0.0F, true);
            return true;
        }

        Interpolater()
        {
        }
    }


    public void start(float f)
    {
        java.lang.Class class1 = getClass();
        float f1 = com.maddox.rts.Property.floatValue(class1, "kalibr", 0.082F);
        timeLife = 0L;
        init(f1, com.maddox.rts.Property.floatValue(class1, "massa", 6.8F), com.maddox.rts.Property.floatValue(class1, "massaEnd", 2.52F), com.maddox.rts.Property.floatValue(class1, "timeFire", 4F), com.maddox.rts.Property.floatValue(class1, "force", 500F), timeLife);
        pos.getAbs(or);
        dir.set(1.0F, 0.0F, 0.0F);
        or.transform(dir);
        dir.scale(f);
        speed.set(dir);
        collide(true);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        if(getOwner() == com.maddox.il2.ai.World.getPlayerAircraft())
            com.maddox.il2.ai.World.cur().scoreCounter.rocketsFire++;
        com.maddox.il2.engine.Hook hook = findHook("_SMOKE");
        java.lang.String s = com.maddox.rts.Property.stringValue(class1, "sprite", null);
        if(s != null)
        {
            sprite = com.maddox.il2.engine.Eff3DActor.New(this, hook, null, f1, s, -1F);
            if(sprite != null)
                sprite.pos.changeHookToRel();
        }
        s = com.maddox.rts.Property.stringValue(class1, "flame", null);
        if(s != null)
        {
            flame = new ActorSimpleMesh(s);
            if(flame != null)
            {
                ((com.maddox.il2.objects.ActorSimpleMesh)flame).mesh().setScale(f1);
                flame.pos.setBase(this, hook, false);
                flame.pos.changeHookToRel();
                flame.pos.resetAsBase();
            }
        }
        s = com.maddox.rts.Property.stringValue(class1, "smoke", null);
        if(s != null)
        {
            smoke = com.maddox.il2.engine.Eff3DActor.New(this, hook, null, 1.0F, s, -1F);
            if(smoke != null)
                smoke.pos.changeHookToRel();
        }
        light = new LightPointActor(new LightPointWorld(), new Point3d());
        light.light.setColor((com.maddox.JGP.Color3f)com.maddox.rts.Property.value(class1, "emitColor", new Color3f(1.0F, 1.0F, 0.5F)));
        light.light.setEmit(com.maddox.rts.Property.floatValue(class1, "emitMax", 1.0F), com.maddox.rts.Property.floatValue(class1, "emitLen", 50F));
        draw.lightMap().put("light", light);
        soundName = com.maddox.rts.Property.stringValue(class1, "sound", null);
        if(soundName != null)
            newSound(soundName, true);
    }

    public RocketSimple(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient, com.maddox.il2.engine.Actor actor)
    {
        pos.setAbs(point3d, orient);
        pos.reset();
        setOwner(actor);
    }

    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.JGP.Vector3f dir = new Vector3f();
    private static com.maddox.il2.engine.Orient or = new Orient();

}
