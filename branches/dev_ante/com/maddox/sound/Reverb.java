package com.maddox.sound;

import com.maddox.rts.SectFile;
import java.io.PrintStream;

public class Reverb extends BaseObject
{
  public static final int ENGINE_EAX1 = 0;
  public static final int ENGINE_EAX2 = 1;
  public static final int ENGINE_IAL2 = 2;
  public static final int ENGINE_EAX3 = 3;
  public static final int NUM_ENGINES = 4;
  public static final int REVERB_GENERIC = 0;
  public static final int REVERB_PADDEDCELL = 1;
  public static final int REVERB_ROOM = 2;
  public static final int REVERB_BATHROOM = 3;
  public static final int REVERB_LIVINGROOM = 4;
  public static final int REVERB_STONEROOM = 5;
  public static final int REVERB_AUDITORIUM = 6;
  public static final int REVERB_CONCERTHALL = 7;
  public static final int REVERB_CAVE = 8;
  public static final int REVERB_ARENA = 9;
  public static final int REVERB_HANGAR = 10;
  public static final int REVERB_CARPETEDHALLWAY = 11;
  public static final int REVERB_HALLWAY = 12;
  public static final int REVERB_STONECORRIDOR = 13;
  public static final int REVERB_ALLEY = 14;
  public static final int REVERB_FOREST = 15;
  public static final int REVERB_CITY = 16;
  public static final int REVERB_MOUNTAINS = 17;
  public static final int REVERB_QUARRY = 18;
  public static final int REVERB_PLAIN = 19;
  public static final int REVERB_PARKINGLOT = 20;
  public static final int REVERB_SEWERPIPE = 21;
  public static final int REVERB_UNDERWATER = 22;
  public static final int REVERB_DRUGGED = 23;
  public static final int REVERB_DIZZY = 24;
  public static final int REVERB_PSYCHOTIC = 25;
  public static final int ROOM = 1;
  public static final int ROOM_HF = 2;
  public static final int DECAY = 3;
  public static final int DECAY_HF = 4;
  public static final int EARLY = 5;
  public static final int EARLY_DELAY = 6;
  public static final int REVERB = 7;
  public static final int REVERB_DELAY = 8;
  public static final int SIZE = 9;
  public static final int DIFFUSION = 10;
  public static final int ABSORPTION = 11;
  public static final int ROOM_ATTN = 100;
  protected int handle = 0;
  protected int engine = 0;

  protected ReverbFX rfx = null;

  protected static String[] envTab = new String[26];

  public Reverb(int paramInt)
  {
    this.engine = paramInt;
    this.handle = jniCreate(paramInt);
  }

  public int getEngine()
  {
    return this.engine;
  }

  public float get(int paramInt)
  {
    return jniGet(this.handle, paramInt);
  }

  public void set(int paramInt, float paramFloat)
  {
    jniSet(this.handle, paramInt, paramFloat);
  }

  public int getType()
  {
    return jniGetType(this.handle);
  }

  public void setType(int paramInt)
  {
    jniSetType(this.handle, paramInt);
  }

  protected void finalize() throws Throwable
  {
    if (this.handle != 0) jniDestroy(this.handle);
    super.finalize();
  }

  protected String getEngineName()
  {
    if (this.engine == 0) return "eax1";
    if (this.engine == 1) return "eax2";
    if (this.engine == 2) return "ial2";
    if (this.engine == 3) return "eax3";
    return "";
  }

  protected void set(String paramString1, String paramString2)
  {
    int k;
    if (this.engine == 0) {
      if (paramString1.compareToIgnoreCase("type") == 0) {
        for (int i = 0; i < 26; i++) {
          if (envTab[i].compareToIgnoreCase(paramString2) == 0) {
            setType(i);
            break;
          }
        }

      }
      else if (paramString1.compareToIgnoreCase("room") == 0) { set(1, Float.parseFloat(paramString2));
      }
      else if (paramString1.compareToIgnoreCase("decay") == 0) { set(3, Float.parseFloat(paramString2));
      }
      else if (paramString1.compareToIgnoreCase("hf") == 0) { set(4, Float.parseFloat(paramString2));
      } else {
        String str1 = "valid type values are : ";
        System.out.println("valid keys are: type room decay hf");
        for (k = 0; k < 26; k++) str1 = str1 + envTab[k];
        System.out.println(str1);
      }
    }
    if ((this.engine == 1) || (this.engine == 2))
      if (paramString1.compareToIgnoreCase("type") == 0) {
        for (int j = 0; j < 26; j++) {
          if (envTab[j].compareToIgnoreCase(paramString2) == 0) {
            setType(j);
            break;
          }
        }

      }
      else if (paramString1.compareToIgnoreCase("room") == 0) { set(1, Float.parseFloat(paramString2));
      }
      else if (paramString1.compareToIgnoreCase("roomhf") == 0) { set(1, Float.parseFloat(paramString2));
      }
      else if (paramString1.compareToIgnoreCase("decay") == 0) { set(3, Float.parseFloat(paramString2));
      }
      else if (paramString1.compareToIgnoreCase("decayhf") == 0) { set(4, Float.parseFloat(paramString2));
      }
      else if (paramString1.compareToIgnoreCase("early") == 0) { set(5, Float.parseFloat(paramString2));
      }
      else if (paramString1.compareToIgnoreCase("earlydelay") == 0) { set(6, Float.parseFloat(paramString2));
      }
      else if (paramString1.compareToIgnoreCase("reverb") == 0) { set(7, Float.parseFloat(paramString2));
      }
      else if (paramString1.compareToIgnoreCase("reverbdelay") == 0) { set(8, Float.parseFloat(paramString2));
      }
      else if (paramString1.compareToIgnoreCase("size") == 0) { set(9, Float.parseFloat(paramString2));
      }
      else if (paramString1.compareToIgnoreCase("diffusion") == 0) { set(10, Float.parseFloat(paramString2));
      }
      else if (paramString1.compareToIgnoreCase("absorption") == 0) { set(11, Float.parseFloat(paramString2));
      } else {
        String str2 = "valid type values are : ";
        System.out.println("valid keys are: <type room roomhf decay decayhf early ");
        System.out.println("....earlydelay reverb reverbdelay size diffusion absorption>");
        for (k = 0; k < 26; k++) str2 = str2 + envTab[k];
        System.out.println(str2);
      }
  }

  protected boolean load(SectFile paramSectFile)
  {
    int i;
    String str1;
    if (this.engine == 0) {
      i = -1;
      str1 = paramSectFile.get("eax1", "Type", (String)null);
      for (int j = 0; j < 26; j++) {
        if (envTab[j].compareToIgnoreCase(str1) == 0) {
          i = j;
          break;
        }
      }
      if (i == -1) return false;
      setType(i);

      set(1, paramSectFile.get("eax1", "Room", 0.0F));
      set(3, paramSectFile.get("eax1", "Decay", 0.0F));
      set(4, paramSectFile.get("eax1", "DecayHf", 0.0F));

      if (this.rfx != null) this.rfx.load(paramSectFile);

      return true;
    }
    if ((this.engine == 1) || (this.engine == 2)) {
      i = -1;
      str1 = this.engine == 2 ? "ial2" : "eax2";
      String str2 = paramSectFile.get(str1, "Type", (String)null);
      for (int k = 0; k < 26; k++) {
        if (envTab[k].compareToIgnoreCase(str2) == 0) {
          i = k;
          break;
        }
      }
      if (i == -1) return false;
      setType(i);

      set(1, paramSectFile.get("eax2", "Room", 0.0F));
      set(2, paramSectFile.get("eax2", "RoomHf", 0.0F));
      set(3, paramSectFile.get("eax2", "Decay", 0.0F));
      set(4, paramSectFile.get("eax2", "DecayHf", 0.0F));
      set(5, paramSectFile.get("eax2", "Early", 0.0F));
      set(6, paramSectFile.get("eax2", "EarlyDelay", 0.0F));
      set(7, paramSectFile.get("eax2", "Reverb", 0.0F));
      set(8, paramSectFile.get("eax2", "ReverbDelay", 0.0F));
      set(9, paramSectFile.get("eax2", "Size", 0.0F));
      set(10, paramSectFile.get("eax2", "Diffusion", 0.0F));
      set(11, paramSectFile.get("eax2", "Absorption", 0.0F));

      if (this.rfx != null) this.rfx.load(paramSectFile);

      return true;
    }
    return false;
  }

  protected boolean save(SectFile paramSectFile)
  {
    if (this.engine == 0) {
      paramSectFile.set("eax1", "Type", envTab[getType()]);

      paramSectFile.set("eax1", "Room", get(1));
      paramSectFile.set("eax1", "Decay", get(3));
      paramSectFile.set("eax1", "DecayHf", get(4));

      return true;
    }
    if ((this.engine == 1) || (this.engine == 2)) {
      String str = this.engine == 2 ? "ial2" : "eax2";
      paramSectFile.set(str, "Type", envTab[getType()]);

      paramSectFile.set("eax2", "Room", get(1));
      paramSectFile.set("eax2", "RoomHf", get(2));
      paramSectFile.set("eax2", "Decay", get(3));
      paramSectFile.set("eax2", "DecayHf", get(4));
      paramSectFile.set("eax2", "Early", get(5));
      paramSectFile.set("eax2", "EarlyDelay", get(6));
      paramSectFile.set("eax2", "Reverb", get(7));
      paramSectFile.set("eax2", "ReverbDelay", get(8));
      paramSectFile.set("eax2", "Size", get(9));
      paramSectFile.set("eax2", "Diffusion", get(10));
      paramSectFile.set("eax2", "Absorption", get(11));

      return true;
    }
    return false;
  }

  protected void apply()
  {
    jniApply(this.handle, true);
  }

  protected static native int jniCreate(int paramInt);

  protected static native void jniDestroy(int paramInt);

  protected static native void jniApply(int paramInt, boolean paramBoolean);

  protected static native float jniGet(int paramInt1, int paramInt2);

  protected static native void jniSet(int paramInt1, int paramInt2, float paramFloat);

  protected static native int jniGetType(int paramInt);

  protected static native void jniSetType(int paramInt1, int paramInt2);

  static
  {
    envTab[0] = "GENERIC"; envTab[1] = "PADDEDCELL";
    envTab[2] = "ROOM"; envTab[3] = "BATHROOM";
    envTab[4] = "LIVINGROOM"; envTab[5] = "STONEROOM";
    envTab[6] = "AUDITORIUM"; envTab[7] = "CONCERTHALL";
    envTab[8] = "CAVE"; envTab[9] = "ARENA";
    envTab[10] = "HANGAR"; envTab[11] = "CARPETEDHALLWAY";
    envTab[12] = "HALLWAY"; envTab[13] = "STONECORRIDOR";
    envTab[14] = "ALLEY"; envTab[15] = "FOREST";
    envTab[16] = "CITY"; envTab[17] = "MOUNTAINS";
    envTab[18] = "QUARRY"; envTab[19] = "PLAIN";
    envTab[20] = "PARKINGLOT"; envTab[21] = "SEWERPIPE";
    envTab[22] = "UNDERWATER"; envTab[23] = "DRUGGED";
    envTab[24] = "DIZZY"; envTab[25] = "PSYCHOTIC";
  }
}