package spring.user.domain;

public class User{

	private static final int BASIC = 1;
	private static final int SILVER = 2;
	private static final int GOLD = 3;


private String name;
private String id;
private String password;
private Level level;
private int login;
private int recommend;


public User(){};
public User(String name,String id,String password){
	this.name=name;
	this.id=id;
	this.password=password;
}

	public User(String name, String id, String password, Level level, int login, int recommend) {
		this.name = name;
		this.id = id;
		this.password = password;
		this.level = level;
		this.login = login;
		this.recommend = recommend;
	}

	public void setName(String name){
this.name=name;
}
public String getName(){
return name;
}

public void setId(String id){
this.id=id;	
}
public String getId(){
return id;
}

public void setPassword(String password){
this.password=password;
}

public String getPassword(){
return password;
}

public void setLevel(Level level){
	this.level=level;
}
public Level getLevel(){
	return level;
}

	public int getLogin() {
		return login;
	}

	public void setLogin(int login) {
		this.login = login;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
}
