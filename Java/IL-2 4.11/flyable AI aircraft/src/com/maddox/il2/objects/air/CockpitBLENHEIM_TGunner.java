// Source File Name: CockpitBLENHEIM_TGunner.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-04-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class CockpitBLENHEIM_TGunner extends CockpitGunner {
	private boolean bNeedSetUp;
	private long prevTime;
	private float prevA0;
	private Hook hook1;

	protected boolean doFocusEnter() {
		if (super.doFocusEnter()) {
			aircraft().hierMesh().chunkVisible("Turret1B_D0", false);
			aircraft().hierMesh().chunkVisible("WireL_D0", false);
			return true;
		}

		return false;
	}

	protected void doFocusLeave() {
		aircraft().hierMesh().chunkVisible("Turret1B_D0", true);
		aircraft().hierMesh().chunkVisible("WireL_D0", true);
		super.doFocusLeave();
	}

	public void moveGun(Orient paramOrient) {
		super.moveGun(paramOrient);
		this.mesh.chunkSetAngles("Turret1A", -paramOrient.getYaw(), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Turret2B", 0.0F, paramOrient.getTangage(),
				0.0F);
		this.mesh.chunkSetAngles("Turret1B", 0.0F, paramOrient.getTangage(),
				0.0F);
	}

	public void clipAnglesGun(Orient paramOrient) {
		float f1 = paramOrient.getYaw();
		float f2 = paramOrient.getTangage();
		while (f1 < -180.0F)
			f1 += 360.0F;
		while (f1 > 180.0F)
			f1 -= 360.0F;
		while (this.prevA0 < -180.0F)
			this.prevA0 += 360.0F;
		while (this.prevA0 > 180.0F)
			this.prevA0 -= 360.0F;
		if (!isRealMode()) {
			this.prevA0 = f1;
		} else {
			if (this.bNeedSetUp) {
				this.prevTime = (Time.current() - 1L);
				this.bNeedSetUp = false;
			}
			if ((f1 < -120.0F) && (this.prevA0 > 120.0F)) {
				f1 += 360.0F;
			} else if ((f1 > 120.0F) && (this.prevA0 < -120.0F))
				this.prevA0 += 360.0F;
			float f4 = f1 - this.prevA0;
			float f5 = 0.001F * (float) (Time.current() - this.prevTime);
			float f6 = Math.abs(f4 / f5);
			if (f6 > 120.0F)
				if (f1 > this.prevA0) {
					f1 = this.prevA0 + 120.0F * f5;
				} else if (f1 < this.prevA0)
					f1 = this.prevA0 - 120.0F * f5;
			this.prevTime = Time.current();
			if (f2 > 73.0F)
				f2 = 73.0F;
			if (f2 < 0.0F)
				f2 = 0.0F;
			paramOrient.setYPR(f1, f2, 0.0F);
			paramOrient.wrap();
			this.prevA0 = f1;
		}
	}

	protected void interpTick() {
		if (isRealMode()) {
			if ((this.emitter == null) || (!this.emitter.haveBullets())
					|| (!aiTurret().bIsOperable))
				this.bGunFire = false;
			this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
			if (this.bGunFire) {
				if (this.hook1 == null)
					this.hook1 = new HookNamed(aircraft(), "_MGUN02");
				doHitMasterAircraft(aircraft(), this.hook1, "_MGUN02");
			}
		}
	}

	public void doGunFire(boolean paramBoolean) {
		if (isRealMode()) {
			if ((this.emitter == null) || (!this.emitter.haveBullets())
					|| (!aiTurret().bIsOperable))
				this.bGunFire = false;
			else
				this.bGunFire = paramBoolean;
			this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
		}
	}

	public void reflectCockpitState() {
		if ((this.fm.AS.astateCockpitState & 0x4) != 0)
			this.mesh.chunkVisible("Z_Holes1_D1", true);
		if ((this.fm.AS.astateCockpitState & 0x10) != 0)
			this.mesh.chunkVisible("Z_Holes2_D1", true);
	}

	public CockpitBLENHEIM_TGunner() {
		super("3DO/Cockpit/BLENHEIM-TGun/TGunnerBLENHEIM.him", "he111_gunner");
		this.bNeedSetUp = true;
		this.prevTime = -1L;
		this.prevA0 = 0.0F;
		this.hook1 = null;
	}

	protected void reflectPlaneMats() {
		HierMesh localHierMesh = aircraft().hierMesh();
		Mat localMat = localHierMesh.material(localHierMesh
				.materialFind("Gloss1D0o"));
		this.mesh.materialReplace("Gloss1D0o", localMat);
		localMat = localHierMesh.material(localHierMesh
				.materialFind("Gloss2D0o"));
		this.mesh.materialReplace("Gloss2D0o", localMat);
	}

	static {
		Property.set(CLASS.THIS(), "aiTuretNum", 0);
		Property.set(CLASS.THIS(), "weaponControlNum", 10);
		Property.set(CLASS.THIS(), "astatePilotIndx", 2);
	}
}