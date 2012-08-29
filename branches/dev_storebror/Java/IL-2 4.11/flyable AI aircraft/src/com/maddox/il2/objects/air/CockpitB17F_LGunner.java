// Source File Name: CockpitB17F_LGunner.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Property;

public class CockpitB17F_LGunner extends CockpitGunner {

	private Hook hook1;

	static {
		Property.set(CockpitB17F_LGunner.class, "aiTuretNum", 5);
		Property.set(CockpitB17F_LGunner.class, "weaponControlNum", 16);
		Property.set(CockpitB17F_LGunner.class, "astatePilotIndx", 5);
	}

	public CockpitB17F_LGunner() {
		super("3DO/Cockpit/B-25J-LGun/LGunnerB17F.him", "he111_gunner");
		this.hook1 = null;
	}

	public void clipAnglesGun(Orient orient) {
		if (this.isRealMode()) {
			if (!this.aiTurret().bIsOperable) {
				orient.setYPR(0.0F, 0.0F, 0.0F);
			} else {
				float f = orient.getYaw();
				float f1 = orient.getTangage();
				if (f < -75F) {
					f = -75F;
				}
				if (f > 10F) {
					f = 10F;
				}
				if (f1 > 32F) {
					f1 = 32F;
				}
				if (f1 < -30F) {
					f1 = -30F;
				}
				orient.setYPR(f, f1, 0.0F);
				orient.wrap();
			}
		}
	}

	protected boolean doFocusEnter() {
		if (super.doFocusEnter()) {
			this.aircraft().hierMesh().chunkVisible("Tail1_D0", false);
			this.aircraft().hierMesh().chunkVisible("Turret6B_D0", false);
			this.aircraft().hierMesh().chunkVisible("Turret7B_D0", false);
			this.aircraft().hierMesh().chunkVisible("Pilot7_D0", false);
			this.aircraft().hierMesh().chunkVisible("Head7_D0", false);
			this.aircraft().hierMesh().chunkVisible("Pilot8_D0", false);
			this.aircraft().hierMesh().chunkVisible("Head8_D0", false);
			return true;
		} else {
			return false;
		}
	}

	protected void doFocusLeave() {
		this.aircraft().hierMesh().chunkVisible("Tail1_D0", true);
		this.aircraft().hierMesh().chunkVisible("Turret6B_D0", true);
		this.aircraft().hierMesh().chunkVisible("Turret7B_D0", true);
		this.aircraft().hierMesh().chunkVisible("Pilot7_D0", true);
		this.aircraft().hierMesh().chunkVisible("Head7_D0", true);
		this.aircraft().hierMesh().chunkVisible("Pilot8_D0", true);
		this.aircraft().hierMesh().chunkVisible("Head8_D0", true);
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
				if (this.hook1 == null) {
					this.hook1 = new HookNamed(this.aircraft(), "_MGUN12");
				}
				this.doHitMasterAircraft(this.aircraft(), this.hook1, "_MGUN12");
			}
		}
	}

	public void moveGun(Orient orient) {
		super.moveGun(orient);
		this.mesh.chunkSetAngles("TurretLA", 0.0F, -orient.getYaw(), 0.0F);
		this.mesh.chunkSetAngles("TurretLB", -43F, orient.getTangage(), 0.0F);
		this.mesh.chunkSetAngles("TurretLC", 0.0F, -orient.getTangage(), 0.0F);
	}

	public void reflectCockpitState() {
		if ((this.fm.AS.astateCockpitState & 4) != 0) {
			this.mesh.chunkVisible("XGlassDamage1", true);
		}
		if ((this.fm.AS.astateCockpitState & 8) != 0) {
			this.mesh.chunkVisible("XGlassDamage1", true);
			this.mesh.chunkVisible("XHullDamage1", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
			this.mesh.chunkVisible("XGlassDamage2", true);
			this.mesh.chunkVisible("XHullDamage2", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
			this.mesh.chunkVisible("XGlassDamage2", true);
			this.mesh.chunkVisible("XHullDamage3", true);
		}
	}

	public void reflectWorldToInstruments(float f) {
		this.mesh.chunkSetAngles("TurretRA", 0.0F,
				this.aircraft().FM.turret[3].tu[0], 0.0F);
		this.mesh.chunkSetAngles("TurretRB", 0.0F,
				this.aircraft().FM.turret[3].tu[1], 0.0F);
		this.mesh.chunkSetAngles("TurretRC", 0.0F,
				this.aircraft().FM.turret[3].tu[1], 0.0F);
		this.mesh.chunkVisible("TurretRC", false);
	}
}
