package com.maddox.sound;

import com.maddox.rts.SectFile;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SoundPreset extends Preset
{
  public static final int M3D_NONE = 0;
  public static final int M3D_NORMAL = 1;
  public static final float STD_SPL = 70.0F;
  protected static final int SND_NORMAL = 0;
  protected static final int SND_MIXER = 1;
  protected ArrayList sources = null;
  protected ArrayList emitters = null;
  protected int fxCaps = 0;
  protected int fxFlags = 0;
  protected int type = 0;

  protected float eaxMix = -1.0F;
  protected float occlusion = 0.0F;
  protected float occlLF = 0.0F;
  protected float obstruction = 0.0F;
  protected float obstrLF = 0.0F;
  protected float eaxRoom = 0.0F;

  protected ArrayList controls = null;

  protected static HashMapExt map = new HashMapExt();

  protected static Class[] csid = new Class[1];
  protected static Object[] args = new Object[1];
  private static String[] typeList;

  public SoundPreset(String paramString)
  {
    super(paramString);
    map.put(paramString, this);
    try {
      if (!BaseObject.enabled) return;
      SectFile localSectFile = new SectFile("presets/sounds/" + paramString + ".prs");
      this.jdField_handle_of_type_Int = Preset.jniGet(paramString);
      if (this.jdField_handle_of_type_Int == 0) this.jdField_handle_of_type_Int = jniCreate(paramString);
      onCreate();
      load(localSectFile, "");
    }
    catch (Exception localException) {
      BaseObject.printf("Cannot load sound preset " + paramString + " (" + localException + ")");
      this.jdField_handle_of_type_Int = 0;
    }
  }

  public static SoundPreset get(String paramString)
  {
    SoundPreset localSoundPreset = (SoundPreset)map.get(paramString);
    if (localSoundPreset == null) {
      localSoundPreset = new SoundPreset(paramString);
    }
    return localSoundPreset;
  }

  public void add(Preset paramPreset)
  {
    this.sources.add(paramPreset);
  }

  public boolean is3d()
  {
    return (this.fxCaps & 0x7) != 0;
  }

  public void load(SectFile paramSectFile, String paramString) throws Exception
  {
    String str2 = paramString + "common";
    if (!paramSectFile.sectionExist(str2)) throw new Exception("Invalid preset format");

    this.eaxMix = paramSectFile.get(str2, "eaxMix", -1.0F);
    this.occlusion = paramSectFile.get(str2, "occlusion", 0.0F);
    this.occlLF = paramSectFile.get(str2, "occlLF", 0.0F);
    this.obstruction = paramSectFile.get(str2, "obstruction", 0.0F);
    this.obstrLF = paramSectFile.get(str2, "obstrLF", 0.0F);
    this.eaxRoom = paramSectFile.get(str2, "eaxRoom", 0.0F);

    jniSetMutable(this.jdField_handle_of_type_Int, paramSectFile.get(str2, "mutable", false));

    int i = paramSectFile.sectionIndex("spl");
    if (i >= 0) {
      j = paramSectFile.vars(i); int k = 0;
      for (int m = 0; m < j; m++) {
        int i1 = 0;
        int i3 = 0; int i4 = 0;
        float f1 = 70.0F; float f2 = 0.0F;
        StringTokenizer localStringTokenizer2 = new StringTokenizer(paramSectFile.line(i, m));
        while (localStringTokenizer2.hasMoreTokens()) {
          String str4 = localStringTokenizer2.nextToken();
          if (str4.compareToIgnoreCase("*") == 0) {
            i1 = 1;
            i3 = 0;
          }
          else if (i1 != 0) {
            i4 |= 1 << Integer.parseInt(str4);
          } else {
            if (i3 == 0) f1 = Float.parseFloat(str4);
            else if (i3 == 1) f2 = Float.parseFloat(str4);
            else
              throw new Exception("Invalid arguments");
            i3++;
          }
        }

        if (i4 == 0) i4 = -1;
        if ((k & i4) != 0) throw new Exception("Invalid spl flags");
        k |= i4;
        jniSetSPL(this.jdField_handle_of_type_Int, f1, f2, i4);
      }
    }

    String str1 = paramSectFile.get(str2, "mode", (String)null);
    if (str1 != null) {
      if (str1.indexOf("seq") >= 0) this.fxFlags |= 32768;
      if (str1.indexOf("normal") >= 0) {
        if (str1.indexOf(" pos") >= 0) this.fxCaps |= 1;
        if (str1.indexOf(" vel") >= 0) this.fxCaps |= 2;
        if (str1.indexOf(" or") >= 0) this.fxCaps |= 4;
        if (str1.indexOf(" all") >= 0) {
          this.fxCaps |= 1;
          this.fxCaps |= 2;
          this.fxCaps |= 4;
          this.fxCaps |= 8;
        }
        if (!is3d()) this.fxCaps |= 3;
      }
      if (str1.indexOf("relist") >= 0) {
        if (str1.indexOf(" pos") >= 0) this.fxCaps |= 1;
        this.fxCaps |= 512;
      }
      if (str1.indexOf("relobj") >= 0) {
        if (str1.indexOf(" pos") >= 0) this.fxCaps |= 1;
        this.fxCaps |= 256;
      }
    }
    str1 = paramSectFile.get(str2, "type", (String)null);
    this.type = 0;
    if (paramSectFile.sectionExist(paramString + "emit")) {
      this.type = 1;
    }
    else if (str1 != null) {
      for (j = 0; j < typeList.length; j++) {
        if (typeList[j].compareToIgnoreCase(str1) != 0) continue; this.type = j; break;
      }
    }

    if (paramSectFile.get(str2, "infinite", false)) this.fxFlags = 1;
    if (paramSectFile.get(str2, "permanent", false)) this.fxFlags |= 16384;
    if (paramSectFile.get(str2, "undetune", false)) this.fxFlags |= 8192;
    if (paramSectFile.get(str2, "numadj", false)) this.fxFlags |= 4096;
    if (paramSectFile.get(str2, "pmax", false)) this.fxFlags |= 65536;
    if (paramSectFile.get(str2, "music", false)) this.fxCaps |= 4096;
    int j = paramSectFile.get(str2, "forcectrl", -1);
    if (this.jdField_handle_of_type_Int != 0) {
      jniSetProperties(this.jdField_handle_of_type_Int, this.type, this.fxFlags, AudioDevice.vObj.jdField_handle_of_type_Int);
      jniSetEAX(this.jdField_handle_of_type_Int, this.eaxMix, this.occlusion, this.occlLF, this.eaxRoom, this.obstruction, this.obstrLF);
      localObject1 = null;
      if ((this.fxCaps & 0x1000) != 0) localObject1 = AudioDevice.vMusic;
      else if (paramSectFile.get(str2, "voice", false)) localObject1 = AudioDevice.vVoice;
      else if ((this.fxFlags & 0x4000) == 0) localObject1 = AudioDevice.vObj;
      if (localObject1 != null) jniSetVGroup(this.jdField_handle_of_type_Int, ((VGroup)localObject1).jdField_handle_of_type_Int);
      jniForceControl(this.jdField_handle_of_type_Int, j);
    }

    Object localObject1 = paramSectFile.get(str2, "controls", (String)null);
    Object localObject2;
    if (localObject1 != null) {
      this.controls = new ArrayList();
      StringTokenizer localStringTokenizer1 = new StringTokenizer((String)localObject1);
      while (localStringTokenizer1.hasMoreTokens()) {
        String str3 = localStringTokenizer1.nextToken();
        localObject2 = ControlInfo.get(str3, jniGetControlList(this.jdField_handle_of_type_Int));
        if (localObject2 == null) {
          continue;
        }
        ((SoundControl)localObject2).load(paramSectFile, paramString + "." + str3);
        this.controls.add(localObject2);
      }

    }

    int n = paramSectFile.sectionIndex(paramString + "samples");
    int i2;
    Object localObject3;
    if (n >= 0) {
      if (this.sources == null) this.sources = new ArrayList(32);
      for (i2 = 0; i2 < paramSectFile.vars(n); i2++) {
        localObject2 = paramSectFile.line(n, i2);
        localObject3 = new Sample((String)localObject2);
        ((Sample)localObject3).load(paramSectFile, "sample." + (String)localObject2);
        this.sources.add(localObject3);
        jniAddSample(this.jdField_handle_of_type_Int, ((Sample)localObject3).jdField_handle_of_type_Int);
      }
    }

    n = paramSectFile.sectionIndex(paramString + "emit");
    if (n >= 0) {
      if (this.emitters == null) this.emitters = new ArrayList(32);
      for (i2 = 0; i2 < paramSectFile.vars(n); i2++) {
        localObject2 = paramSectFile.line(n, i2);
        localObject3 = new Emitter((String)localObject2, this.jdField_handle_of_type_Int);
        ((Emitter)localObject3).load(paramSectFile, "emit." + (String)localObject2);
        this.emitters.add(localObject3);
      }
    }
  }

  public void save(SectFile paramSectFile, String paramString) throws Exception
  {
    String str = paramString + this.name + ".";
    paramSectFile.sectionClear(paramSectFile.sectionIndex(paramString + "common"));
    paramSectFile.sectionClear(paramSectFile.sectionIndex(paramString + "objects"));
    if (this.sources != null)
      for (int i = 0; i < this.sources.size(); i++) {
        Preset localPreset = (Preset)this.sources.get(i);
        localPreset.save(paramSectFile, str + "object.");
      }
  }

  protected void set(String paramString1, String paramString2)
  {
    if (paramString1.compareToIgnoreCase("mix") == 0) this.eaxMix = Float.parseFloat(paramString2);
    else if (paramString1.compareToIgnoreCase("occl") == 0) this.occlusion = Float.parseFloat(paramString2);
    else if (paramString1.compareToIgnoreCase("lf") == 0) this.occlLF = Float.parseFloat(paramString2);
    else if (paramString1.compareToIgnoreCase("room") == 0) this.eaxRoom = Float.parseFloat(paramString2);
    else {
      System.out.println("params: mix = " + this.eaxMix + ", occlusion = " + this.occlusion + ", lf = " + this.occlLF + ", room = " + this.eaxRoom);
    }
    if (this.jdField_handle_of_type_Int != 0) jniSetEAX(this.jdField_handle_of_type_Int, this.eaxMix, this.occlusion, this.occlLF, this.eaxRoom, this.obstruction, this.obstrLF);
  }

  protected void onCreate()
  {
  }

  protected int createObject()
  {
    if (this.jdField_handle_of_type_Int == 0) return 0;
    int i = Preset.jniGetFreeObject(this.jdField_handle_of_type_Int, this.fxCaps);
    if (i == 0) {
      i = Preset.jniCreateObject(this.jdField_handle_of_type_Int, this.fxCaps);
    }
    return i;
  }

  protected native int jniCreate(String paramString);

  protected native void jniSetMutable(int paramInt, boolean paramBoolean);

  protected native void jniSetSPL(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2);

  protected native void jniSetProperties(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  protected native void jniSetEAX(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);

  protected native void jniSetVGroup(int paramInt1, int paramInt2);

  protected native void jniAddSample(int paramInt1, int paramInt2);

  protected native int jniGetControlList(int paramInt);

  protected native void jniForceControl(int paramInt1, int paramInt2);

  static
  {
    csid[0] = String.class;

    typeList = new String[] { "normal", "mixer" };
  }
}