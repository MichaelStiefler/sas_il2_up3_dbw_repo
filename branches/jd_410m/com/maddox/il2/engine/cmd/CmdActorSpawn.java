package com.maddox.il2.engine.cmd;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Property;
import com.maddox.rts.Spawn;
import com.maddox.util.NumberTokenizer;
import com.maddox.util.QuoteTokenizer;
import java.util.HashMap;
import java.util.TreeMap;

public class CmdActorSpawn extends Cmd
{
  public static final String EMPTY = "";
  public static final String NAME = "NAME";
  public static final String OVR = "OVR";
  public static final String ARMY = "ARMY";
  public static final String POSP = "POSP";
  public static final String POSO = "POSO";
  public static final String BASED = "BASED";
  public static final String BASE = "BASE";
  public static final String HOOK = "HOOK";
  public static final String OWNER = "OWNER";
  public static final String ICON = "ICON";
  public static final String MESH = "MESH";
  public static final String MAT = "MAT";
  public static final String PARAMFILE = "PARAMFILE";
  public static final String SIZE = "SIZE";
  public static final String TIMELEN = "TIMELEN";
  public static final String TIMENATIVE = "TIMENATIVE";
  public static final String TYPE = "TYPE";
  public static final String PATH = "PATH";
  public static final String TARGET = "TARGET";
  public static final String ACOUSTIC = "ACOUSTIC";
  public static final String SOUND = "SOUND";
  public static final String PRELOAD = "PRELOAD";
  public static final String COLOR = "COLOR";
  public static final String LIGHT = "LIGHT";
  public static final String Z0 = "Z0";
  public static final String FM = "FM";
  public static final String FM_Type = "FM_Type";
  public static final String WEAPONS = "WEAPONS";
  public static final String FUEL = "FUEL";
  public static final String SPEED = "SPEED";
  public static final String SKILL = "SKILL";
  public static final String PLAYER = "PLAYER";
  public static final String BORNPLACE = "BORNPLACE";
  public static final String STAYPLACE = "STAYPLACE";
  public static final String NUMBEROFF = "NUMBEROFF";
  public static final String RAWDATA = "RAWDATA";
  private boolean nameExist;
  private boolean ovrExist;
  private Actor basedActor;
  private QuoteTokenizer tokens;
  private String word;
  private ActorSpawnArg sarg = new ActorSpawnArg();
  private Point3d P = new Point3d();
  private Orient O = new Orient();
  private float[] light = new float[2];
  private Color3f color3f = new Color3f();
  private Vector3d speed3d = new Vector3d();
  private boolean bExit;

  public boolean isRawFormat()
  {
    return true;
  }

  protected boolean paramContainsKey(String paramString)
  {
    return this.param.containsKey(paramString);
  }
  protected void ERR_HARD(String paramString) { super.ERR_HARD(paramString); }

  public Object exec(CmdEnv paramCmdEnv, String paramString)
  {
    ActorSpawn localActorSpawn = null;
    this.nameExist = false;
    this.ovrExist = false;
    this.basedActor = null;
    this.bExit = false;
    this.sarg.clear();

    this.tokens = new QuoteTokenizer(paramString);
    this.word = null;
    Object localObject2;
    while ((this.tokens.hasMoreTokens()) || (this.word != null)) {
      if (this.word == null)
        this.word = this.tokens.nextToken();
      localObject1 = (Token)this.param.get(this.word);
      if (localObject1 != null) {
        if (localActorSpawn == null) {
          ERR_HARD("class of actor NOT present");
          this.basedActor = null;
          return null;
        }
        ((Token)localObject1).parse();
        if (this.bExit) {
          this.basedActor = null;
          return null;
        }
      }
      else
      {
        localObject2 = Spawn.get_WithSoftClass(this.word, false);
        if (localObject2 == null)
          localObject2 = Spawn.get_WithSoftClass("com.maddox.il2." + this.word, false);
        if (localObject2 == null)
          localObject2 = Spawn.get_WithSoftClass("com.maddox.il2.objects." + this.word, false);
        if (localActorSpawn == null) {
          if (localObject2 == null) {
            ERR_HARD("class " + this.word + " NOT found or NOT registered in Spawn database");
            this.basedActor = null;
            return null;
          }
          if (!(localObject2 instanceof ActorSpawn)) {
            ERR_HARD("class " + this.word + " NOT contains ActorSpawn interface");
            this.basedActor = null;
            return null;
          }
          localActorSpawn = (ActorSpawn)localObject2;
        }
        this.word = null;
      }
    }

    if (this.nameExist) {
      localObject1 = Actor.getByName(this.sarg.name);
      if (localObject1 != null) {
        if (this.ovrExist) {
          ((Actor)localObject1).destroy();
        } else {
          ERR_HARD("actor: " + this.sarg.name + " alredy exist");
          this.basedActor = null;
          return null;
        }
      }
    }

    if (this.basedActor != null)
    {
      if (this.sarg.baseActor != null) {
        localObject1 = this.basedActor.pos.getRelPoint();
        localObject2 = this.basedActor.pos.getRelOrient();
      } else {
        localObject1 = this.basedActor.pos.getAbsPoint();
        localObject2 = this.basedActor.pos.getAbsOrient();
      }
      if (this.sarg.point != null) { this.sarg.point.add((Tuple3d)localObject1); } else {
        this.P.set((Tuple3d)localObject1); this.sarg.point = this.P;
      }if (this.sarg.orient != null) { this.sarg.orient.add((Orient)localObject2); } else {
        this.O.set((Orient)localObject2); this.sarg.orient = this.O;
      }
    }
    this.basedActor = null;

    Object localObject1 = localActorSpawn.actorSpawn(this.sarg);
    if ((localObject1 != null) && (Config.isAppEditor())) {
      Property.set(localObject1, "spawn", paramString);
      Property.set(localObject1, "spawn arg", new ActorSpawnArg(this.sarg));
    }

    return localObject1;
  }

  public CmdActorSpawn()
  {
    this.param.put("NAME", new Token() {
      public void parse() { CmdActorSpawn.this.sarg.name = getStr(); CmdActorSpawn.access$302(CmdActorSpawn.this, true);
      }
    });
    this.param.put("OVR", new Token() {
      public void parse() { getStr(); CmdActorSpawn.access$402(CmdActorSpawn.this, true);
      }
    });
    this.param.put("ARMY", new Token() {
      public void parse() { CmdActorSpawn.this.sarg.armyExist = true;
        String str = getStr();
        try { CmdActorSpawn.this.sarg.army = Integer.parseInt(str);
        } catch (Exception localException) {
          CmdActorSpawn.this.ERR_HARD("bad format army: " + str);
          CmdActorSpawn.access$502(CmdActorSpawn.this, true);
        }
      }
    });
    this.param.put("POSP", new Token() {
      public void parse() { String str = getStr();
        CmdActorSpawn.this.sarg.point = CmdActorSpawn.this.P;
        CmdActorSpawn.this.sarg.point.set(0.0D, 0.0D, 0.0D);
        if (str != "") {
          NumberTokenizer localNumberTokenizer = new NumberTokenizer(str, " ");
          try {
            if (localNumberTokenizer.hasMoreTokens()) CmdActorSpawn.this.sarg.point.x = localNumberTokenizer.nextDouble();
            if (localNumberTokenizer.hasMoreTokens()) CmdActorSpawn.this.sarg.point.y = localNumberTokenizer.nextDouble();
            if (localNumberTokenizer.hasMoreTokens()) CmdActorSpawn.this.sarg.point.z = localNumberTokenizer.nextDouble(); 
          }
          catch (Exception localException) {
            CmdActorSpawn.this.ERR_HARD("bad format position: " + str);
            CmdActorSpawn.access$502(CmdActorSpawn.this, true);
          }
        }
      }
    });
    this.param.put("POSO", new Token() {
      public void parse() { String str = getStr();
        CmdActorSpawn.this.sarg.orient = CmdActorSpawn.this.O;
        CmdActorSpawn.this.sarg.orient.set(0.0F, 0.0F, 0.0F);
        if (str != "") {
          NumberTokenizer localNumberTokenizer = new NumberTokenizer(str, " ");
          try {
            float f1 = 0.0F; float f2 = 0.0F; float f3 = 0.0F;
            if (localNumberTokenizer.hasMoreTokens()) f1 = localNumberTokenizer.nextFloat();
            if (localNumberTokenizer.hasMoreTokens()) f2 = localNumberTokenizer.nextFloat();
            if (localNumberTokenizer.hasMoreTokens()) f3 = localNumberTokenizer.nextFloat();
            CmdActorSpawn.this.sarg.orient.set(f1, f2, f3);
          } catch (Exception localException) {
            CmdActorSpawn.this.ERR_HARD("bad format orientation: " + str);
            CmdActorSpawn.access$502(CmdActorSpawn.this, true);
          }
        }
      }
    });
    this.param.put("BASED", new Token() {
      public void parse() { String str = getStr();
        CmdActorSpawn.access$802(CmdActorSpawn.this, Actor.getByName(str));
        if (CmdActorSpawn.this.basedActor == null) {
          CmdActorSpawn.this.ERR_HARD("based actor: " + str + " not found");
          CmdActorSpawn.access$502(CmdActorSpawn.this, true);
        }
      }
    });
    this.param.put("BASE", new Token() {
      public void parse() { String str = getStr();
        CmdActorSpawn.this.sarg.baseActor = Actor.getByName(str);
        if (CmdActorSpawn.this.sarg.baseActor == null) {
          CmdActorSpawn.this.ERR_HARD("base actor: " + str + " not found");
          CmdActorSpawn.access$502(CmdActorSpawn.this, true);
        }
      }
    });
    this.param.put("HOOK", new Token() {
      public void parse() { CmdActorSpawn.this.sarg.hookName = getStr();
      }
    });
    this.param.put("OWNER", new Token() {
      public void parse() { String str = getStr();
        CmdActorSpawn.this.sarg.ownerActor = Actor.getByName(str);
        if (CmdActorSpawn.this.sarg.ownerActor == null) {
          CmdActorSpawn.this.ERR_HARD("owner actor: " + str + " not found");
          CmdActorSpawn.access$502(CmdActorSpawn.this, true);
        }
      }
    });
    this.param.put("ICON", new Token() {
      public void parse() { CmdActorSpawn.this.sarg.iconName = getStr();
      }
    });
    this.param.put("MESH", new Token() {
      public void parse() { CmdActorSpawn.this.sarg.meshName = getStr();
      }
    });
    this.param.put("MAT", new Token() {
      public void parse() { CmdActorSpawn.this.sarg.matName = getStr();
      }
    });
    this.param.put("PARAMFILE", new Token() {
      public void parse() { CmdActorSpawn.this.sarg.paramFileName = getStr();
      }
    });
    this.param.put("SIZE", new Token() {
      public void parse() { CmdActorSpawn.this.sarg.sizeExist = true;
        String str = getStr();
        try { CmdActorSpawn.this.sarg.size = Float.parseFloat(str);
        } catch (Exception localException) {
          CmdActorSpawn.this.ERR_HARD("bad format size: " + str);
          CmdActorSpawn.access$502(CmdActorSpawn.this, true);
        }
      }
    });
    this.param.put("TIMELEN", new Token() {
      public void parse() { CmdActorSpawn.this.sarg.timeLenExist = true;
        String str = getStr();
        try { CmdActorSpawn.this.sarg.timeLen = Float.parseFloat(str);
        } catch (Exception localException) {
          CmdActorSpawn.this.ERR_HARD("bad format timeLen: " + str);
          CmdActorSpawn.access$502(CmdActorSpawn.this, true);
        }
      }
    });
    this.param.put("TIMENATIVE", new Token() {
      public void parse() { CmdActorSpawn.this.sarg.timeNativeExist = true;
        String str = getStr();
        try { CmdActorSpawn.this.sarg.timeNative = (Integer.parseInt(str) != 0);
        } catch (Exception localException) {
          CmdActorSpawn.this.ERR_HARD("bad format timeNative: " + str);
          CmdActorSpawn.access$502(CmdActorSpawn.this, true);
        }
      }
    });
    this.param.put("TYPE", new Token() {
      public void parse() { CmdActorSpawn.this.sarg.typeExist = true;
        String str = getStr();
        try { CmdActorSpawn.this.sarg.type = Integer.parseInt(str);
        } catch (Exception localException) {
          CmdActorSpawn.this.ERR_HARD("bad format type: " + str);
          CmdActorSpawn.access$502(CmdActorSpawn.this, true);
        }
      }
    });
    this.param.put("PATH", new Token() {
      public void parse() { CmdActorSpawn.this.sarg.path = getStr();
      }
    });
    this.param.put("TARGET", new Token() {
      public void parse() { CmdActorSpawn.this.sarg.target = getStr();
      }
    });
    this.param.put("ACOUSTIC", new Token() {
      public void parse() { CmdActorSpawn.this.sarg.acoustic = getStr();
      }
    });
    this.param.put("SOUND", new Token() {
      public void parse() { CmdActorSpawn.this.sarg.sound = getStr();
      }
    });
    this.param.put("PRELOAD", new Token() {
      public void parse() { CmdActorSpawn.this.sarg.preload = getStr();
      }
    });
    this.param.put("COLOR", new Token() {
      public void parse() { String str = getStr();
        CmdActorSpawn.this.sarg.color3f = CmdActorSpawn.this.color3f;
        CmdActorSpawn.this.color3f.set(1.0F, 1.0F, 1.0F);
        if (str != "") {
          NumberTokenizer localNumberTokenizer = new NumberTokenizer(str, " ");
          try {
            if (localNumberTokenizer.hasMoreTokens()) CmdActorSpawn.this.color3f.x = localNumberTokenizer.nextFloat();
            if (localNumberTokenizer.hasMoreTokens()) CmdActorSpawn.this.color3f.y = localNumberTokenizer.nextFloat();
            if (localNumberTokenizer.hasMoreTokens()) CmdActorSpawn.this.color3f.z = localNumberTokenizer.nextFloat(); 
          }
          catch (Exception localException) {
            CmdActorSpawn.this.ERR_HARD("bad format color3f: " + str);
            CmdActorSpawn.access$502(CmdActorSpawn.this, true);
          }
        }
      }
    });
    this.param.put("LIGHT", new Token() {
      public void parse() { String str = getStr();
        CmdActorSpawn.this.sarg.light = CmdActorSpawn.this.light;
        CmdActorSpawn.this.sarg.light[0] = 1.0F; CmdActorSpawn.this.sarg.light[1] = 10.0F;
        if (str != "") {
          NumberTokenizer localNumberTokenizer = new NumberTokenizer(str, " ");
          try {
            if (localNumberTokenizer.hasMoreTokens()) CmdActorSpawn.this.sarg.light[0] = localNumberTokenizer.nextFloat();
            if (localNumberTokenizer.hasMoreTokens()) CmdActorSpawn.this.sarg.light[1] = localNumberTokenizer.nextFloat(); 
          }
          catch (Exception localException) {
            CmdActorSpawn.this.ERR_HARD("bad format light: " + str);
            CmdActorSpawn.access$502(CmdActorSpawn.this, true);
          }
        }
      }
    });
    this.param.put("Z0", new Token() {
      public void parse() { CmdActorSpawn.this.sarg.Z0Exist = true;
        String str = getStr();
        try { CmdActorSpawn.this.sarg.Z0 = Float.parseFloat(str);
        } catch (Exception localException) {
          CmdActorSpawn.this.ERR_HARD("bad format Z0: " + str);
          CmdActorSpawn.access$502(CmdActorSpawn.this, true);
        }
      }
    });
    this.param.put("FM", new Token() {
      public void parse() { CmdActorSpawn.this.sarg.FM = getStr();
      }
    });
    this.param.put("FM_Type", new Token() {
      public void parse() { String str = getStr();
        try {
          CmdActorSpawn.this.sarg.FM_Type = Integer.parseInt(str);
        } catch (Exception localException) {
          CmdActorSpawn.this.ERR_HARD("bad format FM_Type: " + str);
          CmdActorSpawn.access$502(CmdActorSpawn.this, true);
        }
      }
    });
    this.param.put("WEAPONS", new Token() {
      public void parse() { CmdActorSpawn.this.sarg.weapons = getStr();
      }
    });
    this.param.put("FUEL", new Token() {
      public void parse() { String str = getStr();
        try {
          CmdActorSpawn.this.sarg.fuel = (Float.parseFloat(str) / 100.0F);
          if (CmdActorSpawn.this.sarg.fuel > 1.0F) CmdActorSpawn.this.sarg.fuel = 1.0F;
          if (CmdActorSpawn.this.sarg.fuel < 0.0F) CmdActorSpawn.this.sarg.fuel = 0.0F; 
        }
        catch (Exception localException) {
          CmdActorSpawn.this.ERR_HARD("bad format fuel: " + str);
          CmdActorSpawn.access$502(CmdActorSpawn.this, true);
        }
      }
    });
    this.param.put("SPEED", new Token() {
      public void parse() { String str = getStr();
        CmdActorSpawn.this.sarg.speed = CmdActorSpawn.this.speed3d;
        CmdActorSpawn.this.speed3d.set(0.0D, 0.0D, -1.0D);
        if (str != "") {
          NumberTokenizer localNumberTokenizer = new NumberTokenizer(str, " ");
          try {
            if (localNumberTokenizer.hasMoreTokens()) CmdActorSpawn.this.speed3d.x = localNumberTokenizer.nextFloat();
            if (localNumberTokenizer.hasMoreTokens()) CmdActorSpawn.this.speed3d.y = localNumberTokenizer.nextFloat();
            if (localNumberTokenizer.hasMoreTokens()) CmdActorSpawn.this.speed3d.z = localNumberTokenizer.nextFloat(); 
          }
          catch (Exception localException) {
            CmdActorSpawn.this.ERR_HARD("bad format speed: " + str);
            CmdActorSpawn.access$502(CmdActorSpawn.this, true);
          }
        }
      }
    });
    this.param.put("SKILL", new Token() {
      public void parse() { String str = getStr();
        try { CmdActorSpawn.this.sarg.skill = Integer.parseInt(str);
        } catch (Exception localException) {
          CmdActorSpawn.this.ERR_HARD("bad format skill: " + str);
          CmdActorSpawn.access$502(CmdActorSpawn.this, true);
        }
      }
    });
    this.param.put("PLAYER", new Token() {
      public void parse() { String str = getStr();
        CmdActorSpawn.this.sarg.bPlayer = true;
      }
    });
    this.param.put("NUMBEROFF", new Token() {
      public void parse() { String str = getStr();
        CmdActorSpawn.this.sarg.bNumberOn = false;
      }
    });
    this.param.put("BORNPLACE", new Token() {
      public void parse() { String str = getStr();
        try { CmdActorSpawn.this.sarg.bornPlace = Integer.parseInt(str);
        } catch (Exception localException) {
          CmdActorSpawn.this.ERR_HARD("bad format born place: " + str);
          CmdActorSpawn.access$502(CmdActorSpawn.this, true);
        }
        CmdActorSpawn.this.sarg.bornPlaceExist = true;
      }
    });
    this.param.put("STAYPLACE", new Token() {
      public void parse() { String str = getStr();
        try { CmdActorSpawn.this.sarg.stayPlace = Integer.parseInt(str);
        } catch (Exception localException) {
          CmdActorSpawn.this.ERR_HARD("bad format stay place: " + str);
          CmdActorSpawn.access$502(CmdActorSpawn.this, true);
        }
        CmdActorSpawn.this.sarg.stayPlaceExist = true;
      }
    });
    this.param.put("RAWDATA", new Token() {
      public void parse() { String str = getStr();
        CmdActorSpawn.this.sarg.rawData = str;
      }
    });
    this._properties.put("NAME", "spawn");
    this._levelAccess = 0;
  }

  class Token
  {
    Token()
    {
    }

    public void parse()
    {
    }

    public String getStr()
    {
      if (!CmdActorSpawn.this.tokens.hasMoreTokens()) { CmdActorSpawn.access$102(CmdActorSpawn.this, null); return ""; }
      StringBuffer localStringBuffer = new StringBuffer();
      int i = 0;
      String str = null;
      CmdActorSpawn.access$102(CmdActorSpawn.this, null);
      while (CmdActorSpawn.this.tokens.hasMoreTokens()) {
        str = CmdActorSpawn.this.tokens.nextToken();
        if (CmdActorSpawn.this.paramContainsKey(str)) {
          CmdActorSpawn.access$102(CmdActorSpawn.this, str);
          break;
        }
        if (i++ > 0) localStringBuffer.append(' ');
        localStringBuffer.append(str);
      }
      if (localStringBuffer.length() > 0) return localStringBuffer.toString();
      return "";
    }
  }
}