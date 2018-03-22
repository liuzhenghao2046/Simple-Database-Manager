package Assignment4;

public class Key {
	
	
    private String word;
    private int type;
    public Key(String word,int type){
    	this.word=word;
    	this.type=type;
    }
    
    public String getWord(){
    	return word;
    }
    
    public int getType(){
    	return type;
    }
    
    public int compareTo(Key k){
    	if (word.compareTo(k.word)==0&&type==k.type) {
			return 0;
		}else if(word.compareTo(k.word)<0||(word.compareTo(k.word)==0&&type<k.type)) {
			return -1;
		}else {
			return 1;
		}
    }
}
