// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetFileServerDef.java

package com.maddox.rts.net;

import com.maddox.rts.Compress;
import com.maddox.rts.Destroy;
import com.maddox.rts.Finger;
import com.maddox.rts.HomePath;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.util.HashMapExt;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

// Referenced classes of package com.maddox.rts.net:
//            NetFileServer, NetFileRequest, NetFileTransport

public class NetFileServerDef extends com.maddox.rts.NetObj
    implements com.maddox.rts.net.NetFileServer
{
    public class Out
        implements com.maddox.rts.Destroy
    {

        public boolean openFile(java.lang.String s)
        {
            if(f != null)
                return true;
            try
            {
                f = new FileInputStream(com.maddox.rts.HomePath.toFileSystemName(s, 0));
                if(ptr > 0)
                    f.skip(ptr);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
                f = null;
                return false;
            }
            if(compressMethod() != 0)
                buf = new byte[compressBlockSize()];
            return true;
        }

        public boolean isDestroyed()
        {
            return state == -1;
        }

        public void destroy()
        {
            if(isDestroyed())
                return;
            state = -1;
            if(f != null)
            {
                try
                {
                    f.close();
                }
                catch(java.lang.Exception exception)
                {
                    com.maddox.rts.NetObj.printDebug(exception);
                }
                f = null;
            }
        }

        public java.lang.String toString()
        {
            switch(state)
            {
            case 0: // '\0'
                return "state Init";

            case 1: // '\001'
                return "state WaitCommand";

            case 2: // '\002'
                return "state Send Finger";

            case 3: // '\003'
                return "state Send Header";

            case 4: // '\004'
                return "state Transfer";

            case -1: 
                return "state Destroy";
            }
            return "state UNKNOWN";
        }

        public static final int ST_INIT = 0;
        public static final int ST_WAIT_COMMAND = 1;
        public static final int ST_SEND_FINGER = 2;
        public static final int ST_SEND_HEADER = 3;
        public static final int ST_SEND_DATA = 4;
        public static final int ST_DESTROY = -1;
        public int state;
        public int size;
        public int shortSize;
        public long finger;
        public long shortFinger;
        public int ptr;
        public byte buf[];
        public int bufSize;
        public int bufCur;
        public java.io.FileInputStream f;

        public Out()
        {
            state = 0;
        }
    }

    public class In
        implements com.maddox.rts.Destroy
    {

        public boolean createFile()
        {
            try
            {
                if(localSize > 0)
                    f = new FileOutputStream(com.maddox.rts.HomePath.toFileSystemName(fileName, 0), true);
                else
                    f = new FileOutputStream(com.maddox.rts.HomePath.toFileSystemName(fileName, 0));
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
                return false;
            }
            if(compressMethod() != 0)
                buf = new byte[compressBlockSize()];
            return true;
        }

        public boolean isDestroyed()
        {
            return state == -1;
        }

        public void destroy()
        {
            if(isDestroyed())
                return;
            state = -1;
            if(f != null)
            {
                try
                {
                    f.close();
                }
                catch(java.lang.Exception exception)
                {
                    com.maddox.rts.NetObj.printDebug(exception);
                }
                f = null;
            }
        }

        public java.lang.String toString()
        {
            java.lang.String s;
            if(fileName != null)
                s = fileName + " ";
            else
                s = "";
            switch(state)
            {
            case 0: // '\0'
                return s + "state Init";

            case 1: // '\001'
                return s + "state Request Finger";

            case 2: // '\002'
                return s + "state Answer Finger";

            case 3: // '\003'
                return s + "state Request File";

            case 4: // '\004'
                return s + "state Transfer";
            }
            return s + "state UNKNOWN";
        }

        public static final int ST_INIT = 0;
        public static final int ST_REQUEST_FINGER = 1;
        public static final int ST_ANSWER_FINGER = 2;
        public static final int ST_REQUEST_FILE = 3;
        public static final int ST_TRANSFER = 4;
        public static final int ST_DESTROY = -1;
        public int state;
        public java.lang.String fileName;
        public java.lang.String localFileName;
        public int size;
        public int localSize;
        public long finger;
        public long localFinger;
        public byte buf[];
        public int bufSize;
        public int bufFill;
        public java.io.FileOutputStream f;

        public In()
        {
            state = 0;
        }
    }

    public class NetFile
    {

        public int hashCode()
        {
            return owner.hashCode() + ownerFileName.hashCode();
        }

        public boolean equals(java.lang.Object obj)
        {
            if(obj == this)
                return true;
            if(obj == null)
                return false;
            if(!(obj instanceof com.maddox.rts.net.NetFile))
            {
                return false;
            } else
            {
                com.maddox.rts.net.NetFile netfile = (com.maddox.rts.net.NetFile)obj;
                return netfile.owner == owner && netfile.ownerFileName.equals(ownerFileName);
            }
        }

        public com.maddox.rts.NetObj owner;
        public java.lang.String ownerFileName;
        public long finger;
        public java.lang.String localFileName;

        public NetFile(com.maddox.rts.NetObj netobj, java.lang.String s)
        {
            this(netobj, s, 0L, null);
        }

        public NetFile(com.maddox.rts.NetObj netobj, java.lang.String s, long l, java.lang.String s1)
        {
            owner = netobj;
            ownerFileName = s;
            finger = l;
            localFileName = s1;
        }
    }


    public int compressMethod()
    {
        return 2;
    }

    public int compressBlockSize()
    {
        return 32768;
    }

    public java.lang.String primaryPath()
    {
        return null;
    }

    public java.lang.String alternativePath()
    {
        return null;
    }

    protected byte[] commonBuf(int i)
    {
        if(commonBuf == null || commonBuf.length < i)
            commonBuf = new byte[i];
        return commonBuf;
    }

    public void destroyIn(com.maddox.rts.net.NetFileRequest netfilerequest)
    {
        com.maddox.rts.net.NetFileRequest netfilerequest1 = netfilerequest;
        if(netfilerequest1._serverIn == null || !(netfilerequest1._serverIn instanceof com.maddox.rts.net.In))
        {
            return;
        } else
        {
            ((com.maddox.rts.net.In)netfilerequest1._serverIn).destroy();
            return;
        }
    }

    public void destroyOut(com.maddox.rts.net.NetFileRequest netfilerequest)
    {
        com.maddox.rts.net.NetFileRequest netfilerequest1 = netfilerequest;
        if(netfilerequest1._serverOut == null || !(netfilerequest1._serverIn instanceof com.maddox.rts.net.Out))
        {
            return;
        } else
        {
            ((com.maddox.rts.net.Out)netfilerequest1._serverOut).destroy();
            return;
        }
    }

    public java.lang.String getStateInInfo(com.maddox.rts.net.NetFileRequest netfilerequest)
    {
        com.maddox.rts.net.NetFileRequest netfilerequest1 = netfilerequest;
        if(netfilerequest1._serverIn == null || !(netfilerequest1._serverIn instanceof com.maddox.rts.net.In))
            return null;
        else
            return ((com.maddox.rts.net.In)netfilerequest1._serverIn).toString();
    }

    public java.lang.String getStateOutInfo(com.maddox.rts.net.NetFileRequest netfilerequest)
    {
        com.maddox.rts.net.NetFileRequest netfilerequest1 = netfilerequest;
        if(netfilerequest1._serverOut == null || !(netfilerequest1._serverOut instanceof com.maddox.rts.net.Out))
            return null;
        else
            return ((com.maddox.rts.net.Out)netfilerequest1._serverOut).toString();
    }

    public boolean isStateDataTransfer(com.maddox.rts.net.NetFileRequest netfilerequest)
    {
        com.maddox.rts.net.NetFileRequest netfilerequest1 = netfilerequest;
        if(netfilerequest1._serverOut == null || !(netfilerequest1._serverOut instanceof com.maddox.rts.net.Out))
        {
            return false;
        } else
        {
            com.maddox.rts.net.Out out = (com.maddox.rts.net.Out)netfilerequest1._serverOut;
            return out.state == 3 || out.state == 4;
        }
    }

    public void doRequest(com.maddox.rts.net.NetFileRequest netfilerequest)
    {
        com.maddox.rts.net.NetFileRequest netfilerequest1 = netfilerequest;
        com.maddox.rts.net.NetFile netfile = new NetFile(netfilerequest1.owner(), netfilerequest1.ownerFileName());
        com.maddox.rts.net.NetFile netfile1 = (com.maddox.rts.net.NetFile)fileCache.get(netfile);
        if(netfile1 != null)
        {
            netfilerequest1.setComplete(1.0F);
            netfilerequest1.setLocalFileName(netfile1.localFileName);
            netfilerequest1.setState(0);
            initServerOutData(netfilerequest1);
            netfilerequest1.doAnswer();
            return;
        }
        if(netfilerequest1.owner().isMaster())
        {
            java.lang.String s = filePrimaryName(netfilerequest1.ownerFileName());
            if(isFileExist(s))
            {
                makeRequestComplete(netfilerequest1, netfilerequest1.ownerFileName(), com.maddox.rts.Finger.file(0L, s, -1));
                netfilerequest1.doAnswer();
            } else
            {
                netfilerequest1.setState(-2);
                netfilerequest1.doAnswer();
            }
        } else
        if(netfilerequest1.owner().masterChannel() instanceof com.maddox.rts.NetChannelInStream)
        {
            netfilerequest1.setState(-2);
            netfilerequest1.doAnswer();
        } else
        {
            netfilerequest1.setState(1);
            netfilerequest1._serverIn = new In();
            com.maddox.rts.NetEnv.cur().fileTransport.doRequest(netfilerequest1);
        }
    }

    protected void makeRequestComplete(com.maddox.rts.net.NetFileRequest netfilerequest, java.lang.String s, long l)
    {
        netfilerequest.setLocalFileName(s);
        com.maddox.rts.net.NetFile netfile = new NetFile(netfilerequest.owner(), netfilerequest.ownerFileName(), l, netfilerequest.localFileName());
        fileCache.put(netfile, netfile);
        netfilerequest.setComplete(1.0F);
        netfilerequest.setState(0);
        initServerOutData(netfilerequest);
    }

    public void doCancel(com.maddox.rts.net.NetFileRequest netfilerequest)
    {
        com.maddox.rts.net.NetFileRequest netfilerequest1 = netfilerequest;
        if(netfilerequest1.isEnded())
            return;
        netfilerequest1.setState(-4);
        netfilerequest1.doAnswer();
        if(netfilerequest1.owner().isDestroyed() || netfilerequest1.owner().isMaster())
        {
            return;
        } else
        {
            com.maddox.rts.NetEnv.cur().fileTransport.doCancel(netfilerequest1);
            return;
        }
    }

    public void doAnswer(com.maddox.rts.net.NetFileRequest netfilerequest)
    {
        initServerOutData(netfilerequest);
        netfilerequest.doAnswer();
    }

    private void initServerOutData(com.maddox.rts.net.NetFileRequest netfilerequest)
    {
        if(netfilerequest.state() != 0)
            return;
        if(netfilerequest.client() == null)
            return;
        if(!(netfilerequest.client() instanceof com.maddox.rts.net.NetFileTransport.Answer))
            return;
        if((netfilerequest.client() instanceof com.maddox.rts.Destroy) && ((com.maddox.rts.Destroy)netfilerequest.client()).isDestroyed())
        {
            return;
        } else
        {
            netfilerequest._serverOut = new Out();
            return;
        }
    }

    protected com.maddox.rts.net.In netIn(com.maddox.rts.net.NetFileRequest netfilerequest)
    {
        if(netfilerequest._serverIn == null)
            return null;
        else
            return (com.maddox.rts.net.In)netfilerequest._serverIn;
    }

    protected com.maddox.rts.net.Out netOut(com.maddox.rts.net.NetFileRequest netfilerequest)
    {
        if(netfilerequest._serverOut == null)
            return null;
        else
            return (com.maddox.rts.net.Out)netfilerequest._serverOut;
    }

    public void getRequestData(com.maddox.rts.net.NetFileRequest netfilerequest, com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        com.maddox.rts.net.NetFileRequest netfilerequest1 = netfilerequest;
        com.maddox.rts.net.Out out = netOut(netfilerequest1);
        if(out == null)
            return;
        if(out.state != 1)
            return;
        int i = netmsginput.readInt();
        if(netmsginput.available() == 0)
        {
            out.ptr = i;
            if(compressMethod() != 0)
                out.state = 3;
            else
                out.state = 4;
        } else
        {
            long l = netmsginput.readLong();
            out.shortSize = i;
            out.shortFinger = com.maddox.rts.Finger.file(0L, netfilerequest1.localFullFileName(primaryPath(), alternativePath()), i);
            out.state = 2;
        }
    }

    public int getAnswerState(com.maddox.rts.net.NetFileRequest netfilerequest, int i)
    {
        com.maddox.rts.net.NetFileRequest netfilerequest1 = netfilerequest;
        com.maddox.rts.net.Out out = netOut(netfilerequest1);
        if(out == null)
            return 0;
        switch(out.state)
        {
        case -1: 
        case 1: // '\001'
        default:
            return 0;

        case 0: // '\0'
            out.size = fileLength(netfilerequest1.localFullFileName(primaryPath(), alternativePath()));
            if(out.size == 0)
            {
                return 4;
            } else
            {
                out.finger = com.maddox.rts.Finger.file(0L, netfilerequest1.localFullFileName(primaryPath(), alternativePath()), -1);
                return 2;
            }

        case 2: // '\002'
            return 2;

        case 3: // '\003'
            if(out.bufSize > 0)
                out.ptr += out.buf.length;
            if(out.ptr >= out.size)
            {
                out.ptr = out.size;
                return 3;
            }
            if(!out.openFile(netfilerequest1.localFullFileName(primaryPath(), alternativePath())))
                return 4;
            try
            {
                int j = out.buf.length;
                int k = out.size - out.ptr;
                if(j > k)
                    j = k;
                out.f.read(out.buf, 0, j);
                out.bufSize = com.maddox.rts.Compress.code(compressMethod(), out.buf, j);
                out.bufCur = 0;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
                return 4;
            }
            return 2;

        case 4: // '\004'
            break;
        }
        if(out.size == out.ptr)
            return 3;
        if(!out.openFile(netfilerequest1.localFullFileName(primaryPath(), alternativePath())))
            return 4;
        if(compressMethod() == 0)
        {
            out.bufSize = i;
            if(out.bufSize > out.size - out.ptr)
                out.bufSize = out.size - out.ptr;
            try
            {
                out.f.read(commonBuf(out.bufSize), 0, out.bufSize);
                out.ptr += out.bufSize;
            }
            catch(java.lang.Exception exception1)
            {
                com.maddox.rts.NetObj.printDebug(exception1);
                return 4;
            }
        }
        return 1;
    }

    public boolean sendAnswerData(com.maddox.rts.net.NetFileRequest netfilerequest, com.maddox.rts.NetMsgGuaranted netmsgguaranted, int i, int j)
        throws java.io.IOException
    {
        com.maddox.rts.net.NetFileRequest netfilerequest1 = netfilerequest;
        com.maddox.rts.net.Out out = netOut(netfilerequest1);
        if(out == null)
            return false;
        switch(out.state)
        {
        case -1: 
        case 1: // '\001'
        default:
            return false;

        case 0: // '\0'
            netmsgguaranted.writeInt(j);
            netmsgguaranted.writeInt(out.size);
            netmsgguaranted.writeLong(out.finger);
            out.state = 1;
            return true;

        case 2: // '\002'
            netmsgguaranted.writeInt(j);
            netmsgguaranted.writeInt(out.shortSize);
            netmsgguaranted.writeLong(out.shortFinger);
            out.state = 1;
            return true;

        case 3: // '\003'
            netmsgguaranted.writeInt(j);
            netmsgguaranted.writeInt(out.bufSize);
            out.state = 4;
            return true;

        case 4: // '\004'
            netmsgguaranted.writeInt(j);
            break;
        }
        if(compressMethod() != 0)
        {
            int k = i;
            if(k > out.bufSize - out.bufCur)
                k = out.bufSize - out.bufCur;
            netmsgguaranted.write(out.buf, out.bufCur, k);
            out.bufCur += k;
            if(out.bufCur == out.bufSize)
                out.state = 3;
            return true;
        } else
        {
            netmsgguaranted.write(commonBuf(out.bufSize), 0, out.bufSize);
            return true;
        }
    }

    public int getAnswerData(com.maddox.rts.net.NetFileRequest netfilerequest, com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        com.maddox.rts.net.NetFileRequest netfilerequest1 = netfilerequest;
        com.maddox.rts.net.In in = netIn(netfilerequest1);
        if(in == null)
            return 1;
        if(in.state != 4)
            return 1;
        int i = netmsginput.available();
        if(compressMethod() != 0)
        {
            netmsginput.read(in.buf, in.bufFill, i);
            in.bufFill += i;
            if(in.bufFill == in.bufSize)
            {
                int j = compressBlockSize();
                if(in.size - in.localSize < j)
                    j = in.size - in.localSize;
                if(in.bufSize < j)
                    com.maddox.rts.Compress.decode(compressMethod(), in.buf, in.bufSize);
                try
                {
                    in.f.write(in.buf, 0, j);
                }
                catch(java.lang.Exception exception1)
                {
                    com.maddox.rts.NetObj.printDebug(exception1);
                    return -3;
                }
                in.localSize += j;
                in.bufFill = 0;
            }
        } else
        {
            netmsginput.read(commonBuf(i), 0, i);
            try
            {
                in.f.write(commonBuf(i), 0, i);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
                return -3;
            }
            in.localSize += i;
        }
        netfilerequest1.setComplete((float)in.localSize / (float)in.size);
        if(in.localSize == in.size)
        {
            in.destroy();
            makeRequestComplete(netfilerequest1, in.localFileName, in.finger);
            return 0;
        } else
        {
            return 1;
        }
    }

    public int getAnswerExtData(com.maddox.rts.net.NetFileRequest netfilerequest, com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        com.maddox.rts.net.NetFileRequest netfilerequest1 = netfilerequest;
        com.maddox.rts.net.In in = netIn(netfilerequest1);
        if(in == null)
            return 1;
        switch(in.state)
        {
        case -1: 
        case 1: // '\001'
        case 3: // '\003'
        default:
            break;

        case 0: // '\0'
            in.size = netmsginput.readInt();
            in.finger = netmsginput.readLong();
            java.lang.String s = filePrimaryName(netfilerequest1.ownerFileName());
            int i = fileLength(s);
            if(i > 0 && in.size == i)
            {
                long l = com.maddox.rts.Finger.file(0L, s, -1);
                if(l == in.finger)
                {
                    makeRequestComplete(netfilerequest1, netfilerequest1.ownerFileName(), in.finger);
                    return 0;
                }
            }
            in.fileName = fileAlternativeName(netfilerequest1.ownerFileName(), in.finger, true);
            in.localFileName = fileAlternativeName(netfilerequest1.ownerFileName(), in.finger, false);
            i = fileLength(in.fileName);
            if(i > 0)
                if(i == in.size)
                {
                    long l1 = com.maddox.rts.Finger.file(0L, in.fileName, -1);
                    if(l1 == in.finger)
                    {
                        makeRequestComplete(netfilerequest1, in.localFileName, in.finger);
                        return 0;
                    }
                } else
                {
                    if(i < in.size)
                    {
                        in.localSize = i;
                        in.localFinger = com.maddox.rts.Finger.file(0L, in.fileName, -1);
                        in.state = 1;
                    }
                    break;
                }
            in.state = 3;
            if(!in.createFile())
                return -3;
            break;

        case 2: // '\002'
            int j = netmsginput.readInt();
            long l2 = netmsginput.readLong();
            if(j != in.localSize || l2 != in.localFinger)
                in.localSize = 0;
            in.state = 3;
            if(!in.createFile())
                return -3;
            break;

        case 4: // '\004'
            if(compressMethod() != 0)
                in.bufSize = netmsginput.readInt();
            break;
        }
        return 1;
    }

    public boolean sendRequestData(com.maddox.rts.net.NetFileRequest netfilerequest, com.maddox.rts.NetMsgGuaranted netmsgguaranted, int i, int j)
        throws java.io.IOException
    {
        com.maddox.rts.net.NetFileRequest netfilerequest1 = netfilerequest;
        com.maddox.rts.net.In in = netIn(netfilerequest1);
        if(in == null)
            return false;
        switch(in.state)
        {
        case -1: 
        case 0: // '\0'
        case 2: // '\002'
        case 4: // '\004'
        default:
            return false;

        case 1: // '\001'
            netmsgguaranted.writeInt(j);
            netmsgguaranted.writeInt(in.localSize);
            netmsgguaranted.writeLong(in.localFinger);
            in.state = 2;
            return true;

        case 3: // '\003'
            netmsgguaranted.writeInt(j);
            break;
        }
        netmsgguaranted.writeInt(in.localSize);
        in.state = 4;
        return true;
    }

    public void msgNetDelChannel(com.maddox.rts.NetChannel netchannel)
    {
        java.util.Map.Entry entry = fileCache.nextEntry(null);
        java.util.ArrayList arraylist = new ArrayList();
        for(; entry != null; entry = fileCache.nextEntry(entry))
        {
            com.maddox.rts.net.NetFile netfile = (com.maddox.rts.net.NetFile)entry.getKey();
            if(netfile.owner.isDestroyed() || netfile.owner.masterChannel() == netchannel)
                arraylist.add(netfile);
        }

        for(int i = 0; i < arraylist.size(); i++)
            fileCache.remove(arraylist.get(i));

        arraylist.clear();
    }

    public void msgNetNewChannel(com.maddox.rts.NetChannel netchannel)
    {
    }

    protected boolean isFileExist(java.lang.String s)
    {
        java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName(s, 0));
        return file.exists();
    }

    protected int fileLength(java.lang.String s)
    {
        java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName(s, 0));
        return (int)file.length();
    }

    protected java.lang.String filePrimaryName(java.lang.String s)
    {
        java.lang.String s1 = primaryPath();
        if(s1 == null || s1.length() == 0)
            return s;
        if(s == null || s.length() == 0)
            return s;
        char c = s1.charAt(s1.length() - 1);
        if(c == '/' || c == '\\')
        {
            char c1 = s.charAt(0);
            if(c1 == '/' || c1 == '\\')
                return s1.substring(0, s1.length() - 1) + s;
            else
                return s1 + s;
        } else
        {
            return s1 + '/' + s;
        }
    }

    protected java.lang.String fileAlternativeName(java.lang.String s, long l, boolean flag)
    {
        java.lang.String s1 = null;
        if(flag)
        {
            s1 = alternativePath();
            if(s1 != null && s1.length() == 0)
                s1 = null;
        }
        java.lang.String s2 = null;
        int i = s.lastIndexOf('.');
        if(i >= 0)
            s2 = s.substring(i);
        if(s1 != null)
        {
            char c = s1.charAt(s1.length() - 1);
            if(c != '\\' && c != '/')
                if(s2 != null)
                    return s1 + "/" + l + s2;
                else
                    return s1 + "/" + l;
            if(s2 != null)
                return s1 + l + s2;
            else
                return s1 + l;
        }
        if(s2 != null)
            return "" + l + s2;
        else
            return "" + l;
    }

    public NetFileServerDef(int i)
    {
        super(null, i);
        fileCache = new HashMapExt();
    }

    protected com.maddox.util.HashMapExt fileCache;
    private byte commonBuf[];




}
