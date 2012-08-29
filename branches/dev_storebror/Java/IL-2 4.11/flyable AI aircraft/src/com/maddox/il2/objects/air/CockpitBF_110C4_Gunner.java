// Source File Name: CockpitBF_110C4_Gunner.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FMMath;

public class CockpitBF_110C4_Gunner extends CockpitGunner {
	class Interpolater extends InterpolateRef {

		Interpolater() {
		}

		public boolean tick() {
			CockpitBF_110C4_Gunner.this.fm = World.getPlayerFM();
			if (CockpitBF_110C4_Gunner.this.fm == null) {
				return true;
			}
			if (CockpitBF_110C4_Gunner.this.bNeedSetUp) {
				CockpitBF_110C4_Gunner.this.reflectPlaneMats();
				CockpitBF_110C4_Gunner.this.bNeedSetUp = false;
			}
			return true;
		}
	}

	private boolean bNeedSetUp;
	private Hook hook1;
	private Hook hook2;

	public CockpitBF_110C4_Gunner() {
		super("3DO/Cockpit/Bf-110C4-Gun/hier.him", "bf109");
		this.bNeedSetUp = true;
		this.hook1 = null;
		this.hook2 = null;
	}

	public void clipAnglesGun(Orient orient) {
		if (this.isRealMode()) {
			if (!this.aiTurret().bIsOperable) {
				orient.setYPR(0.0F, 0.0F, 0.0F);
			} else {
				float f = -orient.getYaw();
				float f1 = orient.getTangage();
				if (f1 < -19F) {
					f1 = -19F;
				}
				if (f1 > 30F) {
					f1 = 30F;
				}
				float f2;
				if (f1 < 0.0F) {
					f2 = this.cvt(f1, -19F, 0.0F, 20F, 30F);
				} else if (f1 < 12F) {
					f2 = this.cvt(f1, 0.0F, 12F, 30F, 35F);
				} else {
					f2 = this.cvt(f1, 12F, 30F, 35F, 40F);
				}
				if (f < 0.0F) {
					if (f < -f2) {
						f = -f2;
					}
				} else if (f > f2) {
					f = f2;
				}
				orient.setYPR(-f, f1, 0.0F);
				orient.wrap();
			}
		}
	}

	protected boolean doFocusEnter() {
		if (super.doFocusEnter()) {
			this.aircraft().hierMesh().chunkVisible("Interior_D0", false);
			return true;
		} else {
			return false;
		}
	}

	protected void doFocusLeave() {
		this.aircraft().hierMesh().chunkVisible("Interior_D0", true);
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
			this.fm.CT.WeaponControl[10] = this.bGunFire;
		}
	}

	protected void interpTick() {
		if (this.isRealMode()) {
			if ((this.emitter == null) || !this.emitter.haveBullets()
					|| !this.aiTurret().bIsOperable) {
				this.bGunFire = false;
			}
			this.fm.CT.WeaponControl[10] = this.bGunFire;
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
		float f1 = orient.getTangage();
		this.mesh.chunkSetAngles("TurretA", -f, 0.0F, 0.0F);
		this.mesh.chunkSetAngles("TurretB", 0.0F, 0.0F, -f1 + -15F);
		this.mesh.chunkSetAngles(
				"TurretC",
				0.0F,
				-FMMath.clamp(f, -this.cvt(f1, -19F, 12F, 5F, 35F),
						this.cvt(f1, -19F, 12F, 5F, 35F)), 0.0F);
		this.mesh.chunkSetAngles("TurretD", 0.0F, f1, 0.0F);
	}

	protected void reflectPlaneMats() {
		HierMesh hiermesh = this.aircraft().hierMesh();
		com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh
				.materialFind("Gloss1D0o"));
		this.mesh.materialReplace("Gloss1D0o", mat);
		mat = hiermesh.material(hiermesh.materialFind("Gloss2D0o"));
		this.mesh.materialReplace("Gloss2D0o", mat);
		mat = hiermesh.material(hiermesh.materialFind("Pilot1"));
		this.mesh.materialReplace("Pilot1", mat);
	}

	public void reflectWorldToInstruments(float f) {
		if (this.fm != null) {
			if (this.bNeedSetUp) {
				this.reflectPlaneMats();
				this.bNeedSetUp = false;
			}
			this.mesh.chunkVisible("Head1_D0", this.aircraft().hierMesh()
					.isChunkVisible("Pilot1_D0"));
			this.mesh.chunkVisible("Head1_D1", this.aircraft().hierMesh()
					.isChunkVisible("Pilot1_D1"));
		}
	}

}
