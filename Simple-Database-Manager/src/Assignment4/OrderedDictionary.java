package Assignment4;

public class OrderedDictionary implements OrderedDictionaryADT{
	
	private Node root;       //二叉搜索树的根节点
	
	public OrderedDictionary() {
		// TODO Auto-generated constructor stub
		root=null;
	}

	@Override
	public Record find(Key k) {
		// TODO Auto-generated method stub
		if (null==root) {         //如果二叉搜索树为空，则直接返回null
			return null;
		}
		Node current=root;
		while(current!=null){
			int cmp=current.record.getKey().compareTo(k);
			if (cmp>0) {          //如果要查找的key值小于该节点的key值，则搜索该节点的左子树
				current=current.left;
			}else if (cmp<0) {    //如果要查找的key值大于该节点的key值，则搜索该节点的右子树
				current=current.right;
			}else {               //若相等则直接返回
				return current.record;
			}
		}
		return null;
	}

	@Override
	public void insert(Record r) throws DictionaryException {
		// TODO Auto-generated method stub
		Node parentNode=null;
		Node newNode=new Node(r, null, null, null);      //新建一个节点对象
		if (null==root) {      //若二叉搜索树为空，则直接将新建的节点当做root节点然后返回
			root=newNode;
			return;
		}
		
		Node pNode=root;
		while(pNode!=null){
			parentNode=pNode;
			int cmp=pNode.record.getKey().compareTo(r.getKey());
			if (cmp>0) {          //如果要插入记录的key值小于该节点的key值，则搜索该节点的左子树
				pNode=pNode.left;
			}else if (cmp<0) {    //如果要插入记录的key值大于该节点的key值，则搜索该节点的右子树
				pNode=pNode.right;
			}else {            //如果key值相等，则抛出异常
				throw new DictionaryException("The key has already exits!");
			}
		}
		
		//将新的节点插入到pNode后面
		if (r.getKey().compareTo(parentNode.record.getKey())<0) {
			parentNode.left=newNode;
			newNode.parent=parentNode;
		}else {
			parentNode.right=newNode;
			newNode.parent=parentNode;
		}
	}

	@Override
	public void remove(Key k) throws DictionaryException {
		// TODO Auto-generated method stub
		Node current=root;
		while(current!=null){
			int cmp=current.record.getKey().compareTo(k);
			if (cmp>0) {
				current=current.left;
			}else if (cmp<0) {
				current=current.right;
			}else {
				break;
			}
		}
		if (current==null) {          //如要删除的key值不存在，则抛出一个异常
			throw new DictionaryException("The key doesn't exits!");
		}
		
		//key值对应的节点即无左子节点，又无右子节点
		if (current.left==null&&current.right==null) {
			Node parentNode=current.parent;
			if (parentNode==null) {
				root=null;
				return;
			}
			if (current==parentNode.left) {
				parentNode.left=null;
			}else {
				parentNode.right=null;
			}
		}else if(current.left==null&&current.right!=null){    //key值对应的节点无左子节点，有右子节点
			Node parentNode=current.parent;
			if (current==parentNode.left) {
				parentNode.left=current.right;
				current.right.parent=parentNode;
			}else {
				parentNode.right=current.right;
				current.right.parent=parentNode;
			}
		}else if(current.left!=null&&current.right==null){   //key值对应的节点有左子节点，无右子节点
			Node parentNode=current.parent;
			if (current==parentNode.left) {
				parentNode.left=current.left;
				current.right.parent=parentNode;
			}else {
				parentNode.right=current.left;
				current.right.parent=parentNode;
			}
		}else {           //key值对应的节点左子节点和右子节点均非空，则删除该节点的后继节点，并用该后继节点取代该节点
			Node successorNode=successor(current);
			remove(successorNode.record.getKey());
			current.record=successorNode.record;
		}
	}

	@Override
	public Record successor(Key k) {
		// TODO Auto-generated method stub
		if (null==root) {       //若二叉搜索树为空，直接返回null
			return null;
		}
		Node current=root;
		while(current!=null){        //遍历二叉树，找到该key值k所对应的节点为current
			int cmp=current.record.getKey().compareTo(k);
			if (cmp>0) {
				current=current.left;
			}else if (cmp<0) {
				current=current.right;
			}else {
				break;
			}
		}
		
		//若该节点的右子节点不为空，则其后继节点就是右子树中的最小关键字节点
		if (current.right!=null) {
			if (null==current.right) {
				return null;
			}
			Node node=current.right;
			while(node.left!=null){
				node=node.left;
			}
			return node.record;
		}
		
		//若该节点current的右子节点为空
		Node parentNode=current.parent;
		while(parentNode!=null&&current==parentNode.right){
			current=parentNode;
			parentNode=parentNode.parent;
		}
		return parentNode.record;
	}

	@Override
	public Record predecessor(Key k) {
		// TODO Auto-generated method stub
		if (null==root) {          //若二叉搜索树为空，直接返回null
			return null;
		}
		Node current=root;
		while(current!=null){       //遍历二叉树，找到该key值k所对应的节点为current
			int cmp=current.record.getKey().compareTo(k);
			if (cmp>0) {
				current=current.left;
			}else if (cmp<0) {
				current=current.right;
			}else {
				break;
			}
		}
		
		if (current==null) {
			return null;
		}
		//若该节点的左子节点不为空，则其前驱节点就是左子树中的最大关键字节点
		if (current.left!=null) {
			Node node=current.left;
			while(node.right!=null){
				node=node.right;
			}
			return node.record;
		}
		
		//若该节点的左子节点为空
		Node parentNode=current.parent;
		while(parentNode!=null&&current==parentNode.left){
			current=parentNode;
			parentNode=parentNode.parent;
		}
		return parentNode.record;
	}

	@Override
	public Record smallest() {
		// TODO Auto-generated method stub
		if (null==root) {   //若二叉搜索树为空，直接返回null
			return null;
		}
		Node node=root;
		while(node.left!=null){        //找到二叉树最最左端的一个节点，并返回
			node=node.left;
		}
		return node.record;
	}

	@Override
	public Record largest() {
		// TODO Auto-generated method stub
		if (null==root) {      //若二叉搜索树为空，直接返回null
			return null;
		}
		Node node=root;
		while(node.right!=null){       //找到二叉树最最右端的一个节点，并返回
			node=node.right;
		}
		return node.record;
	}
	
	//寻找一个节点的后继节点
	private Node successor(Node node){
		if (node==null) {
			return null;
		}
		if (node.right!=null) {
			if (null==node.right) {
				return null;
			}
			Node temp=node.right;
			while(temp.left!=null){
				temp=temp.left;
			}
			return temp;
		}
		
		Node parentNode=node.parent;
		while(parentNode!=null&&node==parentNode.right){
			node=parentNode;
			parentNode=parentNode.parent;
		}
		return parentNode;
	}
	
	//二叉树节点类
	public class Node{
		Record record;    //记录信息
		Node parent;      //父节点
		Node left;        //左子节点
		Node right;       //右子节点
		
		//构造函数
		public Node(Record record,Node parent,Node left,Node right){
			this.record=record;
			this.parent=parent;
			this.left=left;
			this.right=right;
		}
	}

}
