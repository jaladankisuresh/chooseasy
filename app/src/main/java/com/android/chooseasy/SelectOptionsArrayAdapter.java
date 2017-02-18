package com.android.chooseasy;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.chooseasy.R;

import java.util.List;

public class SelectOptionsArrayAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Selection.Selectable> collection;
    private Selection.SelectableListener listener;
    private int gridColumnsCount;

    public SelectOptionsArrayAdapter(Selection.SelectableListener listener, List collection) {
        this(listener, collection, 1);
    }

    public SelectOptionsArrayAdapter(Selection.SelectableListener listener, List collection, int columnsCount) {
        this.collection = collection;

        this.listener = listener;
        gridColumnsCount = columnsCount;
    }


    private void loadImageToView(ImageView view, Selection.Selectable selectItem){
        view.setImageResource(R.drawable.profile_large);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if( viewType == Selection.SelectDisplayType.SINGLE.getValue()) {
            View convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listitem_itemselector, parent, false);
            return new SingleViewHolder(convertView, viewType);
        }
        else { //if ( viewType == SelectDisplayType.GROUP.getValue()) {
            View convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listitem_itemselector_group, parent, false);
            return new GroupViewHolder(convertView, viewType);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        int viewType = getItemViewType(position);
        if( viewType == Selection.SelectDisplayType.SINGLE.getValue()) {

            final Selection.Selectable selectItem = collection.get(position);
            final SingleViewHolder singleViewHolder = (SingleViewHolder) viewHolder;

            singleViewHolder.select_profile_title.setText(selectItem.getName());
            loadImageToView(singleViewHolder.img_profile_mini, selectItem);

            singleViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onSelectableClicked(selectItem);
                }
            });

        }
        else { //if ( viewType == SelectDisplayType.GROUP.getValue()) {
            Selection.SelectableGroup selectGroup = (Selection.SelectableGroup) collection.get(position);
            GroupViewHolder groupViewHolder = (GroupViewHolder) viewHolder;

            groupViewHolder.select_group_title.setText(selectGroup.getTitle());
            RecyclerView recyclerView = groupViewHolder.lyt_select_group_collection;

            recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), gridColumnsCount));
            recyclerView.setAdapter(new SelectableArrayAdapter(selectGroup.getCollection()));
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
            Log.e("Arun", "this type is not expected for position:"+position);
            return 0;
        }

    }

    @Override
    public int getItemCount() {
        return collection != null ? collection.size() : 0;
    }


    public static class SingleViewHolder extends RecyclerView.ViewHolder {
        public final View rootView;
        public final CircleImageView img_profile_mini;
        public final TextView select_profile_title;

        public SingleViewHolder(View convertView, int viewType) {
            super(convertView);
            rootView = convertView;
            img_profile_mini = (CircleImageView) convertView.findViewById(R.id.img_profile_mini);
            select_profile_title = (TextView) convertView.findViewById(R.id.select_title);
        }
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder {
        public final RecyclerView lyt_select_group_collection;
        public final TextView select_group_title;

        public GroupViewHolder(View convertView, int viewType) {
            super(convertView);
            lyt_select_group_collection = (RecyclerView) convertView.findViewById(R.id.lyt_select_group_collection);
            select_group_title = (TextView) convertView.findViewById(R.id.select_group_title);
        }

    }

    public class SelectableArrayAdapter
            extends RecyclerView.Adapter<SelectOptionsArrayAdapter.SingleViewHolder> {

        private final List<Selection.Selectable> childCollection;
        //private ImageServiceClient proxyImageService;

        public SelectableArrayAdapter(List<Selection.Selectable> collection) {
            this.childCollection = collection;
            //proxyImageService = new ImageServiceClient();
        }


        @Override
        public SingleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listitem_itemselector, parent, false);
            return new SingleViewHolder(convertView, viewType);
        }

        @Override
        public void onBindViewHolder(final SingleViewHolder singleViewHolder, final int position) {
            final Selection.Selectable selectItem = childCollection.get(position);
            singleViewHolder.select_profile_title.setText(selectItem.getName());
            loadImageToView(singleViewHolder.img_profile_mini, selectItem);

            singleViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onSelectableClicked(selectItem);
                    loadImageToView(singleViewHolder.img_profile_mini, selectItem);
                }
            });
        }

        @Override
        public int getItemCount() {
            return childCollection.size();
        }
    }
}
