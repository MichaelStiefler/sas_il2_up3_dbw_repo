package com.maddox.sound;

import com.maddox.il2.engine.TextScr;
import com.maddox.il2.objects.sounds.VoiceFX;
import com.maddox.rts.CfgTools;
import com.maddox.rts.IniFile;
import com.maddox.rts.Time;
import java.io.PrintStream;
import java.io.RandomAccessFile;

public class AudioDevice extends BaseObject
{
  public static final int CTRL_FX_OVERLOAD = 2000;
  public static final int CAPCTRL_LEVEL = 510;
  public static final int CAPCTRL_OVERFLOW = 512;
  public static final int CAPCTRL_STATUS = 511;
  public static final int CAPCTRL_MICLEVEL = 504;
  public static final int CAPCTRL_ACTLEVEL = 505;
  public static final int CAPCTRL_EMPHASIS = 506;
  public static final int CAPCTRL_LATENCY = 507;
  public static final int CAPCTRL_CODEC = 508;
  public static final int CAPCTRL_PTT_MODE = 520;
  public static final int CAPCTRL_PTT = 521;
  public static final int CAPCTRL_AGC = 522;
  public static final int CAPCTRL_SFX = 523;
  public static final int CAPSTAT_DISABLED = 0;
  public static final int CAPSTAT_ENABLED = 1;
  public static final int CAPSTAT_RUNNING = 2;
  public static final int CAPSTAT_ACTIVE = 3;
  public static final int CAPSTAT_VOICED = 16;
  public static final int CAP_CODEC_LPCM = 1;
  public static final int CAP_CODEC_GSM6 = 2;
  public static final int CAP_CODEC_GSM_HR = 3;
  protected static boolean npRunning = false;
  protected static final int htgSize = 512;
  protected static boolean performInfo = false;
  protected static boolean doPerformInfo = false;
  protected static int numFrames = 0;
  protected static long startTime = 0L; protected static long prevTime = 0L;
  protected static int[] htg = null;
  protected static int htgNum = 0;

  private static boolean fastMute = false;

  public static VGroup vMaster = new VGroup("MasterVolume", 0);
  public static VGroup vMusic = new VGroup("MusicVolume", 1);
  public static VGroup vObj = new VGroup("ObjectVolume", 2);
  public static VGroup vVoice = new VGroup("VoiceVolume", 3);

  public static String[] soundEngines = { "disable_sound", "directx" };
  public static String[] sr = { "default", "22050", "44100" };
  public static String[] chs = { "default", "8", "16", "32" };
  public static String[] soundMode = { "default", "minimum", "balanced", "full" };
  public static String[] speakers = { "default", "headphones", "desktop_speakers", "quadraphonic", "surround" };

  public static CfgIntSound vEngine = new CfgIntSound(1, "SoundEngine", soundEngines, 0);
  public static CfgIntSound vSR = new CfgIntSound(200, "SamplingRate", sr, 1);
  public static CfgIntSound vChs = new CfgIntSound(201, "NumChannels", chs, 0);
  public static CfgIntSound vMode = new CfgIntSound(202, "SoundMode", soundMode, 0);
  public static CfgIntSound vSpk = new CfgIntSound(203, "Speakers", speakers, 0);

  public static CfgNpFlags npFlags = new CfgNpFlags();
  public static CfgIntSound actLevel = new CfgIntSound(505, "ActLevel", null, 0);
  public static CfgIntSound micLevel = new CfgIntSound(504, "MicLevel", null, 0);
  public static CfgFlagsSound sndFlags;
  public static CfgMusState musState = new CfgMusState();
  public static CfgMusFlags musFlags = new CfgMusFlags();

  protected static boolean _cur_en = true;
  protected static final int ACAP_GEOMETRY = 1;
  protected static final int ACAP_EAX1 = 2;
  protected static final int ACAP_EAX2 = 4;
  protected static final int ACAP_IAL2 = 8;

  public static void setPerformInfo(boolean paramBoolean)
  {
    performInfo = paramBoolean;
    if (!paramBoolean) doPerformInfo = false; 
  }
  public static native int getInputData(byte[] paramArrayOfByte, int paramInt);

  public static native void resetInput(boolean paramBoolean);

  public static boolean isNetPhoneRunning() {
    return npRunning;
  }

  public static boolean getExtraOcclusion()
  {
    return getControl(711) != 0;
  }

  public static void setExtraOcclusion(boolean paramBoolean)
  {
    setControl(711, paramBoolean ? 1 : 0);
  }

  public static void beginNetPhone()
  {
    if (!npRunning) {
      if (npFlags.get(0)) jniBeginNetPhone();
      npRunning = true;
    }
  }

  public static void endNetPhone()
  {
    if (npRunning) {
      jniEndNetPhone();
      npRunning = false;
    }
  }

  public static void setInput(int paramInt)
  {
    setControl(508, paramInt);
  }

  public static boolean getAGC()
  {
    return getControl(522) != 0;
  }

  public static void setAGC(boolean paramBoolean)
  {
    setControl(522, paramBoolean ? 1 : 0);
  }

  public static boolean getPTTMode()
  {
    return getControl(520) != 0;
  }

  public static void setPTTMode(boolean paramBoolean)
  {
    setControl(520, paramBoolean ? 1 : 0);
  }

  public static boolean getPTT()
  {
    return getControl(521) != 0;
  }

  public static void setPTT(boolean paramBoolean)
  {
    setControl(521, paramBoolean ? 1 : 0);
  }

  public static boolean getPhoneFX()
  {
    return getControl(523) != 0;
  }

  public static void setPhoneFX(boolean paramBoolean)
  {
    setControl(523, paramBoolean ? 1 : 0);
  }

  public static int getRadioLevel()
  {
    return getControl(510);
  }

  public static int getRadioOverflow()
  {
    return getControl(512);
  }

  public static boolean isRadioRunning()
  {
    return getControl(511) >= 2;
  }

  public static int getRadioStatus()
  {
    return getControl(511); } 
  public static void inputStat() {  } 
  public static native boolean isControlEnabled(int paramInt);

  public static native int getControl(int paramInt);

  public static native boolean setControl(int paramInt1, int paramInt2);

  public static native boolean initialize(int paramInt, String paramString);

  public static native void done();

  public static void flush() { String str = jniFlush();
    if (str != null) printf(str);

    VoiceFX.tick();
    SoundListener.flush();
    RadioChannel.hack();
    if (doPerformInfo) {
      long l = Time.real();
      int i = (int)(l - prevTime) / 2;
      if (i >= 512) i = 511;
      else if (i < 0) i = 0;
      htg[i] += 1;
      prevTime = l;
      numFrames += 1;
    }
    CmdMusic.tick();
  }

  public static void toggleMute()
  {
    fastMute = !fastMute;
    setControl(306, fastMute ? 1 : 0);
  }

  public static void soundsOn()
  {
    if (fastMute) toggleMute();
    setControl(305, 0);
    if (performInfo) {
      doPerformInfo = true;
      numFrames = 0;
      startTime = Time.real();
      prevTime = startTime;
      System.out.println("Integral performance test started.");
      htg = new int[512];
      for (int i = 0; i < 512; i++) htg[i] = 0;
    }
  }

  public static void soundsOff()
  {
    if (fastMute) toggleMute();
    setControl(305, 1);
    if (doPerformInfo) {
      float f1 = (float)(Time.real() - startTime) / 1000.0F;
      System.out.println("Integral performance test stopped.");
      if (f1 > 0.0F) {
        float f2 = numFrames / f1;
        System.out.println("Frames : " + numFrames + ", time : " + f1 + ", fps : " + f2);
      }
      try {
        RandomAccessFile localRandomAccessFile = new RandomAccessFile("./ph" + htgNum++ + ".dat", "rw");
        localRandomAccessFile.setLength(0L);
        for (int i = 0; i < 512; i++) localRandomAccessFile.writeInt(htg[i]);
        localRandomAccessFile.close();
      }
      catch (Exception localException) {
        System.out.println("Error saving performance histogram : " + localException);
      }
      htg = null;
      doPerformInfo = false;
    }
  }

  protected static void initControls()
  {
    CfgFlagsInfo[] arrayOfCfgFlagsInfo = new CfgFlagsInfo[3];
    arrayOfCfgFlagsInfo[0] = new CfgFlagsInfo("reversestereo", 300, true, true);
    arrayOfCfgFlagsInfo[1] = new CfgFlagsInfo("hardware", 301, true, false);
    arrayOfCfgFlagsInfo[2] = new CfgFlagsInfo("forceEAX1", 307, false, false);

    sndFlags = new CfgFlagsSound("SoundFlags", arrayOfCfgFlagsInfo);

    CfgTools.register(sndFlags);
    CfgTools.register(npFlags);

    CfgTools.register(vMaster);
    CfgTools.register(vMusic);
    CfgTools.register(vObj);
    CfgTools.register(vVoice);

    CfgTools.register(musState);
    CfgTools.register(musFlags);

    CfgTools.register(vEngine);
    CfgTools.register(vMode);
    CfgTools.register(vSpk);
    CfgTools.register(vSR);
    CfgTools.register(vChs);

    CfgTools.register(actLevel);
    CfgTools.register(micLevel);
  }

  public static native void onMenuEnter();

  private static void _reportApply() {
    System.out.print("apply: ");
    if (sndFlags.needApply()) System.out.print(" flags");
    if (vMode.needApply) System.out.print(" mode");
    if (vSpk.needApply) System.out.print(" spk");
    if (vSR.needApply) System.out.print(" sr");
    if (vEngine.needApply) System.out.print(" engine");
    System.out.println();
  }

  public static void applySettings()
  {
    if ((sndFlags.needApply()) || (vMode.needApply) || (vSpk.needApply) || (vSR.needApply) || (vEngine.needApply)) {
      done();
      sndFlags.apply();
      vMode.apply();
      vSpk.apply();
      vSR.apply();
      if (vEngine.get() > 0) initialize(-1, null);
      vEngine.reset();
      vEngine.needApply = false;
    }
  }

  protected static native int getAcousticsCaps();

  public static void loadControls(IniFile paramIniFile, String paramString)
  {
    boolean bool = _cur_en;
    _cur_en = true;
    CfgTools.load(sndFlags, paramIniFile, paramString); sndFlags.apply();
    CfgTools.load(npFlags, paramIniFile, paramString); npFlags.apply();

    CfgTools.load(vMaster, paramIniFile, paramString); vMaster.apply();
    CfgTools.load(vMusic, paramIniFile, paramString); vMusic.apply();
    CfgTools.load(vObj, paramIniFile, paramString); vObj.apply();
    CfgTools.load(vVoice, paramIniFile, paramString); vVoice.apply();

    CfgTools.load(musState, paramIniFile, paramString); musState.apply();
    CfgTools.load(musFlags, paramIniFile, paramString); musFlags.apply();

    CfgTools.load(vMode, paramIniFile, paramString); vMode.apply();
    CfgTools.load(vSpk, paramIniFile, paramString); vSpk.apply();
    CfgTools.load(vSR, paramIniFile, paramString); vSR.apply();
    CfgTools.load(vChs, paramIniFile, paramString); vChs.apply();

    CfgTools.load(actLevel, paramIniFile, paramString);
    CfgTools.load(micLevel, paramIniFile, paramString);

    _cur_en = bool;
    CfgTools.load(vEngine, paramIniFile, paramString); vEngine.apply();
  }

  public static void saveControls(IniFile paramIniFile, String paramString)
  {
    boolean bool = _cur_en;
    _cur_en = true;
    CfgTools.save(false, sndFlags, paramIniFile, paramString);
    CfgTools.save(false, npFlags, paramIniFile, paramString);

    CfgTools.save(false, vMaster, paramIniFile, paramString);
    CfgTools.save(false, vMusic, paramIniFile, paramString);
    CfgTools.save(false, vObj, paramIniFile, paramString);
    CfgTools.save(false, vVoice, paramIniFile, paramString);

    CfgTools.save(false, musState, paramIniFile, paramString);
    CfgTools.save(false, musFlags, paramIniFile, paramString);

    CfgTools.save(false, vEngine, paramIniFile, paramString);
    CfgTools.save(false, vMode, paramIniFile, paramString);
    CfgTools.save(false, vSpk, paramIniFile, paramString);
    CfgTools.save(false, vSR, paramIniFile, paramString);
    CfgTools.save(false, vChs, paramIniFile, paramString);

    CfgTools.save(false, actLevel, paramIniFile, paramString);
    CfgTools.save(false, micLevel, paramIniFile, paramString);

    _cur_en = bool;
  }

  public static void resetControls()
  {
  }

  public static void setMessageMode(int paramInt)
  {
    initControls();
  }

  protected static void printVMStat()
  {
    int i = 0; int j = 0; int k = 40; int m = 30;
    while (true) {
      String str = jniVMStat(i, j);
      if (str == null) break;
      TextScr.output(k, m, str);
      m += 20;
      j++;
    }
  }

  protected static native String jniFlush();

  protected static native void jniBeginNetPhone();

  protected static native void jniEndNetPhone();

  protected static native String jniVMStat(int paramInt1, int paramInt2);
}