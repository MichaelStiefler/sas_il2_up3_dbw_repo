// Source File Name: SEAGLADIATOR2.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.rts.Property;

public class SEAGLADIATOR2 extends SEAGLADIATOR {

	private float arrestor;

	static {
		Class class1 = SEAGLADIATOR2.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "Gladiator");
		Property.set(class1, "meshName",
				"3DO/Plane/SeaGladiatorMkII(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeSEAGLAD());
		Property.set(class1, "meshName_gb",
				"3DO/Plane/SeaGladiatorMkII(GB)/hier.him");
		Property.set(class1, "PaintScheme_gb", new PaintSchemeSEAGLAD());
		Property.set(class1, "yearService", 1939F);
		Property.set(class1, "yearExpired", 1943F);
		Property.set(class1, "FlightModel", "FlightModels/SeaGladiatorMkII.fmd");
		Property.set(class1, "cockpitClass",
				new Class[] { CockpitSEAGLADIATOR.class });
		Property.set(class1, "LOSElevation", 0.8472F);
		Property.set(class1, "originCountry", PaintScheme.countryBritain);
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 1, 1 });
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 1, 1 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_MGUN04" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunBrowning303sipzl 600", "MGunBrowning303sipzl 600",
				"MGunBrowning303k 400", "MGunBrowning303k 400" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null });
	}

	public SEAGLADIATOR2() {
		this.arrestor = 0.0F;
	}

	protected boolean cutFM(int i, int j, Actor actor) {
		switch (i) {
		case 19: // '\023'
			this.FM.CT.bHasArrestorControl = false;
			break;
		}
		return super.cutFM(i, j, actor);
	}

	public void moveArrestorHook(float f) {
		this.hierMesh().chunkSetAngles("Hook1_D0", 0.0F, -57F * f, 0.0F);
		this.resetYPRmodifier();
		Aircraft.xyz[2] = 0.1385F * f;
		this.arrestor = f;
	}

	public void msgCollision(Actor actor, String s, String s1) {
		if ((!this.isNet() || !this.isNetMirror()) && !s.startsWith("Hook")) {
			super.msgCollision(actor, s, s1);
		}
	}

	public void update(float f) {
		super.update(f);
		if (this.FM.CT.getArrestor() > 0.2F) {
			if (this.FM.Gears.arrestorVAngle != 0.0F) {
				float f1 = Aircraft.cvt(this.FM.Gears.arrestorVAngle, -50F, 7F,
						1.0F, 0.0F);
				this.arrestor = (0.8F * this.arrestor) + (0.2F * f1);
				this.moveArrestorHook(this.arrestor);
			} else {
				float f2 = (-33F * this.FM.Gears.arrestorVSink) / 57F;
				if ((f2 < 0.0F) && (this.FM.getSpeedKMH() > 60F)) {
					Eff3DActor.New(this, this.FM.Gears.arrestorHook, null,
							1.0F, "3DO/Effects/Fireworks/04_Sparks.eff", 0.1F);
				}
				if ((f2 > 0.0F) && (this.FM.CT.getArrestor() < 0.95F)) {
					f2 = 0.0F;
				}
				if (f2 > 0.2F) {
					f2 = 0.2F;
				}
				if (f2 > 0.0F) {
					this.arrestor = (0.7F * this.arrestor)
							+ (0.3F * (this.arrestor + f2));
				} else {
					this.arrestor = (0.3F * this.arrestor)
							+ (0.7F * (this.arrestor + f2));
				}
				if (this.arrestor < 0.0F) {
					this.arrestor = 0.0F;
				} else if (this.arrestor > 1.0F) {
					this.arrestor = 1.0F;
				}
				this.moveArrestorHook(this.arrestor);
			}
		}
	}
}
