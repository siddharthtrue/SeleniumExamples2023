package seleniumSessions;

public class VerifyText
{
	public static boolean equalValues(String actResult, String expResult)
	{
		if(actResult.equals(expResult))
		{
			System.out.println(actResult + " is equal to expected result - " + expResult);
			return true;
		}
		else
		{
			System.out.println(actResult + " is not equal to expected result - " + expResult);
			return false;
		}
	}
	
	public static boolean containValues(String actResult, String expResult)
	{
		if(actResult.contains(expResult))
		{
			System.out.println("URL " + actResult + " contains " + expResult);
			return true;
		}
		else
		{
			System.out.println(actResult + " does not contain " + expResult);
			return false;
		}
	}
}
