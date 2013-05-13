package org.ddelizia.vcrud.gui7.window;

import com.vaadin.ui.*;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 10/05/13
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
public class LoginWindow extends Window
{
    private Button btnLogin = new Button("Login");
    private TextField login = new TextField ( "Username");
    private PasswordField password = new PasswordField ( "Password");
    private VerticalLayout layout = new VerticalLayout();


    public LoginWindow ()
    {
        super("Authentication Required !");
        setEnabled(true);
        setContent(layout);
        //UI.getCurrent().get;
        initUI();
    }

    private void initUI ()
    {
        layout.addComponent ( new Label("Please login in order to use the application") );
        layout.addComponent ( new Label () );
        layout.addComponent ( login );
        layout.addComponent ( password );
        layout.addComponent ( btnLogin );
    }
}
