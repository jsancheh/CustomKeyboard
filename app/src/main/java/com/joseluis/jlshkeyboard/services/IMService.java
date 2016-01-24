package com.joseluis.jlshkeyboard.services;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

import com.joseluis.jlshkeyboard.R;
import com.joseluis.jlshkeyboard.views.CustomKeyboardView;

import java.util.Arrays;

/**
 * Author José Luis Sánchez Hurtado
 */
public class IMService extends android.inputmethodservice.InputMethodService
        implements KeyboardView.OnKeyboardActionListener{

    /**
     * Array keys {CAPS, DELETE, DONE, SPACE BAR}
     */
    private String[] aKeys = {"-1","-5","-4","32"};

    /**
     *  Custom keyboard view
     */
    private CustomKeyboardView kv;

    /**
     * Keyboard Class (Android)
     */
    private Keyboard keyboard;

    /**
     * Caps FLAG
     */
    private boolean caps = false;

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        playClick(primaryCode);
        switch(primaryCode){

            //key delete pressed
            case Keyboard.KEYCODE_DELETE :
                ic.deleteSurroundingText(1, 0);
                break;

            //key caps pressed
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
                break;

            //key done pressed
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            default:

                //others
                char code = (char)primaryCode;
                if(Character.isLetter(code) && caps){
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code),1);
        }
    }

    @Override
    public void onPress(int primaryCode) {
        checkKeys(primaryCode);
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onText(CharSequence text) {
    }

    @Override
    public void swipeDown() {
    }

    @Override
    public void swipeLeft() {
    }

    @Override
    public void swipeRight() {
    }

    @Override
    public void swipeUp() {
    }

    @Override
    public View onCreateInputView() {

        //inflate the keyboard layout
        kv = (CustomKeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);

        //instance keyboard with own keys
        keyboard = new Keyboard(this, R.xml.qwerty);

        //set keyboar to keyboard layout
        kv.setKeyboard(keyboard);

        //add listener
        kv.setOnKeyboardActionListener(this);
        return kv;
    }

    /**
     * Control keys (Done, Delete, Return, Space bar)
     * @param keyCode
     */
    private void playClick(int keyCode){

        AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch(keyCode){
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default: am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

    /**
     * Disable/Enable key's preview
     * @param keyCode
     */
    private void checkKeys(int keyCode){
        boolean enablePreview = contains(String.valueOf(keyCode));
        kv.setPreviewEnabled(!enablePreview);
    }

    /**
     * Check if key is in Array
     * @param key
     * @return
     */
    private boolean contains(final String key) {
        return Arrays.asList(aKeys).contains(key);
    }

}