// Source File Name: CockpitB29_Bombardier.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
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

public class CockpitB29_Bombardier extends CockpitPilot {
	class Interpolater extends InterpolateRef {

		Interpolater() {
		}

		public boolean tick() {
			float f = ((B_29) CockpitB29_Bombardier.this.aircraft()).fSightCurForwardAngle;
			float f1 = ((B_29) CockpitB29_Bombardier.this.aircraft()).fSightCurSideslip;
			CockpitB29_Bombardier.this.mesh.chunkSetAngles("BlackBox", 0.0F,
					-f1, f);
			if (CockpitB29_Bombardier.this.bEntered) {
				HookPilot hookpilot = HookPilot.current;
				hookpilot.setInstantOrient(
						CockpitB29_Bombardier.this.aAim + f1,
						CockpitB29_Bombardier.this.tAim + f, 0.0F);
			}
			CockpitB29_Bombardier.this.mesh.chunkSetAngles("TurretA", 0.0F,
					CockpitB29_Bombardier.this.aircraft().FM.turret[0].tu[0],
					0.0F);
			CockpitB29_Bombardier.this.mesh.chunkSetAngles("TurretB", 0.0F,
					CockpitB29_Bombardier.this.aircraft().FM.turret[0].tu[1],
					0.0F);
			return true;
		}
	}

	private static final float angleScale[] = { -38.5F, 16.5F, 41.5F, 52.5F,
			59.25F, 64F, 67F, 70F, 72F, 73.25F, 75F, 76.5F, 77F, 78F, 79F, 80F };

	private static final float speedometerScale[] = { 0.0F, 2.5F, 54F, 104F,
			154.5F, 205.5F, 224F, 242F, 259.5F, 277.5F, 296.25F, 314F, 334F,
			344.5F };

	private float saveFov;

	private float aAim;

	private float tAim;

	private boolean enteringAim;

	private boolean bEntered;

	private static Point3d P1 = new Point3d();

	private static Vector3d V = new Vector3d();

	static {
		Property.set(CockpitB29_Bombardier.class, "astatePilotIndx", 0);
	}

	public CockpitB29_Bombardier() {
		super("3DO/Cockpit/B-25J-Bombardier/BombardierB29.him", "bf109");
		enteringAim = false;

		this.bEntered = false;
		try {

			Loc loc = new Loc();
			HookNamed hooknamed = new HookNamed(this.mesh, "CAMERAAIM");
			hooknamed.computePos(this, this.pos.getAbs(), loc);
			this.aAim = loc.getOrient().getAzimut();
			this.tAim = loc.getOrient().getTangage();
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			exception.printStackTrace();
		}
		this.cockpitNightMats = (new String[] { "textrbm9", "texture25" });
		this.setNightMats(false);
		this.interpPut(new Interpolater(), null, Time.current(), null);
		printCompassHeading = true;
	}

	public void destroy() {
		super.destroy();
		this.leave();
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
		if (this.isFocused()) {
			this.leave();
			super.doFocusLeave();
		}
	}

	public void doToggleAim(boolean flag) {
		if (this.isFocused() && (this.isToggleAim() != flag)) {
			if (flag) {
				prepareToEnter();
			} else {
				this.leave();
			}
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
		this.saveFov = Main3D.FOVX;
		CmdEnv.top().exec("fov 23.913");
		Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
		HookPilot hookpilot = HookPilot.current;
		hookpilot.setInstantOrient(aAim, tAim, 0.0F);
		hookpilot.setSimpleUse(true);
		doSetSimpleUse(true);
		hookpilot.setSimpleAimOrient(this.aAim, this.tAim, 0.0F);
		HotKeyEnv.enable("PanView", false);
		HotKeyEnv.enable("SnapView", false);
		this.bEntered = true;
	}

	private void leave() {
		if (enteringAim) {
			HookPilot hookpilot = HookPilot.current;
			hookpilot.setInstantOrient(0.0F, -33F, 0.0F);
			hookpilot.doAim(false);
			hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
			return;
		}
		if (this.bEntered) {
			Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
			CmdEnv.top().exec("fov " + this.saveFov);
			HookPilot hookpilot1 = HookPilot.current;
			hookpilot1.setInstantOrient(0.0F, -33F, 0.0F);
			hookpilot1.doAim(false);
			hookpilot1.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
			hookpilot1.setSimpleUse(false);
			doSetSimpleUse(false);
			boolean flag = HotKeyEnv.isEnabled("aircraftView");
			HotKeyEnv.enable("PanView", flag);
			HotKeyEnv.enable("SnapView", flag);
			this.bEntered = false;
		}
	}

	public void reflectCockpitState() {
		if ((this.fm.AS.astateCockpitState & 1) != 0) {
			this.mesh.chunkVisible("XGlassDamage1", true);
		}
		if ((this.fm.AS.astateCockpitState & 8) != 0) {
			this.mesh.chunkVisible("XGlassDamage2", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
			this.mesh.chunkVisible("XGlassDamage2", true);
		}
		if ((this.fm.AS.astateCockpitState & 2) != 0) {
			this.mesh.chunkVisible("XGlassDamage3", true);
		}
		if ((this.fm.AS.astateCockpitState & 4) != 0) {
			this.mesh.chunkVisible("XHullDamage1", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
			this.mesh.chunkVisible("XHullDamage2", true);
		}
	}

	public void reflectWorldToInstruments(float f) {
		this.mesh.chunkSetAngles("zSpeed", 0.0F, this.floatindex(this.cvt(
				Pitot.Indicator((float) this.fm.Loc.z, this.fm.getSpeedKMH()),
				0.0F, 836.859F, 0.0F, 13F), speedometerScale), 0.0F);
		this.mesh.chunkSetAngles("zSpeed1", 0.0F, this.floatindex(
				this.cvt(this.fm.getSpeedKMH(), 0.0F, 836.859F, 0.0F, 13F),
				speedometerScale), 0.0F);
		this.mesh.chunkSetAngles("zAlt1", 0.0F,
				this.cvt((float) this.fm.Loc.z, 0.0F, 9144F, 0.0F, 10800F),
				0.0F);
		this.mesh
				.chunkSetAngles("zAlt2", 0.0F, this.cvt((float) this.fm.Loc.z,
						0.0F, 9144F, 0.0F, 1080F), 0.0F);
		this.mesh.chunkSetAngles("zHour", 0.0F,
				this.cvt(World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
		this.mesh.chunkSetAngles("zMinute", 0.0F,
				this.cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F),
				0.0F);
		this.mesh.chunkSetAngles("zSecond", 0.0F, this.cvt(
				((World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F,
				360F), 0.0F);
		this.mesh.chunkSetAngles("zCompass1", 0.0F, this.fm.Or.getAzimut(),
				0.0F);
		WayPoint waypoint = this.fm.AP.way.curr();
		if (waypoint != null) {
			waypoint.getP(P1);
			V.sub(P1, this.fm.Loc);
			float f2 = (float) (57.295779513082323D * Math.atan2(V.x, V.y));
			this.mesh.chunkSetAngles("zCompass2", 0.0F, 90F + f2, 0.0F);
		}
		if (enteringAim) {
			HookPilot hookpilot = HookPilot.current;
			if (hookpilot.isAimReached()) {
				enteringAim = false;
				enter();
			} else if (!hookpilot.isAim())
				enteringAim = false;
		}
		if (this.bEntered) {
			this.mesh.chunkSetAngles("zAngleMark", -this.floatindex(this.cvt(
					((B_29) this.aircraft()).fSightCurForwardAngle, 7F, 140F,
					0.7F, 14F), angleScale), 0.0F, 0.0F);
			boolean flag = ((B_29) this.aircraft()).fSightCurReadyness > 0.93F;
			this.mesh.chunkVisible("BlackBox", true);
			this.mesh.chunkVisible("zReticle", flag);
			this.mesh.chunkVisible("zAngleMark", flag);
		} else {
			this.mesh.chunkVisible("BlackBox", false);
			this.mesh.chunkVisible("zReticle", false);
			this.mesh.chunkVisible("zAngleMark", false);
		}
	}

	public void toggleLight() {
		this.cockpitLightControl = !this.cockpitLightControl;
		if (this.cockpitLightControl) {
			this.setNightMats(true);
		} else {
			this.setNightMats(false);
		}
	}

}
