package exceptions; 
public class HackingException extends Throwable {
	
	private static final long serialVersionUID = 714417083065507764L;

	public HackingException(){
		super();
	}
	
	public HackingException(Throwable oops){
		super(oops);
	}

}