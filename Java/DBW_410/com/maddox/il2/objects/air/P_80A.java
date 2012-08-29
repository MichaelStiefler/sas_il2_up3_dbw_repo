// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 18/07/2011 12:28:55 AM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   P_80A.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.World;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_80, PaintSchemeFMPar05, PaintSchemeFMPar06, NetAircraft

public class P_80A extends P_80
{

    public P_80A()
    {
        oldthrl = -1F;
        curthrl = -1F;
        engineSurgeDamage = 0.0F;
    }

    public void engineSurge(float f) {
    	if (((FlightModelMain) (super.FM)).AS.isMaster()) {
    		if (curthrl == -1F) {
    			curthrl = oldthrl = ((FlightModelMain) (super.FM)).EI.engines[0].getControlThrottle();
    		} else {
    			curthrl = ((FlightModelMain) (super.FM)).EI.engines[0].getControlThrottle();
    			if (curthrl < 1.00F) {
    				if ((curthrl - oldthrl) / f > 8.0F && FM.EI.engines[0].getRPM() < 3200.0F && FM.EI.engines[0].getStage() == 6 && World.Rnd().nextFloat() < 0.50F) {
    					if (FM.actor == World.getPlayerAircraft()) {
    						HUD.log(AircraftHotKeys.hudLogWeaponId, "Compressor Stall!");
    					}
    					super.playSound("weapon.MGunMk108s", true);
    					engineSurgeDamage += 1.0000000000000001E-002D * (double) (((FlightModelMain) (super.FM)).EI.engines[0].getRPM() / 1000F);
    					((FlightModelMain) (super.FM)).EI.engines[0].doSetReadyness(((FlightModelMain) (super.FM)).EI.engines[0].getReadyness()
    							- engineSurgeDamage);
    					if (World.Rnd().nextFloat() < 0.20F && FM instanceof RealFlightModel && ((RealFlightModel) FM).isRealMode()) {
    						FM.AS.hitEngine(this, 0, 100);
    					}
    					if (World.Rnd().nextFloat() < 0.20F && FM instanceof RealFlightModel && ((RealFlightModel) FM).isRealMode()) {
    						FM.EI.engines[0].setEngineDies(this);
    					}
    				}
    				if ((curthrl - oldthrl) / f < -8.0F && (curthrl - oldthrl) / f > -100.0F && FM.EI.engines[0].getRPM() < 3200.0F && FM.EI.engines[0].getStage() == 6) {
    					super.playSound("weapon.MGunMk108s", true);
    					engineSurgeDamage += 1.0000000000000001E-003D * (double) (((FlightModelMain) (super.FM)).EI.engines[0].getRPM() / 1000F);
    					((FlightModelMain) (super.FM)).EI.engines[0].doSetReadyness(((FlightModelMain) (super.FM)).EI.engines[0].getReadyness()
    							- engineSurgeDamage);
    					if (World.Rnd().nextFloat() < 0.50F && FM instanceof RealFlightModel && ((RealFlightModel) FM).isRealMode()) {
    						if (FM.actor == World.getPlayerAircraft()) {
    							HUD.log(AircraftHotKeys.hudLogWeaponId, "Engine Flameout!");
    						}
    						FM.EI.engines[0].setEngineStops(this);
    					} else {
    						if (FM.actor == World.getPlayerAircraft()) {
    							HUD.log(AircraftHotKeys.hudLogWeaponId, "Compressor Stall!");
    						}
    					}
    				}
    			}
    			oldthrl = curthrl;
    		}
    	}
    }
    private float oldthrl;
    private float curthrl;
    private float engineSurgeDamage;
    
    static 
    {
        Class class1 = com.maddox.il2.objects.air.P_80A.class;
        new NetAircraft.SPAWN(class1);
        Property.set(class1, "iconFar_shortClassName", "YP-80");
        Property.set(class1, "meshName", "3DO/Plane/P-80(Multi1)/hier.him");
        Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        Property.set(class1, "meshName_us", "3DO/Plane/P-80(USA)/hier.him");
        Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar06());
        Property.set(class1, "meshNameDemo", "3DO/Plane/P-80(USA)/hier.him");
        Property.set(class1, "yearService", 1944.9F);
        Property.set(class1, "yearExpired", 1948.3F);
        Property.set(class1, "FlightModel", "FlightModels/P-80A.fmd");
		Property.set(class1, "cockpitClass", new Class[]{
	            com.maddox.il2.objects.air.CockpitYP_80.class
	        });
        Property.set(class1, "LOSElevation", 0.965F);
        weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0
        });
        weaponHooksRegister(class1, new String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06"
        });
        weaponsRegister(class1, "default", new String[] {
            "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300"
        });
        weaponsRegister(class1, "none", new String[] {
            null, null, null, null, null, null
        });
    }
}