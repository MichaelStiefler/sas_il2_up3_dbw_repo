// Source File Name: CockpitMBR_2AM34_NGunner.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Property;

public class CockpitMBR_2AM34_NGunner extends CockpitGunner {

	private Hook hook1;

	private Hook hook2;

	static {
		Property.set(CockpitMBR_2AM34_NGunner.class, "aiTuretNum", 0);
		Property.set(CockpitMBR_2AM34_NGunner.class, "weaponControlNum", 10);
		Property.set(CockpitMBR_2AM34_NGunner.class, "astatePilotIndx", 2);
	}

	public CockpitMBR_2AM34_NGunner() {
		super("3DO/Cockpit/TB-3-NGun/NGunnerMBR2AM34.him", "i16");
		this.hook1 = null;
		this.hook2 = null;
		BulletEmitter abulletemitter[] = this.aircraft().FM.CT.Weapons[this
				.weaponControlNum()];
		if (abulletemitter != null) {
			for (int i = 0; i < abulletemitter.length; i++) {
				if (abulletemitter[i] instanceof Actor) {
					((Actor) abulletemitter[i]).visibilityAsBase(false);
				}
			}

		}
	}

	public void clipAnglesGun(Orient orient) {
		if (this.isRealMode()) {
			if (!this.aiTurret().bIsOperable) {
				orient.setYPR(0.0F, 0.0F, 0.0F);
			} else {
				float f = orient.getYaw();
				float f1 = orient.getTangage();
				float f2 = Math.abs(f);
				if (f1 < -47F) {
					f1 = -47F;
				}
				if (f1 > 47F) {
					f1 = 47F;
				}
				if (f2 < 147F) {
					if (f1 < ((0.5964912F * f2) - 117.6842F)) {
						f1 = (0.5964912F * f2) - 117.6842F;
					}
				} else if (f2 < 157F) {
					if (f1 < ((0.3F * f2) - 74.1F)) {
						f1 = (0.3F * f2) - 74.1F;
					}
				} else if (f1 < ((0.2173913F * f2) - 61.13044F)) {
					f1 = (0.2173913F * f2) - 61.13044F;
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
		float f = -orient.getYaw();
		float f1 = orient.getTangage();
		this.mesh.chunkSetAngles("Turret1A", f, 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Turret1B", 0.0F, f1, 0.0F);
		this.mesh.chunkSetAngles("Turret2B", 0.0F, f1, 0.0F);
		this.mesh.chunkSetAngles("Turret1C", 0.0F, -f, 0.0F);
		float f2 = 0.01905F * (float) Math.sin(Math.toRadians(f));
		float f3 = 0.01905F * (float) Math.cos(Math.toRadians(f));
		f = (float) Math.toDegrees(Math.atan(f2 / (f3 + 0.3565F)));
		this.mesh.chunkSetAngles("Turret1D", 0.0F, f, 0.0F);
	}
}
