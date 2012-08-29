// Source File Name: CockpitBEAU10Gun.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class CockpitBEAU10Gun extends CockpitGunner {

	private boolean bNeedSetUp;

	private int iCocking;

	static {
		Property.set(CLASS.THIS(), "aiTuretNum", 0);
		Property.set(CLASS.THIS(), "weaponControlNum", 10);
		Property.set(CLASS.THIS(), "astatePilotIndx", 1);
	}

	public CockpitBEAU10Gun() {
		super("3DO/Cockpit/G4M1-11-TGun/BeaufighterMk10.him", "he111_gunner");
		this.bNeedSetUp = true;
		this.iCocking = 0;
	}

	public void clipAnglesGun(Orient orient) {
		if (this.isRealMode()) {
			if (!this.aiTurret().bIsOperable) {
				orient.setYPR(0.0F, 0.0F, 0.0F);
			} else {
				float f = orient.getYaw();
				float f1 = orient.getTangage();
				if (f < -50F) {
					f = -50F;
				}
				if (f > 50F) {
					f = 50F;
				}
				if (f1 > this.cvt(Math.abs(f), 0.0F, 50F, 40F, 25F)) {
					f1 = this.cvt(Math.abs(f), 0.0F, 50F, 40F, 25F);
				}
				if (f1 < this.cvt(Math.abs(f), 0.0F, 50F, -10F, -3.5F)) {
					f1 = this.cvt(Math.abs(f), 0.0F, 50F, -10F, -3.5F);
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
		this.aircraft().hierMesh().chunkVisible("Turret1B_D0", true);
		super.doFocusLeave();
	}

	public void doGunFire(boolean flag) {
		if (this.isRealMode()) {
			if ((this.emitter == null) || !this.emitter.haveBullets()
					|| !this.aiTurret().bIsOperable) {
				this.bGunFire = false;
			} else {
				this.bGunFire = flag;
			}
			this.fm.CT.WeaponControl[this.weaponControlNum()] = this.bGunFire;
		}
	}

	protected void interpTick() {
		if (this.isRealMode()) {
			if ((this.emitter == null) || !this.emitter.haveBullets()
					|| !this.aiTurret().bIsOperable) {
				this.bGunFire = false;
			}
			this.fm.CT.WeaponControl[this.weaponControlNum()] = this.bGunFire;
			if (this.bGunFire) {
				if (this.iCocking > 0) {
					this.iCocking = 0;
				} else {
					this.iCocking = 1;
				}
			} else {
				this.iCocking = 0;
			}
			this.resetYPRmodifier();
			Cockpit.xyz[1] = -0.07F * this.iCocking;
			Cockpit.ypr[1] = 0.0F;
			this.mesh.chunkSetLocate("Turret3C", Cockpit.xyz, Cockpit.ypr);
		}
	}

	public void moveGun(Orient orient) {
		super.moveGun(orient);
		float f = -orient.getYaw();
		float f1 = orient.getTangage();
		this.mesh.chunkSetAngles("Turret3A", 0.0F, -f, 0.0F);
		this.mesh.chunkSetAngles("Turret3B", 0.0F, -6F + f1, 0.0F);
		if (f < -33F) {
			f = -33F;
		}
		if (f > 33F) {
			f = 33F;
		}
		this.mesh.chunkSetAngles("Turret3D", 0.0F, -f, 0.0F);
		this.mesh.chunkSetAngles("Turret3E", 0.0F, f1, 0.0F);
	}

	public void reflectCockpitState() {
	}

	protected void reflectPlaneMats() {
		HierMesh hiermesh = this.aircraft().hierMesh();
		com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh
				.materialFind("Gloss1D0o"));
		this.mesh.materialReplace("Gloss1D0o", mat);
	}

	public void reflectWorldToInstruments(float f) {
		if (this.bNeedSetUp) {
			this.reflectPlaneMats();
			this.bNeedSetUp = false;
		}
	}
}
