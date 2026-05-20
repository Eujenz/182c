package net.world.instance.inventory.function;

import net.database.bean.Item;
import net.network.client.C_BasePacket;
import net.network.server.S_BasePacket;
import net.network.server.S_ServerMessage;
import net.world.instance.ItemInstance;
import net.world.instance.PetInstance;
import net.world.object.Character;

public class DogCollar extends ItemInstance {
  private PetInstance pet;
  
  private String petName;
  
  private int petClassId;
  
  private int petLevel;
  
  private int petMxhp;
  
  private boolean deleteDb;
  
  public DogCollar(Item i) {
    super(i);
  }
  
  public ItemInstance clone() {
    DogCollar temp = (DogCollar)super.clone();
    temp.setPetName(this.petName);
    temp.setPetClassId(this.petClassId);
    temp.setPetLevel(this.petLevel);
    temp.setPetMxhp(this.petMxhp);
    temp.setDeleteDb(this.deleteDb);
    return temp;
  }
  
  public void drop(Character cha, int x, int y, long count) {
    if (getPet() == null || getPet().isDelete())
      super.drop(cha, x, y, count); 
  }
  
  public void clickItem(Character cha, C_BasePacket bp) {
    cha.SendPacket((S_BasePacket)new S_ServerMessage(79));
  }
  
  public void setPet(PetInstance pet) {
    this.pet = pet;
    if (pet != null) {
      setPetObjectId(pet.getObjectId());
      this.petName = pet.getName();
      this.petClassId = pet.getMon().getNameidN();
      this.petLevel = pet.getLevel();
      this.petMxhp = pet.getTotalHp();
    } 
  }
  
  public PetInstance getPet() {
    return this.pet;
  }
  
  public String getPetName() {
    return this.petName;
  }
  
  public void setPetName(String petName) {
    this.petName = petName;
  }
  
  public int getPetClassId() {
    return this.petClassId;
  }
  
  public void setPetClassId(int petClassId) {
    this.petClassId = petClassId;
  }
  
  public int getPetLevel() {
    return this.petLevel;
  }
  
  public void setPetLevel(int petLevel) {
    this.petLevel = petLevel;
  }
  
  public int getPetMxhp() {
    return this.petMxhp;
  }
  
  public void setPetMxhp(int petMxhp) {
    this.petMxhp = petMxhp;
  }
  
  public boolean isDeleteDb() {
    return this.deleteDb;
  }
  
  public void setDeleteDb(boolean deleteDb) {
    this.deleteDb = deleteDb;
  }
}
