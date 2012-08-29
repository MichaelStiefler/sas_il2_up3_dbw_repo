// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 7/28/2011 11:49:08 AM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: fullnames 
// Source File Name:   BF_109B1.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.objects.Wreckage;

// Referenced classes of package com.maddox.il2.objects.air:
//            BF_109, CockpitBF_109Bx, PaintSchemeFMPar01, Aircraft, 
//            Cockpit, NetAircraft

public class BF_109B1 extends com.maddox.il2.objects.air.BF_109
{

    public void registerPit(com.maddox.il2.objects.air.CockpitBF_109Bx cockpitbf_109bx)
    {
        pit = cockpitbf_109bx;
    }

    public BF_109B1()
    {
        sideWindowOpened = false;
        slideRWindow = false;
		sideWindow = false;
        pit = null;
        bChangedPit = true;
        canopyF = 0.0F;
        fMaxKMHSpeedForOpenCanopy = 250F;
        kangle = 0.0F;
        tiltCanopyOpened = false;
        bHasBlister = true;
    }

    public void update(float f)
    {
         if(FM.getSpeed() > 5F)
        {
            hierMesh().chunkSetAngles("SlatL_D0", 0.0F, 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, 1.5F));
            hierMesh().chunkSetAngles("SlatR_D0", 0.0F, 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, 1.5F));
        }
        hierMesh().chunkSetAngles("WaterL_D0", 0.0F, -38F * kangle, 0.0F);
        kangle = 0.95F * kangle + 0.05F * FM.EI.engines[0].getControlRadiator();
        if(kangle > 1.0F)
            kangle = 1.0F;
        super.update(f);
        if((double)FM.CT.getCockpitDoor() > 0.20000000000000001D && bHasBlister && FM.getSpeedKMH() > fMaxKMHSpeedForOpenCanopy && hierMesh().chunkFindCheck("Blister1_D0") != -1 && !sideWindowOpened)
        {
            try
            {
                if(this == com.maddox.il2.ai.World.getPlayerAircraft())
                    ((com.maddox.il2.objects.air.CockpitBF_109Bx)com.maddox.il2.game.Main3D.cur3D().cockpitCur).removeCanopy();
            }
            catch(java.lang.Exception exception) { }
            hierMesh().hideSubTrees("Blister1_D0");
            com.maddox.il2.objects.Wreckage wreckage = new Wreckage(this, hierMesh().chunkFind("Blister1_D0"));
            wreckage.collide(true);
            com.maddox.JGP.Vector3d vector3d = new Vector3d();
            vector3d.set(FM.Vwld);
            wreckage.setSpeed(vector3d);
            bHasBlister = false;
            FM.CT.bHasCockpitDoorControl = false;
            FM.setGCenter(-0.5F);
        }
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
                {
                hierMesh().chunkVisible("Blister1_D0", false);
                    hierMesh().chunkVisible("WindowL1_D0", false);
                    hierMesh().chunkVisible("WindowR1_D0", false);
                } else
            if(bHasBlister)
                {
                hierMesh().chunkVisible("Blister1_D0", true);
                    hierMesh().chunkVisible("WindowL1_D0", true);
                    hierMesh().chunkVisible("WindowR1_D0", true);
                }
            com.maddox.JGP.Point3d point3d = ((com.maddox.il2.engine.Actor) (com.maddox.il2.ai.World.getPlayerAircraft())).pos.getAbsPoint();
            if(((com.maddox.JGP.Tuple3d) (point3d)).z - com.maddox.il2.ai.World.land().HQ(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y) < 0.0099999997764825821D)
                hierMesh().chunkVisible("CF_D0", true);
                if(FM.AS.bIsAboutToBailout)
                {
                hierMesh().chunkVisible("Blister1_D0", false);
                    hierMesh().chunkVisible("WindowL1_D0", false);
                    hierMesh().chunkVisible("WindowR1_D0", false);
                }
        }
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        float f1 = 0.8F;
        float f2 = -0.5F * (float)java.lang.Math.cos((double)(f / f1) * 3.1415926535897931D) + 0.5F;
        if(f <= f1)
        {
            hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -78F * f2, 0.0F);
            hiermesh.chunkSetAngles("GearL2_D0", -24F * f2, 0.0F, 0.0F);
        }
        f2 = -0.5F * (float)java.lang.Math.cos((double)((f - (1.0F - f1)) / f1) * 3.1415926535897931D) + 0.5F;
        if(f >= 1.0F - f1)
        {
            hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 78F * f2, 0.0F);
            hiermesh.chunkSetAngles("GearR2_D0", 24F * f2, 0.0F, 0.0F);
        }
        if(f > 0.99F)
        {
            hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -78F, 0.0F);
            hiermesh.chunkSetAngles("GearL2_D0", -24F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 78F, 0.0F);
            hiermesh.chunkSetAngles("GearR2_D0", 24F, 0.0F, 0.0F);
        }
        if(f < 0.01F)
        {
            hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 0.0F, 0.0F);
            hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 0.0F, 0.0F);
        }
    }

    protected void moveGear(float f)
    {
        float f1 = 0.9F - (float)((com.maddox.il2.ai.Wing)getOwner()).aircIndex(this) * 0.1F;
        float f2 = -0.5F * (float)java.lang.Math.cos((double)(f / f1) * 3.1415926535897931D) + 0.5F;
        if(f <= f1)
        {
            hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -78F * f2, 0.0F);
            hierMesh().chunkSetAngles("GearL2_D0", -24F * f2, 0.0F, 0.0F);
        }
        f2 = -0.5F * (float)java.lang.Math.cos((double)((f - (1.0F - f1)) / f1) * 3.1415926535897931D) + 0.5F;
        if(f >= 1.0F - f1)
        {
            hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 78F * f2, 0.0F);
            hierMesh().chunkSetAngles("GearR2_D0", 24F * f2, 0.0F, 0.0F);
        }
        if(f > 0.99F)
        {
            hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -78F, 0.0F);
            hierMesh().chunkSetAngles("GearL2_D0", -24F, 0.0F, 0.0F);
            hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 78F, 0.0F);
            hierMesh().chunkSetAngles("GearR2_D0", 24F, 0.0F, 0.0F);
        }
    }

    public void moveSteering(float f)
    {
        if(f > 77.5F)
        {
            f = 77.5F;
            FM.Gears.steerAngle = f;
        }
        if(f < -77.5F)
        {
            f = -77.5F;
            FM.Gears.steerAngle = f;
        }
        hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -f, 0.0F);
    }

    public void moveCockpitDoor(float f)
    {
        if(f > canopyF)
        {
            if((FM.Gears.onGround() && FM.getSpeed() < 5F || tiltCanopyOpened) && (FM.isPlayers() || isNetPlayer()))
            {
                sideWindow = false;
                tiltCanopyOpened = true;
                hierMesh().chunkSetAngles("Blister1_D0", 0.0F, 90F * f, 0.0F);
            } else
            {
                sideWindow = true;
                if(pit != null && canopyF == 0.0F)
                    slideRWindow = pit.isViewRight();
                sideWindowOpened = true;
                resetYPRmodifier();
                com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.24F);
                if(slideRWindow)
                    hierMesh().chunkSetLocate("WindowR1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                else
                    hierMesh().chunkSetLocate("WindowL1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
            }
        } else
        if(FM.Gears.onGround() && FM.getSpeed() < 5F && !sideWindowOpened || tiltCanopyOpened)
        {
            sideWindow = false;
            hierMesh().chunkSetAngles("Blister1_D0", 0.0F, 90F * f, 0.0F);
            if(f == 0.0F)
                tiltCanopyOpened = false;
        } else
        {
            sideWindow = true;
            resetYPRmodifier();
            com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.24F);
            if(slideRWindow)
                hierMesh().chunkSetLocate("WindowR1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
            else
                hierMesh().chunkSetLocate("WindowL1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
            if(f == 0.0F)
                sideWindowOpened = false;
        }
        canopyF = f;
        if((double)canopyF < 0.01D)
            canopyF = 0.0F;
            if(com.maddox.il2.engine.Config.isUSE_RENDER())
            {
                if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                    com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
                setDoorSnd(f);
            }
        }


    public float canopyF;
    private float fMaxKMHSpeedForOpenCanopy;
    private float kangle;
    public boolean tiltCanopyOpened;
    public boolean bHasBlister;
    private boolean sideWindowOpened;
    private boolean slideRWindow;
    public boolean sideWindow;
    com.maddox.il2.objects.air.CockpitBF_109Bx pit;
    public boolean bChangedPit;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.BF_109B1.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Bf109");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/BF_109B1/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        
        com.maddox.rts.Property.set(class1, "meshName_de", "3DO/Plane/BF_109B1/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_de", new PaintSchemeFCSPar01());
        
        
        com.maddox.rts.Property.set(class1, "yearService", 1938F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1941F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Bf-109B-1SAS.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitBF_109Bx.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.74985F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 1
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON03", "_CANNON01", "_CANNON02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null
        });
    }
}