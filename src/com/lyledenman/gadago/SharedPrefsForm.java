package com.lyledenman.gadago;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.UIManager;

/**
 * Created by Lyle on 3/30/2017.
 */
public class SharedPrefsForm {

    Friend friend;
    Form sharedPrefsForm;
    Form backToPage;

    SharedPrefsForm(Friend friend, Form backToPage, Container prefsButtons) {

        String[] name = friend.getName().split(" ");
        sharedPrefsForm = new Form(String.format("You and %s like", name[0]), new BorderLayout((BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE)));
        setBackCommand(sharedPrefsForm, backToPage);

        Container sharedPrefsContainer = new Container(BoxLayout.y());
        sharedPrefsContainer.setScrollableY(true);

        int counter = 0;
        for (Object o : prefsButtons) {
            if (o instanceof BooleanButton) {
                if (((BooleanButton) o).isPreference() && friend.getMatchingPreferences().contains(counter)) {
                    System.out.println("You and friend both like: " + ((BooleanButton) o).getText());

                    System.out.println();
                }
            }
            counter++;
        }

        sharedPrefsForm.addComponent(BorderLayout.NORTH,
                LayeredLayout.encloseIn(
                        BoxLayout.encloseY(
                                sharedPrefsContainer
                        )
                )
        );
    }

    public void showForm() {
        sharedPrefsForm.show();
    }

    protected void setBackCommand(Form from, Form to) {
        Command back = new Command("") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                to.showBack();
            }

        };
        Image img = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, UIManager.getInstance().getComponentStyle("TitleCommand"));
        back.setIcon(img);
        from.getToolbar().addCommandToLeftBar(back);
        from.getToolbar().setTitleCentered(true);
        from.setBackCommand(back);
    }

}
