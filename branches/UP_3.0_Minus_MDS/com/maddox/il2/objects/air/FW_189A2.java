// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   FW_189A2.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme3, PaintSchemeBMPar03, TypeScout, TypeBomber, 
//            Aircraft, NetAircraft, PaintScheme

public class FW_189A2 extends com.maddox.il2.objects.air.Scheme3
    implements com.maddox.il2.objects.air.TypeScout, com.maddox.il2.objects.air.TypeBomber
{

    public FW_189A2()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 115F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 115F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, 100F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 90F * f, 0.0F);
        float f1 = java.lang.Math.max(-f * 1500F, -110F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, -f1, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.FW_189A2.moveGear(hierMesh(), f);
    }

    protected void moveFlap(float f)
    {
        float f1 = -40F * f;
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f1, 0.0F);
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        setShot(shot);
        if(shot.chunkName.startsWith("WingLIn"))
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.048F)
                FM.AS.setJamBullets(0, 0);
            if(com.maddox.il2.objects.air.Aircraft.v1.x < 0.25D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && com.maddox.il2.ai.World.Rnd().nextFloat(0.01F, 0.121F) < shot.mass)
                FM.AS.hitTank(shot.initiator, 0, (int)(1.0F + shot.mass * 26.08F));
        }
        if(shot.chunkName.startsWith("WingRIn"))
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.048F)
                FM.AS.setJamBullets(0, 1);
            if(com.maddox.il2.objects.air.Aircraft.v1.x < 0.25D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && com.maddox.il2.ai.World.Rnd().nextFloat(0.01F, 0.121F) < shot.mass)
                FM.AS.hitTank(shot.initiator, 1, (int)(1.0F + shot.mass * 26.08F));
        }
        if(shot.chunkName.startsWith("Engine1"))
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.5F)
                FM.AS.hitEngine(shot.initiator, 0, (int)(1.0F + shot.mass * 20.7F));
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                FM.AS.hitEngine(shot.initiator, 0, 5);
        }
        if(shot.chunkName.startsWith("Engine2"))
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.5F)
                FM.AS.hitEngine(shot.initiator, 1, (int)(1.0F + shot.mass * 20.7F));
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                FM.AS.hitEngine(shot.initiator, 1, 5);
        }
        if(shot.chunkName.startsWith("Pilot1"))
        {
            if(com.maddox.il2.objects.air.Aircraft.v1.x > -0.5D || (double)shot.power * -com.maddox.il2.objects.air.Aircraft.v1.x > 12800D)
            {
                killPilot(shot.initiator, 0);
                FM.setCapableOfBMP(false, shot.initiator);
                if(com.maddox.il2.objects.air.Aircraft.Pd.z > 0.5D && shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                    com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
            }
            shot.chunkName = "CF_D0";
        }
        if(shot.chunkName.startsWith("Pilot2"))
        {
            killPilot(shot.initiator, 1);
            if(com.maddox.il2.objects.air.Aircraft.Pd.z > 0.5D && shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
            shot.chunkName = "CF_D0";
        }
        if(shot.chunkName.startsWith("Pilot3"))
        {
            killPilot(shot.initiator, 2);
            if(com.maddox.il2.objects.air.Aircraft.Pd.z > 0.5D && shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
            shot.chunkName = "CF_D0";
        }
        if(shot.chunkName.startsWith("Tail1") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
            FM.AS.hitTank(shot.initiator, 0, (int)(1.0F + shot.mass * 26.08F));
        if(shot.chunkName.startsWith("Tail2") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
            FM.AS.hitTank(shot.initiator, 1, (int)(1.0F + shot.mass * 26.08F));
        if((FM.AS.astateEngineStates[0] == 4 || FM.AS.astateEngineStates[1] == 4) && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 33)
            FM.setCapableOfBMP(false, shot.initiator);
        super.msgShot(shot);
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 33: // '!'
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.66F)
                FM.AS.hitTank(((com.maddox.il2.engine.Actor) (this)), 0, 6);
            return super.cutFM(34, j, actor);

        case 36: // '$'
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.66F)
                FM.AS.hitTank(((com.maddox.il2.engine.Actor) (this)), 1, 6);
            return super.cutFM(37, j, actor);
        }
        return super.cutFM(i, j, actor);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        for(int i = 1; i < 4; i++)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask" + i + "_D0", false);
            else
                hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));

    }

    public void doKillPilot(int i)
    {
        if(i == 1)
        {
            FM.turret[1].bIsOperable = false;
            hierMesh().chunkVisible("Turret2A_D0", false);
            hierMesh().chunkVisible("Turret2B_D0", false);
            hierMesh().chunkVisible("Turret2B_D1", true);
        }
        if(i == 2)
        {
            FM.turret[0].bIsOperable = false;
            hierMesh().chunkVisible("Turret1A_D0", false);
            hierMesh().chunkVisible("Turret1B_D0", false);
            hierMesh().chunkVisible("Turret1B_D1", true);
        }
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

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("HMask2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            break;
        }
    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        float f = -af[0];
        float f1 = af[1];
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            if(f1 < 40F)
            {
                f1 = 40F;
                flag = false;
            }
            if(f1 > 96F)
            {
                f1 = 96F;
                flag = false;
            }
            break;

        case 1: // '\001'
            if(f < -75F)
            {
                f = -75F;
                flag = false;
            }
            if(f > 85F)
            {
                f = 85F;
                flag = false;
            }
            if(f1 < 4F)
            {
                f1 = 4F;
                flag = false;
            }
            if(f1 > 80F)
            {
                f1 = 80F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    public boolean typeBomberToggleAutomation()
    {
        return false;
    }

    public void typeBomberAdjDistanceReset()
    {
    }

    public void typeBomberAdjDistancePlus()
    {
    }

    public void typeBomberAdjDistanceMinus()
    {
    }

    public void typeBomberAdjSideslipReset()
    {
    }

    public void typeBomberAdjSideslipPlus()
    {
    }

    public void typeBomberAdjSideslipMinus()
    {
    }

    public void typeBomberAdjAltitudeReset()
    {
    }

    public void typeBomberAdjAltitudePlus()
    {
    }

    public void typeBomberAdjAltitudeMinus()
    {
    }

    public void typeBomberAdjSpeedReset()
    {
    }

    public void typeBomberAdjSpeedPlus()
    {
    }

    public void typeBomberAdjSpeedMinus()
    {
    }

    public void typeBomberUpdate(float f)
    {
        if(FM.isPlayers())
            if(!com.maddox.il2.game.Main3D.cur3D().isViewOutside())
                hierMesh().chunkVisible("CF_D0", false);
            else
                hierMesh().chunkVisible("CF_D0", true);
        if(FM.isPlayers())
        {
            if(!com.maddox.il2.game.Main3D.cur3D().isViewOutside())
                hierMesh().chunkVisible("CF_D1", false);
            hierMesh().chunkVisible("CF_D2", false);
            hierMesh().chunkVisible("CF_D3", false);
        }
        if(FM.isPlayers())
        {
            if(!com.maddox.il2.game.Main3D.cur3D().isViewOutside())
                hierMesh().chunkVisible("Blister1_D0", false);
            else
                hierMesh().chunkVisible("Blister1_D0", true);
            com.maddox.JGP.Point3d point3d = com.maddox.il2.ai.World.getPlayerAircraft().pos.getAbsPoint();
            if(point3d.z - com.maddox.il2.ai.World.land().HQ(point3d.x, point3d.y) < 0.0099999997764825821D)
                hierMesh().chunkVisible("CF_D0", true);
            if(FM.AS.bIsAboutToBailout)
                hierMesh().chunkVisible("Blister1_D0", false);
        }
    }

    public void typeBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
    }

    public void typeBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.FW_189A2.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "Fw-189");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3do/plane/Fw-189A-2/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeBMPar03())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "originCountry", com.maddox.il2.objects.air.PaintScheme.countryGermany);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1941.6F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1948F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/Fw-189A-2.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitFw189.class, com.maddox.il2.objects.air.CockpitFw189_RGunner.class
        })));
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 3, 3, 3, 3, 10, 10, 11, 11
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17k 660", "MGunMG17k 660", null, null, null, null, "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG81t 1500", "MGunMG81t 1500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xSC50", new java.lang.String[] {
            "MGunMG17k 660", "MGunMG17k 660", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "MGunMG81t 1000", "MGunMG81t 1000", "MGunMG81t 1500", "MGunMG81t 1500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
