// Source File Name: Mig_15bislate.java
// Author:           
// Last Modified by: Storebror 2011-06-01
package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;
import com.maddox.sound.Sample;
import com.maddox.sound.SoundFX;

// Referenced classes of package com.maddox.il2.objects.air:
//            Mig_15F, PaintSchemeFCSPar06, PaintSchemeFMPar06, Aircraft, 
//            NetAircraft
public class Mig_15bislate extends Mig_15F {

  public Mig_15bislate() {
	  smplSirena.setInfinite(true);
  }
  
  private SoundFX fxSirena = newSound("aircraft.Sirena2", false);
  private Sample smplSirena = new Sample("sample.Sirena2.wav", 256, 65535);
  private boolean sirenaSoundPlaying = false;
  private boolean bRadarWarning;
 
 private boolean sirenaWarning()
  {
	  Point3d point3d = new Point3d();
	  pos.getAbs(point3d);
	  Vector3d vector3d = new Vector3d();
	  Aircraft aircraft = World.getPlayerAircraft();
	  double d = Main3D.cur3D().land2D.worldOfsX() + ((Actor) (aircraft)).pos.getAbsPoint().x;
	  double d1 = Main3D.cur3D().land2D.worldOfsY() + ((Actor) (aircraft)).pos.getAbsPoint().y;
	  double d2 = Main3D.cur3D().land2D.worldOfsY() + ((Actor) (aircraft)).pos.getAbsPoint().z;
	  int i = (int)(-((double)((Actor) (aircraft)).pos.getAbsOrient().getYaw() - 90D));
	  if(i < 0)
		  i = 360 + i;
	  int j = (int)(-((double)((Actor) (aircraft)).pos.getAbsOrient().getPitch() - 90D));
	  if(j < 0)
		  j = 360 + j;
		  Actor actor = War.getNearestEnemy(this, 4000F);
		  if((actor instanceof Aircraft) && actor.getArmy() != World.getPlayerArmy() && ((actor instanceof TypeFighterAceMaker) && (actor instanceof TypeSupersonic || actor instanceof TypeFastJet))&& actor != World.getPlayerAircraft() && actor.getSpeed(vector3d) > 20D)
		  {
			  pos.getAbs(point3d);
			  double d3 = Main3D.cur3D().land2D.worldOfsX() + actor.pos.getAbsPoint().x;
			  double d4 = Main3D.cur3D().land2D.worldOfsY() + actor.pos.getAbsPoint().y;
			  double d5 = Main3D.cur3D().land2D.worldOfsY() + actor.pos.getAbsPoint().z;
			  new String();
			  new String();
			  double d6 = (int)(Math.ceil((d2 - d5) / 10D) * 10D);
			  new String();
			  double d7 = d3 - d;
			  double d8 = d4 - d1;
			  float f = 57.32484F * (float)Math.atan2(d8, -d7);
			  int i1 = (int)(Math.floor((int)f) - 90D);
			  if(i1 < 0)
				  i1 = 360 + i1;
			  int j1 = i1 - i;
			  double d9 = d - d3;
			  double d10 = d1 - d4;
			  double d11 = Math.sqrt(d6 * d6);
			  int k1 = (int)(Math.ceil(Math.sqrt(d10 * d10 + d9 * d9) / 10D) * 10D);
			  float f1 = 57.32484F * (float)Math.atan2(k1, d11);
			  int l1 = (int)(Math.floor((int)f1) - 90D);
			  if(l1 < 0)
				  l1 = 360 + l1;
			  int i2 = l1 - j;
			  int j2 = (int)(Math.ceil(((double)k1 * 3.2808399000000001D) / 100D) * 100D);
			  if(j2 >= 5280)
			  {
				  j2 = (int)Math.floor(j2 / 5280);
			  }
			  bRadarWarning = (double)k1 <= 3000D && (double)k1 >= 50D && i2 >= 195 && i2 <= 345 && Math.sqrt(j1 * j1) >= 120D;
	          playSirenaWarning(bRadarWarning);
		  }
		  else
		  {
			  bRadarWarning = false;
			  playSirenaWarning(bRadarWarning);
		  }
	  return true;
  }

  public void playSirenaWarning(boolean isThreatened)
  {
      if (isThreatened && !sirenaSoundPlaying) {
  		fxSirena.play(smplSirena);
  		sirenaSoundPlaying = true;
  		HUD.log(AircraftHotKeys.hudLogWeaponId, "SPO-2: Enemy on Six!");
      }
	  else if(!isThreatened && sirenaSoundPlaying){
		fxSirena.cancel();
		sirenaSoundPlaying = false;
	 }
  }
  
  public void update(float f) {
	  super.update(f);
	  HUD.log(World.getPlayerRegiment().country());
	  if(World.getPlayerRegiment().country() == "ru" || World.getPlayerRegiment().country() == "kx" || World.getPlayerRegiment().country() == "kp")
		  sirenaWarning();
	  if (FM.getSpeedKMH() > 1100.00F) {
		  FM.CT.AirBrakeControl = 1.0F;
		  World.cur();
	  }
  }

  static {
    Class class1 = com.maddox.il2.objects.air.Mig_15bislate.class;
    new NetAircraft.SPAWN(class1);
    Property.set(class1, "iconFar_shortClassName", "MiG-15");
    Property.set(class1, "meshName_ru", "3DO/Plane/MiG-15bis(Multi1)/hier.him");
    Property.set(class1, "PaintScheme_ru", new PaintSchemeFCSPar1956());
    Property.set(class1, "meshName_sk", "3DO/Plane/MiG-15bis(Multi1)/hier.him");
    Property.set(class1, "PaintScheme_sk", new PaintSchemeFMPar1956());
    Property.set(class1, "meshName_ro", "3DO/Plane/MiG-15bis(Multi1)/hier.him");
    Property.set(class1, "PaintScheme_ro", new PaintSchemeFMPar1956());
    Property.set(class1, "meshName_hu", "3DO/Plane/MiG-15bis(Multi1)/hier.him");
    Property.set(class1, "PaintScheme_hu", new PaintSchemeFMPar1956());
    Property.set(class1, "meshName", "3DO/Plane/MiG-15bis(Multi1)/hier.him");
    Property.set(class1, "PaintScheme", new PaintSchemeFMPar06());
    Property.set(class1, "yearService", 1951.4F);
    Property.set(class1, "yearExpired", 1960.3F);
    Property.set(class1, "FlightModel", "FlightModels/MiG-15F.fmd");
    Property.set(class1, "cockpitClass", new Class[]{
              com.maddox.il2.objects.air.CockpitMig_15F.class
            });
    Property.set(class1, "LOSElevation", 0.725F);
    com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[]{
              1, 0, 0, 9, 9
            });
    com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[]{
              "_CANNON01", "_CANNON02", "_CANNON03", "_ExternalDev01", "_ExternalDev02"
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xDroptanks", new java.lang.String[]{
              "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1"
            });
    com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[]{
              null, null, null, null, null
            });
  }
}