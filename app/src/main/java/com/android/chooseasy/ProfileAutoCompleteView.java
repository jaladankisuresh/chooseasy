package com.android.chooseasy;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import com.android.chooseasy.R;
import com.tokenautocomplete.TokenCompleteTextView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class ProfileAutoCompleteView
        extends TokenCompleteTextView<Selection.Selectable> {

    private Context context;
    private Tokenizer tokenizer;

    public ProfileAutoCompleteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public ProfileAutoCompleteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
        //proxyImageService = new ImageServiceClient();
    }


    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        this.context = context;
        tokenizer = new MultiAutoCompleteTextView.CommaTokenizer();
        setTokenizer(tokenizer);

        setThreshold(3);
        allowDuplicates(false);
        allowCollapse(false);
        setTokenClickStyle(TokenClickStyle.Select);
    }

    @Override
    protected View getViewForObject(Selection.Selectable selectItem) {

        LayoutInflater inflator = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout rootView = (LinearLayout) inflator.inflate(R.layout.chip_layout, (ViewGroup) getParent(), false);
        TextView txt_user_name = (TextView) rootView.findViewById(R.id.txt_user_name);
        txt_user_name.setText(selectItem.getName());

        return rootView;
    }

    @Override
    protected Selection.Selectable defaultObject(String completionText) {
        //Stupid simple example of guessing if we have an email or not
        /*
        int index = completionText.indexOf('@');
        if (index == -1) {
            completionText += "@example.com";
        }
        */
        return new Selection.SelectableItem(completionText, completionText.hashCode());
    }

    @Override
    public void addObject(Selection.Selectable object){
        for(Selection.Selectable item: getObjects()){
            if(item.getId() == object.getId()){
                return;
            }
        }
        super.addObject(object);
    }

    @Override
    public void removeObject(Selection.Selectable object){
        for(Selection.Selectable item: getObjects()){
            if(item.getId() == object.getId()){
                super.removeObject(item);
                return;
            }
        }
    }

    public String currentCompletionText() {
        return super.currentCompletionText();
    }

    public void clearCompletionText() {
        Editable editable = getText();
        int end = tokenizer.findTokenEnd(editable, getSelectionEnd());
        int start = tokenizer.findTokenStart(editable, end);
        editable.replace(start, end, "");
    }
}