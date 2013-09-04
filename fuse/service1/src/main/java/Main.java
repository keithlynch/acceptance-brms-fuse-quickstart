import org.apache.commons.io.output.ByteArrayOutputStream;
import org.codehaus.jackson.map.ObjectMapper;


public class Main {
  private class Person{
    private String name;
    private boolean human;
    private boolean likesBeer;
    public void setName(String n){
      this.name=n;
    }
    public String getName(){return name;}
    public boolean isHuman() {
      return human;
    }
    public void setHuman(boolean human) {
      this.human = human;
    }
    public boolean isLikesBeer() {
      return likesBeer;
    }
    public void setLikesBeer(boolean likesBeer) {
      this.likesBeer = likesBeer;
    }
    public String toString(){
      return "Person[human="+human+"; likesBeer="+likesBeer+"]";
    }
  }
  
  public static void main(String[] ads) throws Exception{
    Person person=new Main().new Person();
    person.setName("Fred");
    person.setHuman(true);
    
    
    ByteArrayOutputStream out=new ByteArrayOutputStream();
    new ObjectMapper().writeValue(out, person);
    System.out.println(new String(out.toByteArray()));
  }
}
