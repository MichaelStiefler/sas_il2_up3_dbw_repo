package com.maddox.il2.objects.air;

import com.maddox.il2.ai.*;
import com.maddox.il2.ai.air.*;
import com.maddox.il2.engine.*;
import com.maddox.il2.fm.*;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.*;
import java.io.IOException;

public class F9F8 extends F9F_Cougar
    implements  TypeDockable
{

	  private Actor queen_last;
	  private long queen_time;
	  private boolean bNeedSetup;
	  private long dtime;
	  private Actor target_;
	  private Actor queen_;
	  private int dockport_;

    public F9F8()
    {
    }
   
    public void update(float f) {
      if (bNeedSetup) {
        checkAsDrone();
      }
      int i = aircIndex();
      if (super.FM instanceof Maneuver) {
        if (typeDockableIsDocked()) {
          if (!(super.FM instanceof RealFlightModel) || !((RealFlightModel) super.FM).isRealMode()) {
            ((Maneuver) super.FM).unblock();
            ((Maneuver) super.FM).set_maneuver(48);
            for (int j = 0; j < i; j++) {
              ((Maneuver) super.FM).push(48);
            }

            if (((FlightModelMain) (super.FM)).AP.way.curr().Action != 3) {
              ((FlightModelMain) ((Maneuver) super.FM)).AP.way.setCur(((FlightModelMain) (((SndAircraft) ((Aircraft) queen_)).FM)).AP.way.Cur());
            }
            ((Pilot) super.FM).setDumbTime(3000L);
          }
          if (((FlightModelMain) (super.FM)).M.fuel < ((FlightModelMain) (super.FM)).M.maxFuel) {
            ((FlightModelMain) (super.FM)).M.fuel += 20F * f;
          }
        } else if (!(super.FM instanceof RealFlightModel) || !((RealFlightModel) super.FM).isRealMode()) {
          if (FM.CT.GearControl == 0.0F && ((FlightModelMain) (super.FM)).EI.engines[0].getStage() == 0) {
            ((FlightModelMain) (super.FM)).EI.setEngineRunning();
          }
          if (dtime > 0L && ((Maneuver) super.FM).Group != null) {
            ((Maneuver) super.FM).Group.leaderGroup = null;
            ((Maneuver) super.FM).set_maneuver(22);
            ((Pilot) super.FM).setDumbTime(3000L);
            if (Time.current() > dtime + 3000L) {
              dtime = -1L;
              ((Maneuver) super.FM).clear_stack();
              ((Maneuver) super.FM).set_maneuver(0);
              ((Pilot) super.FM).setDumbTime(0L);
            }
          } else if (((FlightModelMain) (super.FM)).AP.way.curr().Action == 0) {
            Maneuver maneuver = (Maneuver) super.FM;
            if (maneuver.Group != null && maneuver.Group.airc[0] == this && maneuver.Group.clientGroup != null) {
              maneuver.Group.setGroupTask(2);
            }
          }
        }
      }
      super.update(f);
    }

    public void msgCollisionRequest(Actor actor, boolean aflag[]) {
      super.msgCollisionRequest(actor, aflag);
      if (queen_last != null && queen_last == actor && (queen_time == 0L || Time.current() < queen_time + 5000L)) {
        aflag[0] = false;
      } else {
        aflag[0] = true;
      }
    }

    public void missionStarting() {
      checkAsDrone();
    }

    private void checkAsDrone() {
      if (target_ == null) {
        if (((FlightModelMain) (super.FM)).AP.way.curr().getTarget() == null) {
          ((FlightModelMain) (super.FM)).AP.way.next();
        }
        target_ = ((FlightModelMain) (super.FM)).AP.way.curr().getTarget();
        if (Actor.isValid(target_) && (target_ instanceof Wing)) {
          Wing wing = (Wing) target_;
          int i = aircIndex();
          if (Actor.isValid(wing.airc[i / 2])) {
            target_ = wing.airc[i / 2];
          } else {
            target_ = null;
          }
        }
      }
      if (Actor.isValid(target_) && (target_ instanceof TypeTankerDrogue)) {
        queen_last = target_;
        queen_time = Time.current();
        if (isNetMaster()) {
          ((TypeDockable) target_).typeDockableRequestAttach(this, aircIndex() % 2, true);
        }
      }
      bNeedSetup = false;
      target_ = null;
    }

    public int typeDockableGetDockport() {
      if (typeDockableIsDocked()) {
        return dockport_;
      } else {
        return -1;
      }
    }

    public Actor typeDockableGetQueen() {
      return queen_;
    }

    public boolean typeDockableIsDocked() {
      return Actor.isValid(queen_);
    }

    public void typeDockableAttemptAttach() {
      if (((FlightModelMain) (super.FM)).AS.isMaster() && !typeDockableIsDocked()) {
        Aircraft aircraft = War.getNearestFriend(this);
        if (aircraft instanceof TypeTankerDrogue) {
          ((TypeDockable) aircraft).typeDockableRequestAttach(this);
        }
      }
    }

    public void typeDockableAttemptDetach() {
      if (((FlightModelMain) (super.FM)).AS.isMaster() && typeDockableIsDocked() && Actor.isValid(queen_)) {
        ((TypeDockable) queen_).typeDockableRequestDetach(this);
      }
    }

    public void typeDockableRequestAttach(Actor actor1) {
    }

    public void typeDockableRequestDetach(Actor actor1) {
    }

    public void typeDockableRequestAttach(Actor actor1, int j, boolean flag1) {
    }

    public void typeDockableRequestDetach(Actor actor1, int j, boolean flag1) {
    }

    public void typeDockableDoAttachToDrone(Actor actor1, int j) {
    }

    public void typeDockableDoDetachFromDrone(int j) {
    }

    public void typeDockableDoAttachToQueen(Actor actor, int i) {

      queen_ = actor;
      dockport_ = i;
      queen_last = queen_;
      queen_time = 0L;
      ((FlightModelMain) (super.FM)).EI.setEngineRunning();
      ((FlightModelMain) (super.FM)).CT.setGearAirborne();
      moveGear(0.0F);
      com.maddox.il2.fm.FlightModel flightmodel = ((SndAircraft) ((Aircraft) queen_)).FM;
      if (aircIndex() == 0 && (super.FM instanceof Maneuver) && (flightmodel instanceof Maneuver)) {
        Maneuver maneuver = (Maneuver) flightmodel;
        Maneuver maneuver1 = (Maneuver) super.FM;
        if (maneuver.Group != null && maneuver1.Group != null && maneuver1.Group.numInGroup(this) == maneuver1.Group.nOfAirc - 1) {
          AirGroup airgroup = new AirGroup(maneuver1.Group);
          maneuver1.Group.delAircraft(this);
          airgroup.addAircraft(this);
          airgroup.attachGroup(maneuver.Group);
          airgroup.rejoinGroup = null;
          airgroup.leaderGroup = null;
          airgroup.clientGroup = maneuver.Group;
        }
      }
    }

    public void typeDockableDoDetachFromQueen(int i) {
      if (dockport_ == i) {
        queen_last = queen_;
        queen_time = Time.current();
        queen_ = null;
        dockport_ = 0;
      }
    }

    public void typeDockableReplicateToNet(NetMsgGuaranted netmsgguaranted)
            throws IOException {
      if (typeDockableIsDocked()) {
        netmsgguaranted.writeByte(1);
        com.maddox.il2.engine.ActorNet actornet = null;
        if (Actor.isValid(queen_)) {
          actornet = queen_.net;
          if (actornet.countNoMirrors() > 0) {
            actornet = null;
          }
        }
        netmsgguaranted.writeByte(dockport_);
        netmsgguaranted.writeNetObj(actornet);
      } else {
        netmsgguaranted.writeByte(0);
      }
    }

    public void typeDockableReplicateFromNet(NetMsgInput netmsginput)
            throws IOException {
      if (netmsginput.readByte() == 1) {
        dockport_ = netmsginput.readByte();
        NetObj netobj = netmsginput.readNetObj();
        if (netobj != null) {
          Actor actor = (Actor) netobj.superObj();
          ((TypeDockable) actor).typeDockableDoAttachToDrone(this, dockport_);
        }
      }
    }

    public void rareAction(float f, boolean flag) {
      super.rareAction(f, flag);
      if ((super.FM instanceof RealFlightModel) && ((RealFlightModel) super.FM).isRealMode() || !flag || !(super.FM instanceof Pilot)) {
        return;
      }
      if (flag && ((FlightModelMain) (super.FM)).AP.way.curr().Action == 3 && typeDockableIsDocked() && Math.abs(((FlightModelMain) (((SndAircraft) ((Aircraft) queen_)).FM)).Or.getKren()) < 3F) {
        if (super.FM.isPlayers()) {
          if ((super.FM instanceof RealFlightModel) && !((RealFlightModel) super.FM).isRealMode()) {
            typeDockableAttemptDetach();
            ((Maneuver) super.FM).set_maneuver(22);
            ((Maneuver) super.FM).setCheckStrike(false);
            ((FlightModelMain) (super.FM)).Vwld.z -= 5D;
            dtime = Time.current();
          }
        } else {
          typeDockableAttemptDetach();
          ((Maneuver) super.FM).set_maneuver(22);
          ((Maneuver) super.FM).setCheckStrike(false);
          ((FlightModelMain) (super.FM)).Vwld.z -= 5D;
          dtime = Time.current();
        }
      }
    }

    static 
    {
        Class class1 = com.maddox.il2.objects.air.F9F8.class;
        new NetAircraft.SPAWN(class1);
        Property.set(class1, "iconFar_shortClassName", "F9F8");
        Property.set(class1, "meshName", "3DO/Plane/F9F8/hier.him");
        Property.set(class1, "PaintScheme", new PaintSchemeFMPar06());
        Property.set(class1, "yearService", 1946.9F);
        Property.set(class1, "yearExpired", 1955.3F);
        Property.set(class1, "FlightModel", "FlightModels/F9F8.fmd:Cougars");
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
        		"FuelTankGun_Cougar150gal 1","FuelTankGun_Cougar150gal 1", null, null, null, null, null, null, null, null, 
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
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "04xMk81_Bomb", new java.lang.String[]{
        		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200", null, null,"PylonAero15A 1","PylonAero15A 1","PylonAero15A 1","PylonAero15A 1", 
        		null, null, null, null,"BombGunMk81 1","BombGunMk81 1","BombGunMk81 1","BombGunMk81 1", null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null
        });   
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "04xMk81_Bomb_2xTank", new java.lang.String[]{
        		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","PylonAero65A 1","PylonAero65A 1","PylonAero15A 1","PylonAero15A 1","PylonAero15A 1","PylonAero15A 1",
        		"FuelTankGun_Cougar150gal 1","FuelTankGun_Cougar150gal 1", null, null,"BombGunMk81 1","BombGunMk81 1","BombGunMk81 1","BombGunMk81 1", null, null, 
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
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "04xMk82_Bomb", new java.lang.String[]{
        		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","PylonAero65A 1","PylonAero65A 1",null, null,"PylonAero15A 1","PylonAero15A 1",  
        		null, null,"BombGunMk82 1","BombGunMk82 1", null, null,"BombGunMk82 1","BombGunMk82 1", null, null, 
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null, 
        		null
        });  
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "04xMk82_Bomb_2xTank", new java.lang.String[]{
        		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","PylonAero65A 1","PylonAero65A 1","PylonAero15A 1","PylonAero15A 1", null, null, 
        		"FuelTankGun_Cougar150gal 1","FuelTankGun_Cougar150gal 1", null, null,"BombGunMk82 1","BombGunMk82 1","BombGunMk82 1","BombGunMk82 1", null, null, 
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null, 
        		null
        }); 
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02xMk82_Bomb+04xMk81_Bomb", new java.lang.String[]{
        		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","PylonAero65A 1","PylonAero65A 1","PylonAero15A 1","PylonAero15A 1", null, null, 
        		null, null,"BombGunMk82 1","BombGunMk82 1","BombGunMk81 1","BombGunMk81 1","BombGunMk81 1","BombGunMk81 1", null, null, 
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
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02xM117_Bomb+04xMk81_Bomb", new java.lang.String[]{
        		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","PylonAero65A 1","PylonAero65A 1","PylonAero15A 1","PylonAero15A 1","PylonAero15A 1","PylonAero15A 1", 
        		null, null,"BombGun750lbsM117 1","BombGun750lbsM117 1","BombGunMk81 1","BombGunMk81 1","BombGunMk81 1","BombGunMk81 1", null, null, 
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
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "01xMk7_Nuke", new java.lang.String[]{
        		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","PylonAero22A 1","PylonAero65A 1", null, null, null, null, 
        		null, "FuelTankGun_Cougar150gal 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null,
        		null, null, null, null, null, null, null, null, null, null, 
        		"BombGunMk7 1"
        }); 
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02xLAU10", new java.lang.String[]{
        		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","PylonAero65A 1","PylonAero65A 1", null, null, null, null, 
        		null, null, null, null, null, null, null, null,"Pylon_Zuni 1","Pylon_Zuni 1",
        		"RocketGun5inchZuni 1","RocketGun5inchZuni 1","RocketGun5inchZuni 1","RocketGun5inchZuni 1","RocketGun5inchZuni 1","RocketGun5inchZuni 1","RocketGun5inchZuni 1","RocketGun5inchZuni 1", null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null
        }); 
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02xLAU10_04xMk81_Bomb", new java.lang.String[]{
        		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","PylonAero65A 1","PylonAero65A 1","PylonAero15A 1","PylonAero15A 1","PylonAero15A 1","PylonAero15A 1", 
        		null, null, null, null,"BombGunMk81 1","BombGunMk81 1","BombGunMk81 1","BombGunMk81 1","Pylon_Zuni 1","Pylon_Zuni 1",
        		"RocketGun5inchZuni 1","RocketGun5inchZuni 1","RocketGun5inchZuni 1","RocketGun5inchZuni 1","RocketGun5inchZuni 1","RocketGun5inchZuni 1","RocketGun5inchZuni 1","RocketGun5inchZuni 1", null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null
        }); 
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4x_AIM9B", new java.lang.String[]{
        		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200", null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null,"PylonF86_Sidewinder 1","PylonF86_Sidewinder 1",
        		"PylonF86_Sidewinder 1","PylonF86_Sidewinder 1","RocketGunAIM9B 1"," RocketGunNull 1","RocketGunAIM9B 1"," RocketGunNull 1","RocketGunAIM9B 1"," RocketGunNull 1","RocketGunAIM9B 1"," RocketGunNull 1", 
        		null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4x_AIM9B_2xTank", new java.lang.String[]{
        		"MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","MGunHispanoMkIk 200","PylonAero65A 1","PylonAero65A 1", null, null, null, null, 
        		"FuelTankGun_Cougar150gal 1","FuelTankGun_Cougar150gal 1", null, null, null, null, null, null, null, null, 
        		null, null, null, null, null, null, null, null,"PylonF86_Sidewinder 1","PylonF86_Sidewinder 1",
        		"PylonF86_Sidewinder 1","PylonF86_Sidewinder 1","RocketGunAIM9B 1"," RocketGunNull 1","RocketGunAIM9B 1"," RocketGunNull 1","RocketGunAIM9B 1"," RocketGunNull 1","RocketGunAIM9B 1"," RocketGunNull 1", 
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
