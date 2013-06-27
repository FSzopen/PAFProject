package database;

import java.util.ArrayList;

public class GroupedAttack extends Classification {

	public GroupedAttack(String name) {
		super(name);
	}

	public GroupedAttack(String string, Attack att) {
		super(string, att);
	}

	@Override
	public boolean compareTo(String name) {
		return false;
	}

	@Override
	public boolean compareTo(Attack attack) {
		ArrayList<Attack> attackList=this.getAttackList();
		Attack tempAttack = attackList.get(0);
		for(Attack attackInList : attackList) {
			if((tempAttack.timeBefore(attackInList))&&(attackInList.timeBefore(attack)))
				tempAttack=attackInList;		
		}
		if(tempAttack.sameAttackgroup(attack)) 
			return true;
		else
			return false;
	}

	@Override
	public boolean before(Classification classification) {
		// TODO Auto-generated method stub
		return false;
	}

}