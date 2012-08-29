// Source File Name: CockpitCantZ_TGunner.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-04-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.*;
import com.maddox.il2.fm.*;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class CockpitCantZ_TGunner extends CockpitGunner {

	protected boolean doFocusEnter() {
		if (super.doFocusEnter()) {
			aircraft().hierMesh().chunkVisible("Turret1B_D0", false);
			aircraft().hierMesh().chunkVisible("Turret1C_D0", false);
			return true;
		} else {
			return false;
		}
	}

	protected void doFocusLeave() {
		aircraft().hierMesh().chunkVisible("Turret1B_D0", true);
		aircraft().hierMesh().chunkVisible("Turret1C_D0", true);
		super.doFocusLeave();
	}

	public void moveGun(Orient orient) {
		super.moveGun(orient);
		super.mesh.chunkSetAngles("TurrelA", 0.0F, orient.getYaw(), 0.0F);
		super.mesh.chunkSetAngles("TurrelB", 0.0F, orient.getTangage(), 0.0F);
	}

	public void clipAnglesGun(Orient orient) {
		if (isRealMode())
			if (!aiTurret().bIsOperable) {
				orient.setYPR(0.0F, 0.0F, 0.0F);
			} else {
				float f = orient.getYaw();
				float f1 = orient.getTangage();
				if (f1 > 80F)
					f1 = 80F;
				if (f1 < -2F)
					f1 = -2F;
				orient.setYPR(f, f1, 0.0F);
				orient.wrap();
			}
	}

	protected void interpTick() {
		if (isRealMode()) {
			if (super.emitter == null || !super.emitter.haveBullets()
					|| !aiTurret().bIsOperable)
				super.bGunFire = false;
			((FlightModelMain) (super.fm)).CT.WeaponControl[weaponControlNum()] = super.bGunFire;
			if (super.bGunFire) {
				if (hook1 == null)
					hook1 = new HookNamed(aircraft(), "_MGUN02");
				doHitMasterAircraft(aircraft(), hook1, "_MGUN02");
			}
		}
	}

	public void doGunFire(boolean flag) {
		if (isRealMode()) {
			if (super.emitter == null || !super.emitter.haveBullets()
					|| !aiTurret().bIsOperable)
				super.bGunFire = false;
			else
				super.bGunFire = flag;
			((FlightModelMain) (super.fm)).CT.WeaponControl[weaponControlNum()] = super.bGunFire;
		}
	}

	public CockpitCantZ_TGunner() {
		super("3DO/Cockpit/CantZ-TGun/Cant-TGun.him", "bf109");
		hook1 = null;
	}

	private Hook hook1;

	static {
		Property.set(CLASS.THIS(), "aiTuretNum", 0);
		Property.set(CLASS.THIS(), "weaponControlNum", 10);
		Property.set(CLASS.THIS(), "astatePilotIndx", 2);
	}
}
