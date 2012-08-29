// Source File Name: CockpitAR_196A3.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;

public class CockpitAR_196A3 extends CockpitPilot {
	class Interpolater extends InterpolateRef {

		Interpolater() {
		}

		public boolean tick() {
			if (CockpitAR_196A3.this.fm != null) {
				CockpitAR_196A3.this.setTmp = CockpitAR_196A3.this.setOld;
				CockpitAR_196A3.this.setOld = CockpitAR_196A3.this.setNew;
				CockpitAR_196A3.this.setNew = CockpitAR_196A3.this.setTmp;
				CockpitAR_196A3.this.setNew.flaps = (0.9F * CockpitAR_196A3.this.setOld.flaps)
						+ (0.1F * ((FlightModelMain) (CockpitAR_196A3.this.fm)).CT.FlapsControl);
				CockpitAR_196A3.this.setNew.gear = (0.7F * CockpitAR_196A3.this.setOld.gear)
						+ (0.3F * ((FlightModelMain) (CockpitAR_196A3.this.fm)).CT.GearControl);
				CockpitAR_196A3.this.setNew.throttle = (0.8F * CockpitAR_196A3.this.setOld.throttle)
						+ (0.2F * ((FlightModelMain) (CockpitAR_196A3.this.fm)).CT.PowerControl);
				CockpitAR_196A3.this.setNew.prop = (0.8F * CockpitAR_196A3.this.setOld.prop)
						+ (0.2F * ((FlightModelMain) (CockpitAR_196A3.this.fm)).EI.engines[0]
								.getControlProp());
				CockpitAR_196A3.this.setNew.mix = (0.8F * CockpitAR_196A3.this.setOld.mix)
						+ (0.2F * ((FlightModelMain) (CockpitAR_196A3.this.fm)).EI.engines[0]
								.getControlMix());
				CockpitAR_196A3.this.setNew.divebrake = (0.8F * CockpitAR_196A3.this.setOld.divebrake)
						+ (0.2F * ((FlightModelMain) (CockpitAR_196A3.this.fm)).CT.AirBrakeControl);
				CockpitAR_196A3.this.setNew.man = (0.92F * CockpitAR_196A3.this.setOld.man)
						+ (0.08F * ((FlightModelMain) (CockpitAR_196A3.this.fm)).EI.engines[0]
								.getManifoldPressure());
				CockpitAR_196A3.this.setNew.altimeter = CockpitAR_196A3.this.fm
						.getAltitude();
				CockpitAR_196A3.this.setNew.azimuth.setDeg(
						CockpitAR_196A3.this.setOld.azimuth.getDeg(1.0F),
						((FlightModelMain) (CockpitAR_196A3.this.fm)).Or
								.azimut());
				CockpitAR_196A3.this.setNew.vspeed = ((399F * CockpitAR_196A3.this.setOld.vspeed) + CockpitAR_196A3.this.fm
						.getVertSpeed()) / 400F;
			}
			return true;
		}
	}

	private class Variables {

		float flaps;
		float gear;
		float throttle;
		float prop;
		float mix;
		float divebrake;
		float altimeter;
		float man;
		float vspeed;
		AnglesFork azimuth;

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
	private static final float speedometerScale[] = { 0.0F, 16.5F, 79.5F, 143F,
			206.5F, 229.5F, 251F, 272.5F, 294F, 316F, 339.5F };
	private static final float variometerScale[] = { 0.0F, 25F, 49.5F, 64F,
			78.5F, 89.5F, 101F, 112F, 123F, 134.5F, 145.5F, 157F, 168F, 168F };

	public CockpitAR_196A3() {
		super("3DO/Cockpit/AR_196A3/hier.him", "bf109");
		this.setOld = new Variables();
		this.setNew = new Variables();
		this.w = new Vector3f();
		this.pictAiler = 0.0F;
		this.pictElev = 0.0F;
		super.cockpitNightMats = (new String[] { "GagePanel1", "GagePanel1_D1",
				"GagePanel2", "GagePanel2_D1", "GagePanel3", "GagePanel3_D1",
				"GagePanel4", "GagePanel4_D1", "misc2" });
		this.setNightMats(false);
		this.interpPut(new Interpolater(), null, Time.current(), null);
		if (super.acoustics != null) {
			super.acoustics.globFX = new ReverbFXRoom(0.45F);
		}
	}

	public void reflectCockpitState() {
	}

	public void reflectWorldToInstruments(float f) {
		this.resetYPRmodifier();
		Cockpit.xyz[1] = this.cvt(
				((FlightModelMain) (super.fm)).CT.getCockpitDoor(), 0.01F,
				0.99F, 0.0F, 0.7F);
		super.mesh.chunkSetLocate("Canopy", Cockpit.xyz, Cockpit.ypr);
		super.mesh.chunkSetAngles("Z_Trim1", 0.0F,
				1444F * ((FlightModelMain) (super.fm)).CT
						.getTrimAileronControl(), 0.0F);
		super.mesh.chunkSetAngles("Z_Trim2", 0.0F,
				1444F * ((FlightModelMain) (super.fm)).CT
						.getTrimRudderControl(), 0.0F);
		super.mesh.chunkSetAngles("Z_Trim3", 0.0F,
				1444F * ((FlightModelMain) (super.fm)).CT
						.getTrimElevatorControl(), 0.0F);
		super.mesh.chunkSetAngles("Z_Flaps1", 0.0F, -77F * this.setNew.flaps,
				0.0F);
		super.mesh.chunkSetAngles("Z_Gear1", 0.0F, -77F * this.setNew.gear,
				0.0F);
		super.mesh.chunkSetAngles("Z_Throtle1", 0.0F, -40F
				* this.setNew.throttle, 0.0F);
		super.mesh.chunkSetAngles("Z_Prop1", 0.0F, -68F * this.setNew.prop,
				0.0F);
		super.mesh.chunkSetAngles("Z_Mixture1", 0.0F, -55F * this.setNew.mix,
				0.0F);
		super.mesh.chunkSetAngles(
				"Z_Supercharger1",
				0.0F,
				-55F
						* ((FlightModelMain) (super.fm)).EI.engines[0]
								.getControlCompressor(), 0.0F);
		super.mesh.chunkSetAngles("Z_RightPedal", 0.0F,
				20F * ((FlightModelMain) (super.fm)).CT.getRudder(), 0.0F);
		super.mesh.chunkSetAngles("Z_LeftPedal", 0.0F,
				20F * ((FlightModelMain) (super.fm)).CT.getRudder(), 0.0F);
		super.mesh
				.chunkSetAngles(
						"Z_Columnbase",
						0.0F,
						(this.pictAiler = (0.85F * this.pictAiler)
								+ (0.15F * ((FlightModelMain) (super.fm)).CT.AileronControl)) * 8F,
						0.0F);
		super.mesh
				.chunkSetAngles(
						"Z_Column",
						0.0F,
						(this.pictElev = (0.85F * this.pictElev)
								+ (0.15F * ((FlightModelMain) (super.fm)).CT.ElevatorControl)) * 8F,
						0.0F);
		super.mesh.chunkSetAngles("Z_DiveBrake1", 0.0F, -77F
				* this.setNew.divebrake, 0.0F);
		super.mesh.chunkSetAngles(
				"Z_CowlFlap1",
				0.0F,
				-28F
						* ((FlightModelMain) (super.fm)).EI.engines[0]
								.getControlRadiator(), 0.0F);
		if ((((FlightModelMain) (super.fm)).CT.Weapons[3] != null)
				&& !((FlightModelMain) (super.fm)).CT.Weapons[3][0]
						.haveBullets()) {
			super.mesh.chunkSetAngles("Z_Bomb1", 0.0F, 35F, 0.0F);
		}
		super.mesh.chunkSetAngles("Z_Altimeter1", 0.0F, this.cvt(
				this.interp(this.setNew.altimeter, this.setOld.altimeter, f),
				0.0F, 30480F, 0.0F, -3600F), 0.0F);
		super.mesh.chunkSetAngles("Z_Altimeter2", 0.0F, this.cvt(
				this.interp(this.setNew.altimeter, this.setOld.altimeter, f),
				0.0F, 30480F, 0.0F, -36000F), 0.0F);
		super.mesh
				.chunkSetAngles(
						"Z_Speedometer1",
						0.0F,
						-this.floatindex(
								this.cvt(
										Pitot.Indicator(
												(float) ((Tuple3d) (((FlightModelMain) (super.fm)).Loc)).z,
												super.fm.getSpeedKMH()), 0.0F,
										925.9998F, 0.0F, 10F), speedometerScale),
						0.0F);
		this.w.set(super.fm.getW());
		((FlightModelMain) (super.fm)).Or.transform(this.w);
		super.mesh.chunkSetAngles("Z_TurnBank1", 0.0F, this.cvt(
				((Tuple3f) (this.w)).z, -0.23562F, 0.23562F, 22F, -22F), 0.0F);
		super.mesh.chunkSetAngles("Z_TurnBank2", 0.0F,
				this.cvt(this.getBall(6D), -6F, 6F, -16F, 16F), 0.0F);
		super.mesh.chunkSetAngles("Z_TurnBank3", 0.0F,
				-((FlightModelMain) (super.fm)).Or.getKren(), 0.0F);
		this.resetYPRmodifier();
		Cockpit.xyz[2] = this.cvt(
				((FlightModelMain) (super.fm)).Or.getTangage(), -45F, 45F,
				0.025F, -0.025F);
		super.mesh.chunkSetLocate("Z_TurnBank4", Cockpit.xyz, Cockpit.ypr);
		super.mesh.chunkSetAngles(
				"Z_Climb1",
				0.0F,
				this.setNew.vspeed < 0.0F ? this.floatindex(this.cvt(
						-this.setNew.vspeed / 5.08F, 0.0F, 6F, 0.0F, 12F),
						variometerScale) : -this.floatindex(this.cvt(
						this.setNew.vspeed / 5.08F, 0.0F, 6F, 0.0F, 12F),
						variometerScale), 0.0F);
		super.mesh.chunkSetAngles("Z_Compass1", 0.0F,
				-this.setNew.azimuth.getDeg(f), 0.0F);
		super.mesh.chunkSetAngles("Z_Compass2", 0.0F,
				this.setNew.azimuth.getDeg(f), 0.0F);
		super.mesh.chunkSetAngles("Z_RPM1", 0.0F, this.cvt(
				((FlightModelMain) (super.fm)).EI.engines[0].getRPM(), 0.0F,
				10000F, 0.0F, -360F), 0.0F);
		super.mesh.chunkSetAngles("Z_Hour1", 0.0F,
				this.cvt(World.getTimeofDay(), 0.0F, 24F, 0.0F, -360F), 0.0F);
		super.mesh.chunkSetAngles("Z_Minute1", 0.0F,
				this.cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, -360F),
				0.0F);
		super.mesh.chunkSetAngles("Z_Fuel1", 0.0F, this.cvt(
				((FlightModelMain) (super.fm)).M.fuel, 0.0F, 400F, 0.0F, -87F),
				0.0F);
		super.mesh.chunkSetAngles("Z_Fuel2", 0.0F, this.cvt(
				((FlightModelMain) (super.fm)).M.fuel, 0.0F, 400F, 0.0F, -87F),
				0.0F);
		super.mesh.chunkSetAngles("Z_Fuel3", 0.0F, this.cvt(
				((FlightModelMain) (super.fm)).M.fuel, 0.0F, 400F, 0.0F, -87F),
				0.0F);
		super.mesh.chunkSetAngles("Z_Fuel4", 0.0F, this.cvt(
				((FlightModelMain) (super.fm)).M.fuel, 0.0F, 400F, 0.0F, -87F),
				0.0F);
		super.mesh.chunkSetAngles("Z_FuelPres1", 0.0F, this.cvt(
				((FlightModelMain) (super.fm)).M.fuel <= 1.0F ? 0.0F : 0.26F,
				0.0F, 0.3F, 0.0F, -180F), 0.0F);
		super.mesh.chunkSetAngles("Z_Temp1", 0.0F, this.cvt(
				((FlightModelMain) (super.fm)).EI.engines[0].tWaterOut, 0.0F,
				350F, 0.0F, -74F), 0.0F);
		super.mesh.chunkSetAngles("Z_Temp2", 0.0F, this.cvt(
				((FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 0.0F,
				100F, 0.0F, -180F), 0.0F);
		super.mesh.chunkSetAngles("Z_Pres1", 0.0F,
				this.cvt(this.setNew.man, 0.3386378F, 2.539784F, 0.0F, -344F),
				0.0F);
		super.mesh.chunkSetAngles("Z_Oil1", 0.0F, this.cvt(
				((FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 0.0F,
				200F, 0.0F, -180F), 0.0F);
		super.mesh
				.chunkSetAngles(
						"Z_Oilpres1",
						0.0F,
						this.cvt(
								1.0F + (0.05F * ((FlightModelMain) (super.fm)).EI.engines[0].tOilOut * ((FlightModelMain) (super.fm)).EI.engines[0]
										.getReadyness()), 0.0F, 7.45F, 0.0F,
								-301F), 0.0F);
		float f1 = ((FlightModelMain) (super.fm)).EI.engines[0].getRPM();
		f1 = 2.5F * (float) Math.sqrt(Math.sqrt(Math.sqrt(Math.sqrt(f1))));
		super.mesh.chunkSetAngles("Z_Suction1", 0.0F,
				this.cvt(f1, 0.0F, 10F, 0.0F, -300F), 0.0F);
		if (((FlightModelMain) (super.fm)).Gears.lgear) {
			this.resetYPRmodifier();
			Cockpit.xyz[0] = -0.133F
					* ((FlightModelMain) (super.fm)).CT.getGear();
			super.mesh.chunkSetLocate("Z_GearL1", Cockpit.xyz, Cockpit.ypr);
		}
		if (((FlightModelMain) (super.fm)).Gears.rgear) {
			this.resetYPRmodifier();
			Cockpit.xyz[0] = -0.133F
					* ((FlightModelMain) (super.fm)).CT.getGear();
			super.mesh.chunkSetLocate("Z_GearR1", Cockpit.xyz, Cockpit.ypr);
		}
		this.resetYPRmodifier();
		Cockpit.xyz[0] = -0.118F * ((FlightModelMain) (super.fm)).CT.getFlap();
		super.mesh.chunkSetLocate("Z_Flap1", Cockpit.xyz, Cockpit.ypr);
		super.mesh
				.chunkSetAngles(
						"Z_EnginePrim",
						0.0F,
						((FlightModelMain) (super.fm)).EI.engines[0].getStage() <= 0 ? 0.0F
								: -36F, 0.0F);
		super.mesh.chunkSetAngles("Z_MagSwitch", 0.0F, this.cvt(
				((FlightModelMain) (super.fm)).EI.engines[0]
						.getControlMagnetos(), 0.0F, 3F, 0.0F, -111F), 0.0F);
	}

	public void toggleLight() {
		super.cockpitLightControl = !super.cockpitLightControl;
		if (super.cockpitLightControl) {
			this.setNightMats(true);
		} else {
			this.setNightMats(false);
		}
	}

}
