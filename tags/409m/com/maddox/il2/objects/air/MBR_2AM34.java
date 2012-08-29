// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MBR_2AM34.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, PaintSchemeBMPar02, PaintSchemeFCSPar01, TypeTransport, 
//            TypeBomber, TypeSailPlane, NetAircraft, PaintScheme, 
//            Aircraft

public class MBR_2AM34 extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeTransport, com.maddox.il2.objects.air.TypeBomber, com.maddox.il2.objects.air.TypeSailPlane
{

    public MBR_2AM34()
    {
        tmpp = new Point3d();
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 33: // '!'
            return super.cutFM(34, j, actor);

        case 36: // '$'
            return super.cutFM(37, j, actor);

        case 11: // '\013'
            cutFM(17, j, actor);
            FM.cut(17, j, actor);
            cutFM(18, j, actor);
            FM.cut(18, j, actor);
            return super.cutFM(i, j, actor);

        case 19: // '\023'
            FM.Gears.bIsSail = false;
            break;
        }
        return super.cutFM(i, j, actor);
    }

    protected void moveElevator(float f)
    {
        hierMesh().chunkSetAngles("VatorL_D0", 0.0F, 45F * f, 0.0F);
        hierMesh().chunkSetAngles("VatorR_D0", 0.0F, 45F * f, 0.0F);
    }

    public void update(float f)
    {
        super.update(f);
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 2; j++)
                if(FM.Gears.clpGearEff[i][j] != null)
                {
                    tmpp.set(FM.Gears.clpGearEff[i][j].pos.getAbsPoint());
                    tmpp.z = 0.01D;
                    FM.Gears.clpGearEff[i][j].pos.setAbs(tmpp);
                    FM.Gears.clpGearEff[i][j].pos.reset();
                }

        }

    }

    public void doKillPilot(int i)
    {
        switch(i)
        {
        case 1: // '\001'
            FM.turret[0].bIsOperable = false;
            break;

        case 2: // '\002'
            FM.turret[1].bIsOperable = false;
            break;
        }
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            hierMesh().chunkVisible("Gore0_D0", true);
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            hierMesh().chunkVisible("Gore1_D0", true);
            break;

        case 2: // '\002'
            hierMesh().chunkVisible("Pilot3_D0", false);
            hierMesh().chunkVisible("Pilot3_D1", true);
            hierMesh().chunkVisible("Gore2_D0", true);
            break;
        }
    }

    protected void moveFlap(float f)
    {
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -45F * f, 0.0F);
        hierMesh().chunkSetAngles("Flap04_D0", 0.0F, -45F * f, 0.0F);
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.MBR_2AM34.moveGear(hierMesh(), f);
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
            float f2 = java.lang.Math.abs(f);
            if(f2 < 114F)
            {
                if(f1 < -33F)
                {
                    f1 = -33F;
                    flag = false;
                }
            } else
            if(f2 < 153F)
            {
                if(f1 < -24F)
                    f1 = -24F;
                if(f1 < -61.962F + 0.333F * f)
                    flag = false;
                if(f1 < -19.111F + 0.185185F * f && f1 > 40F - 0.333F * f)
                    flag = false;
            } else
            if(f2 < 168F)
            {
                if(f1 < -97.666F + 0.481482F * f)
                    f1 = -97.666F + 0.481482F * f;
                if(f1 < -19.111F + 0.185185F * f)
                    flag = false;
            } else
            {
                if(f1 < -97.666F + 0.481482F * f)
                    f1 = -97.666F + 0.481482F * f;
                flag = false;
            }
            if(f1 > 60F)
            {
                f1 = 60F;
                flag = false;
            }
            break;

        case 1: // '\001'
            float f3 = java.lang.Math.abs(f);
            if(f3 < 2.0F)
                flag = false;
            if(f3 < 6.5F)
            {
                if(f1 < -4F)
                    f1 = -4F;
                if(f1 < 17.666F - 3.333F * f)
                    flag = false;
            } else
            if(f3 < 45F)
            {
                if(f1 < 1.232F - 0.805F * f)
                {
                    f1 = 1.232F - 0.805F * f;
                    flag = false;
                }
            } else
            if(f3 < 105F)
            {
                if(f1 < -35F)
                {
                    f1 = -35F;
                    flag = false;
                }
            } else
            if(f1 < 2.2F)
            {
                f1 = 2.2F;
                flag = false;
            }
            if(f > 161F)
            {
                f = 161F;
                flag = false;
            }
            if(f < -161F)
            {
                f = -161F;
                flag = false;
            }
            if(f1 > 56F)
                flag = false;
            if(f1 > 48F)
                f1 = 48F;
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        setShot(shot);
        if(shot.chunkName.startsWith("WingLIn") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitTank(shot.initiator, 0, 1);
        if(shot.chunkName.startsWith("WingRIn") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitTank(shot.initiator, 1, 1);
        if(shot.chunkName.startsWith("Engine1") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitEngine(shot.initiator, 0, 1);
        if(shot.chunkName.startsWith("Pilot1"))
            killPilot(shot.initiator, 0);
        if(shot.chunkName.startsWith("Pilot2"))
            killPilot(shot.initiator, 1);
        if(shot.chunkName.startsWith("Pilot3"))
            killPilot(shot.initiator, 2);
        super.msgShot(shot);
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
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private com.maddox.JGP.Point3d tmpp;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.MBR_2AM34.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "MBR-2");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/MBR-2-AM-34(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "meshName_ru", "3DO/Plane/MBR-2-AM-34/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ru", new PaintSchemeFCSPar01());
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryRussia);
        com.maddox.rts.Property.set(class1, "yearService", 1937F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/MBR-2-AM-34.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 11, 3, 3, 3, 3, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASt 750", "MGunShKASt 750", null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8xAO10", new java.lang.String[] {
            "MGunShKASt 750", "MGunShKASt 750", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6xFAB50", new java.lang.String[] {
            "MGunShKASt 750", "MGunShKASt 750", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8xFAB50", new java.lang.String[] {
            "MGunShKASt 750", "MGunShKASt 750", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xFAB100", new java.lang.String[] {
            "MGunShKASt 750", "MGunShKASt 750", null, null, null, null, null, null, "BombGunFAB100", "BombGunFAB100"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xFAB250", new java.lang.String[] {
            "MGunShKASt 750", "MGunShKASt 750", null, null, null, null, null, null, "BombGunFAB250", "BombGunFAB250"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
