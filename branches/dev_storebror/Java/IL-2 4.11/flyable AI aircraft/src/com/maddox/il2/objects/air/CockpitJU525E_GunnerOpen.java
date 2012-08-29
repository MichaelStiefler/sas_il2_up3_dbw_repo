// Source File Name: CockpitJU525E_GunnerOpen.java
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

public class CockpitJU525E_GunnerOpen extends CockpitGunner {

	private Hook hook1;

	private boolean bNeedSetUp;

	static {
		Property.set(CockpitJU525E_GunnerOpen.class, "aiTuretNum", 0);
		Property.set(CockpitJU525E_GunnerOpen.class, "weaponControlNum", 10);
		Property.set(CockpitJU525E_GunnerOpen.class, "astatePilotIndx", 1);
	}

	public CockpitJU525E_GunnerOpen() {
		super("3DO/Cockpit/Ju52-TGun/hier.him", "he111_gunner");
		this.hook1 = null;
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
				if (f1 > 45F) {
					f1 = 45F;
				}
				if (f1 < -5F) {
					f1 = -5F;
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

	protected boolean doFocusEnter() {
		if (super.doFocusEnter()) {
			this.aircraft().hierMesh().chunkVisible("CF_D0", true);
			return true;
		} else {
			return false;
		}
	}

	protected void doFocusLeave() {
		this.aircraft().hierMesh().chunkVisible("CF_D0", true);
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
					this.hook1 = new HookNamed(this.aircraft(), "_MGUN01");
				}
				this.doHitMasterAircraft(this.aircraft(), this.hook1, "_MGUN01");
			}
		}
	}

	public void moveGun(Orient orient) {
		super.moveGun(orient);
		this.mesh.chunkSetAngles("TurretA", -orient.getYaw(), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("TurretB", 0.0F, 0.0F, -orient.getTangage());
	}

	protected void reflectPlaneMats() {
		HierMesh hiermesh = this.aircraft().hierMesh();
		com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh
				.materialFind("Gloss1D0o"));
		this.mesh.materialReplace("Gloss1D0o", mat);
		mat = hiermesh.material(hiermesh.materialFind("Gloss1D1o"));
		this.mesh.materialReplace("Gloss1D1o", mat);
		mat = hiermesh.material(hiermesh.materialFind("Gloss1D2o"));
		this.mesh.materialReplace("Gloss1D2o", mat);
	}

	public void reflectWorldToInstruments(float f) {
		if (this.bNeedSetUp) {
			this.reflectPlaneMats();
			this.bNeedSetUp = false;
		}
	}
}
