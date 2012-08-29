// Source File Name: CockpitCantZ_RGunner.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-04-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.*;
import com.maddox.il2.fm.*;
import com.maddox.rts.Property;

public class CockpitCantZ_RGunner extends CockpitGunner {

	public void moveGun(Orient orient) {
		super.moveGun(orient);
		super.mesh.chunkSetAngles("Turret4A", 0.0F, orient.getYaw(), 0.0F);
		super.mesh.chunkSetAngles("Turret4B", 0.0F, orient.getTangage(), 0.0F);
	}

	public void clipAnglesGun(Orient orient) {
		if (!isRealMode())
			return;
		if (!aiTurret().bIsOperable) {
			orient.setYPR(0.0F, 0.0F, 0.0F);
			return;
		}
		float f = orient.getYaw();
		float f1 = orient.getTangage();
		if (f < -63F)
			f = -63F;
		if (f > 17F)
			f = 17F;
		if (f1 < -45F)
			f1 = -45F;
		if (f1 > 45F)
			f1 = 45F;
		if (f < -30F) {
			if (f1 < cvt(f, -60F, -30F, -10F, -16F))
				f1 = cvt(f, -60F, -30F, -10F, -16F);
		} else if (f < 0.0F) {
			if (f1 < cvt(f, -30F, 0.0F, -16F, -23F))
				f1 = cvt(f, -30F, 0.0F, -16F, -23F);
			if (f1 > cvt(f, -10F, 0.0F, 45F, 33F))
				f1 = cvt(f, -10F, 0.0F, 45F, 33F);
		} else {
			if (f1 < cvt(f, 0.0F, 30F, -23F, -6F))
				f1 = cvt(f, 0.0F, 30F, -23F, -6F);
			if (f1 > cvt(f, 0.0F, 30F, 33F, 22F))
				f1 = cvt(f, 0.0F, 30F, 33F, 22F);
		}
		orient.setYPR(f, f1, 0.0F);
		orient.wrap();
	}

	protected void interpTick() {
		if (!isRealMode())
			return;
		if (super.emitter == null || !super.emitter.haveBullets()
				|| !aiTurret().bIsOperable)
			super.bGunFire = false;
		((FlightModelMain) (super.fm)).CT.WeaponControl[weaponControlNum()] = super.bGunFire;
		if (super.bGunFire) {
			if (hook1 == null)
				hook1 = new HookNamed(aircraft(), "_MGUN04");
			doHitMasterAircraft(aircraft(), hook1, "_MGUN04");
			if (iCocking > 0)
				iCocking = 0;
			else
				iCocking = 1;
			iNewVisDrums = (int) ((float) super.emitter.countBullets() / 250F);
			if (iNewVisDrums < iOldVisDrums) {
				iOldVisDrums = iNewVisDrums;
				super.mesh.chunkVisible("DrumR1", iNewVisDrums > 1);
				super.mesh.chunkVisible("DrumR2", iNewVisDrums > 0);
				sfxClick(13);
			}
		} else {
			iCocking = 0;
		}
		resetYPRmodifier();
		Cockpit.xyz[0] = -0.07F * (float) iCocking;
		super.mesh.chunkSetLocate("LeverR", Cockpit.xyz, Cockpit.ypr);
	}

	public void doGunFire(boolean flag) {
		if (!isRealMode())
			return;
		if (super.emitter == null || !super.emitter.haveBullets()
				|| !aiTurret().bIsOperable)
			super.bGunFire = false;
		else
			super.bGunFire = flag;
		((FlightModelMain) (super.fm)).CT.WeaponControl[weaponControlNum()] = super.bGunFire;
	}

	public CockpitCantZ_RGunner() {
		super("3DO/Cockpit/CantZ-RGun/hier.him", "he111_gunner");
		hook1 = null;
		iCocking = 0;
		iOldVisDrums = 2;
		iNewVisDrums = 2;
	}

	public void toggleLight() {
		super.cockpitLightControl = !super.cockpitLightControl;
		if (super.cockpitLightControl) {
			super.mesh.chunkVisible("Flare", true);
			setNightMats(true);
		} else {
			super.mesh.chunkVisible("Flare", false);
			setNightMats(false);
		}
	}

	public void reflectCockpitState() {
		if (((FlightModelMain) (super.fm)).AS.astateCockpitState != 0)
			super.mesh.chunkVisible("Holes_D1", true);
	}

	private Hook hook1;
	private int iCocking;
	private int iOldVisDrums;
	private int iNewVisDrums;

	static {
		Property.set(com.maddox.il2.objects.air.CockpitCantZ_RGunner.class,
				"aiTuretNum", 0);
		Property.set(com.maddox.il2.objects.air.CockpitCantZ_RGunner.class,
				"weaponControlNum", 13);
		Property.set(com.maddox.il2.objects.air.CockpitCantZ_RGunner.class,
				"astatePilotIndx", 4);
	}
}
