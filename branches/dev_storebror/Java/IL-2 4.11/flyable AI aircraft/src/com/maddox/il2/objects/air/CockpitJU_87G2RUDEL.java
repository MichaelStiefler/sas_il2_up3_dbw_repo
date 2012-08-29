// Source File Name: CockpitJU_87G2RUDEL.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;
import com.maddox.sound.SoundFX;

public class CockpitJU_87G2RUDEL extends CockpitPilot {
	class Interpolater extends InterpolateRef {

		Interpolater() {
		}

		public boolean tick() {
			CockpitJU_87G2RUDEL.this.fm = World.getPlayerFM();
			if (CockpitJU_87G2RUDEL.this.fm != null) {
				if (CockpitJU_87G2RUDEL.this.bNeedSetUp) {
					CockpitJU_87G2RUDEL.this.reflectPlaneMats();
					CockpitJU_87G2RUDEL.this.bNeedSetUp = false;
				}
				CockpitJU_87G2RUDEL.this.setTmp = CockpitJU_87G2RUDEL.this.setOld;
				CockpitJU_87G2RUDEL.this.setOld = CockpitJU_87G2RUDEL.this.setNew;
				CockpitJU_87G2RUDEL.this.setNew = CockpitJU_87G2RUDEL.this.setTmp;
				CockpitJU_87G2RUDEL.this.setNew.altimeter = CockpitJU_87G2RUDEL.this.fm
						.getAltitude();
				if (CockpitJU_87G2RUDEL.this.cockpitDimControl) {
					if (CockpitJU_87G2RUDEL.this.setNew.dimPosition > 0.0F) {
						CockpitJU_87G2RUDEL.this.setNew.dimPosition = CockpitJU_87G2RUDEL.this.setOld.dimPosition - 0.05F;
					}
				} else if (CockpitJU_87G2RUDEL.this.setNew.dimPosition < 1.0F) {
					CockpitJU_87G2RUDEL.this.setNew.dimPosition = CockpitJU_87G2RUDEL.this.setOld.dimPosition + 0.05F;
				}
				CockpitJU_87G2RUDEL.this.setNew.throttle = ((10F * CockpitJU_87G2RUDEL.this.setOld.throttle) + CockpitJU_87G2RUDEL.this.fm.CT.PowerControl) / 11F;
				CockpitJU_87G2RUDEL.this.setNew.azimuth = CockpitJU_87G2RUDEL.this.fm.Or
						.getYaw();
				if ((CockpitJU_87G2RUDEL.this.setOld.azimuth > 270F)
						&& (CockpitJU_87G2RUDEL.this.setNew.azimuth < 90F)) {
					CockpitJU_87G2RUDEL.this.setOld.azimuth -= 360F;
				}
				if ((CockpitJU_87G2RUDEL.this.setOld.azimuth < 90F)
						&& (CockpitJU_87G2RUDEL.this.setNew.azimuth > 270F)) {
					CockpitJU_87G2RUDEL.this.setOld.azimuth += 360F;
				}
				CockpitJU_87G2RUDEL.this.setNew.waypointAzimuth = ((10F * CockpitJU_87G2RUDEL.this.setOld.waypointAzimuth)
						+ (CockpitJU_87G2RUDEL.this.waypointAzimuth() - CockpitJU_87G2RUDEL.this.setOld.azimuth) + World
						.Rnd().nextFloat(-30F, 30F)) / 11F;
				CockpitJU_87G2RUDEL.this.setNew.vspeed = ((499F * CockpitJU_87G2RUDEL.this.setOld.vspeed) + CockpitJU_87G2RUDEL.this.fm
						.getVertSpeed()) / 500F;
				if (CockpitJU_87G2RUDEL.this.buzzerFX != null) {
					if ((CockpitJU_87G2RUDEL.this.fm.Loc.z < 750D)
							&& (((JU_87) CockpitJU_87G2RUDEL.this.fm.actor).diveMechStage == 1)) {
						CockpitJU_87G2RUDEL.this.buzzerFX.play();
					} else if (CockpitJU_87G2RUDEL.this.buzzerFX.isPlaying()) {
						CockpitJU_87G2RUDEL.this.buzzerFX.stop();
					}
				}
			}
			return true;
		}
	}

	private class Variables {

		float altimeter;
		float throttle;
		float dimPosition;
		float azimuth;
		float waypointAzimuth;
		float vspeed;

		private Variables() {
		}

	}

	protected SoundFX buzzerFX;

	private Gun gun[] = { null, null };

	private Variables setOld;

	private Variables setNew;

	private Variables setTmp;

	private LightPointActor light1;

	private LightPointActor light2;

	private float pictAiler;
	private float pictElev;
	private boolean bNeedSetUp;
	private boolean bG1;
	private static final float speedometerScale[] = { 0.0F, -12.33333F, 18.5F,
			37F, 62.5F, 90F, 110.5F, 134F, 158.5F, 186F, 212.5F, 238.5F, 265F,
			289.5F, 315F, 339.5F, 346F, 346F };
	private static final float rpmScale[] = { 0.0F, 11.25F, 54F, 111F, 171.5F,
			229.5F, 282.5F, 334F, 342.5F, 342.5F };
	private static final float fuelScale[] = { 0.0F, 11.5F, 24.5F, 46.5F, 67F,
			88F };
	private Point3d tmpP;
	private Vector3d tmpV;

	public CockpitJU_87G2RUDEL() {
		super("3DO/Cockpit/Ju-87D-3/hier.him", "bf109");
		this.setOld = new Variables();
		this.setNew = new Variables();
		this.pictAiler = 0.0F;
		this.pictElev = 0.0F;
		this.bNeedSetUp = true;
		this.bG1 = false;
		this.tmpP = new Point3d();
		this.tmpV = new Vector3d();
		this.setNew.dimPosition = 1.0F;
		HookNamed hooknamed = new HookNamed(this.mesh, "LAMPHOOK1");
		Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
		hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F),
				loc);
		this.light1 = new LightPointActor(new LightPoint(), loc.getPoint());
		this.light1.light.setColor(126F, 232F, 245F);
		this.light1.light.setEmit(0.0F, 0.0F);
		this.pos.base().draw.lightMap().put("LAMPHOOK1", this.light1);
		hooknamed = new HookNamed(this.mesh, "LAMPHOOK2");
		loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
		hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F),
				loc);
		this.light2 = new LightPointActor(new LightPoint(), loc.getPoint());
		this.light2.light.setColor(126F, 232F, 245F);
		this.light2.light.setEmit(0.0F, 0.0F);
		this.pos.base().draw.lightMap().put("LAMPHOOK2", this.light2);
		this.cockpitNightMats = (new String[] { "87DClocks1", "87DClocks2",
				"87DClocks3", "87DClocks4", "87DClocks5", "87DPlanks2" });
		this.setNightMats(false);
		this.interpPut(new Interpolater(), null, Time.current(), null);
		this.buzzerFX = this.aircraft().newSound("models.buzzthru", false);
	}

	public void reflectCockpitState() {
		if (((this.fm.AS.astateCockpitState & 4) != 0)
				|| ((this.fm.AS.astateCockpitState & 8) != 0)) {
			this.mesh.chunkVisible("Z_Holes1_D1", true);
			this.mesh.chunkVisible("Z_Holes2_D1", true);
		}
		if (((this.fm.AS.astateCockpitState & 0x10) != 0)
				|| ((this.fm.AS.astateCockpitState & 0x20) != 0)) {
			this.mesh.chunkVisible("Z_Holes1_D1", true);
			this.mesh.chunkVisible("Z_Holes2_D1", true);
		}
		if (((this.fm.AS.astateCockpitState & 1) != 0)
				|| ((this.fm.AS.astateCockpitState & 2) != 0)) {
			this.mesh.chunkVisible("Revi_D0", false);
			this.mesh.chunkVisible("Z_ReviTint", false);
			this.mesh.chunkVisible("Z_ReviTinter", false);
			this.mesh.chunkVisible("Z_Z_RETICLE", false);
			this.mesh.chunkVisible("Z_Z_MASK", false);
			this.mesh.chunkVisible("Revi_D1", true);
			if (this.bG1) {
				this.mesh.chunkVisible("Z_Holes3G_D1", true);
			} else {
				this.mesh.chunkVisible("Z_Holes3D_D1", true);
			}
		}
		if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
			;
		}
		if ((this.fm.AS.astateCockpitState & 0x80) != 0) {
			this.mesh.chunkVisible("Z_OilSplats_D1", true);
		}
	}

	protected void reflectPlaneMats() {
		if (Actor.isValid(this.fm.actor)
				&& (this.fm.actor instanceof JU_87G2RUDEL)) {
			this.mesh.chunkVisible("ARMOR", true);
			this.bG1 = true;
		}
	}

	public void reflectWorldToInstruments(float f) {
		if (this.fm != null) {
			if (this.bNeedSetUp) {
				this.reflectPlaneMats();
				this.bNeedSetUp = false;
			}
			if ((this.gun[0] == null) && !this.bNeedSetUp) {
				if (this.bG1) {
					this.gun[0] = ((Aircraft) this.fm.actor)
							.getGunByHookName("_CANNON01");
					this.gun[1] = ((Aircraft) this.fm.actor)
							.getGunByHookName("_CANNON02");
				} else {
					this.gun[0] = ((Aircraft) this.fm.actor)
							.getGunByHookName("_MGUN01");
					this.gun[1] = ((Aircraft) this.fm.actor)
							.getGunByHookName("_MGUN02");
				}
			}
			this.mesh.chunkSetAngles("zAlt1", this.cvt(this.interp(
					this.setNew.altimeter, this.setOld.altimeter, f), 0.0F,
					10000F, 0.0F, 3600F), 0.0F, 0.0F);
			this.mesh.chunkSetAngles("zAlt2", this.cvt(this.interp(
					this.setNew.altimeter, this.setOld.altimeter, f), 0.0F,
					10000F, 0.0F, -360F), 0.0F, 0.0F);
			this.mesh
					.chunkSetAngles(
							"zAltCtr1",
							(((JU_87) this.aircraft()).fDiveRecoveryAlt * 360F) / 6000F,
							0.0F, 0.0F);
			this.mesh.chunkSetAngles("zAltCtr2", this.cvt(this.interp(
					this.setNew.altimeter, this.setOld.altimeter, f), 0.0F,
					6000F, 0.0F, 360F), 0.0F, 0.0F);
			this.mesh.chunkSetAngles("Z_ReviTinter", this.cvt(this.interp(
					this.setNew.dimPosition, this.setOld.dimPosition, f), 0.0F,
					1.0F, 0.0F, -30F), 0.0F, 0.0F);
			this.mesh.chunkSetAngles("Z_ReviTint", this.cvt(this.interp(
					this.setNew.dimPosition, this.setOld.dimPosition, f), 0.0F,
					1.0F, 0.0F, 40F), 0.0F, 0.0F);
			this.mesh.chunkSetAngles("zBoost1", this.cvt(
					this.fm.EI.engines[0].getManifoldPressure(), 0.6F, 1.8F,
					0.0F, 332F), 0.0F, 0.0F);
			this.mesh.chunkSetAngles("zSpeed", this.floatindex(this.cvt(
					Pitot.Indicator((float) this.fm.Loc.z,
							this.fm.getSpeedKMH()), 0.0F, 800F, 0.0F, 16F),
					speedometerScale), 0.0F, 0.0F);
			this.mesh.chunkSetAngles("zRPM1", this.floatindex(this.cvt(
					this.fm.EI.engines[0].getRPM(), 0.0F, 4000F, 0.0F, 8F),
					rpmScale), 0.0F, 0.0F);
			this.mesh.chunkSetAngles("zFuel1", this.floatindex(
					this.cvt(this.fm.M.fuel / 0.72F, 0.0F, 250F, 0.0F, 5F),
					fuelScale), 0.0F, 0.0F);
			this.mesh.chunkSetAngles("zOilTemp1", this.cvt(
					this.fm.EI.engines[0].tOilOut, 0.0F, 120F, 0.0F, 120F),
					0.0F, 0.0F);
			this.mesh.chunkSetAngles("zFuelPrs1", this
					.cvt(this.fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 3F, 0.0F,
							135F), 0.0F, 0.0F);
			this.mesh.chunkSetAngles("zOilPrs1", this.cvt(
					1.0F + (0.05F * this.fm.EI.engines[0].tOilOut), 0.0F, 15F,
					0.0F, 135F), 0.0F, 0.0F);
			this.mesh.chunkSetAngles(
					"zTurnBank",
					this.cvt(
							(this.setNew.azimuth - this.setOld.azimuth)
									/ Time.tickLenFs(), -3F, 3F, 30F, -30F),
					0.0F, 0.0F);
			this.mesh.chunkSetAngles("zBall",
					this.cvt(this.getBall(6D), -6F, 6F, -10F, 10F), 0.0F, 0.0F);
			this.mesh.chunkSetAngles("zCompass", 0.0F,
					-this.interp(this.setNew.azimuth, this.setOld.azimuth, f),
					0.0F);
			this.mesh.chunkSetAngles("zRepeater", -this
					.interp(this.setNew.waypointAzimuth,
							this.setOld.waypointAzimuth, f), 0.0F, 0.0F);
			this.mesh.chunkSetAngles("zCompassOil1", this.fm.Or.getTangage(),
					0.0F, 0.0F);
			this.mesh.chunkSetAngles("zCompassOil3", this.fm.Or.getKren(),
					0.0F, 0.0F);
			this.mesh.chunkSetAngles("zCompassOil2",
					-this.interp(this.setNew.azimuth, this.setOld.azimuth, f),
					0.0F, 0.0F);
			this.mesh.chunkSetAngles("zVSI",
					this.cvt(this.setNew.vspeed, -15F, 15F, -135F, 135F), 0.0F,
					0.0F);
			this.mesh.chunkSetAngles("zwatertemp", this.cvt(
					this.fm.EI.engines[0].tOilOut, 0.0F, 120F, 0.0F, 100F),
					0.0F, 0.0F);
			if (this.bG1) {
				if (this.gun[0] != null) {
					this.mesh.chunkSetAngles("Z_AmmoCounter1", this.cvt(
							this.gun[0].countBullets(), 0.0F, 50F, 15F, 0.0F),
							0.0F, 0.0F);
				}
				if (this.gun[1] != null) {
					this.mesh.chunkSetAngles("Z_AmmoCounter2", this.cvt(
							this.gun[1].countBullets(), 0.0F, 50F, 15F, 0.0F),
							0.0F, 0.0F);
				}
			} else {
				if (this.gun[0] != null) {
					this.mesh.chunkSetAngles("Z_AmmoCounter1", this.cvt(
							this.gun[0].countBullets(), 0.0F, 500F, 15F, 0.0F),
							0.0F, 0.0F);
				}
				if (this.gun[1] != null) {
					this.mesh.chunkSetAngles("Z_AmmoCounter2", this.cvt(
							this.gun[1].countBullets(), 0.0F, 500F, 15F, 0.0F),
							0.0F, 0.0F);
				}
			}
			this.mesh.chunkSetAngles("zHour",
					this.cvt(World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F),
					0.0F, 0.0F);
			this.mesh.chunkSetAngles("zMinute", this.cvt(
					World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F,
					0.0F);
			this.mesh.chunkSetAngles("zColumn1",
					(this.pictAiler = (0.85F * this.pictAiler)
							+ (0.15F * this.fm.CT.AileronControl)) * 15F, 0.0F,
					(this.pictElev = (0.85F * this.pictElev)
							+ (0.15F * this.fm.CT.ElevatorControl)) * 10F);
			this.mesh.chunkSetAngles("zPedalL", -this.fm.CT.getRudder() * 10F,
					0.0F, 0.0F);
			this.mesh.chunkSetAngles("zPedalR", this.fm.CT.getRudder() * 10F,
					0.0F, 0.0F);
			this.mesh
					.chunkSetAngles("zThrottle1",
							this.interp(this.setNew.throttle,
									this.setOld.throttle, f) * 80F, 0.0F, 0.0F);
			this.mesh.chunkSetAngles(
					"zPitch1",
					(this.fm.CT.getStepControl() < 0.0F ? this.interp(
							this.setNew.throttle, this.setOld.throttle, f)
							: this.fm.CT.getStepControl()) * 45F, 0.0F, 0.0F);
			this.mesh.chunkSetAngles("zFlaps1", 55F * this.fm.CT.FlapsControl,
					0.0F, 0.0F);
			this.mesh.chunkSetAngles("zPipka1",
					60F * this.fm.CT.AirBrakeControl, 0.0F, 0.0F);
			this.mesh.chunkSetAngles("zBrake1",
					46.5F * this.fm.CT.AirBrakeControl, 0.0F, 0.0F);
			this.resetYPRmodifier();
			if (this.fm.EI.engines[0].getControlCompressor() > 0) {
				Cockpit.xyz[0] = 0.155F;
				Cockpit.ypr[2] = 22F;
			}
			this.mesh.chunkSetLocate("zBoostCrank1", Cockpit.xyz, Cockpit.ypr);
		}
	}

	public void toggleDim() {
		this.cockpitDimControl = !this.cockpitDimControl;
	}

	public void toggleLight() {
		this.cockpitLightControl = !this.cockpitLightControl;
		if (this.cockpitLightControl) {
			this.light1.light.setEmit(0.005F, 0.5F);
			this.light2.light.setEmit(0.005F, 0.5F);
			this.setNightMats(true);
		} else {
			this.light1.light.setEmit(0.0F, 0.0F);
			this.light2.light.setEmit(0.0F, 0.0F);
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
			return (float) (57.295779513082323D * Math.atan2(this.tmpV.y,
					this.tmpV.x));
		}
	}

}
