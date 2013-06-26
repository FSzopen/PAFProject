package database;

import java.util.ArrayList;

public class MainBase {

	private ArrayList<Attack> attackList = new ArrayList<Attack>();
	private ArrayList<AttackDay> attackDayList = new ArrayList<AttackDay>();
	private ArrayList<AttackUsername> attackUsernameList = new ArrayList<AttackUsername>();
	private ArrayList<Country> attackCountryList = new ArrayList<Country>();
	private ArrayList<IPAddress> attackIPAddressList = new ArrayList<IPAddress>();
	private ArrayList<GroupedAttack> attackGroupedAttackList = new ArrayList<GroupedAttack>();

	public MainBase() {}

	// GETTERS
	public ArrayList<Attack> getAttackList() { return attackList; }
	public ArrayList<AttackDay> getAttackDayList() { return attackDayList; }
	public ArrayList<AttackUsername> getAttackUsernameList() { return attackUsernameList; }
	public ArrayList<Country> getAttackCountryList() { return attackCountryList; }
	public ArrayList<IPAddress> getAttackIPAddressList() { return attackIPAddressList; }
	public ArrayList<GroupedAttack> getAttackGroupedAttackList() { return attackGroupedAttackList; }
	
	// SETTERS
	public void setDataBase(ArrayList<Attack> attackList) { this.attackList = attackList; }
	public void setAttackDayList(ArrayList<AttackDay> attackDayList) { this.attackDayList = attackDayList; }
	public void setAttackUsernameList(ArrayList<AttackUsername> attackUsernameList) { this.attackUsernameList = attackUsernameList; }
	public void setAttackCountryList(ArrayList<Country> attackCountryList) { this.attackCountryList = attackCountryList; }
	public void setAttackIPAddressList(ArrayList<IPAddress> attackIPAddressList) { this.attackIPAddressList = attackIPAddressList; }
	public void setAttackGroupedAttackList(ArrayList<GroupedAttack> attackGroupedAttackList) { this.attackGroupedAttackList = attackGroupedAttackList; }
	
}