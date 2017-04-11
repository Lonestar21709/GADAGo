package com.lyledenman.gadago;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.UIManager;

/**
 * Created by Lyle on 4/11/2017.
 */
public class InviteForm  {

    Friend friend;
    Form thisForm;
    Form backToPage;
    String restaurant;

    public InviteForm(Friend friend, Form backToPage, String restaurant) {
        super();
        this.friend = friend;
        this.backToPage = backToPage;
        this.restaurant = restaurant;

        thisForm = new Form(("Invite Sent"), new BorderLayout((BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE)));
        setBackCommand(thisForm, backToPage);

        Container inviteSentContainer = new Container(BoxLayout.y());
        inviteSentContainer.setScrollableY(false);

        TextArea noInterests = new TextArea(String.format("You have invited %s to %s.", friend.getName(), restaurant));
        noInterests.getStyle().setFgColor(0xff);
        noInterests.getStyle().setBgColor(0x0fff);
        noInterests.addActionListener((ActionEvent e) -> {
            backToPage.show();
        });

        inviteSentContainer.add(noInterests);

        thisForm.addComponent(BorderLayout.NORTH,
                LayeredLayout.encloseIn(
                        BoxLayout.encloseY(
                                inviteSentContainer
                        )
                )
        );
    }

    private void setBackCommand(Form from, Form to) {
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

    public void showForm() {
        thisForm.show();
    }
}
