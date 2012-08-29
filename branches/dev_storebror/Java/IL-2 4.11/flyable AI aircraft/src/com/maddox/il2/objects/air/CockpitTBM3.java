// Source File Name: CockpitTBM3.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;

public class CockpitTBM3 extends CockpitPilot {
	class Interpolater extends InterpolateRef {

		Interpolater() {
		}

		public boolean tick() {
			if (CockpitTBM3.this.fm != null) {
				CockpitTBM3.this.setTmp = CockpitTBM3.this.setOld;
				CockpitTBM3.this.setOld = CockpitTBM3.this.setNew;
				CockpitTBM3.this.setNew = CockpitTBM3.this.setTmp;
				CockpitTBM3.this.setNew.throttle = ((10F * CockpitTBM3.this.setOld.throttle) + CockpitTBM3.this.fm.CT.PowerControl) / 11F;
				CockpitTBM3.this.setNew.altimeter = CockpitTBM3.this.fm
						.getAltitude();
				if (Math.abs(CockpitTBM3.this.fm.Or.getKren()) < 30F) {
					CockpitTBM3.this.setNew.azimuth = ((35F * CockpitTBM3.this.setOld.azimuth) + -CockpitTBM3.this.fm.Or
							.getYaw()) / 36F;
				}
				if ((CockpitTBM3.this.setOld.azimuth > 270F)
						&& (CockpitTBM3.this.setNew.azimuth < 90F)) {
					CockpitTBM3.this.setOld.azimuth -= 360F;
				}
				if ((CockpitTBM3.this.setOld.azimuth < 90F)
						&& (CockpitTBM3.this.setNew.azimuth > 270F)) {
					CockpitTBM3.this.setOld.azimuth += 360F;
				}
				CockpitTBM3.this.setNew.waypointAzimuth = ((10F * CockpitTBM3.this.setOld.waypointAzimuth)
						+ (CockpitTBM3.this.waypointAzimuth() - CockpitTBM3.this.setOld.azimuth) + World
						.Rnd().nextFloat(-30F, 30F)) / 11F;
				CockpitTBM3.this.setNew.vspeed = ((199F * CockpitTBM3.this.setOld.vspeed) + CockpitTBM3.this.fm
						.getVertSpeed()) / 200F;
			}
			return true;
		}
	}

	private class Variables {

		float throttle;
		float altimeter;
		float azimuth;
		float vspeed;
		float waypointAzimuth;

		private Variables() {
		}

	}

	private Gun gun[] = { null, null };

	private Variables setOld;

	private Variables setNew;

	private Variables setTmp;

	private Vector3f w;

	private float pictAiler;
	private float pictElev;
	private Point3d tmpP;
	private Vector3d tmpV;

	public CockpitTBM3() {
		super("3DO/Cockpit/F2A-1/CockpitTBM3.him", "bf109");
		this.setOld = new Variables();
		this.setNew = new Variables();
		this.w = new Vector3f();
		this.pictAiler = 0.0F;
		this.pictElev = 0.0F;
		this.tmpP = new Point3d();
		this.tmpV = new Vector3d();
		this.cockpitNightMats = (new String[] { "F2ABoxes", "F2Acables",
				"F2AWindshields", "F2Agauges1", "F2Agauges3", "F2Agauges",
				"F2Azegary4" });
		this.setNightMats(false);
		this.interpPut(new Interpolater(), null, Time.current(), null);
	}

	public void reflectCockpitState() {
		if ((this.fm.AS.astateCockpitState & 0x80) != 0) {
			this.mesh.chunkVisible("Z_OilSplats_D1", true);
		}
		if ((this.fm.AS.astateCockpitState & 2) != 0) {
			this.mesh.chunkVisible("Z_Z_RETICLE", false);
			this.mesh.chunkVisible("Z_Z_MASK", false);
			this.mesh.chunkVisible("Revi_D0", false);
			this.mesh.chunkVisible("Revi_D1", true);
			this.mesh.chunkVisible("Z_Hullhole_3", true);
		}
		if ((this.fm.AS.astateCockpitState & 1) != 0) {
			this.mesh.chunkVisible("Z_Bullethole_1", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
			this.mesh.chunkVisible("Panel_D0", false);
			this.mesh.chunkVisible("Panel_D1", true);
			this.mesh.chunkVisible("Z_Manifold", false);
			this.mesh.chunkVisible("Z_Speed", false);
			this.mesh.chunkVisible("Z_Alt_Large", false);
			this.mesh.chunkVisible("Z_Alt_Small", false);
		}
		if ((this.fm.AS.astateCockpitState & 4) != 0) {
			this.mesh.chunkVisible("Z_Bullethole_2", true);
			this.mesh.chunkVisible("Z_Hullhole_1", true);
		}
		if ((this.fm.AS.astateCockpitState & 8) != 0) {
			this.mesh.chunkVisible("Z_Hullhole_2", true);
			this.mesh.chunkVisible("Z_Hullhole_3", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
			this.mesh.chunkVisible("Z_Bullethole_2", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
			this.mesh.chunkVisible("Z_Hullhole_1", true);
		}
	}

	public void reflectWorldToInstruments(float f) {
		if (this.gun[0] == null) {
			this.gun[0] = ((Aircraft) this.fm.actor)
					.getGunByHookName("_MGUN01");
			this.gun[1] = ((Aircraft) this.fm.actor)
					.getGunByHookName("_MGUN02");
		}
		this.mesh.chunkVisible("XLampGear_1", !this.fm.Gears.lgear
				|| !this.fm.Gears.lgear);
		this.mesh.chunkSetAngles("Z_Gear", 32F * this.fm.CT.getGear(), 0.0F,
				0.0F);
		this.mesh.chunkSetAngles("Z_Flap", 48F * this.fm.CT.getFlap(), 0.0F,
				0.0F);
		this.mesh.chunkSetAngles("Pedal_L", 0.0F, 20F * this.fm.CT.getRudder(),
				0.0F);
		this.mesh.chunkSetAngles(
				"Pedal1_L",
				0.0F,
				(this.fm.CT.getRudder() <= 0.0F ? 25F : 30F)
						* this.fm.CT.getRudder(), 0.0F);
		this.mesh.chunkSetAngles(
				"Pedal2_L",
				0.0F,
				(this.fm.CT.getRudder() <= 0.0F ? 20F : 25F)
						* this.fm.CT.getBrake(), 0.0F);
		this.mesh.chunkSetAngles("Pedal_R", 0.0F,
				-20F * this.fm.CT.getRudder(), 0.0F);
		this.mesh.chunkSetAngles(
				"Pedal1_R",
				0.0F,
				(this.fm.CT.getRudder() >= 0.0F ? -25F : -30F)
						* this.fm.CT.getRudder(), 0.0F);
		this.mesh.chunkSetAngles(
				"Pedal2_R",
				0.0F,
				(this.fm.CT.getRudder() >= 0.0F ? -20F : -25F)
						* this.fm.CT.getBrake(), 0.0F);
		this.mesh.chunkSetAngles("Columnbase", 0.0F, -10F
				* (this.pictAiler = (0.85F * this.pictAiler)
						+ (0.15F * this.fm.CT.AileronControl)), 0.0F);
		this.mesh.chunkSetAngles("Column", 0.0F,
				10F * (this.pictElev = (0.85F * this.pictElev)
						+ (0.15F * this.fm.CT.ElevatorControl)), 0.0F);
		this.mesh.chunkSetAngles("ColumnL", 0.0F, 10F * this.pictAiler, 0.0F);
		this.mesh.chunkSetAngles("ColumnR", 0.0F, -10F * this.pictAiler, 0.0F);
		this.mesh.chunkSetAngles(
				"Z_Manifold",
				this.fm.EI.engines[0].getManifoldPressure() >= 0.399966F ? this
						.cvt(this.fm.EI.engines[0].getManifoldPressure(),
								0.399966F, 1.599864F, 34F, 326F) : this.cvt(
						this.fm.EI.engines[0].getManifoldPressure(), 0.0F,
						0.399966F, 0.0F, 34F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Alt_Large", this.cvt(
				this.interp(this.setNew.altimeter, this.setOld.altimeter, f),
				0.0F, 9144F, 0.0F, 21600F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Alt_Small", this.cvt(
				this.interp(this.setNew.altimeter, this.setOld.altimeter, f),
				0.0F, 9144F, 0.0F, 2160F), 0.0F, 0.0F);
		this.mesh
				.chunkSetAngles(
						"Z_Speed",
						Pitot.Indicator((float) this.fm.Loc.z,
								this.fm.getSpeed()) >= 41.15554F ? this.cvt(
								Pitot.Indicator((float) this.fm.Loc.z,
										this.fm.getSpeed()), 41.15554F,
								246.9333F, 30F, 340F) : this.cvt(
								Pitot.Indicator((float) this.fm.Loc.z,
										this.fm.getSpeed()), 0.0F, 41.15554F,
								0.0F, 30F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Compass",
				90F + this.interp(this.setNew.azimuth, this.setOld.azimuth, f),
				0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Magn_Compas",
				90F + this.interp(this.setNew.azimuth, this.setOld.azimuth, f),
				0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Comp_Handle", this.cvt(this.interp(
				this.setNew.waypointAzimuth, this.setOld.waypointAzimuth, f),
				-45F, 45F, -20F, 20F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Bank",
				this.cvt(this.getBall(8D), -8F, 8F, 14F, -14F), 0.0F, 0.0F);
		this.w.set(this.fm.getW());
		this.fm.Or.transform(this.w);
		this.mesh.chunkSetAngles("Z_Turn",
				this.cvt(this.w.z, -0.23562F, 0.23562F, 34F, -34F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Climb",
				this.cvt(this.setNew.vspeed, -20F, 20F, -180F, 180F), 0.0F,
				0.0F);
		this.mesh.chunkSetAngles("Z_Clock_H",
				this.cvt(World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F,
				0.0F);
		this.mesh.chunkSetAngles("Z_Clock_Min",
				this.cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F),
				0.0F, 0.0F);
		if ((this.gun[0] != null) && this.gun[0].haveBullets()) {
			this.mesh.chunkSetAngles("Z_Ammo_W1",
					0.36F * this.gun[0].countBullets(), 0.0F, 0.0F);
			this.mesh.chunkSetAngles("Z_Ammo_W2",
					3.6F * this.gun[0].countBullets(), 0.0F, 0.0F);
			this.mesh.chunkSetAngles("Z_Ammo_W3",
					36F * this.gun[0].countBullets(), 0.0F, 0.0F);
		}
		if ((this.gun[1] != null) && this.gun[1].haveBullets()) {
			this.mesh.chunkSetAngles("Z_Ammo_W4",
					0.36F * this.gun[1].countBullets(), 0.0F, 0.0F);
			this.mesh.chunkSetAngles("Z_Ammo_W5",
					3.6F * this.gun[1].countBullets(), 0.0F, 0.0F);
			this.mesh.chunkSetAngles("Z_Ammo_W6",
					36F * this.gun[1].countBullets(), 0.0F, 0.0F);
		}
		this.mesh.chunkSetAngles("Z_Temp_Handle", this.cvt(
				this.fm.EI.engines[0].tWaterOut, 0.0F, 400F, 0.0F, 70F), 0.0F,
				0.0F);
		this.mesh
				.chunkSetAngles("Z_Temp_Eng", this.cvt(
						this.fm.EI.engines[0].tOilOut, 0.0F, 100F, 0.0F, 180F),
						0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Oil_Eng", this.cvt(
				1.0F + (0.05F * this.fm.EI.engines[0].tOilOut), 0.0F, 15F,
				0.0F, 180F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Fuel_Eng", this.cvt(
				this.fm.M.fuel <= 1.0F ? 0.0F : 10F * this.fm.EI.engines[0]
						.getPowerOutput(), 0.0F, 20F, 0.0F, 180F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Tahometr_Eng", this.cvt(
				this.fm.EI.engines[0].getRPM(), 0.0F, 2000F, 0.0F, 360F), 0.0F,
				0.0F);
		this.mesh.chunkSetAngles("Z_Ign_Switch", 0.0F,
				this.fm.EI.engines[0].getStage() != 0 ? -60F : 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Fuel_1",
				this.cvt(this.fm.M.fuel, 0.0F, 504F, 0.0F, 155F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Fuel_2",
				this.cvt(this.fm.M.fuel, 0.0F, 504F, 0.0F, 155F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Rudder_1", 0.0F,
				-60F * this.fm.CT.getTrimRudderControl(), 0.0F);
		this.mesh.chunkSetAngles("Z_Elevator_2", 0.0F,
				-60F * this.fm.CT.getTrimElevatorControl(), 0.0F);
		this.mesh.chunkSetAngles("Z_Tail_W_Lock", 0.0F,
				this.fm.Gears.bTailwheelLocked ? -40F : 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Booster_Level", 0.0F, -38F
				* this.fm.EI.engines[0].getControlMix(), 0.0F);
		this.mesh.chunkSetAngles("Z_Throttle", 0.0F, -77.27F
				* this.fm.EI.engines[0].getControlThrottle(), 0.0F);
		this.mesh.chunkSetAngles("Z_Pitch", 0.0F,
				-68F * this.fm.EI.engines[0].getControlProp(), 0.0F);
		this.mesh.chunkSetAngles("Z_Flaps_Lever", 0.0F, -45F
				* this.fm.CT.FlapsControl, 0.0F);
		this.mesh.chunkSetAngles("Z_Gear_Lever", 0.0F, -45F
				* this.fm.CT.GearControl, 0.0F);
		this.mesh.chunkSetAngles("Z_Hor_Handle", -this.fm.Or.getKren(), 0.0F,
				0.0F);
		this.resetYPRmodifier();
		Cockpit.xyz[1] = this.cvt(this.fm.Or.getTangage(), -45F, 45F, 0.019F,
				-0.019F);
		this.mesh.chunkSetLocate("Z_Hor_Handle2", Cockpit.xyz, Cockpit.ypr);
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
		} else {
			waypoint.getP(this.tmpP);
			this.tmpV.sub(this.tmpP, this.fm.Loc);
			return (float) (57.295779513082323D * Math.atan2(-this.tmpV.y,
					this.tmpV.x));
		}
	}

}
