package cat.udl.tidic.amb.tournmaster;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("created_at")
    private String created_at;
    @SerializedName("username")
    private String username;
    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String name;
    @SerializedName("surname")
    private String surname;
    @SerializedName("genere")
    private String genere;
    @SerializedName("phone")
    private String phone;
    @SerializedName("photo")
    private String photo;
    @SerializedName("password")
    private String password;

    public User() {
    }

    public User(String created_at, String username, String email, String name, String surname, String birthday, String genere,String password) {
        this.created_at = created_at;
        this.username = username;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.genere = genere;
        this.password= password;
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

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @NonNull
    @Override
    public String toString(){
        return this.name + " " + this.surname;
    }
}
