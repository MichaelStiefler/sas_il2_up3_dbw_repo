// Source File Name: CockpitB17D_Bombardier.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-06
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class CockpitB17D_Bombardier extends CockpitPilot {
	class Interpolater extends InterpolateRef {

		public boolean tick() {
			float f = ((B_17D) aircraft()).fSightCurForwardAngle;
			float f1 = ((B_17D) aircraft()).fSightCurSideslip;
			mesh.chunkSetAngles("BlackBox", 0.0F, -f1, f);
			if (bEntered) {
				HookPilot hookpilot = HookPilot.current;
				hookpilot.setInstantOrient(aAim + f1, tAim + f, 0.0F);
			}
			mesh.chunkSetAngles("TurretA", 0.0F, aircraft().FM.turret[0].tu[0],
					0.0F);
			mesh.chunkSetAngles("TurretB", 0.0F, aircraft().FM.turret[0].tu[1],
					0.0F);
			return true;
		}

		Interpolater() {
		}
	}

	protected boolean doFocusEnter() {
		if (super.doFocusEnter()) {
			HookPilot hookpilot = HookPilot.current;
			hookpilot.doAim(false);
			Point3d point3d = new Point3d();
			point3d.set(0.15000000596046448D, 0.0D, -0.10000000149011612D);
			hookpilot.setTubeSight(point3d);
			return true;
		} else {
			return false;
		}
	}

	protected void doFocusLeave() {
		if (isFocused()) {
			leave();
			super.doFocusLeave();
		}
	}

	private void prepareToEnter() {
		HookPilot hookpilot = HookPilot.current;
		if (hookpilot.isPadlock())
			hookpilot.stopPadlock();
		hookpilot.doAim(true);
		hookpilot.setSimpleAimOrient(0.0F, -33F, 0.0F);
		enteringAim = true;
	}

	private void enter() {
		saveFov = Main3D.FOVX;
		CmdEnv.top().exec("fov 23.913");
		Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
		HookPilot hookpilot = HookPilot.current;
		hookpilot.setInstantOrient(aAim, tAim, 0.0F);
		hookpilot.setSimpleUse(true);
		doSetSimpleUse(true);
		HotKeyEnv.enable("PanView", false);
		HotKeyEnv.enable("SnapView", false);
		bEntered = true;
	}

	private void leave() {
		if (enteringAim) {
			HookPilot hookpilot = HookPilot.current;
			hookpilot.setInstantOrient(0.0F, -33F, 0.0F);
			hookpilot.doAim(false);
			hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
			return;
		}
		if (bEntered) {
			Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
			CmdEnv.top().exec("fov " + saveFov);
			HookPilot hookpilot1 = HookPilot.current;
			hookpilot1.setInstantOrient(0.0F, -33F, 0.0F);
			hookpilot1.doAim(false);
			hookpilot1.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
			hookpilot1.setSimpleUse(false);
			doSetSimpleUse(false);
			boolean flag = HotKeyEnv.isEnabled("aircraftView");
			HotKeyEnv.enable("PanView", flag);
			HotKeyEnv.enable("SnapView", flag);
			bEntered = false;
		}
	}

	public void destroy() {
		super.destroy();
		leave();
	}

	public void doToggleAim(boolean flag) {
		if (isFocused() && isToggleAim() != flag)
			if (flag)
				prepareToEnter();
			else
				leave();
	}

	public CockpitB17D_Bombardier() {
		super("3DO/Cockpit/B-25J-Bombardier/BombardierB17D.him", "bf109");
		enteringAim = false;
		bEntered = false;
		try {
			Loc loc = new Loc();
			HookNamed hooknamed = new HookNamed(mesh, "CAMERAAIM");
			hooknamed.computePos(this, pos.getAbs(), loc);
			aAim = loc.getOrient().getAzimut();
			tAim = loc.getOrient().getTangage();
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			exception.printStackTrace();
		}
		cockpitNightMats = (new String[] { "textrbm9", "texture25" });
		setNightMats(false);
		interpPut(new Interpolater(), null, Time.current(), null);
		printCompassHeading = true;
	}

	public void toggleLight() {
		cockpitLightControl = !cockpitLightControl;
		if (cockpitLightControl)
			setNightMats(true);
		else
			setNightMats(false);
	}

	public void reflectWorldToInstruments(float f) {
		mesh.chunkSetAngles(
				"zSpeed",
				0.0F,
				floatindex(
						cvt(Pitot.Indicator((float) fm.Loc.z, fm.getSpeedKMH()),
								0.0F, 836.859F, 0.0F, 13F), speedometerScale),
				0.0F);
		mesh.chunkSetAngles(
				"zSpeed1",
				0.0F,
				floatindex(cvt(fm.getSpeedKMH(), 0.0F, 836.859F, 0.0F, 13F),
						speedometerScale), 0.0F);
		mesh.chunkSetAngles("zAlt1", 0.0F,
				cvt((float) fm.Loc.z, 0.0F, 9144F, 0.0F, 10800F), 0.0F);
		mesh.chunkSetAngles("zAlt2", 0.0F,
				cvt((float) fm.Loc.z, 0.0F, 9144F, 0.0F, 1080F), 0.0F);
		mesh.chunkSetAngles("zHour", 0.0F,
				cvt(World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
		mesh.chunkSetAngles("zMinute", 0.0F,
				cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
		mesh.chunkSetAngles(
				"zSecond",
				0.0F,
				cvt(((World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F,
						0.0F, 360F), 0.0F);
		mesh.chunkSetAngles("zCompass1", 0.0F, fm.Or.getAzimut(), 0.0F);
		WayPoint waypoint = fm.AP.way.curr();
		if (waypoint != null) {
			waypoint.getP(P1);
			V.sub(P1, fm.Loc);
			float f2 = (float) (57.295779513082323D * Math.atan2(V.x, V.y));
			mesh.chunkSetAngles("zCompass2", 0.0F, 90F + f2, 0.0F);
		}
		if (enteringAim) {
			HookPilot hookpilot = HookPilot.current;
			if (hookpilot.isAimReached()) {
				enteringAim = false;
				enter();
			} else if (!hookpilot.isAim())
				enteringAim = false;
		}
		if (bEntered) {
			mesh.chunkSetAngles(
					"zAngleMark",
					-floatindex(
							cvt(((B_17D) aircraft()).fSightCurForwardAngle, 7F,
									140F, 0.7F, 14F), angleScale), 0.0F, 0.0F);
			boolean flag = ((B_17D) aircraft()).fSightCurReadyness > 0.93F;
			mesh.chunkVisible("BlackBox", true);
			mesh.chunkVisible("zReticle", flag);
			mesh.chunkVisible("zAngleMark", flag);
		} else {
			mesh.chunkVisible("BlackBox", false);
			mesh.chunkVisible("zReticle", false);
			mesh.chunkVisible("zAngleMark", false);
		}
	}

	public void reflectCockpitState() {
		if ((fm.AS.astateCockpitState & 1) != 0)
			mesh.chunkVisible("XGlassDamage1", true);
		if ((fm.AS.astateCockpitState & 8) != 0)
			mesh.chunkVisible("XGlassDamage2", true);
		if ((fm.AS.astateCockpitState & 0x20) != 0)
			mesh.chunkVisible("XGlassDamage2", true);
		if ((fm.AS.astateCockpitState & 2) != 0)
			mesh.chunkVisible("XGlassDamage3", true);
		if ((fm.AS.astateCockpitState & 4) != 0)
			mesh.chunkVisible("XHullDamage1", true);
		if ((fm.AS.astateCockpitState & 0x10) != 0)
			mesh.chunkVisible("XHullDamage2", true);
	}

	private boolean enteringAim;
	private static final float angleScale[] = { -38.5F, 16.5F, 41.5F, 52.5F,
			59.25F, 64F, 67F, 70F, 72F, 73.25F, 75F, 76.5F, 77F, 78F, 79F, 80F };
	private static final float speedometerScale[] = { 0.0F, 2.5F, 54F, 104F,
			154.5F, 205.5F, 224F, 242F, 259.5F, 277.5F, 296.25F, 314F, 334F,
			344.5F };
	private float saveFov;
	private float aAim;
	private float tAim;
	private boolean bEntered;
	private static Point3d P1 = new Point3d();
	private static Vector3d V = new Vector3d();

	static {
		Property.set(com.maddox.il2.objects.air.CockpitB17D_Bombardier.class,
				"astatePilotIndx", 0);
	}

}
