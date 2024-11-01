package Model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Component implements Comparable<Component>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2860580639279214877L;
	private static int idCounter = 1;
	private Integer id;
	private String componentName;
	private boolean hasLactose;
	private boolean hasGluten;
	private Double price;
	private String image;
	
	public Component(String componentName, boolean hasLactose, boolean hasGluten, double price) {
		super();
		this.id = idCounter++;
		this.componentName = componentName;
		this.hasLactose = hasLactose;
		this.hasGluten = hasGluten;
		setPrice(price);
		setImage();
	}
	
	public Component(int id) {
		this.id = id;
	}

	public static int getIdCounter() {
		return idCounter;
	}

	public static void setIdCounter(int idCounter) {
		Component.idCounter = idCounter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public boolean isHasLactose() {
		return hasLactose;
	}

	public void setHasLactose(boolean hasLactose) {
		this.hasLactose = hasLactose;
	}

	public boolean isHasGluten() {
		return hasGluten;
	}

	public void setHasGluten(boolean hasGluten) {
		this.hasGluten = hasGluten;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		if(price > 0.0)
			this.price = price;
		else
			price = 0.0;
	}
	
	/**
	 * Convert from a filename to a file URL. Copied from https://stackoverflow.com/questions/8323760/java-get-uri-from-filepath
	 */
	private static String convertToFileURL ( String filename )
	{
	    // On JDK 1.2 and later, simplify this to:
	    // "path = file.toURL().toString()".
	    String path = new File ( filename ).getAbsolutePath ();
	    if ( File.separatorChar != '/' )
	    {
	        path = path.replace ( File.separatorChar, '/' );
	    }
	    if ( !path.startsWith ( "/" ) )
	    {
	        path = "/" + path;
	    }
	    String retVal =  "file:" + path;

	    return retVal;
	}
	
	private void setImage() {
		String thisImage = this.getComponentName() + ".jpg";
		File thisFile = new File(thisImage);
		File defaultFile = new File("default.jpg");
		File selectedFile = thisFile;
		
		if (!selectedFile.exists())
			selectedFile = defaultFile;
		
		String mimetype = null;
		try {
			mimetype = Files.probeContentType(selectedFile.toPath());
		} catch (IOException e) {
			selectedFile = defaultFile;
		}

		if (!(mimetype != null && mimetype.split("/")[0].equals("image"))) {
			selectedFile = defaultFile;
		}
		
		this.image = convertToFileURL(selectedFile.getAbsolutePath());
	}
	
	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Component other = (Component) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Component [id=" + id + ", componentName=" + componentName + ", price=" + price + "]";
	}


	@Override
	public int compareTo(Component o) {
		if(this.price.compareTo(o.getPrice())!=0)
			return this.price.compareTo(o.getPrice());
		return this.id.compareTo(o.getId());
	}
}
