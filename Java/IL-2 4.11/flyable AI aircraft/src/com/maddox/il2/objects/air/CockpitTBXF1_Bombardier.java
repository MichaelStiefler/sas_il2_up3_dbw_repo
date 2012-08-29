// Source File Name: CockpitTBXF1_Bombardier.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-04-06
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.*;
import com.maddox.il2.engine.*;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.*;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.*;

public class CockpitTBXF1_Bombardier extends CockpitPilot
{
    class Interpolater extends InterpolateRef
    {

        public boolean tick()
        {
        	float f = 0.0F;
        	float f1 = 0.0F;
            if (aircraft() instanceof TBF1) {
                f = ((TBF1)aircraft()).fSightCurForwardAngle;
                f1 = ((TBF1)aircraft()).fSightCurSideslip;
            }
            else if (aircraft() instanceof TBF1C) {
                f = ((TBF1C)aircraft()).fSightCurForwardAngle;
                f1 = ((TBF1C)aircraft()).fSightCurSideslip;
            }
            mesh.chunkSetAngles("BlackBox", 0.0F, -f1, f);
            if(bEntered)
            {
                HookPilot hookpilot = HookPilot.current;
                hookpilot.setInstantOrient(aAim + f1, tAim + f, 0.0F);
            }
            mesh.chunkSetAngles("TurretA", 0.0F, aircraft().FM.turret[0].tu[0], 0.0F);
            mesh.chunkSetAngles("TurretB", 0.0F, aircraft().FM.turret[0].tu[1], 0.0F);
            return true;
        }

        Interpolater()
        {
        }
    }


    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter()) {
//            saveFov = Main3D.FOVX;
//        CmdEnv.top().exec("fov 23.913");
//        Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
	        HookPilot hookpilot = HookPilot.current;
	        if(hookpilot.isPadlock())
	            hookpilot.stopPadlock();
	        hookpilot.doAim(true);
	        hookpilot.setSimpleUse(true);
	        hookpilot.setSimpleAimOrient(aAim, tAim, 0.0F);
	        HotKeyEnv.enable("PanView", false);
	        HotKeyEnv.enable("SnapView", false);
			Point3d point3d = new Point3d();
			point3d.set(0.0D, 0.0D, 0.0D);
			hookpilot.setTubeSight(point3d);
			enter();
//        bEntered = true;
			return true;
        } else {
        	return false;
        }
    }

    protected void doFocusLeave()
    {
        if(!isFocused())
        {
            return;
        } else
        {
            leave();
            super.doFocusLeave();
            return;
        }
    }

    private void enter()
    {
        saveFov = Main3D.FOVX;
        CmdEnv.top().exec("fov 23.913");
        Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
        HookPilot hookpilot = HookPilot.current;
        if(hookpilot.isPadlock())
            hookpilot.stopPadlock();
        hookpilot.doAim(true);
        hookpilot.setSimpleUse(true);
        hookpilot.setSimpleAimOrient(aAim, tAim, 0.0F);
		hookpilot.setInstantOrient(aAim, tAim, 0.0F);
		doSetSimpleUse(true);
        HotKeyEnv.enable("PanView", false);
        HotKeyEnv.enable("SnapView", false);
        bEntered = true;
    }

    private void leave()
    {
        if(!bEntered)
        {
            return;
        } else
        {
            Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
            CmdEnv.top().exec("fov " + saveFov);
            HookPilot hookpilot = HookPilot.current;
            hookpilot.doAim(false);
            hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
            hookpilot.setSimpleUse(false);
			doSetSimpleUse(false);
            boolean flag = HotKeyEnv.isEnabled("aircraftView");
            HotKeyEnv.enable("PanView", flag);
            HotKeyEnv.enable("SnapView", flag);
            bEntered = false;
            return;
        }
    }

    public void destroy()
    {
        super.destroy();
        leave();
    }

    public void doToggleAim(boolean flag)
    {
        if(!isFocused())
            return;
        else
            return;
    }

    public CockpitTBXF1_Bombardier()
    {
        super("3DO/Cockpit/TBFVIZX/hier.him", "bf109");
        bEntered = false;
        try
        {
            Loc loc = new Loc();
            HookNamed hooknamed = new HookNamed(mesh, "CAMERAAIM");
            hooknamed.computePos(this, pos.getAbs(), loc);
            aAim = loc.getOrient().getAzimut();
            tAim = loc.getOrient().getTangage();
        }
        catch(Exception exception)
        {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        cockpitNightMats = (new String[] {
            "textrbm9", "texture25"
        });
        setNightMats(false);
        interpPut(new Interpolater(), null, Time.current(), null);
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
            setNightMats(true);
        else
            setNightMats(false);
    }

    public void reflectWorldToInstruments(float f)
    {
        mesh.chunkSetAngles("zSpeed", 0.0F, floatindex(cvt(Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 836.859F, 0.0F, 13F), speedometerScale), 0.0F);
        mesh.chunkSetAngles("zSpeed1", 0.0F, floatindex(cvt(fm.getSpeedKMH(), 0.0F, 836.859F, 0.0F, 13F), speedometerScale), 0.0F);
        mesh.chunkSetAngles("zAlt1", 0.0F, cvt((float)fm.Loc.z, 0.0F, 9144F, 0.0F, 10800F), 0.0F);
        mesh.chunkSetAngles("zAlt2", 0.0F, cvt((float)fm.Loc.z, 0.0F, 9144F, 0.0F, 1080F), 0.0F);
        mesh.chunkSetAngles("zHour", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        mesh.chunkSetAngles("zMinute", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zSecond", 0.0F, cvt(((World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zCompass1", 0.0F, fm.Or.getAzimut(), 0.0F);
        WayPoint waypoint = fm.AP.way.curr();
        if(waypoint != null)
        {
            waypoint.getP(P1);
            V.sub(P1, fm.Loc);
            float f1 = (float)(57.295779513082323D * Math.atan2(V.x, V.y));
            mesh.chunkSetAngles("zCompass2", 0.0F, 90F + f1, 0.0F);
        }
        if(bEntered)
        {
            boolean flag = false;
            if (aircraft() instanceof TBF1) {
                mesh.chunkSetAngles("zAngleMark", -floatindex(cvt(((TBF1)aircraft()).fSightCurForwardAngle, 7F, 140F, 0.7F, 14F), angleScale), 0.0F, 0.0F);
            	flag = ((TBF1)aircraft()).fSightCurReadyness > 0.93F;
            }
            else if (aircraft() instanceof TBF1C) {
                mesh.chunkSetAngles("zAngleMark", -floatindex(cvt(((TBF1C)aircraft()).fSightCurForwardAngle, 7F, 140F, 0.7F, 14F), angleScale), 0.0F, 0.0F);
                flag = ((TBF1C)aircraft()).fSightCurReadyness > 0.93F;
            }
            mesh.chunkVisible("BlackBox", true);
            mesh.chunkVisible("zReticle", flag);
            mesh.chunkVisible("zAngleMark", flag);
        } else
        {
            mesh.chunkVisible("BlackBox", false);
            mesh.chunkVisible("zReticle", false);
            mesh.chunkVisible("zAngleMark", false);
        }
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("XGlassDamage1", true);
        if((fm.AS.astateCockpitState & 8) != 0)
            mesh.chunkVisible("XGlassDamage2", true);
        if((fm.AS.astateCockpitState & 0x20) != 0)
            mesh.chunkVisible("XGlassDamage2", true);
        if((fm.AS.astateCockpitState & 2) != 0)
            mesh.chunkVisible("XGlassDamage3", true);
        if((fm.AS.astateCockpitState & 4) != 0)
            mesh.chunkVisible("XHullDamage1", true);
        if((fm.AS.astateCockpitState & 0x10) != 0)
            mesh.chunkVisible("XHullDamage2", true);
    }

    static Class _mthclass$(String s)
    {
        try
        {
            return Class.forName(s);
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    private static final float angleScale[] = {
        -38.5F, 16.5F, 41.5F, 52.5F, 59.25F, 64F, 67F, 70F, 72F, 73.25F, 
        75F, 76.5F, 77F, 78F, 79F, 80F
    };
    private static final float speedometerScale[] = {
        0.0F, 2.5F, 54F, 104F, 154.5F, 205.5F, 224F, 242F, 259.5F, 277.5F, 
        296.25F, 314F, 334F, 344.5F
    };
    private float saveFov;
    private float aAim;
    private float tAim;
    private boolean bEntered;
    private static Point3d P1 = new Point3d();
    private static Vector3d V = new Vector3d();

    static 
    {
        Property.set(com.maddox.il2.objects.air.CockpitTBXF1_Bombardier.class, "astatePilotIndx", 0);
    }



}
