// Source File Name: CockpitCantZ.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-04-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.JGP.*;
import com.maddox.il2.ai.*;
import com.maddox.il2.engine.*;
import com.maddox.il2.fm.*;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;

public class CockpitCantZ extends CockpitPilot {
	private class Interpolater extends InterpolateRef {

		public boolean tick() {
			if (fm != null) {
				setTmp = setOld;
				setOld = setNew;
				setNew = setTmp;
				setNew.throttle1 = 0.9F
						* setOld.throttle1
						+ 0.1F
						* ((FlightModelMain) (fm)).EI.engines[0]
								.getControlThrottle();
				setNew.prop1 = 0.9F
						* setOld.prop1
						+ 0.1F
						* ((FlightModelMain) (fm)).EI.engines[0]
								.getControlProp();
				setNew.mix1 = 0.8F
						* setOld.mix1
						+ 0.2F
						* ((FlightModelMain) (fm)).EI.engines[0]
								.getControlMix();
				setNew.man1 = 0.92F
						* setOld.man1
						+ 0.08F
						* ((FlightModelMain) (fm)).EI.engines[0]
								.getManifoldPressure();
				setNew.throttle2 = 0.9F
						* setOld.throttle2
						+ 0.1F
						* ((FlightModelMain) (fm)).EI.engines[1]
								.getControlThrottle();
				setNew.prop2 = 0.9F
						* setOld.prop2
						+ 0.1F
						* ((FlightModelMain) (fm)).EI.engines[1]
								.getControlProp();
				setNew.mix2 = 0.8F
						* setOld.mix2
						+ 0.2F
						* ((FlightModelMain) (fm)).EI.engines[1]
								.getControlMix();
				setNew.man2 = 0.92F
						* setOld.man2
						+ 0.08F
						* ((FlightModelMain) (fm)).EI.engines[1]
								.getManifoldPressure();
				setNew.altimeter = fm.getAltitude();
				setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F),
						((FlightModelMain) (fm)).Or.azimut());
				setNew.vspeed = (100F * setOld.vspeed + fm.getVertSpeed()) / 101F;
				float f = waypointAzimuth();
				setNew.waypointAzimuth.setDeg(
						setOld.waypointAzimuth.getDeg(0.1F),
						(f - setOld.azimuth.getDeg(1.0F))
								+ World.Rnd().nextFloat(-10F, 10F));
				setNew.waypointDirection.setDeg(
						setOld.waypointDirection.getDeg(1.0F), f);
				setNew.inert = 0.999F
						* setOld.inert
						+ 0.001F
						* (((FlightModelMain) (fm)).EI.engines[0].getStage() != 6 ? 0.0F
								: 0.867F);
			}
			return true;
		}

		Interpolater() {
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
		AnglesFork waypointAzimuth;
		AnglesFork waypointDirection;
		float vspeed;
		float inert;

		private Variables() {
			azimuth = new AnglesFork();
			waypointAzimuth = new AnglesFork();
			waypointDirection = new AnglesFork();
		}

		Variables(Variables variables) {
			this();
		}
	}

	protected boolean doFocusEnter() {
		if (super.doFocusEnter()) {
			((CantZ506B) ((Interpolate) (super.fm)).actor).bPitUnfocused = false;
			aircraft().hierMesh().chunkVisible("Interior_D0", false);
			return true;
		} else {
			return false;
		}
	}

	protected void doFocusLeave() {
		if (isFocused()) {
			((CantZ506B) ((Interpolate) (super.fm)).actor).bPitUnfocused = true;
			aircraft().hierMesh().chunkVisible("Interior_D0", true);
			super.doFocusLeave();
		}
	}

	protected float waypointAzimuth() {
		WayPoint waypoint = ((FlightModelMain) (super.fm)).AP.way.curr();
		if (waypoint == null)
			return 0.0F;
		waypoint.getP(tmpP);
		tmpV.sub(tmpP, ((FlightModelMain) (super.fm)).Loc);
		float f;
		for (f = (float) (57.295779513082323D * Math.atan2(
				-((Tuple3d) (tmpV)).y, ((Tuple3d) (tmpV)).x)); f <= -180F; f += 180F)
			;
		for (; f > 180F; f -= 180F)
			;
		return f;
	}

	public CockpitCantZ() {
		super("3DO/Cockpit/CantZ/hier.him", "he111");
		bNeedSetUp = true;
		setOld = new Variables(null);
		setNew = new Variables(null);
		w = new Vector3f();
		pictAiler = 0.0F;
		pictElev = 0.0F;
		tmpP = new Point3d();
		tmpV = new Vector3d();
		super.cockpitNightMats = (new String[] { "GP1", "GP2", "GP_II_DM",
				"GP_III_DM", "GP3", "GP_IV_DM", "GP_IV", "GP4", "GP5", "GP6",
				"GP7", "GP8", "GP9", "compass", "instr", "Ita_Needles",
				"gauges5", "throttle", "Eqpt_II", "Trans_II", "Trans_VI_Pilot",
				"Trans_VII_Pilot" });
		setNightMats(false);
		interpPut(new Interpolater(), null, Time.current(), null);
		if (super.acoustics != null)
			super.acoustics.globFX = new ReverbFXRoom(0.45F);
	}

	public void reflectWorldToInstruments(float f) {
		if (bNeedSetUp) {
			reflectPlaneMats();
			bNeedSetUp = false;
		}
		resetYPRmodifier();
		super.mesh.chunkSetAngles("Z_Throtle1",
				-45F * interp(setNew.throttle1, setOld.throttle1, f), 0.0F,
				0.0F);
		super.mesh.chunkSetAngles("Z_Throtle2",
				-45F * interp(setNew.throttle2, setOld.throttle2, f), 0.0F,
				0.0F);
		super.mesh.chunkSetAngles("Z_Prop1",
				-45F * interp(setNew.prop1, setOld.prop1, f), 0.0F, 0.0F);
		super.mesh.chunkSetAngles("Z_Prop2",
				-45F * interp(setNew.prop2, setOld.prop2, f), 0.0F, 0.0F);
		resetYPRmodifier();
		Cockpit.xyz[1] = -0.095F
				* ((FlightModelMain) (super.fm)).CT.getRudder();
		super.mesh.chunkSetLocate("Z_RightPedal", Cockpit.xyz, Cockpit.ypr);
		Cockpit.xyz[1] = -Cockpit.xyz[1];
		super.mesh.chunkSetLocate("Z_LeftPedal", Cockpit.xyz, Cockpit.ypr);
		super.mesh.chunkSetAngles("Z_Columnbase", -8F
				* (pictElev = 0.65F * pictElev + 0.35F
						* ((FlightModelMain) (super.fm)).CT.ElevatorControl),
				0.0F, 0.0F);
		super.mesh.chunkSetAngles("Z_Column", -45F
				* (pictAiler = 0.65F * pictAiler + 0.35F
						* ((FlightModelMain) (super.fm)).CT.AileronControl),
				0.0F, 0.0F);
		resetYPRmodifier();
		super.mesh.chunkSetAngles(
				"Z_Altimeter1",
				cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F,
						20000F, 0.0F, -720F), 0.0F, 0.0F);
		super.mesh.chunkSetAngles(
				"Z_Altimeter2",
				cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F,
						20000F, 0.0F, -7200F), 0.0F, 0.0F);
		super.mesh.chunkSetAngles(
				"Z_Altimeter3",
				cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F,
						20000F, 0.0F, -720F), 0.0F, 0.0F);
		super.mesh.chunkSetAngles(
				"Z_Altimeter4",
				cvt(interp(setNew.altimeter, setOld.altimeter, f), 0.0F,
						20000F, 0.0F, -7200F), 0.0F, 0.0F);
		super.mesh
				.chunkSetAngles(
						"Z_Speedometer1",
						-floatindex(
								cvt(Pitot
										.Indicator(
												(float) ((Tuple3d) (((FlightModelMain) (super.fm)).Loc)).z,
												super.fm.getSpeedKMH()), 0.0F,
										800F, 0.0F, 16F), speedometerScale),
						0.0F, 0.0F);
		super.mesh
				.chunkSetAngles(
						"Z_Speedometer2",
						-floatindex(
								cvt(Pitot
										.Indicator(
												(float) ((Tuple3d) (((FlightModelMain) (super.fm)).Loc)).z,
												super.fm.getSpeedKMH()), 0.0F,
										800F, 0.0F, 16F), speedometerScale),
						0.0F, 0.0F);
		resetYPRmodifier();
		Cockpit.xyz[1] = cvt(((FlightModelMain) (super.fm)).Or.getTangage(),
				-45F, 45F, 0.018F, -0.018F);
		super.mesh.chunkSetLocate("Z_TurnBank1", Cockpit.xyz, Cockpit.ypr);
		super.mesh.chunkSetAngles("Z_TurnBank1Q",
				-((FlightModelMain) (super.fm)).Or.getKren(), 0.0F, 0.0F);
		w.set(super.fm.getW());
		((FlightModelMain) (super.fm)).Or.transform(w);
		super.mesh.chunkSetAngles("Z_TurnBank2",
				cvt(((Tuple3f) (w)).z, -0.23562F, 0.23562F, -27F, 27F), 0.0F,
				0.0F);
		super.mesh.chunkSetAngles("Z_TurnBank3",
				cvt(getBall(7D), -7F, 7F, 10F, -10F), 0.0F, 0.0F);
		super.mesh.chunkSetAngles("Z_Climb1",
				cvt(setNew.vspeed, -30F, 30F, 180F, -180F), 0.0F, 0.0F);
		super.mesh.chunkSetAngles("Z_Compass2", setNew.azimuth.getDeg(f), 0.0F,
				0.0F);
		super.mesh.chunkSetAngles(
				"Z_RPM1",
				cvt(((FlightModelMain) (super.fm)).EI.engines[0].getRPM(),
						0.0F, 3000F, 15F, 345F), 0.0F, 0.0F);
		super.mesh.chunkSetAngles(
				"Z_RPM2",
				cvt(((FlightModelMain) (super.fm)).EI.engines[1].getRPM(),
						0.0F, 3000F, 15F, 345F), 0.0F, 0.0F);
		float f1 = 0.0F;
		if (((FlightModelMain) (super.fm)).M.fuel > 1.0F)
			f1 = cvt(((FlightModelMain) (super.fm)).EI.engines[0].getRPM(),
					0.0F, 570F, 0.0F, 0.26F);
		super.mesh.chunkSetAngles("Z_FuelPres1",
				cvt(f1, 0.0F, 1.0F, 0.0F, -270F), 0.0F, 0.0F);
		f1 = 0.0F;
		if (((FlightModelMain) (super.fm)).M.fuel > 1.0F)
			f1 = cvt(((FlightModelMain) (super.fm)).EI.engines[1].getRPM(),
					0.0F, 570F, 0.0F, 0.26F);
		super.mesh.chunkSetAngles("Z_FuelPres2",
				cvt(f1, 0.0F, 1.0F, 0.0F, -270F), 0.0F, 0.0F);
		super.mesh.chunkSetAngles(
				"Z_Temp1",
				cvt(((FlightModelMain) (super.fm)).EI.engines[0].tWaterOut,
						0.0F, 160F, 0.0F, -75F), 0.0F, 0.0F);
		super.mesh.chunkSetAngles(
				"Z_Temp2",
				cvt(((FlightModelMain) (super.fm)).EI.engines[1].tWaterOut,
						0.0F, 160F, 0.0F, -75F), 0.0F, 0.0F);
		super.mesh
				.chunkSetAngles("Z_Pres1",
						cvt(setNew.man1, 0.399966F, 1.599864F, 0.0F, -300F),
						0.0F, 0.0F);
		super.mesh
				.chunkSetAngles("Z_Pres2",
						cvt(setNew.man2, 0.399966F, 1.599864F, 0.0F, -300F),
						0.0F, 0.0F);
		super.mesh.chunkSetAngles(
				"Z_Oil1",
				cvt(((FlightModelMain) (super.fm)).EI.engines[0].tOilIn, 0.0F,
						160F, 0.0F, -75F), 0.0F, 0.0F);
		super.mesh.chunkSetAngles(
				"Z_Oil2",
				cvt(((FlightModelMain) (super.fm)).EI.engines[1].tOilIn, 0.0F,
						160F, 0.0F, -75F), 0.0F, 0.0F);
		super.mesh.chunkSetAngles(
				"Z_FlapPos",
				cvt(((FlightModelMain) (super.fm)).CT.getFlap(), 0.0F, 1.0F,
						0.0F, -180F), 0.0F, 0.0F);
		super.mesh.chunkSetAngles("Z_Compass4", setNew.azimuth.getDeg(f), 0.0F,
				0.0F);
		super.mesh.chunkVisible("XRGearUp",
				((FlightModelMain) (super.fm)).CT.getGear() < 0.01F
						|| !((FlightModelMain) (super.fm)).Gears.rgear);
		super.mesh.chunkVisible("XLGearUp",
				((FlightModelMain) (super.fm)).CT.getGear() < 0.01F
						|| !((FlightModelMain) (super.fm)).Gears.lgear);
		super.mesh.chunkVisible("XRGearDn",
				((FlightModelMain) (super.fm)).CT.getGear() > 0.99F
						&& ((FlightModelMain) (super.fm)).Gears.rgear);
		super.mesh.chunkVisible("XLGearDn",
				((FlightModelMain) (super.fm)).CT.getGear() > 0.99F
						&& ((FlightModelMain) (super.fm)).Gears.lgear);
		super.mesh.chunkSetAngles(
				"Zfuel",
				0.0F,
				cvt(((FlightModelMain) (super.fm)).M.fuel, 0.0F, 3117F, 0.0F,
						245F), 0.0F);
	}

	public void toggleLight() {
		super.cockpitLightControl = !super.cockpitLightControl;
		if (super.cockpitLightControl)
			setNightMats(true);
		else
			setNightMats(false);
	}

	protected void reflectPlaneMats() {
		HierMesh hiermesh = aircraft().hierMesh();
		com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh
				.materialFind("Gloss1D0o"));
		super.mesh.materialReplace("Gloss1D0o", mat);
	}

	private boolean bNeedSetUp;
	private Variables setOld;
	private Variables setNew;
	private Variables setTmp;
	public Vector3f w;
	private float pictAiler;
	private float pictElev;
	private static final float speedometerScale[] = { 0.0F, 0.0F, 10.5F, 42.5F,
			85F, 125F, 165.5F, 181F, 198F, 214.5F, 231F, 249F, 266.5F, 287.5F,
			308F, 326.5F, 346F };
	private Point3d tmpP;
	private Vector3d tmpV;

}
