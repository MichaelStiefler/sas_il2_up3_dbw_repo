/* BF_109 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package com.maddox.il2.objects.air;
import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.rts.Property;

public abstract class BF_109 extends Scheme1 implements TypeFighter
{
    private float trimElevator = 0.0F;
    private boolean bHasElevatorControl = true;
    private float X = 1.0F;
    private float s17;
    private float s18;
    private float s31;
    private float s32;
    /*synthetic*/ static Class class$com$maddox$il2$objects$air$BF_109;
    
    public BF_109() {
    GlassState = 0;
	s17 = s18 = 0.15F;
	s31 = s32 = 0.35F;
    }
    
    protected void moveFlap(float f) {
	float f_0_ = -45.0F * f;
	hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f_0_, 0.0F);
	hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f_0_, 0.0F);
    }
    
    public void moveWheelSink() {
	resetYPRmodifier();
	Aircraft.xyz[2]
	    = Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.3F, 0.0F, 0.3F);
	hierMesh().chunkSetLocate("GearL99_D0", Aircraft.xyz, Aircraft.ypr);
	Aircraft.xyz[2]
	    = Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.3F, 0.0F, 0.3F);
	hierMesh().chunkSetLocate("GearR99_D0", Aircraft.xyz, Aircraft.ypr);
    }
    
    public void update(float f) {
	super.update(f);
	if (!getOp(31) || !getOp(32))
	    FM.CT.trimAileron
		= (FM.CT.ElevatorControl * (s32 - s31)
		   + FM.CT.trimElevator * (s18 - s17)) * FM.SensPitch / 3.0F;
	if (!bHasElevatorControl)
	    FM.CT.ElevatorControl = 0.0F;
	if (trimElevator != FM.CT.trimElevator) {
	    trimElevator = FM.CT.trimElevator;
	    hierMesh().chunkSetAngles("StabL_D0", 0.0F, 0.0F,
				      -16.0F * trimElevator);
	    hierMesh().chunkSetAngles("StabR_D0", 0.0F, 0.0F,
				      -16.0F * trimElevator);
	}
    }
    
    protected void moveElevator(float f) {
    	f = f - trimElevator;
	    hierMesh().chunkSetAngles("VatorL_D0", 0.0F,
				      -30.0F * f, 0.0F);
	    hierMesh().chunkSetAngles("VatorR_D0", 0.0F,
				      -30.0F * f, 0.0F);
    }
    
    public void doKillPilot(int i) {
	/* empty */
    }
    
    public void doMurderPilot(int i) {
	switch (i) {
	case 0:
	    hierMesh().chunkVisible("Pilot1_D0", false);
	    hierMesh().chunkVisible("Head1_D0", false);
	    hierMesh().chunkVisible("Pilot1_D1", true);
	    hierMesh().chunkVisible("HMask1_D0", false);
	    if (!FM.AS.bIsAboutToBailout) {
		if (hierMesh().isChunkVisible("Blister1_D0"))
		    hierMesh().chunkVisible("Gore1_D0", true);
		hierMesh().chunkVisible("Gore2_D0", true);
	    }
	}
    }
    
    public boolean cut(String string) {
	if (string.startsWith("Tail1"))
	    FM.AS.hitTank(this, 2, 100);
	return super.cut(string);
    }
    
    public void rareAction(float f, boolean bool) {
	super.rareAction(f, bool);
	if (bool && FM.AS.astateTankStates[0] > 5)
	    FM.AS.repairTank(0);
	if (FM.getAltitude() < 3000.0F)
	    hierMesh().chunkVisible("HMask1_D0", false);
	else
	    hierMesh().chunkVisible("HMask1_D0",
				    hierMesh().isChunkVisible("Head1_D0"));
    }
    
    private void reflectGlassState(int i)
    {
        GlassState = GlassState | i;
        switch(GlassState & 3)
        {
        case 1: // '\001'
            hierMesh().materialReplace("Glass2", "ZBulletsHoles");
            break;

        case 2: // '\002'
            hierMesh().materialReplace("Glass2", "GlassOil");
            break;

        case 3: // '\003'
            hierMesh().materialReplace("Glass2", "GlassOilHoles");
            break;
        }
        switch(GlassState & 0xc)
        {
        case 4: // '\004'
            hierMesh().materialReplace("GlassW", "ZBulletsHoles");
            break;

        case 8: // '\b'
            hierMesh().materialReplace("GlassW", "Wounded");
            hierMesh().chunkVisible("Gore2_D0", true);
            break;

        case 12: // '\f'
            hierMesh().materialReplace("GlassW", "WoundedHoles");
            hierMesh().chunkVisible("Gore2_D0", true);
            break;
        }
    }
    
    protected void hitBone(String string, Shot shot, Point3d point3d) {
	if (string.startsWith("xx")) {
	    if (string.startsWith("xxarmor")) {
		Aircraft.debugprintln(this, "*** Armor: Hit..");
		if (string.endsWith("p1")) {
		    getEnergyPastArmor(World.Rnd().nextFloat(20.0F, 30.0F),
				       shot);
		    FM.AS.setCockpitState(shot.initiator,
					  FM.AS.astateCockpitState | 0x2);
            reflectGlassState(5);
		    Aircraft.debugprintln(this, "*** Armor Glass: Hit..");
		    if (shot.power <= 0.0F) {
			Aircraft.debugprintln
			    (this, "*** Armor Glass: Bullet Stopped..");
			if (World.Rnd().nextFloat() < 0.5F)
			    doRicochetBack(shot);
		    }
		} else if (string.endsWith("p2"))
		    getEnergyPastArmor(0.5F, shot);
		else if (string.endsWith("p3")) {
		    if (point3d.z < -0.27)
			getEnergyPastArmor((4.099999904632568
					    / (Math.abs(Aircraft.v1.z)
					       + 9.999999747378752E-6)),
					   shot);
		    else
			getEnergyPastArmor((8.100000381469727
					    / (Math.abs(Aircraft.v1.x)
					       + 9.999999747378752E-6)),
					   shot);
		} else if (string.endsWith("p4"))
		    getEnergyPastArmor(9.0 / (Math.abs(Aircraft.v1.z)
					      + 9.999999747378752E-6),
				       shot);
		else if (string.endsWith("p5"))
		    getEnergyPastArmor(9.0 / (Math.abs(Aircraft.v1.x)
					      + 9.999999747378752E-6),
				       shot);
		else if (string.endsWith("p6"))
		    getEnergyPastArmor(9.0 / (Math.abs(Aircraft.v1.x)
					      + 9.999999747378752E-6),
				       shot);
		else if (string.endsWith("a1")) {
		    if (World.Rnd().nextFloat() < 0.5F)
			shot.powerType = 0;
		    getEnergyPastArmor(World.Rnd().nextFloat(1.0F, 3.0F),
				       shot);
		}
	    } else {
		if (string.startsWith("xxcontrols")) {
		    int i = string.charAt(10) - 48;
		    switch (i) {
		    default:
			break;
		    case 1:
		    case 4:
			if (getEnergyPastArmor(0.1F, shot) > 0.0F) {
			    FM.AS.setControlsDamage(shot.initiator, 0);
			    Aircraft.debugprintln
				(this,
				 "*** Aileron Controls: Control Crank Destroyed..");
			}
			break;
		    case 2:
		    case 3:
			if (getEnergyPastArmor(0.12F, shot) > 0.0F
			    && World.Rnd().nextFloat() < 0.1F) {
			    FM.AS.setControlsDamage(shot.initiator, 0);
			    Aircraft.debugprintln
				(this, "*** Aileron Controls: Disabled..");
			}
			break;
		    case 5:
		    case 6:
			if (getEnergyPastArmor(0.0020F, shot) > 0.0F
			    && World.Rnd().nextFloat() < 0.1F) {
			    bHasElevatorControl = false;
			    if (!FM.CT.bHasElevatorTrim)
				FM.AS.setControlsDamage(shot.initiator, 1);
			    Aircraft.debugprintln
				(this,
				 "*** Elevator Controls: Disabled / Strings Broken..");
			}
			if (getEnergyPastArmor(0.0020F, shot) > 0.0F
			    && World.Rnd().nextFloat() < 0.1F) {
			    FM.CT.bHasElevatorTrim = false;
			    if (!bHasElevatorControl)
				FM.AS.setControlsDamage(shot.initiator, 1);
			    Aircraft.debugprintln
				(this,
				 "*** Trimmer Controls: Disabled / Strings Broken..");
			}
			break;
		    case 7:
			if (getEnergyPastArmor(2.3F, shot) > 0.0F
			    && World.Rnd().nextFloat() < 0.2F) {
			    FM.AS.setControlsDamage(shot.initiator, 2);
			    Aircraft.debugprintln
				(this, "*** Rudder Controls: Disabled..");
			}
			break;
		    case 8:
			if (getEnergyPastArmor(3.2F, shot) > 0.0F) {
			    Aircraft.debugprintln
				(this,
				 "*** Control Column: Hit, Controls Destroyed..");
			    FM.AS.setControlsDamage(shot.initiator, 2);
			    FM.AS.setControlsDamage(shot.initiator, 1);
			    FM.AS.setControlsDamage(shot.initiator, 0);
			}
			break;
		    case 9:
			if (getEnergyPastArmor(0.1F, shot) > 0.0F) {
			    FM.AS.setCockpitState(shot.initiator,
						  (FM.AS.astateCockpitState
						   | 0x8));
			    FM.AS.setEngineSpecificDamage(shot.initiator, 0,
							  1);
			    FM.AS.setEngineSpecificDamage(shot.initiator, 0,
							  6);
			    Aircraft.debugprintln
				(this,
				 "*** Throttle Quadrant: Hit, Engine Controls Disabled..");
			}
		    }
		}
		if (string.startsWith("xxspar")) {
		    Aircraft.debugprintln(this,
					  "*** Spar Construction: Hit..");
		    if (string.startsWith("xxspart")
			&& chunkDamageVisible("Tail1") > 2
			&& (getEnergyPastArmor
			    ((3.5F
			      / (float) Math.sqrt(Aircraft.v1.y * Aircraft.v1.y
						  + (Aircraft.v1.z
						     * Aircraft.v1.z))),
			     shot)) > 0.0F) {
			Aircraft.debugprintln
			    (this, "*** Tail1 Spars Broken in Half..");
			nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
		    }
		    if (string.startsWith("xxsparli")
			&& chunkDamageVisible("WingLIn") > 2
			&& getEnergyPastArmor((9.5F
					       * World.Rnd().nextFloat(1.0F,
								       1.2F)),
					      shot) > 0.0F) {
			Aircraft.debugprintln(this,
					      "*** WingLIn Spars Damaged..");
			nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
		    }
		    if (string.startsWith("xxsparri")
			&& chunkDamageVisible("WingRIn") > 2
			&& getEnergyPastArmor((9.5F
					       * World.Rnd().nextFloat(1.0F,
								       1.2F)),
					      shot) > 0.0F) {
			Aircraft.debugprintln(this,
					      "*** WingRIn Spars Damaged..");
			nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
		    }
		    if (string.startsWith("xxsparlm")
			&& chunkDamageVisible("WingLMid") > 2
			&& getEnergyPastArmor((9.5F
					       * World.Rnd().nextFloat(1.0F,
								       1.2F)),
					      shot) > 0.0F) {
			Aircraft.debugprintln(this,
					      "*** WingLMid Spars Damaged..");
			nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
		    }
		    if (string.startsWith("xxsparrm")
			&& chunkDamageVisible("WingRMid") > 2
			&& getEnergyPastArmor((9.5F
					       * World.Rnd().nextFloat(1.0F,
								       1.2F)),
					      shot) > 0.0F) {
			Aircraft.debugprintln(this,
					      "*** WingRMid Spars Damaged..");
			nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
		    }
		    if (string.startsWith("xxsparlo")
			&& chunkDamageVisible("WingLOut") > 2
			&& getEnergyPastArmor((9.5F
					       * World.Rnd().nextFloat(1.0F,
								       1.2F)),
					      shot) > 0.0F) {
			Aircraft.debugprintln(this,
					      "*** WingLOut Spars Damaged..");
			nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
		    }
		    if (string.startsWith("xxsparro")
			&& chunkDamageVisible("WingROut") > 2
			&& getEnergyPastArmor((9.5F
					       * World.Rnd().nextFloat(1.0F,
								       1.2F)),
					      shot) > 0.0F) {
			Aircraft.debugprintln(this,
					      "*** WingROut Spars Damaged..");
			nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
		    }
		}
		if (string.startsWith("xxwj")
		    && getEnergyPastArmor(12.5F, shot) > 0.0F) {
		    if (string.endsWith("l")) {
			Aircraft.debugprintln
			    (this, "*** WingL Console Lock Destroyed..");
			nextDMGLevels(4, 2,
				      ("WingLIn_D"
				       + chunkDamageVisible("WingLIn")),
				      shot.initiator);
		    } else {
			Aircraft.debugprintln
			    (this, "*** WingR Console Lock Destroyed..");
			nextDMGLevels(4, 2,
				      ("WingRIn_D"
				       + chunkDamageVisible("WingRIn")),
				      shot.initiator);
		    }
		}
		if (string.startsWith("xxlock")) {
		    Aircraft.debugprintln(this,
					  "*** Lock Construction: Hit..");
		    if (string.startsWith("xxlockr")
			&& getEnergyPastArmor((5.5F
					       * World.Rnd().nextFloat(1.0F,
								       1.2F)),
					      shot) > 0.0F) {
			Aircraft.debugprintln(this,
					      "*** Rudder Lock Shot Off..");
			nextDMGLevels(3, 2,
				      ("Rudder1_D"
				       + chunkDamageVisible("Rudder1")),
				      shot.initiator);
		    }
		    if (string.startsWith("xxlockvl")
			&& getEnergyPastArmor((5.5F
					       * World.Rnd().nextFloat(1.0F,
								       1.2F)),
					      shot) > 0.0F) {
			Aircraft.debugprintln(this,
					      "*** VatorL Lock Shot Off..");
			nextDMGLevels(3, 2,
				      ("VatorL_D"
				       + chunkDamageVisible("VatorL")),
				      shot.initiator);
		    }
		    if (string.startsWith("xxlockvr")
			&& getEnergyPastArmor((5.5F
					       * World.Rnd().nextFloat(1.0F,
								       1.2F)),
					      shot) > 0.0F) {
			Aircraft.debugprintln(this,
					      "*** VatorR Lock Shot Off..");
			nextDMGLevels(3, 2,
				      ("VatorR_D"
				       + chunkDamageVisible("VatorR")),
				      shot.initiator);
		    }
		}
		if (string.startsWith("xxeng")) {
		    Aircraft.debugprintln(this, "*** Engine Module: Hit..");
		    if (string.endsWith("pipe")) {
			if (World.Rnd().nextFloat() < 0.1F
			    && FM.CT.Weapons[1] != null
			    && FM.CT.Weapons[1].length != 2) {
			    FM.AS.setJamBullets(1, 0);
			    Aircraft.debugprintln
				(this,
				 "*** Engine Module: Nose Nozzle Pipe Bent..");
			}
			getEnergyPastArmor(0.3F, shot);
		    } else if (string.endsWith("prop")) {
			if (getEnergyPastArmor(0.1F, shot) > 0.0F
			    && World.Rnd().nextFloat() < 0.8F) {
			    if (World.Rnd().nextFloat() < 0.5F) {
				FM.AS.setEngineSpecificDamage(shot.initiator,
							      0, 3);
				Aircraft.debugprintln
				    (this,
				     "*** Engine Module: Prop Governor Hit, Disabled..");
			    } else {
				FM.AS.setEngineSpecificDamage(shot.initiator,
							      0, 4);
				Aircraft.debugprintln
				    (this,
				     "*** Engine Module: Prop Governor Hit, Damaged..");
			    }
			}
		    } else if (string.endsWith("gear")) {
			if (getEnergyPastArmor(4.6F, shot) > 0.0F) {
			    if (World.Rnd().nextFloat() < 0.5F) {
				FM.EI.engines[0]
				    .setEngineStuck(shot.initiator);
				Aircraft.debugprintln
				    (this,
				     "*** Engine Module: Bullet Jams Reductor Gear..");
			    } else {
				FM.AS.setEngineSpecificDamage(shot.initiator,
							      0, 3);
				FM.AS.setEngineSpecificDamage(shot.initiator,
							      0, 4);
				Aircraft.debugprintln
				    (this,
				     "*** Engine Module: Reductor Gear Damaged, Prop Governor Failed..");
			    }
			}
		    } else if (string.endsWith("supc")) {
			if (getEnergyPastArmor(0.1F, shot) > 0.0F) {
			    FM.AS.setEngineSpecificDamage(shot.initiator, 0,
							  0);
			    Aircraft.debugprintln
				(this,
				 "*** Engine Module: Supercharger Disabled..");
			}
		    } else if (string.endsWith("feed")) {
			if (getEnergyPastArmor(3.2F, shot) > 0.0F
			    && World.Rnd().nextFloat() < 0.5F
			    && FM.EI.engines[0].getPowerOutput() > 0.7F) {
			    FM.AS.hitEngine(shot.initiator, 0, 100);
			    Aircraft.debugprintln
				(this,
				 "*** Engine Module: Pressurized Fuel Line Pierced, Fuel Flamed..");
			}
		    } else if (string.endsWith("fuel")) {
			if (getEnergyPastArmor(1.1F, shot) > 0.0F) {
			    FM.EI.engines[0].setEngineStops(shot.initiator);
			    Aircraft.debugprintln
				(this,
				 "*** Engine Module: Fuel Line Stalled, Engine Stalled..");
			}
		    } else if (string.endsWith("case")) {
			if (getEnergyPastArmor(2.1F, shot) > 0.0F) {
			    if (World.Rnd().nextFloat()
				< shot.power / 175000.0F) {
				FM.AS.setEngineStuck(shot.initiator, 0);
				Aircraft.debugprintln
				    (this,
				     "*** Engine Module: Bullet Jams Crank Ball Bearing..");
			    }
			    if (World.Rnd().nextFloat()
				< shot.power / 50000.0F) {
				FM.AS.hitEngine(shot.initiator, 0, 2);
				Aircraft.debugprintln
				    (this,
				     ("*** Engine Module: Crank Case Hit, Readyness Reduced to "
				      + FM.EI.engines[0].getReadyness()
				      + ".."));
			    }
			    FM.EI.engines[0].setReadyness
				(shot.initiator,
				 (FM.EI.engines[0].getReadyness()
				  - World.Rnd().nextFloat(0.0F,
							  (shot.power
							   / 48000.0F))));
			    Aircraft.debugprintln
				(this,
				 ("*** Engine Module: Crank Case Hit, Readyness Reduced to "
				  + FM.EI.engines[0].getReadyness() + ".."));
			}
			getEnergyPastArmor(22.5F, shot);
		    } else if (string.startsWith("xxeng1cyl")) {
			if (getEnergyPastArmor(0.1F, shot) > 0.0F
			    && (World.Rnd().nextFloat()
				< (FM.EI.engines[0].getCylindersRatio()
				   * 1.75F))) {
			    FM.EI.engines[0].setCyliderKnockOut
				(shot.initiator,
				 World.Rnd().nextInt(1, (int) (shot.power
							       / 4800.0F)));
			    Aircraft.debugprintln
				(this,
				 ("*** Engine Module: Cylinders Hit, "
				  + FM.EI.engines[0].getCylindersOperable()
				  + "/" + FM.EI.engines[0].getCylinders()
				  + " Left.."));
			    if (World.Rnd().nextFloat()
				< shot.power / 24000.0F) {
				FM.AS.hitEngine(shot.initiator, 0, 3);
				Aircraft.debugprintln
				    (this,
				     "*** Engine Module: Cylinders Hit, Engine Fires..");
			    }
			    if (World.Rnd().nextFloat() < 0.01F) {
				FM.AS.setEngineStuck(shot.initiator, 0);
				Aircraft.debugprintln
				    (this,
				     "*** Engine Module: Bullet Jams Piston Head..");
			    }
			    getEnergyPastArmor(22.5F, shot);
			}
		    } else if (string.startsWith("xxeng1mag")) {
			int i = string.charAt(9) - 49;
			FM.EI.engines[0].setMagnetoKnockOut(shot.initiator, i);
			Aircraft.debugprintln(this,
					      ("*** Engine Module: Magneto "
					       + i + " Destroyed.."));
		    } else if (string.endsWith("sync")) {
			if (getEnergyPastArmor(2.1F, shot) > 0.0F
			    && World.Rnd().nextFloat() < 0.5F) {
			    Aircraft.debugprintln
				(this,
				 "*** Engine Module: Gun Synchronized Hit, Nose Guns Lose Authority..");
			    FM.AS.setJamBullets(0, 0);
			    FM.AS.setJamBullets(0, 1);
			}
		    }        
		    else if (string.endsWith("oil1")) {
			FM.AS.hitOil(shot.initiator, 0);
            FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x80);
            reflectGlassState(2);
			Aircraft.debugprintln
			    (this, "*** Engine Module: Oil Radiator Hit..");
		    }
		}
		if (string.startsWith("xxoil")) {
		    FM.AS.hitOil(shot.initiator, 0);
		    getEnergyPastArmor(0.22F, shot);
            FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x80);
            reflectGlassState(2);
		    Aircraft.debugprintln
			(this, "*** Engine Module: Oil Tank Pierced..");
		}
		if (string.startsWith("xxtank")) {
		    int i = string.charAt(6) - 48;
		    switch (i) {
		    default:
			break;
		    case 1:
		    case 2:
			if (getEnergyPastArmor(0.1F, shot) > 0.0F
			    && World.Rnd().nextFloat() < 0.25F) {
			    if (FM.AS.astateTankStates[2] == 0) {
				Aircraft.debugprintln
				    (this, "*** Fuel Tank: Pierced..");
				FM.AS.hitTank(shot.initiator, 2, 1);
				FM.AS.doSetTankState(shot.initiator, 2, 1);
			    }
			    if (shot.powerType == 3
				&& World.Rnd().nextFloat() < 0.5F) {
				FM.AS.hitTank(shot.initiator, 2, 2);
				Aircraft.debugprintln(this,
						      "*** Fuel Tank: Hit..");
			    }
			}
			break;
		    case 3:
			if (World.Rnd().nextFloat() < 0.05F) {
			    Aircraft.debugprintln(this,
						  "*** MW50 Tank: Pierced..");
			    FM.AS.setInternalDamage(shot.initiator, 2);
			}
			break;
		    case 4:
			if (getEnergyPastArmor(1.7F, shot) > 0.0F
			    && ((shot.powerType == 3
				 && World.Rnd().nextFloat() < 0.5F)
				|| World.Rnd().nextFloat() < 0.25F)) {
			    Aircraft.debugprintln
				(this,
				 "*** Nitrogen Oxyde Tank: Pierced, Nitros Flamed..");
			    FM.AS.hitTank(shot.initiator, 0, 100);
			    FM.AS.hitTank(shot.initiator, 1, 100);
			    FM.AS.hitTank(shot.initiator, 2, 100);
			}
		    }
		}
		if (string.startsWith("xxmgun")) {
		    if (string.endsWith("01")) {
			Aircraft.debugprintln(this,
					      "*** Cowling Gun: Disabled..");
			FM.AS.setJamBullets(0, 0);
		    }
		    if (string.endsWith("02")) {
			Aircraft.debugprintln(this,
					      "*** Cowling Gun: Disabled..");
			FM.AS.setJamBullets(0, 1);
		    }
		    if (string.endsWith("l")) {
			Aircraft.debugprintln(this,
					      "*** Wing Gun (L): Disabled..");
			FM.AS.setJamBullets(1, 0);
		    }
		    if (string.endsWith("r")) {
			Aircraft.debugprintln(this,
					      "*** Wing Gun (L): Disabled..");
			FM.AS.setJamBullets(1, 1);
		    }
		    getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 12.96F),
				       shot);
		}
		if (string.startsWith("xxcannon")) {
		    Aircraft.debugprintln(this, "*** Nose Cannon: Disabled..");
		    FM.AS.setJamBullets(1, 0);
		    getEnergyPastArmor(World.Rnd().nextFloat(3.3F, 24.6F),
				       shot);
		}
		if (string.startsWith("xxammo")) {
		    if (World.Rnd().nextFloat(3800.0F, 30000.0F)
			< shot.power) {
			if (string.endsWith("01")) {
			    Aircraft.debugprintln
				(this,
				 "*** Cowling Gun: Ammo Feed Chain Broken..");
			    FM.AS.setJamBullets(0, 0);
			}
			if (string.endsWith("02")) {
			    Aircraft.debugprintln
				(this,
				 "*** Cowling Gun: Ammo Feed Chain Broken..");
			    FM.AS.setJamBullets(0, 1);
			}
			if (string.endsWith("l")) {
			    Aircraft.debugprintln
				(this,
				 "*** Wing Gun (L): Ammo Feed Drum Damaged..");
			    FM.AS.setJamBullets(1, 0);
			}
			if (string.endsWith("r")) {
			    Aircraft.debugprintln
				(this,
				 "*** Wing Gun (R): Ammo Feed Drum Damaged..");
			    FM.AS.setJamBullets(1, 1);
			}
		    }
		    getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 28.33F),
				       shot);
		}
	    }
	} else if (string.startsWith("xcf") || string.startsWith("xcockpit")) {
	    if (chunkDamageVisible("CF") < 3)
		hitChunk("CF", shot);
	    if (string.startsWith("xcockpit")) {    	
		if (point3d.z > 0.4) {
		    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x1);
		    reflectGlassState(5);
		    if (World.Rnd().nextFloat() < 0.1F){
			FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x20);
		    reflectGlassState(5);
		    }
		} else if (point3d.y > 0.0)
		    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x4);
		else {
		    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x10);
		    reflectGlassState(5);
		}
		if (point3d.x > 0.2)
		    FM.AS.setCockpitState(shot.initiator,
					  FM.AS.astateCockpitState | 0x40);
	    }
	} else if (string.startsWith("xeng")) {
	    if (chunkDamageVisible("Engine1") < 2)
		hitChunk("Engine1", shot);
	} else if (string.startsWith("xtail")) {
	    if (chunkDamageVisible("Tail1") < 3)
		hitChunk("Tail1", shot);
	} else if (string.startsWith("xkeel")) {
	    if (chunkDamageVisible("Keel1") < 2)
		hitChunk("Keel1", shot);
	} else if (string.startsWith("xrudder")) {
	    if (chunkDamageVisible("Rudder1") < 1)
		hitChunk("Rudder1", shot);
	} else if (string.startsWith("xstab")) {
	    if (string.startsWith("xstabl") && chunkDamageVisible("StabL") < 2)
		hitChunk("StabL", shot);
	    if (string.startsWith("xstabr") && chunkDamageVisible("StabR") < 1)
		hitChunk("StabR", shot);
	} else if (string.startsWith("xvator")) {
	    if (string.startsWith("xvatorl")
		&& chunkDamageVisible("VatorL") < 1)
		hitChunk("VatorL", shot);
	    if (string.startsWith("xvatorr")
		&& chunkDamageVisible("VatorR") < 1)
		hitChunk("VatorR", shot);
	} else if (string.startsWith("xwing")) {
	    if (string.startsWith("xwinglin")
		&& chunkDamageVisible("WingLIn") < 3)
		hitChunk("WingLIn", shot);
	    if (string.startsWith("xwingrin")
		&& chunkDamageVisible("WingRIn") < 3)
		hitChunk("WingRIn", shot);
	    if (string.startsWith("xwinglmid")
		&& chunkDamageVisible("WingLMid") < 3)
		hitChunk("WingLMid", shot);
	    if (string.startsWith("xwingrmid")
		&& chunkDamageVisible("WingRMid") < 3)
		hitChunk("WingRMid", shot);
	    if (string.startsWith("xwinglout")
		&& chunkDamageVisible("WingLOut") < 3)
		hitChunk("WingLOut", shot);
	    if (string.startsWith("xwingrout")
		&& chunkDamageVisible("WingROut") < 3)
		hitChunk("WingROut", shot);
	} else if (string.startsWith("xarone")) {
	    if (string.startsWith("xaronel"))
		hitChunk("AroneL", shot);
	    if (string.startsWith("xaroner"))
		hitChunk("AroneR", shot);
	} else if (string.startsWith("xpilot") || string.startsWith("xhead")) {
	    int i = 0;
	    int i_1_;
	    if (string.endsWith("a")) {
		i = 1;
		i_1_ = string.charAt(6) - 49;
	    } else if (string.endsWith("b")) {
		i = 2;
		i_1_ = string.charAt(6) - 49;
	    } else
		i_1_ = string.charAt(5) - 49;
	    hitFlesh(i_1_, shot, i);
        if (FM.AS.getPilotHealth(0) < 1.0F)
        reflectGlassState(8);
	}
    }
    
    private void cutOp(int i) {
	FM.Operate &= 1L << i ^ 0xffffffffffffffffL;
    }
    
    protected boolean getOp(int i) {
	return (FM.Operate & 1L << i) != 0L;
    }
    
    private float Op(int i) {
	return getOp(i) ? 1.0F : 0.0F;
    }
    
    protected boolean cutFM(int i, int i_2_, Actor actor) {
	if (!getOp(i))
	    return false;
	switch (i) {
	case 17:
	    cut("StabL");
	    cutOp(17);
	    FM.setCapableOfACM(false);
	    if (World.Rnd().nextInt(-1, 8) < FM.Skill)
		FM.setReadyToReturn(true);
	    if (World.Rnd().nextInt(-1, 16) < FM.Skill)
		FM.setReadyToDie(true);
	    FM.Sq.liftStab *= 0.5F * Op(18) + 0.1F;
	    FM.Sq.liftWingLIn *= 1.1F;
	    FM.Sq.liftWingRIn *= 0.9F;
	    FM.Sq.dragProducedCx -= 0.06F;
	    if (Op(18) == 0.0F) {
		FM.SensPitch = 0.0F;
		FM.setGCenter(0.2F);
	    } else {
		FM.setGCenter(0.1F);
		s17 = 0.0F;
		FM.SensPitch *= s17 + s18 + s31 + s32;
		X = 1.0F / (s17 + s18 + s31 + s32);
		s18 *= X;
		s31 *= X;
		s32 *= X;
	    }
	    /* fall through */
	case 31:
	    if (Op(31) == 0.0F)
		return false;
	    cut("VatorL");
	    cutOp(31);
	    if (Op(32) == 0.0F) {
		bHasElevatorControl = false;
		FM.setCapableOfACM(false);
		if (Op(18) == 0.0F)
		    FM.setReadyToDie(true);
	    }
	    FM.Sq.squareElevators *= 0.5F * Op(32);
	    FM.Sq.dragProducedCx += 0.06F;
	    s31 = 0.0F;
	    FM.SensPitch *= s17 + s18 + s31 + s32;
	    X = 1.0F / (s17 + s18 + s31 + s32);
	    s17 *= X;
	    s18 *= X;
	    s32 *= X;
	    return false;
	case 18:
	    cut("StabR");
	    cutOp(18);
	    FM.setCapableOfACM(false);
	    if (World.Rnd().nextInt(-1, 8) < FM.Skill)
		FM.setReadyToReturn(true);
	    if (World.Rnd().nextInt(-1, 16) < FM.Skill)
		FM.setReadyToDie(true);
	    FM.Sq.liftStab *= 0.5F * Op(17) + 0.1F;
	    FM.Sq.liftWingLIn *= 0.9F;
	    FM.Sq.liftWingRIn *= 1.1F;
	    FM.Sq.dragProducedCx -= 0.06F;
	    if (Op(17) == 0.0F) {
		FM.SensPitch = 0.0F;
		FM.setGCenter(0.2F);
	    } else {
		FM.setGCenter(0.1F);
		s18 = 0.0F;
		FM.SensPitch *= s17 + s18 + s31 + s32;
		X = 1.0F / (s17 + s18 + s31 + s32);
		s17 *= X;
		s31 *= X;
		s32 *= X;
	    }
	    /* fall through */
	case 32:
	    if (Op(32) == 0.0F)
		return false;
	    cut("VatorR");
	    cutOp(32);
	    if (Op(31) == 0.0F) {
		bHasElevatorControl = false;
		FM.setCapableOfACM(false);
		if (Op(17) == 0.0F)
		    FM.setReadyToDie(true);
	    }
	    FM.Sq.squareElevators *= 0.5F * Op(31);
	    FM.Sq.dragProducedCx += 0.06F;
	    s32 = 0.0F;
	    FM.SensPitch *= s17 + s18 + s31 + s32;
	    X = 1.0F / (s17 + s18 + s31 + s32);
	    s17 *= X;
	    s18 *= X;
	    s31 *= X;
	    return false;
	default:
	    return super.cutFM(i, i_2_, actor);
	}
    }
    
    public void msgExplosion(Explosion explosion) {
	setExplosion(explosion);
	if (explosion.chunkName != null && explosion.power > 0.0F
	    && explosion.chunkName.startsWith("Tail1")) {
	    if (World.Rnd().nextFloat(0.0F, 0.038F) < explosion.power)
		FM.AS.setControlsDamage(explosion.initiator, 1);
	    if (World.Rnd().nextFloat(0.0F, 0.042F) < explosion.power)
		FM.AS.setControlsDamage(explosion.initiator, 2);
	}
	super.msgExplosion(explosion);
    }
    
    /*synthetic*/ static Class class$(String string) {
	Class var_class;
	try {
	    var_class = Class.forName(string);
	} catch (ClassNotFoundException classnotfoundexception) {
	    throw new NoClassDefFoundError(classnotfoundexception
					       .getMessage());
	}
	return var_class;
    }
    
    private int GlassState;
    
    static {
	Class var_class = (class$com$maddox$il2$objects$air$BF_109 == null
			   ? (class$com$maddox$il2$objects$air$BF_109
			      = class$("com.maddox.il2.objects.air.BF_109"))
			   : class$com$maddox$il2$objects$air$BF_109);
	Property.set(var_class, "originCountry", PaintScheme.countryGermany);
    }
}