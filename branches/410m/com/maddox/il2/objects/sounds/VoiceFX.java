// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   VoiceFX.java

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

// Referenced classes of package com.maddox.il2.objects.sounds:
//            VoiceBase

public class VoiceFX
{

    public VoiceFX()
    {
        time = 0L;
        voice = -1;
        armyNum = 0;
        country = null;
        phrase = null;
        subFX = new ArrayList();
    }

    protected void playVoice()
    {
        com.maddox.sound.SoundFX soundfx = voices[voice];
        if(soundfx != null)
        {
            for(int i = 0; i < phrase.length; i++)
            {
                java.lang.String s = null;
                if(country != null && countryMap.containsKey(country))
                    s = country;
                else
                    switch(armyNum)
                    {
                    case 1: // '\001'
                        s = "ru";
                        break;

                    case 2: // '\002'
                        s = "de";
                        break;

                    default:
                        return;
                    }
                s = "Speech/" + s + "/Actor" + (voice + 1) + "/" + com.maddox.il2.objects.sounds.VoiceBase.vbStr[phrase[i]];
                soundfx.play(new Sample(s + ".wav", 1));
            }

        }
    }

    public static void tick()
    {
        if(!enabled)
            return;
        long l = com.maddox.rts.Time.real();
        prevVoice = -1;
        curDt = 100;
        if(curSync != null && !voices[curSync.voice].isPlaying())
        {
            long l1 = 100L;
            com.maddox.il2.objects.sounds.VoiceFX voicefx3;
            for(int i1 = -1; curSync.subFX.size() > 0; i1 = voicefx3.voice)
            {
                voicefx3 = (com.maddox.il2.objects.sounds.VoiceFX)curSync.subFX.get(0);
                if(i1 >= 0 && i1 != voicefx3.voice)
                    l1 = 1500 + rnd.nextInt(3500);
                else
                    l1 += 200L;
                voicefx3.time = l + l1;
                async.add(voicefx3);
                curSync.subFX.remove(0);
            }

            curSync = null;
            curFX = null;
        }
        if(curSync == null && queue.size() > 0)
        {
            boolean flag = false;
            curSync = (com.maddox.il2.objects.sounds.VoiceFX)queue.get(0);
            curSync.playVoice();
            queue.remove(0);
            do
            {
                if(queue.size() <= 0)
                    break;
                com.maddox.il2.objects.sounds.VoiceFX voicefx = (com.maddox.il2.objects.sounds.VoiceFX)queue.get(0);
                if(voicefx.voice != curSync.voice)
                    break;
                voicefx.playVoice();
                for(; voicefx.subFX.size() > 0; voicefx.subFX.remove(0))
                {
                    com.maddox.il2.objects.sounds.VoiceFX voicefx1 = (com.maddox.il2.objects.sounds.VoiceFX)voicefx.subFX.get(0);
                    curSync.subFX.add(voicefx1);
                }

                queue.remove(0);
            } while(true);
        }
        if(async.size() > 0)
        {
            int i = 0;
            for(int j = 0; j < 9; j++)
                if(voices[j].isPlaying())
                    i++;

            int k = 0;
label0:
            do
            {
                if(k >= async.size())
                    break;
                com.maddox.il2.objects.sounds.VoiceFX voicefx2 = (com.maddox.il2.objects.sounds.VoiceFX)async.get(k);
                if(i > 2)
                    break;
                if(voicefx2.time < l)
                {
                    voicefx2.playVoice();
                    async.remove(k);
                    i++;
                    do
                    {
                        if(async.size() <= k)
                            continue label0;
                        com.maddox.il2.objects.sounds.VoiceFX voicefx4 = (com.maddox.il2.objects.sounds.VoiceFX)async.get(k);
                        if(voicefx2.voice != voicefx4.voice)
                            continue label0;
                        voicefx4.playVoice();
                        async.remove(k);
                    } while(true);
                }
                k++;
            } while(true);
        }
    }

    public static void play(int i, int j, int k, java.lang.String s, int ai[])
    {
        if(ai == null || ai.length == 0)
            return;
        com.maddox.il2.objects.sounds.VoiceFX voicefx = new VoiceFX();
        j--;
        int l;
        for(l = 0; l < ai.length && ai[l] != 0; l++);
        voicefx.voice = j;
        voicefx.armyNum = k;
        voicefx.country = s;
        if(voicefx.country != null)
            voicefx.country = voicefx.country.toLowerCase();
        voicefx.phrase = new int[l];
        for(int i1 = 0; i1 < l; i1++)
            voicefx.phrase[i1] = ai[i1];

        if(i == 1)
            i = 0;
        if(i == 2)
            async.add(voicefx);
        else
        if(i == 0)
        {
            curFX = voicefx;
            queue.add(voicefx);
        } else
        if(i == 1)
            curFX.subFX.add(voicefx);
        prevVoice = j;
    }

    public static void end()
    {
        for(int i = 0; i < 9; i++)
            if(voices[i] != null)
            {
                voices[i].cancel();
                voices[i].clear();
            }

        queue.clear();
        async.clear();
    }

    public static boolean isEnabled()
    {
        return enabled;
    }

    public static void setEnabled(boolean flag)
    {
        if(preset == null)
            preset = new SoundPreset("voice");
        enabled = flag;
        for(int i = 0; i < 9; i++)
        {
            if(enabled)
            {
                if(voices[i] == null)
                    voices[i] = new SoundFX(preset);
                continue;
            }
            if(voices[i] != null)
            {
                voices[i].cancel();
                voices[i] = null;
            }
        }

    }

    public static final int numVoices = 9;
    protected long time;
    protected int voice;
    protected int armyNum;
    protected java.lang.String country;
    protected int phrase[];
    protected java.util.ArrayList subFX;
    protected static com.maddox.sound.SoundPreset preset = null;
    protected static com.maddox.sound.SoundFX voices[];
    protected static java.util.ArrayList queue = new ArrayList();
    protected static java.util.ArrayList async = new ArrayList();
    protected static com.maddox.il2.objects.sounds.VoiceFX curFX = null;
    protected static com.maddox.il2.objects.sounds.VoiceFX curSync = null;
    protected static boolean enabled = false;
    private static java.util.HashMap countryMap;
    private static java.util.Random rnd = new Random();
    private static final int rndDelay = 3500;
    private static final int minDelay = 1500;
    private static int prevVoice = -1;
    private static int curDt = 100;

    static 
    {
        voices = new com.maddox.sound.SoundFX[9];
        countryMap = new HashMap();
        for(int i = 0; i < 9; i++)
            voices[i] = null;

        countryMap.put("ru".intern(), null);
        countryMap.put("de".intern(), null);
        java.io.File file = new File(com.maddox.rts.HomePath.get(0), "samples/speech");
        if(file != null)
        {
            java.io.File afile[] = file.listFiles();
            if(afile != null)
            {
                for(int j = 0; j < afile.length; j++)
                {
                    if(!afile[j].isDirectory() || afile[j].isHidden())
                        continue;
                    java.lang.String s = afile[j].getName().toLowerCase();
                    if(!countryMap.containsKey(s))
                        countryMap.put(s.intern(), null);
                }

            }
        }
    }
}
