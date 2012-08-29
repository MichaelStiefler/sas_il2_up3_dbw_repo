// Source File Name: CockpitB_24J100_FGunner.java
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

public class CockpitB_24J100_FGunner extends CockpitGunner {

	private Hook hook1;

	private Hook hook2;

	static {
		Property.set(CockpitB_24J100_FGunner.class, "aiTuretNum", 0);
		Property.set(CockpitB_24J100_FGunner.class, "weaponControlNum", 10);
		Property.set(CockpitB_24J100_FGunner.class, "astatePilotIndx", 2);
	}

	public CockpitB_24J100_FGunner() {
		super("3DO/Cockpit/ConsolidatedA6CTurret/B24J100FGunner.him", "bf109");
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
				if (f < -38F) {
					f = -38F;
				}
				if (f > 38F) {
					f = 38F;
				}
				if (f1 > 38F) {
					f1 = 38F;
				}
				if (f1 < -41F) {
					f1 = -41F;
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
		this.mesh.chunkSetAngles("Body", 180F, 0.0F, 0.0F);
		this.mesh.chunkSetAngles("TurretA", 0.0F, -orient.getYaw(), 0.0F);
		this.mesh.chunkSetAngles("TurretB", 0.0F, orient.getTangage(), 0.0F);
		this.mesh.chunkSetAngles("TurretC", 0.0F,
				this.cvt(orient.getYaw(), -38F, 38F, -15F, 15F), 0.0F);
		this.mesh.chunkSetAngles("TurretE", -orient.getYaw(), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("TurretF", 0.0F, orient.getTangage(), 0.0F);
		this.mesh.chunkSetAngles("TurretG",
				-this.cvt(orient.getYaw(), -33F, 33F, -33F, 33F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("TurretH", 0.0F,
				this.cvt(orient.getTangage(), -10F, 32F, -10F, 32F), 0.0F);
		this.resetYPRmodifier();
		Cockpit.xyz[0] = this.cvt(
				Math.max(Math.abs(orient.getYaw()),
						Math.abs(orient.getTangage())), 0.0F, 20F, 0.0F, 0.3F);
		this.mesh.chunkSetLocate("TurretI", Cockpit.xyz, Cockpit.ypr);
	}

	protected void reflectPlaneMats() {
		HierMesh hiermesh = this.aircraft().hierMesh();
		com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh
				.materialFind("Gloss1D0o"));
		this.mesh.materialReplace("Gloss1D0o", mat);
	}
}
