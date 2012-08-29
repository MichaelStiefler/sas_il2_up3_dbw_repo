// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   DXXI_DK.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            DXXI, PaintSchemeFMPar00DXXI, NetAircraft, PaintScheme, 
//            Aircraft

public class DXXI_DK extends com.maddox.il2.objects.air.DXXI
{

    public DXXI_DK()
    {
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(thisWeaponsName.equals("madsen"))
        {
            hierMesh().chunkVisible("MadsenR_D0", true);
            hierMesh().chunkVisible("MadsenL_D0", true);
        }
        if(com.maddox.il2.engine.Config.isUSE_RENDER() && com.maddox.il2.ai.World.cur().camouflage == 1)
        {
            hierMesh().chunkVisible("GearL3_D0", false);
            hierMesh().chunkVisible("GearR3_D0", false);
            hierMesh().chunkVisible("GearL3W_D0", true);
            hierMesh().chunkVisible("GearR3W_D0", true);
        }
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        if(com.maddox.il2.ai.World.cur().camouflage == 1)
        {
            hiermesh.chunkVisible("GearL3_D0", false);
            hiermesh.chunkVisible("GearR3_D0", false);
            hiermesh.chunkVisible("GearL3W_D0", true);
            hiermesh.chunkVisible("GearR3W_D0", true);
        }
    }

    protected void moveFan(float f)
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            super.moveFan(f);
            float f1 = FM.CT.getAileron();
            float f2 = FM.CT.getElevator();
            hierMesh().chunkSetAngles("Stick_D0", 0.0F, 9F * f1, com.maddox.il2.objects.air.DXXI_DK.cvt(f2, -1F, 1.0F, -8F, 9.5F));
            hierMesh().chunkSetAngles("pilotarm2_d0", com.maddox.il2.objects.air.DXXI_DK.cvt(f1, -1F, 1.0F, 14F, -16F), 0.0F, com.maddox.il2.objects.air.DXXI_DK.cvt(f1, -1F, 1.0F, 6F, -8F) - com.maddox.il2.objects.air.DXXI_DK.cvt(f2, -1F, 1.0F, -37F, 35F));
            hierMesh().chunkSetAngles("pilotarm1_d0", 0.0F, 0.0F, com.maddox.il2.objects.air.DXXI_DK.cvt(f1, -1F, 1.0F, -16F, 14F) + com.maddox.il2.objects.air.DXXI_DK.cvt(f2, -1F, 0.0F, -61F, 0.0F) + com.maddox.il2.objects.air.DXXI_DK.cvt(f2, 0.0F, 1.0F, 0.0F, 43F));
        }
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
        if(hierMesh().isChunkVisible("GearR22_D2") && !hierMesh().isChunkVisible("gearr31_d0"))
        {
            hierMesh().chunkVisible("gearr31_d0", true);
            hierMesh().chunkVisible("gearr32_d0", true);
            com.maddox.il2.objects.Wreckage wreckage = new Wreckage(((com.maddox.il2.engine.ActorHMesh) (this)), hierMesh().chunkFind("GearR22_D1"));
            wreckage.collide(true);
            com.maddox.JGP.Vector3d vector3d = new Vector3d();
            vector3d.set(((com.maddox.JGP.Tuple3d) (FM.Vwld)));
            wreckage.setSpeed(vector3d);
        }
        if(hierMesh().isChunkVisible("GearL22_D2") && !hierMesh().isChunkVisible("gearl31_d0"))
        {
            hierMesh().chunkVisible("gearl31_d0", true);
            hierMesh().chunkVisible("gearl32_d0", true);
            com.maddox.il2.objects.Wreckage wreckage1 = new Wreckage(((com.maddox.il2.engine.ActorHMesh) (this)), hierMesh().chunkFind("GearL22_D1"));
            wreckage1.collide(true);
            com.maddox.JGP.Vector3d vector3d1 = new Vector3d();
            vector3d1.set(((com.maddox.JGP.Tuple3d) (FM.Vwld)));
            wreckage1.setSpeed(vector3d1);
        }
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
        java.lang.Class class1 = com.maddox.il2.objects.air.DXXI_DK.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "D.XXI");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/DXXI_DK/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar00DXXI())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1938F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1943F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/FokkerDK.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitDXXI_SARJA4.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.8472F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "originCountry", com.maddox.il2.objects.air.PaintScheme.countryNoName);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04"
        });
        com.maddox.il2.objects.air.DXXI_DK.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303sipzl 500", "MGunBrowning303sipzl 500", null, null
        });
        com.maddox.il2.objects.air.DXXI_DK.weaponsRegister(class1, "madsen", new java.lang.String[] {
            "MGunBrowning303sipzl 500", "MGunBrowning303sipzl 500", "MGunMadsen20 60", "MGunMadsen20 60"
        });
        com.maddox.il2.objects.air.DXXI_DK.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
