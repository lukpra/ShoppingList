package pl.lukpra.shoppinglist;


public class ShopList {
    private String name, info;
    private long listId, dateCreated;
    private Category category;


    public enum Category{ PUMPKIN, TOMATO, CARROT, FISH, BANANA}

    public ShopList(String name, String info, Category category, long listId, long dateCreated){
        this.name = name;
        this.info = info;
        this.category = category;
        this.listId = listId;
        this.dateCreated = dateCreated;
    }

    public String getName(){
        return this.name;
    }

    public String getInfo(){
        return this.info;
    }

    public Category getCategory() {
        return category;
    }

    public long getDate() {
        return dateCreated;
    }

    public long getListId() {
        return listId;
    }

    public String toString() {
        return "ID: " +  listId + " Name: "+ name;
    }

    public int getRightDrawable(){
        return getDrawable(category);
    }

    public static int getDrawable(Category category)
    {
        switch(category){
            case PUMPKIN:
                return R.drawable.pumpkin;
            case TOMATO:
                return R.drawable.tomato;
            case CARROT:
                return R.drawable.carrot;
            case FISH:
                return R.drawable.fish;
            case BANANA:
                return R.drawable.banana;
        }
        return R.drawable.no;
    }

}
