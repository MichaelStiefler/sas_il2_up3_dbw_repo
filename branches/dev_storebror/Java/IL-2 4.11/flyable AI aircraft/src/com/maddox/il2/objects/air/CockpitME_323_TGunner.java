// Source File Name: CockpitME_323_TGunner.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Property;

public class CockpitME_323_TGunner extends CockpitGunner {

	private boolean bNeedSetUp;

	private Hook hook1;

	private Hook hook2;

	static {
		Property.set(CockpitME_323_TGunner.class, "aiTuretNum", 6);
		Property.set(CockpitME_323_TGunner.class, "weaponControlNum", 16);
		Property.set(CockpitME_323_TGunner.class, "astatePilotIndx", 3);
	}

	public CockpitME_323_TGunner() {
		super("3do/Cockpit/B-25J-TGun/TGunnerME323.him", "bf109");
		this.bNeedSetUp = true;
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
				if (f < -42F) {
					f = -42F;
				}
				if (f > 42F) {
					f = 42F;
				}
				if (f1 > 60F) {
					f1 = 60F;
				}
				if (f1 < -3F) {
					f1 = -3F;
				}
				orient.setYPR(f, f1, 0.0F);
				orient.wrap();
			}
		}
	}

	protected boolean doFocusEnter() {
		if (super.doFocusEnter()) {
			this.aircraft().hierMesh().chunkVisible("Turret2B_D0", false);
			return true;
		} else {
			return false;
		}
	}

	protected void doFocusLeave() {
		this.aircraft()
				.hierMesh()
				.chunkVisible(
						"Turret2B_D0",
						this.aircraft().hierMesh()
								.isChunkVisible("Turret2A_D0"));
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
		this.mesh.chunkSetAngles("TurretA", 0.0F, -orient.getYaw(), 0.0F);
		this.mesh.chunkSetAngles("TurretB", 0.0F, orient.getTangage(), 0.0F);
		this.mesh.chunkSetAngles("TurretC", 0.0F,
				this.cvt(orient.getTangage(), -10F, 58F, -10F, 58F), 0.0F);
	}

	public void reflectCockpitState() {
		if ((this.fm.AS.astateCockpitState & 4) != 0) {
			this.mesh.chunkVisible("XGlassDamage1", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
			this.mesh.chunkVisible("XGlassDamage2", true);
		}
	}

	protected void reflectPlaneMats() {
		HierMesh hiermesh = this.aircraft().hierMesh();
		com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh
				.materialFind("Gloss1D0o"));
		this.mesh.materialReplace("Gloss1D0o", mat);
	}

	public void reflectWorldToInstruments(float f) {
		if (this.bNeedSetUp) {
			this.bNeedSetUp = false;
			this.reflectPlaneMats();
		}
	}
}
