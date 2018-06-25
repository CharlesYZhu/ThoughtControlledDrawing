package thoughtTrainer;

import com.emotiv.Iedk.Edk;
import com.emotiv.Iedk.EdkErrorCode;
import com.emotiv.Iedk.EmoState;
import com.emotiv.Iedk.EmoState.IEE_MentalCommandAction_t;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class Main {

	public static final Pointer eEvent = Edk.INSTANCE.IEE_EmoEngineEventCreate();
	public static final Pointer eState = Edk.INSTANCE.IEE_EmoStateCreate();
	public static IntByReference engineUserID = null;
	public String savingURL = "C:\\Users\\Charles\\Desktop\\Emotiv_Profiles";
	public static int state = 0;
	
	public static void main(String[] args) {
		
		ThoughtTrainer trainer = new ThoughtTrainer();
		
		if(Edk.INSTANCE.IEE_EngineConnect("Emotiv Systems-5") != EdkErrorCode.EDK_OK.ToInt()) {
			System.out.println("Emotiv Engine startup failed");
		}
		
		while(true) {
			state = Edk.INSTANCE.IEE_EngineGetNextEvent(eEvent);
			
			if(state == EdkErrorCode.EDK_OK.ToInt()) {
				int eventType = Edk.INSTANCE.IEE_EmoEngineEventGetType(eEvent);
				Edk.INSTANCE.IEE_EmoEngineEventGetUserId(eEvent, engineUserID);
				
				if(eventType == Edk.IEE_Event_t.IEE_UserAdded.ToInt()) {
					System.out.println("New User " + engineUserID + " added");
				
					setActiveActions(engineUserID);
					setMentalCommandActions(engineUserID, EmoState.IEE_MentalCommandAction_t.MC_NEUTRAL);
				}
				else if(eventType == Edk.IEE_Event_t.IEE_UserRemoved.ToInt()) {
					System.out.println("User has been removed");
				}
				else {
					break;
				}
			} else if (state != EdkErrorCode.EDK_NO_EVENT.ToInt()) {
				System.out.println("Internal error in Emotiv Engine");
			}
		}
		
		Edk.INSTANCE.IEE_EngineDisconnect();
		Edk.INSTANCE.IEE_EmoStateFree(eState);
		Edk.INSTANCE.IEE_EmoEngineEventFree(eEvent);
		
	}
	

	private static void setMentalCommandActions(IntByReference userID, IEE_MentalCommandAction_t command) {
		// TODO Auto-generated method stub
		long action1 = EmoState.IEE_MentalCommandAction_t.MC_LIFT.ToInt();
		long action2 = EmoState.IEE_MentalCommandAction_t.MC_RIGHT.ToInt();
		long action3 = EmoState.IEE_MentalCommandAction_t.MC_LEFT.ToInt();
		long action4 = EmoState.IEE_MentalCommandAction_t.MC_DROP.ToInt();
		long actionList = action1 | action2 | action3 | action4;
		
		int errorCode = Edk.INSTANCE.IEE_MentalCommandSetActiveActions(userID.getValue(), actionList);
		
		if(errorCode == EdkErrorCode.EDK_OK.ToInt()) {
			System.out.println("Setting Mental Command active actions (LIFT|RIGHT|LEFT|DROP) for user " + userID.getValue());
		} else {
			System.out.println("Setting Mental Command Error:" + errorCode);
		}
	}

	private static void setActiveActions(IntByReference userID) {
		// TODO Auto-generated method stub
		
	}



}
