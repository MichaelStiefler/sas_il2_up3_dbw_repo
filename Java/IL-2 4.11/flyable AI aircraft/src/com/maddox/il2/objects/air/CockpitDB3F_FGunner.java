// Source File Name: CockpitDB3F_FGunner.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Orient;
import com.maddox.rts.Property;

public class CockpitDB3F_FGunner extends CockpitGunner {

	static {
		Property.set(CockpitDB3F_FGunner.class, "aiTuretNum", 0);
		Property.set(CockpitDB3F_FGunner.class, "weaponControlNum", 10);
		Property.set(CockpitDB3F_FGunner.class, "astatePilotIndx", 2);
	}

	public CockpitDB3F_FGunner() {
		super("3DO/Cockpit/He-111H-6-NGun/FGunnerDB3F.him", "he111_gunner");
	}

	public void clipAnglesGun(Orient orient) {
		if (this.isRealMode()) {
			if (!this.aiTurret().bIsOperable) {
				orient.setYPR(0.0F, 0.0F, 0.0F);
			} else {
				float f = orient.getYaw();
				float f1 = orient.getTangage();
				if (f < -25F) {
					f = -25F;
				}
				if (f > 25F) {
					f = 25F;
				}
				if (f1 > 20F) {
					f1 = 20F;
				}
				if (f1 < -40F) {
					f1 = -40F;
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
				this.mesh.chunkSetAngles("Butona", 0.15F, 0.0F, 0.0F);
			} else {
				this.mesh.chunkSetAngles("Butona", 0.0F, 0.0F, 0.0F);
			}
		}
	}

	public void moveGun(Orient orient) {
		super.moveGun(orient);
		float f = -orient.getYaw();
		float f1 = orient.getTangage();
		this.mesh.chunkSetAngles("TurretA", 0.0F, f, 0.0F);
		this.mesh.chunkSetAngles("TurretB", 0.0F, f1, 0.0F);
		if (f > 15F) {
			f = 15F;
		}
		if (f1 < -21F) {
			f1 = -21F;
		}
		this.mesh.chunkSetAngles("CameraRodA", 0.0F, f, 0.0F);
		this.mesh.chunkSetAngles("CameraRodB", 0.0F, f1, 0.0F);
	}

	public void reflectCockpitState() {
		if ((this.fm.AS.astateCockpitState & 4) != 0) {
			this.mesh.chunkVisible("ZHolesL_D1", true);
		}
		if ((this.fm.AS.astateCockpitState & 8) != 0) {
			this.mesh.chunkVisible("ZHolesL_D2", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
			this.mesh.chunkVisible("ZHolesR_D1", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
			this.mesh.chunkVisible("ZHolesR_D2", true);
		}
		if ((this.fm.AS.astateCockpitState & 1) != 0) {
			this.mesh.chunkVisible("ZHolesF_D1", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x80) != 0) {
			this.mesh.chunkVisible("zOil_D1", true);
		}
	}

	public void reflectWorldToInstruments(float f) {
		this.mesh.chunkSetAngles("zColumn1", 0.0F, 0.0F, -10F
				* this.fm.CT.ElevatorControl);
		this.mesh.chunkSetAngles("zColumn2", 0.0F, 0.0F, -40F
				* this.fm.CT.AileronControl);
	}
}
