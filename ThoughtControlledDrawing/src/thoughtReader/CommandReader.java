package thoughtReader;

import com.emotiv.Iedk.*;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.*;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;


public class CommandReader {
	public static final Pointer eEvent = Edk.INSTANCE.IEE_EmoEngineEventCreate();
	public static final Pointer eState = Edk.INSTANCE.IEE_EmoStateCreate();

	public static int state = 0;
	public static IntByReference engineUserID = null;
	public static IntByReference userCloudID = null;
	public static IntByReference profileID = null;
	public static String profileName = null;
	
	public static Boolean mouseIsDown;
	
	public static void main(String[] args) throws InterruptedException, AWTException {
		Robot bot = new Robot();

		String userName = "joseph.quick";
		String password = "Inner.Workings.9";
		profileName = "Charles.Zhu";
		
		mouseIsDown = false;

		
		engineUserID = new IntByReference(0);
		userCloudID  = new IntByReference(0);
		profileID 	 = new IntByReference(-1);

		if (Edk.INSTANCE.IEE_EngineConnect("Emotiv Systems-5") != EdkErrorCode.EDK_OK.ToInt()) {
			System.out.println("Emotiv Engine start up failed.");
			return;
		}

		if (EmotivCloudClient.INSTANCE.EC_Connect() != EdkErrorCode.EDK_OK.ToInt()) {
			System.out.println("Cannot connect to Emotiv Cloud");
			return;
		}

		if (EmotivCloudClient.INSTANCE.EC_Login(userName, password) != EdkErrorCode.EDK_OK.ToInt()) {
			System.out.println("Your login attempt has failed. The username or password may be incorrect");
			return;
		}

		System.out.println("Logged in as " + userName);

		if (EmotivCloudClient.INSTANCE.EC_GetUserDetail(userCloudID) != EdkErrorCode.EDK_OK.ToInt()) {
			return;
		}

		MainLoop(bot);
		
		System.out.println("Quitting...");

		Edk.INSTANCE.IEE_EngineDisconnect();
		Edk.INSTANCE.IEE_EmoStateFree(eState);
		Edk.INSTANCE.IEE_EmoEngineEventFree(eEvent);
		
		System.out.println("Wating for the Thread");

	}

	

	public static void MainLoop(Robot bot) {

		while (true) {

			state = Edk.INSTANCE.IEE_EngineGetNextEvent(eEvent);

			if (state == EdkErrorCode.EDK_OK.ToInt()) {
				int eventType = Edk.INSTANCE.IEE_EmoEngineEventGetType(eEvent);
				Edk.INSTANCE.IEE_EmoEngineEventGetUserId(eEvent, engineUserID);

				if (eventType == Edk.IEE_Event_t.IEE_UserAdded.ToInt()) {
					System.out.println("New user " + engineUserID.getValue() + " added");
					LoadProfile(userCloudID.getValue(), engineUserID.getValue(), false, profileName);
				}
				else if (eventType == Edk.IEE_Event_t.IEE_UserRemoved.ToInt())
					System.out.println("User " + engineUserID.getValue() + " has been removed.");

				else if (eventType == Edk.IEE_Event_t.IEE_EmoStateUpdated.ToInt()) {
					Edk.INSTANCE.IEE_EmoEngineEventGetEmoState(eEvent, eState);

//					if (EmoState.INSTANCE.IS_FacialExpressionGetSmileExtent(eState) > 0) {
//						System.out.println("Smile");
//					}
					
					if (EmoState.INSTANCE.IS_FacialExpressionIsBlink(eState) == 1) {
						if(mouseIsDown) {
							handleMouseClick(bot, false);
						}
						else if (mouseIsDown == false) {
							handleMouseClick(bot, true);
						}
						System.out.println("Blink");
					}
					
					handleMouseMove(bot, EmoState.INSTANCE.IS_MentalCommandGetCurrentAction(eState), EmoState.INSTANCE.IS_MentalCommandGetCurrentActionPower(eState));
					
//					System.out.println("Action: " + EmoState.INSTANCE.IS_MentalCommandGetCurrentAction(eState) + 
//							" | Power: " + EmoState.INSTANCE.IS_MentalCommandGetCurrentActionPower(eState));
				}
				else {
				}

			} else if (state != EdkErrorCode.EDK_NO_EVENT.ToInt())
				System.out.println("Internal error in Emotiv Engine!");
		}

	}

	private static void handleMouseMove(Robot bot, int action, float power) {
		int currentX = MouseInfo.getPointerInfo().getLocation().x;
		int currentY = MouseInfo.getPointerInfo().getLocation().y;
		switch(action) {
		case 8:
			if(power > 0) {
				System.out.println("Mouse Up");
				bot.mouseMove(currentX , currentY - (int)(power*10) );
				
			}
			break;
		case 16:
			if(power > 0) {
				System.out.println("Mouse Down");
				bot.mouseMove(currentX , currentY + (int)(power*10) );
			}
			break;
		case 32:
			if(power > 0) {
				System.out.println("Mouse Left");
				bot.mouseMove(currentX - (int)(power*10) , currentY);
			}
			break;
		case 64:
			if(power > 0) {
				System.out.println("Mouse Right");
				bot.mouseMove(currentX + (int)(power*10) , currentY);
			}
			break;
		default:
			//Do nothing
		}
	}
	
	private static void handleMouseClick(Robot bot, Boolean mouseDown) {
		if(mouseDown) {
			bot.mouseRelease(InputEvent.BUTTON1_MASK);
		} else {
			bot.mousePress(InputEvent.BUTTON1_MASK);
		}
	}



	private static void LoadProfile(int userCloudID, int engineUserID, boolean save, String profileName) {
		int getNumberProfile = EmotivCloudClient.INSTANCE.EC_GetAllProfileName(userCloudID);
		
		/* This part is needed */
		int result = EmotivCloudClient.INSTANCE.EC_GetProfileId(userCloudID, profileName, profileID);

			if (getNumberProfile > 0) {
				if (EmotivCloudClient.INSTANCE.EC_LoadUserProfile(userCloudID, engineUserID, profileID.getValue(),
						-1) == EdkErrorCode.EDK_OK.ToInt()) {
					System.out.println("Loading finished");
					//Current Mental Command Activiation Level
					IntByReference level = new IntByReference(0);
					Edk.INSTANCE.IEE_MentalCommandGetActivationLevel(engineUserID, level);
					System.out.println("Current MentalCommand Activation level: " + level.getValue());
					//Overall skill rating
					FloatByReference skill = new FloatByReference(0);
					Edk.INSTANCE.IEE_MentalCommandGetOverallSkillRating(engineUserID, skill);
					System.out.println("Current overall skill rating: " + skill.getValue());
				}
				else
					System.out.println("Loading failed");
			}
			return;
	}
	
}
