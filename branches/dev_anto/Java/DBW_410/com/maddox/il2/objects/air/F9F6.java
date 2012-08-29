package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            F9F, PaintSchemeFMPar06, Aircraft, NetAircraft

public class F9F6 extends F9F_Cougar
{

protected boolean bSlatsOff;

    public F9F6()
    {
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(FM.isPlayers())
        {
              FM.CT.bHasCockpitDoorControl = true;
              FM.CT.dvCockpitDoor = 0.5F;   
        }
    }
    
    public void update(float f)
    {
        if (FM.getSpeed() > 5.0F) {
    	    moveSlats(f);
    	    bSlatsOff = false;
    	} else
    	    slatsOff();
        super.update(f);
    }
        
    protected void moveSlats(float paramFloat) {
    	this.resetYPRmodifier();
    	Aircraft.xyz[1] = Aircraft.cvt(FM.getAOA(), 6.8F, 15.0F, 0.0F, -0.1F);
    	Aircraft.xyz[2] = Aircraft.cvt(FM.getAOA(), 6.8F, 15.0F, 0.0F, 0.065F);
    	this.hierMesh().chunkSetAngles("WingLEdge", 0.0F, Aircraft.cvt(FM.getAOA(), 6.8F, 15.0F, 0.0F, 8.5F), 0.0F);
    	this.hierMesh().chunkSetLocate("WingLEdge", Aircraft.xyz, Aircraft.ypr);
    	this.hierMesh().chunkSetAngles("WingREdge", 0.0F, Aircraft.cvt(FM.getAOA(), 6.8F, 15.0F, 0.0F, 8.5F), 0.0F);
    	this.hierMesh().chunkSetLocate("WingREdge", Aircraft.xyz, Aircraft.ypr);
        }
        
    protected void slatsOff() {
    	if (!bSlatsOff) {
    	    this.resetYPRmodifier();
    	    Aircraft.xyz[1] = -0.1F;
    	    Aircraft.xyz[2] = 0.065F;
    	    this.hierMesh().chunkSetAngles("WingLEdge", 0.0F, 8.5F, 0.0F);
    	    this.hierMesh().chunkSetLocate("WingLEdge", Aircraft.xyz, Aircraft.ypr);
    	    this.hierMesh().chunkSetAngles("WingREdge", 0.0F, 8.5F, 0.0F);
    	    this.hierMesh().chunkSetLocate("WingREdge", Aircraft.xyz, Aircraft.ypr);
    	    bSlatsOff = true;
    	}
     }
    
    static 
    {
        Class class1 = com.maddox.il2.objects.air.F9F6.class;
        new NetAircraft.SPAWN(class1);
        Property.set(class1, "iconFar_shortClassName", "F9F6");
        Property.set(class1, "meshName", "3DO/Plane/F9F6/hier.him");
        Property.set(class1, "PaintScheme", new PaintSchemeFMPar06());
        Property.set(class1, "yearService", 1946.9F);
        Property.set(class1, "yearExpired", 1955.3F);
        Property.set(class1, "FlightModel", "FlightModels/F9F6.fmd:Cougars");
        Property.set(class1, "cockpitClass", new Class[] {
            com.maddox.il2.objects.air.CockpitF9F_Cougar.class
        });
        Aircraft.weaponTriggersRegister(class1, new int[] {
                0, 0, 0, 0, 9, 9, 9, 9, 9, 9, 
                9, 9, 3, 3, 3, 3, 3, 3, 9, 9, 
                2, 2, 2, 2, 2, 2, 2, 2, 9, 9, 
                9, 9, 2, 2, 2, 2, 2, 2, 2, 2, 
                3
            });
            Aircraft.weaponHooksRegister(class1, new String[] {
                "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", 
                "_ExternalDev07", "_ExternalDev08", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalDev09", "_ExternalDev10", 
                "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalDev11", "_ExternalDev12", 
                "_ExternalDev13", "_ExternalDev14", "_ExternalRock09", "_ExternalRock09", "_ExternalRock10", "_ExternalRock10", "_ExternalRock11", "_ExternalRock11", "_ExternalRock12", "_ExternalRock12", 
                "_ExternalBomb07"
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[]{
            		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200", null, null, null, null, null, null, 
            		null, null, null, null, null, null, null, null, null, null, 
            		null, null, null, null, null, null, null, null, null, null, 
            		null, null, null, null, null, null, null, null, null, null, 
            		null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02x154Gal_Tank", new java.lang.String[]{
            		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","PylonAero65A 1","PylonAero65A 1", null, null, null, null,
            		"FuelTankGun_Cougar150galB 1","FuelTankGun_Cougar150galB 1", null, null, null, null, null, null, null, null, 
            		null, null, null, null, null, null, null, null, null, null, 
            		null, null, null, null, null, null, null, null, null, null, 
            		null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02x75Gal_Napalm", new java.lang.String[]{
            		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","PylonAero65A 1","PylonAero65A 1", null, null, null, null, 
            		null, null,"BombGun75Napalm 1","BombGun75Napalm 1", null, null, null, null, null, null, 
            		null, null, null, null, null, null, null, null, null, null,
            		null, null, null, null, null, null, null, null, null, null, 
            		null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02x250_Bomb", new java.lang.String[]{
            		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","PylonAero65A 1","PylonAero65A 1", null, null, null, null, 
            		null, null,"BombGun250lbs 1","BombGun250lbs 1", null, null, null, null, null, null, 
            		null, null, null, null, null, null, null, null, null, null,
            		null, null, null, null, null, null, null, null, null, null, 
            		null
            }); 
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02x500_Bomb", new java.lang.String[]{
            		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","PylonAero65A 1","PylonAero65A 1", null, null, null, null, 
            		null, null,"BombGun500lbs 1","BombGun500lbs 1", null, null, null, null, null, null, 
            		null, null, null, null, null, null, null, null, null, null,
            		null, null, null, null, null, null, null, null, null, null, 
            		null
            }); 
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02x750_Bomb", new java.lang.String[]{
            		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","PylonAero65A 1","PylonAero65A 1", null, null, null, null, 
            		null, null,"BombGun750lbs 1","BombGun750lbs 1", null, null, null, null, null, null, 
            		null, null, null, null, null, null, null, null, null, null,
            		null, null, null, null, null, null, null, null, null, null, 
            		null
            }); 
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02xM117_Bomb", new java.lang.String[]{
            		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","PylonAero65A 1","PylonAero65A 1", null, null, null, null, 
            		null, null,"BombGun750lbsM117 1","BombGun750lbsM117 1", null, null, null, null, null, null, 
            		null, null, null, null, null, null, null, null, null, null,
            		null, null, null, null, null, null, null, null, null, null, 
            		null
            });  
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02x1000_Bomb", new java.lang.String[]{
            		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","PylonAero65A 1","PylonAero65A 1", null, null, null, null, 
            		null, null,"BombGun1000lbs 1","BombGun1000lbs 1", null, null, null, null, null, null, 
            		null, null, null, null, null, null, null, null, null, null,
            		null, null, null, null, null, null, null, null, null, null, 
            		null
            });  
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02xM114_Bomb", new java.lang.String[]{
            		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","PylonAero65A 1","PylonAero65A 1", null, null, null, null, 
            		null, null,"BombGun1000lbs_M114 1","BombGun1000lbs_M114 1", null, null, null, null, null, null, 
            		null, null, null, null, null, null, null, null, null, null,
            		null, null, null, null, null, null, null, null, null, null, 
            		null
            });  
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02xMk81_Bomb", new java.lang.String[]{
            		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","PylonAero65A 1","PylonAero65A 1", null, null, null, null, 
            		null, null,"BombGunMk81 1","BombGunMk81 1", null, null, null, null, null, null, 
            		null, null, null, null, null, null, null, null, null, null,
            		null, null, null, null, null, null, null, null, null, null, 
            		null
            }); 
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02xMk82_Bomb", new java.lang.String[]{
            		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","PylonAero65A 1","PylonAero65A 1", null, null, null, null, 
            		null, null,"BombGunMk82 1","BombGunMk82 1", null, null, null, null, null, null, 
            		null, null, null, null, null, null, null, null, null, null,
            		null, null, null, null, null, null, null, null, null, null, 
            		null
            });  

            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02xMk83_Bomb", new java.lang.String[]{
            		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","PylonAero65A 1","PylonAero65A 1", null, null, null, null, 
            		null, null,"BombGunMk83 1","BombGunMk83 1", null, null, null, null, null, null, 
            		null, null, null, null, null, null, null, null, null, null,
            		null, null, null, null, null, null, null, null, null, null, 
            		null
            }); 
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02xLAU10", new java.lang.String[]{
            		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","PylonAero65A 1","PylonAero65A 1", null, null, null, null, 
            		null, null, null, null, null, null, null, null,"Pylon_Zuni 1","Pylon_Zuni 1",
            		"RocketGun5inchZuni 1","RocketGun5inchZuni 1","RocketGun5inchZuni 1","RocketGun5inchZuni 1","RocketGun5inchZuni 1","RocketGun5inchZuni 1","RocketGun5inchZuni 1","RocketGun5inchZuni 1", null, null, 
            		null, null, null, null, null, null, null, null, null, null, 
            		null
            }); 
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x_AIM9B", new java.lang.String[]{
            		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200", null, null, null, null, null, null, 
            		null, null, null, null, null, null, null, null, null, null, 
            		null, null, null, null, null, null, null, null,"PylonF86_Sidewinder 1","PylonF86_Sidewinder 1",
            		null, null,"RocketGunAIM9B 1"," RocketGunNull 1","RocketGunAIM9B 1"," RocketGunNull 1", null, null, null, null, 
            		null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[]{
            		null, null, null, null, null, null, null, null, null, null,
            		null, null, null, null, null, null, null, null, null, null, 
            		null, null, null, null, null, null, null, null, null, null, 
            		null, null, null, null, null, null, null, null, null, null,
            		null
            });
        }
    }