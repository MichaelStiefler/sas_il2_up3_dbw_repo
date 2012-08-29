// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RocketryWagon.java

package com.maddox.il2.objects.vehicles.artillery;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Message;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.vehicles.artillery:
//            RocketryRocket, RocketryGeneric

public class RocketryWagon extends com.maddox.il2.engine.ActorMesh
{
    class Move extends com.maddox.il2.engine.Interpolate
    {

        private void disappear()
        {
            if(rocket != null)
            {
                rocket.forgetWagon();
                rocket = null;
            }
            collide(false);
            drawing(false);
            postDestroy();
        }

        public boolean tick()
        {
            if(sta == -2)
                if(--countdownTicks > 0L)
                {
                    return true;
                } else
                {
                    disappear();
                    return false;
                }
            long l = com.maddox.rts.Time.current();
            float f = (float)(l - timeOfStartMS) * 0.001F;
            int i = chooseTrajectorySegment(f);
            if(sta != i)
            {
                if(i == -2)
                {
                    disappear();
                    return false;
                }
                advanceState(sta, i);
            }
            computeCurLoc(sta, f, com.maddox.il2.objects.vehicles.artillery.RocketryWagon.tmpL);
            if(sta != 1);
            if(sta == 1)
            {
                double d = com.maddox.il2.engine.Engine.land().HQ_Air(com.maddox.il2.objects.vehicles.artillery.RocketryWagon.tmpL.getPoint().x, com.maddox.il2.objects.vehicles.artillery.RocketryWagon.tmpL.getPoint().y);
                if(com.maddox.il2.objects.vehicles.artillery.RocketryWagon.tmpL.getPoint().z <= d)
                {
                    if(com.maddox.il2.engine.Engine.land().isWater(com.maddox.il2.objects.vehicles.artillery.RocketryWagon.tmpL.getPoint().x, com.maddox.il2.objects.vehicles.artillery.RocketryWagon.tmpL.getPoint().y))
                    {
                        com.maddox.il2.objects.effects.Explosions.WreckageDrop_Water(com.maddox.il2.objects.vehicles.artillery.RocketryWagon.tmpL.getPoint());
                        countdownTicks = 0L;
                    } else
                    {
                        com.maddox.il2.objects.vehicles.artillery.RocketryWagon.tmpL.getPoint().z = d;
                        countdownTicks = com.maddox.il2.objects.vehicles.artillery.RocketryWagon.SecsToTicks(com.maddox.il2.ai.World.Rnd().nextFloat(15F, 25F));
                    }
                    sta = -2;
                }
            }
            pos.setAbs(com.maddox.il2.objects.vehicles.artillery.RocketryWagon.tmpL);
            return true;
        }

        Move()
        {
        }
    }


    void silentDeath()
    {
        rocket = null;
        destroy();
    }

    void forgetRocket()
    {
        rocket = null;
    }

    private static final long SecsToTicks(float f)
    {
        long l = (long)(0.5D + (double)(f / com.maddox.rts.Time.tickLenFs()));
        return l >= 1L ? l : 1L;
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    private int chooseTrajectorySegment(float f)
    {
        int i;
        for(i = 0; i < traj.length; i++)
            if((double)f < traj[i].t0)
                return i - 1;

        if((double)f < traj[i - 1].t0 + traj[i - 1].t)
            return i - 1;
        else
            return -2;
    }

    private void computeCurLoc(int i, float f, com.maddox.il2.engine.Loc loc)
    {
        if(i < 0)
        {
            loc.getPoint().set(traj[0].pos0);
            if(traj[0].v0.lengthSquared() > 0.0D)
                loc.getOrient().setAT0(traj[0].v0);
            else
                loc.getOrient().setAT0(traj[0].a);
            return;
        }
        f = (float)((double)f - traj[i].t0);
        tmpV3d0.scale(traj[i].v0, f);
        tmpV3d1.scale(traj[i].a, (double)(f * f) * 0.5D);
        tmpV3d0.add(tmpV3d1);
        tmpV3d0.add(traj[i].pos0);
        loc.getPoint().set(tmpV3d0);
        tmpV3d0.scale(traj[i].a, f);
        tmpV3d0.add(traj[i].v0);
        if(tmpV3d0.lengthSquared() <= 0.0D)
            tmpV3d0.set(traj[i].a);
        loc.getOrient().setAT0(tmpV3d0);
    }

    private void advanceState(int i, int j)
    {
        for(sta = i; sta < j;)
        {
            sta++;
            switch(sta)
            {
            case 0: // '\0'
                if(rocket != null)
                {
                    rocket.forgetWagon();
                    rocket = null;
                }
                com.maddox.il2.engine.Eff3DActor.New(this, null, null, 1.0F, "3DO/Effects/Tracers/GunpowderRocket/rocket.eff", -1F);
                break;
            }
        }

        sta = j;
    }

    public RocketryWagon(com.maddox.il2.objects.vehicles.artillery.RocketryRocket rocketryrocket, java.lang.String s, long l, long l1, com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.TrajSeg atrajseg[])
    {
        super(s);
        sta = -1;
        traj = null;
        rocket = null;
        countdownTicks = 0L;
        rocket = rocketryrocket;
        traj = atrajseg;
        timeOfStartMS = l;
        setArmy(0);
        sta = -1;
        float f = (float)(l1 - timeOfStartMS) * 0.001F;
        int i = chooseTrajectorySegment(f);
        if(i == -2)
        {
            rocket = null;
            collide(false);
            drawing(false);
            return;
        }
        collide(false);
        drawing(true);
        if(sta != i)
            advanceState(sta, i);
        computeCurLoc(sta, f, tmpL);
        pos.setAbs(tmpL);
        pos.reset();
        if(!interpEnd("move"))
            interpPut(new Move(), "move", l1, null);
    }

    private static final int STA_HELL = -2;
    private static final int STA_WAIT = -1;
    private static final int STA_RAMP = 0;
    private static final int STA_FALL = 1;
    private int sta;
    private com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.TrajSeg traj[];
    private com.maddox.il2.objects.vehicles.artillery.RocketryRocket rocket;
    long timeOfStartMS;
    private long countdownTicks;
    private static com.maddox.JGP.Point3d tmpP = new Point3d();
    private static com.maddox.JGP.Vector3d tmpV = new Vector3d();
    private static com.maddox.JGP.Vector3d tmpV3d0 = new Vector3d();
    private static com.maddox.JGP.Vector3d tmpV3d1 = new Vector3d();
    private static com.maddox.il2.engine.Loc tmpL = new Loc();












}
