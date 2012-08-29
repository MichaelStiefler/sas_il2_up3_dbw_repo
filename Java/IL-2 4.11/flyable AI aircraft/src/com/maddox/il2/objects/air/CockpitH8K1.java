// Source File Name: CockpitH8K1.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;

public class CockpitH8K1 extends CockpitPilot {
	class Interpolater extends InterpolateRef {

		Interpolater() {
		}

		public boolean tick() {
			if (CockpitH8K1.this.fm != null) {
				CockpitH8K1.this.setTmp = CockpitH8K1.this.setOld;
				CockpitH8K1.this.setOld = CockpitH8K1.this.setNew;
				CockpitH8K1.this.setNew = CockpitH8K1.this.setTmp;
				CockpitH8K1.this.setNew.throttle1 = (0.9F * CockpitH8K1.this.setOld.throttle1)
						+ (0.1F * CockpitH8K1.this.fm.EI.engines[0]
								.getControlThrottle());
				CockpitH8K1.this.setNew.prop1 = (0.9F * CockpitH8K1.this.setOld.prop1)
						+ (0.1F * CockpitH8K1.this.fm.EI.engines[0]
								.getControlProp());
				CockpitH8K1.this.setNew.mix1 = (0.8F * CockpitH8K1.this.setOld.mix1)
						+ (0.2F * CockpitH8K1.this.fm.EI.engines[0]
								.getControlMix());
				CockpitH8K1.this.setNew.throttle2 = (0.9F * CockpitH8K1.this.setOld.throttle2)
						+ (0.1F * CockpitH8K1.this.fm.EI.engines[1]
								.getControlThrottle());
				CockpitH8K1.this.setNew.prop2 = (0.9F * CockpitH8K1.this.setOld.prop2)
						+ (0.1F * CockpitH8K1.this.fm.EI.engines[1]
								.getControlProp());
				CockpitH8K1.this.setNew.mix2 = (0.8F * CockpitH8K1.this.setOld.mix2)
						+ (0.2F * CockpitH8K1.this.fm.EI.engines[1]
								.getControlMix());
				CockpitH8K1.this.setNew.man1 = (0.92F * CockpitH8K1.this.setOld.man1)
						+ (0.08F * CockpitH8K1.this.fm.EI.engines[0]
								.getManifoldPressure());
				CockpitH8K1.this.setNew.man2 = (0.92F * CockpitH8K1.this.setOld.man2)
						+ (0.08F * CockpitH8K1.this.fm.EI.engines[1]
								.getManifoldPressure());
				CockpitH8K1.this.setNew.altimeter = CockpitH8K1.this.fm
						.getAltitude();
				CockpitH8K1.this.setNew.azimuth.setDeg(
						CockpitH8K1.this.setOld.azimuth.getDeg(1.0F),
						CockpitH8K1.this.fm.Or.azimut());
				CockpitH8K1.this.setNew.vspeed = ((199F * CockpitH8K1.this.setOld.vspeed) + CockpitH8K1.this.fm
						.getVertSpeed()) / 200F;
			}
			return true;
		}
	}

	private class Variables {

		float throttle1;
		float throttle2;
		float prop1;
		float prop2;
		float mix1;
		float mix2;
		float man1;
		float man2;
		float altimeter;
		AnglesFork azimuth;
		float vspeed;

		private Variables() {
			this.azimuth = new AnglesFork();
		}

	}

	private Variables setOld;

	private Variables setNew;

	private Variables setTmp;

	public Vector3f w;

	private float pictAiler;

	private float pictElev;

	private static final float speedometerScale[] = { 0.0F, 6.5F, 30F, 66F,
			105F, 158.5F, 212F, 272.5F, 333F, 384F, 432.5F, 479.5F, 526.5F,
			573.5F, 624.5F, 674F };

	private static final float oilTempScale[] = { 0.0F, 4F, 17.5F, 38F, 63F,
			90.5F, 115F, 141.3F, 180F, 221.7F, 269.5F, 311F, 357F };

	private static final float rpmScale[] = { 0.0F, 10F, 75F, 126.5F, 179.5F,
			232F, 284.5F, 336F };
	private Point3d tmpP;
	private Vector3d tmpV;

	public CockpitH8K1() {
		super("3DO/Cockpit/G4M1-11/CockpitH8K1.him", "he111");
		this.setOld = new Variables();
		this.setNew = new Variables();
		this.w = new Vector3f();
		this.pictAiler = 0.0F;
		this.pictElev = 0.0F;
		this.tmpP = new Point3d();
		this.tmpV = new Vector3d();
		this.cockpitNightMats = (new String[] { "gauges5", "GP1_d1", "GP1",
				"GP2_d1", "GP2", "GP3", "GP4_d1", "GP4", "GP5_d1", "GP5",
				"GP6_d1", "GP6", "GP7", "GP9", "throttle", "Volt_Tacho" });
		this.setNightMats(false);
		this.interpPut(new Interpolater(), null, Time.current(), null);
		if (this.acoustics != null) {
			this.acoustics.globFX = new ReverbFXRoom(0.45F);
		}
	}

	protected boolean doFocusEnter() {
		if (super.doFocusEnter()) {
			this.aircraft().hierMesh().chunkVisible("Cockpit_D0", false);
			this.aircraft().hierMesh().chunkVisible("CF_D0", false);
			this.aircraft().hierMesh().chunkVisible("Pilot2_D0", false);
			return true;
		} else {
			return false;
		}
	}

	protected void doFocusLeave() {
		this.aircraft().hierMesh().chunkVisible("Cockpit_D0", true);
		this.aircraft().hierMesh().chunkVisible("CF_D0", true);
		this.aircraft().hierMesh().chunkVisible("Pilot2_D0", true);
		super.doFocusLeave();
	}

	public void reflectCockpitState() {
		if ((this.fm.AS.astateCockpitState & 2) != 0) {
			this.mesh.chunkVisible("XGlassDamage1", true);
			this.mesh.chunkVisible("XHullDamage5", true);
		}
		if ((this.fm.AS.astateCockpitState & 1) != 0) {
			this.mesh.chunkVisible("XGlassDamage2", true);
			this.mesh.chunkVisible("XGlassDamage5", true);
			this.mesh.chunkVisible("XHullDamage4", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
			this.mesh.chunkVisible("Panel_D0", false);
			this.mesh.chunkVisible("Panel_D1", true);
			this.mesh.chunkVisible("Z_Fuel1", false);
			this.mesh.chunkVisible("Z_Fuel2", false);
			this.mesh.chunkVisible("Z_TurnBank2", false);
			this.mesh.chunkVisible("Z_TurnBank3", false);
			this.mesh.chunkVisible("Z_TurnBank4", false);
			this.mesh.chunkVisible("Z_TurnBank5", false);
			this.mesh.chunkVisible("Z_Flap2", false);
			this.mesh.chunkVisible("Z_GearGreen1", false);
			this.mesh.chunkVisible("Z_GearGreen2", false);
			this.mesh.chunkVisible("Z_GearGreen3", false);
			this.mesh.chunkVisible("Z_GearRed1", false);
			this.mesh.chunkVisible("Z_GearRed2", false);
			this.mesh.chunkVisible("Z_GearRed3", false);
			this.mesh.chunkVisible("Z_RPM1", false);
			this.mesh.chunkVisible("Z_RPM2", false);
			this.mesh.chunkVisible("Z_Oil1", false);
			this.mesh.chunkVisible("Z_Oil2", false);
			this.mesh.chunkVisible("Z_fuelpress1", false);
			this.mesh.chunkVisible("Z_fuelpress2", false);
			this.mesh.chunkVisible("Z_Temp1", false);
			this.mesh.chunkVisible("Z_Temp2", false);
		}
		if ((this.fm.AS.astateCockpitState & 4) != 0) {
			this.mesh.chunkVisible("XGlassDamage3", true);
			this.mesh.chunkVisible("XHullDamage2", true);
		}
		if ((this.fm.AS.astateCockpitState & 8) != 0) {
			this.mesh.chunkVisible("XHullDamage1", true);
			this.mesh.chunkVisible("XHullDamage4", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
			this.mesh.chunkVisible("XGlassDamage4", true);
			this.mesh.chunkVisible("XHullDamage3", true);
			this.mesh.chunkVisible("XHullDamage5", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
			this.mesh.chunkVisible("XGlassDamage5", true);
			this.mesh.chunkVisible("XHullDamage1", true);
		}
		this.retoggleLight();
	}

	public void reflectWorldToInstruments(float f) {
		this.mesh.chunkSetAngles("Z_Columnbase", 0.0F, 0.0F,
				10F * (this.pictElev = (0.85F * this.pictElev)
						+ (0.15F * this.fm.CT.ElevatorControl)));
		this.mesh.chunkSetAngles("Z_ColumnL", 0.0F, 0.0F, -60.6F
				* (this.pictAiler = (0.85F * this.pictAiler)
						+ (0.15F * this.fm.CT.AileronControl)));
		this.mesh.chunkSetAngles("Z_ColumnR", 0.0F, 0.0F, -60.6F
				* this.pictAiler);
		this.mesh.chunkSetAngles("Z_Throtle1", 40F * this.interp(
				this.setNew.throttle1, this.setOld.throttle1, f), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Throtle2", 40F * this.interp(
				this.setNew.throttle2, this.setOld.throttle2, f), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Prop1",
				75.5F * this.interp(this.setNew.prop1, this.setOld.prop1, f),
				0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Prop2",
				75.5F * this.interp(this.setNew.prop2, this.setOld.prop2, f),
				0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Mixture1",
				62.9F * this.interp(this.setNew.mix1, this.setOld.mix1, f),
				0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Mixture2",
				62.9F * this.interp(this.setNew.mix2, this.setOld.mix2, f),
				0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_RPedal", 0.0F, 0.0F,
				-22.2F * this.fm.CT.getRudder());
		this.mesh.chunkSetAngles("Z_RPedalStep1", 0.0F, 0.0F, -22.2F
				* this.fm.CT.getRudder());
		this.mesh.chunkSetAngles("Z_RPedalStep2", 0.0F, 0.0F, -22.2F
				* this.fm.CT.getRudder());
		this.mesh.chunkSetAngles("Z_LPedal", 0.0F, 0.0F,
				-22.2F * this.fm.CT.getRudder());
		this.mesh.chunkSetAngles("Z_LPedalStep1", 0.0F, 0.0F, -22.2F
				* this.fm.CT.getRudder());
		this.mesh.chunkSetAngles("Z_LPedalStep2", 0.0F, 0.0F, -22.2F
				* this.fm.CT.getRudder());
		this.mesh.chunkSetAngles("Z_Compass1", 0.0F,
				-this.setNew.azimuth.getDeg(f), 0.0F);
		this.mesh.chunkSetAngles("Z_Compass2", 0.0F,
				this.setNew.azimuth.getDeg(f), 0.0F);
		this.mesh.chunkSetAngles("Z_Compass3", 0.0F,
				this.setNew.azimuth.getDeg(f), 0.0F);
		this.mesh.chunkSetAngles("Z_Compass4", 0.0F,
				this.setNew.azimuth.getDeg(f), 0.0F);
		this.mesh.chunkSetAngles("Z_Compass5", -this.setNew.azimuth.getDeg(f),
				0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_AH1", 0.0F, 0.0F, this.fm.Or.getKren());
		this.resetYPRmodifier();
		Cockpit.xyz[2] = this.cvt(this.fm.Or.getTangage(), -45F, 45F, -0.03F,
				0.03F);
		this.mesh.chunkSetLocate("Z_AH2", Cockpit.xyz, Cockpit.ypr);
		this.mesh.chunkSetAngles("Z_AH3", 0.0F, 0.0F, this.fm.Or.getKren());
		this.resetYPRmodifier();
		Cockpit.xyz[2] = this.cvt(this.fm.Or.getTangage(), -45F, 45F, -0.03F,
				0.03F);
		this.mesh.chunkSetLocate("Z_AH4", Cockpit.xyz, Cockpit.ypr);
		this.w.set(this.fm.getW());
		this.fm.Or.transform(this.w);
		float f1 = this.getBall(7D);
		this.mesh.chunkSetAngles("Z_TurnBank1",
				this.cvt(f1, -5F, 5F, 8.5F, -8.5F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_TurnBank2",
				this.cvt(this.w.z, -0.23562F, 0.23562F, 22F, -22F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_TurnBank3",
				this.cvt(f1, -7F, 7F, 16F, -16F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_TurnBank4",
				this.cvt(this.w.z, -0.23562F, 0.23562F, 22F, -22F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_TurnBank5",
				this.cvt(f1, -7F, 7F, 16F, -16F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Speedometer1", this.floatindex(
				this.cvt(
						Pitot.Indicator((float) this.fm.Loc.z,
								this.fm.getSpeedKMH()), 0.0F, 300F, 0.0F, 15F),
				speedometerScale), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Speedometer2", this.floatindex(
				this.cvt(
						Pitot.Indicator((float) this.fm.Loc.z,
								this.fm.getSpeedKMH()), 0.0F, 300F, 0.0F, 15F),
				speedometerScale), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Hour1",
				this.cvt(World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F,
				0.0F);
		this.mesh.chunkSetAngles("Z_Minute1",
				this.cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F),
				0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Altimeter1", this.cvt(
				this.interp(this.setNew.altimeter, this.setOld.altimeter, f),
				0.0F, 20000F, 0.0F, 1440F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Altimeter2", this.cvt(
				this.interp(this.setNew.altimeter, this.setOld.altimeter, f),
				0.0F, 20000F, 0.0F, 1440F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Temp1", this.cvt(
				this.fm.EI.engines[1].tWaterOut, 0.0F, 350F, 0.0F, 90F), 0.0F,
				0.0F);
		this.mesh.chunkSetAngles("Z_Temp2", this.cvt(
				this.fm.EI.engines[0].tWaterOut, 0.0F, 350F, 0.0F, 90F), 0.0F,
				0.0F);
		this.mesh.chunkSetAngles("Z_Temp3",
				this.cvt(this.fm.EI.engines[1].tOilIn, 0.0F, 350F, 0.0F, 90F),
				0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Temp4",
				this.cvt(this.fm.EI.engines[0].tOilIn, 0.0F, 350F, 0.0F, 90F),
				0.0F, 0.0F);
		float f2 = (Atmosphere.temperature((float) this.fm.Loc.z) - 273.09F)
				+ (44.4F * this.fm.EI.engines[0].getPowerOutput());
		if (f2 < 0.0F) {
			this.mesh.chunkSetAngles("Z_Temp5",
					this.cvt(f2, -40F, 0.0F, 0.0F, 45F), 0.0F, 0.0F);
		} else {
			this.mesh.chunkSetAngles("Z_Temp5",
					this.cvt(f2, 0.0F, 60F, 45F, 90F), 0.0F, 0.0F);
		}
		f2 = (Atmosphere.temperature((float) this.fm.Loc.z) - 273.09F)
				+ (44.4F * this.fm.EI.engines[1].getPowerOutput());
		if (f2 < 0.0F) {
			this.mesh.chunkSetAngles("Z_Temp6",
					this.cvt(f2, -40F, 0.0F, 0.0F, 45F), 0.0F, 0.0F);
		} else {
			this.mesh.chunkSetAngles("Z_Temp6",
					this.cvt(f2, 0.0F, 60F, 45F, 90F), 0.0F, 0.0F);
		}
		this.mesh.chunkSetAngles("Z_Temp7", this.floatindex(
				this.cvt(this.fm.EI.engines[1].tOilOut, 0.0F, 120F, 0.0F, 12F),
				oilTempScale), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Temp8", this.floatindex(
				this.cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 120F, 0.0F, 12F),
				oilTempScale), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Flap1",
				this.cvt(this.fm.CT.getFlap(), 0.0F, 0.75F, 0.0F, 90F), 0.0F,
				0.0F);
		this.mesh.chunkSetAngles("Z_Flap2", 57F * this.fm.CT.getFlap(), 0.0F,
				0.0F);
		this.mesh.chunkSetAngles("Z_Fuel1", this.cvt(
				(float) Math.sqrt(this.fm.M.fuel), 0.0F, 34.641F, 0.0F, 225F),
				0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Fuel2", this.cvt(
				(float) Math.sqrt(this.fm.M.fuel), 0.0F, 34.641F, 0.0F, 225F),
				0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Fuel3", this.cvt(
				(float) Math.sqrt(this.fm.M.fuel), 0.0F, 38.729F, 0.0F, 225F),
				0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Fuel4",
				this.cvt((float) Math.sqrt(this.fm.M.fuel), 26.925F, 38.729F,
						0.0F, 225F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Fuel5",
				this.cvt((float) Math.sqrt(this.fm.M.fuel), 26.925F, 38.729F,
						0.0F, 225F), 0.0F, 0.0F);
		this.resetYPRmodifier();
		Cockpit.xyz[2] = this.cvt(this.setNew.vspeed, -15F, 15F, -0.055F,
				0.055F);
		this.mesh.chunkSetLocate("Z_Climb1", Cockpit.xyz, Cockpit.ypr);
		this.mesh.chunkSetAngles("Z_Climb2",
				this.cvt(this.setNew.vspeed, -10F, 10F, -180F, 180F), 0.0F,
				0.0F);
		this.mesh.chunkSetAngles("Z_Manifold1",
				this.cvt(this.setNew.man1, 0.33339F, 1.66661F, -162F, 162F),
				0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Manifold2",
				this.cvt(this.setNew.man2, 0.33339F, 1.66661F, -162F, 162F),
				0.0F, 0.0F);
		this.mesh
				.chunkSetAngles(
						"Z_Oil1",
						this.cvt(
								1.0F + (0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0]
										.getReadyness()), 0.0F, 7.45F, 0.0F,
								180F), 0.0F, 0.0F);
		this.mesh
				.chunkSetAngles(
						"Z_Oil2",
						this.cvt(
								1.0F + (0.05F * this.fm.EI.engines[1].tOilOut * this.fm.EI.engines[1]
										.getReadyness()), 0.0F, 7.45F, 0.0F,
								180F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_fuelpress1", this.cvt(
				this.fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 0.5F, 0.0F, 180F),
				0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_fuelpress2", this.cvt(
				this.fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 0.5F, 0.0F, 180F),
				0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_RPM1",
				this.floatindex(this.cvt(this.fm.EI.engines[0].getRPM(), 0.0F,
						3500F, 0.0F, 7F), rpmScale), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_RPM2",
				this.floatindex(this.cvt(this.fm.EI.engines[1].getRPM(), 0.0F,
						3500F, 0.0F, 7F), rpmScale), 0.0F, 0.0F);
		if ((this.fm.AS.astateCockpitState & 0x40) == 0) {
			this.mesh
					.chunkVisible("Z_GearGreen1", this.fm.CT.getGear() > 0.99F);
			this.mesh.chunkVisible("Z_GearGreen2",
					(this.fm.CT.getGear() > 0.99F) && this.fm.Gears.rgear);
			this.mesh.chunkVisible("Z_GearGreen3",
					(this.fm.CT.getGear() > 0.99F) && this.fm.Gears.lgear);
			this.mesh.chunkVisible("Z_GearRed1", this.fm.CT.getGear() < 0.01F);
			this.mesh.chunkVisible("Z_GearRed2", (this.fm.CT.getGear() < 0.01F)
					|| !this.fm.Gears.rgear);
			this.mesh.chunkVisible("Z_GearRed3", (this.fm.CT.getGear() < 0.01F)
					|| !this.fm.Gears.lgear);
		}
	}

	private void retoggleLight() {
		if (this.cockpitLightControl) {
			this.setNightMats(false);
			this.setNightMats(true);
		} else {
			this.setNightMats(true);
			this.setNightMats(false);
		}
	}

	public void toggleLight() {
		this.cockpitLightControl = !this.cockpitLightControl;
		if (this.cockpitLightControl) {
			this.setNightMats(true);
		} else {
			this.setNightMats(false);
		}
	}

	protected float waypointAzimuth() {
		WayPoint waypoint = this.fm.AP.way.curr();
		if (waypoint == null) {
			return 0.0F;
		}
		waypoint.getP(this.tmpP);
		this.tmpV.sub(this.tmpP, this.fm.Loc);
		float f;
		for (f = (float) (57.295779513082323D * Math.atan2(-this.tmpV.y,
				this.tmpV.x)); f <= -180F; f += 180F) {
			;
		}
		for (; f > 180F; f -= 180F) {
			;
		}
		return f;
	}

}
