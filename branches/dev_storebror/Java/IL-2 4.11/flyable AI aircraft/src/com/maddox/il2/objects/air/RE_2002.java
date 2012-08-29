// Source File Name: RE_2002.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import java.io.IOException;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Config;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.CLASS;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;

public class RE_2002 extends RE_2002xyz implements TypeDiveBomber {

	public float canopyF;

	private boolean tiltCanopyOpened;

	private boolean slideCanopyOpened;

	private boolean blisterRemoved;

	static {
		Class class1 = CLASS.THIS();
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "RE.2002");
		Property.set(class1, "meshName_it", "3DO/Plane/RE-2002(it)/hier.him");
		Property.set(class1, "PaintScheme_it", new PaintSchemeBMPar09());
		Property.set(class1, "meshName", "3DO/Plane/RE-2002(multi)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
		Property.set(class1, "yearService", 1943F);
		Property.set(class1, "yearExpired", 1948.5F);
		Property.set(class1, "cockpitClass",
				new Class[] { CockpitRE_2000.class });
		Property.set(class1, "FlightModel", "FlightModels/RE-2000.fmd");
		Property.set(class1, "LOSElevation", 0.9119F);
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 0, 0, 3, 3,
				3, 3, 3, 9 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01",
				"_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04",
				"_ExternalTorp01", "_ExternalDev01" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450",
				"MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640", null, null,
				null, null, null, null });
		Aircraft.weaponsRegister(class1, "2x100kg", new String[] {
				"MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450",
				"MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640",
				"BombGunIT_100_M 1", "BombGunIT_100_M 1", null, null, null,
				null });
		Aircraft.weaponsRegister(class1, "2x100kg+1x240lTank", new String[] {
				"MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450",
				"MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640",
				"BombGunIT_100_M 1", "BombGunIT_100_M 1", null, null, null,
				"FuelTankGun_Tank240 1" });
		Aircraft.weaponsRegister(class1, "1x250kg+2x100kg", new String[] {
				"MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450",
				"MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640",
				"BombGunIT_100_M 1", "BombGunIT_100_M 1", "BombGunIT_250_T 1",
				null, null, null });
		Aircraft.weaponsRegister(class1, "1x250kg", new String[] {
				"MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450",
				"MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640", null, null,
				"BombGunIT_250_T 1", null, null, null });
		Aircraft.weaponsRegister(class1, "1x500kg", new String[] {
				"MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450",
				"MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640", null, null,
				"BombGunIT_500_T 1", null, null, null });
		Aircraft.weaponsRegister(class1, "1x630kg", new String[] {
				"MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450",
				"MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640", null, null,
				null, "BombGunIT_630 1", null, null });
		Aircraft.weaponsRegister(class1, "1xTorpedo", new String[] {
				"MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450",
				"MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640", null, null,
				null, null, "BombGunTorp650 1", null });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null, null, null, null });
	}

	public RE_2002() {
		this.canopyF = 0.0F;
		this.tiltCanopyOpened = false;
		this.slideCanopyOpened = false;
		this.blisterRemoved = false;
	}

	private final void doRemoveBlister1() {
		this.blisterRemoved = true;
		if (this.hierMesh().chunkFindCheck("Blister1_D0") != -1) {
			this.hierMesh().hideSubTrees("Blister1_D0");
			Wreckage wreckage = new Wreckage(this, this.hierMesh().chunkFind(
					"Blister1_D0"));
			wreckage.collide(true);
			Vector3d vector3d = new Vector3d();
			vector3d.set(this.FM.Vwld);
			wreckage.setSpeed(vector3d);
		}
	}

	public void moveCockpitDoor(float f) {
		if (f > this.canopyF) {
			if ((this.FM.Gears.onGround() && (this.FM.getSpeed() < 5F))
					|| this.tiltCanopyOpened) {
				this.tiltCanopyOpened = true;
				this.hierMesh().chunkSetAngles("Blister1_D0", 0.0F, 100F * f,
						0.0F);
			} else {
				this.slideCanopyOpened = true;
				this.resetYPRmodifier();
				Aircraft.xyz[0] = -0.01F;
				Aircraft.xyz[1] = Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.3F);
				this.hierMesh().chunkSetLocate("Blister4L_D0", Aircraft.xyz,
						Aircraft.ypr);
				Aircraft.xyz[0] = 0.01F;
				this.hierMesh().chunkSetLocate("Blister4R_D0", Aircraft.xyz,
						Aircraft.ypr);
			}
		} else if ((this.FM.Gears.onGround() && (this.FM.getSpeed() < 5F) && !this.slideCanopyOpened)
				|| this.tiltCanopyOpened) {
			this.hierMesh().chunkSetAngles("Blister1_D0", 0.0F, 100F * f, 0.0F);
			if ((this.FM.getSpeed() > 50F) && (f < 0.6F)
					&& !this.blisterRemoved) {
				this.doRemoveBlister1();
			}
			if (f == 0.0F) {
				this.tiltCanopyOpened = false;
			}
		} else {
			this.resetYPRmodifier();
			Aircraft.xyz[1] = Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.3F);
			this.hierMesh().chunkSetLocate("Blister4L_D0", Aircraft.xyz,
					Aircraft.ypr);
			this.hierMesh().chunkSetLocate("Blister4R_D0", Aircraft.xyz,
					Aircraft.ypr);
			if (f == 0.0F) {
				this.slideCanopyOpened = false;
			}
		}
		this.canopyF = f;
		if (Config.isUSE_RENDER()) {
			if ((Main3D.cur3D().cockpits != null)
					&& (Main3D.cur3D().cockpits[0] != null)) {
				Main3D.cur3D().cockpits[0].onDoorMoved(f);
			}
			this.setDoorSnd(f);
		}
	}

	protected void mydebug(String s) {
		System.out.println(s);
	}

	public void rareAction(float f, boolean flag) {
		super.rareAction(f, flag);
		if (this.tiltCanopyOpened && !this.blisterRemoved
				&& (this.FM.getSpeed() > 75F)) {
			this.doRemoveBlister1();
		}
	}

	public void typeDiveBomberAdjAltitudeMinus() {
	}

	public void typeDiveBomberAdjAltitudePlus() {
	}

	public void typeDiveBomberAdjAltitudeReset() {
	}

	public void typeDiveBomberAdjDiveAngleMinus() {
	}

	public void typeDiveBomberAdjDiveAnglePlus() {
	}

	public void typeDiveBomberAdjDiveAngleReset() {
	}

	public void typeDiveBomberAdjVelocityMinus() {
	}

	public void typeDiveBomberAdjVelocityPlus() {
	}

	public void typeDiveBomberAdjVelocityReset() {
	}

	public void typeDiveBomberReplicateFromNet(NetMsgInput netmsginput)
			throws IOException {
	}

	public void typeDiveBomberReplicateToNet(NetMsgGuaranted netmsgguaranted)
			throws IOException {
	}

	public boolean typeDiveBomberToggleAutomation() {
		return false;
	}
}
