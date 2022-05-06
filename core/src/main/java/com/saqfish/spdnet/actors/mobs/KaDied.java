package com.saqfish.spdnet.actors.mobs;

import com.saqfish.spdnet.Dungeon;
import com.saqfish.spdnet.actors.Actor;
import com.saqfish.spdnet.actors.Char;
import com.saqfish.spdnet.actors.buffs.Buff;
import com.saqfish.spdnet.effects.particles.ShadowParticle;
import com.saqfish.spdnet.items.Generator;
import com.saqfish.spdnet.scenes.GameScene;
import com.saqfish.spdnet.sprites.RatKingSprite;
import com.saqfish.spdnet.sprites.WarlockSprite;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.PathFinder;

public class KaDied extends Warlock{

    {
        spriteClass = RatKingSprite.class;

        HP = HT = 1;
        defenseSkill = 1;

        EXP = 11;
        maxLvl = 21;

        loot = Generator.Category.POTION;
        lootChance = 0.5f;

        properties.add(Property.UNDEAD);
    }

    @Override
    public int defenseSkill( Char enemy ) {
        return INFINITE_EVASION;
    }

    @Override
    public void damage( int dmg, Object src ) {
    }

    @Override
    public void add( Buff buff ) {
    }

    private static final float SPAWN_DELAY	= 2f;
    private int level;
    public void adjustStats( int level ) {
        this.level = level;
        defenseSkill = attackSkill( null ) * 5;
        enemySeen = true;
    }
    public void spawnAround( int pos ) {
        for (int n : PathFinder.CIRCLE4) {
            int cell = pos + n;
            if (Dungeon.level.passable[pos+2] && Actor.findChar( cell ) == null) {
                spawnAt( cell );
            }
        }
    }


    public static KaDied spawnAt( int pos ) {
        if ((!Dungeon.level.solid[pos] || Dungeon.level.passable[pos]) && Actor.findChar( pos ) == null) {

            KaDied w = new KaDied();
            w.adjustStats( Dungeon.depth );
            w.pos = pos;
            w.state = w.HUNTING;
            GameScene.add( w, SPAWN_DELAY );
            Dungeon.level.occupyCell(w);

            w.sprite.alpha( 0 );
            w.sprite.parent.add( new AlphaTweener( w.sprite, 1, 0.5f ) );

            w.sprite.emitter().burst( ShadowParticle.CURSE, 5 );

            return w;
        } else {
            return null;
        }
    }
}
