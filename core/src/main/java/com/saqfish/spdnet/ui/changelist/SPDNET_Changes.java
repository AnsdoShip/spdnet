package com.saqfish.spdnet.ui.changelist;

import com.saqfish.spdnet.messages.Messages;
import com.saqfish.spdnet.net.ui.NetIcons;

import java.util.ArrayList;

public class SPDNET_Changes {

    public static void addAllChanges( ArrayList<ChangeInfo> changeInfos ){
        add_v1_0_Changes(changeInfos);
    }

    public static void add_v1_0_Changes( ArrayList<ChangeInfo> changeInfos ) {
        ChangeInfo changes = new ChangeInfo("SPD-NET", true, "");
        changes.hardlight(0xCCCCCC);
        changeInfos.add(changes);

        changes.addButton( new ChangeButton(NetIcons.get(NetIcons.GLOBE), Messages.get(SPDNET_Changes.class,"update"),
                Messages.get(SPDNET_Changes.class,"updatelogs")));
    }
}
