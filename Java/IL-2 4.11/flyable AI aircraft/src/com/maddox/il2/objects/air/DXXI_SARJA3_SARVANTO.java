// Source File Name: DXXI_SARJA3_SARVANTO.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-04-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.game.Main;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Property;
import com.maddox.rts.HomePath;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class DXXI_SARJA3_SARVANTO extends DXXI implements TypeAcePlane {
	private boolean hasRevi = false;
	private CockpitDXXI_SARJA3_SARVANTO pit = null;
	private float skiAngleL = 0.0F;
	private float skiAngleR = 0.0F;
	private float spring = 0.15F;
	public float gyroDelta = 0.0F;

	public void missionStarting() {
		super.missionStarting();
		customization();
		if (this.FM.isStationedOnGround())
			this.gyroDelta += (float) Math.random() * 360.0F;
	}

	public void registerPit(
			CockpitDXXI_SARJA3_SARVANTO paramCockpitDXXI_SARJA3_SARVANTO) {
		this.pit = paramCockpitDXXI_SARJA3_SARVANTO;
	}

	public boolean hasRevi() {
		return this.hasRevi;
	}

	public void onAircraftLoaded() {
		super.onAircraftLoaded();
		this.FM.Skill = 3;
		JormaSarvanto();
		if ((Config.isUSE_RENDER()) && (World.cur().camouflage == 1)) {
			this.hasSkis = true;
			hierMesh().chunkVisible("GearL1_D0", false);
			hierMesh().chunkVisible("GearL22_D0", false);
			hierMesh().chunkVisible("GearR1_D0", false);
			hierMesh().chunkVisible("GearR22_D0", false);
			hierMesh().chunkVisible("GearC1_D0", false);
			hierMesh().chunkVisible("GearL31_D0", false);
			hierMesh().chunkVisible("GearL32_D0", false);
			hierMesh().chunkVisible("GearR31_D0", false);
			hierMesh().chunkVisible("GearR32_D0", false);

			hierMesh().chunkVisible("GearC11_D0", true);
			hierMesh().chunkVisible("GearL11_D0", true);
			hierMesh().chunkVisible("GearL21_D0", true);
			hierMesh().chunkVisible("GearR11_D0", true);
			hierMesh().chunkVisible("GearR21_D0", true);

			this.FM.CT.bHasBrakeControl = false;
		}
		if (World.Rnd().nextFloat() < 0.01F) {
			removeWheelSpats();
		}
	}

	public void rareAction(float paramFloat, boolean paramBoolean) {
		super.rareAction(paramFloat, paramBoolean);
		if ((this.FM.Or.getKren() < -10.0F) || (this.FM.Or.getKren() > 10.0F)) {
			this.gyroDelta = (float) (this.gyroDelta - 0.01D);
		}
	}

	private void JormaSarvanto() {
		for (int i = 0; i < this.FM.CT.Weapons.length; i++) {
			BulletEmitter[] arrayOfBulletEmitter = this.FM.CT.Weapons[i];
			if (arrayOfBulletEmitter == null)
				continue;
			for (int j = 0; j < arrayOfBulletEmitter.length; j++) {
				BulletEmitter localBulletEmitter = arrayOfBulletEmitter[j];
				if (!(localBulletEmitter instanceof Gun))
					continue;
				GunProperties localGunProperties = ((Gun) localBulletEmitter).prop;
				BulletProperties[] arrayOfBulletProperties = localGunProperties.bullet;
				if (arrayOfBulletProperties == null)
					continue;
				for (int k = 0; k < arrayOfBulletProperties.length; k++) {
					arrayOfBulletProperties[k].powerType = 3;
					arrayOfBulletProperties[k].massa = 0.02F;
					arrayOfBulletProperties[k].kalibr = 4.442132E-005F;
					arrayOfBulletProperties[k].speed = 835.0F;

					if (arrayOfBulletProperties[k].power == 0.0F)
						continue;
					arrayOfBulletProperties[k].power = 0.002F;
				}
			}
		}
	}

	private void removeWheelSpats() {
		hierMesh().chunkVisible("GearR22_D0", false);
		hierMesh().chunkVisible("GearL22_D0", false);
		hierMesh().chunkVisible("GearR22_D2", true);
		hierMesh().chunkVisible("GearL22_D2", true);
		hierMesh().chunkVisible("gearl31_d0", true);
		hierMesh().chunkVisible("gearl32_d0", true);
		hierMesh().chunkVisible("gearr31_d0", true);
		hierMesh().chunkVisible("gearr32_d0", true);
	}

	private void customization() {
		int i = hierMesh().chunkFindCheck("cf_D0");
		int j = hierMesh().materialFindInChunk("Gloss1D0o", i);
		Mat localMat = hierMesh().material(j);
		String str1 = localMat.Name();
		if (str1.startsWith("PaintSchemes/Cache")) {
			try {
				str1 = str1.substring(19);
				str1 = str1.substring(0, str1.indexOf("/"));
				String str2 = Main.cur().netFileServerSkin.primaryPath();
				File localFile = new File(HomePath.toFileSystemName(str2
						+ "/DXXI_SARJA3_EARLY/Customization.ini", 0));
				BufferedReader localBufferedReader = new BufferedReader(
						new FileReader(localFile));
				String str3 = null;
				int k = 0;
				int m = 0;
				while ((str3 = localBufferedReader.readLine()) != null) {
					if (str3.equals("[ReflectorSight]")) {
						k = 1;
						m = 0;
						continue;
					}
					if (str3.equals("[NoWheelSpats]")) {
						k = 0;
						m = 1;
						continue;
					}
					if (!str3.equals(str1))
						continue;
					if (k != 0) {
						hierMesh().chunkVisible("Revi_D0", true);
						hierMesh().chunkVisible("Goertz_D0", false);
						this.hasRevi = true;
					}
					if ((m == 0) || (World.cur().camouflage == 1))
						continue;
					removeWheelSpats();
				}

				localBufferedReader.close();
			} catch (Exception localException) {
				System.out.println(localException);
			}

		} else if (World.Rnd().nextFloat() > 0.6F) {
			hierMesh().chunkVisible("Revi_D0", true);
			hierMesh().chunkVisible("Goertz_D0", false);
			this.hasRevi = true;
		}

		if ((this.hasRevi) && (this.pit != null))
			this.pit.setRevi();
	}

	public static void moveGear(HierMesh paramHierMesh, float paramFloat) {
		if ((World.cur().camouflage == 1) && (World.Rnd().nextFloat() > 0.1F)) {
			paramHierMesh.chunkVisible("GearL1_D0", false);
			paramHierMesh.chunkVisible("GearL22_D0", false);
			paramHierMesh.chunkVisible("GearR1_D0", false);
			paramHierMesh.chunkVisible("GearR22_D0", false);
			paramHierMesh.chunkVisible("GearC1_D0", false);
			paramHierMesh.chunkVisible("GearL31_D0", false);
			paramHierMesh.chunkVisible("GearL32_D0", false);
			paramHierMesh.chunkVisible("GearR31_D0", false);
			paramHierMesh.chunkVisible("GearR32_D0", false);

			paramHierMesh.chunkVisible("GearC11_D0", true);
			paramHierMesh.chunkVisible("GearL11_D0", true);
			paramHierMesh.chunkVisible("GearL21_D0", true);
			paramHierMesh.chunkVisible("GearR11_D0", true);
			paramHierMesh.chunkVisible("GearR21_D0", true);

			paramHierMesh.chunkSetAngles("GearL21_D0", 0.0F, 12.0F, 0.0F);
			paramHierMesh.chunkSetAngles("GearR21_D0", 0.0F, 12.0F, 0.0F);
			paramHierMesh.chunkSetAngles("GearC11_D0", 0.0F, 12.0F, 0.0F);
		}
	}

	protected void moveFan(float paramFloat) {
		if (Config.isUSE_RENDER()) {
			super.moveFan(paramFloat);
			float f1 = this.FM.CT.getAileron();
			float f2 = this.FM.CT.getElevator();

			hierMesh().chunkSetAngles("Stick_D0", 0.0F, 9.0F * f1,
					cvt(f2, -1.0F, 1.0F, -8.0F, 9.5F));
			hierMesh().chunkSetAngles(
					"pilotarm2_d0",
					cvt(f1, -1.0F, 1.0F, 14.0F, -16.0F),
					0.0F,
					cvt(f1, -1.0F, 1.0F, 6.0F, -8.0F)
							- cvt(f2, -1.0F, 1.0F, -37.0F, 35.0F));
			hierMesh().chunkSetAngles(
					"pilotarm1_d0",
					0.0F,
					0.0F,
					cvt(f1, -1.0F, 1.0F, -16.0F, 14.0F)
							+ cvt(f2, -1.0F, 0.0F, -61.0F, 0.0F)
							+ cvt(f2, 0.0F, 1.0F, 0.0F, 43.0F));

			if (World.cur().camouflage == 1) {
				float f3 = Aircraft.cvt(this.FM.getSpeed(), 30.0F, 100.0F,
						1.0F, 0.0F);
				float f4 = Aircraft.cvt(this.FM.getSpeed(), 0.0F, 30.0F, 0.0F,
						0.5F);

				if (this.FM.Gears.gWheelSinking[0] > 0.0F) {
					this.skiAngleL = (0.5F * this.skiAngleL + 0.5F * this.FM.Or
							.getTangage());

					if (this.skiAngleL > 20.0F) {
						this.skiAngleL -= this.spring;
					}

					hierMesh().chunkSetAngles(
							"GearL21_D0",
							World.Rnd().nextFloat(-f4, f4),
							World.Rnd().nextFloat(-f4 * 2.0F, f4 * 2.0F)
									+ this.skiAngleL,
							World.Rnd().nextFloat(f4, f4));

					if ((this.FM.Gears.gWheelSinking[1] == 0.0F)
							&& (this.FM.Or.getRoll() < 365.0F)
							&& (this.FM.Or.getRoll() > 355.0F)) {
						this.skiAngleR = this.skiAngleL;
						hierMesh().chunkSetAngles(
								"GearR21_D0",
								World.Rnd().nextFloat(-f4, f4),
								World.Rnd().nextFloat(-f4 * 2.0F, f4 * 2.0F)
										+ this.skiAngleR,
								World.Rnd().nextFloat(f4, f4));
					}

				} else {
					if (this.skiAngleL > f3 * -10.0F + 0.01D) {
						this.skiAngleL -= this.spring;
					} else if (this.skiAngleL < f3 * -10.0F - 0.01D) {
						this.skiAngleL += this.spring;
					}

					hierMesh().chunkSetAngles("GearL21_D0", 0.0F,
							this.skiAngleL, 0.0F);
				}

				if (this.FM.Gears.gWheelSinking[1] > 0.0F) {
					this.skiAngleR = (0.5F * this.skiAngleR + 0.5F * this.FM.Or
							.getTangage());

					if (this.skiAngleR > 20.0F) {
						this.skiAngleR -= this.spring;
					}

					hierMesh().chunkSetAngles(
							"GearR21_D0",
							World.Rnd().nextFloat(-f4, f4),
							World.Rnd().nextFloat(-f4 * 2.0F, f4 * 2.0F)
									+ this.skiAngleR,
							World.Rnd().nextFloat(f4, f4));
				} else {
					if (this.skiAngleR > f3 * -10.0F + 0.01D) {
						this.skiAngleR -= this.spring;
					} else if (this.skiAngleR < f3 * -10.0F - 0.01D) {
						this.skiAngleR += this.spring;
					}
					hierMesh().chunkSetAngles("GearR21_D0", 0.0F,
							this.skiAngleR, 0.0F);
				}

				hierMesh().chunkSetAngles("GearC11_D0", 0.0F,
						(this.skiAngleL + this.skiAngleR) / 2.0F, 0.0F);
			}
		}
	}

	public void sfxWheels() {
		if (!this.hasSkis)
			super.sfxWheels();
	}

	public void auxPlus(int paramInt) {
		switch (paramInt) {
		case 1:
			this.gyroDelta += 1.0F;
		}
	}

	public void auxMinus(int paramInt) {
		switch (paramInt) {
		case 1:
			this.gyroDelta -= 1.0F;
		}
	}

	static {
		Class localClass = DXXI_SARJA3_SARVANTO.class;
		new NetAircraft.SPAWN(localClass);
		Property.set(localClass, "iconFar_shortClassName", "D.XXI");
		Property.set(localClass, "meshName",
				"3DO/Plane/DXXI_SARJA3_EARLY(Sarvanto)/hier.him");
		Property.set(localClass, "PaintScheme", new PaintSchemeFMPar00DXXI());
		Property.set(localClass, "yearService", 1939.0F);
		Property.set(localClass, "yearExpired", 1940.0F);
		Property.set(localClass, "FlightModel",
				"FlightModels/FokkerS3Early.fmd");
		Property.set(localClass, "LOSElevation", 0.8472F);
		Property.set(localClass, "originCountry", PaintScheme.countryFinland);
		Property.set(localClass, "cockpitClass",
				new Class[] { CockpitDXXI_SARJA3_SARVANTO.class });
		Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0 });
		Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_MGUN04" });

		weaponsRegister(localClass, "default", new String[] {
				"MGunBrowning303sipzl 500", "MGunBrowning303sipzl 500",
				"MGunBrowning303k 500", "MGunBrowning303k 500" });

		weaponsRegister(localClass, "AlternativeTracers", new String[] {
				"MGunBrowning303sipzl_fullTracers 500",
				"MGunBrowning303sipzl_NoTracers 500",
				"MGunBrowning303k_NoTracers 500",
				"MGunBrowning303k_NoTracers 500" });

		weaponsRegister(localClass, "none", new String[] { null, null, null,
				null });
	}
}