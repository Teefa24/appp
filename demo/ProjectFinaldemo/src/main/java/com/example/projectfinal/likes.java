package com.example.projectfinal;


import java.sql.*;
import java.util.ArrayList;

public class likes {
	
	private static  ArrayList<String> usersWhoLiked = new ArrayList<String>();
	static public void likes() {}

	public void AddLike(String Post)
	{
		String url = "jdbc:mysql://127.0.0.1:3306/?user=root";
		String userName = "root";
		String dbPassword = "123456";

		try (Connection connection = DriverManager.getConnection(url, userName, dbPassword)) {
			String dbName = "db";
			String selectDbQuery = "USE " + dbName;

			try (Statement statement = connection.createStatement()) {
				statement.execute(selectDbQuery);
			}

			String sql = "INSERT INTO likes (Post) VALUES (?)";
			PreparedStatement statement = connection.prepareStatement(sql);

			// Set parameters
			statement.setString(1, Post);

			int rowsInserted = statement.executeUpdate();



		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	static public void likePost(String username) {
		
		usersWhoLiked.add(username);
		
	}
	
	static public void unlikePost(String username) {
		usersWhoLiked.remove(username);
		
	
	}
	
	static public void printLikes() {
		

		System.out.println("\nlike count : "+usersWhoLiked.size());
		System.out.println("\nusers that liked: ");
		for (int i=0; i< usersWhoLiked.size() ;i++) {
			System.out.println("\n	"+usersWhoLiked.get(i));
		}
	}
	
	static public int getLikeCount() {
		return usersWhoLiked.size();
	}
	
	public static void main(String[] args) {
		
	}
}
