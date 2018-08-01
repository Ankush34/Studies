package in.co.khuranasales.khuranasales.exportWorkers;

public class selectionPojo {
    public String selection_text;
    public Boolean selected;
    public String promoter_email;

    public String getSelection_text() {
        return selection_text;
    }

    public void setSelection_text(String selection_text) {
        this.selection_text = selection_text;
    }

    public selectionPojo(String text, Boolean selected)
    {
        this.selected = selected;
        this.selection_text = text;
    }
    public Boolean is_selected()
    {
        return selected;
    }

    public void set_selected(Boolean selected)
    {
        this.selected = selected;
    }

    public String getPromoter_email() {
        return promoter_email;
    }

    public void setPromoter_email(String promoter_email) {
        this.promoter_email = promoter_email;
    }

}
