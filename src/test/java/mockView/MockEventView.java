package mockView;

import java.rmi.RemoteException;

import model.GameInterface;
import model.LandType;
import utility.Observer;
import view.ViewInterface;

public class MockEventView implements ViewInterface,Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int actionNumber = 0;
	private GameInterface game;
	private String nickname = "Vikki";

	public void update() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public String getString() throws RemoteException {
		switch (actionNumber) {
		case 0:
			actionNumber++;
			return ""+11;
		case 1:
			actionNumber++;
			return ""+1;
		case 2:
			actionNumber++;
			return ""+49;
		case 3:
			actionNumber++;
			return LandType.FOREST.toString();
		case 4:
			actionNumber++;
			return ""+50;
		case 5:
			actionNumber++;
			return LandType.FIELD.toString();
		case 6:
			actionNumber++;
			return ""+47;
		case 7:
			actionNumber++;
			return ""+27;
		case 8:
			actionNumber++;
			return ""+31;
		case 9:
			actionNumber++;
			return ""+36;
		case 10:
			actionNumber++;
			return "N";
		case 11:
			actionNumber++;
			return "Y";
		case 12:
			actionNumber++;
			return LandType.FIELD.toString();
		case 13:
			actionNumber++;
			return ""+5;
		case 14:
			actionNumber++;
			return "Y";
		case 15:
			actionNumber++;
			return LandType.FIELD.toString();
		case 16:
			actionNumber++;
			return ""+2;
		case 17:
			actionNumber++;
			return "N";
		case 18:
			actionNumber++;
			return "Y";
		case 19:
			actionNumber++;
			return ""+0;
		case 20:
			actionNumber++;
			return "N";
		case 21:
			actionNumber++;
			return "Y";
		case 22:
			actionNumber++;
			return ""+0;
		case 23:
			actionNumber++;
			return "N";
		default:
			break;
		}
		return "N";
	}

	public void printString(String string) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void setGame(GameInterface game) throws RemoteException {
		this.game =game;
		
	}

	public String getNickname() throws RemoteException {
		return this.nickname;
	}

	public void printBoard() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public String getConnectionType() throws RemoteException {
		return "MOCK";
	}

	public GameInterface getGameInterface() throws RemoteException {
		return this.game;
	}

	public void setNickname(String nickname) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
