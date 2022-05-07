package com.saqfish.spdnet.items.quest;


import static com.saqfish.spdnet.ShatteredPixelDungeon.net;

import com.saqfish.spdnet.Assets;
import com.saqfish.spdnet.Chrome;
import com.saqfish.spdnet.Dungeon;
import com.saqfish.spdnet.Statistics;
import com.saqfish.spdnet.actors.Actor;
import com.saqfish.spdnet.actors.Char;
import com.saqfish.spdnet.actors.buffs.Buff;
import com.saqfish.spdnet.actors.buffs.ColdDown;
import com.saqfish.spdnet.actors.buffs.LockedFloor;
import com.saqfish.spdnet.actors.hero.Hero;
import com.saqfish.spdnet.custom.messages.M;
import com.saqfish.spdnet.custom.utils.Constants;
import com.saqfish.spdnet.effects.Speck;
import com.saqfish.spdnet.items.artifacts.TimekeepersHourglass;
import com.saqfish.spdnet.items.scrolls.ScrollOfTeleportation;
import com.saqfish.spdnet.messages.Messages;
import com.saqfish.spdnet.plants.Swiftthistle;
import com.saqfish.spdnet.scenes.CellSelector;
import com.saqfish.spdnet.scenes.GameScene;
import com.saqfish.spdnet.scenes.InterlevelScene;
import com.saqfish.spdnet.scenes.PixelScene;
import com.saqfish.spdnet.sprites.HeroSprite;
import com.saqfish.spdnet.sprites.ItemSprite;
import com.saqfish.spdnet.sprites.ItemSpriteSheet;
import com.saqfish.spdnet.ui.Icons;
import com.saqfish.spdnet.ui.RenderedTextBlock;
import com.saqfish.spdnet.ui.ScrollPane;
import com.saqfish.spdnet.ui.StyledButton;
import com.saqfish.spdnet.ui.Window;
import com.saqfish.spdnet.utils.BArray;
import com.saqfish.spdnet.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public class LevelTeleporter extends TestItem {
    {
        image = ItemSpriteSheet.TXZONE;

        defaultAction = AC_INTER_TP;
    }

    private static final String AC_INTER_TP = "interlevel_tp";

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );

        actions.add(AC_INTER_TP);
        return actions;
    }

    private static final ItemSprite.Glowing YELLOW = new ItemSprite.Glowing(0xFF00FF);
    private static final ItemSprite.Glowing BLUE = new ItemSprite.Glowing(0x00FFFF);
    @Override
    public ItemSprite.Glowing glowing() {
        if (Dungeon.depth!=1 ) {
            return YELLOW;
        } else {
            return BLUE;
        }
    }

    public int image() {
        if (Dungeon.depth != 1 ) {
            return ItemSpriteSheet.TXZTWO;
        }
        return image;
    }

    @Override
    public void doThrow( Hero hero ) {
        if(net().connected()) {
            GLog.w(Messages.get(LevelTeleporter.class,"why"));
        } else {
            GameScene.selectCell(thrower);
        }
    }

    @Override
    public void execute( Hero hero, String action ) {
        super.execute( hero, action );
            if(action.equals(AC_INTER_TP)){
            if(Dungeon.hero.buff(LockedFloor.class) != null || hero.HP < hero.HT/2 || Dungeon.hero.buff(ColdDown.class) != null) {
                GLog.w(Messages.get(this,"cannot_send"));
                return;
            }
            Buff.affect(hero, ColdDown.class).set( (100-Dungeon.depth/5), 1 );
            GameScene.show(new WndSelectLevel());
                GLog.w(Messages.get(this,"must"));
        }
    }

    public static class WndSelectLevel extends Window {
        private static final int WIDTH = 120;
        private static final int GAP = 2;
        private static final int BTN_SIZE = 16;
        private static final int PANE_MAX_HEIGHT = 96;

        private int selectedLevel = 0;
        private ArrayList<DepthButton> btns = new ArrayList<>();
        private StyledButton icb;

        public WndSelectLevel(){
            super();
            resize(WIDTH, 0);
            RenderedTextBlock ttl = PixelScene.renderTextBlock(8);
            ttl.text(M.L(LevelTeleporter.class, "interlevel_teleport_title"));
            add(ttl);
            ttl.setPos(WIDTH/2f-ttl.width()/2f, GAP);
            PixelScene.align(ttl);
            ScrollPane sp = new ScrollPane(new Component()){
                @Override
                public void onClick(float x, float y) {
                    super.onClick(x, y);
                    for(DepthButton db: btns){
                        if(db.click(x, y)){
                            break;
                        }
                    }
                }
            };
            add(sp);
            //sp.setRect(0, ttl.bottom() + GAP * 2, WIDTH, PANE_MAX_HEIGHT);
            //GLog.i("%f", ttl.bottom() + GAP * 2);
            Component content = sp.content();
            float xpos = (WIDTH - 5*BTN_SIZE - GAP*8)/2f;
            float ypos = 0;
            float each = GAP*2 + BTN_SIZE;
            for(int i = 1; i< 25; ++i){
                int column = i % 5;
                int row = i / 5;
                final int j = i+1;
                DepthButton db = new DepthButton(j){
                    @Override
                    protected void onClick() {
                        super.onClick();
                        setSelectedLevel(j);
                    }
                };
                db.enable(!(j > Statistics.deepestFloor));
                db.setRect(xpos + column * each, ypos + row * each, BTN_SIZE, BTN_SIZE);
                PixelScene.align(db);
                content.add(db);
                btns.add(db);
            }

            content.setSize(WIDTH, btns.get(btns.size() - 1).bottom());
            sp.setRect(0, ttl.bottom() + GAP * 2, WIDTH, Math.min(btns.get(btns.size()-1).bottom(), PANE_MAX_HEIGHT));

            icb = new StyledButton(Chrome.Type.RED_BUTTON, M.L(LevelTeleporter.class, "interlevel_teleport_go", selectedLevel)){
                @Override
                protected void onClick() {
                    super.onClick();
                    Buff buff = Dungeon.hero.buff(TimekeepersHourglass.timeFreeze.class);
                    if (buff != null) buff.detach();
                    buff = Dungeon.hero.buff(Swiftthistle.TimeBubble.class);
                    if (buff != null) buff.detach();
                    InterlevelScene.mode = InterlevelScene.Mode.RETURN;
                    InterlevelScene.returnDepth = selectedLevel;
                    InterlevelScene.returnPos = -1;
                    Game.switchScene( InterlevelScene.class );
                }
            };
            add(icb);
            icb.icon(Icons.get(Icons.DEPTH));
            icb.setRect(0, sp.bottom() + GAP * 2, WIDTH, BTN_SIZE);
            setSelectedLevel(0);

            sp.scrollTo(0, 0);

            resize(WIDTH, (int) (icb.bottom()));

            sp.setPos(0, ttl.bottom() + GAP * 2);
        }

        private void setSelectedLevel(int lvl){
            this.selectedLevel = lvl;
            icb.text(M.L(LevelTeleporter.class, "interlevel_teleport_go", selectedLevel));
            icb.enable(selectedLevel > 0 && selectedLevel <= Constants.MAX_DEPTH);
        }
    }

    public static class DepthButton extends StyledButton{
        private int depth;
        public DepthButton(int depth){
            super(Chrome.Type.GREY_BUTTON_TR, String.valueOf(depth), 8);
            this.depth = Dungeon.depth = 1;
        }

        @Override
        protected void layout() {
            super.layout();
            hotArea.width = 0;
            hotArea.height = 0;
        }

        public int getDepth() {
            return depth;
        }

        public boolean click(float x, float y){
            if(active && x > left() && x < right() && y > top() && y < bottom()){
                onClick();
                return true;
            }
            return false;
        }

    }









    public void empoweredRead() {

        GameScene.selectCell(new CellSelector.Listener() {
            @Override
            public void onSelect(Integer target) {
                if (target != null) {
                    //time isn't spent
                    ((HeroSprite)curUser.sprite).read();
                    teleportToLocation(curUser, target);

                }
            }

            @Override
            public String prompt() {
                return Messages.get(ScrollOfTeleportation.class, "prompt");
            }
        });
    }

    public static void teleportToLocation(Hero hero, int pos){
        PathFinder.buildDistanceMap(pos, BArray.or(Dungeon.level.passable, Dungeon.level.avoid, null));
        if (Dungeon.level.avoid[pos] || !Dungeon.level.passable[pos]
                || Actor.findChar(pos) != null){
            GLog.w( Messages.get(ScrollOfTeleportation.class, "cant_reach") );
            return;
        }

        appear( hero, pos );
        Dungeon.level.occupyCell(hero );
        Dungeon.observe();
        GameScene.updateFog();

    }

    public static void appear(Char ch, int pos ) {

        ch.sprite.interruptMotion();

        if (Dungeon.level.heroFOV[pos] || Dungeon.level.heroFOV[ch.pos]){
            Sample.INSTANCE.play(Assets.Sounds.TELEPORT);
        }

        ch.move( pos );
        if (ch.pos == pos) ch.sprite.place( pos );

        if (ch.invisible == 0) {
            ch.sprite.alpha( 0 );
            ch.sprite.parent.add( new AlphaTweener( ch.sprite, 1, 0.4f ) );
        }

        if (Dungeon.level.heroFOV[pos] || ch == Dungeon.hero ) {
            ch.sprite.emitter().start(Speck.factory(Speck.LIGHT), 0.2f, 3);
        }
    }
}
