package com.saqfish.spdnet.actors.buffs;

import com.saqfish.spdnet.actors.hero.Hero;
import com.saqfish.spdnet.messages.Messages;
import com.saqfish.spdnet.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public class ColdDown extends Buff {

    @Override
    public int icon() {
        return BuffIndicator.CORRUPT;
    }

    @Override
    public void tintIcon(Image icon) {
        icon.hardlight(0x777777);
    }

    {
        type = buffType.POSITIVE;
    }

    public static int level = 0;
    private int interval = 1;

    @Override
    public boolean act() {
        if (target.isAlive()) {

            spend(interval);
            if (--level <= 0) {
                detach();
            }

        }

        return true;
    }

    public int level() {
        return level;
    }

    public void set( int value, int time ) {
        //decide whether to override, preferring high value + low interval
        if (Math.sqrt(interval)*level <= Math.sqrt(time)*value) {
            level = value;
            interval = time;
            spend(time - cooldown() - 1);
        }
    }

    @Override
    public float iconFadePercent() {
        if (target instanceof Hero){
            float max = ((Hero) target).lvl;
            return Math.max(0, (max-level)/max);
        }
        return 0;
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", level, dispTurns(visualcooldown()));
    }

    private static final String LEVEL	    = "level";
    private static final String INTERVAL    = "interval";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( INTERVAL, interval );
        bundle.put( LEVEL, level );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        interval = bundle.getInt( INTERVAL );
        level = bundle.getInt( LEVEL );
    }
}
