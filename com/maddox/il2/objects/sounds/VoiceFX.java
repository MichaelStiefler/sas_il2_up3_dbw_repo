package com.maddox.il2.objects.sounds;

import com.maddox.rts.HomePath;
import com.maddox.rts.Time;
import com.maddox.sound.Sample;
import com.maddox.sound.SoundFX;
import com.maddox.sound.SoundPreset;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class VoiceFX
{
  public static final int numVoices = 9;
  protected long time;
  protected int voice;
  protected int armyNum;
  protected String country;
  protected int[] phrase;
  protected ArrayList subFX;
  protected static SoundPreset preset = null;
  protected static SoundFX[] voices = new SoundFX[9];

  protected static ArrayList queue = new ArrayList();
  protected static ArrayList async = new ArrayList();

  protected static VoiceFX curFX = null;
  protected static VoiceFX curSync = null;
  protected static boolean enabled = false;

  private static HashMap countryMap = new HashMap();

  private static Random rnd = new Random();
  private static final int rndDelay = 3500;
  private static final int minDelay = 1500;
  private static int prevVoice = -1;
  private static int curDt = 100;

  public VoiceFX()
  {
    this.time = 0L;
    this.voice = -1;
    this.armyNum = 0;
    this.country = null;
    this.phrase = null;
    this.subFX = new ArrayList();
  }

  protected void playVoice()
  {
    SoundFX localSoundFX = voices[this.voice];
    if (localSoundFX != null)
      for (int i = 0; i < this.phrase.length; i++) {
        String str = null;
        if ((this.country != null) && (countryMap.containsKey(this.country))) {
          str = this.country;
        }
        else
        {
          switch (this.armyNum) { case 1:
            str = "ru"; break;
          case 2:
            str = "de"; break;
          default:
            return;
          }

        }

        str = "Speech/" + str + "/Actor" + (this.voice + 1) + "/" + VoiceBase.vbStr[this.phrase[i]];
        localSoundFX.play(new Sample(str + ".wav", 1));
      }
  }

  public static void tick()
  {
    if (!enabled) return;

    long l1 = Time.real();
    prevVoice = -1;
    curDt = 100;
    VoiceFX localVoiceFX3;
    if ((curSync != null) && 
      (!voices[curSync.voice].isPlaying())) {
      long l2 = 100L;
      int k = -1;
      while (curSync.subFX.size() > 0) {
        localVoiceFX3 = (VoiceFX)curSync.subFX.get(0);
        if ((k >= 0) && (k != localVoiceFX3.voice)) l2 = 1500 + rnd.nextInt(3500); else
          l2 += 200L;
        localVoiceFX3.time = (l1 + l2);
        async.add(localVoiceFX3);
        curSync.subFX.remove(0);
        k = localVoiceFX3.voice;
      }
      curSync = null;
      curFX = null;
    }
    int i;
    if ((curSync == null) && 
      (queue.size() > 0)) {
      i = 0;
      curSync = (VoiceFX)queue.get(0);
      curSync.playVoice();
      queue.remove(0);
      while (queue.size() > 0) {
        VoiceFX localVoiceFX1 = (VoiceFX)queue.get(0);
        if (localVoiceFX1.voice != curSync.voice) break;
        localVoiceFX1.playVoice();
        while (localVoiceFX1.subFX.size() > 0) {
          VoiceFX localVoiceFX2 = (VoiceFX)localVoiceFX1.subFX.get(0);
          curSync.subFX.add(localVoiceFX2);
          localVoiceFX1.subFX.remove(0);
        }
        queue.remove(0);
      }

    }

    if (async.size() > 0) {
      i = 0;
      for (int j = 0; j < 9; j++) {
        if (!voices[j].isPlaying()) continue; i++;
      }
      for (int m = 0; m < async.size(); ) {
        localVoiceFX3 = (VoiceFX)async.get(m);
        if (i > 2) break;
        if (localVoiceFX3.time < l1) {
          localVoiceFX3.playVoice();
          async.remove(m);
          i++;
          while (async.size() > m) {
            VoiceFX localVoiceFX4 = (VoiceFX)async.get(m);
            if (localVoiceFX3.voice != localVoiceFX4.voice) break;
            localVoiceFX4.playVoice();
            async.remove(m);
          }
        } else {
          m++;
        }
      }
    }
  }

  public static void play(int paramInt1, int paramInt2, int paramInt3, String paramString, int[] paramArrayOfInt)
  {
    if ((paramArrayOfInt == null) || (paramArrayOfInt.length == 0)) return;

    VoiceFX localVoiceFX = new VoiceFX();
    paramInt2--;
    for (int i = 0; (i < paramArrayOfInt.length) && (paramArrayOfInt[i] != 0); i++);
    localVoiceFX.voice = paramInt2;
    localVoiceFX.armyNum = paramInt3;
    localVoiceFX.country = paramString;
    if (localVoiceFX.country != null)
      localVoiceFX.country = localVoiceFX.country.toLowerCase();
    localVoiceFX.phrase = new int[i];
    for (int j = 0; j < i; j++) localVoiceFX.phrase[j] = paramArrayOfInt[j];
    if (paramInt1 == 1) paramInt1 = 0;

    if (paramInt1 == 2)
    {
      async.add(localVoiceFX);
    }
    else if (paramInt1 == 0) {
      curFX = localVoiceFX;
      queue.add(localVoiceFX);
    }
    else if (paramInt1 == 1)
    {
      curFX.subFX.add(localVoiceFX);
    }
    prevVoice = paramInt2;
  }

  public static void end()
  {
    for (int i = 0; i < 9; i++) {
      if (voices[i] != null) {
        voices[i].cancel();
        voices[i].clear();
      }
    }
    queue.clear();
    async.clear();
  }

  public static boolean isEnabled()
  {
    return enabled;
  }

  public static void setEnabled(boolean paramBoolean)
  {
    if (preset == null)
      preset = new SoundPreset("voice");
    enabled = paramBoolean;
    for (int i = 0; i < 9; i++)
      if (enabled) {
        if (voices[i] == null) {
          voices[i] = new SoundFX(preset);
        }
      }
      else if (voices[i] != null) {
        voices[i].cancel();
        voices[i] = null;
      }
  }

  static
  {
    for (int i = 0; i < 9; i++) voices[i] = null;

    countryMap.put("ru".intern(), null);
    countryMap.put("de".intern(), null);

    File localFile = new File(HomePath.get(0), "samples/speech");
    if (localFile != null) {
      File[] arrayOfFile = localFile.listFiles();
      if (arrayOfFile != null)
        for (int j = 0; j < arrayOfFile.length; j++)
          if ((arrayOfFile[j].isDirectory()) && (!arrayOfFile[j].isHidden())) {
            String str = arrayOfFile[j].getName().toLowerCase();
            if (countryMap.containsKey(str))
              continue;
            countryMap.put(str.intern(), null);
          }
    }
  }
}