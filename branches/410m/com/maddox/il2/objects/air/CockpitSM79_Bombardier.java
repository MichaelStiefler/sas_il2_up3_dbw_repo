// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitSM79_Bombardier.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, SM79, Aircraft, Cockpit

public class CockpitSM79_Bombardier extends com.maddox.il2.objects.air.CockpitPilot
{
    private class Variables
    {

        float altimeter;
        float rudderTrim;
        com.maddox.il2.ai.AnglesFork azimuth;

        private Variables()
        {
            azimuth = new AnglesFork();
        }

    }

    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            if(fm != null)
            {
                setTmp = setOld;
                setOld = setNew;
                setNew = setTmp;
                setNew.altimeter = fm.getAltitude();
                setNew.rudderTrim = fm.CT.getTrimRudderControl();
                setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), 90F + fm.Or.azimut());
            }
            float f = ((com.maddox.il2.objects.air.SM79)aircraft()).fSightCurForwardAngle;
            float f1 = ((com.maddox.il2.objects.air.SM79)aircraft()).fSightCurSideslip;
            if(bEntered)
            {
                com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
                hookpilot.setSimpleAimOrient(aAim + f1, tAim + f, 0.0F);
                sideSlipRad = java.lang.Math.toRadians(f1);
                sinSideSlip = java.lang.Math.sin(sideSlipRad);
                cosSideSlip = java.lang.Math.cos(sideSlipRad);
                cameraPoint.x = -3.8826999999999998D - 0.17085D * (sinSideSlip * cos35 + (1.0D - cosSideSlip) * sin35);
                cameraPoint.y = -0.34499999999999997D + 0.17085D * (sinSideSlip * sin35 + (1.0D - cosSideSlip) * cos35);
                cameraPoint.z = -0.46929999999999999D;
                hookpilot.setAim(cameraPoint);
            }
            return true;
        }

        private double sinSideSlip;
        private double cosSideSlip;
        private double sin35;
        private double cos35;
        private double sideSlipRad;

        Interpolater()
        {
            sin35 = 0.57572000000000001D;
            cos35 = 0.81764999999999999D;
        }
    }


    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.doAim(false);
            ((com.maddox.il2.objects.air.SM79)fm.actor).bPitUnfocused = false;
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
            aircraft().hierMesh().chunkVisible("Gambali_D0", false);
            if(aircraft().thisWeaponsName.startsWith("12") || aircraft().thisWeaponsName.startsWith("6"))
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
            if(firstEnter)
            {
                if(aircraft().thisWeaponsName.startsWith("1x"))
                {
                    mesh.chunkVisible("Stand", false);
                    mesh.chunkVisible("Cylinder", false);
                    mesh.chunkVisible("Support", false);
                    mesh.chunkVisible("Reticle1", false);
                    mesh.chunkVisible("Reticle2", false);
                    mesh.chunkVisible("ZCursor1", false);
                    mesh.chunkVisible("ZCursor2", false);
                }
                firstEnter = false;
            }
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
            aircraft().hierMesh().chunkVisible("Turret1B_D0", false);
            aircraft().hierMesh().chunkVisible("Turret2B_D0", false);
            aircraft().hierMesh().chunkVisible("Turret3B_D0", false);
            aircraft().hierMesh().chunkVisible("Turret4B_D0", false);
            aircraft().hierMesh().chunkVisible("Gambali_D0", false);
            for(int k = 1; k <= 12; k++)
                aircraft().hierMesh().chunkVisible("Bomb100Kg" + k + "_D0", false);

            for(int l = 1; l <= 12; l++)
                aircraft().hierMesh().chunkVisible("Bomb50Kg" + l + "_D0", false);

            for(int i1 = 1; i1 <= 5; i1++)
                aircraft().hierMesh().chunkVisible("Bomb250Kg" + i1 + "_D0", false);

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
            aircraft().hierMesh().chunkVisible("Interior2_D0", flag);
            aircraft().hierMesh().chunkVisible("Interior1_D0", true);
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
            aircraft().hierMesh().chunkVisible("Gambali_D0", flag);
            aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", !mesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));
            aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", mesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));
            aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", !mesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));
            aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", mesh.isChunkVisible("Tur2_DoorR_open_Int_D0"));
            aircraft().hierMesh().chunkVisible("Tur2_Door1_D0", true);
            aircraft().hierMesh().chunkVisible("Tur2_Door2_D0", true);
            aircraft().hierMesh().chunkVisible("Tur2_Door3_D0", true);
            mesh.chunkVisible("Tur2_DoorR_open_Int_D0", false);
            if(aircraft().thisWeaponsName.startsWith("12") || aircraft().thisWeaponsName.startsWith("6"))
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
            leave();
            super.doFocusLeave();
        }
    }

    private void enter()
    {
        saveFov = com.maddox.il2.game.Main3D.FOVX;
        com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
        if(hookpilot.isPadlock())
            hookpilot.stopPadlock();
        hookpilot.doAim(true);
        hookpilot.setSimpleUse(true);
        hookpilot.setSimpleAimOrient(aAim, tAim, 0.0F);
        com.maddox.rts.HotKeyEnv.enable("PanView", false);
        com.maddox.rts.HotKeyEnv.enable("SnapView", false);
        bEntered = true;
        bombToDrop = 0;
        dropTime = 0.0F;
    }

    private void leave()
    {
        if(bEntered)
        {
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.doAim(false);
            hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
            hookpilot.setSimpleUse(false);
            boolean flag = com.maddox.rts.HotKeyEnv.isEnabled("aircraftView");
            com.maddox.rts.HotKeyEnv.enable("PanView", flag);
            com.maddox.rts.HotKeyEnv.enable("SnapView", flag);
            bEntered = false;
        }
    }

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
                    mydebugcockpit("BOMB DROPPED!!! ");
                    ((com.maddox.il2.objects.air.SM79)fm.actor).numBombsOld = i;
                    bombToDrop = i + 1;
                    dropTime = 0.0F;
                    mydebugcockpit("Bomb to drop: " + bombToDrop);
                }
                if(aircraft().thisWeaponsName.startsWith("12x1"))
                {
                    for(int k = 1; k < i + 1; k++)
                        mesh.chunkVisible("Bomb100Kg" + k, true);

                    for(int l = i + 1; l <= 12; l++)
                        mesh.chunkVisible("Bomb100Kg" + l, false);

                }
                if(aircraft().thisWeaponsName.startsWith("12x5"))
                {
                    for(int i1 = 1; i1 < i + 1; i1++)
                        mesh.chunkVisible("Bomb50Kg" + i1, true);

                    for(int j1 = i + 1; j1 <= 12; j1++)
                        mesh.chunkVisible("Bomb50Kg" + j1, false);

                }
                if(aircraft().thisWeaponsName.startsWith("6"))
                {
                    for(int k1 = 1; k1 < i + 1; k1++)
                        mesh.chunkVisible("Bomb100Kg" + k1, true);

                    for(int l1 = i + 1; l1 <= 6; l1++)
                        mesh.chunkVisible("Bomb100Kg" + l1, false);

                }
                if(aircraft().thisWeaponsName.startsWith("5"))
                {
                    for(int i2 = 1; i2 < i + 1; i2++)
                        mesh.chunkVisible("Bomb250Kg" + i2, true);

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
            mydebugcockpit("drawFallingBomb Drop time = " + dropTime);
            dropTime += 0.03F;
            if(aircraft().thisWeaponsName.startsWith("12x1"))
            {
                mesh.chunkVisible("Bomb100Kg" + bombToDrop, true);
                resetYPRmodifier();
                com.maddox.il2.objects.air.Cockpit.xyz[2] = -dropTime;
                mesh.chunkSetLocate("Bomb100Kg" + bombToDrop, com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
                if(dropTime >= 0.4F)
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
                if(dropTime >= 0.4F)
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
                if(dropTime >= 0.4F)
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
                if(dropTime >= 0.5F)
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
                if(dropTime >= 0.5F)
                {
                    bombToDrop = 0;
                    dropTime = 0.0F;
                }
            }
        }
    }

    public void destroy()
    {
        super.destroy();
        leave();
    }

    public void doToggleAim(boolean flag)
    {
        if(isFocused() && isToggleAim() != flag)
            if(flag)
                enter();
            else
                leave();
    }

    public CockpitSM79_Bombardier()
    {
        super("3DO/Cockpit/SM79Bombardier/hier.him", "he111");
        firstEnter = true;
        cameraPoint = new Point3d();
        bEntered = false;
        bombToDrop = 0;
        dropTime = 0.0F;
        setOld = new Variables();
        setNew = new Variables();
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        try
        {
            com.maddox.il2.engine.Loc loc = new Loc();
            com.maddox.il2.engine.HookNamed hooknamed1 = new HookNamed(mesh, "CAMERAAIM");
            hooknamed1.computePos(this, pos.getAbs(), loc);
            aAim = loc.getOrient().getAzimut();
            tAim = loc.getOrient().getTangage();
            kAim = loc.getOrient().getKren();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(mesh, "LAMPHOOK1");
        com.maddox.il2.engine.Loc loc1 = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc1);
        light1 = new LightPointActor(new LightPoint(), loc1.getPoint());
        light1.light.setColor(109F, 99F, 90F);
        light1.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LAMPHOOK1", light1);
        cockpitNightMats = (new java.lang.String[] {
            "Panel", "Needles"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
        {
            light1.light.setEmit(0.0032F, 7.2F);
            setNightMats(true);
        } else
        {
            light1.light.setEmit(0.0F, 0.0F);
            setNightMats(false);
        }
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        reflectPlaneToModel();
        resetYPRmodifier();
        aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", false);
        aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", false);
        aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", false);
        aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", false);
        float f1 = fm.CT.getCockpitDoor();
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
            f2 = 10F * f1;
            mesh.chunkSetAngles("Tur2_DoorL_Int_D0", 0.0F, -f2, 0.0F);
            mesh.chunkSetAngles("Tur2_DoorR_Int_D0", 0.0F, f2, 0.0F);
        } else
        {
            mesh.chunkVisible("Tur2_DoorR_Int_D0", false);
            mesh.chunkVisible("Tur2_DoorR_open_Int_D0", true);
            mesh.chunkVisible("Tur2_DoorL_Int_D0", false);
            mesh.chunkSetAngles("Tur2_Door1_Int_D0", 0.0F, -13.8F, 0.0F);
            mesh.chunkSetAngles("Tur2_Door2_Int_D0", 0.0F, -8.8F, 0.0F);
            mesh.chunkSetAngles("Tur2_Door3_Int_D0", 0.0F, -3.1F, 0.0F);
        }
        float f3 = interp(setNew.altimeter, setOld.altimeter, f) * 0.072F;
        mesh.chunkSetAngles("Z_Altimeter", 0.0F, f3, 0.0F);
        mesh.chunkSetAngles("Z_Speedometer", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 460F, 0.0F, 23F), speedometerScale), 0.0F);
        mesh.chunkSetAngles("z_Hour", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        mesh.chunkSetAngles("z_Minute", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("z_Second", 0.0F, cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("Zturret3A", 0.0F, -fm.turret[2].tu[0], 0.0F);
        mesh.chunkSetAngles("Zturret3B", 0.0F, fm.turret[2].tu[1], 0.0F);
        mesh.chunkSetAngles("Zturret2A", 0.0F, -fm.turret[1].tu[0], 0.0F);
        mesh.chunkSetAngles("Zturret2B", 0.0F, fm.turret[1].tu[1], 0.0F);
        mesh.chunkSetAngles("Zturret4A", 0.0F, -fm.turret[3].tu[0], 0.0F);
        mesh.chunkSetAngles("Zturret4B", 0.0F, fm.turret[3].tu[1], 0.0F);
        mesh.chunkSetAngles("TurretA", 0.0F, -fm.turret[0].tu[0], 0.0F);
        mesh.chunkSetAngles("TurretB", 0.0F, fm.turret[0].tu[1], 0.0F);
        mesh.chunkSetAngles("Z_Compass1", 0.0F, -setNew.azimuth.getDeg(f), 0.0F);
        mesh.chunkSetAngles("Z_TurnBank2", 0.0F, cvt(getBall(8D), -8F, 8F, -28F, 28F), 0.0F);
        mesh.chunkSetAngles("ZRudderTrim", 0.0F, interp(setNew.rudderTrim, setOld.rudderTrim, f) * 360F, 0.0F);
        float f4 = fm.CT.getCockpitDoor();
        aircraft().hierMesh().chunkVisible("Tur1_DoorL_D0", false);
        aircraft().hierMesh().chunkVisible("Tur1_DoorR_D0", false);
        float f5 = -28F * f4;
        float f6 = cvt(f4, 0.0F, 1.0F, 0.0F, -20F);
        float f7 = cvt(f4, 0.5F, 1.0F, 0.0F, -15F);
        mesh.chunkSetAngles("Tur1_DoorL_Int_D0", -f6, -f5, -f7);
        mesh.chunkSetAngles("Tur1_DoorR_Int_D0", f6, f5, -f7);
        if(!aircraft().thisWeaponsName.startsWith("1x"))
        {
            resetYPRmodifier();
            float f8 = fm.Or.getPitch();
            if(f8 > 360F)
                f8 -= 360F;
            f8 *= 0.00872664F;
            float f10 = ((com.maddox.il2.objects.air.SM79)aircraft()).fSightSetForwardAngle - (float)java.lang.Math.toRadians(f8);
            float f11 = (float)(0.16915999352931976D * java.lang.Math.tan(f10));
            if(f11 < 0.032F)
                f11 = 0.032F;
            else
            if(f11 > 0.21F)
                f11 = 0.21F;
            f11 *= 0.85F;
            float f12 = f11 * 0.667F;
            com.maddox.il2.objects.air.Cockpit.xyz[0] = f11;
            mesh.chunkSetLocate("ZCursor1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            com.maddox.il2.objects.air.Cockpit.xyz[0] = f12;
            mesh.chunkSetLocate("ZCursor2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            mesh.chunkSetAngles("Cylinder", 0.0F, ((com.maddox.il2.objects.air.SM79)aircraft()).fSightCurSideslip, 0.0F);
        }
        drawBombsInt();
        drawFallingBomb(f);
        float f9 = ((com.maddox.il2.objects.air.SM79)fm.actor).bayDoorAngle;
        if(aircraft().thisWeaponsName.startsWith("12"))
        {
            mesh.chunkSetAngles("TypewriterLever01", 0.0F, 55F * f9, 0.0F);
            mesh.chunkSetAngles("TypewriterLever02", 0.0F, 55F * f9, 0.0F);
            mesh.chunkSetAngles("TypewriterLever03", 0.0F, 55F * f9, 0.0F);
            mesh.chunkSetAngles("TypewriterLever04", 0.0F, 55F * f9, 0.0F);
            mesh.chunkSetAngles("TypewriterLever05", 0.0F, 55F * f9, 0.0F);
            mesh.chunkSetAngles("TypewriterLever06", 0.0F, 55F * f9, 0.0F);
            mesh.chunkSetAngles("TypewriterLever07", 0.0F, 55F * f9, 0.0F);
            mesh.chunkSetAngles("TypewriterLever08", 0.0F, 55F * f9, 0.0F);
            mesh.chunkSetAngles("TypewriterLever09", 0.0F, 55F * f9, 0.0F);
            mesh.chunkSetAngles("TypewriterLever10", 0.0F, 55F * f9, 0.0F);
            mesh.chunkSetAngles("TypewriterLever11", 0.0F, 55F * f9, 0.0F);
            mesh.chunkSetAngles("TypewriterLever12", 0.0F, 55F * f9, 0.0F);
        }
        if(aircraft().thisWeaponsName.startsWith("6"))
        {
            mesh.chunkSetAngles("TypewriterLever01", 0.0F, 55F * f9, 0.0F);
            mesh.chunkSetAngles("TypewriterLever02", 0.0F, 55F * f9, 0.0F);
            mesh.chunkSetAngles("TypewriterLever03", 0.0F, 55F * f9, 0.0F);
            mesh.chunkSetAngles("TypewriterLever04", 0.0F, 55F * f9, 0.0F);
            mesh.chunkSetAngles("TypewriterLever05", 0.0F, 55F * f9, 0.0F);
            mesh.chunkSetAngles("TypewriterLever06", 0.0F, 55F * f9, 0.0F);
        }
        if(aircraft().thisWeaponsName.startsWith("5"))
        {
            mesh.chunkSetAngles("TypewriterLever01", 0.0F, 55F * f9, 0.0F);
            mesh.chunkSetAngles("TypewriterLever02", 0.0F, 55F * f9, 0.0F);
            mesh.chunkSetAngles("TypewriterLever03", 0.0F, 55F * f9, 0.0F);
            mesh.chunkSetAngles("TypewriterLever04", 0.0F, 55F * f9, 0.0F);
            mesh.chunkSetAngles("TypewriterLever05", 0.0F, 55F * f9, 0.0F);
        }
        if(aircraft().thisWeaponsName.startsWith("2"))
        {
            mesh.chunkSetAngles("TypewriterLever02", 0.0F, 55F * f9, 0.0F);
            mesh.chunkSetAngles("TypewriterLever03", 0.0F, 55F * f9, 0.0F);
            mesh.chunkSetAngles("TypewriterLever06", 0.0F, 55F * f9, 0.0F);
            mesh.chunkSetAngles("TypewriterLever07", 0.0F, 55F * f9, 0.0F);
            mesh.chunkVisible("Bridge1", true);
            mesh.chunkVisible("Bridge2", true);
        }
        if(aircraft().thisWeaponsName.endsWith("drop"))
        {
            int i = 0;
            if(fm.CT.Weapons[3] != null)
            {
                for(int j = 0; j < fm.CT.Weapons[3].length; j++)
                    if(fm.CT.Weapons[3][j] != null)
                        i += fm.CT.Weapons[3][j].countBullets();

            }
            if(aircraft().thisWeaponsName.startsWith("12"))
            {
                for(int k = 1; k <= 12 - i; k++)
                    if(k < 10)
                        mesh.chunkSetAngles("Typewriter_Key0" + k, 0.0F, 25F, 0.0F);
                    else
                        mesh.chunkSetAngles("Typewriter_Key" + k, 0.0F, 25F, 0.0F);

            }
            if(aircraft().thisWeaponsName.startsWith("6"))
            {
                for(int l = 1; l <= 6 - i; l++)
                    mesh.chunkSetAngles("Typewriter_Key" + l, 0.0F, 25F, 0.0F);

            }
            if(aircraft().thisWeaponsName.startsWith("5"))
            {
                for(int i1 = 1; i1 <= 5 - i; i1++)
                    mesh.chunkSetAngles("Typewriter_Key0" + i1, 0.0F, 25F, 0.0F);

            }
            if(aircraft().thisWeaponsName.startsWith("2"))
            {
                if(i == 0)
                {
                    mesh.chunkSetAngles("Typewriter_Key02", 0.0F, 25F, 0.0F);
                    mesh.chunkSetAngles("Typewriter_Key03", 0.0F, 25F, 0.0F);
                    mesh.chunkSetAngles("Typewriter_Key06", 0.0F, 25F, 0.0F);
                    mesh.chunkSetAngles("Typewriter_Key07", 0.0F, 25F, 0.0F);
                }
                if(i == 1)
                {
                    mesh.chunkSetAngles("Typewriter_Key02", 0.0F, 25F, 0.0F);
                    mesh.chunkSetAngles("Typewriter_Key03", 0.0F, 25F, 0.0F);
                }
            }
        }
    }

    protected void mydebugcockpit(java.lang.String s)
    {
        java.lang.System.out.println(s);
    }

    public void reflectCockpitState()
    {
        if(fm.AS.astateCockpitState != 0)
        {
            if((fm.AS.astateCockpitState & 0x10) != 0)
                mesh.chunkVisible("XGlassHoles3", true);
            if((fm.AS.astateCockpitState & 0x20) != 0)
                mesh.chunkVisible("XGlassHoles3", true);
        }
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Glass2"));
        mesh.materialReplace("Glass2", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D0o"));
        mesh.materialReplace("Gloss2D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt1D0o"));
        mesh.materialReplace("Matt1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Pilot1"));
        mesh.materialReplace("Pilot1", mat);
    }

    protected void reflectPlaneToModel()
    {
    }

    private static final float speedometerScale[] = {
        0.0F, 10F, 20F, 30F, 50F, 68F, 88F, 109F, 126F, 142F, 
        159F, 176F, 190F, 206F, 220F, 238F, 253F, 270F, 285F, 300F, 
        312F, 325F, 337F, 350F, 360F
    };
    private boolean firstEnter;
    private boolean bTurrVisible;
    private float saveFov;
    private float aAim;
    private float tAim;
    private com.maddox.JGP.Point3d cameraPoint;
    private float kAim;
    private boolean bEntered;
    private int bombToDrop;
    private float dropTime;
    private com.maddox.il2.objects.air.Variables setOld;
    private com.maddox.il2.objects.air.Variables setNew;
    private com.maddox.il2.objects.air.Variables setTmp;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;
    private com.maddox.il2.engine.LightPointActor light1;
    private boolean bNeedSetUp;
    private com.maddox.il2.engine.Hook hook1;











}
