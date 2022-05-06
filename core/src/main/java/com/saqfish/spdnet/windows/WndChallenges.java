/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.saqfish.spdnet.windows;

import com.saqfish.spdnet.Challenges;
import com.saqfish.spdnet.Dungeon;
import com.saqfish.spdnet.SPDSettings;
import com.saqfish.spdnet.ShatteredPixelDungeon;
import com.saqfish.spdnet.custom.TextChallenges;
import com.saqfish.spdnet.custom.TextField;
import com.saqfish.spdnet.custom.messages.M;
import com.saqfish.spdnet.custom.utils.CustomGameSettings;
import com.saqfish.spdnet.messages.Messages;
import com.saqfish.spdnet.scenes.PixelScene;
import com.saqfish.spdnet.ui.CheckBox;
import com.saqfish.spdnet.ui.IconButton;
import com.saqfish.spdnet.ui.Icons;
import com.saqfish.spdnet.ui.RedButton;
import com.saqfish.spdnet.ui.RenderedTextBlock;
import com.saqfish.spdnet.ui.ScrollPane;
import com.saqfish.spdnet.ui.Window;
import com.saqfish.spdnet.ui.WndTextInput;
import com.saqfish.spdnet.utils.DungeonSeed;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.ui.Component;

import java.util.ArrayList;

public class WndChallenges extends Window {

	private static final int WIDTH		= 120;
	private static final int HEIGHT		= 162;
	private static final int BTN_HEIGHT = 16;
	private static final int GAP        = 1;

	private boolean editable;
	private ArrayList<CanScrollCheckBox> boxes;
	private ArrayList<CanScrollInfo> infos;
	private CanScrollButton seedButton;
	private CanScrollButton deleteSeedInput;

	public WndChallenges( long checked, boolean editable ) {

		super();

		resize(WIDTH, HEIGHT);

		this.editable = editable;

		ScrollPane pane = new ScrollPane(new Component()) {
			@Override
			public void onClick(float x, float y) {
				int max_size = boxes.size();
				for (int i = 0; i < max_size; ++i) {
					if (boxes.get(i).onClick(x, y))
						return;
				}
				max_size = infos.size();
				for(int i = 0; i<max_size;++i){
					if(infos.get(i).onClick(x,y)){
						return;
					}
				}
				if(seedButton.onClick(x, y)){
					return;
				}
				if(deleteSeedInput.onClick(x, y)){
					return;
				}
			}
		};
		add(pane);
		pane.setRect(0, GAP, WIDTH, HEIGHT - 2 * GAP);
		Component content = pane.content();

		boxes = new ArrayList<>();
		infos = new ArrayList<>();

		boolean isCustom = false;
		float pos = 0;

		for (int i = 0; i < Challenges.NAME_IDS.length; i++) {

			final String challenge = Challenges.NAME_IDS[i];

			if(i==0){
				RenderedTextBlock block = PixelScene.renderTextBlock(10);
				block.text(M.L(TextChallenges.class, "traditional"));
				block.hardlight(TITLE_COLOR);
				block.setPos((WIDTH - block.width()) / 2,
						pos + GAP *4);
				PixelScene.align(block);
				content.add(block);
				pos += block.height() + 8*GAP;
			}

			if(Challenges.NAME_IDS[i].equals("test_mode")){
				RenderedTextBlock block = PixelScene.renderTextBlock(10);
				block.text(M.L(TextChallenges.class, "expansion"));
				block.hardlight(0xFF00FF);
				block.setPos((WIDTH - block.width()) / 2,
						pos + GAP *4);
				PixelScene.align(block);
				content.add(block);
				pos += block.height() + 8*GAP;
				isCustom = true;
			}

			CanScrollCheckBox cb = new CanScrollCheckBox( M.TL(isCustom ? TextChallenges.class : Challenges.class, challenge));
			cb.checked((checked & Challenges.MASKS[i]) != 0);
			cb.active = editable;

			if (i > 0) {
				pos += GAP;
			}
			cb.setRect(0, pos, WIDTH - 16, BTN_HEIGHT);

			content.add(cb);
			boxes.add(cb);

			boolean finalIsCustom = isCustom;
			CanScrollInfo info = new CanScrollInfo(Icons.get(Icons.INFO)) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scene().add(
							new WndMessage(M.L(finalIsCustom ? TextChallenges.class : Challenges.class, challenge + "_desc"))
					);
				}
			};
			info.setRect(cb.right(), pos, 16, BTN_HEIGHT);
			infos.add(info);
			content.add(info);

			pos = cb.bottom();
		}

		pos += GAP;

		seedButton = new CanScrollButton(""){
			@Override
			protected void onClick() {
				super.onClick();
				WndTextInput wti = new WndTextInput(M.L(TextChallenges.class, "seed_custom_title"), "",
						99, false, Messages.get(WndTextInput.class,"yes"), Messages.get(WndTextInput.class,"no")){
					@Override
					public void onSelect(boolean positive, String text) {
						super.onSelect(positive, text);
						if(positive){
							CustomGameSettings.putSeedString(text);
							updateSeedText(text, true);
						}
					}
				};
				Game.scene().add(wti);
			}
		};

		content.add(seedButton);
		seedButton.enable(editable);
		seedButton.setRect(0, pos, WIDTH-GAP-16, 22);
		updateSeedText("", false);

		deleteSeedInput = new CanScrollButton(M.L(TextChallenges.class, "delete_seed_input")){
			@Override
			protected void onClick() {
				super.onClick();
				CustomGameSettings.putSeedString("");
				updateSeedText("", false);
			}
		};
		content.add(deleteSeedInput);
		deleteSeedInput.enable(editable);
		deleteSeedInput.setRect(seedButton.right() + GAP, pos, 16, 22);

		pos = seedButton.bottom();

		content.setSize(WIDTH, (int) pos + GAP*2);
		pane.scrollTo(0, 0);
	}

	@Override
	public void onBackPressed() {

		if (editable) {
			int value = 0;
			for (int i=0; i < boxes.size(); i++) {
				if (boxes.get( i ).checked()) {
					value |= Challenges.MASKS[i];
				}
			}
			SPDSettings.challenges( value );
		}

		super.onBackPressed();
	}
	private void updateSeedText(String text, boolean update){
		if(CustomGameSettings.getSeedString().equals("") && editable){
			seedButton.text(M.L(TextChallenges.class, "hint"));
		}else{
			seedButton.text(
					M.L(TextChallenges.class, "seed_custom_title") +
							(update ? text :
									(editable ? CustomGameSettings.getSeedString() : DungeonSeed.convertToCode(Dungeon.seed))
							)
			);
		}
	}

	public static class CanScrollCheckBox extends CheckBox{

		public CanScrollCheckBox(String label) {
			super(label);
		}

		protected boolean onClick(float x, float y){
			if(!inside(x,y)) return false;
			if(active) onClick();

			return true;
		}

		@Override
		protected void layout(){
			super.layout();
			hotArea.width = hotArea.height = 0;
		}
	}

	public static class CanScrollInfo extends IconButton{
		public CanScrollInfo(Image Icon){super(Icon);}

		protected boolean onClick(float x, float y){
			if(!inside(x,y)) return false;
			if(active) onClick();
			return true;
		}

		@Override
		protected void layout(){
			super.layout();
			hotArea.width = hotArea.height = 0;
		}
	}


	public static class CanScrollButton extends RedButton {

		public CanScrollButton(String label) {
			super(label, 7);
		}

		@Override
		protected void layout() {
			super.layout();
			hotArea.height = hotArea.width = 0;
		}

		protected boolean onClick(float x, float y){
			if(!inside(x,y)) return false;
			if(active) onClick();

			return true;
		}
	}
}