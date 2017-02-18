package com.android.chooseasy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Selection {

    public static class SelectedResult {

        int displayViewId;
        Selectable selectedItem;

        public SelectedResult(Selectable selectedItem, int displayViewId) {
            this.selectedItem = selectedItem;
            this.displayViewId = displayViewId;
        }

        public Selectable getSelectedItem() {
            return selectedItem;
        }

        public int getDisplayViewId() {
            return displayViewId;
        }
    }

    public enum SelectDisplayType {
        SINGLE (1),
        GROUP (2);

        private final int value;
        public int getValue() {
            return value;
        }
        SelectDisplayType(int value) {
            this.value = value;
        }
    }

    public interface Selectable{
        public void setSelected(boolean selected);
        public boolean isSelected();
        public int getId();
        public String getName();
        public String getImage();
    }

    public static class SelectableItem
            implements Selection.Selectable, Serializable {
        private String name;
        private int id;
        private boolean isSelected = false;

        public SelectableItem(String name, int id){
            this.name = name;
            this.id = id;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
        public boolean isSelected() {
            return isSelected;
        }
        public String getName() { return name; }
        public int getId() { return id; }
        public String getImage() { return null; }
    }

    public static class SelectableGroup {
        private String title;
        private ArrayList<Selectable> collection;

        public SelectableGroup(String title) {
            this.title = title;
            collection = new ArrayList<Selectable>();
        }
        public boolean addSelectable(Selectable item) {
            return collection.add(item);
        }
        public boolean addSelectableList(List list) {
            return collection.addAll(list);
        }
        public String getTitle() { return title; }
        public List<Selectable> getCollection() { return collection; }
    }

    public interface SelectableListener {
        void onSelectableClicked(Selectable selectItem);
    }
}


