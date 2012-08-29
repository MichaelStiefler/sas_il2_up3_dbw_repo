// Source File Name: CockpitME_323_FLGunner.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Property;

public class CockpitME_323_FLGunner extends CockpitGunner {

	private Hook hook1;

	private int iCocking;

	private int iOldVisDrums;

	private int iNewVisDrums;

	static {
		Property.set(CockpitME_323_FLGunner.class, "aiTuretNum", 0);
		Property.set(CockpitME_323_FLGunner.class, "weaponControlNum", 10);
		Property.set(CockpitME_323_FLGunner.class, "astatePilotIndx", 4);
	}

	public CockpitME_323_FLGunner() {
		super("3DO/Cockpit/He-111H-2-LGun/FLGunnerME323.him", "he111_gunner");
		this.hook1 = null;
		this.iCocking = 0;
		this.iOldVisDrums = 2;
		this.iNewVisDrums = 2;
	}

	public void clipAnglesGun(Orient orient) {
		if (this.isRealMode()) {
			if (!this.aiTurret().bIsOperable) {
				orient.setYPR(0.0F, 0.0F, 0.0F);
			} else {
				float f = orient.getYaw();
				float f1 = orient.getTangage();
				if (f < -55F) {
					f = -55F;
				}
				if (f > 40F) {
					f = 40F;
				}
				if (f1 > 30F) {
					f1 = 30F;
				}
				if (f1 < -40F) {
					f1 = -40F;
				}
				if (f1 < (-55F - (0.5F * f))) {
					f1 = -55F - (0.5F * f);
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
			this.fm.CT.WeaponControl[this.weaponControlNum()] = this.bGunFire;
			if (this.bGunFire) {
				if (this.hook1 == null) {
					this.hook1 = new HookNamed(this.aircraft(), "_MGUN04");
				}
				this.doHitMasterAircraft(this.aircraft(), this.hook1, "_MGUN04");
				if (this.iCocking > 0) {
					this.iCocking = 0;
				} else {
					this.iCocking = 1;
				}
				this.iNewVisDrums = (int) (this.emitter.countBullets() / 250F);
				if (this.iNewVisDrums < this.iOldVisDrums) {
					this.iOldVisDrums = this.iNewVisDrums;
					this.mesh.chunkVisible("DrumL1", this.iNewVisDrums > 1);
					this.mesh.chunkVisible("DrumL2", this.iNewVisDrums > 0);
					this.sfxClick(13);
				}
			} else {
				this.iCocking = 0;
			}
			this.resetYPRmodifier();
			Cockpit.xyz[0] = -0.07F * this.iCocking;
			this.mesh.chunkSetLocate("LeverL", Cockpit.xyz, Cockpit.ypr);
		}
	}

	public void moveGun(Orient orient) {
		super.moveGun(orient);
		this.mesh.chunkSetAngles("Body", -45F, 0.0F, 0.0F);
		this.mesh.chunkSetAngles("TurretLA", 0.0F, -orient.getYaw(), 0.0F);
		this.mesh.chunkSetAngles("TurretLB", 0.0F, orient.getTangage(), 0.0F);
	}

	public void reflectCockpitState() {
		if (this.fm.AS.astateCockpitState != 0) {
			this.mesh.chunkVisible("Holes_D1", true);
		}
	}

	public void toggleLight() {
		this.cockpitLightControl = !this.cockpitLightControl;
		if (this.cockpitLightControl) {
			this.mesh.chunkVisible("Flare", true);
			this.setNightMats(true);
		} else {
			this.mesh.chunkVisible("Flare", false);
			this.setNightMats(false);
		}
	}
}
