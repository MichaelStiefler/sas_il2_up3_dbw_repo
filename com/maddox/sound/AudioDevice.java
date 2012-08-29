// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AudioDevice.java

package com.maddox.sound;

import com.maddox.il2.engine.TextScr;
import com.maddox.il2.objects.sounds.VoiceFX;
import com.maddox.rts.CfgTools;
import com.maddox.rts.IniFile;
import com.maddox.rts.Time;
import java.io.PrintStream;
import java.io.RandomAccessFile;

// Referenced classes of package com.maddox.sound:
//            BaseObject, CfgFlagsInfo, CfgFlagsSound, VGroup, 
//            CfgIntSound, CfgNpFlags, CfgMusState, CfgMusFlags, 
//            SoundListener, RadioChannel, CmdMusic

public class AudioDevice extends com.maddox.sound.BaseObject
{

    public AudioDevice()
    {
    }

    public static void setPerformInfo(boolean flag)
    {
        performInfo = flag;
        if(!flag)
            doPerformInfo = false;
    }

    public static native int getInputData(byte abyte0[], int i);

    public static native void resetInput(boolean flag);

    public static boolean isNetPhoneRunning()
    {
        return npRunning;
    }

    public static boolean getExtraOcclusion()
    {
        return com.maddox.sound.AudioDevice.getControl(711) != 0;
    }

    public static void setExtraOcclusion(boolean flag)
    {
        com.maddox.sound.AudioDevice.setControl(711, flag ? 1 : 0);
    }

    public static void beginNetPhone()
    {
        if(!npRunning)
        {
            if(npFlags.get(0))
                com.maddox.sound.AudioDevice.jniBeginNetPhone();
            npRunning = true;
        }
    }

    public static void endNetPhone()
    {
        if(npRunning)
        {
            com.maddox.sound.AudioDevice.jniEndNetPhone();
            npRunning = false;
        }
    }

    public static void setInput(int i)
    {
        com.maddox.sound.AudioDevice.setControl(508, i);
    }

    public static boolean getAGC()
    {
        return com.maddox.sound.AudioDevice.getControl(522) != 0;
    }

    public static void setAGC(boolean flag)
    {
        com.maddox.sound.AudioDevice.setControl(522, flag ? 1 : 0);
    }

    public static boolean getPTTMode()
    {
        return com.maddox.sound.AudioDevice.getControl(520) != 0;
    }

    public static void setPTTMode(boolean flag)
    {
        com.maddox.sound.AudioDevice.setControl(520, flag ? 1 : 0);
    }

    public static boolean getPTT()
    {
        return com.maddox.sound.AudioDevice.getControl(521) != 0;
    }

    public static void setPTT(boolean flag)
    {
        com.maddox.sound.AudioDevice.setControl(521, flag ? 1 : 0);
    }

    public static boolean getPhoneFX()
    {
        return com.maddox.sound.AudioDevice.getControl(523) != 0;
    }

    public static void setPhoneFX(boolean flag)
    {
        com.maddox.sound.AudioDevice.setControl(523, flag ? 1 : 0);
    }

    public static int getRadioLevel()
    {
        return com.maddox.sound.AudioDevice.getControl(510);
    }

    public static int getRadioOverflow()
    {
        return com.maddox.sound.AudioDevice.getControl(512);
    }

    public static boolean isRadioRunning()
    {
        return com.maddox.sound.AudioDevice.getControl(511) >= 2;
    }

    public static int getRadioStatus()
    {
        return com.maddox.sound.AudioDevice.getControl(511);
    }

    public static void inputStat()
    {
    }

    public static native boolean isControlEnabled(int i);

    public static native int getControl(int i);

    public static native boolean setControl(int i, int j);

    public static native boolean initialize(int i, java.lang.String s);

    public static native void done();

    public static void flush()
    {
        java.lang.String s = com.maddox.sound.AudioDevice.jniFlush();
        if(s != null)
            com.maddox.sound.BaseObject.printf(s);
        com.maddox.il2.objects.sounds.VoiceFX.tick();
        com.maddox.sound.SoundListener.flush();
        com.maddox.sound.RadioChannel.hack();
        if(doPerformInfo)
        {
            long l = com.maddox.rts.Time.real();
            int i = (int)(l - prevTime) / 2;
            if(i >= 512)
                i = 511;
            else
            if(i < 0)
                i = 0;
            htg[i]++;
            prevTime = l;
            numFrames++;
        }
        com.maddox.sound.CmdMusic.tick();
    }

    public static void toggleMute()
    {
        fastMute = !fastMute;
        com.maddox.sound.AudioDevice.setControl(306, fastMute ? 1 : 0);
    }

    public static void soundsOn()
    {
        if(fastMute)
            com.maddox.sound.AudioDevice.toggleMute();
        com.maddox.sound.AudioDevice.setControl(305, 0);
        if(performInfo)
        {
            doPerformInfo = true;
            numFrames = 0;
            startTime = com.maddox.rts.Time.real();
            prevTime = startTime;
            java.lang.System.out.println("Integral performance test started.");
            htg = new int[512];
            for(int i = 0; i < 512; i++)
                htg[i] = 0;

        }
    }

    public static void soundsOff()
    {
        if(fastMute)
            com.maddox.sound.AudioDevice.toggleMute();
        com.maddox.sound.AudioDevice.setControl(305, 1);
        if(doPerformInfo)
        {
            float f = (float)(com.maddox.rts.Time.real() - startTime) / 1000F;
            java.lang.System.out.println("Integral performance test stopped.");
            if(f > 0.0F)
            {
                float f1 = (float)numFrames / f;
                java.lang.System.out.println("Frames : " + numFrames + ", time : " + f + ", fps : " + f1);
            }
            try
            {
                java.io.RandomAccessFile randomaccessfile = new RandomAccessFile("./ph" + htgNum++ + ".dat", "rw");
                randomaccessfile.setLength(0L);
                for(int i = 0; i < 512; i++)
                    randomaccessfile.writeInt(htg[i]);

                randomaccessfile.close();
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println("Error saving performance histogram : " + exception);
            }
            htg = null;
            doPerformInfo = false;
        }
    }

    protected static void initControls()
    {
        com.maddox.sound.CfgFlagsInfo acfgflagsinfo[] = new com.maddox.sound.CfgFlagsInfo[3];
        acfgflagsinfo[0] = new CfgFlagsInfo("reversestereo", 300, true, true);
        acfgflagsinfo[1] = new CfgFlagsInfo("hardware", 301, true, false);
        acfgflagsinfo[2] = new CfgFlagsInfo("forceEAX1", 307, false, false);
        sndFlags = new CfgFlagsSound("SoundFlags", acfgflagsinfo);
        com.maddox.rts.CfgTools.register(sndFlags);
        com.maddox.rts.CfgTools.register(npFlags);
        com.maddox.rts.CfgTools.register(vMaster);
        com.maddox.rts.CfgTools.register(vMusic);
        com.maddox.rts.CfgTools.register(vObj);
        com.maddox.rts.CfgTools.register(vVoice);
        com.maddox.rts.CfgTools.register(musState);
        com.maddox.rts.CfgTools.register(musFlags);
        com.maddox.rts.CfgTools.register(vEngine);
        com.maddox.rts.CfgTools.register(vMode);
        com.maddox.rts.CfgTools.register(vSpk);
        com.maddox.rts.CfgTools.register(vSR);
        com.maddox.rts.CfgTools.register(vChs);
        com.maddox.rts.CfgTools.register(actLevel);
        com.maddox.rts.CfgTools.register(micLevel);
    }

    public static native void onMenuEnter();

    private static void _reportApply()
    {
        java.lang.System.out.print("apply: ");
        if(sndFlags.needApply())
            java.lang.System.out.print(" flags");
        if(vMode.needApply)
            java.lang.System.out.print(" mode");
        if(vSpk.needApply)
            java.lang.System.out.print(" spk");
        if(vSR.needApply)
            java.lang.System.out.print(" sr");
        if(vEngine.needApply)
            java.lang.System.out.print(" engine");
        java.lang.System.out.println();
    }

    public static void applySettings()
    {
        if(sndFlags.needApply() || vMode.needApply || vSpk.needApply || vSR.needApply || vEngine.needApply)
        {
            com.maddox.sound.AudioDevice.done();
            sndFlags.apply();
            vMode.apply();
            vSpk.apply();
            vSR.apply();
            if(vEngine.get() > 0)
                com.maddox.sound.AudioDevice.initialize(-1, null);
            vEngine.reset();
            vEngine.needApply = false;
        }
    }

    protected static native int getAcousticsCaps();

    public static void loadControls(com.maddox.rts.IniFile inifile, java.lang.String s)
    {
        boolean flag = _cur_en;
        _cur_en = true;
        com.maddox.rts.CfgTools.load(sndFlags, inifile, s);
        sndFlags.apply();
        com.maddox.rts.CfgTools.load(npFlags, inifile, s);
        npFlags.apply();
        com.maddox.rts.CfgTools.load(vMaster, inifile, s);
        vMaster.apply();
        com.maddox.rts.CfgTools.load(vMusic, inifile, s);
        vMusic.apply();
        com.maddox.rts.CfgTools.load(vObj, inifile, s);
        vObj.apply();
        com.maddox.rts.CfgTools.load(vVoice, inifile, s);
        vVoice.apply();
        com.maddox.rts.CfgTools.load(musState, inifile, s);
        musState.apply();
        com.maddox.rts.CfgTools.load(musFlags, inifile, s);
        musFlags.apply();
        com.maddox.rts.CfgTools.load(vMode, inifile, s);
        vMode.apply();
        com.maddox.rts.CfgTools.load(vSpk, inifile, s);
        vSpk.apply();
        com.maddox.rts.CfgTools.load(vSR, inifile, s);
        vSR.apply();
        com.maddox.rts.CfgTools.load(vChs, inifile, s);
        vChs.apply();
        com.maddox.rts.CfgTools.load(actLevel, inifile, s);
        com.maddox.rts.CfgTools.load(micLevel, inifile, s);
        _cur_en = flag;
        com.maddox.rts.CfgTools.load(vEngine, inifile, s);
        vEngine.apply();
    }

    public static void saveControls(com.maddox.rts.IniFile inifile, java.lang.String s)
    {
        boolean flag = _cur_en;
        _cur_en = true;
        com.maddox.rts.CfgTools.save(false, sndFlags, inifile, s);
        com.maddox.rts.CfgTools.save(false, npFlags, inifile, s);
        com.maddox.rts.CfgTools.save(false, vMaster, inifile, s);
        com.maddox.rts.CfgTools.save(false, vMusic, inifile, s);
        com.maddox.rts.CfgTools.save(false, vObj, inifile, s);
        com.maddox.rts.CfgTools.save(false, vVoice, inifile, s);
        com.maddox.rts.CfgTools.save(false, musState, inifile, s);
        com.maddox.rts.CfgTools.save(false, musFlags, inifile, s);
        com.maddox.rts.CfgTools.save(false, vEngine, inifile, s);
        com.maddox.rts.CfgTools.save(false, vMode, inifile, s);
        com.maddox.rts.CfgTools.save(false, vSpk, inifile, s);
        com.maddox.rts.CfgTools.save(false, vSR, inifile, s);
        com.maddox.rts.CfgTools.save(false, vChs, inifile, s);
        com.maddox.rts.CfgTools.save(false, actLevel, inifile, s);
        com.maddox.rts.CfgTools.save(false, micLevel, inifile, s);
        _cur_en = flag;
    }

    public static void resetControls()
    {
    }

    public static void setMessageMode(int i)
    {
        com.maddox.sound.AudioDevice.initControls();
    }

    protected static void printVMStat()
    {
        int i = 0;
        int j = 0;
        byte byte0 = 40;
        int k = 30;
        do
        {
            java.lang.String s = com.maddox.sound.AudioDevice.jniVMStat(i, j);
            if(s != null)
            {
                com.maddox.il2.engine.TextScr.output(byte0, k, s);
                k += 20;
                j++;
            } else
            {
                return;
            }
        } while(true);
    }

    protected static native java.lang.String jniFlush();

    protected static native void jniBeginNetPhone();

    protected static native void jniEndNetPhone();

    protected static native java.lang.String jniVMStat(int i, int j);

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
    protected static long startTime = 0L;
    protected static long prevTime = 0L;
    protected static int htg[] = null;
    protected static int htgNum = 0;
    private static boolean fastMute = false;
    public static com.maddox.sound.VGroup vMaster = new VGroup("MasterVolume", 0);
    public static com.maddox.sound.VGroup vMusic = new VGroup("MusicVolume", 1);
    public static com.maddox.sound.VGroup vObj = new VGroup("ObjectVolume", 2);
    public static com.maddox.sound.VGroup vVoice = new VGroup("VoiceVolume", 3);
    public static java.lang.String soundEngines[] = {
        "disable_sound", "directx"
    };
    public static java.lang.String sr[] = {
        "default", "22050", "44100"
    };
    public static java.lang.String chs[] = {
        "default", "8", "16", "32"
    };
    public static java.lang.String soundMode[] = {
        "default", "minimum", "balanced", "full"
    };
    public static java.lang.String speakers[] = {
        "default", "headphones", "desktop_speakers", "quadraphonic", "surround"
    };
    public static com.maddox.sound.CfgIntSound vEngine;
    public static com.maddox.sound.CfgIntSound vSR;
    public static com.maddox.sound.CfgIntSound vChs;
    public static com.maddox.sound.CfgIntSound vMode;
    public static com.maddox.sound.CfgIntSound vSpk;
    public static com.maddox.sound.CfgNpFlags npFlags = new CfgNpFlags();
    public static com.maddox.sound.CfgIntSound actLevel = new CfgIntSound(505, "ActLevel", null, 0);
    public static com.maddox.sound.CfgIntSound micLevel = new CfgIntSound(504, "MicLevel", null, 0);
    public static com.maddox.sound.CfgFlagsSound sndFlags;
    public static com.maddox.sound.CfgMusState musState = new CfgMusState();
    public static com.maddox.sound.CfgMusFlags musFlags = new CfgMusFlags();
    protected static boolean _cur_en = true;
    protected static final int ACAP_GEOMETRY = 1;
    protected static final int ACAP_EAX1 = 2;
    protected static final int ACAP_EAX2 = 4;
    protected static final int ACAP_IAL2 = 8;

    static 
    {
        vEngine = new CfgIntSound(1, "SoundEngine", soundEngines, 0);
        vSR = new CfgIntSound(200, "SamplingRate", sr, 1);
        vChs = new CfgIntSound(201, "NumChannels", chs, 0);
        vMode = new CfgIntSound(202, "SoundMode", soundMode, 0);
        vSpk = new CfgIntSound(203, "Speakers", speakers, 0);
    }
}
