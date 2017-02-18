package com.lyledenman.gadago;

import com.codename1.ui.*;
import com.codename1.location.Location;
import com.codename1.location.LocationManager;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.FileNotFoundException;
import com.codename1.ui.Dialog;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.UITimer;


/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose 
 * of building native mobile applications using Java.
 */
public class GADAGo {

    private Form current;
    private Resources theme;

    private boolean userLoggedIn;
    private boolean isUserLoggedIn() {
        // STUB
        return true;
    }

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);

        // Check if user is already logged in
        userLoggedIn = isUserLoggedIn();
    }
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }

        if (userLoggedIn) {
            Form profileForm = new Form("GADA Go",new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));

            FontImage messageIcon = FontImage.createMaterial(FontImage.MATERIAL_CHAT, UIManager.getInstance().getComponentStyle("TitleCommand"));
            FontImage settingsIcon = FontImage.createMaterial(FontImage.MATERIAL_SETTINGS_APPLICATIONS, UIManager.getInstance().getComponentStyle("TitleCommand"));


            profileForm.getToolbar().addCommandToSideMenu("", messageIcon, (e) -> System.out.println("Clicked"));
            profileForm.getToolbar().addCommandToOverflowMenu("", settingsIcon, (e) -> System.out.println("Clicked"));

            // Create profile buttons
            Button viewFriends = new Button("Friends");
            FontImage.setMaterialIcon(viewFriends, FontImage.MATERIAL_PERSON);

            Button viewPrefs = new Button("Preferences");
            FontImage.setMaterialIcon(viewPrefs, FontImage.MATERIAL_CHECK_BOX);

            Button viewInvites = new Button("Invitations");
            FontImage.setMaterialIcon(viewInvites, FontImage.MATERIAL_INSERT_INVITATION);

            // Set userImage
            Label userImg = new Label(theme.getImage("user_200.png"));
            profileForm.addComponent(BorderLayout.CENTER,
                    LayeredLayout.encloseIn(
                            BoxLayout.encloseY(
                                    userImg,
                                    viewFriends,
                                    viewPrefs,
                                    viewInvites
                            )
                    )
            );

            // FRIENDS FORM
            Form friendsForm = new Form("Friends", new BorderLayout((BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE)));
            setBackCommand(friendsForm, profileForm);
            final TextField searchField = new TextField();
            searchField.setHint("Find a new friend");

            Button friend1 = new Button ("Cesar          10.1 mi");
            Button friend2 = new Button("Gina            3.9 mi");
            Button friend3 = new Button("Michael         l4.5 mi");

            Component.setSameWidth(searchField, friend1);
            friendsForm.addComponent(BorderLayout.NORTH,
                    LayeredLayout.encloseIn(
                            BoxLayout.encloseY(
                                searchField,
                                friend1,
                                friend2,
                                friend3
                            )
                    )
            );

            // PREFERENCES FORM
            Form preferencesForm = new Form("Preferences", new BorderLayout((BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE)));
            setBackCommand(preferencesForm, profileForm);

            Container buttonContainer = new Container(BoxLayout.y());
            buttonContainer.setScrollableY(true);
            System.out.println(buttonContainer.isScrollableY());

            GetButtonsFromText gb = new GetButtonsFromText();
            try
            {
                gb.getButtonsFromText();
            } catch (FileNotFoundException e) {
                System.out.println("Failed.");
            }

            java.util.List<String> list = gb.getItemList();
            java.util.Iterator<String> listIterator = list.iterator();
            while (listIterator.hasNext()) {
                Button newButton = new Button(listIterator.next());
                newButton.addActionListener((e) -> {
                    System.out.println(newButton.getText());
                });
                // Current setUIID options: {Button_Gray, Button_Green}
                buttonContainer.add(newButton);
            }

            // Add buttons to PreferencesForm
            preferencesForm.addComponent(BorderLayout.NORTH,
                    LayeredLayout.encloseIn(
                            BoxLayout.encloseY(
                                buttonContainer
                            )
                    )
            );

            // INVITATIONS FORM
            Form invitationsForm = new Form("Invitations", new BorderLayout((BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE)));
            setBackCommand(invitationsForm, profileForm);

            viewFriends.addActionListener((ActionEvent e) -> {
                //Display.getInstance().execute("https://www.codenameone.com/developers.html");
                friendsForm.show();
            });

            viewPrefs.addActionListener((ActionEvent e) -> {
                //Display.getInstance().execute("https://www.codenameone.com/developers.html");
                preferencesForm.show();
            });

            viewInvites.addActionListener((ActionEvent e) -> {
                //Display.getInstance().execute("https://www.codenameone.com/developers.html");
                invitationsForm.show();
            });

            profileForm.show();
        } else {
            // Login Form
            // Shown if user is not logged in
            Form login = new Form("Welcome to GADA Go");

            // Create username and password fields and add to the login form
            TextField loginUsername = new TextField();
            loginUsername.setHint("Enter your e-mail or username");
            login.addComponent(loginUsername);
            TextField loginPassword = new TextField();
            loginPassword.setHint("Enter your password");
            login.addComponent(loginPassword);

            // Create log in validation button and add to the login form
            Button loginValidateUser = new Button("Login");
            loginValidateUser.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if (!loginUsername.getText().equals("") && !loginPassword.getText().equals("")) {
                        Dialog.show("Validating user login...", "At this point, the system will validate the username / password combination.", "Ok", "Cancel" );
                        System.out.println("Login clicked");
                        System.out.printf("Username provided: %s%n", loginUsername.getText());
                        System.out.printf("Password provided: %s%n", loginPassword.getText());
                        if (loginUsername.getText().equals("admin") && loginPassword.getText().equals("admin1")) {
                            userLoggedIn = true;
                            Dialog.show("VALID USER FOUND", "At this point, the system has found the username:password pair provided. The app will now go to the main form.", "Ok", "Cancel" );
                        } else {
                            loginUsername.setText("");
                            loginPassword.setText("");
                            Dialog.show("INVALID USER", "At this point, the system has not found the username:password pair provided.", "Ok", "Cancel" );
                        }

                        // If the user is validated, run start() again
                        if (userLoggedIn) {
                            try {
                                start();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                    }
                }
            });
            login.addComponent(loginValidateUser);

            // Create sign up button and add to the login form
            Button signup = new Button("New user? Sign up.");
            signup.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    // Signup Form
                    // Shown if user clicks the SignUp button
                    Form signup = new Form("Sign up for GADA Go");

                    // Provide navigation back to the login form
                    setBackCommand(signup, login);

                    // Create email, password, and username fields and add them to the signup form
                    TextField signupEmail = new TextField();
                    signupEmail.setHint("Enter your e-mail");
                    signup.addComponent(signupEmail);

                    TextField signupPwd = new TextField();
                    signupPwd.setHint("Enter your password");
                    signup.addComponent(signupPwd);

                    TextField signupPwdValidation = new TextField();
                    signupPwdValidation.setHint("Verify your password");
                    signup.addComponent(signupPwdValidation);

                    TextField signupUsername = new TextField();
                    signupUsername.setHint("Enter your desired username");
                    signup.addComponent(signupUsername);

                    // Create new user validation button and add it to the signup form
                    Button signupUserValidation = new Button("Enter information");
                    signupUserValidation.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            Dialog.show("Validating new user...", "At this point, the system will validate the new user information.", "Ok", "Cancel" );
                            System.out.println("Sign up enter info clicked");
                        }
                    });
                    signup.addComponent(signupUserValidation);

                    // Show the signup form
                    signup.show();
                }
            });
            login.addComponent(signup);


            // Show the login form in the app
            login.show();
        }

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

    public void stop() {
        current = Display.getInstance().getCurrent();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = Display.getInstance().getCurrent();
        }
    }
    
    public void destroy() {
    }

}
