// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdMusic.java

package com.maddox.sound;

import com.maddox.rts.CfgTools;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import java.io.File;
import java.io.FileFilter;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.TreeMap;

// Referenced classes of package com.maddox.sound:
//            SoundFX, Sample, SoundFlags, AudioStream

public class CmdMusic extends com.maddox.rts.Cmd
{
    static class PlayInfo
    {

        public java.lang.String objName;
        public boolean isPath;
        public java.lang.String list[];

        public PlayInfo(java.lang.String s, boolean flag)
        {
            objName = s;
            isPath = flag;
            list = null;
        }

        public PlayInfo(java.lang.String as[])
        {
            objName = null;
            isPath = false;
            list = as;
        }
    }

    static class WFileFilter
        implements java.io.FileFilter
    {

        public boolean accept(java.io.File file)
        {
            if(!file.isFile() || file.isHidden())
                return false;
            java.lang.String s = file.getName();
            if(s == null)
                return false;
            int i = s.length();
            if(i < 5)
                return false;
            java.lang.String s1 = s.substring(i - 3);
            if(s1.compareToIgnoreCase("wav") != 0 && s1.compareToIgnoreCase("mp3") != 0)
                return false;
            return s.compareToIgnoreCase("empty") != 0;
        }

        WFileFilter()
        {
        }
    }


    public CmdMusic()
    {
        param.put("PATH", null);
        param.put("RAND", null);
        param.put("FILE", null);
        param.put("LIST", null);
        param.put("PLAY", null);
        param.put("STOP", null);
        param.put("BREAK", null);
        param.put("VOL", null);
        param.put("PUSH", null);
        param.put("POP", null);
        param.put("APPLY", null);
        _properties.put("NAME", "music");
        _levelAccess = 1;
        if(musFX == null)
            musFX = new SoundFX("cmdmusic");
    }

    private static void setFile(java.lang.String s, boolean flag)
    {
        if(fileName != null && s != null && fileName.compareToIgnoreCase(s) == 0)
            return;
        fileName = s;
        bPlay = true;
        bPlaying = false;
        if(prevMusic != null)
        {
            prevMusic.cancel();
            prevMusic.release();
            prevMusic = null;
        }
        if(fileName == null)
        {
            if(curMusic != null)
            {
                curMusic.cancel();
                curMusic.release();
                curMusic = null;
            }
            musFX.cancel();
        } else
        {
            bPlaying = false;
            if(musFX != null)
            {
                com.maddox.sound.Sample sample = null;
                if(bList)
                    sample = new Sample((java.lang.String)null, fileName);
                else
                    sample = new Sample(fileName);
                if(!sample.exists())
                {
                    java.lang.System.out.println("The sample " + fileName + " not found");
                    sample = null;
                    list = null;
                    return;
                }
                sample.setInfinite(!flag);
                prevMusic = curMusic;
                fader = 1.0F;
                curMusic = sample.get();
                curMusic.setVolume(1.0F);
                curMusic.play();
                musFX.add(curMusic);
                musFX.play();
                if(prevMusic != null)
                    prevMusic.stop();
                bPlaying = true;
                com.maddox.sound.CmdMusic.apply();
            }
        }
    }

    public static void tick()
    {
        if(prevMusic != null)
            if(fader > faderIncr)
            {
                fader -= faderIncr;
                prevMusic.setVolume(fader);
            } else
            {
                prevMusic.cancel();
                prevMusic.release();
                prevMusic = null;
            }
        if(list != null)
        {
            if(curMusic != null && !curMusic.isPlaying() && com.maddox.sound.CmdMusic.playState())
            {
                curMusic.cancel();
                curMusic.release();
                curMusic = null;
            }
            if(curMusic == null && com.maddox.sound.CmdMusic.playState())
            {
                index++;
                if(index >= list.length)
                    index = 0;
                fileName = null;
                com.maddox.sound.CmdMusic.setFile(list[index], true);
            }
        }
    }

    public static boolean isPathEnabled(java.lang.String s)
    {
        byte byte0 = -1;
        if(s == null)
            return true;
        if(s.compareToIgnoreCase("music/takeoff") == 0)
            byte0 = 0;
        else
        if(s.compareToIgnoreCase("music/inflight") == 0)
            byte0 = 1;
        else
        if(s.compareToIgnoreCase("music/crash") == 0)
            byte0 = 2;
        else
            return true;
        com.maddox.sound.SoundFlags soundflags = (com.maddox.sound.SoundFlags)com.maddox.rts.CfgTools.get("MusState");
        if(soundflags == null)
            return false;
        else
            return soundflags.get(byte0);
    }

    public static void setPath(java.lang.String s, boolean flag)
    {
        if(s == null)
            return;
        if(flag && pathRandName != null && s.compareToIgnoreCase(pathRandName) == 0)
            return;
        java.io.File file = new File("./samples/" + s);
        java.io.File afile[] = file.listFiles(new WFileFilter());
        if(afile == null || afile.length < 1)
        {
            java.lang.System.out.println("warning: no files : " + s);
            pathRandName = null;
            list = null;
            com.maddox.sound.CmdMusic.setFile(null, true);
            return;
        }
        pathRandName = s;
        if(afile.length == 1)
        {
            java.lang.StringBuffer stringbuffer = new StringBuffer();
            stringbuffer.append(s);
            stringbuffer.append("/");
            stringbuffer.append(afile[0].getName());
            int j = stringbuffer.length();
            if(j < 5)
                java.lang.System.out.println("ERROR: invalid filename : " + stringbuffer);
            else
                com.maddox.sound.CmdMusic.setFile(stringbuffer.toString(), false);
            list = null;
        } else
        {
            list = new java.lang.String[afile.length];
            for(int i = 0; i < afile.length; i++)
            {
                java.lang.StringBuffer stringbuffer1 = new StringBuffer();
                stringbuffer1.append(s);
                stringbuffer1.append("/");
                stringbuffer1.append(afile[i].getName());
                int k = stringbuffer1.length();
                if(k < 5)
                {
                    java.lang.System.out.println("ERROR: invalid filename : " + stringbuffer1);
                    list = null;
                    return;
                }
                list[i] = stringbuffer1.toString();
            }

            index = 0;
            com.maddox.sound.CmdMusic.setFile(list[0], true);
        }
    }

    public static void setList(java.util.Map map)
    {
        if(com.maddox.rts.Cmd.nargs(map, "LIST") == 0)
            return;
        list = new java.lang.String[com.maddox.rts.Cmd.nargs(map, "LIST")];
        for(int i = 0; i < list.length; i++)
            list[i] = com.maddox.rts.Cmd.arg(map, "LIST", i);

        index = 0;
        com.maddox.sound.CmdMusic.setFile(list[0], true);
    }

    public static void setVolume(float f)
    {
        vol = f;
    }

    public static boolean playState()
    {
        if(!com.maddox.sound.CmdMusic.isPathEnabled(pathRandName))
            return false;
        com.maddox.sound.SoundFlags soundflags = (com.maddox.sound.SoundFlags)com.maddox.rts.CfgTools.get("MusFlags");
        if(soundflags == null)
            return false;
        else
            return soundflags.get(0);
    }

    public static void play()
    {
        bPlay = true;
        com.maddox.sound.CmdMusic.apply();
    }

    public static void stop()
    {
        bPlay = false;
        com.maddox.sound.CmdMusic.apply();
    }

    public static void apply()
    {
        if(com.maddox.sound.CmdMusic.playState() && bPlay)
        {
            if(!bPlaying)
            {
                if(musFX != null)
                    musFX.play();
                bPlaying = true;
            }
        } else
        if(bPlaying)
        {
            if(musFX != null)
                musFX.cancel();
            bPlaying = false;
        }
    }

    public static void cancel()
    {
        if(musFX != null)
            musFX.cancel();
    }

    public static void push()
    {
        com.maddox.sound.PlayInfo playinfo = null;
        if(bList)
            playinfo = new PlayInfo(list);
        else
            playinfo = new PlayInfo(pathRandName != null ? pathRandName : fileName, pathRandName != null);
        stack.push(playinfo);
    }

    public static void pop()
    {
        com.maddox.sound.PlayInfo playinfo = null;
        if(stack.empty())
            java.lang.System.out.println("ERROR: stack is empty.");
        else
            playinfo = (com.maddox.sound.PlayInfo)stack.pop();
        if(playinfo != null)
            if(playinfo.list != null)
            {
                bList = true;
                list = playinfo.list;
                index = 0;
                com.maddox.sound.CmdMusic.setFile(list[0], true);
            } else
            if(playinfo.isPath)
            {
                bList = false;
                list = null;
                com.maddox.sound.CmdMusic.setPath(playinfo.objName, true);
            } else
            {
                bList = false;
                pathRandName = null;
                list = null;
                com.maddox.sound.CmdMusic.setFile(playinfo.objName, false);
            }
    }

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        boolean flag = false;
        if(map.isEmpty())
        {
            java.lang.System.out.println("  music  : " + fileName);
            java.lang.System.out.println("  volume : " + vol);
            java.lang.System.out.println("  state  : " + (bPlaying ? "PLAYING" : "STOPPED"));
        } else
        if(map.containsKey("_$$"))
        {
            java.lang.System.out.println("Unknown command :" + com.maddox.rts.Cmd.arg(map, "_$$", 0));
        } else
        {
            if(map.containsKey("PLAY"))
                com.maddox.sound.CmdMusic.play();
            if(map.containsKey("STOP"))
                com.maddox.sound.CmdMusic.stop();
            if(map.containsKey("BREAK"))
                com.maddox.sound.CmdMusic.cancel();
            if(map.containsKey("PUSH"))
                com.maddox.sound.CmdMusic.push();
            if(map.containsKey("POP"))
                com.maddox.sound.CmdMusic.pop();
            if(map.containsKey("APPLY"))
                com.maddox.sound.CmdMusic.apply();
            if(map.containsKey("PATH"))
            {
                if(com.maddox.rts.Cmd.arg(map, "PATH", 0) == null)
                {
                    java.lang.System.out.println("ERROR: path name expected");
                } else
                {
                    bList = false;
                    com.maddox.sound.CmdMusic.setPath(com.maddox.rts.Cmd.arg(map, "PATH", 0), false);
                }
            } else
            if(map.containsKey("RAND"))
            {
                if(com.maddox.rts.Cmd.arg(map, "RAND", 0) == null)
                {
                    java.lang.System.out.println("ERROR: path name expected");
                } else
                {
                    bList = false;
                    com.maddox.sound.CmdMusic.setPath(com.maddox.rts.Cmd.arg(map, "RAND", 0), true);
                }
            } else
            if(map.containsKey("FILE"))
            {
                if(com.maddox.rts.Cmd.arg(map, "FILE", 0) == null)
                {
                    java.lang.System.out.println("ERROR: file name expected");
                } else
                {
                    pathRandName = null;
                    bList = false;
                    com.maddox.sound.CmdMusic.setFile(com.maddox.rts.Cmd.arg(map, "FILE", 0) + ".wav", false);
                }
            } else
            if(map.containsKey("LIST"))
                if(com.maddox.rts.Cmd.arg(map, "LIST", 0) == null)
                {
                    java.lang.System.out.println("ERROR: list names expected");
                } else
                {
                    bList = true;
                    com.maddox.sound.CmdMusic.setList(map);
                }
            if(map.containsKey("VOL"))
            {
                java.lang.String s = com.maddox.rts.Cmd.arg(map, "PLAY", 0);
                if(s == null)
                {
                    java.lang.System.out.println("ERROR: volume gain (0..1) expected");
                } else
                {
                    float f = java.lang.Float.parseFloat(s);
                    if(f < 0.0F || f > 1.0F)
                        java.lang.System.out.println("ERROR: value must be between 0.0 - 1.0");
                    else
                        com.maddox.sound.CmdMusic.setVolume(f);
                }
            }
        }
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    private static final boolean _debug = false;
    protected static com.maddox.sound.SoundFX musFX = null;
    protected static java.lang.String fileName = null;
    protected static java.lang.String pathRandName = null;
    protected static float vol = 1.0F;
    protected static java.util.Random rnd = new Random();
    protected static java.util.Stack stack = new Stack();
    protected static boolean bPlay = false;
    protected static boolean bPlaying = false;
    protected static boolean bList = false;
    public static final java.lang.String PATH = "PATH";
    public static final java.lang.String RAND = "RAND";
    public static final java.lang.String FILE = "FILE";
    public static final java.lang.String LIST = "LIST";
    public static final java.lang.String PLAY = "PLAY";
    public static final java.lang.String STOP = "STOP";
    public static final java.lang.String BREAK = "BREAK";
    public static final java.lang.String VOL = "VOL";
    public static final java.lang.String PUSH = "PUSH";
    public static final java.lang.String POP = "POP";
    public static final java.lang.String APPLY = "APPLY";
    private static com.maddox.sound.AudioStream prevMusic = null;
    private static com.maddox.sound.AudioStream curMusic = null;
    private static float faderIncr = 0.02F;
    private static float fader = 1.0F;
    private static java.lang.String list[] = null;
    private static int index = 0;

}
