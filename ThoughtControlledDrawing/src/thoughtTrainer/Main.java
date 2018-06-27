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
	public static long actionList = 0;
	
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
//					setMentalCommandActions(engineUserID, EmoState.IEE_MentalCommandAction_t.MC_NEUTRAL.ToInt());
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
	

	public static void setMentalCommandActions(IntByReference userID, int command) {
		int action = 0;
		switch(command){
		case 0: 
			System.out.println("Set Action to Neutral");
			action = EmoState.IEE_MentalCommandAction_t.MC_NEUTRAL.ToInt();
			break;
		case 1: 
			System.out.println("Set Action to Lift");
			action = EmoState.IEE_MentalCommandAction_t.MC_LIFT.ToInt();
			break;
		case 2:
			System.out.println("Set Action to Drop");
			action = EmoState.IEE_MentalCommandAction_t.MC_DROP.ToInt();
			break;
		case 3:
			System.out.println("Set Action to Left");
			action = EmoState.IEE_MentalCommandAction_t.MC_LEFT.ToInt();
			break;
		case 4:
			System.out.println("Set Action to Right");
			action = EmoState.IEE_MentalCommandAction_t.MC_RIGHT.ToInt();
			break;
		}
		
		int errorCode = Edk.INSTANCE.IEE_MentalCommandSetTrainingAction(userID.getValue(), action);
		errorCode = Edk.INSTANCE.IEE_MentalCommandSetTrainingControl(userID.getValue(), Edk.IEE_MentalCommandTrainingControl_t.MC_START.getType());
		
	}

	private static void setActiveActions(IntByReference userID) {
		long action1 = EmoState.IEE_MentalCommandAction_t.MC_LIFT.ToInt();
		long action2 = EmoState.IEE_MentalCommandAction_t.MC_RIGHT.ToInt();
		long action3 = EmoState.IEE_MentalCommandAction_t.MC_LEFT.ToInt();
		long action4 = EmoState.IEE_MentalCommandAction_t.MC_DROP.ToInt();
		actionList = action1 | action2 | action3 | action4;
		
		int errorCode = Edk.INSTANCE.IEE_MentalCommandSetActiveActions(userID.getValue(), actionList);
		
		if(errorCode == EdkErrorCode.EDK_OK.ToInt()) {
			System.out.println("Setting Mental Command active actions (LIFT|RIGHT|LEFT|DROP) for user " + userID.getValue());
		} else {
			System.out.println("Setting Mental Command Error:" + errorCode);
		}
	}
	
	private void trainMentalCommandActions(IntByReference userId){
		if(Edk.INSTANCE.IEE_SaveUserProfile(userId.getValue(), savingURL) == EdkErrorCode.EDK_OK.ToInt()){
			System.out.println("Profile Saved");
		} else {
			System.out.println("Can't Save Profile");
		}
	}



}
