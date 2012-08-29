/* CockpitSpit8 - Decompiled by JODE
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
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

public class CockpitSpitPR extends CockpitPilot
{
    private Variables setOld = new Variables();
    private Variables setNew = new Variables();
    private Variables setTmp;
    public Vector3f w = new Vector3f();
    private float pictAiler = 0.0F;
    private float pictElev = 0.0F;
    private float pictBrake = 0.0F;
    private float pictFlap = 0.0F;
    private float pictGear = 0.0F;
    private float pictManf = 1.0F;
    private boolean bNeedSetUp = true;
    private static final float[] speedometerScale
	= { 0.0F, 7.5F, 17.5F, 37.0F, 63.0F, 88.5F, 114.5F, 143.0F, 171.5F,
	    202.5F, 228.5F, 255.5F, 282.0F, 309.0F, 336.5F, 366.5F, 394.0F,
	    421.0F, 448.5F, 474.5F, 500.5F, 530.0F, 557.5F, 584.0F, 609.0F,
	    629.0F };
    private static final float[] radScale
	= { 0.0F, 3.0F, 7.0F, 13.5F, 21.5F, 27.0F, 34.5F, 50.5F, 71.0F, 94.0F,
	    125.0F, 161.0F, 202.5F, 253.0F, 315.5F };
    private static final float[] rpmScale
	= { 0.0F, 0.0F, 0.0F, 22.0F, 58.0F, 103.5F, 152.5F, 193.5F, 245.0F,
	    281.5F, 311.5F };
    private static final float[] variometerScale
	= { -158.0F, -111.0F, -65.5F, -32.5F, 0.0F, 32.5F, 65.5F, 111.0F,
	    158.0F };
    private Point3d tmpP = new Point3d();
    private Vector3d tmpV = new Vector3d();
    
    class Interpolater extends InterpolateRef
    {
	public boolean tick() {
	    if (fm != null) {
		setTmp = setOld;
		setOld = setNew;
		setNew = setTmp;
		setNew.throttle
		    = 0.92F * setOld.throttle + 0.08F * fm.CT.PowerControl;
		setNew.prop = (0.92F * setOld.prop
			       + 0.08F * fm.EI.engines[0].getControlProp());
		setNew.mix = (0.92F * setOld.mix
			      + 0.08F * fm.EI.engines[0].getControlMix());
		setNew.altimeter = fm.getAltitude();
		if (Math.abs(fm.Or.getKren()) < 30.0F)
		    setNew.azimuth
			= 0.97F * setOld.azimuth + 0.03F * -fm.Or.getYaw();
		if (setOld.azimuth > 270.0F && setNew.azimuth < 90.0F)
		    setOld.azimuth -= 360.0F;
		if (setOld.azimuth < 90.0F && setNew.azimuth > 270.0F)
		    setOld.azimuth += 360.0F;
		setNew.waypointAzimuth
		    = (0.91F * setOld.waypointAzimuth
		       + 0.09F * (waypointAzimuth() - setOld.azimuth)
		       + World.Rnd().nextFloat(-10.0F, 10.0F));
		setNew.vspeed
		    = 0.99F * setOld.vspeed + 0.01F * fm.getVertSpeed();
	    }
	    return true;
	}
    }
    
    private class Variables
    {
	float throttle;
	float prop;
	float mix;
	float altimeter;
	float azimuth;
	float vspeed;
	float waypointAzimuth;
	
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
	return (float) (57.29577951308232 * Math.atan2(-tmpV.y, tmpV.x));
    }
    
    public CockpitSpitPR() {
	super("3DO/Cockpit/SpitfirePR/hier.him", "bf109");
	cockpitNightMats
	    = new String[] { "COMPASS", "BORT2", "prib_five",
			     "prib_five_damage", "prib_one", "prib_one_damage",
			     "prib_three", "prib_three_damage", "prib_two",
			     "prib_two_damage", "text13", "text15" };
	setNightMats(false);
	interpPut(new Interpolater(), null, Time.current(), null);
    }
    
    public void reflectWorldToInstruments(float f) {
	if (bNeedSetUp) {
	    reflectPlaneMats();
	    bNeedSetUp = false;
	}
    com.maddox.il2.objects.air.Cockpit.xyz[1] = cvt(fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.55F);
    mesh.chunkSetLocate("Canopy", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
	mesh.chunkVisible("XLampGearUpL", fm.CT.getGear() == 0.0F);
	mesh.chunkVisible("XLampGearDownL", fm.CT.getGear() == 1.0F);
	mesh.chunkVisible("XLampboost",
			  fm.EI.engines[0].getControlCompressor() > 0);
	mesh.chunkSetAngles("Z_Columnbase", 0.0F,
			    8.0F * (pictElev
				    = (0.85F * pictElev
				       + 0.15F * fm.CT.ElevatorControl)),
			    0.0F);
	mesh.chunkSetAngles("Z_Column",
			    -30.0F * (pictAiler
				      = (0.85F * pictAiler
					 + 0.15F * fm.CT.AileronControl)),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_Shlang01a", -5.0F * pictAiler, 0.0F, 0.0F);
	mesh.chunkSetAngles("Z_Shlang01b", -9.0F * pictAiler, 0.0F, 0.0F);
	mesh.chunkSetAngles("Z_Shlang01c", -12.0F * pictAiler, 0.0F, 0.0F);
	mesh.chunkSetAngles("Z_Shlang02a", -5.0F * pictAiler, 0.0F, 0.0F);
	mesh.chunkSetAngles("Z_Shlang02b", -7.5F * pictAiler, 0.0F, 0.0F);
	mesh.chunkSetAngles("Z_Shlang02c", -15.0F * pictAiler, 0.0F, 0.0F);
	mesh.chunkSetAngles("Z_Shlang03a", -5.0F * pictAiler, 0.0F, 0.0F);
	mesh.chunkSetAngles("Z_Shlang03b", -8.5F * pictAiler, 0.0F, 0.0F);
	mesh.chunkSetAngles("Z_Shlang03c", -18.5F * pictAiler, 0.0F, 0.0F);
	mesh.chunkSetAngles("Z_Stick_Shtok01", 0.0F, 0.0F, 8.0F * pictElev);
	mesh.chunkSetAngles("Z_ColumnSwitch",
			    -18.0F * (pictBrake
				      = (0.89F * pictBrake
					 + 0.11F * fm.CT.BrakeControl)),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_Throtle1",
			    cvt(interp(setNew.throttle, setOld.throttle, f),
				0.0F, 1.1F, 20.0F, -30.0F),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_BasePedal", 20.0F * fm.CT.getRudder(), 0.0F,
			    0.0F);
	resetYPRmodifier();
	Cockpit.xyz[2] = 0.0578F * fm.CT.getRudder();
	mesh.chunkSetLocate("Z_LeftPedal", Cockpit.xyz, Cockpit.ypr);
	Cockpit.xyz[2] = -0.0578F * fm.CT.getRudder();
	mesh.chunkSetLocate("Z_RightPedal", Cockpit.xyz, Cockpit.ypr);
	mesh.chunkSetAngles("Z_Gear1",
			    (-160.0F
			     + 160.0F * (pictGear
					 = (0.89F * pictGear
					    + 0.11F * fm.CT.GearControl))),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_Flaps1",
			    160.0F * (pictFlap
				      = (0.89F * pictFlap
					 + 0.11F * fm.CT.FlapsControl)),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_Trim1",
			    1000.0F * fm.CT.getTrimElevatorControl(), 0.0F,
			    0.0F);
	mesh.chunkSetAngles("Z_Trim2", 1000.0F * fm.CT.getTrimRudderControl(),
			    0.0F, 0.0F);
	mesh.chunkSetAngles("Z_Prop1", -30.0F * setNew.prop, 0.0F, 0.0F);
	mesh.chunkSetAngles("COMPASS_M",
			    -interp(setNew.azimuth, setOld.azimuth, f), 0.0F,
			    0.0F);
	mesh.chunkSetAngles("SHKALA_DIRECTOR",
			    interp(setNew.azimuth, setOld.azimuth, f), 0.0F,
			    0.0F);
	mesh.chunkSetAngles("STREL_ALT_LONG", 0.0F, 0.0F,
			    cvt(interp(setNew.altimeter, setOld.altimeter, f),
				0.0F, 9144.0F, 0.0F, -10800.0F));
	mesh.chunkSetAngles("STREL_ALT_SHORT", 0.0F, 0.0F,
			    cvt(interp(setNew.altimeter, setOld.altimeter, f),
				0.0F, 9144.0F, 0.0F, -1080.0F));
	mesh.chunkSetAngles("STREL_ALT_SHRT1", 0.0F, 0.0F,
			    cvt(interp(setNew.altimeter, setOld.altimeter, f),
				0.0F, 9144.0F, 0.0F, -108.0F));
	mesh.chunkSetAngles("STRELKA_BOOST", 0.0F, 0.0F,
			    -cvt((pictManf
				  = (0.91F * pictManf
				     + 0.09F * fm.EI.engines[0]
						   .getManifoldPressure())),
				 0.5173668F, 2.7236898F, -70.0F, 250.0F));
	mesh.chunkSetAngles("STRELKA_FUEL", 0.0F, 0.0F,
			    cvt(fm.M.fuel, 0.0F, 378.54F, 0.0F, 68.0F));
	mesh.chunkSetAngles("STRELKA_RPM", 0.0F, 0.0F,
			    -floatindex(cvt(fm.EI.engines[0].getRPM(), 1000.0F,
					    5000.0F, 2.0F, 10.0F),
					rpmScale));
	mesh.chunkSetAngles("STRELK_TEMP_OIL", 0.0F, 0.0F,
			    -cvt(fm.EI.engines[0].tOilOut, 50.0F, 100.0F, 0.0F,
				 271.0F));
	mesh.chunkSetAngles("STRELK_TEMP_RAD", 0.0F, 0.0F,
			    -floatindex(cvt(fm.EI.engines[0].tWaterOut, 0.0F,
					    140.0F, 0.0F, 14.0F),
					radScale));
	mesh.chunkSetAngles("STR_OIL_LB", 0.0F,
			    cvt(1.0F + 0.05F * fm.EI.engines[0].tOilOut, 0.0F,
				10.0F, 0.0F, -37.0F),
			    0.0F);
	w.set(fm.getW());
	fm.Or.transform(w);
	mesh.chunkSetAngles("STREL_TURN_DOWN", 0.0F, 0.0F,
			    -cvt(w.z, -0.23562F, 0.23562F, -48.0F, 48.0F));
	mesh.chunkSetAngles("STRELK_TURN_UP", 0.0F, 0.0F,
			    -cvt(getBall(8.0), -8.0F, 8.0F, 35.0F, -35.0F));
	mesh.chunkVisible("STRELK_V_SHORT", false);
	mesh.chunkSetAngles("STRELK_V_LONG", 0.0F, 0.0F,
			    -floatindex(cvt(Pitot.Indicator((float) fm.Loc.z,
							    fm.getSpeed()),
					    0.0F, 223.52003F, 0.0F, 25.0F),
					speedometerScale));
	mesh.chunkSetAngles("STRELKA_VY", 0.0F, 0.0F,
			    -floatindex(cvt(setNew.vspeed, -20.32F, 20.32F,
					    0.0F, 8.0F),
					variometerScale));
	mesh.chunkSetAngles("STRELKA_GOR", 0.0F, 0.0F, fm.Or.getKren());
	resetYPRmodifier();
	Cockpit.xyz[2]
	    = cvt(fm.Or.getTangage(), -45.0F, 45.0F, 0.032F, -0.032F);
	mesh.chunkSetLocate("STRELKA_GOS", Cockpit.xyz, Cockpit.ypr);
	mesh.chunkSetAngles("STR_CLIMB", 0.0F, 0.0F,
			    cvt(fm.CT.trimElevator, -0.5F, 0.5F, -35.23F,
				35.23F));
    }
    
    protected void reflectPlaneMats() {
	HierMesh hiermesh = aircraft().hierMesh();
	Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
	mesh.materialReplace("Gloss1D0o", mat);
    }
    
    public void reflectCockpitState() {
	if ((fm.AS.astateCockpitState & 0x80) != 0)
	    mesh.chunkVisible("Z_OilSplats_D1", true);
    }
    
    public void toggleLight() {
	cockpitLightControl = !cockpitLightControl;
	if (cockpitLightControl)
	    setNightMats(true);
	else
	    setNightMats(false);
    }
}