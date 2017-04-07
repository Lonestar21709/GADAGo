package com.lyledenman.gadago;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.UIManager;

import java.util.List;
import java.util.Map;

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

        YelpRequest req = new YelpRequest(foodPreference);
        Map<String,Object> yelpResult = req.getYelpResult();

        // TODO: Parse the Map of yelpResults to get the items I need: 1. Name 2. Distance (miles) 3. Stars

        List<Map<String, Object>> test = (java.util.List<Map<String, Object>>)yelpResult.get("root");
        System.out.println("test: " + test);

        if (yelpResult == null || yelpResult.size() < 1) {
            TextArea yelpMsg = new TextArea(String.format("No %s restaurants were found in your area. Please try another food type."));
            yelpMsg.getStyle().setFgColor(0xff);
            yelpMsg.getStyle().setBgColor(0x0fff);
            yelpResultsContainer.add(yelpMsg);
        } else {

//            for(Map<String, Object> obj : yelpResult) {
//                String url = (String) obj.get("url");
//                String name = (String) obj.get("name");
//                java.util.List<String> titles = (java.util.List<String>) obj.get("titles");
//            }

            for (Map.Entry<String, Object> entry : yelpResult.entrySet())
            {
                System.out.println(entry.getKey() + "/" + entry.getValue());
            }
        }

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
