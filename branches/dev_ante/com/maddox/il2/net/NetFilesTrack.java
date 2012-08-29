package com.maddox.il2.net;

import com.maddox.il2.game.Mission;
import com.maddox.rts.Finger;
import com.maddox.rts.InOutStreams;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetObj;
import com.maddox.rts.net.NetFileRequest;
import com.maddox.rts.net.NetFileServerDef;
import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapInt;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map.Entry;

public class NetFilesTrack
{
  private HashMapExt entryMap;
  private int iosFileNum;

  public NetFilesTrack()
  {
    this.entryMap = new HashMapExt();
    this.iosFileNum = 0;
  }

  public static void recordFile(NetFileServerDef paramNetFileServerDef, NetUser paramNetUser, String paramString1, String paramString2)
  {
    if (!NetMissionTrack.isRecording()) return;
    NetMissionTrack.netFilesTrackRecording._recordFile(paramNetFileServerDef, paramNetUser, paramString1, paramString2);
  }

  public void _recordFile(NetFileServerDef paramNetFileServerDef, NetUser paramNetUser, String paramString1, String paramString2)
  {
    Entry localEntry = new Entry(paramNetFileServerDef.idLocal(), paramNetUser.idLocal(), paramString1);
    if (this.entryMap.containsKey(localEntry))
      return;
    if (paramString1.equalsIgnoreCase(paramString2))
      paramString2 = paramNetFileServerDef.primaryPath() + "/" + paramString2;
    else
      paramString2 = paramNetFileServerDef.alternativePath() + "/" + paramString2;
    String str = "netFile" + this.iosFileNum++;
    long l = record(paramString2, str);
    if (l != 0L) {
      localEntry.iosFileName = str;
      localEntry.finger = l;
      this.entryMap.put(localEntry, null);
    }
  }

  private long record(String paramString1, String paramString2) {
    try {
      FileInputStream localFileInputStream = new FileInputStream(paramString1);
      OutputStream localOutputStream = NetMissionTrack.io.createStream(paramString2);
      byte[] arrayOfByte = new byte[1024];
      long l = 0L;
      while (true) {
        int i = localFileInputStream.available();
        if (i == 0) break;
        if (i >= arrayOfByte.length)
          i = arrayOfByte.length;
        else {
          arrayOfByte = new byte[i];
        }
        localFileInputStream.read(arrayOfByte, 0, i);
        l = Finger.incLong(l, arrayOfByte);
        localOutputStream.write(arrayOfByte, 0, i);
      }
      localFileInputStream.close();
      localOutputStream.close();
      return l;
    } catch (Exception localException) {
      printDebug(localException);
    }return 0L;
  }

  public void startRecording()
  {
    Mission.cur().recordNetFiles();
    if (!NetMissionTrack.isPlaying())
      ((NetUser)NetEnv.host()).recordNetFiles();
    List localList = NetEnv.hosts();
    int i = localList.size();
    for (int j = 0; j < i; j++) {
      NetUser localNetUser = (NetUser)localList.get(j);
      localNetUser.recordNetFiles();
    }
  }

  public void stopRecording() {
    try {
      PrintStream localPrintStream = new PrintStream(NetMissionTrack.io.createStream("NetFilesDir"));
      Map.Entry localEntry = this.entryMap.nextEntry(null);
      while (localEntry != null) {
        Entry localEntry1 = (Entry)localEntry.getKey();
        localPrintStream.println(localEntry1.idServer);
        localPrintStream.println(localEntry1.idOwner);
        localPrintStream.println(localEntry1.ownerFileName);
        localPrintStream.println(localEntry1.iosFileName);
        localPrintStream.println(localEntry1.finger);
        localEntry = this.entryMap.nextEntry(localEntry);
      }
      localPrintStream.close();
    } catch (Exception localException) {
      printDebug(localException);
    }
    this.entryMap.clear();
  }

  public static boolean existFile(NetFileRequest paramNetFileRequest)
  {
    if (!NetMissionTrack.isPlaying())
      return false;
    return NetMissionTrack.netFilesTrackPlaying._existFile(paramNetFileRequest);
  }
  public static String getLocalFileName(NetFileServerDef paramNetFileServerDef, NetObj paramNetObj, String paramString) {
    if (!NetMissionTrack.isPlaying())
      return null;
    return NetMissionTrack.netFilesTrackPlaying._getLocalFileName(paramNetFileServerDef, paramNetObj, paramString);
  }

  private String _getLocalFileName(NetFileServerDef paramNetFileServerDef, NetObj paramNetObj, String paramString) {
    Entry localEntry = new Entry(paramNetFileServerDef.idLocal(), paramNetObj.idRemote(), paramString);
    localEntry = (Entry)this.entryMap.get(localEntry);
    if (localEntry == null)
      return null;
    return localEntry.finger + ".bmp";
  }

  public boolean _existFile(NetFileRequest paramNetFileRequest) {
    Entry localEntry = new Entry(((NetFileServerDef)paramNetFileRequest.server()).idLocal(), paramNetFileRequest.owner().idRemote(), paramNetFileRequest.ownerFileName());

    localEntry = (Entry)this.entryMap.get(localEntry);
    if (localEntry == null)
      return false;
    paramNetFileRequest.setLocalFileName(localEntry.finger + ".bmp");
    return true;
  }

  private boolean extract(InOutStreams paramInOutStreams, String paramString1, String paramString2) {
    try {
      InputStream localInputStream = paramInOutStreams.openStream(paramString1);
      FileOutputStream localFileOutputStream = new FileOutputStream(paramString2);
      byte[] arrayOfByte = new byte[1024];
      while (true) {
        int i = localInputStream.available();
        if (i == 0) break;
        if (i > arrayOfByte.length)
          i = arrayOfByte.length;
        localInputStream.read(arrayOfByte, 0, i);
        localFileOutputStream.write(arrayOfByte, 0, i);
      }
      localInputStream.close();
      localFileOutputStream.close();
      return true;
    } catch (Exception localException) {
      printDebug(localException);
    }return false;
  }

  public void startPlaying(InOutStreams paramInOutStreams)
  {
    try {
      InputStream localInputStream = paramInOutStreams.openStream("NetFilesDir");
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localInputStream));
      while (true) {
        String str1 = localBufferedReader.readLine();
        if ((str1 == null) || (str1.length() == 0))
          break;
        int i = Integer.parseInt(str1);
        int j = Integer.parseInt(localBufferedReader.readLine());
        String str2 = localBufferedReader.readLine();
        String str3 = localBufferedReader.readLine();
        long l = Long.parseLong(localBufferedReader.readLine());
        NetFileServerDef localNetFileServerDef = (NetFileServerDef)NetEnv.cur().objects.get(i);

        if (extract(paramInOutStreams, str3, localNetFileServerDef.alternativePath() + "/" + l + ".bmp")) {
          Entry localEntry = new Entry(i, j, str2);
          localEntry.iosFileName = str3;
          localEntry.finger = l;
          this.entryMap.put(localEntry, localEntry);
        }
      }
      localInputStream.close();
    } catch (Exception localException) {
      printDebug(localException);
    }
  }

  public void stopPlaying() {
    this.entryMap.clear();
  }

  static void printDebug(Exception paramException)
  {
    System.out.println(paramException.getMessage());
    paramException.printStackTrace();
  }

  static class Entry
  {
    public int idServer;
    public int idOwner;
    public String ownerFileName;
    public String iosFileName;
    public long finger;
    private int hash;

    public boolean equals(Object paramObject)
    {
      if (paramObject == null) return false;
      if (!(paramObject instanceof Entry)) return false;
      return (this.idServer == ((Entry)paramObject).idServer) && (this.idOwner == ((Entry)paramObject).idOwner) && (this.ownerFileName.equals(((Entry)paramObject).ownerFileName));
    }

    public int hashCode() {
      return this.hash;
    }
    public Entry(int paramInt1, int paramInt2, String paramString) { this.idServer = paramInt1;
      this.idOwner = paramInt2;
      this.ownerFileName = paramString;
      this.hash = (paramInt1 + paramInt2 + Finger.Int(paramString));
    }
  }
}