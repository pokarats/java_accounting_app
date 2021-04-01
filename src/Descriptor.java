/**
 * stores category and description for each Transaction
 *
 * CS261 - Assignment #4-#5 - Summer 2017
 * Created by: Noon Pokaratsiri
 * Version: 08/13/2017
 */
public class Descriptor {
    private String category;
    private String description;

    public Descriptor(){
        category = "No Category";
        description = "No description";
    }

    public Descriptor(String aCategory, String aDescription) {
        category = aCategory;
        description = aDescription;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return category + " - " + description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Descriptor))
            return false;
        Descriptor aDescriptor = (Descriptor) obj;

        return this.category.equals(aDescriptor.category) || this.description.equals(aDescriptor.description);
    }
}
