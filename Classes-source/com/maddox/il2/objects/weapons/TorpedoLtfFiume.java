// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TorpedoLtfFiume.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.LightPointWorld;
import com.maddox.il2.engine.MsgCollision;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.SoundFX;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb, Ballistics

public class TorpedoLtfFiume extends com.maddox.il2.objects.weapons.Bomb
{

    public TorpedoLtfFiume()
    {
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String string, java.lang.String string_0_)
    {
        double randf = java.lang.Math.random() * 100D;
        impact = com.maddox.rts.Time.current() - started;
        Other = actor;
        OtherChunk = string_0_;
        if(actor instanceof com.maddox.il2.objects.ActorLand)
        {
            if(flow)
            {
                doExplosion(actor, string_0_);
                return;
            }
            surface();
            if(com.maddox.il2.ai.World.land().isWater(P.x, P.y))
                return;
        }
        if(impact < 5700L)
        {
            collide(false);
            return;
        }
        if(randf >= 53.200000000000003D && randf <= 53.899999999999999D)
        {
            collide(false);
            return;
        }
        if(randf >= 12.5D && randf <= 21.870000000000001D)
            destroy();
        else
            doExplosion(actor, string_0_);
    }

    private void surface()
    {
        java.lang.Class var_class = getClass();
        double randi = java.lang.Math.random() * 100D;
        travelTime = (long)com.maddox.rts.Property.floatValue(var_class, "traveltime", 1.0F) * 1000L;
        panne = com.maddox.rts.Time.current() - started;
        pos.getAbs(P, Or);
        flow = true;
        getSpeed(spd);
        if(com.maddox.il2.ai.World.land().isWater(P.x, P.y))
        {
            if(spd.z < -0.11999999731779099D)
                com.maddox.il2.objects.effects.Explosions.RS82_Water(P, 4F, 1.0F);
            double d = spd.length();
            if(d > 0.001D)
                d = spd.z / spd.length();
            else
                d = -1D;
            if(d > -0.56999999999999995D && randi <= 1.03D)
                velocity = 0.5F;
            if(d > -0.56999999999999995D && randi >= 38.100000000000001D && randi <= 41D)
            {
                velocity = 0.02F;
                destroy();
            }
            if(d < -0.56999999999999995D)
            {
                velocity = 0.02F;
                destroy();
            }
        }
        spd.z = 0.0D;
        setSpeed(spd);
        P.z = 0.0D;
        float fs[] = new float[3];
        Or.getYPR(fs);
        Or.setYPR(fs[0], 0.0F, fs[2]);
        pos.setAbs(P, Or);
        flags &= 0xffffffbf;
        drawing(false);
        com.maddox.il2.engine.Eff3DActor.New(this, null, null, 1.0F, "3DO/Effects/Tracers/533mmTorpedo/Line.eff", -1F);
        com.maddox.il2.engine.Eff3DActor.New(this, null, null, 1.0F, "3DO/Effects/Tracers/533mmTorpedo/wave.eff", -1F);
    }

    public void interpolateTick()
    {
        float f = com.maddox.rts.Time.tickLenFs();
        pos.getAbs(P);
        if(P.z <= -0.10000000149011612D)
            surface();
        if(!flow)
        {
            com.maddox.il2.objects.weapons.Ballistics.update(this, M, S);
        } else
        {
            getSpeed(spd);
            if(spd.length() > (double)velocity)
                spd.scale(0.99000000953674316D);
            else
            if(spd.length() < (double)velocity)
                spd.scale(1.0099999904632568D);
            setSpeed(spd);
            pos.getAbs(P);
            P.x += spd.x * (double)f;
            P.y += spd.y * (double)f;
            pos.setAbs(P);
            if(com.maddox.rts.Time.current() > started + travelTime || !com.maddox.il2.ai.World.land().isWater(P.x, P.y))
                sendexplosion();
        }
        updateSound();
    }

    private void sendexplosion()
    {
        com.maddox.il2.engine.MsgCollision.post(com.maddox.rts.Time.current(), this, Other, null, OtherChunk);
    }

    public void start()
    {
        java.lang.Class var_class = getClass();
        init(com.maddox.rts.Property.floatValue(var_class, "kalibr", 1.0F), com.maddox.rts.Property.floatValue(var_class, "massa", 1.0F));
        started = com.maddox.rts.Time.current();
        velocity = com.maddox.rts.Property.floatValue(var_class, "velocity", 1.0F);
        travelTime = (long)com.maddox.rts.Property.floatValue(var_class, "traveltime", 1.0F) * 1000L;
        setOwner(pos.base(), false, false, false);
        pos.setBase(null, null, true);
        pos.setAbs(pos.getCurrent());
        getSpeed(spd);
        pos.getAbs(P, Or);
        com.maddox.JGP.Vector3d vector3d = new Vector3d(com.maddox.rts.Property.floatValue(var_class, "startingspeed", 0.0F), 0.0D, 0.0D);
        Or.transform(vector3d);
        spd.add(vector3d);
        setSpeed(spd);
        collide(true);
        interpPut(new Bomb.Interpolater(this), null, com.maddox.rts.Time.current(), null);
        drawing(true);
        if(com.maddox.rts.Property.containsValue(var_class, "emitColor"))
        {
            com.maddox.il2.engine.LightPointActor lightpointactor = new LightPointActor(new LightPointWorld(), new Point3d());
            lightpointactor.light.setColor((com.maddox.JGP.Color3f)com.maddox.rts.Property.value(var_class, "emitColor", new Color3f(1.0F, 1.0F, 0.5F)));
            lightpointactor.light.setEmit(com.maddox.rts.Property.floatValue(var_class, "emitMax", 1.0F), com.maddox.rts.Property.floatValue(var_class, "emitLen", 50F));
            draw.lightMap().put("light", lightpointactor);
        }
        sound = newSound(com.maddox.rts.Property.stringValue(var_class, "sound", null), false);
        if(sound != null)
            sound.play();
    }

    com.maddox.il2.engine.Actor Other;
    java.lang.String OtherChunk;
    java.lang.String ThisChunk;
    boolean flow;
    private float velocity;
    private long travelTime;
    private long started;
    private long impact;
    private long panne;
    private long i;
    static com.maddox.JGP.Vector3d spd = new Vector3d();
    static com.maddox.il2.engine.Orient Or = new Orient();
    static com.maddox.JGP.Point3d P = new Point3d();

}
