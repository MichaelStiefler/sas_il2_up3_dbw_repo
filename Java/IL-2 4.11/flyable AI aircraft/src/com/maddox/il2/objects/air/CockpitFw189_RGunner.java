// Source File Name: CockpitFw189_RGunner.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class CockpitFw189_RGunner extends CockpitGunner {

	private boolean bNeedSetUp;

	private Hook hook1;

	private Hook hook2;

	static {
		Property.set(CLASS.THIS(), "aiTuretNum", 1);
		Property.set(CLASS.THIS(), "weaponControlNum", 11);
		Property.set(CLASS.THIS(), "astatePilotIndx", 1);
	}

	public CockpitFw189_RGunner() {
		super("3DO/Cockpit/He-111H-2/RGunnerFw189.him", "bf109");
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
				if (f < -35F) {
					f = -35F;
				}
				if (f > 35F) {
					f = 35F;
				}
				if (f1 > 45F) {
					f1 = 45F;
				}
				if (f1 < 0.0F) {
					f1 = 0.0F;
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
			this.fm.CT.WeaponControl[11] = this.bGunFire;
		}
	}

	protected void interpTick() {
		if (this.isRealMode()) {
			if ((this.emitter == null) || !this.emitter.haveBullets()
					|| !this.aiTurret().bIsOperable) {
				this.bGunFire = false;
			}
			this.fm.CT.WeaponControl[11] = this.bGunFire;
			if (this.bGunFire) {
				if (this.hook1 == null) {
					this.hook1 = new HookNamed(this.aircraft(), "_MGUN05");
				}
				this.doHitMasterAircraft(this.aircraft(), this.hook1, "_MGUN05");
				if (this.hook2 == null) {
					this.hook2 = new HookNamed(this.aircraft(), "_MGUN06");
				}
				this.doHitMasterAircraft(this.aircraft(), this.hook2, "_MGUN06");
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

	protected void reflectPlaneMats() {
		HierMesh hiermesh = this.aircraft().hierMesh();
		com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh
				.materialFind("Gloss1D0o"));
		this.mesh.materialReplace("Gloss1D0o", mat);
	}

	public void reflectWorldToInstruments(float f) {
		if (this.bNeedSetUp) {
			this.reflectPlaneMats();
			this.bNeedSetUp = false;
		}
	}
}
