// Source File Name: CockpitBLENHEIM1_Bombardier.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-04-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CLASS;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class CockpitBLENHEIM1_Bombardier extends CockpitPilot {
	private float saveFov;
	private float aAim;
	private float tAim;
	private boolean bEntered = false;

	class Interpolater extends InterpolateRef {

		public boolean tick() {
			if (bEntered) {
				float f = ((BLENHEIM1) aircraft()).fSightCurForwardAngle;
				float f1 = -((BLENHEIM1) aircraft()).fSightCurSideslip;
				mesh.chunkSetAngles("BlackBox", f1, 0.0F, -f);
				HookPilot hookpilot = HookPilot.current;
				hookpilot.setInstantOrient(-f1, tAim + f, 0.0F);
			}
			return true;
		}

		Interpolater() {
		}
	}

	protected boolean doFocusEnter() {
		if (super.doFocusEnter()) {
			HookPilot localHookPilot = HookPilot.current;
			localHookPilot.doAim(false);
			Point3d point3d = new Point3d();
			point3d.set(0.0D, 0.0D, 0.0D);
			localHookPilot.setTubeSight(point3d);
			enter();
			return true;
		}
		return false;
	}

	protected void doFocusLeave() {
		if (isFocused()) {
			leave();
			super.doFocusLeave();
		}
	}

	private void enter() {
		this.saveFov = Main3D.FOVX;
		CmdEnv.top().exec("fov 50.0");
		Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
		HookPilot localHookPilot = HookPilot.current;
		if (localHookPilot.isPadlock())
			localHookPilot.stopPadlock();
		localHookPilot.doAim(true);
		localHookPilot.setSimpleUse(true);
		localHookPilot.setSimpleAimOrient(this.aAim, this.tAim, 0.0F);
		localHookPilot.setInstantOrient(aAim, tAim, 0.0F);
		doSetSimpleUse(true);
		HotKeyEnv.enable("PanView", false);
		HotKeyEnv.enable("SnapView", false);
		this.bEntered = true;
	}

	private void leave() {
		if (this.bEntered) {
			Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
			CmdEnv.top().exec("fov " + this.saveFov);
			HookPilot localHookPilot = HookPilot.current;
			localHookPilot.doAim(false);
			localHookPilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
			localHookPilot.setSimpleUse(false);
			doSetSimpleUse(false);
			boolean bool = HotKeyEnv.isEnabled("aircraftView");
			HotKeyEnv.enable("PanView", bool);
			HotKeyEnv.enable("SnapView", bool);
			this.bEntered = false;
		}
	}

	public void destroy() {
		super.destroy();
		leave();
	}

	public void doToggleAim(boolean paramBoolean) {
	}

	public CockpitBLENHEIM1_Bombardier() {
		super("3DO/Cockpit/Pe-2-Bombardier/BombardierBLENHEIM1.him", "he111");
		try {
			Loc localLoc = new Loc();
			HookNamed localHookNamed = new HookNamed(this.mesh, "CAMERA");
			localHookNamed.computePos(this, this.pos.getAbs(), localLoc);
			this.aAim = localLoc.getOrient().getAzimut();
			this.tAim = localLoc.getOrient().getTangage();
		} catch (Exception localException) {
			System.out.println(localException.getMessage());
			localException.printStackTrace();
		}
		interpPut(new CockpitBLENHEIM1_Bombardier.Interpolater(), null,
				Time.current(), null);
	}

	public void reflectWorldToInstruments(float paramFloat) {
		this.mesh.chunkSetAngles("zMark1",
				((BLENHEIM1) aircraft()).fSightCurForwardAngle * 3.675F, 0.0F,
				0.0F);

		float f = cvt(((BLENHEIM1) aircraft()).fSightSetForwardAngle, -15.0F,
				75.0F, -15.0F, 75.0F);

		this.mesh.chunkSetAngles("zMark2", f * 3.675F, 0.0F, 0.0F);
		resetYPRmodifier();
		Cockpit.xyz[0] = cvt(
				this.fm.Or.getKren() * Math.abs(this.fm.Or.getKren()),
				-1225.0F, 1225.0F, -0.23F, 0.23F);

		Cockpit.xyz[1] = cvt(
				(this.fm.Or.getTangage() - 1.0F)
						* Math.abs(this.fm.Or.getTangage() - 1.0F), -1225.0F,
				1225.0F, 0.23F, -0.23F);

		Cockpit.ypr[0] = cvt(this.fm.Or.getKren(), -45.0F, 45.0F, -180.0F,
				180.0F);
		this.mesh.chunkSetLocate("zBulb", Cockpit.xyz, Cockpit.ypr);
		resetYPRmodifier();
		Cockpit.xyz[0] = cvt(Cockpit.xyz[0], -0.23F, 0.23F, 0.0095F, -0.0095F);
		Cockpit.xyz[1] = cvt(Cockpit.xyz[1], -0.23F, 0.23F, 0.0095F, -0.0095F);
		this.mesh.chunkSetLocate("zRefraction", Cockpit.xyz, Cockpit.ypr);
	}

	static {
		Property.set(CLASS.THIS(), "astatePilotIndx", 0);
	}
}