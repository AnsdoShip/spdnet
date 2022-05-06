package com.saqfish.spdnet.items.quest;


import com.saqfish.spdnet.Dungeon;
import com.saqfish.spdnet.Statistics;
import com.saqfish.spdnet.actors.buffs.Buff;
import com.saqfish.spdnet.actors.buffs.Hunger;
import com.saqfish.spdnet.actors.buffs.LockedFloor;
import com.saqfish.spdnet.actors.hero.Hero;
import com.saqfish.spdnet.actors.mobs.Mob;
import com.saqfish.spdnet.items.Item;
import com.saqfish.spdnet.items.artifacts.DriedRose;
import com.saqfish.spdnet.items.artifacts.TimekeepersHourglass;
import com.saqfish.spdnet.messages.Messages;
import com.saqfish.spdnet.scenes.InterlevelScene;
import com.saqfish.spdnet.scenes.TitleScene;
import com.saqfish.spdnet.sprites.ItemSprite;
import com.saqfish.spdnet.sprites.ItemSpriteSheet;
import com.saqfish.spdnet.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class ReadyGoKet extends Item {

    public static final float TIME_TO_USE = 5;

    public static final String AC_PORT = "传送";


    private int returnDepth = 0;
    private int returnPos;

    {
        image = ItemSpriteSheet.TXZONE;

        stackable = false;
        unique = true;
    }

    public int image() {
        if (Dungeon.depth != 1 ) {
            return ItemSpriteSheet.TXZTWO;
        }
        return image;
    }

    private static final String DEPTH = "depth";
    private static final String POS = "pos";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(DEPTH, returnDepth);
        if (returnDepth != -1) {
            bundle.put(POS, returnPos);
        }
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        returnDepth = bundle.getInt(DEPTH);
        returnPos = bundle.getInt(POS);
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_PORT);

        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        if (action == AC_PORT) {

            if (Dungeon.bossLevel()) {
                hero.spend(TIME_TO_USE);
                GLog.w(Messages.get(TitleScene.class,"no_used"));
                return;
            }

            if (hero.HP <= hero.HT/2 ) {
                hero.spend(TIME_TO_USE);
                GLog.w(Messages.get(TitleScene.class,"no_hp"));
                return;
            }

        }

        if (action == AC_PORT) {

            Buff buff = Dungeon.hero
                    .buff(TimekeepersHourglass.timeFreeze.class);
            if (buff != null)
                buff.detach();

            for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0]))
                if (mob instanceof DriedRose.GhostHero)
                    mob.destroy();
            if (Dungeon.depth!=1 ){

                returnDepth = Dungeon.depth;
                returnPos = hero.pos;
                InterlevelScene.mode = InterlevelScene.Mode.LETGO;
                InterlevelScene.returnDepth = Dungeon.depth = 1;
            } else {
                InterlevelScene.mode = InterlevelScene.Mode.RETURN;
            }

            InterlevelScene.returnDepth = returnDepth;
            InterlevelScene.returnPos = returnPos;
            Game.switchScene(InterlevelScene.class);

        } else {

            super.execute(hero, action);

        }
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }


    private static final ItemSprite.Glowing YELLOW = new ItemSprite.Glowing(0xFF00FF);
    private static final ItemSprite.Glowing BLUE = new ItemSprite.Glowing(0x00FFFF);
    @Override
    public ItemSprite.Glowing glowing() {
        if (Dungeon.depth!=1 ) {
            return BLUE;
        } else {
            return YELLOW;
        }
    }

    @Override
    public String info() {
        return Messages.get(this, "desc");
    }
}

