// Source File Name: CockpitJU_87G2RUDEL_Gunner.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;

public class CockpitJU_87G2RUDEL_Gunner extends CockpitGunner {

	private Hook hook1;

	private Hook hook2;

	public CockpitJU_87G2RUDEL_Gunner() {
		super("3DO/Cockpit/JU-87G2RUDEL-Gun/hier.him", "bf109");
		this.hook1 = null;
		this.hook2 = null;
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
				if (f1 > 45F) {
					f1 = 45F;
				}
				if (f1 < -10F) {
					f1 = -10F;
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
			this.fm.CT.WeaponControl[10] = this.bGunFire;
		}
	}

	protected void interpTick() {
		if (this.isRealMode()) {
			if ((this.emitter == null) || !this.emitter.haveBullets()
					|| !this.aiTurret().bIsOperable) {
				this.bGunFire = false;
			}
			this.fm.CT.WeaponControl[10] = this.bGunFire;
			if (this.bGunFire) {
				if (this.hook1 == null) {
					this.hook1 = new HookNamed(this.aircraft(), "_MGUN01");
				}
				this.doHitMasterAircraft(this.aircraft(), this.hook1, "_MGUN01");
				if (this.hook2 == null) {
					this.hook2 = new HookNamed(this.aircraft(), "_MGUN02");
				}
				this.doHitMasterAircraft(this.aircraft(), this.hook2, "_MGUN02");
			}
		}
	}

	public void moveGun(Orient orient) {
		super.moveGun(orient);
		float f = orient.getYaw();
		float f1 = -orient.getTangage();
		this.mesh.chunkSetAngles("TurretA", 0.0F, f, 0.0F);
		this.mesh.chunkSetAngles("TurretB", 0.0F, f1, 0.0F);
		this.mesh.chunkSetAngles("Hose", (-0.333F * Math.abs(f1)) - 3F,
				0.5F * f, 0.0F);
		this.mesh.chunkSetAngles("PatronsL", 0.0F, f, 0.0F);
		this.mesh.chunkSetAngles("PatronsL_add", 0.0F,
				this.cvt(f, -25F, 0.0F, -91F, 0.0F), 0.0F);
		this.mesh.chunkSetAngles("PatronsR", 0.0F, f, 0.0F);
		this.mesh.chunkSetAngles("PatronsR_add", 0.0F,
				this.cvt(f, 0.0F, 25F, 0.0F, 91F), 0.0F);
		if (f1 < (-30F - (5F * f))) {
			this.mesh.chunkVisible("PatronsL_add", false);
		} else {
			this.mesh.chunkVisible("PatronsL_add", true);
		}
		if (f1 < (-30F + (5F * f))) {
			this.mesh.chunkVisible("PatronsR_add", false);
		} else {
			this.mesh.chunkVisible("PatronsR_add", true);
		}
	}
}
