package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class Fokker_EIII extends Fokker_Eindecker{

	public Fokker_EIII(){		
	} 

	static 
	{
		Class class1 = com.maddox.il2.objects.air.Fokker_EIII.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "Fokker_EIII");
		Property.set(class1, "meshName", "3do/plane/FokkerE3(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
		Property.set(class1, "yearService", 1915F);
		Property.set(class1, "yearExpired", 1916F);
		Property.set(class1, "FlightModel", "FlightModels/FokkerEIII.fmd:FokkerE3");
		Property.set(class1, "cockpitClass", new Class[] {
				com.maddox.il2.objects.air.CockpitFokkerEIII.class
		});
		Aircraft.weaponTriggersRegister(class1, new int[] {
				0
		});
		Aircraft.weaponHooksRegister(class1, new String[] {
				"_MGUN01"
		});
		weaponsRegister(class1, "default", new String[] {
				"MGunSpandauMaxim08s 500"
		});
		weaponsRegister(class1, "none", new String[] {
				null
		});
	}

}
