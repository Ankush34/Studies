
public class UploadProductPojo {
	public String image_source;
	public String name;
	public String uploaded_date;
	public String uploaded_status;
	public boolean from_url;
	public UploadProductPojo(String source, String name, String uploaded_date, String uploaded_status, boolean from_url)
	{
		this.image_source = source;
		this.name = name;
		this.uploaded_date = uploaded_date;
		this.uploaded_status = uploaded_status;
		this.from_url = from_url;
	}
	
	public boolean isFromUrl()
	{
		return from_url;
	}
	
	public String getImage_source() {
		return image_source;
	}

	public void setImage_source(String image_source) {
		this.image_source = image_source;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUploaded_date() {
		return uploaded_date;
	}

	public void setUploaded_date(String uploaded_date) {
		this.uploaded_date = uploaded_date;
	}

	public String getUploaded_status() {
		return uploaded_status;
	}

	public void setUploaded_status(String uploaded_status) {
		this.uploaded_status = uploaded_status;
	}
}
