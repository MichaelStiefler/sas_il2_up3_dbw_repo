// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GameStateStack.java

package com.maddox.il2.game;

import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.game:
//            GameState

public class GameStateStack
{

    public GameStateStack()
    {
        stack = new ArrayList();
    }

    public boolean isEmpty()
    {
        return stack.isEmpty();
    }

    public int size()
    {
        return stack.size();
    }

    public com.maddox.il2.game.GameState change(int i)
    {
        com.maddox.il2.game.GameState gamestate = com.maddox.il2.game.GameState.get(i);
        if(gamestate == null)
            return null;
        else
            return change(gamestate);
    }

    public com.maddox.il2.game.GameState change(com.maddox.il2.game.GameState gamestate)
    {
        int i = stack.size();
        if(i == 0)
        {
            stack.add(gamestate);
            gamestate.enter(null);
        } else
        {
            com.maddox.il2.game.GameState gamestate1 = (com.maddox.il2.game.GameState)stack.get(i - 1);
            gamestate1.leave(gamestate);
            stack.set(i - 1, gamestate);
            gamestate.enter(gamestate1);
        }
        return gamestate;
    }

    public com.maddox.il2.game.GameState push(int i)
    {
        com.maddox.il2.game.GameState gamestate = com.maddox.il2.game.GameState.get(i);
        if(gamestate == null)
            return null;
        else
            return push(gamestate);
    }

    public com.maddox.il2.game.GameState push(com.maddox.il2.game.GameState gamestate)
    {
        int i = stack.size();
        com.maddox.il2.game.GameState gamestate1 = null;
        if(i > 0)
        {
            gamestate1 = (com.maddox.il2.game.GameState)stack.get(i - 1);
            gamestate1.leavePush(gamestate);
        }
        stack.add(gamestate);
        gamestate.enterPush(gamestate1);
        return gamestate;
    }

    public com.maddox.il2.game.GameState pop()
    {
        int i = stack.size();
        Object obj = null;
        com.maddox.il2.game.GameState gamestate1 = null;
        if(i > 0)
        {
            if(i > 1)
                gamestate1 = (com.maddox.il2.game.GameState)stack.get(i - 2);
            com.maddox.il2.game.GameState gamestate = (com.maddox.il2.game.GameState)stack.get(i - 1);
            gamestate.leavePop(gamestate1);
            stack.remove(i - 1);
            if(gamestate1 != null)
                gamestate1.enterPop(gamestate);
        }
        return gamestate1;
    }

    public com.maddox.il2.game.GameState peek()
    {
        int i = stack.size();
        if(i == 0)
            return null;
        else
            return (com.maddox.il2.game.GameState)stack.get(i - 1);
    }

    protected java.util.ArrayList stack;
}
