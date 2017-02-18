package com.android.chooseasy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import com.android.chooseasy.R;

public class AddParticipantsActivity extends AppCompatActivity
        implements Selection.SelectableListener {

    private RecyclerView list_app_profiles;
    private ArrayList mFavouritiesCollection;
    private ProfileAutoCompleteView auto_select_profiles;
    private MultiSelectOptionsArrayAdapter selectOptionsAdapter;

    public AddParticipantsActivity() {
        // Required empty public constructor
        mFavouritiesCollection = new ArrayList();
        Selection.SelectableGroup flyGroup = new Selection.SelectableGroup("WE FLY");
        List flyCollection = new ArrayList();
        flyCollection.add(new Selection.SelectableItem("Pigeon", 11));
        flyCollection.add(new Selection.SelectableItem("Parrot", 12));
        flyCollection.add(new Selection.SelectableItem("Lady Bird", 13));
        flyCollection.add(new Selection.SelectableItem("Eagle", 14));
        flyGroup.addSelectableList(flyCollection);
        mFavouritiesCollection.add(flyGroup);
        Selection.SelectableGroup swimGroup = new Selection.SelectableGroup("WE SWIM");
        List swimCollection = new ArrayList();
        swimCollection.add(new Selection.SelectableItem("Gold Fish", 21));
        swimCollection.add(new Selection.SelectableItem("Dolphin", 22));
        swimCollection.add(new Selection.SelectableItem("Jelly Fish", 23));
        swimCollection.add(new Selection.SelectableItem("Tortoise", 24));
        swimGroup.addSelectableList(swimCollection);
        mFavouritiesCollection.add(swimGroup);
        Selection.SelectableGroup runGroup = new Selection.SelectableGroup("WE RUN");
        List runCollection = new ArrayList();
        runCollection.add(new Selection.SelectableItem("Rabbit", 31));
        runCollection.add(new Selection.SelectableItem("Cat", 32));
        runCollection.add(new Selection.SelectableItem("Dog", 33));
        runCollection.add(new Selection.SelectableItem("Horse", 34));
        runGroup.addSelectableList(runCollection);
        mFavouritiesCollection.add(runGroup);

        selectOptionsAdapter = new MultiSelectOptionsArrayAdapter(this, mFavouritiesCollection);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_participants);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        auto_select_profiles = (ProfileAutoCompleteView) findViewById(R.id.auto_select_profiles);
        auto_select_profiles.setFocusable(false);

        list_app_profiles = (RecyclerView) findViewById(R.id.list_app_profiles);
        list_app_profiles.setLayoutManager(new GridLayoutManager(list_app_profiles.getContext(), 1));
        list_app_profiles.setAdapter(selectOptionsAdapter);
    }

    private void updateItemToSelectedTokens(Selection.Selectable token){
        auto_select_profiles.clearCompletionText();

        if(token.isSelected()) {
            auto_select_profiles.addObject(token);
        }
        else {
            auto_select_profiles.removeObject(token);
        }
    }

    @Override
    public void onSelectableClicked(Selection.Selectable selectItem) {
        updateItemToSelectedTokens(selectItem);
    }

}


