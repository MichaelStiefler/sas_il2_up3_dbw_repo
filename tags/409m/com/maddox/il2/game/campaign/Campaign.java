// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Campaign.java

package com.maddox.il2.game.campaign;

import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.Statics;
import com.maddox.rts.Console;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.util.SharedTokenizer;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.game.campaign:
//            Awards

public abstract class Campaign
{

    public Campaign()
    {
        amountMissions = 0;
    }

    public int army()
    {
        return 0;
    }

    public java.lang.String branch()
    {
        return _country;
    }

    public java.lang.String country()
    {
        return com.maddox.il2.ai.Regiment.getCountryFromBranch(_country);
    }

    public java.lang.String missionsDir()
    {
        return _missionsDir;
    }

    public int difficulty()
    {
        return _difficulty;
    }

    public int completeMissions()
    {
        return _completeMissions;
    }

    public int score()
    {
        return _score;
    }

    public int scoreRank()
    {
        return _scoreRank;
    }

    public int rank()
    {
        return _rank;
    }

    public int aircraftLost()
    {
        return _aircraftLost;
    }

    public int enemyAirDestroyed()
    {
        return _enemyAirDestroyed;
    }

    public int[] enemyGroundDestroyed()
    {
        return _enemyGroundDestroyed;
    }

    public int friendDestroyed()
    {
        return _friendDestroyed;
    }

    public java.lang.String lastMissionMapName()
    {
        return _lastMissionMapName;
    }

    public void setLastMissionMapName(java.lang.String s)
    {
        _lastMissionMapName = s;
    }

    public java.lang.String epilogueTrack()
    {
        return _epilogueTrack;
    }

    public boolean isComplete()
    {
        if(amountMissions == 0)
        {
            com.maddox.rts.SectFile sectfile = missionsSectFile();
            int i = sectfile.sectionIndex("list");
            if(i >= 0)
            {
                int j = sectfile.vars(i);
                for(int k = 0; k < j; k++)
                {
                    java.lang.String s = sectfile.var(i, k);
                    if(!lineIsIntroName(s))
                        amountMissions++;
                }

            }
        }
        return amountMissions == completeMissions();
    }

    public int awards(int i)
    {
        int j = i;
        if(j < _scoreAward)
            j = _scoreAward;
        return _awards.count(j);
    }

    public int[] getAwards(int i)
    {
        int j = i;
        if(j < _scoreAward)
            j = _scoreAward;
        return _awards.index(j);
    }

    public void incAircraftLost()
    {
        _aircraftLost++;
    }

    protected int rankStep()
    {
        return 1200;
    }

    public int newRank(int i)
    {
        if(i < _scoreRank + rankStep())
            return _rank;
        int j = _rank + (i - _scoreRank) / rankStep();
        if(j >= 6)
        {
            j = 6;
            newScoreRank = i;
        } else
        {
            newScoreRank = (j - _rank) * rankStep() + _scoreRank;
        }
        return j;
    }

    public void currentMissionComplete(int i, int j, int ai[], int k)
    {
        _completeMissions++;
        _score = i;
        _enemyAirDestroyed = j;
        _enemyGroundDestroyed = ai;
        _friendDestroyed = k;
        int l = newRank(i);
        if(l > _rank)
        {
            _rank = l;
            _scoreRank = newScoreRank;
        }
        if(i > _scoreAward)
            _scoreAward = i;
    }

    public void clearSavedStatics(com.maddox.rts.SectFile sectfile)
    {
        java.lang.String s = branch() + missionsDir();
        int i = sectfile.sectionIndex(s + "Bridge");
        if(i >= 0)
            sectfile.sectionRemove(i);
        i = sectfile.sectionIndex(s + "House");
        if(i >= 0)
            sectfile.sectionRemove(i);
    }

    public void saveStatics(com.maddox.rts.SectFile sectfile)
    {
        java.lang.String s = branch() + missionsDir();
        clearSavedStatics(sectfile);
        if(com.maddox.il2.game.Mission.cur() == null)
            return;
        if(com.maddox.il2.game.Mission.cur().sectFile() == null)
        {
            return;
        } else
        {
            int i = sectfile.sectionAdd(s + "Bridge");
            com.maddox.il2.ai.World.cur().statics.saveStateBridges(sectfile, i);
            i = sectfile.sectionAdd(s + "House");
            com.maddox.il2.ai.World.cur().statics.saveStateHouses(sectfile, i);
            java.lang.String s1 = com.maddox.il2.game.Mission.cur().sectFile().get("MAIN", "MAP", (java.lang.String)null);
            setLastMissionMapName(s1);
            return;
        }
    }

    public void copySavedStatics(com.maddox.rts.SectFile sectfile, com.maddox.rts.SectFile sectfile1)
    {
        java.lang.String s = branch() + missionsDir();
        copySection(sectfile, s + "Bridge", sectfile1, "AddBridge");
        copySection(sectfile, s + "House", sectfile1, "AddHouse");
    }

    private void copySection(com.maddox.rts.SectFile sectfile, java.lang.String s, com.maddox.rts.SectFile sectfile1, java.lang.String s1)
    {
        int i = sectfile1.sectionIndex(s1);
        if(i >= 0)
            sectfile1.sectionRemove(i);
        int j = sectfile.sectionIndex(s);
        if(j < 0)
            return;
        i = sectfile1.sectionAdd(s1);
        int k = sectfile.vars(j);
        for(int l = 0; l < k; l++)
            sectfile1.lineAdd(i, sectfile.line(j, l));

    }

    private void copySavedStatics(com.maddox.rts.SectFile sectfile)
    {
        java.lang.String s = sectfile.get("MAIN", "MAP", (java.lang.String)null);
        if(s == null)
            return;
        if(!s.equals(lastMissionMapName()))
        {
            return;
        } else
        {
            java.lang.String s1 = "users/" + com.maddox.il2.ai.World.cur().userCfg.sId + "/campaigns.ini";
            com.maddox.rts.SectFile sectfile1 = new SectFile(s1, 0, false, com.maddox.il2.ai.World.cur().userCfg.krypto());
            copySavedStatics(sectfile1, sectfile);
            return;
        }
    }

    public com.maddox.rts.SectFile missionsSectFile()
    {
        if(missionsSectFile != null)
        {
            return missionsSectFile;
        } else
        {
            missionsSectFile = new SectFile("missions/campaign/" + branch() + "/" + missionsDir() + "/campaign.ini", 0);
            return missionsSectFile;
        }
    }

    public boolean isDGen()
    {
        com.maddox.rts.SectFile sectfile = missionsSectFile();
        java.lang.String s = sectfile.get("Main", "ExecGenerator", (java.lang.String)null);
        if(s == null)
            return false;
        else
            return "dgen.exe".equalsIgnoreCase(s);
    }

    public void doExternalGenerator()
    {
        com.maddox.rts.SectFile sectfile = missionsSectFile();
        java.lang.String s = sectfile.get("Main", "ExecGenerator", (java.lang.String)null);
        if(s == null)
            return;
        amountMissions = 0;
        missionsSectFile = null;
        try
        {
            com.maddox.rts.RTSConf.cur.console.flush();
            com.maddox.il2.ai.EventLog.close();
        }
        catch(java.lang.Throwable throwable)
        {
            java.lang.System.out.println(throwable.getMessage());
            throwable.printStackTrace();
        }
        try
        {
            java.lang.String s1 = "users/" + com.maddox.il2.ai.World.cur().userCfg.sId + "/";
            java.lang.String s2 = "missions/campaign/" + branch() + "/" + missionsDir() + "/";
            java.lang.String s3 = s1 + " " + s2 + " " + completeMissions() + " " + difficulty() + " " + score() + " " + rank();
            java.lang.Runtime runtime = java.lang.Runtime.getRuntime();
            java.lang.Process process = runtime.exec(s + " " + s3);
            process.waitFor();
        }
        catch(java.lang.Throwable throwable1)
        {
            java.lang.System.out.println(throwable1.getMessage());
            throwable1.printStackTrace();
        }
    }

    public java.lang.String nextIntro()
    {
        com.maddox.rts.SectFile sectfile = missionsSectFile();
        int i = sectfile.sectionIndex("list");
        if(i >= 0)
        {
            int j = sectfile.vars(i);
            int k = 0;
            for(int l = 0; l < j; l++)
            {
                java.lang.String s = sectfile.var(i, l);
                if(lineIsIntroName(s))
                {
                    if(k == _completeMissions)
                    {
                        com.maddox.util.SharedTokenizer.set(sectfile.line(i, l));
                        java.lang.String s1 = com.maddox.util.SharedTokenizer.nextToken();
                        return com.maddox.util.SharedTokenizer.nextToken();
                    }
                } else
                {
                    k++;
                }
            }

        }
        return null;
    }

    public com.maddox.rts.SectFile nextMission()
    {
        if(isComplete())
            return null;
        com.maddox.rts.SectFile sectfile = missionsSectFile();
        int i = sectfile.sectionIndex("list");
        java.lang.String s = null;
        int j = sectfile.vars(i);
        int k = 0;
        for(int l = 0; l < j; l++)
        {
            java.lang.String s1 = sectfile.var(i, l);
            if(lineIsIntroName(s1))
                continue;
            if(k == _completeMissions)
            {
                s = sectfile.line(i, l);
                break;
            }
            k++;
        }

        com.maddox.util.SharedTokenizer.set(s);
        j = com.maddox.util.SharedTokenizer.countTokens();
        int i1 = (int)java.lang.Math.round((double)(j - 1) * java.lang.Math.random());
        java.lang.String s2;
        for(s2 = com.maddox.util.SharedTokenizer.nextToken(); i1-- > 0; s2 = com.maddox.util.SharedTokenizer.nextToken());
        java.lang.String s3 = "missions/campaign/" + branch() + "/" + missionsDir() + "/" + s2;
        com.maddox.rts.SectFile sectfile1 = new SectFile(s3, 0);
        if(!isDGen() && !prepareMission(sectfile1))
        {
            return null;
        } else
        {
            copySavedStatics(sectfile1);
            return sectfile1;
        }
    }

    private boolean lineIsIntroName(java.lang.String s)
    {
        if(s == null)
            return false;
        int i = s.length();
        for(int j = 0; j < i; j++)
        {
            char c = s.charAt(j);
            if(c == '*')
                return true;
            if(c > ' ')
                return false;
        }

        return false;
    }

    protected boolean prepareMission(com.maddox.rts.SectFile sectfile)
    {
        java.lang.String s = sectfile.get("MAIN", "player", (java.lang.String)null);
        java.lang.String s1 = s.substring(0, s.length() - 1);
        int ai[] = {
            0, 0, 0, 0
        };
        int i = sectfile.sectionIndex("Wing");
        int j = sectfile.vars(i);
        for(int k = 0; k < j; k++)
        {
            java.lang.String s2 = sectfile.var(i, k);
            if(s2.startsWith(s1))
            {
                int i1 = s2.charAt(s2.length() - 1) - 48;
                int k1 = sectfile.get(s2, "Planes", 0, 0, 4);
                ai[i1] = k1;
            }
        }

        int l = -1;
        int j1 = 0;
label0:
        switch(_rank)
        {
        default:
            break;

        case 6: // '\006'
            for(int l1 = 0; l1 < 4; l1++)
            {
                if(ai[l1] == 0)
                    continue;
                l = l1;
                j1 = 0;
                break;
            }

            break;

        case 5: // '\005'
            int i2 = 0;
            for(int k2 = 0; k2 < 4; k2++)
            {
                if(ai[k2] == 0)
                    continue;
                if(i2 == 0)
                {
                    i2++;
                    continue;
                }
                l = k2;
                j1 = 0;
                break;
            }

            if(l >= 0)
                break;
            // fall through

        case 4: // '\004'
            for(int j2 = 3; j2 >= 0; j2--)
                if(ai[j2] != 0)
                {
                    l = j2;
                    j1 = 0;
                    break label0;
                }

            break;

        case 3: // '\003'
            for(int l2 = 0; l2 < 4; l2++)
            {
                if(ai[l2] <= 1)
                    continue;
                l = l2;
                j1 = 1;
                break;
            }

            if(l >= 0)
                break;
            // fall through

        case 2: // '\002'
            for(int i3 = 3; i3 >= 0; i3--)
            {
                if(ai[i3] <= 1)
                    continue;
                l = i3;
                j1 = 1;
                break;
            }

            if(l >= 0)
                break;
            for(int j3 = 3; j3 >= 0; j3--)
                if(ai[j3] != 0)
                {
                    l = j3;
                    j1 = 0;
                    break label0;
                }

            break;

        case 1: // '\001'
            for(int k3 = 0; k3 < 4; k3++)
            {
                if(ai[k3] <= 2)
                    continue;
                l = k3;
                j1 = ai[k3] - 1;
                break;
            }

            if(l >= 0)
                break;
            // fall through

        case 0: // '\0'
            for(int l3 = 3; l3 >= 0; l3--)
                if(ai[l3] != 0)
                {
                    l = l3;
                    j1 = ai[l3] - 1;
                    break label0;
                }

            break;
        }
        for(int i4 = 0; i4 < 4; i4++)
            if(ai[i4] > 0)
            {
                java.lang.String s3 = sectfile.get(s1 + i4, "Class", (java.lang.String)null);
                try
                {
                    java.lang.Class class1 = com.maddox.rts.ObjIO.classForName(s3);
                    if(!com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
                        ai[i4] = 0;
                }
                catch(java.lang.Exception exception)
                {
                    ai[i4] = 0;
                }
            }

        if(ai[l] <= 0)
        {
            l++;
            j1 = -1;
            for(; l < 4; l++)
            {
                if(ai[l] <= 0)
                    continue;
                j1 = 0;
                break;
            }

            if(j1 < 0)
                while(l > 0) 
                {
                    l--;
                    if(ai[l] > 0)
                    {
                        j1 = ai[l] - 1;
                        break;
                    }
                }
        }
        if(j1 >= 0)
        {
            sectfile.set("Main", "player", s1 + l);
            sectfile.set("Main", "playerNum", j1);
            return true;
        } else
        {
            return false;
        }
    }

    public void init(com.maddox.il2.game.campaign.Awards awards1, java.lang.String s, java.lang.String s1, int i, int j)
    {
        _awards = awards1;
        _nawards = -1;
        _country = s;
        _missionsDir = s1;
        _difficulty = i;
        _rank = j;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private static java.lang.Class _cls;
    public com.maddox.il2.game.campaign.Awards _awards;
    public int _nawards;
    public java.lang.String _country;
    public java.lang.String _missionsDir;
    public int _difficulty;
    public int _completeMissions;
    public int _score;
    public int _scoreAward;
    public int _scoreRank;
    public int _rank;
    public int _aircraftLost;
    public int _enemyAirDestroyed;
    public int _enemyGroundDestroyed[];
    public int _friendDestroyed;
    public java.lang.String _lastMissionMapName;
    public java.lang.String _epilogueTrack;
    private int amountMissions;
    private int newScoreRank;
    private com.maddox.rts.SectFile missionsSectFile;

    static 
    {
        _cls = com.maddox.il2.game.campaign.Campaign.class;
        com.maddox.rts.ObjIO.field(_cls, "_awards");
        com.maddox.rts.ObjIO.field(_cls, "_nawards");
        com.maddox.rts.ObjIO.field(_cls, "_country");
        com.maddox.rts.ObjIO.field(_cls, "_missionsDir");
        com.maddox.rts.ObjIO.field(_cls, "_difficulty");
        com.maddox.rts.ObjIO.field(_cls, "_completeMissions");
        com.maddox.rts.ObjIO.field(_cls, "_score");
        com.maddox.rts.ObjIO.field(_cls, "_scoreAward");
        com.maddox.rts.ObjIO.field(_cls, "_scoreRank");
        com.maddox.rts.ObjIO.field(_cls, "_rank");
        com.maddox.rts.ObjIO.field(_cls, "_aircraftLost");
        com.maddox.rts.ObjIO.field(_cls, "_enemyAirDestroyed");
        com.maddox.rts.ObjIO.field(_cls, "_enemyGroundDestroyed");
        com.maddox.rts.ObjIO.field(_cls, "_friendDestroyed");
        com.maddox.rts.ObjIO.field(_cls, "_lastMissionMapName");
        com.maddox.rts.ObjIO.field(_cls, "_epilogueTrack");
    }
}
