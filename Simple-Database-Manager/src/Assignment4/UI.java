package Assignment4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class UI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename="";
		StringReader keyboard=new StringReader();
		OrderedDictionary dictionary=null;          //有序字典对象,即一棵二叉搜索树
		if (args.length==1) {
			filename=args[0];
		}else {
			System.err.println("Must input the filename of the input!");
			System.exit(1);
		}
		
		dictionary=buildDictionary(filename);
		while(true){
			System.out.println("\n1.check for a text");
			System.out.println("2.play a sound");
			System.out.println("3.display an image");
			System.out.println("4.add a record");
			System.out.println("5.remove a record");
			System.out.println("6.search a record");
			System.out.println("7.quit");
			String line=keyboard.read("Enter next command: ");
			if (line.equals("1")) {                  //搜索一个普通文本记录
				String keyword=keyboard.read("Please enter the key word: ");
				Key key=new Key(keyword, 1);
				Record record=dictionary.find(key);
				if (record==null) {
					System.err.println("The matched record doesn't exits!");
				}else {
					System.out.println("The text is:");
					System.out.println(record.getData());
				}
			}else if(line.equals("2")){                //播放音频文件
				String keyword=keyboard.read("Please enter the key word of the sound: ");
				Key key=new Key(keyword, 2);
				Record record=dictionary.find(key);
				if (record==null) {
					System.err.println("The matched record doesn't exits!");
				}else {
					try {
						SoundPlayer player = new SoundPlayer();
						player.play(record.getData());
					} catch (MultimediaException e) {
						System.out.println(e.getMessage());
					}
				}
			}else if (line.equals("3")) {            //查看照片文件
				String keyword=keyboard.read("Please enter the key word of the image: ");
				Key key=new Key(keyword, 3);
				Record record=dictionary.find(key);
				if (record==null) {
					System.err.println("The matched record doesn't exits!");
				}else {
					try {
						PictureViewer viewer = new PictureViewer();
						viewer.show(record.getData());
					} catch (MultimediaException e) {
						System.out.println(e.getMessage());
					}
				}
			}else if(line.equals("4")){                 //往字典中添加一条新的记录
				String keyword=keyboard.read("Please enter the key word of this record: ");
				String data=keyboard.read("Please enter the data of this record: ");
				int type;
				if (data.endsWith(".wav")||data.endsWith(".mid")) {
					type=2;
				}else if(data.endsWith(".gif")||data.endsWith(".jpg")) {
					type=3;
				}else {
					type=1;
				}
				Key key=new Key(keyword, type);
				Record record=new Record(key, data);
				try {
					dictionary.insert(record);
				} catch (Exception e) {
					// TODO: handle exception
					System.err.println(e.getMessage());
				}
			}else if(line.equals("5")){               //从字典中删除一条记录
				String keyword=keyboard.read("Please enter the key word of this record: ");
				String typeString=keyboard.read("Please enter the type of this record: ");
				Key key=new Key(keyword, Integer.valueOf(typeString));
				try {
					dictionary.remove(key);
					System.out.println("Remove successful!");
				} catch (Exception e) {
					// TODO: handle exception
					System.err.println(e.getMessage());
				}
			}else if (line.equals("6")) {           //查找字典中任意一条记录
				String keyword=keyboard.read("Please enter the key word of this record: ");
				String typeString=keyboard.read("Please enter the type of this record: ");
				Key key=new Key(keyword, Integer.valueOf(typeString));
				Record record=dictionary.find(key);
				if (record==null) {
					System.err.println("The matched record doesn't exits!");
				}else {
					System.out.println("The data is:");
					System.out.println(record.getData());
				}
			}else if (line.equals("7")) {
				System.out.println("Good bye!");
				break;
			}else {
				System.err.println("Invalid command!");
			}
		}
	}

	//从给定的文件中读取信息，构造一棵二叉搜索树
	private static OrderedDictionary buildDictionary(String filename){
		OrderedDictionary dictionary=new OrderedDictionary();
		BufferedReader reader=null;
		try {
			reader=new BufferedReader(new FileReader(new File(filename)));
			String line=null;
			while((line=reader.readLine())!=null){        //读取记录的word部分
				int type=0;
				String data=reader.readLine();         //读取记录的data部分
				if (data.endsWith(".wav")||data.endsWith(".mid")) {  //根据data的内容判断记录的类型
					type=2;
				}else if(data.endsWith(".gif")||data.endsWith(".jpg")) {
					type=3;
				}else {
					type=1;
				}
				Key key=new Key(line, type);
				Record record=new Record(key, data);
				dictionary.insert(record);
			}
		} catch (Exception e) {       //如果构造过程中出错，则输出警示信息
			// TODO: handle exception
			System.err.println("Occur error when building dictionary!");
		}finally{
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dictionary;
	}
}
