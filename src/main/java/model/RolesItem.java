package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class RolesItem{

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private String id;

	public RolesItem setName(String name){
		this.name = name;
		return this;
	}

	public String getName(){
		return name;
	}

	public RolesItem setId(String id){
		this.id = id;
		return this;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"RolesItem{" + 
			"name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RolesItem rolesItem = (RolesItem) o;

		if (name != null ? !name.equals(rolesItem.name) : rolesItem.name != null) return false;
		if (id != null ? !id.equals(rolesItem.id) : rolesItem.id != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (id != null ? id.hashCode() : 0);
		return result;
	}
}