// Source File Name: JU_52_3MG4E.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import java.io.IOException;

import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;

public class JU_52_3MG4E extends JU_52 implements TypeBomber {

	static {
		Class class1 = JU_52_3MG4E.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "FlightModel", "FlightModels/Ju-52_3mg4e.fmd");
		Property.set(class1, "meshName", "3do/plane/Ju-52_3mg4e/hier.him");
		Property.set(class1, "iconFar_shortClassName", "Ju-52");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
		Property.set(class1, "yearService", 1939F);
		Property.set(class1, "yearExpired", 1945.5F);
		Property.set(class1, "cockpitClass", new Class[] { CockpitJU524E.class,
				CockpitJU525E_GunnerOpen.class });
		Aircraft.weaponTriggersRegister(class1, new int[] { 10, 3 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_BombSpawn01" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunMG15t 250", null });
		Aircraft.weaponsRegister(class1, "18xPara", new String[] {
				"MGunMG15t 250", "BombGunPara 18" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null });
	}

	public JU_52_3MG4E() {
	}

	public void msgShot(Shot shot) {
		this.setShot(shot);
		if (shot.chunkName.startsWith("WingLIn")
				&& (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)) {
			this.FM.AS.hitTank(shot.initiator, 0, 1);
		}
		if (shot.chunkName.startsWith("WingRIn")
				&& (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)) {
			this.FM.AS.hitTank(shot.initiator, 1, 1);
		}
		if (shot.chunkName.startsWith("Engine1")
				&& (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)) {
			this.FM.AS.hitEngine(shot.initiator, 0, 1);
		}
		if (shot.chunkName.startsWith("Engine2")
				&& (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)) {
			this.FM.AS.hitEngine(shot.initiator, 1, 1);
		}
		if ((this.FM.AS.astateEngineStates[0] > 2)
				&& (this.FM.AS.astateEngineStates[1] > 2)) {
			this.FM.setCapableOfBMP(false, shot.initiator);
		}
		super.msgShot(shot);
	}

	public boolean turretAngles(int i, float af[]) {
		boolean flag = super.turretAngles(i, af);
		if (af[0] < -50F) {
			af[0] = -50F;
			flag = false;
		} else if (af[0] > 50F) {
			af[0] = 50F;
			flag = false;
		}
		float f = Math.abs(af[0]);
		if (f < 20F) {
			if (af[1] < -1F) {
				af[1] = -1F;
				flag = false;
			}
		} else if (af[1] < -5F) {
			af[1] = -5F;
			flag = false;
		}
		if (af[1] > 45F) {
			af[1] = 45F;
			flag = false;
		}
		return flag;
	}

	public void typeBomberAdjAltitudeMinus() {
	}

	public void typeBomberAdjAltitudePlus() {
	}

	public void typeBomberAdjAltitudeReset() {
	}

	public void typeBomberAdjDistanceMinus() {
	}

	public void typeBomberAdjDistancePlus() {
	}

	public void typeBomberAdjDistanceReset() {
	}

	public void typeBomberAdjSideslipMinus() {
	}

	public void typeBomberAdjSideslipPlus() {
	}

	public void typeBomberAdjSideslipReset() {
	}

	public void typeBomberAdjSpeedMinus() {
	}

	public void typeBomberAdjSpeedPlus() {
	}

	public void typeBomberAdjSpeedReset() {
	}

	public void typeBomberReplicateFromNet(NetMsgInput netmsginput)
			throws IOException {
	}

	public void typeBomberReplicateToNet(NetMsgGuaranted netmsgguaranted)
			throws IOException {
	}

	public boolean typeBomberToggleAutomation() {
		return false;
	}

	public void typeBomberUpdate(float f) {
	}
}
