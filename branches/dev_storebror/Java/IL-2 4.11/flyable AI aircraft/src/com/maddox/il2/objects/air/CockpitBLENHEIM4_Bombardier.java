// Source File Name: CockpitBLENHEIM4_Bombardier.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
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
import com.maddox.rts.CLASS;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class CockpitBLENHEIM4_Bombardier extends CockpitPilot {
	class Interpolater extends InterpolateRef {

		Interpolater() {
		}

		public boolean tick() {
			float f = ((BLENHEIM4) CockpitBLENHEIM4_Bombardier.this.aircraft()).fSightCurForwardAngle;
			float f1 = ((BLENHEIM4) CockpitBLENHEIM4_Bombardier.this.aircraft()).fSightCurSideslip;
			CockpitBLENHEIM4_Bombardier.this.mesh.chunkSetAngles("BlackBox",
					0.0F, -f1, f);
			if (CockpitBLENHEIM4_Bombardier.this.bEntered) {
				HookPilot hookpilot = HookPilot.current;
				hookpilot.setInstantOrient(
						CockpitBLENHEIM4_Bombardier.this.aAim + f1,
						CockpitBLENHEIM4_Bombardier.this.tAim + f, 0.0F);
			}
			return true;
		}
	}

	public Vector3f w;

	private static final float angleScale[] = { -38.5F, 16.5F, 41.5F, 52.5F,
			59.25F, 64F, 67F, 70F, 72F, 73.25F, 75F, 76.5F, 77F, 78F, 79F, 80F };

	private static final float speedometerScale[] = { 0.0F, 17.5F, 82F, 143.5F,
			205F, 226.5F, 248.5F, 270F, 292F, 315F, 338.5F };

	private float saveFov;

	private float aAim;

	private float tAim;

	private boolean enteringAim;

	private boolean bEntered;
	static {
		Property.set(CLASS.THIS(), "astatePilotIndx", 0);
	}

	public CockpitBLENHEIM4_Bombardier() {
		super("3DO/Cockpit/A-20C-Bombardier/BombardierBLENHEIM4.him", "he111");
		this.w = new Vector3f();
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
		this.cockpitNightMats = (new String[] { "4_gauges" });
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
		if (!this.isFocused()) {
			return;
		} else {
			this.leave();
			super.doFocusLeave();
			return;
		}
	}

	public void doToggleAim(boolean flag) {
		if (!this.isFocused()) {
			return;
		}
		if (this.isToggleAim() == flag) {
			return;
		}
		if (flag)
			prepareToEnter();
		else
			leave();

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
		if (!this.bEntered) {
			return;
		} else {
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
			return;
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
			this.mesh.chunkVisible("XGlassDamage4", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
			this.mesh.chunkVisible("XGlassDamage4", true);
		}
	}

	public void reflectWorldToInstruments(float f) {
		this.mesh.chunkSetAngles("zSpeed", 0.0F, this.floatindex(this.cvt(
				Pitot.Indicator((float) this.fm.Loc.z, this.fm.getSpeedKMH()),
				0.0F, 804.6721F, 0.0F, 10F), speedometerScale), 0.0F);
		this.mesh
				.chunkSetAngles("zAlt1", 0.0F, this.cvt((float) this.fm.Loc.z,
						0.0F, 9144F, 0.0F, 1080F), 0.0F);
		this.mesh.chunkSetAngles("zAlt2", 0.0F,
				this.cvt((float) this.fm.Loc.z, 0.0F, 9144F, 0.0F, 10800F),
				0.0F);
		this.mesh.chunkSetAngles("zCompass1", 0.0F, this.fm.Or.getAzimut(),
				0.0F);
		this.w.set(this.fm.getW());
		this.fm.Or.transform(this.w);
		this.mesh.chunkSetAngles("Z_TurnBank1", 0.0F,
				this.cvt(this.w.z, -0.23562F, 0.23562F, 22F, -22F), 0.0F);
		this.mesh.chunkSetAngles("Z_TurnBank2", 0.0F,
				this.cvt(this.fm.getAOS(), -8F, 8F, -12F, 12F), 0.0F);
		if (this.bEntered) {
			this.mesh.chunkSetAngles("zAngleMark", -this.floatindex(this.cvt(
					((BLENHEIM4) this.aircraft()).fSightCurForwardAngle, 7F,
					140F, 0.7F, 14F), angleScale), 0.0F, 0.0F);
			boolean flag = ((BLENHEIM4) this.aircraft()).fSightCurReadyness > 0.93F;
			this.mesh.chunkVisible("BlackBox", true);
			this.mesh.chunkVisible("zReticle", flag);
			this.mesh.chunkVisible("zAngleMark", flag);
		} else {
			this.mesh.chunkVisible("BlackBox", false);
			this.mesh.chunkVisible("zReticle", false);
			this.mesh.chunkVisible("zAngleMark", false);
		}
		if (enteringAim) {
			HookPilot hookpilot = HookPilot.current;
			if (hookpilot.isAimReached()) {
				enteringAim = false;
				enter();
			} else if (!hookpilot.isAim())
				enteringAim = false;
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
