// Source File Name: CockpitAR_196_Gunner.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.rts.Property;

public class CockpitAR_196_Gunner extends CockpitGunner {

	private static final float angles[] = { 4F, 5.5F, 5.5F, 7F, 10.5F, 15.5F,
			24F, 33F, 40F, 46F, 52.5F, 59F, 64.5F, 69F, 69F, 69F, 69F, 69F,
			69F, 69F, 69F, 69F, 69F, 66.5F, 62.5F, 55F, 49.5F, -40F, -74.5F,
			-77F, -77F, -77F, -77F, -77F, -77F, -77F, -77F };

	private Hook hook1;

	private Hook hook2;

	private boolean bNeedSetUp;

	static {
		Property.set(CockpitAR_196_Gunner.class, "aiTuretNum", 0);
		Property.set(CockpitAR_196_Gunner.class, "weaponControlNum", 10);
		Property.set(CockpitAR_196_Gunner.class, "astatePilotIndx", 1);
	}

	public CockpitAR_196_Gunner() {
		super("3DO/Cockpit/AR_196A3_Gun/AR_196_Gun.him", "he111_gunner");
		this.hook1 = null;
		this.hook2 = null;
		this.bNeedSetUp = true;
	}

	public void clipAnglesGun(Orient orient) {
		if (this.isRealMode()) {
			if (!this.aiTurret().bIsOperable) {
				orient.setYPR(0.0F, 0.0F, 0.0F);
			} else {
				float f = orient.getYaw();
				if (f < -90F) {
					f = -90F;
				}
				if (f > 90F) {
					f = 90F;
				}
				float f1 = orient.getTangage();
				if (f1 > 60F) {
					f1 = 60F;
				}
				if (f1 < -30F) {
					f1 = -30F;
				}
				float f2;
				for (f2 = Math.abs(f); f2 > 180F; f2 -= 180F) {
					;
				}
				if (f1 < -this.floatindex(this.cvt(f2, 0.0F, 180F, 0.0F, 36F),
						angles)) {
					f1 = -this.floatindex(this.cvt(f2, 0.0F, 180F, 0.0F, 36F),
							angles);
				}
				orient.setYPR(f, f1, 0.0F);
				orient.wrap();
			}
		}
	}

	protected boolean doFocusEnter() {
		if (super.doFocusEnter()) {
			this.aircraft().hierMesh().chunkVisible("Turret1B_D0", false);
			return true;
		} else {
			return false;
		}
	}

	protected void doFocusLeave() {
		this.aircraft()
				.hierMesh()
				.chunkVisible(
						"Turret1B_D0",
						this.aircraft().hierMesh()
								.isChunkVisible("Turret1A_D0"));
		super.doFocusLeave();
	}

	public void doGunFire(boolean flag) {
		if (this.isRealMode()) {
			if ((super.emitter == null) || !super.emitter.haveBullets()
					|| !this.aiTurret().bIsOperable) {
				super.bGunFire = false;
			} else {
				super.bGunFire = flag;
			}
			((FlightModelMain) (super.fm)).CT.WeaponControl[this
					.weaponControlNum()] = super.bGunFire;
		}
	}

	protected void interpTick() {
		if (super.bGunFire) {
			super.mesh.chunkSetAngles("Trigger", 0.0F, 17.5F, 0.0F);
		} else {
			super.mesh.chunkSetAngles("Trigger", 0.0F, 0.0F, 0.0F);
		}
		if (this.isRealMode()) {
			if ((super.emitter == null) || !super.emitter.haveBullets()
					|| !this.aiTurret().bIsOperable) {
				super.bGunFire = false;
			}
			((FlightModelMain) (super.fm)).CT.WeaponControl[this
					.weaponControlNum()] = super.bGunFire;
			if (super.bGunFire) {
				if (this.hook1 == null) {
					this.hook1 = new HookNamed(this.aircraft(), "_MGUN03");
				}
				this.doHitMasterAircraft(this.aircraft(), this.hook1, "_MGUN03");
				if (this.hook2 == null) {
					this.hook2 = new HookNamed(this.aircraft(), "_MGUN04");
				}
				this.doHitMasterAircraft(this.aircraft(), this.hook2, "_MGUN04");
			}
		}
	}

	public void moveGun(Orient orient) {
		super.moveGun(orient);
		super.mesh.chunkSetAngles("Turret_Base", 0.0F, orient.getYaw(), 0.0F);
		super.mesh.chunkSetAngles("MGun", 0.0F, orient.getTangage(), 0.0F);
		super.mesh.chunkSetAngles("Turret_Base2", 0.0F, orient.getYaw(), 0.0F);
		super.mesh.chunkSetAngles("MGun2", 0.0F,
				this.cvt(orient.getTangage(), -20F, 45F, -20F, 45F), 0.0F);
	}

	protected void reflectPlaneMats() {
		HierMesh hiermesh = this.aircraft().hierMesh();
		com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh
				.materialFind("Gloss1D0o"));
		super.mesh.materialReplace("Gloss1D0o", mat);
	}

	public void reflectWorldToInstruments(float f) {
		if (this.bNeedSetUp) {
			this.reflectPlaneMats();
			this.bNeedSetUp = false;
		}
	}
}
