// Source File Name: CockpitFW200_TGunner.java
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

public class CockpitFW200_TGunner extends CockpitGunner {

	private boolean bNeedSetUp;

	private Hook hook1;

	private int iCocking;

	private int iOldVisDrums;

	private int iNewVisDrums;

	static {
		Property.set(CockpitFW200_TGunner.class, "aiTuretNum", 2);
		Property.set(CockpitFW200_TGunner.class, "weaponControlNum", 12);
		Property.set(CockpitFW200_TGunner.class, "astatePilotIndx", 3);
	}

	public CockpitFW200_TGunner() {
		super("3DO/Cockpit/He-111H-2-TGun/TGunnerFW200.him", "he111_gunner");
		this.bNeedSetUp = true;
		this.hook1 = null;
		this.iCocking = 0;
		this.iOldVisDrums = 3;
		this.iNewVisDrums = 3;
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
					this.hook1 = new HookNamed(this.aircraft(), "_MGUN02");
				}
				this.doHitMasterAircraft(this.aircraft(), this.hook1, "_MGUN02");
				if (this.iCocking > 0) {
					this.iCocking = 0;
				} else {
					this.iCocking = 1;
				}
			} else {
				this.iCocking = 0;
			}
			this.iNewVisDrums = (int) (this.emitter.countBullets() / 333F);
			if (this.iNewVisDrums < this.iOldVisDrums) {
				this.iOldVisDrums = this.iNewVisDrums;
				this.mesh.chunkVisible("Drum1", this.iNewVisDrums > 2);
				this.mesh.chunkVisible("Drum2", this.iNewVisDrums > 1);
				this.mesh.chunkVisible("Drum3", this.iNewVisDrums > 0);
				this.sfxClick(13);
			}
			this.mesh.chunkSetAngles("CockingLever", -0.75F * this.iCocking,
					0.0F, 0.0F);
		}
	}

	public void moveGun(Orient orient) {
		super.moveGun(orient);
		this.mesh.chunkSetAngles("TurretA", -orient.getYaw(), 0.0F, 3F);
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
