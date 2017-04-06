package com.lyledenman.gadago;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.UIManager;

public class InitiateInviteForm {

    Friend friend;
    Form initInviteForm;
    Form backToPage;
    String foodPref;

    InitiateInviteForm(Friend friend, Form backToPage, String foodPreference) {
        this.friend = friend;
        this.backToPage = backToPage;
        this.foodPref = foodPreference;

        String[] name = friend.getName().split(" ");
        this.initInviteForm = new Form(("Select a restaurant:"), new BorderLayout((BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE)));
        setBackCommand(this.initInviteForm, backToPage);

        Container yelpResultsContainer = new Container(BoxLayout.y());
        yelpResultsContainer.setScrollableY(true);

        System.out.println("Food to send to Yelp API: " + foodPreference);

        // PLACEHOLDER
        TextArea yelpMsg = new TextArea("This is where Yelp API query results will go.");
        yelpMsg.getStyle().setFgColor(0xff);
        yelpMsg.getStyle().setBgColor(0x0fff);

        yelpResultsContainer.add(yelpMsg);
        //\

        // Working with YelpRequest (using CN1 stuff now)
        try {
            YelpRequest req = new YelpRequest();
        } catch (NullPointerException npe) {
            System.err.println("Shit happens");
        }

//        java.util.List<Map<String, Object>> results = req.fetchPropertyData("house");
//        System.out.println(results);
        //\

        // Working on Yelp API
//        YelpAPI yelp = new YelpAPI(ConstantValues.YELP_CONSUMER_KEY,
//                ConstantValues.YELP_CONSUMER_SECRET,
//                ConstantValues.YELP_TOKEN,
//                ConstantValues.YELP_TOKEN_SECRET);
//        System.out.println(yelp);

        //\

        initInviteForm.addComponent(BorderLayout.NORTH,
                LayeredLayout.encloseIn(
                        BoxLayout.encloseY(
                                yelpResultsContainer
                        )
                )
        );
    }

    public void showForm() {
        initInviteForm.show();
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
