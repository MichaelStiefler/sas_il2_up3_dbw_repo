// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   C_47.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Tuple3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Property;
import java.util.Random;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme2, PaintSchemeBMPar02, PaintSchemeBMPar04, TypeTransport, 
//            Aircraft, NetAircraft, PaintScheme

public class C_47 extends com.maddox.il2.objects.air.Scheme2
    implements com.maddox.il2.objects.air.TypeTransport
{

    public C_47()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, -45F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -45F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 20F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 20F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, -120F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, -120F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.C_47.moveGear(((com.maddox.il2.engine.ActorHMesh)this).hierMesh(), f);
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        ((com.maddox.il2.objects.air.Aircraft)this).setShot(shot);
        if(shot.chunkName.startsWith("WingLOut") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F && java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).y) < 6D)
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitTank(shot.initiator, 0, 1);
        if(shot.chunkName.startsWith("WingROut") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F && java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).y) < 6D)
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitTank(shot.initiator, 3, 1);
        if(shot.chunkName.startsWith("WingLIn") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F && java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).y) < 1.940000057220459D)
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitTank(shot.initiator, 1, 1);
        if(shot.chunkName.startsWith("WingRIn") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F && java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).y) < 1.940000057220459D)
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitTank(shot.initiator, 2, 1);
        if(shot.chunkName.startsWith("Engine1") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitEngine(shot.initiator, 0, 1);
        if(shot.chunkName.startsWith("Engine2") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitEngine(shot.initiator, 1, 1);
        if(shot.chunkName.startsWith("Nose") && ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).x > 4.9000000953674316D && ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).z > -0.090000003576278687D && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.1F)
            if(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.Pd)).y > 0.0D)
            {
                ((com.maddox.il2.objects.air.Aircraft)this).killPilot(shot.initiator, 0);
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).setCapableOfBMP(false, shot.initiator);
            } else
            {
                ((com.maddox.il2.objects.air.Aircraft)this).killPilot(shot.initiator, 1);
            }
        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateEngineStates[0] > 2 && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateEngineStates[1] > 2 && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 33)
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).setCapableOfBMP(false, shot.initiator);
        ((com.maddox.il2.objects.air.Aircraft)this).msgShot(shot);
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot1_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Head1_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask1_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot1_D1", true);
            break;

        case 1: // '\001'
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot2_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask2_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot2_D1", true);
            break;
        }
    }

    public void rareAction(float f, boolean flag)
    {
        ((com.maddox.il2.objects.air.Aircraft)this).rareAction(f, flag);
        for(int i = 1; i < 3; i++)
            if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getAltitude() < 3000F)
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask" + i + "_D0", false);
            else
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask" + i + "_D0", ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().isChunkVisible("Pilot" + i + "_D0"));

    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        default:
            break;

        case 13: // '\r'
            ((com.maddox.il2.objects.air.Aircraft)this).killPilot(((com.maddox.il2.engine.Actor) (this)), 0);
            ((com.maddox.il2.objects.air.Aircraft)this).killPilot(((com.maddox.il2.engine.Actor) (this)), 1);
            break;

        case 35: // '#'
            if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.25F)
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitTank(((com.maddox.il2.engine.Actor) (this)), 1, com.maddox.il2.ai.World.Rnd().nextInt(2, 6));
            break;

        case 38: // '&'
            if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.25F)
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitTank(((com.maddox.il2.engine.Actor) (this)), 2, com.maddox.il2.ai.World.Rnd().nextInt(2, 6));
            break;
        }
        return super.cutFM(i, j, actor);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(((java.lang.Throwable) (classnotfoundexception)).getMessage());
        }
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.C_47.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "Douglas");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshNameDemo", "3DO/Plane/C-47(USA)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/C-47(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeBMPar02())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_us", "3DO/Plane/C-47(USA)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_us", ((java.lang.Object) (new PaintSchemeBMPar04())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "originCountry", com.maddox.il2.objects.air.PaintScheme.countryUSA);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "noseart", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1939F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 2999.9F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/DC-3.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitC47.class
        })));
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_BombSpawn01"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "28xPara", new java.lang.String[] {
            "BombGunPara 28"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "5xCargoA", new java.lang.String[] {
            "BombGunCargoA 5"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null
        });
    }
}
