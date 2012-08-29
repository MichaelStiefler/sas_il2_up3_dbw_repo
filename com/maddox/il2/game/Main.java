// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Main.java

package com.maddox.il2.game;

import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GObj;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.GameSpy;
import com.maddox.il2.net.NetChannelListener;
import com.maddox.il2.net.NetFileServerMissProp;
import com.maddox.il2.net.NetFileServerNoseart;
import com.maddox.il2.net.NetFileServerPilot;
import com.maddox.il2.net.NetFileServerReg;
import com.maddox.il2.net.NetFileServerSkin;
import com.maddox.il2.net.NetMissionListener;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.Paratrooper;
import com.maddox.il2.objects.humans.Soldier;
import com.maddox.il2.objects.vehicles.artillery.RocketryGeneric;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Console;
import com.maddox.rts.ConsoleOut;
import com.maddox.rts.Cpu86ID;
import com.maddox.rts.Finger;
import com.maddox.rts.HomePath;
import com.maddox.rts.IniFile;
import com.maddox.rts.KryptoInputFilter;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetEnv;
import com.maddox.rts.Property;
import com.maddox.rts.RTS;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSInputStream;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.util.HashMapExt;
import com.maddox.util.SharedTokenizer;
import com.maddox.util.UnicodeTo8bit;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

// Referenced classes of package com.maddox.il2.game:
//            DotRange, GameStateStack, Mission, GameState

public abstract class Main
{
    class ConsoleServer extends java.lang.Thread
        implements com.maddox.rts.ConsoleOut
    {

        public void type(java.lang.String s)
        {
            if(!bEnableType)
                return;
            if(out != null && s != null)
                outQueue.put(s);
        }

        public void flush()
        {
        }

        public void typeNum()
        {
            if(out != null)
                outQueue.put("<consoleN><" + (com.maddox.rts.RTSConf.cur.console.getEnv().curNumCmd() + 1) + ">");
        }

        public void run()
        {
            java.net.InetAddress inetaddress;
            java.net.Socket socket;
            inetaddress = null;
            try
            {
                if(com.maddox.il2.engine.Config.cur.netLocalHost != null && com.maddox.il2.engine.Config.cur.netLocalHost.length() > 0)
                    inetaddress = java.net.InetAddress.getByName(com.maddox.il2.engine.Config.cur.netLocalHost);
                else
                    inetaddress = java.net.InetAddress.getLocalHost();
                serverSocket = new ServerSocket(port, 1, inetaddress);
            }
            catch(java.lang.Exception exception)
            {
                return;
            }
            socket = null;
              goto _L1
_L9:
            try
            {
                socket = serverSocket.accept();
            }
            catch(java.lang.Exception exception1)
            {
                socket = null;
                break; /* Loop/switch isn't completed */
            }
            boolean flag = false;
            for(int i = 0; i < clientAdr.size(); i++)
            {
                java.lang.String s = (java.lang.String)clientAdr.get(i);
                if(!socket.getInetAddress().getHostAddress().equals(s))
                    continue;
                flag = true;
                break;
            }

            if(!flag && !inetaddress.equals(socket.getInetAddress()))
            {
                try
                {
                    socket.close();
                }
                catch(java.lang.Exception exception4) { }
                socket = null;
                continue; /* Loop/switch isn't completed */
            }
            java.io.BufferedReader bufferedreader;
            out = new PrintWriter(socket.getOutputStream(), true);
            bufferedreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
              goto _L2
_L7:
            java.lang.String s1;
            s1 = com.maddox.util.UnicodeTo8bit.load(s1);
            if(s1.startsWith("<QUIT QUIT>"))
                break; /* Loop/switch isn't completed */
_L5:
label0:
            {
                synchronized(oCommandSync)
                {
                    if(!com.maddox.rts.RTSConf.isRequestExitApp())
                        break label0;
                }
                break; /* Loop/switch isn't completed */
            }
            if(bCommand)
                break MISSING_BLOCK_LABEL_296;
            sCommand = s1;
            bCommandNet = true;
            bCommand = true;
            obj;
            JVM INSTR monitorexit ;
            break; /* Loop/switch isn't completed */
            obj;
            JVM INSTR monitorexit ;
              goto _L3
            exception6;
            throw exception6;
_L3:
            try
            {
                java.lang.Thread.sleep(10L);
            }
            catch(java.lang.Exception exception7) { }
            if(true) goto _L5; else goto _L4
_L4:
            if(com.maddox.rts.RTSConf.isRequestExitApp())
                break; /* Loop/switch isn't completed */
_L2:
            if((s1 = bufferedreader.readLine()) != null) goto _L7; else goto _L6
_L6:
            out.close();
            bufferedreader.close();
            break MISSING_BLOCK_LABEL_364;
            java.lang.Exception exception5;
            exception5;
            out = null;
_L1:
            if(com.maddox.rts.RTSConf.cur != null && !com.maddox.rts.RTSConf.isRequestExitApp()) goto _L9; else goto _L8
_L8:
            if(socket != null)
                try
                {
                    socket.close();
                }
                catch(java.lang.Exception exception2) { }
            try
            {
                serverSocket.close();
            }
            catch(java.lang.Exception exception3) { }
            return;
        }

        java.util.ArrayList clientAdr;
        java.net.ServerSocket serverSocket;
        java.io.PrintWriter out;
        com.maddox.il2.game.ConsoleWriter outWriter;
        com.maddox.il2.game.ConsoleOutQueue outQueue;
        int port;
        boolean bEnableType;

        public ConsoleServer(int i, java.util.ArrayList arraylist)
        {
            outQueue = new ConsoleOutQueue();
            bEnableType = true;
            port = i;
            clientAdr = arraylist;
            com.maddox.rts.RTSConf.cur.console.addType(false, this);
            com.maddox.rts.RTSConf.cur.console.addType(true, this);
            outWriter = new ConsoleWriter();
        }
    }

    class ConsoleWriter extends java.lang.Thread
    {

        public void run()
        {
            while(com.maddox.rts.RTSConf.cur != null && !com.maddox.rts.RTSConf.isRequestExitApp()) 
            {
                java.lang.String s = consoleServer.outQueue.get();
                s = com.maddox.util.UnicodeTo8bit.save(s, false);
                if(consoleServer.out != null)
                    try
                    {
                        consoleServer.out.print(s);
                        consoleServer.out.println();
                        if(consoleServer.outQueue.isEmpty())
                            consoleServer.out.flush();
                    }
                    catch(java.lang.Exception exception) { }
            }
        }

        ConsoleWriter()
        {
        }
    }

    class ConsoleOutQueue
    {

        public synchronized void put(java.lang.String s)
        {
            int i = outQueue.size();
            if(i >= 1024)
                truncated++;
            else
            if(truncated > 0)
            {
                if(i >= 768)
                {
                    truncated++;
                } else
                {
                    outQueue.add("!!! Output truncated ( " + truncated + " lines )\n");
                    outQueue.add(s);
                    truncated = 0;
                }
            } else
            {
                outQueue.add(s);
            }
            notifyAll();
        }

        public synchronized java.lang.String get()
        {
            while(outQueue.isEmpty()) 
                try
                {
                    wait();
                }
                catch(java.lang.InterruptedException interruptedexception) { }
            return (java.lang.String)outQueue.removeFirst();
        }

        public synchronized boolean isEmpty()
        {
            return outQueue.isEmpty();
        }

        java.util.LinkedList outQueue;
        int truncated;

        ConsoleOutQueue()
        {
            outQueue = new LinkedList();
            truncated = 0;
        }
    }


    public Main()
    {
        airClasses = new ArrayList();
        dotRangeFriendly = new DotRange();
        dotRangeFoe = new DotRange();
        stateStack = new GameStateStack();
        oCommandSync = new Object();
        bCommand = false;
        bCommandNet = false;
        sCommand = null;
        consoleServer = null;
    }

    public boolean beginApp(java.lang.String s, java.lang.String s1, int i)
    {
        return false;
    }

    public void onBeginApp()
    {
    }

    public void loopApp()
    {
    }

    public void endApp()
    {
    }

    public static com.maddox.il2.game.GameStateStack stateStack()
    {
        return cur.stateStack;
    }

    public static com.maddox.il2.game.GameState state()
    {
        return cur.stateStack.peek();
    }

    public void resetGameClear()
    {
        com.maddox.il2.engine.Engine.cur.resetGameClear();
        com.maddox.rts.RTSConf.cur.resetGameClear();
    }

    public void resetGameCreate()
    {
        com.maddox.rts.RTSConf.cur.resetGameCreate();
        com.maddox.il2.engine.Engine.cur.resetGameCreate();
        com.maddox.il2.objects.air.Paratrooper.resetGame();
        com.maddox.il2.objects.humans.Soldier.resetGame();
    }

    public void resetGame()
    {
        com.maddox.rts.RTSConf.cur.setFlagResetGame(true);
        resetGameClear();
        com.maddox.rts.RTSConf.cur.setFlagResetGame(false);
        resetGameCreate();
        java.lang.Runtime.getRuntime().gc();
        java.lang.Runtime.getRuntime().runFinalization();
        com.maddox.il2.engine.GObj.DeleteCppObjects();
    }

    public void resetUserClear()
    {
    }

    public void resetUserCreate()
    {
    }

    public void resetUser()
    {
        resetUserClear();
        resetUserCreate();
    }

    public static void closeAllNetChannels()
    {
        com.maddox.rts.NetEnv _tmp = com.maddox.rts.RTSConf.cur.netEnv;
        java.util.List list = com.maddox.rts.NetEnv.channels();
        for(int i = 0; i < list.size(); i++)
        {
            com.maddox.rts.NetChannel netchannel = (com.maddox.rts.NetChannel)list.get(i);
            if(!netchannel.isDestroyed())
                netchannel.destroy("Remote user has left the game.");
        }

    }

    public static void doGameExit()
    {
        if(bRequestExit)
            return;
        bRequestExit = true;
        com.maddox.rts.NetEnv _tmp = com.maddox.rts.RTSConf.cur.netEnv;
        java.util.List list = com.maddox.rts.NetEnv.channels();
        if(list.size() == 0)
        {
            com.maddox.rts.RTSConf.setRequestExitApp(true);
        } else
        {
            com.maddox.il2.game.Main.closeAllNetChannels();
            new com.maddox.rts.MsgAction(64, 2D, "") {

                public void doAction(java.lang.Object obj)
                {
                    com.maddox.rts.RTSConf.setRequestExitApp(true);
                }

            }
;
        }
    }

    public void preloadNetClasses()
    {
        com.maddox.rts.Spawn.get("com.maddox.rts.NetControl");
        com.maddox.rts.Spawn.get("com.maddox.il2.net.NetUser");
        com.maddox.rts.Spawn.get("com.maddox.il2.net.NetServerParams");
        com.maddox.rts.Spawn.get("com.maddox.il2.net.Chat");
        com.maddox.rts.Spawn.get("com.maddox.il2.game.Mission");
        netFileServerMissProp = new NetFileServerMissProp(254);
        netFileServerReg = new NetFileServerReg(253);
        netFileServerSkin = new NetFileServerSkin(252);
        netFileServerPilot = new NetFileServerPilot(251);
        netFileServerNoseart = new NetFileServerNoseart(249);
        com.maddox.rts.Spawn.get("com.maddox.il2.objects.air.Paratrooper");
        com.maddox.rts.Spawn.get("com.maddox.il2.objects.air.NetGunner");
    }

    public void preloadAirClasses()
    {
        com.maddox.rts.SectFile sectfile = new SectFile("com/maddox/il2/objects/air.ini", 0);
        int i = sectfile.sections();
        for(int j = 0; j < i; j++)
        {
            int k = sectfile.vars(j);
            for(int l = 0; l < k; l++)
            {
                java.lang.String s = sectfile.value(j, l);
                java.util.StringTokenizer stringtokenizer = new StringTokenizer(s);
                if(stringtokenizer.hasMoreTokens())
                {
                    java.lang.String s1 = "com.maddox.il2.objects." + stringtokenizer.nextToken();
                    com.maddox.rts.Spawn.get(s1);
                    try
                    {
                        java.lang.Class class1 = java.lang.Class.forName(s1);
                        java.lang.String s2 = sectfile.var(j, l).intern();
                        com.maddox.rts.Property.set(class1, "keyName", s2);
                        com.maddox.rts.Property.set(s2, "airClass", class1);
                        com.maddox.il2.objects.air.Aircraft.weapons(class1);
                        airClasses.add(class1);
                    }
                    catch(java.lang.Exception exception) { }
                }
            }

        }

    }

    public void preloadChiefClasses()
    {
        com.maddox.rts.SectFile sectfile = new SectFile("com/maddox/il2/objects/chief.ini", 0);
        int i = sectfile.sections();
        for(int j = 0; j < i; j++)
            if(sectfile.sectionName(j).indexOf('.') >= 0)
            {
                int k = sectfile.vars(j);
                for(int l = 0; l < k; l++)
                {
                    java.lang.String s = sectfile.var(j, l);
                    java.util.StringTokenizer stringtokenizer = new StringTokenizer(s);
                    if(stringtokenizer.hasMoreTokens())
                        com.maddox.rts.Spawn.get_WithSoftClass(stringtokenizer.nextToken());
                }

            }

    }

    public void preloadStationaryClasses()
    {
        com.maddox.rts.SectFile sectfile = new SectFile("com/maddox/il2/objects/stationary.ini", 0);
        int i = sectfile.sections();
        for(int j = 0; j < i; j++)
        {
            int k = sectfile.vars(j);
            for(int l = 0; l < k; l++)
            {
                java.lang.String s = sectfile.value(j, l);
                java.util.StringTokenizer stringtokenizer = new StringTokenizer(s);
                if(stringtokenizer.hasMoreTokens())
                    com.maddox.rts.Spawn.get_WithSoftClass("com.maddox.il2.objects." + stringtokenizer.nextToken());
            }

        }

        com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.PreLoad("com/maddox/il2/objects/rockets.ini");
    }

    private static int[] getSwTbl2(int i)
    {
        if(i < 0)
            i = -i;
        int j = i % 16 + 21;
        int k = i % com.maddox.rts.Finger.kTable.length;
        if(j < 0)
            j = -j % 16;
        if(j < 10)
            j = 10;
        if(k < 0)
            k = -k % com.maddox.rts.Finger.kTable.length;
        int ai[] = new int[j];
        for(int l = 0; l < j; l++)
            ai[l] = com.maddox.rts.Finger.kTable[(k + l) % com.maddox.rts.Finger.kTable.length];

        return ai;
    }

    public void preload()
    {
        java.util.Map.Entry entry = com.maddox.rts.Property.propertyHoldMap.nextEntry(null);
        java.util.ArrayList arraylist = new ArrayList();
label0:
        while(entry != null) 
        {
            java.lang.Object obj = entry.getKey();
            if(!(obj instanceof java.lang.Class))
                continue;
            java.lang.Class class1 = (java.lang.Class)obj;
            if(!(com.maddox.il2.objects.weapons.Bomb.class).isAssignableFrom(class1) && !(com.maddox.il2.objects.weapons.BombGun.class).isAssignableFrom(class1) && !(com.maddox.il2.objects.weapons.FuelTank.class).isAssignableFrom(class1) && !(com.maddox.il2.objects.weapons.FuelTankGun.class).isAssignableFrom(class1) && !(com.maddox.il2.objects.weapons.Rocket.class).isAssignableFrom(class1) && !(com.maddox.il2.objects.weapons.RocketGun.class).isAssignableFrom(class1))
                continue;
            arraylist.clear();
            if(!com.maddox.rts.Property.vars(arraylist, obj))
                continue;
            try
            {
                int i = com.maddox.rts.Finger.Int("wp" + class1.getName() + "mr");
                java.io.BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(new KryptoInputFilter(new SFSInputStream(com.maddox.rts.Finger.LongFN(0L, "cod/" + com.maddox.rts.Finger.incInt(i, "slg"))), com.maddox.il2.game.Main.getSwTbl2(i))));
                do
                {
                    java.lang.String s = bufferedreader.readLine();
                    if(s != null)
                    {
                        com.maddox.util.SharedTokenizer.set(s);
                        java.lang.String s1 = com.maddox.util.SharedTokenizer.next();
                        java.lang.String s2 = com.maddox.util.SharedTokenizer.next();
                        switch(s2.charAt(0))
                        {
                        case 83: // 'S'
                            java.lang.String s3 = com.maddox.util.SharedTokenizer.next();
                            com.maddox.rts.Property.set(obj, s1, s3);
                            break;

                        case 68: // 'D'
                            double d = com.maddox.util.SharedTokenizer.next(0.0D);
                            com.maddox.rts.Property.set(obj, s1, d);
                            break;

                        case 76: // 'L'
                            long l = com.maddox.util.SharedTokenizer.next(0);
                            com.maddox.rts.Property.set(obj, s1, l);
                            break;
                        }
                        continue;
                    }
                    bufferedreader.close();
                    continue label0;
                } while(true);
            }
            catch(java.lang.Exception exception)
            {
                entry = com.maddox.rts.Property.propertyHoldMap.nextEntry(entry);
            }
        }
    }

    private static void clearDir(java.io.File file, boolean flag)
    {
        java.io.File afile[] = file.listFiles();
        if(afile != null)
        {
            for(int i = 0; i < afile.length; i++)
            {
                java.io.File file1 = afile[i];
                java.lang.String s = file1.getName();
                if(!".".equals(s) && !"..".equals(s))
                    if(file1.isDirectory())
                        com.maddox.il2.game.Main.clearDir(file1, true);
                    else
                        file1.delete();
            }

        }
        if(flag)
            file.delete();
    }

    public static void clearCache()
    {
        if(com.maddox.il2.engine.Config.cur == null)
            return;
        if(!com.maddox.il2.engine.Config.cur.clear_cache)
            return;
        try
        {
            java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName("PaintSchemes/Cache", 0));
            if(file.exists())
                com.maddox.il2.game.Main.clearDir(file, false);
            else
                file.mkdirs();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    public static com.maddox.il2.game.Main cur()
    {
        return cur;
    }

    public static void exec(com.maddox.il2.game.Main main, java.lang.String s, java.lang.String s1, int i)
    {
        com.maddox.rts.IniFile inifile = new IniFile(s, 0);
        java.lang.String s2 = inifile.get("rts", "locale", "us");
        java.util.Locale.setDefault(java.util.Locale.US);
        try
        {
            s2.toLowerCase();
            if(s2.length() == 2)
                java.util.Locale.setDefault(new Locale(s2, ""));
        }
        catch(java.lang.Exception exception) { }
        if(com.maddox.il2.engine.Config.LOCALE.equals("RU"))
        {
            java.util.Locale.setDefault(new Locale("ru", "RU"));
            try
            {
                com.maddox.rts.Cpu86ID.getMask();
            }
            catch(java.lang.Throwable throwable) { }
        } else
        if(com.maddox.il2.engine.Config.LOCALE.equals("JP"))
        {
            java.util.Locale.setDefault(new Locale("ja", "JP"));
        } else
        {
            java.lang.String s3 = java.util.Locale.getDefault().getLanguage();
            if(!"de".equals(s3) && !"fr".equals(s3) && !"cs".equals(s3) && !"pl".equals(s3) && !"hu".equals(s3) && !"lt".equals(s3) && !"us".equals(s3))
                java.util.Locale.setDefault(java.util.Locale.US);
        }
        if("ru".equals(java.util.Locale.getDefault().getLanguage()))
            com.maddox.rts.RTSConf.charEncoding = "Cp1251";
        else
        if("cs".equals(java.util.Locale.getDefault().getLanguage()))
            com.maddox.rts.RTSConf.charEncoding = "Cp1250";
        else
        if("pl".equals(java.util.Locale.getDefault().getLanguage()))
            com.maddox.rts.RTSConf.charEncoding = "Cp1250";
        else
        if("hu".equals(java.util.Locale.getDefault().getLanguage()))
            com.maddox.rts.RTSConf.charEncoding = "Cp1250";
        else
        if("lt".equals(java.util.Locale.getDefault().getLanguage()))
            com.maddox.rts.RTSConf.charEncoding = "Cp1257";
        else
        if("ja".equals(java.util.Locale.getDefault().getLanguage()))
            com.maddox.rts.RTSConf.charEncoding = "SJIS";
        if(cur != null)
            throw new RuntimeException("Traying recurse execute main method");
        java.lang.Runtime.getRuntime().traceInstructions(false);
        java.lang.Runtime.getRuntime().traceMethodCalls(false);
        cur = main;
        if(!main.isLifeLimitted())
        {
            try
            {
                if(main.beginApp(s, s1, i))
                {
                    com.maddox.il2.game.Main.clearCache();
                    if(com.maddox.il2.engine.Config.LOCALE.equals("RU"))
                        try
                        {
                            com.maddox.rts.Cpu86ID.getMask();
                        }
                        catch(java.lang.Throwable throwable1)
                        {
                            for(int j = 0; j < 10; j++)
                                new com.maddox.rts.MsgAction(64, 1.0D + (double)(j * 10) + java.lang.Math.random() * 10D) {

                                    public void doAction()
                                    {
                                        com.maddox.il2.game.Main.doGameExit();
                                    }

                                }
;

                        }
                    try
                    {
                        main.lifeLimitted();
                        main.loopApp();
                    }
                    catch(java.lang.Exception exception1)
                    {
                        java.lang.System.out.println("Main loop: " + exception1.getMessage());
                        exception1.printStackTrace();
                    }
                }
            }
            catch(java.lang.Exception exception2)
            {
                java.lang.System.out.println("Main begin: " + exception2.getMessage());
                exception2.printStackTrace();
            }
            com.maddox.il2.game.Main.clearCache();
            try
            {
                main.endApp();
            }
            catch(java.lang.Exception exception3)
            {
                java.lang.System.out.println("Main end: " + exception3.getMessage());
                exception3.printStackTrace();
            }
            if(com.maddox.rts.RTSConf.cur != null && com.maddox.rts.RTSConf.cur.console != null)
                com.maddox.rts.RTSConf.cur.console.log(false);
            if(com.maddox.rts.RTSConf.cur != null && com.maddox.rts.RTSConf.cur.execPostProcessCmd != null)
                try
                {
                    com.maddox.rts.RTS.setPostProcessCmd(com.maddox.rts.RTSConf.cur.execPostProcessCmd);
                }
                catch(java.lang.Exception exception4)
                {
                    java.lang.System.out.println("Exec cmd (" + com.maddox.rts.RTSConf.cur.execPostProcessCmd + ") error: " + exception4.getMessage());
                    exception4.printStackTrace();
                }
        }
        java.lang.System.exit(0);
    }

    private boolean isLifeLimitted()
    {
        return false;
    }

    private void lifeLimitted()
    {
    }

    protected void createConsoleServer()
    {
        int i = com.maddox.il2.engine.Config.cur.ini.get("Console", "IP", 0, 0, 65000);
        if(i == 0)
            return;
        java.util.ArrayList arraylist = new ArrayList();
        java.lang.String s = com.maddox.il2.engine.Config.cur.ini.get("Console", "IPS", (java.lang.String)null);
        if(s != null)
        {
            java.lang.String s1;
            for(java.util.StringTokenizer stringtokenizer = new StringTokenizer(s); stringtokenizer.hasMoreTokens(); arraylist.add(s1))
                s1 = stringtokenizer.nextToken();

        }
        consoleServer = new ConsoleServer(i, arraylist);
        consoleServer.setPriority(java.lang.Thread.currentThread().getPriority() + 1);
        consoleServer.start();
        consoleServer.outWriter.setPriority(java.lang.Thread.currentThread().getPriority() + 1);
        consoleServer.outWriter.start();
    }

    private static int[] getSwTbl(int i)
    {
        if(i < 0)
            i = -i;
        int j = i % 16 + 14;
        int k = i % com.maddox.rts.Finger.kTable.length;
        if(j < 0)
            j = -j % 16;
        if(j < 10)
            j = 10;
        if(k < 0)
            k = -k % com.maddox.rts.Finger.kTable.length;
        int ai[] = new int[j];
        for(int l = 0; l < j; l++)
            ai[l] = com.maddox.rts.Finger.kTable[(k + l) % com.maddox.rts.Finger.kTable.length];

        return ai;
    }

    public static final java.lang.String AIR_INI = "com/maddox/il2/objects/air.ini";
    public java.util.ArrayList airClasses;
    public com.maddox.il2.game.Mission mission;
    public com.maddox.il2.game.Mission missionLoading;
    public int missionCounter;
    public com.maddox.il2.game.campaign.Campaign campaign;
    public com.maddox.il2.game.DotRange dotRangeFriendly;
    public com.maddox.il2.game.DotRange dotRangeFoe;
    public com.maddox.il2.net.NetChannelListener netChannelListener;
    public com.maddox.il2.net.NetMissionListener netMissionListener;
    public com.maddox.il2.net.NetServerParams netServerParams;
    public com.maddox.il2.net.Chat chat;
    public com.maddox.il2.net.NetFileServerMissProp netFileServerMissProp;
    public com.maddox.il2.net.NetFileServerReg netFileServerReg;
    public com.maddox.il2.net.NetFileServerSkin netFileServerSkin;
    public com.maddox.il2.net.NetFileServerPilot netFileServerPilot;
    public com.maddox.il2.net.NetFileServerNoseart netFileServerNoseart;
    public com.maddox.il2.net.GameSpy netGameSpy;
    public com.maddox.il2.net.GameSpy netGameSpyListener;
    public com.maddox.rts.SectFile currentMissionFile;
    public com.maddox.il2.game.GameStateStack stateStack;
    protected java.lang.Object oCommandSync;
    protected boolean bCommand;
    protected boolean bCommandNet;
    protected java.lang.String sCommand;
    protected com.maddox.il2.game.ConsoleServer consoleServer;
    private static boolean bRequestExit = false;
    private static com.maddox.il2.game.Main cur;

    static 
    {
        try
        {
            java.io.BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(new KryptoInputFilter(new SFSInputStream(com.maddox.rts.Finger.LongFN(0L, "cod/" + com.maddox.rts.Finger.Int("allc"))), com.maddox.il2.game.Main.getSwTbl(com.maddox.rts.Finger.Int("alls")))));
            do
            {
                java.lang.String s = bufferedreader.readLine();
                if(s == null)
                    break;
                java.lang.Class.forName("com.maddox." + s);
            } while(true);
            bufferedreader.close();
        }
        catch(java.lang.Exception exception) { }
    }

}
