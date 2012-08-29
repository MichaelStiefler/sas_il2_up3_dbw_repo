// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   I_16TYPE5.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            I_16, PaintSchemeFCSPar07, TypeTNBFighter, Aircraft, 
//            Cockpit, NetAircraft

public class I_16TYPE5 extends com.maddox.il2.objects.air.I_16
    implements com.maddox.il2.objects.air.TypeTNBFighter
{

    public I_16TYPE5()
    {
        bailingOut = false;
        canopyForward = false;
        okToJump = false;
        flaperonAngle = 0.0F;
        aileronsAngle = 0.0F;
        sideDoorOpened = false;
        oneTimeCheckDone = false;
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xxtank1") && getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.3F)
        {
            if(FM.AS.astateTankStates[0] == 0)
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");
                FM.AS.hitTank(shot.initiator, 0, 2);
            }
            if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
            {
                FM.AS.hitTank(shot.initiator, 0, 2);
                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
            }
        } else
        {
            super.hitBone(s, shot, point3d);
        }
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 19: // '\023'
            FM.Gears.hitCentreGear();
            break;
        }
        return super.cutFM(i, j, actor);
    }

    public void moveCockpitDoor(float f)
    {
        if(bailingOut && f >= 1.0F && !canopyForward)
        {
            canopyForward = true;
            FM.CT.forceCockpitDoor(0.0F);
            FM.AS.setCockpitDoor(this, 1);
        } else
        if(canopyForward)
        {
            hierMesh().chunkSetAngles("Blister2_D0", 0.0F, 160F * f, 0.0F);
            if(f >= 1.0F)
            {
                okToJump = true;
                super.hitDaSilk();
            }
        } else
        {
            com.maddox.il2.objects.air.Aircraft.xyz[0] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.ypr[0] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.ypr[1] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.ypr[2] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.xyz[1] = f * 0.548F;
            hierMesh().chunkSetLocate("Blister1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        }
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    public void hitDaSilk()
    {
        if(okToJump)
            super.hitDaSilk();
        else
        if(FM.isPlayers() || isNetPlayer())
        {
            if((double)FM.CT.getCockpitDoor() == 1.0D && !bailingOut)
            {
                bailingOut = true;
                okToJump = true;
                canopyForward = true;
                super.hitDaSilk();
            }
        } else
        if(!FM.AS.isPilotDead(0))
            if((double)FM.CT.getCockpitDoor() < 1.0D && !bailingOut)
            {
                bailingOut = true;
                FM.AS.setCockpitDoor(this, 1);
            } else
            if((double)FM.CT.getCockpitDoor() == 1.0D && !bailingOut)
            {
                bailingOut = true;
                okToJump = true;
                canopyForward = true;
                super.hitDaSilk();
            }
        if(!sideDoorOpened && FM.AS.bIsAboutToBailout && !FM.AS.isPilotDead(0))
        {
            sideDoorOpened = true;
            FM.CT.forceCockpitDoor(0.0F);
            FM.AS.setCockpitDoor(this, 1);
        }
    }

    public void moveGear(float f)
    {
        super.moveGear(f);
        if(f > 0.5F)
        {
            hierMesh().chunkSetAngles("GearWireR1_D0", com.maddox.il2.objects.air.Aircraft.cvt(f, 0.5F, 1.0F, 14.5F, -8F), com.maddox.il2.objects.air.Aircraft.cvt(f, 0.5F, 1.0F, 44F, 62.5F), 0.0F);
            hierMesh().chunkSetAngles("GearWireL1_D0", com.maddox.il2.objects.air.Aircraft.cvt(f, 0.5F, 1.0F, -14.5F, 8F), com.maddox.il2.objects.air.Aircraft.cvt(f, 0.5F, 1.0F, -44F, -62.5F), 0.0F);
        } else
        if(f > 0.25F)
        {
            hierMesh().chunkSetAngles("GearWireR1_D0", com.maddox.il2.objects.air.Aircraft.cvt(f, 0.25F, 0.5F, 33F, 14.5F), com.maddox.il2.objects.air.Aircraft.cvt(f, 0.25F, 0.5F, 38F, 44F), 0.0F);
            hierMesh().chunkSetAngles("GearWireL1_D0", com.maddox.il2.objects.air.Aircraft.cvt(f, 0.25F, 0.5F, -33F, -14.5F), com.maddox.il2.objects.air.Aircraft.cvt(f, 0.25F, 0.5F, -38F, -44F), 0.0F);
        } else
        {
            hierMesh().chunkSetAngles("GearWireR1_D0", com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 0.25F, 0.0F, 33F), com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 0.25F, 0.0F, 38F), 0.0F);
            hierMesh().chunkSetAngles("GearWireL1_D0", com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 0.25F, 0.0F, -33F), com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 0.25F, 0.0F, -38F), 0.0F);
        }
        if(f > 0.5F)
        {
            hierMesh().chunkVisible("GearWireR2_D0", true);
            hierMesh().chunkVisible("GearWireL2_D0", true);
        } else
        {
            hierMesh().chunkVisible("GearWireR2_D0", false);
            hierMesh().chunkVisible("GearWireL2_D0", false);
        }
    }

    protected void moveAileron(float f)
    {
        aileronsAngle = f;
        hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -30F * f - flaperonAngle, 0.0F);
        hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -30F * f + flaperonAngle, 0.0F);
    }

    protected void moveFlap(float f)
    {
        flaperonAngle = f * 17F;
        hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -30F * aileronsAngle - flaperonAngle, 0.0F);
        hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -30F * aileronsAngle + flaperonAngle, 0.0F);
    }

    protected void moveFan(float f)
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            super.moveFan(f);
            float f1 = FM.CT.getAileron();
            float f2 = FM.CT.getElevator();
            hierMesh().chunkSetAngles("Stick_D0", 0.0F, 12F * f1, com.maddox.il2.objects.air.Aircraft.cvt(f2, -1F, 1.0F, -12F, 18F));
            hierMesh().chunkSetAngles("pilotarm2_d0", com.maddox.il2.objects.air.Aircraft.cvt(f1, -1F, 1.0F, 14F, -16F), 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f1, -1F, 1.0F, 6F, -8F) - (com.maddox.il2.objects.air.Aircraft.cvt(f2, -1F, 0.0F, -36F, 0.0F) + com.maddox.il2.objects.air.Aircraft.cvt(f2, 0.0F, 1.0F, 0.0F, 32F)));
            hierMesh().chunkSetAngles("pilotarm1_d0", 0.0F, 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f1, -1F, 1.0F, -16F, 14F) + com.maddox.il2.objects.air.Aircraft.cvt(f2, -1F, 0.0F, -62F, 0.0F) + com.maddox.il2.objects.air.Aircraft.cvt(f2, 0.0F, 1.0F, 0.0F, 44F));
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(!oneTimeCheckDone && !FM.isPlayers() && !isNetPlayer() && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
        {
            oneTimeCheckDone = true;
            if(com.maddox.il2.ai.World.cur().camouflage == 1)
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.25F)
                {
                    FM.CT.cockpitDoorControl = 1.0F;
                    FM.AS.setCockpitDoor(this, 1);
                }
            } else
            if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.5F)
            {
                FM.CT.cockpitDoorControl = 1.0F;
                FM.AS.setCockpitDoor(this, 1);
            }
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
            hierMesh().chunkVisible("Head1_D1", true);
            hierMesh().chunkVisible("pilotarm2_d0", false);
            hierMesh().chunkVisible("pilotarm1_d0", false);
            break;
        }
    }

    public void doRemoveBodyFromPlane(int i)
    {
        super.doRemoveBodyFromPlane(i);
        hierMesh().chunkVisible("pilotarm2_d0", false);
        hierMesh().chunkVisible("pilotarm1_d0", false);
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        hierMesh().chunkVisible("GearWireR1_D0", true);
        hierMesh().chunkVisible("GearWireL1_D0", true);
    }

    public void missionStarting()
    {
        super.missionStarting();
        hierMesh().chunkVisible("pilotarm2_d0", true);
        hierMesh().chunkVisible("pilotarm1_d0", true);
    }

    public void prepareCamouflage()
    {
        super.prepareCamouflage();
        hierMesh().chunkVisible("pilotarm2_d0", true);
        hierMesh().chunkVisible("pilotarm1_d0", true);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private boolean bailingOut;
    private boolean canopyForward;
    private boolean okToJump;
    private float flaperonAngle;
    private float aileronsAngle;
    private boolean sideDoorOpened;
    private boolean oneTimeCheckDone;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "I-16");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/I-16type5(multi)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFCSPar07());
        com.maddox.rts.Property.set(class1, "meshName_ru", "3DO/Plane/I-16type5/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ru", new PaintSchemeFCSPar07());
        com.maddox.rts.Property.set(class1, "yearService", 1935F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1942F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/I-16type5.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitI_16TYPE5.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.82595F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 3, 3, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev07", "_ExternalDev08"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASk 900", "MGunShKASk 900", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x50kg", new java.lang.String[] {
            "MGunShKASk 900", "MGunShKASk 900", "BombGunFAB50 1", "BombGunFAB50 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x100kg", new java.lang.String[] {
            "MGunShKASk 900", "MGunShKASk 900", "BombGunFAB100 1", "BombGunFAB100 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "PV-1", new java.lang.String[] {
            "MGunPV1 900", "MGunPV1 900", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
