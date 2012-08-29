// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Train.java

package com.maddox.il2.objects.trains;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Chief;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.NearestEnemies;
import com.maddox.il2.ai.ground.RoadPart;
import com.maddox.il2.ai.ground.RoadPath;
import com.maddox.il2.ai.ground.RoadSegment;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorException;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.Interpolate;
import com.maddox.rts.Message;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import java.io.PrintStream;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.objects.trains:
//            Wagon, WagonSpawn, LocomotiveVerm

public class Train extends com.maddox.il2.ai.Chief
{
    class Move extends com.maddox.il2.engine.Interpolate
    {

        public boolean tick()
        {
            if(com.maddox.rts.Time.current() >= startDelay)
                moveTrain(com.maddox.rts.Time.tickLenFs());
            return true;
        }

        Move()
        {
        }
    }

    public static class TrainState
    {

        public int _headSeg;
        public double _headAlong;
        public float _curSpeed;
        public double _milestoneDist;
        public float _requiredSpeed;
        public float _maxAcceler;

        public TrainState()
        {
        }
    }


    void getStateData(com.maddox.il2.objects.trains.TrainState trainstate)
    {
        trainstate._headSeg = headSeg;
        trainstate._headAlong = headAlong;
        trainstate._curSpeed = curSpeed;
        trainstate._milestoneDist = milestoneDist;
        trainstate._requiredSpeed = requiredSpeed;
        trainstate._maxAcceler = maxAcceler;
    }

    protected final float getEngineSmokeKoef()
    {
        if(requiredSpeed < 11.11111F)
            return curSpeed / 11.11111F;
        else
            return 1.0F;
    }

    protected final boolean stoppedForever()
    {
        return requiredSpeed < 0.0F;
    }

    private Train(com.maddox.il2.objects.trains.Train train, int i)
    {
        if(i <= 0)
            com.maddox.il2.objects.trains.Train.ERR("Split at head");
        road = new RoadPath(train.road);
        startDelay = road.get(0).waitTime;
        if(startDelay < 0L)
            startDelay = 0L;
        road.RegisterTravellerToBridges(this);
        setName(train.name() + "x");
        setArmy(train.getArmy());
        headSeg = (int)train.getLocationOfWagon(i, true);
        headAlong = train.getLocationOfWagon(i, false);
        pos = new ActorPosMove(this);
        pos.setAbs(road.get(headSeg).computePos_Fit(headAlong, 0.0D, 0.0F));
        pos.reset();
        computeSpeedsWhenCrush(train.curSpeed);
        wagons = new ArrayList();
        for(int j = i; j < train.wagons.size(); j++)
        {
            com.maddox.il2.objects.trains.Wagon wagon = (com.maddox.il2.objects.trains.Wagon)train.wagons.get(j);
            wagons.add(wagon);
            wagon.setOwner(this);
        }

        for(int k = train.wagons.size() - 1; k >= i; k--)
            train.wagons.remove(k);

        train.recomputeTrainLength();
        recomputeTrainLength();
        placeTrain(false, false);
        if(!interpEnd("move"))
            interpPut(new Move(), "move", com.maddox.rts.Time.current(), null);
    }

    private static final void ERR_NO_WAGONS(java.lang.String s)
    {
        java.lang.String s1 = "INTERNAL ERROR IN Train." + s + "(): No wagons";
        java.lang.System.out.println(s1);
        throw new ActorException(s1);
    }

    private static final void ERR(java.lang.String s)
    {
        java.lang.String s1 = "INTERNAL ERROR IN Train: " + s;
        java.lang.System.out.println(s1);
        throw new ActorException(s1);
    }

    private static final void ConstructorFailure()
    {
        java.lang.System.out.println("Train: Creation error");
        throw new ActorException();
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public Train(java.lang.String s, int i, com.maddox.rts.SectFile sectfile, java.lang.String s1, com.maddox.rts.SectFile sectfile1, java.lang.String s2)
    {
        try
        {
            road = new RoadPath(sectfile1, s2);
            startDelay = road.get(0).waitTime;
            if(startDelay < 0L)
                startDelay = 0L;
            road.RegisterTravellerToBridges(this);
            setName(s);
            setArmy(i);
            headSeg = 0;
            headAlong = 0.0D;
            pos = new ActorPosMove(this);
            pos.setAbs(road.get(0).start);
            pos.reset();
            int j = sectfile.sectionIndex(s1);
            int k = sectfile.vars(j);
            if(k <= 0)
                throw new ActorException("Train: Missing wagons");
            wagons = new ArrayList();
            for(int l = 0; l < k; l++)
            {
                java.lang.String s3 = sectfile.var(j, l);
                java.lang.Object obj = com.maddox.rts.Spawn.get(s3);
                if(obj == null)
                    throw new ActorException("Train: Unknown class of wagon (" + s3 + ")");
                com.maddox.il2.objects.trains.Wagon wagon = ((com.maddox.il2.objects.trains.WagonSpawn)obj).wagonSpawn(this);
                wagon.setName(s + l);
                wagons.add(wagon);
            }

            recomputeTrainLength();
            curSpeed = 0.0F;
            requiredSpeed = 0.0F;
            placeTrain(true, false);
            recomputeSpeedRequirements((road.get(headSeg).length2Dallprev + headAlong) - (double)trainLength);
            if(!interpEnd("move"))
                interpPut(new Move(), "move", com.maddox.rts.Time.current(), null);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("Train creation failure:");
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
            com.maddox.il2.objects.trains.Train.ConstructorFailure();
        }
    }

    public void BridgeSegmentDestroyed(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        boolean flag = road.MarkDestroyedSegments(i, j);
        if(!flag)
            return;
        int k;
        for(k = tailSeg; k <= headSeg; k++)
            if(road.segIsWrongOrDamaged(k))
                break;

        if(k > headSeg)
            return;
        for(int l = 0; l < wagons.size(); l++)
        {
            com.maddox.il2.objects.trains.Wagon wagon = (com.maddox.il2.objects.trains.Wagon)wagons.get(l);
            wagon.absoluteDeath(actor);
        }

        road.UnregisterTravellerFromBridges(this);
        destroy();
    }

    private static final int SecsToTicks(float f)
    {
        int i = (int)(0.5D + (double)(f / com.maddox.rts.Time.tickLenFs()));
        return i > 0 ? i : 0;
    }

    public com.maddox.il2.engine.Actor GetNearestEnemy(com.maddox.JGP.Point3d point3d, double d, int i)
    {
        com.maddox.il2.ai.ground.NearestEnemies.set(i);
        return com.maddox.il2.ai.ground.NearestEnemies.getAFoundEnemy(point3d, d, getArmy());
    }

    private static float solveSquareEq(float f, float f1, float f2)
    {
        float f3 = f1 * f1 - 4F * f * f2;
        if(f3 < 0.0F)
            return -1F;
        f3 = (float)java.lang.Math.sqrt(f3);
        float f4 = (-f1 + f3) / (2.0F * f);
        float f5 = (-f1 - f3) / (2.0F * f);
        if(f5 > f4)
            f4 = f5;
        return f4 < 0.0F ? -1F : f4;
    }

    private static float findSideBOfTriangle(float f, float f1, float f2)
    {
        return com.maddox.il2.objects.trains.Train.solveSquareEq(1.0F, 2.0F * f * f2, f * f - f1 * f1);
    }

    private void recomputeTrainLength()
    {
        trainLength = 0.0F;
        for(int i = 0; i < wagons.size(); i++)
        {
            com.maddox.il2.objects.trains.Wagon wagon = (com.maddox.il2.objects.trains.Wagon)wagons.get(i);
            trainLength += wagon.getLength();
        }

    }

    private void placeTrain(boolean flag, boolean flag1)
    {
        if(flag1)
        {
            for(int i = 0; i < wagons.size(); i++)
            {
                com.maddox.il2.objects.trains.Wagon wagon = (com.maddox.il2.objects.trains.Wagon)wagons.get(i);
                wagon.place(null, null, false, true);
            }

            return;
        }
        if(flag)
        {
            float f = trainLength * 1.02F;
            com.maddox.il2.ai.ground.RoadPart roadpart = new RoadPart();
            if(!road.FindFreeSpace(f, headSeg, headAlong, roadpart))
            {
                java.lang.System.out.println("Train: Not enough room for wagons");
                throw new ActorException();
            }
            headSeg = roadpart.begseg;
            headAlong = roadpart.begt;
        }
        boolean flag2 = false;
        double d1 = 0.0D;
        int j = headSeg;
        double d = headAlong;
        com.maddox.JGP.Point3d point3d = road.get(j).computePos_Fit(d, 0.0D, 0.0F);
        for(int l = 0; l < wagons.size(); l++)
        {
            com.maddox.il2.objects.trains.Wagon wagon1 = (com.maddox.il2.objects.trains.Wagon)wagons.get(l);
            float f1 = wagon1.getLength();
            int k;
            double d2;
            if(d >= (double)f1)
            {
                k = j;
                d2 = d - (double)f1;
            } else
            {
                if(j <= 0)
                {
                    java.lang.System.out.println("Train: No room for wagons (curly station road?)");
                    throw new ActorException();
                }
                k = j - 1;
                d2 = road.get(k).length2D;
                float f2 = road.computeCosToNextSegment(k);
                float f3 = com.maddox.il2.objects.trains.Train.findSideBOfTriangle((float)d, f1, f2);
                if(f3 < 0.0F || (double)f3 > d2)
                {
                    java.lang.System.out.println("Train: internal error in computings (len=" + d2 + " B=" + f3 + ")");
                    throw new ActorException();
                }
                d2 -= f3;
                if(d2 <= 0.0D)
                    d2 = 0.0D;
            }
            com.maddox.JGP.Point3d point3d1 = road.get(k).computePos_Fit(d2, 0.0D, 0.0F);
            wagon1.place(point3d, point3d1, flag, false);
            j = k;
            d = d2;
            point3d.set(point3d1);
            tailSeg = k;
            tailAlong = d2;
        }

    }

    private final double getLocationOfWagon(int i, boolean flag)
    {
        int j = headSeg;
        double d = headAlong;
        for(int k = 0; k < i; k++)
        {
            com.maddox.il2.objects.trains.Wagon wagon = (com.maddox.il2.objects.trains.Wagon)wagons.get(k);
            float f = wagon.getLength();
            if(d >= (double)f)
            {
                d -= f;
            } else
            {
                if(j <= 0)
                {
                    java.lang.System.out.println("Train: No room for wagons (curly station road)");
                    throw new ActorException();
                }
                j--;
                double d1 = road.get(j).length2D;
                float f1 = road.computeCosToNextSegment(j);
                float f2 = com.maddox.il2.objects.trains.Train.findSideBOfTriangle((float)d, f, f1);
                if(f2 < 0.0F || (double)f2 > d1)
                {
                    java.lang.System.out.println("Train: internal error in computings (len=" + d1 + " B=" + f2 + ")");
                    throw new ActorException();
                }
                d = d1 - (double)f2;
                if(d <= 0.0D)
                    d = 0.0D;
            }
        }

        return flag ? j : d;
    }

    private void computeSpeedsWhenCrush(float f)
    {
        curSpeed = f * 0.9F;
        milestoneDist = 99999000D;
        requiredSpeed = 0.0F;
        maxAcceler = 3.5F;
    }

    private void LocoSound(int i)
    {
        if(wagons == null || wagons.size() <= 0)
            return;
        com.maddox.il2.objects.trains.Wagon wagon = (com.maddox.il2.objects.trains.Wagon)wagons.get(0);
        if(wagon == null || !(wagon instanceof com.maddox.il2.objects.trains.LocomotiveVerm))
            return;
        switch(i)
        {
        case 0: // '\0'
            wagon.newSound("models.train", true);
            break;

        case 1: // '\001'
            wagon.newSound("objects.train_signal", true);
            break;

        default:
            wagon.breakSounds();
            break;
        }
    }

    private void recomputeSpeedRequirements(double d)
    {
        double d1 = road.get(road.nsegments() - 1).length2Dallprev;
        d1 -= trainLength;
        maxAcceler = 1.5F;
        double d2;
        if(d1 <= 350D)
        {
            if(d < d1 * 0.5D)
            {
                LocoSound(0);
                milestoneDist = d1 * 0.5D;
                d2 = milestoneDist - d;
                requiredSpeed = 11.11111F;
            } else
            {
                LocoSound(1);
                milestoneDist = 99999900D;
                d2 = d1 - d;
                requiredSpeed = 0.7222222F;
            }
        } else
        if(d < 350D)
        {
            LocoSound(0);
            milestoneDist = 350D;
            d2 = milestoneDist - d;
            requiredSpeed = 11.11111F;
        } else
        if(d < d1 - 350D)
        {
            milestoneDist = d1 - 350D;
            d2 = 175D;
            requiredSpeed = 11.11111F;
        } else
        {
            LocoSound(1);
            milestoneDist = 99999900D;
            d2 = d1 - d;
            requiredSpeed = 0.7222222F;
        }
        if(d2 > 0.050000000000000003D)
        {
            float f = (float)((double)(requiredSpeed * requiredSpeed - curSpeed * curSpeed) / (2D * d2));
            f = java.lang.Math.abs(f);
            if(f <= maxAcceler)
                maxAcceler = f;
        }
        if(maxAcceler < 0.01F)
            maxAcceler = 0.01F;
    }

    private boolean cantEnterIntoSegment(int i)
    {
        return i >= road.nsegments() - 1 || !road.segIsPassableBy(i, this);
    }

    private void moveTrain(float f)
    {
        if(requiredSpeed < 0.0F)
        {
            placeTrain(false, true);
            return;
        }
        com.maddox.il2.ai.ground.RoadSegment roadsegment = road.get(headSeg);
        double d = (roadsegment.length2Dallprev + headAlong) - (double)trainLength;
        if(d >= milestoneDist)
            recomputeSpeedRequirements(d);
        float f1 = requiredSpeed - curSpeed;
        double d1;
        if(f1 != 0.0F)
        {
            f1 /= f;
            float f2;
            if(java.lang.Math.abs(f1) > maxAcceler)
            {
                f1 = f1 < 0.0F ? -maxAcceler : maxAcceler;
                f2 = curSpeed + f * f1;
                if(f2 < 0.0F)
                    f2 = 0.0F;
            } else
            {
                f2 = requiredSpeed;
            }
            d1 = curSpeed * f + f1 * f * f * 0.5F;
            curSpeed = f2;
            if(d1 <= 0.0D)
                d1 = 0.0D;
        } else
        {
            d1 = curSpeed * f;
            if(requiredSpeed == 0.0F)
                requiredSpeed = -1F;
        }
        for(; headAlong + d1 >= roadsegment.length2D; roadsegment = road.get(headSeg))
        {
            if(cantEnterIntoSegment(headSeg + 1))
            {
                headAlong = roadsegment.length2D;
                d1 = 0.0D;
                curSpeed = 0.0F;
                requiredSpeed = -1F;
                if(headSeg + 1 >= road.nsegments() - 1)
                    com.maddox.il2.ai.World.onTaskComplete(this);
                break;
            }
            headAlong = (headAlong + d1) - roadsegment.length2D;
            headSeg++;
        }

        headAlong += d1;
        pos.setAbs(roadsegment.computePos_Fit(headAlong, 0.0D, 0.0F));
        placeTrain(false, false);
    }

    void wagonDied(com.maddox.il2.objects.trains.Wagon wagon, com.maddox.il2.engine.Actor actor)
    {
        int i;
        for(i = 0; i < wagons.size(); i++)
            if(wagon == (com.maddox.il2.objects.trains.Wagon)wagons.get(i))
                break;

        if(i >= wagons.size())
            com.maddox.il2.objects.trains.Train.ERR("Unknown wagon");
        if(requiredSpeed >= 0.0F)
            if(i == 0)
            {
                computeSpeedsWhenCrush(curSpeed);
                if(wagon instanceof com.maddox.il2.objects.trains.LocomotiveVerm)
                    com.maddox.il2.ai.World.onActorDied(this, actor);
            } else
            {
                computeSpeedsWhenCrush(curSpeed);
                if(wagon instanceof com.maddox.il2.objects.trains.LocomotiveVerm)
                    com.maddox.il2.ai.World.onActorDied(this, actor);
            }
    }

    boolean isAnybodyDead()
    {
        for(int i = 0; i < wagons.size(); i++)
        {
            com.maddox.il2.objects.trains.Wagon wagon = (com.maddox.il2.objects.trains.Wagon)wagons.get(i);
            if(wagon.IsDeadOrDying())
                return true;
        }

        return false;
    }

    void setStateDataMirror(com.maddox.il2.objects.trains.TrainState trainstate, boolean flag)
    {
        headSeg = trainstate._headSeg;
        headAlong = trainstate._headAlong;
        curSpeed = trainstate._curSpeed;
        milestoneDist = trainstate._milestoneDist;
        requiredSpeed = trainstate._requiredSpeed;
        maxAcceler = trainstate._maxAcceler;
        placeTrain(false, false);
        if(requiredSpeed >= 0.0F && flag)
            computeSpeedsWhenCrush(curSpeed);
    }

    public static final float TRAIN_SPEED = 11.11111F;
    private static final float TRAIN_SLOWSPEED = 0.7222222F;
    private static final float TRAIN_ACCEL = 1.5F;
    private static final float TRAIN_CRUSHACCEL = 3.5F;
    public static final double TRAIN_SPEEDUPDIST = 350D;
    private java.util.ArrayList wagons;
    private float trainLength;
    private com.maddox.il2.ai.ground.RoadPath road;
    private long startDelay;
    private int headSeg;
    private double headAlong;
    private int tailSeg;
    private double tailAlong;
    private float curSpeed;
    private double milestoneDist;
    private float requiredSpeed;
    private float maxAcceler;


}
