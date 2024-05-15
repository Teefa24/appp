package com.example.projectfinal;

import java.util.*;
import javax.swing.ImageIcon;

public class User {
    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String bio;
    private ImageIcon profilepicture;
    private String birthDate;
    private List<User> friendlist;
    private List<Post> posts;

    public User() {
        this.friendlist = new ArrayList<>();
        this.posts = new ArrayList<Post>();
    }

    public User(String username, String password,String firstName,String lastName, String bio,String email, String profilepicture)
    {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.profilepicture = new ImageIcon(profilepicture);
        this.friendlist = new ArrayList<>();
        this.posts = new ArrayList<Post>();


    }

    public void addPost(Post post) {
        posts.add(post);
    }
    public void removePost(Post post) {
        posts.remove(post);
    }
    public List<Post> getPosts() {
        return posts;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setFirstname(String name) {
        this.firstName = name;
    }
    public void setLastname(String name) {
        this.lastName = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setProfilePicture(ImageIcon profilepicture) {
        this.profilepicture = profilepicture;
    }


    public String getUsername() {
        return username;
    }
    public String getBirthDate() {
        return birthDate;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public String getBio() {
        return bio;
    }
    public List<User> getFriendList() {
        return friendlist;
    }
    public ImageIcon getProfilePicture(){
        return profilepicture;
    }


    public void addFriend(User friend)
    {
        boolean isExist;
        isExist = friendlist.contains(friend);
        System.out.println(" is friend exist =  " + isExist );
        if (isExist == false )
        {
            friendlist.add(friend);
            System.out.println("Friend added successfully.");
        }
        else
        {
            System.out.println("User is already a friend.");
        }
    }
    public void removeFriend(User friend)
    {
        boolean isExist;
        isExist= friendlist.contains(friend);
        System.out.println(" is friend exist =  " +
                isExist );
        if (isExist == true )
        {
            friendlist.remove(friend);
            System.out.println("Friend removed successfully.");
        }
        else
        {
            System.out.println("User is not in the friendlist .");
        }
    }
}





