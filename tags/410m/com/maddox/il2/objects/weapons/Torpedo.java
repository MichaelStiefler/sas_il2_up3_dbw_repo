// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Torpedo.java

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
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeHasToKG;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.SoundFX;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb, BombTorpFFF, BombTorpFFF1, BombParaTorp, 
//            Ballistics

public class Torpedo extends com.maddox.il2.objects.weapons.Bomb
{

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        java.lang.Class class1 = getClass();
        armingTime = (long)com.maddox.rts.Property.floatValue(class1, "armingTime", 1.0F) * 1000L;
        timeTravelInWater = com.maddox.rts.Time.current() - timeHitWater;
        Other = actor;
        OtherChunk = s1;
        if((this instanceof com.maddox.il2.objects.weapons.BombTorpFFF) && ((com.maddox.il2.objects.weapons.BombTorpFFF)this).bOnChute2)
        {
            ((com.maddox.il2.objects.weapons.BombTorpFFF)this).bOnChute2 = false;
            if(!(actor instanceof com.maddox.il2.objects.ActorLand))
            {
                doExplosion(actor, s1);
                return;
            }
            if(com.maddox.il2.ai.World.land().isWater(P.x, P.y))
            {
                return;
            } else
            {
                doExplosion(actor, s1);
                return;
            }
        }
        if(actor instanceof com.maddox.il2.objects.ActorLand)
        {
            if(flow)
            {
                doExplosion(actor, s1);
                return;
            }
            surface();
            if(com.maddox.il2.ai.World.land().isWater(P.x, P.y))
                return;
        }
        if((float)timeTravelInWater < armingTime && !(actor instanceof com.maddox.il2.objects.ActorLand))
        {
            if(getOwner() instanceof com.maddox.il2.objects.air.Aircraft)
            {
                com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)getOwner();
                if(aircraft.FM.isPlayers())
                    com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "TorpedoDidNotArm");
            }
            destroy();
        } else
        if(hasHitLand || !flow)
        {
            com.maddox.il2.objects.effects.Explosions.Explode10Kg_Land(P, 2.0F, 2.0F);
        } else
        {
            if(getOwner() instanceof com.maddox.il2.objects.air.Aircraft)
            {
                com.maddox.il2.objects.air.Aircraft aircraft1 = (com.maddox.il2.objects.air.Aircraft)getOwner();
                if(aircraft1.FM.isPlayers())
                    com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "TorpedoHit");
            }
            doExplosion(actor, s1);
        }
    }

    private void surface()
    {
        java.lang.Class class1 = getClass();
        boolean flag = false;
        travelTime = (long)com.maddox.rts.Property.floatValue(class1, "traveltime", 1.0F) * 1000L;
        impactAngleMin = com.maddox.rts.Property.floatValue(class1, "impactAngleMin", 1.0F);
        impactAngleMax = com.maddox.rts.Property.floatValue(class1, "impactAngleMax", 1.0F);
        impactSpeed = com.maddox.rts.Property.floatValue(class1, "impactSpeed", 1.0F);
        timeHitWater = com.maddox.rts.Time.current();
        if(this instanceof com.maddox.il2.objects.weapons.BombTorpFFF1)
        {
            turnSpeed = 0.12F;
            float f = (float)spd.length();
            float f3 = 5.2F / f;
            spd.scale(f3);
            setSpeed(spd);
        } else
        if(this instanceof com.maddox.il2.objects.weapons.BombTorpFFF)
        {
            turnSpeed = 0.1F;
            float f1 = (float)spd.length();
            float f4 = 5F / f1;
            spd.scale(f4);
            setSpeed(spd);
        } else
        if(this instanceof com.maddox.il2.objects.weapons.BombParaTorp)
        {
            turnSpeed = 0.1F;
            float f2 = (float)spd.length();
            float f5 = 5F / f2;
            spd.scale(f5);
            setSpeed(spd);
        }
        pos.getAbs(P, Or);
        flow = true;
        getSpeed(spd);
        if(com.maddox.il2.ai.World.land().isWater(P.x, P.y))
        {
            if(spd.z < -0.11999999731779099D)
                com.maddox.il2.objects.effects.Explosions.RS82_Water(P, 4F, 1.0F);
            double d = spd.length();
            com.maddox.il2.objects.air.Aircraft aircraft1 = null;
            boolean flag1 = false;
            if((getOwner() instanceof com.maddox.il2.objects.air.Aircraft) && !(this instanceof com.maddox.il2.objects.weapons.BombParaTorp))
            {
                flag1 = true;
                aircraft1 = (com.maddox.il2.objects.air.Aircraft)getOwner();
                if(aircraft1.FM.isPlayers() || aircraft1.isNetPlayer())
                {
                    flag1 = true;
                } else
                {
                    impactAngleMin *= 0.8F;
                    impactAngleMax *= 1.2F;
                    impactSpeed *= 1.2F;
                }
            }
            if(d > (double)impactSpeed && flag1)
            {
                if(aircraft1 != null && aircraft1.FM.isPlayers())
                    com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "TorpedoBrokenOnEntryIntoWater");
                flag = true;
                destroy();
            } else
            {
                if(d > 0.001D)
                    d = spd.z / spd.length();
                else
                    d = -1D;
                float f6 = (180F * (float)java.lang.Math.abs(java.lang.Math.asin(d))) / 3.14159F;
                if(flag1 && (f6 > impactAngleMax || d < -0.99000000953674316D))
                {
                    if(aircraft1 != null && aircraft1.FM.isPlayers())
                        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "TorpedoFailedEntryIntoWater");
                    flag = true;
                    destroy();
                }
                if(flag1 && f6 < impactAngleMin)
                {
                    if(aircraft1 != null && aircraft1.FM.isPlayers())
                        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "TorpedoFailedEntryIntoWater");
                    flag = true;
                    destroy();
                }
            }
        } else
        {
            flag = true;
            hasHitLand = true;
            destroy();
        }
        if(!flag)
        {
            if(getOwner() instanceof com.maddox.il2.objects.air.TypeHasToKG)
            {
                com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)getOwner();
                gyroTargetAngle = aircraft.FM.AS.getGyroAngle();
                spreadAngle = aircraft.FM.AS.getSpreadAngle();
                int i = com.maddox.rts.Property.intValue(class1, "spreadDirection", 0);
                gyroTargetAngle += ((float)spreadAngle / 2.0F) * (float)i;
            }
            getSpeed(spd);
            spd.z = 0.0D;
            setSpeed(spd);
            P.z = 0.0D;
            float af[] = new float[3];
            Or.getYPR(af);
            Or.setYPR(af[0], 0.0F, af[2]);
            pos.setAbs(P, Or);
            flags &= 0xffffffbf;
            drawing(false);
            if(this instanceof com.maddox.il2.objects.weapons.BombParaTorp)
            {
                com.maddox.il2.engine.Eff3DActor.New(this, null, null, 0.5F, "3DO/Effects/Tracers/533mmTorpedo/CirclingLine.eff", -1F);
            } else
            {
                com.maddox.il2.engine.Eff3DActor.New(this, null, null, 1.0F, "3DO/Effects/Tracers/533mmTorpedo/Line.eff", -1F);
                com.maddox.il2.engine.Eff3DActor.New(this, null, null, 1.0F, "3DO/Effects/Tracers/533mmTorpedo/wave.eff", -1F);
            }
        }
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
            float f1 = gyroTargetAngle - gyroAngle;
            if(this instanceof com.maddox.il2.objects.weapons.BombTorpFFF1)
            {
                turnSpeed -= 7.8E-006F;
                if(turnSpeed < 0.0084F)
                    turnSpeed = 0.0084F;
                f1 = -0.5F;
            } else
            if(this instanceof com.maddox.il2.objects.weapons.BombParaTorp)
            {
                turnSpeed -= 7E-006F;
                if(turnSpeed < 0.008F)
                    turnSpeed = 0.008F;
                f1 = 0.5F;
            }
            if(f1 != 0.0F)
            {
                if(f1 < 0.0F)
                    gyroAngle = gyroAngle - turnSpeed;
                else
                if(f1 > 0.0F)
                    gyroAngle = gyroAngle + turnSpeed;
                float f2 = -(float)java.lang.Math.toRadians(gyroAngle);
                float f3 = (float)java.lang.Math.cos(java.lang.Math.abs(f2));
                float f4 = (float)java.lang.Math.sin(f2);
                float f5 = (float)(spd.x * (double)f3 - spd.y * (double)f4);
                float f6 = (float)(spd.x * (double)f4 + spd.y * (double)f3);
                spd.x = f5;
                spd.y = f6;
            }
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
        java.lang.Class class1 = getClass();
        init(com.maddox.rts.Property.floatValue(class1, "kalibr", 1.0F), com.maddox.rts.Property.floatValue(class1, "massa", 1.0F));
        started = com.maddox.rts.Time.current();
        velocity = com.maddox.rts.Property.floatValue(class1, "velocity", 1.0F);
        travelTime = (long)com.maddox.rts.Property.floatValue(class1, "traveltime", 1.0F) * 1000L;
        setOwner(pos.base(), false, false, false);
        pos.setBase(null, null, true);
        pos.setAbs(pos.getCurrent());
        getSpeed(spd);
        pos.getAbs(P, Or);
        com.maddox.JGP.Vector3d vector3d = new Vector3d(com.maddox.rts.Property.floatValue(class1, "startingspeed", 0.0F), 0.0D, 0.0D);
        Or.transform(vector3d);
        spd.add(vector3d);
        setSpeed(spd);
        collide(true);
        interpPut(new Bomb.Interpolater(this), null, com.maddox.rts.Time.current(), null);
        drawing(true);
        if(com.maddox.rts.Property.containsValue(class1, "emitColor"))
        {
            com.maddox.il2.engine.LightPointActor lightpointactor = new LightPointActor(new LightPointWorld(), new Point3d());
            lightpointactor.light.setColor((com.maddox.JGP.Color3f)com.maddox.rts.Property.value(class1, "emitColor", new Color3f(1.0F, 1.0F, 0.5F)));
            lightpointactor.light.setEmit(com.maddox.rts.Property.floatValue(class1, "emitMax", 1.0F), com.maddox.rts.Property.floatValue(class1, "emitLen", 50F));
            draw.lightMap().put("light", lightpointactor);
        }
        sound = newSound(com.maddox.rts.Property.stringValue(class1, "sound", null), false);
        if(sound != null)
            sound.play();
    }

    public Torpedo()
    {
        gyroAngle = 0.0F;
        gyroTargetAngle = 0.0F;
        turnSpeed = 0.1F;
        spreadAngle = 0;
        hasHitLand = false;
        doLandExplosion = false;
    }

    com.maddox.il2.engine.Actor Other;
    java.lang.String OtherChunk;
    java.lang.String ThisChunk;
    boolean flow;
    private float velocity;
    private long travelTime;
    private long started;
    private float gyroAngle;
    private float gyroTargetAngle;
    private float turnSpeed;
    private int spreadAngle;
    private long timeHitWater;
    private long timeTravelInWater;
    private float impactAngleMin;
    private float impactAngleMax;
    private float impactSpeed;
    private float armingTime;
    boolean hasHitLand;
    boolean doLandExplosion;
    static com.maddox.JGP.Vector3d spd = new Vector3d();
    static com.maddox.il2.engine.Orient Or = new Orient();
    static com.maddox.JGP.Point3d P = new Point3d();

}
