// Source File Name: CockpitTBX1_BGunner.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-04-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.*;
import com.maddox.rts.Property;

public class CockpitTBX1_BGunner extends CockpitGunner {

	public void moveGun(Orient orient) {
		super.moveGun(orient);
		mesh.chunkSetAngles("Turret1A", -14.2F, -orient.getYaw(), 0.0F);
		mesh.chunkSetAngles("Turret1B", 0.0F, orient.getTangage(), 0.0F);
	}

	public void clipAnglesGun(Orient orient) {
		if (isRealMode())
			if (!aiTurret().bIsOperable) {
				orient.setYPR(0.0F, 0.0F, 0.0F);
			} else {
				float f = orient.getYaw();
				float f1 = orient.getTangage();
				if (f < -40F)
					f = -40F;
				if (f > 40F)
					f = 40F;
				if (f1 > -10F)
					f1 = -10F;
				if (f1 < -65F)
					f1 = -65F;
				orient.setYPR(f, f1, 0.0F);
				orient.wrap();
			}
	}

	protected void interpTick() {
		if (isRealMode()) {
			if (emitter == null || !emitter.haveBullets()
					|| !aiTurret().bIsOperable)
				bGunFire = false;
			fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
		}
	}

	public void doGunFire(boolean flag) {
		if (isRealMode()) {
			if (emitter == null || !emitter.haveBullets()
					|| !aiTurret().bIsOperable)
				bGunFire = false;
			else
				bGunFire = flag;
			fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
		}
	}

	public void reflectCockpitState() {
		if (fm.AS.astateCockpitState != 0) {
			mesh.chunkVisible("XGlassDamage1", true);
			mesh.chunkVisible("XGlassDamage2", true);
			mesh.chunkVisible("XHullDamage1", true);
			mesh.chunkVisible("XHullDamage2", true);
			mesh.chunkVisible("XHullDamage3", true);
		}
	}

	public CockpitTBX1_BGunner() {
		super("3DO/Cockpit/A-20G-BGun/BGunnerTBX1.him", "he111_gunner");
	}

	static {
		Property.set(com.maddox.il2.objects.air.CockpitTBX1_BGunner.class,
				"aiTuretNum", 1);
		Property.set(com.maddox.il2.objects.air.CockpitTBX1_BGunner.class,
				"weaponControlNum", 11);
		Property.set(com.maddox.il2.objects.air.CockpitTBX1_BGunner.class,
				"astatePilotIndx", 2);
	}
}
