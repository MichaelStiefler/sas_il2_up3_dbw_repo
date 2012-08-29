// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetFilesTrack.java

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
import java.util.Map;

// Referenced classes of package com.maddox.il2.net:
//            NetUser, NetMissionTrack

public class NetFilesTrack
{
    static class Entry
    {

        public boolean equals(java.lang.Object obj)
        {
            if(obj == null)
                return false;
            if(!(obj instanceof com.maddox.il2.net.Entry))
                return false;
            else
                return idServer == ((com.maddox.il2.net.Entry)obj).idServer && idOwner == ((com.maddox.il2.net.Entry)obj).idOwner && ownerFileName.equals(((com.maddox.il2.net.Entry)obj).ownerFileName);
        }

        public int hashCode()
        {
            return hash;
        }

        public int idServer;
        public int idOwner;
        public java.lang.String ownerFileName;
        public java.lang.String iosFileName;
        public long finger;
        private int hash;

        public Entry(int i, int j, java.lang.String s)
        {
            idServer = i;
            idOwner = j;
            ownerFileName = s;
            hash = i + j + com.maddox.rts.Finger.Int(s);
        }
    }


    public NetFilesTrack()
    {
        entryMap = new HashMapExt();
        iosFileNum = 0;
    }

    public static void recordFile(com.maddox.rts.net.NetFileServerDef netfileserverdef, com.maddox.il2.net.NetUser netuser, java.lang.String s, java.lang.String s1)
    {
        if(!com.maddox.il2.net.NetMissionTrack.isRecording())
        {
            return;
        } else
        {
            com.maddox.il2.net.NetMissionTrack.netFilesTrackRecording._recordFile(netfileserverdef, netuser, s, s1);
            return;
        }
    }

    public void _recordFile(com.maddox.rts.net.NetFileServerDef netfileserverdef, com.maddox.il2.net.NetUser netuser, java.lang.String s, java.lang.String s1)
    {
        com.maddox.il2.net.Entry entry = new Entry(netfileserverdef.idLocal(), netuser.idLocal(), s);
        if(entryMap.containsKey(entry))
            return;
        if(s.equalsIgnoreCase(s1))
            s1 = netfileserverdef.primaryPath() + "/" + s1;
        else
            s1 = netfileserverdef.alternativePath() + "/" + s1;
        java.lang.String s2 = "netFile" + iosFileNum++;
        long l = record(s1, s2);
        if(l != 0L)
        {
            entry.iosFileName = s2;
            entry.finger = l;
            entryMap.put(entry, null);
        }
    }

    private long record(java.lang.String s, java.lang.String s1)
    {
        long l;
        java.io.FileInputStream fileinputstream = new FileInputStream(s);
        java.io.OutputStream outputstream = com.maddox.il2.net.NetMissionTrack.io.createStream(s1);
        byte abyte0[] = new byte[1024];
        l = 0L;
        do
        {
            int i = fileinputstream.available();
            if(i == 0)
                break;
            if(i >= abyte0.length)
                i = abyte0.length;
            else
                abyte0 = new byte[i];
            fileinputstream.read(abyte0, 0, i);
            l = com.maddox.rts.Finger.incLong(l, abyte0);
            outputstream.write(abyte0, 0, i);
        } while(true);
        fileinputstream.close();
        outputstream.close();
        return l;
        java.lang.Exception exception;
        exception;
        com.maddox.il2.net.NetFilesTrack.printDebug(exception);
        return 0L;
    }

    public void startRecording()
    {
        com.maddox.il2.game.Mission.cur().recordNetFiles();
        if(!com.maddox.il2.net.NetMissionTrack.isPlaying())
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).recordNetFiles();
        java.util.List list = com.maddox.rts.NetEnv.hosts();
        int i = list.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)list.get(j);
            netuser.recordNetFiles();
        }

    }

    public void stopRecording()
    {
        try
        {
            java.io.PrintStream printstream = new PrintStream(com.maddox.il2.net.NetMissionTrack.io.createStream("NetFilesDir"));
            for(java.util.Map.Entry entry = entryMap.nextEntry(null); entry != null; entry = entryMap.nextEntry(entry))
            {
                com.maddox.il2.net.Entry entry1 = (com.maddox.il2.net.Entry)entry.getKey();
                printstream.println(entry1.idServer);
                printstream.println(entry1.idOwner);
                printstream.println(entry1.ownerFileName);
                printstream.println(entry1.iosFileName);
                printstream.println(entry1.finger);
            }

            printstream.close();
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.net.NetFilesTrack.printDebug(exception);
        }
        entryMap.clear();
    }

    public static boolean existFile(com.maddox.rts.net.NetFileRequest netfilerequest)
    {
        if(!com.maddox.il2.net.NetMissionTrack.isPlaying())
            return false;
        else
            return com.maddox.il2.net.NetMissionTrack.netFilesTrackPlaying._existFile(netfilerequest);
    }

    public static java.lang.String getLocalFileName(com.maddox.rts.net.NetFileServerDef netfileserverdef, com.maddox.rts.NetObj netobj, java.lang.String s)
    {
        if(!com.maddox.il2.net.NetMissionTrack.isPlaying())
            return null;
        else
            return com.maddox.il2.net.NetMissionTrack.netFilesTrackPlaying._getLocalFileName(netfileserverdef, netobj, s);
    }

    private java.lang.String _getLocalFileName(com.maddox.rts.net.NetFileServerDef netfileserverdef, com.maddox.rts.NetObj netobj, java.lang.String s)
    {
        com.maddox.il2.net.Entry entry = new Entry(netfileserverdef.idLocal(), netobj.idRemote(), s);
        entry = (com.maddox.il2.net.Entry)entryMap.get(entry);
        if(entry == null)
            return null;
        else
            return entry.finger + ".bmp";
    }

    public boolean _existFile(com.maddox.rts.net.NetFileRequest netfilerequest)
    {
        com.maddox.il2.net.Entry entry = new Entry(((com.maddox.rts.net.NetFileServerDef)netfilerequest.server()).idLocal(), netfilerequest.owner().idRemote(), netfilerequest.ownerFileName());
        entry = (com.maddox.il2.net.Entry)entryMap.get(entry);
        if(entry == null)
        {
            return false;
        } else
        {
            netfilerequest.setLocalFileName(entry.finger + ".bmp");
            return true;
        }
    }

    private boolean extract(com.maddox.rts.InOutStreams inoutstreams, java.lang.String s, java.lang.String s1)
    {
        java.io.InputStream inputstream = inoutstreams.openStream(s);
        java.io.FileOutputStream fileoutputstream = new FileOutputStream(s1);
        byte abyte0[] = new byte[1024];
        do
        {
            int i = inputstream.available();
            if(i == 0)
                break;
            if(i > abyte0.length)
                i = abyte0.length;
            inputstream.read(abyte0, 0, i);
            fileoutputstream.write(abyte0, 0, i);
        } while(true);
        inputstream.close();
        fileoutputstream.close();
        return true;
        java.lang.Exception exception;
        exception;
        com.maddox.il2.net.NetFilesTrack.printDebug(exception);
        return false;
    }

    public void startPlaying(com.maddox.rts.InOutStreams inoutstreams)
    {
        try
        {
            java.io.InputStream inputstream = inoutstreams.openStream("NetFilesDir");
            java.io.BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
            do
            {
                java.lang.String s = bufferedreader.readLine();
                if(s == null || s.length() == 0)
                    break;
                int i = java.lang.Integer.parseInt(s);
                int j = java.lang.Integer.parseInt(bufferedreader.readLine());
                java.lang.String s1 = bufferedreader.readLine();
                java.lang.String s2 = bufferedreader.readLine();
                long l = java.lang.Long.parseLong(bufferedreader.readLine());
                com.maddox.rts.net.NetFileServerDef netfileserverdef = (com.maddox.rts.net.NetFileServerDef)com.maddox.rts.NetEnv.cur().objects.get(i);
                if(extract(inoutstreams, s2, netfileserverdef.alternativePath() + "/" + l + ".bmp"))
                {
                    com.maddox.il2.net.Entry entry = new Entry(i, j, s1);
                    entry.iosFileName = s2;
                    entry.finger = l;
                    entryMap.put(entry, entry);
                }
            } while(true);
            inputstream.close();
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.net.NetFilesTrack.printDebug(exception);
        }
    }

    public void stopPlaying()
    {
        entryMap.clear();
    }

    static void printDebug(java.lang.Exception exception)
    {
        java.lang.System.out.println(exception.getMessage());
        exception.printStackTrace();
    }

    private com.maddox.util.HashMapExt entryMap;
    private int iosFileNum;
}
