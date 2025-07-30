public class MotherWaitsForChild {

	private static volatile boolean done = false;

	public static void main(String[] args){
		Runnable childTask = () -> {
			System.out.println("child");
			done = true;
		};

		System.out.println("mother : begin");
		Thread childThread = new Thread(childTask, "child-task");
		childThread.start();

		while(!done){}

		System.out.println("mother: end");
	}
}
