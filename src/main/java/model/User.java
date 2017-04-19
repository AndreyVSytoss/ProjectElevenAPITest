package model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class User{

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("full_name")
	private String fullName;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("organization")
	private String organization;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("roles")
	private List<RolesItem> roles;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("id")
	private String id;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("email")
	private String email;

	@JsonInclude(JsonInclude.Include.NON_NULL)
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (!fullName.equals(user.fullName)) return false;
		if (!organization.equals(user.organization)) return false;
		if (roles != null ? !roles.equals(user.roles) : user.roles != null) return false;
		if (!id.equals(user.id)) return false;
		if (!email.equals(user.email)) return false;
		if (!username.equals(user.username)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = fullName.hashCode();
		result = 31 * result + organization.hashCode();
		result = 31 * result + (roles != null ? roles.hashCode() : 0);
		result = 31 * result + id.hashCode();
		result = 31 * result + email.hashCode();
		result = 31 * result + username.hashCode();
		return result;
	}
}