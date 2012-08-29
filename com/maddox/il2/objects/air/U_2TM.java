// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   U_2TM.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.HUD;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, PaintSchemeBMPar00, TypeScout, TypeTransport, 
//            Aircraft, NetAircraft, PaintScheme

public class U_2TM extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeScout, com.maddox.il2.objects.air.TypeTransport
{

    public U_2TM()
    {
    }

    protected void moveFlap(float f)
    {
        float f1 = -60F * f;
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
    }

    private float floatindex(float f, float af[])
    {
        int i = (int)f;
        if(i >= af.length - 1)
            return af[af.length - 1];
        if(i < 0)
            return af[0];
        if(i == 0)
        {
            if(f > 0.0F)
                return af[0] + f * (af[1] - af[0]);
            else
                return af[0];
        } else
        {
            return af[i] + (f % (float)i) * (af[i + 1] - af[i]);
        }
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        setShot(shot);
        if(shot.chunkName.startsWith("WingLMid") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.121F) < shot.mass)
            FM.AS.hitTank(shot.initiator, 0, (int)(1.0F + shot.mass * 18.95F * 2.0F));
        if(shot.chunkName.startsWith("WingRMid") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.121F) < shot.mass)
            FM.AS.hitTank(shot.initiator, 1, (int)(1.0F + shot.mass * 18.95F * 2.0F));
        if(shot.chunkName.startsWith("Engine"))
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < shot.mass)
                FM.AS.hitEngine(shot.initiator, 0, 1);
            if(com.maddox.il2.objects.air.Aircraft.v1.z > 0.0D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
            {
                FM.AS.setEngineDies(shot.initiator, 0);
                if(shot.mass > 0.1F)
                    FM.AS.hitEngine(shot.initiator, 0, 5);
            }
            if(com.maddox.il2.objects.air.Aircraft.v1.x < 0.10000000149011612D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.57F)
                FM.AS.hitOil(shot.initiator, 0);
        }
        if(shot.chunkName.startsWith("Pilot1"))
        {
            killPilot(shot.initiator, 0);
            FM.setCapableOfBMP(false, shot.initiator);
            if(com.maddox.il2.objects.air.Aircraft.Pd.z > 0.5D && shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
        } else
        if(shot.chunkName.startsWith("Pilot2"))
        {
            killPilot(shot.initiator, 1);
            if(com.maddox.il2.objects.air.Aircraft.Pd.z > 0.5D && shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
        } else
        {
            if(shot.chunkName.startsWith("Turret"))
                FM.turret[0].bIsOperable = false;
            if(FM.AS.astateEngineStates[0] == 4 && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 33)
                FM.setCapableOfBMP(false, shot.initiator);
            super.msgShot(shot);
        }
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 34: // '"'
            return super.cutFM(35, j, actor);

        case 37: // '%'
            return super.cutFM(38, j, actor);
        }
        return super.cutFM(i, j, actor);
    }

    public void doKillPilot(int i)
    {
        if(i == 1)
            FM.turret[0].bIsOperable = false;
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            if(!FM.AS.bIsAboutToBailout)
                hierMesh().chunkVisible("Gore1_D0", true);
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("Head2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            if(!FM.AS.bIsAboutToBailout)
                hierMesh().chunkVisible("Gore2_D0", true);
            break;
        }
    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        float f = -af[0];
        float f1 = af[1];
        if(f < -45F)
        {
            f = -45F;
            flag = false;
        }
        if(f > 45F)
        {
            f = 45F;
            flag = false;
        }
        if(f1 < -45F)
        {
            f1 = -45F;
            flag = false;
        }
        if(f1 > 20F)
        {
            f1 = 20F;
            flag = false;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
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

    private static final float gearL2[] = {
        0.0F, 1.0F, 2.0F, 2.9F, 3.2F, 3.35F
    };
    private static final float gearL4[] = {
        0.0F, 7.5F, 15F, 22F, 29F, 35.5F
    };
    private static final float gearL5[] = {
        0.0F, 1.5F, 4F, 7.5F, 10F, 11.5F
    };

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.U_2TM.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "U-2TM");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3do/plane/U-2TM/PlaneU2TM.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeBMPar00())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "originCountry", com.maddox.il2.objects.air.PaintScheme.countryBritain);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1939F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1956F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/U-2TM.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitU2TM.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 1.01885F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            10
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null
        });
    }
}
