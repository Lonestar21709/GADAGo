package com.lyledenman.gadago;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.UIManager;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InitiateInviteForm {
    private final double METERS_IN_MILE = 1609.34;

    Friend friend;
    Form initInviteForm;
    Form backToPage;
    String foodPref;
    Form profileForm;

    InitiateInviteForm(Friend friend, Form backToPage, String foodPreference, Form profileForm) {
        this.friend = friend;
        this.backToPage = backToPage;
        this.foodPref = foodPreference;
        this.profileForm = profileForm;

        String[] name = friend.getName().split(" ");
        this.initInviteForm = new Form(("Select a restaurant:"), new BorderLayout((BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE)));
        setBackCommand(this.initInviteForm, backToPage);

        Container yelpResultsContainer = new Container(BoxLayout.y());
        yelpResultsContainer.setScrollableY(true);

        YelpRequest req = new YelpRequest(foodPreference);
        Map<String, Object> data = req.getYelpResult();
        ArrayList<String> businesses = (ArrayList<String>)data.get("businesses");
        int busSize = businesses.size();

        if (data.size() < 1) {
            TextArea yelpMsg = new TextArea(String.format("No %s restaurants were found in your area. Please try another food type."));
            yelpMsg.getStyle().setFgColor(0xff);
            yelpMsg.getStyle().setBgColor(0x0fff);
            yelpResultsContainer.add(yelpMsg);
        } else {

            String[] names = new String[busSize];
            String[] ratings = new String[busSize];
            String[] distances = new String[busSize];

            int count = 0;
            for (Object entry : businesses) {
                String curr = entry.toString();

                System.out.println(parseName(curr));
                System.out.println(parseRating(curr));
                System.out.println(parseDistance(curr));
                System.out.println(String.format("%.2f", getMiles(parseDistance(curr))));

                names[count] = parseName(curr);
                ratings[count] = parseRating(curr);
                distances[count] = String.format("%.2f", getMiles(parseDistance(curr)));

                count++;
            }

            // TODO: INFINITE SCROLL MANAGER
            Form hi = new Form(String.format("Choose a Restaurant", name[0]), new BorderLayout((BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE)));
            setBackCommand(hi, backToPage);
            Container hiContainer = new Container(BoxLayout.y());
            hiContainer.setScrollableY(true);

            ArrayList<Button> busButtons = new ArrayList<>();
            for (int i = 0; i < busSize; i++) {
                System.out.println(i);
                String busName = names[i];
                String busRating = ratings[i];
                String busDist = distances[i];
                final Button newButton = new Button(String.format("%s %-6s %smi", busName, busRating, busDist));
                newButton.addActionListener((e) -> {
                    System.out.println("Clicked on button: " + newButton.getText());
                    InviteForm inviteForm = new InviteForm(friend, profileForm, busName);
                    inviteForm.showForm();
                });
                busButtons.add(newButton);
            }

            for (Button b : busButtons) {
                hiContainer.add(b);
            }

            hi.addComponent(BorderLayout.NORTH,
                    LayeredLayout.encloseIn(
                            BoxLayout.encloseY(
                                    hiContainer
                            )
                    )
            );
            hi.show();
            //\


        }

        initInviteForm.addComponent(BorderLayout.NORTH,
                LayeredLayout.encloseIn(
                        BoxLayout.encloseY(
                                yelpResultsContainer
                        )
                )
        );
    }

    private double getMiles(String s) {
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        double meters = Double.parseDouble(s);
        return (meters / METERS_IN_MILE);
    }

    private String parseName(String s) {
        Pattern p = Pattern.compile("name=([^,]*)");
        Matcher m = p.matcher(s);
        return m.find() ? m.group(1) : null;
    }

    private String parseRating(String s) {
        Pattern p = Pattern.compile("rating=([^,]*)");
        Matcher m = p.matcher(s);
        return m.find() ? m.group(1) : null;
    }

    private String parseDistance(String s) {
        Pattern p = Pattern.compile("distance=([^,}]*)");
        Matcher m = p.matcher(s);
        return m.find() ? m.group(1) : null;
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
