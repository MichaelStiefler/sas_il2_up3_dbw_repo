package com.maddox.il2.engine;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Cmd;
import com.maddox.rts.NetChannel;
import com.maddox.rts.Spawn;
import java.io.PrintStream;

public class ActorSpawnArg
{
  public String name;
  public boolean armyExist;
  public int army;
  public Point3d point;
  public Orient orient;
  public Actor baseActor;
  public String hookName;
  public Actor ownerActor;
  public String iconName;
  public String meshName;
  public String matName;
  public String paramFileName;
  public boolean sizeExist;
  public float size;
  public boolean timeLenExist;
  public float timeLen;
  public boolean timeNativeExist;
  public boolean timeNative;
  public boolean typeExist;
  public int type;
  public String path;
  public String target;
  public String acoustic;
  public String sound;
  public String preload;
  public Color3f color3f;
  public float[] light;
  public boolean Z0Exist;
  public float Z0;
  public String FM;
  public int FM_Type;
  public NetChannel netChannel;
  public int netIdRemote;
  public String weapons;
  public float fuel;
  public Vector3d speed;
  public int skill;
  public boolean bPlayer;
  public boolean bornPlaceExist;
  public int bornPlace;
  public boolean stayPlaceExist;
  public int stayPlace;
  public boolean bNumberOn = true;
  public String rawData;
  public String country;
  private static Loc lempty = new Loc();
  private static ActorPosMoveInit apos = new ActorPosMoveInit();

  public ActorSpawnArg()
  {
  }

  public ActorSpawnArg(ActorSpawnArg paramActorSpawnArg)
  {
    this.name = paramActorSpawnArg.name;
    this.armyExist = paramActorSpawnArg.armyExist;
    this.army = paramActorSpawnArg.army;
    this.point = paramActorSpawnArg.point;
    this.orient = paramActorSpawnArg.orient;
    this.baseActor = paramActorSpawnArg.baseActor;
    this.hookName = paramActorSpawnArg.hookName;
    this.ownerActor = paramActorSpawnArg.ownerActor;
    this.iconName = paramActorSpawnArg.iconName;
    this.meshName = paramActorSpawnArg.meshName;
    this.matName = paramActorSpawnArg.matName;
    this.paramFileName = paramActorSpawnArg.paramFileName;
    this.sizeExist = paramActorSpawnArg.sizeExist;
    this.size = paramActorSpawnArg.size;
    this.timeLenExist = paramActorSpawnArg.timeLenExist;
    this.timeLen = paramActorSpawnArg.timeLen;
    this.timeNativeExist = paramActorSpawnArg.timeNativeExist;
    this.timeNative = paramActorSpawnArg.timeNative;
    this.typeExist = paramActorSpawnArg.typeExist;
    this.type = paramActorSpawnArg.type;
    this.path = paramActorSpawnArg.path;
    this.target = paramActorSpawnArg.target;
    this.acoustic = paramActorSpawnArg.acoustic;
    this.sound = paramActorSpawnArg.sound;
    this.preload = paramActorSpawnArg.preload;
    this.color3f = paramActorSpawnArg.color3f;
    this.light = paramActorSpawnArg.light;
    this.Z0 = paramActorSpawnArg.Z0;
    this.Z0Exist = paramActorSpawnArg.Z0Exist;
    this.FM = paramActorSpawnArg.FM;
    this.FM_Type = paramActorSpawnArg.FM_Type;
    this.netChannel = paramActorSpawnArg.netChannel;
    this.netIdRemote = paramActorSpawnArg.netIdRemote;
    this.weapons = paramActorSpawnArg.weapons;
    this.fuel = paramActorSpawnArg.fuel;
    this.speed = paramActorSpawnArg.speed;
    this.skill = paramActorSpawnArg.skill;
    this.bPlayer = paramActorSpawnArg.bPlayer;
    this.bornPlaceExist = paramActorSpawnArg.bornPlaceExist;
    this.bornPlace = paramActorSpawnArg.bornPlace;
    this.stayPlaceExist = paramActorSpawnArg.stayPlaceExist;
    this.stayPlace = paramActorSpawnArg.stayPlace;
    this.bNumberOn = paramActorSpawnArg.bNumberOn;
    this.rawData = paramActorSpawnArg.rawData;
    this.country = paramActorSpawnArg.country;
  }

  public void clear() {
    this.name = null;
    this.armyExist = false;
    this.army = 0;
    this.point = null;
    this.orient = null;
    this.baseActor = null;
    this.hookName = null;
    this.ownerActor = null;
    this.iconName = null;
    this.meshName = null;
    this.matName = null;
    this.paramFileName = null;
    this.sizeExist = false;
    this.timeLenExist = false;
    this.timeNativeExist = false;
    this.typeExist = false;
    this.path = null;
    this.target = null;
    this.acoustic = null;
    this.sound = null;
    this.preload = null;
    this.color3f = null;
    this.light = null;
    this.Z0 = 0.0F;
    this.Z0Exist = false;
    this.FM = null;
    this.FM_Type = 0;
    this.netChannel = null;
    this.netIdRemote = 0;
    this.weapons = null;
    this.fuel = 100.0F;
    this.speed = null;
    this.skill = 1;
    this.bPlayer = false;
    this.bornPlaceExist = false;
    this.bornPlace = -1;
    this.stayPlaceExist = false;
    this.stayPlace = -1;
    this.bNumberOn = true;
    this.rawData = null;
    this.country = null;
  }

  public Actor set(Actor paramActor)
  {
    if (this.name != null)
      paramActor.setName(this.name);
    if (this.armyExist)
      paramActor.setArmy(this.army);
    if (this.baseActor != null) {
      Hook localHook = null;
      if (this.hookName != null)
        localHook = this.baseActor.findHook(this.hookName);
      paramActor.pos.setBase(this.baseActor, localHook, false);
      if ((this.point != null) && (this.orient != null)) paramActor.pos.setRel(this.point, this.orient);
      else if (this.point != null) paramActor.pos.setRel(this.point);
      else if (this.orient != null) paramActor.pos.setRel(this.orient); else
        paramActor.pos.setRel(lempty);
      paramActor.pos.reset();
    } else {
      if ((this.point != null) && (this.orient != null)) paramActor.pos.setAbs(this.point, this.orient);
      else if (this.point != null) paramActor.pos.setAbs(this.point);
      else if (this.orient != null) paramActor.pos.setAbs(this.orient); else
        paramActor.pos.setAbs(lempty);
      paramActor.pos.reset();
    }
    if (this.ownerActor != null)
      paramActor.setOwner(this.ownerActor);
    if (this.speed != null) {
      paramActor.setSpeed(this.speed);
      if (paramActor.pos != null)
        paramActor.pos.reset();
    }
    if (this.iconName != null) {
      try { paramActor.icon = Mat.New(this.iconName); } catch (Exception localException) {
      }if (paramActor.icon == null)
        ERR_SOFT("Icon : " + this.iconName + " not loaded");
    }
    else {
      IconDraw.create(paramActor);
    }
    if ((this.bPlayer) && ((paramActor instanceof Aircraft))) {
      World.setPlayerAircraft((Aircraft)paramActor);
    }
    return paramActor;
  }

  public Actor setStationary(Actor paramActor)
  {
    if (this.name != null) {
      paramActor.setName(this.name);
    }
    if (!this.armyExist)
      throw new ActorException(paramActor.getClass().getName() + ": missing army");
    paramActor.setArmy(this.army);

    if ((this.point == null) || (this.orient == null))
      throw new ActorException(paramActor.getClass().getName() + ": missing pos or orient");
    paramActor.pos.setAbs(this.point, this.orient);
    paramActor.pos.reset();

    if (this.iconName != null) {
      try { paramActor.icon = Mat.New(this.iconName); } catch (Exception localException) {
      }if (paramActor.icon == null)
        ERR_SOFT(paramActor.getClass().getName() + ": icon '" + this.iconName + "' not loaded");
    }
    else {
      IconDraw.create(paramActor);
    }

    return paramActor;
  }

  public Actor setStationaryNoIcon(Actor paramActor)
  {
    if (this.name != null) {
      paramActor.setName(this.name);
    }
    if (!this.armyExist)
      throw new ActorException(paramActor.getClass().getName() + ": missing army");
    paramActor.setArmy(this.army);

    if ((this.point == null) || (this.orient == null))
      throw new ActorException(paramActor.getClass().getName() + ": missing pos or orient");
    paramActor.pos.setAbs(this.point, this.orient);
    paramActor.pos.reset();

    return paramActor;
  }

  public Actor setNameOwnerIcon(Actor paramActor)
  {
    if (this.name != null)
      paramActor.setName(this.name);
    if (this.armyExist)
      paramActor.setArmy(this.army);
    if (this.ownerActor != null)
      paramActor.setOwner(this.ownerActor);
    if (this.iconName != null) {
      try { paramActor.icon = Mat.New(this.iconName); } catch (Exception localException) {
      }if (paramActor.icon == null)
        ERR_SOFT("Icon : " + this.iconName + " not loaded");
    }
    else {
      IconDraw.create(paramActor);
    }
    return paramActor;
  }

  public Loc getAbsLoc()
  {
    if (this.baseActor != null) {
      localObject = null;
      if (this.hookName != null)
        localObject = this.baseActor.findHook(this.hookName);
      apos.setBase(this.baseActor, (Hook)localObject, false);
      if ((this.point != null) && (this.orient != null)) apos.setRel(this.point, this.orient);
      else if (this.point != null) apos.setRel(this.point);
      else if (this.orient != null) apos.setRel(this.orient); else
        apos.setRel(lempty);
    }
    else if ((this.point != null) && (this.orient != null)) { apos.setAbs(this.point, this.orient);
    } else if (this.point != null) { apos.setAbs(this.point);
    } else if (this.orient != null) { apos.setAbs(this.orient); } else {
      apos.setAbs(lempty);
    }
    Object localObject = new Loc();
    apos.getAbs((Loc)localObject);
    apos.setBase(null, null, false);
    return (Loc)localObject;
  }

  public void ERR_HARD(String paramString)
  {
    if (Cmd.ERR_HARD)
      System.err.println("spawn " + Spawn.getLastClassName() + ": " + paramString);
  }

  public void ERR_SOFT(String paramString)
  {
    if (Cmd.ERR_SOFT)
      System.err.println("spawn " + Spawn.getLastClassName() + ": " + paramString);
  }

  public boolean isNoExistHARD(Object paramObject, String paramString)
  {
    if (paramObject == null) { ERR_HARD(paramString); return true; }
    return false;
  }

  public void INFO_HARD(String paramString)
  {
    if (Cmd.INFO_HARD)
      System.out.println(paramString);
  }

  public void INFO_SOFT(String paramString)
  {
    if (Cmd.INFO_SOFT)
      System.out.println(paramString);
  }
}