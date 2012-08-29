// Source File Name: CockpitDB3T_BGunner.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Property;

public class CockpitDB3T_BGunner extends CockpitGunner {

	private Hook hook1;

	static {
		Property.set(CockpitDB3T_BGunner.class, "aiTuretNum", 2);
		Property.set(CockpitDB3T_BGunner.class, "weaponControlNum", 12);
		Property.set(CockpitDB3T_BGunner.class, "astatePilotIndx", 2);
	}

	public CockpitDB3T_BGunner() {
		super("3DO/Cockpit/A-20G-BGun/BGunnerDB3T.him", "he111_gunner");
		this.hook1 = null;
	}

	public void clipAnglesGun(Orient orient) {
		if (this.isRealMode()) {
			if (!this.aiTurret().bIsOperable) {
				orient.setYPR(0.0F, 0.0F, 0.0F);
			} else {
				float f = orient.getYaw();
				float f1 = orient.getTangage();
				if (f < -40F) {
					f = -40F;
				}
				if (f > 40F) {
					f = 40F;
				}
				if (f1 > 20F) {
					f1 = 20F;
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
				if (this.hook1 == null) {
					this.hook1 = new HookNamed(this.aircraft(), "_MGUN09");
				}
				this.doHitMasterAircraft(this.aircraft(), this.hook1, "_MGUN09");
			}
		}
	}

	public void moveGun(Orient orient) {
		super.moveGun(orient);
		this.mesh.chunkSetAngles("Turret1A", 15F, -orient.getYaw(), 0.0F);
		this.mesh.chunkSetAngles("Turret1B", 0.0F, orient.getTangage(), 0.0F);
	}

	public void reflectCockpitState() {
		if (this.fm.AS.astateCockpitState != 0) {
			this.mesh.chunkVisible("XGlassDamage1", true);
			this.mesh.chunkVisible("XGlassDamage2", true);
			this.mesh.chunkVisible("XHullDamage1", true);
			this.mesh.chunkVisible("XHullDamage2", true);
			this.mesh.chunkVisible("XHullDamage3", true);
		}
	}
}
