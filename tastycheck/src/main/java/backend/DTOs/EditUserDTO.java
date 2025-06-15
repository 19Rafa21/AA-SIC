
package backend.DTOs;

import backend.Models.User;

public class EditUserDTO {
    private String username;
    private String email;
    private String password;
    private String userImage;

    public EditUserDTO(String username, String email, String password, String userImage) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userImage = userImage;
    }

    public EditUserDTO(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}

