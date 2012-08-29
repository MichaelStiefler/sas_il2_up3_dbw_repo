// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   KeyRecord.java

package com.maddox.rts;

import com.maddox.util.HashMapInt;
import com.maddox.util.SharedTokenizer;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

// Referenced classes of package com.maddox.rts:
//            MsgEvent, HotKeyCmdMouseMove, HotKeyCmdTrackIRAngles, HotKeyCmdMove, 
//            HotKeyCmdRedirect, MsgTimeOut, MsgHotKeyCmdListener, MsgTimeOutListener, 
//            Time, HotKeyCmd, KeyRecordCallback, NetObj, 
//            HotKeyCmdEnv, Keyboard, Mouse, Joy, 
//            SectFile, KeyRecordFinger, RTSConf, HotKeyCmdEnvs, 
//            NetMsgGuaranted, NetChannelOutStream, NetChannelInStream, NetMsgInput, 
//            NetChannel, HotKeyEnv

public class KeyRecord
    implements com.maddox.rts.MsgHotKeyCmdListener, com.maddox.rts.MsgTimeOutListener
{
    class NetKeyRecord extends com.maddox.rts.NetObj
    {

        public void post(com.maddox.rts.MsgEvent msgevent)
        {
            if(!isRecording())
                return;
            if(out == null || out.isDestroying())
                return;
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeInt(msgevent.id);
                if(msgevent.p9 != -1)
                {
                    netmsgguaranted.writeInt(msgevent.p1);
                    netmsgguaranted.writeInt(msgevent.p2);
                    netmsgguaranted.writeInt(msgevent.p3);
                    netmsgguaranted.writeInt(msgevent.p4);
                    netmsgguaranted.writeInt(msgevent.p5);
                    netmsgguaranted.writeInt(msgevent.p6);
                    netmsgguaranted.writeInt(msgevent.p7);
                    netmsgguaranted.writeInt(msgevent.p8);
                    netmsgguaranted.writeInt(msgevent.p9);
                } else
                if(msgevent.p8 != -1)
                {
                    netmsgguaranted.writeInt(msgevent.p1);
                    netmsgguaranted.writeInt(msgevent.p2);
                    netmsgguaranted.writeInt(msgevent.p3);
                    netmsgguaranted.writeInt(msgevent.p4);
                    netmsgguaranted.writeInt(msgevent.p5);
                    netmsgguaranted.writeInt(msgevent.p6);
                    netmsgguaranted.writeInt(msgevent.p7);
                    netmsgguaranted.writeInt(msgevent.p8);
                } else
                if(msgevent.p7 != -1)
                {
                    netmsgguaranted.writeInt(msgevent.p1);
                    netmsgguaranted.writeInt(msgevent.p2);
                    netmsgguaranted.writeInt(msgevent.p3);
                    netmsgguaranted.writeInt(msgevent.p4);
                    netmsgguaranted.writeInt(msgevent.p5);
                    netmsgguaranted.writeInt(msgevent.p6);
                    netmsgguaranted.writeInt(msgevent.p7);
                } else
                if(msgevent.p6 != -1)
                {
                    netmsgguaranted.writeInt(msgevent.p1);
                    netmsgguaranted.writeInt(msgevent.p2);
                    netmsgguaranted.writeInt(msgevent.p3);
                    netmsgguaranted.writeInt(msgevent.p4);
                    netmsgguaranted.writeInt(msgevent.p5);
                    netmsgguaranted.writeInt(msgevent.p6);
                } else
                if(msgevent.p5 != -1)
                {
                    netmsgguaranted.writeInt(msgevent.p1);
                    netmsgguaranted.writeInt(msgevent.p2);
                    netmsgguaranted.writeInt(msgevent.p3);
                    netmsgguaranted.writeInt(msgevent.p4);
                    netmsgguaranted.writeInt(msgevent.p5);
                } else
                if(msgevent.p4 != -1)
                {
                    netmsgguaranted.writeInt(msgevent.p1);
                    netmsgguaranted.writeInt(msgevent.p2);
                    netmsgguaranted.writeInt(msgevent.p3);
                    netmsgguaranted.writeInt(msgevent.p4);
                } else
                if(msgevent.p3 != -1)
                {
                    netmsgguaranted.writeInt(msgevent.p1);
                    netmsgguaranted.writeInt(msgevent.p2);
                    netmsgguaranted.writeInt(msgevent.p3);
                } else
                if(msgevent.p2 != -1)
                {
                    netmsgguaranted.writeInt(msgevent.p1);
                    netmsgguaranted.writeInt(msgevent.p2);
                } else
                {
                    netmsgguaranted.writeInt(msgevent.p1);
                }
                postTo(out, netmsgguaranted);
                out.flush();
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
            }
        }

        public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            if(!isPlaying())
                return true;
            com.maddox.rts.MsgEvent msgevent = new MsgEvent();
            msgevent.time = com.maddox.rts.Time.current();
            msgevent.id = netmsginput.readInt();
            if(netmsginput.available() != 0)
            {
                msgevent.p1 = netmsginput.readInt();
                if(netmsginput.available() != 0)
                {
                    msgevent.p2 = netmsginput.readInt();
                    if(netmsginput.available() != 0)
                    {
                        msgevent.p3 = netmsginput.readInt();
                        if(netmsginput.available() != 0)
                        {
                            msgevent.p4 = netmsginput.readInt();
                            if(netmsginput.available() != 0)
                            {
                                msgevent.p5 = netmsginput.readInt();
                                if(netmsginput.available() != 0)
                                {
                                    msgevent.p6 = netmsginput.readInt();
                                    if(netmsginput.available() != 0)
                                    {
                                        msgevent.p7 = netmsginput.readInt();
                                        if(netmsginput.available() != 0)
                                        {
                                            msgevent.p8 = netmsginput.readInt();
                                            if(netmsginput.available() != 0)
                                            {
                                                msgevent.p9 = netmsginput.readInt();
                                                if(netmsginput.available() != 0);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            hotPlayer.msgTimeOut(msgevent);
            return true;
        }

        public void msgNetNewChannel(com.maddox.rts.NetChannel netchannel)
        {
            if(netchannel instanceof com.maddox.rts.NetChannelOutStream)
                out = (com.maddox.rts.NetChannelOutStream)netchannel;
        }

        public void msgNetDelChannel(com.maddox.rts.NetChannel netchannel)
        {
            if(netchannel == out)
                out = null;
            else
            if((netchannel instanceof com.maddox.rts.NetChannelInStream) && isPlaying() && callBack != null)
                callBack.playRecordedEnded();
        }

        com.maddox.rts.NetChannelOutStream out;

        public NetKeyRecord(int i)
        {
            super(null, i);
        }
    }

    class HotPlayer
        implements com.maddox.rts.MsgTimeOutListener
    {

        public void msgTimeOut(java.lang.Object obj)
        {
            if(hotPlayer != this)
                return;
            com.maddox.rts.MsgEvent msgevent = (com.maddox.rts.MsgEvent)obj;
            com.maddox.rts.HotKeyCmd hotkeycmd = com.maddox.rts.HotKeyCmd.getByRecordedId(msgevent.id);
            if(hotkeycmd == null)
                return;
            if(hotkeycmd instanceof com.maddox.rts.HotKeyCmdMouseMove)
            {
                if(!hotkeycmd.hotKeyCmdEnv().hotKeyEnv().isEnabled())
                {
                    com.maddox.rts.HotKeyCmdMouseMove hotkeycmdmousemove = (com.maddox.rts.HotKeyCmdMouseMove)hotkeycmd;
                    hotkeycmdmousemove._exec(msgevent.p1, msgevent.p2, 0);
                }
            } else
            if(hotkeycmd instanceof com.maddox.rts.HotKeyCmdTrackIRAngles)
            {
                if(!hotkeycmd.hotKeyCmdEnv().hotKeyEnv().isEnabled())
                {
                    com.maddox.rts.HotKeyCmdTrackIRAngles hotkeycmdtrackirangles = (com.maddox.rts.HotKeyCmdTrackIRAngles)hotkeycmd;
                    hotkeycmdtrackirangles._exec((float)msgevent.p1 / 100F, (float)msgevent.p2 / 100F, (float)msgevent.p3 / 100F);
                }
            } else
            if(hotkeycmd instanceof com.maddox.rts.HotKeyCmdMove)
            {
                if(!hotkeycmd.hotKeyCmdEnv().hotKeyEnv().isEnabled())
                {
                    com.maddox.rts.HotKeyCmdMove hotkeycmdmove = (com.maddox.rts.HotKeyCmdMove)hotkeycmd;
                    hotkeycmdmove._exec(msgevent.p1);
                }
            } else
            if(hotkeycmd instanceof com.maddox.rts.HotKeyCmdRedirect)
            {
                if(!hotkeycmd.hotKeyCmdEnv().hotKeyEnv().isEnabled())
                {
                    com.maddox.rts.HotKeyCmdRedirect hotkeycmdredirect = (com.maddox.rts.HotKeyCmdRedirect)hotkeycmd;
                    hotkeycmdredirect._exec(msgevent.p2, msgevent.p3, msgevent.p4, msgevent.p5, msgevent.p6, msgevent.p7, msgevent.p8, msgevent.p9);
                }
            } else
            if(!hotkeycmd.hotKeyCmdEnv().hotKeyEnv().isEnabled() || bFirstHotCmd)
            {
                curPlayArg0 = msgevent.p2;
                curPlayArg1 = msgevent.p3;
                curPlaySArg0 = msgevent.arg0;
                curPlaySArg1 = msgevent.arg1;
                if(bFirstHotCmd && callBack != null)
                {
                    callBack.doFirstHotCmd(true);
                    hotkeycmd._exec(msgevent.p1 == 1);
                    callBack.doFirstHotCmd(false);
                } else
                {
                    hotkeycmd._exec(msgevent.p1 == 1);
                }
                if(bFirstHotCmd && msgevent.p1 == 1)
                    hotkeycmd._exec(false);
                bFirstHotCmd = false;
            }
        }

        public HotPlayer()
        {
            bFirstHotCmd = true;
        }
    }

    class FingerPlayer
        implements com.maddox.rts.MsgTimeOutListener
    {

        public void msgTimeOut(java.lang.Object obj)
        {
            if(fingerCalculator == null)
                return;
            if(fingerPlayer != this)
                return;
            if(obj == null)
            {
                if(bSave)
                {
                    int ai[] = fingerCalculator.calculateFingers();
                    recordLst.add(new MsgEvent(com.maddox.rts.Time.current(), 0, ai, fingersSize));
                    com.maddox.rts.MsgTimeOut.post(com.maddox.rts.Time.current() + (long)fingerCalculator.checkPeriod(), 0x80000001, this, null);
                }
            } else
            if(bPlay)
            {
                com.maddox.rts.MsgEvent msgevent = (com.maddox.rts.MsgEvent)obj;
                fingers[0] = msgevent.p1;
                if(fingersSize > 1)
                {
                    fingers[1] = msgevent.p2;
                    if(fingersSize > 2)
                    {
                        fingers[2] = msgevent.p3;
                        if(fingersSize > 3)
                        {
                            fingers[3] = msgevent.p4;
                            if(fingersSize > 4)
                            {
                                fingers[4] = msgevent.p5;
                                if(fingersSize > 5)
                                {
                                    fingers[5] = msgevent.p6;
                                    if(fingersSize > 6)
                                    {
                                        fingers[6] = msgevent.p7;
                                        if(fingersSize > 7)
                                        {
                                            fingers[7] = msgevent.p8;
                                            if(fingersSize > 8)
                                                fingers[8] = msgevent.p9;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                fingerCalculator.checkFingers(fingers);
            }
        }

        FingerPlayer()
        {
        }
    }


    public void addExcludeCmdEnv(java.lang.String s)
    {
        excludeCmdEnv.put(s, null);
    }

    public void clearListExcludeCmdEnv()
    {
        excludeCmdEnv.clear();
    }

    public void addExcludePrevCmd(int i)
    {
        excludePrevCmd.put(i, null);
    }

    public void setEnablePlayArgs(boolean flag)
    {
        bEnablePlayArgs = flag;
    }

    public boolean isEnablePlayArgs()
    {
        return bEnablePlayArgs || bFirstHotCmd;
    }

    public boolean isExistPlayArgs()
    {
        return (bEnablePlayArgs || bFirstHotCmd) && _isExistPlayArgs();
    }

    public boolean _isExistPlayArgs()
    {
        return curPlayArg0 != -1 || curPlayArg1 != -1;
    }

    public boolean isExistPlaySArgs()
    {
        return (bEnablePlayArgs || bFirstHotCmd) && _isExistPlaySArgs();
    }

    public boolean _isExistPlaySArgs()
    {
        return curPlaySArg0 != null || curPlaySArg1 != null;
    }

    public int getPlayArg0()
    {
        return bEnablePlayArgs ? curPlayArg0 : -1;
    }

    public int getPlayArg1()
    {
        return bEnablePlayArgs ? curPlayArg1 : -1;
    }

    public java.lang.String getPlaySArg0()
    {
        return bEnablePlayArgs ? curPlaySArg0 : null;
    }

    public java.lang.String getPlaySArg1()
    {
        return bEnablePlayArgs ? curPlaySArg1 : null;
    }

    public int _getPlayArg0()
    {
        return curPlayArg0;
    }

    public int _getPlayArg1()
    {
        return curPlayArg1;
    }

    public java.lang.String _getPlaySArg0()
    {
        return curPlaySArg0;
    }

    public java.lang.String _getPlaySArg1()
    {
        return curPlaySArg1;
    }

    public void msgTimeOut(java.lang.Object obj)
    {
        try
        {
            long l = com.maddox.rts.Time.current() + (long)(2 * com.maddox.rts.Time.tickLen());
            long l1 = l;
            boolean flag = playLst.size() > 0;
            for(; playLst.size() > 0; playLst.removeFirst())
            {
                com.maddox.rts.MsgEvent msgevent = (com.maddox.rts.MsgEvent)playLst.getFirst();
                if(msgevent.time > l)
                    break;
                l1 = msgevent.time;
                if(msgevent.id == 0)
                {
                    if(fingerPlayer != null)
                        com.maddox.rts.MsgTimeOut.post(msgevent.time, 0x80000001, fingerPlayer, msgevent);
                } else
                {
                    com.maddox.rts.HotKeyCmd hotkeycmd = com.maddox.rts.HotKeyCmd.getByRecordedId(msgevent.id);
                    if(hotkeycmd != null)
                        com.maddox.rts.MsgTimeOut.post(msgevent.time, hotPlayer, msgevent);
                }
            }

            if(playLst.size() > 0)
            {
                tickMsg.setFlags(24);
                tickMsg.post();
            } else
            if(flag)
            {
                tickMsg.setFlags(0);
                tickMsg.setTime(l1);
                tickMsg.post();
            } else
            if(callBack != null)
                callBack.playRecordedEnded();
        }
        catch(java.lang.Exception exception)
        {
            if(callBack != null)
                callBack.playRecordedEnded();
        }
    }

    public void setCurRecordArgs(com.maddox.rts.NetObj netobj, com.maddox.rts.NetObj netobj1)
    {
        if(netobj != null)
            curRecordArg0 = netobj.idLocal();
        if(netobj1 != null)
            curRecordArg1 = netobj1.idLocal();
    }

    public void setCurRecordArg0(com.maddox.rts.NetObj netobj)
    {
        if(netobj != null)
            curRecordArg0 = netobj.idLocal();
    }

    public void setCurRecordArg1(com.maddox.rts.NetObj netobj)
    {
        if(netobj != null)
            curRecordArg1 = netobj.idLocal();
    }

    public void setCurRecordSArgs(java.lang.String s, java.lang.String s1)
    {
        curRecordSArg0 = s;
        curRecordSArg1 = s1;
    }

    public void setCurRecordSArg0(java.lang.String s)
    {
        curRecordSArg0 = s;
    }

    public void setCurRecordSArg1(java.lang.String s)
    {
        curRecordSArg1 = s;
    }

    public void msgHotKeyCmd(com.maddox.rts.HotKeyCmd hotkeycmd, boolean flag, boolean flag1)
    {
        if(hotkeycmd.recordId() != 0)
        {
            if(excludeCmdEnv.containsKey(hotkeycmd.hotKeyCmdEnv().name()))
                return;
            if(hotkeycmd instanceof com.maddox.rts.HotKeyCmdMouseMove)
            {
                if(!bSave || flag1)
                    return;
                com.maddox.rts.HotKeyCmdMouseMove hotkeycmdmousemove = (com.maddox.rts.HotKeyCmdMouseMove)hotkeycmd;
                saveEvent(new MsgEvent(com.maddox.rts.Time.current(), hotkeycmd.recordId(), hotkeycmdmousemove._dx, hotkeycmdmousemove._dy));
            } else
            if(hotkeycmd instanceof com.maddox.rts.HotKeyCmdTrackIRAngles)
            {
                if(!bSave || flag1)
                    return;
                com.maddox.rts.HotKeyCmdTrackIRAngles hotkeycmdtrackirangles = (com.maddox.rts.HotKeyCmdTrackIRAngles)hotkeycmd;
                saveEvent(new MsgEvent(com.maddox.rts.Time.current(), hotkeycmd.recordId(), (int)(hotkeycmdtrackirangles._yaw * 100F), (int)(hotkeycmdtrackirangles._pitch * 100F), (int)(hotkeycmdtrackirangles._roll * 100F)));
            } else
            if(hotkeycmd instanceof com.maddox.rts.HotKeyCmdMove)
            {
                if(!bSave || flag1)
                    return;
                com.maddox.rts.HotKeyCmdMove hotkeycmdmove = (com.maddox.rts.HotKeyCmdMove)hotkeycmd;
                saveEvent(new MsgEvent(com.maddox.rts.Time.current(), hotkeycmd.recordId(), hotkeycmdmove._mov));
            } else
            if(hotkeycmd instanceof com.maddox.rts.HotKeyCmdRedirect)
            {
                if(!bSave || flag1)
                    return;
                com.maddox.rts.HotKeyCmdRedirect hotkeycmdredirect = (com.maddox.rts.HotKeyCmdRedirect)hotkeycmd;
                saveEvent(new MsgEvent(com.maddox.rts.Time.current(), hotkeycmd.recordId(), hotkeycmdredirect.idRedirect(), hotkeycmdredirect._r[0], hotkeycmdredirect._r[1], hotkeycmdredirect._r[2], hotkeycmdredirect._r[3], hotkeycmdredirect._r[4], hotkeycmdredirect._r[5], hotkeycmdredirect._r[6], hotkeycmdredirect._r[7]));
            } else
            if(flag1)
            {
                curRecordArg0 = -1;
                curRecordArg1 = -1;
                curRecordSArg0 = null;
                curRecordSArg1 = null;
            } else
            {
                com.maddox.rts.MsgEvent msgevent = null;
                if(curRecordArg0 != -1 || curRecordArg1 != -1)
                {
                    if(!excludePrevCmd.containsKey(hotkeycmd.recordId()))
                    {
                        prevRecordId = hotkeycmd.recordId();
                        prevRecordStart = flag ? 1 : 0;
                        prevRecordArg0 = curRecordArg0;
                        prevRecordArg1 = curRecordArg1;
                    }
                    if(bSave)
                        saveEvent(msgevent = new MsgEvent(com.maddox.rts.Time.current(), hotkeycmd.recordId(), flag ? 1 : 0, curRecordArg0, curRecordArg1));
                } else
                if(bSave)
                    saveEvent(msgevent = new MsgEvent(com.maddox.rts.Time.current(), hotkeycmd.recordId(), flag ? 1 : 0));
                if(msgevent != null)
                {
                    msgevent.arg0 = curRecordSArg0;
                    msgevent.arg1 = curRecordSArg1;
                }
            }
        }
    }

    public boolean startPlay(com.maddox.rts.KeyRecordCallback keyrecordcallback)
    {
        callBack = keyrecordcallback;
        com.maddox.rts.Keyboard.adapter()._clear();
        com.maddox.rts.Mouse.adapter()._clear();
        com.maddox.rts.Joy.adapter()._clear();
        hotPlayer = new HotPlayer();
        bPlay = true;
        return bPlay;
    }

    public boolean startPlay(com.maddox.rts.SectFile sectfile, int i, int j, com.maddox.rts.KeyRecordCallback keyrecordcallback)
    {
        callBack = keyrecordcallback;
        bPlay = false;
        playLst.clear();
        int k = sectfile.vars(i);
        long l = 0L;
        Object obj = null;
        for(int i1 = j; i1 < k; i1++)
        {
            java.lang.String s = sectfile.line(i, i1);
            com.maddox.util.SharedTokenizer.set(s);
            if(com.maddox.util.SharedTokenizer.hasMoreTokens())
            {
                com.maddox.rts.MsgEvent msgevent = new MsgEvent();
                long l1 = com.maddox.util.SharedTokenizer.next(0);
                l += l1;
                msgevent.time = l;
                playLst.add(parseEvent(msgevent));
            }
        }

        if(playLst.size() > 0)
        {
            com.maddox.rts.Keyboard.adapter()._clear();
            com.maddox.rts.Mouse.adapter()._clear();
            com.maddox.rts.Joy.adapter()._clear();
            tickMsg.remove();
            tickMsg.setFlags(0);
            tickMsg.setTime(0L);
            tickMsg.post();
            hotPlayer = new HotPlayer();
        }
        bPlay = true;
        return bPlay;
    }

    private com.maddox.rts.MsgEvent parseEvent(com.maddox.rts.MsgEvent msgevent)
    {
        try
        {
            com.maddox.util.SharedTokenizer._nextWord();
            msgevent.id = com.maddox.util.SharedTokenizer._getInt();
            com.maddox.util.SharedTokenizer._nextWord();
            msgevent.p1 = com.maddox.util.SharedTokenizer._getInt();
            com.maddox.util.SharedTokenizer._nextWord();
            msgevent.p2 = com.maddox.util.SharedTokenizer._getInt();
            com.maddox.util.SharedTokenizer._nextWord();
            msgevent.p3 = com.maddox.util.SharedTokenizer._getInt();
            com.maddox.util.SharedTokenizer._nextWord();
            msgevent.p4 = com.maddox.util.SharedTokenizer._getInt();
            com.maddox.util.SharedTokenizer._nextWord();
            msgevent.p5 = com.maddox.util.SharedTokenizer._getInt();
            com.maddox.util.SharedTokenizer._nextWord();
            msgevent.p6 = com.maddox.util.SharedTokenizer._getInt();
            com.maddox.util.SharedTokenizer._nextWord();
            msgevent.p7 = com.maddox.util.SharedTokenizer._getInt();
            com.maddox.util.SharedTokenizer._nextWord();
            msgevent.p8 = com.maddox.util.SharedTokenizer._getInt();
            com.maddox.util.SharedTokenizer._nextWord();
            msgevent.p9 = com.maddox.util.SharedTokenizer._getInt();
        }
        catch(java.lang.Exception exception)
        {
            msgevent.arg0 = com.maddox.util.SharedTokenizer._getString();
            com.maddox.util.SharedTokenizer._nextWord();
            msgevent.arg1 = com.maddox.util.SharedTokenizer._getString();
        }
        return msgevent;
    }

    public void stopPlay()
    {
        bPlay = false;
        callBack = null;
        hotPlayer = null;
        playLst.clear();
    }

    public boolean isPlaying()
    {
        return bPlay;
    }

    public boolean isRecording()
    {
        return bSave;
    }

    public boolean isContainRecorded()
    {
        return recordLst.size() > 0;
    }

    public void clearRecorded()
    {
        recordLst.clear();
    }

    public void clearPrevStates()
    {
        prevRecordId = 0;
    }

    private void saveEvent(com.maddox.rts.MsgEvent msgevent)
    {
        if(bNet)
            net.post(msgevent);
        else
            recordLst.add(msgevent);
    }

    public void startRecordingNet()
    {
        bSave = true;
        bNet = true;
        fingerPlayer = null;
        if(prevRecordId != 0)
        {
            saveEvent(new MsgEvent(com.maddox.rts.Time.current(), prevRecordId, 1, prevRecordArg0, prevRecordArg1));
            saveEvent(new MsgEvent(com.maddox.rts.Time.current(), prevRecordId, 0, prevRecordArg0, prevRecordArg1));
        }
    }

    public void startRecording()
    {
        bSave = true;
        bNet = false;
        if(fingerCalculator != null)
        {
            fingerPlayer = new FingerPlayer();
            com.maddox.rts.MsgTimeOut.post(com.maddox.rts.Time.current(), 0x80000001, fingerPlayer, null);
        }
    }

    public void stopRecording(boolean flag)
    {
        if(flag)
            saveEvent(new MsgEvent(com.maddox.rts.Time.current(), -1, 0));
        bSave = false;
        fingerPlayer = null;
    }

    public void setFingerCalculator(com.maddox.rts.KeyRecordFinger keyrecordfinger)
    {
        fingerCalculator = keyrecordfinger;
        fingersSize = fingerCalculator.countSaveFingers();
    }

    public boolean saveRecorded(com.maddox.rts.SectFile sectfile, int i)
    {
        if(recordLst.size() > 0)
            try
            {
                long l = 0L;
                for(java.util.Iterator iterator = recordLst.iterator(); iterator.hasNext();)
                {
                    com.maddox.rts.MsgEvent msgevent = (com.maddox.rts.MsgEvent)iterator.next();
                    long l1 = msgevent.time - l;
                    l = msgevent.time;
                    java.lang.String s = null;
                    if(msgevent.p9 != -1)
                        s = "" + l1 + " " + msgevent.id + " " + msgevent.p1 + " " + msgevent.p2 + " " + msgevent.p3 + " " + msgevent.p4 + " " + msgevent.p5 + " " + msgevent.p6 + " " + msgevent.p7 + " " + msgevent.p8 + " " + msgevent.p9;
                    else
                    if(msgevent.p8 != -1)
                        s = "" + l1 + " " + msgevent.id + " " + msgevent.p1 + " " + msgevent.p2 + " " + msgevent.p3 + " " + msgevent.p4 + " " + msgevent.p5 + " " + msgevent.p6 + " " + msgevent.p7 + " " + msgevent.p8;
                    else
                    if(msgevent.p7 != -1)
                        s = "" + l1 + " " + msgevent.id + " " + msgevent.p1 + " " + msgevent.p2 + " " + msgevent.p3 + " " + msgevent.p4 + " " + msgevent.p5 + " " + msgevent.p6 + " " + msgevent.p7;
                    else
                    if(msgevent.p6 != -1)
                        s = "" + l1 + " " + msgevent.id + " " + msgevent.p1 + " " + msgevent.p2 + " " + msgevent.p3 + " " + msgevent.p4 + " " + msgevent.p5 + " " + msgevent.p6;
                    else
                    if(msgevent.p5 != -1)
                        s = "" + l1 + " " + msgevent.id + " " + msgevent.p1 + " " + msgevent.p2 + " " + msgevent.p3 + " " + msgevent.p4 + " " + msgevent.p5;
                    else
                    if(msgevent.p4 != -1)
                        s = "" + l1 + " " + msgevent.id + " " + msgevent.p1 + " " + msgevent.p2 + " " + msgevent.p3 + " " + msgevent.p4;
                    else
                    if(msgevent.p3 != -1)
                        s = "" + l1 + " " + msgevent.id + " " + msgevent.p1 + " " + msgevent.p2 + " " + msgevent.p3;
                    else
                    if(msgevent.p2 != -1)
                        s = "" + l1 + " " + msgevent.id + " " + msgevent.p1 + " " + msgevent.p2;
                    else
                        s = "" + l1 + " " + msgevent.id + " " + msgevent.p1;
                    if(msgevent.arg0 == null && msgevent.arg1 == null)
                        sectfile.lineAdd(i, s);
                    else
                    if(msgevent.arg1 == null)
                        sectfile.lineAdd(i, s + " " + msgevent.arg0);
                    else
                        sectfile.lineAdd(i, s + " " + msgevent.arg0 + " " + msgevent.arg1);
                }

            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println("Track file saved failed: " + exception.getMessage());
                return false;
            }
        return true;
    }

    public KeyRecord()
    {
        playLst = new LinkedList();
        recordLst = new LinkedList();
        excludeCmdEnv = new HashMap();
        excludePrevCmd = new HashMapInt();
        bPlay = false;
        bSave = false;
        bNet = false;
        bFirstHotCmd = true;
        fingers = new int[9];
        fingersSize = 0;
        tickMsg = new MsgTimeOut();
        curPlayArg0 = -1;
        curPlayArg1 = -1;
        curPlaySArg0 = null;
        curPlaySArg1 = null;
        bEnablePlayArgs = true;
        prevRecordId = 0;
        prevRecordStart = 0;
        prevRecordArg0 = -1;
        prevRecordArg1 = -1;
        curRecordArg0 = -1;
        curRecordArg1 = -1;
        curRecordSArg0 = null;
        curRecordSArg1 = null;
        tickMsg.setListener(this);
        tickMsg.setNotCleanAfterSend();
        tickMsg.setFlags(24);
        com.maddox.rts.RTSConf.cur.hotKeyCmdEnvs.msgAddListener(this, null);
        net = new NetKeyRecord(9);
    }

    private java.util.LinkedList playLst;
    private java.util.LinkedList recordLst;
    private java.util.HashMap excludeCmdEnv;
    private com.maddox.util.HashMapInt excludePrevCmd;
    private boolean bPlay;
    private boolean bSave;
    private boolean bNet;
    private com.maddox.rts.KeyRecordCallback callBack;
    private boolean bFirstHotCmd;
    private com.maddox.rts.KeyRecordFinger fingerCalculator;
    private com.maddox.rts.FingerPlayer fingerPlayer;
    private int fingers[];
    private int fingersSize;
    private com.maddox.rts.HotPlayer hotPlayer;
    private com.maddox.rts.MsgTimeOut tickMsg;
    private com.maddox.rts.NetKeyRecord net;
    public static final int ArgNONE = -1;
    private int curPlayArg0;
    private int curPlayArg1;
    private java.lang.String curPlaySArg0;
    private java.lang.String curPlaySArg1;
    private boolean bEnablePlayArgs;
    private int prevRecordId;
    private int prevRecordStart;
    private int prevRecordArg0;
    private int prevRecordArg1;
    private int curRecordArg0;
    private int curRecordArg1;
    private java.lang.String curRecordSArg0;
    private java.lang.String curRecordSArg1;















}
