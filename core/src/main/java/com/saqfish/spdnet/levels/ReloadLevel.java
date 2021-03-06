package com.saqfish.spdnet.levels;

import com.saqfish.spdnet.Assets;
import com.saqfish.spdnet.Dungeon;
import com.saqfish.spdnet.actors.Actor;
import com.saqfish.spdnet.actors.mobs.Mob;

public class ReloadLevel extends Level {
    private static final int SIZE = 5;
    private static final int[] pre_map = new int[]{64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64
            , 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 69, 69, 69, 69, 69, 69, 69, 69, 69, 64, 98, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 98, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 98, 4, 4, 4, 4, 4, 4, 64, 0, 1, 0, 0, 0, 0, 0, 0, 1, 64, 98, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 98, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 98, 4, 4, 4, 4, 4, 4, 64, 2, 3, 1, 2, 2, 2, 0, 2, 3, 64, 98, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 98, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 98, 4, 4, 4, 4, 4, 4, 64, 0, 1, 1, 12, 16, 12, 0, 2, 3, 64, 98, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 98, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 98, 4, 4, 4, 4, 4, 4, 64, 2, 3, 1, 12, 12, 12, 0, 2, 3, 64, 98, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 98, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 98, 4, 4, 4, 4, 4, 4, 64, 0, 1, 1, 12, 12, 0, 0, 2, 3, 64, 98, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 98, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 98, 4, 4, 4, 4, 4, 4, 64, 2, 3, 1, 12, 12, 12, 0, 2, 3, 64, 98, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 98, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 98, 4, 4, 4, 4, 4, 4, 64, 0, 1, 3, 96, 12, 12, 2, 2, 3, 64, 98, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 98, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 80, 4, 4, 4, 4, 4, 4, 98, 64, 64, 64, 64, 64, 80, 64, 64, 64, 64, 64, 64, 64, 64, 80, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 80, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 4, 4, 4, 4, 4, 4, 4, 80, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 80, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 4, 4, 4, 4, 4, 4, 4, 64, 64, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 64, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 4, 80, 4, 4, 4, 4, 4, 4, 98, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 64, 64, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 0, 0, 0, 96, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 4, 3, 1, 3, 1, 4, 3, 1, 3, 1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 64, 64, 64, 64, 64, 64, 64, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 0, 0, 4, 2, 3, 2, 3, 4, 2, 3, 2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 124, 124, 124, 124, 124, 124, 124, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 4, 3, 1, 3, 1, 4, 3, 1, 3, 1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 124, 124, 124, 124, 124, 124, 124, 64, 64, 98, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 4, 2, 3, 2, 3, 4, 2, 3, 3, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 98, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 80, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 98, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 4, 3, 1, 3, 1, 4, 3, 1, 3, 1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 98, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 4, 2, 3, 2, 3, 4, 2, 3, 2, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 98, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 4, 3, 1, 3, 1, 4, 3, 1, 3, 1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 98, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 4, 2, 3, 2, 3, 4, 2, 3, 2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 98, 4, 4, 4, 4, 4, 4, 80, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 80, 4, 4, 4, 4, 4, 4, 98, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 80, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 4, 80, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 80, 64, 64, 64, 64, 64, 64, 64, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 124, 124, 124, 124, 124, 124, 124, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 124, 124, 124, 124, 124, 124, 124, 64, 64, 98, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 98, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 98, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 98, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 98, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 98, 4, 4, 4, 4, 4, 4, 64, 4, 4, 4, 4, 4, 4, 4, 4, 4, 64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 4, 4, 4, 4, 4, 4, 98, 64, 64, 98, 4, 4, 4, 4, 4, 4, 80, 4, 4, 4, 4, 4, 4, 4, 4, 4, 80, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 80, 4, 4, 4, 4, 4, 4, 98, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64};

    public ReloadLevel() {
        this.color1 = 5459774;
        this.color2 = 12179041;
        Dungeon.isChallenged(32);
        this.viewDistance = 10;
    }

    private int mapToTerrain(int var1) {
        if (var1 != 1 && var1 != 2 && var1 != 3) {
            if (var1 != 4) {
                if (var1 == 16) {
                    return 7;
                }

                if (var1 == 17) {
                    return 8;
                }

                switch(var1) {
                    case -2147483644:
                        break;
                    case -2147483584:
                    case 64:
                    case 115:
                        return 4;
                    case -2147483550:
                    case 98:
                        return 25;
                    case -2147483524:
                    case 124:
                    case 140:
                        return 27;
                    case 69:
                        return 12;
                    case 80:
                        return 5;
                    case 96:
                        return 1;
                    case 120:
                        return 20;
                    default:
                        return 1;
                }
            }

            return 14;
        } else {
            return 29;
        }
    }

    protected boolean build() {
        this.setSize(60, 60);
        this.exit = this.width * 28 + 28;
        this.entrance = this.width * 4 + 13;

        for(int var1 = 0; var1 < this.map.length; ++var1) {
            this.map[var1] = this.mapToTerrain(pre_map[var1]);
        }

        return true;
    }

    protected void createItems() {
    }

    public Mob createMob() {
        return null;
    }

    protected void createMobs() {
    }

    public int randomRespawnCell() {
        return this.entrance - this.width();
    }

    public Actor respawner() {
        return null;
    }

    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_CITY;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_CITY;
    }
}
