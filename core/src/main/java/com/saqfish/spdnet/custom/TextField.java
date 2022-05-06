package com.saqfish.spdnet.custom;

import com.badlogic.gdx.Gdx;
import com.saqfish.spdnet.Chrome;
import com.saqfish.spdnet.custom.utils.Constants;
import com.saqfish.spdnet.custom.utils.TextInput2;
import com.saqfish.spdnet.scenes.PixelScene;
import com.saqfish.spdnet.ui.RedButton;
import com.saqfish.spdnet.ui.RenderedTextBlock;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.TextInput;

public abstract class TextField extends RedButton {

    private NinePatch inputWnd;

    private RenderedTextBlock inputText;

    private String hint = "";

    private static final int MIN_HEIGHT_LARGE = 26;
    private static final int MIN_HEIGHT_PACKED = 18;

    private int max_str_length = Integer.MAX_VALUE;

    //defines if title and input window are in separate lines.
    private boolean large = true;

    public TextField( String label ) {
        this(label,"");
    }

    public TextField( String label, String txt ){
        super(label, 8);

        inputText = PixelScene.renderTextBlock( 6 );
        inputText.text( txt );
        add( inputText );
    }

    public void setTitle(String title){
        text.text(title);
    }

    public void setHint(String hint){
        this.hint = hint;
    }

    public void setMaxStringLength(int maxLength){
        this.max_str_length = Math.max(0, maxLength);
    }

    public void setLarge(boolean isLarge){
        this.large = isLarge;
    }

    @Override
    protected void createChildren() {
        super.createChildren();

        inputWnd = Chrome.get(Chrome.Type.TOAST);
        add(inputWnd);
    }

    @Override
    protected void onClick() {
        TextInput2 tinp = new TextInput2(){
            @Override
            public void input(String text) {
                if(parent != null) {
                    if (text.length() > max_str_length) {
                        text = text.substring(0, max_str_length);
                    }
                    text(text);
                    onTextChange();
                }
            }

            @Override
            public void canceled() {
                if(parent != null) {
                    onTextCancel();
                }
            }
        };
        if(Constants.gameIsAndroid()) {
            Gdx.input.getTextInput(tinp, text.text(), inputText.text(), hint);
        }else{
            TextInput2.getTextInput(tinp, text.text(), inputText.text(), hint);
        }
    }

    public abstract void onTextChange();

    public abstract void onTextCancel();

    @Override
    public void text(String value) {
        inputText.text(value);
        if(value.equals("")){
            inputText.clear();
        }
        layout();
    }

    public void label(String value){
        text.text(value);
        layout();
    }

    public String text(){
        return inputText.text();
    }

    @Override
    public void enable(boolean value) {
        super.enable(value);
        inputText.alpha( value ? 1.0f : 0.3f );
        inputWnd.alpha( value ? 1.0f : 0.3f );
    }

    @Override
    protected void layout() {

        if(large) {
            bg.x = x;
            bg.y = y;
            height = Math.max(height, MIN_HEIGHT_LARGE);
            bg.size(width, height);

            hotArea.x = x;
            hotArea.y = y;
            hotArea.width = width;
            hotArea.height = height;

            if (icon != null) {
                text.setPos((width - text.width() - text.height() - 2) / 2, bg.y + 2);
                PixelScene.align(text);
                icon.scale.set((text.height() + 2) / icon.height);
                icon.x = text.right() + 2;
                icon.y = text.top() - 2;
            } else {
                text.setPos((width - text.width()) / 2, bg.y + 2);
                PixelScene.align(text);
            }

            inputWnd.x = x + 2;
            inputWnd.y = text.bottom() + 2;
            inputWnd.size(width - 4, height - 7 - text.height());

            inputText.maxWidth((int) inputWnd.width);
            inputText.setPos((inputWnd.width() - inputText.width()) / 2 + inputWnd.x, inputWnd.y + (inputWnd.height - inputText.height()) / 2);
            PixelScene.align(inputText);

        }else{

            bg.x = x;
            bg.y = y;
            height = Math.max(height, MIN_HEIGHT_PACKED);
            bg.size(width, height);

            hotArea.x = x;
            hotArea.y = y;
            hotArea.width = width;
            hotArea.height = height;

            if (icon != null) {
                icon.x = 1;
                icon.y = (bg.height - icon.height)/2 + bg.y;
                text.setPos(icon.x + icon.width() + 1, (bg.height - text.height())/2 + bg.y);
                PixelScene.align(text);
            } else {
                text.setPos( 2, (bg.height - text.height())/2 + bg.y);
                PixelScene.align(text);
            }

            inputWnd.x = text.right() + 2;
            inputWnd.y = bg.y + 2;
            inputWnd.size(bg.x + bg.width - text.right() - 4, bg.height - 4);

            inputText.maxWidth((int) inputWnd.width);
            inputText.setPos((inputWnd.width() - inputText.width()) / 2 + inputWnd.x, inputWnd.y + (inputWnd.height - inputText.height()) / 2);
            PixelScene.align(inputText);

        }
    }

}

