// Source File Name: CockpitDo217_RGunner.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-04-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.*;
import com.maddox.rts.Property;

public class CockpitDo217_RGunner extends CockpitGunner {

	protected boolean doFocusEnter() {
		if (super.doFocusEnter()) {
			((Do217) fm.actor).bPitUnfocused = false;
			aircraft().hierMesh().chunkVisible("Interior1_D0", false);
			aircraft().hierMesh().chunkVisible("Pilot1_D0", false);
			aircraft().hierMesh().chunkVisible("Head1_D0", false);
			aircraft().hierMesh().chunkVisible("Hmask1_D0", false);
			aircraft().hierMesh().chunkVisible("Pilot2_D0", false);
			aircraft().hierMesh().chunkVisible("Hmask2_D0", false);
			aircraft().hierMesh().chunkVisible("Pilot3_D0", false);
			aircraft().hierMesh().chunkVisible("Hmask3_D0", false);
			aircraft().hierMesh().chunkVisible("Pilot4_D0", false);
			aircraft().hierMesh().chunkVisible("Hmask4_D0", false);
			aircraft().hierMesh().chunkVisible("Pilot1_D1", false);
			aircraft().hierMesh().chunkVisible("Pilot2_D1", false);
			aircraft().hierMesh().chunkVisible("Pilot3_D1", false);
			aircraft().hierMesh().chunkVisible("Pilot4_D1", false);
			aircraft().hierMesh().chunkVisible("Turret1B_D0", false);
			aircraft().hierMesh().chunkVisible("Turret3B_D0", false);
			aircraft().hierMesh().chunkVisible("Turret2A_D0", false);
			aircraft().hierMesh().chunkVisible("Turret2B_D0", false);
			aircraft().hierMesh().chunkVisible("Turret4B_D0", false);
			aircraft().hierMesh().chunkVisible("Turret5B_D0", false);
			return true;
		} else {
			return false;
		}
	}

	protected void doFocusLeave() {
		if (isFocused()) {
			((Do217) fm.actor).bPitUnfocused = true;
			aircraft().hierMesh().chunkVisible("Interior1_D0", true);
			if (!fm.AS.isPilotParatrooper(0)) {
				aircraft().hierMesh().chunkVisible("Pilot1_D0",
						!fm.AS.isPilotDead(0));
				aircraft().hierMesh().chunkVisible("Head1_D0",
						!fm.AS.isPilotDead(0));
				aircraft().hierMesh().chunkVisible("Pilot1_D1",
						fm.AS.isPilotDead(0));
			}
			if (!fm.AS.isPilotParatrooper(1)) {
				aircraft().hierMesh().chunkVisible("Pilot2_D0",
						!fm.AS.isPilotDead(1));
				aircraft().hierMesh().chunkVisible("Pilot2_D1",
						fm.AS.isPilotDead(1));
			}
			if (!fm.AS.isPilotParatrooper(2)) {
				aircraft().hierMesh().chunkVisible("Pilot3_D0",
						!fm.AS.isPilotDead(2));
				aircraft().hierMesh().chunkVisible("Pilot3_D1",
						fm.AS.isPilotDead(2));
			}
			if (!fm.AS.isPilotParatrooper(3)) {
				aircraft().hierMesh().chunkVisible("Pilot4_D0",
						!fm.AS.isPilotDead(3));
				aircraft().hierMesh().chunkVisible("Pilot4_D1",
						fm.AS.isPilotDead(3));
			}
			aircraft().hierMesh().chunkVisible("Turret1B_D0", true);
			aircraft().hierMesh().chunkVisible("Turret2A_D0", true);
			aircraft().hierMesh().chunkVisible("Turret2B_D0", true);
			aircraft().hierMesh().chunkVisible("Turret3B_D0", true);
			aircraft().hierMesh().chunkVisible("Turret4B_D0", true);
			aircraft().hierMesh().chunkVisible("Turret5B_D0", true);
			super.doFocusLeave();
		}
	}

	public void reflectWorldToInstruments(float f) {
		if (bNeedSetUp) {
			reflectPlaneMats();
			bNeedSetUp = false;
		}
		mesh.chunkSetAngles("Z_turret1A", 0.0F, -fm.turret[0].tu[0], 0.0F);
		mesh.chunkSetAngles("Z_turret1B", 0.0F, fm.turret[0].tu[1], 0.0F);
		mesh.chunkSetAngles("Z_turret2A", 0.0F, -fm.turret[1].tu[0], 0.0F);
		mesh.chunkSetAngles("Z_turret2B", 0.0F, fm.turret[1].tu[1], 0.0F);
		mesh.chunkSetAngles("Z_turret3A", 0.0F, -fm.turret[2].tu[0], 0.0F);
		mesh.chunkSetAngles("Z_turret3B", 0.0F, fm.turret[2].tu[1], 0.0F);
		mesh.chunkSetAngles("Z_turret4A", 0.0F, -fm.turret[3].tu[0], 0.0F);
		mesh.chunkSetAngles("Z_turret4B", 0.0F, fm.turret[3].tu[1], 0.0F);
	}

	public void moveGun(Orient orient) {
		super.moveGun(orient);
		mesh.chunkSetAngles("z_Turret5A", 0.0F, orient.getYaw(), 0.0F);
		mesh.chunkSetAngles("z_Turret5B", 0.0F, orient.getTangage(), 0.0F);
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
		if (f1 > 35F)
			f1 = 35F;
		if (f1 < -3F)
			f1 = -3F;
		if (f > 30F)
			f = 30F;
		if (f < -55F)
			f = -55F;
		orient.setYPR(f, f1, 0.0F);
		orient.wrap();
	}

	protected void interpTick() {
		if (!isRealMode())
			return;
		if (emitter == null || !emitter.haveBullets()
				|| !aiTurret().bIsOperable)
			bGunFire = false;
		fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
		if (bGunFire) {
			if (hook1 == null)
				hook1 = new HookNamed(aircraft(), "_MGUN05");
			doHitMasterAircraft(aircraft(), hook1, "_MGUN05");
			if (iCocking > 0)
				iCocking = 0;
			else
				iCocking = 1;
		} else {
			iCocking = 0;
		}
	}

	public void doGunFire(boolean flag) {
		if (!isRealMode())
			return;
		if (emitter == null || !emitter.haveBullets()
				|| !aiTurret().bIsOperable)
			bGunFire = false;
		else
			bGunFire = flag;
		fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
	}

	protected void reflectPlaneMats() {
	}

	public void reflectCockpitState() {
		if ((fm.AS.astateCockpitState & 4) != 0) {
			mesh.chunkVisible("XGlassHoles1", true);
			mesh.chunkVisible("XGlassHoles3", true);
		}
		if ((fm.AS.astateCockpitState & 8) != 0) {
			mesh.chunkVisible("XGlassHoles7", true);
			mesh.chunkVisible("XGlassHoles4", true);
			mesh.chunkVisible("XGlassHoles2", true);
		}
		if ((fm.AS.astateCockpitState & 0x10) != 0) {
			mesh.chunkVisible("XGlassHoles5", true);
			mesh.chunkVisible("XGlassHoles3", true);
		}
		if ((fm.AS.astateCockpitState & 0x20) != 0)
			mesh.chunkVisible("XGlassHoles1", true);
		mesh.chunkVisible("XGlassHoles6", true);
		mesh.chunkVisible("XGlassHoles4", true);
		if ((fm.AS.astateCockpitState & 1) != 0)
			mesh.chunkVisible("XGlassHoles6", true);
		if ((fm.AS.astateCockpitState & 0x40) != 0) {
			mesh.chunkVisible("XGlassHoles7", true);
			mesh.chunkVisible("XGlassHoles2", true);
		}
		if ((fm.AS.astateCockpitState & 0x80) != 0) {
			mesh.chunkVisible("XGlassHoles5", true);
			mesh.chunkVisible("XGlassHoles2", true);
		}
	}

	public CockpitDo217_RGunner() {
		super("3DO/Cockpit/Do217k1/hierRGun.him", "he111_gunner");
		w = new Vector3f();
		bNeedSetUp = true;
		hook1 = null;
		iCocking = 0;
	}

	public Vector3f w;
	private boolean bNeedSetUp;
	private Hook hook1;
	private int iCocking;

	static {
		Property.set(com.maddox.il2.objects.air.CockpitDo217_RGunner.class,
				"aiTuretNum", 4);
		Property.set(com.maddox.il2.objects.air.CockpitDo217_RGunner.class,
				"weaponControlNum", 14);
		Property.set(com.maddox.il2.objects.air.CockpitDo217_RGunner.class,
				"astatePilotIndx", 5);
	}
}
