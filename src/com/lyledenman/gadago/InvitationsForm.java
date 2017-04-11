package com.lyledenman.gadago;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.UIManager;

import java.util.Set;

/**
 * Created by Lyle on 4/11/2017.
 */
public class InvitationsForm {

    Friend friend;
    Form thisInvitationsForm;
    Form backToPage;
    Form profileForm;

    InvitationsForm(Set<Button> invitationsButtons, Form backToPage) {
        this.backToPage = backToPage;

        thisInvitationsForm = new Form("Invitations", new BorderLayout((BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE)));
        setBackCommand(thisInvitationsForm, backToPage);

        Container invitesContainer = new Container(BoxLayout.y());
        invitesContainer.setScrollableY(true);

        if (invitationsButtons.size() == 0) {
            TextArea noInvites = new TextArea("You have no current plans, but you can make some.");
            noInvites.getStyle().setFgColor(0xff);
            noInvites.getStyle().setBgColor(0x0fff);
            invitesContainer.add(noInvites);
        } else {
            thisInvitationsForm = new Form("Invitations", new BorderLayout((BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE)));
            invitesContainer.removeAll();
            setBackCommand(thisInvitationsForm, backToPage);
            invitesContainer = new Container(BoxLayout.y());
            for (Button b : invitationsButtons) {
                invitesContainer.add(b);
            }
        }
        thisInvitationsForm.addComponent(BorderLayout.NORTH,
                LayeredLayout.encloseIn(
                        BoxLayout.encloseY(
                                invitesContainer
                        )
                )
        );
    }

    void showForm() {
        thisInvitationsForm.show();
    }

    public void setBackCommand(Form from, Form to) {
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
