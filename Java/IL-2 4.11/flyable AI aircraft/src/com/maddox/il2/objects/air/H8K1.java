// Source File Name: H8K1.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import java.io.IOException;

import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;

public class H8K1 extends H8K implements TypeBomber {

	private boolean bSightAutomation;

	private boolean bSightBombDump;

	private float fSightCurDistance;

	public float fSightCurForwardAngle;

	public float fSightCurSideslip;

	public float fSightCurAltitude;

	public float fSightCurSpeed;

	public float fSightCurReadyness;

	static {
		Class class1 = H8K1.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "H8K");
		Property.set(class1, "meshName", "3DO/Plane/H8K1(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar04());
		Property.set(class1, "meshName_ja", "3DO/Plane/H8K1(ja)/hier.him");
		Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar05());
		Property.set(class1, "yearService", 1941F);
		Property.set(class1, "yearExpired", 2048F);
		Property.set(class1, "FlightModel", "FlightModels/H8K1.fmd");
		Property.set(class1, "cockpitClass", new Class[] { CockpitH8K1.class,
				CockpitH8K1_Bombardier.class, CockpitH8K1_NGunner.class,
				CockpitH8K1_AGunner.class, CockpitH8K1_TGunner.class });
		Property.set(class1, "LOSElevation", 1.4078F);
		Aircraft.weaponTriggersRegister(class1, new int[] { 10, 11, 12, 13, 14,
				3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
				3, 3, 3, 3, 3 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_ExternalBomb01",
				"_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04",
				"_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07",
				"_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10",
				"_ExternalBomb11", "_ExternalBomb12", "_ExternalBomb13",
				"_ExternalBomb14", "_ExternalBomb15", "_ExternalBomb16",
				"_ExternalBomb17", "_ExternalBomb18", "_ExternalBomb19",
				"_ExternalBomb20", "_ExternalBomb21", "_ExternalBomb22",
				"_ExternalBomb23", "_ExternalBomb24", "_ExternalBomb25",
				"_ExternalBomb26" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunMG81t 450", "MGunMG81t 450", "MGunMG81t 450",
				"MGunMG15120MGt 450", "MGunMG15120MGt 600", null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null });
		Aircraft.weaponsRegister(class1, "16x60", new String[] {
				"MGunMG81t 450", "MGunMG81t 450", "MGunMG81t 450",
				"MGunMG15120MGt 450", "MGunMG15120MGt 600", null, null, null,
				null, null, null, null, null, null, null, "BombGun60kgJ",
				"BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ",
				"BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ",
				"BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ",
				"BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ" });
		Aircraft.weaponsRegister(class1, "8x250", new String[] {
				"MGunMG81t 450", "MGunMG81t 450", "MGunMG81t 450",
				"MGunMG15120MGt 450", "MGunMG15120MGt 600", null, null,
				"BombGun250kgJ", "BombGun250kgJ", "BombGun250kgJ",
				"BombGun250kgJ", "BombGun250kgJ", "BombGun250kgJ",
				"BombGun250kgJ", "BombGun250kgJ", null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null });
		Aircraft.weaponsRegister(class1, "2x4512", new String[] {
				"MGunMG81t 450", "MGunMG81t 450", "MGunMG81t 450",
				"MGunMG15120MGt 450", "MGunMG15120MGt 600", "BombGun4512",
				"BombGun4512", null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null });
	}

	public H8K1() {
		this.bSightAutomation = false;
		this.bSightBombDump = false;
		this.fSightCurDistance = 0.0F;
		this.fSightCurForwardAngle = 0.0F;
		this.fSightCurSideslip = 0.0F;
		this.fSightCurAltitude = 850F;
		this.fSightCurSpeed = 150F;
		this.fSightCurReadyness = 0.0F;
	}

	protected boolean cutFM(int i, int j, Actor actor) {
		switch (i) {
		default:
			break;

		case 33: // '!'
			this.hitProp(0, j, actor);
			break;

		case 36: // '$'
			this.hitProp(1, j, actor);
			break;

		case 34: // '"'
			this.FM.AS.hitEngine(this, 0, 2);
			if (World.Rnd().nextInt(0, 99) < 66) {
				this.FM.AS.hitEngine(this, 0, 2);
			}
			break;

		case 37: // '%'
			this.FM.AS.hitEngine(this, 1, 2);
			if (World.Rnd().nextInt(0, 99) < 66) {
				this.FM.AS.hitEngine(this, 1, 2);
			}
			break;

		case 19: // '\023'
			this.killPilot(actor, 6);
			break;
		}
		return super.cutFM(i, j, actor);
	}

	public void doKillPilot(int i) {
		switch (i) {
		case 2: // '\002'
			this.FM.turret[0].bIsOperable = false;
			break;

		case 3: // '\003'
			this.FM.turret[1].bIsOperable = false;
			this.FM.turret[2].bIsOperable = false;
			break;

		case 4: // '\004'
			this.FM.turret[3].bIsOperable = false;
			break;

		case 5: // '\005'
			this.FM.turret[4].bIsOperable = false;
			break;
		}
	}

	public void doMurderPilot(int i) {
		if (i <= 5) {
			this.hierMesh().chunkVisible("Pilot" + (i + 1) + "_D0", false);
			this.hierMesh().chunkVisible("HMask" + (i + 1) + "_D0", false);
			this.hierMesh().chunkVisible("Pilot" + (i + 1) + "_D1", true);
			this.hierMesh().chunkVisible("Head" + (i + 1) + "_D0", false);
		}
	}

	public void rareAction(float f, boolean flag) {
		super.rareAction(f, flag);
		if (flag) {
			if (this.FM.AS.astateEngineStates[0] > 3) {
				if (World.Rnd().nextFloat() < 0.02F) {
					this.FM.AS.hitTank(this, 0, 1);
				}
				if (World.Rnd().nextFloat() < 0.02F) {
					this.FM.AS.hitTank(this, 1, 1);
				}
			}
			if (this.FM.AS.astateEngineStates[1] > 3) {
				if (World.Rnd().nextFloat() < 0.02F) {
					this.FM.AS.hitTank(this, 2, 1);
				}
				if (World.Rnd().nextFloat() < 0.02F) {
					this.FM.AS.hitTank(this, 3, 1);
				}
			}
			if ((this.FM.AS.astateTankStates[0] > 5)
					&& (World.Rnd().nextFloat() < 0.02F)) {
				this.FM.AS.hitTank(this, 1, 1);
			}
			if ((this.FM.AS.astateTankStates[1] > 5)
					&& (World.Rnd().nextFloat() < 0.02F)) {
				this.FM.AS.hitTank(this, 0, 1);
			}
			if ((this.FM.AS.astateTankStates[1] > 5)
					&& (World.Rnd().nextFloat() < 0.02F)) {
				this.FM.AS.hitTank(this, 2, 1);
			}
			if ((this.FM.AS.astateTankStates[2] > 5)
					&& (World.Rnd().nextFloat() < 0.02F)) {
				this.FM.AS.hitTank(this, 1, 1);
			}
			if ((this.FM.AS.astateTankStates[2] > 5)
					&& (World.Rnd().nextFloat() < 0.02F)) {
				this.FM.AS.hitTank(this, 3, 1);
			}
			if ((this.FM.AS.astateTankStates[3] > 5)
					&& (World.Rnd().nextFloat() < 0.02F)) {
				this.FM.AS.hitTank(this, 2, 1);
			}
		}
		if (this.FM.getAltitude() < 3000F) {
			for (int i = 1; i < 8; i++) {
				this.hierMesh().chunkVisible("HMask" + i + "_D0", false);
			}

		} else {
			for (int j = 1; j < 8; j++) {
				this.hierMesh().chunkVisible("HMask" + j + "_D0",
						this.hierMesh().isChunkVisible("Pilot1_D0"));
			}

		}
	}

	public boolean turretAngles(int i, float af[]) {
		boolean flag = super.turretAngles(i, af);
		float f = -af[0];
		float f1 = af[1];
		switch (i) {
		default:
			break;

		case 0: // '\0'
			if (f < -35F) {
				f = -35F;
				flag = false;
			}
			if (f > 35F) {
				f = 35F;
				flag = false;
			}
			if (f1 < -25F) {
				f1 = -25F;
				flag = false;
			}
			if (f1 > 25F) {
				f1 = 25F;
				flag = false;
			}
			break;

		case 1: // '\001'
			if (f < -22F) {
				f = -22F;
				flag = false;
			}
			if (f > 22F) {
				f = 22F;
				flag = false;
			}
			if (f1 < -57F) {
				f1 = -57F;
				flag = false;
			}
			if (f1 > 33F) {
				f1 = 33F;
				flag = false;
			}
			break;

		case 2: // '\002'
			if (f < -22F) {
				f = -22F;
				flag = false;
			}
			if (f > 22F) {
				f = 22F;
				flag = false;
			}
			if (f1 < -57F) {
				f1 = -57F;
				flag = false;
			}
			if (f1 > 33F) {
				f1 = 33F;
				flag = false;
			}
			break;

		case 3: // '\003'
			if (f1 < 0.0F) {
				f1 = 0.0F;
				flag = false;
			}
			if (f1 > 50F) {
				f1 = 50F;
				flag = false;
			}
			break;

		case 4: // '\004'
			if (f < -25F) {
				f = -25F;
				flag = false;
			}
			if (f > 25F) {
				f = 25F;
				flag = false;
			}
			if (f1 < -25F) {
				f1 = -25F;
				flag = false;
			}
			if (f1 > 25F) {
				f1 = 25F;
				flag = false;
			}
			break;
		}
		af[0] = -f;
		af[1] = f1;
		return flag;
	}

	public void typeBomberAdjAltitudeMinus() {
		this.fSightCurAltitude -= 10F;
		if (this.fSightCurAltitude < 850F) {
			this.fSightCurAltitude = 850F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude",
				new Object[] { new Integer((int) this.fSightCurAltitude) });
		this.fSightCurDistance = this.fSightCurAltitude
				* (float) Math.tan(Math.toRadians(this.fSightCurForwardAngle));
	}

	public void typeBomberAdjAltitudePlus() {
		this.fSightCurAltitude += 10F;
		if (this.fSightCurAltitude > 10000F) {
			this.fSightCurAltitude = 10000F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude",
				new Object[] { new Integer((int) this.fSightCurAltitude) });
		this.fSightCurDistance = this.fSightCurAltitude
				* (float) Math.tan(Math.toRadians(this.fSightCurForwardAngle));
	}

	public void typeBomberAdjAltitudeReset() {
		this.fSightCurAltitude = 850F;
	}

	public void typeBomberAdjDistanceMinus() {
		this.fSightCurForwardAngle--;
		if (this.fSightCurForwardAngle < 0.0F) {
			this.fSightCurForwardAngle = 0.0F;
		}
		this.fSightCurDistance = this.fSightCurAltitude
				* (float) Math.tan(Math.toRadians(this.fSightCurForwardAngle));
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation",
				new Object[] { new Integer((int) this.fSightCurForwardAngle) });
		if (this.bSightAutomation) {
			this.typeBomberToggleAutomation();
		}
	}

	public void typeBomberAdjDistancePlus() {
		this.fSightCurForwardAngle++;
		if (this.fSightCurForwardAngle > 85F) {
			this.fSightCurForwardAngle = 85F;
		}
		this.fSightCurDistance = this.fSightCurAltitude
				* (float) Math.tan(Math.toRadians(this.fSightCurForwardAngle));
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation",
				new Object[] { new Integer((int) this.fSightCurForwardAngle) });
		if (this.bSightAutomation) {
			this.typeBomberToggleAutomation();
		}
	}

	public void typeBomberAdjDistanceReset() {
		this.fSightCurDistance = 0.0F;
		this.fSightCurForwardAngle = 0.0F;
	}

	public void typeBomberAdjSideslipMinus() {
		this.fSightCurSideslip -= 0.1F;
		if (this.fSightCurSideslip < -3F) {
			this.fSightCurSideslip = -3F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId,
				"BombsightSlip",
				new Object[] { new Integer((int) (this.fSightCurSideslip * 10F)) });
	}

	public void typeBomberAdjSideslipPlus() {
		this.fSightCurSideslip += 0.1F;
		if (this.fSightCurSideslip > 3F) {
			this.fSightCurSideslip = 3F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId,
				"BombsightSlip",
				new Object[] { new Integer((int) (this.fSightCurSideslip * 10F)) });
	}

	public void typeBomberAdjSideslipReset() {
		this.fSightCurSideslip = 0.0F;
	}

	public void typeBomberAdjSpeedMinus() {
		this.fSightCurSpeed -= 10F;
		if (this.fSightCurSpeed < 150F) {
			this.fSightCurSpeed = 150F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed",
				new Object[] { new Integer((int) this.fSightCurSpeed) });
	}

	public void typeBomberAdjSpeedPlus() {
		this.fSightCurSpeed += 10F;
		if (this.fSightCurSpeed > 600F) {
			this.fSightCurSpeed = 600F;
		}
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed",
				new Object[] { new Integer((int) this.fSightCurSpeed) });
	}

	public void typeBomberAdjSpeedReset() {
		this.fSightCurSpeed = 150F;
	}

	public void typeBomberReplicateFromNet(NetMsgInput netmsginput)
			throws IOException {
		int i = netmsginput.readUnsignedByte();
		this.bSightAutomation = (i & 1) != 0;
		this.bSightBombDump = (i & 2) != 0;
		this.fSightCurDistance = netmsginput.readFloat();
		this.fSightCurForwardAngle = netmsginput.readUnsignedByte();
		this.fSightCurSideslip = -3F
				+ (netmsginput.readUnsignedByte() / 33.33333F);
		this.fSightCurAltitude = netmsginput.readFloat();
		this.fSightCurSpeed = netmsginput.readUnsignedByte() * 2.5F;
		this.fSightCurReadyness = netmsginput.readUnsignedByte() / 200F;
	}

	public void typeBomberReplicateToNet(NetMsgGuaranted netmsgguaranted)
			throws IOException {
		netmsgguaranted.writeByte((this.bSightAutomation ? 1 : 0)
				| (this.bSightBombDump ? 2 : 0));
		netmsgguaranted.writeFloat(this.fSightCurDistance);
		netmsgguaranted.writeByte((int) this.fSightCurForwardAngle);
		netmsgguaranted
				.writeByte((int) ((this.fSightCurSideslip + 3F) * 33.33333F));
		netmsgguaranted.writeFloat(this.fSightCurAltitude);
		netmsgguaranted.writeByte((int) (this.fSightCurSpeed / 2.5F));
		netmsgguaranted.writeByte((int) (this.fSightCurReadyness * 200F));
	}

	public boolean typeBomberToggleAutomation() {
		this.bSightAutomation = !this.bSightAutomation;
		this.bSightBombDump = false;
		HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAutomation"
				+ (this.bSightAutomation ? "ON" : "OFF"));
		return this.bSightAutomation;
	}

	public void typeBomberUpdate(float f) {
		if (Math.abs(this.FM.Or.getKren()) > 4.5D) {
			this.fSightCurReadyness -= 0.0666666F * f;
			if (this.fSightCurReadyness < 0.0F) {
				this.fSightCurReadyness = 0.0F;
			}
		}
		if (this.fSightCurReadyness < 1.0F) {
			this.fSightCurReadyness += 0.0333333F * f;
		} else if (this.bSightAutomation) {
			this.fSightCurDistance -= (this.fSightCurSpeed / 3.6F) * f;
			if (this.fSightCurDistance < 0.0F) {
				this.fSightCurDistance = 0.0F;
				this.typeBomberToggleAutomation();
			}
			this.fSightCurForwardAngle = (float) Math.toDegrees(Math
					.atan(this.fSightCurDistance / this.fSightCurAltitude));
			if (this.fSightCurDistance < ((this.fSightCurSpeed / 3.6F) * Math
					.sqrt(this.fSightCurAltitude * 0.2038736F))) {
				this.bSightBombDump = true;
			}
			if (this.bSightBombDump) {
				if (this.FM.isTick(3, 0)) {
					if ((this.FM.CT.Weapons[3] != null)
							&& (this.FM.CT.Weapons[3][this.FM.CT.Weapons[3].length - 1] != null)
							&& this.FM.CT.Weapons[3][this.FM.CT.Weapons[3].length - 1]
									.haveBullets()) {
						this.FM.CT.WeaponControl[3] = true;
						HUD.log(AircraftHotKeys.hudLogWeaponId,
								"BombsightBombdrop");
					}
				} else {
					this.FM.CT.WeaponControl[3] = false;
				}
			}
		}
	}
}
