// Source File Name: CockpitKI_21_II_Bombardier.java
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
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class CockpitKI_21_II_Bombardier extends CockpitPilot {
	class Interpolater extends InterpolateRef {

		Interpolater() {
		}

		public boolean tick() {
			float f = ((KI_21_II) CockpitKI_21_II_Bombardier.this.aircraft()).fSightCurForwardAngle;
			float f1 = ((KI_21_II) CockpitKI_21_II_Bombardier.this.aircraft()).fSightCurSideslip;
			CockpitKI_21_II_Bombardier.this.mesh.chunkSetAngles("BlackBox",
					0.0F, -f1, f);
			if (CockpitKI_21_II_Bombardier.this.bEntered) {
				HookPilot hookpilot = HookPilot.current;
				hookpilot.setInstantOrient(CockpitKI_21_II_Bombardier.this.aAim
						+ f1, CockpitKI_21_II_Bombardier.this.tAim + f, 0.0F);
			}
			CockpitKI_21_II_Bombardier.this.mesh
					.chunkSetAngles(
							"Turret1A",
							0.0F,
							-CockpitKI_21_II_Bombardier.this.aircraft().FM.turret[0].tu[0],
							0.0F);
			CockpitKI_21_II_Bombardier.this.mesh
					.chunkSetAngles(
							"Turret1B",
							0.0F,
							CockpitKI_21_II_Bombardier.this.aircraft().FM.turret[0].tu[1],
							0.0F);
			return true;
		}
	}

	private static final float angleScale[] = { -38.5F, 16.5F, 41.5F, 52.5F,
			59.25F, 64F, 67F, 70F, 72F, 73.25F, 75F, 76.5F, 77F, 78F, 79F, 80F };

	private float saveFov;

	private float aAim;

	private float tAim;

	private boolean enteringAim;

	private boolean bEntered;
	static {
		Property.set(CockpitKI_21_II_Bombardier.class, "astatePilotIndx", 0);
	}

	public CockpitKI_21_II_Bombardier() {
		super("3DO/Cockpit/G4M1-11-Bombardier/BombardierKI21II.him", "he111");
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
		this.interpPut(new Interpolater(), null, Time.current(), null);
		printCompassHeading = true;
	}

	public void destroy() {
		super.destroy();
		this.leave();
	}

	protected boolean doFocusEnter() {
		if (super.doFocusEnter()) {
			this.aircraft().hierMesh().chunkVisible("NoseAXX_D0", false);
			this.aircraft().hierMesh().chunkVisible("Pilot3_D0", false);
			this.aircraft().hierMesh().chunkVisible("Pilot3_D1", false);
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
			this.aircraft()
					.hierMesh()
					.chunkVisible("NoseAXX_D0",
							this.aircraft().isChunkAnyDamageVisible("CF_D"));
			this.aircraft()
					.hierMesh()
					.chunkVisible("Pilot3_D0",
							this.aircraft().FM.AS.astatePilotStates[2] < 95);
			this.aircraft()
					.hierMesh()
					.chunkVisible(
							"Pilot3_D1",
							(this.aircraft().FM.AS.astatePilotStates[2] > 95)
									&& (this.aircraft().FM.AS.astateBailoutStep < 12));
			this.leave();
			super.doFocusLeave();
		}
	}

	public void doToggleAim(boolean flag) {
		if (this.isFocused() && (this.isToggleAim() != flag)) {
			if (flag)
				prepareToEnter();
			else
				leave();

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
		if (this.bEntered) {
			this.mesh.chunkSetAngles("zAngleMark", -this.floatindex(this.cvt(
					((KI_21_II) this.aircraft()).fSightCurForwardAngle, 7F,
					140F, 0.7F, 14F), angleScale), 0.0F, 0.0F);
			boolean flag = ((KI_21_II) this.aircraft()).fSightCurReadyness > 0.93F;
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

}
