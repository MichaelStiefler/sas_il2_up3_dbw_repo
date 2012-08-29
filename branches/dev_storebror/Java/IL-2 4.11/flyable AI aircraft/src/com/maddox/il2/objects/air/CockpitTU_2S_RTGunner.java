// Source File Name: CockpitTU_2S_RTGunner.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class CockpitTU_2S_RTGunner extends CockpitGunner {

	private Hook hook1;

	static {
		Property.set(CLASS.THIS(), "aiTuretNum", 1);
		Property.set(CLASS.THIS(), "weaponControlNum", 11);
		Property.set(CLASS.THIS(), "astatePilotIndx", 2);
	}

	public CockpitTU_2S_RTGunner() {
		super("3do/cockpit/Il-2-Gun/RTGunnerTU2S.him", "il2rear");
		this.hook1 = null;
	}

	public void clipAnglesGun(Orient orient) {
		if (this.isRealMode()) {
			if (!this.aiTurret().bIsOperable) {
				orient.setYPR(0.0F, 0.0F, 0.0F);
			} else {
				float f = orient.getYaw();
				float f1 = orient.getTangage();
				if (f < -45F) {
					f = -45F;
				}
				if (f > 45F) {
					f = 45F;
				}
				if (f1 > 45F) {
					f1 = 45F;
				}
				if (f1 < -12F) {
					f1 = -12F;
				}
				if (Math.abs(f) < 3.5F) {
					if (f1 < -2.5F) {
						f1 = -2.5F;
					}
				} else if ((Math.abs(f) < 18.5F)
						&& (f1 < (-2.5F - (0.6333333F * (Math.abs(f) - 3.5F))))) {
					f1 = -2.5F - (0.6333333F * (Math.abs(f) - 3.5F));
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
					this.hook1 = new HookNamed(this.aircraft(), "_MGUN03");
				}
				this.doHitMasterAircraft(this.aircraft(), this.hook1, "_MGUN03");
			}
		}
	}

	public void moveGun(Orient orient) {
		super.moveGun(orient);
		this.mesh.chunkSetAngles("TurrelA", 0.0F, orient.getYaw(), 0.0F);
		this.mesh.chunkSetAngles("TurrelB", 0.0F, orient.getTangage(), 0.0F);
	}
}
