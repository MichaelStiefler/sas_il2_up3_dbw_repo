// Source File Name: CockpitCantZ_BGunner.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-04-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.*;
import com.maddox.il2.fm.*;
import com.maddox.rts.Property;

public class CockpitCantZ_BGunner extends CockpitGunner {

	protected boolean doFocusEnter() {
		if (super.doFocusEnter()) {
			aircraft().hierMesh().chunkVisible("Turret2B_D0", false);
			return true;
		} else {
			return false;
		}
	}

	protected void doFocusLeave() {
		aircraft().hierMesh().chunkVisible("Turret2B_D0", true);
		super.doFocusLeave();
	}

	public void moveGun(Orient orient) {
		super.moveGun(orient);
		super.mesh.chunkSetAngles("Turret1A", 30F, -orient.getYaw(), 0.0F);
		super.mesh.chunkSetAngles("Turret1B", 0.0F, orient.getTangage(), 0.0F);
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
				if (f1 > 45F)
					f1 = 45F;
				if (f1 < -25F)
					f1 = -25F;
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
					hook1 = new HookNamed(aircraft(), "_MGUN03");
				doHitMasterAircraft(aircraft(), hook1, "_MGUN03");
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

	public void reflectCockpitState() {
		if (((FlightModelMain) (super.fm)).AS.astateCockpitState != 0) {
			super.mesh.chunkVisible("XGlassDamage1", true);
			super.mesh.chunkVisible("XGlassDamage2", true);
			super.mesh.chunkVisible("XHullDamage1", true);
			super.mesh.chunkVisible("XHullDamage2", true);
			super.mesh.chunkVisible("XHullDamage3", true);
		}
	}

	public CockpitCantZ_BGunner() {
		super("3DO/Cockpit/CantZ-BGun/BGunnerCant.him", "he111_gunner");
		hook1 = null;
	}

	private Hook hook1;

	static {
		Property.set(com.maddox.il2.objects.air.CockpitCantZ_BGunner.class,
				"aiTuretNum", 1);
		Property.set(com.maddox.il2.objects.air.CockpitCantZ_BGunner.class,
				"weaponControlNum", 11);
		Property.set(com.maddox.il2.objects.air.CockpitCantZ_BGunner.class,
				"astatePilotIndx", 3);
	}
}
