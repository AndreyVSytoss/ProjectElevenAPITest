package model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class User{

	@JsonProperty("full_name")
	private String fullName;

	@JsonProperty("organization")
	private String organization;

	@JsonProperty("roles")
	private List<RolesItem> roles;

	@JsonProperty("id")
	private String id;

	@JsonProperty("email")
	private String email;

	@JsonProperty("username")
	private String username;

	public User setFullName(String fullName){
		this.fullName = fullName;
		return this;
	}

	public String getFullName(){
		return fullName;
	}

	public User setOrganization(String organization){
		this.organization = organization;
		return this;
	}

	public String getOrganization(){
		return organization;
	}

	public User setRoles(List<RolesItem> roles){
		this.roles = roles;
		return this;
	}

	public List<RolesItem> getRoles(){
		return roles;
	}

	public User setId(String id){
		this.id = id;
		return this;
	}

	public String getId(){
		return id;
	}

	public User setEmail(String email){
		this.email = email;
		return this;
	}

	public String getEmail(){
		return email;
	}

	public User setUsername(String username){
		this.username = username;
		return this;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"User{" + 
			"full_name = '" + fullName + '\'' + 
			",organization = '" + organization + '\'' + 
			",roles = '" + roles + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}