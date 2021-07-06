package com.shatteredpixel.shatteredpixeldungeon.net.windows;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shatteredpixel.shatteredpixeldungeon.net.Settings;
import com.shatteredpixel.shatteredpixeldungeon.net.Types;
import com.shatteredpixel.shatteredpixeldungeon.net.events.recieve.playerlist.PlayerList;
import com.shatteredpixel.shatteredpixeldungeon.net.ui.LabeledText;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Game;
import com.watabou.noosa.ui.Component;

import static com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon.net;
import static com.shatteredpixel.shatteredpixeldungeon.net.Util.error;
import static com.shatteredpixel.shatteredpixeldungeon.net.Util.message;
import static com.shatteredpixel.shatteredpixeldungeon.net.Util.showPlayerList;

public class WndServerInfo extends Window {
    private static final int WIDTH_P	    = 122;
    private static final int WIDTH_L	    = 223;

    private static final float GAP          = 2;

    RenderedTextBlock title;
    ColorBlock sep1;
    LabeledText type;
    LabeledText host;
    LabeledText port;
    LabeledText status;
    RedButton connectBtn;
    RedButton playerListButton;

    public WndServerInfo() {
        super();

        int height, y = 0;

        int width = PixelScene.landscape() ? WIDTH_L : WIDTH_P;
        int maxBtnHeight = PixelScene.landscape() ? 16: 12;
        int maxTitleHeight =  PixelScene.landscape() ? 12: 9;
        String maxTitleText = PixelScene.landscape() ? "Server Info": "Server";

        title = PixelScene.renderTextBlock(maxTitleText, maxTitleHeight);
        title.hardlight(TITLE_COLOR);
        add(title);

        sep1 = new ColorBlock(1, 1, 0xFF000000);
        add(sep1);

        type = new LabeledText("Type", Settings.uri().getScheme());
        add(type);

        host = new LabeledText("Host", Settings.uri().getHost());
        add(host);

        port = new LabeledText("Port", String.valueOf(Settings.uri().getPort()));
        add(port);


        playerListButton = new RedButton("Players") {
            @Override
            protected void onClick() {
                super.onClick();
                if (net().connected()) {
                    net().send(Types.Recieve.MESSAGE, Types.Send.PLAYERLIISTREQUEST, null);
                }else{
                    error("You are not connected!");
                    return;
                }
                net().socket().once(Types.Recieve.PLAYERLIST, args -> {
                    String data = (String) args[0];
                    try {
                        final PlayerList pl = net().mapper().readValue(data, PlayerList.class);
                        Game.runOnRenderThread(() -> showPlayerList(pl));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                });
            }
        };
        add(playerListButton);

        connectBtn = new RedButton("Connect") {
            @Override
            public synchronized void update() {
                super.update();
                text.text(net().connected() ? "Disconnect" : "Connect");
                setSize(net().connected() ? 50 : 40, maxBtnHeight);
                setPos(playerListButton.left() - connectBtn.width() - GAP, 0);
            }

            @Override
            protected void onClick() {
                super.onClick();
                net().toggle();
            }
        };
        add(connectBtn);
        status = new LabeledText("Status", net().connected() ? "Connected" : "Disconnected") {
            @Override
            public synchronized void update() {
                super.update();
                text().text(net().connected() ? "Connected" : "Disconnected");
                text().hardlight(net().connected() ? 0x00FF00 : 0xFF0000);
            }
        };
        add(status);


        float bottom = y;

        title.setPos(GAP, (PixelScene.landscape() ? 1: 2));
        playerListButton.setSize(40, maxBtnHeight);
        playerListButton.setPos(width - playerListButton.width(), 0);
        connectBtn.setSize(40, maxBtnHeight);
        connectBtn.setPos(0, 0);

        sep1.size(width, 1);
        sep1.y = connectBtn.bottom() + GAP;

        bottom = sep1.y + 1;

        float right = 0;

        type.setPos(right, bottom + GAP);
        bottom = type.bottom() + GAP;

        host.setPos(right + host.width(), bottom + GAP);
        bottom = host.bottom() + GAP;

        port.setPos(right + port.width(), bottom + GAP);
        bottom = port.bottom() + GAP;

        right = 0;

        status.setPos(right, bottom + GAP);

        height = (int) (status.bottom() + 4 * GAP);

       resize(width, height);
    }


}


