// Source File Name: CockpitMOSQUITO4_Bombardier.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-04-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
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

public class CockpitMOSQUITO4_Bombardier extends CockpitPilot {
	class Interpolater extends InterpolateRef {

		public boolean tick() {
			float f = ((MOSQUITO4) aircraft()).fSightCurForwardAngle;
			float f1 = ((MOSQUITO4) aircraft()).fSightCurSideslip;
			CockpitMOSQUITO4_Bombardier.calibrAngle = 360F - fm.Or.getPitch();
			mesh.chunkSetAngles("BlackBox", -10F * f1, 0.0F,
					CockpitMOSQUITO4_Bombardier.calibrAngle + f);
			if (bEntered) {
				HookPilot hookpilot = HookPilot.current;
				// hookpilot.setSimpleAimOrient(aAim + 10F * f1, tAim +
				// CockpitMOSQUITO4_Bombardier.calibrAngle + f, 0.0F);
				hookpilot.setInstantOrient(aAim + 10F * f1, tAim
						+ CockpitMOSQUITO4_Bombardier.calibrAngle + f, 0.0F);
			}
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
			point3d.set(0.0D, 0.0D, 0.0D);
			hookpilot.setTubeSight(point3d);
			enter();
			return true;
		} else {
			return false;
		}
	}

	protected void doFocusLeave() {
		if (!isFocused()) {
			return;
		} else {
			leave();
			super.doFocusLeave();
			return;
		}
	}

	private void enter() {
		saveFov = Main3D.FOVX;
		CmdEnv.top().exec("fov 23.913");
		Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
		HookPilot hookpilot = HookPilot.current;
		if (hookpilot.isPadlock())
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

	private void leave() {
		if (!bEntered) {
			return;
		} else {
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

	public void destroy() {
		super.destroy();
		leave();
	}

	public void doToggleAim(boolean flag) {
	}

	public CockpitMOSQUITO4_Bombardier() {
		super("3DO/Cockpit/Blenheim-Bombardier/BombardierBLENHEIM1.him",
				"he111");
		w = new Vector3f();
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
		cockpitNightMats = (new String[] { "4_gauges" });
		interpPut(new Interpolater(), null, Time.current(), null);
	}

	public void reflectWorldToInstruments(float f) {
		mesh.chunkSetAngles(
				"zSpeed",
				0.0F,
				floatindex(
						cvt(Pitot.Indicator((float) fm.Loc.z, fm.getSpeedKMH()),
								0.0F, 804.6721F, 0.0F, 10F), speedometerScale),
				0.0F);
		if (bEntered) {
			mesh.chunkSetAngles(
					"zAngleMark",
					-floatindex(
							cvt(((MOSQUITO4) aircraft()).fSightCurForwardAngle,
									7F, 140F, 0.7F, 14F), angleScale), 0.0F,
					0.0F);
			boolean flag = ((MOSQUITO4) aircraft()).fSightCurReadyness > 0.93F;
			mesh.chunkVisible("BlackBox", true);
			mesh.chunkVisible("zReticle", flag);
			mesh.chunkVisible("zAngleMark", flag);
		} else {
			mesh.chunkVisible("BlackBox", false);
			mesh.chunkVisible("zReticle", false);
			mesh.chunkVisible("zAngleMark", false);
		}
	}

	static Class _mthclass$(String s) {
		try {
			return Class.forName(s);
		} catch (ClassNotFoundException classnotfoundexception) {
			throw new NoClassDefFoundError(classnotfoundexception.getMessage());
		}
	}

	public Vector3f w;
	private static final float angleScale[] = { -38.5F, 16.5F, 41.5F, 52.5F,
			59.25F, 64F, 67F, 70F, 72F, 73.25F, 75F, 76.5F, 77F, 78F, 79F, 80F };
	private static final float speedometerScale[] = { 0.0F, 17.5F, 82F, 143.5F,
			205F, 226.5F, 248.5F, 270F, 292F, 315F, 338.5F };
	private static float calibrAngle = 0.0F;
	private float saveFov;
	private float aAim;
	private float tAim;
	private boolean bEntered;

	static {
		Property.set(
				com.maddox.il2.objects.air.CockpitMOSQUITO4_Bombardier.class,
				"astatePilotIndx", 0);
	}
}
