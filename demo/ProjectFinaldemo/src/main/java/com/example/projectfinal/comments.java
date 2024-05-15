package com.example.projectfinal;

import java.sql.*;
import java.util.ArrayList;

public class comments {
	
	private static  ArrayList<String> usersWhoCommented = new ArrayList<String>();
	private static  ArrayList<String> commentContents = new ArrayList<String>();

	static public void comment() {

	}

    static public void comment(String username, String comment) {
    	usersWhoCommented.add(username);
    	commentContents.add(comment);
    	
    }
    
    public void AddComment(String CommentContent, String Post)
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

			String sql = "INSERT INTO comments (Post,Content) VALUES (?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);

			// Set parameters
			statement.setString(2, CommentContent);
			statement.setString(1, Post);

			int rowsInserted = statement.executeUpdate();



		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    static public void deleteComment(String username, String comment) {
    	usersWhoCommented.remove(username);
    	commentContents.remove(comment);
    }
    
    static public void PrintComments() {
		System.out.println("\n comments ("+ usersWhoCommented.size() +")");
		
		for (int i=0; i< usersWhoCommented.size() ;i++) {
			System.out.println("\n	("+usersWhoCommented.get(i)+"):");
			System.out.println("	  "+commentContents.get(i));
		}
		
    }
	
	public static void main(String[] args) {
		
	}

}
