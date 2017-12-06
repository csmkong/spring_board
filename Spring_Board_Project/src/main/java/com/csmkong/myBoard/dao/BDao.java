package com.csmkong.myBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.csmkong.myBoard.dto.BDto;

public class BDao {
	DataSource dataSource;
	public BDao() {
		System.out.println("create BDao.");
		try {
		Context context = new InitialContext();
		dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g");
		
		}catch(Exception ex){
			System.out.println("DB connection Error1");
		}
		System.out.println("Created connection.");
	}

	public ArrayList<BDto> list() {
		
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();

			String query = "SELECT bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent from mvc_board order by bGroup desc, bStep asc";
			System.out.println("ready query.");
			preparedStatement = connection.prepareStatement(query);
			System.out.println("preparedStatement.");
			resultSet= preparedStatement.executeQuery();
			System.out.println("Excute Query.");
			while(resultSet.next()) {
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				
				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				dtos.add(dto);
			}
		}catch(Exception ex) {

			System.out.println("DB connection Error2");
			ex.printStackTrace();
		}finally {
			try {
			if(resultSet != null) resultSet.close();
			if(preparedStatement != null ) preparedStatement.close();
			if(connection != null) connection.close();
			}catch(Exception e) {
				
			}
		}
		
		return dtos;
	}
	
	public void write(String bName, String bTitle, String bContent) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();

			String query = "INSERT INTO MVC_BOARD (bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent) VALUES (mvc_board_seq.nextval,?,?,?,0,mvc_board_seq.currval,0,0)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			int rs = preparedStatement.executeUpdate();
			
		}catch(Exception ex) {

			System.out.println("DB connection Error2");
			ex.printStackTrace();
		}finally {
			try {
			if(preparedStatement != null ) preparedStatement.close();
			if(connection != null) connection.close();
			}catch(Exception e) {
				
			}
		}
	}
	
	public BDto contentView(String strId) {
		BDto dto = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		upHit(strId);
		try {
			String query = "SELECT * FROM mvc_board WHERE bId = ?";
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, Integer.parseInt(strId));
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				
				dto = new BDto(bId,bName,bTitle,bContent,bDate,bHit,bGroup,bStep,bIndent);
			}
			
		}catch(Exception ex) {
			
		}finally {
			try {
				if(connection != null)connection.close();
				if(preparedStatement != null)preparedStatement.close();
			}catch(Exception ex2) {
				
			}
		}
		
		return dto;
	}
	
	public void upHit(String strId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			String query = "UPDATE mvc_board SET bHit = bHit + 1 where bId = ?";
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,strId);
			int rs = preparedStatement.executeUpdate();
		}catch(Exception ex) {
			
		}finally {
			try {
				if(preparedStatement != null ) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e) {
				
			}
		}
	}
}
