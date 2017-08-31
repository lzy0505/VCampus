package utils;
import java.io.Serializable;

public class Message implements Serializable {
		private Entity entity;
		private int opType;
		public Message(int type){
			this.opType=type;
		}
		public void setEntity(Entity e){
			this.entity=e;
		}
		public Entity getEntity(){
			return entity;
		}
		public int getOpType(){
			return opType;
		}
}

