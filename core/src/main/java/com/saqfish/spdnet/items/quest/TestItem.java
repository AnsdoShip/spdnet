package com.saqfish.spdnet.items.quest;

import com.saqfish.spdnet.Dungeon;
import com.saqfish.spdnet.Statistics;
import com.saqfish.spdnet.actors.buffs.Buff;
import com.saqfish.spdnet.actors.hero.Hero;
import com.saqfish.spdnet.actors.mobs.Mob;
import com.saqfish.spdnet.items.Item;
import com.saqfish.spdnet.items.artifacts.DriedRose;
import com.saqfish.spdnet.items.artifacts.TimekeepersHourglass;
import com.saqfish.spdnet.messages.Messages;
import com.saqfish.spdnet.scenes.InterlevelScene;
import com.saqfish.spdnet.sprites.ItemSprite;
import com.saqfish.spdnet.sprites.ItemSpriteSheet;
import com.saqfish.spdnet.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class TestItem extends Item {
    {
        unique = true;
        bones = false;
    }

    protected boolean changeDefAct = false;

    protected void changeDefaultAction(String action){
        if(!allowChange(action)) return;
        defaultAction = action;
    }

    protected boolean allowChange(String action){
        return !(action.equals(AC_DROP) || action.equals(AC_THROW));
    }

    @Override
    public void execute(Hero hero, String action){
        super.execute(hero, action);
        if(changeDefAct) changeDefaultAction(action);
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }
}
