package com.lyledenman.gadago;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Image;

public class BooleanButton extends Button {
    public boolean isPreference() {
        return isPreference;
    }

    public void setPreference(boolean preference) {
        isPreference = preference;
    }

    boolean isPreference = false;

    BooleanButton() {
        super();
    }

    BooleanButton(Command cmd) {
        super(cmd);
    }

    BooleanButton(Image icon) {
        super(icon);
    }

    BooleanButton(String text) {
        super(text);
    }

    BooleanButton(String text, Image icon) {
        super(text, icon);
    }

}