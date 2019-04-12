package springbook.user.domain;

public class User{
private String name;
private String id;
private String password;

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


}
