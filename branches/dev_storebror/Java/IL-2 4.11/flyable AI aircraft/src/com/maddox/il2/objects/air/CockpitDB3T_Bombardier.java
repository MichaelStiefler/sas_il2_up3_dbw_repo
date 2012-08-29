// Source File Name: CockpitDB3T_Bombardier.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
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

public class CockpitDB3T_Bombardier extends CockpitPilot {
	class Interpolater extends InterpolateRef {

		Interpolater() {
		}

		public boolean tick() {
			if (CockpitDB3T_Bombardier.this.bEntered) {
				float f = ((IL_4_DB3T) CockpitDB3T_Bombardier.this.aircraft()).fSightCurForwardAngle;
				float f1 = -((IL_4_DB3T) CockpitDB3T_Bombardier.this.aircraft()).fSightCurSideslip;
				CockpitDB3T_Bombardier.this.mesh.chunkSetAngles("BlackBox", f1,
						0.0F, -f);
				HookPilot hookpilot = HookPilot.current;
				hookpilot.setInstantOrient(-f1,
						CockpitDB3T_Bombardier.this.tAim + f, 0.0F);
			}
			return true;
		}
	}

	private float saveFov;

	private float aAim;

	private float tAim;

	private boolean enteringAim;

	private boolean bEntered;
	static {
		Property.set(CLASS.THIS(), "astatePilotIndx", 0);
	}

	public CockpitDB3T_Bombardier() {
		super("3DO/Cockpit/Pe-2-Bombardier/BombardierDB3T.him", "he111");
		enteringAim = false;
		this.bEntered = false;
		try {

			Loc loc = new Loc();
			HookNamed hooknamed = new HookNamed(this.mesh, "CAMERA");
			hooknamed.computePos(this, this.pos.getAbs(), loc);
			this.aAim = loc.getOrient().getAzimut();
			this.tAim = loc.getOrient().getTangage();
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			exception.printStackTrace();
		}
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
			this.prepareToEnter();
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
		CmdEnv.top().exec("fov 50.0");
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

	public void reflectWorldToInstruments(float f) {
		this.mesh.chunkSetAngles("zMark1",
				((IL_4_DB3T) this.aircraft()).fSightCurForwardAngle * 3.675F,
				0.0F, 0.0F);
		float f1 = this.cvt(
				((IL_4_DB3T) this.aircraft()).fSightSetForwardAngle, -15F, 75F,
				-15F, 75F);
		this.mesh.chunkSetAngles("zMark2", f1 * 3.675F, 0.0F, 0.0F);
		this.resetYPRmodifier();
		Cockpit.xyz[0] = this.cvt(
				this.fm.Or.getKren() * Math.abs(this.fm.Or.getKren()), -1225F,
				1225F, -0.23F, 0.23F);
		Cockpit.xyz[1] = this.cvt(
				(this.fm.Or.getTangage() - 1.0F)
						* Math.abs(this.fm.Or.getTangage() - 1.0F), -1225F,
				1225F, 0.23F, -0.23F);
		Cockpit.ypr[0] = this.cvt(this.fm.Or.getKren(), -45F, 45F, -180F, 180F);
		this.mesh.chunkSetLocate("zBulb", Cockpit.xyz, Cockpit.ypr);
		this.resetYPRmodifier();
		Cockpit.xyz[0] = this.cvt(Cockpit.xyz[0], -0.23F, 0.23F, 0.0095F,
				-0.0095F);
		Cockpit.xyz[1] = this.cvt(Cockpit.xyz[1], -0.23F, 0.23F, 0.0095F,
				-0.0095F);
		this.mesh.chunkSetLocate("zRefraction", Cockpit.xyz, Cockpit.ypr);
		if (enteringAim) {
			HookPilot hookpilot = HookPilot.current;
			if (hookpilot.isAimReached()) {
				enteringAim = false;
				enter();
			} else if (!hookpilot.isAim())
				enteringAim = false;
		}
	}

}
