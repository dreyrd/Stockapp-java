package br.com.ifsp.StockApp.model.user;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode
@Entity(name = "User")
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String name;
    private byte[] userPhoto;
    private String email;
    private String password;

    public User(UserDataCreation userDataCreation){
        this.name = userDataCreation.name();
        this.userPhoto = userDataCreation.userPhoto();
        this.email = userDataCreation.email();
        this.password = userDataCreation.password();
    }
}
