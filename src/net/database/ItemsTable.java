package net.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import net.Config;
import net.database.bean.Item;
import net.world.instance.ItemArmorInstance;
import net.world.instance.ItemInstance;
import net.world.instance.ItemMagicInstance;
import net.world.instance.ItemWeaponInstance;
import net.world.instance.PcInstance;
import net.world.instance.inventory.function.BerserkerAxe;
import net.world.instance.inventory.function.BlessEva;
import net.world.instance.inventory.function.BlindingPotion;
import net.world.instance.inventory.function.BluePotion;
import net.world.instance.inventory.function.Candle;
import net.world.instance.inventory.function.ChainMailMagicResistance;
import net.world.instance.inventory.function.CloakInvisibility;
import net.world.instance.inventory.function.CloakMagic;
import net.world.instance.inventory.function.ConcentratedPotionExtraHealing;
import net.world.instance.inventory.function.ConcentratedPotionGreaterHealing;
import net.world.instance.inventory.function.ConcentratedPotionHealing;
import net.world.instance.inventory.function.CurePoisonPotion;
import net.world.instance.inventory.function.DogCollar;
import net.world.instance.inventory.function.DogWhistle;
import net.world.instance.inventory.function.EbonyWand;
import net.world.instance.inventory.function.ElvenCloak;
import net.world.instance.inventory.function.ElvenShield;
import net.world.instance.inventory.function.ElvenWafer;
import net.world.instance.inventory.function.EmptyScroll;
import net.world.instance.inventory.function.EnchantArmor;
import net.world.instance.inventory.function.EnchantWeapon;
import net.world.instance.inventory.function.FireCracker;
import net.world.instance.inventory.function.FloatingEyeMeat;
import net.world.instance.inventory.function.GreaterHastePotion;
import net.world.instance.inventory.function.GreaterHealingPotion;
import net.world.instance.inventory.function.HastePotion;
import net.world.instance.inventory.function.HealingPotion;
import net.world.instance.inventory.function.HelmMagicHealing;
import net.world.instance.inventory.function.HelmMagicPower;
import net.world.instance.inventory.function.HelmMagicResistance;
import net.world.instance.inventory.function.HelmMagicSpeed;
import net.world.instance.inventory.function.ItemExpDouble;
import net.world.instance.inventory.function.ItemShockStun;
import net.world.instance.inventory.function.ItemStormWalk;
import net.world.instance.inventory.function.ItemTripleArrow;
import net.world.instance.inventory.function.Lamp;
import net.world.instance.inventory.function.Lantern;
import net.world.instance.inventory.function.LanternOil;
import net.world.instance.inventory.function.LesserHealingPotion;
import net.world.instance.inventory.function.Letter;
import net.world.instance.inventory.function.LetterPledge;
import net.world.instance.inventory.function.MagicFlute;
import net.world.instance.inventory.function.MapUse;
import net.world.instance.inventory.function.MapleWand;
import net.world.instance.inventory.function.Meat;
import net.world.instance.inventory.function.MonthCards;
import net.world.instance.inventory.function.MonthCardsOther;
import net.world.instance.inventory.function.OgreBelt;
import net.world.instance.inventory.function.PineWand;
import net.world.instance.inventory.function.PotionWisdom;
import net.world.instance.inventory.function.PotionofBravery;
import net.world.instance.inventory.function.PowerScroll;
import net.world.instance.inventory.function.RandomBox;
import net.world.instance.inventory.function.RingPolymorphControl;
import net.world.instance.inventory.function.RingTeleportControl;
import net.world.instance.inventory.function.ScrollEscapeGiran;
import net.world.instance.inventory.function.ScrollEscapePledge;
import net.world.instance.inventory.function.ScrollLabeledKERNODWEL;
import net.world.instance.inventory.function.ScrollLabeledPRATYAVAYAH;
import net.world.instance.inventory.function.ScrollLabeledVELOXNEB;
import net.world.instance.inventory.function.ScrollLabeledVERRYEDHORAE;
import net.world.instance.inventory.function.ScrollPolymorph;
import net.world.instance.inventory.function.ScrollPolymorphTO;
import net.world.instance.inventory.function.ScrollTeleportation;
import net.world.instance.inventory.function.ShieldEva;
import net.world.instance.inventory.function.ShuromItem_01;
import net.world.instance.inventory.function.ShuromItem_02;
import net.world.instance.inventory.function.ShuromItem_03;
import net.world.instance.inventory.function.ShuromItem_04;
import net.world.instance.inventory.function.ShuromItem_05;
import net.world.instance.inventory.function.SlimeRaceTicket;
import net.world.instance.inventory.function.StaffOfMana;
import net.world.instance.inventory.function.TotemAtuba;
import net.world.instance.inventory.function.TotemDudaMari;
import net.world.instance.inventory.function.TotemGandi;
import net.world.instance.inventory.function.TotemNeruga;
import net.world.instance.inventory.function.TotemRova;
import net.world.instance.inventory.function.TrollBelt;
import net.world.instance.inventory.function.Whetstone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemsTable {
  final Logger log = LoggerFactory.getLogger(ItemsTable.class);
  
  private Map<Byte, Integer> _Gfxmodes = new HashMap<Byte, Integer>();
  
  private Map<String, Integer> _materialTypes = new HashMap<String, Integer>();
  
  private Map<String, Byte> _ItemTypes = new HashMap<String, Byte>();
  
  private HashMap<Integer, Item> _items = null;
  
  private static class Holder {
    static ItemsTable instance = new ItemsTable();
  }
  
  public static ItemsTable getInstance() {
    return Holder.instance;
  }
  
  private ItemsTable() {
    System.out.print("[SQL] 加?物品信息.");
    try {
      this._ItemTypes.put("none", Byte.valueOf((byte)0));
      this._ItemTypes.put("arrow", Byte.valueOf((byte)1));
      this._ItemTypes.put("axe", Byte.valueOf((byte)2));
      this._ItemTypes.put("bow", Byte.valueOf((byte)3));
      this._ItemTypes.put("spear", Byte.valueOf((byte)4));
      this._ItemTypes.put("sword", Byte.valueOf((byte)5));
      this._ItemTypes.put("wand", Byte.valueOf((byte)6));
      this._ItemTypes.put("dagger", Byte.valueOf((byte)8));
      this._ItemTypes.put("belt", Byte.valueOf((byte)10));
      this._ItemTypes.put("helm", Byte.valueOf((byte)12));
      this._ItemTypes.put("amulet", Byte.valueOf((byte)14));
      this._ItemTypes.put("shirt", Byte.valueOf((byte)15));
      this._ItemTypes.put("armor", Byte.valueOf((byte)16));
      this._ItemTypes.put("cloak", Byte.valueOf((byte)17));
      this._ItemTypes.put("accessory", Byte.valueOf((byte)18));
      this._ItemTypes.put("glove", Byte.valueOf((byte)20));
      this._ItemTypes.put("shield", Byte.valueOf((byte)21));
      this._ItemTypes.put("boots", Byte.valueOf((byte)23));
      this._ItemTypes.put("twohand", Byte.valueOf((byte)24));
      this._ItemTypes.put("book", Byte.valueOf((byte)30));
      this._Gfxmodes.put(Byte.valueOf((byte)5), new Integer(4));
      this._Gfxmodes.put(Byte.valueOf((byte)2), new Integer(11));
      this._Gfxmodes.put(Byte.valueOf((byte)3), new Integer(20));
      this._Gfxmodes.put(Byte.valueOf((byte)4), new Integer(24));
      this._Gfxmodes.put(Byte.valueOf((byte)6), new Integer(40));
      this._Gfxmodes.put(Byte.valueOf((byte)8), new Integer(4));
      this._Gfxmodes.put(Byte.valueOf((byte)24), new Integer(4));
      this._materialTypes.put("oriharukon", new Integer(22));
      this._materialTypes.put("mineral", new Integer(21));
      this._materialTypes.put("gemstone", new Integer(20));
      this._materialTypes.put("glass", new Integer(19));
      this._materialTypes.put("blackmithril", new Integer(18));
      this._materialTypes.put("mithril", new Integer(17));
      this._materialTypes.put("platinum", new Integer(16));
      this._materialTypes.put("gold", new Integer(15));
      this._materialTypes.put("silver", new Integer(14));
      this._materialTypes.put("copper", new Integer(13));
      this._materialTypes.put("steel", new Integer(12));
      this._materialTypes.put("iron", new Integer(11));
      this._materialTypes.put("dragonscale", new Integer(10));
      this._materialTypes.put("bone", new Integer(9));
      this._materialTypes.put("wood", new Integer(8));
      this._materialTypes.put("leather", new Integer(7));
      this._materialTypes.put("cloth", new Integer(6));
      this._materialTypes.put("paper", new Integer(5));
      this._materialTypes.put("animalmatter", new Integer(4));
      this._materialTypes.put("vegetation", new Integer(3));
      this._materialTypes.put("web", new Integer(2));
      this._materialTypes.put("liquid", new Integer(1));
      this._materialTypes.put("none", new Integer(0));
      Connection con = DatabaseConnection.getInstance().getConnection();
      PreparedStatement st = con.prepareStatement("SELECT * FROM items");
      ResultSet rs = st.executeQuery();
      Item i = null;
      this._items = new HashMap<Integer, Item>();
      while (rs.next()) {
        try {
          i = new Item();
          i.setItemId(rs.getInt("item_id"));
          i.set_name(rs.getString("name"));
          i.setType(((Byte)this._ItemTypes.get(rs.getString("type"))).byteValue());
          i.set_dmgsmall(rs.getInt("dmg_small"));
          i.set_dmglarge(rs.getInt("dmg_large"));
          if (rs.getString("material") != null && rs.getString("material").length() > 0)
            i.set_material(((Integer)this._materialTypes.get(rs.getString("material"))).intValue()); 
          i.setWeight(rs.getInt("weight"));
          i.set_gfxid(rs.getInt("inv_gfx_id"));
          i.set_groundgfxid(rs.getInt("grd_gfx_id"));
          i.set_nameid(rs.getString("nameid"));
          try {
            i.set_nameidN(Integer.valueOf(rs.getString("nameid").substring(1, rs.getString("nameid").length())).intValue());
          } catch (Exception e) {
            try {
              StringTokenizer stt = new StringTokenizer(rs.getString("nameid"), "$");
              StringBuilder sb = new StringBuilder();
              while (stt.hasMoreTokens())
                sb.append(stt.nextToken()); 
              stt = new StringTokenizer(sb.toString());
              sb = new StringBuilder();
              while (stt.hasMoreTokens())
                sb.append(stt.nextToken()); 
              i.set_nameidN(Integer.valueOf(sb.toString()).intValue());
            } catch (Exception e2) {
              this.log.debug(e2.getMessage());
            } 
          } 
          i.setSell((rs.getInt("sell") == 1));
          i.setPiles((rs.getInt("piles") == 1));
          i.setTrade((rs.getInt("trade") == 1));
          i.setCanDrop((rs.getInt("drop") == 1));
          i.setWerehouse((rs.getInt("werehouse") == 1));
          i.setEnchant((rs.getInt("enchant") == 1));
          i.set_safenchant(rs.getInt("safe_enchant"));
          i.set_royal(rs.getInt("royal"));
          i.set_knight(rs.getInt("knight"));
          i.set_elf(rs.getInt("elf"));
          i.set_mage(rs.getInt("mage"));
          i.setAddHit(rs.getInt("add_hit"));
          i.setAddDmg(rs.getInt("add_dmg"));
          i.set_ac(rs.getInt("add_ac"));
          i.setAddstr(rs.getInt("add_str"));
          i.setAdddex(rs.getInt("add_dex"));
          i.setAddcon(rs.getInt("add_con"));
          i.setAddint(rs.getInt("add_int"));
          i.setAddwis(rs.getInt("add_wis"));
          i.setAddcha(rs.getInt("add_cha"));
          i.setAddhp(rs.getInt("add_hp"));
          i.setAddmp(rs.getInt("add_mp"));
          i.setAddsp(rs.getInt("add_sp"));
          i.setAddmr(rs.getInt("add_mr"));
          i.setAddHpr(rs.getInt("add_hpr"));
          i.setAddMpr(rs.getInt("add_mpr"));
          i.sethaste((rs.getInt("haste") == 1));
          i.setCanbedmg((rs.getInt("can_be_dmg") == 1));
          i.setMinLvl(rs.getInt("minlvl"));
          i.setMaxLvl(rs.getInt("maxlvl"));
          i.set_EffectID(rs.getInt("effect_id"));
          i.setIschaotic(true);
          i.setIslawful(true);
          i.setIsmomtree(true);
          i.setIsneutral(true);
          i.setIstower(true);
          i.setSkill_id(rs.getInt("skill_id"));
          i.setTohand((rs.getInt("two_hand") == 1));
          i.setSetitemUid(rs.getInt("set_item_uid"));
          i.setContinuous(rs.getInt("continuous"));
          int type1 = 0;
          switch (i.getType()) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
            case 24:
              i.setGfxmode(((Integer)this._Gfxmodes.get(Byte.valueOf(i.getType()))).intValue());
            case 1:
              i.setType1(1);
              i.setType2(1);
              break;
            case 10:
              if (type1 == 0)
                type1 = 37; 
            case 12:
              if (type1 == 0)
                type1 = 22; 
            case 13:
              if (type1 == 0)
                type1 = 40; 
            case 14:
              if (type1 == 0)
                type1 = 24; 
            case 15:
              if (type1 == 0)
                type1 = 18; 
            case 16:
              if (type1 == 0)
                type1 = 2; 
            case 17:
              if (type1 == 0)
                type1 = 10; 
            case 18:
              if (type1 == 0)
                type1 = 23; 
            case 19:
              if (type1 == 0)
                type1 = 37; 
            case 20:
              if (type1 == 0)
                type1 = 20; 
            case 21:
              if (type1 == 0)
                type1 = 25; 
            case 22:
              if (type1 == 0)
                type1 = 1; 
            case 23:
              if (type1 == 0)
                type1 = 21; 
              i.setType1(2);
              i.setType2(type1);
              break;
            case 0:
              i.setType1(0);
              i.setType2(0);
              break;
            case 30:
              i.setType1(3);
              i.setType2(3);
              break;
          } 
          this._items.put(Integer.valueOf(i.getItemId()), i);
        } catch (Exception localException1) {
          localException1.printStackTrace();
        } 
      } 
      System.out.println(" ?量：" + this._items.size());
      DatabaseConnection.getInstance().close(con, st, rs);
    } catch (Exception localException2) {
      localException2.printStackTrace();
    } 
  }
  
  public Item getTemplate(int id) {
    try {
      return this._items.get(Integer.valueOf(id));
    } catch (Exception localException) {
      return null;
    } 
  }
  
  public ItemInstance newItem(int id, boolean is, boolean nextID) {
    try {
      Item i = getTemplate(id);
      if (i != null) {
        ItemInstance newItem = FunctionItem(i);
        if (newItem != null) {
          if (nextID) {
            newItem.setInvID(Config.getObjectID_ETC());
            newItem.setObjectId(newItem.getInvID());
          } 
          newItem.setGfx(newItem.getItem().get_groundgfxid());
          newItem.setCount(1L);
          newItem.setBless(1);
          newItem.setDefinite(is);
          return newItem;
        } 
      } else {
        System.out.println("???建物品?出?，物品ID：" + id);
      } 
    } catch (Exception localException) {}
    return null;
  }
  
  public ItemInstance FunctionItem(Item temp) {
    EmptyScroll emptyScroll;
    MapUse mapUse;
    Lamp lamp;
    Meat meat;
    MapleWand mapleWand;
    PineWand pineWand;
    EnchantArmor enchantArmor;
    ScrollLabeledPRATYAVAYAH scrollLabeledPRATYAVAYAH;
    EnchantWeapon enchantWeapon;
    ScrollLabeledVERRYEDHORAE scrollLabeledVERRYEDHORAE;
    ScrollTeleportation scrollTeleportation;
    ScrollLabeledKERNODWEL scrollLabeledKERNODWEL;
    ScrollLabeledVELOXNEB scrollLabeledVELOXNEB;
    Candle candle;
    ElvenWafer elvenWafer;
    ElvenCloak elvenCloak;
    CloakInvisibility cloakInvisibility;
    CloakMagic cloakMagic;
    ElvenShield elvenShield;
    BluePotion bluePotion;
    CurePoisonPotion curePoisonPotion;
    HastePotion hastePotion;
    GreaterHastePotion greaterHastePotion;
    HealingPotion healingPotion;
    LesserHealingPotion lesserHealingPotion;
    GreaterHealingPotion greaterHealingPotion;
    BlindingPotion blindingPotion;
    RingTeleportControl ringTeleportControl;
    RingPolymorphControl ringPolymorphControl;
    EbonyWand ebonyWand;
    Lantern lantern;
    LanternOil lanternOil;
    SlimeRaceTicket slimeRaceTicket;
    BerserkerAxe berserkerAxe;
    ShieldEva shieldEva;
    TotemAtuba totemAtuba;
    TotemNeruga totemNeruga;
    TotemGandi totemGandi;
    TotemRova totemRova;
    TotemDudaMari totemDudaMari;
    ChainMailMagicResistance chainMailMagicResistance;
    HelmMagicResistance helmMagicResistance;
    FloatingEyeMeat floatingEyeMeat;
    MagicFlute magicFlute;
    HelmMagicHealing helmMagicHealing;
    HelmMagicSpeed helmMagicSpeed;
    HelmMagicPower helmMagicPower;
    PotionofBravery potionofBravery;
    PotionWisdom potionWisdom;
    ScrollPolymorph scrollPolymorph;
    StaffOfMana staffOfMana;
    Letter letter;
    DogWhistle dogWhistle;
    Whetstone whetstone;
    LetterPledge letterPledge;
    DogCollar dogCollar;
    ConcentratedPotionHealing concentratedPotionHealing;
    ConcentratedPotionExtraHealing concentratedPotionExtraHealing;
    ConcentratedPotionGreaterHealing concentratedPotionGreaterHealing;
    ScrollEscapeGiran scrollEscapeGiran;
    BlessEva blessEva;
    TrollBelt trollBelt;
    OgreBelt ogreBelt;
    ItemWeaponInstance itemWeaponInstance;
    ShuromItem_05 shuromItem_05;
    ItemArmorInstance itemArmorInstance;
    ItemMagicInstance itemMagicInstance;
    ScrollEscapePledge scrollEscapePledge;
    ShuromItem_02 shuromItem_02;
    ShuromItem_01 shuromItem_01;
    ShuromItem_03 shuromItem_03;
    ShuromItem_04 shuromItem_04;
    ItemInstance itemInstance1;
    MonthCards monthCards;
    MonthCardsOther monthCardsOther;
    FireCracker fireCracker = null;
    ItemInstance item = null;
    if (RandomGift.boxidlist.contains("" + temp.getItemId()))
      return (ItemInstance)new RandomBox(temp); 
    if (temp.getItemId() >= 600 && temp.getItemId() <= 636)
      return (ItemInstance)new PowerScroll(temp); 
    if (temp.get_name().equalsIgnoreCase("暴風疾走"))
      return (ItemInstance)new ItemStormWalk(temp); 
    if (temp.get_name().equalsIgnoreCase("衝擊之暈"))
      return (ItemInstance)new ItemShockStun(temp); 
    if (temp.get_name().equalsIgnoreCase("三重矢"))
      return (ItemInstance)new ItemTripleArrow(temp); 
    if (temp.get_name().startsWith("經驗藥水"))
      return (ItemInstance)new ItemExpDouble(temp); 
    if (temp.get_name().startsWith("商城變卷"))
      return (ItemInstance)new ScrollPolymorphTO(temp); 
    switch (temp.get_nameidN()) {
      case 1486:
      case 1892:
      case 1893:
      case 1894:
      case 1895:
        emptyScroll = new EmptyScroll(temp);
        break;
      case 1102:
      case 1103:
      case 1104:
      case 1105:
      case 1106:
      case 1107:
      case 1108:
      case 1109:
      case 1188:
      case 1533:
      case 1534:
      case 1535:
      case 1607:
      case 1889:
        mapUse = new MapUse(temp);
        break;
      case 2:
        lamp = new Lamp(temp);
        break;
      case 23:
        meat = new Meat(temp);
        break;
      case 27:
      case 260:
        mapleWand = new MapleWand(temp);
        break;
      case 28:
      case 258:
        pineWand = new PineWand(temp);
        break;
      case 30:
      case 249:
        enchantArmor = new EnchantArmor(temp);
        break;
      case 34:
      case 243:
        scrollLabeledPRATYAVAYAH = new ScrollLabeledPRATYAVAYAH(temp);
        break;
      case 35:
      case 244:
        enchantWeapon = new EnchantWeapon(temp);
        break;
      case 39:
      case 505:
      case 1409:
        scrollLabeledVERRYEDHORAE = new ScrollLabeledVERRYEDHORAE(temp);
        break;
      case 40:
      case 230:
        scrollTeleportation = new ScrollTeleportation(temp);
        break;
      case 43:
      case 55:
        scrollLabeledKERNODWEL = new ScrollLabeledKERNODWEL(temp);
        break;
      case 49:
      case 257:
        scrollLabeledVELOXNEB = new ScrollLabeledVELOXNEB(temp);
        break;
      case 67:
        candle = new Candle(temp);
        break;
      case 110:
        elvenWafer = new ElvenWafer(temp);
        break;
      case 170:
        elvenCloak = new ElvenCloak(temp);
        break;
      case 180:
        cloakInvisibility = new CloakInvisibility(temp);
        break;
      case 182:
        cloakMagic = new CloakMagic(temp);
        break;
      case 187:
        elvenShield = new ElvenShield(temp);
        break;
      case 232:
      case 507:
        bluePotion = new BluePotion(temp);
        break;
      case 233:
      case 763:
        curePoisonPotion = new CurePoisonPotion(temp);
        break;
      case 234:
      case 264:
        hastePotion = new HastePotion(temp);
        break;
      case 1652234:
        greaterHastePotion = new GreaterHastePotion(temp);
        break;
      case 235:
        healingPotion = new HealingPotion(temp);
        break;
      case 237:
        lesserHealingPotion = new LesserHealingPotion(temp);
        break;
      case 238:
      case 794:
        greaterHealingPotion = new GreaterHealingPotion(temp);
        break;
      case 239:
        blindingPotion = new BlindingPotion(temp);
        break;
      case 241:
        ringTeleportControl = new RingTeleportControl(temp);
        break;
      case 261:
        ringPolymorphControl = new RingPolymorphControl(temp);
        break;
      case 263:
        ebonyWand = new EbonyWand(temp);
        break;
      case 326:
        lantern = new Lantern(temp);
        break;
      case 327:
        lanternOil = new LanternOil(temp);
        break;
      case 343:
        slimeRaceTicket = new SlimeRaceTicket(temp);
        break;
      case 418:
        berserkerAxe = new BerserkerAxe(temp);
        break;
      case 419:
        shieldEva = new ShieldEva(temp);
        break;
      case 499:
        totemAtuba = new TotemAtuba(temp);
        break;
      case 500:
        totemNeruga = new TotemNeruga(temp);
        break;
      case 501:
        totemGandi = new TotemGandi(temp);
        break;
      case 502:
        totemRova = new TotemRova(temp);
        break;
      case 503:
        totemDudaMari = new TotemDudaMari(temp);
        break;
      case 516:
        chainMailMagicResistance = new ChainMailMagicResistance(temp);
        break;
      case 569:
        helmMagicResistance = new HelmMagicResistance(temp);
        break;
      case 623:
        floatingEyeMeat = new FloatingEyeMeat(temp);
        break;
      case 777:
        magicFlute = new MagicFlute(temp);
        break;
      case 938:
        helmMagicHealing = new HelmMagicHealing(temp);
        break;
      case 939:
        helmMagicSpeed = new HelmMagicSpeed(temp);
        break;
      case 940:
        helmMagicPower = new HelmMagicPower(temp);
        break;
      case 943:
        potionofBravery = new PotionofBravery(temp);
        break;
      case 944:
        potionWisdom = new PotionWisdom(temp);
        break;
      case 971:
        scrollPolymorph = new ScrollPolymorph(temp);
        break;
      case 1063:
        staffOfMana = new StaffOfMana(temp);
        break;
      case 1075:
        letter = new Letter(temp);
        break;
      case 1086:
        dogWhistle = new DogWhistle(temp);
        break;
      case 1100:
        whetstone = new Whetstone(temp);
        break;
      case 1146:
        letterPledge = new LetterPledge(temp);
        break;
      case 1173:
        dogCollar = new DogCollar(temp);
        break;
      case 1251:
        concentratedPotionHealing = new ConcentratedPotionHealing(temp);
        break;
      case 1252:
        concentratedPotionExtraHealing = new ConcentratedPotionExtraHealing(temp);
        break;
      case 1253:
        concentratedPotionGreaterHealing = new ConcentratedPotionGreaterHealing(temp);
        break;
      case 1410:
        scrollEscapeGiran = new ScrollEscapeGiran(temp);
        break;
      case 1507:
      case 1508:
        blessEva = new BlessEva(temp);
        break;
      case 1818:
        trollBelt = new TrollBelt(temp);
        break;
      case 1819:
        ogreBelt = new OgreBelt(temp);
        break;
      default:
        switch (temp.getType1()) {
          case 1:
            itemWeaponInstance = new ItemWeaponInstance(temp);
            break;
          case 2:
            switch (temp.getItemId()) {
              case 352:
                shuromItem_05 = new ShuromItem_05(temp);
                break;
            } 
            itemArmorInstance = new ItemArmorInstance(temp);
            break;
          case 3:
            itemMagicInstance = new ItemMagicInstance(temp);
            break;
        } 
        switch (temp.getItemId()) {
          case 343:
            scrollEscapePledge = new ScrollEscapePledge(temp);
            break;
          case 345:
            shuromItem_02 = new ShuromItem_02(temp);
            break;
          case 346:
            shuromItem_01 = new ShuromItem_01(temp);
            break;
          case 347:
            shuromItem_03 = new ShuromItem_03(temp);
            break;
          case 348:
            shuromItem_04 = new ShuromItem_04(temp);
            break;
        } 
        itemInstance1 = new ItemInstance(temp);
        break;
    } 
    switch (temp.getItemId()) {
      case 355:
        monthCards = new MonthCards(temp);
        break;
      case 356:
        monthCardsOther = new MonthCardsOther(temp);
        break;
    } 
    if (40136 <= temp.getItemId() && temp.getItemId() <= 40161)
      fireCracker = new FireCracker(temp); 
    return (ItemInstance)fireCracker;
  }
  
  public void ChangeItem(ItemInstance temp, PcInstance pc) {}
}
