// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   Bomb.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.DifficultySettings;
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
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Wind;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.Chat;
import com.maddox.il2.objects.ActorCrater;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.buildings.Plate;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Message;
import com.maddox.rts.MsgInvokeMethod_Object;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.AudioStream;
import com.maddox.sound.SoundFX;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            BombSD2A, BombSD4HL, BombB22EZ, Ballistics

public class Bomb extends com.maddox.il2.engine.ActorMesh
    implements com.maddox.il2.engine.MsgCollisionRequestListener, com.maddox.il2.engine.MsgCollisionListener
{
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
            loc1.get(((com.maddox.JGP.Tuple3d) (p)));
            if(com.maddox.il2.engine.Actor.isValid(actor))
            {
                loc = new Loc();
                other.pos.getTime(com.maddox.rts.Time.current(), com.maddox.il2.objects.weapons.Bomb.__loc);
                loc.sub(loc1, com.maddox.il2.objects.weapons.Bomb.__loc);
            }
        }
    }

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

    private static class PlateFilter
        implements com.maddox.il2.engine.ActorFilter
    {

        public boolean isUse(com.maddox.il2.engine.Actor actor, double d)
        {
            if(!(actor instanceof com.maddox.il2.objects.buildings.Plate))
                return true;
            com.maddox.il2.engine.Mesh mesh = ((com.maddox.il2.engine.ActorMesh)actor).mesh();
            mesh.getBoundBox(com.maddox.il2.objects.weapons.Bomb.plateBox);
            ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.weapons.Bomb.corn1)).set(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.weapons.Bomb.corn)));
            com.maddox.il2.engine.Loc loc1 = actor.pos.getAbs();
            loc1.transformInv(com.maddox.il2.objects.weapons.Bomb.corn1);
            if((double)(com.maddox.il2.objects.weapons.Bomb.plateBox[0] - 2.5F) < ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.weapons.Bomb.corn1)).x && ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.weapons.Bomb.corn1)).x < (double)(com.maddox.il2.objects.weapons.Bomb.plateBox[3] + 2.5F) && (double)(com.maddox.il2.objects.weapons.Bomb.plateBox[1] - 2.5F) < ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.weapons.Bomb.corn1)).y && ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.weapons.Bomb.corn1)).y < (double)(com.maddox.il2.objects.weapons.Bomb.plateBox[4] + 2.5F))
            {
                com.maddox.il2.objects.weapons.Bomb.bPlateExist = true;
                com.maddox.il2.objects.weapons.Bomb.bPlateGround = ((com.maddox.il2.objects.buildings.Plate)actor).isGround();
            }
            return true;
        }

        private PlateFilter()
        {
        }

        PlateFilter(com.maddox.il2.objects.weapons.PlateFilter platefilter)
        {
            this();
        }
    }


    public void msgCollisionRequest(com.maddox.il2.engine.Actor actor, boolean aflag[])
    {
        if(actor == ((com.maddox.il2.engine.Actor)this).getOwner())
            aflag[0] = false;
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        ((com.maddox.il2.engine.Actor)this).pos.getTime(com.maddox.rts.Time.current(), p);
        impact = com.maddox.rts.Time.current() - started;
        if(impact < armingTime && isArmed)
            isArmed = false;
        if(actor != null && (actor instanceof com.maddox.il2.objects.ActorLand) && isPointApplicableForJump())
        {
            if(((com.maddox.JGP.Tuple3d) (speed)).z >= 0.0D)
                return;
            float f = (float)speed.length();
            if(f >= 30F)
            {
                ((com.maddox.JGP.Tuple3f) (dir)).set(((com.maddox.JGP.Tuple3d) (speed)));
                ((com.maddox.JGP.Tuple3f) (dir)).scale(1.0F / f);
                if(-((com.maddox.JGP.Tuple3f) (dir)).z < 0.31F)
                {
                    ((com.maddox.il2.engine.Actor)this).pos.getAbs(or);
                    ((com.maddox.JGP.Tuple3f) (dirN)).set(1.0F, 0.0F, 0.0F);
                    or.transform(((com.maddox.JGP.Tuple3f) (dirN)));
                    if(dir.dot(((com.maddox.JGP.Tuple3f) (dirN))) >= 0.91F)
                    {
                        float f1 = -((com.maddox.JGP.Tuple3f) (dir)).z;
                        f1 *= 3.225806F;
                        f1 = 0.85F - 0.35F * f1;
                        f1 *= com.maddox.il2.ai.World.Rnd().nextFloat(0.85F, 1.0F);
                        ((com.maddox.JGP.Tuple3d) (speed)).scale(f1);
                        speed.z *= f1;
                        if(((com.maddox.JGP.Tuple3d) (speed)).z < 0.0D)
                            speed.z = -((com.maddox.JGP.Tuple3d) (speed)).z;
                        p.z = com.maddox.il2.engine.Engine.land().HQ(((com.maddox.JGP.Tuple3d) (p)).x, ((com.maddox.JGP.Tuple3d) (p)).y);
                        ((com.maddox.il2.engine.Actor)this).pos.setAbs(p);
                        if(M >= 200F)
                            f1 = 1.0F;
                        else
                        if(M <= 5F)
                            f1 = 0.0F;
                        else
                            f1 = (M - 5F) / 195F;
                        float f2 = 3.5F + f1 * 12F;
                        if(com.maddox.il2.engine.Engine.land().isWater(((com.maddox.JGP.Tuple3d) (p)).x, ((com.maddox.JGP.Tuple3d) (p)).y))
                            com.maddox.il2.objects.effects.Explosions.SomethingDrop_Water(p, f2);
                        else
                            com.maddox.il2.objects.effects.Explosions.Explode10Kg_Land(p, 2.0F, 2.0F);
                        return;
                    }
                }
            }
        }
        if(((com.maddox.il2.engine.Actor)this).getOwner() == com.maddox.il2.ai.World.getPlayerAircraft() && !(actor instanceof com.maddox.il2.objects.ActorLand))
        {
            com.maddox.il2.ai.World.cur().scoreCounter.bombHit++;
            if(com.maddox.il2.game.Mission.isNet() && (actor instanceof com.maddox.il2.objects.air.Aircraft) && ((com.maddox.il2.objects.air.NetAircraft) ((com.maddox.il2.objects.air.Aircraft)actor)).isNetPlayer())
                com.maddox.il2.net.Chat.sendLogRnd(3, "gore_bombed", (com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Actor)this).getOwner(), (com.maddox.il2.objects.air.Aircraft)actor);
        }
        if(delayExplosion > 0.0F)
        {
            ((com.maddox.il2.engine.Actor)this).pos.getTime(com.maddox.rts.Time.current(), loc);
            ((com.maddox.il2.engine.Actor)this).collide(false);
            ((com.maddox.il2.engine.Actor)this).drawing(false);
            com.maddox.il2.objects.weapons.DelayParam delayparam = new DelayParam(actor, s1, loc);
            if(((com.maddox.JGP.Tuple3d) (p)).z < com.maddox.il2.engine.Engine.land().HQ(((com.maddox.JGP.Tuple3d) (p)).x, ((com.maddox.JGP.Tuple3d) (p)).y) + 5D)
                if(com.maddox.il2.engine.Engine.land().isWater(((com.maddox.JGP.Tuple3d) (p)).x, ((com.maddox.JGP.Tuple3d) (p)).y))
                    com.maddox.il2.objects.effects.Explosions.Explode10Kg_Water(p, 2.0F, 2.0F);
                else
                    com.maddox.il2.objects.effects.Explosions.Explode10Kg_Land(p, 2.0F, 2.0F);
            ((com.maddox.il2.engine.Actor)this).interpEndAll();
            ((com.maddox.rts.Message) (new MsgInvokeMethod_Object("doDelayExplosion", ((java.lang.Object) (delayparam))))).post(((java.lang.Object) (this)), delayExplosion);
            if(sound != null)
                ((com.maddox.sound.AudioStream) (sound)).cancel();
        } else
        {
            doExplosion(actor, s1);
        }
    }

    private boolean isPointApplicableForJump()
    {
        if(com.maddox.il2.engine.Engine.land().isWater(((com.maddox.JGP.Tuple3d) (p)).x, ((com.maddox.JGP.Tuple3d) (p)).y))
            return true;
        float f = 200F;
        bPlateExist = false;
        bPlateGround = false;
        ((com.maddox.JGP.Tuple3d) (p)).get(((com.maddox.JGP.Tuple3d) (corn)));
        com.maddox.il2.engine.Engine.drawEnv().getFiltered(((java.util.AbstractCollection) (null)), ((com.maddox.JGP.Tuple3d) (corn)).x - (double)f, ((com.maddox.JGP.Tuple3d) (corn)).y - (double)f, ((com.maddox.JGP.Tuple3d) (corn)).x + (double)f, ((com.maddox.JGP.Tuple3d) (corn)).y + (double)f, 1, ((com.maddox.il2.engine.ActorFilter) (plateFilter)));
        if(bPlateExist)
            return true;
        int i = com.maddox.il2.engine.Engine.cur.land.HQ_RoadTypeHere(((com.maddox.JGP.Tuple3d) (p)).x, ((com.maddox.JGP.Tuple3d) (p)).y);
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
        ((com.maddox.il2.engine.Actor)this).pos.getTime(com.maddox.rts.Time.current(), p);
        if(isArmed())
        {
            doExplosion(actor, s, p);
        } else
        {
            if(((com.maddox.JGP.Tuple3d) (p)).z < com.maddox.il2.engine.Engine.land().HQ(((com.maddox.JGP.Tuple3d) (p)).x, ((com.maddox.JGP.Tuple3d) (p)).y) + 5D)
                if(com.maddox.il2.engine.Engine.land().isWater(((com.maddox.JGP.Tuple3d) (p)).x, ((com.maddox.JGP.Tuple3d) (p)).y))
                    com.maddox.il2.objects.effects.Explosions.Explode10Kg_Water(p, 2.0F, 2.0F);
                else
                    com.maddox.il2.objects.effects.Explosions.Explode10Kg_Land(p, 2.0F, 2.0F);
            ((com.maddox.il2.engine.ActorMesh)this).destroy();
        }
    }

    protected void doExplosion(com.maddox.il2.engine.Actor actor, java.lang.String s, com.maddox.JGP.Point3d point3d)
    {
        java.lang.Class class1 = ((java.lang.Object)this).getClass();
        float f = com.maddox.rts.Property.floatValue(class1, "power", 1000F);
        int i = com.maddox.rts.Property.intValue(class1, "powerType", 0);
        float f1 = com.maddox.rts.Property.floatValue(class1, "radius", 150F);
        int j = com.maddox.rts.Property.intValue(class1, "newEffect", 0);
        int k = com.maddox.rts.Property.intValue(class1, "nuke", 0);
        if(isArmed())
        {
            com.maddox.il2.ai.MsgExplosion.send(actor, s, point3d, ((com.maddox.il2.engine.Actor)this).getOwner(), M, f, i, f1, k);
            com.maddox.il2.objects.ActorCrater.initOwner = ((com.maddox.il2.engine.Actor)this).getOwner();
            com.maddox.il2.objects.effects.Explosions.generate(actor, point3d, f, i, f1, !com.maddox.il2.game.Mission.isNet(), j);
            com.maddox.il2.objects.ActorCrater.initOwner = null;
            ((com.maddox.il2.engine.ActorMesh)this).destroy();
        } else
        {
            ((com.maddox.il2.engine.ActorMesh)this).destroy();
        }
    }

    private boolean isArmed()
    {
        return isArmed || (this instanceof com.maddox.il2.objects.weapons.BombSD2A) || (this instanceof com.maddox.il2.objects.weapons.BombSD4HL) || (this instanceof com.maddox.il2.objects.weapons.BombB22EZ);
    }

    public boolean isFuseArmed()
    {
        return isArmed;
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
        return com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) > 0.5F ? f : -f;
    }

    private static void randomizeStart(com.maddox.il2.engine.Orient orient, com.maddox.JGP.Vector3d vector3d, float f, int i)
    {
        if(i != 0)
        {
            ((com.maddox.JGP.Tuple3f) (dir)).set(com.maddox.il2.objects.weapons.Bomb.RndFloatSign(0.1F, 1.0F), com.maddox.il2.objects.weapons.Bomb.RndFloatSign(0.1F, 1.0F), com.maddox.il2.objects.weapons.Bomb.RndFloatSign(0.1F, 1.0F));
            dir.normalize();
        } else
        {
            ((com.maddox.JGP.Tuple3f) (dir)).set(1.0F, 0.0F, 0.0F);
            orient.transform(((com.maddox.JGP.Tuple3f) (dir)));
            float f1 = 0.04F;
            ((com.maddox.JGP.Tuple3f) (dir)).add(com.maddox.il2.ai.World.Rnd().nextFloat(-f1, f1), com.maddox.il2.ai.World.Rnd().nextFloat(-f1, f1), com.maddox.il2.ai.World.Rnd().nextFloat(-f1, f1));
            dir.normalize();
        }
        orient.setAT0(dir);
        ((com.maddox.JGP.Tuple3d) (vector3d)).set(com.maddox.il2.objects.weapons.Bomb.RndFloatSign(0.1F, 1.0F), com.maddox.il2.objects.weapons.Bomb.RndFloatSign(0.1F, 1.0F), com.maddox.il2.objects.weapons.Bomb.RndFloatSign(0.1F, 1.0F));
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
        ((com.maddox.JGP.Tuple3d) (vector3d)).scale(f2);
    }

    public double getSpeed(com.maddox.JGP.Vector3d vector3d)
    {
        if(vector3d != null)
            ((com.maddox.JGP.Tuple3d) (vector3d)).set(((com.maddox.JGP.Tuple3d) (speed)));
        return speed.length();
    }

    public void setSpeed(com.maddox.JGP.Vector3d vector3d)
    {
        ((com.maddox.JGP.Tuple3d) (speed)).set(((com.maddox.JGP.Tuple3d) (vector3d)));
    }

    protected void init(float f, float f1)
    {
        if(com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Actor)this).getOwner()) && com.maddox.il2.ai.World.getPlayerAircraft() == ((com.maddox.il2.engine.Actor)this).getOwner())
            ((com.maddox.il2.engine.Actor)this).setName("_bomb_");
        ((com.maddox.il2.engine.Actor)this).getSpeed(speed);
        if(f1 > 35F && com.maddox.il2.ai.World.cur().diffCur.Wind_N_Turbulence)
        {
            com.maddox.JGP.Point3d point3d = new Point3d();
            com.maddox.JGP.Vector3d vector3d = new Vector3d();
            ((com.maddox.il2.engine.Actor)this).pos.getAbs(point3d);
            com.maddox.il2.ai.World.wind().getVectorWeapon(point3d, vector3d);
            ((com.maddox.JGP.Tuple3d) (speed)).add(-((com.maddox.JGP.Tuple3d) (vector3d)).x, -((com.maddox.JGP.Tuple3d) (vector3d)).y, 0.0D);
        }
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
            ((com.maddox.sound.AudioStream) (sound)).setControl(200, (float)getSpeed(((com.maddox.JGP.Vector3d) (null))));
            if(curTm < 5F)
                ((com.maddox.sound.AudioStream) (sound)).setControl(201, curTm);
            else
            if(curTm < 5F + (float)(2 * com.maddox.rts.Time.tickConstLen()))
                ((com.maddox.sound.AudioStream) (sound)).setControl(201, 5F);
        }
    }

    protected boolean haveSound()
    {
        return true;
    }

    public void start()
    {
        java.lang.Class class1 = ((java.lang.Object)this).getClass();
        init(com.maddox.rts.Property.floatValue(class1, "kalibr", 0.082F), com.maddox.rts.Property.floatValue(class1, "massa", 6.8F));
        started = com.maddox.rts.Time.current();
        curTm = 0.0F;
        ((com.maddox.il2.engine.Actor)this).setOwner(((com.maddox.il2.engine.Actor)this).pos.base(), false, false, false);
        ((com.maddox.il2.engine.Actor)this).pos.setBase(((com.maddox.il2.engine.Actor) (null)), ((com.maddox.il2.engine.Hook) (null)), true);
        ((com.maddox.il2.engine.Actor)this).pos.setAbs(((com.maddox.il2.engine.Actor)this).pos.getCurrent());
        ((com.maddox.il2.engine.Actor)this).pos.getAbs(or);
        com.maddox.il2.objects.weapons.Bomb.randomizeStart(or, rotAxis, M, com.maddox.rts.Property.intValue(class1, "randomOrient", 0));
        ((com.maddox.il2.engine.Actor)this).pos.setAbs(or);
        getSpeed(spd);
        ((com.maddox.il2.engine.Actor)this).pos.getAbs(P, Or);
        com.maddox.JGP.Vector3d vector3d = new Vector3d(0.0D, 0.0D, 0.0D);
        vector3d.x += com.maddox.il2.ai.World.Rnd().nextFloat_Dome(-2F, 2.0F);
        vector3d.y += com.maddox.il2.ai.World.Rnd().nextFloat_Dome(-1.2F, 1.2F);
        Or.transform(((com.maddox.JGP.Tuple3d) (vector3d)));
        ((com.maddox.JGP.Tuple3d) (spd)).add(((com.maddox.JGP.Tuple3d) (vector3d)));
        setSpeed(spd);
        getSpeed(spd);
        ((com.maddox.il2.engine.Actor)this).collide(true);
        ((com.maddox.il2.engine.Actor)this).interpPut(((com.maddox.il2.engine.Interpolate) (new Interpolater())), ((java.lang.Object) (null)), com.maddox.rts.Time.current(), ((com.maddox.rts.Message) (null)));
        ((com.maddox.il2.engine.Actor)this).drawing(true);
        if(com.maddox.il2.engine.Actor.isAlive(((com.maddox.il2.engine.Actor) (com.maddox.il2.ai.World.getPlayerAircraft()))) && ((com.maddox.il2.engine.Actor)this).getOwner() == com.maddox.il2.ai.World.getPlayerAircraft())
        {
            com.maddox.il2.ai.World.cur().scoreCounter.bombFire++;
            com.maddox.il2.ai.World.cur();
            com.maddox.il2.fm.FlightModel flightmodel = com.maddox.il2.ai.World.getPlayerFM();
            ((com.maddox.il2.fm.FlightModelMain) (flightmodel)).M.computeParasiteMass(((com.maddox.il2.fm.FlightModelMain) (flightmodel)).CT.Weapons);
            ((com.maddox.il2.fm.FlightModelMain) (flightmodel)).getW().y -= 0.0004F * java.lang.Math.min(M, 50F);
        }
        if(com.maddox.rts.Property.containsValue(class1, "emitColor"))
        {
            com.maddox.il2.engine.LightPointActor lightpointactor = new LightPointActor(((com.maddox.il2.engine.LightPoint) (new LightPointWorld())), new Point3d());
            lightpointactor.light.setColor((com.maddox.JGP.Color3f)com.maddox.rts.Property.value(class1, "emitColor", ((java.lang.Object) (new Color3f(1.0F, 1.0F, 0.5F)))));
            lightpointactor.light.setEmit(com.maddox.rts.Property.floatValue(class1, "emitMax", 1.0F), com.maddox.rts.Property.floatValue(class1, "emitLen", 50F));
            ((com.maddox.il2.engine.Actor)this).draw.lightMap().put("light", ((java.lang.Object) (lightpointactor)));
        }
        if(haveSound())
        {
            java.lang.String s = com.maddox.rts.Property.stringValue(class1, "sound", ((java.lang.String) (null)));
            if(s != null)
                sound = ((com.maddox.il2.engine.Actor)this).newSound(s, true);
        }
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return ((java.lang.Object) (this));
    }

    public Bomb()
    {
        ((com.maddox.il2.engine.ActorMesh)this).setMesh(((com.maddox.il2.engine.Mesh) (com.maddox.il2.engine.MeshShared.get(com.maddox.rts.Property.stringValue(((java.lang.Object)this).getClass(), "mesh", ((java.lang.String) (null)))))));
        isArmed = true;
        armingTime = 2000L;
        delayExplosion = 0.0F;
        speed = new Vector3d();
        rotAxis = new Vector3d(0.0D, 0.0D, 0.0D);
        sound = null;
        java.lang.String s = com.maddox.rts.Property.stringValue(((java.lang.Object)this).getClass(), "mesh", ((java.lang.String) (null)));
        ((com.maddox.il2.engine.ActorMesh)this).setMesh(((com.maddox.il2.engine.Mesh) (com.maddox.il2.engine.MeshShared.get(s))));
        ((com.maddox.il2.engine.Actor)this).flags |= 0xe0;
        ((com.maddox.il2.engine.Actor)this).collide(false);
        ((com.maddox.il2.engine.Actor)this).drawing(true);
    }

    private long started;
    private long impact;
    private boolean isArmed;
    public long armingTime;
    static com.maddox.JGP.Vector3d spd = new Vector3d();
    static com.maddox.il2.engine.Orient Or = new Orient();
    static com.maddox.JGP.Point3d P = new Point3d();
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
    private static com.maddox.il2.objects.weapons.PlateFilter plateFilter = new PlateFilter(((com.maddox.il2.objects.weapons.PlateFilter) (null)));
    private static com.maddox.JGP.Point3d corn = new Point3d();
    private static com.maddox.JGP.Point3d corn1 = new Point3d();
    private static float plateBox[] = new float[6];
    private static boolean bPlateExist = false;
    private static boolean bPlateGround = false;
    private static com.maddox.il2.engine.Loc __loc = new Loc();
    protected com.maddox.sound.SoundFX sound;
    protected static final float SND_TIME_BOUND = 5F;









}
