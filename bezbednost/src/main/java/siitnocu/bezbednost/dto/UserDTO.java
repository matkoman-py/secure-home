package siitnocu.bezbednost.dto;

import siitnocu.bezbednost.data.Role;
import siitnocu.bezbednost.data.User;

public class UserDTO {

	private String username;
    private String password;
	private String name;
    private String surname;
    private String email;
    private String dateOfBirth;
    private String roleName;
    
    
	public UserDTO() {

	}

	public UserDTO(String username, String password, String name, String surname, String email, String dateOfBirth,
			String roleName) {;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.roleName = roleName;
	}

	public UserDTO(User user) {;
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.email = user.getEmail();
		this.dateOfBirth = user.getDateOfBirth();
		this.roleName = user.getRole().getRoleName();
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
