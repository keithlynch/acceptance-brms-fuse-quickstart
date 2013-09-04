package org.jboss.fuse.drools;

public class UnusedPerson {
  private boolean human;
  private boolean likesBeer;
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
