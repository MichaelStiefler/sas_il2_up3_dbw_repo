// Source File Name: CockpitKI_21_I_NGunner.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Orient;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class CockpitKI_21_I_NGunner extends CockpitGunner {

	private int iCocking;

	static {
		Property.set(CLASS.THIS(), "aiTuretNum", 0);
		Property.set(CLASS.THIS(), "weaponControlNum", 10);
		Property.set(CLASS.THIS(), "astatePilotIndx", 2);
	}

	public CockpitKI_21_I_NGunner() {
		super("3DO/Cockpit/G4M1-11-NGun/NGunnerKI21I.him", "he111");
		this.iCocking = 0;
	}

	public void clipAnglesGun(Orient orient) {
		if (this.isRealMode()) {
			if (!this.aiTurret().bIsOperable) {
				orient.setYPR(0.0F, 0.0F, 0.0F);
			} else {
				float f = orient.getYaw();
				float f1 = orient.getTangage();
				if (f < -35F) {
					f = -35F;
				}
				if (f > 35F) {
					f = 35F;
				}
				if (f1 > 25F) {
					f1 = 25F;
				}
				if (f1 < -45F) {
					f1 = -45F;
				}
				orient.setYPR(f, f1, 0.0F);
				orient.wrap();
			}
		}
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
			this.mesh.chunkSetLocate("Turret1C", Cockpit.xyz, Cockpit.ypr);
		}
	}

	public void moveGun(Orient orient) {
		super.moveGun(orient);
		float f = -orient.getYaw();
		float f1 = orient.getTangage();
		this.mesh.chunkSetAngles("Turret1A", 0.0F, -f, 0.0F);
		this.mesh.chunkSetAngles("Turret1B", 0.0F, f1, 0.0F);
	}

	public void reflectCockpitState() {
	}
}
