// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitG4M2E_Bombardier.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, G4M, Cockpit, Aircraft

public class CockpitG4M2E_Bombardier extends com.maddox.il2.objects.air.CockpitPilot
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            float f = ((com.maddox.il2.objects.air.G4M)aircraft()).fSightCurForwardAngle;
            float f1 = ((com.maddox.il2.objects.air.G4M)aircraft()).fSightCurSideslip;
            mesh.chunkSetAngles("BlackBox", 0.0F, -f1, f);
            if(bEntered)
            {
                com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
                hookpilot.setSimpleAimOrient(aAim + f1, tAim + f, 0.0F);
            }
            mesh.chunkSetAngles("Turret1A", 0.0F, -((com.maddox.il2.objects.sounds.SndAircraft) (aircraft())).FM.turret[0].tu[0], 0.0F);
            mesh.chunkSetAngles("Turret1B", 0.0F, ((com.maddox.il2.objects.sounds.SndAircraft) (aircraft())).FM.turret[0].tu[1], 0.0F);
            return true;
        }

        Interpolater()
        {
        }
    }


    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().chunkVisible("NoseAXX_D0", false);
            ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().chunkVisible("Pilot3_D0", false);
            ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().chunkVisible("Pilot3_D1", false);
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.doAim(false);
            ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().chunkVisible("CF_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        if(!((com.maddox.il2.objects.air.Cockpit)this).isFocused())
        {
            return;
        } else
        {
            ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().chunkVisible("NoseAXX_D0", ((com.maddox.il2.objects.air.Cockpit)this).aircraft().isChunkAnyDamageVisible("CF_D"));
            ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().chunkVisible("Pilot3_D0", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).FM)).AS.astatePilotStates[2] < 95);
            ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().chunkVisible("Pilot3_D1", ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).FM)).AS.astatePilotStates[2] > 95 && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).FM)).AS.astateBailoutStep < 12);
            ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().chunkVisible("CF_D0", true);
            leave();
            super.doFocusLeave();
            return;
        }
    }

    private void enter()
    {
        saveFov = com.maddox.il2.game.Main3D.FOVX;
        com.maddox.rts.CmdEnv.top().exec("fov 23.913");
        com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
        com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
        if(hookpilot.isPadlock())
            hookpilot.stopPadlock();
        hookpilot.doAim(true);
        hookpilot.setSimpleUse(true);
        hookpilot.setSimpleAimOrient(aAim, tAim, 0.0F);
        com.maddox.rts.HotKeyEnv.enable("PanView", false);
        com.maddox.rts.HotKeyEnv.enable("SnapView", false);
        bEntered = true;
    }

    private void leave()
    {
        if(!bEntered)
        {
            return;
        } else
        {
            com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
            com.maddox.rts.CmdEnv.top().exec("fov " + saveFov);
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.doAim(false);
            hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
            hookpilot.setSimpleUse(false);
            boolean flag = com.maddox.rts.HotKeyEnv.isEnabled("aircraftView");
            com.maddox.rts.HotKeyEnv.enable("PanView", flag);
            com.maddox.rts.HotKeyEnv.enable("SnapView", flag);
            bEntered = false;
            return;
        }
    }

    public void destroy()
    {
        ((com.maddox.il2.objects.air.Cockpit)this).destroy();
        leave();
    }

    public void doToggleAim(boolean flag)
    {
        if(!((com.maddox.il2.objects.air.Cockpit)this).isFocused())
            return;
        if(((com.maddox.il2.objects.air.CockpitPilot)this).isToggleAim() == flag)
            return;
        if(flag)
            enter();
        else
            leave();
    }

    public CockpitG4M2E_Bombardier()
    {
        super("3DO/Cockpit/G4M2E-Bombardier/hier.him", "he111");
        bEntered = false;
        try
        {
            com.maddox.il2.engine.Loc loc = new Loc();
            com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(((com.maddox.il2.engine.Mesh) (((com.maddox.il2.objects.air.Cockpit)this).mesh)), "CAMERAAIM");
            hooknamed.computePos(((com.maddox.il2.engine.Actor) (this)), ((com.maddox.il2.engine.Actor)this).pos.getAbs(), loc);
            aAim = loc.getOrient().getAzimut();
            tAim = loc.getOrient().getTangage();
            kAim = loc.getOrient().getKren();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(((java.lang.Throwable) (exception)).getMessage());
            ((java.lang.Throwable) (exception)).printStackTrace();
        }
        ((com.maddox.il2.engine.Actor)this).interpPut(((com.maddox.il2.engine.Interpolate) (new Interpolater())), ((java.lang.Object) (null)), com.maddox.rts.Time.current(), ((com.maddox.rts.Message) (null)));
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bEntered)
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("zAngleMark", -((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(((com.maddox.il2.objects.air.G4M)((com.maddox.il2.objects.air.Cockpit)this).aircraft()).fSightCurForwardAngle, 7F, 140F, 0.7F, 14F), angleScale), 0.0F, 0.0F);
            boolean flag = ((com.maddox.il2.objects.air.G4M)((com.maddox.il2.objects.air.Cockpit)this).aircraft()).fSightCurReadyness > 0.93F;
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("BlackBox", true);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zReticle", flag);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zAngleMark", flag);
        } else
        {
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("BlackBox", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zReticle", false);
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkVisible("zAngleMark", false);
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
            throw new NoClassDefFoundError(((java.lang.Throwable) (classnotfoundexception)).getMessage());
        }
    }

    private static final float angleScale[] = {
        -38.5F, 16.5F, 41.5F, 52.5F, 59.25F, 64F, 67F, 70F, 72F, 73.25F, 
        75F, 76.5F, 77F, 78F, 79F, 80F
    };
    private float saveFov;
    private float aAim;
    private float tAim;
    private float kAim;
    private boolean bEntered;

    static 
    {
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitG4M2E_Bombardier.class)))), "astatePilotIndx", 0);
    }



}
