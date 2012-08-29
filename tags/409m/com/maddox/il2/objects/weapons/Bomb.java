// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Bomb.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.ScoreCounter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.DrawEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.LightPointWorld;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MeshShared;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.Chat;
import com.maddox.il2.objects.ActorCrater;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.buildings.Plate;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Message;
import com.maddox.rts.MsgInvokeMethod_Object;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.SoundFX;
import com.maddox.util.HashMapExt;
import java.util.AbstractCollection;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Ballistics

public class Bomb extends com.maddox.il2.engine.ActorMesh
    implements com.maddox.il2.engine.MsgCollisionRequestListener, com.maddox.il2.engine.MsgCollisionListener
{
    class Interpolater extends com.maddox.il2.engine.Interpolate
    {

        public boolean tick()
        {
            interpolateTick();
            return true;
        }

        Interpolater()
        {
        }
    }

    static class DelayParam
    {

        com.maddox.il2.engine.Actor other;
        java.lang.String otherChunk;
        com.maddox.JGP.Point3d p;
        com.maddox.il2.engine.Loc loc;

        DelayParam(com.maddox.il2.engine.Actor actor, java.lang.String s, com.maddox.il2.engine.Loc loc1)
        {
            p = new Point3d();
            other = actor;
            otherChunk = s;
            loc1.get(p);
            if(com.maddox.il2.engine.Actor.isValid(actor))
            {
                loc = new Loc();
                other.pos.getTime(com.maddox.rts.Time.current(), com.maddox.il2.objects.weapons.Bomb.__loc);
                loc.sub(loc1, com.maddox.il2.objects.weapons.Bomb.__loc);
            }
        }
    }

    private static class PlateFilter
        implements com.maddox.il2.engine.ActorFilter
    {

        public boolean isUse(com.maddox.il2.engine.Actor actor, double d)
        {
            if(!(actor instanceof com.maddox.il2.objects.buildings.Plate))
                return true;
            com.maddox.il2.engine.Mesh mesh = ((com.maddox.il2.engine.ActorMesh)actor).mesh();
            mesh.getBoundBox(com.maddox.il2.objects.weapons.Bomb.plateBox);
            com.maddox.il2.objects.weapons.Bomb.corn1.set(com.maddox.il2.objects.weapons.Bomb.corn);
            com.maddox.il2.engine.Loc loc1 = actor.pos.getAbs();
            loc1.transformInv(com.maddox.il2.objects.weapons.Bomb.corn1);
            if((double)(com.maddox.il2.objects.weapons.Bomb.plateBox[0] - 2.5F) < com.maddox.il2.objects.weapons.Bomb.corn1.x && com.maddox.il2.objects.weapons.Bomb.corn1.x < (double)(com.maddox.il2.objects.weapons.Bomb.plateBox[3] + 2.5F) && (double)(com.maddox.il2.objects.weapons.Bomb.plateBox[1] - 2.5F) < com.maddox.il2.objects.weapons.Bomb.corn1.y && com.maddox.il2.objects.weapons.Bomb.corn1.y < (double)(com.maddox.il2.objects.weapons.Bomb.plateBox[4] + 2.5F))
            {
                com.maddox.il2.objects.weapons.Bomb.bPlateExist = true;
                com.maddox.il2.objects.weapons.Bomb.bPlateGround = ((com.maddox.il2.objects.buildings.Plate)actor).isGround();
            }
            return true;
        }

        private PlateFilter()
        {
        }

    }


    public void msgCollisionRequest(com.maddox.il2.engine.Actor actor, boolean aflag[])
    {
        if(actor == getOwner())
            aflag[0] = false;
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        pos.getTime(com.maddox.rts.Time.current(), p);
        while(actor != null && (actor instanceof com.maddox.il2.objects.ActorLand) && isPointApplicableForJump()) 
        {
            if(speed.z >= 0.0D)
                return;
            float f = (float)speed.length();
            if(f >= 30F)
            {
                dir.set(speed);
                dir.scale(1.0F / f);
                if(-dir.z < 0.31F)
                {
                    pos.getAbs(or);
                    dirN.set(1.0F, 0.0F, 0.0F);
                    or.transform(dirN);
                    if(dir.dot(dirN) >= 0.91F)
                    {
                        float f1 = -dir.z;
                        f1 *= 3.225806F;
                        f1 = 0.85F - 0.35F * f1;
                        f1 *= com.maddox.il2.ai.World.Rnd().nextFloat(0.85F, 1.0F);
                        speed.scale(f1);
                        speed.z *= f1;
                        if(speed.z < 0.0D)
                            speed.z = -speed.z;
                        p.z = com.maddox.il2.engine.Engine.land().HQ(p.x, p.y);
                        pos.setAbs(p);
                        if(M >= 200F)
                            f1 = 1.0F;
                        else
                        if(M <= 5F)
                            f1 = 0.0F;
                        else
                            f1 = (M - 5F) / 195F;
                        float f2 = 3.5F + f1 * 12F;
                        if(com.maddox.il2.engine.Engine.land().isWater(p.x, p.y))
                            com.maddox.il2.objects.effects.Explosions.SomethingDrop_Water(p, f2);
                        else
                            com.maddox.il2.objects.effects.Explosions.Explode10Kg_Land(p, 2.0F, 2.0F);
                        return;
                    }
                }
            }
            break;
        }
        if(getOwner() == com.maddox.il2.ai.World.getPlayerAircraft() && !(actor instanceof com.maddox.il2.objects.ActorLand))
        {
            com.maddox.il2.ai.World.cur().scoreCounter.bombHit++;
            if(com.maddox.il2.game.Mission.isNet() && (actor instanceof com.maddox.il2.objects.air.Aircraft) && ((com.maddox.il2.objects.air.Aircraft)actor).isNetPlayer())
                com.maddox.il2.net.Chat.sendLogRnd(3, "gore_bombed", (com.maddox.il2.objects.air.Aircraft)getOwner(), (com.maddox.il2.objects.air.Aircraft)actor);
        }
        if(delayExplosion > 0.0F)
        {
            pos.getTime(com.maddox.rts.Time.current(), loc);
            collide(false);
            drawing(false);
            com.maddox.il2.objects.weapons.DelayParam delayparam = new DelayParam(actor, s1, loc);
            if(p.z < com.maddox.il2.engine.Engine.land().HQ(p.x, p.y) + 5D)
                if(com.maddox.il2.engine.Engine.land().isWater(p.x, p.y))
                    com.maddox.il2.objects.effects.Explosions.Explode10Kg_Water(p, 2.0F, 2.0F);
                else
                    com.maddox.il2.objects.effects.Explosions.Explode10Kg_Land(p, 2.0F, 2.0F);
            interpEndAll();
            (new MsgInvokeMethod_Object("doDelayExplosion", delayparam)).post(this, delayExplosion);
            if(sound != null)
                sound.cancel();
        } else
        {
            doExplosion(actor, s1);
        }
    }

    private boolean isPointApplicableForJump()
    {
        if(com.maddox.il2.engine.Engine.land().isWater(p.x, p.y))
            return true;
        float f = 200F;
        bPlateExist = false;
        bPlateGround = false;
        p.get(corn);
        com.maddox.il2.engine.Engine.drawEnv().getFiltered((java.util.AbstractCollection)null, corn.x - (double)f, corn.y - (double)f, corn.x + (double)f, corn.y + (double)f, 1, plateFilter);
        if(bPlateExist)
            return true;
        int i = com.maddox.il2.engine.Engine.cur.land.HQ_RoadTypeHere(p.x, p.y);
        switch(i)
        {
        case 1: // '\001'
            return true;

        case 2: // '\002'
            return true;

        case 3: // '\003'
            return false;
        }
        return false;
    }

    public void doDelayExplosion(java.lang.Object obj)
    {
        com.maddox.il2.objects.weapons.DelayParam delayparam = (com.maddox.il2.objects.weapons.DelayParam)obj;
        if(com.maddox.il2.engine.Actor.isValid(delayparam.other))
        {
            delayparam.other.pos.getTime(com.maddox.rts.Time.current(), __loc);
            delayparam.loc.add(__loc);
            doExplosion(delayparam.other, delayparam.otherChunk, delayparam.loc.getPoint());
        } else
        {
            doExplosion(com.maddox.il2.engine.Engine.actorLand(), "Body", delayparam.p);
        }
    }

    protected void doExplosion(com.maddox.il2.engine.Actor actor, java.lang.String s)
    {
        pos.getTime(com.maddox.rts.Time.current(), p);
        doExplosion(actor, s, p);
    }

    protected void doExplosion(com.maddox.il2.engine.Actor actor, java.lang.String s, com.maddox.JGP.Point3d point3d)
    {
        java.lang.Class class1 = getClass();
        float f = com.maddox.rts.Property.floatValue(class1, "power", 1000F);
        int i = com.maddox.rts.Property.intValue(class1, "powerType", 0);
        float f1 = com.maddox.rts.Property.floatValue(class1, "radius", 150F);
        com.maddox.il2.ai.MsgExplosion.send(actor, s, point3d, getOwner(), M, f, i, f1);
        com.maddox.il2.objects.ActorCrater.initOwner = getOwner();
        com.maddox.il2.objects.effects.Explosions.generate(actor, point3d, f, i, f1);
        com.maddox.il2.objects.ActorCrater.initOwner = null;
        destroy();
    }

    public void interpolateTick()
    {
        curTm += com.maddox.rts.Time.tickLenFs();
        com.maddox.il2.objects.weapons.Ballistics.updateBomb(this, M, S, J, DistFromCMtoStab);
        updateSound();
    }

    static float RndFloatSign(float f, float f1)
    {
        f = com.maddox.il2.ai.World.Rnd().nextFloat(f, f1);
        return com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) <= 0.5F ? -f : f;
    }

    private static void randomizeStart(com.maddox.il2.engine.Orient orient, com.maddox.JGP.Vector3d vector3d, float f, int i)
    {
        if(i != 0)
        {
            dir.set(com.maddox.il2.objects.weapons.Bomb.RndFloatSign(0.1F, 1.0F), com.maddox.il2.objects.weapons.Bomb.RndFloatSign(0.1F, 1.0F), com.maddox.il2.objects.weapons.Bomb.RndFloatSign(0.1F, 1.0F));
            dir.normalize();
        } else
        {
            dir.set(1.0F, 0.0F, 0.0F);
            orient.transform(dir);
            float f1 = 0.04F;
            dir.add(com.maddox.il2.ai.World.Rnd().nextFloat(-f1, f1), com.maddox.il2.ai.World.Rnd().nextFloat(-f1, f1), com.maddox.il2.ai.World.Rnd().nextFloat(-f1, f1));
            dir.normalize();
        }
        orient.setAT0(dir);
        vector3d.set(com.maddox.il2.objects.weapons.Bomb.RndFloatSign(0.1F, 1.0F), com.maddox.il2.objects.weapons.Bomb.RndFloatSign(0.1F, 1.0F), com.maddox.il2.objects.weapons.Bomb.RndFloatSign(0.1F, 1.0F));
        vector3d.normalize();
        float f2 = com.maddox.JGP.Geom.DEG2RAD(com.maddox.il2.objects.weapons.Bomb.RndFloatSign(2.0F, 35F));
        if(f > 60F)
        {
            float f3 = 0.05F;
            if(f < 350F)
            {
                f3 = 1.0F - (f - 60F) / 290F;
                f3 = f3 * 0.95F + 0.05F;
            }
            f2 *= f3;
        }
        if(i != 0)
            f2 *= 0.2F;
        vector3d.scale(f2);
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

    protected void init(float f, float f1)
    {
        if(com.maddox.il2.engine.Actor.isValid(getOwner()) && com.maddox.il2.ai.World.getPlayerAircraft() == getOwner())
            setName("_bomb_");
        super.getSpeed(speed);
        S = (float)((3.1415926535897931D * (double)f * (double)f) / 4D);
        M = f1;
        M *= com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.06F);
        float f2 = f * 0.5F;
        float f3 = f * 4F;
        float f4 = f2;
        float f5 = f3 * 0.5F;
        J = M * 0.1F * (f4 * f4 * f5 * f5);
        DistFromCMtoStab = f3 * 0.05F;
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

    protected boolean haveSound()
    {
        return true;
    }

    public void start()
    {
        java.lang.Class class1 = getClass();
        init(com.maddox.rts.Property.floatValue(class1, "kalibr", 0.082F), com.maddox.rts.Property.floatValue(class1, "massa", 6.8F));
        curTm = 0.0F;
        setOwner(pos.base(), false, false, false);
        pos.setBase(null, null, true);
        pos.setAbs(pos.getCurrent());
        pos.getAbs(or);
        com.maddox.il2.objects.weapons.Bomb.randomizeStart(or, rotAxis, M, com.maddox.rts.Property.intValue(class1, "randomOrient", 0));
        pos.setAbs(or);
        collide(true);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        drawing(true);
        if(com.maddox.il2.engine.Actor.isAlive(com.maddox.il2.ai.World.getPlayerAircraft()) && getOwner() == com.maddox.il2.ai.World.getPlayerAircraft())
        {
            com.maddox.il2.ai.World.cur().scoreCounter.bombFire++;
            com.maddox.il2.ai.World.cur();
            com.maddox.il2.fm.FlightModel flightmodel = com.maddox.il2.ai.World.getPlayerFM();
            flightmodel.M.computeParasiteMass(flightmodel.CT.Weapons);
            flightmodel.getW().y -= 0.0004F * java.lang.Math.min(M, 50F);
        }
        if(com.maddox.rts.Property.containsValue(class1, "emitColor"))
        {
            com.maddox.il2.engine.LightPointActor lightpointactor = new LightPointActor(new LightPointWorld(), new Point3d());
            lightpointactor.light.setColor((com.maddox.JGP.Color3f)com.maddox.rts.Property.value(class1, "emitColor", new Color3f(1.0F, 1.0F, 0.5F)));
            lightpointactor.light.setEmit(com.maddox.rts.Property.floatValue(class1, "emitMax", 1.0F), com.maddox.rts.Property.floatValue(class1, "emitLen", 50F));
            draw.lightMap().put("light", lightpointactor);
        }
        if(haveSound())
        {
            java.lang.String s = com.maddox.rts.Property.stringValue(class1, "sound", null);
            if(s != null)
                sound = newSound(s, true);
        }
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public Bomb()
    {
        delayExplosion = 0.0F;
        speed = new Vector3d();
        rotAxis = new Vector3d(0.0D, 0.0D, 0.0D);
        sound = null;
        java.lang.String s = com.maddox.rts.Property.stringValue(getClass(), "mesh", null);
        setMesh(com.maddox.il2.engine.MeshShared.get(s));
        flags |= 0xe0;
        collide(false);
        drawing(true);
    }

    protected float delayExplosion;
    float curTm;
    protected com.maddox.JGP.Vector3d speed;
    protected float S;
    protected float M;
    protected float J;
    protected float DistFromCMtoStab;
    com.maddox.JGP.Vector3d rotAxis;
    protected int index;
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.JGP.Vector3f dir = new Vector3f();
    private static com.maddox.JGP.Vector3f dirN = new Vector3f();
    private static com.maddox.il2.engine.Orient or = new Orient();
    private static com.maddox.il2.engine.Loc loc = new Loc();
    private static com.maddox.il2.objects.weapons.PlateFilter plateFilter = new PlateFilter();
    private static com.maddox.JGP.Point3d corn = new Point3d();
    private static com.maddox.JGP.Point3d corn1 = new Point3d();
    private static float plateBox[] = new float[6];
    private static boolean bPlateExist = false;
    private static boolean bPlateGround = false;
    private static com.maddox.il2.engine.Loc __loc = new Loc();
    protected com.maddox.sound.SoundFX sound;
    protected static final float SND_TIME_BOUND = 5F;







}
