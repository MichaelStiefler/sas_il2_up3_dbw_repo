// Source File Name: Mig_17A.java
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
import com.maddox.il2.game.Mission;
import com.maddox.rts.Property;
import com.maddox.sound.Sample;
import com.maddox.sound.SoundFX;

public class Mig_17A extends Mig_17 {

  public Mig_17A() {
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
		  if((actor instanceof Aircraft) && actor.getArmy() != World.getPlayerArmy() && ((actor instanceof TypeFighterAceMaker) && (actor instanceof TypeRadarGunsight))&& actor != World.getPlayerAircraft() && actor.getSpeed(vector3d) > 20D)
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
  
  public void update(float f1) {
    super.update(f1);
    if (FM.EI.engines[0].getPowerOutput() > 0.50F
            && FM.EI.engines[0].getStage() == 6) {
      if (FM.EI.engines[0].getPowerOutput() > 0.50F) {
        FM.AS.setSootState(this, 0, 3);
      }
    } else {
      FM.AS.setSootState(this, 0, 0);
    }
	  if((World.getPlayerRegiment().country() == "ru" || World.getPlayerRegiment().country() == "nv") && Mission.curYear() >= 1952)
		  sirenaWarning();
  }

  static {
    Class class1 = com.maddox.il2.objects.air.Mig_17A.class;
    new NetAircraft.SPAWN(class1);
    Property.set(class1, "iconFar_shortClassName", "MiG-17");
    Property.set(class1, "meshName_ru", "3DO/Plane/MiG-17A(Multi1)/hier.him");
    Property.set(class1, "PaintScheme_ru", new PaintSchemeFCSPar1956());
    Property.set(class1, "meshName_sk", "3DO/Plane/MiG-17A(Multi1)/hier.him");
    Property.set(class1, "PaintScheme_sk", new PaintSchemeFMPar1956());
    Property.set(class1, "meshName_ro", "3DO/Plane/MiG-17A(Multi1)/hier.him");
    Property.set(class1, "PaintScheme_ro", new PaintSchemeFMPar1956());
    Property.set(class1, "meshName_hu", "3DO/Plane/MiG-17A(Multi1)/hier.him");
    Property.set(class1, "PaintScheme_hu", new PaintSchemeFMPar1956());
    Property.set(class1, "meshName", "3DO/Plane/MiG-17A(Multi1)/hier.him");
    Property.set(class1, "PaintScheme", new PaintSchemeFMPar06());
    Property.set(class1, "yearService", 1952.11F);
    Property.set(class1, "yearExpired", 1960.3F);
    Property.set(class1, "FlightModel", "FlightModels/MiG-17.fmd");
    Property.set(class1, "cockpitClass", new Class[]{
              com.maddox.il2.objects.air.CockpitMig_17.class
            });
    Property.set(class1, "LOSElevation", 0.725F);
    com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[]{
            1, 0, 0, 9, 9, 9, 9, 9, 9, 2,
            2, 2, 2, 2, 2, 2, 2, 9, 9, 2,
            2, 2, 2, 9, 9, 9, 9, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 9,
            9, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 9, 9, 3, 3, 3, 3, 3,
            3, 3, 3
          });
  com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[]{
            "_CANNON01", "_CANNON02", "_CANNON03", "_ExternalDev03", "_ExternalDev04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev05", "_ExternalDev06", "_ExternalRock01",
            "_ExternalRock01", "_ExternalRock02", "_ExternalRock02", "_ExternalRock03", "_ExternalRock03", "_ExternalRock04", "_ExternalRock04", "_ExternalDev07", "_ExternalDev08", "_ExternalRock05",
            "_ExternalRock05", "_ExternalRock06", "_ExternalRock06", "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09",
            "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", "_ExternalRock19",
            "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalRock23", "_ExternalRock24", "_ExternalRock25", "_ExternalRock26", "_ExternalRock27", "_ExternalRock28", "_ExternalRock29",
            "_ExternalRock30", "_ExternalRock31", "_ExternalRock32", "_ExternalRock33", "_ExternalRock34", "_ExternalRock35", "_ExternalRock36", "_ExternalRock37", "_ExternalRock38", "_ExternalDev13",
            "_ExternalDev14", "_ExternalRock39", "_ExternalRock40", "_ExternalRock41", "_ExternalRock42", "_ExternalRock43", "_ExternalRock44", "_ExternalRock45", "_ExternalRock46", "_ExternalRock47",
            "_ExternalRock48", "_ExternalRock49", "_ExternalRock50", "_ExternalRock51", "_ExternalRock52", "_ExternalRock53", "_ExternalRock54", "_ExternalRock55", "_ExternalRock56", "_ExternalRock57",
            "_ExternalRock58", "_ExternalRock59", "_ExternalRock60", "_ExternalRock61", "_ExternalRock62", "_ExternalRock63", "_ExternalRock64", "_ExternalRock65", "_ExternalRock66", "_ExternalRock67",
            "_ExternalRock68", "_ExternalRock69", "_ExternalRock70", "_ExternalDev15", "_ExternalDev16", "_ExternalBomb01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb02", "_ExternalBomb03",
            "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb04"
          });
  com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[]{
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null
          });
  com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xdrops", new java.lang.String[]{
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null
          });
  com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x100", new java.lang.String[]{
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, "PylonMiG17Inner 1",
            "PylonMiG17Inner 1", null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, "BombGunFAB100 1",
            null, "BombGunFAB100 1", null
          });
  com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x100+2xdrops", new java.lang.String[]{
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, "PylonMiG17Inner 1",
            "PylonMiG17Inner 1", null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, "BombGunFAB100 1",
            null, "BombGunFAB100 1", null
          });
  com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x100+2x250m46", new java.lang.String[]{
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, "PylonMiG17Inner 1",
            "PylonMiG17Inner 1", null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, "PylonMiG15 1", "PylonMiG15 1", "BombGunFAB250m46 1", null, "BombGunFAB250m46 1", null, "BombGunFAB100 1",
            null, "BombGunFAB100 1", null
          });
  com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4x100", new java.lang.String[]{
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, "PylonMiG17Inner 1",
            "PylonMiG17Inner 1", null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, "PylonMiG15 1", "PylonMiG15 1", "BombGunFAB100 1", null, "BombGunFAB100 1", null, "BombGunFAB100 1",
            null, "BombGunFAB100 1", null
          });
  com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250m46", new java.lang.String[]{
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, "PylonMiG15 1", "PylonMiG15 1", "BombGunFAB250m46 1", null, "BombGunFAB250m46 1", null, null,
            null, null, null
          });
  com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xMARS2", new java.lang.String[]{
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, "PylonMARS2 1",
            "PylonMARS2 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", null, null, null, null, null, null, null,
            null, null, null
          });
  com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xMARS2+2xdrops", new java.lang.String[]{
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, "PylonMARS2 1",
            "PylonMARS2 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", null, null, null, null, null, null, null,
            null, null, null
          });
  com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xORO57", new java.lang.String[]{
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, "PylonORO57 1", "PylonORO57 1", null, null, "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null
          });
  com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xORO57+2xdrops", new java.lang.String[]{
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, "PylonORO57 1", "PylonORO57 1", null, null, "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null
          });
  com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xORO57", new java.lang.String[]{
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, "PylonORO57 1", "PylonORO57 1", "PylonORO57 1", "PylonORO57 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null
          });
  com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xORO57+2xdrops", new java.lang.String[]{
            "MGunN37ki 40", "MGunNR23ki 80", "MGunNR23ki 80", "FTGunL 1", "FTGunR 1", null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, "PylonORO57 1", "PylonORO57 1", "PylonORO57 1", "PylonORO57 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1",
            "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", "RocketGunS5 1", null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null
          });
  com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[]{
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null,
            null, null, null
          });
  }
}