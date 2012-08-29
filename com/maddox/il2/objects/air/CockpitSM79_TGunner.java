// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitSM79_TGunner.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitGunner, SM79, Aircraft, Cockpit

public class CockpitSM79_TGunner extends com.maddox.il2.objects.air.CockpitGunner
{

    protected void drawBombsInt()
    {
        if(aircraft().thisWeaponsName.endsWith("drop"))
        {
            int i = 0;
            if(fm.CT.Weapons[3] != null)
            {
                for(int j = 0; j < fm.CT.Weapons[3].length; j++)
                    if(fm.CT.Weapons[3][j] != null)
                        i += fm.CT.Weapons[3][j].countBullets();

                if(i < ((com.maddox.il2.objects.air.SM79)fm.actor).numBombsOld)
                {
                    ((com.maddox.il2.objects.air.SM79)fm.actor).numBombsOld = i;
                    bombToDrop = i + 1;
                    dropTime = 0.0F;
                }
                if(aircraft().thisWeaponsName.startsWith("12x1"))
                {
                    for(int k = 1; k < i + 1; k++)
                        mesh.chunkVisible("Bomb100Kg" + k, true);

                    for(int k1 = i + 1; k1 <= 12; k1++)
                        mesh.chunkVisible("Bomb100Kg" + k1, false);

                }
                if(aircraft().thisWeaponsName.startsWith("12x5"))
                {
                    for(int l = 1; l < i + 1; l++)
                        mesh.chunkVisible("Bomb50Kg" + l, true);

                    for(int l1 = i + 1; l1 <= 12; l1++)
                        mesh.chunkVisible("Bomb50Kg" + l1, false);

                }
                if(aircraft().thisWeaponsName.startsWith("6"))
                {
                    for(int i1 = 1; i1 < i + 1; i1++)
                        mesh.chunkVisible("Bomb100Kg" + i1, true);

                    for(int i2 = i + 1; i2 <= 6; i2++)
                        mesh.chunkVisible("Bomb100Kg" + i2, false);

                }
                if(aircraft().thisWeaponsName.startsWith("5"))
                {
                    for(int j1 = 1; j1 < i + 1; j1++)
                        mesh.chunkVisible("Bomb250Kg" + j1, true);

                    for(int j2 = i + 1; j2 <= 5; j2++)
                        mesh.chunkVisible("Bomb250Kg" + j2, false);

                }
                if(aircraft().thisWeaponsName.startsWith("2"))
                {
                    if(i == 2)
                    {
                        mesh.chunkVisible("Bomb500Kg1", true);
                        mesh.chunkVisible("Bomb500Kg2", true);
                    }
                    if(i == 1)
                    {
                        mesh.chunkVisible("Bomb500Kg1", true);
                        mesh.chunkVisible("Bomb500Kg2", false);
                    }
                    if(i == 0)
                    {
                        mesh.chunkVisible("Bomb500Kg1", false);
                        mesh.chunkVisible("Bomb500Kg2", false);
                    }
                }
            }
        }
    }

    protected void drawFallingBomb(float f)
    {
        if(bombToDrop != 0)
        {
            dropTime += 0.06F * f;
            if(aircraft().thisWeaponsName.startsWith("12x1"))
            {
                mesh.chunkVisible("Bomb100Kg" + bombToDrop, true);
                resetYPRmodifier();
                com.maddox.il2.objects.air.Cockpit.xyz[2] = -dropTime;
                mesh.chunkSetLocate("Bomb100Kg" + bombToDrop, com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
                if(dropTime >= 0.6F)
                {
                    bombToDrop = 0;
                    dropTime = 0.0F;
                }
            }
            if(aircraft().thisWeaponsName.startsWith("12x5"))
            {
                mesh.chunkVisible("Bomb50Kg" + bombToDrop, true);
                resetYPRmodifier();
                com.maddox.il2.objects.air.Cockpit.xyz[2] = -dropTime;
                mesh.chunkSetLocate("Bomb50Kg" + bombToDrop, com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
                if(dropTime >= 0.6F)
                {
                    bombToDrop = 0;
                    dropTime = 0.0F;
                }
            }
            if(aircraft().thisWeaponsName.startsWith("6"))
            {
                mesh.chunkVisible("Bomb100Kg" + bombToDrop, true);
                resetYPRmodifier();
                com.maddox.il2.objects.air.Cockpit.xyz[2] = -dropTime;
                mesh.chunkSetLocate("Bomb100Kg" + bombToDrop, com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
                if(dropTime >= 0.6F)
                {
                    bombToDrop = 0;
                    dropTime = 0.0F;
                }
            }
            if(aircraft().thisWeaponsName.startsWith("5"))
            {
                mesh.chunkVisible("Bomb250Kg" + bombToDrop, true);
                resetYPRmodifier();
                com.maddox.il2.objects.air.Cockpit.xyz[2] = -dropTime;
                mesh.chunkSetLocate("Bomb250Kg" + bombToDrop, com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
                if(dropTime >= 0.6F)
                {
                    bombToDrop = 0;
                    dropTime = 0.0F;
                }
            }
            if(aircraft().thisWeaponsName.startsWith("2"))
            {
                mesh.chunkVisible("Bomb500Kg" + bombToDrop, true);
                resetYPRmodifier();
                com.maddox.il2.objects.air.Cockpit.xyz[2] = -dropTime;
                mesh.chunkSetLocate("Bomb500Kg" + bombToDrop, com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
                if(dropTime >= 0.6F)
                {
                    bombToDrop = 0;
                    dropTime = 0.0F;
                }
            }
        }
    }

    protected boolean doFocusEnter()
    {
        if(firstEnter)
        {
            matNull = aircraft().hierMesh().material(aircraft().hierMesh().materialFind("NullMat"));
            matInterior = aircraft().hierMesh().material(aircraft().hierMesh().materialFind("InteriorFake1"));
            firstEnter = false;
        }
        if(super.doFocusEnter())
        {
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.doAim(false);
            aircraft().hierMesh().materialReplace("InteriorFake", matNull);
            aircraft().hierMesh().chunkVisible("Pilot1_D0", false);
            aircraft().hierMesh().chunkVisible("Pilot1_D1", false);
            aircraft().hierMesh().chunkVisible("Pilot2_D0", false);
            aircraft().hierMesh().chunkVisible("Pilot2_D1", false);
            aircraft().hierMesh().chunkVisible("Pilot3_D0", false);
            aircraft().hierMesh().chunkVisible("Pilot3_D1", false);
            aircraft().hierMesh().chunkVisible("Pilot4_D0", false);
            aircraft().hierMesh().chunkVisible("Pilot4_D1", false);
            aircraft().hierMesh().chunkVisible("Pilot5_D0", false);
            aircraft().hierMesh().chunkVisible("Pilot5_D1", false);
            ((com.maddox.il2.objects.air.SM79)fm.actor).bPitUnfocused = false;
            mesh.chunkVisible("Tur2_DoorR_Int_D0", aircraft().hierMesh().isChunkVisible("Tur2_DoorR_D0"));
            mesh.chunkVisible("Tur2_DoorR_open_Int_D0", !aircraft().hierMesh().isChunkVisible("Tur2_DoorR_D0"));
            mesh.chunkVisible("Tur2_DoorL_Int_D0", aircraft().hierMesh().isChunkVisible("Tur2_DoorR_D0"));
            mesh.chunkVisible("Tur2_Door1_Int_D0", true);
            mesh.chunkVisible("Tur2_Door2_Int_D0", true);
            mesh.chunkVisible("Tur2_Door3_Int_D0", true);
            aircraft().hierMesh().chunkVisible("Interior2_D0", false);
            aircraft().hierMesh().chunkVisible("Interior1_D0", false);
            aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", false);
            aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", false);
            aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", false);
            aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", false);
            aircraft().hierMesh().chunkVisible("Tur2_Door1_D0", false);
            aircraft().hierMesh().chunkVisible("Tur2_Door2_D0", false);
            aircraft().hierMesh().chunkVisible("Tur2_Door3_D0", false);
            aircraft().hierMesh().chunkVisible("Tur1_DoorL_D0", false);
            aircraft().hierMesh().chunkVisible("Tur1_DoorR_D0", false);
            aircraft().hierMesh().chunkVisible("Tur1_DoorL_open_D0", false);
            aircraft().hierMesh().chunkVisible("Tur1_DoorR_open_D0", false);
            aircraft().hierMesh().chunkVisible("Turret1B_D0", false);
            aircraft().hierMesh().chunkVisible("Turret2B_D0", false);
            aircraft().hierMesh().chunkVisible("Turret3B_D0", false);
            aircraft().hierMesh().chunkVisible("Turret4B_D0", false);
            if(aircraft().thisWeaponsName.startsWith("12"))
            {
                for(int i = 1; i <= 12; i++)
                {
                    mesh.chunkVisible("BombRack" + i, true);
                    aircraft().hierMesh().chunkVisible("BombRack" + i + "_D0", false);
                }

            }
            if(aircraft().thisWeaponsName.startsWith("5"))
            {
                for(int j = 1; j <= 5; j++)
                {
                    mesh.chunkVisible("BombRack250_" + j, true);
                    aircraft().hierMesh().chunkVisible("BombRack250_" + j + "_D0", false);
                }

            }
            if(aircraft().thisWeaponsName.startsWith("2"))
            {
                mesh.chunkVisible("BombRack500_1", true);
                mesh.chunkVisible("BombRack500_2", true);
                aircraft().hierMesh().chunkVisible("BombRack500_1_D0", false);
                aircraft().hierMesh().chunkVisible("BombRack500_2_D0", false);
            }
            for(int k = 1; k <= 12; k++)
                aircraft().hierMesh().chunkVisible("Bomb100Kg" + k + "_D0", false);

            for(int l = 1; l <= 5; l++)
                aircraft().hierMesh().chunkVisible("Bomb250Kg" + l + "_D0", false);

            aircraft().hierMesh().chunkVisible("Bomb500Kg2_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        if(isFocused())
        {
            ((com.maddox.il2.objects.air.SM79)fm.actor).bPitUnfocused = true;
            boolean flag = aircraft().isChunkAnyDamageVisible("Tail1_D");
            if(!fm.AS.isPilotParatrooper(0))
            {
                aircraft().hierMesh().chunkVisible("Pilot1_D0", !((com.maddox.il2.objects.air.SM79)fm.actor).bPilot1Killed);
                aircraft().hierMesh().chunkVisible("Pilot1_D1", ((com.maddox.il2.objects.air.SM79)fm.actor).bPilot1Killed);
            }
            if(!fm.AS.isPilotParatrooper(1))
            {
                aircraft().hierMesh().chunkVisible("Pilot2_D0", !((com.maddox.il2.objects.air.SM79)fm.actor).bPilot2Killed);
                aircraft().hierMesh().chunkVisible("Pilot2_D1", ((com.maddox.il2.objects.air.SM79)fm.actor).bPilot2Killed);
            }
            if(!fm.AS.isPilotParatrooper(2))
            {
                aircraft().hierMesh().chunkVisible("Pilot3_D0", !((com.maddox.il2.objects.air.SM79)fm.actor).bPilot3Killed);
                aircraft().hierMesh().chunkVisible("Pilot3_D1", ((com.maddox.il2.objects.air.SM79)fm.actor).bPilot3Killed);
            }
            if(flag)
            {
                if(!fm.AS.isPilotParatrooper(3))
                {
                    aircraft().hierMesh().chunkVisible("Pilot4_D0", !((com.maddox.il2.objects.air.SM79)fm.actor).bPilot4Killed);
                    aircraft().hierMesh().chunkVisible("Pilot4_D1", ((com.maddox.il2.objects.air.SM79)fm.actor).bPilot4Killed);
                }
                if(!fm.AS.isPilotParatrooper(4))
                {
                    aircraft().hierMesh().chunkVisible("Pilot5_D0", !((com.maddox.il2.objects.air.SM79)fm.actor).bPilot5Killed);
                    aircraft().hierMesh().chunkVisible("Pilot5_D1", ((com.maddox.il2.objects.air.SM79)fm.actor).bPilot5Killed);
                }
            }
            aircraft().hierMesh().chunkVisible("Interior2_D0", flag);
            aircraft().hierMesh().chunkVisible("Interior1_D0", true);
            aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", !mesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));
            aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", mesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));
            aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", !mesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));
            aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", mesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));
            aircraft().hierMesh().chunkVisible("Tur2_Door1_D0", true);
            aircraft().hierMesh().chunkVisible("Tur2_Door2_D0", true);
            aircraft().hierMesh().chunkVisible("Tur2_Door3_D0", true);
            mesh.chunkVisible("Tur2_Door1_Int_D0", false);
            mesh.chunkVisible("Tur2_Door2_Int_D0", false);
            mesh.chunkVisible("Tur2_Door3_Int_D0", false);
            mesh.chunkVisible("Tur2_DoorR_open_Int_D0", false);
            float f = fm.CT.getCockpitDoor();
            if((double)f > 0.98999999999999999D)
            {
                aircraft().hierMesh().chunkVisible("Tur1_DoorL_open_D0", flag);
                aircraft().hierMesh().chunkVisible("Tur1_DoorR_open_D0", flag);
            } else
            {
                aircraft().hierMesh().chunkVisible("Tur1_DoorL_D0", flag);
                aircraft().hierMesh().chunkVisible("Tur1_DoorR_D0", flag);
            }
            aircraft().hierMesh().chunkVisible("Turret1B_D0", true);
            aircraft().hierMesh().chunkVisible("Turret2B_D0", flag);
            aircraft().hierMesh().chunkVisible("Turret3B_D0", flag);
            aircraft().hierMesh().chunkVisible("Turret4B_D0", flag);
            if(aircraft().thisWeaponsName.startsWith("12"))
            {
                for(int i = 1; i <= 12; i++)
                {
                    mesh.chunkVisible("BombRack" + i, false);
                    aircraft().hierMesh().chunkVisible("BombRack" + i + "_D0", true);
                }

            }
            if(aircraft().thisWeaponsName.startsWith("5"))
            {
                for(int j = 1; j <= 5; j++)
                {
                    mesh.chunkVisible("BombRack250_" + j, false);
                    aircraft().hierMesh().chunkVisible("BombRack250_" + j + "_D0", true);
                }

            }
            if(aircraft().thisWeaponsName.startsWith("2"))
            {
                mesh.chunkVisible("BombRack500_1", false);
                mesh.chunkVisible("BombRack500_2", false);
                aircraft().hierMesh().chunkVisible("BombRack500_1_D0", true);
                aircraft().hierMesh().chunkVisible("BombRack500_2_D0", true);
            }
            super.doFocusLeave();
        }
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        drawBombsInt();
        drawFallingBomb(f);
        aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", false);
        aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", false);
        aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", false);
        aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", false);
        float f1 = fm.CT.getCockpitDoor();
        aircraft().hierMesh().chunkVisible("Tur1_DoorL_D0", false);
        aircraft().hierMesh().chunkVisible("Tur1_DoorR_D0", false);
        aircraft().hierMesh().chunkVisible("Tur1_DoorL_open_D0", false);
        aircraft().hierMesh().chunkVisible("Tur1_DoorR_open_D0", false);
        mesh.chunkSetAngles("TurDoorLever", 0.0F, -40F * (pictLever = 0.89F * pictLever + 0.11F * fm.CT.cockpitDoorControl), 0.0F);
        if(f1 < 0.99F)
        {
            if(!mesh.isChunkVisible("Tur2_DoorR_Int_D0"))
            {
                mesh.chunkVisible("Tur2_DoorR_Int_D0", true);
                mesh.chunkVisible("Tur2_DoorR_open_Int_D0", false);
                mesh.chunkVisible("Tur2_DoorL_Int_D0", true);
            }
            float f2 = 13.8F * f1;
            mesh.chunkSetAngles("Tur2_Door1_Int_D0", 0.0F, -f2, 0.0F);
            f2 = 8.8F * f1;
            mesh.chunkSetAngles("Tur2_Door2_Int_D0", 0.0F, -f2, 0.0F);
            f2 = 3.1F * f1;
            mesh.chunkSetAngles("Tur2_Door3_Int_D0", 0.0F, -f2, 0.0F);
            f2 = 15F * f1;
            float f4 = cvt(f1, 0.2F, 1.0F, 0.0F, -16F);
            float f5 = cvt(f1, 0.4F, 1.0F, 0.0F, 7.5F);
            mesh.chunkSetAngles("Tur2_DoorL_Int_D0", -f4, -f2, -f5);
            mesh.chunkSetAngles("Tur2_DoorR_Int_D0", f4, f2, -f5);
        } else
        {
            mesh.chunkVisible("Tur2_DoorR_Int_D0", false);
            mesh.chunkVisible("Tur2_DoorR_open_Int_D0", true);
            mesh.chunkVisible("Tur2_DoorL_Int_D0", false);
            mesh.chunkSetAngles("Tur2_Door1_Int_D0", 0.0F, -13.8F, 0.0F);
            mesh.chunkSetAngles("Tur2_Door2_Int_D0", 0.0F, -8.8F, 0.0F);
            mesh.chunkSetAngles("Tur2_Door3_Int_D0", 0.0F, -3.1F, 0.0F);
        }
        float f3 = -20F * f1;
        mesh.chunkSetAngles("Tur1_DoorL_Int_D0", 0.0F, f3, 0.0F);
        mesh.chunkSetAngles("Tur1_DoorR_Int_D0", 0.0F, -f3, 0.0F);
        mesh.chunkSetAngles("Zturret3A", 0.0F, -fm.turret[2].tu[0], 0.0F);
        mesh.chunkSetAngles("Zturret3B", 0.0F, fm.turret[2].tu[1], 0.0F);
        mesh.chunkSetAngles("Zturret2A", 0.0F, -fm.turret[1].tu[0], 0.0F);
        mesh.chunkSetAngles("Zturret2B", 0.0F, fm.turret[1].tu[1], 0.0F);
        mesh.chunkSetAngles("Zturret4A", 0.0F, -fm.turret[3].tu[0], 0.0F);
        mesh.chunkSetAngles("Zturret4B", 0.0F, fm.turret[3].tu[1], 0.0F);
    }

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        mesh.chunkSetAngles("TurretA", 0.0F, orient.getYaw(), 0.0F);
        mesh.chunkSetAngles("TurretB", 0.0F, orient.getTangage(), 0.0F);
    }

    public void clipAnglesGun(com.maddox.il2.engine.Orient orient)
    {
        if(!isRealMode())
            return;
        if(!aiTurret().bIsOperable || fm.CT.getCockpitDoor() < 0.5F)
        {
            orient.setYPR(0.0F, 0.0F, 0.0F);
            return;
        }
        float f = orient.getYaw();
        float f1 = orient.getTangage();
        if(f < -40F)
            f = -40F;
        if(f > 40F)
            f = 40F;
        if(f1 > 70F)
            f1 = 70F;
        if(f1 < -4F)
            f1 = -4F;
        orient.setYPR(f, f1, 0.0F);
        orient.wrap();
    }

    protected void interpTick()
    {
        if(!isRealMode())
            return;
        if(emitter == null || !emitter.haveBullets() || !aiTurret().bIsOperable)
            bGunFire = false;
        fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
        if(bGunFire)
        {
            if(hook1 == null)
                hook1 = new HookNamed(aircraft(), "_12,7_02");
            doHitMasterAircraft(aircraft(), hook1, "_12,7_02");
            if(iCocking > 0)
                iCocking = 0;
            else
                iCocking = 1;
        } else
        {
            iCocking = 0;
        }
        if(emitter != null)
        {
            boolean flag = emitter.countBullets() % 2 == 0;
            mesh.chunkVisible("TurretB-Bullet0", flag);
            mesh.chunkVisible("TurretB-Bullet1", !flag);
        }
    }

    public void doGunFire(boolean flag)
    {
        if(!isRealMode())
            return;
        if(emitter == null || !emitter.haveBullets() || !aiTurret().bIsOperable)
            bGunFire = false;
        else
            bGunFire = flag;
        fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D1o"));
        mesh.materialReplace("Gloss1D1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D2o"));
        mesh.materialReplace("Gloss1D2o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D0o"));
        mesh.materialReplace("Gloss2D0o", mat);
    }

    public CockpitSM79_TGunner()
    {
        super("3DO/Cockpit/SM79Tgun/hier.him", "he111_gunner");
        bombToDrop = 0;
        dropTime = 0.0F;
        firstEnter = true;
        w = new Vector3f();
        bNeedSetUp = true;
        hook1 = null;
        iCocking = 0;
        iOldVisDrums = 3;
        iNewVisDrums = 3;
        pictLever = 0.0F;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private int bombToDrop;
    private float dropTime;
    private boolean firstEnter;
    private com.maddox.il2.engine.Mat matNull;
    private com.maddox.il2.engine.Mat matInterior;
    public com.maddox.JGP.Vector3f w;
    private boolean bNeedSetUp;
    private com.maddox.il2.engine.Hook hook1;
    private int iCocking;
    private int iOldVisDrums;
    private int iNewVisDrums;
    private float pictLever;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitSM79_TGunner.class, "aiTuretNum", 0);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitSM79_TGunner.class, "weaponControlNum", 10);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitSM79_TGunner.class, "astatePilotIndx", 2);
    }
}
