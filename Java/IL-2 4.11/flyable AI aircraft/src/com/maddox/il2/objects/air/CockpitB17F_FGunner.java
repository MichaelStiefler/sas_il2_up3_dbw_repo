// Source File Name: CockpitB17F_FGunner.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Property;

public class CockpitB17F_FGunner extends CockpitGunner {

	private static final float speedometerScale[] = { 0.0F, 2.5F, 54F, 104F,
			154.5F, 205.5F, 224F, 242F, 259.5F, 277.5F, 296.25F, 314F, 334F,
			344.5F };

	private static Point3d P1 = new Point3d();

	private static Vector3d V = new Vector3d();

	private Hook hook1;

	static {
		Property.set(CockpitB17F_FGunner.class, "aiTuretNum", 0);
		Property.set(CockpitB17F_FGunner.class, "weaponControlNum", 10);
		Property.set(CockpitB17F_FGunner.class, "astatePilotIndx", 2);
	}

	public CockpitB17F_FGunner() {
		super("3DO/Cockpit/B-25J-FGun/FGunnerB17F.him", "bf109");
		this.hook1 = null;
		this.cockpitNightMats = (new String[] { "textrbm9", "texture25" });
		this.setNightMats(false);
	}

	public void clipAnglesGun(Orient orient) {
		if (this.isRealMode()) {
			if (!this.aiTurret().bIsOperable) {
				orient.setYPR(0.0F, 0.0F, 0.0F);
			} else {
				float f = orient.getYaw();
				float f1 = orient.getTangage();
				if (f < -35F) {
					f = -35F;
				}
				if (f > 23F) {
					f = 23F;
				}
				if (f1 > 15F) {
					f1 = 15F;
				}
				if (f1 < -25F) {
					f1 = -25F;
				}
				orient.setYPR(f, f1, 0.0F);
				orient.wrap();
			}
		}
	}

	protected boolean doFocusEnter() {
		if (super.doFocusEnter()) {
			this.aircraft().hierMesh().chunkVisible("CF_D0", false);
			this.aircraft().hierMesh().chunkVisible("Turret1B_D0", false);
			return true;
		} else {
			return false;
		}
	}

	protected void doFocusLeave() {
		this.aircraft().hierMesh().chunkVisible("CF_D0", true);
		this.aircraft().hierMesh().chunkVisible("Turret1B_D0", true);
		super.doFocusLeave();
	}

	public void doGunFire(boolean flag) {
		if (this.isRealMode()) {
			if ((this.emitter == null) || !this.emitter.haveBullets()
					|| !this.aiTurret().bIsOperable) {
				this.bGunFire = false;
			} else {
				this.bGunFire = flag;
			}
			this.fm.CT.WeaponControl[10] = this.bGunFire;
		}
	}

	protected void interpTick() {
		if (this.isRealMode()) {
			if ((this.emitter == null) || !this.emitter.haveBullets()
					|| !this.aiTurret().bIsOperable) {
				this.bGunFire = false;
			}
			this.fm.CT.WeaponControl[10] = this.bGunFire;
			if (this.bGunFire) {
				if (this.hook1 == null) {
					this.hook1 = new HookNamed(this.aircraft(), "_MGUN02");
				}
				this.doHitMasterAircraft(this.aircraft(), this.hook1, "_MGUN02");
			}
		}
	}

	public void moveGun(Orient orient) {
		super.moveGun(orient);
		this.mesh.chunkSetAngles("TurretA", -13F, -orient.getYaw(), 0.0F);
		this.mesh.chunkSetAngles("TurretB", -15.5F, orient.getTangage(), 0.0F);
		this.mesh.chunkSetAngles("TurretC", 0.0F,
				-this.cvt(orient.getYaw(), -17F, 17F, -17F, 17F), 0.0F);
	}

	public void reflectCockpitState() {
		if ((this.fm.AS.astateCockpitState & 1) != 0) {
			this.mesh.chunkVisible("XGlassDamage1", true);
		}
		if ((this.fm.AS.astateCockpitState & 8) != 0) {
			this.mesh.chunkVisible("XGlassDamage2", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
			this.mesh.chunkVisible("XGlassDamage2", true);
		}
		if ((this.fm.AS.astateCockpitState & 2) != 0) {
			this.mesh.chunkVisible("XGlassDamage3", true);
		}
		if ((this.fm.AS.astateCockpitState & 4) != 0) {
			this.mesh.chunkVisible("XHullDamage1", true);
		}
		if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
			this.mesh.chunkVisible("XHullDamage2", true);
		}
	}

	public void reflectWorldToInstruments(float f) {
		this.mesh.chunkSetAngles("zSpeed", 0.0F, this.floatindex(this.cvt(
				Pitot.Indicator((float) this.fm.Loc.z, this.fm.getSpeedKMH()),
				0.0F, 836.859F, 0.0F, 13F), speedometerScale), 0.0F);
		this.mesh.chunkSetAngles("zSpeed1", 0.0F, this.floatindex(
				this.cvt(this.fm.getSpeedKMH(), 0.0F, 836.859F, 0.0F, 13F),
				speedometerScale), 0.0F);
		this.mesh.chunkSetAngles("zAlt1", 0.0F,
				this.cvt((float) this.fm.Loc.z, 0.0F, 9144F, 0.0F, 10800F),
				0.0F);
		this.mesh
				.chunkSetAngles("zAlt2", 0.0F, this.cvt((float) this.fm.Loc.z,
						0.0F, 9144F, 0.0F, 1080F), 0.0F);
		this.mesh.chunkSetAngles("zHour", 0.0F,
				this.cvt(World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
		this.mesh.chunkSetAngles("zMinute", 0.0F,
				this.cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F),
				0.0F);
		this.mesh.chunkSetAngles("zSecond", 0.0F, this.cvt(
				((World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F,
				360F), 0.0F);
		this.mesh.chunkSetAngles("zCompass1", 0.0F, this.fm.Or.getAzimut(),
				0.0F);
		WayPoint waypoint = this.fm.AP.way.curr();
		if (waypoint != null) {
			waypoint.getP(P1);
			V.sub(P1, this.fm.Loc);
			float f2 = (float) (57.295779513082323D * Math.atan2(V.x, V.y));
			this.mesh.chunkSetAngles("zCompass2", 0.0F, 90F + f2, 0.0F);
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
}
