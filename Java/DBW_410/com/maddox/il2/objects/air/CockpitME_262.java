/* CockpitME_262 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package com.maddox.il2.objects.air;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;

public class CockpitME_262 extends CockpitPilot
{
    private Variables setOld = new Variables();
    private Variables setNew = new Variables();
    private Variables setTmp;
    public Vector3f w = new Vector3f();
    private Point3d tmpP = new Point3d();
    private Vector3d tmpV = new Vector3d();
    private Gun[] gun = { null, null, null, null };
    private boolean bNeedSetUp = true;
    private float pictAiler = 0.0F;
    private float pictElev = 0.0F;
    private boolean bU4 = false;
    private static final float[] speedometerIndScale
	= { 0.0F, 0.0F, 0.0F, 17.0F, 35.5F, 57.5F, 76.0F, 95.0F, 112.0F };
    private static final float[] speedometerTruScale
	= { 0.0F, 32.75F, 65.5F, 98.25F, 131.0F, 164.0F, 200.0F, 237.0F,
	    270.5F, 304.0F, 336.0F };
    private static final float[] variometerScale
	= { 0.0F, 13.5F, 27.0F, 43.5F, 90.0F, 142.5F, 157.0F, 170.5F, 184.0F,
	    201.5F, 214.5F, 226.0F, 239.5F, 253.0F, 266.0F };
    private static final float[] rpmScale
	= { 0.0F, 0.0F, 0.0F, 16.5F, 34.5F, 55.0F, 77.5F, 104.0F, 133.5F,
	    162.5F, 192.0F, 224.0F, 254.0F, 255.5F, 260.0F };
    private static final float[] fuelScale
	= { 0.0F, 11.0F, 31.0F, 57.0F, 84.0F, 103.5F };
    
    class Interpolater extends InterpolateRef
    {
	public boolean tick() {
	    setTmp = setOld;
	    setOld = setNew;
	    setNew = setTmp;
	    setNew.altimeter = fm.getAltitude();
	    setNew.throttlel
		= (10.0F * setOld.throttlel
		   + fm.EI.engines[0].getControlThrottle()) / 11.0F;
	    setNew.throttler
		= (10.0F * setOld.throttler
		   + fm.EI.engines[1].getControlThrottle()) / 11.0F;
	    setNew.azimuth = fm.Or.getYaw();
	    if (setOld.azimuth > 270.0F && setNew.azimuth < 90.0F)
		setOld.azimuth -= 360.0F;
	    if (setOld.azimuth < 90.0F && setNew.azimuth > 270.0F)
		setOld.azimuth += 360.0F;
	    setNew.waypointAzimuth
		= (10.0F * setOld.waypointAzimuth
		   + (waypointAzimuth() - setOld.azimuth)
		   + World.Rnd().nextFloat(-30.0F, 30.0F)) / 11.0F;
	    setNew.vspeed
		= (299.0F * setOld.vspeed + fm.getVertSpeed()) / 300.0F;
	    if (cockpitDimControl) {
		if (setNew.dimPosition > 0.0F)
		    setNew.dimPosition = setOld.dimPosition - 0.05F;
	    } else if (setNew.dimPosition < 1.0F)
		setNew.dimPosition = setOld.dimPosition + 0.05F;
	    return true;
	}
    }
    
    private class Variables
    {
	float altimeter;
	float throttlel;
	float throttler;
	float azimuth;
	float waypointAzimuth;
	float vspeed;
	float dimPosition;
	
	private Variables() {
	    /* empty */
	}
    }
    
    protected float waypointAzimuth() {
	WayPoint waypoint = fm.AP.way.curr();
	if (waypoint == null)
	    return 0.0F;
	waypoint.getP(tmpP);
	tmpV.sub(tmpP, fm.Loc);
	return (float) (57.29577951308232 * Math.atan2(tmpV.y, tmpV.x));
    }
    
    public CockpitME_262() {
	super("3DO/Cockpit/Me-262/hier.him", "he111");
	cockpitNightMats
	    = new String[] { "2petitsb_d1", "2petitsb", "aiguill1",
			     "badinetm_d1", "badinetm", "baguecom", "brasdele",
			     "comptemu_d1", "comptemu", "petitfla_d1",
			     "petitfla", "turnbank" };
	setNightMats(false);
	setNew.dimPosition = 1.0F;
	interpPut(new Interpolater(), null, Time.current(), null);
    }
    
    public void removeCanopy()
    {
        mesh.chunkVisible("Canopy", false);
        mesh.chunkVisible("Z_Holes2_D1", false);
        mesh.chunkVisible("Z_Holes1_D1", false);
    }
    
    public void reflectWorldToInstruments(float f) {
	if (bNeedSetUp) {
	    reflectPlaneMats();
	    bNeedSetUp = false;
	}
	if (gun[0] == null) {
	    gun[0] = ((Aircraft) fm.actor).getGunByHookName("_CANNON03");
	    gun[1] = ((Aircraft) fm.actor).getGunByHookName("_CANNON01");
	    gun[2] = ((Aircraft) fm.actor).getGunByHookName("_CANNON02");
	    gun[3] = ((Aircraft) fm.actor).getGunByHookName("_CANNON04");
	}
	if (fm.isTick(44, 0)) {
	    mesh.chunkVisible("Z_GearLGreen1",
			      fm.CT.getGear() == 1.0F && fm.Gears.lgear);
	    mesh.chunkVisible("Z_GearRGreen1",
			      fm.CT.getGear() == 1.0F && fm.Gears.rgear);
	    mesh.chunkVisible("Z_GearCGreen1", fm.CT.getGear() == 1.0F);
	    mesh.chunkVisible("Z_GearLRed1", (fm.CT.getGear() == 0.0F
					      || fm.Gears.isAnyDamaged()));
	    mesh.chunkVisible("Z_GearRRed1", (fm.CT.getGear() == 0.0F
					      || fm.Gears.isAnyDamaged()));
	    mesh.chunkVisible("Z_GearCRed1", fm.CT.getGear() == 0.0F);
	    if (!bU4) {
		mesh.chunkVisible("Z_GunLamp01", !gun[0].haveBullets());
		mesh.chunkVisible("Z_GunLamp02", !gun[1].haveBullets());
		mesh.chunkVisible("Z_GunLamp03", !gun[2].haveBullets());
		mesh.chunkVisible("Z_GunLamp04", !gun[3].haveBullets());
	    }
	    mesh.chunkVisible("Z_MachLamp",
			      ((fm.getSpeed()
				/ Atmosphere.sonicSpeed((float) fm.Loc.z))
			       > 0.8F));
	    mesh.chunkVisible("Z_CabinLamp", fm.Loc.z > 12000.0);
	    mesh.chunkVisible("Z_FuelLampV", fm.M.fuel < 300.0F);
	    mesh.chunkVisible("Z_FuelLampIn", fm.M.fuel < 300.0F);
	}
	mesh.chunkSetAngles("Z_ReviTint",
			    cvt(interp(setNew.dimPosition, setOld.dimPosition,
				       f),
				0.0F, 1.0F, 0.0F, -45.0F),
			    0.0F, 0.0F);
	resetYPRmodifier();
    mesh.chunkSetAngles("Canopy", 0.0F, 0.0F, -100F * ((FlightModelMain) (fm)).CT.getCockpitDoor());
	Cockpit.xyz[1] = (fm.CT.GearControl == 0.0F && fm.CT.getGear() != 0.0F
			  ? -0.0107F : 0.0F);
	mesh.chunkSetLocate("Z_GearEin", Cockpit.xyz, Cockpit.ypr);
	Cockpit.xyz[1] = (fm.CT.GearControl == 1.0F && fm.CT.getGear() != 1.0F
			  ? -0.0107F : 0.0F);
	mesh.chunkSetLocate("Z_GearAus", Cockpit.xyz, Cockpit.ypr);
	resetYPRmodifier();
	Cockpit.xyz[1]
	    = fm.CT.FlapsControl < fm.CT.getFlap() ? -0.0107F : 0.0F;
	mesh.chunkSetLocate("Z_FlapEin", Cockpit.xyz, Cockpit.ypr);
	Cockpit.xyz[1]
	    = fm.CT.FlapsControl > fm.CT.getFlap() ? -0.0107F : 0.0F;
	mesh.chunkSetLocate("Z_FlapAus", Cockpit.xyz, Cockpit.ypr);
	mesh.chunkSetAngles("Z_Column",
			    10.0F * (pictAiler
				     = (0.85F * pictAiler
					+ 0.15F * fm.CT.AileronControl)),
			    0.0F,
			    10.0F * (pictElev
				     = (0.85F * pictElev
					+ 0.15F * fm.CT.ElevatorControl)));
	resetYPRmodifier();
	if (fm.CT.saveWeaponControl[0])
	    Cockpit.xyz[2] = -0.0025F;
	mesh.chunkSetLocate("Z_Columnbutton1", Cockpit.xyz, Cockpit.ypr);
	resetYPRmodifier();
	if (fm.CT.saveWeaponControl[2] || fm.CT.saveWeaponControl[3])
	    Cockpit.xyz[2] = -0.00325F;
	mesh.chunkSetLocate("Z_Columnbutton2", Cockpit.xyz, Cockpit.ypr);
	mesh.chunkSetAngles("Z_PedalStrut", 20.0F * fm.CT.getRudder(), 0.0F,
			    0.0F);
	mesh.chunkSetAngles("Z_LeftPedal", -20.0F * fm.CT.getRudder(), 0.0F,
			    0.0F);
	mesh.chunkSetAngles("Z_RightPedal", -20.0F * fm.CT.getRudder(), 0.0F,
			    0.0F);
	mesh.chunkSetAngles("Z_ThrottleL", 0.0F,
			    -75.0F * interp(setNew.throttlel, setOld.throttlel,
					    f),
			    0.0F);
	mesh.chunkSetAngles("Z_ThrottleR", 0.0F,
			    -75.0F * interp(setNew.throttler, setOld.throttler,
					    f),
			    0.0F);
	mesh.chunkSetAngles("Z_FuelLeverL",
			    (fm.EI.engines[0].getControlMagnetos() == 3 ? 6.5F
			     : 0.0F),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_FuelLeverR",
			    (fm.EI.engines[1].getControlMagnetos() == 3 ? 6.5F
			     : 0.0F),
			    0.0F, 0.0F);
	resetYPRmodifier();
	Cockpit.xyz[1] = 0.03675F * fm.CT.getTrimElevatorControl();
	mesh.chunkSetLocate("Z_TailTrim", Cockpit.xyz, Cockpit.ypr);
	if (fm.CT.Weapons[3] != null && !fm.CT.Weapons[3][0].haveBullets())
	    mesh.chunkSetAngles("Z_Bombbutton", 0.0F, 53.0F, 0.0F);
	mesh.chunkSetAngles("Z_AmmoCounter1",
			    cvt((float) gun[1].countBullets(), 0.0F, 100.0F,
				0.0F, -7.0F),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_AmmoCounter2",
			    cvt((float) gun[2].countBullets(), 0.0F, 100.0F,
				0.0F, -7.0F),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_Speedometer1",
			    floatindex(cvt(Pitot.Indicator((float) fm.Loc.z,
							   fm.getSpeedKMH()),
					   100.0F, 400.0F, 2.0F, 8.0F),
				       speedometerIndScale),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_Speedometer2",
			    floatindex(cvt(fm.getSpeedKMH(), 100.0F, 1000.0F,
					   1.0F, 10.0F),
				       speedometerTruScale),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_Altimeter1",
			    cvt(interp(setNew.altimeter, setOld.altimeter, f),
				0.0F, 16000.0F, 0.0F, 360.0F),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_Altimeter2",
			    cvt(interp(setNew.altimeter, setOld.altimeter, f),
				0.0F, 20000.0F, 0.0F, 7200.0F),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_Hour1", cvt(World.getTimeofDay(), 0.0F, 24.0F,
					   0.0F, 720.0F), 0.0F, 0.0F);
	mesh.chunkSetAngles("Z_Minute1", cvt(World.getTimeofDay() % 1.0F, 0.0F,
					     1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
	mesh.chunkSetAngles("Z_Second1",
			    cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F,
				0.0F, 1.0F, 0.0F, 360.0F),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_TurnBank1", fm.Or.getTangage(), 0.0F,
			    fm.Or.getKren());
	mesh.chunkSetAngles("Z_TurnBank2", 0.0F, 0.0F,
			    -cvt(getBall(6.0), -6.0F, 6.0F, -7.5F, 7.5F));
	w.set(fm.getW());
	fm.Or.transform(w);
	mesh.chunkSetAngles("Z_TurnBank3", 0.0F, 0.0F,
			    cvt(w.z, -0.23562F, 0.23562F, -50.0F, 50.0F));
	mesh.chunkSetAngles("Z_Climb1",
			    floatindex(cvt(setNew.vspeed, -20.0F, 50.0F, 0.0F,
					   14.0F),
				       variometerScale),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_RPML",
			    floatindex(cvt((fm.EI.engines[0].getRPM() * 10.0F
					    * 0.25F),
					   2000.0F, 14000.0F, 2.0F, 14.0F),
				       rpmScale),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_RPMR",
			    floatindex(cvt((fm.EI.engines[1].getRPM() * 10.0F
					    * 0.25F),
					   2000.0F, 14000.0F, 2.0F, 14.0F),
				       rpmScale),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_Compass1",
			    interp(setNew.azimuth, setOld.azimuth, f), 0.0F,
			    0.0F);
	mesh.chunkSetAngles("Z_Compass2",
			    -interp(setNew.waypointAzimuth,
				    setOld.waypointAzimuth, f),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_GasPressureL",
			    cvt((fm.M.fuel > 1.0F
				 ? 0.6F * fm.EI.engines[0].getPowerOutput()
				 : 0.0F),
				0.0F, 1.0F, 0.0F, 273.5F),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_GasPressureR",
			    cvt((fm.M.fuel > 1.0F
				 ? 0.6F * fm.EI.engines[1].getPowerOutput()
				 : 0.0F),
				0.0F, 1.0F, 0.0F, 273.5F),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_GasTempL",
			    cvt(fm.EI.engines[0].tWaterOut, 300.0F, 1000.0F,
				0.0F, 96.0F),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_GasTempR",
			    cvt(fm.EI.engines[1].tWaterOut, 300.0F, 1000.0F,
				0.0F, 96.0F),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_OilPressureL",
			    cvt(1.0F + 0.0050F * fm.EI.engines[0].tOilOut,
				0.0F, 10.0F, 0.0F, 278.0F),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_OilPressureR",
			    cvt(1.0F + 0.0050F * fm.EI.engines[1].tOilOut,
				0.0F, 10.0F, 0.0F, 278.0F),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_FuelPressL",
			    cvt((fm.M.fuel > 1.0F
				 ? (80.0F * fm.EI.engines[0].getPowerOutput()
				    * fm.EI.engines[0].getReadyness())
				 : 0.0F),
				0.0F, 160.0F, 0.0F, 278.0F),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_FuelPressR",
			    cvt((fm.M.fuel > 1.0F
				 ? (80.0F * fm.EI.engines[1].getPowerOutput()
				    * fm.EI.engines[1].getReadyness())
				 : 0.0F),
				0.0F, 160.0F, 0.0F, 278.0F),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_FuelRemainV",
			    floatindex(cvt(fm.M.fuel / 0.72F, 0.0F, 1000.0F,
					   0.0F, 5.0F),
				       fuelScale),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_FuelRemainIn",
			    floatindex(cvt(fm.M.fuel / 0.72F, 0.0F, 1000.0F,
					   0.0F, 5.0F),
				       fuelScale),
			    0.0F, 0.0F);
    }
    
    public void reflectCockpitState() {
	if ((fm.AS.astateCockpitState & 0x4) != 0) {
	    mesh.chunkVisible("HullDamage2", true);
	    mesh.chunkVisible("XGlassDamage4", true);
	    mesh.chunkVisible("Speedometer1", false);
	    mesh.chunkVisible("Speedometer1_D1", true);
	    mesh.chunkVisible("Z_Speedometer1", false);
	    mesh.chunkVisible("Z_Speedometer2", false);
	    mesh.chunkVisible("RPML", false);
	    mesh.chunkVisible("RPML_D1", true);
	    mesh.chunkVisible("Z_RPML", false);
	    mesh.chunkVisible("FuelRemainV", false);
	    mesh.chunkVisible("FuelRemainV_D1", true);
	    mesh.chunkVisible("Z_FuelRemainV", false);
	}
	if ((fm.AS.astateCockpitState & 0x8) != 0) {
	    mesh.chunkVisible("HullDamage4", true);
	    mesh.chunkVisible("XGlassDamage3", true);
	    mesh.chunkVisible("Altimeter1", false);
	    mesh.chunkVisible("Altimeter1_D1", true);
	    mesh.chunkVisible("Z_Altimeter1", false);
	    mesh.chunkVisible("Z_Altimeter2", false);
	    mesh.chunkVisible("GasPressureL", false);
	    mesh.chunkVisible("GasPressureL_D1", true);
	    mesh.chunkVisible("Z_GasPressureL", false);
	}
	if ((fm.AS.astateCockpitState & 0x10) != 0) {
	    mesh.chunkVisible("HullDamage1", true);
	    mesh.chunkVisible("XGlassDamage4", true);
	    mesh.chunkVisible("RPMR", false);
	    mesh.chunkVisible("RPMR_D1", true);
	    mesh.chunkVisible("Z_RPMR", false);
	    mesh.chunkVisible("FuelPressR", false);
	    mesh.chunkVisible("FuelPressR_D1", true);
	    mesh.chunkVisible("Z_FuelPressR", false);
	}
	if ((fm.AS.astateCockpitState & 0x20) != 0) {
	    mesh.chunkVisible("HullDamage3", true);
	    mesh.chunkVisible("XGlassDamage3", true);
	    mesh.chunkVisible("GasPressureR", false);
	    mesh.chunkVisible("GasPressureR_D1", true);
	    mesh.chunkVisible("Z_GasPressureR", false);
	}
	if ((fm.AS.astateCockpitState & 0x1) != 0) {
	    mesh.chunkVisible("XGlassDamage1", true);
	    mesh.chunkVisible("XGlassDamage2", true);
	    mesh.chunkVisible("Climb", false);
	    mesh.chunkVisible("Climb_D1", true);
	    mesh.chunkVisible("Z_Climb1", false);
	    mesh.chunkVisible("FuelPressR", false);
	    mesh.chunkVisible("FuelPressR_D1", true);
	    mesh.chunkVisible("Z_FuelPressR", false);
	}
	if ((fm.AS.astateCockpitState & 0x2) != 0) {
	    mesh.chunkVisible("XGlassDamage1", true);
	    mesh.chunkVisible("HullDamage1", true);
	    mesh.chunkVisible("HullDamage2", true);
	    mesh.chunkVisible("Revi_D0", false);
	    mesh.chunkVisible("Revi_D1", true);
	    mesh.chunkVisible("Z_Z_RETICLE", false);
	    mesh.chunkVisible("Z_Z_MASK", false);
	    mesh.chunkVisible("FuelPressL", false);
	    mesh.chunkVisible("FuelPressL_D1", true);
	    mesh.chunkVisible("Z_FuelPressL", false);
	}
	if ((fm.AS.astateCockpitState & 0x40) != 0) {
	    mesh.chunkVisible("HullDamage1", true);
	    mesh.chunkVisible("Altimeter1", false);
	    mesh.chunkVisible("Altimeter1_D1", true);
	    mesh.chunkVisible("Z_Altimeter1", false);
	    mesh.chunkVisible("Z_Altimeter2", false);
	    mesh.chunkVisible("Climb", false);
	    mesh.chunkVisible("Climb_D1", true);
	    mesh.chunkVisible("Z_Climb1", false);
	    mesh.chunkVisible("AFN", false);
	    mesh.chunkVisible("AFN_D1", true);
	    mesh.chunkVisible("Z_AFN1", false);
	    mesh.chunkVisible("Z_AFN2", false);
	    mesh.chunkVisible("FuelPressL", false);
	    mesh.chunkVisible("FuelPressL_D1", true);
	    mesh.chunkVisible("Z_FuelPressL", false);
	    mesh.chunkVisible("FuelRemainIn", false);
	    mesh.chunkVisible("FuelRemainIn_D1", true);
	    mesh.chunkVisible("Z_FuelRemainIn", false);
	}
	if ((fm.AS.astateCockpitState & 0x80) == 0) {
	    /* empty */
	}
	retoggleLight();
    }
    
    protected void reflectPlaneMats() {
	HierMesh hiermesh = aircraft().hierMesh();
	Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
	mesh.materialReplace("Gloss1D0o", mat);
	if (aircraft() instanceof ME_262A1AU4) {
	    mesh.chunkVisible("Z_Ammo262U4", true);
	    bU4 = true;
	}
    }
    
    public void toggleDim() {
	cockpitDimControl = !cockpitDimControl;
    }
    
    public void toggleLight() {
	cockpitLightControl = !cockpitLightControl;
	if (cockpitLightControl)
	    setNightMats(true);
	else
	    setNightMats(false);
    }
    
    private void retoggleLight() {
	if (cockpitLightControl) {
	    setNightMats(false);
	    setNightMats(true);
	} else {
	    setNightMats(true);
	    setNightMats(false);
	}
    }
}
