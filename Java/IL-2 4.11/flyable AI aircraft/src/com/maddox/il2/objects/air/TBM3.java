// Source File Name: TBM3.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class TBM3 extends TBF {

	private float flapps;

	static {
		Class class1 = TBM3.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "TBF");
		Property.set(class1, "meshName", "3DO/Plane/TBM-3(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar01());
		Property.set(class1, "meshName_us", "3DO/Plane/TBM-3(USA)/hier.him");
		Property.set(class1, "PaintScheme_us", new PaintSchemeBMPar01());
		Property.set(class1, "yearService", 1943F);
		Property.set(class1, "yearExpired", 1945.5F);
		Property.set(class1, "FlightModel", "FlightModels/TBM-3.fmd");
//		Property.set(class1, "cockpitClass", new Class[] { CockpitTBM3.class,
//				CockpitTBM3_TGunner.class, CockpitTBM3_BGunner.class });
        Property.set(class1, "cockpitClass", new Class[] {
                CockpitTBX1.class, CockpitTBX1_TGunner.class, CockpitTBX1_BGunner.class
            });
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 10, 11, 2, 2,
				2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_MGUN04", "_ExternalRock01",
				"_ExternalRock02", "_ExternalRock03", "_ExternalRock04",
				"_ExternalRock05", "_ExternalRock06", "_ExternalRock07",
				"_ExternalRock08", "_BombSpawn01", "_BombSpawn02",
				"_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06",
				"_BombSpawn07" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500", null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null });
		Aircraft.weaponsRegister(class1, "8xhvargp", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500",
				"RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5",
				"RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5",
				"RocketGunHVAR5", "RocketGunHVAR5", null, null, null, null,
				null, null, null });
		Aircraft.weaponsRegister(class1, "8xhvarap", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", null, null, null, null,
				null, null, null });
		Aircraft.weaponsRegister(class1, "4x100", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500", null, null,
				null, null, null, null, null, null, null, null, null,
				"BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1",
				"BombGunFAB50 1" });
		Aircraft.weaponsRegister(class1, "4x1008xhvargp", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500",
				"RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5",
				"RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5",
				"RocketGunHVAR5", "RocketGunHVAR5", null, null, null,
				"BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1",
				"BombGunFAB50 1" });
		Aircraft.weaponsRegister(class1, "4x1008xhvarap", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", null, null, null,
				"BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1",
				"BombGunFAB50 1" });
		Aircraft.weaponsRegister(class1, "2x250", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500", null, null,
				null, null, null, null, null, null, null, "BombGun250lbs 1",
				"BombGun250lbs 1", null, null, null, null });
		Aircraft.weaponsRegister(class1, "2x2508xhvargp", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500",
				"RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5",
				"RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5",
				"RocketGunHVAR5", "RocketGunHVAR5", null, "BombGun250lbs 1",
				"BombGun250lbs 1", null, null, null, null });
		Aircraft.weaponsRegister(class1, "2x2508xhvarap", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", null,
				"BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null });
		Aircraft.weaponsRegister(class1, "4x250", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500", null, null,
				null, null, null, null, null, null, null, null, null,
				"BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1",
				"BombGun250lbs 1" });
		Aircraft.weaponsRegister(class1, "2x500", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500", null, null,
				null, null, null, null, null, null, null, "BombGun500lbs 1",
				"BombGun500lbs 1", null, null, null, null });
		Aircraft.weaponsRegister(class1, "2x5008xhvargp", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500",
				"RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5",
				"RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5",
				"RocketGunHVAR5", "RocketGunHVAR5", null, "BombGun500lbs 1",
				"BombGun500lbs 1", null, null, null, null });
		Aircraft.weaponsRegister(class1, "2x5008xhvarap", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP",
				"RocketGunHVAR5AP", "RocketGunHVAR5AP", null,
				"BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null });
		Aircraft.weaponsRegister(class1, "4x500", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500", null, null,
				null, null, null, null, null, null, null, null, null,
				"BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1",
				"BombGun500lbs 1" });
		Aircraft.weaponsRegister(class1, "2x1000", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500", null, null,
				null, null, null, null, null, null, null, "BombGun1000lbs 1",
				"BombGun1000lbs 1", null, null, null, null });
		Aircraft.weaponsRegister(class1, "1x1600", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500", null, null,
				null, null, null, null, null, null, "BombGun1600lbs", null,
				null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "1x2000", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500", null, null,
				null, null, null, null, null, null, "BombGun2000lbs", null,
				null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "1xmk13", new String[] {
				"MGunBrowning50kWF 600", "MGunBrowning50kWF 600",
				"MGunBrowning50t 400", "MGunBrowning303t 500", null, null,
				null, null, null, null, null, null, "BombGunTorpMk13", null,
				null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null });
	}

	public TBM3() {
		this.flapps = 0.0F;
	}

	public boolean turretAngles(int i, float af[]) {
		boolean flag = super.turretAngles(i, af);
		float f = -af[0];
		float f1 = af[1];
		switch (i) {
		case 0: // '\0'
			if (f < -33F) {
				f = -33F;
				flag = false;
			}
			if (f > 33F) {
				f = 33F;
				flag = false;
			}
			if (f1 < -3F) {
				f1 = -3F;
				flag = false;
			}
			if (f1 > 62F) {
				f1 = 62F;
				flag = false;
			}
			break;
		}
		af[0] = -f;
		af[1] = f1;
		return flag;
	}

	public void update(float f) {
		super.update(f);
		float f1 = this.FM.EI.engines[0].getControlRadiator();
		if (Math.abs(this.flapps - f1) > 0.01F) {
			this.flapps = f1;
			for (int i = 1; i < 9; i++) {
				this.hierMesh().chunkSetAngles("Cowflap" + i + "_D0", 0.0F,
						22.2F * f1, 0.0F);
			}

		}
	}
}
