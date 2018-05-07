package com.yyy.school.share.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class ShareDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public List<Map<String, Object>> loadQuestionList(Integer start,
			Integer count) {
		String sql = "select * from question order by createTimeStamp desc limit ?,?";
		return this.jdbcTemplate.queryForList(sql, start, count);
	}

	public Map<String, Object> getUserInfoByUid(String uid) {
		String sql = "select * from user where id = ?";
		return this.jdbcTemplate.queryForMap(sql, uid);
	}


	public List<Map<String, Object>> getAgree(Integer type, Integer mid) {
		String tableName = "";
		if (type == 1){
			tableName = "question";
		} else {
			
		}
		String sql = "select agreeList from " + tableName + " where id = ?";
		return this.jdbcTemplate.queryForList(sql, mid);
	}
	
	public List<Map<String, Object>> getDisagree(Integer type, Integer mid) {
		String tableName = "";
		if (type == 1){
			tableName = "question";
		} else {
			
		}
		String sql = "select disagreeList from " + tableName + " where id = ?";
		return this.jdbcTemplate.queryForList(sql, mid);
	}
	
	public List<Map<String, Object>> getCollect(Integer type, Integer mid) {
		String tableName = "";
		if (type == 1){
			tableName = "question";
		} else {
			
		}
		String sql = "select collectList from " + tableName + " where id = ?";
		return this.jdbcTemplate.queryForList(sql, mid);
	}
	
	public List<Map<String, Object>> getReport(Integer type, Integer mid) {
		String tableName = "";
		if (type == 1){
			tableName = "question";
		} else {
			
		}
		String sql = "select reportList from " + tableName + " where id = ?";
		return this.jdbcTemplate.queryForList(sql, mid);
	}

	public void setAgree(Integer type, Integer mid, String agreeStr) {
		String tableName = "";
		if (type == 1){
			tableName = "question";
		} else {
			
		}
		String sql = "update " + tableName + " set agreeList = ? where id = ?";
		this.jdbcTemplate.update(sql, agreeStr, mid);
		
	}
	
	public void setDisagree(Integer type, Integer mid, String disagreeStr) {
		String tableName = "";
		if (type == 1){
			tableName = "question";
		} else {
			
		}
		String sql = "update " + tableName + " set disagreeList = ? where id = ?";
		this.jdbcTemplate.update(sql, disagreeStr, mid);
		
	}
	
	public void setCollect(Integer type, Integer mid, String collectStr) {
		String tableName = "";
		if (type == 1){
			tableName = "question";
		} else {
			
		}
		String sql = "update " + tableName + " set collectList = ? where id = ?";
		this.jdbcTemplate.update(sql, collectStr, mid);
		
	}
	
	public void setReport(Integer type, Integer mid, String reportStr) {
		String tableName = "";
		if (type == 1){
			tableName = "question";
		} else {
			
		}
		String sql = "update " + tableName + " set reportList = ? where id = ?";
		this.jdbcTemplate.update(sql, reportStr, mid);
		
	}

	public int saveUserInfo(String uid, String nickName, String avatarUrl, String school, Integer sex) {
		String getSql = "select * from user where id = ?";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(getSql, uid);
		if(list.size() != 0 && list != null){
			return 1;
		}
		String sql = "insert into user (id, nickName, avatarUrl, school, createTimeStamp, sex) values(?,?,?,?,?,?)";
		return this.jdbcTemplate.update(sql, uid, nickName, avatarUrl, school, System.currentTimeMillis(), sex);
		
	}

	public int setBioByUid(String bio, String uid) {
		String sql = "update user set bio = ? where id = ?";
		return this.jdbcTemplate.update(sql, bio, uid);
	}

	public int addToQuestion(String content, String picUrl, String uid) {
		String sql = "insert into question (uid, content, picUrl, createTimeStamp) values(?,?,?,?)";
		return this.jdbcTemplate.update(sql, uid, content, picUrl, System.currentTimeMillis());
	}

	public int addToComment(String uid, Integer type, Integer mid,
			String content, String fatherId) {
		String sql = "insert into comment (type, mid, uid, createTimeStamp, content, fatherId) values(?,?,?,?,?,?)";
		return this.jdbcTemplate.update(sql, type, mid, uid, System.currentTimeMillis(), content, fatherId);
	}

	public List<Map<String, Object>> getComment(Integer type, Integer mid) {
		String sql = "select * from comment where type = ? and mid = ? order by createTimeStamp desc";
		return this.jdbcTemplate.queryForList(sql, type, mid);
	}

	public String getNickNameByUid(String uid) {
		String sql = "select nickName from user where id = ?";
		return this.jdbcTemplate.queryForList(sql, uid).get(0).get("nickName").toString();
	}

	public List<Map<String, Object>> getQuestionListByUid(Integer start,
			Integer count, String nowUid) {
		String sql = "select * from question where uid = ? order by createTimeStamp desc limit ?,?";
		return this.jdbcTemplate.queryForList(sql, nowUid, start, count);
	}

	public int deleteFromQuestionByMid(Integer mid) {
		String sql = "delete from question where id = ?";
		return this.jdbcTemplate.update(sql, mid);
	}

	public void addToFeedback(String uid, String content) {
		String sql = "insert into feedback (uid, content, createTimeStamp) values(?,?,?)";
		this.jdbcTemplate.update(sql, uid, content, System.currentTimeMillis());
		
	}

	public Map<String, Object> getMessageByMidAndType(Integer type, Integer mid) {
		String tableName = "";
		if (type == 1) {
			tableName = "question";
		} else {
			
		}
		String sql = "select * from " + tableName + " where id = ?";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql, mid);
		if (list == null || list.size() == 0) {
			return new HashMap<String, Object>();
		} else {
			return list.get(0);
		}
	}

	public int getCommentNumByTypeAndMid(int type, Integer mid) {
		String sql = "select count(1) as commentNum from comment where mid = ? and type = ?";
		return Integer.parseInt(this.jdbcTemplate.queryForMap(sql, mid, type).get("commentNum").toString());
	}


	
}
