package mockView;

import java.rmi.RemoteException;

import model.GameInterface;
import model.LandType;
import utility.Observer;
import view.ViewInterface;

public class MockView implements ViewInterface,Observer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int actionNumber = 0;
	private GameInterface game;
	private String nickname = "Vikki";

	public String getString() throws RemoteException {
		switch (actionNumber) {
		case 0:
			actionNumber++;
			return ""+666;
		case 1:
			actionNumber++;
			return ""+30;
		case 2:
			actionNumber++;
			return ""+51;
		case 3:
			actionNumber++;
			return ""+47;
		case 4:
			actionNumber++;
			return ""+47;
		case 5:
			actionNumber++;
			return ""+52;
		case 6:
			actionNumber++;
			return ""+42;
		case 7:
			actionNumber++;
			return ""+777;
		case 8:
			actionNumber++;
			return ""+61;
		case 9:
			actionNumber++;
			return ""+888;
		case 10:
			actionNumber++;
			return ""+6;
		case 11:
			actionNumber++;
			return LandType.FIELD.toString();
		case 12:
			actionNumber++;
			return LandType.FOREST.toString();
		case 13:
			actionNumber++;
			return ""+26;
		case 14:
			actionNumber++;
			return LandType.FOREST.toString();
		case 15:
			actionNumber++;
			return LandType.FOREST.toString();
		case 16:
			actionNumber++;
			return LandType.FOREST.toString();
		case 17:
			actionNumber++;
			return LandType.FOREST.toString();
		case 18:
			actionNumber++;
			return LandType.FOREST.toString();
		case 19:
			actionNumber++;
			return LandType.RIVER.toString();
		case 20:
			actionNumber++;
			return ""+16;
		case 21:
			actionNumber++;
			return ""+6;
		case 22:
			actionNumber++;
			return ""+6;
		case 23:
			actionNumber++;
			return ""+35;
		case 24:
			actionNumber++;
			return ""+53;
		case 25:
			actionNumber++;
			return ""+61;
		case 26:
			actionNumber++;
			return ""+6;
		default:
			break;
		}
		return null;
	}

	public void printString(String string) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void setGame(GameInterface game) throws RemoteException {
		this.game = game;
		
	}

	public String getNickname() throws RemoteException {
		return this.nickname;
	}

	public void printBoard() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public String getConnectionType() throws RemoteException {
		return "LocalMock";
	}

	public GameInterface getGameInterface() throws RemoteException {
		return this.game;
	}

	public void update() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void setNickname(String nickname) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	
	public static void setActionNumber(int actionNumber){
		MockView.actionNumber =actionNumber;
	}

}
