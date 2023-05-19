package play.project1.repository.member.sql;

public abstract class MemberSQL {

	public static final String INSERT = "insert into member(id, name) values (?, ?)";
	public static final String FIND_BY_ID = "select * from member where id = ? for update";
	public static final String UPDATE = "update member set name = ?, point = ? where id = ?";
	public static final String DELETE = "delete from member where id = ?";
}
