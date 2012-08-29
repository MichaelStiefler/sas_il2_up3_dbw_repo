// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitCant_Bombardier.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
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
import com.maddox.il2.game.Main3D;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, CantZ1007, Aircraft, Cockpit

public class CockpitCant_Bombardier extends com.maddox.il2.objects.air.CockpitPilot
{
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
            float f = ((com.maddox.il2.objects.air.CantZ1007)aircraft()).fSightCurForwardAngle;
            float f1 = 0.0F;
            if(bEntered)
            {
                com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
                hookpilot.setSimpleAimOrient(aAim + f1, tAim + f, 0.0F);
            }
            return true;
        }

        Interpolater()
        {
        }
    }

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


    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.doAim(false);
            ((com.maddox.il2.objects.air.CantZ1007)fm.actor).bPitUnfocused = false;
            aircraft().hierMesh().chunkVisible("Interior_D0", false);
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
            ((com.maddox.il2.objects.air.CantZ1007)fm.actor).bPitUnfocused = true;
            boolean flag = aircraft().isChunkAnyDamageVisible("Tail1_D");
            aircraft().hierMesh().chunkVisible("Interior_D0", aircraft().hierMesh().isChunkVisible("CF_D0"));
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
            hookpilot.setSimpleUse(false);
            boolean flag = com.maddox.rts.HotKeyEnv.isEnabled("aircraftView");
            com.maddox.rts.HotKeyEnv.enable("PanView", flag);
            com.maddox.rts.HotKeyEnv.enable("SnapView", flag);
            bEntered = false;
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

    public CockpitCant_Bombardier()
    {
        super("3DO/Cockpit/Cant-Bombardier/hier.him", "he111");
        firstEnter = true;
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
        float f1 = interp(setNew.altimeter, setOld.altimeter, f) * 0.072F;
        mesh.chunkSetAngles("Z_Altimeter", 0.0F, f1, 0.0F);
        mesh.chunkSetAngles("Z_Speedometer", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 460F, 0.0F, 23F), speedometerScale), 0.0F);
        mesh.chunkSetAngles("z_Hour", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        mesh.chunkSetAngles("z_Minute", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("z_Second", 0.0F, cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("Z_Compass1", 0.0F, 90F - setNew.azimuth.getDeg(f), 0.0F);
        mesh.chunkSetAngles("Z_TurnBank2", 0.0F, cvt(getBall(8D), -8F, 8F, -28F, 28F), 0.0F);
        if(!aircraft().thisWeaponsName.startsWith("1x"))
        {
            resetYPRmodifier();
            float f2 = fm.Or.getPitch();
            if(f2 > 360F)
                f2 -= 360F;
            f2 *= 0.00872664F;
            float f3 = ((com.maddox.il2.objects.air.CantZ1007)aircraft()).fSightSetForwardAngle - (float)java.lang.Math.toRadians(f2);
            float f4 = (float)(0.16915999352931976D * java.lang.Math.tan(f3));
            if(f4 < 0.032F)
                f4 = 0.032F;
            else
            if(f4 > 0.21F)
                f4 = 0.21F;
            float f5 = f4 * 0.667F;
            com.maddox.il2.objects.air.Cockpit.xyz[0] = f4;
            mesh.chunkSetLocate("ZCursor1", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            com.maddox.il2.objects.air.Cockpit.xyz[0] = f5;
            mesh.chunkSetLocate("ZCursor2", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
            mesh.chunkSetAngles("Cylinder", 0.0F, ((com.maddox.il2.objects.air.CantZ1007)aircraft()).fSightCurSideslip, 0.0F);
        }
    }

    protected void mydebugcockpit(java.lang.String s)
    {
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
    static java.lang.Class class$com$maddox$il2$objects$air$CockpitCant_Bombardier;
    private boolean bNeedSetUp;
    private com.maddox.il2.engine.Hook hook1;










}
