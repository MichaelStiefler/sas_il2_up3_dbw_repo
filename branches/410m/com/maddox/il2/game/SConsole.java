// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SConsole.java

package com.maddox.il2.game;

import com.maddox.rts.IniFile;
import com.maddox.util.UnicodeTo8bit;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class SConsole
{

    public SConsole()
    {
    }

    public static void main(java.lang.String args[])
    {
        int i = 0;
        com.maddox.rts.IniFile inifile = new IniFile("confc.ini", 0);
        i = inifile.get("Console", "IP", 0, 0, 65000);
        if(i == 0)
            return;
        java.net.Socket socket = null;
        java.io.PrintWriter printwriter = null;
        java.io.BufferedReader bufferedreader = null;
        try
        {
            java.net.InetAddress inetaddress = java.net.InetAddress.getLocalHost();
            if(args != null && args.length > 0)
                inetaddress = java.net.InetAddress.getByName(args[0]);
            java.net.InetAddress inetaddress1 = java.net.InetAddress.getLocalHost();
            java.lang.String s1 = inifile.get("NET", "localHost", (java.lang.String)null);
            if(s1 != null && s1.length() > 0)
                inetaddress1 = java.net.InetAddress.getByName(s1);
            socket = new Socket(inetaddress, i, inetaddress1, 0);
            printwriter = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedreader = new BufferedReader(new InputStreamReader(java.lang.System.in));
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.err.println("Couldn't get I/O for the connection to IL2");
            java.lang.System.exit(1);
        }
        java.lang.Thread thread = new java.lang.Thread() {

            public void run()
            {
                java.lang.String s2 = "<consoleN><";
                int j = s2.length();
                try
                {
                    do
                    {
                        if(!com.maddox.il2.game.SConsole.bReady)
                            break;
                        java.lang.String s3 = com.maddox.il2.game.SConsole.in.readLine();
                        if(s3 == null)
                            break;
                        s3 = com.maddox.util.UnicodeTo8bit.load(s3);
                        int k = s3.length();
                        int l = 0;
                        boolean flag = false;
                        do
                        {
                            if(l + j > k)
                                break;
                            if(s3.regionMatches(l, s2, 0, j))
                            {
                                int i1 = l + j;
                                int j1 = s3.indexOf(">", i1);
                                if(j1 > 0)
                                {
                                    try
                                    {
                                        flag = true;
                                        com.maddox.il2.game.SConsole.prompt = java.lang.Integer.parseInt(s3.substring(i1, j1));
                                    }
                                    catch(java.lang.Exception exception4) { }
                                    if(j1 + 1 < k)
                                        s3 = s3.substring(0, l) + s3.substring(j1 + 1, k);
                                    else
                                        s3 = s3.substring(0, l);
                                    k = s3.length();
                                    if(k == 1 && s3.charAt(0) == '\n')
                                    {
                                        k = 0;
                                        s3 = null;
                                    }
                                } else
                                {
                                    l++;
                                }
                            } else
                            {
                                l++;
                            }
                        } while(true);
                        if(s3 != null && s3.length() > 0)
                            java.lang.System.out.print(s3);
                        if(flag)
                        {
                            java.lang.System.out.print(com.maddox.il2.game.SConsole.prompt + ">");
                            java.lang.System.out.flush();
                        }
                    } while(true);
                }
                catch(java.lang.Exception exception3)
                {
                    com.maddox.il2.game.SConsole.bReady = false;
                }
            }

        }
;
        thread.start();
        try
        {
            java.lang.System.out.println("IL2 remote console");
            java.lang.System.out.println("For end console enter 'quit'");
            printwriter.println("server");
            java.lang.String s;
            do
            {
                if(!bReady || (s = bufferedreader.readLine()) == null)
                    break;
                if("quit".equals(s))
                {
                    printwriter.println("<QUIT QUIT>");
                    printwriter.flush();
                    break;
                }
                printwriter.println(com.maddox.util.UnicodeTo8bit.save(s, false));
                printwriter.flush();
            } while(!"exit".equals(s));
        }
        catch(java.lang.Exception exception1) { }
        bReady = false;
        try
        {
            printwriter.close();
            in.close();
            socket.close();
        }
        catch(java.lang.Exception exception2) { }
        thread.interrupt();
        java.lang.System.exit(0);
    }

    static java.io.BufferedReader in = null;
    static int prompt = -1;
    static boolean bReady = true;

}
