// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RocketBomb.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.MsgShot;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.ScoreCounter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.LightPointWorld;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MeshShared;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.Chat;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.ActorSimpleMesh;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeGuidedBombCarrier;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Message;
import com.maddox.rts.ObjState;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.SoundFX;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Ballistics

public class RocketBomb extends com.maddox.il2.engine.ActorMesh
    implements com.maddox.il2.engine.MsgCollisionRequestListener, com.maddox.il2.engine.MsgCollisionListener
{
    class Interpolater extends com.maddox.il2.engine.Interpolate
    {

        public boolean tick()
        {
            if(timeBegin + timeLife < com.maddox.rts.Time.current())
            {
                doExplosionAir();
                postDestroy();
                collide(false);
                drawing(false);
                return false;
            }
            if(timeBegin + timeFire < com.maddox.rts.Time.current())
            {
                endSmoke();
                P = 0.0F;
                isThrust = false;
            } else
            {
                M -= DM;
            }
            if(interpolateStep())
            {
                boolean flag = timeBegin + noGDelay < com.maddox.rts.Time.current();
                curTm += com.maddox.rts.Time.tickLenFs();
                com.maddox.il2.objects.weapons.Ballistics.updateRocketBomb(actor, M, S, P, timeBegin + noGDelay < com.maddox.rts.Time.current());
                updateSound();
            }
            return true;
        }

        Interpolater()
        {
        }
    }


    protected void updateSound()
    {
        if(sound != null)
        {
            sound.setControl(200, (float)getSpeed(null));
            if(curTm < 5F)
                sound.setControl(201, curTm);
            else
            if(curTm < 5F + (float)(2 * com.maddox.rts.Time.tickConstLen()))
                sound.setControl(201, 5F);
        }
    }

    public void msgCollisionRequest(com.maddox.il2.engine.Actor actor, boolean aflag[])
    {
        if(actor == getOwner())
            aflag[0] = false;
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        impact = com.maddox.rts.Time.current() - started;
        if(impact < armingTime && isArmed)
            isArmed = false;
        if(getOwner() == com.maddox.il2.ai.World.getPlayerAircraft() && !(actor instanceof com.maddox.il2.objects.ActorLand))
        {
            com.maddox.il2.ai.World.cur().scoreCounter.rocketsHit++;
            if(com.maddox.il2.game.Mission.isNet() && (actor instanceof com.maddox.il2.objects.air.Aircraft) && ((com.maddox.il2.objects.air.Aircraft)actor).isNetPlayer())
                com.maddox.il2.net.Chat.sendLogRnd(3, "gore_rocketed", (com.maddox.il2.objects.air.Aircraft)getOwner(), (com.maddox.il2.objects.air.Aircraft)actor);
        }
        if(isArmed)
        {
            if(s1.indexOf("Mast") > -1 || s1.indexOf("Wire") > -1 || s1.indexOf("SSC") > -1 || s1.indexOf("struct") > -1)
                doExplosion(actor, "Hull2");
            else
                doExplosion(actor, s1);
        } else
        {
            destroy();
        }
    }

    protected void doExplosion(com.maddox.il2.engine.Actor actor, java.lang.String s)
    {
        if(getOwner() != null && (getOwner() instanceof com.maddox.il2.objects.air.TypeGuidedBombCarrier))
        {
            com.maddox.il2.objects.air.TypeGuidedBombCarrier typeguidedbombcarrier = (com.maddox.il2.objects.air.TypeGuidedBombCarrier)(com.maddox.il2.objects.air.TypeGuidedBombCarrier)getOwner();
            typeguidedbombcarrier.typeGuidedBombCsetIsGuiding(false);
        }
        pos.getTime(com.maddox.rts.Time.current(), p);
        java.lang.Class class1 = getClass();
        float f = com.maddox.rts.Property.floatValue(class1, "power", 1000F);
        int i = com.maddox.rts.Property.intValue(class1, "powerType", 0);
        float f1 = com.maddox.rts.Property.floatValue(class1, "radius", 0.0F);
        getSpeed(speed);
        com.maddox.JGP.Vector3f vector3f = new Vector3f(speed);
        if(f1 <= 0.0F)
        {
            com.maddox.il2.ai.MsgShot.send(actor, s, p, vector3f, M, getOwner(), f, 1, 0.0D);
        } else
        {
            com.maddox.il2.ai.MsgShot.send(actor, s, p, vector3f, M, getOwner(), (float)((double)(0.5F * M) * speed.lengthSquared()), 0, 0.0D);
            com.maddox.il2.ai.MsgExplosion.send(actor, s, p, getOwner(), M, f, i, f1);
        }
        com.maddox.il2.objects.effects.Explosions.generate(actor, p, f, i, f1, !com.maddox.il2.game.Mission.isNet());
        destroy();
    }

    protected void doExplosionAir()
    {
        if(getOwner() != null && (getOwner() instanceof com.maddox.il2.objects.air.TypeGuidedBombCarrier))
        {
            com.maddox.il2.objects.air.TypeGuidedBombCarrier typeguidedbombcarrier = (com.maddox.il2.objects.air.TypeGuidedBombCarrier)(com.maddox.il2.objects.air.TypeGuidedBombCarrier)getOwner();
            typeguidedbombcarrier.typeGuidedBombCsetIsGuiding(false);
        }
        pos.getTime(com.maddox.rts.Time.current(), p);
        java.lang.Class class1 = getClass();
        float f = com.maddox.rts.Property.floatValue(class1, "power", 1000F);
        int i = com.maddox.rts.Property.intValue(class1, "powerType", 0);
        float f1 = com.maddox.rts.Property.floatValue(class1, "radius", 150F);
        com.maddox.il2.ai.MsgExplosion.send(null, null, p, getOwner(), M, f, i, f1);
        com.maddox.il2.objects.effects.Explosions.AirFlak(p, 0);
    }

    public boolean interpolateStep()
    {
        return true;
    }

    protected void endSmoke()
    {
        if(!endedSmoke)
        {
            endedSmoke = true;
            if(light != null)
                light.light.setEmit(0.0F, 1.0F);
            com.maddox.il2.engine.Eff3DActor.finish(smoke);
            com.maddox.il2.engine.Eff3DActor.finish(sprite);
            com.maddox.rts.ObjState.destroy(flame);
        }
    }

    public void destroy()
    {
        if(getOwner() != null && (getOwner() instanceof com.maddox.il2.objects.air.TypeGuidedBombCarrier))
        {
            com.maddox.il2.objects.air.TypeGuidedBombCarrier typeguidedbombcarrier = (com.maddox.il2.objects.air.TypeGuidedBombCarrier)(com.maddox.il2.objects.air.TypeGuidedBombCarrier)getOwner();
            typeguidedbombcarrier.typeGuidedBombCsetIsGuiding(false);
        }
        endSmoke();
        super.destroy();
        smoke = null;
        sprite = null;
        flame = null;
        light = null;
        if(sound != null)
            sound.cancel();
    }

    protected void setThrust(float f)
    {
        P = f;
    }

    public double getSpeed(com.maddox.JGP.Vector3d vector3d)
    {
        if(vector3d != null)
            vector3d.set(speed);
        return speed.length();
    }

    public void setSpeed(com.maddox.JGP.Vector3d vector3d)
    {
        speed.set(vector3d);
    }

    protected void init(float f, float f1, float f2, float f3, float f4, float f5)
    {
        if(com.maddox.il2.engine.Actor.isValid(getOwner()) && com.maddox.il2.ai.World.getPlayerAircraft() == getOwner())
            setName("_rocket_");
        super.getSpeed(speed);
        S = (float)((3.1415926535897931D * (double)f * (double)f) / 4D);
        M = f1;
        Minit = M;
        if(f3 > 0.0F)
            DM = (f1 - f2) / (f3 / com.maddox.rts.Time.tickConstLenFs());
        else
            DM = 0.0F;
        P = f4;
        timeFire = (long)((double)(f3 * 1000F) + 0.5D);
        timeLife = (long)((double)(f5 * 1000F) + 0.5D);
    }

    public void start(float f)
    {
        java.lang.Class class1 = getClass();
        float f1 = com.maddox.rts.Property.floatValue(class1, "kalibr", 0.082F);
        if(f <= 0.0F)
            f = com.maddox.rts.Property.floatValue(class1, "timeLife", 45F);
        init(f1, com.maddox.rts.Property.floatValue(class1, "massa", 6.8F), com.maddox.rts.Property.floatValue(class1, "massaEnd", 2.52F), com.maddox.rts.Property.floatValue(class1, "timeFire", 4F), com.maddox.rts.Property.floatValue(class1, "force", 500F), f);
        curTm = 0.0F;
        setOwner(pos.base(), false, false, false);
        pos.setBase(null, null, true);
        pos.setAbs(pos.getCurrent());
        pos.getAbs(com.maddox.il2.objects.air.Aircraft.tmpOr);
        float f2 = 0.5F * com.maddox.rts.Property.floatValue(class1, "maxDeltaAngle", 0.0F);
        com.maddox.il2.objects.air.Aircraft.tmpOr.increment(com.maddox.il2.ai.World.Rnd().nextFloat(-f2, f2), com.maddox.il2.ai.World.Rnd().nextFloat(-f2, f2), 0.0F);
        pos.setAbs(com.maddox.il2.objects.air.Aircraft.tmpOr);
        pos.getRelOrient().transformInv(speed);
        speed.z -= 3.5D;
        if(getOwner() != null && (getOwner() instanceof com.maddox.il2.objects.air.TypeGuidedBombCarrier))
        {
            com.maddox.il2.objects.air.TypeGuidedBombCarrier typeguidedbombcarrier = (com.maddox.il2.objects.air.TypeGuidedBombCarrier)(com.maddox.il2.objects.air.TypeGuidedBombCarrier)getOwner();
            typeguidedbombcarrier.typeGuidedBombCsetIsGuiding(true);
        }
        pos.getRelOrient().transform(speed);
        collide(true);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        if(getOwner() == com.maddox.il2.ai.World.getPlayerAircraft())
            com.maddox.il2.ai.World.cur().scoreCounter.rocketsFire++;
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            com.maddox.il2.engine.Hook hook = null;
            java.lang.String s = com.maddox.rts.Property.stringValue(class1, "sprite", null);
            if(s != null)
            {
                if(hook == null)
                    hook = findHook("_SMOKE");
                sprite = com.maddox.il2.engine.Eff3DActor.New(this, hook, null, f1, s, -1F);
                if(sprite != null)
                    sprite.pos.changeHookToRel();
            }
            s = com.maddox.rts.Property.stringValue(class1, "flame", null);
            if(s != null)
            {
                if(hook == null)
                    hook = findHook("_SMOKE");
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
                if(hook == null)
                    hook = findHook("_SMOKE");
                smoke = com.maddox.il2.engine.Eff3DActor.New(this, hook, null, 1.0F, s, -1F);
                if(smoke != null)
                    smoke.pos.changeHookToRel();
            }
            light = new LightPointActor(new LightPointWorld(), new Point3d());
            light.light.setColor((com.maddox.JGP.Color3f)com.maddox.rts.Property.value(class1, "emitColor", new Color3f(1.0F, 1.0F, 0.5F)));
            light.light.setEmit(com.maddox.rts.Property.floatValue(class1, "emitMax", 1.0F), com.maddox.rts.Property.floatValue(class1, "emitLen", 50F));
            draw.lightMap().put("light", light);
            if(haveSound())
            {
                java.lang.String s1 = com.maddox.rts.Property.stringValue(class1, "sound", null);
                if(s1 != null)
                    sound = newSound(s1, true);
            }
        }
    }

    protected boolean haveSound()
    {
        return true;
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public RocketBomb()
    {
        isArmed = true;
        noGDelay = -1L;
        endedSmoke = false;
        speed = new Vector3d();
        isThrust = true;
        sound = null;
        setMesh(com.maddox.il2.engine.MeshShared.get(com.maddox.rts.Property.stringValue(getClass(), "mesh", null)));
        flags |= 0xe0;
        collide(false);
        drawing(true);
    }

    protected void mydebug(java.lang.String s)
    {
    }

    private long started;
    private long impact;
    private boolean isArmed;
    private static long armingTime = 5000L;
    private static final boolean DEBUG_TYPE = false;
    protected long noGDelay;
    public static com.maddox.JGP.Point3d p = new Point3d();
    private boolean endedSmoke;
    protected com.maddox.il2.engine.Eff3DActor smoke;
    protected com.maddox.il2.engine.Eff3DActor sprite;
    protected com.maddox.il2.engine.Actor flame;
    protected com.maddox.il2.engine.LightPointActor light;
    protected long timeFire;
    protected long timeLife;
    protected com.maddox.JGP.Vector3d speed;
    private float S;
    public float M;
    public float Minit;
    public boolean isThrust;
    private float DM;
    private float P;
    protected com.maddox.sound.SoundFX sound;
    static com.maddox.JGP.Vector3d spd = new Vector3d();
    static com.maddox.il2.engine.Orient Or = new Orient();
    float curTm;





}
