// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 7/27/2011 2:58:50 AM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: fullnames 
// Source File Name:   BF_109E3.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.objects.Wreckage;

import java.util.Random;

// Referenced classes of package com.maddox.il2.objects.air:
//            BF_109, CockpitBF_109E3, PaintSchemeFMPar02, Aircraft, 
//            Cockpit, NetAircraft

public class BF_109E3 extends com.maddox.il2.objects.air.BF_109
{

    public void registerPit(com.maddox.il2.objects.air.CockpitBF_109E3 cockpitbf_109e3)
    {
        pit = cockpitbf_109e3;
    }

    public BF_109E3()
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
        burst_fire = new int[2][2];
        bIsE3 = true;
    }

    public void onAircraftLoaded(){
    	super.onAircraftLoaded();
        if(!(getBulletEmitterByHookName("_ExternalDev01") instanceof com.maddox.il2.objects.weapons.GunEmpty) && bIsE3)
        {
            bHasArmor = true;
            hierMesh().chunkVisible("Armor1_D0", true);
        }	
    }
    public void update(float f)
    {
        super.update(f);
        if(getGunByHookName("_CANNON01") instanceof com.maddox.il2.objects.weapons.MGunMGFFki && bIsE3)
        {
            hierMesh().chunkVisible("NoseCannon1_D0", true);
            if(((FlightModelMain) (super.FM)).EI.engines[0].tOilOut > 90F)
            {
                Random random = new Random();
                int i = 1;
                if(FM.CT.WeaponControl[i])
                {
                    for(int j = 0; j < 1; j++)
                    {
                        int l = FM.CT.Weapons[i][j].countBullets();
                        if(l < burst_fire[j][1])
                        {
                            burst_fire[j][0]++;
                            burst_fire[j][1] = l;
                            int i1 = Math.abs(random.nextInt()) % 100;
                            float f1 = (float)burst_fire[j][0] * 1.0F;
                            if((float)i1 < f1)
                                FM.AS.setJamBullets(i, j);
                        }
                    }

                } else
                {
                    for(int k = 0; k < 1; k++)
                    {
                        burst_fire[k][0] = 0;
                        burst_fire[k][1] = FM.CT.Weapons[i][k].countBullets();
                    }

                }
            }
        }
        if(FM.getSpeed() > 5F)
        {
            hierMesh().chunkSetAngles("SlatL_D0", 0.0F, 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, 1.5F));
            hierMesh().chunkSetAngles("SlatR_D0", 0.0F, 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, 1.5F));
        }
        hierMesh().chunkSetAngles("WaterL_D0", 0.0F, -38F * kangle, 0.0F);
        hierMesh().chunkSetAngles("WaterR_D0", 0.0F, -38F * kangle, 0.0F);
        kangle = 0.95F * kangle + 0.05F * FM.EI.engines[0].getControlRadiator();
        if(kangle > 1.0F)
            kangle = 1.0F;
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
    com.maddox.il2.objects.air.CockpitBF_109E3 pit;
    public boolean bChangedPit;
    private int burst_fire[][];
    public boolean bHasArmor;
    protected boolean bIsE3;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.BF_109E3.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Bf109");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Bf-109E-3/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1940F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1944F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Bf-109E-3.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitBF_109E3.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.74985F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 1, 9, 3, 3, 3, 3, 
            3, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_ExternalDev01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", 
            "_ExternalBomb05", "_ExternalDev02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", null, "MGunMGFFs 60", "MGunMGFFs 60", null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xMGFF", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMGFFki 60", "MGunMGFFs 60", "MGunMGFFs 60", null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1sc250", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", null, "MGunMGFFs 60", "MGunMGFFs 60", "PylonETC900 1", "BombGunSC250 1", null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4sc50", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", null, "MGunMGFFs 60", "MGunMGFFs 60", "PylonETC50Bf109 1", null, "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", 
            "BombGunSC50 1", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "E5", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", null, "MGunMGFFs 60", "MGunMGFFs 60", null, null, null, null, null, 
            null, "PylonRECO1BF109 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
    }
}