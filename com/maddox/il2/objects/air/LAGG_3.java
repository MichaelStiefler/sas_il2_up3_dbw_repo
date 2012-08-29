// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LAGG_3.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.HUD;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, LAGG_3RD, TypeFighter, Aircraft, 
//            PaintScheme

public abstract class LAGG_3 extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeFighter
{

    public LAGG_3()
    {
        kangle = 0.0F;
    }

    public void update(float f)
    {
        if(!(this instanceof com.maddox.il2.objects.air.LAGG_3RD))
            hierMesh().chunkSetAngles("Water_luk", 0.0F, -17.5F + 17.5F * kangle, 0.0F);
        kangle = 0.95F * kangle + 0.05F * FM.EI.engines[0].getControlRadiator();
        super.update(f);
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("HMask1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            break;
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(FM.getAltitude() < 3000F)
            hierMesh().chunkVisible("HMask1_D0", false);
        else
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 80F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -80F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -100F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 100F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.LAGG_3.moveGear(hierMesh(), f);
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        setShot(shot);
        if(shot.chunkName.startsWith("Engine") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < shot.mass)
            FM.AS.hitEngine(shot.initiator, 0, 1);
        if(shot.chunkName.startsWith("WingLMid") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && shot.power > 7650F && shot.powerType == 3)
            FM.AS.hitTank(shot.initiator, 0, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.mass * 100F)));
        if(shot.chunkName.startsWith("WingLIn") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && shot.power > 7650F && shot.powerType == 3)
            FM.AS.hitTank(shot.initiator, 1, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.mass * 100F)));
        if(shot.chunkName.startsWith("WingRIn") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && shot.power > 7650F && shot.powerType == 3)
            FM.AS.hitTank(shot.initiator, 2, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.mass * 100F)));
        if(shot.chunkName.startsWith("WingRMid") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && shot.power > 7650F && shot.powerType == 3)
            FM.AS.hitTank(shot.initiator, 3, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.mass * 100F)));
        if(shot.chunkName.startsWith("Pilot"))
        {
            if(com.maddox.il2.objects.air.Aircraft.v1.x > 0.86000001430511475D)
            {
                if((double)shot.power * com.maddox.il2.objects.air.Aircraft.v1.x > 19200D)
                    killPilot(shot.initiator, 0);
            } else
            if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 4600F) < shot.power)
            {
                killPilot(shot.initiator, 0);
                if(com.maddox.il2.objects.air.Aircraft.Pd.z > 0.5D && shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                    com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
            }
            shot.chunkName = "CF_D" + chunkDamageVisible("CF");
        }
        super.msgShot(shot);
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        default:
            break;

        case 33: // '!'
            FM.AS.hitTank(this, 1, com.maddox.il2.ai.World.Rnd().nextInt(3, 6));
            return super.cutFM(34, j, actor);

        case 36: // '$'
            FM.AS.hitTank(this, 2, com.maddox.il2.ai.World.Rnd().nextInt(3, 6));
            return super.cutFM(37, j, actor);

        case 19: // '\023'
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
            {
                int k = com.maddox.il2.ai.World.Rnd().nextInt(3, 6);
                FM.AS.hitTank(this, 1, k);
                FM.AS.hitTank(this, 2, k);
            }
            break;
        }
        return super.cutFM(i, j, actor);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte0 = 0;
            int i;
            if(s.endsWith("a"))
            {
                byte0 = 1;
                i = s.charAt(6) - 49;
            } else
            if(s.endsWith("b"))
            {
                byte0 = 2;
                i = s.charAt(6) - 49;
            } else
            {
                i = s.charAt(5) - 49;
            }
            hitFlesh(i, shot, byte0);
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private float kangle;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.LAGG_3.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryRussia);
    }
}
