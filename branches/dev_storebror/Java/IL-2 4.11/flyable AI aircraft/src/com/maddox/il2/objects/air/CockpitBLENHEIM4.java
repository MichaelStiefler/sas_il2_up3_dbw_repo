// Source File Name: CockpitBLENHEIM4.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-04-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;

public class CockpitBLENHEIM4 extends CockpitPilot {
	class Interpolater extends InterpolateRef {

		Interpolater() {
		}

		public boolean tick() {
			if (CockpitBLENHEIM4.this.fm != null) {
				CockpitBLENHEIM4.this.setTmp = CockpitBLENHEIM4.this.setOld;
				CockpitBLENHEIM4.this.setOld = CockpitBLENHEIM4.this.setNew;
				CockpitBLENHEIM4.this.setNew = CockpitBLENHEIM4.this.setTmp;
				CockpitBLENHEIM4.this.setNew.throttle1 = (0.85F * CockpitBLENHEIM4.this.setOld.throttle1)
						+ (((FlightModelMain) (CockpitBLENHEIM4.this.fm)).EI.engines[0]
								.getControlThrottle() * 0.15F);
				CockpitBLENHEIM4.this.setNew.throttle2 = (0.85F * CockpitBLENHEIM4.this.setOld.throttle2)
						+ (((FlightModelMain) (CockpitBLENHEIM4.this.fm)).EI.engines[1]
								.getControlThrottle() * 0.15F);
				CockpitBLENHEIM4.this.setNew.prop1 = (0.85F * CockpitBLENHEIM4.this.setOld.prop1)
						+ (((FlightModelMain) (CockpitBLENHEIM4.this.fm)).EI.engines[0]
								.getControlProp() * 0.15F);
				CockpitBLENHEIM4.this.setNew.prop2 = (0.85F * CockpitBLENHEIM4.this.setOld.prop2)
						+ (((FlightModelMain) (CockpitBLENHEIM4.this.fm)).EI.engines[1]
								.getControlProp() * 0.15F);
				CockpitBLENHEIM4.this.setNew.altimeter = CockpitBLENHEIM4.this.fm
						.getAltitude();
				float f = CockpitBLENHEIM4.this.waypointAzimuth();
				CockpitBLENHEIM4.this.setNew.azimuth.setDeg(
						CockpitBLENHEIM4.this.setOld.azimuth.getDeg(1.0F),
						((FlightModelMain) (CockpitBLENHEIM4.this.fm)).Or
								.azimut());
				CockpitBLENHEIM4.this.setNew.waypointAzimuth.setDeg(
						CockpitBLENHEIM4.this.setOld.waypointAzimuth
								.getDeg(1.0F), f);
				CockpitBLENHEIM4.this.setNew.waypointDeviation.setDeg(
						CockpitBLENHEIM4.this.setOld.waypointDeviation
								.getDeg(0.1F),
						(f - CockpitBLENHEIM4.this.setOld.azimuth.getDeg(1.0F))
								+ World.Rnd().nextFloat(-10F, 10F));
				if ((((FlightModelMain) (CockpitBLENHEIM4.this.fm)).AS.astateCockpitState & 0x40) == 0) {
					CockpitBLENHEIM4.this.setNew.vspeed = ((199F * CockpitBLENHEIM4.this.setOld.vspeed) + CockpitBLENHEIM4.this.fm
							.getVertSpeed()) / 200F;
				} else {
					CockpitBLENHEIM4.this.setNew.vspeed = ((1990F * CockpitBLENHEIM4.this.setOld.vspeed) + CockpitBLENHEIM4.this.fm
							.getVertSpeed()) / 2000F;
				}
				World.land();
				CockpitBLENHEIM4.this.setNew.radioalt = (0.9F * CockpitBLENHEIM4.this.setOld.radioalt)
						+ (0.1F * ((CockpitBLENHEIM4.this.fm.getAltitude() - Landscape
								.HQ((float) ((Tuple3d) (((FlightModelMain) (CockpitBLENHEIM4.this.fm)).Loc)).x,
										(float) ((Tuple3d) (((FlightModelMain) (CockpitBLENHEIM4.this.fm)).Loc)).y)) + World
								.Rnd().nextFloat(-10F, 10F)));
			}
			return true;
		}
	}

	private class Variables {

		float throttle1;
		float throttle2;
		float prop1;
		float prop2;
		float altimeter;
		AnglesFork azimuth;
		AnglesFork waypointAzimuth;
		float vspeed;
		AnglesFork waypointDeviation;
		float radioalt;

		private Variables() {
			this.azimuth = new AnglesFork();
			this.waypointAzimuth = new AnglesFork();
			this.waypointDeviation = new AnglesFork();
		}

	}

	private Variables setOld;

	private Variables setNew;

	private Variables setTmp;

	public Vector3f w;

	private float pictAiler;

	private float pictElev;

	private float pictFlap;
	private float pictGear;
	private float pictManf1;
	private float pictManf2;
	private static final float speedometerScaleFAF[] = { 0.0F, 0.0F, 2.0F, 6F,
			21F, 40F, 56F, 72.5F, 89.5F, 114F, 135.5F, 157F, 182.5F, 205F,
			227.5F, 246.5F, 265.5F, 286F, 306F, 326F, 345.5F, 363F, 385F, 401F,
			414.5F, 436.5F, 457F, 479F, 496.5F, 515F, 539F, 559.5F, 576.5F };
	private static final float variometerScale[] = { -158F, -110F, -63.5F,
			-29F, 0.0F, 29F, 63.5F, 110F, 158F };
	private static final float rpmScale[] = { 0.0F, 20F, 54F, 99F, 151.5F,
			195.5F, 249.25F, 284.5F, 313.75F };
	private static final float radScale[] = { 0.0F, 3F, 7F, 13.5F, 21.5F, 27F,
			34.5F, 50.5F, 71F, 94F, 125F, 161F, 202.5F, 253F, 315.5F };
	private Point3d tmpP;
	private Vector3d tmpV;

	public CockpitBLENHEIM4() {
		super("3DO/Cockpit/BlenheimMk4/hier.him", "bf109");
		this.setOld = new Variables();
		this.setNew = new Variables();
		this.w = new Vector3f();
		this.pictAiler = 0.0F;
		this.pictElev = 0.0F;
		this.pictFlap = 0.0F;
		this.pictGear = 0.0F;
		this.pictManf1 = 1.0F;
		this.pictManf2 = 1.0F;
		this.tmpP = new Point3d();
		this.tmpV = new Vector3d();
		super.cockpitNightMats = (new String[] { "prib_one_fin", "prib_two",
				"prib_three", "prib_four", "gauges2", "prib_one_fin_damage",
				"prib_two_damage", "prib_three_damage", "prib_four_damage",
				"gauges2_damage", "PEICES1", "PEICES2" });
		this.setNightMats(false);
		this.interpPut(new Interpolater(), null, Time.current(), null);
		if (super.acoustics != null) {
			super.acoustics.globFX = new ReverbFXRoom(0.45F);
		}
	}

	public void reflectCockpitState() {
		if ((((FlightModelMain) (super.fm)).AS.astateCockpitState & 0x80) != 0) {
			super.mesh.chunkVisible("Z_OilSplats1_D1", true);
			super.mesh.chunkVisible("Z_OilSplats2_D1", true);
		}
		if ((((FlightModelMain) (super.fm)).AS.astateCockpitState & 2) != 0) {
			super.mesh.chunkVisible("Z_Z_RETICLE", false);
			super.mesh.chunkVisible("Z_Z_MASK", false);
			super.mesh.chunkVisible("XGlassDamage1", true);
			super.mesh.chunkVisible("XHullDamage1", true);
		}
		if ((((FlightModelMain) (super.fm)).AS.astateCockpitState & 1) != 0) {
			super.mesh.chunkVisible("XGlassDamage4", true);
			super.mesh.chunkVisible("XHullDamage1", true);
		}
		if ((((FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) != 0) {
			super.mesh.chunkVisible("XHullDamage1", true);
			super.mesh.chunkVisible("Panel_D0", false);
			super.mesh.chunkVisible("Panel_D1", true);
			super.mesh.chunkVisible("Z_Fuel2", false);
			super.mesh.chunkVisible("Z_Altimeter1", false);
			super.mesh.chunkVisible("Z_Altimeter2", false);
			super.mesh.chunkVisible("Z_Altimeter3", false);
			super.mesh.chunkVisible("Z_Hour1", false);
			super.mesh.chunkVisible("Z_Minute1", false);
			super.mesh.chunkVisible("Z_Oilpres1", false);
			super.mesh.chunkVisible("Z_FuelPres2", false);
			super.mesh.chunkVisible("Z_TurnBank1", false);
			super.mesh.chunkVisible("Z_TurnBank2", false);
		}
		if ((((FlightModelMain) (super.fm)).AS.astateCockpitState & 4) != 0) {
			super.mesh.chunkVisible("XGlassDamage3", true);
		}
		if ((((FlightModelMain) (super.fm)).AS.astateCockpitState & 8) != 0) {
			super.mesh.chunkVisible("XHullDamage2", true);
		}
		if ((((FlightModelMain) (super.fm)).AS.astateCockpitState & 0x10) != 0) {
			super.mesh.chunkVisible("XGlassDamage2", true);
		}
		if ((((FlightModelMain) (super.fm)).AS.astateCockpitState & 0x20) != 0) {
			super.mesh.chunkVisible("XHullDamage3", true);
		}
		this.retoggleLight();
	}

	public void reflectWorldToInstruments(float f) {
		this.resetYPRmodifier();
		super.mesh.chunkSetAngles("Canopy", 0.0F, this.cvt(
				((FlightModelMain) (super.fm)).CT.getCockpitDoor(), 0.01F,
				0.99F, 0.0F, -120F), 0.0F);
		super.mesh.chunkSetAngles("Z_Trim1", 0.0F,
				161F * ((FlightModelMain) (super.fm)).CT
						.getTrimAileronControl(), 0.0F);
		super.mesh
				.chunkSetAngles("Z_Trim2", 0.0F,
						332F * ((FlightModelMain) (super.fm)).CT
								.getTrimRudderControl(), 0.0F);
		super.mesh.chunkSetAngles("Z_Trim3", 0.0F,
				722F * ((FlightModelMain) (super.fm)).CT
						.getTrimElevatorControl(), 0.0F);
		super.mesh
				.chunkSetAngles(
						"Z_Flaps1",
						0.0F,
						-75.5F
								* (this.pictFlap = (0.85F * this.pictFlap)
										+ (0.15F * ((FlightModelMain) (super.fm)).CT.FlapsControl)),
						0.0F);
		super.mesh
				.chunkSetAngles(
						"Z_Gear1",
						0.0F,
						-75.5F
								* (this.pictGear = (0.85F * this.pictGear)
										+ (0.15F * ((FlightModelMain) (super.fm)).CT.GearControl)),
						0.0F);
		super.mesh.chunkSetAngles("Z_Throtle1", 0.0F, 90F * this.interp(
				this.setNew.throttle1, this.setOld.throttle1, f), 0.0F);
		super.mesh.chunkSetAngles("Z_Prop1", 0.0F,
				100F * this.interp(this.setNew.prop1, this.setOld.prop1, f),
				0.0F);
		super.mesh.chunkSetAngles("Z_Prop2", 0.0F,
				100F * this.interp(this.setNew.prop2, this.setOld.prop2, f),
				0.0F);
		super.mesh.chunkSetAngles("Z_Mixture1", 0.0F,
				90F * ((FlightModelMain) (super.fm)).EI.engines[0]
						.getControlMix(), 0.0F);
		super.mesh.chunkSetAngles("Z_Supercharger1", 0.0F, this.cvt(
				((FlightModelMain) (super.fm)).EI.engines[0]
						.getControlCompressor(), 0.0F, 1.0F, 0.0F, -50F), 0.0F);
		super.mesh.chunkSetAngles("Z_Supercharger1", 0.0F, this.cvt(
				((FlightModelMain) (super.fm)).EI.engines[1]
						.getControlCompressor(), 0.0F, 1.0F, 0.0F, -50F), 0.0F);
		super.mesh.chunkSetAngles("Z_RightPedal", 0.0F, -10F
				* ((FlightModelMain) (super.fm)).CT.getRudder(), 0.0F);
		super.mesh.chunkSetAngles("Z_LeftPedal", 0.0F,
				10F * ((FlightModelMain) (super.fm)).CT.getRudder(), 0.0F);
		super.mesh
				.chunkSetAngles(
						"Z_Columnbase",
						0.0F,
						(this.pictElev = (0.85F * this.pictElev)
								+ (0.15F * ((FlightModelMain) (super.fm)).CT.ElevatorControl)) * 8F,
						0.0F);
		super.mesh
				.chunkSetAngles(
						"Z_Column",
						0.0F,
						(this.pictAiler = (0.85F * this.pictAiler)
								+ (0.15F * ((FlightModelMain) (super.fm)).CT.AileronControl)) * 57F,
						0.0F);
		super.mesh.chunkSetAngles("Z_Brake", 0.0F, -25F
				* ((FlightModelMain) (super.fm)).CT.getBrake(), 0.0F);
		super.mesh.chunkSetAngles("Z_Altimeter2", 0.0F, this.cvt(
				this.interp(this.setNew.altimeter, this.setOld.altimeter, f),
				0.0F, 9144F, 0.0F, 10800F), 0.0F);
		super.mesh.chunkSetAngles("Z_Altimeter1", 0.0F, this.cvt(
				this.interp(this.setNew.altimeter, this.setOld.altimeter, f),
				0.0F, 9144F, 0.0F, 1080F), 0.0F);
		super.mesh.chunkSetAngles("Z_Altimeter3", 0.0F, this.cvt(
				this.interp(this.setNew.radioalt, this.setOld.radioalt, f),
				0.0F, 609.6F, 0.0F, 720F), 0.0F);
		if ((((FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) == 0) {
			super.mesh
					.chunkSetAngles(
							"Z_Speedometer1",
							0.0F,
							this.floatindex(
									this.cvt(
											Pitot.Indicator(
													(float) ((Tuple3d) (((FlightModelMain) (super.fm)).Loc)).z,
													super.fm.getSpeed()), 0.0F,
											143.0528F, 0.0F, 32F),
									speedometerScaleFAF), 0.0F);
		}
		this.w.set(super.fm.getW());
		((FlightModelMain) (super.fm)).Or.transform(this.w);
		super.mesh.chunkSetAngles("Z_TurnBank2", 0.0F, this.cvt(
				((Tuple3f) (this.w)).z, -0.23562F, 0.23562F, -48F, 48F), 0.0F);
		super.mesh.chunkSetAngles("Z_TurnBank1", 0.0F,
				this.cvt(this.getBall(8D), -8F, 8F, 35F, -35F), 0.0F);
		super.mesh.chunkSetAngles("Z_TurnBank3", 0.0F,
				-((FlightModelMain) (super.fm)).Or.getKren(), 0.0F);
		this.resetYPRmodifier();
		Cockpit.xyz[2] = this.cvt(
				((FlightModelMain) (super.fm)).Or.getTangage(), -45F, 45F,
				0.0275F, -0.0275F);
		super.mesh.chunkSetLocate("Z_TurnBank4", Cockpit.xyz, Cockpit.ypr);
		super.mesh.chunkSetAngles("Z_Climb1", 0.0F, this.floatindex(
				this.cvt(this.setNew.vspeed, -20.32F, 20.32F, 0.0F, 8F),
				variometerScale), 0.0F);
		super.mesh.chunkSetAngles("Z_Compass1", 0.0F,
				-this.setNew.azimuth.getDeg(f), 0.0F);
		super.mesh.chunkSetAngles("Z_Compass2", 0.0F,
				this.setNew.azimuth.getDeg(f), 0.0F);
		super.mesh.chunkSetAngles("Z_RPM1", 0.0F, this.floatindex(this.cvt(
				((FlightModelMain) (super.fm)).EI.engines[0].getRPM(), 1000F,
				5000F, 0.0F, 8F), rpmScale), 0.0F);
		if ((((FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) == 0) {
			super.mesh.chunkSetAngles("Z_RPM2", 0.0F, this.floatindex(this.cvt(
					((FlightModelMain) (super.fm)).EI.engines[1].getRPM(),
					1000F, 5000F, 0.0F, 8F), rpmScale), 0.0F);
		} else {
			super.mesh.chunkSetAngles("Z_RPM2", 0.0F, this.floatindex(this.cvt(
					((FlightModelMain) (super.fm)).EI.engines[1].getRPM(),
					500F, 9800F, 0.0F, 8F), rpmScale), 0.0F);
		}
		super.mesh.chunkSetAngles("Z_Hour1", 0.0F,
				this.cvt(World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
		super.mesh.chunkSetAngles("Z_Minute1", 0.0F,
				this.cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F),
				0.0F);
		super.mesh.chunkSetAngles("Z_Fuel1", 0.0F, 0.0F, this.cvt(
				((FlightModelMain) (super.fm)).M.fuel, 0.0F, 2332F, 0.0F, 77F));
		super.mesh.chunkSetAngles("Z_Fuel2", 0.0F, 0.0F, this.cvt(
				((FlightModelMain) (super.fm)).M.fuel, 0.0F, 2332F, 0.0F, 77F));
		super.mesh.chunkSetAngles("Z_FuelPres1", 0.0F, this.cvt(
				((FlightModelMain) (super.fm)).EI.engines[0].getRPM() / 910F,
				0.0F, 10F, 0.0F, 277F), 0.0F);
		super.mesh.chunkSetAngles("Z_FuelPres2", 0.0F, this.cvt(
				((FlightModelMain) (super.fm)).EI.engines[1].getRPM() / 880F,
				0.0F, 10F, 0.0F, 277F), 0.0F);
		super.mesh.chunkSetAngles("Z_Temp1", 0.0F, this.floatindex(this.cvt(
				((FlightModelMain) (super.fm)).EI.engines[0].tOilIn, 0.0F,
				140F, 0.0F, 14F), radScale), 0.0F);
		if ((((FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) == 0) {
			super.mesh.chunkSetAngles("Z_Temp2", 0.0F, this.floatindex(this
					.cvt(((FlightModelMain) (super.fm)).EI.engines[1].tOilIn,
							0.0F, 140F, 0.0F, 14F), radScale), 0.0F);
		}
		super.mesh.chunkSetAngles(
				"Z_Pres1",
				0.0F,
				this.pictManf1 = (0.9F * this.pictManf1)
						+ (0.1F * this.cvt(
								((FlightModelMain) (super.fm)).EI.engines[0]
										.getManifoldPressure(), 0.3386378F,
								2.539784F, 0.0F, 346.5F)), 0.0F);
		super.mesh.chunkSetAngles(
				"Z_Pres2",
				0.0F,
				this.pictManf2 = (0.9F * this.pictManf2)
						+ (0.1F * this.cvt(
								((FlightModelMain) (super.fm)).EI.engines[0]
										.getManifoldPressure(), 0.3386378F,
								2.539784F, 0.0F, 346.5F)), 0.0F);
		super.mesh.chunkSetAngles("Z_Oil1", 0.0F, this.cvt(
				((FlightModelMain) (super.fm)).EI.engines[0].tOilOut, 40F,
				100F, 0.0F, 274F), 0.0F);
		if ((((FlightModelMain) (super.fm)).AS.astateCockpitState & 0x40) == 0) {
			super.mesh.chunkSetAngles("Z_Oil2", 0.0F, this.cvt(
					((FlightModelMain) (super.fm)).EI.engines[1].tOilOut, 40F,
					100F, 0.0F, 274F), 0.0F);
		}
		if (super.fm.getOverload() < 0.0F) {
			super.mesh
					.chunkSetAngles(
							"Z_Oilpres1",
							0.0F,
							this.cvt(
									1.0F
											+ (0.05F * (((FlightModelMain) (super.fm)).EI.engines[0].tOilOut / 10F))
											+ (((FlightModelMain) (super.fm)).EI.engines[0]
													.getRPM() / 800F)
											+ (super.fm.getOverload() / 1.8F),
									0.0F, 12.59F, 0.0F, 277F), 0.0F);
			super.mesh
					.chunkSetAngles(
							"Z_Oilpres2",
							0.0F,
							this.cvt(
									1.0F
											+ (0.05F * (((FlightModelMain) (super.fm)).EI.engines[1].tOilOut / 10F))
											+ (((FlightModelMain) (super.fm)).EI.engines[1]
													.getRPM() / 820F)
											+ (super.fm.getOverload() / 1.8F),
									0.0F, 12.59F, 0.0F, 277F), 0.0F);
		} else {
			super.mesh
					.chunkSetAngles(
							"Z_Oilpres1",
							0.0F,
							this.cvt(
									1.0F
											+ (0.05F * (((FlightModelMain) (super.fm)).EI.engines[0].tOilOut / 10F))
											+ (((FlightModelMain) (super.fm)).EI.engines[0]
													.getRPM() / 800F)
											+ (super.fm.getOverload() / 3.8F),
									0.0F, 12.59F, 0.0F, 277F), 0.0F);
		}
		super.mesh
				.chunkSetAngles(
						"Z_Oilpres2",
						0.0F,
						this.cvt(
								1.0F
										+ (0.05F * (((FlightModelMain) (super.fm)).EI.engines[1].tOilOut / 10F))
										+ (((FlightModelMain) (super.fm)).EI.engines[1]
												.getRPM() / 820F)
										+ (super.fm.getOverload() / 3.8F),
								0.0F, 12.59F, 0.0F, 277F), 0.0F);
		float f1 = (0.5F * ((FlightModelMain) (super.fm)).EI.engines[0]
				.getRPM())
				+ (0.5F * ((FlightModelMain) (super.fm)).EI.engines[1].getRPM());
		f1 = 2.5F * (float) Math.sqrt(Math.sqrt(Math.sqrt(Math.sqrt(f1))));
		super.mesh.chunkSetAngles("Z_Suction", 0.0F,
				this.cvt(f1, 0.0F, 10F, 0.0F, 302F), 0.0F);
		super.mesh.chunkSetAngles("Z_Approach", 0.0F, this.cvt(
				this.setNew.waypointDeviation.getDeg(f), -90F, 90F, -46.5F,
				46.5F), 0.0F);
		super.mesh
				.chunkSetAngles(
						"Z_AirTemp",
						0.0F,
						this.cvt(
								Atmosphere
										.temperature((float) ((Tuple3d) (((FlightModelMain) (super.fm)).Loc)).z) - 273.15F,
								-17.8F, 60F, 0.0F, -109.5F), 0.0F);
	}

	private void retoggleLight() {
		if (super.cockpitLightControl) {
			this.setNightMats(false);
			this.setNightMats(true);
		} else {
			this.setNightMats(true);
			this.setNightMats(false);
		}
	}

	public void toggleLight() {
		super.cockpitLightControl = !super.cockpitLightControl;
		if (super.cockpitLightControl) {
			this.setNightMats(true);
		} else {
			this.setNightMats(false);
		}
	}

	protected float waypointAzimuth() {
		WayPoint waypoint = ((FlightModelMain) (super.fm)).AP.way.curr();
		if (waypoint == null) {
			return 0.0F;
		}
		waypoint.getP(this.tmpP);
		this.tmpV.sub(this.tmpP, ((FlightModelMain) (super.fm)).Loc);
		float f;
		for (f = (float) (57.295779513082323D * Math.atan2(
				-((Tuple3d) (this.tmpV)).y, ((Tuple3d) (this.tmpV)).x)); f <= -180F; f += 180F) {
			;
		}
		for (; f > 180F; f -= 180F) {
			;
		}
		return f;
	}

}
