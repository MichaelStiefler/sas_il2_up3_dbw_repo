// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Soldier.java

package com.maddox.il2.objects.humans;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.ai.ground.UnitInterface;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorMeshDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Message;
import com.maddox.rts.Time;

public class Soldier extends com.maddox.il2.engine.ActorMesh
    implements com.maddox.il2.engine.MsgCollisionRequestListener, com.maddox.il2.engine.MsgCollisionListener, com.maddox.il2.ai.MsgExplosionListener, com.maddox.il2.ai.MsgShotListener, com.maddox.il2.ai.ground.Prey
{
    class Move extends com.maddox.il2.engine.Interpolate
    {

        public boolean tick()
        {
            if((st == 3 || st == 4) && com.maddox.rts.Time.current() >= disappearTime)
            {
                postDestroy();
                return false;
            }
            if(dying != 0)
            {
                switch(st)
                {
                case 1: // '\001'
                    st = 2;
                    animStartTime = com.maddox.rts.Time.current();
                    break;

                case 3: // '\003'
                    st = 4;
                    idxOfDeadPose = com.maddox.il2.ai.World.Rnd().nextInt(0, 3);
                    break;
                }
                setAnimFrame(com.maddox.rts.Time.tickNext());
            }
            long l = com.maddox.rts.Time.tickNext() - animStartTime;
            switch(st)
            {
            default:
                break;

            case 0: // '\0'
                pos.getAbs(com.maddox.il2.objects.humans.Soldier.p);
                com.maddox.il2.objects.humans.Soldier.p.scaleAdd(com.maddox.rts.Time.tickLenFs(), speed, com.maddox.il2.objects.humans.Soldier.p);
                speed.z -= com.maddox.rts.Time.tickLenFs() * com.maddox.il2.ai.World.g();
                com.maddox.il2.engine.Engine.land();
                float f = com.maddox.il2.engine.Landscape.HQ((float)com.maddox.il2.objects.humans.Soldier.p.x, (float)com.maddox.il2.objects.humans.Soldier.p.y);
                if(com.maddox.il2.objects.humans.Soldier.p.z <= (double)f)
                {
                    speed.z = 0.0D;
                    speed.normalize();
                    speed.scale(6.5454545021057129D);
                    com.maddox.il2.objects.humans.Soldier.p.z = f;
                    st = 1;
                    nRunCycles = com.maddox.il2.ai.World.Rnd().nextInt(9, 17);
                }
                pos.setAbs(com.maddox.il2.objects.humans.Soldier.p);
                break;

            case 1: // '\001'
                pos.getAbs(com.maddox.il2.objects.humans.Soldier.p);
                com.maddox.il2.objects.humans.Soldier.p.scaleAdd(com.maddox.rts.Time.tickLenFs(), speed, com.maddox.il2.objects.humans.Soldier.p);
                com.maddox.il2.objects.humans.Soldier.p.z = com.maddox.il2.engine.Engine.land().HQ(com.maddox.il2.objects.humans.Soldier.p.x, com.maddox.il2.objects.humans.Soldier.p.y);
                pos.setAbs(com.maddox.il2.objects.humans.Soldier.p);
                if(l / 733L >= (long)nRunCycles || com.maddox.il2.ai.World.land().isWater(com.maddox.il2.objects.humans.Soldier.p.x, com.maddox.il2.objects.humans.Soldier.p.y))
                {
                    st = 2;
                    animStartTime = com.maddox.rts.Time.current();
                }
                break;

            case 2: // '\002'
                pos.getAbs(com.maddox.il2.objects.humans.Soldier.p);
                com.maddox.il2.objects.humans.Soldier.p.scaleAdd(com.maddox.rts.Time.tickLenFs(), speed, com.maddox.il2.objects.humans.Soldier.p);
                com.maddox.il2.objects.humans.Soldier.p.z = com.maddox.il2.engine.Engine.land().HQ(com.maddox.il2.objects.humans.Soldier.p.x, com.maddox.il2.objects.humans.Soldier.p.y);
                if(com.maddox.il2.ai.World.land().isWater(com.maddox.il2.objects.humans.Soldier.p.x, com.maddox.il2.objects.humans.Soldier.p.y))
                    com.maddox.il2.objects.humans.Soldier.p.z -= 0.5D;
                pos.setAbs(com.maddox.il2.objects.humans.Soldier.p);
                if(l >= 1066L)
                {
                    st = 3;
                    animStartTime = com.maddox.rts.Time.current();
                    disappearTime = com.maddox.rts.Time.tickNext() + (long)(1000 * com.maddox.il2.ai.World.Rnd().nextInt(25, 35));
                }
                break;

            case 3: // '\003'
            case 4: // '\004'
                pos.getAbs(com.maddox.il2.objects.humans.Soldier.p);
                com.maddox.il2.objects.humans.Soldier.p.z = com.maddox.il2.engine.Engine.land().HQ(com.maddox.il2.objects.humans.Soldier.p.x, com.maddox.il2.objects.humans.Soldier.p.y);
                if(com.maddox.il2.ai.World.land().isWater(com.maddox.il2.objects.humans.Soldier.p.x, com.maddox.il2.objects.humans.Soldier.p.y))
                    com.maddox.il2.objects.humans.Soldier.p.z -= 3D;
                pos.setAbs(com.maddox.il2.objects.humans.Soldier.p);
                break;
            }
            setAnimFrame(com.maddox.rts.Time.tickNext());
            return true;
        }

        Move()
        {
        }
    }

    private class SoldDraw extends com.maddox.il2.engine.ActorMeshDraw
    {

        public int preRender(com.maddox.il2.engine.Actor actor)
        {
            setAnimFrame(com.maddox.rts.Time.current());
            return super.preRender(actor);
        }

        private SoldDraw()
        {
        }

    }


    public static void resetGame()
    {
        preload1 = preload2 = null;
    }

    public static void PRELOAD()
    {
        preload1 = new Mesh(com.maddox.il2.objects.humans.Soldier.GetMeshName(1));
        preload2 = new Mesh(com.maddox.il2.objects.humans.Soldier.GetMeshName(2));
    }

    public void msgCollisionRequest(com.maddox.il2.engine.Actor actor, boolean aflag[])
    {
        if(actor == getOwner())
            aflag[0] = false;
        if(actor instanceof com.maddox.il2.objects.humans.Soldier)
            aflag[0] = false;
        if(dying != 0)
            aflag[0] = false;
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        if(dying != 0)
            return;
        com.maddox.JGP.Point3d point3d = p;
        pos.getAbs(p);
        com.maddox.JGP.Point3d point3d1 = actor.pos.getAbsPoint();
        com.maddox.JGP.Vector3d vector3d = new Vector3d();
        vector3d.set(point3d.x - point3d1.x, point3d.y - point3d1.y, 0.0D);
        if(vector3d.length() < 0.001D)
        {
            float f = com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 359.99F);
            vector3d.set(com.maddox.JGP.Geom.sinDeg(f), com.maddox.JGP.Geom.cosDeg(f), 0.0D);
        }
        vector3d.normalize();
        float f1 = 0.2F;
        vector3d.add(com.maddox.il2.ai.World.Rnd().nextFloat(-f1, f1), com.maddox.il2.ai.World.Rnd().nextFloat(-f1, f1), com.maddox.il2.ai.World.Rnd().nextFloat(-f1, f1));
        vector3d.normalize();
        float f2 = 13.09091F * com.maddox.rts.Time.tickLenFs();
        vector3d.scale(f2);
        point3d.add(vector3d);
        pos.setAbs(point3d);
        if(st == 1)
        {
            st = 2;
            animStartTime = com.maddox.rts.Time.current();
        }
        if(st == 3 && dying == 0 && (actor instanceof com.maddox.il2.ai.ground.UnitInterface) && actor.getSpeed(null) > 0.5D)
            Die(actor);
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        shot.bodyMaterial = 3;
        if(dying != 0)
            return;
        if(shot.power <= 0.0F)
            return;
        if(shot.powerType == 1)
        {
            Die(shot.initiator);
            return;
        }
        if(shot.v.length() < 20D)
        {
            return;
        } else
        {
            Die(shot.initiator);
            return;
        }
    }

    public void msgExplosion(com.maddox.il2.ai.Explosion explosion)
    {
        if(dying != 0)
            return;
        float f = 0.005F;
        float f1 = 0.1F;
        com.maddox.il2.ai.Explosion _tmp = explosion;
        if(com.maddox.il2.ai.Explosion.killable(this, explosion.receivedTNT_1meter(this), f, f1, 0.0F))
            Die(explosion.initiator);
    }

    private void Die(com.maddox.il2.engine.Actor actor)
    {
        if(dying != 0)
        {
            return;
        } else
        {
            com.maddox.il2.ai.World.onActorDied(this, actor);
            dying = 1;
            return;
        }
    }

    public void destroy()
    {
        super.destroy();
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    void setAnimFrame(double d)
    {
        int i;
        int j;
        float f;
        switch(st)
        {
        case 0: // '\0'
        case 1: // '\001'
            i = 0;
            j = 22;
            int k = 733;
            double d1 = d - (double)animStartTime;
            d1 %= k;
            if(d1 < 0.0D)
                d1 += k;
            f = (float)(d1 / (double)k);
            break;

        case 2: // '\002'
            i = 22;
            j = 54;
            int l = 1066;
            double d2 = d - (double)animStartTime;
            if(d2 <= 0.0D)
            {
                f = 0.0F;
                break;
            }
            if(d2 >= (double)l)
                f = 1.0F;
            else
                f = (float)(d2 / (double)l);
            break;

        case 3: // '\003'
            i = 54;
            j = 74;
            int i1 = 666;
            double d3 = d - (double)animStartTime;
            if(d3 <= 0.0D)
            {
                f = 0.0F;
                break;
            }
            if(d3 >= (double)i1)
                f = 1.0F;
            else
                f = (float)(d3 / (double)i1);
            break;

        default:
            i = j = 75 + idxOfDeadPose;
            f = 0.0F;
            break;
        }
        mesh().setFrameFromRange(i, j, f);
    }

    public int HitbyMask()
    {
        return -25;
    }

    public int chooseBulletType(com.maddox.il2.engine.BulletProperties abulletproperties[])
    {
        if(dying != 0)
            return -1;
        if(abulletproperties.length == 1)
            return 0;
        if(abulletproperties.length <= 0)
            return -1;
        if(abulletproperties[0].power <= 0.0F)
            return 1;
        if(abulletproperties[0].powerType == 1)
            return 0;
        if(abulletproperties[1].powerType == 1)
            return 1;
        if(abulletproperties[0].cumulativePower > 0.0F)
            return 1;
        return abulletproperties[0].powerType != 2 ? 0 : 1;
    }

    public int chooseShotpoint(com.maddox.il2.engine.BulletProperties bulletproperties)
    {
        return dying == 0 ? 0 : -1;
    }

    public boolean getShotpointOffset(int i, com.maddox.JGP.Point3d point3d)
    {
        if(dying != 0)
            return false;
        if(i != 0)
            return false;
        if(point3d != null)
            point3d.set(0.0D, 0.0D, 0.0D);
        return true;
    }

    private static java.lang.String GetMeshName(int i)
    {
        boolean flag = i == 2;
        boolean flag1 = com.maddox.il2.ai.World.cur().camouflage == 1;
        return "3do/humans/soldiers/" + (flag ? "Germany" : "Russia") + (flag1 ? "Winter" : "Summer") + "/mono.sim";
    }

    public Soldier(com.maddox.il2.engine.Actor actor, int i, com.maddox.il2.engine.Loc loc)
    {
        super(com.maddox.il2.objects.humans.Soldier.GetMeshName(i));
        st = 0;
        dying = 0;
        setOwner(actor);
        setArmy(i);
        com.maddox.JGP.Point3d point3d = new Point3d();
        com.maddox.il2.engine.Orient orient = new Orient();
        loc.get(point3d, orient);
        com.maddox.JGP.Vector3d vector3d = new Vector3d();
        vector3d.set(1.0D, 0.0D, 0.0D);
        orient.transform(vector3d);
        speed = new Vector3d();
        speed.set(vector3d);
        if(speed.length() < 0.0099999997764825821D)
            speed.set(1.0D, 0.0D, 0.0D);
        speed.normalize();
        if(java.lang.Math.abs(speed.z) > 0.90000000000000002D)
        {
            speed.set(1.0D, 0.0D, 0.0D);
            speed.normalize();
        }
        orient.setAT0(speed);
        orient.set(orient.azimut(), 0.0F, 0.0F);
        pos.setAbs(point3d, orient);
        pos.reset();
        speed.scale(6.5454545021057129D);
        st = 0;
        animStartTime = com.maddox.rts.Time.tick() + (long)com.maddox.il2.ai.World.Rnd().nextInt(0, 2300);
        dying = 0;
        collide(true);
        draw = new SoldDraw();
        drawing(true);
        if(!interpEnd("move"))
            interpPut(new Move(), "move", com.maddox.rts.Time.current(), null);
    }

    private static final int FPS = 30;
    private static final int RUN_START_FRAME = 0;
    private static final int RUN_LAST_FRAME = 22;
    private static final int RUN_N_FRAMES = 23;
    private static final int RUN_CYCLE_TIME = 733;
    private static final int FALL_START_FRAME = 22;
    private static final int FALL_LAST_FRAME = 54;
    private static final int FALL_N_FRAMES = 33;
    private static final int FALL_CYCLE_TIME = 1066;
    private static final int LIE_START_FRAME = 54;
    private static final int LIE_LAST_FRAME = 74;
    private static final int LIE_N_FRAMES = 21;
    private static final int LIE_CYCLE_TIME = 666;
    private static final int LIEDEAD_START_FRAME = 75;
    private static final int LIEDEAD_N_FRAMES = 4;
    private static final float RUN_SPEED = 6.545455F;
    private com.maddox.JGP.Vector3d speed;
    private static final int ST_FLY = 0;
    private static final int ST_RUN = 1;
    private static final int ST_FALL = 2;
    private static final int ST_LIE = 3;
    private static final int ST_LIEDEAD = 4;
    private int st;
    private int dying;
    static final int DYING_NONE = 0;
    static final int DYING_DEAD = 1;
    private int idxOfDeadPose;
    private long animStartTime;
    private long disappearTime;
    private int nRunCycles;
    private static com.maddox.il2.engine.Mesh preload1 = null;
    private static com.maddox.il2.engine.Mesh preload2 = null;
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();
    private static com.maddox.JGP.Vector3f n = new Vector3f();













}
