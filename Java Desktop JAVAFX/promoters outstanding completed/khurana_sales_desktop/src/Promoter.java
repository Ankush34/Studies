
public class Promoter {
String name = "";
String email = "";
String promoter_id = "";
String promoter_outstanding = "";

public Promoter(String name,String email, String promoter_id)
	 {
		this.name = name;
		this.email = email;
		this.promoter_id = promoter_id;
	 }


public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getPromoter_id() {
	return promoter_id;
}

public String getPromoter_outstanding() {
	return promoter_outstanding;
}


public void setPromoter_outstanding(String promoter_outstanding) {
	this.promoter_outstanding = promoter_outstanding;
}

public void setPromoter_id(String promoter_id) {
	this.promoter_id = promoter_id;
}

}
