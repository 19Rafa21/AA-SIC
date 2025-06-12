
package backend.DTOs;

import backend.Models.User;

public class UserDTO {
    private String username;
    private String email;
    private String password;
    private String discriminator;

    public UserDTO(String username, String email, String password, String discriminator) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.discriminator = discriminator;
    }

    public UserDTO(User user) {
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

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

}
