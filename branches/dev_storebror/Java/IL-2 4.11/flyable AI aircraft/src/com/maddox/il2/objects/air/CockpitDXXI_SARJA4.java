// Source File Name: CockpitDXXI_SARJA4.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;

public class CockpitDXXI_SARJA4 extends CockpitPilot {
	class Interpolater extends InterpolateRef {

		Interpolater() {
		}

		public boolean tick() {
			if (CockpitDXXI_SARJA4.this.bNeedSetUp) {
				CockpitDXXI_SARJA4.this.reflectPlaneMats();
				CockpitDXXI_SARJA4.this.bNeedSetUp = false;
			}
			CockpitDXXI_SARJA4.this.setTmp = CockpitDXXI_SARJA4.this.setOld;
			CockpitDXXI_SARJA4.this.setOld = CockpitDXXI_SARJA4.this.setNew;
			CockpitDXXI_SARJA4.this.setNew = CockpitDXXI_SARJA4.this.setTmp;
			if (((CockpitDXXI_SARJA4.this.fm.AS.astateCockpitState & 2) != 0)
					&& (CockpitDXXI_SARJA4.this.setNew.stbyPosition < 1.0F)) {
				CockpitDXXI_SARJA4.this.setNew.stbyPosition = CockpitDXXI_SARJA4.this.setOld.stbyPosition + 0.0125F;
				CockpitDXXI_SARJA4.this.setOld.stbyPosition = CockpitDXXI_SARJA4.this.setNew.stbyPosition;
			}
			CockpitDXXI_SARJA4.this.setNew.altimeter = CockpitDXXI_SARJA4.this.fm
					.getAltitude();
			if ((Math.abs(CockpitDXXI_SARJA4.this.fm.Or.getKren()) < 30F)
					&& (Math.abs(CockpitDXXI_SARJA4.this.fm.Or.tangage()) < 30F)) {
				CockpitDXXI_SARJA4.this.setNew.azimuth.setDeg(
						CockpitDXXI_SARJA4.this.setOld.azimuth.getDeg(1.0F),
						CockpitDXXI_SARJA4.this.fm.Or.azimut());
			}
			CockpitDXXI_SARJA4.this.setNew.throttle = ((10F * CockpitDXXI_SARJA4.this.setOld.throttle) + CockpitDXXI_SARJA4.this.fm.EI.engines[0]
					.getControlThrottle()) / 11F;
			CockpitDXXI_SARJA4.this.setNew.mix = ((10F * CockpitDXXI_SARJA4.this.setOld.mix) + CockpitDXXI_SARJA4.this.fm.EI.engines[0]
					.getControlMix()) / 11F;
			CockpitDXXI_SARJA4.this.setNew.prop = CockpitDXXI_SARJA4.this.setOld.prop;
			if (CockpitDXXI_SARJA4.this.setNew.prop < (CockpitDXXI_SARJA4.this.fm.EI.engines[0]
					.getControlProp() - 0.01F)) {
				CockpitDXXI_SARJA4.this.setNew.prop += 0.0025F;
			}
			if (CockpitDXXI_SARJA4.this.setNew.prop > (CockpitDXXI_SARJA4.this.fm.EI.engines[0]
					.getControlProp() + 0.01F)) {
				CockpitDXXI_SARJA4.this.setNew.prop -= 0.0025F;
			}
			CockpitDXXI_SARJA4.this.w.set(CockpitDXXI_SARJA4.this.fm.getW());
			CockpitDXXI_SARJA4.this.fm.Or.transform(CockpitDXXI_SARJA4.this.w);
			CockpitDXXI_SARJA4.this.setNew.turn = ((12F * CockpitDXXI_SARJA4.this.setOld.turn) + CockpitDXXI_SARJA4.this.w.z) / 13F;
			CockpitDXXI_SARJA4.this.setNew.vspeed = ((299F * CockpitDXXI_SARJA4.this.setOld.vspeed) + CockpitDXXI_SARJA4.this.fm
					.getVertSpeed()) / 300F;
			CockpitDXXI_SARJA4.this.pictSupc = (0.8F * CockpitDXXI_SARJA4.this.pictSupc)
					+ (0.2F * CockpitDXXI_SARJA4.this.fm.EI.engines[0]
							.getControlCompressor());
			if (CockpitDXXI_SARJA4.this.cockpitDimControl) {
				if (CockpitDXXI_SARJA4.this.setNew.dimPos < 1.0F) {
					CockpitDXXI_SARJA4.this.setNew.dimPos = CockpitDXXI_SARJA4.this.setOld.dimPos + 0.03F;
				}
			} else if (CockpitDXXI_SARJA4.this.setNew.dimPos > 0.0F) {
				CockpitDXXI_SARJA4.this.setNew.dimPos = CockpitDXXI_SARJA4.this.setOld.dimPos - 0.03F;
			}
			if (((CockpitDXXI_SARJA4.this.flaps > (CockpitDXXI_SARJA4.this.fm.CT.FlapsControl - 0.050000000000000003D)) && (CockpitDXXI_SARJA4.this.flaps < (CockpitDXXI_SARJA4.this.fm.CT.FlapsControl + 0.050000000000000003D)))
					|| CockpitDXXI_SARJA4.this.fm.CT.bHasFlapsControlRed) {
				CockpitDXXI_SARJA4.this.flapsDirection = 0;
			} else if (CockpitDXXI_SARJA4.this.flaps < CockpitDXXI_SARJA4.this.fm.CT.FlapsControl) {
				CockpitDXXI_SARJA4.this.flaps = CockpitDXXI_SARJA4.this.flaps + 0.00095F;
				CockpitDXXI_SARJA4.this.flapsPump = CockpitDXXI_SARJA4.this.flapsPump
						+ CockpitDXXI_SARJA4.this.flapsPumpIncrement;
				CockpitDXXI_SARJA4.this.flapsDirection = 1;
				if ((CockpitDXXI_SARJA4.this.flapsPump < 0.0F)
						|| (CockpitDXXI_SARJA4.this.flapsPump > 1.0F)) {
					CockpitDXXI_SARJA4.this.flapsPumpIncrement = -CockpitDXXI_SARJA4.this.flapsPumpIncrement;
				}
			} else if (CockpitDXXI_SARJA4.this.flaps > CockpitDXXI_SARJA4.this.fm.CT.FlapsControl) {
				CockpitDXXI_SARJA4.this.flaps = CockpitDXXI_SARJA4.this.flaps - 0.005F;
				CockpitDXXI_SARJA4.this.flapsPump = CockpitDXXI_SARJA4.this.flapsPump
						+ CockpitDXXI_SARJA4.this.flapsPumpIncrement;
				CockpitDXXI_SARJA4.this.flapsDirection = -1;
				if ((CockpitDXXI_SARJA4.this.flapsPump < 0.0F)
						|| (CockpitDXXI_SARJA4.this.flapsPump > 1.0F)) {
					CockpitDXXI_SARJA4.this.flapsPumpIncrement = -CockpitDXXI_SARJA4.this.flapsPumpIncrement;
				}
			}
			if (!CockpitDXXI_SARJA4.this.fm.Gears.bTailwheelLocked
					&& (CockpitDXXI_SARJA4.this.tailWheelLock < 1.0F)) {
				CockpitDXXI_SARJA4.this.tailWheelLock = CockpitDXXI_SARJA4.this.tailWheelLock + 0.05F;
			} else if (CockpitDXXI_SARJA4.this.fm.Gears.bTailwheelLocked
					&& (CockpitDXXI_SARJA4.this.tailWheelLock > 0.0F)) {
				CockpitDXXI_SARJA4.this.tailWheelLock = CockpitDXXI_SARJA4.this.tailWheelLock - 0.05F;
			}
			return true;
		}
	}

	private class Variables {

		float altimeter;
		AnglesFork azimuth;
		float throttle;
		float mix;
		float prop;
		float turn;
		float vspeed;
		float stbyPosition;
		float dimPos;

		private Variables() {
			this.azimuth = new AnglesFork();
		}

	}

	private Variables setOld;

	private Variables setNew;

	private Variables setTmp;

	private Vector3f w;

	private boolean bNeedSetUp;

	private float pictAiler;

	private float pictElev;

	private float pictSupc;

	private float flaps;

	private float pictManifold;

	private boolean bEntered;

	private float saveFov;

	private boolean hasRevi;

	private float tailWheelLock;

	private int flapsDirection;

	private float flapsPump;

	private float flapsPumpIncrement;
	private LightPointActor light1;
	private LightPointActor light2;
	private LightPointActor light3;
	private LightPointActor light4;
	private LightPointActor light5;

	public CockpitDXXI_SARJA4() {
		super("3DO/Cockpit/DXXI_SARJA3_EARLY/hier.him", "bf109");
		this.setOld = new Variables();
		this.setNew = new Variables();
		this.w = new Vector3f();
		this.bNeedSetUp = true;
		this.pictAiler = 0.0F;
		this.pictElev = 0.0F;
		this.pictSupc = 0.0F;
		this.flaps = 0.0F;
		this.bEntered = false;
		this.hasRevi = true;
		this.tailWheelLock = 1.0F;
		this.flapsDirection = 0;
		this.flapsPump = 0.0F;
		this.flapsPumpIncrement = 0.1F;
		this.setRevi();
		HookNamed hooknamed = new HookNamed(this.mesh, "LAMPHOOK01");
		Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
		hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F),
				loc);
		this.light1 = new LightPointActor(new LightPoint(), loc.getPoint());
		this.light1.light.setColor(126F, 232F, 245F);
		this.light1.light.setEmit(0.0F, 0.0F);
		this.pos.base().draw.lightMap().put("LAMPHOOK01", this.light1);
		hooknamed = new HookNamed(this.mesh, "LAMPHOOK02");
		loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
		hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F),
				loc);
		this.light2 = new LightPointActor(new LightPoint(), loc.getPoint());
		this.light2.light.setColor(126F, 232F, 245F);
		this.light2.light.setEmit(0.0F, 0.0F);
		this.pos.base().draw.lightMap().put("LAMPHOOK02", this.light2);
		hooknamed = new HookNamed(this.mesh, "LAMPHOOK03");
		loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
		hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F),
				loc);
		this.light3 = new LightPointActor(new LightPoint(), loc.getPoint());
		this.light3.light.setColor(126F, 232F, 245F);
		this.light3.light.setEmit(0.0F, 0.0F);
		this.pos.base().draw.lightMap().put("LAMPHOOK03", this.light3);
		hooknamed = new HookNamed(this.mesh, "LAMPHOOK04");
		loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
		hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F),
				loc);
		this.light4 = new LightPointActor(new LightPoint(), loc.getPoint());
		this.light4.light.setColor(126F, 232F, 245F);
		this.light4.light.setEmit(0.0F, 0.0F);
		this.pos.base().draw.lightMap().put("LAMPHOOK04", this.light4);
		hooknamed = new HookNamed(this.mesh, "LAMPHOOK05");
		loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
		hooknamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F),
				loc);
		this.light5 = new LightPointActor(new LightPoint(), loc.getPoint());
		this.light5.light.setColor(126F, 232F, 245F);
		this.light5.light.setEmit(0.0F, 0.0F);
		this.pos.base().draw.lightMap().put("LAMPHOOK05", this.light5);
		this.cockpitNightMats = (new String[] { "gauge_speed", "gauge_alt",
				"gauge_fuel", "gauges_various_1", "gauges_various_2",
				"LABELS1", "gauges_various_3", "gauges_various4",
				"gauges_various_3_dam", "gauge_alt_dam", "gauges_various_2_dam" });
		this.setNightMats(false);
		this.interpPut(new Interpolater(), null, Time.current(), null);
		if (this.acoustics != null) {
			this.acoustics.globFX = new ReverbFXRoom(0.45F);
		}
	}

	public void destroy() {
		this.leave();
		super.destroy();
	}

	protected boolean doFocusEnter() {
		HierMesh hiermesh = this.aircraft().hierMesh();
		hiermesh.chunkVisible("tail1_internal_d0", false);
		if (super.doFocusEnter()) {
			HookPilot hookpilot = HookPilot.current;
			hookpilot.doAim(false);
			return true;
		} else {
			return false;
		}
	}

	protected void doFocusLeave() {
		HierMesh hiermesh = this.aircraft().hierMesh();
		hiermesh.chunkVisible("tail1_internal_d0", true);
		if (this.isFocused()) {
			this.leave();
			super.doFocusLeave();
		}
	}

	public void doToggleAim(boolean flag) {
		if (this.isFocused() && (this.isToggleAim() != flag)) {
			if (flag) {
				this.enter();
			} else {
				this.leave();
			}
		}
	}

	public void doToggleUp(boolean flag) {
		super.doToggleUp(flag);
		System.out.println("TOGGLE UP");
	}

	private void enter() {
		this.saveFov = Main3D.FOVX;
		CmdEnv.top().exec("fov 31");
		Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
		HookPilot hookpilot = HookPilot.current;
		if (hookpilot.isPadlock()) {
			hookpilot.stopPadlock();
		}
		hookpilot.doAim(true);
		hookpilot.setSimpleUse(true);
		hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
		HotKeyEnv.enable("PanView", false);
		HotKeyEnv.enable("SnapView", false);
		this.bEntered = true;
		this.mesh.chunkVisible("superretic", true);
		this.mesh.chunkVisible("Z_BoxTinter", true);
	}

	public boolean isToggleUp() {
		System.out.println("isToggleUp");
		return super.isToggleUp();
	}

	private void leave() {
		if (this.bEntered) {
			Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
			CmdEnv.top().exec("fov " + this.saveFov);
			HookPilot hookpilot = HookPilot.current;
			hookpilot.doAim(false);
			hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
			hookpilot.setSimpleUse(false);
			boolean flag = HotKeyEnv.isEnabled("aircraftView");
			HotKeyEnv.enable("PanView", flag);
			HotKeyEnv.enable("SnapView", flag);
			this.bEntered = false;
			this.mesh.chunkVisible("superretic", false);
			this.mesh.chunkVisible("Z_BoxTinter", false);
		}
	}

	public void reflectCockpitState() {
		if ((this.fm.AS.astateCockpitState & 2) != 0) {
			if (this.hasRevi) {
				this.mesh.chunkVisible("reticle", false);
				this.mesh.chunkVisible("reticlemask", false);
				this.mesh.chunkVisible("Revi_D0", false);
				this.mesh.chunkVisible("Revi_D1", true);
			}
			this.mesh.chunkVisible("GlassDamageFront2", true);
			this.mesh.chunkVisible("HullDamageRear", true);
		}
		if ((this.fm.AS.astateCockpitState & 1) != 0) {
			this.mesh.chunkVisible("GlassDamageFront", true);
			this.mesh.chunkVisible("HullDamageRear", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
			this.mesh.chunkVisible("Gauges_d0", false);
			this.mesh.chunkVisible("Gauges_d1", true);
			this.mesh.chunkVisible("HullDamageFront", true);
			this.mesh.chunkVisible("Z_Need_manifold", false);
			this.mesh.chunkVisible("Z_Need_oilpressure", false);
			this.mesh.chunkVisible("Z_Need_rpm", false);
			this.mesh.chunkVisible("Z_Need_alt1", false);
			this.mesh.chunkVisible("Z_Need_alt2", false);
			this.mesh.chunkVisible("Z_Need_variometer", false);
			this.mesh.chunkVisible("Z_Need_clock_sec", false);
			this.mesh.chunkVisible("Z_Need_clock_minute", false);
			this.mesh.chunkVisible("Z_Need_clock_hour", false);
			this.mesh.chunkVisible("Z_Need_clock_timer", false);
			this.mesh.chunkVisible("Z_Need_cylheadtemp", false);
		}
		if ((this.fm.AS.astateCockpitState & 4) != 0) {
			this.mesh.chunkVisible("GlassDamageLeft", true);
			this.mesh.chunkVisible("HullDamageLeft", true);
		}
		if ((this.fm.AS.astateCockpitState & 8) != 0) {
			this.mesh.chunkVisible("GlassDamageLeft", true);
			this.mesh.chunkVisible("HullDamageLeft", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
			this.mesh.chunkVisible("GlassDamageRight", true);
			this.mesh.chunkVisible("HullDamageRight", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
			this.mesh.chunkVisible("GlassDamageRight", true);
			this.mesh.chunkVisible("HullDamageRight", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x80) != 0) {
			if (this.hasRevi) {
				this.mesh.chunkVisible("OilRevi", true);
			} else {
				this.mesh.chunkVisible("Oil", true);
			}
		}
	}

	protected void reflectPlaneMats() {
		HierMesh hiermesh = this.aircraft().hierMesh();
		com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh
				.materialFind("Gloss1D0o"));
		this.mesh.materialReplace("Gloss1D0o", mat);
		mat = hiermesh.material(hiermesh.materialFind("Gloss1D1o"));
		this.mesh.materialReplace("Gloss1D1o", mat);
		mat = hiermesh.material(hiermesh.materialFind("Gloss1D2o"));
		this.mesh.materialReplace("Gloss1D2o", mat);
		mat = hiermesh.material(hiermesh.materialFind("Gloss2D0o"));
		this.mesh.materialReplace("Gloss2D0o", mat);
		mat = hiermesh.material(hiermesh.materialFind("Gloss2D1o"));
		this.mesh.materialReplace("Gloss2D1o", mat);
		mat = hiermesh.material(hiermesh.materialFind("Gloss2D2o"));
		this.mesh.materialReplace("Gloss2D2o", mat);
		mat = hiermesh.material(hiermesh.materialFind("Matt2D0o"));
		this.mesh.materialReplace("Matt2D0o", mat);
		mat = hiermesh.material(hiermesh.materialFind("Matt2D2o"));
		this.mesh.materialReplace("Matt2D2o", mat);
	}

	protected void reflectPlaneToModel() {
		HierMesh hiermesh = this.aircraft().hierMesh();
		this.mesh.chunkVisible("CF_D0", hiermesh.isChunkVisible("CF_D0"));
		this.mesh.chunkVisible("CF_D1", hiermesh.isChunkVisible("CF_D1"));
		this.mesh.chunkVisible("CF_D2", hiermesh.isChunkVisible("CF_D2"));
	}

	public void reflectWorldToInstruments(float f) {
		float f1 = 0.0F;
		if (this.bNeedSetUp) {
			this.reflectPlaneMats();
			this.bNeedSetUp = false;
		}
		this.mesh.chunkSetAngles("Z_reviIron", 90F * this.setNew.stbyPosition,
				0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Need_manifold",
				this.cvt(
						this.pictManifold = (0.85F * this.pictManifold)
								+ (0.15F * this.fm.EI.engines[0]
										.getManifoldPressure() * 76F), 30F,
						120F, 22F, 296F), 0.0F, 0.0F);
		f1 = -15F
				* (this.pictAiler = (0.85F * this.pictAiler)
						+ (0.15F * this.fm.CT.AileronControl));
		this.mesh.chunkSetAngles("Z_stick_horiz_axis", f1, 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_aileron_rods", -f1 / 14F, 0.0F, 0.0F);
		f1 = 14F * (this.pictElev = (0.85F * this.pictElev)
				+ (0.2F * this.fm.CT.ElevatorControl));
		this.mesh.chunkSetAngles("Z_Stick", f1, 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_elev_wire1", -f1, 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_elev_wire2", -f1, 0.0F, 0.0F);
		f1 = this.fm.CT.getRudder();
		this.mesh.chunkSetAngles("Z_wheel_break_valve", -12F * f1, 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_pedal_L", 24F * f1, 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_pedal_R", -24F * f1, 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_rudder_rod_L", -25F * f1, 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_rudder_rod_R", 25F * f1, 0.0F, 0.0F);
		this.mesh.chunkSetAngles(
				"Z_Throttle",
				-70F
						* this.interp(this.setNew.throttle,
								this.setOld.throttle, f), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Mixture",
				-70F * this.interp(this.setNew.mix, this.setOld.mix, f), 0.0F,
				0.0F);
		this.mesh.chunkSetAngles("Z_Need_alt1", this.cvt(
				this.interp(this.setNew.altimeter, this.setOld.altimeter, f),
				0.0F, 10000F, 0.0F, 7200F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Need_alt2", this.cvt(
				this.interp(this.setNew.altimeter, this.setOld.altimeter, f),
				0.0F, 10000F, 0.0F, 720F), 0.0F, 0.0F);
		float f3 = Pitot
				.Indicator((float) this.fm.Loc.z, this.fm.getSpeedKMH());
		if (f3 > 360F) {
			this.mesh.chunkSetAngles("Z_Need_speed",
					this.cvt(f3, 360F, 600F, -329F, -550F), 0.0F, 0.0F);
		} else {
			this.mesh.chunkSetAngles("Z_Need_speed",
					this.cvt(f3, 60F, 360F, 0.0F, -329F), 0.0F, 0.0F);
		}
		f1 = this.setNew.azimuth.getDeg(f);
		this.mesh.chunkSetAngles("Z_Need_compass", f1, 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Need_rpm", this.cvt(
				this.fm.EI.engines[0].getRPM(), 440F, 3320F, 0.0F, -332F),
				0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Need_clock_hour",
				-this.cvt(World.getTimeofDay(), 0.0F, 24F, 0.0F, -720F), 0.0F,
				0.0F);
		this.mesh
				.chunkSetAngles("Z_Need_clock_minute", -this.cvt(
						World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, -360F),
						0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Need_clock_sec", -this.cvt(
				((World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F,
				-360F), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Need_fuel",
				-this.cvt(this.fm.M.fuel, 0.0F, 300F, 0.0F, 52F), 0.0F, 0.0F);
		this.mesh
				.chunkSetAngles("Z_Need_oiltemp", this.cvt(
						this.fm.EI.engines[0].tOilOut, 0.0F, 100F, 0.0F, 329F),
						0.0F, 0.0F);
		this.mesh
				.chunkSetAngles(
						"Z_Need_oilpressure",
						this.cvt(
								1.0F + (0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0]
										.getReadyness()), 0.0F, 7F, 0.0F, -315F),
						0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Need_cylheadtemp", this.cvt(
				this.fm.EI.engines[0].tWaterOut, 0.0F, 350F, 0.0F, 110F), 0.0F,
				0.0F);
		this.mesh.chunkSetAngles("Z_Need_fuelpressure", this.cvt(
				this.fm.M.fuel <= 1.0F ? 0.0F : 0.78F, 0.0F, 4F, 0.0F, 100F),
				0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_magneto",
				-30F * this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);
		this.resetYPRmodifier();
		Cockpit.xyz[1] = -this.cvt(this.fm.Or.getTangage(), -20F, 20F, 0.04F,
				-0.04F);
		this.mesh.chunkSetLocate("Z_Need_red_liquid", Cockpit.xyz, Cockpit.ypr);
		this.mesh.chunkSetAngles("Z_Need_Turn",
				this.cvt(this.setNew.turn, -0.2F, 0.2F, -22.5F, 22.5F), 0.0F,
				0.0F);
		this.mesh
				.chunkSetAngles("Z_Need_bank",
						-this.cvt(this.getBall(8D), -8F, 8F, 16.9F, -16.9F),
						0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Need_variometer",
				-this.cvt(this.setNew.vspeed, -20F, 20F, 180F, -180F), 0.0F,
				0.0F);
		this.mesh.chunkSetAngles("Z_Need_oxygeneflow", -260F, 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Need_oxygenetank", -320F, 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_flaps_indicator", 0.7F * this.flaps, 0.0F,
				0.0F);
		if (this.flapsDirection == 1) {
			this.mesh.chunkSetAngles("Z_flaps_valve", -33F, 0.0F, 0.0F);
			this.mesh.chunkSetAngles("Z_flapsLeverKnob", -33F, 0.0F, 0.0F);
		} else if (this.flapsDirection == -1) {
			this.mesh.chunkSetAngles("Z_flaps_valve", 33F, 0.0F, 0.0F);
			this.mesh.chunkSetAngles("Z_flapsLeverKnob", 33F, 0.0F, 0.0F);
		} else {
			this.mesh.chunkSetAngles("Z_flaps_valve", 0.0F, 0.0F, 0.0F);
			this.mesh.chunkSetAngles("Z_flapsLeverKnob", 0.0F, 0.0F, 0.0F);
		}
		this.mesh.chunkSetAngles("Z_trim_indicator",
				1.9F * -this.fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_trim_wheel",
				600F * this.fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);
		if (this.fm.CT.bHasBrakeControl) {
			float f2 = this.fm.CT.getBrake();
			this.mesh.chunkSetAngles("Z_break_handle", f2 * 20F, 0.0F, 0.0F);
			this.mesh.chunkSetAngles("Z_Need_breakpressureR", this.cvt(f2
					+ (f2 * this.fm.CT.getRudder()), 0.0F, 1.5F, 0.0F, 148F),
					0.0F, 0.0F);
			this.mesh.chunkSetAngles("Z_Need_breakpressureL", -this.cvt(f2
					- (f2 * this.fm.CT.getRudder()), 0.0F, 1.5F, 0.0F, 148F),
					0.0F, 0.0F);
			this.mesh.chunkSetAngles("Z_Need_breakpressure1", -150F
					+ (f2 * 20F), 0.0F, 0.0F);
		}
		this.mesh.chunkSetAngles("Z_flaps_pump", -this.flapsPump * 40F, 0.0F,
				0.0F);
		if (this.fm.AS.bLandingLightOn) {
			this.mesh
					.chunkSetAngles("Z_switch_landing_light", -35F, 0.0F, 0.0F);
		} else {
			this.mesh
					.chunkSetAngles("Z_switch_landing_light", 0.0F, 0.0F, 0.0F);
		}
		if (this.fm.AS.bNavLightsOn) {
			this.mesh.chunkSetAngles("Z_switch_navigation_light", -35F, 0.0F,
					0.0F);
		} else {
			this.mesh.chunkSetAngles("Z_switch_navigation_light", 0.0F, 0.0F,
					0.0F);
		}
		if (this.cockpitLightControl) {
			this.mesh
					.chunkSetAngles("Z_switch_cockpit_light", -35F, 0.0F, 0.0F);
		} else {
			this.mesh
					.chunkSetAngles("Z_switch_cockpit_light", 0.0F, 0.0F, 0.0F);
		}
		if (this.tailWheelLock >= 1.0F) {
			this.mesh.chunkSetAngles("Z_tailwheel", this.tailWheelLock * 57F,
					0.0F, 7F);
			this.mesh.chunkSetAngles("Z_tailwheel_lever_wire",
					this.tailWheelLock * 57F, 0.0F, 7F);
		} else {
			this.mesh.chunkSetAngles("Z_tailwheel", this.tailWheelLock * 57F,
					0.0F, 0.0F);
			this.mesh.chunkSetAngles("Z_tailwheel_lever_wire",
					this.tailWheelLock * 57F, 0.0F, 0.0F);
		}
		this.mesh.chunkSetAngles("Z_wheelLockKnob", this.tailWheelLock * 57F,
				0.0F, 0.0F);
		this.mesh.chunkSetAngles("Z_Need_extinguisher",
				this.fm.EI.engines[0].getExtinguishers() * 95F, 0.0F, 0.0F);
		if (this.hasRevi) {
			this.mesh.chunkSetAngles("Z_reviDimmer", -this.cvt(
					this.interp(this.setNew.dimPos, this.setOld.dimPos, f),
					0.0F, 1.0F, 0.0F, -90F), 0.0F, 0.0F);
			this.mesh.chunkSetAngles("Z_reviDimmerLever", -this.cvt(
					this.interp(this.setNew.dimPos, this.setOld.dimPos, f),
					0.0F, 1.0F, 0.0F, 0.004F), 0.0F, 0.0F);
		} else {
			this.mesh.chunkSetAngles("Z_sight_cap", this.cvt(
					this.interp(this.setNew.dimPos, this.setOld.dimPos, f),
					0.0F, 1.0F, 0.0F, -130F), 0.0F, 0.0F);
			this.mesh.chunkSetAngles("Z_sight_cap_big", 0.0F, this.cvt(
					this.interp(this.setNew.dimPos, this.setOld.dimPos, f),
					0.0F, 1.0F, 0.0F, -130F), 0.0F);
			this.mesh.chunkSetAngles("Z_sight_cap_inside", this.cvt(
					this.interp(this.setNew.dimPos, this.setOld.dimPos, f),
					0.0F, 1.0F, 0.0F, -130F), 0.0F, 0.0F);
		}
	}

	public void setRevi() {
		this.hasRevi = true;
		this.mesh.chunkVisible("reticle", true);
		this.mesh.chunkVisible("reticlemask", true);
		this.mesh.chunkVisible("Revi_D0", true);
		this.mesh.chunkVisible("Z_sight_cap", false);
		this.mesh.chunkVisible("tubeSight", false);
		this.mesh.chunkVisible("tubeSightLens", false);
		this.mesh.chunkVisible("tube_inside", false);
		this.mesh.chunkVisible("tube_mask", false);
		this.mesh.chunkVisible("Z_sight_cap_inside", false);
		this.mesh.chunkVisible("GlassTube", false);
		this.mesh.chunkVisible("GlassRevi", true);
		this.mesh.chunkVisible("Z_reviIron", true);
		this.mesh.chunkVisible("Z_reviDimmer", true);
		this.mesh.chunkVisible("Z_reviDimmerLever", true);
	}

	public void toggleDim() {
		this.cockpitDimControl = !this.cockpitDimControl;
	}

	public void toggleLight() {
		this.cockpitLightControl = !this.cockpitLightControl;
		if (this.cockpitLightControl) {
			this.light1.light.setEmit(0.005F, 0.2F);
			this.light2.light.setEmit(0.005F, 0.2F);
			this.light3.light.setEmit(0.005F, 0.2F);
			this.light4.light.setEmit(0.002F, 0.1F);
			this.light5.light.setEmit(0.005F, 0.2F);
			this.setNightMats(true);
		} else {
			this.light1.light.setEmit(0.0F, 0.0F);
			this.light2.light.setEmit(0.0F, 0.0F);
			this.light3.light.setEmit(0.0F, 0.0F);
			this.light4.light.setEmit(0.0F, 0.0F);
			this.light5.light.setEmit(0.0F, 0.0F);
			this.setNightMats(false);
		}
	}

}
