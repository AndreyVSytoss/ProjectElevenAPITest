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
}