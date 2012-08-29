// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Console.java

package com.maddox.rts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Referenced classes of package com.maddox.rts:
//            ConsoleOut, ConsoleLogFile, SFSReader, MsgTimeOut, 
//            CmdEnv, ConsoleOutPrint, ConsolePrintStream, MsgKeyboardListener, 
//            MsgTimeOutListener, Time, HomePath, Keyboard, 
//            MsgAddListener, ConsoleListener

public class Console
    implements com.maddox.rts.MsgKeyboardListener, com.maddox.rts.MsgTimeOutListener
{
    public static class Exec
    {

        public void doExec(java.lang.String s)
        {
        }

        public java.lang.String getPrompt()
        {
            return "";
        }

        public Exec()
        {
        }
    }


    public static boolean isTypeTimeInLogFile()
    {
        return bTypeTimeInLogFile;
    }

    public static void setTypeTimeInLogFile(boolean flag)
    {
        bTypeTimeInLogFile = flag;
    }

    public void setListenerChanges(com.maddox.rts.ConsoleListener consolelistener)
    {
        listenerChanges = consolelistener;
    }

    public boolean isActive()
    {
        return bActive;
    }

    public void activate(boolean flag)
    {
        if(bActive != flag)
        {
            if(flag)
            {
                bStartTimePaused = com.maddox.rts.Time.isPaused();
                if(bPaused && !bStartTimePaused && com.maddox.rts.Time.isEnableChangePause())
                    com.maddox.rts.Time.setPause(true);
            } else
            if(bPaused && !bStartTimePaused && com.maddox.rts.Time.isEnableChangePause())
                com.maddox.rts.Time.setPause(false);
            bActive = flag;
        }
    }

    public boolean isPaused()
    {
        return bPaused;
    }

    public void setPause(boolean flag)
    {
        if(bPaused != flag && bEnableSetPause)
        {
            if(bActive && !bStartTimePaused && com.maddox.rts.Time.isEnableChangePause())
                com.maddox.rts.Time.setPause(flag);
            bPaused = flag;
        }
    }

    public java.lang.String getPrompt()
    {
        if(exec != null)
            return exec.getPrompt();
        else
            return _getPrompt();
    }

    public java.lang.String _getPrompt()
    {
        if(getEnv().curNumCmd() + 1 != curNumPrompt)
        {
            prompt = (getEnv().curNumCmd() + 1) + ">";
            curNumPrompt = getEnv().curNumCmd() + 1;
        }
        return prompt;
    }

    public int getNumPrompt()
    {
        return getEnv().curNumCmd() + 1;
    }

    public int maxHistoryOut()
    {
        return maxHistoryOut;
    }

    public int maxHistoryCmd()
    {
        return maxHistoryCmd;
    }

    public java.util.List historyOut()
    {
        return bufHistoryOut;
    }

    public java.util.List historyCmd()
    {
        return bufHistoryCmd;
    }

    public void setMaxHistoryOut(int i)
    {
        if(i >= 0 && i != maxHistoryOut)
        {
            maxHistoryOut = i;
            for(int j = bufHistoryOut.size() - maxHistoryOut; j > 0; j--)
                bufHistoryOut.removeLast();

            if(startHistoryOut >= maxHistoryOut)
                startHistoryOut = 0;
        }
    }

    public void setMaxHistoryCmd(int i)
    {
        if(i >= 0 && i != maxHistoryCmd)
        {
            maxHistoryCmd = i;
            for(int j = bufHistoryCmd.size() - maxHistoryCmd; j > 0; j--)
                bufHistoryCmd.removeLast();

        }
    }

    public void clear()
    {
        bufHistoryOut.clear();
        startHistoryOut = 0;
    }

    public boolean isShowHistoryOut()
    {
        return bShowHistoryOut;
    }

    public int startHistoryOut()
    {
        return startHistoryOut;
    }

    public int pageHistoryOut()
    {
        return pageHistoryOut;
    }

    public void setPageHistoryOut(int i)
    {
        pageHistoryOut = i;
    }

    public int startHistoryCmd()
    {
        int i = curHistoryCmd;
        if(i >= bufHistoryCmd.size())
            i = bufHistoryCmd.size();
        if(i < 0)
            i = 0;
        return i;
    }

    public java.lang.Object[] getTypes(boolean flag)
    {
        if(flag)
            return errTypes.toArray();
        else
            return outTypes.toArray();
    }

    public void addType(boolean flag, com.maddox.rts.ConsoleOut consoleout)
    {
        java.util.ArrayList arraylist = outTypes;
        if(flag)
            arraylist = errTypes;
        if(!arraylist.contains(consoleout))
            arraylist.add(consoleout);
    }

    public void removeType(boolean flag, com.maddox.rts.ConsoleOut consoleout)
    {
        java.util.ArrayList arraylist = outTypes;
        if(flag)
            arraylist = errTypes;
        int i = arraylist.indexOf(consoleout);
        if(i >= 0)
            arraylist.remove(i);
    }

    public void disableStdOuts()
    {
        if(!bDisabledStdOuts)
        {
            com.maddox.rts.ConsoleOut consoleout = (com.maddox.rts.ConsoleOut)outTypes.get(0);
            removeType(false, consoleout);
            consoleout = (com.maddox.rts.ConsoleOut)errTypes.get(0);
            removeType(true, consoleout);
            bDisabledStdOuts = true;
        }
    }

    public boolean isLogKeep()
    {
        return bLogKeep;
    }

    public void setLogKeep(boolean flag)
    {
        bLogKeep = flag;
    }

    public boolean isLog()
    {
        return log != null;
    }

    public void log(boolean flag)
    {
        if(flag != isLog())
            if(flag)
            {
                if(!bLogKeep)
                    try
                    {
                        java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName(nameLogFile, 0));
                        file.delete();
                    }
                    catch(java.lang.Exception exception) { }
                try
                {
                    log = new ConsoleLogFile(nameLogFile);
                    addType(true, log);
                    addType(false, log);
                }
                catch(java.lang.Exception exception1) { }
            } else
            {
                removeType(true, log);
                removeType(false, log);
                log.close();
                log = null;
            }
    }

    public java.lang.String logFileName()
    {
        return nameLogFile;
    }

    public void setLogFileName(java.lang.String s)
    {
        nameLogFile = s;
        if(isLog())
        {
            log(false);
            log(true);
        }
    }

    public void logFilePrintln(java.lang.String s)
    {
        if(log != null)
            log.println(s);
    }

    public void load(java.lang.String s)
    {
        try
        {
            java.io.BufferedReader bufferedreader = new BufferedReader(new SFSReader(s));
            java.lang.String s1 = "";
            do
            {
                java.lang.String s2 = bufferedreader.readLine();
                if(s2 == null)
                    break;
                s2 = s2.trim();
                if(s2.length() > 0)
                    addHistoryCmd(s2);
            } while(true);
            bufferedreader.close();
        }
        catch(java.io.IOException ioexception)
        {
            java.lang.System.out.println("Console commands file: " + s + " load failed: " + ioexception.getMessage());
        }
    }

    public void save(java.lang.String s)
    {
        try
        {
            java.io.PrintWriter printwriter = new PrintWriter(new BufferedWriter(new FileWriter(com.maddox.rts.HomePath.toFileSystemName(s, 0))));
            for(int i = bufHistoryCmd.size(); --i >= 0;)
            {
                java.lang.String s1 = (java.lang.String)bufHistoryCmd.get(i);
                printwriter.println(s1);
            }

            printwriter.close();
        }
        catch(java.io.IOException ioexception)
        {
            java.lang.System.out.println("Console commands file: " + s + " create failed: " + ioexception.getMessage());
        }
    }

    public com.maddox.rts.CmdEnv getEnv()
    {
        return env;
    }

    public Console(int i)
    {
        exec = null;
        editBuf = new StringBuffer();
        editPos = 0;
        bWrap = true;
        bActive = false;
        bStartTimePaused = false;
        bEnableSetPause = true;
        bPaused = true;
        outTypes = new ArrayList();
        errTypes = new ArrayList();
        bDisabledStdOuts = false;
        nameLogFile = "log.lst";
        log = null;
        bLogKeep = true;
        timeRepeat = 50L;
        curNumPrompt = -1;
        prompt = ">";
        maxHistoryOut = 128;
        bufHistoryOut = new LinkedList();
        startHistoryOut = 0;
        pageHistoryOut = 20;
        bShowHistoryOut = true;
        maxHistoryCmd = 128;
        bufHistoryCmd = new LinkedList();
        curHistoryCmd = -1;
        outBuf = new StringBuffer();
        errBuf = new StringBuffer();
        curKey = -1;
        nextTime = 0L;
        ticker = new MsgTimeOut();
        env = new CmdEnv(com.maddox.rts.CmdEnv.top());
        env.setLevelAccess(i);
        addType(false, new ConsoleOutPrint(java.lang.System.out));
        addType(true, new ConsoleOutPrint(java.lang.System.err));
        java.lang.System.setErr(new ConsolePrintStream(true, this));
        java.lang.System.setOut(new ConsolePrintStream(false, this));
        com.maddox.rts.MsgAddListener.post(64, com.maddox.rts.Keyboard.adapter(), this, null);
        ticker.setListener(this);
        ticker.setNotCleanAfterSend();
        ticker.setFlags(72);
    }

    protected void outWrite(char c)
    {
        outBuf.append(c);
        if(c == '\n')
        {
            java.lang.String s = outBuf.toString();
            for(int i = 0; i < outTypes.size(); i++)
                ((com.maddox.rts.ConsoleOut)outTypes.get(i)).type(s);

            outBuf = new StringBuffer();
            addHistoryOut(s);
        }
    }

    protected void outFlush()
    {
        if(outBuf.length() > 0)
        {
            java.lang.String s = outBuf.toString();
            for(int j = 0; j < outTypes.size(); j++)
                ((com.maddox.rts.ConsoleOut)outTypes.get(j)).type(s);

            outBuf = new StringBuffer();
        }
        for(int i = 0; i < outTypes.size(); i++)
            ((com.maddox.rts.ConsoleOut)outTypes.get(i)).flush();

    }

    protected void errWrite(char c)
    {
        errBuf.append(c);
        if(c == '\n')
        {
            java.lang.String s = errBuf.toString();
            for(int i = 0; i < errTypes.size(); i++)
                ((com.maddox.rts.ConsoleOut)errTypes.get(i)).type(s);

            errBuf = new StringBuffer();
            addHistoryOut(s);
        }
    }

    protected void errFlush()
    {
        if(errBuf.length() > 0)
        {
            java.lang.String s = errBuf.toString();
            for(int j = 0; j < errTypes.size(); j++)
                ((com.maddox.rts.ConsoleOut)errTypes.get(j)).type(s);

            errBuf = new StringBuffer();
        }
        for(int i = 0; i < errTypes.size(); i++)
            ((com.maddox.rts.ConsoleOut)errTypes.get(i)).flush();

    }

    public void flush()
    {
        outFlush();
        errFlush();
    }

    protected void addHistoryOut(java.lang.String s)
    {
        bufHistoryOut.addFirst(s);
        for(; bufHistoryOut.size() > maxHistoryOut; bufHistoryOut.removeLast());
        if(listenerChanges != null)
            listenerChanges.consoleChanged();
    }

    public void addHistoryCmd(java.lang.String s)
    {
        bufHistoryCmd.addFirst(s);
        for(; bufHistoryCmd.size() > maxHistoryCmd; bufHistoryCmd.removeLast());
    }

    public void msgKeyboardKey(int i, boolean flag)
    {
        if(flag && isActive())
        {
            int j = editBuf.length();
            switch(i)
            {
            case 17: // '\021'
                bShowHistoryOut = !bShowHistoryOut;
                break;

            case 37: // '%'
                if(editPos > 0)
                    editPos--;
                break;

            case 39: // '\''
                if(editPos < j)
                    editPos++;
                break;

            case 8: // '\b'
                if(editPos > 0)
                {
                    editPos--;
                    editBuf.deleteCharAt(editPos);
                }
                break;

            case 127: // '\177'
                if(editPos < j)
                    editBuf.deleteCharAt(editPos);
                break;

            case 36: // '$'
                editPos = 0;
                break;

            case 35: // '#'
                editPos = j;
                break;

            case 33: // '!'
                if(startHistoryOut + pageHistoryOut < historyOut().size())
                    startHistoryOut += pageHistoryOut;
                break;

            case 34: // '"'
                startHistoryOut -= pageHistoryOut;
                if(startHistoryOut < 0)
                    startHistoryOut = 0;
                break;

            case 38: // '&'
            case 40: // '('
                java.util.List list = historyCmd();
                if(list.size() > 0)
                {
                    if(i == 38)
                    {
                        if(curHistoryCmd < list.size())
                            curHistoryCmd++;
                        else
                            curHistoryCmd = 0;
                    } else
                    if(curHistoryCmd >= 0)
                        curHistoryCmd--;
                    else
                        curHistoryCmd = list.size() - 1;
                    if(curHistoryCmd < 0 || curHistoryCmd >= list.size())
                    {
                        editBuf.delete(0, j);
                        editPos = 0;
                    } else
                    {
                        editBuf.delete(0, j);
                        editBuf.append((java.lang.String)list.get(curHistoryCmd));
                        j = editBuf.length();
                        if(editPos > j)
                            editPos = j;
                    }
                }
                break;

            case 10: // '\n'
                java.lang.System.out.print(getPrompt() + editBuf.toString() + '\n');
                if(j > 0)
                {
                    java.lang.String s = editBuf.toString();
                    doExec(s);
                    addHistoryCmd(s);
                    curHistoryCmd = -1;
                    editPos = 0;
                    editBuf.delete(0, j);
                } else
                if(exec != null)
                    exec.doExec("");
                break;

            default:
                curKey = -1;
                return;
            }
        } else
        {
            curKey = -1;
        }
    }

    public void doExec(java.lang.String s)
    {
        if(exec != null)
            exec.doExec(s);
        else
            getEnv().exec(s);
    }

    public void msgKeyboardChar(char c)
    {
        if(bActive && c >= ' ')
        {
            editBuf.insert(editPos, c);
            editPos++;
        }
    }

    public void msgTimeOut(java.lang.Object obj)
    {
        if(curKey != -1)
        {
            ticker.post();
            if(com.maddox.rts.Time.real() >= nextTime)
                msgKeyboardKey(curKey, true);
        }
    }

    public com.maddox.rts.Exec exec;
    private static final boolean USE_REPEAT = false;
    protected static boolean bTypeTimeInLogFile = false;
    public java.lang.StringBuffer editBuf;
    public int editPos;
    public boolean bWrap;
    private com.maddox.rts.ConsoleListener listenerChanges;
    protected boolean bActive;
    private boolean bStartTimePaused;
    public boolean bEnableSetPause;
    protected boolean bPaused;
    protected java.util.ArrayList outTypes;
    protected java.util.ArrayList errTypes;
    private boolean bDisabledStdOuts;
    private java.lang.String nameLogFile;
    private com.maddox.rts.ConsoleLogFile log;
    private boolean bLogKeep;
    public long timeRepeat;
    private int curNumPrompt;
    private java.lang.String prompt;
    private int maxHistoryOut;
    private java.util.LinkedList bufHistoryOut;
    protected int startHistoryOut;
    protected int pageHistoryOut;
    protected boolean bShowHistoryOut;
    private int maxHistoryCmd;
    private java.util.LinkedList bufHistoryCmd;
    public int curHistoryCmd;
    private com.maddox.rts.CmdEnv env;
    private java.lang.StringBuffer outBuf;
    private java.lang.StringBuffer errBuf;
    private int curKey;
    private long nextTime;
    private com.maddox.rts.MsgTimeOut ticker;

}
