// Source File Name: CockpitCantZ_Bombardier.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-04-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.JGP.*;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.*;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.*;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Time;

public class CockpitCantZ_Bombardier extends CockpitPilot {
	class Interpolater extends InterpolateRef {

		public boolean tick() {
			if (fm != null) {
				setTmp = setOld;
				setOld = setNew;
				setNew = setTmp;
				setNew.altimeter = fm.getAltitude();
				setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F),
						90F + ((FlightModelMain) (fm)).Or.azimut());
			}
			float f = ((CantZ506) aircraft()).fSightCurForwardAngle;
			float f1 = 0.0F;
			if (bEntered) {
				HookPilot hookpilot = HookPilot.current;
				hookpilot.setInstantOrient(aAim + f1, tAim + f, 0.0F);
			}
			return true;
		}

		Interpolater() {
		}
	}

	private class Variables {

		float altimeter;
		AnglesFork azimuth;

		private Variables() {
			azimuth = new AnglesFork();
		}

		Variables(Variables variables) {
			this();
		}
	}

	protected boolean doFocusEnter() {
		if (super.doFocusEnter()) {
			HookPilot hookpilot = HookPilot.current;
			hookpilot.doAim(false);
			Point3d point3d = new Point3d();
			point3d.set(0.0D, 0.0D, 0.0D);
			hookpilot.setTubeSight(point3d);
			((CantZ506) ((Interpolate) (super.fm)).actor).bPitUnfocused = false;
			aircraft().hierMesh().chunkVisible("Interior_D0", false);
			return true;
		} else {
			return false;
		}
	}

	protected void doFocusLeave() {
		if (isFocused()) {
			((CantZ506) ((Interpolate) (super.fm)).actor).bPitUnfocused = true;
			aircraft().hierMesh().chunkVisible("Interior_D0",
					aircraft().hierMesh().isChunkVisible("CF_D0"));
			leave();
			super.doFocusLeave();
		}
	}

	private void enter() {
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
		if (bEntered) {
			HookPilot hookpilot = HookPilot.current;
			hookpilot.doAim(false);
			hookpilot.setSimpleUse(false);
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
				enter();
			else
				leave();
	}

	public CockpitCantZ_Bombardier() {
		super("3DO/Cockpit/CantZ-Bombardier/hier.him", "he111");
		bEntered = false;
		setOld = new Variables(null);
		setNew = new Variables(null);
		w = new Vector3f();
		try {
			Loc loc = new Loc();
			HookNamed hooknamed1 = new HookNamed(super.mesh, "CAMERAAIM");
			hooknamed1.computePos(this, super.pos.getAbs(), loc);
			aAim = loc.getOrient().getAzimut();
			tAim = loc.getOrient().getTangage();
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			exception.printStackTrace();
		}
		HookNamed hooknamed = new HookNamed(super.mesh, "LAMPHOOK1");
		Loc loc1 = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
		hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F),
				loc1);
		light1 = new LightPointActor(new LightPoint(), loc1.getPoint());
		light1.light.setColor(109F, 99F, 90F);
		light1.light.setEmit(0.0F, 0.0F);
		super.pos.base().draw.lightMap().put("LAMPHOOK1", light1);
		super.cockpitNightMats = (new String[] { "Panel", "Needles" });
		setNightMats(false);
		interpPut(new Interpolater(), null, Time.current(), null);
	}

	public void toggleLight() {
		super.cockpitLightControl = !super.cockpitLightControl;
		if (super.cockpitLightControl) {
			light1.light.setEmit(0.0032F, 7.2F);
			setNightMats(true);
		} else {
			light1.light.setEmit(0.0F, 0.0F);
			setNightMats(false);
		}
	}

	public void reflectWorldToInstruments(float f) {
		if (bNeedSetUp) {
			reflectPlaneMats();
			bNeedSetUp = false;
		}
		reflectPlaneToModel();
		resetYPRmodifier();
		float f1 = interp(setNew.altimeter, setOld.altimeter, f) * 0.072F;
		super.mesh.chunkSetAngles("Z_Altimeter", 0.0F, f1, 0.0F);
		super.mesh
				.chunkSetAngles(
						"Z_Speedometer",
						0.0F,
						floatindex(
								cvt(Pitot
										.Indicator(
												(float) ((Tuple3d) (((FlightModelMain) (super.fm)).Loc)).z,
												super.fm.getSpeedKMH()), 0.0F,
										460F, 0.0F, 23F), speedometerScale),
						0.0F);
		super.mesh.chunkSetAngles("z_Hour", 0.0F,
				cvt(World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
		super.mesh.chunkSetAngles("z_Minute", 0.0F,
				cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
		super.mesh.chunkSetAngles(
				"z_Second",
				0.0F,
				cvt(((World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F,
						0.0F, 360F), 0.0F);
		super.mesh.chunkSetAngles("Z_Compass1", 0.0F,
				90F - setNew.azimuth.getDeg(f), 0.0F);
		super.mesh.chunkSetAngles("Z_TurnBank2", 0.0F,
				cvt(getBall(8D), -8F, 8F, -28F, 28F), 0.0F);
		if (!((NetAircraft) (aircraft())).thisWeaponsName.startsWith("1x")) {
			resetYPRmodifier();
			float f2 = ((FlightModelMain) (super.fm)).Or.getPitch();
			if (f2 > 360F)
				f2 -= 360F;
			f2 *= 0.00872664F;
			float f3 = ((CantZ506) aircraft()).fSightSetForwardAngle
					- (float) Math.toRadians(f2);
			float f4 = (float) (0.16915999352931976D * Math.tan(f3));
			if (f4 < 0.032F)
				f4 = 0.032F;
			else if (f4 > 0.21F)
				f4 = 0.21F;
			float f5 = f4 * 0.667F;
			Cockpit.xyz[0] = f4;
			super.mesh.chunkSetLocate("ZCursor1", Cockpit.xyz, Cockpit.ypr);
			Cockpit.xyz[0] = f5;
			super.mesh.chunkSetLocate("ZCursor2", Cockpit.xyz, Cockpit.ypr);
			super.mesh.chunkSetAngles("Cylinder", 0.0F,
					((CantZ506) aircraft()).fSightCurSideslip, 0.0F);
		}
	}

	protected void mydebugcockpit(String s) {
	}

	public void reflectCockpitState() {
		if (((FlightModelMain) (super.fm)).AS.astateCockpitState != 0) {
			if ((((FlightModelMain) (super.fm)).AS.astateCockpitState & 0x10) != 0)
				super.mesh.chunkVisible("XGlassHoles3", true);
			if ((((FlightModelMain) (super.fm)).AS.astateCockpitState & 0x20) != 0)
				super.mesh.chunkVisible("XGlassHoles3", true);
		}
	}

	protected void reflectPlaneMats() {
	}

	protected void reflectPlaneToModel() {
	}

	private static final float speedometerScale[] = { 0.0F, 10F, 20F, 30F, 50F,
			68F, 88F, 109F, 126F, 142F, 159F, 176F, 190F, 206F, 220F, 238F,
			253F, 270F, 285F, 300F, 312F, 325F, 337F, 350F, 360F };
	private float aAim;
	private float tAim;
	private boolean bEntered;
	private Variables setOld;
	private Variables setNew;
	private Variables setTmp;
	public Vector3f w;
	private LightPointActor light1;
	static Class class$com$maddox$il2$objects$air$CockpitCantZ506B_Bombardier;
	private boolean bNeedSetUp;
}
