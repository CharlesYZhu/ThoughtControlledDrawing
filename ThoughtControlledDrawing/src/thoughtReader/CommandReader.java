package thoughtReader;

import com.emotiv.Iedk.*;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.*;

/** Simple example of JNA interface mapping and usage. */
public class CommandReader {
	
	//Step 1 - Indicate location to save local profiles
	//private static String profileNameForLoading = "C:\\Users\\Charles\\Desktop\\Emotiv_Profiles\\profile1.emu";
	//private String profileNameForSaving = "C:\\Users\\Charles\\Desktop\\Emotiv_Profiles\\profile1.emu";
	public static IntByReference engineUserID = null;
	public static IntByReference userCloudID = null;
	public static IntByReference profileID = null;
	public static String profileName = null;
	//Step 1 ---- END ----
	
	public static void main(String[] args) {
		
		//** Step 1 for Cloud profile **
		String userName = "joseph.quick";
		String password = "Inner.Workings.9";
		profileName = "joseph.quick";
		
		engineUserID = new IntByReference(0);
		userCloudID  = new IntByReference(0);
		profileID 	 = new IntByReference(-1);
		
		//Step 2 create event
		Pointer eEvent = Edk.INSTANCE.IEE_EmoEngineEventCreate();
		Pointer eState = Edk.INSTANCE.IEE_EmoStateCreate();
		//Step 2 ---- END ----
		IntByReference userID = null;
		int state = 0;

		userID = new IntByReference(0);
		
		//Step 3 connect to the Emotiv Engine
		if (Edk.INSTANCE.IEE_EngineConnect("Emotiv Systems-5") != EdkErrorCode.EDK_OK
				.ToInt()) {
			System.out.println("Emotiv Engine start up failed.");
			return;
		}
		//Step 3 ---- END ----
		
		//Main loop
		while (true) {
			state = Edk.INSTANCE.IEE_EngineGetNextEvent(eEvent);

			// New event needs to be handled
			if (state == EdkErrorCode.EDK_OK.ToInt()) {

				int eventType = Edk.INSTANCE.IEE_EmoEngineEventGetType(eEvent);
				Edk.INSTANCE.IEE_EmoEngineEventGetUserId(eEvent, userID);
				
				if(eventType == Edk.IEE_Event_t.IEE_UserAdded.ToInt()) {
					//Step 5 - When user is added, load profile
					System.out.println("User " + userID.getValue() + " added");
					loadProfile(userID.getValue());
				}
				else if(eventType == Edk.IEE_Event_t.IEE_UserRemoved.ToInt()) {
					System.out.println("User removed");
				}
				else if(eventType == Edk.IEE_Event_t.IEE_EmoStateUpdated.ToInt()) {
					//For displaying detected mental commands
					Edk.INSTANCE.IEE_EmoEngineEventGetEmoState(eEvent, eState);
					showCurrentAction(eState);
				}
				else if(eventType == Edk.IEE_Event_t.IEE_MentalCommandEvent.ToInt()) {
					//TODO: For training
				}


			} else if (state != EdkErrorCode.EDK_NO_EVENT.ToInt()) {
				System.out.println("Internal error in Emotiv Engine!");
				break;
			}
		}

		Edk.INSTANCE.IEE_EngineDisconnect();
		Edk.INSTANCE.IEE_EmoStateFree(eState);
		Edk.INSTANCE.IEE_EmoEngineEventFree(eEvent);
		System.out.println("Disconnected!");
	}
	
	//load the profile
	private static void loadProfile(int userID) {
		//Local version
//		if(Edk.INSTANCE.IEE_LoadUserProfile(userID, profileNameForLoading) == EdkErrorCode.EDK_OK.ToInt()){
//			System.out.println("Loading Profile : Successful");
//		} else {
//			System.out.println("Can't load profile or one does not exist");
//		}
		
		int ProfileNum = EmotivCloudClient.INSTANCE.EC_GetAllProfileName(userCloudID.getValue());
		
		if(ProfileNum > 0) {
			if (EmotivCloudClient.INSTANCE.EC_LoadUserProfile(userCloudID.getValue(), engineUserID.getValue(), profileID.getValue(),
					-1) == EdkErrorCode.EDK_OK.ToInt())
				System.out.println("Loading finished");
			else
				System.out.println("Loading failed");
		}
	}
	
	private static void showCurrentAction(Pointer eState) {
		System.out.println(EmoState.INSTANCE.IS_MentalCommandGetCurrentAction(eState));
	}
}

