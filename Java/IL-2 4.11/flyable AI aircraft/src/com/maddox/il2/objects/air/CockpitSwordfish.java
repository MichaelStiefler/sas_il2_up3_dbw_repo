// Source File Name: CockpitSwordfish.java
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
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

public class CockpitSwordfish extends CockpitPilot {
	class Interpolater extends InterpolateRef {

		Interpolater() {
		}

		public boolean tick() {
			if (CockpitSwordfish.this.fm != null) {
				CockpitSwordfish.this.setTmp = CockpitSwordfish.this.setOld;
				CockpitSwordfish.this.setOld = CockpitSwordfish.this.setNew;
				CockpitSwordfish.this.setNew = CockpitSwordfish.this.setTmp;
				CockpitSwordfish.this.setNew.throttle = (0.92F * CockpitSwordfish.this.setOld.throttle)
						+ (0.08F * CockpitSwordfish.this.fm.CT.PowerControl);
				CockpitSwordfish.this.setNew.prop = (0.92F * CockpitSwordfish.this.setOld.prop)
						+ (0.08F * CockpitSwordfish.this.fm.EI.engines[0]
								.getControlProp());
				CockpitSwordfish.this.setNew.mix = (0.92F * CockpitSwordfish.this.setOld.mix)
						+ (0.08F * CockpitSwordfish.this.fm.EI.engines[0]
								.getControlMix());
				CockpitSwordfish.this.setNew.altimeter = CockpitSwordfish.this.fm
						.getAltitude();
				CockpitSwordfish.this.setNew.azimuth = (0.97F * CockpitSwordfish.this.setOld.azimuth)
						+ (0.03F * -CockpitSwordfish.this.fm.Or.getYaw());
				if ((CockpitSwordfish.this.setOld.azimuth > 270F)
						&& (CockpitSwordfish.this.setNew.azimuth < 90F)) {
					CockpitSwordfish.this.setOld.azimuth -= 360F;
				}
				if ((CockpitSwordfish.this.setOld.azimuth < 90F)
						&& (CockpitSwordfish.this.setNew.azimuth > 270F)) {
					CockpitSwordfish.this.setOld.azimuth += 360F;
				}
				CockpitSwordfish.this.setNew.waypointAzimuth = (0.91F * CockpitSwordfish.this.setOld.waypointAzimuth)
						+ (0.09F * (CockpitSwordfish.this.waypointAzimuth() - CockpitSwordfish.this.setOld.azimuth))
						+ World.Rnd().nextFloat(-10F, 10F);
				CockpitSwordfish.this.setNew.vspeed = (0.99F * CockpitSwordfish.this.setOld.vspeed)
						+ (0.01F * CockpitSwordfish.this.fm.getVertSpeed());
				if (CockpitSwordfish.this.fm.CT.GearControl < 0.5F) {
					if (CockpitSwordfish.this.setNew.gearPhi > 0.0F) {
						CockpitSwordfish.this.setNew.gearPhi = CockpitSwordfish.this.setOld.gearPhi - 0.021F;
					}
				} else if (CockpitSwordfish.this.setNew.gearPhi < 1.0F) {
					CockpitSwordfish.this.setNew.gearPhi = CockpitSwordfish.this.setOld.gearPhi + 0.021F;
				}
			}
			return true;
		}
	}

	private class Variables {

		float throttle;
		float prop;
		float mix;
		float altimeter;
		float azimuth;
		float vspeed;
		float gearPhi;
		float waypointAzimuth;

		private Variables() {
		}

	}

	private Variables setOld;

	private Variables setNew;

	private Variables setTmp;

	public Vector3f w;

	private float pictAiler;

	private float pictElev;

	private float pictFlap;
	private float pictSupc;
	private float pictLlit;
	private float pictManf;
	private boolean bNeedSetUp;
	private static final float speedometerScale[] = { 0.0F, 15.5F, 76F, 153.5F,
			234F, 304F, 372.5F, 440F, 504F, 566F, 630F };
	private static final float radScale[] = { 0.0F, 3F, 7F, 13.5F, 30.5F,
			40.5F, 51.5F, 68F, 89F, 114F, 145.5F, 181F, 222F, 270.5F, 331.5F };
	private static final float rpmScale[] = { 0.0F, 15F, 32F, 69.5F, 106.5F,
			143F, 180F, 217.5F, 253F, 290F, 327.5F };
	private static final float variometerScale[] = { -158F, -111F, -65.5F,
			-32.5F, 0.0F, 32.5F, 65.5F, 111F, 158F };
	private Point3d tmpP;
	private Vector3d tmpV;

	public CockpitSwordfish() {
		super("3DO/Cockpit/TempestMkV/swordhier.him", "bf109");
		this.setOld = new Variables();
		this.setNew = new Variables();
		this.w = new Vector3f();
		this.pictAiler = 0.0F;
		this.pictElev = 0.0F;
		this.pictFlap = 0.0F;
		this.pictSupc = 0.0F;
		this.pictLlit = 0.0F;
		this.pictManf = 1.0F;
		this.bNeedSetUp = true;
		this.tmpP = new Point3d();
		this.tmpV = new Vector3d();
		this.cockpitNightMats = (new String[] { "TEMPPIT5-op", "TEMPPIT6-op",
				"TEMPPIT14-op", "TEMPPIT18-op", "TEMPPIT22-op", "TEMPPIT28-op",
				"TEMPPIT38-op", "TEMPPIT1-tr", "TEMPPIT2-tr", "TEMPPIT3-tr",
				"TEMPPIT4-tr", "TEMPPIT5-tr", "TEMPPIT6-tr", "TEMPPIT14-tr",
				"TEMPPIT18-tr", "TEMPPIT22-tr", "TEMPPIT28-tr", "TEMPPIT38-tr",
				"TEMPPIT1_damage", "TEMPPIT3_damage" });
		this.setNightMats(false);
		this.interpPut(new Interpolater(), null, Time.current(), null);
	}

	public void reflectCockpitState() {
		if ((this.fm.AS.astateCockpitState & 0x80) != 0) {
			this.mesh.chunkVisible("Z_OilSplats_D1", true);
		}
		if ((this.fm.AS.astateCockpitState & 4) != 0) {
			this.mesh.chunkVisible("XGlassDamage4", true);
		}
		if ((this.fm.AS.astateCockpitState & 8) != 0) {
			this.mesh.chunkVisible("XGlassDamage2", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
			this.mesh.chunkVisible("XGlassDamage3", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
			this.mesh.chunkVisible("XGlassDamage2", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
			this.mesh.chunkVisible("Panel_D0", false);
			this.mesh.chunkVisible("Panel_D1", true);
			this.mesh.chunkVisible("STRELK_V_LONG", false);
			this.mesh.chunkVisible("STREL_ALT_LONG", false);
			this.mesh.chunkVisible("STREL_ALT_SHORT", false);
			this.mesh.chunkVisible("STRELKA_VY", false);
			this.mesh.chunkVisible("STRELKA_RPM", false);
			this.mesh.chunkVisible("STRELK_TEMP_RAD", false);
			this.mesh.chunkVisible("STRELK_TEMP_OIL", false);
		}
		if ((this.fm.AS.astateCockpitState & 1) != 0) {
			this.mesh.chunkVisible("XGlassDamage2", true);
		}
		if ((this.fm.AS.astateCockpitState & 2) != 0) {
			this.mesh.chunkVisible("XGlassDamage1", true);
		}
		this.retoggleLight();
	}

	protected void reflectPlaneMats() {
		HierMesh hiermesh = this.aircraft().hierMesh();
		com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh
				.materialFind("Gloss1D0o"));
		this.mesh.materialReplace("Gloss1D0o", mat);
	}

	public void reflectWorldToInstruments(float f) {
		if (this.bNeedSetUp) {
			this.reflectPlaneMats();
			this.bNeedSetUp = false;
		}
		this.mesh
				.chunkVisible(
						"XLampGearUpR",
						((this.fm.CT.getGear() > 0.01F) && (this.fm.CT
								.getGear() < 0.99F)) || !this.fm.Gears.rgear);
		this.mesh
				.chunkVisible(
						"XLampGearUpL",
						((this.fm.CT.getGear() > 0.01F) && (this.fm.CT
								.getGear() < 0.99F)) || !this.fm.Gears.lgear);
		this.mesh.chunkVisible("XLampGearUpC", (this.fm.CT.getGear() > 0.01F)
				&& (this.fm.CT.getGear() < 0.99F));
		this.mesh.chunkVisible("XLampGearDownR", (this.fm.CT.getGear() > 0.99F)
				&& this.fm.Gears.rgear);
		this.mesh.chunkVisible("XLampGearDownL", (this.fm.CT.getGear() > 0.99F)
				&& this.fm.Gears.lgear);
		this.mesh.chunkVisible("XLampGearDownC", this.fm.CT.getGear() > 0.99F);
		this.mesh.chunkSetAngles("Z_Columnbase",
				16F * (this.pictElev = (0.85F * this.pictElev)
						+ (0.15F * this.fm.CT.ElevatorControl)), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Column",
				45F * (this.pictAiler = (0.85F * this.pictAiler)
						+ (0.15F * this.fm.CT.AileronControl)), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Elev", -16F * this.pictElev, 0.0F, 0.0F);
		this.resetYPRmodifier();
		Cockpit.xyz[2] = this.cvt(this.pictAiler, -1F, 1.0F, -0.027F, 0.027F);
		this.mesh.chunkSetLocate("Z_Shlang01", Cockpit.xyz, Cockpit.ypr);
		Cockpit.xyz[2] = -Cockpit.xyz[2];
		this.mesh.chunkSetLocate("Z_Shlang02", Cockpit.xyz, Cockpit.ypr);
		this.mesh.chunkSetAngles("Z_Throtle1", 65.45F * this.interp(
				this.setNew.throttle, this.setOld.throttle, f), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_BasePedal", 20F * this.fm.CT.getRudder(),
				0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_LeftPedal", 0.0F, 0.0F,
				-20F * this.fm.CT.getRudder());
		this.mesh.chunkSetAngles("Z_RightPedal", 0.0F, 0.0F,
				-20F * this.fm.CT.getRudder());
		this.mesh.chunkSetAngles("Z_Gear1",
				this.cvt(this.setNew.gearPhi, 0.2F, 0.8F, 0.0F, 116F), 0.0F,
				0.0F);
		if (this.setNew.gearPhi < 0.5F) {
			this.mesh.chunkSetAngles("Z_Gear2",
					this.cvt(this.setNew.gearPhi, 0.0F, 0.2F, 0.0F, -65F),
					0.0F, 0.0F);
		} else {
			this.mesh.chunkSetAngles("Z_Gear2",
					this.cvt(this.setNew.gearPhi, 0.8F, 1.0F, -65F, 0.0F),
					0.0F, 0.0F);
		}
		float f1;
		if (Math.abs(this.fm.CT.FlapsControl - this.fm.CT.getFlap()) > 0.02F) {
			if ((this.fm.CT.FlapsControl - this.fm.CT.getFlap()) > 0.0F) {
				f1 = 24F;
			} else {
				f1 = -24F;
			}
		} else {
			f1 = 0.0F;
		}
		this.mesh.chunkSetAngles("Z_Flaps1",
				this.pictFlap = (0.8F * this.pictFlap) + (0.2F * f1), 0.0F,
				0.0F);
		this.mesh.chunkSetAngles("Z_Trim1",
				1000F * this.fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Prop1", 72.5F * this.setNew.prop, 0.0F,
				0.0F);
		this.mesh.chunkSetAngles("Z_Surch1",
				this.cvt(
						this.pictSupc = (0.8F * this.pictSupc)
								+ (0.1F * this.fm.EI.engines[0]
										.getControlCompressor()), 0.0F, 1.0F,
						0.0F, 60F), 0.0F, 0.0F);
		f1 = 0.0F;
		if (this.fm.AS.bLandingLightOn) {
			f1 = 66F;
		}
		this.mesh.chunkSetAngles("Z_Land1",
				this.pictLlit = (0.85F * this.pictLlit) + (0.15F * f1), 0.0F,
				0.0F);
		this.mesh.chunkSetAngles("COMPASS_M",
				this.interp(this.setNew.azimuth, this.setOld.azimuth, f), 0.0F,
				0.0F);
		this.mesh.chunkSetAngles("STRELK_V_LONG", -this.floatindex(this.cvt(
				Pitot.Indicator((float) this.fm.Loc.z, this.fm.getSpeed()),
				0.0F, 257.2222F, 0.0F, 10F), speedometerScale), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("STREL_ALT_LONG", this.cvt(
				this.interp(this.setNew.altimeter, this.setOld.altimeter, f),
				0.0F, 9144F, 0.0F, -10800F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("STREL_ALT_SHORT", this.cvt(
				this.interp(this.setNew.altimeter, this.setOld.altimeter, f),
				0.0F, 9144F, 0.0F, -1080F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("SHKALA_DIRECTOR",
				this.interp(this.setNew.azimuth, this.setOld.azimuth, f), 0.0F,
				0.0F);
		this.mesh.chunkSetAngles("STRELKA_VY", -this.floatindex(
				this.cvt(this.setNew.vspeed, -20.32F, 20.32F, 0.0F, 8F),
				variometerScale), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("STRELKA_BOOST",
				this.cvt(
						this.pictManf = (0.91F * this.pictManf)
								+ (0.09F * this.fm.EI.engines[0]
										.getManifoldPressure()), 0.7242097F,
						2.103161F, 60F, -240F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("STRELKA_FUEL1",
				this.cvt(this.fm.M.fuel, 88.38F, 350.23F, 0.0F, -306F), 0.0F,
				0.0F);
		this.mesh.chunkSetAngles(
				"STRELKA_FUEL2",
				this.cvt((float) Math.sqrt(this.fm.M.fuel), 0.0F,
						(float) Math.sqrt(88.379997253417969D), 0.0F, -317F),
				0.0F, 0.0F);
		this.mesh.chunkSetAngles("STRELKA_FUEL3",
				this.cvt(this.fm.M.fuel, 88.38F, 170.2F, 0.0F, -311F), 0.0F,
				0.0F);
		this.mesh.chunkSetAngles("STRELKA_FUEL4",
				this.cvt(this.fm.M.fuel, 88.38F, 170.2F, 0.0F, -311F), 0.0F,
				0.0F);
		this.mesh.chunkSetAngles("STRELKA_RPM", -this.floatindex(this.cvt(
				this.fm.EI.engines[0].getRPM(), 0.0F, 5000F, 0.0F, 10F),
				rpmScale), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("STRELK_TEMP_OIL", this.cvt(
				this.fm.EI.engines[0].tOilOut, 0.0F, 160F, 0.0F, -306F), 0.0F,
				0.0F);
		this.mesh.chunkSetAngles("STRELK_TEMP_RAD", -this.floatindex(this.cvt(
				this.fm.EI.engines[0].tWaterOut, 0.0F, 140F, 0.0F, 14F),
				radScale), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("STR_OIL_LB", this.cvt(
				1.0F + (0.05F * this.fm.EI.engines[0].tOilOut), 0.0F, 10F,
				0.0F, -36F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("STRELK_TURN_UP",
				-this.cvt(this.getBall(8D), -8F, 8F, 35F, -35F), 0.0F, 0.0F);
		this.w.set(this.fm.getW());
		this.fm.Or.transform(this.w);
		this.mesh
				.chunkSetAngles("STREL_TURN_DOWN",
						-this.cvt(this.w.z, -0.23562F, 0.23562F, -48F, 48F),
						0.0F, 0.0F);
		this.mesh.chunkSetAngles("STRELKA_GOR", this.fm.Or.getKren(), 0.0F,
				0.0F);
		this.resetYPRmodifier();
		Cockpit.xyz[1] = this.cvt(this.fm.Or.getTangage(), -45F, 45F, 0.022F,
				-0.022F);
		this.mesh.chunkSetLocate("STRELKA_GOS", Cockpit.xyz, Cockpit.ypr);
		this.mesh.chunkSetAngles("STRELKA_HOUR",
				-this.cvt(World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F,
				0.0F);
		this.mesh.chunkSetAngles("STRELKA_MINUTE",
				this.cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F),
				0.0F, 0.0F);
		this.mesh.chunkSetAngles("STRELKA_SECUND", -this.cvt(
				((World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F,
				360F), 0.0F, 0.0F);
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
		} else {
			waypoint.getP(this.tmpP);
			this.tmpV.sub(this.tmpP, this.fm.Loc);
			return (float) (57.295779513082323D * Math.atan2(-this.tmpV.y,
					this.tmpV.x));
		}
	}

}
