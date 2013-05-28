package org.ddelizia.vcrud.gui7.component;

import com.vaadin.event.LayoutEvents;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Runo;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 21/05/13
 * Time: 9:35
 * To change this template use File | Settings | File Templates.
 */
public class CollapsableLayout extends VerticalLayout{

    public static final int HEADER_LEFT_COMPONENT_1=0;
    public static final int HEADER_RIGHT_COMPONENT_1=20;

    public static final int HEADER_SPACE=30;

    private AbsoluteLayout header;
    private Layout layout;

    private Label titleLabel;
    private Image collapseIcon;


    public CollapsableLayout(Layout layout) {

        super();
        titleLabel=new Label();
        init(layout);
    }

    public CollapsableLayout(Layout layout, String title) {
        super();
        titleLabel=new Label(title);
        init(layout);
    }

    private void init(Layout layout){

        setWidth(100, Unit.PERCENTAGE);
        setVisible(true);
        setSizeUndefined();

        this.layout=layout;

        titleLabel.setContentMode(ContentMode.TEXT);
        //titleLabel.setIcon(new ThemeResource("../runo/icons/16/arrow-right.png"));
        titleLabel.setStyleName(Runo.LABEL_H2);

        collapseIcon = new Image();
        collapseIcon.setIcon(new ThemeResource("../runo/icons/16/arrow-right.png"));
        collapseIcon.addClickListener(new CollapseEvent());

        header = new AbsoluteLayout();
        header.setWidth(100,Unit.PERCENTAGE);
        //header.setHeight(40, Unit.PIXELS);
        header.setVisible(true);
        header.setSizeUndefined();
        header.addComponent(titleLabel,"left: "+HEADER_LEFT_COMPONENT_1+";");
        header.addComponent(collapseIcon,"right: "+HEADER_RIGHT_COMPONENT_1+";");
        //header.addComponent(titleLabel);


        addComponent(header);
        addComponent(this.layout);
    }


    public void setTitle(String title){
        titleLabel.setValue(title);
    }

    class CollapseEvent implements MouseEvents.ClickListener, Listener{

        @Override
        public void click(MouseEvents.ClickEvent clickEvent) {
            init();
        }

        //@Override
        public void layoutClick(LayoutEvents.LayoutClickEvent layoutClickEvent) {
            init();
        }

        private void init(){
            if (layout.isVisible()){
                layout.setVisible(false);
            }else{
                layout.setVisible(true);
            }
        }

        @Override
        public void componentEvent(Event event) {

        }
    }


    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public void setTitleLabel(Label titleLabel) {
        this.titleLabel = titleLabel;
    }

    public Image getCollapseIcon() {
        return collapseIcon;
    }

    public void setCollapseIcon(Image collapseIcon) {
        this.collapseIcon = collapseIcon;
    }


}
