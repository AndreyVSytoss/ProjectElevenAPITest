package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class AuthUser{

	@JsonProperty("password")
	private String password;

	@JsonProperty("username")
	private String username;

	public AuthUser setPassword(String password){
		this.password = password;
		return this;
	}

	public String getPassword(){
		return password;
	}

	public AuthUser setUsername(String username){
		this.username = username;
		return this;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"AuthUser{" + 
			"password = '" + password + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}