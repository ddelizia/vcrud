package org.ddelizia.vcrud.gui7.frames;

import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import org.ddelizia.vcrud.core.service.ModelService;
import org.ddelizia.vcrud.gui7.config.SpringContextHelper;
import org.ddelizia.vcrud.model.VcrudModel;
import org.ddelizia.vcrud.model.annotation.VcrudItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 14/05/13
 * Time: 10:45
 * To change this template use File | Settings | File Templates.
 */
public class Menu extends Panel {

    private Tree menuTree;
    private HierarchicalContainer hierarchicalContainer;
    private Map<String,List<TreeObject>> treeObjects;
    private ModelService modelService;

    public Menu() {
        super("Menu");

        modelService = SpringContextHelper.getBean(ModelService.class);
    }

    private void init(){
        menuTree=new Tree();
        hierarchicalContainer = new HierarchicalContainer();
        treeObjects = new HashMap<String, List<TreeObject>>();

        //addTreeObject(ROOT_GROUP, null);

        List<Class<? extends VcrudModel>> allVcrudItems = modelService.getAllVcrudItems();

        for (Class<? extends VcrudModel> clazz: allVcrudItems){
            VcrudItem vcrudItem = clazz.getAnnotation(VcrudItem.class);
            TreeObject element = new TreeObject(vcrudItem.group(), vcrudItem.parent(), vcrudItem.label(), clazz);
            addTreeObject(vcrudItem.parent(), element);
        }

        ItemClickEvent.ItemClickListener treeclick = new ItemClickEvent.ItemClickListener(){

            @Override
            public void itemClick(final ItemClickEvent event) {

            }
        };

        menuTree.addItemClickListener(treeclick);
    }

    private void addTreeObject(String group, TreeObject treeObject){
        List<TreeObject> list = treeObjects.get(group);
        if (list==null){
            list = new ArrayList<TreeObject>();
        }
        if(treeObject!=null){
            list.add(treeObject);
        }

    }

    private void buildHierarchicalContainer(){
        for (String element:treeObjects.keySet()){
            hierarchicalContainer.addItem(element);

            hierarchicalContainer.addItem(VcrudItem.ROOT);
            for (TreeObject treeObject:treeObjects.get(element)){
                Item item =  hierarchicalContainer.getItem(treeObject.getGroup());
                if (item==null){
                    hierarchicalContainer.addItem(treeObject.getGroup());

                }
                hierarchicalContainer.addItem(treeObject);
                hierarchicalContainer.setParent(treeObject,treeObject.getGroup());
            }
        }

    }

    private class TreeObject{
        private String group;
        private String parent;
        private String label;
        private Class clazz;

        private TreeObject(String group, String parent, String label, Class clazz) {
            this.group = group;
            this.parent = parent;
            this.label = label;
            this.clazz = clazz;
        }

        private String getGroup() {
            return group;
        }

        private void setGroup(String group) {
            this.group = group;
        }

        private String getParent() {
            return parent;
        }

        private void setParent(String parent) {
            this.parent = parent;
        }

        private String getLabel() {
            return label;
        }

        private void setLabel(String label) {
            this.label = label;
        }

        private Class getClazz() {
            return clazz;
        }

        private void setClazz(Class clazz) {
            this.clazz = clazz;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TreeObject that = (TreeObject) o;

            if (!clazz.equals(that.clazz)) return false;
            if (!group.equals(that.group)) return false;
            if (!label.equals(that.label)) return false;
            if (!parent.equals(that.parent)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = group.hashCode();
            result = 31 * result + parent.hashCode();
            result = 31 * result + label.hashCode();
            result = 31 * result + clazz.hashCode();
            return result;
        }
    }

}
