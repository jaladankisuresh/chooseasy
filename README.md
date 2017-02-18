# chooseasy
An Android micro-library with grouped pickers that makes choosing easy
Let your android application users find selectable items conveniently. Create and bind hierarchical grouped lists to your ArrayAdapters for **Pickers** , **MultiAutoCompleteTextViews** and **Search Filter Views**

![Android grouped Pickers](/choose_easy.png "Android grouped Pickers")

# Usage
As illustrated above, You could display items in groups with Group Headers as the hierachical parent for the selectable content.In addition to displaying array of (*grouped*) items vertically under each group, one could choose to show the same content using **N** columns layout by passing **n** as optional parameter to MultiSelectOptionsArrayAdapter constructor.
```java
//MultiSelectOptionsArrayAdapter(Selection.SelectableListener listener, List collection, int columnsCount)
new MultiSelectOptionsArrayAdapter(listener, collection, columnsCount)
```
### Hello Me Example:
Simple example code that is used in this demo
```java
mFavouritiesCollection = new ArrayList();
Selection.SelectableGroup flyGroup = new Selection.SelectableGroup("WE FLY");
List flyCollection = new ArrayList();
flyCollection.add(new Selection.SelectableItem("Pigeon", 11));
flyCollection.add(new Selection.SelectableItem("Parrot", 12));
flyGroup.addSelectableList(flyCollection);
mFavouritiesCollection.add(flyGroup);
Selection.SelectableGroup swimGroup = new Selection.SelectableGroup("WE SWIM");
List swimCollection = new ArrayList();
swimCollection.add(new Selection.SelectableItem("Gold Fish", 21));
swimCollection.add(new Selection.SelectableItem("Dolphin", 22));
swimGroup.addSelectableList(swimCollection);
mFavouritiesCollection.add(swimGroup);

selectOptionsAdapter = new MultiSelectOptionsArrayAdapter(this, mFavouritiesCollection);
```
### Getting Dynamic
If you are planning to use this library you are mostly likely going to use it with dynamic data array that is coming from a data store or a external WebAPI. _We have built this for this exact purpose_

#### 1. Read serialized data from external source (DB or WebAPI)
You could be using HTTP libraries like [Volley](https://android.googlesource.com/platform/frameworks/volley/) or [Retrofit](https://github.com/square/retrofit), 
```json
// JSON serialization structure for the deserialized object respresentation in the Hello Me Example 
[{
  "title": "WE FLY",
  "collection": [{"name":"Pigeon", "id":11}, {"name":"Parrot", "id":12}]
},
{
  "title": "WE SWIM",
  "collection": [{"name":"Gold Fish", "id":21}, {"name":"Dolphin", "id":22}]
}]
```
#### 2. Build POJO objects
You could be using JSON library like [Gson](https://github.com/google/gson) or [Jackson](https://github.com/FasterXML/jackson)
**BUT**, When you need to extend and use custom objects you could implement **Selectable** Interface
All the object instances that are to be selectable (*a.k.a clickable*) should implement Selection.Selectable Interface

```java
public class Profile
        implements Serializable, Selection.Selectable {

    @SerializedName("profile_type")
    Selection.SelectedResult.IdType profileType;
    @SerializedName("profile_ref_id")
    int profileRefId;
    @SerializedName("profile_title")
    String title;
    @SerializedName("img_path")
    String profileIcon;

    @Override
    public int getId() {
        return profileRefId;
    }    
    @Override
    public String getName() {
        return title;
    }
    @Override
    public String getImage() {
        return profileIcon;
    }    
    @Override
    public void setSelected(boolean selected) {
        
    }
    @Override
    public boolean isSelected() {
        return false;
    }

}
```

#### 3. Initialize the MultiSelectOptionsArrayAdapter Adapter
Construct/Build **_collection_** array from array of **Selectable** objects (refer Hello Me Example above)

#### 4. Listen for click events in your activity class
Implement Selection.SelectableListener and pass in **_listner_** and **_collection_** array to MultiSelectOptionsArrayAdapter adapter

```java
//MultiSelectOptionsArrayAdapter(Selection.SelectableListener listener, List collection, int columnsCount)
new MultiSelectOptionsArrayAdapter(listener, collection, columnsCount)
```

# Contribute
-If you have suggestion(s) on how the API for this library could be improved, Plz create a [issue](/issues)  
-If you could like contribute to this library, you are welcome to do a pull request  

# License
Open sourced with [MIT](./License.md) license
