// Source File Name: CockpitPBN1_LGunner.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class CockpitPBN1_LGunner extends CockpitGunner {

	static {
		Property.set(CLASS.THIS(), "aiTuretNum", 1);
		Property.set(CLASS.THIS(), "weaponControlNum", 11);
		Property.set(CLASS.THIS(), "astatePilotIndx", 3);
	}

	public CockpitPBN1_LGunner() {
		super("3DO/Cockpit/PBN1-LGun/hier.him", "he111_gunner");
	}

	public void clipAnglesGun(Orient orient) {
		if (!this.isRealMode()) {
			return;
		}
		if (!this.aiTurret().bIsOperable) {
			orient.setYPR(0.0F, 0.0F, 0.0F);
			return;
		}
		float f = orient.getYaw();
		float f1 = orient.getTangage();
		if (f < -75F) {
			f = -75F;
		}
		if (f > 45F) {
			f = 45F;
		}
		if (f1 > 39F) {
			f1 = 39F;
		}
		if (f1 < -20F) {
			f1 = -20F;
		}
		orient.setYPR(f, f1, 0.0F);
		orient.wrap();
	}

	public void doGunFire(boolean flag) {
		if (!this.isRealMode()) {
			return;
		}
		if ((this.emitter == null) || !this.emitter.haveBullets()
				|| !this.aiTurret().bIsOperable) {
			this.bGunFire = false;
		} else {
			this.bGunFire = flag;
		}
		this.fm.CT.WeaponControl[this.weaponControlNum()] = this.bGunFire;
	}

	public void moveGun(Orient orient) {
		super.moveGun(orient);
		float f = -orient.getYaw();
		float f1 = orient.getTangage();
		this.mesh.chunkSetAngles("Turret1A", 0.0F, f, 0.0F);
		this.mesh.chunkSetAngles("Turret1B", 0.0F, f1, 0.0F);
	}

	protected void reflectPlaneMats() {
		HierMesh hiermesh = this.aircraft().hierMesh();
		com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh
				.materialFind("Gloss1D0o"));
		this.mesh.materialReplace("Gloss1D0o", mat);
	}
}
