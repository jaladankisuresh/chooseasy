package com.android.chooseasy;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.chooseasy.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MultiSelectOptionsArrayAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private HashMap tokenObjects;
    private List collection;
    private Selection.SelectableListener listener;
    private int gridColumnsCount;

    private final String LOG_APP_TAG = "chooseasy";

    public MultiSelectOptionsArrayAdapter(Selection.SelectableListener listener, List collection) {
        this(listener, collection, 1);
    }

    public MultiSelectOptionsArrayAdapter(Selection.SelectableListener listener, List collection, int columnsCount) {
        init(collection);

        this.listener = listener;
        gridColumnsCount = columnsCount;
    }

    private void init(List<Selection.Selectable> defaultCollection) {
        collection = new ArrayList<>();
        collection.addAll(defaultCollection);

        tokenObjects = new HashMap();
    }

    public void updateItemToSelectedTokens(Selection.Selectable token){

        if(token.isSelected()) {
            tokenObjects.put(token.getId(), token);
        }
        else {
            tokenObjects.remove(token.getId());
        }
    }

    private void loadImageToView(ImageView view, Selection.Selectable selectItem){

        if(selectItem.isSelected()) {
            view.setImageResource(R.drawable.check);
        }
        else {
            view.setImageResource(R.drawable.profile_large);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if( viewType == Selection.SelectDisplayType.SINGLE.getValue()) {
            View convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listitem_itemselector, parent, false);
            return new SelectOptionsArrayAdapter.SingleViewHolder(convertView, viewType);
        }
        else { //if ( viewType == SelectDisplayType.GROUP.getValue()) {
            View convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listitem_itemselector_group, parent, false);
            return new SelectOptionsArrayAdapter.GroupViewHolder(convertView, viewType);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        int viewType = getItemViewType(position);
        if( viewType == Selection.SelectDisplayType.SINGLE.getValue()) {

            final Selection.Selectable selectItem = (Selection.Selectable) collection.get(position);
            final SelectOptionsArrayAdapter.SingleViewHolder singleViewHolder = (SelectOptionsArrayAdapter.SingleViewHolder) viewHolder;

            boolean isSelected = tokenObjects.containsKey(selectItem.getId());
            singleViewHolder.select_profile_title.setText(selectItem.getName());
            selectItem.setSelected(isSelected);
            loadImageToView(singleViewHolder.img_profile_mini, selectItem);

            singleViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    selectItem.setSelected(!selectItem.isSelected()); //Onclick, toggle the selected flag
                    loadImageToView(singleViewHolder.img_profile_mini, selectItem);
                    updateItemToSelectedTokens(selectItem);
                    listener.onSelectableClicked(selectItem);
                }
            });

        }
        else { //if ( viewType == SelectDisplayType.GROUP.getValue()) {
            Selection.SelectableGroup selectGroup = (Selection.SelectableGroup) collection.get(position);
            SelectOptionsArrayAdapter.GroupViewHolder groupViewHolder = (SelectOptionsArrayAdapter.GroupViewHolder) viewHolder;

            groupViewHolder.select_group_title.setText(selectGroup.getTitle());
            RecyclerView recyclerView = groupViewHolder.lyt_select_group_collection;

            recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), gridColumnsCount));
            recyclerView.setAdapter(new MultiSelectableArrayAdapter(selectGroup.getCollection()));
        }

    }

    @Override
    public int getItemViewType(int position) {

        if(collection.get(position) instanceof Selection.Selectable) {
            return Selection.SelectDisplayType.SINGLE.getValue();
        }
        else if(collection.get(position) instanceof Selection.SelectableGroup) {
            return Selection.SelectDisplayType.GROUP.getValue();
        } else {
            Log.e(LOG_APP_TAG, "this type is not expected for position:"+position);
            return 0;
        }

    }

    @Override
    public int getItemCount() {
        return collection != null ? collection.size() : 0;
    }


    public class MultiSelectableArrayAdapter
            extends RecyclerView.Adapter<SelectOptionsArrayAdapter.SingleViewHolder> {

        private final List<Selection.Selectable> childCollection;
        //private ImageServiceClient proxyImageService;

        public MultiSelectableArrayAdapter(List<Selection.Selectable> collection) {
            this.childCollection = collection;
            //proxyImageService = new ImageServiceClient();
        }


        @Override
        public SelectOptionsArrayAdapter.SingleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listitem_itemselector, parent, false);
            return new SelectOptionsArrayAdapter.SingleViewHolder(convertView, viewType);
        }

        @Override
        public void onBindViewHolder(final SelectOptionsArrayAdapter.SingleViewHolder singleViewHolder, final int position) {
            final Selection.Selectable selectItem = childCollection.get(position);
            boolean isSelected = tokenObjects.containsKey(selectItem.getId());
            singleViewHolder.select_profile_title.setText(selectItem.getName());
            selectItem.setSelected(isSelected);
            loadImageToView(singleViewHolder.img_profile_mini, selectItem);

            singleViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    selectItem.setSelected(!selectItem.isSelected()); //Onclick, toggle the selected flag
                    loadImageToView(singleViewHolder.img_profile_mini, selectItem);
                    updateItemToSelectedTokens(selectItem);
                    listener.onSelectableClicked(selectItem);
                }
            });
        }

        @Override
        public int getItemCount() {
            return childCollection.size();
        }
    }

}
