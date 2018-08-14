package in.co.khuranasales.khuranasales;

public class SubCategory {
    public String name;
    public String description;
    public int resource;
    public String fragment_name;
    public String categorizing_text;
    public SubCategory()
    {

    }
    public SubCategory(String name, String description, int resource, String categorizing_text, String fragment_name)
    {
        this.name = name;
        this.description = description;
        this.resource = resource;
        this.categorizing_text = categorizing_text;
        this.fragment_name = fragment_name;
    }

    public String getFragment_name() {
        return fragment_name;
    }

    public void setFragment_name(String fragment_name) {
        this.fragment_name = fragment_name;
    }

    public String getCategorizing_text() {
        return categorizing_text;
    }

    public void setCategorizing_text(String categorizing_text) {
        this.categorizing_text = categorizing_text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

}
