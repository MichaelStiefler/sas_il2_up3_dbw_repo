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

public class CmdMusic extends Cmd
{
  private static final boolean _debug = false;
  protected static SoundFX musFX = null;
  protected static String fileName = null;
  protected static String pathRandName = null;
  protected static float vol = 1.0F;
  protected static Random rnd = new Random();
  protected static Stack stack = new Stack();
  protected static boolean bPlay = false;
  protected static boolean bPlaying = false;
  protected static boolean bList = false;
  public static final String PATH = "PATH";
  public static final String RAND = "RAND";
  public static final String FILE = "FILE";
  public static final String LIST = "LIST";
  public static final String PLAY = "PLAY";
  public static final String STOP = "STOP";
  public static final String BREAK = "BREAK";
  public static final String VOL = "VOL";
  public static final String PUSH = "PUSH";
  public static final String POP = "POP";
  public static final String APPLY = "APPLY";
  private static AudioStream prevMusic = null;
  private static AudioStream curMusic = null;
  private static float faderIncr = 0.02F;
  private static float fader = 1.0F;
  private static String[] list = null;
  private static int index = 0;

  public CmdMusic()
  {
    this.param.put("PATH", null);
    this.param.put("RAND", null);
    this.param.put("FILE", null);
    this.param.put("LIST", null);
    this.param.put("PLAY", null);
    this.param.put("STOP", null);
    this.param.put("BREAK", null);
    this.param.put("VOL", null);
    this.param.put("PUSH", null);
    this.param.put("POP", null);
    this.param.put("APPLY", null);
    this._properties.put("NAME", "music");
    this._levelAccess = 1;
    if (musFX == null)
      musFX = new SoundFX("cmdmusic");
  }

  private static void setFile(String paramString, boolean paramBoolean)
  {
    if ((fileName != null) && (paramString != null) && (fileName.compareToIgnoreCase(paramString) == 0))
      return;
    fileName = paramString;
    bPlay = true;
    bPlaying = false;
    if (prevMusic != null)
    {
      prevMusic.cancel();
      prevMusic.release();
      prevMusic = null;
    }
    if (fileName == null)
    {
      if (curMusic != null)
      {
        curMusic.cancel();
        curMusic.release();
        curMusic = null;
      }
      musFX.cancel();
    }
    else {
      bPlaying = false;
      if (musFX != null)
      {
        Sample localSample = null;
        if (bList)
          localSample = new Sample((String)null, fileName);
        else
          localSample = new Sample(fileName);
        if (!localSample.exists())
        {
          System.out.println("The sample " + fileName + " not found");
          localSample = null;
          list = null;
          return;
        }
        localSample.setInfinite(!paramBoolean);
        prevMusic = curMusic;
        fader = 1.0F;
        curMusic = localSample.get();
        curMusic.setVolume(1.0F);
        curMusic.play();
        musFX.add(curMusic);
        musFX.play();
        if (prevMusic != null)
          prevMusic.stop();
        bPlaying = true;
        apply();
      }
    }
  }

  public static void tick()
  {
    if (prevMusic != null)
      if (fader > faderIncr)
      {
        fader -= faderIncr;
        prevMusic.setVolume(fader);
      }
      else {
        prevMusic.cancel();
        prevMusic.release();
        prevMusic = null;
      }
    if (list != null)
    {
      if ((curMusic != null) && (!curMusic.isPlaying()) && (playState()))
      {
        curMusic.cancel();
        curMusic.release();
        curMusic = null;
      }
      if ((curMusic == null) && (playState()))
      {
        index += 1;
        if (index >= list.length)
          index = 0;
        fileName = null;
        setFile(list[index], true);
      }
    }
  }

  public static boolean isPathEnabled(String paramString)
  {
    int i = -1;
    if (paramString == null)
      return true;
    if (paramString.compareToIgnoreCase("music/takeoff") == 0) {
      i = 0;
    }
    else if (paramString.compareToIgnoreCase("music/inflight") == 0) {
      i = 1;
    }
    else if (paramString.compareToIgnoreCase("music/crash") == 0)
      i = 2;
    else
      return true;
    SoundFlags localSoundFlags = (SoundFlags)CfgTools.get("MusState");
    if (localSoundFlags == null) {
      return false;
    }
    return localSoundFlags.get(i);
  }

  public static void setPath(String paramString, boolean paramBoolean)
  {
    if (paramString == null)
      return;
    if ((paramBoolean) && (pathRandName != null) && (paramString.compareToIgnoreCase(pathRandName) == 0))
      return;
    File localFile = new File("./samples/" + paramString);
    File[] arrayOfFile = localFile.listFiles(new WFileFilter());
    if ((arrayOfFile == null) || (arrayOfFile.length < 1))
    {
      System.out.println("warning: no files : " + paramString);
      pathRandName = null;
      list = null;
      setFile(null, true);
      return;
    }
    pathRandName = paramString;
    if (arrayOfFile.length == 1)
    {
      StringBuffer localStringBuffer1 = new StringBuffer();
      localStringBuffer1.append(paramString);
      localStringBuffer1.append("/");
      localStringBuffer1.append(arrayOfFile[0].getName());
      int j = localStringBuffer1.length();
      if (j < 5)
        System.out.println("ERROR: invalid filename : " + localStringBuffer1);
      else
        setFile(localStringBuffer1.toString(), false);
      list = null;
    }
    else {
      list = new String[arrayOfFile.length];
      for (int i = 0; i < arrayOfFile.length; i++)
      {
        StringBuffer localStringBuffer2 = new StringBuffer();
        localStringBuffer2.append(paramString);
        localStringBuffer2.append("/");
        localStringBuffer2.append(arrayOfFile[i].getName());
        int k = localStringBuffer2.length();
        if (k < 5)
        {
          System.out.println("ERROR: invalid filename : " + localStringBuffer2);
          list = null;
          return;
        }
        list[i] = localStringBuffer2.toString();
      }

      index = 0;
      setFile(list[0], true);
    }
  }

  public static void setList(Map paramMap)
  {
    if (Cmd.nargs(paramMap, "LIST") == 0)
      return;
    list = new String[Cmd.nargs(paramMap, "LIST")];
    for (int i = 0; i < list.length; i++) {
      list[i] = Cmd.arg(paramMap, "LIST", i);
    }
    index = 0;
    setFile(list[0], true);
  }

  public static void setVolume(float paramFloat)
  {
    vol = paramFloat;
  }

  public static boolean playState()
  {
    if (!isPathEnabled(pathRandName))
      return false;
    SoundFlags localSoundFlags = (SoundFlags)CfgTools.get("MusFlags");
    if (localSoundFlags == null) {
      return false;
    }
    return localSoundFlags.get(0);
  }

  public static void play()
  {
    bPlay = true;
    apply();
  }

  public static void stop()
  {
    bPlay = false;
    apply();
  }

  public static void toggle(boolean paramBoolean)
  {
    if (paramBoolean) {
      curMusic.setVolume(1.0F);
    }
    else
    {
      curMusic.setVolume(0.0F);
    }
  }

  public static void apply()
  {
    if ((playState()) && (bPlay))
    {
      if (!bPlaying)
      {
        if (musFX != null)
          musFX.play();
        bPlaying = true;
      }
    }
    else if (bPlaying)
    {
      if (musFX != null)
        musFX.cancel();
      bPlaying = false;
    }
  }

  public static void cancel()
  {
    if (musFX != null)
      musFX.cancel();
  }

  public static void push()
  {
    PlayInfo localPlayInfo = null;
    if (bList)
      localPlayInfo = new PlayInfo(list);
    else
      localPlayInfo = new PlayInfo(pathRandName != null ? pathRandName : fileName, pathRandName != null);
    stack.push(localPlayInfo);
  }

  public static void pop()
  {
    PlayInfo localPlayInfo = null;
    if (stack.empty())
      System.out.println("ERROR: stack is empty.");
    else
      localPlayInfo = (PlayInfo)stack.pop();
    if (localPlayInfo != null)
      if (localPlayInfo.list != null)
      {
        bList = true;
        list = localPlayInfo.list;
        index = 0;
        setFile(list[0], true);
      }
      else if (localPlayInfo.isPath)
      {
        bList = false;
        list = null;
        setPath(localPlayInfo.objName, true);
      }
      else {
        bList = false;
        pathRandName = null;
        list = null;
        setFile(localPlayInfo.objName, false);
      }
  }

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    int i = 0;
    if (paramMap.isEmpty())
    {
      System.out.println("  music  : " + fileName);
      System.out.println("  volume : " + vol);
      System.out.println("  state  : " + (bPlaying ? "PLAYING" : "STOPPED"));
    }
    else if (paramMap.containsKey("_$$"))
    {
      System.out.println("Unknown command :" + Cmd.arg(paramMap, "_$$", 0));
    }
    else {
      if (paramMap.containsKey("PLAY"))
        play();
      if (paramMap.containsKey("STOP"))
        stop();
      if (paramMap.containsKey("BREAK"))
        cancel();
      if (paramMap.containsKey("PUSH"))
        push();
      if (paramMap.containsKey("POP"))
        pop();
      if (paramMap.containsKey("APPLY"))
        apply();
      if (paramMap.containsKey("PATH"))
      {
        if (Cmd.arg(paramMap, "PATH", 0) == null)
        {
          System.out.println("ERROR: path name expected");
        }
        else {
          bList = false;
          setPath(Cmd.arg(paramMap, "PATH", 0), false);
        }
      }
      else if (paramMap.containsKey("RAND"))
      {
        if (Cmd.arg(paramMap, "RAND", 0) == null)
        {
          System.out.println("ERROR: path name expected");
        }
        else {
          bList = false;
          setPath(Cmd.arg(paramMap, "RAND", 0), true);
        }
      }
      else if (paramMap.containsKey("FILE"))
      {
        if (Cmd.arg(paramMap, "FILE", 0) == null)
        {
          System.out.println("ERROR: file name expected");
        }
        else {
          pathRandName = null;
          bList = false;
          setFile(Cmd.arg(paramMap, "FILE", 0) + ".wav", false);
        }
      }
      else if (paramMap.containsKey("LIST"))
        if (Cmd.arg(paramMap, "LIST", 0) == null)
        {
          System.out.println("ERROR: list names expected");
        }
        else {
          bList = true;
          setList(paramMap);
        }
      if (paramMap.containsKey("VOL"))
      {
        String str = Cmd.arg(paramMap, "PLAY", 0);
        if (str == null)
        {
          System.out.println("ERROR: volume gain (0..1) expected");
        }
        else {
          float f = Float.parseFloat(str);
          if ((f < 0.0F) || (f > 1.0F))
            System.out.println("ERROR: value must be between 0.0 - 1.0");
          else
            setVolume(f);
        }
      }
    }
    return CmdEnv.RETURN_OK;
  }

  static class WFileFilter
    implements FileFilter
  {
    public boolean accept(File paramFile)
    {
      if ((!paramFile.isFile()) || (paramFile.isHidden()))
        return false;
      String str1 = paramFile.getName();
      if (str1 == null)
        return false;
      int i = str1.length();
      if (i < 5)
        return false;
      String str2 = str1.substring(i - 3);
      if ((str2.compareToIgnoreCase("wav") != 0) && (str2.compareToIgnoreCase("mp3") != 0))
        return false;
      return str1.compareToIgnoreCase("empty") != 0;
    }
  }

  static class PlayInfo
  {
    public String objName;
    public boolean isPath;
    public String[] list;

    public PlayInfo(String paramString, boolean paramBoolean)
    {
      this.objName = paramString;
      this.isPath = paramBoolean;
      this.list = null;
    }

    public PlayInfo(String[] paramArrayOfString)
    {
      this.objName = null;
      this.isPath = false;
      this.list = paramArrayOfString;
    }
  }
}