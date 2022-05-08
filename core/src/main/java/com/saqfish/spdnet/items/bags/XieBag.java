package com.saqfish.spdnet.items.bags;

import com.saqfish.spdnet.actors.hero.Hero;
import com.saqfish.spdnet.items.Item;
import com.saqfish.spdnet.items.quest.TestItem;
import com.saqfish.spdnet.items.spells.BeaconOfReturning;
import com.saqfish.spdnet.messages.Messages;
import com.saqfish.spdnet.sprites.ItemSpriteSheet;
import com.saqfish.spdnet.utils.GLog;

public class XieBag extends Bag {

    {
        image = ItemSpriteSheet.SOMETHING;
    }

    @Override
    public void doThrow( Hero hero ) {
        GLog.w(Messages.get(XieBag.class,"why"));
    }

    @Override
    public boolean canHold( Item item ) {
        if (item instanceof TestItem){
            return super.canHold(item);
        } else {
            return false;
        }
    }

    public int capacity(){
        return 1;
    }

    @Override
    public void onDetach( ) {
        super.onDetach();
        for (Item item : items) {
            if (item instanceof BeaconOfReturning) {
                ((BeaconOfReturning) item).returnDepth = -1;
            }
        }
    }

    @Override
    public int value() {
        return 0;
    }

}
